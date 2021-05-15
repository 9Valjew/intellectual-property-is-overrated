package mcheli.helicopter;

import cpw.mods.fml.relauncher.*;
import org.lwjgl.opengl.*;
import mcheli.aircraft.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderHeli extends MCH_RenderAircraft
{
    public MCH_RenderHeli() {
        this.field_76989_e = 2.0f;
    }
    
    @Override
    public void renderAircraft(final MCH_EntityAircraft entity, final double posX, final double posY, final double posZ, final float yaw, final float pitch, final float roll, final float tickTime) {
        MCH_HeliInfo heliInfo = null;
        if (entity == null || !(entity instanceof MCH_EntityHeli)) {
            return;
        }
        final MCH_EntityHeli heli = (MCH_EntityHeli)entity;
        heliInfo = heli.getHeliInfo();
        if (heliInfo == null) {
            return;
        }
        this.renderDebugHitBox(heli, posX, posY, posZ, yaw, pitch);
        this.renderDebugPilotSeat(heli, posX, posY, posZ, yaw, pitch, roll);
        GL11.glTranslated(posX, posY, posZ);
        GL11.glRotatef(yaw, 0.0f, -1.0f, 0.0f);
        GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(roll, 0.0f, 0.0f, 1.0f);
        this.bindTexture("textures/helicopters/" + heli.getTextureName() + ".png", heli);
        MCH_RenderAircraft.renderBody(heliInfo.model);
        this.drawModelBlade(heli, heliInfo, tickTime);
    }
    
    public void drawModelBlade(final MCH_EntityHeli heli, final MCH_HeliInfo info, final float tickTime) {
        for (int i = 0; i < heli.rotors.length && i < info.rotorList.size(); ++i) {
            final MCH_HeliInfo.Rotor rotorInfo = info.rotorList.get(i);
            final MCH_Rotor rotor = heli.rotors[i];
            GL11.glPushMatrix();
            if (rotorInfo.oldRenderMethod) {
                GL11.glTranslated(rotorInfo.pos.field_72450_a, rotorInfo.pos.field_72448_b, rotorInfo.pos.field_72449_c);
            }
            for (final MCH_Blade b : rotor.blades) {
                GL11.glPushMatrix();
                final float rot = b.getRotation();
                float prevRot = b.getPrevRotation();
                if (rot - prevRot < -180.0f) {
                    prevRot -= 360.0f;
                }
                else if (prevRot - rot < -180.0f) {
                    prevRot += 360.0f;
                }
                if (!rotorInfo.oldRenderMethod) {
                    GL11.glTranslated(rotorInfo.pos.field_72450_a, rotorInfo.pos.field_72448_b, rotorInfo.pos.field_72449_c);
                }
                GL11.glRotatef(prevRot + (rot - prevRot) * tickTime, (float)rotorInfo.rot.field_72450_a, (float)rotorInfo.rot.field_72448_b, (float)rotorInfo.rot.field_72449_c);
                if (!rotorInfo.oldRenderMethod) {
                    GL11.glTranslated(-rotorInfo.pos.field_72450_a, -rotorInfo.pos.field_72448_b, -rotorInfo.pos.field_72449_c);
                }
                MCH_RenderAircraft.renderPart(rotorInfo.model, info.model, rotorInfo.modelName);
                GL11.glPopMatrix();
            }
            GL11.glPopMatrix();
        }
    }
    
    @Override
    protected ResourceLocation func_110775_a(final Entity entity) {
        return MCH_RenderHeli.TEX_DEFAULT;
    }
}
