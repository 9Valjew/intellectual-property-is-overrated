package mcheli.weapon;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import mcheli.particles.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.block.*;
import mcheli.chain.*;
import java.util.*;
import mcheli.wrapper.*;
import net.minecraft.entity.player.*;
import mcheli.aircraft.*;
import net.minecraft.util.*;
import net.minecraft.entity.passive.*;
import mcheli.*;
import net.minecraft.nbt.*;

public abstract class MCH_EntityBaseBullet extends W_Entity
{
    public static final int DATAWT_RESERVE1 = 26;
    public static final int DATAWT_TARGET_ENTITY = 27;
    public static final int DATAWT_MARKER_STAT = 28;
    public static final int DATAWT_NAME = 29;
    public static final int DATAWT_BULLET_MODEL = 30;
    public static final int DATAWT_BOMBLET_FLAG = 31;
    public Entity shootingEntity;
    public Entity shootingAircraft;
    private int countOnUpdate;
    public int explosionPower;
    public int explosionPowerInWater;
    private int power;
    public double acceleration;
    public double accelerationFactor;
    public Entity targetEntity;
    public int piercing;
    public int delayFuse;
    public int sprinkleTime;
    public byte isBomblet;
    private MCH_WeaponInfo weaponInfo;
    private MCH_BulletModel model;
    public double prevPosX2;
    public double prevPosY2;
    public double prevPosZ2;
    public double prevMotionX;
    public double prevMotionY;
    public double prevMotionZ;
    
    public MCH_EntityBaseBullet(final World par1World) {
        super(par1World);
        this.countOnUpdate = 0;
        this.func_70105_a(1.0f, 1.0f);
        this.field_70126_B = this.field_70177_z;
        this.field_70127_C = this.field_70125_A;
        this.targetEntity = null;
        this.setPower(1);
        this.acceleration = 1.0;
        this.accelerationFactor = 1.0;
        this.piercing = 0;
        this.explosionPower = 0;
        this.explosionPowerInWater = 0;
        this.delayFuse = 0;
        this.sprinkleTime = 0;
        this.isBomblet = -1;
        this.weaponInfo = null;
        this.field_70158_ak = true;
        if (par1World.field_72995_K) {
            this.model = null;
        }
    }
    
    public MCH_EntityBaseBullet(final World par1World, final double px, final double py, final double pz, final double mx, final double my, final double mz, final float yaw, final float pitch, double acceleration) {
        this(par1World);
        this.func_70105_a(1.0f, 1.0f);
        this.func_70012_b(px, py, pz, yaw, pitch);
        this.func_70107_b(px, py, pz);
        this.field_70126_B = yaw;
        this.field_70127_C = pitch;
        this.field_70129_M = 0.0f;
        if (acceleration > 3.9) {
            acceleration = 3.9;
        }
        final double d = MathHelper.func_76133_a(mx * mx + my * my + mz * mz);
        this.field_70159_w = mx * acceleration / d;
        this.field_70181_x = my * acceleration / d;
        this.field_70179_y = mz * acceleration / d;
        this.prevMotionX = this.field_70159_w;
        this.prevMotionY = this.field_70181_x;
        this.prevMotionZ = this.field_70179_y;
        this.acceleration = acceleration;
    }
    
    public void func_70012_b(final double par1, final double par3, final double par5, final float par7, final float par8) {
        super.func_70012_b(par1, par3, par5, par7, par8);
        this.prevPosX2 = par1;
        this.prevPosY2 = par3;
        this.prevPosZ2 = par5;
    }
    
    @Override
    protected void func_70088_a() {
        super.func_70088_a();
        this.func_70096_w().func_75682_a(27, (Object)0);
        this.func_70096_w().func_75682_a(29, (Object)String.valueOf(""));
        this.func_70096_w().func_75682_a(30, (Object)String.valueOf(""));
        this.func_70096_w().func_75682_a(31, (Object)(byte)0);
    }
    
