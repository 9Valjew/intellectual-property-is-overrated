package mcheli.aircraft;

import net.minecraft.entity.player.*;
import com.google.common.io.*;
import net.minecraft.entity.*;
import mcheli.wrapper.*;
import mcheli.*;
import net.minecraft.server.*;
import mcheli.weapon.*;
import net.minecraft.world.*;
import java.util.*;

public class MCH_AircraftPacketHandler
{
    public static void onPacketIndRotation(final EntityPlayer player, final ByteArrayDataInput data) {
        if (player == null || player.field_70170_p.field_72995_K) {
            return;
        }
        final MCH_PacketIndRotation req = new MCH_PacketIndRotation();
        req.readData(data);
        if (req.entityID_Ac <= 0) {
            return;
        }
        final Entity e = player.field_70170_p.func_73045_a(req.entityID_Ac);
        if (e instanceof MCH_EntityAircraft) {
            final MCH_EntityAircraft ac = (MCH_EntityAircraft)e;
            ac.setRotRoll(req.roll);
            if (req.rollRev) {
                MCH_Lib.DbgLog(ac.field_70170_p, "onPacketIndRotation Error:req.rollRev y=%.2f, p=%.2f, r=%.2f", req.yaw, req.pitch, req.roll);
                if (ac.getRiddenByEntity() != null) {
                    ac.getRiddenByEntity().field_70177_z = req.yaw;
                    ac.getRiddenByEntity().field_70126_B = req.yaw;
                }
                for (int sid = 0; sid < ac.getSeatNum(); ++sid) {
                    final Entity entity = ac.getEntityBySeatId(1 + sid);
                    if (entity != null) {
                        final Entity entity2 = entity;
                        entity2.field_70177_z += ((entity.field_70177_z <= 0.0f) ? 180.0f : -180.0f);
                    }
                }
            }
            ac.setRotYaw(req.yaw);
            ac.setRotPitch(req.pitch);
        }
    }
    
    public static void onPacketOnMountEntity(final EntityPlayer player, final ByteArrayDataInput data) {
        if (player == null || !player.field_70170_p.field_72995_K) {
            return;
        }
        final MCH_PacketNotifyOnMountEntity req = new MCH_PacketNotifyOnMountEntity();
        req.readData(data);
        MCH_Lib.DbgLog(player.field_70170_p, "onPacketOnMountEntity.rcv:%d, %d, %d, %d", W_Entity.getEntityId((Entity)player), req.entityID_Ac, req.entityID_rider, req.seatID);
        if (req.entityID_Ac <= 0) {
            return;
        }
        if (req.entityID_rider <= 0) {
            return;
        }
        if (req.seatID < 0) {
            return;
        }
        final Entity e = player.field_70170_p.func_73045_a(req.entityID_Ac);
        if (e instanceof MCH_EntityAircraft) {
            MCH_Lib.DbgLog(player.field_70170_p, "onPacketOnMountEntity:" + W_Entity.getEntityId((Entity)player), new Object[0]);
            final Entity rider = player.field_70170_p.func_73045_a(req.entityID_rider);
            final MCH_EntityAircraft ac = (MCH_EntityAircraft)e;
        }
    }
    
    public static void onPacketNotifyAmmoNum(final EntityPlayer player, final ByteArrayDataInput data) {
        if (player == null || !player.field_70170_p.field_72995_K) {
            return;
        }
        final MCH_PacketNotifyAmmoNum status = new MCH_PacketNotifyAmmoNum();
        status.readData(data);
        if (status.entityID_Ac <= 0) {
            return;
        }
        final Entity e = player.field_70170_p.func_73045_a(status.entityID_Ac);
        if (e instanceof MCH_EntityAircraft) {
            final MCH_EntityAircraft ac = (MCH_EntityAircraft)e;
            String msg = "onPacketNotifyAmmoNum:";
            msg = msg + ((ac.getAcInfo() != null) ? ac.getAcInfo().displayName : "null") + ":";
            if (status.all) {
                msg = msg + "All=true, Num=" + status.num;
                for (int i = 0; i < ac.getWeaponNum() && i < status.num; ++i) {
                    ac.getWeapon(i).setAmmoNum(status.ammo[i]);
                    ac.getWeapon(i).setRestAllAmmoNum(status.restAmmo[i]);
                    msg = msg + ", [" + status.ammo[i] + "/" + status.restAmmo[i] + "]";
                }
                MCH_Lib.DbgLog(e.field_70170_p, msg, new Object[0]);
            }
            else if (status.weaponID < ac.getWeaponNum()) {
                msg = msg + "All=false, WeaponID=" + status.weaponID + ", " + status.ammo[0] + ", " + status.restAmmo[0];
                ac.getWeapon(status.weaponID).setAmmoNum(status.ammo[0]);
                ac.getWeapon(status.weaponID).setRestAllAmmoNum(status.restAmmo[0]);
                MCH_Lib.DbgLog(e.field_70170_p, msg, new Object[0]);
            }
            else {
                MCH_Lib.DbgLog(e.field_70170_p, "Error:" + status.weaponID, new Object[0]);
            }
        }
    }
    
