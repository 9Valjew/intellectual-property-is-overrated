package mcheli.tool;

import net.minecraft.entity.player.*;
import com.google.common.io.*;
import mcheli.tool.rangefinder.*;
import mcheli.multiplay.*;
import mcheli.wrapper.*;
import mcheli.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import mcheli.aircraft.*;

public class MCH_ToolPacketHandler
{
    public static void onPacket_IndSpotEntity(final EntityPlayer player, final ByteArrayDataInput data) {
        if (player.field_70170_p.field_72995_K) {
            return;
        }
        final MCH_PacketIndSpotEntity pc = new MCH_PacketIndSpotEntity();
        pc.readData(data);
        final ItemStack itemStack = player.func_70694_bm();
        if (itemStack != null && itemStack.func_77973_b() instanceof MCH_ItemRangeFinder) {
            if (pc.targetFilter == 0) {
                if (MCH_Multiplay.markPoint(player, player.field_70165_t, player.field_70163_u + player.func_70047_e(), player.field_70161_v)) {
                    W_WorldFunc.MOD_playSoundAtEntity((Entity)player, "pi", 1.0f, 1.0f);
                }
                else {
                    W_WorldFunc.MOD_playSoundAtEntity((Entity)player, "ng", 1.0f, 1.0f);
                }
            }
            else if (itemStack.func_77960_j() < itemStack.func_77958_k()) {
                final MCH_Config config = MCH_MOD.config;
                if (MCH_Config.RangeFinderConsume.prmBool) {
                    itemStack.func_77972_a(1, (EntityLivingBase)player);
                }
                int prmInt;
                if ((pc.targetFilter & 0xFC) == 0x0) {
                    prmInt = 60;
                }
                else {
                    final MCH_Config config2 = MCH_MOD.config;
                    prmInt = MCH_Config.RangeFinderSpotTime.prmInt;
                }
                final int time = prmInt;
                final MCH_EntityAircraft ac = null;
                final double field_70165_t = player.field_70165_t;
                final double posY = player.field_70163_u + player.func_70047_e();
                final double field_70161_v = player.field_70161_v;
                final int targetFilter = pc.targetFilter;
                final MCH_Config config3 = MCH_MOD.config;
                if (MCH_Multiplay.spotEntity(player, ac, field_70165_t, posY, field_70161_v, targetFilter, MCH_Config.RangeFinderSpotDist.prmInt, time, 20.0f)) {
                    W_WorldFunc.MOD_playSoundAtEntity((Entity)player, "pi", 1.0f, 1.0f);
                }
                else {
                    W_WorldFunc.MOD_playSoundAtEntity((Entity)player, "ng", 1.0f, 1.0f);
                }
            }
        }
    }
}
