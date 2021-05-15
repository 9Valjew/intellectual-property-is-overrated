package mcheli.tank;

import cpw.mods.fml.relauncher.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import mcheli.aircraft.*;
import mcheli.*;

@SideOnly(Side.CLIENT)
public class MCH_GuiTank extends MCH_AircraftCommonGui
{
    public MCH_GuiTank(final Minecraft minecraft) {
        super(minecraft);
    }
    
    @Override
    public boolean isDrawGui(final EntityPlayer player) {
        return MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player) instanceof MCH_EntityTank;
    }
    
    @Override
    public void drawGui(final EntityPlayer player, final boolean isThirdPersonView) {
        final MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player);
        if (!(ac instanceof MCH_EntityTank) || ac.isDestroyed()) {
            return;
        }
        final MCH_EntityTank tank = (MCH_EntityTank)ac;
        final int seatID = ac.getSeatIdByEntity((Entity)player);
        GL11.glLineWidth((float)MCH_GuiTank.scaleFactor);
        if (tank.getCameraMode(player) == 1) {
            this.drawNightVisionNoise();
        }
        Label_0081: {
            if (isThirdPersonView) {
                final MCH_Config config = MCH_MOD.config;
                if (!MCH_Config.DisplayHUDThirdPerson.prmBool) {
                    break Label_0081;
                }
            }
            this.drawHud(ac, player, seatID);
        }
        this.drawDebugtInfo(tank);
        Label_0152: {
            if (isThirdPersonView) {
                final MCH_Config config2 = MCH_MOD.config;
                if (!MCH_Config.DisplayHUDThirdPerson.prmBool) {
                    break Label_0152;
                }
            }
            if (tank.getTVMissile() != null && (tank.getIsGunnerMode((Entity)player) || tank.isUAV())) {
                this.drawTvMissileNoise(tank, tank.getTVMissile());
            }
            else {
                this.drawKeybind(tank, player, seatID);
            }
        }
        this.drawHitBullet(tank, -14101432, seatID);
    }
    
    public void drawDebugtInfo(final MCH_EntityTank ac) {
        final MCH_Config config = MCH_MOD.config;
        if (MCH_Config.DebugLog) {
            final int LX = this.centerX - 100;
            super.drawDebugtInfo(ac);
        }
    }
    
    public void drawKeybind(final MCH_EntityTank tank, final EntityPlayer player, final int seatID) {
        final MCH_Config config = MCH_MOD.config;
        if (MCH_Config.HideKeybind.prmBool) {
            return;
        }
        final MCH_TankInfo info = tank.getTankInfo();
        if (info == null) {
            return;
        }
        final int colorActive = -1342177281;
        final int colorInactive = -1349546097;
        final int RX = this.centerX + 120;
        final int LX = this.centerX - 200;
        this.drawKeyBind(tank, info, player, seatID, RX, LX, colorActive, colorInactive);
        if (seatID == 0 && tank.hasBrake()) {
            final StringBuilder append = new StringBuilder().append("Brake : ");
            final MCH_Config config2 = MCH_MOD.config;
            final String msg = append.append(MCH_KeyName.getDescOrName(MCH_Config.KeySwitchHovering.prmInt)).toString();
            this.drawString(msg, RX, this.centerY - 30, colorActive);
        }
        if (seatID > 0 && tank.canSwitchGunnerModeOtherSeat(player)) {
            final StringBuilder append2 = new StringBuilder().append(tank.getIsGunnerMode((Entity)player) ? "Normal" : "Camera").append(" : ");
            final MCH_Config config3 = MCH_MOD.config;
            final String msg = append2.append(MCH_KeyName.getDescOrName(MCH_Config.KeySwitchMode.prmInt)).toString();
            this.drawString(msg, RX, this.centerY - 40, colorActive);
        }
        if (tank.getIsGunnerMode((Entity)player) && info.cameraZoom > 1) {
            final StringBuilder append3 = new StringBuilder().append("Zoom : ");
            final MCH_Config config4 = MCH_MOD.config;
            final String msg = append3.append(MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt)).toString();
            this.drawString(msg, LX, this.centerY - 80, colorActive);
        }
        else if (seatID == 0 && (tank.canFoldHatch() || tank.canUnfoldHatch())) {
            final StringBuilder append4 = new StringBuilder().append("OpenHatch : ");
            final MCH_Config config5 = MCH_MOD.config;
            final String msg = append4.append(MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt)).toString();
            this.drawString(msg, LX, this.centerY - 80, colorActive);
        }
    }
}
