package mcheli.gltd;

import net.minecraft.entity.player.*;
import com.google.common.io.*;
import net.minecraft.entity.*;

public class MCH_GLTDPacketHandler
{
    public static void onPacket_GLTDPlayerControl(final EntityPlayer player, final ByteArrayDataInput data) {
        if (!(player.field_70154_o instanceof MCH_EntityGLTD)) {
            return;
        }
        if (player.field_70170_p.field_72995_K) {
            return;
        }
        final MCH_PacketGLTDPlayerControl pc = new MCH_PacketGLTDPlayerControl();
        pc.readData(data);
        final MCH_EntityGLTD gltd = (MCH_EntityGLTD)player.field_70154_o;
        if (pc.unmount) {
            if (gltd.field_70153_n != null) {
                gltd.field_70153_n.func_70078_a((Entity)null);
            }
        }
        else {
            if (pc.switchCameraMode >= 0) {
                gltd.camera.setMode(0, pc.switchCameraMode);
            }
            if (pc.switchWeapon >= 0) {
                gltd.switchWeapon(pc.switchWeapon);
            }
            if (pc.useWeapon) {
                gltd.useCurrentWeapon(pc.useWeaponOption1, pc.useWeaponOption2);
            }
        }
    }
}
