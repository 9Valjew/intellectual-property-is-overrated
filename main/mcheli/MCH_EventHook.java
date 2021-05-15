package mcheli;

import net.minecraftforge.event.*;
import mcheli.command.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import mcheli.wrapper.*;
import net.minecraftforge.event.entity.living.*;
import java.util.*;
import net.minecraftforge.event.entity.player.*;
import mcheli.chain.*;
import mcheli.aircraft.*;
import net.minecraft.item.*;
import net.minecraftforge.event.entity.*;
import mcheli.weapon.*;

public class MCH_EventHook extends W_EventHook
{
    @Override
    public void commandEvent(final CommandEvent event) {
        MCH_Command.onCommandEvent(event);
    }
    
    @Override
    public void entitySpawn(final EntityJoinWorldEvent event) {
        if (W_Lib.isEntityLivingBase(event.entity) && !W_EntityPlayer.isPlayer(event.entity)) {
            final Entity entity = event.entity;
            final double field_70155_l = entity.field_70155_l;
            final MCH_Config config = MCH_MOD.config;
            entity.field_70155_l = field_70155_l * MCH_Config.MobRenderDistanceWeight.prmDouble;
        }
        else if (event.entity instanceof MCH_EntityAircraft) {
            final MCH_EntityAircraft aircraft = (MCH_EntityAircraft)event.entity;
            if (!aircraft.field_70170_p.field_72995_K && !aircraft.isCreatedSeats()) {
                aircraft.createSeats(UUID.randomUUID().toString());
            }
        }
        else if (W_EntityPlayer.isPlayer(event.entity)) {
            final Entity e = event.entity;
            boolean b = Float.isNaN(e.field_70125_A);
            b |= Float.isNaN(e.field_70127_C);
            b |= Float.isInfinite(e.field_70125_A);
            b |= Float.isInfinite(e.field_70127_C);
            if (b) {
                MCH_Lib.Log(event.entity, "### EntityJoinWorldEvent Error:Player invalid rotation pitch(" + e.field_70125_A + ")", new Object[0]);
                e.field_70125_A = 0.0f;
                e.field_70127_C = 0.0f;
            }
            b = Float.isInfinite(e.field_70177_z);
            b |= Float.isInfinite(e.field_70126_B);
            b |= Float.isNaN(e.field_70177_z);
            b |= Float.isNaN(e.field_70126_B);
            if (b) {
                MCH_Lib.Log(event.entity, "### EntityJoinWorldEvent Error:Player invalid rotation yaw(" + e.field_70177_z + ")", new Object[0]);
                e.field_70177_z = 0.0f;
                e.field_70126_B = 0.0f;
            }
            if (!e.field_70170_p.field_72995_K && event.entity instanceof EntityPlayerMP) {
                MCH_Lib.DbgLog(false, "EntityJoinWorldEvent:" + event.entity, new Object[0]);
                MCH_PacketNotifyServerSettings.send((EntityPlayerMP)event.entity);
            }
        }
    }
    
    @Override
    public void livingAttackEvent(final LivingAttackEvent event) {
        final MCH_EntityAircraft ac = this.getRiddenAircraft(event.entity);
        if (ac == null) {
            return;
        }
        if (ac.getAcInfo() == null) {
            return;
        }
        if (ac.isDestroyed()) {
            return;
        }
        if (ac.getAcInfo().damageFactor > 0.0f) {
            return;
        }
        final Entity attackEntity = event.source.func_76346_g();
        if (attackEntity == null) {
            event.setCanceled(true);
        }
        else if (W_Entity.isEqual(attackEntity, event.entity)) {
            event.setCanceled(true);
        }
        else if (ac.isMountedEntity(attackEntity)) {
            event.setCanceled(true);
        }
        else {
            final MCH_EntityAircraft atkac = this.getRiddenAircraft(attackEntity);
            if (W_Entity.isEqual(atkac, ac)) {
                event.setCanceled(true);
            }
        }
    }
    
    @Override
    public void livingHurtEvent(final LivingHurtEvent event) {
        final MCH_EntityAircraft ac = this.getRiddenAircraft(event.entity);
        if (ac == null) {
            return;
        }
        if (ac.getAcInfo() == null) {
            return;
        }
        if (ac.isDestroyed()) {
            return;
        }
        final Entity attackEntity = event.source.func_76346_g();
        if (attackEntity == null) {
            ac.func_70097_a(event.source, event.ammount * 2.0f);
            event.ammount *= ac.getAcInfo().damageFactor;
        }
        else if (W_Entity.isEqual(attackEntity, event.entity)) {
            ac.func_70097_a(event.source, event.ammount * 2.0f);
            event.ammount *= ac.getAcInfo().damageFactor;
        }
        else if (ac.isMountedEntity(attackEntity)) {
            event.ammount = 0.0f;
            event.setCanceled(true);
        }
        else {
            final MCH_EntityAircraft atkac = this.getRiddenAircraft(attackEntity);
            if (W_Entity.isEqual(atkac, ac)) {
                event.ammount = 0.0f;
                event.setCanceled(true);
            }
            else {
                ac.func_70097_a(event.source, event.ammount * 2.0f);
                event.ammount *= ac.getAcInfo().damageFactor;
            }
        }
    }
    
    public MCH_EntityAircraft getRiddenAircraft(final Entity entity) {
        MCH_EntityAircraft ac = null;
        final Entity ridden = entity.field_70154_o;
        if (ridden instanceof MCH_EntityAircraft) {
            ac = (MCH_EntityAircraft)ridden;
        }
        else if (ridden instanceof MCH_EntitySeat) {
            ac = ((MCH_EntitySeat)ridden).getParent();
        }
        if (ac == null) {
            final List list = entity.field_70170_p.func_72872_a((Class)MCH_EntityAircraft.class, entity.field_70121_D.func_72314_b(50.0, 50.0, 50.0));
            if (list != null) {
                for (int i = 0; i < list.size(); ++i) {
                    final MCH_EntityAircraft tmp = list.get(i);
                    if (tmp.isMountedEntity(entity)) {
                        return tmp;
                    }
                }
            }
        }
        return ac;
    }
    
    @Override
    public void entityInteractEvent(final EntityInteractEvent event) {
        final ItemStack item = event.entityPlayer.func_70694_bm();
        if (item == null) {
            return;
        }
        if (item.func_77973_b() instanceof MCH_ItemChain) {
            MCH_ItemChain.interactEntity(item, event.target, event.entityPlayer, event.entityPlayer.field_70170_p);
            event.setCanceled(true);
        }
        else if (item.func_77973_b() instanceof MCH_ItemAircraft) {
            ((MCH_ItemAircraft)item.func_77973_b()).rideEntity(item, event.target, event.entityPlayer);
        }
    }
    
    @Override
    public void entityCanUpdate(final EntityEvent.CanUpdate event) {
        if (event.entity instanceof MCH_EntityBaseBullet) {
            final MCH_EntityBaseBullet bullet = (MCH_EntityBaseBullet)event.entity;
            bullet.func_70106_y();
        }
    }
}
