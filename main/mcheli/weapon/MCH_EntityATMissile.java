package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.util.*;

public class MCH_EntityATMissile extends MCH_EntityBaseBullet
{
    public int guidanceType;
    
    public MCH_EntityATMissile(final World par1World) {
        super(par1World);
        this.guidanceType = 0;
    }
    
    public MCH_EntityATMissile(final World par1World, final double posX, final double posY, final double posZ, final double targetX, final double targetY, final double targetZ, final float yaw, final float pitch, final double acceleration) {
        super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
        this.guidanceType = 0;
    }
    
    @Override
    public void func_70071_h_() {
        super.func_70071_h_();
        if (this.getInfo() != null && !this.getInfo().disableSmoke && this.field_70173_aa >= this.getInfo().trajectoryParticleStartTick) {
            this.spawnParticle(this.getInfo().trajectoryParticleName, 3, 5.0f * this.getInfo().smokeSize * 0.5f);
        }
        if (!this.field_70170_p.field_72995_K) {
            if (this.shootingEntity != null && this.targetEntity != null && !this.targetEntity.field_70128_L) {
                this.onUpdateMotion();
            }
            else {
                this.func_70106_y();
            }
        }
        final double a = (float)Math.atan2(this.field_70179_y, this.field_70159_w);
        this.field_70177_z = (float)(a * 180.0 / 3.141592653589793) - 90.0f;
        final double r = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
        this.field_70125_A = -(float)(Math.atan2(this.field_70181_x, r) * 180.0 / 3.141592653589793);
    }
    
    public void onUpdateMotion() {
        final double x = this.targetEntity.field_70165_t - this.field_70165_t;
        final double y = this.targetEntity.field_70163_u - this.field_70163_u;
        final double z = this.targetEntity.field_70161_v - this.field_70161_v;
        double d = x * x + y * y + z * z;
        if (d > 2250000.0 || this.targetEntity.field_70128_L) {
            this.func_70106_y();
        }
        else if (this.getInfo().proximityFuseDist >= 0.1f && d < this.getInfo().proximityFuseDist) {
            final MovingObjectPosition mop = new MovingObjectPosition(this.targetEntity);
            mop.field_72308_g = null;
            this.onImpact(mop, 1.0f);
        }
        else {
            final int rigidityTime = this.getInfo().rigidityTime;
            final float af = (this.getCountOnUpdate() < rigidityTime + this.getInfo().trajectoryParticleStartTick) ? 0.5f : 1.0f;
            if (this.getCountOnUpdate() > rigidityTime) {
                if (this.guidanceType == 1) {
                    if (this.getCountOnUpdate() <= rigidityTime + 20) {
                        this.guidanceToTarget(this.targetEntity.field_70165_t, this.shootingEntity.field_70163_u + 150.0, this.targetEntity.field_70161_v, af);
                    }
                    else if (this.getCountOnUpdate() <= rigidityTime + 30) {
                        this.guidanceToTarget(this.targetEntity.field_70165_t, this.shootingEntity.field_70163_u, this.targetEntity.field_70161_v, af);
                    }
                    else {
                        if (this.getCountOnUpdate() == rigidityTime + 35) {
                            this.setPower((int)(this.getPower() * 1.2f));
                            if (this.explosionPower > 0) {
                                ++this.explosionPower;
                            }
                        }
                        this.guidanceToTarget(this.targetEntity.field_70165_t, this.targetEntity.field_70163_u, this.targetEntity.field_70161_v, af);
                    }
                }
                else {
                    d = MathHelper.func_76133_a(d);
                    this.field_70159_w = x * this.acceleration / d * af;
                    this.field_70181_x = y * this.acceleration / d * af;
                    this.field_70179_y = z * this.acceleration / d * af;
                }
            }
        }
    }
    
    @Override
    public MCH_BulletModel getDefaultBulletModel() {
        return MCH_DefaultBulletModels.ATMissile;
    }
}
