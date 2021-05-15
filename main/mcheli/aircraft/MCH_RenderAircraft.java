package mcheli.aircraft;

import net.minecraftforge.client.model.*;
import org.lwjgl.opengl.*;
import mcheli.multiplay.*;
import mcheli.lweapon.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.texture.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import net.minecraft.client.gui.*;
import mcheli.wrapper.modelloader.*;
import mcheli.*;
import net.minecraft.client.*;
import mcheli.wrapper.*;
import mcheli.uav.*;
import mcheli.gui.*;
import mcheli.weapon.*;

public abstract class MCH_RenderAircraft extends W_Render
{
    public static boolean renderingEntity;
    public static IModelCustom debugModel;
    
    public void func_76986_a(final Entity entity, final double posX, final double posY, final double posZ, final float par8, final float tickTime) {
        final MCH_EntityAircraft ac = (MCH_EntityAircraft)entity;
        final MCH_AircraftInfo info = ac.getAcInfo();
        if (info != null) {
            GL11.glPushMatrix();
            final float yaw = this.calcRot(ac.getRotYaw(), ac.field_70126_B, tickTime);
            final float pitch = ac.calcRotPitch(tickTime);
            final float roll = this.calcRot(ac.getRotRoll(), ac.prevRotationRoll, tickTime);
            final MCH_Config config = MCH_MOD.config;
            if (MCH_Config.EnableModEntityRender.prmBool) {
                this.renderRiddenEntity(ac, tickTime, yaw, pitch + info.entityPitch, roll + info.entityRoll, info.entityWidth, info.entityHeight);
            }
            if (!shouldSkipRender(entity)) {
                this.setCommonRenderParam(info.smoothShading, ac.func_70070_b(tickTime));
                if (ac.isDestroyed()) {
                    GL11.glColor4f(0.15f, 0.15f, 0.15f, 1.0f);
                }
                else {
                    GL11.glColor4f(0.75f, 0.75f, 0.75f, 1.0f);
                }
                this.renderAircraft(ac, posX, posY, posZ, yaw, pitch, roll, tickTime);
                this.renderCommonPart(ac, info, posX, posY, posZ, tickTime);
                renderLight(posX, posY, posZ, tickTime, ac, info);
                this.restoreCommonRenderParam();
            }
            GL11.glPopMatrix();
            MCH_GuiTargetMarker.addMarkEntityPos(1, entity, posX, posY + info.markerHeight, posZ);
            MCH_ClientLightWeaponTickHandler.markEntity(entity, posX, posY, posZ);
            renderEntityMarker(ac);
        }
    }
    
    public static boolean shouldSkipRender(final Entity entity) {
        if (entity instanceof MCH_IEntityCanRideAircraft) {
            final MCH_IEntityCanRideAircraft e = (MCH_IEntityCanRideAircraft)entity;
            if (e.isSkipNormalRender()) {
                return !MCH_RenderAircraft.renderingEntity;
            }
        }
        else if ((entity.getClass().toString().indexOf("flansmod.common.driveables.EntityPlane") > 0 || entity.getClass().toString().indexOf("flansmod.common.driveables.EntityVehicle") > 0) && entity.field_70154_o instanceof MCH_EntitySeat) {
            return !MCH_RenderAircraft.renderingEntity;
        }
        return false;
    }
    
    public void func_76979_b(final Entity entity, final double p_76979_2_, final double p_76979_4_, final double p_76979_6_, final float p_76979_8_, final float p_76979_9_) {
        if (entity.func_90999_ad()) {
            this.renderEntityOnFire(entity, p_76979_2_, p_76979_4_, p_76979_6_, p_76979_9_);
        }
    }
    
