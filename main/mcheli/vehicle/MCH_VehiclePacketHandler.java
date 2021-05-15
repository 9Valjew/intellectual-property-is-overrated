package mcheli.vehicle;

import net.minecraft.entity.player.*;
import com.google.common.io.*;
import net.minecraft.entity.*;
import mcheli.weapon.*;
import mcheli.chain.*;

public class MCH_VehiclePacketHandler
{
    public static void onPacket_PlayerControl(final EntityPlayer player, final ByteArrayDataInput data) {
        if (!(player.field_70154_o instanceof MCH_EntityVehicle)) {
            return;
        }
        if (player.field_70170_p.field_72995_K) {
            return;
        }
        final MCH_PacketVehiclePlayerControl pc = new MCH_PacketVehiclePlayerControl();
        pc.readData(data);
        final MCH_EntityVehicle vehicle = (MCH_EntityVehicle)player.field_70154_o;
        if (pc.isUnmount == 1) {
            vehicle.unmountEntity();
        }
        else if (pc.isUnmount == 2) {
            vehicle.unmountCrew();
        }
        else {
            if (pc.switchSearchLight) {
                vehicle.setSearchLight(!vehicle.isSearchLightON());
            }
            if (pc.switchCameraMode > 0) {
                vehicle.switchCameraMode(player, pc.switchCameraMode - 1);
            }
            if (pc.switchWeapon >= 0) {
                vehicle.switchWeapon((Entity)player, pc.switchWeapon);
            }
            if (pc.useWeapon) {
                final MCH_WeaponParam prm = new MCH_WeaponParam();
                prm.entity = vehicle;
                prm.user = (Entity)player;
                prm.setPosAndRot(pc.useWeaponPosX, pc.useWeaponPosY, pc.useWeaponPosZ, 0.0f, 0.0f);
                prm.option1 = pc.useWeaponOption1;
                prm.option2 = pc.useWeaponOption2;
                vehicle.useCurrentWeapon(prm);
            }
            if (vehicle.isPilot((Entity)player)) {
                vehicle.throttleUp = pc.throttleUp;
                vehicle.throttleDown = pc.throttleDown;
                vehicle.moveLeft = pc.moveLeft;
                vehicle.moveRight = pc.moveRight;
            }
            if (pc.useFlareType > 0) {
                vehicle.useFlare(pc.useFlareType);
            }
            if (pc.unhitchChainId >= 0) {
                final Entity e = player.field_70170_p.func_73045_a(pc.unhitchChainId);
                if (e instanceof MCH_EntityChain) {
                    e.func_70106_y();
                }
            }
            if (pc.openGui) {
                vehicle.openGui(player);
            }
            if (pc.switchHatch > 0) {
                vehicle.foldHatch(pc.switchHatch == 2);
            }
            if (pc.isUnmount == 3) {
                vehicle.unmountAircraft();
            }
        }
    }
}
