package mcheli.aircraft;

import mcheli.gui.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import mcheli.hud.*;
import org.lwjgl.opengl.*;
import mcheli.wrapper.*;
import org.lwjgl.input.*;
import mcheli.*;
import mcheli.weapon.*;

@SideOnly(Side.CLIENT)
public abstract class MCH_AircraftCommonGui extends MCH_Gui
{
    public MCH_AircraftCommonGui(final Minecraft minecraft) {
        super(minecraft);
    }
    
    public void drawHud(final MCH_EntityAircraft ac, final EntityPlayer player, final int seatId) {
        final MCH_AircraftInfo info = ac.getAcInfo();
        if (info == null) {
            return;
        }
        if (ac.isMissileCameraMode((Entity)player) && ac.getTVMissile() != null && info.hudTvMissile != null) {
            info.hudTvMissile.draw(ac, player, this.smoothCamPartialTicks);
        }
        else {
            if (seatId < 0) {
                return;
            }
            if (seatId < info.hudList.size()) {
                final MCH_Hud hud = info.hudList.get(seatId);
                if (hud != null) {
                    hud.draw(ac, player, this.smoothCamPartialTicks);
                }
            }
        }
    }
    
    public void drawDebugtInfo(final MCH_EntityAircraft ac) {
        final MCH_Config config = MCH_MOD.config;
        if (MCH_Config.DebugLog) {
            final int LX = this.centerX - 100;
        }
    }
    
    public void drawNightVisionNoise() {
        GL11.glEnable(3042);
        GL11.glColor4f(0.0f, 1.0f, 0.0f, 0.3f);
        final int srcBlend = GL11.glGetInteger(3041);
        final int dstBlend = GL11.glGetInteger(3040);
        GL11.glBlendFunc(1, 1);
        W_McClient.MOD_bindTexture("textures/gui/alpha.png");
        this.drawTexturedModalRectRotate(0.0, 0.0, this.field_146294_l, this.field_146295_m, this.rand.nextInt(256), this.rand.nextInt(256), 256.0, 256.0, 0.0f);
        GL11.glBlendFunc(srcBlend, dstBlend);
        GL11.glDisable(3042);
    }
    
    public void drawHitBullet(final int hs, final int hsMax, int color) {
        if (hs > 0) {
            final int cx = this.centerX;
            final int cy = this.centerY;
            final int IVX = 10;
            final int IVY = 10;
            final int SZX = 5;
            final int SZY = 5;
            final double[] ls = { cx - IVX, cy - IVY, cx - SZX, cy - SZY, cx - IVX, cy + IVY, cx - SZX, cy + SZY, cx + IVX, cy - IVY, cx + SZX, cy - SZY, cx + IVX, cy + IVY, cx + SZX, cy + SZY };
            final MCH_Config config = MCH_MOD.config;
            color = MCH_Config.hitMarkColorRGB;
            final int alpha = hs * (256 / hsMax);
            final int n = color;
            final MCH_Config config2 = MCH_MOD.config;
            color = (n | (int)(MCH_Config.hitMarkColorAlpha * alpha) << 24);
            this.drawLine(ls, color);
        }
    }
    
    public void drawHitBullet(final MCH_EntityAircraft ac, final int color, final int seatID) {
        this.drawHitBullet(ac.getHitStatus(), ac.getMaxHitStatus(), color);
    }
    
    protected void drawTvMissileNoise(final MCH_EntityAircraft ac, final MCH_EntityTvMissile tvmissile) {
        GL11.glEnable(3042);
        GL11.glColor4f(0.5f, 0.5f, 0.5f, 0.4f);
        final int srcBlend = GL11.glGetInteger(3041);
        final int dstBlend = GL11.glGetInteger(3040);
        GL11.glBlendFunc(1, 1);
        W_McClient.MOD_bindTexture("textures/gui/noise.png");
        this.drawTexturedModalRectRotate(0.0, 0.0, this.field_146294_l, this.field_146295_m, this.rand.nextInt(256), this.rand.nextInt(256), 256.0, 256.0, 0.0f);
        GL11.glBlendFunc(srcBlend, dstBlend);
        GL11.glDisable(3042);
    }
    
