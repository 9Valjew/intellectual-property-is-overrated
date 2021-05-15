package mcheli.throwable;

import net.minecraft.entity.projectile.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import mcheli.wrapper.*;
import net.minecraft.block.material.*;
import mcheli.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import mcheli.particles.*;

public class MCH_EntityThrowable extends EntityThrowable
{
    private static final int DATAID_NAME = 31;
    private int countOnUpdate;
    private MCH_ThrowableInfo throwableInfo;
    public double boundPosX;
    public double boundPosY;
    public double boundPosZ;
    public MovingObjectPosition lastOnImpact;
    public int noInfoCount;
    
    public MCH_EntityThrowable(final World par1World) {
        super(par1World);
        this.init();
    }
    
    public MCH_EntityThrowable(final World par1World, final EntityLivingBase par2EntityLivingBase, final float acceleration) {
        super(par1World, par2EntityLivingBase);
        this.field_70159_w *= acceleration;
        this.field_70181_x *= acceleration;
        this.field_70179_y *= acceleration;
        this.init();
    }
    
    public MCH_EntityThrowable(final World par1World, final double par2, final double par4, final double par6) {
        super(par1World, par2, par4, par6);
        this.init();
    }
    
    public MCH_EntityThrowable(final World p_i1777_1_, final double x, final double y, final double z, final float yaw, final float pitch) {
        this(p_i1777_1_);
        this.func_70105_a(0.25f, 0.25f);
        this.func_70012_b(x, y, z, yaw, pitch);
        this.field_70165_t -= MathHelper.func_76134_b(this.field_70177_z / 180.0f * 3.1415927f) * 0.16f;
        this.field_70163_u -= 0.10000000149011612;
        this.field_70161_v -= MathHelper.func_76126_a(this.field_70177_z / 180.0f * 3.1415927f) * 0.16f;
        this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
        this.field_70129_M = 0.0f;
        final float f = 0.4f;
        this.field_70159_w = -MathHelper.func_76126_a(this.field_70177_z / 180.0f * 3.1415927f) * MathHelper.func_76134_b(this.field_70125_A / 180.0f * 3.1415927f) * f;
        this.field_70179_y = MathHelper.func_76134_b(this.field_70177_z / 180.0f * 3.1415927f) * MathHelper.func_76134_b(this.field_70125_A / 180.0f * 3.1415927f) * f;
        this.field_70181_x = -MathHelper.func_76126_a((this.field_70125_A + this.func_70183_g()) / 180.0f * 3.1415927f) * f;
        this.func_70186_c(this.field_70159_w, this.field_70181_x, this.field_70179_y, this.func_70182_d(), 1.0f);
    }
    
    public void init() {
        this.lastOnImpact = null;
        this.countOnUpdate = 0;
        this.setInfo(null);
        this.noInfoCount = 0;
        this.func_70096_w().func_75682_a(31, (Object)new String(""));
    }
    
    public void func_70106_y() {
        final String s = (this.getInfo() != null) ? this.getInfo().name : "null";
        MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityThrowable.setDead(%s)", s);
        super.func_70106_y();
    }
    
