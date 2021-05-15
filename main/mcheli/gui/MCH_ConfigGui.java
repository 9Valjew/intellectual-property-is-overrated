package mcheli.gui;

import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.client.*;
import java.util.*;
import net.minecraft.entity.*;
import org.lwjgl.input.*;
import mcheli.*;
import mcheli.weapon.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import mcheli.multiplay.*;
import mcheli.aircraft.*;
import mcheli.wrapper.*;
import net.minecraft.client.gui.*;

public class MCH_ConfigGui extends W_GuiContainer
{
    private final EntityPlayer thePlayer;
    private int scaleFactor;
    private MCH_GuiOnOffButton buttonMouseInv;
    private MCH_GuiOnOffButton buttonStickModeHeli;
    private MCH_GuiOnOffButton buttonStickModePlane;
    private MCH_GuiOnOffButton buttonHideKeyBind;
    private MCH_GuiOnOffButton buttonShowHUDTP;
    private MCH_GuiOnOffButton buttonSmoothShading;
    private MCH_GuiOnOffButton buttonShowEntityMarker;
    private MCH_GuiOnOffButton buttonMarkThroughWall;
    private MCH_GuiOnOffButton buttonReplaceCamera;
    private MCH_GuiOnOffButton buttonNewExplosion;
    private MCH_GuiSlider sliderEntityMarkerSize;
    private MCH_GuiSlider sliderBlockMarkerSize;
    private MCH_GuiSlider sliderSensitivity;
    private MCH_GuiSlider[] sliderHitMark;
    private MCH_GuiOnOffButton buttonTestMode;
    private MCH_GuiOnOffButton buttonThrottleHeli;
    private MCH_GuiOnOffButton buttonThrottlePlane;
    private MCH_GuiOnOffButton buttonThrottleTank;
    private MCH_GuiOnOffButton buttonFlightSimMode;
    private MCH_GuiOnOffButton buttonSwitchWeaponWheel;
    private W_GuiButton buttonReloadAircraftInfo;
    private W_GuiButton buttonReloadWeaponInfo;
    private W_GuiButton buttonReloadAllHUD;
    public List<W_GuiButton> listControlButtons;
    public List<W_GuiButton> listRenderButtons;
    public List<W_GuiButton> listKeyBindingButtons;
    public List<W_GuiButton> listDevelopButtons;
    public MCH_GuiList keyBindingList;
    public int waitKeyButtonId;
    public int waitKeyAcceptCount;
    public static final int BUTTON_RENDER = 50;
    public static final int BUTTON_KEY_BINDING = 51;
    public static final int BUTTON_PREV_CONTROL = 52;
    public static final int BUTTON_DEVELOP = 55;
    public static final int BUTTON_KEY_LIST = 53;
    public static final int BUTTON_KEY_RESET_ALL = 54;
    public static final int BUTTON_KEY_LIST_BASE = 200;
    public static final int BUTTON_KEY_RESET_BASE = 300;
    public static final int BUTTON_DEV_RELOAD_AC = 400;
    public static final int BUTTON_DEV_RELOAD_WEAPON = 401;
    public static final int BUTTON_DEV_RELOAD_HUD = 402;
    public static final int BUTTON_SAVE_CLOSE = 100;
    public static final int BUTTON_CANCEL = 101;
    public int currentScreenId;
    public static final int SCREEN_CONTROLS = 0;
    public static final int SCREEN_RENDER = 1;
    public static final int SCREEN_KEY_BIND = 2;
    public static final int SCREEN_DEVELOP = 3;
    private int ignoreButtonCounter;
    
    public MCH_ConfigGui(final EntityPlayer player) {
        super(new MCH_ConfigGuiContainer(player));
        this.currentScreenId = 0;
        this.ignoreButtonCounter = 0;
        this.thePlayer = player;
        this.field_146999_f = 330;
        this.field_147000_g = 200;
    }
    
