package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.util.*;

public class MCH_EntityTorpedo extends MCH_EntityBaseBullet
{
    public double targetPosX;
    public double targetPosY;
    public double targetPosZ;
    public double accelerationInWater;
    
    public MCH_EntityTorpedo(final World par1World) {
        super(par1World);
        this.accelerationInWater = 2.0;
        this.targetPosX = 0.0;
        this.targetPosY = 0.0;
        this.targetPosZ = 0.0;
    }
    
    @Override
    public void func_70071_h_() {
        super.func_70071_h_();
        if (this.getInfo() != null && this.getInfo().isGuidedTorpedo) {
            this.onUpdateGuided();
        }
        else {
            this.onUpdateNoGuided();
        }
        if (this.func_70090_H() && this.getInfo() != null && !this.getInfo().disableSmoke) {
            this.spawnParticle(this.getInfo().trajectoryParticleName, 3, 5.0f * this.getInfo().smokeSize * 0.5f);
        }
    }
    
    private void onUpdateNoGuided() {
        if (!this.field_70170_p.field_72995_K && this.func_70090_H()) {
            this.field_70181_x *= 0.800000011920929;
            if (this.acceleration < this.accelerationInWater) {
                this.acceleration += 0.1;
            }
            else if (this.acceleration > this.accelerationInWater + 0.20000000298023224) {
                this.acceleration -= 0.1;
            }
            final double x = this.field_70159_w;
            final double y = this.field_70181_x;
            final double z = this.field_70179_y;
            final double d = MathHelper.func_76133_a(x * x + y * y + z * z);
            this.field_70159_w = x * this.acceleration / d;
            this.field_70181_x = y * this.acceleration / d;
            this.field_70179_y = z * this.acceleration / d;
        }
        if (this.func_70090_H()) {
            final double a = (float)Math.atan2(this.field_70179_y, this.field_70159_w);
            this.field_70177_z = (float)(a * 180.0 / 3.141592653589793) - 90.0f;
        }
    }
    
    private void onUpdateGuided() {
        if (!this.field_70170_p.field_72995_K && this.func_70090_H()) {
            if (this.acceleration < this.accelerationInWater) {
                this.acceleration += 0.1;
            }
            else if (this.acceleration > this.accelerationInWater + 0.20000000298023224) {
                this.acceleration -= 0.1;
            }
            final double x = this.targetPosX - this.field_70165_t;
            final double y = this.targetPosY - this.field_70163_u;
            final double z = this.targetPosZ - this.field_70161_v;
            final double d = MathHelper.func_76133_a(x * x + y * y + z * z);
            this.field_70159_w = x * this.acceleration / d;
            this.field_70181_x = y * this.acceleration / d;
            this.field_70179_y = z * this.acceleration / d;
        }
        if (this.func_70090_H()) {
            final double a = (float)Math.atan2(this.field_70179_y, this.field_70159_w);
            this.field_70177_z = (float)(a * 180.0 / 3.141592653589793) - 90.0f;
            final double r = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
            this.field_70125_A = -(float)(Math.atan2(this.field_70181_x, r) * 180.0 / 3.141592653589793);
        }
    }
    
    public MCH_EntityTorpedo(final World par1World, final double posX, final double posY, final double posZ, final double targetX, final double targetY, final double targetZ, final float yaw, final float pitch, final double acceleration) {
        super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
        this.accelerationInWater = 2.0;
    }
    
    @Override
    public MCH_BulletModel getDefaultBulletModel() {
        return MCH_DefaultBulletModels.Torpedo;
    }
}
