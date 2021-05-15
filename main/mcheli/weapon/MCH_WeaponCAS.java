package mcheli.weapon;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import mcheli.wrapper.*;
import mcheli.*;
import net.minecraft.util.*;

public class MCH_WeaponCAS extends MCH_WeaponBase
{
    private double targetPosX;
    private double targetPosY;
    private double targetPosZ;
    public int direction;
    private int startTick;
    private int cntAtk;
    private Entity shooter;
    public Entity user;
    
    public MCH_WeaponCAS(final World w, final Vec3 v, final float yaw, final float pitch, final String nm, final MCH_WeaponInfo wi) {
        super(w, v, yaw, pitch, nm, wi);
        this.acceleration = 4.0f;
        this.explosionPower = 2;
        this.power = 32;
        this.interval = -300;
        if (w.field_72995_K) {
            this.interval -= 10;
        }
        this.targetPosX = 0.0;
        this.targetPosY = 0.0;
        this.targetPosZ = 0.0;
        this.direction = 0;
        this.startTick = 0;
        this.cntAtk = 3;
        this.shooter = null;
        this.user = null;
    }
    
    @Override
    public void update(final int countWait) {
        super.update(countWait);
        if (!this.worldObj.field_72995_K && this.cntAtk < 3 && countWait != 0 && this.tick == this.startTick) {
            double x = 0.0;
            double z = 0.0;
            if (this.cntAtk >= 1) {
                final double sign = (this.cntAtk == 1) ? 1.0 : -1.0;
                if (this.direction == 0 || this.direction == 2) {
                    x = MCH_WeaponCAS.rand.nextDouble() * 10.0 * sign;
                    z = (MCH_WeaponCAS.rand.nextDouble() - 0.5) * 10.0;
                }
                if (this.direction == 1 || this.direction == 3) {
                    z = MCH_WeaponCAS.rand.nextDouble() * 10.0 * sign;
                    x = (MCH_WeaponCAS.rand.nextDouble() - 0.5) * 10.0;
                }
            }
            this.spawnA10(this.targetPosX + x, this.targetPosY + 20.0, this.targetPosZ + z);
            this.startTick = this.tick + 45;
            ++this.cntAtk;
        }
    }
    
    @Override
    public void modifyParameters() {
        if (this.interval > -250) {
            this.interval = -250;
        }
    }
    
    public void setTargetPosition(final double x, final double y, final double z) {
        this.targetPosX = x;
        this.targetPosY = y;
        this.targetPosZ = z;
    }
    
    public void spawnA10(double x, final double y, double z) {
        double mX = 0.0;
        final double mY = 0.0;
        double mZ = 0.0;
        final int SPEED = 3;
        if (this.direction == 0) {
            mZ += 3.0;
            z -= 90.0;
        }
        if (this.direction == 1) {
            mX -= 3.0;
            x += 90.0;
        }
        if (this.direction == 2) {
            mZ -= 3.0;
            z += 90.0;
        }
        if (this.direction == 3) {
            mX += 3.0;
            x -= 90.0;
        }
        final MCH_EntityA10 a10 = new MCH_EntityA10(this.worldObj, x, y, z);
        a10.setWeaponName(this.name);
        final MCH_EntityA10 mch_EntityA10 = a10;
        final MCH_EntityA10 mch_EntityA11 = a10;
        final float n = 90 * this.direction;
        mch_EntityA11.field_70177_z = n;
        mch_EntityA10.field_70126_B = n;
        a10.field_70159_w = mX;
        a10.field_70181_x = mY;
        a10.field_70179_y = mZ;
        a10.direction = this.direction;
        a10.shootingEntity = this.user;
        a10.shootingAircraft = this.shooter;
        a10.explosionPower = this.explosionPower;
        a10.power = this.power;
        a10.acceleration = this.acceleration;
        this.worldObj.func_72838_d((Entity)a10);
        W_WorldFunc.MOD_playSoundEffect(this.worldObj, x, y, z, "a-10_snd", 150.0f, 1.0f);
    }
    
