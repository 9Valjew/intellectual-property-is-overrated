package mcheli.gltd;

import cpw.mods.fml.relauncher.*;
import java.util.*;
import net.minecraftforge.client.model.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import mcheli.wrapper.*;
import mcheli.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderGLTD extends W_Render
{
    public static final Random rand;
    public static IModelCustom model;
    
    public MCH_RenderGLTD() {
        this.field_76989_e = 0.5f;
        MCH_RenderGLTD.model = null;
    }
    
    public void func_76986_a(final Entity entity, final double posX, final double posY, final double posZ, final float par8, final float tickTime) {
        if (!(entity instanceof MCH_EntityGLTD)) {
            return;
        }
        final MCH_EntityGLTD gltd = (MCH_EntityGLTD)entity;
        GL11.glPushMatrix();
        GL11.glTranslated(posX, posY, posZ);
        this.setCommonRenderParam(true, entity.func_70070_b(tickTime));
        this.bindTexture("textures/gltd.png");
        final Minecraft mc = Minecraft.func_71410_x();
        boolean isNotRenderHead = false;
        if (gltd.field_70153_n != null) {
            gltd.isUsedPlayer = true;
            gltd.renderRotaionYaw = gltd.field_70153_n.field_70177_z;
            gltd.renderRotaionPitch = gltd.field_70153_n.field_70125_A;
            isNotRenderHead = (mc.field_71474_y.field_74320_O == 0 && W_Lib.isClientPlayer(gltd.field_70153_n));
        }
        if (gltd.isUsedPlayer) {
            GL11.glPushMatrix();
            GL11.glRotatef(-gltd.field_70177_z, 0.0f, 1.0f, 0.0f);
            MCH_RenderGLTD.model.renderPart("$body");
            GL11.glPopMatrix();
        }
        else {
            GL11.glRotatef(-gltd.field_70177_z, 0.0f, 1.0f, 0.0f);
            MCH_RenderGLTD.model.renderPart("$body");
        }
        GL11.glTranslatef(0.0f, 0.45f, 0.0f);
        if (gltd.isUsedPlayer) {
            GL11.glRotatef(gltd.renderRotaionYaw, 0.0f, -1.0f, 0.0f);
            GL11.glRotatef(gltd.renderRotaionPitch, 1.0f, 0.0f, 0.0f);
        }
        GL11.glTranslatef(0.0f, -0.45f, 0.0f);
        if (!isNotRenderHead) {
            MCH_RenderGLTD.model.renderPart("$head");
        }
        GL11.glTranslatef(0.0f, 0.45f, 0.0f);
        this.restoreCommonRenderParam();
        GL11.glDisable(2896);
        final Vec3[] v = { Vec3.func_72443_a(0.0, 0.2, 0.0), Vec3.func_72443_a(0.0, 0.2, 100.0) };
        final int a = MCH_RenderGLTD.rand.nextInt(64);
        MCH_RenderLib.drawLine(v, 0x6080FF80 | a << 24);
        GL11.glEnable(2896);
        GL11.glPopMatrix();
    }
    
    @Override
    protected ResourceLocation func_110775_a(final Entity entity) {
        return MCH_RenderGLTD.TEX_DEFAULT;
    }
    
    static {
        rand = new Random();
    }
}