    public void func_73866_w_() {
        super.func_73866_w_();
        this.field_146292_n.clear();
        final int x1 = this.field_147003_i + 10;
        final int x2 = this.field_147003_i + 10 + 150 + 10;
        final int y = this.field_147009_r;
        final int DY = 25;
        this.listControlButtons = new ArrayList<W_GuiButton>();
        this.buttonMouseInv = new MCH_GuiOnOffButton(0, x1, y + 25, 150, 20, "Invert Mouse : ");
        this.sliderSensitivity = new MCH_GuiSlider(0, x1, y + 50, 150, 20, "Sensitivity : %.1f", 0.0f, 0.0f, 30.0f, 0.1f);
        this.buttonFlightSimMode = new MCH_GuiOnOffButton(0, x1, y + 75, 150, 20, "Mouse Flight Sim Mode : ");
        this.buttonSwitchWeaponWheel = new MCH_GuiOnOffButton(0, x1, y + 100, 150, 20, "Switch Weapon Wheel : ");
        this.listControlButtons.add(new W_GuiButton(50, x1, y + 125, 150, 20, "Render Settings >>"));
        this.listControlButtons.add(new W_GuiButton(51, x1, y + 150, 150, 20, "Key Binding >>"));
        this.listControlButtons.add(new W_GuiButton(55, x2, y + 150, 150, 20, "Development >>"));
        this.buttonTestMode = new MCH_GuiOnOffButton(0, x1, y + 175, 150, 20, "Test Mode : ");
        this.buttonStickModeHeli = new MCH_GuiOnOffButton(0, x2, y + 25, 150, 20, "Stick Mode Heli : ");
        this.buttonStickModePlane = new MCH_GuiOnOffButton(0, x2, y + 50, 150, 20, "Stick Mode Plane : ");
        this.buttonThrottleHeli = new MCH_GuiOnOffButton(0, x2, y + 75, 150, 20, "Throttle Down Heli : ");
        this.buttonThrottlePlane = new MCH_GuiOnOffButton(0, x2, y + 100, 150, 20, "Throttle Down Plane : ");
        this.buttonThrottleTank = new MCH_GuiOnOffButton(0, x2, y + 125, 150, 20, "Throttle Down Tank : ");
        this.listControlButtons.add(this.buttonMouseInv);
        this.listControlButtons.add(this.buttonStickModeHeli);
        this.listControlButtons.add(this.buttonStickModePlane);
        this.listControlButtons.add(this.sliderSensitivity);
        this.listControlButtons.add(this.buttonThrottleHeli);
        this.listControlButtons.add(this.buttonThrottlePlane);
        this.listControlButtons.add(this.buttonThrottleTank);
        this.listControlButtons.add(this.buttonTestMode);
        this.listControlButtons.add(this.buttonFlightSimMode);
        this.listControlButtons.add(this.buttonSwitchWeaponWheel);
        for (final GuiButton b : this.listControlButtons) {
            this.field_146292_n.add(b);
        }
        this.listRenderButtons = new ArrayList<W_GuiButton>();
        this.buttonShowHUDTP = new MCH_GuiOnOffButton(0, x1, y + 25, 150, 20, "Show HUD Third Person : ");
        this.buttonHideKeyBind = new MCH_GuiOnOffButton(0, x1, y + 50, 150, 20, "Hide Key Binding : ");
        this.sliderHitMark = new MCH_GuiSlider[] { new MCH_GuiSlider(0, x1 + 0, y + 125, 75, 20, "Alpha:%.0f", 0.0f, 0.0f, 255.0f, 16.0f), new MCH_GuiSlider(0, x1 + 75, y + 75, 75, 20, "Red:%.0f", 0.0f, 0.0f, 255.0f, 16.0f), new MCH_GuiSlider(0, x1 + 75, y + 100, 75, 20, "Green:%.0f", 0.0f, 0.0f, 255.0f, 16.0f), new MCH_GuiSlider(0, x1 + 75, y + 125, 75, 20, "Blue:%.0f", 0.0f, 0.0f, 255.0f, 16.0f) };
        this.buttonReplaceCamera = new MCH_GuiOnOffButton(0, x1, y + 150, 150, 20, "Change Camera Pos : ");
        this.listRenderButtons.add(new W_GuiButton(52, x1, y + 175, 90, 20, "Controls <<"));
        this.buttonSmoothShading = new MCH_GuiOnOffButton(0, x2, y + 25, 150, 20, "Smooth Shading : ");
        this.buttonShowEntityMarker = new MCH_GuiOnOffButton(0, x2, y + 50, 150, 20, "Show Entity Maker : ");
        this.sliderEntityMarkerSize = new MCH_GuiSlider(0, x2 + 30, y + 75, 120, 20, "Entity Marker Size:%.0f", 10.0f, 0.0f, 30.0f, 1.0f);
        this.sliderBlockMarkerSize = new MCH_GuiSlider(0, x2 + 60, y + 100, 90, 20, "Block Marker Size:%.0f", 10.0f, 0.0f, 20.0f, 1.0f);
        this.buttonMarkThroughWall = new MCH_GuiOnOffButton(0, x2 + 30, y + 100, 120, 20, "Mark Through Wall : ");
        this.buttonNewExplosion = new MCH_GuiOnOffButton(0, x2, y + 150, 150, 20, "Default Explosion : ");
        this.listRenderButtons.add(this.buttonShowHUDTP);
        for (int i = 0; i < this.sliderHitMark.length; ++i) {
            this.listRenderButtons.add(this.sliderHitMark[i]);
        }
        this.listRenderButtons.add(this.buttonSmoothShading);
        this.listRenderButtons.add(this.buttonHideKeyBind);
        this.listRenderButtons.add(this.buttonShowEntityMarker);
        this.listRenderButtons.add(this.buttonReplaceCamera);
        this.listRenderButtons.add(this.buttonNewExplosion);
        this.listRenderButtons.add(this.sliderEntityMarkerSize);
        this.listRenderButtons.add(this.sliderBlockMarkerSize);
        for (final GuiButton b : this.listRenderButtons) {
            this.field_146292_n.add(b);
        }
        this.listKeyBindingButtons = new ArrayList<W_GuiButton>();
        this.waitKeyButtonId = 0;
        this.waitKeyAcceptCount = 0;
        this.keyBindingList = new MCH_GuiList(53, 7, x1, y + 25 - 2, 310, 150, "");
        this.listKeyBindingButtons.add(this.keyBindingList);
        this.listKeyBindingButtons.add(new W_GuiButton(52, x1, y + 175, 90, 20, "Controls <<"));
        this.listKeyBindingButtons.add(new W_GuiButton(54, x1 + 90, y + 175, 60, 20, "Reset All"));
        final int id = 200;
        final int idr = 300;
        final MCH_GuiListItemKeyBind[] array = new MCH_GuiListItemKeyBind[23];
        final int n = 0;
        final int id2 = 200;
        final int idReset = 300;
        final int posX = x1;
        final String dispStr = "Up";
        final MCH_Config config = MCH_MOD.config;
        array[n] = new MCH_GuiListItemKeyBind(id2, idReset, posX, dispStr, MCH_Config.KeyUp);
        final int n2 = 1;
        final int id3 = 201;
        final int idReset2 = 301;
        final int posX2 = x1;
        final String dispStr2 = "Down";
        final MCH_Config config2 = MCH_MOD.config;
        array[n2] = new MCH_GuiListItemKeyBind(id3, idReset2, posX2, dispStr2, MCH_Config.KeyDown);
        final int n3 = 2;
        final int id4 = 202;
        final int idReset3 = 302;
        final int posX3 = x1;
        final String dispStr3 = "Right";
        final MCH_Config config3 = MCH_MOD.config;
        array[n3] = new MCH_GuiListItemKeyBind(id4, idReset3, posX3, dispStr3, MCH_Config.KeyRight);
        final int n4 = 3;
        final int id5 = 203;
        final int idReset4 = 303;
        final int posX4 = x1;
        final String dispStr4 = "Left";
        final MCH_Config config4 = MCH_MOD.config;
        array[n4] = new MCH_GuiListItemKeyBind(id5, idReset4, posX4, dispStr4, MCH_Config.KeyLeft);
        final int n5 = 4;
        final int id6 = 204;
        final int idReset5 = 304;
        final int posX5 = x1;
        final String dispStr5 = "Switch Gunner";
        final MCH_Config config5 = MCH_MOD.config;
        array[n5] = new MCH_GuiListItemKeyBind(id6, idReset5, posX5, dispStr5, MCH_Config.KeySwitchMode);
        final int n6 = 5;
        final int id7 = 205;
        final int idReset6 = 305;
        final int posX6 = x1;
        final String dispStr6 = "Switch Hovering";
        final MCH_Config config6 = MCH_MOD.config;
        array[n6] = new MCH_GuiListItemKeyBind(id7, idReset6, posX6, dispStr6, MCH_Config.KeySwitchHovering);
        final int n7 = 6;
        final int id8 = 206;
        final int idReset7 = 306;
        final int posX7 = x1;
        final String dispStr7 = "Switch Weapon1";
        final MCH_Config config7 = MCH_MOD.config;
        array[n7] = new MCH_GuiListItemKeyBind(id8, idReset7, posX7, dispStr7, MCH_Config.KeySwitchWeapon1);
        final int n8 = 7;
        final int id9 = 207;
        final int idReset8 = 307;
        final int posX8 = x1;
        final String dispStr8 = "Switch Weapon2";
        final MCH_Config config8 = MCH_MOD.config;
        array[n8] = new MCH_GuiListItemKeyBind(id9, idReset8, posX8, dispStr8, MCH_Config.KeySwitchWeapon2);
        final int n9 = 8;
        final int id10 = 208;
        final int idReset9 = 308;
        final int posX9 = x1;
        final String dispStr9 = "Switch Weapon Mode";
        final MCH_Config config9 = MCH_MOD.config;
        array[n9] = new MCH_GuiListItemKeyBind(id10, idReset9, posX9, dispStr9, MCH_Config.KeySwWeaponMode);
        final int n10 = 9;
        final int id11 = 209;
        final int idReset10 = 309;
        final int posX10 = x1;
        final String dispStr10 = "Zoom / Fold Wing";
        final MCH_Config config10 = MCH_MOD.config;
        array[n10] = new MCH_GuiListItemKeyBind(id11, idReset10, posX10, dispStr10, MCH_Config.KeyZoom);
        final int n11 = 10;
        final int id12 = 210;
        final int idReset11 = 310;
        final int posX11 = x1;
        final String dispStr11 = "Camera Mode";
        final MCH_Config config11 = MCH_MOD.config;
        array[n11] = new MCH_GuiListItemKeyBind(id12, idReset11, posX11, dispStr11, MCH_Config.KeyCameraMode);
        final int n12 = 11;
        final int id13 = 211;
        final int idReset12 = 311;
        final int posX12 = x1;
        final String dispStr12 = "Unmount Mobs";
        final MCH_Config config12 = MCH_MOD.config;
        array[n12] = new MCH_GuiListItemKeyBind(id13, idReset12, posX12, dispStr12, MCH_Config.KeyUnmount);
        final int n13 = 12;
        final int id14 = 212;
        final int idReset13 = 312;
        final int posX13 = x1;
        final String dispStr13 = "Flare";
        final MCH_Config config13 = MCH_MOD.config;
        array[n13] = new MCH_GuiListItemKeyBind(id14, idReset13, posX13, dispStr13, MCH_Config.KeyFlare);
        final int n14 = 13;
        final int id15 = 213;
        final int idReset14 = 313;
        final int posX14 = x1;
        final String dispStr14 = "Vtol / Drop / Fold Blade";
        final MCH_Config config14 = MCH_MOD.config;
        array[n14] = new MCH_GuiListItemKeyBind(id15, idReset14, posX14, dispStr14, MCH_Config.KeyExtra);
        final int n15 = 14;
        final int id16 = 214;
        final int idReset15 = 314;
        final int posX15 = x1;
        final String dispStr15 = "Third Person Distance Up";
        final MCH_Config config15 = MCH_MOD.config;
        array[n15] = new MCH_GuiListItemKeyBind(id16, idReset15, posX15, dispStr15, MCH_Config.KeyCameraDistUp);
        final int n16 = 15;
        final int id17 = 215;
        final int idReset16 = 315;
        final int posX16 = x1;
        final String dispStr16 = "Third Person Distance Down";
        final MCH_Config config16 = MCH_MOD.config;
        array[n16] = new MCH_GuiListItemKeyBind(id17, idReset16, posX16, dispStr16, MCH_Config.KeyCameraDistDown);
        final int n17 = 16;
        final int id18 = 216;
        final int idReset17 = 316;
        final int posX17 = x1;
        final String dispStr17 = "Switch Free Look";
        final MCH_Config config17 = MCH_MOD.config;
        array[n17] = new MCH_GuiListItemKeyBind(id18, idReset17, posX17, dispStr17, MCH_Config.KeyFreeLook);
        final int n18 = 17;
        final int id19 = 217;
        final int idReset18 = 317;
        final int posX18 = x1;
        final String dispStr18 = "Open GUI";
        final MCH_Config config18 = MCH_MOD.config;
        array[n18] = new MCH_GuiListItemKeyBind(id19, idReset18, posX18, dispStr18, MCH_Config.KeyGUI);
        final int n19 = 18;
        final int id20 = 218;
        final int idReset19 = 318;
        final int posX19 = x1;
        final String dispStr19 = "Gear Up Down";
        final MCH_Config config19 = MCH_MOD.config;
        array[n19] = new MCH_GuiListItemKeyBind(id20, idReset19, posX19, dispStr19, MCH_Config.KeyGearUpDown);
        final int n20 = 19;
        final int id21 = 219;
        final int idReset20 = 319;
        final int posX20 = x1;
        final String dispStr20 = "Put entity in the rack";
        final MCH_Config config20 = MCH_MOD.config;
        array[n20] = new MCH_GuiListItemKeyBind(id21, idReset20, posX20, dispStr20, MCH_Config.KeyPutToRack);
        final int n21 = 20;
        final int id22 = 220;
        final int idReset21 = 320;
        final int posX21 = x1;
        final String dispStr21 = "Drop entity from the rack";
        final MCH_Config config21 = MCH_MOD.config;
        array[n21] = new MCH_GuiListItemKeyBind(id22, idReset21, posX21, dispStr21, MCH_Config.KeyDownFromRack);
        final int n22 = 21;
        final int id23 = 221;
        final int idReset22 = 321;
        final int posX22 = x1;
        final String dispStr22 = "[MP]Score board";
        final MCH_Config config22 = MCH_MOD.config;
        array[n22] = new MCH_GuiListItemKeyBind(id23, idReset22, posX22, dispStr22, MCH_Config.KeyScoreboard);
        final int n23 = 22;
        final int id24 = 222;
        final int idReset23 = 322;
        final int posX23 = x1;
        final String dispStr23 = "[MP][OP]Multiplay manager";
        final MCH_Config config23 = MCH_MOD.config;
        array[n23] = new MCH_GuiListItemKeyBind(id24, idReset23, posX23, dispStr23, MCH_Config.KeyMultiplayManager);
        final MCH_GuiListItemKeyBind[] arr$;
        final MCH_GuiListItemKeyBind[] listKeyBindItems = arr$ = array;
        for (final MCH_GuiListItemKeyBind item : arr$) {
            this.keyBindingList.addItem(item);
        }
        for (final GuiButton b2 : this.listKeyBindingButtons) {
            this.field_146292_n.add(b2);
        }
        this.listDevelopButtons = new ArrayList<W_GuiButton>();
        if (Minecraft.func_71410_x().func_71356_B()) {
            this.buttonReloadAircraftInfo = new W_GuiButton(400, x1, y + 50, 150, 20, "Reload aircraft setting");
            this.buttonReloadWeaponInfo = new W_GuiButton(401, x1, y + 75, 150, 20, "Reload All Weapons");
            this.buttonReloadAllHUD = new W_GuiButton(402, x1, y + 100, 150, 20, "Reload All HUD");
            this.listDevelopButtons.add(this.buttonReloadAircraftInfo);
            this.listDevelopButtons.add(this.buttonReloadWeaponInfo);
            this.listDevelopButtons.add(this.buttonReloadAllHUD);
        }
        this.listDevelopButtons.add(new W_GuiButton(52, x1, y + 175, 90, 20, "Controls <<"));
        for (final GuiButton b2 : this.listDevelopButtons) {
            this.field_146292_n.add(b2);
        }
        this.field_146292_n.add(new GuiButton(100, x2, y + 175, 80, 20, "Save & Close"));
        this.field_146292_n.add(new GuiButton(101, x2 + 90, y + 175, 60, 20, "Cancel"));
        this.switchScreen(0);
        this.applySwitchScreen();
        this.getAllStatusFromConfig();
    }
    
