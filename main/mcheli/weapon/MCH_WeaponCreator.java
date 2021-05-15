package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.util.*;

public class MCH_WeaponCreator
{
    public static MCH_WeaponBase createWeapon(final World w, final String weaponName, final Vec3 v, final float yaw, final float pitch, final MCH_IEntityLockChecker lockChecker, final boolean onTurret) {
        final MCH_WeaponInfo info = MCH_WeaponInfoManager.get(weaponName);
        if (info == null || info.type == "") {
            return null;
        }
        MCH_WeaponBase weapon = null;
        if (info.type.compareTo("machinegun1") == 0) {
            weapon = new MCH_WeaponMachineGun1(w, v, yaw, pitch, weaponName, info);
        }
        if (info.type.compareTo("machinegun2") == 0) {
            weapon = new MCH_WeaponMachineGun2(w, v, yaw, pitch, weaponName, info);
        }
        if (info.type.compareTo("tvmissile") == 0) {
            weapon = new MCH_WeaponTvMissile(w, v, yaw, pitch, weaponName, info);
        }
        if (info.type.compareTo("torpedo") == 0) {
            weapon = new MCH_WeaponTorpedo(w, v, yaw, pitch, weaponName, info);
        }
        if (info.type.compareTo("cas") == 0) {
            weapon = new MCH_WeaponCAS(w, v, yaw, pitch, weaponName, info);
        }
        if (info.type.compareTo("rocket") == 0) {
            weapon = new MCH_WeaponRocket(w, v, yaw, pitch, weaponName, info);
        }
        if (info.type.compareTo("asmissile") == 0) {
            weapon = new MCH_WeaponASMissile(w, v, yaw, pitch, weaponName, info);
        }
        if (info.type.compareTo("aamissile") == 0) {
            weapon = new MCH_WeaponAAMissile(w, v, yaw, pitch, weaponName, info);
        }
        if (info.type.compareTo("atmissile") == 0) {
            weapon = new MCH_WeaponATMissile(w, v, yaw, pitch, weaponName, info);
        }
        if (info.type.compareTo("bomb") == 0) {
            weapon = new MCH_WeaponBomb(w, v, yaw, pitch, weaponName, info);
        }
        if (info.type.compareTo("mkrocket") == 0) {
            weapon = new MCH_WeaponMarkerRocket(w, v, yaw, pitch, weaponName, info);
        }
        if (info.type.compareTo("dummy") == 0) {
            weapon = new MCH_WeaponDummy(w, v, yaw, pitch, weaponName, info);
        }
        if (info.type.compareTo("smoke") == 0) {
            weapon = new MCH_WeaponSmoke(w, v, yaw, pitch, weaponName, info);
        }
        if (info.type.compareTo("dispenser") == 0) {
            weapon = new MCH_WeaponDispenser(w, v, yaw, pitch, weaponName, info);
        }
        if (info.type.compareTo("targetingpod") == 0) {
            weapon = new MCH_WeaponTargetingPod(w, v, yaw, pitch, weaponName, info);
        }
        if (weapon != null) {
            weapon.displayName = info.displayName;
            weapon.power = info.power;
            weapon.acceleration = info.acceleration;
            weapon.explosionPower = info.explosion;
            weapon.explosionPowerInWater = info.explosionInWater;
            weapon.interval = info.delay;
            weapon.setLockCountMax(info.lockTime);
            weapon.setLockChecker(lockChecker);
            weapon.numMode = info.modeNum;
            weapon.piercing = info.piercing;
            weapon.heatCount = info.heatCount;
            weapon.onTurret = onTurret;
            if (info.maxHeatCount > 0 && weapon.heatCount < 2) {
                weapon.heatCount = 2;
            }
            if (w.field_72995_K) {
                if (weapon.interval < 4) {
                    final MCH_WeaponBase mch_WeaponBase = weapon;
                    ++mch_WeaponBase.interval;
                }
                else if (weapon.interval < 7) {
                    final MCH_WeaponBase mch_WeaponBase2 = weapon;
                    mch_WeaponBase2.interval += 2;
                }
                else if (weapon.interval < 10) {
                    final MCH_WeaponBase mch_WeaponBase3 = weapon;
                    mch_WeaponBase3.interval += 3;
                }
                else if (weapon.interval < 20) {
                    final MCH_WeaponBase mch_WeaponBase4 = weapon;
                    mch_WeaponBase4.interval += 6;
                }
                else {
                    final MCH_WeaponBase mch_WeaponBase5 = weapon;
                    mch_WeaponBase5.interval += 10;
                    if (weapon.interval >= 40) {
                        weapon.interval = -weapon.interval;
                    }
                }
                final MCH_WeaponBase mch_WeaponBase6 = weapon;
                ++mch_WeaponBase6.heatCount;
                weapon.cartridge = info.cartridge;
            }
            weapon.modifyCommonParameters();
        }
        return weapon;
    }
}
