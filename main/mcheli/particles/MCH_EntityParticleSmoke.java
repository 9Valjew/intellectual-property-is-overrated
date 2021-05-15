package mcheli.particles;

import net.minecraft.world.*;
import mcheli.aircraft.*;
import net.minecraft.entity.*;
import java.util.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.client.renderer.*;
import mcheli.wrapper.*;
import org.lwjgl.opengl.*;

public class MCH_EntityParticleSmoke extends MCH_EntityParticleBase
{
    public MCH_EntityParticleSmoke(final World par1World, final double x, final double y, final double z, final double mx, final double my, final double mz) {
        super(par1World, x, y, z, mx, my, mz);
        final float field_70552_h = this.field_70146_Z.nextFloat() * 0.3f + 0.7f;
        this.field_70551_j = field_70552_h;
        this.field_70553_i = field_70552_h;
        this.field_70552_h = field_70552_h;
        this.setParticleScale(this.field_70146_Z.nextFloat() * 0.5f + 5.0f);
        this.setParticleMaxAge((int)(16.0 / (this.field_70146_Z.nextFloat() * 0.8 + 0.2)) + 2);
    }
    
    public void func_70071_h_() {
        this.field_70169_q = this.field_70165_t;
        this.field_70167_r = this.field_70163_u;
        this.field_70166_s = this.field_70161_v;
        if (this.field_70546_d < this.field_70547_e) {
            this.func_70536_a((int)(8.0 * this.field_70546_d / this.field_70547_e));
            ++this.field_70546_d;
            if (this.diffusible && this.field_70544_f < this.particleMaxScale) {
                this.field_70544_f += 0.8f;
            }
            if (this.toWhite) {
                final float mn = this.getMinColor();
                final float mx = this.getMaxColor();
                final float dist = mx - mn;
                if (dist > 0.2) {
                    this.field_70552_h += (mx - this.field_70552_h) * 0.016f;
                    this.field_70553_i += (mx - this.field_70553_i) * 0.016f;
                    this.field_70551_j += (mx - this.field_70551_j) * 0.016f;
                }
            }
            this.effectWind();
            if (this.field_70546_d / this.field_70547_e > this.moutionYUpAge) {
                this.field_70181_x += 0.02;
            }
            else {
                this.field_70181_x += this.gravity;
            }
            this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
            if (this.diffusible) {
                this.field_70159_w *= 0.96;
                this.field_70179_y *= 0.96;
                this.field_70181_x *= 0.96;
            }
            else {
                this.field_70159_w *= 0.9;
                this.field_70179_y *= 0.9;
            }
            return;
        }
        this.func_70106_y();
    }
    
    public float getMinColor() {
        return this.min(this.min(this.field_70551_j, this.field_70553_i), this.field_70552_h);
    }
    
    public float getMaxColor() {
        return this.max(this.max(this.field_70551_j, this.field_70553_i), this.field_70552_h);
    }
    
    public float min(final float a, final float b) {
        return (a < b) ? a : b;
    }
    
    public float max(final float a, final float b) {
        return (a > b) ? a : b;
    }
    
    public void effectWind() {
        if (this.isEffectedWind) {
            final int range = 15;
            final List list = this.field_70170_p.func_72872_a((Class)MCH_EntityAircraft.class, this.func_70046_E().func_72314_b(15.0, 15.0, 15.0));
            for (int i = 0; i < list.size(); ++i) {
                final MCH_EntityAircraft ac = list.get(i);
                if (ac.getThrottle() > 0.10000000149011612) {
                    final float dist = this.func_70032_d((Entity)ac);
                    final double vel = (23.0 - dist) * 0.009999999776482582 * ac.getThrottle();
                    final double mx = ac.field_70165_t - this.field_70165_t;
                    final double mz = ac.field_70161_v - this.field_70161_v;
                    this.field_70159_w -= mx * vel;
                    this.field_70179_y -= mz * vel;
                }
            }
        }
    }
    
    @Override
    public int func_70537_b() {
        return 3;
    }
    
    @SideOnly(Side.CLIENT)
    public int func_70070_b(final float p_70070_1_) {
        final double y = this.field_70163_u;
        this.field_70163_u += 3000.0;
        final int i = super.func_70070_b(p_70070_1_);
        this.field_70163_u = y;
        return i;
    }
    
    public void func_70539_a(final Tessellator par1Tessellator, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        W_McClient.MOD_bindTexture("textures/particles/smoke.png");
        GL11.glEnable(3042);
        final int srcBlend = GL11.glGetInteger(3041);
        final int dstBlend = GL11.glGetInteger(3040);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(2896);
        GL11.glDisable(2884);
        final float f6 = this.field_94054_b / 8.0f;
        final float f7 = f6 + 0.125f;
        final float f8 = 0.0f;
        final float f9 = 1.0f;
        final float f10 = 0.1f * this.field_70544_f;
        final float f11 = (float)(this.field_70169_q + (this.field_70165_t - this.field_70169_q) * par2 - MCH_EntityParticleSmoke.field_70556_an);
        final float f12 = (float)(this.field_70167_r + (this.field_70163_u - this.field_70167_r) * par2 - MCH_EntityParticleSmoke.field_70554_ao);
        final float f13 = (float)(this.field_70166_s + (this.field_70161_v - this.field_70166_s) * par2 - MCH_EntityParticleSmoke.field_70555_ap);
        par1Tessellator.func_78382_b();
        par1Tessellator.func_78369_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as);
        par1Tessellator.func_78380_c(this.func_70070_b(par2));
        par1Tessellator.func_78375_b(0.0f, 1.0f, 0.0f);
        par1Tessellator.func_78374_a((double)(f11 - par3 * f10 - par6 * f10), (double)(f12 - par4 * f10), (double)(f13 - par5 * f10 - par7 * f10), (double)f7, (double)f9);
        par1Tessellator.func_78374_a((double)(f11 - par3 * f10 + par6 * f10), (double)(f12 + par4 * f10), (double)(f13 - par5 * f10 + par7 * f10), (double)f7, (double)f8);
        par1Tessellator.func_78374_a((double)(f11 + par3 * f10 + par6 * f10), (double)(f12 + par4 * f10), (double)(f13 + par5 * f10 + par7 * f10), (double)f6, (double)f8);
        par1Tessellator.func_78374_a((double)(f11 + par3 * f10 - par6 * f10), (double)(f12 - par4 * f10), (double)(f13 + par5 * f10 - par7 * f10), (double)f6, (double)f9);
        par1Tessellator.func_78381_a();
        GL11.glEnable(2884);
        GL11.glEnable(2896);
        GL11.glBlendFunc(srcBlend, dstBlend);
        GL11.glDisable(3042);
    }
}
