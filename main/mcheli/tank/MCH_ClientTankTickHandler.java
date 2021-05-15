package mcheli.tank;

import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import mcheli.uav.*;
import net.minecraft.world.*;
import mcheli.*;
import net.minecraft.entity.*;
import mcheli.aircraft.*;
import mcheli.wrapper.*;

public class MCH_ClientTankTickHandler extends MCH_AircraftClientTickHandler
{
    public MCH_Key KeySwitchMode;
    public MCH_Key KeyZoom;
    public MCH_Key[] Keys;
    
    public MCH_ClientTankTickHandler(final Minecraft minecraft, final MCH_Config config) {
        super(minecraft, config);
        this.updateKeybind(config);
    }
    
    @Override
    public void updateKeybind(final MCH_Config config) {
        super.updateKeybind(config);
        this.KeySwitchMode = new MCH_Key(MCH_Config.KeySwitchMode.prmInt);
        this.KeyZoom = new MCH_Key(MCH_Config.KeyZoom.prmInt);
        this.Keys = new MCH_Key[] { this.KeyUp, this.KeyDown, this.KeyRight, this.KeyLeft, this.KeySwitchMode, this.KeyUseWeapon, this.KeySwWeaponMode, this.KeySwitchWeapon1, this.KeySwitchWeapon2, this.KeyZoom, this.KeyCameraMode, this.KeyUnmount, this.KeyUnmountForce, this.KeyFlare, this.KeyExtra, this.KeyFreeLook, this.KeyGUI, this.KeyGearUpDown, this.KeyBrake, this.KeyPutToRack, this.KeyDownFromRack };
    }
    
    protected void update(final EntityPlayer player, final MCH_EntityTank tank) {
        if (tank.getIsGunnerMode((Entity)player)) {
            final MCH_SeatInfo seatInfo = tank.getSeatInfo((Entity)player);
            if (seatInfo != null) {
                MCH_ClientTickHandlerBase.setRotLimitPitch(seatInfo.minPitch, seatInfo.maxPitch, (Entity)player);
            }
        }
        tank.updateRadar(10);
        tank.updateCameraRotate(player.field_70177_z, player.field_70125_A);
    }
    
    @Override
    protected void onTick(final boolean inGUI) {
        for (final MCH_Key k : this.Keys) {
            k.update();
        }
        this.isBeforeRiding = this.isRiding;
        final EntityPlayer player = (EntityPlayer)this.mc.field_71439_g;
        MCH_EntityTank tank = null;
        boolean isPilot = true;
        if (player != null) {
            if (player.field_70154_o instanceof MCH_EntityTank) {
                tank = (MCH_EntityTank)player.field_70154_o;
            }
            else if (player.field_70154_o instanceof MCH_EntitySeat) {
                final MCH_EntitySeat seat = (MCH_EntitySeat)player.field_70154_o;
                if (seat.getParent() instanceof MCH_EntityTank) {
                    isPilot = false;
                    tank = (MCH_EntityTank)seat.getParent();
                }
            }
            else if (player.field_70154_o instanceof MCH_EntityUavStation) {
                final MCH_EntityUavStation uavStation = (MCH_EntityUavStation)player.field_70154_o;
                if (uavStation.getControlAircract() instanceof MCH_EntityTank) {
                    tank = (MCH_EntityTank)uavStation.getControlAircract();
                }
            }
        }
        if (tank != null && tank.getAcInfo() != null) {
            this.update(player, tank);
            final MCH_ViewEntityDummy viewEntityDummy = MCH_ViewEntityDummy.getInstance((World)this.mc.field_71441_e);
            viewEntityDummy.update(tank.camera);
            if (!inGUI) {
                if (!tank.isDestroyed()) {
                    this.playerControl(player, tank, isPilot);
                }
            }
            else {
                this.playerControlInGUI(player, tank, isPilot);
            }
            boolean hideHand = true;
            if ((isPilot && tank.isAlwaysCameraView()) || tank.getIsGunnerMode((Entity)player) || tank.getCameraId() > 0) {
                MCH_Lib.setRenderViewEntity((EntityLivingBase)viewEntityDummy);
            }
            else {
                MCH_Lib.setRenderViewEntity((EntityLivingBase)player);
                if (!isPilot && tank.getCurrentWeaponID((Entity)player) < 0) {
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
        if (!this.isBeforeRiding && this.isRiding && tank != null) {
            MCH_ViewEntityDummy.getInstance((World)this.mc.field_71441_e).func_70107_b(tank.field_70165_t, tank.field_70163_u + 0.5, tank.field_70161_v);
        }
        else if (this.isBeforeRiding && !this.isRiding) {
            MCH_Lib.enableFirstPersonItemRender();
            MCH_Lib.setRenderViewEntity((EntityLivingBase)player);
            W_Reflection.setCameraRoll(0.0f);
        }
    }
    
    protected void playerControlInGUI(final EntityPlayer player, final MCH_EntityTank tank, final boolean isPilot) {
        this.commonPlayerControlInGUI(player, tank, isPilot, new MCH_TankPacketPlayerControl());
    }
    
    protected void playerControl(final EntityPlayer player, final MCH_EntityTank tank, final boolean isPilot) {
        final MCH_TankPacketPlayerControl pc = new MCH_TankPacketPlayerControl();
        boolean send = false;
        send = this.commonPlayerControl(player, tank, isPilot, pc);
        if (tank.getAcInfo().defaultFreelook && pc.switchFreeLook > 0) {
            pc.switchFreeLook = 0;
        }
        if (isPilot) {
            if (this.KeySwitchMode.isKeyDown()) {
                if (tank.getIsGunnerMode((Entity)player) && tank.canSwitchCameraPos()) {
                    pc.switchMode = 0;
                    tank.switchGunnerMode(false);
                    send = true;
                    tank.setCameraId(1);
                }
                else if (tank.getCameraId() > 0) {
                    tank.setCameraId(tank.getCameraId() + 1);
                    if (tank.getCameraId() >= tank.getCameraPosNum()) {
                        tank.setCameraId(0);
                    }
                }
                else if (tank.canSwitchGunnerMode()) {
                    pc.switchMode = (byte)(tank.getIsGunnerMode((Entity)player) ? 0 : 1);
                    tank.switchGunnerMode(!tank.getIsGunnerMode((Entity)player));
                    send = true;
                    tank.setCameraId(0);
                }
                else if (tank.canSwitchCameraPos()) {
                    tank.setCameraId(1);
                }
                else {
                    playSoundNG();
                }
            }
        }
        else if (this.KeySwitchMode.isKeyDown()) {
            if (tank.canSwitchGunnerModeOtherSeat(player)) {
                tank.switchGunnerModeOtherSeat(player);
                send = true;
            }
            else {
                playSoundNG();
            }
        }
        if (this.KeyZoom.isKeyDown()) {
            final boolean isUav = tank.isUAV() && !tank.getAcInfo().haveHatch();
            if (tank.getIsGunnerMode((Entity)player) || isUav) {
                tank.zoomCamera();
                MCH_ClientTickHandlerBase.playSound("zoom", 0.5f, 1.0f);
            }
            else if (isPilot && tank.getAcInfo().haveHatch()) {
                if (tank.canFoldHatch()) {
                    pc.switchHatch = 2;
                    send = true;
                }
                else if (tank.canUnfoldHatch()) {
                    pc.switchHatch = 1;
                    send = true;
                }
            }
        }
        if (send) {
            W_Network.sendToServer(pc);
        }
    }
}
