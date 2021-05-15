package mcheli.command;

import net.minecraft.entity.player.*;
import com.google.common.io.*;
import mcheli.*;
import mcheli.aircraft.*;
import net.minecraft.entity.*;

public class MCH_CommandPacketHandler
{
    public static void onPacketTitle(final EntityPlayer player, final ByteArrayDataInput data) {
        if (player == null || !player.field_70170_p.field_72995_K) {
            return;
        }
        final MCH_PacketTitle req = new MCH_PacketTitle();
        req.readData(data);
        MCH_MOD.proxy.printChatMessage(req.chatComponent, req.showTime, req.position);
    }
    
    public static void onPacketSave(final EntityPlayer player, final ByteArrayDataInput data) {
        if (player == null || player.field_70170_p.field_72995_K) {
            return;
        }
        final MCH_PacketCommandSave req = new MCH_PacketCommandSave();
        req.readData(data);
        final MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player);
        if (ac != null) {
            ac.setCommand(req.str, player);
        }
    }
}
