package mcheli.plane;

import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import mcheli.uav.*;
import net.minecraft.world.*;
import mcheli.*;
import net.minecraft.entity.*;
import mcheli.aircraft.*;
import mcheli.wrapper.*;

public class MCP_ClientPlaneTickHandler extends MCH_AircraftClientTickHandler
{
    public MCH_Key KeySwitchMode;
    public MCH_Key KeyEjectSeat;
    public MCH_Key KeyZoom;
    public MCH_Key[] Keys;
    
    public MCP_ClientPlaneTickHandler(final Minecraft minecraft, final MCH_Config config) {
        super(minecraft, config);
        this.updateKeybind(config);
    }
    
    @Override
    public void updateKeybind(final MCH_Config config) {
        super.updateKeybind(config);
        this.KeySwitchMode = new MCH_Key(MCH_Config.KeySwitchMode.prmInt);
        this.KeyEjectSeat = new MCH_Key(MCH_Config.KeySwitchHovering.prmInt);
        this.KeyZoom = new MCH_Key(MCH_Config.KeyZoom.prmInt);
        this.Keys = new MCH_Key[] { this.KeyUp, this.KeyDown, this.KeyRight, this.KeyLeft, this.KeySwitchMode, this.KeyEjectSeat, this.KeyUseWeapon, this.KeySwWeaponMode, this.KeySwitchWeapon1, this.KeySwitchWeapon2, this.KeyZoom, this.KeyCameraMode, this.KeyUnmount, this.KeyUnmountForce, this.KeyFlare, this.KeyExtra, this.KeyFreeLook, this.KeyGUI, this.KeyGearUpDown, this.KeyPutToRack, this.KeyDownFromRack };
    }
    
    protected void update(final EntityPlayer player, final MCP_EntityPlane plane) {
        if (plane.getIsGunnerMode((Entity)player)) {
            final MCH_SeatInfo seatInfo = plane.getSeatInfo((Entity)player);
            if (seatInfo != null) {
                MCH_ClientTickHandlerBase.setRotLimitPitch(seatInfo.minPitch, seatInfo.maxPitch, (Entity)player);
            }
        }
        plane.updateRadar(10);
        plane.updateCameraRotate(player.field_70177_z, player.field_70125_A);
    }
    
    @Override
    protected void onTick(final boolean inGUI) {
        for (final MCH_Key k : this.Keys) {
            k.update();
        }
        this.isBeforeRiding = this.isRiding;
        final EntityPlayer player = (EntityPlayer)this.mc.field_71439_g;
        MCP_EntityPlane plane = null;
        boolean isPilot = true;
        if (player != null) {
            if (player.field_70154_o instanceof MCP_EntityPlane) {
                plane = (MCP_EntityPlane)player.field_70154_o;
            }
            else if (player.field_70154_o instanceof MCH_EntitySeat) {
                final MCH_EntitySeat seat = (MCH_EntitySeat)player.field_70154_o;
                if (seat.getParent() instanceof MCP_EntityPlane) {
                    isPilot = false;
                    plane = (MCP_EntityPlane)seat.getParent();
                }
            }
            else if (player.field_70154_o instanceof MCH_EntityUavStation) {
                final MCH_EntityUavStation uavStation = (MCH_EntityUavStation)player.field_70154_o;
                if (uavStation.getControlAircract() instanceof MCP_EntityPlane) {
                    plane = (MCP_EntityPlane)uavStation.getControlAircract();
                }
            }
        }
        if (plane != null && plane.getAcInfo() != null) {
            this.update(player, plane);
            final MCH_ViewEntityDummy viewEntityDummy = MCH_ViewEntityDummy.getInstance((World)this.mc.field_71441_e);
            viewEntityDummy.update(plane.camera);
            if (!inGUI) {
                if (!plane.isDestroyed()) {
                    this.playerControl(player, plane, isPilot);
                }
            }
            else {
                this.playerControlInGUI(player, plane, isPilot);
            }
            boolean hideHand = true;
            if ((isPilot && plane.isAlwaysCameraView()) || plane.getIsGunnerMode((Entity)player) || plane.getCameraId() > 0) {
                MCH_Lib.setRenderViewEntity((EntityLivingBase)viewEntityDummy);
            }
            else {
                MCH_Lib.setRenderViewEntity((EntityLivingBase)player);
                if (!isPilot && plane.getCurrentWeaponID((Entity)player) < 0) {
                    hideHand = false;
                }
            }
            if (hideHand) {
                MCH_Lib.disableFirstPersonItemRender(player.func_71045_bC());
            }
            this.isRiding = true;
        }
        else {
            this.isRiding = false;
        }
        if (!this.isBeforeRiding && this.isRiding && plane != null) {
            MCH_ViewEntityDummy.getInstance((World)this.mc.field_71441_e).func_70107_b(plane.field_70165_t, plane.field_70163_u + 0.5, plane.field_70161_v);
        }
        else if (this.isBeforeRiding && !this.isRiding) {
            MCH_Lib.enableFirstPersonItemRender();
            MCH_Lib.setRenderViewEntity((EntityLivingBase)player);
            W_Reflection.setCameraRoll(0.0f);
        }
    }
    
