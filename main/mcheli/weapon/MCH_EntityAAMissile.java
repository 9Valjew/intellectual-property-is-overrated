package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.util.*;

public class MCH_EntityAAMissile extends MCH_EntityBaseBullet
{
    public MCH_EntityAAMissile(final World par1World) {
        super(par1World);
        this.targetEntity = null;
    }
    
    public MCH_EntityAAMissile(final World par1World, final double posX, final double posY, final double posZ, final double targetX, final double targetY, final double targetZ, final float yaw, final float pitch, final double acceleration) {
        super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
    }
    
    @Override
    public void func_70071_h_() {
        super.func_70071_h_();
        if (this.getCountOnUpdate() > 4 && this.getInfo() != null && !this.getInfo().disableSmoke) {
            this.spawnParticle(this.getInfo().trajectoryParticleName, 3, 7.0f * this.getInfo().smokeSize * 0.5f);
        }
        if (!this.field_70170_p.field_72995_K && this.getInfo() != null) {
            if (this.shootingEntity != null && this.targetEntity != null && !this.targetEntity.field_70128_L) {
                final double x = this.field_70165_t - this.targetEntity.field_70165_t;
                final double y = this.field_70163_u - this.targetEntity.field_70163_u;
                final double z = this.field_70161_v - this.targetEntity.field_70161_v;
                final double d = x * x + y * y + z * z;
                if (d > 3422500.0) {
                    this.func_70106_y();
                }
                else if (this.getCountOnUpdate() > this.getInfo().rigidityTime) {
                    if (this.getInfo().proximityFuseDist >= 0.1f && d < this.getInfo().proximityFuseDist) {
                        final MovingObjectPosition mop = new MovingObjectPosition(this.targetEntity);
                        this.field_70165_t = (this.targetEntity.field_70165_t + this.field_70165_t) / 2.0;
                        this.field_70163_u = (this.targetEntity.field_70163_u + this.field_70163_u) / 2.0;
                        this.field_70161_v = (this.targetEntity.field_70161_v + this.field_70161_v) / 2.0;
                        this.onImpact(mop, 1.0f);
                    }
                    else {
                        this.guidanceToTarget(this.targetEntity.field_70165_t, this.targetEntity.field_70163_u, this.targetEntity.field_70161_v);
                    }
                }
            }
            else {
                this.func_70106_y();
            }
        }
    }
    
    @Override
    public MCH_BulletModel getDefaultBulletModel() {
        return MCH_DefaultBulletModels.AAMissile;
    }
}