    private void renderEntityOnFire(final Entity entity, final double x, final double y, final double z, final float tick) {
        GL11.glDisable(2896);
        final IIcon iicon = Blocks.field_150480_ab.func_149840_c(0);
        final IIcon iicon2 = Blocks.field_150480_ab.func_149840_c(1);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);
        final float f1 = entity.field_70130_N * 1.4f;
        GL11.glScalef(f1 * 2.0f, f1 * 2.0f, f1 * 2.0f);
        final Tessellator tessellator = Tessellator.field_78398_a;
        float f2 = 1.5f;
        final float f3 = 0.0f;
        float f4 = entity.field_70131_O / f1;
        float f5 = (float)(entity.field_70163_u + entity.field_70121_D.field_72338_b);
        GL11.glRotatef(-this.field_76990_c.field_78735_i, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(0.0f, 0.0f, -0.3f + (int)f4 * 0.02f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        float f6 = 0.0f;
        int i = 0;
        tessellator.func_78382_b();
        while (f4 > 0.0f) {
            final IIcon iicon3 = (i % 2 == 0) ? iicon : iicon2;
            this.func_110776_a(TextureMap.field_110575_b);
            float f7 = iicon3.func_94209_e();
            final float f8 = iicon3.func_94206_g();
            float f9 = iicon3.func_94212_f();
            final float f10 = iicon3.func_94210_h();
            if (i / 2 % 2 == 0) {
                final float f11 = f9;
                f9 = f7;
                f7 = f11;
            }
            tessellator.func_78374_a((double)(f2 - f3), (double)(0.0f - f5), (double)f6, (double)f9, (double)f10);
            tessellator.func_78374_a((double)(-f2 - f3), (double)(0.0f - f5), (double)f6, (double)f7, (double)f10);
            tessellator.func_78374_a((double)(-f2 - f3), (double)(1.4f - f5), (double)f6, (double)f7, (double)f8);
            tessellator.func_78374_a((double)(f2 - f3), (double)(1.4f - f5), (double)f6, (double)f9, (double)f8);
            f4 -= 0.45f;
            f5 -= 0.45f;
            f2 *= 0.9f;
            f6 += 0.03f;
            ++i;
        }
        tessellator.func_78381_a();
        GL11.glPopMatrix();
        GL11.glEnable(2896);
    }
    
    public static void renderLight(final double x, final double y, final double z, final float tickTime, final MCH_EntityAircraft ac, final MCH_AircraftInfo info) {
        if (!ac.haveSearchLight()) {
            return;
        }
        if (!ac.isSearchLightON()) {
            return;
        }
        Entity entity = ac.getEntityBySeatId(1);
        if (entity != null) {
            ac.lastSearchLightYaw = entity.field_70177_z;
            ac.lastSearchLightPitch = entity.field_70125_A;
        }
        else {
            entity = ac.getEntityBySeatId(0);
            if (entity != null) {
                ac.lastSearchLightYaw = entity.field_70177_z;
                ac.lastSearchLightPitch = entity.field_70125_A;
            }
        }
        final float yaw = ac.lastSearchLightYaw;
        final float pitch = ac.lastSearchLightPitch;
        RenderHelper.func_74518_a();
        GL11.glDisable(3553);
        GL11.glShadeModel(7425);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        GL11.glDisable(3008);
        GL11.glDisable(2884);
        GL11.glDepthMask(false);
        final float rot = ac.prevRotYawWheel + (ac.rotYawWheel - ac.prevRotYawWheel) * tickTime;
        for (final MCH_AircraftInfo.SearchLight sl : info.searchLights) {
            GL11.glPushMatrix();
            GL11.glTranslated(sl.pos.field_72450_a, sl.pos.field_72448_b, sl.pos.field_72449_c);
            if (!sl.fixDir) {
                GL11.glRotatef(yaw - ac.getRotYaw() + sl.yaw, 0.0f, -1.0f, 0.0f);
                GL11.glRotatef(pitch + 90.0f - ac.getRotPitch() + sl.pitch, 1.0f, 0.0f, 0.0f);
            }
            else {
                float stRot = 0.0f;
                if (sl.steering) {
                    stRot = -rot * sl.stRot;
                }
                GL11.glRotatef(0.0f + sl.yaw + stRot, 0.0f, -1.0f, 0.0f);
                GL11.glRotatef(90.0f + sl.pitch, 1.0f, 0.0f, 0.0f);
            }
            final float height = sl.height;
            final float width = sl.width / 2.0f;
            final Tessellator tessellator = Tessellator.field_78398_a;
            tessellator.func_78371_b(6);
            tessellator.func_78384_a(0xFFFFFF & sl.colorStart, sl.colorStart >> 24 & 0xFF);
            tessellator.func_78377_a(0.0, 0.0, 0.0);
            tessellator.func_78384_a(0xFFFFFF & sl.colorEnd, sl.colorEnd >> 24 & 0xFF);
            final int VNUM = 24;
            for (int i = 0; i < 25; ++i) {
                final float angle = (float)(15.0 * i / 180.0 * 3.141592653589793);
                tessellator.func_78377_a((double)(MathHelper.func_76126_a(angle) * width), (double)height, (double)(MathHelper.func_76134_b(angle) * width));
            }
            tessellator.func_78381_a();
            GL11.glPopMatrix();
        }
        GL11.glDepthMask(true);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(3553);
        GL11.glEnable(3008);
        GL11.glBlendFunc(770, 771);
        RenderHelper.func_74519_b();
    }
    
    protected void bindTexture(final String path, final MCH_EntityAircraft ac) {
        if (ac == MCH_ClientCommonTickHandler.ridingAircraft) {
            final int bk = MCH_ClientCommonTickHandler.cameraMode;
            MCH_ClientCommonTickHandler.cameraMode = 0;
            super.func_110776_a(new ResourceLocation(W_MOD.DOMAIN, path));
            MCH_ClientCommonTickHandler.cameraMode = bk;
        }
        else {
            super.func_110776_a(new ResourceLocation(W_MOD.DOMAIN, path));
        }
    }
    
    public void renderRiddenEntity(final MCH_EntityAircraft ac, final float tickTime, final float yaw, final float pitch, final float roll, final float width, final float height) {
        MCH_ClientEventHook.setCancelRender(false);
        GL11.glPushMatrix();
        this.renderEntitySimple(ac, ac.field_70153_n, tickTime, yaw, pitch, roll, width, height);
        for (final MCH_EntitySeat s : ac.getSeats()) {
            if (s != null) {
                this.renderEntitySimple(ac, s.field_70153_n, tickTime, yaw, pitch, roll, width, height);
            }
        }
        GL11.glPopMatrix();
        MCH_ClientEventHook.setCancelRender(true);
    }
    
    public void renderEntitySimple(final MCH_EntityAircraft ac, final Entity entity, final float tickTime, final float yaw, final float pitch, final float roll, final float width, final float height) {
        if (entity != null) {
            final boolean isPilot = ac.isPilot(entity);
            final boolean isClientPlayer = W_Lib.isClientPlayer(entity);
            if (!isClientPlayer || !W_Lib.isFirstPerson() || (isClientPlayer && isPilot && ac.getCameraId() > 0)) {
                GL11.glPushMatrix();
                if (entity.field_70173_aa == 0) {
                    entity.field_70142_S = entity.field_70165_t;
                    entity.field_70137_T = entity.field_70163_u;
                    entity.field_70136_U = entity.field_70161_v;
                }
                final double x = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * tickTime;
                final double y = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * tickTime;
                final double z = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * tickTime;
                final float f1 = entity.field_70126_B + (entity.field_70177_z - entity.field_70126_B) * tickTime;
                int i = entity.func_70070_b(tickTime);
                if (entity.func_70027_ad()) {
                    i = 15728880;
                }
                final int j = i % 65536;
                final int k = i / 65536;
                OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, j / 1.0f, k / 1.0f);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                final double n = x;
                final RenderManager field_76990_c = this.field_76990_c;
                final double dx = n - RenderManager.field_78725_b;
                final double n2 = y;
                final RenderManager field_76990_c2 = this.field_76990_c;
                final double dy = n2 - RenderManager.field_78726_c;
                final double n3 = z;
                final RenderManager field_76990_c3 = this.field_76990_c;
                final double dz = n3 - RenderManager.field_78723_d;
                GL11.glTranslated(dx, dy, dz);
                GL11.glRotatef(yaw, 0.0f, -1.0f, 0.0f);
                GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(roll, 0.0f, 0.0f, 1.0f);
                GL11.glScaled((double)width, (double)height, (double)width);
                GL11.glRotatef(-yaw, 0.0f, -1.0f, 0.0f);
                GL11.glTranslated(-dx, -dy, -dz);
                final boolean bk = MCH_RenderAircraft.renderingEntity;
                MCH_RenderAircraft.renderingEntity = true;
                final Entity ridingEntity = entity.field_70154_o;
                if (!W_Lib.isEntityLivingBase(entity)) {
                    if (!(entity instanceof MCH_IEntityCanRideAircraft)) {
                        entity.field_70154_o = null;
                    }
                }
                final EntityLivingBase entityLiving = (entity instanceof EntityLivingBase) ? entity : null;
                final float bkYaw = 0.0f;
                final float bkPrevYaw = 0.0f;
                float bkPitch = 0.0f;
                float bkPrevPitch = 0.0f;
                if (isPilot && entityLiving != null) {
                    entityLiving.field_70761_aq = ac.getRotYaw();
                    entityLiving.field_70760_ar = ac.getRotYaw();
                    if (ac.getCameraId() > 0) {
                        entityLiving.field_70759_as = ac.getRotYaw();
                        entityLiving.field_70758_at = ac.getRotYaw();
                        bkPitch = entityLiving.field_70125_A;
                        bkPrevPitch = entityLiving.field_70127_C;
                        entityLiving.field_70125_A = ac.getRotPitch();
                        entityLiving.field_70127_C = ac.getRotPitch();
                    }
                }
                W_EntityRenderer.renderEntityWithPosYaw(this.field_76990_c, entity, dx, dy, dz, f1, tickTime, false);
                if (isPilot && entityLiving != null && ac.getCameraId() > 0) {
                    entityLiving.field_70125_A = bkPitch;
                    entityLiving.field_70127_C = bkPrevPitch;
                }
                entity.field_70154_o = ridingEntity;
                MCH_RenderAircraft.renderingEntity = bk;
                GL11.glPopMatrix();
            }
        }
    }
    
