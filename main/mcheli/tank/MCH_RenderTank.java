package mcheli.tank;

import cpw.mods.fml.relauncher.*;
import mcheli.aircraft.*;
import org.lwjgl.opengl.*;
import mcheli.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderTank extends MCH_RenderAircraft
{
    public MCH_RenderTank() {
        this.field_76989_e = 2.0f;
    }
    
    @Override
    public void renderAircraft(final MCH_EntityAircraft entity, final double posX, final double posY, final double posZ, final float yaw, final float pitch, final float roll, final float tickTime) {
        MCH_TankInfo tankInfo = null;
        if (entity == null || !(entity instanceof MCH_EntityTank)) {
            return;
        }
        final MCH_EntityTank tank = (MCH_EntityTank)entity;
        tankInfo = tank.getTankInfo();
        if (tankInfo == null) {
            return;
        }
        this.renderWheel(tank, posX, posY, posZ);
        this.renderDebugHitBox(tank, posX, posY, posZ, yaw, pitch);
        this.renderDebugPilotSeat(tank, posX, posY, posZ, yaw, pitch, roll);
        GL11.glTranslated(posX, posY, posZ);
        GL11.glRotatef(yaw, 0.0f, -1.0f, 0.0f);
        GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(roll, 0.0f, 0.0f, 1.0f);
        this.bindTexture("textures/tanks/" + tank.getTextureName() + ".png", tank);
        MCH_RenderAircraft.renderBody(tankInfo.model);
    }
    
    public void renderWheel(final MCH_EntityTank tank, final double posX, final double posY, final double posZ) {
        final MCH_Config config = MCH_MOD.config;
        if (!MCH_Config.TestMode.prmBool) {
            return;
        }
        if (MCH_RenderTank.debugModel == null) {
            return;
        }
        GL11.glColor4f(0.75f, 0.75f, 0.75f, 0.5f);
        for (final MCH_EntityWheel w : tank.WheelMng.wheels) {
            GL11.glPushMatrix();
            GL11.glTranslated(w.field_70165_t - tank.field_70165_t + posX, w.field_70163_u - tank.field_70163_u + posY + 0.25, w.field_70161_v - tank.field_70161_v + posZ);
            GL11.glScalef(w.field_70130_N, w.field_70131_O / 2.0f, w.field_70130_N);
            this.bindTexture("textures/seat_pilot.png");
            MCH_RenderTank.debugModel.renderAll();
            GL11.glPopMatrix();
        }
        GL11.glColor4f(0.75f, 0.75f, 0.75f, 1.0f);
        final Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78371_b(1);
        final Vec3 transformedPosition;
        final Vec3 wp = transformedPosition = tank.getTransformedPosition(tank.WheelMng.weightedCenter);
        transformedPosition.field_72450_a -= tank.field_70165_t;
        final Vec3 vec3 = wp;
        vec3.field_72448_b -= tank.field_70163_u;
        final Vec3 vec4 = wp;
        vec4.field_72449_c -= tank.field_70161_v;
        for (int i = 0; i < tank.WheelMng.wheels.length / 2; ++i) {
            tessellator.func_78384_a((((i & 0x4) > 0) ? 16711680 : 0) | (((i & 0x2) > 0) ? 65280 : 0) | (((i & 0x1) > 0) ? 255 : 0), 192);
            final MCH_EntityWheel w2 = tank.WheelMng.wheels[i * 2 + 0];
            final MCH_EntityWheel w3 = tank.WheelMng.wheels[i * 2 + 1];
            if (w2.isPlus) {
                tessellator.func_78377_a(w3.field_70165_t - tank.field_70165_t + posX, w3.field_70163_u - tank.field_70163_u + posY, w3.field_70161_v - tank.field_70161_v + posZ);
                tessellator.func_78377_a(w2.field_70165_t - tank.field_70165_t + posX, w2.field_70163_u - tank.field_70163_u + posY, w2.field_70161_v - tank.field_70161_v + posZ);
                tessellator.func_78377_a(w2.field_70165_t - tank.field_70165_t + posX, w2.field_70163_u - tank.field_70163_u + posY, w2.field_70161_v - tank.field_70161_v + posZ);
                tessellator.func_78377_a(posX + wp.field_72450_a, posY + wp.field_72448_b, posZ + wp.field_72449_c);
                tessellator.func_78377_a(posX + wp.field_72450_a, posY + wp.field_72448_b, posZ + wp.field_72449_c);
                tessellator.func_78377_a(w3.field_70165_t - tank.field_70165_t + posX, w3.field_70163_u - tank.field_70163_u + posY, w3.field_70161_v - tank.field_70161_v + posZ);
            }
            else {
                tessellator.func_78377_a(w2.field_70165_t - tank.field_70165_t + posX, w2.field_70163_u - tank.field_70163_u + posY, w2.field_70161_v - tank.field_70161_v + posZ);
                tessellator.func_78377_a(w3.field_70165_t - tank.field_70165_t + posX, w3.field_70163_u - tank.field_70163_u + posY, w3.field_70161_v - tank.field_70161_v + posZ);
                tessellator.func_78377_a(w3.field_70165_t - tank.field_70165_t + posX, w3.field_70163_u - tank.field_70163_u + posY, w3.field_70161_v - tank.field_70161_v + posZ);
                tessellator.func_78377_a(posX + wp.field_72450_a, posY + wp.field_72448_b, posZ + wp.field_72449_c);
                tessellator.func_78377_a(posX + wp.field_72450_a, posY + wp.field_72448_b, posZ + wp.field_72449_c);
                tessellator.func_78377_a(w2.field_70165_t - tank.field_70165_t + posX, w2.field_70163_u - tank.field_70163_u + posY, w2.field_70161_v - tank.field_70161_v + posZ);
            }
        }
        tessellator.func_78381_a();
    }
    
    @Override
    protected ResourceLocation func_110775_a(final Entity entity) {
        return MCH_RenderTank.TEX_DEFAULT;
    }
}