    public boolean canButtonClick() {
        return this.ignoreButtonCounter <= 0;
    }
    
    public void getAllStatusFromConfig() {
        final MCH_GuiOnOffButton buttonMouseInv = this.buttonMouseInv;
        final MCH_Config config = MCH_MOD.config;
        buttonMouseInv.setOnOff(MCH_Config.InvertMouse.prmBool);
        final MCH_GuiOnOffButton buttonStickModeHeli = this.buttonStickModeHeli;
        final MCH_Config config2 = MCH_MOD.config;
        buttonStickModeHeli.setOnOff(MCH_Config.MouseControlStickModeHeli.prmBool);
        final MCH_GuiOnOffButton buttonStickModePlane = this.buttonStickModePlane;
        final MCH_Config config3 = MCH_MOD.config;
        buttonStickModePlane.setOnOff(MCH_Config.MouseControlStickModePlane.prmBool);
        final MCH_GuiSlider sliderSensitivity = this.sliderSensitivity;
        final MCH_Config config4 = MCH_MOD.config;
        sliderSensitivity.setSliderValue((float)MCH_Config.MouseSensitivity.prmDouble);
        final MCH_GuiOnOffButton buttonShowHUDTP = this.buttonShowHUDTP;
        final MCH_Config config5 = MCH_MOD.config;
        buttonShowHUDTP.setOnOff(MCH_Config.DisplayHUDThirdPerson.prmBool);
        final MCH_GuiOnOffButton buttonSmoothShading = this.buttonSmoothShading;
        final MCH_Config config6 = MCH_MOD.config;
        buttonSmoothShading.setOnOff(MCH_Config.SmoothShading.prmBool);
        final MCH_GuiOnOffButton buttonHideKeyBind = this.buttonHideKeyBind;
        final MCH_Config config7 = MCH_MOD.config;
        buttonHideKeyBind.setOnOff(MCH_Config.HideKeybind.prmBool);
        final MCH_GuiOnOffButton buttonShowEntityMarker = this.buttonShowEntityMarker;
        final MCH_Config config8 = MCH_MOD.config;
        buttonShowEntityMarker.setOnOff(MCH_Config.DisplayEntityMarker.prmBool);
        final MCH_GuiOnOffButton buttonMarkThroughWall = this.buttonMarkThroughWall;
        final MCH_Config config9 = MCH_MOD.config;
        buttonMarkThroughWall.setOnOff(MCH_Config.DisplayMarkThroughWall.prmBool);
        final MCH_GuiSlider sliderEntityMarkerSize = this.sliderEntityMarkerSize;
        final MCH_Config config10 = MCH_MOD.config;
        sliderEntityMarkerSize.setSliderValue((float)MCH_Config.EntityMarkerSize.prmDouble);
        final MCH_GuiSlider sliderBlockMarkerSize = this.sliderBlockMarkerSize;
        final MCH_Config config11 = MCH_MOD.config;
        sliderBlockMarkerSize.setSliderValue((float)MCH_Config.BlockMarkerSize.prmDouble);
        final MCH_GuiOnOffButton buttonReplaceCamera = this.buttonReplaceCamera;
        final MCH_Config config12 = MCH_MOD.config;
        buttonReplaceCamera.setOnOff(MCH_Config.ReplaceRenderViewEntity.prmBool);
        final MCH_GuiOnOffButton buttonNewExplosion = this.buttonNewExplosion;
        final MCH_Config config13 = MCH_MOD.config;
        buttonNewExplosion.setOnOff(MCH_Config.DefaultExplosionParticle.prmBool);
        final MCH_GuiSlider mch_GuiSlider = this.sliderHitMark[0];
        final MCH_Config config14 = MCH_MOD.config;
        mch_GuiSlider.setSliderValue(MCH_Config.hitMarkColorAlpha * 255.0f);
        final MCH_GuiSlider mch_GuiSlider2 = this.sliderHitMark[1];
        final MCH_Config config15 = MCH_MOD.config;
        mch_GuiSlider2.setSliderValue(MCH_Config.hitMarkColorRGB >> 16 & 0xFF);
        final MCH_GuiSlider mch_GuiSlider3 = this.sliderHitMark[2];
        final MCH_Config config16 = MCH_MOD.config;
        mch_GuiSlider3.setSliderValue(MCH_Config.hitMarkColorRGB >> 8 & 0xFF);
        final MCH_GuiSlider mch_GuiSlider4 = this.sliderHitMark[3];
        final MCH_Config config17 = MCH_MOD.config;
        mch_GuiSlider4.setSliderValue(MCH_Config.hitMarkColorRGB >> 0 & 0xFF);
        final MCH_GuiOnOffButton buttonThrottleHeli = this.buttonThrottleHeli;
        final MCH_Config config18 = MCH_MOD.config;
        buttonThrottleHeli.setOnOff(MCH_Config.AutoThrottleDownHeli.prmBool);
        final MCH_GuiOnOffButton buttonThrottlePlane = this.buttonThrottlePlane;
        final MCH_Config config19 = MCH_MOD.config;
        buttonThrottlePlane.setOnOff(MCH_Config.AutoThrottleDownPlane.prmBool);
        final MCH_GuiOnOffButton buttonThrottleTank = this.buttonThrottleTank;
        final MCH_Config config20 = MCH_MOD.config;
        buttonThrottleTank.setOnOff(MCH_Config.AutoThrottleDownTank.prmBool);
        final MCH_GuiOnOffButton buttonTestMode = this.buttonTestMode;
        final MCH_Config config21 = MCH_MOD.config;
        buttonTestMode.setOnOff(MCH_Config.TestMode.prmBool);
        final MCH_GuiOnOffButton buttonFlightSimMode = this.buttonFlightSimMode;
        final MCH_Config config22 = MCH_MOD.config;
        buttonFlightSimMode.setOnOff(MCH_Config.MouseControlFlightSimMode.prmBool);
        final MCH_GuiOnOffButton buttonSwitchWeaponWheel = this.buttonSwitchWeaponWheel;
        final MCH_Config config23 = MCH_MOD.config;
        buttonSwitchWeaponWheel.setOnOff(MCH_Config.SwitchWeaponWithMouseWheel.prmBool);
    }
    
