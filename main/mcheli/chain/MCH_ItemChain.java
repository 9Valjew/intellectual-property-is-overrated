package mcheli.chain;

import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.entity.item.*;
import mcheli.aircraft.*;
import mcheli.uav.*;
import mcheli.parachute.*;
import mcheli.wrapper.*;
import java.util.*;
import net.minecraft.nbt.*;

public class MCH_ItemChain extends W_Item
{
    public MCH_ItemChain(final int par1) {
        super(par1);
        this.func_77625_d(1);
    }
    
    public static void interactEntity(final ItemStack item, final Entity entity, final EntityPlayer player, final World world) {
        if (!world.field_72995_K && entity != null && !entity.field_70128_L) {
            if (entity instanceof EntityItem) {
                return;
            }
            if (entity instanceof MCH_EntityChain) {
                return;
            }
            if (entity instanceof MCH_EntityHitBox) {
                return;
            }
            if (entity instanceof MCH_EntitySeat) {
                return;
            }
            if (entity instanceof MCH_EntityUavStation) {
                return;
            }
            if (entity instanceof MCH_EntityParachute) {
                return;
            }
            if (W_Lib.isEntityLivingBase(entity)) {
                return;
            }
            final MCH_EntityChain towingChain = getTowedEntityChain(entity);
            if (towingChain != null) {
                towingChain.func_70106_y();
                return;
            }
            final Entity entityTowed = getTowedEntity(item, world);
            if (entityTowed == null) {
                playConnectTowedEntity(entity);
                setTowedEntity(item, entity);
            }
            else {
                if (W_Entity.isEqual(entityTowed, entity)) {
                    return;
                }
                final double diff = entity.func_70032_d(entityTowed);
                if (diff < 2.0 || diff > 16.0) {
                    return;
                }
                final MCH_EntityChain chain = new MCH_EntityChain(world, (entityTowed.field_70165_t + entity.field_70165_t) / 2.0, (entityTowed.field_70163_u + entity.field_70163_u) / 2.0, (entityTowed.field_70161_v + entity.field_70161_v) / 2.0);
                chain.setChainLength((int)diff);
                chain.setTowEntity(entityTowed, entity);
                chain.field_70169_q = chain.field_70165_t;
                chain.field_70167_r = chain.field_70163_u;
                chain.field_70166_s = chain.field_70161_v;
                world.func_72838_d((Entity)chain);
                playConnectTowingEntity(entity);
                setTowedEntity(item, null);
            }
        }
    }
    
    public static void playConnectTowingEntity(final Entity e) {
        W_WorldFunc.MOD_playSoundEffect(e.field_70170_p, e.field_70165_t, e.field_70163_u, e.field_70161_v, "chain_ct", 1.0f, 1.0f);
    }
    
    public static void playConnectTowedEntity(final Entity e) {
        W_WorldFunc.MOD_playSoundEffect(e.field_70170_p, e.field_70165_t, e.field_70163_u, e.field_70161_v, "chain", 1.0f, 1.0f);
    }
    
    public void func_77622_d(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
    }
    
    public static MCH_EntityChain getTowedEntityChain(final Entity entity) {
        final List list = entity.field_70170_p.func_72872_a((Class)MCH_EntityChain.class, entity.field_70121_D.func_72314_b(25.0, 25.0, 25.0));
        if (list == null) {
            return null;
        }
        for (int i = 0; i < list.size(); ++i) {
            final MCH_EntityChain chain = list.get(i);
            if (chain.isTowingEntity()) {
                if (W_Entity.isEqual(chain.towEntity, entity)) {
                    return chain;
                }
                if (W_Entity.isEqual(chain.towedEntity, entity)) {
                    return chain;
                }
            }
        }
        return null;
    }
    
    public static void setTowedEntity(final ItemStack item, final Entity entity) {
        NBTTagCompound nbt = item.func_77978_p();
        if (nbt == null) {
            nbt = new NBTTagCompound();
            item.func_77982_d(nbt);
        }
        if (entity != null && !entity.field_70128_L) {
            nbt.func_74768_a("TowedEntityId", W_Entity.getEntityId(entity));
            nbt.func_74778_a("TowedEntityUUID", entity.getPersistentID().toString());
        }
        else {
            nbt.func_74768_a("TowedEntityId", 0);
            nbt.func_74778_a("TowedEntityUUID", "");
        }
    }
    
    public static Entity getTowedEntity(final ItemStack item, final World world) {
        NBTTagCompound nbt = item.func_77978_p();
        if (nbt == null) {
            nbt = new NBTTagCompound();
            item.func_77982_d(nbt);
        }
        else if (nbt.func_74764_b("TowedEntityId") && nbt.func_74764_b("TowedEntityUUID")) {
            final int id = nbt.func_74762_e("TowedEntityId");
            final String uuid = nbt.func_74779_i("TowedEntityUUID");
            final Entity entity = world.func_73045_a(id);
            if (entity != null && !entity.field_70128_L && uuid.compareTo(entity.getPersistentID().toString()) == 0) {
                return entity;
            }
        }
        return null;
    }
}
