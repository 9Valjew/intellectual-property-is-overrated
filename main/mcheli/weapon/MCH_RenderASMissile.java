package mcheli.weapon;

import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderASMissile extends MCH_RenderBulletBase
{
    public MCH_RenderASMissile() {
        this.field_76989_e = 0.5f;
    }
    
    @Override
    public void renderBullet(final Entity entity, final double posX, final double posY, final double posZ, final float yaw, final float partialTickTime) {
        if (entity instanceof MCH_EntityBaseBullet) {
            final MCH_EntityBaseBullet bullet = (MCH_EntityBaseBullet)entity;
            GL11.glPushMatrix();
            GL11.glTranslated(posX, posY, posZ);
            GL11.glRotatef(-entity.field_70177_z, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-entity.field_70125_A, -1.0f, 0.0f, 0.0f);
            this.renderModel(bullet);
            GL11.glPopMatrix();
        }
    }
    
    @Override
    protected ResourceLocation func_110775_a(final Entity entity) {
        return MCH_RenderASMissile.TEX_DEFAULT;
    }
}
