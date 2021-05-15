package mcheli;

import cpw.mods.fml.relauncher.*;
import mcheli.gui.*;
import net.minecraft.client.*;
import mcheli.tool.rangefinder.*;
import mcheli.command.*;
import mcheli.lweapon.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.entity.*;
import mcheli.tool.*;
import mcheli.gltd.*;
import mcheli.helicopter.*;
import mcheli.plane.*;
import mcheli.tank.*;
import mcheli.uav.*;
import mcheli.vehicle.*;
import net.minecraft.util.*;
import mcheli.wrapper.*;
import java.util.*;
import net.minecraft.item.*;
import mcheli.aircraft.*;
import mcheli.weapon.*;
import net.minecraft.client.gui.*;
import mcheli.multiplay.*;

@SideOnly(Side.CLIENT)
public class MCH_ClientCommonTickHandler extends W_TickHandler
{
    public static MCH_ClientCommonTickHandler instance;
    public MCH_GuiCommon gui_Common;
    public MCH_Gui gui_Heli;
    public MCH_Gui gui_Plane;
    public MCH_Gui gui_Tank;
    public MCH_Gui gui_GLTD;
    public MCH_Gui gui_Vehicle;
    public MCH_Gui gui_LWeapon;
    public MCH_Gui gui_Wrench;
    public MCH_Gui gui_EMarker;
    public MCH_Gui gui_RngFndr;
    public MCH_Gui gui_Title;
    public MCH_Gui[] guis;
    public MCH_Gui[] guiTicks;
    public MCH_ClientTickHandlerBase[] ticks;
    public MCH_Key[] Keys;
    public MCH_Key KeyCamDistUp;
    public MCH_Key KeyCamDistDown;
    public MCH_Key KeyScoreboard;
    public MCH_Key KeyMultiplayManager;
    public static int cameraMode;
    public static MCH_EntityAircraft ridingAircraft;
    public static boolean isDrawScoreboard;
    public static int sendLDCount;
    public static boolean isLocked;
    public static int lockedSoundCount;
    int debugcnt;
    private static double prevMouseDeltaX;
    private static double prevMouseDeltaY;
    private static double mouseDeltaX;
    private static double mouseDeltaY;
    private static double mouseRollDeltaX;
    private static double mouseRollDeltaY;
    private static boolean isRideAircraft;
    private static float prevTick;
    
    public MCH_ClientCommonTickHandler(final Minecraft minecraft, final MCH_Config config) {
        super(minecraft);
        this.gui_Common = new MCH_GuiCommon(minecraft);
        this.gui_Heli = new MCH_GuiHeli(minecraft);
        this.gui_Plane = new MCP_GuiPlane(minecraft);
        this.gui_Tank = new MCH_GuiTank(minecraft);
        this.gui_GLTD = new MCH_GuiGLTD(minecraft);
        this.gui_Vehicle = new MCH_GuiVehicle(minecraft);
        this.gui_LWeapon = new MCH_GuiLightWeapon(minecraft);
        this.gui_Wrench = new MCH_GuiWrench(minecraft);
        this.gui_RngFndr = new MCH_GuiRangeFinder(minecraft);
        this.gui_EMarker = new MCH_GuiTargetMarker(minecraft);
        this.gui_Title = new MCH_GuiTitle(minecraft);
        this.guis = new MCH_Gui[] { this.gui_RngFndr, this.gui_LWeapon, this.gui_Heli, this.gui_Plane, this.gui_Tank, this.gui_GLTD, this.gui_Vehicle };
        this.guiTicks = new MCH_Gui[] { this.gui_Common, this.gui_Heli, this.gui_Plane, this.gui_Tank, this.gui_GLTD, this.gui_Vehicle, this.gui_LWeapon, this.gui_Wrench, this.gui_RngFndr, this.gui_EMarker, this.gui_Title };
        this.ticks = new MCH_ClientTickHandlerBase[] { new MCH_ClientHeliTickHandler(minecraft, config), new MCP_ClientPlaneTickHandler(minecraft, config), new MCH_ClientTankTickHandler(minecraft, config), new MCH_ClientGLTDTickHandler(minecraft, config), new MCH_ClientVehicleTickHandler(minecraft, config), new MCH_ClientLightWeaponTickHandler(minecraft, config), new MCH_ClientSeatTickHandler(minecraft, config), new MCH_ClientToolTickHandler(minecraft, config) };
        this.updatekeybind(config);
    }
    