    public void drawKeyBind(final MCH_EntityAircraft ac, final MCH_AircraftInfo info, final EntityPlayer player, final int seatID, final int RX, final int LX, final int colorActive, final int colorInactive) {
        String msg = "";
        int c = 0;
        if (seatID == 0 && ac.canPutToRack()) {
            final StringBuilder append = new StringBuilder().append("PutRack : ");
            final MCH_Config config = MCH_MOD.config;
            msg = append.append(MCH_KeyName.getDescOrName(MCH_Config.KeyPutToRack.prmInt)).toString();
            this.drawString(msg, LX, this.centerY - 10, colorActive);
        }
        if (seatID == 0 && ac.canDownFromRack()) {
            final StringBuilder append2 = new StringBuilder().append("DownRack : ");
            final MCH_Config config2 = MCH_MOD.config;
            msg = append2.append(MCH_KeyName.getDescOrName(MCH_Config.KeyDownFromRack.prmInt)).toString();
            this.drawString(msg, LX, this.centerY - 0, colorActive);
        }
        if (seatID == 0 && ac.canRideRack()) {
            final StringBuilder append3 = new StringBuilder().append("RideRack : ");
            final MCH_Config config3 = MCH_MOD.config;
            msg = append3.append(MCH_KeyName.getDescOrName(MCH_Config.KeyPutToRack.prmInt)).toString();
            this.drawString(msg, LX, this.centerY + 10, colorActive);
        }
        if (seatID == 0 && ac.field_70154_o != null) {
            final StringBuilder append4 = new StringBuilder().append("DismountRack : ");
            final MCH_Config config4 = MCH_MOD.config;
            msg = append4.append(MCH_KeyName.getDescOrName(MCH_Config.KeyDownFromRack.prmInt)).toString();
            this.drawString(msg, LX, this.centerY + 10, colorActive);
        }
        Label_0454: {
            if (seatID <= 0 || ac.getSeatNum() <= 1) {
                final MCH_Config config5 = MCH_MOD.config;
                if (!Keyboard.isKeyDown(MCH_Config.KeyFreeLook.prmInt)) {
                    break Label_0454;
                }
            }
            c = ((seatID == 0) ? -208 : colorActive);
            String string;
            if (seatID == 0) {
                final StringBuilder sb = new StringBuilder();
                final MCH_Config config6 = MCH_MOD.config;
                string = sb.append(MCH_KeyName.getDescOrName(MCH_Config.KeyFreeLook.prmInt)).append(" + ").toString();
            }
            else {
                string = "";
            }
            final String sk = string;
            final StringBuilder append5 = new StringBuilder().append("NextSeat : ").append(sk);
            final MCH_Config config7 = MCH_MOD.config;
            msg = append5.append(MCH_KeyName.getDescOrName(MCH_Config.KeyGUI.prmInt)).toString();
            this.drawString(msg, RX, this.centerY - 70, c);
            final StringBuilder append6 = new StringBuilder().append("PrevSeat : ").append(sk);
            final MCH_Config config8 = MCH_MOD.config;
            msg = append6.append(MCH_KeyName.getDescOrName(MCH_Config.KeyExtra.prmInt)).toString();
            this.drawString(msg, RX, this.centerY - 60, c);
        }
        if (seatID >= 0 && seatID <= 1 && ac.haveFlare()) {
            c = (ac.isFlarePreparation() ? colorInactive : colorActive);
            final StringBuilder append7 = new StringBuilder().append("Flare : ");
            final MCH_Config config9 = MCH_MOD.config;
            msg = append7.append(MCH_KeyName.getDescOrName(MCH_Config.KeyFlare.prmInt)).toString();
            this.drawString(msg, RX, this.centerY - 50, c);
        }
        if (seatID == 0 && info.haveLandingGear()) {
            if (ac.canFoldLandingGear()) {
                final StringBuilder append8 = new StringBuilder().append("Gear Up : ");
                final MCH_Config config10 = MCH_MOD.config;
                msg = append8.append(MCH_KeyName.getDescOrName(MCH_Config.KeyGearUpDown.prmInt)).toString();
                this.drawString(msg, RX, this.centerY - 40, colorActive);
            }
            else if (ac.canUnfoldLandingGear()) {
                final StringBuilder append9 = new StringBuilder().append("Gear Down : ");
                final MCH_Config config11 = MCH_MOD.config;
                msg = append9.append(MCH_KeyName.getDescOrName(MCH_Config.KeyGearUpDown.prmInt)).toString();
                this.drawString(msg, RX, this.centerY - 40, colorActive);
            }
        }
        final MCH_WeaponSet ws = ac.getCurrentWeapon((Entity)player);
        if (ac.getWeaponNum() > 1) {
            final StringBuilder append10 = new StringBuilder().append("Weapon : ");
            final MCH_Config config12 = MCH_MOD.config;
            msg = append10.append(MCH_KeyName.getDescOrName(MCH_Config.KeySwitchWeapon2.prmInt)).toString();
            this.drawString(msg, LX, this.centerY - 70, colorActive);
        }
        if (ws.getCurrentWeapon().numMode > 0) {
            final StringBuilder append11 = new StringBuilder().append("WeaponMode : ");
            final MCH_Config config13 = MCH_MOD.config;
            msg = append11.append(MCH_KeyName.getDescOrName(MCH_Config.KeySwWeaponMode.prmInt)).toString();
            this.drawString(msg, LX, this.centerY - 60, colorActive);
        }
        if (ac.canSwitchSearchLight((Entity)player)) {
            final StringBuilder append12 = new StringBuilder().append("SearchLight : ");
            final MCH_Config config14 = MCH_MOD.config;
            msg = append12.append(MCH_KeyName.getDescOrName(MCH_Config.KeyCameraMode.prmInt)).toString();
            this.drawString(msg, LX, this.centerY - 50, colorActive);
        }
        else if (ac.canSwitchCameraMode(seatID)) {
            final StringBuilder append13 = new StringBuilder().append("CameraMode : ");
            final MCH_Config config15 = MCH_MOD.config;
            msg = append13.append(MCH_KeyName.getDescOrName(MCH_Config.KeyCameraMode.prmInt)).toString();
            this.drawString(msg, LX, this.centerY - 50, colorActive);
        }
        if (seatID == 0 && ac.getSeatNum() >= 1) {
            int color = colorActive;
            if (info.isEnableParachuting && MCH_Lib.getBlockIdY(ac, 3, -10) == 0) {
                final StringBuilder append14 = new StringBuilder().append("Parachuting : ");
                final MCH_Config config16 = MCH_MOD.config;
                msg = append14.append(MCH_KeyName.getDescOrName(MCH_Config.KeyUnmount.prmInt)).toString();
            }
            else if (ac.canStartRepelling()) {
                final StringBuilder append15 = new StringBuilder().append("Repelling : ");
                final MCH_Config config17 = MCH_MOD.config;
                msg = append15.append(MCH_KeyName.getDescOrName(MCH_Config.KeyUnmount.prmInt)).toString();
                color = -256;
            }
            else {
                final StringBuilder append16 = new StringBuilder().append("Dismount : ");
                final MCH_Config config18 = MCH_MOD.config;
                msg = append16.append(MCH_KeyName.getDescOrName(MCH_Config.KeyUnmount.prmInt)).toString();
            }
            this.drawString(msg, LX, this.centerY - 30, color);
        }
        if ((seatID == 0 && ac.canSwitchFreeLook()) || (seatID > 0 && ac.canSwitchGunnerModeOtherSeat(player))) {
            final StringBuilder append17 = new StringBuilder().append("FreeLook : ");
            final MCH_Config config19 = MCH_MOD.config;
            msg = append17.append(MCH_KeyName.getDescOrName(MCH_Config.KeyFreeLook.prmInt)).toString();
            this.drawString(msg, LX, this.centerY - 20, colorActive);
        }
    }
}