    @Override
    public boolean shot(final MCH_WeaponParam prm) {
        final float yaw = prm.user.field_70177_z;
        final float pitch = prm.user.field_70125_A;
        double tX = -MathHelper.func_76126_a(yaw / 180.0f * 3.1415927f) * MathHelper.func_76134_b(pitch / 180.0f * 3.1415927f);
        double tZ = MathHelper.func_76134_b(yaw / 180.0f * 3.1415927f) * MathHelper.func_76134_b(pitch / 180.0f * 3.1415927f);
        double tY = -MathHelper.func_76126_a(pitch / 180.0f * 3.1415927f);
        final double dist = MathHelper.func_76133_a(tX * tX + tY * tY + tZ * tZ);
        if (this.worldObj.field_72995_K) {
            tX = tX * 80.0 / dist;
            tY = tY * 80.0 / dist;
            tZ = tZ * 80.0 / dist;
        }
        else {
            tX = tX * 150.0 / dist;
            tY = tY * 150.0 / dist;
            tZ = tZ * 150.0 / dist;
        }
        final Vec3 src = W_WorldFunc.getWorldVec3(this.worldObj, prm.entity.field_70165_t, prm.entity.field_70163_u + 2.0, prm.entity.field_70161_v);
        final Vec3 dst = W_WorldFunc.getWorldVec3(this.worldObj, prm.entity.field_70165_t + tX, prm.entity.field_70163_u + tY + 2.0, prm.entity.field_70161_v + tZ);
        final MovingObjectPosition m = W_WorldFunc.clip(this.worldObj, src, dst);
        if (m != null && W_MovingObjectPosition.isHitTypeTile(m)) {
            this.targetPosX = m.field_72307_f.field_72450_a;
            this.targetPosY = m.field_72307_f.field_72448_b;
            this.targetPosZ = m.field_72307_f.field_72449_c;
            this.direction = (int)MCH_Lib.getRotate360(yaw + 45.0f) / 90;
            this.direction += (MCH_WeaponCAS.rand.nextBoolean() ? -1 : 1);
            this.direction %= 4;
            if (this.direction < 0) {
                this.direction += 4;
            }
            this.user = prm.user;
            this.shooter = prm.entity;
            if (prm.entity != null) {
                this.playSoundClient(prm.entity, 1.0f, 1.0f);
            }
            this.startTick = 50;
            this.cntAtk = 0;
            return true;
        }
        return false;
    }
    
    public boolean shot(final Entity user, final double px, final double py, final double pz, final int option1, final int option2) {
        final float yaw = user.field_70177_z;
        final float pitch = user.field_70125_A;
        double tX = -MathHelper.func_76126_a(yaw / 180.0f * 3.1415927f) * MathHelper.func_76134_b(pitch / 180.0f * 3.1415927f);
        double tZ = MathHelper.func_76134_b(yaw / 180.0f * 3.1415927f) * MathHelper.func_76134_b(pitch / 180.0f * 3.1415927f);
        double tY = -MathHelper.func_76126_a(pitch / 180.0f * 3.1415927f);
        final double dist = MathHelper.func_76133_a(tX * tX + tY * tY + tZ * tZ);
        if (this.worldObj.field_72995_K) {
            tX = tX * 80.0 / dist;
            tY = tY * 80.0 / dist;
            tZ = tZ * 80.0 / dist;
        }
        else {
            tX = tX * 120.0 / dist;
            tY = tY * 120.0 / dist;
            tZ = tZ * 120.0 / dist;
        }
        final Vec3 src = W_WorldFunc.getWorldVec3(this.worldObj, px, py, pz);
        final Vec3 dst = W_WorldFunc.getWorldVec3(this.worldObj, px + tX, py + tY, pz + tZ);
        final MovingObjectPosition m = W_WorldFunc.clip(this.worldObj, src, dst);
        if (W_MovingObjectPosition.isHitTypeTile(m)) {
            if (this.worldObj.field_72995_K) {
                final double dx = m.field_72307_f.field_72450_a - px;
                final double dy = m.field_72307_f.field_72448_b - py;
                final double dz = m.field_72307_f.field_72449_c - pz;
                if (Math.sqrt(dx * dx + dz * dz) < 20.0) {
                    return false;
                }
            }
            this.targetPosX = m.field_72307_f.field_72450_a;
            this.targetPosY = m.field_72307_f.field_72448_b;
            this.targetPosZ = m.field_72307_f.field_72449_c;
            this.direction = (int)MCH_Lib.getRotate360(yaw + 45.0f) / 90;
            this.direction += (MCH_WeaponCAS.rand.nextBoolean() ? -1 : 1);
            this.direction %= 4;
            if (this.direction < 0) {
                this.direction += 4;
            }
            this.user = user;
            this.shooter = null;
            this.tick = 0;
            this.startTick = 50;
            this.cntAtk = 0;
            return true;
        }
        return false;
    }
}
