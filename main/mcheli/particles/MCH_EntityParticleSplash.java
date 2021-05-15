package mcheli.particles;

import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.*;
import mcheli.wrapper.*;

public class MCH_EntityParticleSplash extends MCH_EntityParticleBase
{
    public MCH_EntityParticleSplash(final World par1World, final double x, final double y, final double z, final double mx, final double my, final double mz) {
        super(par1World, x, y, z, mx, my, mz);
        final float field_70552_h = this.field_70146_Z.nextFloat() * 0.3f + 0.7f;
        this.field_70551_j = field_70552_h;
        this.field_70553_i = field_70552_h;
        this.field_70552_h = field_70552_h;
        this.setParticleScale(this.field_70146_Z.nextFloat() * 0.5f + 5.0f);
        this.setParticleMaxAge((int)(80.0 / (this.field_70146_Z.nextFloat() * 0.8 + 0.2)) + 2);
    }
    
    public void func_70071_h_() {
        this.field_70169_q = this.field_70165_t;
        this.field_70167_r = this.field_70163_u;
        this.field_70166_s = this.field_70161_v;
        if (this.field_70546_d < this.field_70547_e) {
            this.func_70536_a((int)(8.0 * this.field_70546_d / this.field_70547_e));
            ++this.field_70546_d;
        }
        else {
            this.func_70106_y();
        }
        this.field_70181_x -= 0.05999999865889549;
        Block block = W_WorldFunc.getBlock(this.field_70170_p, (int)(this.field_70165_t + 0.5), (int)(this.field_70163_u + 0.5), (int)(this.field_70161_v + 0.5));
        final boolean beforeInWater = W_Block.func_149680_a(block, W_Block.getWater());
        this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
        block = W_WorldFunc.getBlock(this.field_70170_p, (int)(this.field_70165_t + 0.5), (int)(this.field_70163_u + 0.5), (int)(this.field_70161_v + 0.5));
        final boolean nowInWater = W_Block.func_149680_a(block, W_Block.getWater());
        if (this.field_70181_x < -0.6 && !beforeInWater && nowInWater) {
            final double p = -this.field_70181_x * 10.0;
            for (int i = 0; i < p; ++i) {
                this.field_70170_p.func_72869_a("splash", this.field_70165_t + 0.5 + (this.field_70146_Z.nextDouble() - 0.5) * 2.0, this.field_70163_u + this.field_70146_Z.nextDouble(), this.field_70161_v + 0.5 + (this.field_70146_Z.nextDouble() - 0.5) * 2.0, (this.field_70146_Z.nextDouble() - 0.5) * 2.0, 4.0, (this.field_70146_Z.nextDouble() - 0.5) * 2.0);
                this.field_70170_p.func_72869_a("bubble", this.field_70165_t + 0.5 + (this.field_70146_Z.nextDouble() - 0.5) * 2.0, this.field_70163_u - this.field_70146_Z.nextDouble(), this.field_70161_v + 0.5 + (this.field_70146_Z.nextDouble() - 0.5) * 2.0, (this.field_70146_Z.nextDouble() - 0.5) * 2.0, -0.5, (this.field_70146_Z.nextDouble() - 0.5) * 2.0);
            }
        }
        else if (this.field_70122_E) {
            this.func_70106_y();
        }
        this.field_70159_w *= 0.9;
        this.field_70179_y *= 0.9;
    }
    
    public void func_70539_a(final Tessellator par1Tessellator, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        W_McClient.MOD_bindTexture("textures/particles/smoke.png");
        final float f6 = this.field_94054_b / 8.0f;
        final float f7 = f6 + 0.125f;
        final float f8 = 0.0f;
        final float f9 = 1.0f;
        final float f10 = 0.1f * this.field_70544_f;
        final float f11 = (float)(this.field_70169_q + (this.field_70165_t - this.field_70169_q) * par2 - MCH_EntityParticleSplash.field_70556_an);
        final float f12 = (float)(this.field_70167_r + (this.field_70163_u - this.field_70167_r) * par2 - MCH_EntityParticleSplash.field_70554_ao);
        final float f13 = (float)(this.field_70166_s + (this.field_70161_v - this.field_70166_s) * par2 - MCH_EntityParticleSplash.field_70555_ap);
        final float f14 = 1.0f;
        par1Tessellator.func_78369_a(this.field_70552_h * f14, this.field_70553_i * f14, this.field_70551_j * f14, this.field_82339_as);
        par1Tessellator.func_78374_a((double)(f11 - par3 * f10 - par6 * f10), (double)(f12 - par4 * f10), (double)(f13 - par5 * f10 - par7 * f10), (double)f7, (double)f9);
        par1Tessellator.func_78374_a((double)(f11 - par3 * f10 + par6 * f10), (double)(f12 + par4 * f10), (double)(f13 - par5 * f10 + par7 * f10), (double)f7, (double)f8);
        par1Tessellator.func_78374_a((double)(f11 + par3 * f10 + par6 * f10), (double)(f12 + par4 * f10), (double)(f13 + par5 * f10 + par7 * f10), (double)f6, (double)f8);
        par1Tessellator.func_78374_a((double)(f11 + par3 * f10 - par6 * f10), (double)(f12 - par4 * f10), (double)(f13 + par5 * f10 - par7 * f10), (double)f6, (double)f9);
    }
}
