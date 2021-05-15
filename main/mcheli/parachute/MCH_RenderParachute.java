package mcheli.parachute;

import mcheli.wrapper.*;
import cpw.mods.fml.relauncher.*;
import java.util.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import mcheli.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderParachute extends W_Render
{
    public static final Random rand;
    
    public MCH_RenderParachute() {
        this.field_76989_e = 0.5f;
    }
    
    public void func_76986_a(final Entity entity, final double posX, final double posY, final double posZ, final float par8, final float tickTime) {
        if (!(entity instanceof MCH_EntityParachute)) {
            return;
        }
        final MCH_EntityParachute parachute = (MCH_EntityParachute)entity;
        final int type = parachute.getType();
        if (type <= 0) {
            return;
        }
        GL11.glPushMatrix();
        GL11.glEnable(2884);
        GL11.glTranslated(posX, posY, posZ);
        float prevYaw = entity.field_70126_B;
        if (entity.field_70177_z - prevYaw < -180.0f) {
            prevYaw -= 360.0f;
        }
        else if (prevYaw - entity.field_70177_z < -180.0f) {
            prevYaw += 360.0f;
        }
        final float yaw = prevYaw + (entity.field_70177_z - prevYaw) * tickTime;
        GL11.glRotatef(yaw, 0.0f, -1.0f, 0.0f);
        GL11.glColor4f(0.75f, 0.75f, 0.75f, 1.0f);
        GL11.glEnable(3042);
        final int srcBlend = GL11.glGetInteger(3041);
        final int dstBlend = GL11.glGetInteger(3040);
        GL11.glBlendFunc(770, 771);
        final MCH_Config config = MCH_MOD.config;
        if (MCH_Config.SmoothShading.prmBool) {
            GL11.glShadeModel(7425);
        }
        switch (type) {
            case 1: {
                this.bindTexture("textures/parachute1.png");
                MCH_ModelManager.render("parachute1");
                break;
            }
            case 2: {
                this.bindTexture("textures/parachute2.png");
                if (parachute.isOpenParachute()) {
                    MCH_ModelManager.renderPart("parachute2", "$parachute");
                    break;
                }
                MCH_ModelManager.renderPart("parachute2", "$seat");
                break;
            }
            case 3: {
                this.bindTexture("textures/parachute2.png");
                MCH_ModelManager.renderPart("parachute2", "$parachute");
                break;
            }
        }
        GL11.glBlendFunc(srcBlend, dstBlend);
        GL11.glDisable(3042);
        GL11.glShadeModel(7424);
        GL11.glPopMatrix();
    }
    
    @Override
    protected ResourceLocation func_110775_a(final Entity entity) {
        return MCH_RenderParachute.TEX_DEFAULT;
    }
    
    static {
        rand = new Random();
    }
}
