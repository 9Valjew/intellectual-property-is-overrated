package mcheli.plane;

import cpw.mods.fml.relauncher.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import mcheli.aircraft.*;
import org.lwjgl.input.*;
import mcheli.*;

@SideOnly(Side.CLIENT)
public class MCP_GuiPlane extends MCH_AircraftCommonGui
{
    public MCP_GuiPlane(final Minecraft minecraft) {
        super(minecraft);
    }
    
    @Override
    public boolean isDrawGui(final EntityPlayer player) {
        return MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player) instanceof MCP_EntityPlane;
    }
    
    @Override
    public void drawGui(final EntityPlayer player, final boolean isThirdPersonView) {
        final MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player);
        if (!(ac instanceof MCP_EntityPlane) || ac.isDestroyed()) {
            return;
        }
        final MCP_EntityPlane plane = (MCP_EntityPlane)ac;
        final int seatID = ac.getSeatIdByEntity((Entity)player);
        GL11.glLineWidth((float)MCP_GuiPlane.scaleFactor);
        if (plane.getCameraMode(player) == 1) {
            this.drawNightVisionNoise();
        }
        Label_0105: {
            if (isThirdPersonView) {
                final MCH_Config config = MCH_MOD.config;
                if (!MCH_Config.DisplayHUDThirdPerson.prmBool) {
                    break Label_0105;
                }
            }
            if (seatID == 0 && plane.getIsGunnerMode((Entity)player)) {
                this.drawHud(ac, player, 1);
            }
            else {
                this.drawHud(ac, player, seatID);
            }
        }
        this.drawDebugtInfo(plane);
        Label_0176: {
            if (isThirdPersonView) {
                final MCH_Config config2 = MCH_MOD.config;
                if (!MCH_Config.DisplayHUDThirdPerson.prmBool) {
                    break Label_0176;
                }
            }
            if (plane.getTVMissile() != null && (plane.getIsGunnerMode((Entity)player) || plane.isUAV())) {
                this.drawTvMissileNoise(plane, plane.getTVMissile());
            }
            else {
                this.drawKeybind(plane, player, seatID);
            }
        }
        this.drawHitBullet(plane, -14101432, seatID);
    }
    
    public void drawKeybind(final MCP_EntityPlane plane, final EntityPlayer player, final int seatID) {
        final MCH_Config config = MCH_MOD.config;
        if (MCH_Config.HideKeybind.prmBool) {
            return;
        }
        final MCP_PlaneInfo info = plane.getPlaneInfo();
        if (info == null) {
            return;
        }
        final int colorActive = -1342177281;
        final int colorInactive = -1349546097;
        final int RX = this.centerX + 120;
        final int LX = this.centerX - 200;
        this.drawKeyBind(plane, info, player, seatID, RX, LX, colorActive, colorInactive);
        if (seatID == 0 && info.isEnableGunnerMode) {
            final MCH_Config config2 = MCH_MOD.config;
            if (!Keyboard.isKeyDown(MCH_Config.KeyFreeLook.prmInt)) {
                final int c = plane.isHoveringMode() ? colorInactive : colorActive;
                final StringBuilder append = new StringBuilder().append(plane.getIsGunnerMode((Entity)player) ? "Normal" : "Gunner").append(" : ");
                final MCH_Config config3 = MCH_MOD.config;
                final String msg = append.append(MCH_KeyName.getDescOrName(MCH_Config.KeySwitchMode.prmInt)).toString();
                this.drawString(msg, RX, this.centerY - 70, c);
            }
        }
        if (seatID > 0 && plane.canSwitchGunnerModeOtherSeat(player)) {
            final StringBuilder append2 = new StringBuilder().append(plane.getIsGunnerMode((Entity)player) ? "Normal" : "Camera").append(" : ");
            final MCH_Config config4 = MCH_MOD.config;
            final String msg = append2.append(MCH_KeyName.getDescOrName(MCH_Config.KeySwitchMode.prmInt)).toString();
            this.drawString(msg, RX, this.centerY - 40, colorActive);
        }
        if (seatID == 0 && info.isEnableVtol) {
            final MCH_Config config5 = MCH_MOD.config;
            if (!Keyboard.isKeyDown(MCH_Config.KeyFreeLook.prmInt)) {
                final int stat = plane.getVtolMode();
                if (stat != 1) {
                    final StringBuilder append3 = new StringBuilder().append((stat == 0) ? "VTOL : " : "Normal : ");
                    final MCH_Config config6 = MCH_MOD.config;
                    final String msg = append3.append(MCH_KeyName.getDescOrName(MCH_Config.KeyExtra.prmInt)).toString();
                    this.drawString(msg, RX, this.centerY - 60, colorActive);
                }
            }
        }
        if (plane.canEjectSeat((Entity)player)) {
            final StringBuilder append4 = new StringBuilder().append("Eject seat: ");
            final MCH_Config config7 = MCH_MOD.config;
            final String msg = append4.append(MCH_KeyName.getDescOrName(MCH_Config.KeySwitchHovering.prmInt)).toString();
            this.drawString(msg, RX, this.centerY - 30, colorActive);
        }
        if (plane.getIsGunnerMode((Entity)player) && info.cameraZoom > 1) {
            final StringBuilder append5 = new StringBuilder().append("Zoom : ");
            final MCH_Config config8 = MCH_MOD.config;
            final String msg = append5.append(MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt)).toString();
            this.drawString(msg, LX, this.centerY - 80, colorActive);
        }
        else if (seatID == 0) {
            if (plane.canFoldWing() || plane.canUnfoldWing()) {
                final StringBuilder append6 = new StringBuilder().append("FoldWing : ");
                final MCH_Config config9 = MCH_MOD.config;
                final String msg = append6.append(MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt)).toString();
                this.drawString(msg, LX, this.centerY - 80, colorActive);
            }
            else if (plane.canFoldHatch() || plane.canUnfoldHatch()) {
                final StringBuilder append7 = new StringBuilder().append("OpenHatch : ");
                final MCH_Config config10 = MCH_MOD.config;
                final String msg = append7.append(MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt)).toString();
                this.drawString(msg, LX, this.centerY - 80, colorActive);
            }
        }
    }
}
