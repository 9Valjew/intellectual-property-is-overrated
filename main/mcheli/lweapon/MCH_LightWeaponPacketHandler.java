package mcheli.lweapon;

import net.minecraft.entity.player.*;
import com.google.common.io.*;
import net.minecraft.potion.*;
import mcheli.*;
import net.minecraft.util.*;
import mcheli.wrapper.*;
import net.minecraft.item.*;
import mcheli.weapon.*;
import net.minecraft.entity.*;

public class MCH_LightWeaponPacketHandler
{
    public static void onPacket_PlayerControl(final EntityPlayer player, final ByteArrayDataInput data) {
        if (player.field_70170_p.field_72995_K) {
            return;
        }
        final MCH_PacketLightWeaponPlayerControl pc = new MCH_PacketLightWeaponPlayerControl();
        pc.readData(data);
        if (pc.camMode == 1) {
            player.func_82170_o(Potion.field_76439_r.func_76396_c());
        }
        final ItemStack is = player.func_70694_bm();
        if (is == null) {
            return;
        }
        if (!(is.func_77973_b() instanceof MCH_ItemLightWeaponBase)) {
            return;
        }
        final MCH_ItemLightWeaponBase lweapon = (MCH_ItemLightWeaponBase)is.func_77973_b();
        if (pc.camMode == 2 && MCH_ItemLightWeaponBase.isHeld(player)) {
            player.func_70690_d(new PotionEffect(Potion.field_76439_r.func_76396_c(), 255, 0, false));
        }
        if (pc.camMode > 0) {
            MCH_Lib.DbgLog(false, "MCH_LightWeaponPacketHandler NV=%s", (pc.camMode == 2) ? "ON" : "OFF");
        }
        if (pc.useWeapon && is.func_77960_j() < is.func_77958_k()) {
            final String name = MCH_ItemLightWeaponBase.getName(player.func_70694_bm());
            final MCH_WeaponBase w = MCH_WeaponCreator.createWeapon(player.field_70170_p, name, Vec3.func_72443_a(0.0, 0.0, 0.0), 0.0f, 0.0f, null, false);
            final MCH_WeaponParam prm = new MCH_WeaponParam();
            prm.entity = (Entity)player;
            prm.user = (Entity)player;
            prm.setPosAndRot(pc.useWeaponPosX, pc.useWeaponPosY, pc.useWeaponPosZ, player.field_70177_z, player.field_70125_A);
            prm.option1 = pc.useWeaponOption1;
            prm.option2 = pc.useWeaponOption2;
            w.shot(prm);
            if (!player.field_71075_bZ.field_75098_d && is.func_77958_k() == 1) {
                final ItemStack itemStack = is;
                --itemStack.field_77994_a;
            }
            if (is.func_77958_k() > 1) {
                is.func_77964_b(is.func_77958_k());
            }
        }
        else if (pc.cmpReload > 0 && is.func_77960_j() > 1 && W_EntityPlayer.hasItem(player, lweapon.bullet)) {
            if (!player.field_71075_bZ.field_75098_d) {
                W_EntityPlayer.consumeInventoryItem(player, lweapon.bullet);
            }
            is.func_77964_b(0);
        }
    }
}