    public static void onPacketStatusRequest(final EntityPlayer player, final ByteArrayDataInput data) {
        if (player.field_70170_p.field_72995_K) {
            return;
        }
        final MCH_PacketStatusRequest req = new MCH_PacketStatusRequest();
        req.readData(data);
        if (req.entityID_AC <= 0) {
            return;
        }
        final Entity e = player.field_70170_p.func_73045_a(req.entityID_AC);
        if (e instanceof MCH_EntityAircraft) {
            MCH_PacketStatusResponse.sendStatus((MCH_EntityAircraft)e, player);
        }
    }
    
    public static void onPacketIndNotifyAmmoNum(final EntityPlayer player, final ByteArrayDataInput data) {
        if (player.field_70170_p.field_72995_K) {
            return;
        }
        final MCH_PacketIndNotifyAmmoNum req = new MCH_PacketIndNotifyAmmoNum();
        req.readData(data);
        if (req.entityID_Ac <= 0) {
            return;
        }
        final Entity e = player.field_70170_p.func_73045_a(req.entityID_Ac);
        if (e instanceof MCH_EntityAircraft) {
            if (req.weaponID >= 0) {
                MCH_PacketNotifyAmmoNum.sendAmmoNum((MCH_EntityAircraft)e, player, req.weaponID);
            }
            else {
                MCH_PacketNotifyAmmoNum.sendAllAmmoNum((MCH_EntityAircraft)e, player);
            }
        }
    }
    
    public static void onPacketIndReload(final EntityPlayer player, final ByteArrayDataInput data) {
        if (player.field_70170_p.field_72995_K) {
            return;
        }
        final MCH_PacketIndReload ind = new MCH_PacketIndReload();
        ind.readData(data);
        if (ind.entityID_Ac <= 0) {
            return;
        }
        final Entity e = player.field_70170_p.func_73045_a(ind.entityID_Ac);
        if (e instanceof MCH_EntityAircraft) {
            final MCH_EntityAircraft ac = (MCH_EntityAircraft)e;
            MCH_Lib.DbgLog(e.field_70170_p, "onPacketIndReload :%s", ac.getAcInfo().displayName);
            ac.supplyAmmo(ind.weaponID);
        }
    }
    
    public static void onPacketStatusResponse(final EntityPlayer player, final ByteArrayDataInput data) {
        if (!player.field_70170_p.field_72995_K) {
            return;
        }
        final MCH_PacketStatusResponse status = new MCH_PacketStatusResponse();
        status.readData(data);
        String msg = "onPacketStatusResponse:";
        if (status.entityID_AC <= 0) {
            return;
        }
        msg = msg + "EID=" + status.entityID_AC + ":";
        final Entity e = player.field_70170_p.func_73045_a(status.entityID_AC);
        if (e instanceof MCH_EntityAircraft) {
            final MCH_EntityAircraft ac = (MCH_EntityAircraft)e;
            if (status.seatNum > 0 && status.weaponIDs != null && status.weaponIDs.length == status.seatNum) {
                msg = msg + "seatNum=" + status.seatNum + ":";
                for (int i = 0; i < status.seatNum; ++i) {
                    ac.updateWeaponID(i, status.weaponIDs[i]);
                    msg = msg + "[" + i + "," + status.weaponIDs[i] + "]";
                }
            }
            else {
                msg = msg + "Error seatNum=" + status.seatNum;
            }
        }
        MCH_Lib.DbgLog(true, msg, new Object[0]);
    }
    
