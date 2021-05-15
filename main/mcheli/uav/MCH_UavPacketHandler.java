package mcheli.uav;

import net.minecraft.entity.player.*;
import com.google.common.io.*;
import net.minecraft.entity.*;

public class MCH_UavPacketHandler
{
    public static void onPacketUavStatus(final EntityPlayer player, final ByteArrayDataInput data) {
        if (!player.field_70170_p.field_72995_K) {
            final MCH_UavPacketStatus status = new MCH_UavPacketStatus();
            status.readData(data);
            if (player.field_70154_o instanceof MCH_EntityUavStation) {
                ((MCH_EntityUavStation)player.field_70154_o).setUavPosition(status.posUavX, status.posUavY, status.posUavZ);
                if (status.continueControl) {
                    ((MCH_EntityUavStation)player.field_70154_o).controlLastAircraft((Entity)player);
                }
            }
        }
    }
}
