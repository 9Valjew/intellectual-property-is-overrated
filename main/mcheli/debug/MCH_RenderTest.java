package mcheli.debug;

import mcheli.wrapper.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.*;
import mcheli.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderTest extends W_Render
{
    protected MCH_ModelTest model;
    private float offsetX;
    private float offsetY;
    private float offsetZ;
    private String textureName;
    
    public MCH_RenderTest(final float x, final float y, final float z, final String texture_name) {
        this.offsetX = x;
        this.offsetY = y;
        this.offsetZ = z;
        this.textureName = texture_name;
        this.model = new MCH_ModelTest();
    }
    
    public void func_76986_a(final Entity e, final double posX, final double posY, final double posZ, final float par8, final float par9) {
        final MCH_Config config = MCH_MOD.config;
        if (!MCH_Config.TestMode.prmBool) {
            return;
        }
        GL11.glPushMatrix();
        GL11.glTranslated(posX + this.offsetX, posY + this.offsetY, posZ + this.offsetZ);
        GL11.glScalef(e.field_70130_N, e.field_70131_O, e.field_70130_N);
        GL11.glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
        float prevYaw;
        if (e.field_70177_z - e.field_70126_B < -180.0f) {
            prevYaw = e.field_70126_B - 360.0f;
        }
        else if (e.field_70126_B - e.field_70177_z < -180.0f) {
            prevYaw = e.field_70126_B + 360.0f;
        }
        else {
            prevYaw = e.field_70126_B;
        }
        final float yaw = -(prevYaw + (e.field_70177_z - prevYaw) * par9) - 180.0f;
        final float pitch = -(e.field_70127_C + (e.field_70125_A - e.field_70127_C) * par9);
        GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        this.bindTexture("textures/" + this.textureName + ".png");
        this.model.renderModel(0.0, 0.0, 0.1f);
        GL11.glPopMatrix();
    }
    
    @Override
    protected ResourceLocation func_110775_a(final Entity entity) {
        return MCH_RenderTest.TEX_DEFAULT;
    }
}
