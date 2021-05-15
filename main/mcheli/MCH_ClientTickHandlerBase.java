package mcheli;

import cpw.mods.fml.relauncher.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import mcheli.aircraft.*;
import net.minecraft.entity.player.*;
import mcheli.wrapper.*;

@SideOnly(Side.CLIENT)
public abstract class MCH_ClientTickHandlerBase
{
    protected Minecraft mc;
    public static float playerRotMinPitch;
    public static float playerRotMaxPitch;
    public static boolean playerRotLimitPitch;
    public static float playerRotMinYaw;
    public static float playerRotMaxYaw;
    public static boolean playerRotLimitYaw;
    private static int mouseWheel;
    
    public abstract void updateKeybind(final MCH_Config p0);
    
    public static void setRotLimitPitch(final float min, final float max, final Entity player) {
        MCH_ClientTickHandlerBase.playerRotMinPitch = min;
        MCH_ClientTickHandlerBase.playerRotMaxPitch = max;
        MCH_ClientTickHandlerBase.playerRotLimitPitch = true;
        if (player != null) {
            player.field_70125_A = MCH_Lib.RNG(player.field_70125_A, MCH_ClientTickHandlerBase.playerRotMinPitch, MCH_ClientTickHandlerBase.playerRotMaxPitch);
        }
    }
    
    public static void setRotLimitYaw(final float min, final float max, final Entity e) {
        MCH_ClientTickHandlerBase.playerRotMinYaw = min;
        MCH_ClientTickHandlerBase.playerRotMaxYaw = max;
        MCH_ClientTickHandlerBase.playerRotLimitYaw = true;
        if (e != null) {
            if (e.field_70125_A < MCH_ClientTickHandlerBase.playerRotMinPitch) {
                e.field_70125_A = MCH_ClientTickHandlerBase.playerRotMinPitch;
                e.field_70127_C = MCH_ClientTickHandlerBase.playerRotMinPitch;
            }
            else if (e.field_70125_A > MCH_ClientTickHandlerBase.playerRotMaxPitch) {
                e.field_70125_A = MCH_ClientTickHandlerBase.playerRotMaxPitch;
                e.field_70127_C = MCH_ClientTickHandlerBase.playerRotMaxPitch;
            }
        }
    }
    
    public static void initRotLimit() {
        MCH_ClientTickHandlerBase.playerRotMinPitch = -90.0f;
        MCH_ClientTickHandlerBase.playerRotMaxPitch = 90.0f;
        MCH_ClientTickHandlerBase.playerRotLimitYaw = false;
        MCH_ClientTickHandlerBase.playerRotMinYaw = -180.0f;
        MCH_ClientTickHandlerBase.playerRotMaxYaw = 180.0f;
        MCH_ClientTickHandlerBase.playerRotLimitYaw = false;
    }
    
    public static void applyRotLimit(final Entity e) {
        if (e != null) {
            if (MCH_ClientTickHandlerBase.playerRotLimitPitch) {
                if (e.field_70125_A < MCH_ClientTickHandlerBase.playerRotMinPitch) {
                    e.field_70125_A = MCH_ClientTickHandlerBase.playerRotMinPitch;
                    e.field_70127_C = MCH_ClientTickHandlerBase.playerRotMinPitch;
                }
                else if (e.field_70125_A > MCH_ClientTickHandlerBase.playerRotMaxPitch) {
                    e.field_70125_A = MCH_ClientTickHandlerBase.playerRotMaxPitch;
                    e.field_70127_C = MCH_ClientTickHandlerBase.playerRotMaxPitch;
                }
            }
            if (MCH_ClientTickHandlerBase.playerRotLimitYaw) {}
        }
    }
    
    public MCH_ClientTickHandlerBase(final Minecraft minecraft) {
        this.mc = minecraft;
    }
    
    public static boolean updateMouseWheel(final int wheel) {
        boolean cancelEvent = false;
        if (wheel != 0) {
            final MCH_Config config = MCH_MOD.config;
            if (MCH_Config.SwitchWeaponWithMouseWheel.prmBool) {
                setMouseWheel(0);
                final EntityPlayer player = (EntityPlayer)Minecraft.func_71410_x().field_71439_g;
                if (player != null) {
                    final MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player);
                    if (ac != null) {
                        final int cwid = ac.getWeaponIDBySeatID(ac.getSeatIdByEntity((Entity)player));
                        final int nwid = ac.getNextWeaponID((Entity)player, 1);
                        if (cwid != nwid) {
                            setMouseWheel(wheel);
                            cancelEvent = true;
                        }
                    }
                }
            }
        }
        return cancelEvent;
    }
    
    protected abstract void onTick(final boolean p0);
    
    public static void playSoundOK() {
        W_McClient.DEF_playSoundFX("random.click", 1.0f, 1.0f);
    }
    
    public static void playSoundNG() {
        W_McClient.MOD_playSoundFX("ng", 1.0f, 1.0f);
    }
    
    public static void playSound(final String name) {
        W_McClient.MOD_playSoundFX(name, 1.0f, 1.0f);
    }
    
    public static void playSound(final String name, final float vol, final float pitch) {
        W_McClient.MOD_playSoundFX(name, vol, pitch);
    }
    
    public static int getMouseWheel() {
        return MCH_ClientTickHandlerBase.mouseWheel;
    }
    
    public static void setMouseWheel(final int mouseWheel) {
        MCH_ClientTickHandlerBase.mouseWheel = mouseWheel;
    }
    
    static {
        MCH_ClientTickHandlerBase.playerRotMinPitch = -90.0f;
        MCH_ClientTickHandlerBase.playerRotMaxPitch = 90.0f;
        MCH_ClientTickHandlerBase.playerRotLimitPitch = false;
        MCH_ClientTickHandlerBase.playerRotMinYaw = -180.0f;
        MCH_ClientTickHandlerBase.playerRotMaxYaw = 180.0f;
        MCH_ClientTickHandlerBase.playerRotLimitYaw = false;
        MCH_ClientTickHandlerBase.mouseWheel = 0;
    }
}
