package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import mcheli.wrapper.*;
import net.minecraft.util.*;
import java.util.*;

public class MCH_EntityBomb extends MCH_EntityBaseBullet
{
    public MCH_EntityBomb(final World par1World) {
        super(par1World);
    }
    
    public MCH_EntityBomb(final World par1World, final double posX, final double posY, final double posZ, final double targetX, final double targetY, final double targetZ, final float yaw, final float pitch, final double acceleration) {
        super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
    }
    
    @Override
    public void func_70071_h_() {
        super.func_70071_h_();
        if (!this.field_70170_p.field_72995_K && this.getInfo() != null) {
            this.field_70159_w *= 0.999;
            this.field_70179_y *= 0.999;
            if (this.func_70090_H()) {
                this.field_70159_w *= this.getInfo().velocityInWater;
                this.field_70181_x *= this.getInfo().velocityInWater;
                this.field_70179_y *= this.getInfo().velocityInWater;
            }
            final float dist = this.getInfo().proximityFuseDist;
            if (dist > 0.1f && this.getCountOnUpdate() % 10 == 0) {
                final List list = this.field_70170_p.func_72839_b((Entity)this, this.field_70121_D.func_72314_b((double)dist, (double)dist, (double)dist));
                if (list != null) {
                    for (int i = 0; i < list.size(); ++i) {
                        final Entity entity = list.get(i);
                        if (W_Lib.isEntityLivingBase(entity) && this.canBeCollidedEntity(entity)) {
                            final MovingObjectPosition m = new MovingObjectPosition((int)(this.field_70165_t + 0.5), (int)(this.field_70163_u + 0.5), (int)(this.field_70161_v + 0.5), 0, Vec3.func_72443_a(this.field_70165_t, this.field_70163_u, this.field_70161_v));
                            this.onImpact(m, 1.0f);
                            break;
                        }
                    }
                }
            }
        }
        this.onUpdateBomblet();
    }
    
    @Override
    public void sprinkleBomblet() {
        if (!this.field_70170_p.field_72995_K) {
            final MCH_EntityBomb e = new MCH_EntityBomb(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70159_w, this.field_70181_x, this.field_70179_y, this.field_70146_Z.nextInt(360), 0.0f, this.acceleration);
            e.setParameterFromWeapon(this, this.shootingAircraft, this.shootingEntity);
            e.setName(this.getName());
            final float MOTION = 1.0f;
            final float RANDOM = this.getInfo().bombletDiff;
            e.field_70159_w = this.field_70159_w * 1.0 + (this.field_70146_Z.nextFloat() - 0.5f) * RANDOM;
            e.field_70181_x = this.field_70181_x * 1.0 / 2.0 + (this.field_70146_Z.nextFloat() - 0.5f) * RANDOM / 2.0f;
            e.field_70179_y = this.field_70179_y * 1.0 + (this.field_70146_Z.nextFloat() - 0.5f) * RANDOM;
            e.setBomblet();
            this.field_70170_p.func_72838_d((Entity)e);
        }
    }
    
    @Override
    public MCH_BulletModel getDefaultBulletModel() {
        return MCH_DefaultBulletModels.Bomb;
    }
}
