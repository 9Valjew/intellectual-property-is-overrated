package mcheli.weapon;

import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import mcheli.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderA10 extends MCH_RenderBulletBase
{
    public MCH_RenderA10() {
        this.field_76989_e = 10.5f;
    }
    
    @Override
    public void renderBullet(final Entity e, final double posX, final double posY, final double posZ, final float par8, final float tickTime) {
        if (!(e instanceof MCH_EntityA10)) {
            return;
        }
        if (!((MCH_EntityA10)e).isRender()) {
            return;
        }
        GL11.glPushMatrix();
        GL11.glTranslated(posX, posY, posZ);
        final float yaw = -(e.field_70126_B + (e.field_70177_z - e.field_70126_B) * tickTime);
        final float pitch = -(e.field_70127_C + (e.field_70125_A - e.field_70127_C) * tickTime);
        GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        this.bindTexture("textures/bullets/a10.png");
        MCH_ModelManager.render("a-10");
        GL11.glPopMatrix();
    }
    
    @Override
    protected ResourceLocation func_110775_a(final Entity entity) {
        return MCH_RenderA10.TEX_DEFAULT;
    }
}
