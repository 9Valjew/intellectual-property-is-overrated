package mcheli.weapon;

import net.minecraft.entity.*;
import mcheli.wrapper.*;
import org.lwjgl.opengl.*;
import mcheli.*;
import net.minecraft.block.*;

public abstract class MCH_RenderBulletBase extends W_Render
{
    public void func_76986_a(final Entity e, final double var2, final double var4, final double var6, final float var8, final float var9) {
        if (e instanceof MCH_EntityBaseBullet && ((MCH_EntityBaseBullet)e).getInfo() != null) {
            MCH_Color c = ((MCH_EntityBaseBullet)e).getInfo().color;
            for (int y = 0; y < 3; ++y) {
                final Block b = W_WorldFunc.getBlock(e.field_70170_p, (int)(e.field_70165_t + 0.5), (int)(e.field_70163_u + 1.5 - y), (int)(e.field_70161_v + 0.5));
                if (b != null && b == W_Block.getWater()) {
                    c = ((MCH_EntityBaseBullet)e).getInfo().colorInWater;
                    break;
                }
            }
            GL11.glColor4f(c.r, c.g, c.b, c.a);
        }
        else {
            GL11.glColor4f(0.75f, 0.75f, 0.75f, 1.0f);
        }
        GL11.glAlphaFunc(516, 0.001f);
        GL11.glEnable(2884);
        GL11.glEnable(3042);
        final int srcBlend = GL11.glGetInteger(3041);
        final int dstBlend = GL11.glGetInteger(3040);
        GL11.glBlendFunc(770, 771);
        this.renderBullet(e, var2, var4, var6, var8, var9);
        GL11.glColor4f(0.75f, 0.75f, 0.75f, 1.0f);
        GL11.glBlendFunc(srcBlend, dstBlend);
        GL11.glDisable(3042);
    }
    
    public void renderModel(final MCH_EntityBaseBullet e) {
        final MCH_BulletModel model = e.getBulletModel();
        if (model != null) {
            this.bindTexture("textures/bullets/" + model.name + ".png");
            model.model.renderAll();
        }
    }
    
    public abstract void renderBullet(final Entity p0, final double p1, final double p2, final double p3, final float p4, final float p5);
}
