package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import mcheli.particles.*;
import net.minecraft.util.*;
import mcheli.*;
import mcheli.wrapper.*;
import net.minecraft.block.*;

public class MCH_EntityMarkerRocket extends MCH_EntityBaseBullet
{
    public int countDown;
    
    public MCH_EntityMarkerRocket(final World par1World) {
        super(par1World);
        this.setMarkerStatus(0);
        this.countDown = 0;
    }
    
    public MCH_EntityMarkerRocket(final World par1World, final double posX, final double posY, final double posZ, final double targetX, final double targetY, final double targetZ, final float yaw, final float pitch, final double acceleration) {
        super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
        this.setMarkerStatus(0);
        this.countDown = 0;
    }
    
    @Override
    protected void func_70088_a() {
        super.func_70088_a();
        this.func_70096_w().func_75682_a(28, (Object)(byte)0);
    }
    
    public void setMarkerStatus(final int n) {
        if (!this.field_70170_p.field_72995_K) {
            this.func_70096_w().func_75692_b(28, (Object)(byte)n);
        }
    }
    
    public int getMarkerStatus() {
        return this.func_70096_w().func_75683_a(28);
    }
    
    @Override
    public boolean func_70097_a(final DamageSource par1DamageSource, final float par2) {
        return false;
    }
    
    @Override
    public void func_70071_h_() {
        final int status = this.getMarkerStatus();
        if (this.field_70170_p.field_72995_K) {
            if (this.getInfo() == null) {
                super.func_70071_h_();
            }
            if (this.getInfo() != null && !this.getInfo().disableSmoke) {
                if (status != 0) {
                    if (status == 1) {
                        super.func_70071_h_();
                        this.spawnParticle(this.getInfo().trajectoryParticleName, 3, 5.0f * this.getInfo().smokeSize * 0.5f);
                    }
                    else {
                        final float gb = this.field_70146_Z.nextFloat() * 0.3f;
                        this.spawnParticle("explode", 5, 10 + this.field_70146_Z.nextInt(4), this.field_70146_Z.nextFloat() * 0.2f + 0.8f, gb, gb, (this.field_70146_Z.nextFloat() - 0.5f) * 0.7f, 0.3f + this.field_70146_Z.nextFloat() * 0.3f, (this.field_70146_Z.nextFloat() - 0.5f) * 0.7f);
                    }
                }
            }
        }
        else if (status == 0 || this.func_70090_H()) {
            this.func_70106_y();
        }
        else if (status == 1) {
            super.func_70071_h_();
        }
        else if (this.countDown > 0) {
            --this.countDown;
            if (this.countDown == 40) {
                for (int num = 6 + this.field_70146_Z.nextInt(2), i = 0; i < num; ++i) {
                    final MCH_EntityBomb e = new MCH_EntityBomb(this.field_70170_p, this.field_70165_t + (this.field_70146_Z.nextFloat() - 0.5f) * 15.0f, 260.0f + this.field_70146_Z.nextFloat() * 10.0f + i * 30, this.field_70161_v + (this.field_70146_Z.nextFloat() - 0.5f) * 15.0f, 0.0, -0.5, 0.0, 0.0f, 90.0f, 4.0);
                    e.setName(this.getName());
                    e.explosionPower = 3 + this.field_70146_Z.nextInt(2);
                    e.explosionPowerInWater = 0;
                    e.setPower(30);
                    e.piercing = 0;
                    e.shootingAircraft = this.shootingAircraft;
                    e.shootingEntity = this.shootingEntity;
                    this.field_70170_p.func_72838_d((Entity)e);
                }
            }
        }
        else {
            this.func_70106_y();
        }
    }
    
    public void spawnParticle(final String name, final int num, final float size, final float r, final float g, final float b, final float mx, final float my, final float mz) {
        if (this.field_70170_p.field_72995_K) {
            if (name.isEmpty() || num < 1 || num > 50) {
                return;
            }
            final double x = (this.field_70165_t - this.field_70169_q) / num;
            final double y = (this.field_70163_u - this.field_70167_r) / num;
            final double z = (this.field_70161_v - this.field_70166_s) / num;
            for (int i = 0; i < num; ++i) {
                final MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", this.field_70169_q + x * i, this.field_70167_r + y * i, this.field_70166_s + z * i);
                prm.motionX = mx;
                prm.motionY = my;
                prm.motionZ = mz;
                prm.size = size + this.field_70146_Z.nextFloat();
                prm.setColor(1.0f, r, g, b);
                prm.isEffectWind = true;
                MCH_ParticlesUtil.spawnParticle(prm);
            }
        }
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition m, final float damageFactor) {
        if (this.field_70170_p.field_72995_K) {
            return;
        }
        if (m.field_72308_g != null || W_MovingObjectPosition.isHitTypeEntity(m)) {
            return;
        }
        int x = m.field_72311_b;
        int y = m.field_72312_c;
        int z = m.field_72309_d;
        switch (m.field_72310_e) {
            case 0: {
                --y;
                break;
            }
            case 1: {
                ++y;
                break;
            }
            case 2: {
                --z;
                break;
            }
            case 3: {
                ++z;
                break;
            }
            case 4: {
                --x;
                break;
            }
            case 5: {
                ++x;
                break;
            }
        }
        if (this.field_70170_p.func_147437_c(x, y, z)) {
            final MCH_Config config = MCH_MOD.config;
            if (MCH_Config.Explosion_FlamingBlock.prmBool) {
                W_WorldFunc.setBlock(this.field_70170_p, x, y, z, (Block)W_Blocks.field_150480_ab);
            }
            int noAirBlockCount = 0;
            for (int i = y + 1; i < 256 && (this.field_70170_p.func_147437_c(x, i, z) || ++noAirBlockCount < 5); ++i) {}
            if (noAirBlockCount < 5) {
                this.setMarkerStatus(2);
                this.func_70107_b(x + 0.5, y + 1.1, z + 0.5);
                this.field_70169_q = this.field_70165_t;
                this.field_70167_r = this.field_70163_u;
                this.field_70166_s = this.field_70161_v;
                this.countDown = 100;
            }
            else {
                this.func_70106_y();
            }
        }
        else {
            this.func_70106_y();
        }
    }
    
    @Override
    public MCH_BulletModel getDefaultBulletModel() {
        return MCH_DefaultBulletModels.Rocket;
    }
}
