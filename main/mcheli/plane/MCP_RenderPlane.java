package mcheli.plane;

import cpw.mods.fml.relauncher.*;
import org.lwjgl.opengl.*;
import java.util.*;
import mcheli.aircraft.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCP_RenderPlane extends MCH_RenderAircraft
{
    public MCP_RenderPlane() {
        this.field_76989_e = 2.0f;
    }
    
    @Override
    public void renderAircraft(final MCH_EntityAircraft entity, final double posX, final double posY, final double posZ, final float yaw, final float pitch, final float roll, final float tickTime) {
        MCP_PlaneInfo planeInfo = null;
        if (entity == null || !(entity instanceof MCP_EntityPlane)) {
            return;
        }
        final MCP_EntityPlane plane = (MCP_EntityPlane)entity;
        planeInfo = plane.getPlaneInfo();
        if (planeInfo == null) {
            return;
        }
        this.renderDebugHitBox(plane, posX, posY, posZ, yaw, pitch);
        this.renderDebugPilotSeat(plane, posX, posY, posZ, yaw, pitch, roll);
        GL11.glTranslated(posX, posY, posZ);
        GL11.glRotatef(yaw, 0.0f, -1.0f, 0.0f);
        GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(roll, 0.0f, 0.0f, 1.0f);
        this.bindTexture("textures/planes/" + plane.getTextureName() + ".png", plane);
        if (planeInfo.haveNozzle() && plane.partNozzle != null) {
            this.renderNozzle(plane, planeInfo, tickTime);
        }
        if (planeInfo.haveWing() && plane.partWing != null) {
            this.renderWing(plane, planeInfo, tickTime);
        }
        if (planeInfo.haveRotor() && plane.partNozzle != null) {
            this.renderRotor(plane, planeInfo, tickTime);
        }
        MCH_RenderAircraft.renderBody(planeInfo.model);
    }
    
    public void renderRotor(final MCP_EntityPlane plane, final MCP_PlaneInfo planeInfo, final float tickTime) {
        final float rot = plane.getNozzleRotation();
        final float prevRot = plane.getPrevNozzleRotation();
        for (final MCP_PlaneInfo.Rotor r : planeInfo.rotorList) {
            GL11.glPushMatrix();
            GL11.glTranslated(r.pos.field_72450_a, r.pos.field_72448_b, r.pos.field_72449_c);
            GL11.glRotatef((prevRot + (rot - prevRot) * tickTime) * r.maxRotFactor, (float)r.rot.field_72450_a, (float)r.rot.field_72448_b, (float)r.rot.field_72449_c);
            GL11.glTranslated(-r.pos.field_72450_a, -r.pos.field_72448_b, -r.pos.field_72449_c);
            MCH_RenderAircraft.renderPart(r.model, planeInfo.model, r.modelName);
            for (final MCP_PlaneInfo.Blade b : r.blades) {
                float br = plane.prevRotationRotor;
                br += (plane.rotationRotor - plane.prevRotationRotor) * tickTime;
                GL11.glPushMatrix();
                GL11.glTranslated(b.pos.field_72450_a, b.pos.field_72448_b, b.pos.field_72449_c);
                GL11.glRotatef(br, (float)b.rot.field_72450_a, (float)b.rot.field_72448_b, (float)b.rot.field_72449_c);
                GL11.glTranslated(-b.pos.field_72450_a, -b.pos.field_72448_b, -b.pos.field_72449_c);
                for (int i = 0; i < b.numBlade; ++i) {
                    GL11.glTranslated(b.pos.field_72450_a, b.pos.field_72448_b, b.pos.field_72449_c);
                    GL11.glRotatef((float)b.rotBlade, (float)b.rot.field_72450_a, (float)b.rot.field_72448_b, (float)b.rot.field_72449_c);
                    GL11.glTranslated(-b.pos.field_72450_a, -b.pos.field_72448_b, -b.pos.field_72449_c);
                    MCH_RenderAircraft.renderPart(b.model, planeInfo.model, b.modelName);
                }
                GL11.glPopMatrix();
            }
            GL11.glPopMatrix();
        }
    }
    
    public void renderWing(final MCP_EntityPlane plane, final MCP_PlaneInfo planeInfo, final float tickTime) {
        final float rot = plane.getWingRotation();
        final float prevRot = plane.getPrevWingRotation();
        for (final MCP_PlaneInfo.Wing w : planeInfo.wingList) {
            GL11.glPushMatrix();
            GL11.glTranslated(w.pos.field_72450_a, w.pos.field_72448_b, w.pos.field_72449_c);
            GL11.glRotatef((prevRot + (rot - prevRot) * tickTime) * w.maxRotFactor, (float)w.rot.field_72450_a, (float)w.rot.field_72448_b, (float)w.rot.field_72449_c);
            GL11.glTranslated(-w.pos.field_72450_a, -w.pos.field_72448_b, -w.pos.field_72449_c);
            MCH_RenderAircraft.renderPart(w.model, planeInfo.model, w.modelName);
            if (w.pylonList != null) {
                for (final MCP_PlaneInfo.Pylon p : w.pylonList) {
                    GL11.glPushMatrix();
                    GL11.glTranslated(p.pos.field_72450_a, p.pos.field_72448_b, p.pos.field_72449_c);
                    GL11.glRotatef((prevRot + (rot - prevRot) * tickTime) * p.maxRotFactor, (float)p.rot.field_72450_a, (float)p.rot.field_72448_b, (float)p.rot.field_72449_c);
                    GL11.glTranslated(-p.pos.field_72450_a, -p.pos.field_72448_b, -p.pos.field_72449_c);
                    MCH_RenderAircraft.renderPart(p.model, planeInfo.model, p.modelName);
                    GL11.glPopMatrix();
                }
            }
            GL11.glPopMatrix();
        }
    }
    
    public void renderNozzle(final MCP_EntityPlane plane, final MCP_PlaneInfo planeInfo, final float tickTime) {
        final float rot = plane.getNozzleRotation();
        final float prevRot = plane.getPrevNozzleRotation();
        for (final MCH_AircraftInfo.DrawnPart n : planeInfo.nozzles) {
            GL11.glPushMatrix();
            GL11.glTranslated(n.pos.field_72450_a, n.pos.field_72448_b, n.pos.field_72449_c);
            GL11.glRotatef(prevRot + (rot - prevRot) * tickTime, (float)n.rot.field_72450_a, (float)n.rot.field_72448_b, (float)n.rot.field_72449_c);
            GL11.glTranslated(-n.pos.field_72450_a, -n.pos.field_72448_b, -n.pos.field_72449_c);
            MCH_RenderAircraft.renderPart(n.model, planeInfo.model, n.modelName);
            GL11.glPopMatrix();
        }
    }
    
    @Override
    protected ResourceLocation func_110775_a(final Entity entity) {
        return MCP_RenderPlane.TEX_DEFAULT;
    }
}
