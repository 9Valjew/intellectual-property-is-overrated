package mcheli.uav;

import mcheli.wrapper.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import mcheli.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderUavStation extends W_Render
{
    public static final String[] MODEL_NAME;
    public static final String[] TEX_NAME_ON;
    public static final String[] TEX_NAME_OFF;
    
    public MCH_RenderUavStation() {
        this.field_76989_e = 1.0f;
    }
    
    public void func_76986_a(final Entity entity, final double posX, final double posY, final double posZ, final float par8, final float tickTime) {
        if (!(entity instanceof MCH_EntityUavStation)) {
            return;
        }
        final MCH_EntityUavStation uavSt = (MCH_EntityUavStation)entity;
        if (uavSt.getKind() <= 0) {
            return;
        }
        final int kind = uavSt.getKind() - 1;
        GL11.glPushMatrix();
        GL11.glTranslated(posX, posY, posZ);
        GL11.glEnable(2884);
        GL11.glRotatef(entity.field_70177_z, 0.0f, -1.0f, 0.0f);
        GL11.glRotatef(entity.field_70125_A, 1.0f, 0.0f, 0.0f);
        GL11.glColor4f(0.75f, 0.75f, 0.75f, 1.0f);
        GL11.glEnable(3042);
        final int srcBlend = GL11.glGetInteger(3041);
        final int dstBlend = GL11.glGetInteger(3040);
        GL11.glBlendFunc(770, 771);
        if (kind == 0) {
            if (uavSt.getControlAircract() != null && uavSt.field_70153_n != null) {
                this.bindTexture("textures/" + MCH_RenderUavStation.TEX_NAME_ON[kind] + ".png");
            }
            else {
                this.bindTexture("textures/" + MCH_RenderUavStation.TEX_NAME_OFF[kind] + ".png");
            }
            MCH_ModelManager.render(MCH_RenderUavStation.MODEL_NAME[kind]);
        }
        else {
            if (uavSt.rotCover > 0.95f) {
                this.bindTexture("textures/" + MCH_RenderUavStation.TEX_NAME_ON[kind] + ".png");
            }
            else {
                this.bindTexture("textures/" + MCH_RenderUavStation.TEX_NAME_OFF[kind] + ".png");
            }
            this.renderPortableController(uavSt, MCH_RenderUavStation.MODEL_NAME[kind], tickTime);
        }
        GL11.glBlendFunc(srcBlend, dstBlend);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public void renderPortableController(final MCH_EntityUavStation uavSt, final String name, final float tickTime) {
        MCH_ModelManager.renderPart(name, "$body");
        final float rot = MCH_Lib.smooth(uavSt.rotCover, uavSt.prevRotCover, tickTime);
        this.renderRotPart(name, "$cover", rot * 60.0f, 0.0, -0.1812, -0.3186);
        this.renderRotPart(name, "$laptop_cover", rot * 95.0f, 0.0, -0.1808, -0.0422);
        this.renderRotPart(name, "$display", rot * -85.0f, 0.0, -0.1807, 0.2294);
    }
    
    private void renderRotPart(final String modelName, final String partName, final float rot, final double x, final double y, final double z) {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glRotatef(rot, -1.0f, 0.0f, 0.0f);
        GL11.glTranslated(-x, -y, -z);
        MCH_ModelManager.renderPart(modelName, partName);
        GL11.glPopMatrix();
    }
    
    @Override
    protected ResourceLocation func_110775_a(final Entity entity) {
        return MCH_RenderUavStation.TEX_DEFAULT;
    }
    
    static {
        MODEL_NAME = new String[] { "uav_station", "uav_portable_controller" };
        TEX_NAME_ON = new String[] { "uav_station_on", "uav_portable_controller_on" };
        TEX_NAME_OFF = new String[] { "uav_station", "uav_portable_controller" };
    }
}
