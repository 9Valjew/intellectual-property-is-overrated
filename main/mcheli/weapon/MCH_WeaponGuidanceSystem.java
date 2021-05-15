package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.util.*;
import mcheli.aircraft.*;
import mcheli.uav.*;
import net.minecraft.entity.player.*;
import mcheli.wrapper.*;
import mcheli.*;

public class MCH_WeaponGuidanceSystem
{
    protected World worldObj;
    public Entity lastLockEntity;
    private Entity targetEntity;
    private int lockCount;
    private int lockSoundCount;
    private int continueLockCount;
    private int lockCountMax;
    private int prevLockCount;
    public boolean canLockInWater;
    public boolean canLockOnGround;
    public boolean canLockInAir;
    public boolean ridableOnly;
    public double lockRange;
    public int lockAngle;
    public MCH_IEntityLockChecker checker;
    
    public MCH_WeaponGuidanceSystem() {
        this(null);
    }
    
    public MCH_WeaponGuidanceSystem(final World w) {
        this.worldObj = w;
        this.targetEntity = null;
        this.lastLockEntity = null;
        this.lockCount = 0;
        this.continueLockCount = 0;
        this.lockCountMax = 1;
        this.prevLockCount = 0;
        this.canLockInWater = false;
        this.canLockOnGround = false;
        this.canLockInAir = false;
        this.ridableOnly = false;
        this.lockRange = 50.0;
        this.lockAngle = 10;
        this.checker = null;
    }
    
    public void setWorld(final World w) {
        this.worldObj = w;
    }
    
    public void setLockCountMax(final int i) {
        this.lockCountMax = ((i > 0) ? i : 1);
    }
    
    public int getLockCountMax() {
        final float stealth = getEntityStealth(this.targetEntity);
        return (int)(this.lockCountMax + this.lockCountMax * stealth);
    }
    
    public int getLockCount() {
        return this.lockCount;
    }
    
    public boolean isLockingEntity(final Entity entity) {
        return this.getLockCount() > 0 && this.targetEntity != null && !this.targetEntity.field_70128_L && W_Entity.isEqual(entity, this.targetEntity);
    }
    
    public Entity getLockingEntity() {
        return (this.getLockCount() > 0 && this.targetEntity != null && !this.targetEntity.field_70128_L) ? this.targetEntity : null;
    }
    
    public Entity getTargetEntity() {
        return this.targetEntity;
    }
    
    public boolean isLockComplete() {
        return this.getLockCount() == this.getLockCountMax() && this.lastLockEntity != null;
    }
    
    public void update() {
        if (this.worldObj != null && this.worldObj.field_72995_K) {
            if (this.lockCount != this.prevLockCount) {
                this.prevLockCount = this.lockCount;
            }
            else {
                final boolean b = false;
                this.prevLockCount = (b ? 1 : 0);
                this.lockCount = (b ? 1 : 0);
            }
        }
    }
    
