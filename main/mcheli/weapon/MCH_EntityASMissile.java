package mcheli.weapon;

import net.minecraft.world.*;
import mcheli.wrapper.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.block.*;

public class MCH_EntityASMissile extends MCH_EntityBaseBullet
{
    public double targetPosX;
    public double targetPosY;
    public double targetPosZ;
    
    public MCH_EntityASMissile(final World par1World) {
        super(par1World);
        this.targetPosX = 0.0;
        this.targetPosY = 0.0;
        this.targetPosZ = 0.0;
    }
    
    @Override
    public float getGravity() {
        if (this.getBomblet() == 1) {
            return -0.03f;
        }
        return super.getGravity();
    }
    
    @Override
    public float getGravityInWater() {
        if (this.getBomblet() == 1) {
            return -0.03f;
        }
        return super.getGravityInWater();
    }
    
    @Override
    public void func_70071_h_() {
        super.func_70071_h_();
        if (this.getInfo() != null && !this.getInfo().disableSmoke && this.getBomblet() == 0) {
            this.spawnParticle(this.getInfo().trajectoryParticleName, 3, 10.0f * this.getInfo().smokeSize * 0.5f);
        }
        if (this.getInfo() != null && !this.field_70170_p.field_72995_K && this.isBomblet != 1) {
            final Block block = W_WorldFunc.getBlock(this.field_70170_p, (int)this.targetPosX, (int)this.targetPosY, (int)this.targetPosZ);
            if (block != null && block.func_149703_v()) {
                final double dist = this.func_70011_f(this.targetPosX, this.targetPosY, this.targetPosZ);
                if (dist < this.getInfo().proximityFuseDist) {
                    if (this.getInfo().bomblet > 0) {
                        for (int i = 0; i < this.getInfo().bomblet; ++i) {
                            this.sprinkleBomblet();
                        }
                    }
                    else {
                        final MovingObjectPosition mop = new MovingObjectPosition((Entity)this);
                        this.onImpact(mop, 1.0f);
                    }
                    this.func_70106_y();
                }
                else if (this.getGravity() == 0.0) {
                    double up = 0.0;
                    if (this.getCountOnUpdate() < 10) {
                        up = 20.0;
                    }
                    final double x = this.targetPosX - this.field_70165_t;
                    final double y = this.targetPosY + up - this.field_70163_u;
                    final double z = this.targetPosZ - this.field_70161_v;
                    final double d = MathHelper.func_76133_a(x * x + y * y + z * z);
                    this.field_70159_w = x * this.acceleration / d;
                    this.field_70181_x = y * this.acceleration / d;
                    this.field_70179_y = z * this.acceleration / d;
                }
                else {
                    final double x2 = this.targetPosX - this.field_70165_t;
                    double y2 = this.targetPosY - this.field_70163_u;
                    y2 *= 0.3;
                    final double z2 = this.targetPosZ - this.field_70161_v;
                    final double d2 = MathHelper.func_76133_a(x2 * x2 + y2 * y2 + z2 * z2);
                    this.field_70159_w = x2 * this.acceleration / d2;
                    this.field_70179_y = z2 * this.acceleration / d2;
                }
            }
        }
        final double a = (float)Math.atan2(this.field_70179_y, this.field_70159_w);
        this.field_70177_z = (float)(a * 180.0 / 3.141592653589793) - 90.0f;
        final double r = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
        this.field_70125_A = -(float)(Math.atan2(this.field_70181_x, r) * 180.0 / 3.141592653589793);
        this.onUpdateBomblet();
    }
    
    @Override
    public void sprinkleBomblet() {
        if (!this.field_70170_p.field_72995_K) {
            final MCH_EntityASMissile e = new MCH_EntityASMissile(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70159_w, this.field_70181_x, this.field_70179_y, this.field_70146_Z.nextInt(360), 0.0f, this.acceleration);
            e.setParameterFromWeapon(this, this.shootingAircraft, this.shootingEntity);
            e.setName(this.getName());
            final float MOTION = 0.5f;
            final float RANDOM = this.getInfo().bombletDiff;
            e.field_70159_w = this.field_70159_w * 0.5 + (this.field_70146_Z.nextFloat() - 0.5f) * RANDOM;
            e.field_70181_x = this.field_70181_x * 0.5 / 2.0 + (this.field_70146_Z.nextFloat() - 0.5f) * RANDOM / 2.0f;
            e.field_70179_y = this.field_70179_y * 0.5 + (this.field_70146_Z.nextFloat() - 0.5f) * RANDOM;
            e.setBomblet();
            this.field_70170_p.func_72838_d((Entity)e);
        }
    }
    
    public MCH_EntityASMissile(final World par1World, final double posX, final double posY, final double posZ, final double targetX, final double targetY, final double targetZ, final float yaw, final float pitch, final double acceleration) {
        super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
    }
    
    @Override
    public MCH_BulletModel getDefaultBulletModel() {
        return MCH_DefaultBulletModels.ASMissile;
    }
}