    public static void onPacketNotifyWeaponID(final EntityPlayer player, final ByteArrayDataInput data) {
        if (!player.field_70170_p.field_72995_K) {
            return;
        }
        final MCH_PacketNotifyWeaponID status = new MCH_PacketNotifyWeaponID();
        status.readData(data);
        if (status.entityID_Ac <= 0) {
            return;
        }
        final Entity e = player.field_70170_p.func_73045_a(status.entityID_Ac);
        if (e instanceof MCH_EntityAircraft) {
            final MCH_EntityAircraft ac = (MCH_EntityAircraft)e;
            if (ac.isValidSeatID(status.seatID)) {
                ac.getWeapon(status.weaponID).setAmmoNum(status.ammo);
                ac.getWeapon(status.weaponID).setRestAllAmmoNum(status.restAmmo);
                MCH_Lib.DbgLog(true, "onPacketNotifyWeaponID:WeaponID=%d (%d / %d)", status.weaponID, status.ammo, status.restAmmo);
                if (W_Lib.isClientPlayer(ac.getEntityBySeatId(status.seatID))) {
                    MCH_Lib.DbgLog(true, "onPacketNotifyWeaponID:#discard:SeatID=%d, WeaponID=%d", status.seatID, status.weaponID);
                }
                else {
                    MCH_Lib.DbgLog(true, "onPacketNotifyWeaponID:SeatID=%d, WeaponID=%d", status.seatID, status.weaponID);
                    ac.updateWeaponID(status.seatID, status.weaponID);
                }
            }
        }
    }
    
    public static void onPacketNotifyHitBullet(final EntityPlayer player, final ByteArrayDataInput data) {
        if (!player.field_70170_p.field_72995_K) {
            return;
        }
        final MCH_PacketNotifyHitBullet status = new MCH_PacketNotifyHitBullet();
        status.readData(data);
        if (status.entityID_Ac <= 0) {
            MCH_MOD.proxy.hitBullet();
        }
        else {
            final Entity e = player.field_70170_p.func_73045_a(status.entityID_Ac);
            if (e instanceof MCH_EntityAircraft) {
                ((MCH_EntityAircraft)e).hitBullet();
            }
        }
    }
    
    public static void onPacketSeatListRequest(final EntityPlayer player, final ByteArrayDataInput data) {
        if (player.field_70170_p.field_72995_K) {
            return;
        }
        final MCH_PacketSeatListRequest req = new MCH_PacketSeatListRequest();
        req.readData(data);
        if (req.entityID_AC <= 0) {
            return;
        }
        final Entity e = player.field_70170_p.func_73045_a(req.entityID_AC);
        if (e instanceof MCH_EntityAircraft) {
            MCH_PacketSeatListResponse.sendSeatList((MCH_EntityAircraft)e, player);
        }
    }
    
    public static void onPacketNotifyTVMissileEntity(final EntityPlayer player, final ByteArrayDataInput data) {
        if (player.field_70170_p.field_72995_K) {
            final MCH_PacketNotifyTVMissileEntity packet = new MCH_PacketNotifyTVMissileEntity();
            packet.readData(data);
            if (packet.entityID_Ac <= 0) {
                return;
            }
            if (packet.entityID_TVMissile <= 0) {
                return;
            }
            Entity e = player.field_70170_p.func_73045_a(packet.entityID_Ac);
            if (e == null || !(e instanceof MCH_EntityAircraft)) {
                return;
            }
            final MCH_EntityAircraft ac = (MCH_EntityAircraft)e;
            e = player.field_70170_p.func_73045_a(packet.entityID_TVMissile);
            if (e == null || !(e instanceof MCH_EntityTvMissile)) {
                return;
            }
            ((MCH_EntityTvMissile)e).shootingEntity = (Entity)player;
            ac.setTVMissile((MCH_EntityTvMissile)e);
        }
    }
    
    public static void onPacketSeatListResponse(final EntityPlayer player, final ByteArrayDataInput data) {
        if (!player.field_70170_p.field_72995_K) {
            return;
        }
        final MCH_PacketSeatListResponse seatList = new MCH_PacketSeatListResponse();
        seatList.readData(data);
        if (seatList.entityID_AC <= 0) {
            return;
        }
        final Entity e = player.field_70170_p.func_73045_a(seatList.entityID_AC);
        if (e instanceof MCH_EntityAircraft) {
            final MCH_EntityAircraft ac = (MCH_EntityAircraft)e;
            if (seatList.seatNum > 0 && seatList.seatNum == ac.getSeats().length && seatList.seatEntityID != null && seatList.seatEntityID.length == seatList.seatNum) {
                for (int i = 0; i < seatList.seatNum; ++i) {
                    final Entity entity = player.field_70170_p.func_73045_a(seatList.seatEntityID[i]);
                    if (entity instanceof MCH_EntitySeat) {
                        final MCH_EntitySeat seat = (MCH_EntitySeat)entity;
                        seat.seatID = i;
                        seat.setParent(ac);
                        seat.parentUniqueID = ac.getCommonUniqueId();
                        ac.setSeat(i, seat);
                    }
                }
            }
        }
    }
    
