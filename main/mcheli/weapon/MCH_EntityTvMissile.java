package mcheli.weapon;

import net.minecraft.world.*;
import mcheli.aircraft.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class MCH_EntityTvMissile extends MCH_EntityBaseBullet
{
    public boolean isSpawnParticle;
    
    public MCH_EntityTvMissile(final World par1World) {
        super(par1World);
        this.isSpawnParticle = true;
    }
    
    public MCH_EntityTvMissile(final World par1World, final double posX, final double posY, final double posZ, final double targetX, final double targetY, final double targetZ, final float yaw, final float pitch, final double acceleration) {
        super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
        this.isSpawnParticle = true;
    }
    
    @Override
    public void func_70071_h_() {
        super.func_70071_h_();
        if (this.isSpawnParticle && this.getInfo() != null && !this.getInfo().disableSmoke) {
            this.spawnParticle(this.getInfo().trajectoryParticleName, 3, 5.0f * this.getInfo().smokeSize * 0.5f);
        }
        if (this.shootingEntity != null) {
            final double x = this.field_70165_t - this.shootingEntity.field_70165_t;
            final double y = this.field_70163_u - this.shootingEntity.field_70163_u;
            final double z = this.field_70161_v - this.shootingEntity.field_70161_v;
            if (x * x + y * y + z * z > 1440000.0) {
                this.func_70106_y();
            }
            if (!this.field_70170_p.field_72995_K && !this.field_70128_L) {
                this.onUpdateMotion();
            }
        }
        else if (!this.field_70170_p.field_72995_K) {
            this.func_70106_y();
        }
    }
    
    public void onUpdateMotion() {
        final Entity e = this.shootingEntity;
        if (e != null && !e.field_70128_L) {
            final MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl(e);
            if (ac != null && ac.getTVMissile() == this) {
                final float yaw = e.field_70177_z;
                final float pitch = e.field_70125_A;
                final double tX = -MathHelper.func_76126_a(yaw / 180.0f * 3.1415927f) * MathHelper.func_76134_b(pitch / 180.0f * 3.1415927f);
                final double tZ = MathHelper.func_76134_b(yaw / 180.0f * 3.1415927f) * MathHelper.func_76134_b(pitch / 180.0f * 3.1415927f);
                final double tY = -MathHelper.func_76126_a(pitch / 180.0f * 3.1415927f);
                this.setMotion(tX, tY, tZ);
                this.func_70101_b(yaw, pitch);
            }
        }
    }
    
    @Override
    public MCH_BulletModel getDefaultBulletModel() {
        return MCH_DefaultBulletModels.ATMissile;
    }
}