    public static void Test_Material(final int light, final float a, final float b, final float c) {
        GL11.glMaterial(1032, light, W_Render.setColorBuffer(a, b, c, 1.0f));
    }
    
    public static void Test_Light(final int light, final float a, final float b, final float c) {
        GL11.glLight(16384, light, W_Render.setColorBuffer(a, b, c, 1.0f));
        GL11.glLight(16385, light, W_Render.setColorBuffer(a, b, c, 1.0f));
    }
    
    public abstract void renderAircraft(final MCH_EntityAircraft p0, final double p1, final double p2, final double p3, final float p4, final float p5, final float p6, final float p7);
    
    public float calcRot(float rot, float prevRot, final float tickTime) {
        rot = MathHelper.func_76142_g(rot);
        prevRot = MathHelper.func_76142_g(prevRot);
        if (rot - prevRot < -180.0f) {
            prevRot -= 360.0f;
        }
        else if (prevRot - rot < -180.0f) {
            prevRot += 360.0f;
        }
        return prevRot + (rot - prevRot) * tickTime;
    }
    
    public void renderDebugHitBox(final MCH_EntityAircraft e, final double x, final double y, final double z, final float yaw, final float pitch) {
        final MCH_Config config = MCH_MOD.config;
        if (MCH_Config.TestMode.prmBool && MCH_RenderAircraft.debugModel != null) {
            GL11.glPushMatrix();
            GL11.glTranslated(x, y, z);
            GL11.glScalef(e.field_70130_N, e.field_70131_O, e.field_70130_N);
            this.bindTexture("textures/hit_box.png");
            MCH_RenderAircraft.debugModel.renderAll();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated(x, y, z);
            for (final MCH_BoundingBox bb : e.extraBoundingBox) {
                GL11.glPushMatrix();
                GL11.glTranslated(bb.rotatedOffset.field_72450_a, bb.rotatedOffset.field_72448_b, bb.rotatedOffset.field_72449_c);
                GL11.glPushMatrix();
                GL11.glScalef(bb.width, bb.height, bb.width);
                this.bindTexture("textures/bounding_box.png");
                MCH_RenderAircraft.debugModel.renderAll();
                GL11.glPopMatrix();
                this.drawHitBoxDetail(bb);
                GL11.glPopMatrix();
            }
            GL11.glPopMatrix();
        }
    }
    