    public void setName(final String s) {
        if (s != null && !s.isEmpty()) {
            this.weaponInfo = MCH_WeaponInfoManager.get(s);
            if (this.weaponInfo != null) {
                if (!this.field_70170_p.field_72995_K) {
                    this.func_70096_w().func_75692_b(29, (Object)String.valueOf(s));
                }
                this.onSetWeasponInfo();
            }
        }
    }
    
    public String getName() {
        return this.func_70096_w().func_75681_e(29);
    }
    
    public MCH_WeaponInfo getInfo() {
        return this.weaponInfo;
    }
    
    public void onSetWeasponInfo() {
        if (!this.field_70170_p.field_72995_K) {
            this.isBomblet = 0;
        }
        if (this.getInfo().bomblet > 0) {
            this.sprinkleTime = this.getInfo().bombletSTime;
        }
        this.piercing = this.getInfo().piercing;
        if (this instanceof MCH_EntityBullet) {
            if (this.getInfo().acceleration > 4.0f) {
                this.accelerationFactor = this.getInfo().acceleration / 4.0f;
            }
        }
        else if (this instanceof MCH_EntityRocket && this.isBomblet == 0 && this.getInfo().acceleration > 4.0f) {
            this.accelerationFactor = this.getInfo().acceleration / 4.0f;
        }
    }
    
    public void func_70106_y() {
        super.func_70106_y();
    }
    
    public void setBomblet() {
        this.isBomblet = 1;
        this.sprinkleTime = 0;
        this.field_70180_af.func_75692_b(31, (Object)(byte)1);
    }
    
    public byte getBomblet() {
        return this.field_70180_af.func_75683_a(31);
    }
    
    public void setTargetEntity(final Entity entity) {
        this.targetEntity = entity;
        if (!this.field_70170_p.field_72995_K) {
            if (entity != null) {
                this.func_70096_w().func_75692_b(27, (Object)W_Entity.getEntityId(entity));
            }
            else {
                this.func_70096_w().func_75692_b(27, (Object)0);
            }
        }
    }
    
    public int getTargetEntityID() {
        if (this.targetEntity != null) {
            return W_Entity.getEntityId(this.targetEntity);
        }
        return this.func_70096_w().func_75679_c(27);
    }
    
    public MCH_BulletModel getBulletModel() {
        if (this.getInfo() == null) {
            return null;
        }
        if (this.isBomblet < 0) {
            return null;
        }
        if (this.model == null) {
            if (this.isBomblet == 1) {
                this.model = this.getInfo().bombletModel;
            }
            else {
                this.model = this.getInfo().bulletModel;
            }
            if (this.model == null) {
                this.model = this.getDefaultBulletModel();
            }
        }
        return this.model;
    }
    
    public abstract MCH_BulletModel getDefaultBulletModel();
    
    public void sprinkleBomblet() {
    }
    
    public void spawnParticle(final String name, final int num, final float size) {
        if (this.field_70170_p.field_72995_K) {
            if (name.isEmpty() || num < 1 || num > 50) {
                return;
            }
            final double x = (this.field_70165_t - this.field_70169_q) / num;
            final double y = (this.field_70163_u - this.field_70167_r) / num;
            final double z = (this.field_70161_v - this.field_70166_s) / num;
            final double x2 = (this.field_70169_q - this.prevPosX2) / num;
            final double y2 = (this.field_70167_r - this.prevPosY2) / num;
            final double z2 = (this.field_70166_s - this.prevPosZ2) / num;
            if (name.equals("explode")) {
                for (int i = 0; i < num; ++i) {
                    final MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", (this.field_70169_q + x * i + (this.prevPosX2 + x2 * i)) / 2.0, (this.field_70167_r + y * i + (this.prevPosY2 + y2 * i)) / 2.0, (this.field_70166_s + z * i + (this.prevPosZ2 + z2 * i)) / 2.0);
                    prm.size = size + this.field_70146_Z.nextFloat();
                    MCH_ParticlesUtil.spawnParticle(prm);
                }
            }
            else {
                for (int i = 0; i < num; ++i) {
                    MCH_ParticlesUtil.DEF_spawnParticle(name, (this.field_70169_q + x * i + (this.prevPosX2 + x2 * i)) / 2.0, (this.field_70167_r + y * i + (this.prevPosY2 + y2 * i)) / 2.0, (this.field_70166_s + z * i + (this.prevPosZ2 + z2 * i)) / 2.0, 0.0, 0.0, 0.0, 50.0f);
                }
            }
        }
    }
    
