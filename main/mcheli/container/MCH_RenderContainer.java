package mcheli.container;

import mcheli.wrapper.*;
import cpw.mods.fml.relauncher.*;
import java.util.*;
import net.minecraft.entity.*;
import mcheli.aircraft.*;
import org.lwjgl.opengl.*;
import mcheli.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderContainer extends W_Render
{
    public static final Random rand;
    
    public MCH_RenderContainer() {
        this.field_76989_e = 0.5f;
    }
    
    public void func_76986_a(final Entity entity, final double posX, final double posY, final double posZ, final float par8, final float tickTime) {
        if (MCH_RenderAircraft.shouldSkipRender(entity)) {
            return;
        }
        GL11.glPushMatrix();
        GL11.glEnable(2884);
        GL11.glTranslated(posX, posY - 0.2, posZ);
        final float yaw = MCH_Lib.smoothRot(entity.field_70177_z, entity.field_70126_B, tickTime);
        final float pitch = MCH_Lib.smoothRot(entity.field_70125_A, entity.field_70127_C, tickTime);
        GL11.glRotatef(yaw, 0.0f, -1.0f, 0.0f);
        GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        GL11.glColor4f(0.75f, 0.75f, 0.75f, 1.0f);
        this.bindTexture("textures/container.png");
        MCH_ModelManager.render("container");
        GL11.glPopMatrix();
    }
    
    @Override
    protected ResourceLocation func_110775_a(final Entity entity) {
        return MCH_RenderContainer.TEX_DEFAULT;
    }
    
    static {
        rand = new Random();
    }
}
