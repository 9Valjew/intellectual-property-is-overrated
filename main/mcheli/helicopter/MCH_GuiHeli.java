package mcheli.helicopter;

import cpw.mods.fml.relauncher.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import mcheli.weapon.*;
import mcheli.aircraft.*;
import org.lwjgl.input.*;
import mcheli.*;

@SideOnly(Side.CLIENT)
public class MCH_GuiHeli extends MCH_AircraftCommonGui
{
    public MCH_GuiHeli(final Minecraft minecraft) {
        super(minecraft);
    }
    
    @Override
    public boolean isDrawGui(final EntityPlayer player) {
        return MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player) instanceof MCH_EntityHeli;
    }
    
    @Override
    public void drawGui(final EntityPlayer player, final boolean isThirdPersonView) {
        final MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player);
        if (!(ac instanceof MCH_EntityHeli) || ac.isDestroyed()) {
            return;
        }
        final MCH_EntityHeli heli = (MCH_EntityHeli)ac;
        final int seatID = ac.getSeatIdByEntity((Entity)player);
        GL11.glLineWidth((float)MCH_GuiHeli.scaleFactor);
        if (heli.getCameraMode(player) == 1) {
            this.drawNightVisionNoise();
        }
        Label_0105: {
            if (isThirdPersonView) {
                final MCH_Config config = MCH_MOD.config;
                if (!MCH_Config.DisplayHUDThirdPerson.prmBool) {
                    break Label_0105;
                }
            }
            if (seatID == 0 && heli.getIsGunnerMode((Entity)player)) {
                this.drawHud(ac, player, 1);
            }
            else {
                this.drawHud(ac, player, seatID);
            }
        }
        this.drawDebugtInfo(heli);
        if (!heli.getIsGunnerMode((Entity)player)) {
            Label_0146: {
                if (isThirdPersonView) {
                    final MCH_Config config2 = MCH_MOD.config;
                    if (!MCH_Config.DisplayHUDThirdPerson.prmBool) {
                        break Label_0146;
                    }
                }
                this.drawKeyBind(heli, player, seatID);
            }
            this.drawHitBullet(heli, -14101432, seatID);
        }
        else {
            Label_0217: {
                if (isThirdPersonView) {
                    final MCH_Config config3 = MCH_MOD.config;
                    if (!MCH_Config.DisplayHUDThirdPerson.prmBool) {
                        break Label_0217;
                    }
                }
                final MCH_EntityTvMissile tvmissile = heli.getTVMissile();
                if (!heli.isMissileCameraMode((Entity)player)) {
                    this.drawKeyBind(heli, player, seatID);
                }
                else if (tvmissile != null) {
                    this.drawTvMissileNoise(heli, tvmissile);
                }
            }
            this.drawHitBullet(heli, -805306369, seatID);
        }
    }
    
    public void drawKeyBind(final MCH_EntityHeli heli, final EntityPlayer player, final int seatID) {
        final MCH_Config config = MCH_MOD.config;
        if (MCH_Config.HideKeybind.prmBool) {
            return;
        }
        final MCH_HeliInfo info = heli.getHeliInfo();
        if (info == null) {
            return;
        }
        final int colorActive = -1342177281;
        final int colorInactive = -1349546097;
        final int RX = this.centerX + 120;
        final int LX = this.centerX - 200;
        this.drawKeyBind(heli, info, player, seatID, RX, LX, colorActive, colorInactive);
        if (seatID == 0 && info.isEnableGunnerMode) {
            final MCH_Config config2 = MCH_MOD.config;
            if (!Keyboard.isKeyDown(MCH_Config.KeyFreeLook.prmInt)) {
                final int c = heli.isHoveringMode() ? colorInactive : colorActive;
                final StringBuilder append = new StringBuilder().append(heli.getIsGunnerMode((Entity)player) ? "Normal" : "Gunner").append(" : ");
                final MCH_Config config3 = MCH_MOD.config;
                final String msg = append.append(MCH_KeyName.getDescOrName(MCH_Config.KeySwitchMode.prmInt)).toString();
                this.drawString(msg, RX, this.centerY - 70, c);
            }
        }
        if (seatID > 0 && heli.canSwitchGunnerModeOtherSeat(player)) {
            final StringBuilder append2 = new StringBuilder().append(heli.getIsGunnerMode((Entity)player) ? "Normal" : "Camera").append(" : ");
            final MCH_Config config4 = MCH_MOD.config;
            final String msg = append2.append(MCH_KeyName.getDescOrName(MCH_Config.KeySwitchMode.prmInt)).toString();
            this.drawString(msg, RX, this.centerY - 40, colorActive);
        }
        if (seatID == 0) {
            final MCH_Config config5 = MCH_MOD.config;
            if (!Keyboard.isKeyDown(MCH_Config.KeyFreeLook.prmInt)) {
                final int c = heli.getIsGunnerMode((Entity)player) ? colorInactive : colorActive;
                final StringBuilder append3 = new StringBuilder().append(heli.getIsGunnerMode((Entity)player) ? "Normal" : "Hovering").append(" : ");
                final MCH_Config config6 = MCH_MOD.config;
                final String msg = append3.append(MCH_KeyName.getDescOrName(MCH_Config.KeySwitchHovering.prmInt)).toString();
                this.drawString(msg, RX, this.centerY - 60, c);
            }
        }
        if (seatID == 0) {
            if (heli.getTowChainEntity() != null && !heli.getTowChainEntity().field_70128_L) {
                final StringBuilder append4 = new StringBuilder().append("Drop  : ");
                final MCH_Config config7 = MCH_MOD.config;
                final String msg = append4.append(MCH_KeyName.getDescOrName(MCH_Config.KeyExtra.prmInt)).toString();
                this.drawString(msg, RX, this.centerY - 30, colorActive);
            }
            else if (info.isEnableFoldBlade && MCH_Lib.getBlockIdY(heli.field_70170_p, heli.field_70165_t, heli.field_70163_u, heli.field_70161_v, 1, -2, true) > 0 && heli.getCurrentThrottle() <= 0.01) {
                final StringBuilder append5 = new StringBuilder().append("FoldBlade  : ");
                final MCH_Config config8 = MCH_MOD.config;
                final String msg = append5.append(MCH_KeyName.getDescOrName(MCH_Config.KeyExtra.prmInt)).toString();
                this.drawString(msg, RX, this.centerY - 30, colorActive);
            }
        }
        if ((heli.getIsGunnerMode((Entity)player) || heli.isUAV()) && info.cameraZoom > 1) {
            final StringBuilder append6 = new StringBuilder().append("Zoom : ");
            final MCH_Config config9 = MCH_MOD.config;
            final String msg = append6.append(MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt)).toString();
            this.drawString(msg, LX, this.centerY - 80, colorActive);
        }
        else if (seatID == 0 && (heli.canFoldHatch() || heli.canUnfoldHatch())) {
            final StringBuilder append7 = new StringBuilder().append("OpenHatch : ");
            final MCH_Config config10 = MCH_MOD.config;
            final String msg = append7.append(MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt)).toString();
            this.drawString(msg, LX, this.centerY - 80, colorActive);
        }
    }
}