    public void DEF_spawnParticle(final String name, final int num, final float size) {
        if (this.field_70170_p.field_72995_K) {
            if (name.isEmpty() || num < 1 || num > 50) {
                return;
            }
            final double x = (this.field_70165_t - this.field_70169_q) / num;
            final double y = (this.field_70163_u - this.field_70167_r) / num;
            final double z = (this.field_70161_v - this.field_70166_s) / num;
            final double x2 = (this.field_70169_q - this.prevPosX2) / num;
            final double y2 = (this.field_70167_r - this.prevPosY2) / num;
            final double z2 = (this.field_70166_s - this.prevPosZ2) / num;
            for (int i = 0; i < num; ++i) {
                MCH_ParticlesUtil.DEF_spawnParticle(name, (this.field_70169_q + x * i + (this.prevPosX2 + x2 * i)) / 2.0, (this.field_70167_r + y * i + (this.prevPosY2 + y2 * i)) / 2.0, (this.field_70166_s + z * i + (this.prevPosZ2 + z2 * i)) / 2.0, 0.0, 0.0, 0.0, 150.0f);
            }
        }
    }
    
    public int getCountOnUpdate() {
        return this.countOnUpdate;
    }
    
    public void clearCountOnUpdate() {
        this.countOnUpdate = 0;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean func_70112_a(final double par1) {
        double d1 = this.field_70121_D.func_72320_b() * 4.0;
        d1 *= 64.0;
        return par1 < d1 * d1;
    }
    
    public void setParameterFromWeapon(final MCH_WeaponBase w, final Entity entity, final Entity user) {
        this.explosionPower = w.explosionPower;
        this.explosionPowerInWater = w.explosionPowerInWater;
        this.setPower(w.power);
        this.piercing = w.piercing;
        this.shootingAircraft = entity;
        this.shootingEntity = user;
    }
    
    public void setParameterFromWeapon(final MCH_EntityBaseBullet b, final Entity entity, final Entity user) {
        this.explosionPower = b.explosionPower;
        this.explosionPowerInWater = b.explosionPowerInWater;
        this.setPower(b.getPower());
        this.piercing = b.piercing;
        this.shootingAircraft = entity;
        this.shootingEntity = user;
    }
    
    public void setMotion(final double targetX, final double targetY, final double targetZ) {
        final double d6 = MathHelper.func_76133_a(targetX * targetX + targetY * targetY + targetZ * targetZ);
        this.field_70159_w = targetX * this.acceleration / d6;
        this.field_70181_x = targetY * this.acceleration / d6;
        this.field_70179_y = targetZ * this.acceleration / d6;
    }
    
    public void guidanceToTarget(final double targetPosX, final double targetPosY, final double targetPosZ) {
        this.guidanceToTarget(targetPosX, targetPosY, targetPosZ, 1.0f);
    }
    
    public void guidanceToTarget(final double targetPosX, final double targetPosY, final double targetPosZ, final float accelerationFactor) {
        final double tx = targetPosX - this.field_70165_t;
        final double ty = targetPosY - this.field_70163_u;
        final double tz = targetPosZ - this.field_70161_v;
        final double d = MathHelper.func_76133_a(tx * tx + ty * ty + tz * tz);
        final double mx = tx * this.acceleration / d;
        final double my = ty * this.acceleration / d;
        final double mz = tz * this.acceleration / d;
        this.field_70159_w = (this.field_70159_w * 6.0 + mx) / 7.0;
        this.field_70181_x = (this.field_70181_x * 6.0 + my) / 7.0;
        this.field_70179_y = (this.field_70179_y * 6.0 + mz) / 7.0;
        final double a = (float)Math.atan2(this.field_70179_y, this.field_70159_w);
        this.field_70177_z = (float)(a * 180.0 / 3.141592653589793) - 90.0f;
        final double r = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
        this.field_70125_A = -(float)(Math.atan2(this.field_70181_x, r) * 180.0 / 3.141592653589793);
    }
    
    public boolean checkValid() {
        if (this.shootingEntity == null && this.shootingAircraft == null) {
            return false;
        }
        if (this.shootingEntity != null && this.shootingEntity.field_70128_L) {
            return false;
        }
        if (this.shootingAircraft == null || this.shootingAircraft.field_70128_L) {}
        final Entity shooter = (this.shootingEntity != null) ? this.shootingEntity : this.shootingAircraft;
        final double x = this.field_70165_t - shooter.field_70165_t;
        final double z = this.field_70161_v - shooter.field_70161_v;
        return x * x + z * z < 3.38724E7;
    }
    
    public float getGravity() {
        return (this.getInfo() != null) ? this.getInfo().gravity : 0.0f;
    }
    
    public float getGravityInWater() {
        return (this.getInfo() != null) ? this.getInfo().gravityInWater : 0.0f;
    }
    
    public void func_70071_h_() {
        if (this.field_70170_p.field_72995_K && this.countOnUpdate == 0) {
            final int tgtEttId = this.getTargetEntityID();
            if (tgtEttId > 0) {
                this.setTargetEntity(this.field_70170_p.func_73045_a(tgtEttId));
            }
        }
        if (this.prevMotionX != this.field_70159_w || this.prevMotionY != this.field_70181_x || this.prevMotionZ != this.field_70179_y) {
            final double a = (float)Math.atan2(this.field_70179_y, this.field_70159_w);
            this.field_70177_z = (float)(a * 180.0 / 3.141592653589793) - 90.0f;
            final double r = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
            this.field_70125_A = -(float)(Math.atan2(this.field_70181_x, r) * 180.0 / 3.141592653589793);
        }
        this.prevMotionX = this.field_70159_w;
        this.prevMotionY = this.field_70181_x;
        this.prevMotionZ = this.field_70179_y;
        ++this.countOnUpdate;
        if (this.countOnUpdate > 10000000) {
            this.clearCountOnUpdate();
        }
        this.prevPosX2 = this.field_70169_q;
        this.prevPosY2 = this.field_70167_r;
        this.prevPosZ2 = this.field_70166_s;
        super.func_70071_h_();
        if (this.getInfo() == null) {
            if (this.countOnUpdate >= 2) {
                MCH_Lib.Log(this, "##### MCH_EntityBaseBullet onUpdate() Weapon info null %d, %s, Name=%s", W_Entity.getEntityId(this), this.getEntityName(), this.getName());
                this.func_70106_y();
                return;
            }
            this.setName(this.getName());
            if (this.getInfo() == null) {
                return;
            }
        }
        if (this.field_70170_p.field_72995_K && this.isBomblet < 0) {
            this.isBomblet = this.getBomblet();
        }
        if (!this.field_70170_p.field_72995_K) {
            if ((int)this.field_70163_u <= 255 && !this.field_70170_p.func_72899_e((int)this.field_70165_t, (int)this.field_70163_u, (int)this.field_70161_v)) {
                if (this.getInfo().delayFuse <= 0) {
                    this.func_70106_y();
                    return;
                }
                if (this.delayFuse == 0) {
                    this.delayFuse = this.getInfo().delayFuse;
                }
            }
            if (this.delayFuse > 0) {
                --this.delayFuse;
                if (this.delayFuse == 0) {
                    this.onUpdateTimeout();
                    this.func_70106_y();
                    return;
                }
            }
            if (!this.checkValid()) {
                this.func_70106_y();
                return;
            }
            if (this.getInfo().timeFuse > 0 && this.getCountOnUpdate() > this.getInfo().timeFuse) {
                this.onUpdateTimeout();
                this.func_70106_y();
                return;
            }
            if (this.getInfo().explosionAltitude > 0 && MCH_Lib.getBlockIdY(this, 3, -this.getInfo().explosionAltitude) != 0) {
                final MovingObjectPosition mop = new MovingObjectPosition((int)this.field_70165_t, (int)this.field_70163_u, (int)this.field_70161_v, 0, Vec3.func_72443_a(this.field_70165_t, this.field_70163_u, this.field_70161_v));
                this.onImpact(mop, 1.0f);
            }
        }
        if (!this.func_70090_H()) {
            this.field_70181_x += this.getGravity();
        }
        else {
            this.field_70181_x += this.getGravityInWater();
        }
        if (!this.field_70128_L) {
            this.onUpdateCollided();
        }
        this.field_70165_t += this.field_70159_w * this.accelerationFactor;
        this.field_70163_u += this.field_70181_x * this.accelerationFactor;
        this.field_70161_v += this.field_70179_y * this.accelerationFactor;
        if (this.field_70170_p.field_72995_K) {
            this.updateSplash();
        }
        if (this.func_70090_H()) {
            final float f3 = 0.25f;
            this.field_70170_p.func_72869_a("bubble", this.field_70165_t - this.field_70159_w * f3, this.field_70163_u - this.field_70181_x * f3, this.field_70161_v - this.field_70179_y * f3, this.field_70159_w, this.field_70181_x, this.field_70179_y);
        }
        this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
    }
    
    public void updateSplash() {
        if (this.getInfo() == null) {
            return;
        }
        if (this.getInfo().power <= 0) {
            return;
        }
        if (!W_WorldFunc.isBlockWater(this.field_70170_p, (int)(this.field_70169_q + 0.5), (int)(this.field_70167_r + 0.5), (int)(this.field_70166_s + 0.5)) && W_WorldFunc.isBlockWater(this.field_70170_p, (int)(this.field_70165_t + 0.5), (int)(this.field_70163_u + 0.5), (int)(this.field_70161_v + 0.5))) {
            double x = this.field_70165_t - this.field_70169_q;
            double y = this.field_70163_u - this.field_70167_r;
            double z = this.field_70161_v - this.field_70166_s;
            final double d = Math.sqrt(x * x + y * y + z * z);
            if (d <= 0.15) {
                return;
            }
            x /= d;
            y /= d;
            z /= d;
            double px = this.field_70169_q;
            double py = this.field_70167_r;
            double pz = this.field_70166_s;
            for (int i = 0; i <= d; ++i) {
                px += x;
                py += y;
                pz += z;
                if (W_WorldFunc.isBlockWater(this.field_70170_p, (int)(px + 0.5), (int)(py + 0.5), (int)(pz + 0.5))) {
                    float pwr = (this.getInfo().power < 20) ? this.getInfo().power : 20.0f;
                    final int n = this.field_70146_Z.nextInt(1 + (int)pwr / 3) + (int)pwr / 2 + 1;
                    pwr *= 0.03f;
                    for (int j = 0; j < n; ++j) {
                        final MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "splash", px, py + 0.5, pz, pwr * (this.field_70146_Z.nextDouble() - 0.5) * 0.3, pwr * (this.field_70146_Z.nextDouble() * 0.5 + 0.5) * 1.8, pwr * (this.field_70146_Z.nextDouble() - 0.5) * 0.3, pwr * 5.0f);
                        MCH_ParticlesUtil.spawnParticle(prm);
                    }
                    break;
                }
            }
        }
    }
    
