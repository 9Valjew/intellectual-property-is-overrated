package mcheli.weapon;

import mcheli.wrapper.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderCartridge extends W_Render
{
    public MCH_RenderCartridge() {
        this.field_76989_e = 0.0f;
    }
    
    public void func_76986_a(final Entity entity, final double posX, final double posY, final double posZ, final float par8, final float tickTime) {
        MCH_EntityCartridge cartridge = null;
        cartridge = (MCH_EntityCartridge)entity;
        if (cartridge.model != null && !cartridge.texture_name.isEmpty()) {
            GL11.glPushMatrix();
            GL11.glTranslated(posX, posY, posZ);
            GL11.glScalef(cartridge.getScale(), cartridge.getScale(), cartridge.getScale());
            float prevYaw = cartridge.field_70126_B;
            if (cartridge.field_70177_z - prevYaw < -180.0f) {
                prevYaw -= 360.0f;
            }
            else if (prevYaw - cartridge.field_70177_z < -180.0f) {
                prevYaw += 360.0f;
            }
            final float yaw = -(prevYaw + (cartridge.field_70177_z - prevYaw) * tickTime);
            final float pitch = cartridge.field_70127_C + (cartridge.field_70125_A - cartridge.field_70127_C) * tickTime;
            GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
            this.bindTexture("textures/bullets/" + cartridge.texture_name + ".png");
            cartridge.model.renderAll();
            GL11.glPopMatrix();
        }
    }
    
    @Override
    protected ResourceLocation func_110775_a(final Entity entity) {
        return MCH_RenderCartridge.TEX_DEFAULT;
    }
}
