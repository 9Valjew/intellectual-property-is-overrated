package mcheli.hud;

import net.minecraft.client.*;
import java.util.*;
import net.minecraft.util.*;
import mcheli.*;

public class MCH_HudItemString extends MCH_HudItem
{
    private final String posX;
    private final String posY;
    private final String format;
    private final MCH_HudItemStringArgs[] args;
    private final boolean isCenteredString;
    
    public MCH_HudItemString(final int fileLine, final String posx, final String posy, final String fmt, final String[] arg, final boolean centered) {
        super(fileLine);
        this.posX = posx.toLowerCase();
        this.posY = posy.toLowerCase();
        this.format = fmt;
        final int len = (arg.length < 3) ? 0 : (arg.length - 3);
        this.args = new MCH_HudItemStringArgs[len];
        for (int i = 0; i < len; ++i) {
            this.args[i] = MCH_HudItemStringArgs.toArgs(arg[3 + i]);
        }
        this.isCenteredString = centered;
    }
    
    @Override
    public void execute() {
        final int x = (int)(MCH_HudItemString.centerX + MCH_HudItem.calc(this.posX));
        final int y = (int)(MCH_HudItemString.centerY + MCH_HudItem.calc(this.posY));
        final long dateCount = Minecraft.func_71410_x().field_71439_g.field_70170_p.func_82737_E();
        final int worldTime = (int)((MCH_HudItemString.ac.field_70170_p.func_72820_D() + 6000L) % 24000L);
        final Date date = new Date();
        final Object[] prm = new Object[this.args.length];
        final double hp_per = (MCH_HudItemString.ac.getMaxHP() > 0) ? (MCH_HudItemString.ac.getHP() / MCH_HudItemString.ac.getMaxHP()) : 0.0;
        for (int i = 0; i < prm.length; ++i) {
            switch (this.args[i]) {
                case NAME: {
                    prm[i] = MCH_HudItemString.ac.getAcInfo().displayName;
                    break;
                }
                case ALTITUDE: {
                    prm[i] = MCH_HudItemString.Altitude;
                    break;
                }
                case DATE: {
                    prm[i] = date;
                    break;
                }
                case MC_THOR: {
                    prm[i] = worldTime / 1000;
                    break;
                }
                case MC_TMIN: {
                    prm[i] = worldTime % 1000 * 36 / 10 / 60;
                    break;
                }
                case MC_TSEC: {
                    prm[i] = worldTime % 1000 * 36 / 10 % 60;
                    break;
                }
                case MAX_HP: {
                    prm[i] = MCH_HudItemString.ac.getMaxHP();
                    break;
                }
                case HP: {
                    prm[i] = MCH_HudItemString.ac.getHP();
                    break;
                }
                case HP_PER: {
                    prm[i] = hp_per * 100.0;
                    break;
                }
                case POS_X: {
                    prm[i] = MCH_HudItemString.ac.field_70165_t;
                    break;
                }
                case POS_Y: {
                    prm[i] = MCH_HudItemString.ac.field_70163_u;
                    break;
                }
                case POS_Z: {
                    prm[i] = MCH_HudItemString.ac.field_70161_v;
                    break;
                }
                case MOTION_X: {
                    prm[i] = MCH_HudItemString.ac.field_70159_w;
                    break;
                }
                case MOTION_Y: {
                    prm[i] = MCH_HudItemString.ac.field_70181_x;
                    break;
                }
                case MOTION_Z: {
                    prm[i] = MCH_HudItemString.ac.field_70179_y;
                    break;
                }
                case INVENTORY: {
                    prm[i] = MCH_HudItemString.ac.func_70302_i_();
                    break;
                }
                case WPN_NAME: {
                    prm[i] = MCH_HudItemString.WeaponName;
                    if (MCH_HudItemString.CurrentWeapon == null) {
                        return;
                    }
                    break;
                }
                case WPN_AMMO: {
                    prm[i] = MCH_HudItemString.WeaponAmmo;
                    if (MCH_HudItemString.CurrentWeapon == null) {
                        return;
                    }
                    if (MCH_HudItemString.CurrentWeapon.getAmmoNumMax() <= 0) {
                        return;
                    }
                    break;
                }
                case WPN_RM_AMMO: {
                    prm[i] = MCH_HudItemString.WeaponAllAmmo;
                    if (MCH_HudItemString.CurrentWeapon == null) {
                        return;
                    }
                    if (MCH_HudItemString.CurrentWeapon.getAmmoNumMax() <= 0) {
                        return;
                    }
                    break;
                }
                case RELOAD_PER: {
                    prm[i] = MCH_HudItemString.ReloadPer;
                    if (MCH_HudItemString.CurrentWeapon == null) {
                        return;
                    }
                    break;
                }
                case RELOAD_SEC: {
                    prm[i] = MCH_HudItemString.ReloadSec;
                    if (MCH_HudItemString.CurrentWeapon == null) {
                        return;
                    }
                    break;
                }
                case MORTAR_DIST: {
                    prm[i] = MCH_HudItemString.MortarDist;
                    if (MCH_HudItemString.CurrentWeapon == null) {
                        return;
                    }
                    break;
                }
                case MC_VER: {
                    prm[i] = "1.7.10";
                    break;
                }
                case MOD_VER: {
                    prm[i] = MCH_MOD.VER;
                    break;
                }
                case MOD_NAME: {
                    prm[i] = "MC Helicopter MOD";
                    break;
                }
                case YAW: {
                    prm[i] = MCH_Lib.getRotate360(MCH_HudItemString.ac.getRotYaw() + 180.0f);
                    break;
                }
                case PITCH: {
                    prm[i] = -MCH_HudItemString.ac.getRotPitch();
                    break;
                }
                case ROLL: {
                    prm[i] = MathHelper.func_76142_g(MCH_HudItemString.ac.getRotRoll());
                    break;
                }
                case PLYR_YAW: {
                    prm[i] = MCH_Lib.getRotate360(MCH_HudItemString.player.field_70177_z + 180.0f);
                    break;
                }
                case PLYR_PITCH: {
                    prm[i] = -MCH_HudItemString.player.field_70125_A;
                    break;
                }
                case TVM_POS_X: {
                    prm[i] = MCH_HudItemString.TVM_PosX;
                    break;
                }
                case TVM_POS_Y: {
                    prm[i] = MCH_HudItemString.TVM_PosY;
                    break;
                }
                case TVM_POS_Z: {
                    prm[i] = MCH_HudItemString.TVM_PosZ;
                    break;
                }
                case TVM_DIFF: {
                    prm[i] = MCH_HudItemString.TVM_Diff;
                    break;
                }
                case CAM_ZOOM: {
                    prm[i] = MCH_HudItemString.ac.camera.getCameraZoom();
                    break;
                }
                case UAV_DIST: {
                    prm[i] = MCH_HudItemString.UAV_Dist;
                    break;
                }
                case KEY_GUI: {
                    final Object[] array = prm;
                    final int n = i;
                    final MCH_Config config = MCH_MOD.config;
                    array[n] = MCH_KeyName.getDescOrName(MCH_Config.KeyGUI.prmInt);
                    break;
                }
                case THROTTLE: {
                    prm[i] = MCH_HudItemString.ac.getCurrentThrottle() * 100.0;
                    break;
                }
            }
        }
        if (this.isCenteredString) {
            this.drawCenteredString(String.format(this.format, prm), x, y, MCH_HudItemString.colorSetting);
        }
        else {
            this.drawString(String.format(this.format, prm), x, y, MCH_HudItemString.colorSetting);
        }
    }
}