    public void onUpdateTimeout() {
        if (this.func_70090_H()) {
            if (this.explosionPowerInWater > 0) {
                this.newExplosion(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.explosionPowerInWater, this.explosionPowerInWater, true);
            }
        }
        else if (this.explosionPower > 0) {
            this.newExplosion(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.explosionPower, this.getInfo().explosionBlock, false);
        }
        else if (this.explosionPower < 0) {
            this.playExplosionSound();
        }
    }
    
    public void onUpdateBomblet() {
        if (!this.field_70170_p.field_72995_K && this.sprinkleTime > 0 && !this.field_70128_L) {
            --this.sprinkleTime;
            if (this.sprinkleTime == 0) {
                for (int i = 0; i < this.getInfo().bomblet; ++i) {
                    this.sprinkleBomblet();
                }
                this.func_70106_y();
            }
        }
    }
    
    public void boundBullet(final int sideHit) {
        switch (sideHit) {
            case 0: {
                if (this.field_70181_x > 0.0) {
                    this.field_70181_x = -this.field_70181_x * this.getInfo().bound;
                    break;
                }
                break;
            }
            case 1: {
                if (this.field_70181_x < 0.0) {
                    this.field_70181_x = -this.field_70181_x * this.getInfo().bound;
                    break;
                }
                break;
            }
            case 2: {
                if (this.field_70179_y > 0.0) {
                    this.field_70179_y = -this.field_70179_y * this.getInfo().bound;
                    break;
                }
                this.field_70161_v += this.field_70179_y;
                break;
            }
            case 3: {
                if (this.field_70179_y < 0.0) {
                    this.field_70179_y = -this.field_70179_y * this.getInfo().bound;
                    break;
                }
                this.field_70161_v += this.field_70179_y;
                break;
            }
            case 4: {
                if (this.field_70159_w > 0.0) {
                    this.field_70159_w = -this.field_70159_w * this.getInfo().bound;
                    break;
                }
                this.field_70165_t += this.field_70159_w;
                break;
            }
            case 5: {
                if (this.field_70159_w < 0.0) {
                    this.field_70159_w = -this.field_70159_w * this.getInfo().bound;
                    break;
                }
                this.field_70165_t += this.field_70159_w;
                break;
            }
        }
    }
    