    public void drawHitBoxDetail(final MCH_BoundingBox bb) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final float f1 = 0.080000006f;
        final String s = String.format("%.2f", bb.damegeFactor);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, 0.5f + (float)(bb.offsetY * 0.0 + bb.height), 0.0f);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-this.field_76990_c.field_78735_i, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(this.field_76990_c.field_78732_j, 1.0f, 0.0f, 0.0f);
        GL11.glScalef(-f1, -f1, f1);
        GL11.glDisable(2896);
        GL11.glEnable(3042);
        OpenGlHelper.func_148821_a(770, 771, 1, 0);
        GL11.glDisable(3553);
        final FontRenderer fontrenderer = this.func_76983_a();
        final Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78382_b();
        final int i = fontrenderer.func_78256_a(s) / 2;
        tessellator.func_78369_a(0.0f, 0.0f, 0.0f, 0.4f);
        tessellator.func_78377_a((double)(-i - 1), -1.0, 0.1);
        tessellator.func_78377_a((double)(-i - 1), 8.0, 0.1);
        tessellator.func_78377_a((double)(i + 1), 8.0, 0.1);
        tessellator.func_78377_a((double)(i + 1), -1.0, 0.1);
        tessellator.func_78381_a();
        GL11.glEnable(3553);
        GL11.glDepthMask(false);
        final int color = (bb.damegeFactor < 1.0f) ? 65535 : ((bb.damegeFactor > 1.0f) ? 16711680 : 16777215);
        fontrenderer.func_78276_b(s, -fontrenderer.func_78256_a(s) / 2, 0, 0xC0000000 | color);
        GL11.glDepthMask(true);
        GL11.glEnable(2896);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    public void renderDebugPilotSeat(final MCH_EntityAircraft e, final double x, final double y, final double z, final float yaw, final float pitch, final float roll) {
        final MCH_Config config = MCH_MOD.config;
        if (MCH_Config.TestMode.prmBool && MCH_RenderAircraft.debugModel != null) {
            GL11.glPushMatrix();
            final MCH_SeatInfo seat = e.getSeatInfo(0);
            GL11.glTranslated(x, y, z);
            GL11.glRotatef(yaw, 0.0f, -1.0f, 0.0f);
            GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(roll, 0.0f, 0.0f, 1.0f);
            GL11.glTranslated(seat.pos.field_72450_a, seat.pos.field_72448_b, seat.pos.field_72449_c);
            GL11.glScalef(1.0f, 1.0f, 1.0f);
            this.bindTexture("textures/seat_pilot.png");
            MCH_RenderAircraft.debugModel.renderAll();
            GL11.glPopMatrix();
        }
    }
    
    public static void renderBody(final IModelCustom model) {
        if (model != null) {
            if (model instanceof W_ModelCustom) {
                if (((W_ModelCustom)model).containsPart("$body")) {
                    model.renderPart("$body");
                }
                else {
                    model.renderAll();
                }
            }
            else {
                model.renderAll();
            }
        }
    }
    
    public static void renderPart(final IModelCustom model, final IModelCustom modelBody, final String partName) {
        if (model != null) {
            model.renderAll();
        }
        else if (modelBody instanceof W_ModelCustom && ((W_ModelCustom)modelBody).containsPart("$" + partName)) {
            modelBody.renderPart("$" + partName);
        }
    }
    
    public void renderCommonPart(final MCH_EntityAircraft ac, final MCH_AircraftInfo info, final double x, final double y, final double z, final float tickTime) {
        renderRope(ac, info, x, y, z, tickTime);
        renderWeapon(ac, info, tickTime);
        renderRotPart(ac, info, tickTime);
        renderHatch(ac, info, tickTime);
        renderTrackRoller(ac, info, tickTime);
        renderCrawlerTrack(ac, info, tickTime);
        renderSteeringWheel(ac, info, tickTime);
        renderLightHatch(ac, info, tickTime);
        renderWheel(ac, info, tickTime);
        renderThrottle(ac, info, tickTime);
        renderCamera(ac, info, tickTime);
        renderLandingGear(ac, info, tickTime);
        renderWeaponBay(ac, info, tickTime);
        renderCanopy(ac, info, tickTime);
    }
    
    public static void renderLightHatch(final MCH_EntityAircraft ac, final MCH_AircraftInfo info, final float tickTime) {
        if (info.lightHatchList.size() <= 0) {
            return;
        }
        final float rot = ac.prevRotLightHatch + (ac.rotLightHatch - ac.prevRotLightHatch) * tickTime;
        for (final MCH_AircraftInfo.Hatch t : info.lightHatchList) {
            GL11.glPushMatrix();
            GL11.glTranslated(t.pos.field_72450_a, t.pos.field_72448_b, t.pos.field_72449_c);
            GL11.glRotated((double)(rot * t.maxRot), t.rot.field_72450_a, t.rot.field_72448_b, t.rot.field_72449_c);
            GL11.glTranslated(-t.pos.field_72450_a, -t.pos.field_72448_b, -t.pos.field_72449_c);
            renderPart(t.model, info.model, t.modelName);
            GL11.glPopMatrix();
        }
    }
    
    public static void renderSteeringWheel(final MCH_EntityAircraft ac, final MCH_AircraftInfo info, final float tickTime) {
        if (info.partSteeringWheel.size() <= 0) {
            return;
        }
        final float rot = ac.prevRotYawWheel + (ac.rotYawWheel - ac.prevRotYawWheel) * tickTime;
        for (final MCH_AircraftInfo.PartWheel t : info.partSteeringWheel) {
            GL11.glPushMatrix();
            GL11.glTranslated(t.pos.field_72450_a, t.pos.field_72448_b, t.pos.field_72449_c);
            GL11.glRotated((double)(rot * t.rotDir), t.rot.field_72450_a, t.rot.field_72448_b, t.rot.field_72449_c);
            GL11.glTranslated(-t.pos.field_72450_a, -t.pos.field_72448_b, -t.pos.field_72449_c);
            renderPart(t.model, info.model, t.modelName);
            GL11.glPopMatrix();
        }
    }
    
    public static void renderWheel(final MCH_EntityAircraft ac, final MCH_AircraftInfo info, final float tickTime) {
        if (info.partWheel.size() <= 0) {
            return;
        }
        final float yaw = ac.prevRotYawWheel + (ac.rotYawWheel - ac.prevRotYawWheel) * tickTime;
        for (final MCH_AircraftInfo.PartWheel t : info.partWheel) {
            GL11.glPushMatrix();
            GL11.glTranslated(t.pos2.field_72450_a, t.pos2.field_72448_b, t.pos2.field_72449_c);
            GL11.glRotated((double)(yaw * t.rotDir), t.rot.field_72450_a, t.rot.field_72448_b, t.rot.field_72449_c);
            GL11.glTranslated(-t.pos2.field_72450_a, -t.pos2.field_72448_b, -t.pos2.field_72449_c);
            GL11.glTranslated(t.pos.field_72450_a, t.pos.field_72448_b, t.pos.field_72449_c);
            GL11.glRotatef(ac.prevRotWheel + (ac.rotWheel - ac.prevRotWheel) * tickTime, 1.0f, 0.0f, 0.0f);
            GL11.glTranslated(-t.pos.field_72450_a, -t.pos.field_72448_b, -t.pos.field_72449_c);
            renderPart(t.model, info.model, t.modelName);
            GL11.glPopMatrix();
        }
    }
    
    public static void renderRotPart(final MCH_EntityAircraft ac, final MCH_AircraftInfo info, final float tickTime) {
        if (!ac.haveRotPart()) {
            return;
        }
        for (int i = 0; i < ac.rotPartRotation.length; ++i) {
            float rot = ac.rotPartRotation[i];
            final float prevRot = ac.prevRotPartRotation[i];
            if (prevRot > rot) {
                rot += 360.0f;
            }
            rot = MCH_Lib.smooth(rot, prevRot, tickTime);
            final MCH_AircraftInfo.RotPart h = info.partRotPart.get(i);
            GL11.glPushMatrix();
            GL11.glTranslated(h.pos.field_72450_a, h.pos.field_72448_b, h.pos.field_72449_c);
            GL11.glRotatef(rot, (float)h.rot.field_72450_a, (float)h.rot.field_72448_b, (float)h.rot.field_72449_c);
            GL11.glTranslated(-h.pos.field_72450_a, -h.pos.field_72448_b, -h.pos.field_72449_c);
            renderPart(h.model, info.model, h.modelName);
            GL11.glPopMatrix();
        }
    }
    
    public static void renderWeapon(final MCH_EntityAircraft ac, final MCH_AircraftInfo info, final float tickTime) {
        MCH_WeaponSet beforeWs = null;
        final Entity e = ac.getRiddenByEntity();
        int weaponIndex = 0;
        int cnt = 0;
        for (final MCH_AircraftInfo.PartWeapon w : info.partWeapon) {
            final MCH_WeaponSet ws = ac.getWeaponByName(w.name[0]);
            final boolean onTurret = ws != null && ws.getFirstWeapon().onTurret;
            if (ws != beforeWs) {
                weaponIndex = 0;
                beforeWs = ws;
            }
            float rotYaw = 0.0f;
            float prevYaw = 0.0f;
            float rotPitch = 0.0f;
            float prevPitch = 0.0f;
            if (w.hideGM && W_Lib.isFirstPerson()) {
                if (ws != null) {
                    boolean hide = false;
                    for (final String s : w.name) {
                        if (W_Lib.isClientPlayer(ac.getWeaponUserByWeaponName(s))) {
                            hide = true;
                            break;
                        }
                    }
                    if (hide) {
                        continue;
                    }
                }
                else if (ac.isMountedEntity(MCH_Lib.getClientPlayer())) {
                    continue;
                }
            }
            GL11.glPushMatrix();
            if (w.turret) {
                GL11.glTranslated(info.turretPosition.field_72450_a, info.turretPosition.field_72448_b, info.turretPosition.field_72449_c);
                final float ty = MCH_Lib.smooth(ac.getLastRiderYaw() - ac.getRotYaw(), ac.prevLastRiderYaw - ac.field_70126_B, tickTime);
                GL11.glRotatef(ty, 0.0f, -1.0f, 0.0f);
                GL11.glTranslated(-info.turretPosition.field_72450_a, -info.turretPosition.field_72448_b, -info.turretPosition.field_72449_c);
            }
            GL11.glTranslated(w.pos.field_72450_a, w.pos.field_72448_b, w.pos.field_72449_c);
            if (w.yaw) {
                if (ws != null) {
                    rotYaw = ws.rotationYaw - ws.defaultRotationYaw;
                    prevYaw = ws.prevRotationYaw - ws.defaultRotationYaw;
                }
                else if (e != null) {
                    rotYaw = e.field_70177_z - ac.getRotYaw();
                    prevYaw = e.field_70126_B - ac.field_70126_B;
                }
                else {
                    rotYaw = ac.getLastRiderYaw() - ac.field_70177_z;
                    prevYaw = ac.prevLastRiderYaw - ac.field_70126_B;
                }
                if (rotYaw - prevYaw > 180.0f) {
                    prevYaw += 360.0f;
                }
                else if (rotYaw - prevYaw < -180.0f) {
                    prevYaw -= 360.0f;
                }
                GL11.glRotatef(prevYaw + (rotYaw - prevYaw) * tickTime, 0.0f, -1.0f, 0.0f);
            }
            if (w.turret) {
                float ty = MCH_Lib.smooth(ac.getLastRiderYaw() - ac.getRotYaw(), ac.prevLastRiderYaw - ac.field_70126_B, tickTime);
                ty -= ws.rotationTurretYaw;
                GL11.glRotatef(-ty, 0.0f, -1.0f, 0.0f);
            }
            boolean rev_sign = false;
            if (ws != null && (int)ws.defaultRotationYaw != 0) {
                final float t = MathHelper.func_76142_g(ws.defaultRotationYaw);
                rev_sign = ((t >= 45.0f && t <= 135.0f) || (t <= -45.0f && t >= -135.0f));
                GL11.glRotatef(-ws.defaultRotationYaw, 0.0f, -1.0f, 0.0f);
            }
            if (w.pitch) {
                if (ws != null) {
                    rotPitch = ws.rotationPitch;
                    prevPitch = ws.prevRotationPitch;
                }
                else if (e != null) {
                    rotPitch = e.field_70125_A;
                    prevPitch = e.field_70127_C;
                }
                else {
                    rotPitch = ac.getLastRiderPitch();
                    prevPitch = ac.prevLastRiderPitch;
                }
                if (rev_sign) {
                    rotPitch = -rotPitch;
                    prevPitch = -prevPitch;
                }
                GL11.glRotatef(prevPitch + (rotPitch - prevPitch) * tickTime, 1.0f, 0.0f, 0.0f);
            }
            if (ws != null && w.recoilBuf != 0.0f) {
                MCH_WeaponSet.Recoil r = ws.recoilBuf[0];
                if (w.name.length > 1) {
                    for (final String wnm : w.name) {
                        final MCH_WeaponSet tws = ac.getWeaponByName(wnm);
                        if (tws != null && tws.recoilBuf[0].recoilBuf > r.recoilBuf) {
                            r = tws.recoilBuf[0];
                        }
                    }
                }
                final float recoilBuf = r.prevRecoilBuf + (r.recoilBuf - r.prevRecoilBuf) * tickTime;
                GL11.glTranslated(0.0, 0.0, (double)(w.recoilBuf * recoilBuf));
            }
            if (ws != null) {
                GL11.glRotatef(ws.defaultRotationYaw, 0.0f, -1.0f, 0.0f);
                if (w.rotBarrel) {
                    final float rotBrl = ws.prevRotBarrel + (ws.rotBarrel - ws.prevRotBarrel) * tickTime;
                    GL11.glRotatef(rotBrl, (float)w.rot.field_72450_a, (float)w.rot.field_72448_b, (float)w.rot.field_72449_c);
                }
            }
            GL11.glTranslated(-w.pos.field_72450_a, -w.pos.field_72448_b, -w.pos.field_72449_c);
            if (!w.isMissile || !ac.isWeaponNotCooldown(ws, weaponIndex)) {
                renderPart(w.model, info.model, w.modelName);
                for (final MCH_AircraftInfo.PartWeaponChild wc : w.child) {
                    GL11.glPushMatrix();
                    renderWeaponChild(ac, info, wc, ws, e, tickTime);
                    GL11.glPopMatrix();
                }
            }
            GL11.glPopMatrix();
            ++weaponIndex;
            ++cnt;
        }
    }
    
    public static void renderWeaponChild(final MCH_EntityAircraft ac, final MCH_AircraftInfo info, final MCH_AircraftInfo.PartWeaponChild w, final MCH_WeaponSet ws, final Entity e, final float tickTime) {
        float rotYaw = 0.0f;
        float prevYaw = 0.0f;
        float rotPitch = 0.0f;
        float prevPitch = 0.0f;
        GL11.glTranslated(w.pos.field_72450_a, w.pos.field_72448_b, w.pos.field_72449_c);
        if (w.yaw) {
            if (ws != null) {
                rotYaw = ws.rotationYaw - ws.defaultRotationYaw;
                prevYaw = ws.prevRotationYaw - ws.defaultRotationYaw;
            }
            else if (e != null) {
                rotYaw = e.field_70177_z - ac.getRotYaw();
                prevYaw = e.field_70126_B - ac.field_70126_B;
            }
            else {
                rotYaw = ac.getLastRiderYaw() - ac.field_70177_z;
                prevYaw = ac.prevLastRiderYaw - ac.field_70126_B;
            }
            if (rotYaw - prevYaw > 180.0f) {
                prevYaw += 360.0f;
            }
            else if (rotYaw - prevYaw < -180.0f) {
                prevYaw -= 360.0f;
            }
            GL11.glRotatef(prevYaw + (rotYaw - prevYaw) * tickTime, 0.0f, -1.0f, 0.0f);
        }
        boolean rev_sign = false;
        if (ws != null && (int)ws.defaultRotationYaw != 0) {
            final float t = MathHelper.func_76142_g(ws.defaultRotationYaw);
            rev_sign = ((t >= 45.0f && t <= 135.0f) || (t <= -45.0f && t >= -135.0f));
            GL11.glRotatef(-ws.defaultRotationYaw, 0.0f, -1.0f, 0.0f);
        }
        if (w.pitch) {
            if (ws != null) {
                rotPitch = ws.rotationPitch;
                prevPitch = ws.prevRotationPitch;
            }
            else if (e != null) {
                rotPitch = e.field_70125_A;
                prevPitch = e.field_70127_C;
            }
            else {
                rotPitch = ac.getLastRiderPitch();
                prevPitch = ac.prevLastRiderPitch;
            }
            if (rev_sign) {
                rotPitch = -rotPitch;
                prevPitch = -prevPitch;
            }
            GL11.glRotatef(prevPitch + (rotPitch - prevPitch) * tickTime, 1.0f, 0.0f, 0.0f);
        }
        if (ws != null && w.recoilBuf != 0.0f) {
            MCH_WeaponSet.Recoil r = ws.recoilBuf[0];
            if (w.name.length > 1) {
                for (final String wnm : w.name) {
                    final MCH_WeaponSet tws = ac.getWeaponByName(wnm);
                    if (tws != null && tws.recoilBuf[0].recoilBuf > r.recoilBuf) {
                        r = tws.recoilBuf[0];
                    }
                }
            }
            final float recoilBuf = r.prevRecoilBuf + (r.recoilBuf - r.prevRecoilBuf) * tickTime;
            GL11.glTranslated(0.0, 0.0, (double)(-w.recoilBuf * recoilBuf));
        }
        if (ws != null) {
            GL11.glRotatef(ws.defaultRotationYaw, 0.0f, -1.0f, 0.0f);
        }
        GL11.glTranslated(-w.pos.field_72450_a, -w.pos.field_72448_b, -w.pos.field_72449_c);
        renderPart(w.model, info.model, w.modelName);
    }
    
    public static void renderTrackRoller(final MCH_EntityAircraft ac, final MCH_AircraftInfo info, final float tickTime) {
        if (info.partTrackRoller.size() <= 0) {
            return;
        }
        final float[] rot = ac.rotTrackRoller;
        final float[] prevRot = ac.prevRotTrackRoller;
        for (final MCH_AircraftInfo.TrackRoller t : info.partTrackRoller) {
            GL11.glPushMatrix();
            GL11.glTranslated(t.pos.field_72450_a, t.pos.field_72448_b, t.pos.field_72449_c);
            GL11.glRotatef(prevRot[t.side] + (rot[t.side] - prevRot[t.side]) * tickTime, 1.0f, 0.0f, 0.0f);
            GL11.glTranslated(-t.pos.field_72450_a, -t.pos.field_72448_b, -t.pos.field_72449_c);
            renderPart(t.model, info.model, t.modelName);
            GL11.glPopMatrix();
        }
    }
    
    public static void renderCrawlerTrack(final MCH_EntityAircraft ac, final MCH_AircraftInfo info, final float tickTime) {
        if (info.partCrawlerTrack.size() <= 0) {
            return;
        }
        final int prevWidth = GL11.glGetInteger(2833);
        final Tessellator tessellator = Tessellator.field_78398_a;
        for (final MCH_AircraftInfo.CrawlerTrack c : info.partCrawlerTrack) {
            GL11.glPointSize(c.len * 20.0f);
            final MCH_Config config = MCH_MOD.config;
            if (MCH_Config.TestMode.prmBool) {
                GL11.glDisable(3553);
                GL11.glDisable(3042);
                tessellator.func_78371_b(0);
                for (int i = 0; i < c.cx.length; ++i) {
                    tessellator.func_78370_a((int)(255.0f / c.cx.length * i), 80, 255 - (int)(255.0f / c.cx.length * i), 255);
                    tessellator.func_78377_a((double)c.z, c.cx[i], c.cy[i]);
                }
                tessellator.func_78381_a();
            }
            GL11.glEnable(3553);
            GL11.glEnable(3042);
            final int L = c.lp.size() - 1;
            final double rc = (ac != null) ? ac.rotCrawlerTrack[c.side] : 0.0;
            final double pc = (ac != null) ? ac.prevRotCrawlerTrack[c.side] : 0.0;
            for (int j = 0; j < L; ++j) {
                final MCH_AircraftInfo.CrawlerTrackPrm cp = c.lp.get(j);
                final MCH_AircraftInfo.CrawlerTrackPrm np = c.lp.get((j + 1) % L);
                final double x1 = cp.x;
                final double x2 = np.x;
                final double r1 = cp.r;
                final double y1 = cp.y;
                final double y2 = np.y;
                double r2 = np.r;
                if (r2 - r1 < -180.0) {
                    r2 += 360.0;
                }
                if (r2 - r1 > 180.0) {
                    r2 -= 360.0;
                }
                final double sx = x1 + (x2 - x1) * rc;
                final double sy = y1 + (y2 - y1) * rc;
                final double sr = r1 + (r2 - r1) * rc;
                final double ex = x1 + (x2 - x1) * pc;
                final double ey = y1 + (y2 - y1) * pc;
                final double er = r1 + (r2 - r1) * pc;
                final double x3 = sx + (ex - sx) * pc;
                final double y3 = sy + (ey - sy) * pc;
                final double r3 = sr + (er - sr) * pc;
                GL11.glPushMatrix();
                GL11.glTranslated(0.0, x3, y3);
                GL11.glRotatef((float)r3, -1.0f, 0.0f, 0.0f);
                renderPart(c.model, info.model, c.modelName);
                GL11.glPopMatrix();
            }
        }
        GL11.glEnable(3042);
        GL11.glPointSize((float)prevWidth);
    }
    
    public static void renderHatch(final MCH_EntityAircraft ac, final MCH_AircraftInfo info, final float tickTime) {
        if (!info.haveHatch() || ac.partHatch == null) {
            return;
        }
        final float rot = ac.getHatchRotation();
        final float prevRot = ac.getPrevHatchRotation();
        for (final MCH_AircraftInfo.Hatch h : info.hatchList) {
            GL11.glPushMatrix();
            if (h.isSlide) {
                final float r = ac.partHatch.rotation / ac.partHatch.rotationMax;
                final float pr = ac.partHatch.prevRotation / ac.partHatch.rotationMax;
                final float f = pr + (r - pr) * tickTime;
                GL11.glTranslated(h.pos.field_72450_a * f, h.pos.field_72448_b * f, h.pos.field_72449_c * f);
            }
            else {
                GL11.glTranslated(h.pos.field_72450_a, h.pos.field_72448_b, h.pos.field_72449_c);
                GL11.glRotatef((prevRot + (rot - prevRot) * tickTime) * h.maxRotFactor, (float)h.rot.field_72450_a, (float)h.rot.field_72448_b, (float)h.rot.field_72449_c);
                GL11.glTranslated(-h.pos.field_72450_a, -h.pos.field_72448_b, -h.pos.field_72449_c);
            }
            renderPart(h.model, info.model, h.modelName);
            GL11.glPopMatrix();
        }
    }
    
    public static void renderThrottle(final MCH_EntityAircraft ac, final MCH_AircraftInfo info, final float tickTime) {
        if (!info.havePartThrottle()) {
            return;
        }
        final float throttle = MCH_Lib.smooth((float)ac.getCurrentThrottle(), (float)ac.getPrevCurrentThrottle(), tickTime);
        for (final MCH_AircraftInfo.Throttle h : info.partThrottle) {
            GL11.glPushMatrix();
            GL11.glTranslated(h.pos.field_72450_a, h.pos.field_72448_b, h.pos.field_72449_c);
            GL11.glRotatef(throttle * h.rot2, (float)h.rot.field_72450_a, (float)h.rot.field_72448_b, (float)h.rot.field_72449_c);
            GL11.glTranslated(-h.pos.field_72450_a, -h.pos.field_72448_b, -h.pos.field_72449_c);
            GL11.glTranslated(h.slide.field_72450_a * throttle, h.slide.field_72448_b * throttle, h.slide.field_72449_c * throttle);
            renderPart(h.model, info.model, h.modelName);
            GL11.glPopMatrix();
        }
    }
    
    public static void renderWeaponBay(final MCH_EntityAircraft ac, final MCH_AircraftInfo info, final float tickTime) {
        for (int i = 0; i < info.partWeaponBay.size(); ++i) {
            final MCH_AircraftInfo.WeaponBay w = info.partWeaponBay.get(i);
            final MCH_EntityAircraft.WeaponBay ws = ac.weaponBays[i];
            GL11.glPushMatrix();
            if (w.isSlide) {
                final float r = ws.rot / 90.0f;
                final float pr = ws.prevRot / 90.0f;
                final float f = pr + (r - pr) * tickTime;
                GL11.glTranslated(w.pos.field_72450_a * f, w.pos.field_72448_b * f, w.pos.field_72449_c * f);
            }
            else {
                GL11.glTranslated(w.pos.field_72450_a, w.pos.field_72448_b, w.pos.field_72449_c);
                GL11.glRotatef((ws.prevRot + (ws.rot - ws.prevRot) * tickTime) * w.maxRotFactor, (float)w.rot.field_72450_a, (float)w.rot.field_72448_b, (float)w.rot.field_72449_c);
                GL11.glTranslated(-w.pos.field_72450_a, -w.pos.field_72448_b, -w.pos.field_72449_c);
            }
            renderPart(w.model, info.model, w.modelName);
            GL11.glPopMatrix();
        }
    }
    
    public static void renderCamera(final MCH_EntityAircraft ac, final MCH_AircraftInfo info, final float tickTime) {
        if (!info.havePartCamera()) {
            return;
        }
        final float rotYaw = ac.camera.partRotationYaw;
        final float prevRotYaw = ac.camera.prevPartRotationYaw;
        final float rotPitch = ac.camera.partRotationPitch;
        final float prevRotPitch = ac.camera.prevPartRotationPitch;
        final float yaw = prevRotYaw + (rotYaw - prevRotYaw) * tickTime - ac.getRotYaw();
        final float pitch = prevRotPitch + (rotPitch - prevRotPitch) * tickTime - ac.getRotPitch();
        for (final MCH_AircraftInfo.Camera c : info.cameraList) {
            GL11.glPushMatrix();
            GL11.glTranslated(c.pos.field_72450_a, c.pos.field_72448_b, c.pos.field_72449_c);
            if (c.yawSync) {
                GL11.glRotatef(yaw, 0.0f, -1.0f, 0.0f);
            }
            if (c.pitchSync) {
                GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
            }
            GL11.glTranslated(-c.pos.field_72450_a, -c.pos.field_72448_b, -c.pos.field_72449_c);
            renderPart(c.model, info.model, c.modelName);
            GL11.glPopMatrix();
        }
    }
    
    public static void renderCanopy(final MCH_EntityAircraft ac, final MCH_AircraftInfo info, final float tickTime) {
        if (info.haveCanopy() && ac.partCanopy != null) {
            final float rot = ac.getCanopyRotation();
            final float prevRot = ac.getPrevCanopyRotation();
            for (final MCH_AircraftInfo.Canopy c : info.canopyList) {
                GL11.glPushMatrix();
                if (c.isSlide) {
                    final float r = ac.partCanopy.rotation / ac.partCanopy.rotationMax;
                    final float pr = ac.partCanopy.prevRotation / ac.partCanopy.rotationMax;
                    final float f = pr + (r - pr) * tickTime;
                    GL11.glTranslated(c.pos.field_72450_a * f, c.pos.field_72448_b * f, c.pos.field_72449_c * f);
                }
                else {
                    GL11.glTranslated(c.pos.field_72450_a, c.pos.field_72448_b, c.pos.field_72449_c);
                    GL11.glRotatef((prevRot + (rot - prevRot) * tickTime) * c.maxRotFactor, (float)c.rot.field_72450_a, (float)c.rot.field_72448_b, (float)c.rot.field_72449_c);
                    GL11.glTranslated(-c.pos.field_72450_a, -c.pos.field_72448_b, -c.pos.field_72449_c);
                }
                renderPart(c.model, info.model, c.modelName);
                GL11.glPopMatrix();
            }
        }
    }
    
    public static void renderLandingGear(final MCH_EntityAircraft ac, final MCH_AircraftInfo info, final float tickTime) {
        if (info.haveLandingGear() && ac.partLandingGear != null) {
            final float rot = ac.getLandingGearRotation();
            final float prevRot = ac.getPrevLandingGearRotation();
            final float revR = 90.0f - rot;
            final float revPr = 90.0f - prevRot;
            final float rot2 = prevRot + (rot - prevRot) * tickTime;
            final float rot1Rev = revPr + (revR - revPr) * tickTime;
            float rotHatch = 90.0f * MathHelper.func_76126_a(rot2 * 2.0f * 3.1415927f / 180.0f) * 3.0f;
            if (rotHatch > 90.0f) {
                rotHatch = 90.0f;
            }
            for (final MCH_AircraftInfo.LandingGear n : info.landingGear) {
                GL11.glPushMatrix();
                GL11.glTranslated(n.pos.field_72450_a, n.pos.field_72448_b, n.pos.field_72449_c);
                if (!n.reverse) {
                    if (!n.hatch) {
                        GL11.glRotatef(rot2 * n.maxRotFactor, (float)n.rot.field_72450_a, (float)n.rot.field_72448_b, (float)n.rot.field_72449_c);
                    }
                    else {
                        GL11.glRotatef(rotHatch * n.maxRotFactor, (float)n.rot.field_72450_a, (float)n.rot.field_72448_b, (float)n.rot.field_72449_c);
                    }
                }
                else {
                    GL11.glRotatef(rot1Rev * n.maxRotFactor, (float)n.rot.field_72450_a, (float)n.rot.field_72448_b, (float)n.rot.field_72449_c);
                }
                if (n.enableRot2) {
                    if (!n.reverse) {
                        GL11.glRotatef(rot2 * n.maxRotFactor2, (float)n.rot2.field_72450_a, (float)n.rot2.field_72448_b, (float)n.rot2.field_72449_c);
                    }
                    else {
                        GL11.glRotatef(rot1Rev * n.maxRotFactor2, (float)n.rot2.field_72450_a, (float)n.rot2.field_72448_b, (float)n.rot2.field_72449_c);
                    }
                }
                GL11.glTranslated(-n.pos.field_72450_a, -n.pos.field_72448_b, -n.pos.field_72449_c);
                if (n.slide != null) {
                    float f = rot / 90.0f;
                    if (n.reverse) {
                        f = 1.0f - f;
                    }
                    GL11.glTranslated(f * n.slide.field_72450_a, f * n.slide.field_72448_b, f * n.slide.field_72449_c);
                }
                renderPart(n.model, info.model, n.modelName);
                GL11.glPopMatrix();
            }
        }
    }
    
    public static void renderEntityMarker(final Entity entity) {
        final Entity player = (Entity)Minecraft.func_71410_x().field_71439_g;
        if (player == null) {
            return;
        }
        if (W_Entity.isEqual(player, entity)) {
            return;
        }
        MCH_EntityAircraft ac = null;
        if (player.field_70154_o instanceof MCH_EntityAircraft) {
            ac = (MCH_EntityAircraft)player.field_70154_o;
        }
        else if (player.field_70154_o instanceof MCH_EntitySeat) {
            ac = ((MCH_EntitySeat)player.field_70154_o).getParent();
        }
        else if (player.field_70154_o instanceof MCH_EntityUavStation) {
            ac = ((MCH_EntityUavStation)player.field_70154_o).getControlAircract();
        }
        if (ac == null) {
            return;
        }
        if (W_Entity.isEqual(ac, entity)) {
            return;
        }
        final MCH_WeaponGuidanceSystem gs = ac.getCurrentWeapon(player).getCurrentWeapon().getGuidanceSystem();
        if (gs == null || !gs.canLockEntity(entity)) {
            return;
        }
        final RenderManager rm = RenderManager.field_78727_a;
        final double dist = entity.func_70068_e((Entity)rm.field_78734_h);
        final double x = entity.field_70165_t - RenderManager.field_78725_b;
        final double y = entity.field_70163_u - RenderManager.field_78726_c;
        final double z = entity.field_70161_v - RenderManager.field_78723_d;
        if (dist < 10000.0) {
            final float scl = 0.02666667f;
            GL11.glPushMatrix();
            GL11.glTranslatef((float)x, (float)y + entity.field_70131_O + 0.5f, (float)z);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-rm.field_78735_i, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(rm.field_78732_j, 1.0f, 0.0f, 0.0f);
            GL11.glScalef(-0.02666667f, -0.02666667f, 0.02666667f);
            GL11.glDisable(2896);
            GL11.glTranslatef(0.0f, 9.374999f, 0.0f);
            GL11.glDepthMask(false);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3553);
            final int prevWidth = GL11.glGetInteger(2849);
            float size = Math.max(entity.field_70130_N, entity.field_70131_O) * 20.0f;
            if (entity instanceof MCH_EntityAircraft) {
                size *= 2.0f;
            }
            final Tessellator tessellator = Tessellator.field_78398_a;
            tessellator.func_78371_b(2);
            tessellator.func_78380_c(240);
            final boolean isLockEntity = gs.isLockingEntity(entity);
            if (isLockEntity) {
                GL11.glLineWidth(MCH_Gui.scaleFactor * 1.5f);
                tessellator.func_78369_a(1.0f, 0.0f, 0.0f, 1.0f);
            }
            else {
                GL11.glLineWidth((float)MCH_Gui.scaleFactor);
                tessellator.func_78369_a(1.0f, 0.3f, 0.0f, 8.0f);
            }
            tessellator.func_78377_a((double)(-size - 1.0f), 0.0, 0.0);
            tessellator.func_78377_a((double)(-size - 1.0f), (double)(size * 2.0f), 0.0);
            tessellator.func_78377_a((double)(size + 1.0f), (double)(size * 2.0f), 0.0);
            tessellator.func_78377_a((double)(size + 1.0f), 0.0, 0.0);
            tessellator.func_78381_a();
            GL11.glPopMatrix();
            if (!ac.isUAV() && isLockEntity && Minecraft.func_71410_x().field_71474_y.field_74320_O == 0) {
                GL11.glPushMatrix();
                tessellator.func_78371_b(1);
                GL11.glLineWidth(1.0f);
                tessellator.func_78369_a(1.0f, 0.0f, 0.0f, 1.0f);
                tessellator.func_78377_a(x, y + entity.field_70131_O / 2.0f, z);
                tessellator.func_78377_a(ac.field_70142_S - RenderManager.field_78725_b, ac.field_70137_T - RenderManager.field_78726_c - 1.0, ac.field_70136_U - RenderManager.field_78723_d);
                tessellator.func_78380_c(240);
                tessellator.func_78381_a();
                GL11.glPopMatrix();
            }
            GL11.glLineWidth((float)prevWidth);
            GL11.glEnable(3553);
            GL11.glDepthMask(true);
            GL11.glEnable(2896);
            GL11.glDisable(3042);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    public static void renderRope(final MCH_EntityAircraft ac, final MCH_AircraftInfo info, final double x, final double y, final double z, final float tickTime) {
        GL11.glPushMatrix();
        final Tessellator tessellator = Tessellator.field_78398_a;
        if (ac.isRepelling()) {
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            for (int i = 0; i < info.repellingHooks.size(); ++i) {
                tessellator.func_78371_b(3);
                tessellator.func_78378_d(0);
                tessellator.func_78377_a(info.repellingHooks.get(i).pos.field_72450_a, info.repellingHooks.get(i).pos.field_72448_b, info.repellingHooks.get(i).pos.field_72449_c);
                tessellator.func_78377_a(info.repellingHooks.get(i).pos.field_72450_a, info.repellingHooks.get(i).pos.field_72448_b + ac.ropesLength, info.repellingHooks.get(i).pos.field_72449_c);
                tessellator.func_78381_a();
            }
            GL11.glEnable(2896);
            GL11.glEnable(3553);
        }
        GL11.glPopMatrix();
    }
    
    static {
        MCH_RenderAircraft.renderingEntity = false;
        MCH_RenderAircraft.debugModel = null;
    }
}
