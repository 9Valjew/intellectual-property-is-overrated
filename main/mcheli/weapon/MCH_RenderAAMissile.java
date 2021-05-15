package mcheli.weapon;

import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import mcheli.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderAAMissile extends MCH_RenderBulletBase
{
    public MCH_RenderAAMissile() {
        this.field_76989_e = 0.5f;
    }
    
    @Override
    public void renderBullet(final Entity entity, final double posX, final double posY, final double posZ, final float par8, final float par9) {
        if (!(entity instanceof MCH_EntityAAMissile)) {
            return;
        }
        final MCH_EntityAAMissile aam = (MCH_EntityAAMissile)entity;
        final double mx = aam.prevMotionX + (aam.field_70159_w - aam.prevMotionX) * par9;
        final double my = aam.prevMotionY + (aam.field_70181_x - aam.prevMotionY) * par9;
        final double mz = aam.prevMotionZ + (aam.field_70179_y - aam.prevMotionZ) * par9;
        GL11.glPushMatrix();
        GL11.glTranslated(posX, posY, posZ);
        final Vec3 v = MCH_Lib.getYawPitchFromVec(mx, my, mz);
        GL11.glRotatef((float)v.field_72448_b - 90.0f, 0.0f, -1.0f, 0.0f);
        GL11.glRotatef((float)v.field_72449_c, -1.0f, 0.0f, 0.0f);
        this.renderModel(aam);
        GL11.glPopMatrix();
    }
    
    @Override
    protected ResourceLocation func_110775_a(final Entity entity) {
        return MCH_RenderAAMissile.TEX_DEFAULT;
    }
}
