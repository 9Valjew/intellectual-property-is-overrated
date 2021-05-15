package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.entity.*;

public class MCH_EntityRocket extends MCH_EntityBaseBullet
{
    public MCH_EntityRocket(final World par1World) {
        super(par1World);
    }
    
    public MCH_EntityRocket(final World par1World, final double posX, final double posY, final double posZ, final double targetX, final double targetY, final double targetZ, final float yaw, final float pitch, final double acceleration) {
        super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
    }
    
    @Override
    public void func_70071_h_() {
        super.func_70071_h_();
        this.onUpdateBomblet();
        if (this.isBomblet <= 0 && this.getInfo() != null && !this.getInfo().disableSmoke) {
            this.spawnParticle(this.getInfo().trajectoryParticleName, 3, 5.0f * this.getInfo().smokeSize * 0.5f);
        }
    }
    
    @Override
    public void sprinkleBomblet() {
        if (!this.field_70170_p.field_72995_K) {
            final MCH_EntityRocket e = new MCH_EntityRocket(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70159_w, this.field_70181_x, this.field_70179_y, this.field_70177_z, this.field_70125_A, this.acceleration);
            e.setName(this.getName());
            e.setParameterFromWeapon(this, this.shootingAircraft, this.shootingEntity);
            final float MOTION = this.getInfo().bombletDiff;
            final float RANDOM = 1.2f;
            final MCH_EntityRocket mch_EntityRocket = e;
            mch_EntityRocket.field_70159_w += (this.field_70146_Z.nextFloat() - 0.5) * MOTION;
            final MCH_EntityRocket mch_EntityRocket2 = e;
            mch_EntityRocket2.field_70181_x += (this.field_70146_Z.nextFloat() - 0.5) * MOTION;
            final MCH_EntityRocket mch_EntityRocket3 = e;
            mch_EntityRocket3.field_70179_y += (this.field_70146_Z.nextFloat() - 0.5) * MOTION;
            e.setBomblet();
            this.field_70170_p.func_72838_d((Entity)e);
        }
    }
    
    @Override
    public MCH_BulletModel getDefaultBulletModel() {
        return MCH_DefaultBulletModels.Rocket;
    }
}