    protected void onUpdateCollided() {
        final float damageFator = 1.0f;
        final double mx = this.field_70159_w * this.accelerationFactor;
        final double my = this.field_70181_x * this.accelerationFactor;
        final double mz = this.field_70179_y * this.accelerationFactor;
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
            this.onImpact(m, damageFator);
        }
    }
    
    public boolean canBeCollidedEntity(final Entity entity) {
        if (entity instanceof MCH_EntityChain) {
            return false;
        }
        if (!entity.func_70067_L()) {
            return false;
        }
        if (entity instanceof MCH_EntityBaseBullet) {
            if (this.field_70170_p.field_72995_K) {
                return false;
            }
            final MCH_EntityBaseBullet blt = (MCH_EntityBaseBullet)entity;
            if (W_Entity.isEqual(blt.shootingAircraft, this.shootingAircraft)) {
                return false;
            }
            if (W_Entity.isEqual(blt.shootingEntity, this.shootingEntity)) {
                return false;
            }
        }
        if (entity instanceof MCH_EntitySeat) {
            return false;
        }
        if (entity instanceof MCH_EntityHitBox) {
            return false;
        }
        if (W_Entity.isEqual(entity, this.shootingEntity)) {
            return false;
        }
        if (this.shootingAircraft instanceof MCH_EntityAircraft) {
            if (W_Entity.isEqual(entity, this.shootingAircraft)) {
                return false;
            }
            if (((MCH_EntityAircraft)this.shootingAircraft).isMountedEntity(entity)) {
                return false;
            }
        }
        final MCH_Config config = MCH_MOD.config;
        for (final String s : MCH_Config.IgnoreBulletHitList) {
            if (entity.getClass().getName().toLowerCase().indexOf(s.toLowerCase()) >= 0) {
                return false;
            }
        }
        return true;
    }
    
    public void notifyHitBullet() {
        if (this.shootingAircraft instanceof MCH_EntityAircraft && W_EntityPlayer.isPlayer(this.shootingEntity)) {
            MCH_PacketNotifyHitBullet.send((MCH_EntityAircraft)this.shootingAircraft, (EntityPlayer)this.shootingEntity);
        }
        if (W_EntityPlayer.isPlayer(this.shootingEntity)) {
            MCH_PacketNotifyHitBullet.send(null, (EntityPlayer)this.shootingEntity);
        }
    }
    
    protected void onImpact(final MovingObjectPosition m, final float damageFactor) {
        if (!this.field_70170_p.field_72995_K) {
            if (m.field_72308_g != null) {
                this.onImpactEntity(m.field_72308_g, damageFactor);
                this.piercing = 0;
            }
            final float expPower = this.explosionPower * damageFactor;
            final float expPowerInWater = this.explosionPowerInWater * damageFactor;
            final double dx = 0.0;
            final double dy = 0.0;
            final double dz = 0.0;
            if (this.piercing > 0) {
                --this.piercing;
                if (expPower > 0.0f) {
                    this.newExplosion(m.field_72307_f.field_72450_a + dx, m.field_72307_f.field_72448_b + dy, m.field_72307_f.field_72449_c + dz, 1.0f, 1.0f, false);
                }
            }
            else {
                if (expPowerInWater == 0.0f) {
                    if (this.getInfo().isFAE) {
                        this.newFAExplosion(this.field_70165_t, this.field_70163_u, this.field_70161_v, expPower, this.getInfo().explosionBlock);
                    }
                    else if (expPower > 0.0f) {
                        this.newExplosion(m.field_72307_f.field_72450_a + dx, m.field_72307_f.field_72448_b + dy, m.field_72307_f.field_72449_c + dz, expPower, this.getInfo().explosionBlock, false);
                    }
                    else if (expPower < 0.0f) {
                        this.playExplosionSound();
                    }
                }
                else if (m.field_72308_g != null) {
                    if (this.func_70090_H()) {
                        this.newExplosion(m.field_72307_f.field_72450_a + dx, m.field_72307_f.field_72448_b + dy, m.field_72307_f.field_72449_c + dz, expPowerInWater, expPowerInWater, true);
                    }
                    else {
                        this.newExplosion(m.field_72307_f.field_72450_a + dx, m.field_72307_f.field_72448_b + dy, m.field_72307_f.field_72449_c + dz, expPower, this.getInfo().explosionBlock, false);
                    }
                }
                else if (this.func_70090_H() || MCH_Lib.isBlockInWater(this.field_70170_p, m.field_72311_b, m.field_72312_c, m.field_72309_d)) {
                    this.newExplosion(m.field_72311_b, m.field_72312_c, m.field_72309_d, expPowerInWater, expPowerInWater, true);
                }
                else if (expPower > 0.0f) {
                    this.newExplosion(m.field_72307_f.field_72450_a + dx, m.field_72307_f.field_72448_b + dy, m.field_72307_f.field_72449_c + dz, expPower, this.getInfo().explosionBlock, false);
                }
                else if (expPower < 0.0f) {
                    this.playExplosionSound();
                }
                this.func_70106_y();
            }
        }
        else if (this.getInfo() != null && (this.getInfo().explosion == 0 || this.getInfo().modeNum >= 2) && W_MovingObjectPosition.isHitTypeTile(m)) {
            final float p = this.getInfo().power;
            for (int i = 0; i < p / 3.0f; ++i) {
                MCH_ParticlesUtil.spawnParticleTileCrack(this.field_70170_p, m.field_72311_b, m.field_72312_c, m.field_72309_d, m.field_72307_f.field_72450_a + (this.field_70146_Z.nextFloat() - 0.5) * p / 10.0, m.field_72307_f.field_72448_b + 0.1, m.field_72307_f.field_72449_c + (this.field_70146_Z.nextFloat() - 0.5) * p / 10.0, -this.field_70159_w * p / 2.0, p / 2.0f, -this.field_70179_y * p / 2.0);
            }
        }
    }
    
    public void onImpactEntity(final Entity entity, final float damageFactor) {
        if (!entity.field_70128_L) {
            MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityBaseBullet.onImpactEntity:Damage=%d:" + entity.getClass(), this.getPower());
            MCH_Lib.applyEntityHurtResistantTimeConfig(entity);
            final DamageSource ds = DamageSource.func_76356_a((Entity)this, this.shootingEntity);
            final MCH_Config config = MCH_MOD.config;
            float damage = MCH_Config.applyDamageVsEntity(entity, ds, this.getPower() * damageFactor);
            damage *= ((this.getInfo() != null) ? this.getInfo().getDamageFactor(entity) : 1.0f);
            entity.func_70097_a(ds, damage);
            if (this instanceof MCH_EntityBullet && entity instanceof EntityVillager && this.shootingEntity != null && this.shootingEntity.field_70154_o instanceof MCH_EntitySeat) {
                MCH_Achievement.addStat(this.shootingEntity, MCH_Achievement.aintWarHell, 1);
            }
            if (entity.field_70128_L) {}
        }
        this.notifyHitBullet();
    }
    
    public void newFAExplosion(final double x, final double y, final double z, final float exp, final float expBlock) {
        final MCH_Explosion.ExplosionResult result = MCH_Explosion.newExplosion(this.field_70170_p, this, this.shootingEntity, x, y, z, exp, expBlock, true, true, this.getInfo().flaming, false, 15);
        if (result != null && result.hitEntity) {
            this.notifyHitBullet();
        }
    }
    
    public void newExplosion(final double x, final double y, final double z, final float exp, final float expBlock, final boolean inWater) {
        MCH_Explosion.ExplosionResult result;
        if (!inWater) {
            result = MCH_Explosion.newExplosion(this.field_70170_p, this, this.shootingEntity, x, y, z, exp, expBlock, this.isBomblet != 1 || this.field_70146_Z.nextInt(3) == 0, true, this.getInfo().flaming, true, 0, (this.getInfo() != null) ? this.getInfo().damageFactor : null);
        }
        else {
            result = MCH_Explosion.newExplosionInWater(this.field_70170_p, this, this.shootingEntity, x, y, z, exp, expBlock, this.isBomblet != 1 || this.field_70146_Z.nextInt(3) == 0, true, this.getInfo().flaming, true, 0, (this.getInfo() != null) ? this.getInfo().damageFactor : null);
        }
        if (result != null && result.hitEntity) {
            this.notifyHitBullet();
        }
    }
    
    public void playExplosionSound() {
        MCH_Explosion.playExplosionSound(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
    }
    
    public void func_70014_b(final NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.func_74782_a("direction", (NBTBase)this.func_70087_a(new double[] { this.field_70159_w, this.field_70181_x, this.field_70179_y }));
        par1NBTTagCompound.func_74778_a("WeaponName", this.getName());
    }
    
    public void func_70037_a(final NBTTagCompound par1NBTTagCompound) {
        this.func_70106_y();
    }
    
    public boolean func_70067_L() {
        return true;
    }
    
    public float func_70111_Y() {
        return 1.0f;
    }
    
    @Override
    public boolean func_70097_a(final DamageSource ds, final float par2) {
        if (this.func_85032_ar()) {
            return false;
        }
        if (!this.field_70170_p.field_72995_K && par2 > 0.0f && ds.func_76355_l().equalsIgnoreCase("thrown")) {
            this.func_70018_K();
            final MovingObjectPosition m = new MovingObjectPosition((int)(this.field_70165_t + 0.5), (int)(this.field_70163_u + 0.5), (int)(this.field_70161_v + 0.5), 0, Vec3.func_72443_a(this.field_70165_t + 0.5, this.field_70163_u + 0.5, this.field_70161_v + 0.5));
            this.onImpact(m, 1.0f);
            return true;
        }
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public float func_70053_R() {
        return 0.0f;
    }
    
    public float func_70013_c(final float par1) {
        return 1.0f;
    }
    
    @SideOnly(Side.CLIENT)
    public int func_70070_b(final float par1) {
        return 15728880;
    }
    
    public int getPower() {
        return this.power;
    }
    
    public void setPower(final int power) {
        this.power = power;
    }
}