    public void saveAndApplyConfig() {
        final int n = 0;
        final MCH_Config config = MCH_MOD.config;
        MCH_Config.InvertMouse.setPrm(this.buttonMouseInv.getOnOff());
        final MCH_Config config2 = MCH_MOD.config;
        MCH_Config.MouseControlStickModeHeli.setPrm(this.buttonStickModeHeli.getOnOff());
        final MCH_Config config3 = MCH_MOD.config;
        MCH_Config.MouseControlStickModePlane.setPrm(this.buttonStickModePlane.getOnOff());
        final MCH_Config config4 = MCH_MOD.config;
        MCH_Config.MouseControlFlightSimMode.setPrm(this.buttonFlightSimMode.getOnOff());
        final MCH_Config config5 = MCH_MOD.config;
        MCH_Config.SwitchWeaponWithMouseWheel.setPrm(this.buttonSwitchWeaponWheel.getOnOff());
        final MCH_Config config6 = MCH_MOD.config;
        MCH_Config.MouseSensitivity.setPrm(this.sliderSensitivity.getSliderValueInt(1));
        final MCH_Config config7 = MCH_MOD.config;
        MCH_Config.DisplayHUDThirdPerson.setPrm(this.buttonShowHUDTP.getOnOff());
        final MCH_Config config8 = MCH_MOD.config;
        MCH_Config.SmoothShading.setPrm(this.buttonSmoothShading.getOnOff());
        final MCH_Config config9 = MCH_MOD.config;
        MCH_Config.HideKeybind.setPrm(this.buttonHideKeyBind.getOnOff());
        final MCH_Config config10 = MCH_MOD.config;
        MCH_Config.DisplayEntityMarker.setPrm(this.buttonShowEntityMarker.getOnOff());
        final MCH_Config config11 = MCH_MOD.config;
        MCH_Config.DisplayMarkThroughWall.setPrm(this.buttonMarkThroughWall.getOnOff());
        final MCH_Config config12 = MCH_MOD.config;
        MCH_Config.EntityMarkerSize.setPrm(this.sliderEntityMarkerSize.getSliderValueInt(1));
        final MCH_Config config13 = MCH_MOD.config;
        MCH_Config.BlockMarkerSize.setPrm(this.sliderBlockMarkerSize.getSliderValueInt(1));
        final MCH_Config config14 = MCH_MOD.config;
        MCH_Config.ReplaceRenderViewEntity.setPrm(this.buttonReplaceCamera.getOnOff());
        final MCH_Config config15 = MCH_MOD.config;
        MCH_Config.DefaultExplosionParticle.setPrm(this.buttonNewExplosion.getOnOff());
        final float a = this.sliderHitMark[0].getSliderValue();
        final int r = (int)this.sliderHitMark[1].getSliderValue();
        final int g = (int)this.sliderHitMark[2].getSliderValue();
        final int b = (int)this.sliderHitMark[3].getSliderValue();
        final MCH_Config config16 = MCH_MOD.config;
        MCH_Config.hitMarkColorAlpha = a / 255.0f;
        final MCH_Config config17 = MCH_MOD.config;
        MCH_Config.hitMarkColorRGB = (r << 16 | g << 8 | b);
        final MCH_Config config18 = MCH_MOD.config;
        MCH_Config.HitMarkColor.setPrm(String.format("%d, %d, %d, %d", (int)a, r, g, b));
        final MCH_Config config19 = MCH_MOD.config;
        final boolean b2 = MCH_Config.AutoThrottleDownHeli.prmBool;
        final MCH_Config config20 = MCH_MOD.config;
        final boolean b3 = MCH_Config.AutoThrottleDownPlane.prmBool;
        final MCH_Config config21 = MCH_MOD.config;
        MCH_Config.AutoThrottleDownHeli.setPrm(this.buttonThrottleHeli.getOnOff());
        final MCH_Config config22 = MCH_MOD.config;
        MCH_Config.AutoThrottleDownPlane.setPrm(this.buttonThrottlePlane.getOnOff());
        final MCH_Config config23 = MCH_MOD.config;
        MCH_Config.AutoThrottleDownTank.setPrm(this.buttonThrottleTank.getOnOff());
        final boolean b4 = b2;
        final MCH_Config config24 = MCH_MOD.config;
        Label_0499: {
            if (b4 == MCH_Config.AutoThrottleDownHeli.prmBool) {
                final boolean b5 = b3;
                final MCH_Config config25 = MCH_MOD.config;
                if (b5 == MCH_Config.AutoThrottleDownPlane.prmBool) {
                    break Label_0499;
                }
            }
            this.sendClientSettings();
        }
        for (int i = 0; i < this.keyBindingList.getItemNum(); ++i) {
            ((MCH_GuiListItemKeyBind)this.keyBindingList.getItem(i)).applyKeycode();
        }
        MCH_ClientCommonTickHandler.instance.updatekeybind(MCH_MOD.config);
        final MCH_Config config26 = MCH_MOD.config;
        MCH_Config.TestMode.setPrm(this.buttonTestMode.getOnOff());
        MCH_MOD.config.write();
    }
    