    public static boolean isEntityOnGround(final Entity entity) {
        if (entity != null && !entity.field_70128_L) {
            if (entity.field_70122_E) {
                return true;
            }
            for (int i = 0; i < 12; ++i) {
                final int x = (int)(entity.field_70165_t + 0.5);
                final int y = (int)(entity.field_70163_u + 0.5) - i;
                final int z = (int)(entity.field_70161_v + 0.5);
                final int blockId = W_WorldFunc.getBlockId(entity.field_70170_p, x, y, z);
                if (blockId != 0 && !W_WorldFunc.isBlockWater(entity.field_70170_p, x, y, z)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean lock(final Entity user) {
        return this.lock(user, true);
    }
    
    public boolean lock(final Entity user, final boolean isLockContinue) {
        if (!this.worldObj.field_72995_K) {
            return false;
        }
        boolean result = false;
        if (this.lockCount == 0) {
            final List list = this.worldObj.func_72839_b(user, user.field_70121_D.func_72314_b(this.lockRange, this.lockRange, this.lockRange));
            Entity tgtEnt = null;
            final double dist = this.lockRange * this.lockRange * 2.0;
            for (int i = 0; i < list.size(); ++i) {
                final Entity entity = list.get(i);
                if (this.canLockEntity(entity)) {
                    final double dx = entity.field_70165_t - user.field_70165_t;
                    final double dy = entity.field_70163_u - user.field_70163_u;
                    final double dz = entity.field_70161_v - user.field_70161_v;
                    double d = dx * dx + dy * dy + dz * dz;
                    final Entity entityLocker = this.getLockEntity(user);
                    final float stealth = 1.0f - getEntityStealth(entity);
                    final double range = this.lockRange * stealth;
                    final float angle = this.lockAngle * (stealth / 2.0f + 0.5f);
                    if (d < range * range && d < dist && inLockRange(entityLocker, user.field_70177_z, user.field_70125_A, entity, angle)) {
                        final Vec3 v1 = W_WorldFunc.getWorldVec3(this.worldObj, entityLocker.field_70165_t, entityLocker.field_70163_u, entityLocker.field_70161_v);
                        final Vec3 v2 = W_WorldFunc.getWorldVec3(this.worldObj, entity.field_70165_t, entity.field_70163_u + entity.field_70131_O / 2.0f, entity.field_70161_v);
                        final MovingObjectPosition m = W_WorldFunc.clip(this.worldObj, v1, v2, false, true, false);
                        if (m == null || W_MovingObjectPosition.isHitTypeEntity(m)) {
                            d = dist;
                            tgtEnt = entity;
                        }
                    }
                }
            }
            if ((this.targetEntity = tgtEnt) != null) {
                ++this.lockCount;
            }
        }
        else if (this.targetEntity != null && !this.targetEntity.field_70128_L) {
            boolean canLock = true;
            if (!this.canLockInWater && this.targetEntity.func_70090_H()) {
                canLock = false;
            }
            final boolean ong = isEntityOnGround(this.targetEntity);
            if (!this.canLockOnGround && ong) {
                canLock = false;
            }
            if (!this.canLockInAir && !ong) {
                canLock = false;
            }
            if (canLock) {
                final double dx2 = this.targetEntity.field_70165_t - user.field_70165_t;
                final double dy2 = this.targetEntity.field_70163_u - user.field_70163_u;
                final double dz2 = this.targetEntity.field_70161_v - user.field_70161_v;
                final float stealth2 = 1.0f - getEntityStealth(this.targetEntity);
                final double range2 = this.lockRange * stealth2;
                if (dx2 * dx2 + dy2 * dy2 + dz2 * dz2 < range2 * range2) {
                    if (this.worldObj.field_72995_K && this.lockSoundCount == 1) {
                        MCH_PacketNotifyLock.send(this.getTargetEntity());
                    }
                    this.lockSoundCount = (this.lockSoundCount + 1) % 15;
                    final Entity entityLocker2 = this.getLockEntity(user);
                    if (inLockRange(entityLocker2, user.field_70177_z, user.field_70125_A, this.targetEntity, this.lockAngle)) {
                        if (this.lockCount < this.getLockCountMax()) {
                            ++this.lockCount;
                        }
                    }
                    else if (this.continueLockCount > 0) {
                        --this.continueLockCount;
                        if (this.continueLockCount <= 0 && this.lockCount > 0) {
                            --this.lockCount;
                        }
                    }
                    else {
                        this.continueLockCount = 0;
                        --this.lockCount;
                    }
                    if (this.lockCount >= this.getLockCountMax()) {
                        if (this.continueLockCount <= 0) {
                            this.continueLockCount = this.getLockCountMax() / 3;
                            if (this.continueLockCount > 20) {
                                this.continueLockCount = 20;
                            }
                        }
                        result = true;
                        this.lastLockEntity = this.targetEntity;
                        if (isLockContinue) {
                            this.prevLockCount = this.lockCount - 1;
                        }
                        else {
                            this.clearLock();
                        }
                    }
                }
                else {
                    this.clearLock();
                }
            }
            else {
                this.clearLock();
            }
        }
        else {
            this.clearLock();
        }
        return result;
    }
    
    public static float getEntityStealth(final Entity entity) {
        if (entity instanceof MCH_EntityAircraft) {
            return ((MCH_EntityAircraft)entity).getStealth();
        }
        if (entity != null && entity.field_70154_o instanceof MCH_EntityAircraft) {
            return ((MCH_EntityAircraft)entity.field_70154_o).getStealth();
        }
        return 0.0f;
    }
    
    public void clearLock() {
        this.targetEntity = null;
        this.lockCount = 0;
        this.continueLockCount = 0;
        this.lockSoundCount = 0;
    }
    
    public Entity getLockEntity(final Entity entity) {
        if (entity.field_70154_o instanceof MCH_EntityUavStation) {
            final MCH_EntityUavStation us = (MCH_EntityUavStation)entity.field_70154_o;
            if (us.getControlAircract() != null) {
                return us.getControlAircract();
            }
        }
        return entity;
    }
    
    public boolean canLockEntity(final Entity entity) {
        if (this.ridableOnly && entity instanceof EntityPlayer && entity.field_70154_o == null) {
            return false;
        }
        final String className = entity.getClass().getName();
        if (className.indexOf("EntityCamera") >= 0) {
            return false;
        }
        if (!W_Lib.isEntityLivingBase(entity) && !(entity instanceof MCH_EntityAircraft) && className.indexOf("EntityVehicle") < 0 && className.indexOf("EntityPlane") < 0 && className.indexOf("EntityMecha") < 0 && className.indexOf("EntityAAGun") < 0) {
            return false;
        }
        if (!this.canLockInWater && entity.func_70090_H()) {
            return false;
        }
        if (this.checker != null && !this.checker.canLockEntity(entity)) {
            return false;
        }
        final boolean ong = isEntityOnGround(entity);
        return (this.canLockOnGround || !ong) && (this.canLockInAir || ong);
    }
    
    public static boolean inLockRange(final Entity entity, final float rotationYaw, final float rotationPitch, final Entity target, final float lockAng) {
        final double dx = target.field_70165_t - entity.field_70165_t;
        final double dy = target.field_70163_u + target.field_70131_O / 2.0f - entity.field_70163_u;
        final double dz = target.field_70161_v - entity.field_70161_v;
        final float entityYaw = (float)MCH_Lib.getRotate360(rotationYaw);
        final float targetYaw = (float)MCH_Lib.getRotate360(Math.atan2(dz, dx) * 180.0 / 3.141592653589793);
        final float diffYaw = (float)MCH_Lib.getRotate360(targetYaw - entityYaw - 90.0f);
        final double dxz = Math.sqrt(dx * dx + dz * dz);
        final float targetPitch = -(float)(Math.atan2(dy, dxz) * 180.0 / 3.141592653589793);
        final float diffPitch = targetPitch - rotationPitch;
        return (diffYaw < lockAng || diffYaw > 360.0f - lockAng) && Math.abs(diffPitch) < lockAng;
    }
}