    public void func_70071_h_() {
        this.boundPosX = this.field_70165_t;
        this.boundPosY = this.field_70163_u;
        this.boundPosZ = this.field_70161_v;
        if (this.getInfo() != null) {
            final Block block = W_WorldFunc.getBlock(this.field_70170_p, (int)(this.field_70165_t + 0.5), (int)this.field_70163_u, (int)(this.field_70161_v + 0.5));
            final Material mat = W_WorldFunc.getBlockMaterial(this.field_70170_p, (int)(this.field_70165_t + 0.5), (int)this.field_70163_u, (int)(this.field_70161_v + 0.5));
            if (block != null && mat == Material.field_151586_h) {
                this.field_70181_x += this.getInfo().gravityInWater;
            }
            else {
                this.field_70181_x += this.getInfo().gravity;
            }
        }
        super.func_70071_h_();
        if (this.lastOnImpact != null) {
            this.boundBullet(this.lastOnImpact);
            this.func_70107_b(this.boundPosX + this.field_70159_w, this.boundPosY + this.field_70181_x, this.boundPosZ + this.field_70179_y);
            this.lastOnImpact = null;
        }
        ++this.countOnUpdate;
        if (this.countOnUpdate >= 2147483632) {
            this.func_70106_y();
            return;
        }
        if (this.getInfo() == null) {
            final String s = this.func_70096_w().func_75681_e(31);
            if (!s.isEmpty()) {
                this.setInfo(MCH_ThrowableInfoManager.get(s));
            }
            if (this.getInfo() == null) {
                ++this.noInfoCount;
                if (this.noInfoCount > 10) {
                    this.func_70106_y();
                }
                return;
            }
        }
        if (this.field_70128_L) {
            return;
        }
        if (!this.field_70170_p.field_72995_K) {
            if (this.countOnUpdate == this.getInfo().timeFuse && this.getInfo().explosion > 0) {
                MCH_Explosion.newExplosion(this.field_70170_p, null, null, this.field_70165_t, this.field_70163_u, this.field_70161_v, this.getInfo().explosion, this.getInfo().explosion, true, true, false, true, 0);
                this.func_70106_y();
                return;
            }
            if (this.countOnUpdate >= this.getInfo().aliveTime) {
                this.func_70106_y();
            }
        }
        else if (this.countOnUpdate >= this.getInfo().timeFuse) {
            if (this.getInfo().explosion <= 0) {
                for (int i = 0; i < this.getInfo().smokeNum; ++i) {
                    final float y = (this.getInfo().smokeVelocityVertical >= 0.0f) ? 0.2f : -0.2f;
                    final float r = this.getInfo().smokeColor.r * 0.9f + this.field_70146_Z.nextFloat() * 0.1f;
                    float g = this.getInfo().smokeColor.g * 0.9f + this.field_70146_Z.nextFloat() * 0.1f;
                    float b = this.getInfo().smokeColor.b * 0.9f + this.field_70146_Z.nextFloat() * 0.1f;
                    if (this.getInfo().smokeColor.r == this.getInfo().smokeColor.g) {
                        g = r;
                    }
                    if (this.getInfo().smokeColor.r == this.getInfo().smokeColor.b) {
                        b = r;
                    }
                    if (this.getInfo().smokeColor.g == this.getInfo().smokeColor.b) {
                        b = g;
                    }
                    this.spawnParticle("explode", 4, this.getInfo().smokeSize + this.field_70146_Z.nextFloat() * this.getInfo().smokeSize / 3.0f, r, g, b, this.getInfo().smokeVelocityHorizontal * (this.field_70146_Z.nextFloat() - 0.5f), this.getInfo().smokeVelocityVertical * this.field_70146_Z.nextFloat(), this.getInfo().smokeVelocityHorizontal * (this.field_70146_Z.nextFloat() - 0.5f));
                }
            }
        }
    }
    
    public void spawnParticle(final String name, final int num, final float size, final float r, final float g, final float b, final float mx, final float my, final float mz) {
        if (this.field_70170_p.field_72995_K) {
            if (name.isEmpty() || num < 1) {
                return;
            }
            final double x = (this.field_70165_t - this.field_70169_q) / num;
            final double y = (this.field_70163_u - this.field_70167_r) / num;
            final double z = (this.field_70161_v - this.field_70166_s) / num;
            for (int i = 0; i < num; ++i) {
                final MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", this.field_70169_q + x * i, 1.0 + this.field_70167_r + y * i, this.field_70166_s + z * i);
                prm.setMotion(mx, my, mz);
                prm.size = size;
                prm.setColor(1.0f, r, g, b);
                prm.isEffectWind = true;
                prm.toWhite = true;
                MCH_ParticlesUtil.spawnParticle(prm);
            }
        }
    }
    
    protected float func_70185_h() {
        return 0.0f;
    }
    
    public void boundBullet(final MovingObjectPosition m) {
        final float bound = this.getInfo().bound;
        switch (m.field_72310_e) {
            case 0:
            case 1: {
                this.field_70159_w *= 0.8999999761581421;
                this.field_70179_y *= 0.8999999761581421;
                this.boundPosY = m.field_72307_f.field_72448_b;
                if ((m.field_72310_e == 0 && this.field_70181_x > 0.0) || (m.field_72310_e == 1 && this.field_70181_x < 0.0)) {
                    this.field_70181_x = -this.field_70181_x * bound;
                    break;
                }
                this.field_70181_x = 0.0;
                break;
            }
            case 2: {
                if (this.field_70179_y > 0.0) {
                    this.field_70179_y = -this.field_70179_y * bound;
                    break;
                }
                break;
            }
            case 3: {
                if (this.field_70179_y < 0.0) {
                    this.field_70179_y = -this.field_70179_y * bound;
                    break;
                }
                break;
            }
            case 4: {
                if (this.field_70159_w > 0.0) {
                    this.field_70159_w = -this.field_70159_w * bound;
                    break;
                }
                break;
            }
            case 5: {
                if (this.field_70159_w < 0.0) {
                    this.field_70159_w = -this.field_70159_w * bound;
                    break;
                }
                break;
            }
        }
    }
    
    protected void func_70184_a(final MovingObjectPosition m) {
        if (this.getInfo() != null) {
            this.lastOnImpact = m;
        }
    }
    
    public MCH_ThrowableInfo getInfo() {
        return this.throwableInfo;
    }
    
    public void setInfo(final MCH_ThrowableInfo info) {
        this.throwableInfo = info;
        if (info != null && !this.field_70170_p.field_72995_K) {
            this.func_70096_w().func_75692_b(31, (Object)new String(info.name));
        }
    }
}
