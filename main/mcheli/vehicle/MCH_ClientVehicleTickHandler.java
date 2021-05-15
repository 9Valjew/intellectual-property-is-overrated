package mcheli.vehicle;

import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import mcheli.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import mcheli.aircraft.*;
import mcheli.wrapper.*;

public class MCH_ClientVehicleTickHandler extends MCH_AircraftClientTickHandler
{
    public MCH_Key KeySwitchMode;
    public MCH_Key KeySwitchHovering;
    public MCH_Key KeyZoom;
    public MCH_Key KeyExtra;
    public MCH_Key[] Keys;
    
    public MCH_ClientVehicleTickHandler(final Minecraft minecraft, final MCH_Config config) {
        super(minecraft, config);
        this.updateKeybind(config);
    }
    
    @Override
    public void updateKeybind(final MCH_Config config) {
        super.updateKeybind(config);
        this.KeySwitchMode = new MCH_Key(MCH_Config.KeySwitchMode.prmInt);
        this.KeySwitchHovering = new MCH_Key(MCH_Config.KeySwitchHovering.prmInt);
        this.KeyZoom = new MCH_Key(MCH_Config.KeyZoom.prmInt);
        this.KeyExtra = new MCH_Key(MCH_Config.KeyExtra.prmInt);
        this.Keys = new MCH_Key[] { this.KeyUp, this.KeyDown, this.KeyRight, this.KeyLeft, this.KeySwitchMode, this.KeySwitchHovering, this.KeyUseWeapon, this.KeySwWeaponMode, this.KeySwitchWeapon1, this.KeySwitchWeapon2, this.KeyZoom, this.KeyCameraMode, this.KeyUnmount, this.KeyUnmountForce, this.KeyFlare, this.KeyExtra, this.KeyGUI };
    }
    
    protected void update(final EntityPlayer player, final MCH_EntityVehicle vehicle, final MCH_VehicleInfo info) {
        if (info != null) {
            MCH_ClientTickHandlerBase.setRotLimitPitch(info.minRotationPitch, info.maxRotationPitch, (Entity)player);
        }
        vehicle.updateCameraRotate(player.field_70177_z, player.field_70125_A);
        vehicle.updateRadar(5);
    }
    
    @Override
    protected void onTick(final boolean inGUI) {
        for (final MCH_Key k : this.Keys) {
            k.update();
        }
        this.isBeforeRiding = this.isRiding;
        final EntityPlayer player = (EntityPlayer)this.mc.field_71439_g;
        MCH_EntityVehicle vehicle = null;
        boolean isPilot = true;
        if (player != null) {
            if (player.field_70154_o instanceof MCH_EntityVehicle) {
                vehicle = (MCH_EntityVehicle)player.field_70154_o;
            }
            else if (player.field_70154_o instanceof MCH_EntitySeat) {
                final MCH_EntitySeat seat = (MCH_EntitySeat)player.field_70154_o;
                if (seat.getParent() instanceof MCH_EntityVehicle) {
                    isPilot = false;
                    vehicle = (MCH_EntityVehicle)seat.getParent();
                }
            }
        }
        if (vehicle != null && vehicle.getAcInfo() != null) {
            MCH_Lib.disableFirstPersonItemRender(player.func_71045_bC());
            this.update(player, vehicle, vehicle.getVehicleInfo());
            final MCH_ViewEntityDummy viewEntityDummy = MCH_ViewEntityDummy.getInstance((World)this.mc.field_71441_e);
            viewEntityDummy.update(vehicle.camera);
            if (!inGUI) {
                if (!vehicle.isDestroyed()) {
                    this.playerControl(player, vehicle, isPilot);
                }
            }
            else {
                this.playerControlInGUI(player, vehicle, isPilot);
            }
            MCH_Lib.setRenderViewEntity((EntityLivingBase)viewEntityDummy);
            this.isRiding = true;
        }
        else {
            this.isRiding = false;
        }
        if (this.isBeforeRiding || !this.isRiding) {
            if (this.isBeforeRiding && !this.isRiding) {
                MCH_Lib.enableFirstPersonItemRender();
                MCH_Lib.setRenderViewEntity((EntityLivingBase)player);
            }
        }
    }
    
    protected void playerControlInGUI(final EntityPlayer player, final MCH_EntityVehicle vehicle, final boolean isPilot) {
        this.commonPlayerControlInGUI(player, vehicle, isPilot, new MCH_PacketVehiclePlayerControl());
    }
    
    protected void playerControl(final EntityPlayer player, final MCH_EntityVehicle vehicle, final boolean isPilot) {
        final MCH_PacketVehiclePlayerControl pc = new MCH_PacketVehiclePlayerControl();
        boolean send = false;
        send = this.commonPlayerControl(player, vehicle, isPilot, pc);
        if (this.KeyExtra.isKeyDown()) {
            if (vehicle.getTowChainEntity() != null) {
                playSoundOK();
                pc.unhitchChainId = W_Entity.getEntityId(vehicle.getTowChainEntity());
                send = true;
            }
            else {
                playSoundNG();
            }
        }
        if (!this.KeySwitchHovering.isKeyDown()) {
            if (this.KeySwitchMode.isKeyDown()) {}
        }
        if (this.KeyZoom.isKeyDown()) {
            if (vehicle.canZoom()) {
                vehicle.zoomCamera();
                MCH_ClientTickHandlerBase.playSound("zoom", 0.5f, 1.0f);
            }
            else if (vehicle.getAcInfo().haveHatch()) {
                if (vehicle.canFoldHatch()) {
                    pc.switchHatch = 2;
                    send = true;
                }
                else if (vehicle.canUnfoldHatch()) {
                    pc.switchHatch = 1;
                    send = true;
                }
                else {
                    playSoundNG();
                }
            }
        }
        if (send) {
            W_Network.sendToServer(pc);
        }
    }
}
