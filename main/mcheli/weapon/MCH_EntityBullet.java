package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import mcheli.wrapper.*;
import java.util.*;
import mcheli.*;
import net.minecraft.block.*;
import net.minecraft.util.*;

public class MCH_EntityBullet extends MCH_EntityBaseBullet
{
    public MCH_EntityBullet(final World par1World) {
        super(par1World);
    }
    
    public MCH_EntityBullet(final World par1World, final double pX, final double pY, final double pZ, final double targetX, final double targetY, final double targetZ, final float yaw, final float pitch, final double acceleration) {
        super(par1World, pX, pY, pZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
    }
    
    @Override
    public void func_70071_h_() {
        super.func_70071_h_();
        if (!this.field_70128_L && !this.field_70170_p.field_72995_K && this.getCountOnUpdate() > 1 && this.getInfo() != null && this.explosionPower > 0) {
            float pDist = this.getInfo().proximityFuseDist;
            if (pDist > 0.1) {
                ++pDist;
                final float rng = pDist + MathHelper.func_76135_e(this.getInfo().acceleration);
                final List list = this.field_70170_p.func_72839_b((Entity)this, this.field_70121_D.func_72314_b((double)rng, (double)rng, (double)rng));
                for (int i = 0; i < list.size(); ++i) {
                    final Entity entity1 = list.get(i);
                    if (this.canBeCollidedEntity(entity1) && entity1.func_70068_e((Entity)this) < pDist * pDist) {
                        MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityBullet.onUpdate:proximityFuse:" + entity1, new Object[0]);
                        this.field_70165_t = (entity1.field_70165_t + this.field_70165_t) / 2.0;
                        this.field_70163_u = (entity1.field_70163_u + this.field_70163_u) / 2.0;
                        this.field_70161_v = (entity1.field_70161_v + this.field_70161_v) / 2.0;
                        final MovingObjectPosition mop = W_MovingObjectPosition.newMOP((int)this.field_70165_t, (int)this.field_70163_u, (int)this.field_70161_v, 0, W_WorldFunc.getWorldVec3EntityPos(this), false);
                        this.onImpact(mop, 1.0f);
                        break;
                    }
                }
            }
        }
    }
    
    @Override
    protected void onUpdateCollided() {
        final double mx = this.field_70159_w * this.accelerationFactor;
        final double my = this.field_70181_x * this.accelerationFactor;
        final double mz = this.field_70179_y * this.accelerationFactor;
        final float damageFactor = 1.0f;
        MovingObjectPosition m = null;
        for (int i = 0; i < 5; ++i) {
            final Vec3 vec3 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
            final Vec3 vec4 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t + mx, this.field_70163_u + my, this.field_70161_v + mz);
            m = W_WorldFunc.clip(this.field_70170_p, vec3, vec4);
            boolean continueClip = false;
            if (this.shootingEntity != null && W_MovingObjectPosition.isHitTypeTile(m)) {
                final Block block = W_WorldFunc.getBlock(this.field_70170_p, m.field_72311_b, m.field_72312_c, m.field_72309_d);
                final MCH_Config config = MCH_MOD.config;
                if (MCH_Config.bulletBreakableBlocks.contains(block)) {
                    W_WorldFunc.destroyBlock(this.field_70170_p, m.field_72311_b, m.field_72312_c, m.field_72309_d, true);
                    continueClip = true;
                }
            }
            if (!continueClip) {
                break;
            }
        }
        final Vec3 vec3 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
        Vec3 vec4 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t + mx, this.field_70163_u + my, this.field_70161_v + mz);
        if (this.getInfo().delayFuse > 0) {
            if (m != null) {
                this.boundBullet(m.field_72310_e);
                if (this.delayFuse == 0) {
                    this.delayFuse = this.getInfo().delayFuse;
                }
            }
            return;
        }
        if (m != null) {
            vec4 = W_WorldFunc.getWorldVec3(this.field_70170_p, m.field_72307_f.field_72450_a, m.field_72307_f.field_72448_b, m.field_72307_f.field_72449_c);
        }
        Entity entity = null;
        final List list = this.field_70170_p.func_72839_b((Entity)this, this.field_70121_D.func_72321_a(mx, my, mz).func_72314_b(21.0, 21.0, 21.0));
        double d0 = 0.0;
        for (int j = 0; j < list.size(); ++j) {
            final Entity entity2 = list.get(j);
            if (this.canBeCollidedEntity(entity2)) {
                final float f = 0.3f;
                final AxisAlignedBB axisalignedbb = entity2.field_70121_D.func_72314_b((double)f, (double)f, (double)f);
                final MovingObjectPosition m2 = axisalignedbb.func_72327_a(vec3, vec4);
                if (m2 != null) {
                    final double d2 = vec3.func_72438_d(m2.field_72307_f);
                    if (d2 < d0 || d0 == 0.0) {
                        entity = entity2;
                        d0 = d2;
                    }
                }
            }
        }
        if (entity != null) {
            m = new MovingObjectPosition(entity);
        }
        if (m != null) {
            this.onImpact(m, damageFactor);
        }
    }
    
    @Override
    public MCH_BulletModel getDefaultBulletModel() {
        return MCH_DefaultBulletModels.Bullet;
    }
}
