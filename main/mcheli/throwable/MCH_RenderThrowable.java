package mcheli.throwable;

import mcheli.wrapper.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderThrowable extends W_Render
{
    public MCH_RenderThrowable() {
        this.field_76989_e = 0.0f;
    }
    
    public void func_76986_a(final Entity entity, final double posX, final double posY, final double posZ, final float par8, final float tickTime) {
        final MCH_EntityThrowable throwable = (MCH_EntityThrowable)entity;
        final MCH_ThrowableInfo info = throwable.getInfo();
        if (info == null) {
            return;
        }
        GL11.glPushMatrix();
        GL11.glTranslated(posX, posY, posZ);
        GL11.glRotatef(entity.field_70177_z, 0.0f, -1.0f, 0.0f);
        GL11.glRotatef(entity.field_70125_A, 1.0f, 0.0f, 0.0f);
        this.setCommonRenderParam(true, entity.func_70070_b(tickTime));
        if (info.model != null) {
            this.bindTexture("textures/throwable/" + info.name + ".png");
            info.model.renderAll();
        }
        this.restoreCommonRenderParam();
        GL11.glPopMatrix();
    }
    
    @Override
    protected ResourceLocation func_110775_a(final Entity entity) {
        return MCH_RenderThrowable.TEX_DEFAULT;
    }
}