    public void updatekeybind(final MCH_Config config) {
        this.KeyCamDistUp = new MCH_Key(MCH_Config.KeyCameraDistUp.prmInt);
        this.KeyCamDistDown = new MCH_Key(MCH_Config.KeyCameraDistDown.prmInt);
        this.KeyScoreboard = new MCH_Key(MCH_Config.KeyScoreboard.prmInt);
        this.KeyMultiplayManager = new MCH_Key(MCH_Config.KeyMultiplayManager.prmInt);
        this.Keys = new MCH_Key[] { this.KeyCamDistUp, this.KeyCamDistDown, this.KeyScoreboard, this.KeyMultiplayManager };
        for (final MCH_ClientTickHandlerBase t : this.ticks) {
            t.updateKeybind(config);
        }
    }
    
    public String getLabel() {
        return null;
    }
    
    public void onTick() {
        MCH_ClientTickHandlerBase.initRotLimit();
        for (final MCH_Key k : this.Keys) {
            k.update();
        }
        final EntityPlayer player = (EntityPlayer)this.mc.field_71439_g;
        Label_0208: {
            if (player != null && this.mc.field_71462_r == null) {
                if (MCH_ServerSettings.enableCamDistChange && (this.KeyCamDistUp.isKeyDown() || this.KeyCamDistDown.isKeyDown())) {
                    int camdist = (int)W_Reflection.getThirdPersonDistance();
                    if (this.KeyCamDistUp.isKeyDown() && camdist < 60) {
                        camdist += 4;
                        if (camdist > 60) {
                            camdist = 60;
                        }
                        W_Reflection.setThirdPersonDistance(camdist);
                    }
                    else if (this.KeyCamDistDown.isKeyDown()) {
                        camdist -= 4;
                        if (camdist < 4) {
                            camdist = 4;
                        }
                        W_Reflection.setThirdPersonDistance(camdist);
                    }
                }
                if (this.mc.field_71462_r == null) {
                    if (this.mc.func_71356_B()) {
                        final MCH_Config config = MCH_MOD.config;
                        if (!MCH_Config.DebugLog) {
                            break Label_0208;
                        }
                    }
                    MCH_ClientCommonTickHandler.isDrawScoreboard = this.KeyScoreboard.isKeyPress();
                    if (!MCH_ClientCommonTickHandler.isDrawScoreboard && this.KeyMultiplayManager.isKeyDown()) {
                        MCH_PacketIndOpenScreen.send(5);
                    }
                }
            }
        }
        if (MCH_ClientCommonTickHandler.sendLDCount < 10) {
            ++MCH_ClientCommonTickHandler.sendLDCount;
        }
        else {
            MCH_MultiplayClient.sendImageData();
            MCH_ClientCommonTickHandler.sendLDCount = 0;
        }
        final boolean inOtherGui = this.mc.field_71462_r != null;
        for (final MCH_ClientTickHandlerBase t : this.ticks) {
            t.onTick(inOtherGui);
        }
        for (final MCH_Gui g : this.guiTicks) {
            g.onTick();
        }
        final MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player);
        if (player != null && ac != null && !ac.isDestroyed()) {
            if (MCH_ClientCommonTickHandler.isLocked && MCH_ClientCommonTickHandler.lockedSoundCount == 0) {
                MCH_ClientCommonTickHandler.isLocked = false;
                MCH_ClientCommonTickHandler.lockedSoundCount = 20;
                MCH_ClientTickHandlerBase.playSound("locked");
            }
        }
        else {
            MCH_ClientCommonTickHandler.lockedSoundCount = 0;
            MCH_ClientCommonTickHandler.isLocked = false;
        }
        if (MCH_ClientCommonTickHandler.lockedSoundCount > 0) {
            --MCH_ClientCommonTickHandler.lockedSoundCount;
        }
    }
    
    @Override
    public void onTickPre() {
        if (this.mc.field_71439_g != null && this.mc.field_71441_e != null) {
            this.onTick();
        }
    }
    
    @Override
    public void onTickPost() {
        if (this.mc.field_71439_g != null && this.mc.field_71441_e != null) {
            MCH_GuiTargetMarker.onClientTick();
        }
    }
    
    public static double getCurrentStickX() {
        return MCH_ClientCommonTickHandler.mouseRollDeltaX;
    }
    
    public static double getCurrentStickY() {
        double inv = 1.0;
        if (Minecraft.func_71410_x().field_71474_y.field_74338_d) {
            inv = -inv;
        }
        final MCH_Config config = MCH_MOD.config;
        if (MCH_Config.InvertMouse.prmBool) {
            inv = -inv;
        }
        return MCH_ClientCommonTickHandler.mouseRollDeltaY * inv;
    }
    
    public static double getMaxStickLength() {
        return 40.0;
    }
    
    public void updateMouseDelta(final boolean stickMode, final float partialTicks) {
        MCH_ClientCommonTickHandler.prevMouseDeltaX = MCH_ClientCommonTickHandler.mouseDeltaX;
        MCH_ClientCommonTickHandler.prevMouseDeltaY = MCH_ClientCommonTickHandler.mouseDeltaY;
        MCH_ClientCommonTickHandler.mouseDeltaX = 0.0;
        MCH_ClientCommonTickHandler.mouseDeltaY = 0.0;
        if (this.mc.field_71415_G && Display.isActive() && this.mc.field_71462_r == null) {
            if (stickMode) {
                if (Math.abs(MCH_ClientCommonTickHandler.mouseRollDeltaX) < getMaxStickLength() * 0.2) {
                    MCH_ClientCommonTickHandler.mouseRollDeltaX *= 1.0f - 0.15f * partialTicks;
                }
                if (Math.abs(MCH_ClientCommonTickHandler.mouseRollDeltaY) < getMaxStickLength() * 0.2) {
                    MCH_ClientCommonTickHandler.mouseRollDeltaY *= 1.0f - 0.15f * partialTicks;
                }
            }
            this.mc.field_71417_B.func_74374_c();
            final float f1 = this.mc.field_71474_y.field_74341_c * 0.6f + 0.2f;
            final float f2 = f1 * f1 * f1 * 8.0f;
            final MCH_Config config = MCH_MOD.config;
            final double ms = MCH_Config.MouseSensitivity.prmDouble * 0.1;
            MCH_ClientCommonTickHandler.mouseDeltaX = ms * this.mc.field_71417_B.field_74377_a * f2;
            MCH_ClientCommonTickHandler.mouseDeltaY = ms * this.mc.field_71417_B.field_74375_b * f2;
            byte inv = 1;
            if (this.mc.field_71474_y.field_74338_d) {
                inv = -1;
            }
            final MCH_Config config2 = MCH_MOD.config;
            if (MCH_Config.InvertMouse.prmBool) {
                inv *= -1;
            }
            MCH_ClientCommonTickHandler.mouseRollDeltaX += MCH_ClientCommonTickHandler.mouseDeltaX;
            MCH_ClientCommonTickHandler.mouseRollDeltaY += MCH_ClientCommonTickHandler.mouseDeltaY * inv;
            double dist = MCH_ClientCommonTickHandler.mouseRollDeltaX * MCH_ClientCommonTickHandler.mouseRollDeltaX + MCH_ClientCommonTickHandler.mouseRollDeltaY * MCH_ClientCommonTickHandler.mouseRollDeltaY;
            if (dist > 1.0) {
                double d;
                dist = (d = MathHelper.func_76133_a(dist));
                if (d > getMaxStickLength()) {
                    d = getMaxStickLength();
                }
                MCH_ClientCommonTickHandler.mouseRollDeltaX /= dist;
                MCH_ClientCommonTickHandler.mouseRollDeltaY /= dist;
                MCH_ClientCommonTickHandler.mouseRollDeltaX *= d;
                MCH_ClientCommonTickHandler.mouseRollDeltaY *= d;
            }
        }
    }
    
    @Override
    public void onRenderTickPre(final float partialTicks) {
        MCH_GuiTargetMarker.clearMarkEntityPos();
        if (!MCH_ServerSettings.enableDebugBoundingBox) {
            RenderManager.field_85095_o = false;
        }
        MCH_ClientEventHook.haveSearchLightAircraft.clear();
        if (this.mc != null && this.mc.field_71441_e != null) {
            for (final Object o : Minecraft.func_71410_x().field_71441_e.field_72996_f) {
                if (o instanceof MCH_EntityAircraft && ((MCH_EntityAircraft)o).haveSearchLight()) {
                    MCH_ClientEventHook.haveSearchLightAircraft.add((MCH_EntityAircraft)o);
                }
            }
        }
        if (W_McClient.isGamePaused()) {
            return;
        }
        final EntityPlayer player = (EntityPlayer)this.mc.field_71439_g;
        if (player == null) {
            return;
        }
        final ItemStack currentItemstack = player.func_71045_bC();
        if (currentItemstack != null && currentItemstack.func_77973_b() instanceof MCH_ItemWrench && player.func_71052_bv() > 0) {
            W_Reflection.setItemRendererProgress(1.0f);
        }
        MCH_ClientCommonTickHandler.ridingAircraft = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player);
        if (MCH_ClientCommonTickHandler.ridingAircraft != null) {
            MCH_ClientCommonTickHandler.cameraMode = MCH_ClientCommonTickHandler.ridingAircraft.getCameraMode(player);
        }
        else if (player.field_70154_o instanceof MCH_EntityGLTD) {
            final MCH_EntityGLTD gltd = (MCH_EntityGLTD)player.field_70154_o;
            MCH_ClientCommonTickHandler.cameraMode = gltd.camera.getMode(0);
        }
        else {
            MCH_ClientCommonTickHandler.cameraMode = 0;
        }
        MCH_EntityAircraft ac = null;
        if (player.field_70154_o instanceof MCH_EntityHeli || player.field_70154_o instanceof MCP_EntityPlane || player.field_70154_o instanceof MCH_EntityTank) {
            ac = (MCH_EntityAircraft)player.field_70154_o;
        }
        else if (player.field_70154_o instanceof MCH_EntityUavStation) {
            ac = ((MCH_EntityUavStation)player.field_70154_o).getControlAircract();
        }
        else if (player.field_70154_o instanceof MCH_EntityVehicle) {
            final MCH_EntityAircraft vehicle = (MCH_EntityAircraft)player.field_70154_o;
            vehicle.setupAllRiderRenderPosition(partialTicks, player);
        }
        boolean stickMode = false;
        if (ac instanceof MCH_EntityHeli) {
            final MCH_Config config = MCH_MOD.config;
            stickMode = MCH_Config.MouseControlStickModeHeli.prmBool;
        }
        if (ac instanceof MCP_EntityPlane) {
            final MCH_Config config2 = MCH_MOD.config;
            stickMode = MCH_Config.MouseControlStickModePlane.prmBool;
        }
        for (int i = 0; i < 10 && MCH_ClientCommonTickHandler.prevTick > partialTicks; ++i) {
            --MCH_ClientCommonTickHandler.prevTick;
        }
        if (ac != null && ac.canMouseRot()) {
            if (!MCH_ClientCommonTickHandler.isRideAircraft) {
                ac.onInteractFirst(player);
            }
            MCH_ClientCommonTickHandler.isRideAircraft = true;
            this.updateMouseDelta(stickMode, partialTicks);
            boolean fixRot = false;
            float fixYaw = 0.0f;
            float fixPitch = 0.0f;
            final MCH_SeatInfo seatInfo = ac.getSeatInfo((Entity)player);
            if (seatInfo != null && seatInfo.fixRot && ac.getIsGunnerMode((Entity)player) && !ac.isGunnerLookMode(player)) {
                fixRot = true;
                fixYaw = seatInfo.fixYaw;
                fixPitch = seatInfo.fixPitch;
                MCH_ClientCommonTickHandler.mouseRollDeltaX *= 0.0;
                MCH_ClientCommonTickHandler.mouseRollDeltaY *= 0.0;
                MCH_ClientCommonTickHandler.mouseDeltaX *= 0.0;
                MCH_ClientCommonTickHandler.mouseDeltaY *= 0.0;
            }
            else if (ac.isPilot((Entity)player)) {
                final MCH_AircraftInfo.CameraPosition cp = ac.getCameraPosInfo();
                if (cp != null) {
                    fixYaw = cp.yaw;
                    fixPitch = cp.pitch;
                }
            }
            if (ac.getAcInfo() == null) {
                player.func_70082_c((float)MCH_ClientCommonTickHandler.mouseDeltaX, (float)MCH_ClientCommonTickHandler.mouseDeltaY);
            }
            else {
                ac.setAngles((Entity)player, fixRot, fixYaw, fixPitch, (float)(MCH_ClientCommonTickHandler.mouseDeltaX + MCH_ClientCommonTickHandler.prevMouseDeltaX) / 2.0f, (float)(MCH_ClientCommonTickHandler.mouseDeltaY + MCH_ClientCommonTickHandler.prevMouseDeltaY) / 2.0f, (float)MCH_ClientCommonTickHandler.mouseRollDeltaX, (float)MCH_ClientCommonTickHandler.mouseRollDeltaY, partialTicks - MCH_ClientCommonTickHandler.prevTick);
            }
            ac.setupAllRiderRenderPosition(partialTicks, player);
            final double dist = MathHelper.func_76133_a(MCH_ClientCommonTickHandler.mouseRollDeltaX * MCH_ClientCommonTickHandler.mouseRollDeltaX + MCH_ClientCommonTickHandler.mouseRollDeltaY * MCH_ClientCommonTickHandler.mouseRollDeltaY);
            if (!stickMode || dist < getMaxStickLength() * 0.1) {
                MCH_ClientCommonTickHandler.mouseRollDeltaX *= 0.95;
                MCH_ClientCommonTickHandler.mouseRollDeltaY *= 0.95;
            }
            float roll = MathHelper.func_76142_g(ac.getRotRoll());
            final float yaw = MathHelper.func_76142_g(ac.getRotYaw() - player.field_70177_z);
            roll *= MathHelper.func_76134_b((float)(yaw * 3.141592653589793 / 180.0));
            if (ac.getTVMissile() != null && W_Lib.isClientPlayer(ac.getTVMissile().shootingEntity) && ac.getIsGunnerMode((Entity)player)) {
                roll = 0.0f;
            }
            W_Reflection.setCameraRoll(roll);
            this.correctViewEntityDummy((Entity)player);
        }
        else {
            final MCH_EntitySeat seat = (player.field_70154_o instanceof MCH_EntitySeat) ? ((MCH_EntitySeat)player.field_70154_o) : null;
            if (seat != null && seat.getParent() != null) {
                this.updateMouseDelta(stickMode, partialTicks);
                ac = seat.getParent();
                boolean fixRot2 = false;
                final MCH_SeatInfo seatInfo2 = ac.getSeatInfo((Entity)player);
                if (seatInfo2 != null && seatInfo2.fixRot && ac.getIsGunnerMode((Entity)player) && !ac.isGunnerLookMode(player)) {
                    fixRot2 = true;
                    MCH_ClientCommonTickHandler.mouseRollDeltaX *= 0.0;
                    MCH_ClientCommonTickHandler.mouseRollDeltaY *= 0.0;
                    MCH_ClientCommonTickHandler.mouseDeltaX *= 0.0;
                    MCH_ClientCommonTickHandler.mouseDeltaY *= 0.0;
                }
                final Vec3 v = Vec3.func_72443_a(MCH_ClientCommonTickHandler.mouseDeltaX, MCH_ClientCommonTickHandler.mouseRollDeltaY, 0.0);
                W_Vec3.rotateAroundZ((float)(ac.calcRotRoll(partialTicks) / 180.0f * 3.141592653589793), v);
                final MCH_WeaponSet ws = ac.getCurrentWeapon((Entity)player);
                MCH_ClientCommonTickHandler.mouseDeltaY *= ((ws != null && ws.getInfo() != null) ? ws.getInfo().cameraRotationSpeedPitch : 1.0);
                player.func_70082_c((float)MCH_ClientCommonTickHandler.mouseDeltaX, (float)MCH_ClientCommonTickHandler.mouseDeltaY);
                final float y = ac.getRotYaw();
                final float p = ac.getRotPitch();
                final float r = ac.getRotRoll();
                ac.setRotYaw(ac.calcRotYaw(partialTicks));
                ac.setRotPitch(ac.calcRotPitch(partialTicks));
                ac.setRotRoll(ac.calcRotRoll(partialTicks));
                float revRoll = 0.0f;
                if (fixRot2) {
                    player.field_70177_z = ac.getRotYaw() + seatInfo2.fixYaw;
                    player.field_70125_A = ac.getRotPitch() + seatInfo2.fixPitch;
                    if (player.field_70125_A > 90.0f) {
                        final EntityPlayer entityPlayer = player;
                        entityPlayer.field_70127_C -= (player.field_70125_A - 90.0f) * 2.0f;
                        final EntityPlayer entityPlayer2 = player;
                        entityPlayer2.field_70125_A -= (player.field_70125_A - 90.0f) * 2.0f;
                        final EntityPlayer entityPlayer3 = player;
                        entityPlayer3.field_70126_B += 180.0f;
                        final EntityPlayer entityPlayer4 = player;
                        entityPlayer4.field_70177_z += 180.0f;
                        revRoll = 180.0f;
                    }
                    else if (player.field_70125_A < -90.0f) {
                        final EntityPlayer entityPlayer5 = player;
                        entityPlayer5.field_70127_C -= (player.field_70125_A - 90.0f) * 2.0f;
                        final EntityPlayer entityPlayer6 = player;
                        entityPlayer6.field_70125_A -= (player.field_70125_A - 90.0f) * 2.0f;
                        final EntityPlayer entityPlayer7 = player;
                        entityPlayer7.field_70126_B += 180.0f;
                        final EntityPlayer entityPlayer8 = player;
                        entityPlayer8.field_70177_z += 180.0f;
                        revRoll = 180.0f;
                    }
                }
                ac.setupAllRiderRenderPosition(partialTicks, player);
                ac.setRotYaw(y);
                ac.setRotPitch(p);
                ac.setRotRoll(r);
                MCH_ClientCommonTickHandler.mouseRollDeltaX *= 0.9;
                MCH_ClientCommonTickHandler.mouseRollDeltaY *= 0.9;
                float roll2 = MathHelper.func_76142_g(ac.getRotRoll());
                final float yaw2 = MathHelper.func_76142_g(ac.getRotYaw() - player.field_70177_z);
                roll2 *= MathHelper.func_76134_b((float)(yaw2 * 3.141592653589793 / 180.0));
                if (ac.getTVMissile() != null && W_Lib.isClientPlayer(ac.getTVMissile().shootingEntity) && ac.getIsGunnerMode((Entity)player)) {
                    roll2 = 0.0f;
                }
                W_Reflection.setCameraRoll(roll2 + revRoll);
                this.correctViewEntityDummy((Entity)player);
            }
            else {
                if (MCH_ClientCommonTickHandler.isRideAircraft) {
                    W_Reflection.setCameraRoll(0.0f);
                    MCH_ClientCommonTickHandler.isRideAircraft = false;
                }
                MCH_ClientCommonTickHandler.mouseRollDeltaX = 0.0;
                MCH_ClientCommonTickHandler.mouseRollDeltaY = 0.0;
            }
        }
        if (ac != null) {
            if (ac.getSeatIdByEntity((Entity)player) == 0 && !ac.isDestroyed()) {
                ac.lastRiderYaw = player.field_70177_z;
                ac.prevLastRiderYaw = player.field_70126_B;
                ac.lastRiderPitch = player.field_70125_A;
                ac.prevLastRiderPitch = player.field_70127_C;
            }
            ac.updateWeaponsRotation();
        }
        final Entity de = (Entity)MCH_ViewEntityDummy.getInstance(player.field_70170_p);
        if (de != null) {
            de.field_70177_z = player.field_70177_z;
            de.field_70126_B = player.field_70126_B;
            if (ac != null) {
                final MCH_WeaponSet wi = ac.getCurrentWeapon((Entity)player);
                if (wi != null && wi.getInfo() != null && wi.getInfo().fixCameraPitch) {
                    final Entity entity = de;
                    final Entity entity2 = de;
                    final float n = 0.0f;
                    entity2.field_70127_C = n;
                    entity.field_70125_A = n;
                }
            }
        }
        MCH_ClientCommonTickHandler.prevTick = partialTicks;
    }
    
    public void correctViewEntityDummy(final Entity entity) {
        final Entity de = (Entity)MCH_ViewEntityDummy.getInstance(entity.field_70170_p);
        if (de != null) {
            if (de.field_70177_z - de.field_70126_B > 180.0f) {
                final Entity entity2 = de;
                entity2.field_70126_B += 360.0f;
            }
            else if (de.field_70177_z - de.field_70126_B < -180.0f) {
                final Entity entity3 = de;
                entity3.field_70126_B -= 360.0f;
            }
        }
    }
    
    @Override
    public void onPlayerTickPre(final EntityPlayer player) {
        if (player.field_70170_p.field_72995_K) {
            final ItemStack currentItemstack = player.func_71045_bC();
            if (currentItemstack != null && currentItemstack.func_77973_b() instanceof MCH_ItemWrench && player.func_71052_bv() > 0 && player.func_71011_bu() != currentItemstack) {
                final int maxdm = currentItemstack.func_77958_k();
                final int dm = currentItemstack.func_77960_j();
                if (dm <= maxdm && dm > 0) {
                    player.func_71008_a(currentItemstack, player.func_71052_bv());
                }
            }
        }
    }
    
    @Override
    public void onPlayerTickPost(final EntityPlayer player) {
    }
    
    @Override
    public void onRenderTickPost(final float partialTicks) {
        if (this.mc.field_71439_g != null) {
            MCH_ClientTickHandlerBase.applyRotLimit((Entity)this.mc.field_71439_g);
            final Entity e = (Entity)MCH_ViewEntityDummy.getInstance(this.mc.field_71439_g.field_70170_p);
            if (e != null) {
                e.field_70125_A = this.mc.field_71439_g.field_70125_A;
                e.field_70177_z = this.mc.field_71439_g.field_70177_z;
                e.field_70127_C = this.mc.field_71439_g.field_70127_C;
                e.field_70126_B = this.mc.field_71439_g.field_70126_B;
            }
        }
        if (this.mc.field_71462_r == null || this.mc.field_71462_r instanceof GuiChat || this.mc.field_71462_r.getClass().toString().indexOf("GuiDriveableController") >= 0) {
            for (final MCH_Gui gui : this.guis) {
                if (this.drawGui(gui, partialTicks)) {
                    break;
                }
            }
            this.drawGui(this.gui_Common, partialTicks);
            this.drawGui(this.gui_Wrench, partialTicks);
            this.drawGui(this.gui_EMarker, partialTicks);
            if (MCH_ClientCommonTickHandler.isDrawScoreboard) {
                MCH_GuiScoreboard.drawList(this.mc, this.mc.field_71466_p, false);
            }
            this.drawGui(this.gui_Title, partialTicks);
        }
    }
    
    public boolean drawGui(final MCH_Gui gui, final float partialTicks) {
        if (gui.isDrawGui((EntityPlayer)this.mc.field_71439_g)) {
            gui.func_73863_a(0, 0, partialTicks);
            return true;
        }
        return false;
    }
    
    static {
        MCH_ClientCommonTickHandler.cameraMode = 0;
        MCH_ClientCommonTickHandler.ridingAircraft = null;
        MCH_ClientCommonTickHandler.isDrawScoreboard = false;
        MCH_ClientCommonTickHandler.sendLDCount = 0;
        MCH_ClientCommonTickHandler.isLocked = false;
        MCH_ClientCommonTickHandler.lockedSoundCount = 0;
        MCH_ClientCommonTickHandler.mouseDeltaX = 0.0;
        MCH_ClientCommonTickHandler.mouseDeltaY = 0.0;
        MCH_ClientCommonTickHandler.mouseRollDeltaX = 0.0;
        MCH_ClientCommonTickHandler.mouseRollDeltaY = 0.0;
        MCH_ClientCommonTickHandler.isRideAircraft = false;
        MCH_ClientCommonTickHandler.prevTick = 0.0f;
    }
}