    protected void playerControlInGUI(final EntityPlayer player, final MCP_EntityPlane plane, final boolean isPilot) {
        this.commonPlayerControlInGUI(player, plane, isPilot, new MCP_PlanePacketPlayerControl());
    }
    
    protected void playerControl(final EntityPlayer player, final MCP_EntityPlane plane, final boolean isPilot) {
        final MCP_PlanePacketPlayerControl pc = new MCP_PlanePacketPlayerControl();
        boolean send = false;
        send = this.commonPlayerControl(player, plane, isPilot, pc);
        if (isPilot) {
            if (this.KeySwitchMode.isKeyDown()) {
                if (plane.getIsGunnerMode((Entity)player) && plane.canSwitchCameraPos()) {
                    pc.switchMode = 0;
                    plane.switchGunnerMode(false);
                    send = true;
                    plane.setCameraId(1);
                }
                else if (plane.getCameraId() > 0) {
                    plane.setCameraId(plane.getCameraId() + 1);
                    if (plane.getCameraId() >= plane.getCameraPosNum()) {
                        plane.setCameraId(0);
                    }
                }
                else if (plane.canSwitchGunnerMode()) {
                    pc.switchMode = (byte)(plane.getIsGunnerMode((Entity)player) ? 0 : 1);
                    plane.switchGunnerMode(!plane.getIsGunnerMode((Entity)player));
                    send = true;
                    plane.setCameraId(0);
                }
                else if (plane.canSwitchCameraPos()) {
                    plane.setCameraId(1);
                }
                else {
                    playSoundNG();
                }
            }
            if (this.KeyExtra.isKeyDown()) {
                if (plane.canSwitchVtol()) {
                    final boolean currentMode = plane.getNozzleStat();
                    if (!currentMode) {
                        pc.switchVtol = 1;
                    }
                    else {
                        pc.switchVtol = 0;
                    }
                    plane.swithVtolMode(!currentMode);
                    send = true;
                }
                else {
                    playSoundNG();
                }
            }
        }
        else if (this.KeySwitchMode.isKeyDown()) {
            if (plane.canSwitchGunnerModeOtherSeat(player)) {
                plane.switchGunnerModeOtherSeat(player);
                send = true;
            }
            else {
                playSoundNG();
            }
        }
        if (this.KeyZoom.isKeyDown()) {
            final boolean isUav = plane.isUAV() && !plane.getAcInfo().haveHatch() && !plane.getPlaneInfo().haveWing();
            if (plane.getIsGunnerMode((Entity)player) || isUav) {
                plane.zoomCamera();
                MCH_ClientTickHandlerBase.playSound("zoom", 0.5f, 1.0f);
            }
            else if (isPilot) {
                if (plane.getAcInfo().haveHatch()) {
                    if (plane.canFoldHatch()) {
                        pc.switchHatch = 2;
                        send = true;
                    }
                    else if (plane.canUnfoldHatch()) {
                        pc.switchHatch = 1;
                        send = true;
                    }
                }
                else if (plane.canFoldWing()) {
                    pc.switchHatch = 2;
                    send = true;
                }
                else if (plane.canUnfoldWing()) {
                    pc.switchHatch = 1;
                    send = true;
                }
            }
        }
        if (this.KeyEjectSeat.isKeyDown() && plane.canEjectSeat((Entity)player)) {
            pc.ejectSeat = true;
            send = true;
        }
        if (send) {
            W_Network.sendToServer(pc);
        }
    }
}
