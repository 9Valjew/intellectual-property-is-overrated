package mcheli.particles;

import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;

public class MCH_EntityParticleExplode extends MCH_EntityParticleBase
{
    private static final ResourceLocation texture;
    private int nowCount;
    private int endCount;
    private TextureManager theRenderEngine;
    private float size;
    
    public MCH_EntityParticleExplode(final World w, final double x, final double y, final double z, final double size, final double age, final double mz) {
        super(w, x, y, z, 0.0, 0.0, 0.0);
        this.theRenderEngine = Minecraft.func_71410_x().field_71446_o;
        this.endCount = 1 + (int)age;
        this.size = (float)size;
    }
    
    public void func_70539_a(final Tessellator tessellator, final float p_70539_2_, final float p_70539_3_, final float p_70539_4_, final float p_70539_5_, final float p_70539_6_, final float p_70539_7_) {
        final int i = (int)((this.nowCount + p_70539_2_) * 15.0f / this.endCount);
        if (i <= 15) {
            GL11.glEnable(3042);
            final int srcBlend = GL11.glGetInteger(3041);
            final int dstBlend = GL11.glGetInteger(3040);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(2884);
            this.theRenderEngine.func_110577_a(MCH_EntityParticleExplode.texture);
            final float f6 = i % 4 / 4.0f;
            final float f7 = f6 + 0.24975f;
            final float f8 = i / 4 / 4.0f;
            final float f9 = f8 + 0.24975f;
            final float f10 = 2.0f * this.size;
            final float f11 = (float)(this.field_70169_q + (this.field_70165_t - this.field_70169_q) * p_70539_2_ - MCH_EntityParticleExplode.field_70556_an);
            final float f12 = (float)(this.field_70167_r + (this.field_70163_u - this.field_70167_r) * p_70539_2_ - MCH_EntityParticleExplode.field_70554_ao);
            final float f13 = (float)(this.field_70166_s + (this.field_70161_v - this.field_70166_s) * p_70539_2_ - MCH_EntityParticleExplode.field_70555_ap);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            RenderHelper.func_74518_a();
            tessellator.func_78382_b();
            tessellator.func_78369_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as);
            tessellator.func_78375_b(0.0f, 1.0f, 0.0f);
            tessellator.func_78380_c(15728880);
            tessellator.func_78374_a((double)(f11 - p_70539_3_ * f10 - p_70539_6_ * f10), (double)(f12 - p_70539_4_ * f10), (double)(f13 - p_70539_5_ * f10 - p_70539_7_ * f10), (double)f7, (double)f9);
            tessellator.func_78374_a((double)(f11 - p_70539_3_ * f10 + p_70539_6_ * f10), (double)(f12 + p_70539_4_ * f10), (double)(f13 - p_70539_5_ * f10 + p_70539_7_ * f10), (double)f7, (double)f8);
            tessellator.func_78374_a((double)(f11 + p_70539_3_ * f10 + p_70539_6_ * f10), (double)(f12 + p_70539_4_ * f10), (double)(f13 + p_70539_5_ * f10 + p_70539_7_ * f10), (double)f6, (double)f8);
            tessellator.func_78374_a((double)(f11 + p_70539_3_ * f10 - p_70539_6_ * f10), (double)(f12 - p_70539_4_ * f10), (double)(f13 + p_70539_5_ * f10 - p_70539_7_ * f10), (double)f6, (double)f9);
            tessellator.func_78381_a();
            GL11.glPolygonOffset(0.0f, 0.0f);
            GL11.glEnable(2896);
            GL11.glEnable(2884);
            GL11.glBlendFunc(srcBlend, dstBlend);
            GL11.glDisable(3042);
        }
    }
    
    public int func_70070_b(final float p_70070_1_) {
        return 15728880;
    }
    
    public void func_70071_h_() {
        this.field_70169_q = this.field_70165_t;
        this.field_70167_r = this.field_70163_u;
        this.field_70166_s = this.field_70161_v;
        ++this.nowCount;
        if (this.nowCount == this.endCount) {
            this.func_70106_y();
        }
    }
    
    @Override
    public int func_70537_b() {
        return 3;
    }
    
    static {
        texture = new ResourceLocation("textures/entity/explosion.png");
    }
}
