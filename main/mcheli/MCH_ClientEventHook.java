package mcheli;

import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.player.*;
import mcheli.wrapper.*;
import net.minecraft.client.renderer.entity.*;
import mcheli.aircraft.*;
import mcheli.multiplay.*;
import mcheli.lweapon.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.world.*;
import net.minecraftforge.event.entity.*;
import mcheli.tool.rangefinder.*;
import mcheli.particles.*;
import java.util.*;

public class MCH_ClientEventHook extends W_ClientEventHook
{
    MCH_TextureManagerDummy dummyTextureManager;
    public static List<MCH_EntityAircraft> haveSearchLightAircraft;
    private static final ResourceLocation ir_strobe;
    private static boolean cancelRender;
    
    public MCH_ClientEventHook() {
        this.dummyTextureManager = null;
    }
    
    @Override
    public void renderLivingEventSpecialsPre(final RenderLivingEvent.Specials.Pre event) {
        final MCH_Config config = MCH_MOD.config;
        if (MCH_Config.DisableRenderLivingSpecials.prmBool) {
            final MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)Minecraft.func_71410_x().field_71439_g);
            if (ac != null && ac.isMountedEntity((Entity)event.entity)) {
                event.setCanceled(true);
            }
        }
    }
    
    @Override
    public void renderLivingEventSpecialsPost(final RenderLivingEvent.Specials.Post event) {
    }
    
    private void renderIRStrobe(final EntityLivingBase entity, final RenderLivingEvent.Specials.Post event) {
        final int cm = MCH_ClientCommonTickHandler.cameraMode;
        if (cm == 0) {
            return;
        }
        final int ticks = entity.field_70173_aa % 20;
        if (ticks >= 4) {
            return;
        }
        final float alpha = (ticks == 2 || ticks == 1) ? 1.0f : 0.5f;
        final EntityPlayer player = (EntityPlayer)Minecraft.func_71410_x().field_71439_g;
        if (player == null) {
            return;
        }
        if (!player.func_142014_c(entity)) {
            return;
        }
        final int j = 240;
        final int k = 240;
        OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, j / 1.0f, k / 1.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final float f1 = 0.080000006f;
        GL11.glPushMatrix();
        GL11.glTranslated(event.x, event.y + (float)(entity.field_70131_O * 0.75), event.z);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-RenderManager.field_78727_a.field_78735_i, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(RenderManager.field_78727_a.field_78732_j, 1.0f, 0.0f, 0.0f);
        GL11.glScalef(-f1, -f1, f1);
        GL11.glEnable(3042);
        OpenGlHelper.func_148821_a(770, 771, 1, 0);
        GL11.glEnable(3553);
        RenderManager.field_78727_a.field_78724_e.func_110577_a(MCH_ClientEventHook.ir_strobe);
        GL11.glAlphaFunc(516, 0.003921569f);
        final Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78382_b();
        tessellator.func_78369_a(1.0f, 1.0f, 1.0f, alpha * ((cm == 1) ? 0.9f : 0.5f));
        final int i = (int)Math.max(entity.field_70130_N, entity.field_70131_O) * 20;
        tessellator.func_78374_a((double)(-i), (double)(-i), 0.1, 0.0, 0.0);
        tessellator.func_78374_a((double)(-i), (double)i, 0.1, 0.0, 1.0);
        tessellator.func_78374_a((double)i, (double)i, 0.1, 1.0, 1.0);
        tessellator.func_78374_a((double)i, (double)(-i), 0.1, 1.0, 0.0);
        tessellator.func_78381_a();
        GL11.glEnable(2896);
        GL11.glPopMatrix();
    }
    
    @Override
    public void mouseEvent(final MouseEvent event) {
        if (MCH_ClientTickHandlerBase.updateMouseWheel(event.dwheel)) {
            event.setCanceled(true);
        }
    }
    
    public static void setCancelRender(final boolean cancel) {
        MCH_ClientEventHook.cancelRender = cancel;
    }
    
    @Override
    public void renderLivingEventPre(final RenderLivingEvent.Pre event) {
        for (final MCH_EntityAircraft ac : MCH_ClientEventHook.haveSearchLightAircraft) {
            OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, ac.getSearchLightValue((Entity)event.entity), 240.0f);
        }
        final MCH_Config config = MCH_MOD.config;
        if (MCH_Config.EnableModEntityRender.prmBool && MCH_ClientEventHook.cancelRender && (event.entity.field_70154_o instanceof MCH_EntityAircraft || event.entity.field_70154_o instanceof MCH_EntitySeat)) {
            event.setCanceled(true);
            return;
        }
        final MCH_Config config2 = MCH_MOD.config;
        if (MCH_Config.EnableReplaceTextureManager.prmBool) {
            final RenderManager rm = W_Reflection.getRenderManager((Render)event.renderer);
            if (rm != null && !(rm.field_78724_e instanceof MCH_TextureManagerDummy)) {
                if (this.dummyTextureManager == null) {
                    this.dummyTextureManager = new MCH_TextureManagerDummy(rm.field_78724_e);
                }
                rm.field_78724_e = this.dummyTextureManager;
            }
        }
    }
    
    @Override
    public void renderLivingEventPost(final RenderLivingEvent.Post event) {
        MCH_RenderAircraft.renderEntityMarker((Entity)event.entity);
        MCH_GuiTargetMarker.addMarkEntityPos(2, (Entity)event.entity, event.x, event.y + event.entity.field_70131_O + 0.5, event.z);
        MCH_ClientLightWeaponTickHandler.markEntity((Entity)event.entity, event.x, event.y + event.entity.field_70131_O / 2.0f, event.z);
    }
    
    @Override
    public void renderPlayerPre(final RenderPlayerEvent.Pre event) {
        if (event.entity == null) {
            return;
        }
        if (event.entity.field_70154_o instanceof MCH_EntityAircraft) {
            final MCH_EntityAircraft v = (MCH_EntityAircraft)event.entity.field_70154_o;
            if (v.getAcInfo() != null && v.getAcInfo().hideEntity) {
                event.setCanceled(true);
            }
        }
    }
    
    @Override
    public void renderPlayerPost(final RenderPlayerEvent.Post event) {
    }
    
    @Override
    public void worldEventUnload(final WorldEvent.Unload event) {
        MCH_ViewEntityDummy.onUnloadWorld();
    }
    
    @Override
    public void entityJoinWorldEvent(final EntityJoinWorldEvent event) {
        if (event.entity.func_70028_i(MCH_Lib.getClientPlayer())) {
            MCH_Lib.DbgLog(true, "MCH_ClientEventHook.entityJoinWorldEvent : " + event.entity, new Object[0]);
            MCH_ItemRangeFinder.mode = (Minecraft.func_71410_x().func_71356_B() ? 1 : 0);
            MCH_ParticlesUtil.clearMarkPoint();
        }
    }
    
    static {
        MCH_ClientEventHook.haveSearchLightAircraft = new ArrayList<MCH_EntityAircraft>();
        ir_strobe = new ResourceLocation("mcheli", "textures/ir_strobe.png");
        MCH_ClientEventHook.cancelRender = true;
    }
}
