package mcheli.flare;

import cpw.mods.fml.common.registry.*;
import net.minecraft.world.*;
import cpw.mods.fml.relauncher.*;
import mcheli.particles.*;
import io.netty.buffer.*;
import mcheli.wrapper.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public class MCH_EntityFlare extends W_Entity implements IEntityAdditionalSpawnData
{
    public double gravity;
    public double airResistance;
    public float size;
    public int fuseCount;
    
    public MCH_EntityFlare(final World par1World) {
        super(par1World);
        this.gravity = -0.013;
        this.airResistance = 0.992;
        this.func_70105_a(1.0f, 1.0f);
        this.field_70126_B = this.field_70177_z;
        this.field_70127_C = this.field_70125_A;
        this.size = 6.0f;
        this.fuseCount = 0;
    }
    
    public MCH_EntityFlare(final World par1World, final double pX, final double pY, final double pZ, final double mX, final double mY, final double mZ, final float size, final int fuseCount) {
        this(par1World);
        this.func_70012_b(pX, pY, pZ, 0.0f, 0.0f);
        this.field_70129_M = 0.0f;
        this.field_70159_w = mX;
        this.field_70181_x = mY;
        this.field_70179_y = mZ;
        this.size = size;
        this.fuseCount = fuseCount;
    }
    
    public boolean func_85032_ar() {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean func_70112_a(final double par1) {
        double d1 = this.field_70121_D.func_72320_b() * 4.0;
        d1 *= 64.0;
        return par1 < d1 * d1;
    }
    
    public void func_70106_y() {
        super.func_70106_y();
        if (this.fuseCount > 0 && this.field_70170_p.field_72995_K) {
            this.fuseCount = 0;
            final int num = 20;
            for (int i = 0; i < 20; ++i) {
                final double x = (this.field_70146_Z.nextDouble() - 0.5) * 10.0;
                final double y = (this.field_70146_Z.nextDouble() - 0.5) * 10.0;
                final double z = (this.field_70146_Z.nextDouble() - 0.5) * 10.0;
                final MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", this.field_70165_t + x, this.field_70163_u + y, this.field_70161_v + z);
                prm.age = 200 + this.field_70146_Z.nextInt(100);
                prm.size = 20 + this.field_70146_Z.nextInt(25);
                prm.motionX = (this.field_70146_Z.nextDouble() - 0.5) * 0.45;
                prm.motionY = (this.field_70146_Z.nextDouble() - 0.5) * 0.01;
                prm.motionZ = (this.field_70146_Z.nextDouble() - 0.5) * 0.45;
                prm.a = this.field_70146_Z.nextFloat() * 0.1f + 0.85f;
                prm.b = this.field_70146_Z.nextFloat() * 0.2f + 0.5f;
                prm.g = prm.b + 0.05f;
                prm.r = prm.b + 0.1f;
                MCH_ParticlesUtil.spawnParticle(prm);
            }
        }
    }
    
    public void writeSpawnData(final ByteBuf buffer) {
        try {
            buffer.writeByte(this.fuseCount);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void readSpawnData(final ByteBuf additionalData) {
        try {
            this.fuseCount = additionalData.readByte();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void func_70071_h_() {
        if (this.fuseCount > 0 && this.field_70173_aa >= this.fuseCount) {
            this.func_70106_y();
        }
        else if (!this.field_70170_p.field_72995_K && !this.field_70170_p.func_72899_e((int)this.field_70165_t, (int)this.field_70163_u, (int)this.field_70161_v)) {
            this.func_70106_y();
        }
        else if (this.field_70173_aa > 300 && !this.field_70170_p.field_72995_K) {
            this.func_70106_y();
        }
        else {
            super.func_70071_h_();
            if (!this.field_70170_p.field_72995_K) {
                this.onUpdateCollided();
            }
            this.field_70165_t += this.field_70159_w;
            this.field_70163_u += this.field_70181_x;
            this.field_70161_v += this.field_70179_y;
            if (this.field_70170_p.field_72995_K) {
                final int num = 2;
                final double x = (this.field_70165_t - this.field_70169_q) / 2.0;
                final double y = (this.field_70163_u - this.field_70167_r) / 2.0;
                final double z = (this.field_70161_v - this.field_70166_s) / 2.0;
                for (int i = 0; i < 2; ++i) {
                    final MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", this.field_70169_q + x * i, this.field_70167_r + y * i, this.field_70166_s + z * i);
                    prm.size = 6.0f + this.field_70146_Z.nextFloat();
                    if (this.size < 5.0f) {
                        final MCH_ParticleParam mch_ParticleParam = prm;
                        mch_ParticleParam.a *= 0.75;
                        if (this.field_70146_Z.nextInt(2) == 0) {
                            continue;
                        }
                    }
                    if (this.fuseCount > 0) {
                        prm.a = this.field_70146_Z.nextFloat() * 0.1f + 0.85f;
                        prm.b = this.field_70146_Z.nextFloat() * 0.1f + 0.5f;
                        prm.g = prm.b + 0.05f;
                        prm.r = prm.b + 0.1f;
                    }
                    MCH_ParticlesUtil.spawnParticle(prm);
                }
            }
            this.field_70181_x += this.gravity;
            this.field_70159_w *= this.airResistance;
            this.field_70179_y *= this.airResistance;
            if (this.func_70090_H() && !this.field_70170_p.field_72995_K) {
                this.func_70106_y();
            }
            if (this.field_70122_E && !this.field_70170_p.field_72995_K) {
                this.func_70106_y();
            }
            this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
        }
    }
    
    protected void onUpdateCollided() {
        Vec3 vec3 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
        Vec3 vec4 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
        final MovingObjectPosition mop = W_WorldFunc.clip(this.field_70170_p, vec3, vec4);
        vec3 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
        vec4 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
        if (mop != null) {
            vec4 = W_WorldFunc.getWorldVec3(this.field_70170_p, mop.field_72307_f.field_72450_a, mop.field_72307_f.field_72448_b, mop.field_72307_f.field_72449_c);
            this.onImpact(mop);
        }
    }
    
    protected void onImpact(final MovingObjectPosition par1MovingObjectPosition) {
        if (!this.field_70170_p.field_72995_K) {
            this.func_70106_y();
        }
    }
    
    public void func_70014_b(final NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.func_74782_a("direction", (NBTBase)this.func_70087_a(new double[] { this.field_70159_w, this.field_70181_x, this.field_70179_y }));
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
    public boolean func_70097_a(final DamageSource par1DamageSource, final float par2) {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public float func_70053_R() {
        return 0.0f;
    }
}
