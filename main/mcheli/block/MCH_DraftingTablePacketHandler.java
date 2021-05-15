package mcheli.block;

import net.minecraft.entity.player.*;
import com.google.common.io.*;
import mcheli.*;

public class MCH_DraftingTablePacketHandler
{
    public static void onPacketCreate(final EntityPlayer player, final ByteArrayDataInput data) {
        if (!player.field_70170_p.field_72995_K) {
            final MCH_DraftingTableCreatePacket packet = new MCH_DraftingTableCreatePacket();
            packet.readData(data);
            final boolean openScreen = player.field_71070_bA instanceof MCH_DraftingTableGuiContainer;
            MCH_Lib.DbgLog(false, "MCH_DraftingTablePacketHandler.onPacketCreate : " + openScreen, new Object[0]);
            if (openScreen) {
                ((MCH_DraftingTableGuiContainer)player.field_71070_bA).createRecipeItem(packet.outputItem, packet.map);
            }
        }
    }
}
