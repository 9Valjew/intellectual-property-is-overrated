package mcheli.wrapper;

import net.minecraft.client.renderer.entity.*;
import java.nio.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import mcheli.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;

public abstract class W_Render extends Render
{
    private static FloatBuffer colorBuffer;
    protected static final ResourceLocation TEX_DEFAULT;
    public int srcBlend;
    public int dstBlend;
    
    protected void bindTexture(final String path) {
        super.func_110776_a(new ResourceLocation(W_MOD.DOMAIN, path));
    }
    
    protected ResourceLocation func_110775_a(final Entity entity) {
        return W_Render.TEX_DEFAULT;
    }
    
    public static FloatBuffer setColorBuffer(final float p_74521_0_, final float p_74521_1_, final float p_74521_2_, final float p_74521_3_) {
        W_Render.colorBuffer.clear();
        W_Render.colorBuffer.put(p_74521_0_).put(p_74521_1_).put(p_74521_2_).put(p_74521_3_);
        W_Render.colorBuffer.flip();
        return W_Render.colorBuffer;
    }
    
    public void setCommonRenderParam(final boolean smoothShading, final int lighting) {
        if (smoothShading) {
            final MCH_Config config = MCH_MOD.config;
            if (MCH_Config.SmoothShading.prmBool) {
                GL11.glShadeModel(7425);
            }
        }
        GL11.glAlphaFunc(516, 0.001f);
        GL11.glEnable(2884);
        final int j = lighting % 65536;
        final int k = lighting / 65536;
        OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, j / 1.0f, k / 1.0f);
        GL11.glColor4f(0.75f, 0.75f, 0.75f, 1.0f);
        GL11.glEnable(3042);
        this.srcBlend = GL11.glGetInteger(3041);
        this.dstBlend = GL11.glGetInteger(3040);
        GL11.glBlendFunc(770, 771);
    }
    
    public void restoreCommonRenderParam() {
        GL11.glBlendFunc(this.srcBlend, this.dstBlend);
        GL11.glDisable(3042);
        GL11.glShadeModel(7424);
    }
    
    static {
        W_Render.colorBuffer = GLAllocation.func_74529_h(16);
        TEX_DEFAULT = new ResourceLocation(W_MOD.DOMAIN, "textures/default.png");
    }
}