    public static void onPacket_PlayerControl(final EntityPlayer player, final ByteArrayDataInput data) {
        if (player.field_70170_p.field_72995_K) {
            return;
        }
        MCH_EntityAircraft ac = null;
        if (player.field_70154_o instanceof MCH_EntitySeat) {
            final MCH_EntitySeat seat = (MCH_EntitySeat)player.field_70154_o;
            ac = seat.getParent();
        }
        else {
            ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player);
        }
        if (ac == null) {
            return;
        }
        final MCH_PacketSeatPlayerControl pc = new MCH_PacketSeatPlayerControl();
        pc.readData(data);
        if (pc.isUnmount) {
            ac.unmountEntityFromSeat((Entity)player);
        }
        else if (pc.switchSeat > 0) {
            if (pc.switchSeat == 3) {
                player.func_70078_a((Entity)null);
                ac.interactFirst(player, ac.keepOnRideRotation = true);
            }
            if (pc.switchSeat == 1) {
                ac.switchNextSeat((Entity)player);
            }
            if (pc.switchSeat == 2) {
                ac.switchPrevSeat((Entity)player);
            }
        }
        else if (pc.parachuting) {
            ac.unmount((Entity)player);
        }
    }
    
    public static void onPacket_ClientSetting(final EntityPlayer player, final ByteArrayDataInput data) {
        if (player.field_70170_p.field_72995_K) {
            return;
        }
        final MCH_PacketNotifyClientSetting pc = new MCH_PacketNotifyClientSetting();
        pc.readData(data);
        final MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player);
        if (ac != null) {
            final int sid = ac.getSeatIdByEntity((Entity)player);
            if (sid == 0) {
                ac.cs_dismountAll = pc.dismountAll;
                ac.cs_heliAutoThrottleDown = pc.heliAutoThrottleDown;
                ac.cs_planeAutoThrottleDown = pc.planeAutoThrottleDown;
                ac.cs_tankAutoThrottleDown = pc.tankAutoThrottleDown;
            }
            ac.camera.setShaderSupport(sid, pc.shaderSupport);
        }
    }
    
    public static void onPacketNotifyInfoReloaded(final EntityPlayer player, final ByteArrayDataInput data) {
        if (player.field_70170_p.field_72995_K) {
            return;
        }
        final MCH_PacketNotifyInfoReloaded pc = new MCH_PacketNotifyInfoReloaded();
        pc.readData(data);
        switch (pc.type) {
            case 0: {
                MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player);
                if (ac != null && ac.getAcInfo() != null) {
                    final String name = ac.getAcInfo().name;
                    for (final WorldServer world : MinecraftServer.func_71276_C().field_71305_c) {
                        final List list = world.field_72996_f;
                        for (int i = 0; i < list.size(); ++i) {
                            if (list.get(i) instanceof MCH_EntityAircraft) {
                                ac = list.get(i);
                                if (ac.getAcInfo() != null && ac.getAcInfo().name.equals(name)) {
                                    ac.changeType(name);
                                    ac.createSeats(UUID.randomUUID().toString());
                                    ac.onAcInfoReloaded();
                                }
                            }
                        }
                    }
                    break;
                }
                break;
            }
            case 1: {
                MCH_WeaponInfoManager.reload();
                for (final WorldServer world2 : MinecraftServer.func_71276_C().field_71305_c) {
                    final List list2 = world2.field_72996_f;
                    for (int j = 0; j < list2.size(); ++j) {
                        if (list2.get(j) instanceof MCH_EntityAircraft) {
                            final MCH_EntityAircraft ac = list2.get(j);
                            if (ac.getAcInfo() != null) {
                                ac.changeType(ac.getAcInfo().name);
                                ac.createSeats(UUID.randomUUID().toString());
                            }
                        }
                    }
                }
                break;
            }
        }
    }
}
