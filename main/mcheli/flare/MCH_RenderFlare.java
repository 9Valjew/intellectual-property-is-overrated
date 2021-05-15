package mcheli.flare;

import mcheli.wrapper.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderFlare extends W_Render
{
    protected MCH_ModelFlare model;
    
    public MCH_RenderFlare() {
        this.model = new MCH_ModelFlare();
    }
    
    public void func_76986_a(final Entity entity, final double posX, final double posY, final double posZ, final float yaw, final float partialTickTime) {
        GL11.glPushMatrix();
        GL11.glEnable(2884);
        final double x = entity.field_70169_q + entity.field_70159_w * partialTickTime;
        final double y = entity.field_70167_r + entity.field_70181_x * partialTickTime;
        final double z = entity.field_70166_s + entity.field_70179_y * partialTickTime;
        GL11.glTranslated(posX, posY, posZ);
        GL11.glRotatef(-entity.field_70177_z, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(entity.field_70125_A, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(45.0f, 0.0f, 0.0f, 1.0f);
        GL11.glColor4f(1.0f, 1.0f, 0.5f, 1.0f);
        this.bindTexture("textures/flare.png");
        this.model.renderModel(0.0, 0.0, 0.0625f);
        GL11.glPopMatrix();
    }
    
    @Override
    protected ResourceLocation func_110775_a(final Entity entity) {
        return MCH_RenderFlare.TEX_DEFAULT;
    }
}
