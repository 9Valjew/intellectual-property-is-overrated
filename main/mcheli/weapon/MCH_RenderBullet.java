package mcheli.weapon;

import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderBullet extends MCH_RenderBulletBase
{
    @Override
    public void renderBullet(final Entity entity, final double posX, final double posY, final double posZ, final float yaw, final float tickTime) {
        final MCH_EntityBaseBullet blt = (MCH_EntityBaseBullet)entity;
        GL11.glPushMatrix();
        final double x = entity.field_70169_q + entity.field_70159_w * tickTime;
        final double y = entity.field_70167_r + entity.field_70181_x * tickTime;
        final double z = entity.field_70166_s + entity.field_70179_y * tickTime;
        GL11.glTranslated(posX, posY, posZ);
        GL11.glRotatef(-entity.field_70177_z, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(entity.field_70125_A, 1.0f, 0.0f, 0.0f);
        this.renderModel(blt);
        GL11.glPopMatrix();
    }
    
    @Override
    protected ResourceLocation func_110775_a(final Entity entity) {
        return MCH_RenderBullet.TEX_DEFAULT;
    }
}