    public void switchScreen(final int screenID) {
        this.waitKeyButtonId = 0;
        this.currentScreenId = screenID;
        for (final W_GuiButton b : this.listControlButtons) {
            b.setVisible(false);
        }
        for (final W_GuiButton b : this.listRenderButtons) {
            b.setVisible(false);
        }
        for (final W_GuiButton b : this.listKeyBindingButtons) {
            b.setVisible(false);
        }
        for (final W_GuiButton b : this.listDevelopButtons) {
            b.setVisible(false);
        }
        this.ignoreButtonCounter = 3;
    }
    
    public void applySwitchScreen() {
        switch (this.currentScreenId) {
            case 1: {
                for (final W_GuiButton b : this.listRenderButtons) {
                    b.setVisible(true);
                }
                break;
            }
            case 3: {
                for (final W_GuiButton b : this.listDevelopButtons) {
                    b.setVisible(true);
                }
                break;
            }
            case 2: {
                for (final W_GuiButton b : this.listKeyBindingButtons) {
                    b.setVisible(true);
                }
                break;
            }
            default: {
                for (final W_GuiButton b : this.listControlButtons) {
                    b.setVisible(true);
                }
                break;
            }
        }
    }
    
    public void sendClientSettings() {
        if (this.field_146297_k.field_71439_g != null) {
            final MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)this.field_146297_k.field_71439_g);
            if (ac != null) {
                final int seatId = ac.getSeatIdByEntity((Entity)this.field_146297_k.field_71439_g);
                if (seatId == 0) {
                    ac.updateClientSettings(seatId);
                }
            }
        }
    }
    
    public void func_73869_a(final char a, final int code) {
        if (this.waitKeyButtonId != 0) {
            if (code != 1) {
                super.func_73869_a(a, code);
            }
            this.acceptKeycode(code);
            this.waitKeyButtonId = 0;
        }
        else {
            super.func_73869_a(a, code);
        }
    }
    
    protected void func_73864_a(final int par1, final int par2, final int par3) {
        super.func_73864_a(par1, par2, par3);
        if (this.waitKeyButtonId != 0 && this.waitKeyAcceptCount == 0) {
            this.acceptKeycode(par3 - 100);
            this.waitKeyButtonId = 0;
        }
    }
    
    public void acceptKeycode(final int code) {
        if (code != 1 && this.field_146297_k.field_71462_r instanceof MCH_ConfigGui) {
            final MCH_GuiListItemKeyBind kb = (MCH_GuiListItemKeyBind)this.keyBindingList.getItem(this.waitKeyButtonId - 200);
            if (kb != null) {
                kb.setKeycode(code);
            }
        }
    }
    
    public void func_146274_d() {
        super.func_146274_d();
        if (this.waitKeyButtonId != 0) {
            return;
        }
        final int var16 = Mouse.getEventDWheel();
        if (var16 != 0) {
            if (var16 > 0) {
                this.keyBindingList.scrollDown(2.0f);
            }
            else if (var16 < 0) {
                this.keyBindingList.scrollUp(2.0f);
            }
        }
    }
    
    public void func_73876_c() {
        super.func_73876_c();
        if (this.waitKeyAcceptCount > 0) {
            --this.waitKeyAcceptCount;
        }
        if (this.ignoreButtonCounter > 0) {
            --this.ignoreButtonCounter;
            if (this.ignoreButtonCounter == 0) {
                this.applySwitchScreen();
            }
        }
    }
    
    public void func_146281_b() {
        super.func_146281_b();
    }
    
    protected void func_146284_a(final GuiButton button) {
        try {
            super.func_146284_a(button);
            if (!button.field_146124_l) {
                return;
            }
            if (this.waitKeyButtonId != 0) {
                return;
            }
            if (!this.canButtonClick()) {
                return;
            }
            switch (button.field_146127_k) {
                case 50: {
                    this.switchScreen(1);
                    break;
                }
                case 51: {
                    this.switchScreen(2);
                    break;
                }
                case 52: {
                    this.switchScreen(0);
                    break;
                }
                case 55: {
                    this.switchScreen(3);
                    break;
                }
                case 100: {
                    this.saveAndApplyConfig();
                    this.field_146297_k.field_71439_g.func_71053_j();
                    break;
                }
                case 101: {
                    this.field_146297_k.field_71439_g.func_71053_j();
                    break;
                }
                case 53: {
                    final MCH_GuiListItem item = this.keyBindingList.lastPushItem;
                    if (item != null) {
                        final MCH_GuiListItemKeyBind kb = (MCH_GuiListItemKeyBind)item;
                        if (kb.lastPushButton != null) {
                            final int kb_num = this.keyBindingList.getItemNum();
                            if (kb.lastPushButton.field_146127_k >= 200 && kb.lastPushButton.field_146127_k < 200 + kb_num) {
                                this.waitKeyButtonId = kb.lastPushButton.field_146127_k;
                                this.waitKeyAcceptCount = 5;
                            }
                            else if (kb.lastPushButton.field_146127_k >= 300 && kb.lastPushButton.field_146127_k < 300 + kb_num) {
                                kb.resetKeycode();
                            }
                            kb.lastPushButton = null;
                        }
                        break;
                    }
                    break;
                }
                case 54: {
                    for (int i = 0; i < this.keyBindingList.getItemNum(); ++i) {
                        ((MCH_GuiListItemKeyBind)this.keyBindingList.getItem(i)).resetKeycode();
                    }
                    break;
                }
                case 402: {
                    MCH_MOD.proxy.reloadHUD();
                }
                case 400: {
                    MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)this.thePlayer);
                    if (ac != null && ac.getAcInfo() != null) {
                        final String name = ac.getAcInfo().name;
                        MCH_Lib.DbgLog(true, "MCH_BaseInfo.reload : " + name, new Object[0]);
                        final List list = this.field_146297_k.field_71441_e.field_72996_f;
                        for (int j = 0; j < list.size(); ++j) {
                            if (list.get(j) instanceof MCH_EntityAircraft) {
                                ac = list.get(j);
                                if (ac.getAcInfo() != null && ac.getAcInfo().name.equals(name)) {
                                    ac.getAcInfo().reload();
                                    ac.changeType(name);
                                    ac.onAcInfoReloaded();
                                }
                            }
                        }
                        MCH_PacketNotifyInfoReloaded.sendRealodAc();
                    }
                    this.field_146297_k.field_71439_g.func_71053_j();
                    break;
                }
                case 401: {
                    MCH_Lib.DbgLog(true, "MCH_BaseInfo.reload all weapon info.", new Object[0]);
                    MCH_PacketNotifyInfoReloaded.sendRealodAllWeapon();
                    MCH_WeaponInfoManager.reload();
                    final List list2 = this.field_146297_k.field_71441_e.field_72996_f;
                    for (int k = 0; k < list2.size(); ++k) {
                        if (list2.get(k) instanceof MCH_EntityAircraft) {
                            final MCH_EntityAircraft ac = list2.get(k);
                            if (ac.getAcInfo() != null) {
                                ac.getAcInfo().reload();
                                ac.changeType(ac.getAcInfo().name);
                                ac.onAcInfoReloaded();
                            }
                        }
                    }
                    this.field_146297_k.field_71439_g.func_71053_j();
                    break;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean func_73868_f() {
        return true;
    }
    
    protected void func_146979_b(final int par1, final int par2) {
        super.func_146979_b(par1, par2);
        this.drawString("MC Helicopter MOD Options", 10, 10, 16777215);
        if (this.currentScreenId == 0) {
            this.drawString("< Controls >", 170, 10, 16777215);
        }
        else if (this.currentScreenId == 1) {
            this.drawString("< Render >", 170, 10, 16777215);
            this.drawString("Hit Mark", 10, 75, 16777215);
            int color = 0;
            color |= (int)this.sliderHitMark[0].getSliderValue() << 24;
            color |= (int)this.sliderHitMark[1].getSliderValue() << 16;
            color |= (int)this.sliderHitMark[2].getSliderValue() << 8;
            color |= (int)this.sliderHitMark[3].getSliderValue() << 0;
            this.drawSampleHitMark(40, 105, color);
            double size = this.sliderEntityMarkerSize.getSliderValue();
            double x = 170.0 + (30.0 - size) / 2.0;
            double y = this.sliderEntityMarkerSize.field_146129_i - this.sliderEntityMarkerSize.getHeight();
            final double[] ls = { x + size, y, x, y, x + size / 2.0, y + size };
            this.drawLine(ls, -65536, 4);
            size = this.sliderBlockMarkerSize.getSliderValue();
            x = 185.0;
            y = this.sliderBlockMarkerSize.field_146129_i;
            color = -65536;
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glDisable(3553);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color >> 0 & 0xFF), (byte)(color >> 24 & 0xFF));
            Tessellator.field_78398_a.func_78371_b(1);
            MCH_GuiTargetMarker.drawRhombus(Tessellator.field_78398_a, 15, x, y, this.field_73735_i, size, color);
            Tessellator.field_78398_a.func_78381_a();
            GL11.glEnable(3553);
            GL11.glDisable(3042);
            GL11.glColor4b((byte)(-1), (byte)(-1), (byte)(-1), (byte)(-1));
            GL11.glPopMatrix();
        }
        else if (this.currentScreenId == 2) {
            this.drawString("< Key Binding >", 170, 10, 16777215);
            if (this.waitKeyButtonId != 0) {
                func_73734_a(30, 30, this.field_146999_f - 30, this.field_147000_g - 30, -533712848);
                final String msg = "Please ant key or mouse button.";
                final int w = this.getStringWidth(msg);
                this.drawString(msg, (this.field_146999_f - w) / 2, this.field_147000_g / 2 - 4, 16777215);
            }
        }
        else if (this.currentScreenId == 3) {
            this.drawString("< Development >", 170, 10, 16777215);
            this.drawString("Single player only!", 10, 30, 16711680);
            if (this.buttonReloadAircraftInfo != null && this.buttonReloadAircraftInfo.isOnMouseOver()) {
                this.drawString("The following items are not reload.", 170, 30, 16777215);
                final String[] ignoreItems = MCH_AircraftInfo.getCannotReloadItem();
                int y2 = 10;
                for (final String s : ignoreItems) {
                    this.drawString("  " + s, 170, 30 + y2, 16777215);
                    y2 += 10;
                }
            }
        }
    }
    
    protected void func_146976_a(final float var1, final int var2, final int var3) {
        final ScaledResolution scaledresolution = new W_ScaledResolution(this.field_146297_k, this.field_146297_k.field_71443_c, this.field_146297_k.field_71440_d);
        this.scaleFactor = scaledresolution.func_78325_e();
        W_McClient.MOD_bindTexture("textures/gui/config.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final int x = (this.field_146294_l - this.field_146999_f) / 2;
        final int y = (this.field_146295_m - this.field_147000_g) / 2;
        this.drawTexturedModalRectRotate(x, y, this.field_146999_f, this.field_147000_g, 0.0, 0.0, this.field_146999_f, this.field_147000_g, 0.0f, 512.0, 256.0);
    }
    
    public void drawSampleHitMark(final int x, final int y, final int color) {
        final int IVX = 10;
        final int IVY = 10;
        final int SZX = 5;
        final int SZY = 5;
        final double[] ls = { x - IVX, y - IVY, x - SZX, y - SZY, x - IVX, y + IVY, x - SZX, y + SZY, x + IVX, y - IVY, x + SZX, y - SZY, x + IVX, y + IVY, x + SZX, y + SZY };
        this.drawLine(ls, color, 1);
    }
    
    public void drawLine(final double[] line, final int color, final int mode) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color >> 0 & 0xFF), (byte)(color >> 24 & 0xFF));
        final Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78371_b(mode);
        for (int i = 0; i < line.length; i += 2) {
            tessellator.func_78377_a(line[i + 0], line[i + 1], (double)this.field_73735_i);
        }
        tessellator.func_78381_a();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glColor4b((byte)(-1), (byte)(-1), (byte)(-1), (byte)(-1));
        GL11.glPopMatrix();
    }
    
    public void drawTexturedModalRectRotate(final double left, final double top, final double width, final double height, final double uLeft, final double vTop, final double uWidth, final double vHeight, final float rot, final double texWidth, final double texHeight) {
        GL11.glPushMatrix();
        GL11.glTranslated(left + width / 2.0, top + height / 2.0, 0.0);
        GL11.glRotatef(rot, 0.0f, 0.0f, 1.0f);
        final float fw = (float)(1.0 / texWidth);
        final float fh = (float)(1.0 / texHeight);
        final Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78382_b();
        tessellator.func_78374_a(-width / 2.0, height / 2.0, (double)this.field_73735_i, uLeft * fw, (vTop + vHeight) * fh);
        tessellator.func_78374_a(width / 2.0, height / 2.0, (double)this.field_73735_i, (uLeft + uWidth) * fw, (vTop + vHeight) * fh);
        tessellator.func_78374_a(width / 2.0, -height / 2.0, (double)this.field_73735_i, (uLeft + uWidth) * fw, vTop * fh);
        tessellator.func_78374_a(-width / 2.0, -height / 2.0, (double)this.field_73735_i, uLeft * fw, vTop * fh);
        tessellator.func_78381_a();
        GL11.glPopMatrix();
    }
}
