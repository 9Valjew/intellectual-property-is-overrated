package mcheli.weapon;

import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderBomb extends MCH_RenderBulletBase
{
    public MCH_RenderBomb() {
        this.field_76989_e = 0.5f;
    }
    
    @Override
    public void renderBullet(final Entity entity, final double posX, final double posY, final double posZ, final float yaw, final float partialTickTime) {
        if (!(entity instanceof MCH_EntityBomb)) {
            return;
        }
        final MCH_EntityBomb bomb = (MCH_EntityBomb)entity;
        if (bomb.getInfo() == null) {
            return;
        }
        GL11.glPushMatrix();
        GL11.glTranslated(posX, posY, posZ);
        GL11.glRotatef(-entity.field_70177_z, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-entity.field_70125_A, -1.0f, 0.0f, 0.0f);
        if (bomb.isBomblet > 0 || bomb.getInfo().bomblet <= 0 || bomb.getInfo().bombletSTime > 0) {
            this.renderModel(bomb);
        }
        GL11.glPopMatrix();
    }
    
    @Override
    protected ResourceLocation func_110775_a(final Entity entity) {
        return MCH_RenderBomb.TEX_DEFAULT;
    }
}
