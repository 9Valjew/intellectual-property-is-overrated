package mcheli;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import mcheli.wrapper.*;
import java.util.*;
import net.minecraft.util.*;
import mcheli.helicopter.*;
import mcheli.plane.*;
import mcheli.tank.*;
import mcheli.vehicle.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import java.io.*;

public class MCH_Config
{
    public static String mcPath;
    public static String configFilePath;
    public static boolean DebugLog;
    public static String configVer;
    public static int hitMarkColorRGB;
    public static float hitMarkColorAlpha;
    public static List<Block> bulletBreakableBlocks;
    public static final List<Block> dummyBreakableBlocks;
    public static final List<Material> dummyBreakableMaterials;
    public static List<Block> carNoBreakableBlocks;
    public static List<Block> carBreakableBlocks;
    public static List<Material> carBreakableMaterials;
    public static List<Block> tankNoBreakableBlocks;
    public static List<Block> tankBreakableBlocks;
    public static List<Material> tankBreakableMaterials;
    public static MCH_ConfigPrm KeyUp;
    public static MCH_ConfigPrm KeyDown;
    public static MCH_ConfigPrm KeyRight;
    public static MCH_ConfigPrm KeyLeft;
    public static MCH_ConfigPrm KeySwitchMode;
    public static MCH_ConfigPrm KeySwitchHovering;
    public static MCH_ConfigPrm KeyAttack;
    public static MCH_ConfigPrm KeyUseWeapon;
    public static MCH_ConfigPrm KeySwitchWeapon1;
    public static MCH_ConfigPrm KeySwitchWeapon2;
    public static MCH_ConfigPrm KeySwWeaponMode;
    public static MCH_ConfigPrm KeyZoom;
    public static MCH_ConfigPrm KeyCameraMode;
    public static MCH_ConfigPrm KeyUnmount;
    public static MCH_ConfigPrm KeyFlare;
    public static MCH_ConfigPrm KeyExtra;
    public static MCH_ConfigPrm KeyCameraDistUp;
    public static MCH_ConfigPrm KeyCameraDistDown;
    public static MCH_ConfigPrm KeyFreeLook;
    public static MCH_ConfigPrm KeyGUI;
    public static MCH_ConfigPrm KeyGearUpDown;
    public static MCH_ConfigPrm KeyPutToRack;
    public static MCH_ConfigPrm KeyDownFromRack;
    public static MCH_ConfigPrm KeyScoreboard;
    public static MCH_ConfigPrm KeyMultiplayManager;
    public static List<MCH_ConfigPrm> DamageVs;
    public static List<String> IgnoreBulletHitList;
    public static MCH_ConfigPrm IgnoreBulletHitItem;
    public static DamageFactor[] DamageFactorList;
    public static DamageFactor DamageVsEntity;
    public static DamageFactor DamageVsLiving;
    public static DamageFactor DamageVsPlayer;
    public static DamageFactor DamageVsMCHeliAircraft;
    public static DamageFactor DamageVsMCHeliTank;
    public static DamageFactor DamageVsMCHeliVehicle;
    public static DamageFactor DamageVsMCHeliOther;
    public static DamageFactor DamageAircraftByExternal;
    public static DamageFactor DamageTankByExternal;
    public static DamageFactor DamageVehicleByExternal;
    public static DamageFactor DamageOtherByExternal;
    public static List<MCH_ConfigPrm> CommandPermission;
    public static List<CommandPermission> CommandPermissionList;
    public static MCH_ConfigPrm TestMode;
    public static MCH_ConfigPrm EnableCommand;
    public static MCH_ConfigPrm PlaceableOnSpongeOnly;
    public static MCH_ConfigPrm HideKeybind;
    public static MCH_ConfigPrm ItemDamage;
    public static MCH_ConfigPrm ItemFuel;
    public static MCH_ConfigPrm AutoRepairHP;
    public static MCH_ConfigPrm Collision_DestroyBlock;
    public static MCH_ConfigPrm Explosion_DestroyBlock;
    public static MCH_ConfigPrm Explosion_FlamingBlock;
    public static MCH_ConfigPrm BulletBreakableBlock;
    public static MCH_ConfigPrm Collision_Car_BreakableBlock;
    public static MCH_ConfigPrm Collision_Car_NoBreakableBlock;
    public static MCH_ConfigPrm Collision_Car_BreakableMaterial;
    public static MCH_ConfigPrm Collision_Tank_BreakableBlock;
    public static MCH_ConfigPrm Collision_Tank_NoBreakableBlock;
    public static MCH_ConfigPrm Collision_Tank_BreakableMaterial;
    public static MCH_ConfigPrm Collision_EntityDamage;
    public static MCH_ConfigPrm Collision_EntityTankDamage;
    public static MCH_ConfigPrm LWeaponAutoFire;
    public static MCH_ConfigPrm DismountAll;
    public static MCH_ConfigPrm MountMinecartHeli;
    public static MCH_ConfigPrm MountMinecartPlane;
    public static MCH_ConfigPrm MountMinecartVehicle;
    public static MCH_ConfigPrm MountMinecartTank;
    public static MCH_ConfigPrm AutoThrottleDownHeli;
    public static MCH_ConfigPrm AutoThrottleDownPlane;
    public static MCH_ConfigPrm AutoThrottleDownTank;
    public static MCH_ConfigPrm DisableItemRender;
    public static MCH_ConfigPrm RenderDistanceWeight;
    public static MCH_ConfigPrm MobRenderDistanceWeight;
    public static MCH_ConfigPrm CreativeTabIcon;
    public static MCH_ConfigPrm CreativeTabIconHeli;
    public static MCH_ConfigPrm CreativeTabIconPlane;
    public static MCH_ConfigPrm CreativeTabIconTank;
    public static MCH_ConfigPrm CreativeTabIconVehicle;
    public static MCH_ConfigPrm DisableShader;
    public static MCH_ConfigPrm AliveTimeOfCartridge;
    public static MCH_ConfigPrm InfinityAmmo;
    public static MCH_ConfigPrm InfinityFuel;
    public static MCH_ConfigPrm HitMarkColor;
    public static MCH_ConfigPrm SmoothShading;
    public static MCH_ConfigPrm EnableModEntityRender;
    public static MCH_ConfigPrm DisableRenderLivingSpecials;
    public static MCH_ConfigPrm PreventingBroken;
    public static MCH_ConfigPrm DropItemInCreativeMode;
    public static MCH_ConfigPrm BreakableOnlyPickaxe;
    public static MCH_ConfigPrm InvertMouse;
    public static MCH_ConfigPrm MouseSensitivity;
    public static MCH_ConfigPrm MouseControlStickModeHeli;
    public static MCH_ConfigPrm MouseControlStickModePlane;
    public static MCH_ConfigPrm MouseControlFlightSimMode;
    public static MCH_ConfigPrm SwitchWeaponWithMouseWheel;
    public static MCH_ConfigPrm AllPlaneSpeed;
    public static MCH_ConfigPrm AllHeliSpeed;
    public static MCH_ConfigPrm AllTankSpeed;
    public static MCH_ConfigPrm HurtResistantTime;
    public static MCH_ConfigPrm DisplayHUDThirdPerson;
    public static MCH_ConfigPrm DisableCameraDistChange;
    public static MCH_ConfigPrm EnableReplaceTextureManager;
    public static MCH_ConfigPrm DisplayEntityMarker;
    public static MCH_ConfigPrm EntityMarkerSize;
    public static MCH_ConfigPrm BlockMarkerSize;
    public static MCH_ConfigPrm DisplayMarkThroughWall;
    public static MCH_ConfigPrm ReplaceRenderViewEntity;
    public static MCH_ConfigPrm StingerLockRange;
    public static MCH_ConfigPrm DefaultExplosionParticle;
    public static MCH_ConfigPrm RangeFinderSpotDist;
    public static MCH_ConfigPrm RangeFinderSpotTime;
    public static MCH_ConfigPrm RangeFinderConsume;
    public static MCH_ConfigPrm EnablePutRackInFlying;
    public static MCH_ConfigPrm EnableDebugBoundingBox;
    public static MCH_ConfigPrm ItemID_Fuel;
    public static MCH_ConfigPrm ItemID_GLTD;
    public static MCH_ConfigPrm ItemID_Chain;
    public static MCH_ConfigPrm ItemID_Parachute;
    public static MCH_ConfigPrm ItemID_Container;
    public static MCH_ConfigPrm ItemID_Stinger;
    public static MCH_ConfigPrm ItemID_StingerMissile;
    public static MCH_ConfigPrm[] ItemID_UavStation;
    public static MCH_ConfigPrm ItemID_InvisibleItem;
    public static MCH_ConfigPrm ItemID_DraftingTable;
    public static MCH_ConfigPrm ItemID_Wrench;
    public static MCH_ConfigPrm ItemID_RangeFinder;
    public static MCH_ConfigPrm BlockID_DraftingTableOFF;
    public static MCH_ConfigPrm BlockID_DraftingTableON;
    public static MCH_ConfigPrm ItemRecipe_Fuel;
    public static MCH_ConfigPrm ItemRecipe_GLTD;
    public static MCH_ConfigPrm ItemRecipe_Chain;
    public static MCH_ConfigPrm ItemRecipe_Parachute;
    public static MCH_ConfigPrm ItemRecipe_Container;
    public static MCH_ConfigPrm ItemRecipe_Stinger;
    public static MCH_ConfigPrm ItemRecipe_StingerMissile;
    public static MCH_ConfigPrm ItemRecipe_Javelin;
    public static MCH_ConfigPrm ItemRecipe_JavelinMissile;
    public static MCH_ConfigPrm[] ItemRecipe_UavStation;
    public static MCH_ConfigPrm ItemRecipe_DraftingTable;
    public static MCH_ConfigPrm ItemRecipe_Wrench;
    public static MCH_ConfigPrm ItemRecipe_RangeFinder;
    public static MCH_ConfigPrm[] KeyConfig;
    public static MCH_ConfigPrm[] General;
    public final String destroyBlockNames = "glass_pane, stained_glass_pane, tallgrass, double_plant, yellow_flower, red_flower, vine, wheat, reeds, waterlily";
    
    public MCH_Config(final String minecraftPath, final String cfgFile) {
        MCH_Config.mcPath = minecraftPath;
        MCH_Config.configFilePath = MCH_Config.mcPath + cfgFile;
        MCH_Config.DebugLog = false;
        MCH_Config.configVer = "0.0.0";
        MCH_Config.bulletBreakableBlocks = new ArrayList<Block>();
        MCH_Config.carBreakableBlocks = new ArrayList<Block>();
        MCH_Config.carNoBreakableBlocks = new ArrayList<Block>();
        MCH_Config.carBreakableMaterials = new ArrayList<Material>();
        MCH_Config.tankBreakableBlocks = new ArrayList<Block>();
        MCH_Config.tankNoBreakableBlocks = new ArrayList<Block>();
        MCH_Config.tankBreakableMaterials = new ArrayList<Material>();
        MCH_Config.KeyUp = new MCH_ConfigPrm("KeyUp", 17);
        MCH_Config.KeyDown = new MCH_ConfigPrm("KeyDown", 31);
        MCH_Config.KeyRight = new MCH_ConfigPrm("KeyRight", 32);
        MCH_Config.KeyLeft = new MCH_ConfigPrm("KeyLeft", 30);
        MCH_Config.KeySwitchMode = new MCH_ConfigPrm("KeySwitchGunner", 35);
        MCH_Config.KeySwitchHovering = new MCH_ConfigPrm("KeySwitchHovering", 57);
        MCH_Config.KeyAttack = new MCH_ConfigPrm("KeyAttack", -100);
        MCH_Config.KeyUseWeapon = new MCH_ConfigPrm("KeyUseWeapon", -99);
        MCH_Config.KeySwitchWeapon1 = new MCH_ConfigPrm("KeySwitchWeapon1", -98);
        MCH_Config.KeySwitchWeapon2 = new MCH_ConfigPrm("KeySwitchWeapon2", 34);
        MCH_Config.KeySwWeaponMode = new MCH_ConfigPrm("KeySwitchWeaponMode", 45);
        MCH_Config.KeyZoom = new MCH_ConfigPrm("KeyZoom", 44);
        MCH_Config.KeyCameraMode = new MCH_ConfigPrm("KeyCameraMode", 46);
        MCH_Config.KeyUnmount = new MCH_ConfigPrm("KeyUnmountMob", 21);
        MCH_Config.KeyFlare = new MCH_ConfigPrm("KeyFlare", 47);
        MCH_Config.KeyExtra = new MCH_ConfigPrm("KeyExtra", 33);
        MCH_Config.KeyCameraDistUp = new MCH_ConfigPrm("KeyCameraDistanceUp", 201);
        MCH_Config.KeyCameraDistDown = new MCH_ConfigPrm("KeyCameraDistanceDown", 209);
        MCH_Config.KeyFreeLook = new MCH_ConfigPrm("KeyFreeLook", 29);
        MCH_Config.KeyGUI = new MCH_ConfigPrm("KeyGUI", 19);
        MCH_Config.KeyGearUpDown = new MCH_ConfigPrm("KeyGearUpDown", 48);
        MCH_Config.KeyPutToRack = new MCH_ConfigPrm("KeyPutToRack", 36);
        MCH_Config.KeyDownFromRack = new MCH_ConfigPrm("KeyDownFromRack", 22);
        MCH_Config.KeyScoreboard = new MCH_ConfigPrm("KeyScoreboard", 38);
        MCH_Config.KeyMultiplayManager = new MCH_ConfigPrm("KeyMultiplayManager", 50);
        MCH_Config.KeyConfig = new MCH_ConfigPrm[] { MCH_Config.KeyUp, MCH_Config.KeyDown, MCH_Config.KeyRight, MCH_Config.KeyLeft, MCH_Config.KeySwitchMode, MCH_Config.KeySwitchHovering, MCH_Config.KeySwitchWeapon1, MCH_Config.KeySwitchWeapon2, MCH_Config.KeySwWeaponMode, MCH_Config.KeyZoom, MCH_Config.KeyCameraMode, MCH_Config.KeyUnmount, MCH_Config.KeyFlare, MCH_Config.KeyExtra, MCH_Config.KeyCameraDistUp, MCH_Config.KeyCameraDistDown, MCH_Config.KeyFreeLook, MCH_Config.KeyGUI, MCH_Config.KeyGearUpDown, MCH_Config.KeyPutToRack, MCH_Config.KeyDownFromRack, MCH_Config.KeyScoreboard, MCH_Config.KeyMultiplayManager };
        MCH_Config.DamageVs = new ArrayList<MCH_ConfigPrm>();
        MCH_Config.CommandPermission = new ArrayList<MCH_ConfigPrm>();
        MCH_Config.CommandPermissionList = new ArrayList<CommandPermission>();
        MCH_Config.IgnoreBulletHitList = new ArrayList<String>();
        MCH_Config.IgnoreBulletHitItem = new MCH_ConfigPrm("IgnoreBulletHit", "");
        MCH_Config.TestMode = new MCH_ConfigPrm("TestMode", false);
        MCH_Config.EnableCommand = new MCH_ConfigPrm("EnableCommand", true);
        MCH_Config.PlaceableOnSpongeOnly = new MCH_ConfigPrm("PlaceableOnSpongeOnly", false);
        MCH_Config.HideKeybind = new MCH_ConfigPrm("HideKeybind", false);
        MCH_Config.ItemDamage = new MCH_ConfigPrm("ItemDamage", true);
        MCH_Config.ItemFuel = new MCH_ConfigPrm("ItemFuel", true);
        MCH_Config.AutoRepairHP = new MCH_ConfigPrm("AutoRepairHP", 0.5);
        MCH_Config.Collision_DestroyBlock = new MCH_ConfigPrm("Collision_DestroyBlock", true);
        MCH_Config.Explosion_DestroyBlock = new MCH_ConfigPrm("Explosion_DestroyBlock", true);
        MCH_Config.Explosion_FlamingBlock = new MCH_ConfigPrm("Explosion_FlamingBlock", true);
        MCH_Config.Collision_Car_BreakableBlock = new MCH_ConfigPrm("Collision_Car_BreakableBlock", "double_plant, glass_pane,stained_glass_pane");
        MCH_Config.Collision_Car_NoBreakableBlock = new MCH_ConfigPrm("Collision_Car_NoBreakBlock", "torch");
        MCH_Config.Collision_Car_BreakableMaterial = new MCH_ConfigPrm("Collision_Car_BreakableMaterial", "cactus, cake, gourd, leaves, vine, plants");
        MCH_Config.Collision_Tank_BreakableBlock = new MCH_ConfigPrm("Collision_Tank_BreakableBlock", "nether_brick_fence");
        MCH_Config.Collision_Tank_BreakableBlock.validVer = "1.0.0";
        MCH_Config.Collision_Tank_NoBreakableBlock = new MCH_ConfigPrm("Collision_Tank_NoBreakBlock", "torch, glowstone");
        MCH_Config.Collision_Tank_BreakableMaterial = new MCH_ConfigPrm("Collision_Tank_BreakableMaterial", "cactus, cake, carpet, circuits, glass, gourd, leaves, vine, wood, plants");
        MCH_Config.Collision_EntityDamage = new MCH_ConfigPrm("Collision_EntityDamage", true);
        MCH_Config.Collision_EntityTankDamage = new MCH_ConfigPrm("Collision_EntityTankDamage", false);
        MCH_Config.LWeaponAutoFire = new MCH_ConfigPrm("LWeaponAutoFire", false);
        MCH_Config.DismountAll = new MCH_ConfigPrm("DismountAll", false);
        MCH_Config.MountMinecartHeli = new MCH_ConfigPrm("MountMinecartHeli", true);
        MCH_Config.MountMinecartPlane = new MCH_ConfigPrm("MountMinecartPlane", true);
        MCH_Config.MountMinecartVehicle = new MCH_ConfigPrm("MountMinecartVehicle", false);
        MCH_Config.MountMinecartTank = new MCH_ConfigPrm("MountMinecartTank", true);
        MCH_Config.AutoThrottleDownHeli = new MCH_ConfigPrm("AutoThrottleDownHeli", true);
        MCH_Config.AutoThrottleDownPlane = new MCH_ConfigPrm("AutoThrottleDownPlane", false);
        MCH_Config.AutoThrottleDownTank = new MCH_ConfigPrm("AutoThrottleDownTank", false);
        MCH_Config.DisableItemRender = new MCH_ConfigPrm("DisableItemRender", 1);
        MCH_Config.DisableItemRender.desc = ";DisableItemRender = 0 ~ 3 (1 = Recommended)";
        MCH_Config.RenderDistanceWeight = new MCH_ConfigPrm("RenderDistanceWeight", 10.0);
        MCH_Config.MobRenderDistanceWeight = new MCH_ConfigPrm("MobRenderDistanceWeight", 1.0);
        MCH_Config.CreativeTabIcon = new MCH_ConfigPrm("CreativeTabIconItem", "fuel");
        MCH_Config.CreativeTabIconHeli = new MCH_ConfigPrm("CreativeTabIconHeli", "ah-64");
        MCH_Config.CreativeTabIconPlane = new MCH_ConfigPrm("CreativeTabIconPlane", "f22a");
        MCH_Config.CreativeTabIconTank = new MCH_ConfigPrm("CreativeTabIconTank", "merkava_mk4");
        MCH_Config.CreativeTabIconVehicle = new MCH_ConfigPrm("CreativeTabIconVehicle", "mk15");
        MCH_Config.DisableShader = new MCH_ConfigPrm("DisableShader", false);
        MCH_Config.AliveTimeOfCartridge = new MCH_ConfigPrm("AliveTimeOfCartridge", 200);
        MCH_Config.InfinityAmmo = new MCH_ConfigPrm("InfinityAmmo", false);
        MCH_Config.InfinityFuel = new MCH_ConfigPrm("InfinityFuel", false);
        MCH_Config.HitMarkColor = new MCH_ConfigPrm("HitMarkColor", "255, 255, 0, 0");
        MCH_Config.HitMarkColor.desc = ";HitMarkColor = Alpha, Red, Green, Blue";
        MCH_Config.SmoothShading = new MCH_ConfigPrm("SmoothShading", true);
        MCH_Config.BulletBreakableBlock = new MCH_ConfigPrm("BulletBreakableBlocks", "glass_pane, stained_glass_pane, tallgrass, double_plant, yellow_flower, red_flower, vine, wheat, reeds, waterlily");
        MCH_Config.BulletBreakableBlock.validVer = "0.10.4";
        MCH_Config.EnableModEntityRender = new MCH_ConfigPrm("EnableModEntityRender", true);
        MCH_Config.DisableRenderLivingSpecials = new MCH_ConfigPrm("DisableRenderLivingSpecials", true);
        MCH_Config.PreventingBroken = new MCH_ConfigPrm("PreventingBroken", false);
        MCH_Config.DropItemInCreativeMode = new MCH_ConfigPrm("DropItemInCreativeMode", false);
        MCH_Config.BreakableOnlyPickaxe = new MCH_ConfigPrm("BreakableOnlyPickaxe", false);
        MCH_Config.InvertMouse = new MCH_ConfigPrm("InvertMouse", false);
        MCH_Config.MouseSensitivity = new MCH_ConfigPrm("MouseSensitivity", 10.0);
        MCH_Config.MouseControlStickModeHeli = new MCH_ConfigPrm("MouseControlStickModeHeli", false);
        MCH_Config.MouseControlStickModePlane = new MCH_ConfigPrm("MouseControlStickModePlane", false);
        MCH_Config.MouseControlFlightSimMode = new MCH_ConfigPrm("MouseControlFlightSimMode", false);
        MCH_Config.MouseControlFlightSimMode.desc = ";MouseControlFlightSimMode = true ( Yaw:key, Roll=mouse )";
        MCH_Config.SwitchWeaponWithMouseWheel = new MCH_ConfigPrm("SwitchWeaponWithMouseWheel", true);
        MCH_Config.AllHeliSpeed = new MCH_ConfigPrm("AllHeliSpeed", 1.0);
        MCH_Config.AllPlaneSpeed = new MCH_ConfigPrm("AllPlaneSpeed", 1.0);
        MCH_Config.AllTankSpeed = new MCH_ConfigPrm("AllTankSpeed", 1.0);
        MCH_Config.HurtResistantTime = new MCH_ConfigPrm("HurtResistantTime", 0.0);
        MCH_Config.DisplayHUDThirdPerson = new MCH_ConfigPrm("DisplayHUDThirdPerson", false);
        MCH_Config.DisableCameraDistChange = new MCH_ConfigPrm("DisableThirdPersonCameraDistChange", false);
        MCH_Config.EnableReplaceTextureManager = new MCH_ConfigPrm("EnableReplaceTextureManager", true);
        MCH_Config.DisplayEntityMarker = new MCH_ConfigPrm("DisplayEntityMarker", true);
        MCH_Config.DisplayMarkThroughWall = new MCH_ConfigPrm("DisplayMarkThroughWall", true);
        MCH_Config.EntityMarkerSize = new MCH_ConfigPrm("EntityMarkerSize", 10.0);
        MCH_Config.BlockMarkerSize = new MCH_ConfigPrm("BlockMarkerSize", 10.0);
        MCH_Config.ReplaceRenderViewEntity = new MCH_ConfigPrm("ReplaceRenderViewEntity", true);
        MCH_Config.StingerLockRange = new MCH_ConfigPrm("StingerLockRange", 320.0);
        MCH_Config.StingerLockRange.validVer = "1.0.0";
        MCH_Config.DefaultExplosionParticle = new MCH_ConfigPrm("DefaultExplosionParticle", false);
        MCH_Config.RangeFinderSpotDist = new MCH_ConfigPrm("RangeFinderSpotDist", 400);
        MCH_Config.RangeFinderSpotTime = new MCH_ConfigPrm("RangeFinderSpotTime", 15);
        MCH_Config.RangeFinderConsume = new MCH_ConfigPrm("RangeFinderConsume", true);
        MCH_Config.EnablePutRackInFlying = new MCH_ConfigPrm("EnablePutRackInFlying", true);
        MCH_Config.EnableDebugBoundingBox = new MCH_ConfigPrm("EnableDebugBoundingBox", true);
        MCH_Config.hitMarkColorAlpha = 1.0f;
        MCH_Config.hitMarkColorRGB = 16711680;
        MCH_Config.ItemRecipe_Fuel = new MCH_ConfigPrm("ItemRecipe_Fuel", "\"ICI\", \"III\", I, iron_ingot, C, coal");
        MCH_Config.ItemRecipe_GLTD = new MCH_ConfigPrm("ItemRecipe_GLTD", "\" B \", \"IDI\", \"IRI\", B, iron_block, I, iron_ingot, D, diamond, R, redstone");
        MCH_Config.ItemRecipe_Chain = new MCH_ConfigPrm("ItemRecipe_Chain", "\"I I\", \"III\", \"I I\", I, iron_ingot");
        MCH_Config.ItemRecipe_Parachute = new MCH_ConfigPrm("ItemRecipe_Parachute", "\"WWW\", \"S S\", \" W \", W, wool, S, string");
        MCH_Config.ItemRecipe_Container = new MCH_ConfigPrm("ItemRecipe_Container", "\"CCI\", C, chest, I, iron_ingot");
        MCH_Config.ItemRecipe_UavStation = new MCH_ConfigPrm[] { new MCH_ConfigPrm("ItemRecipe_UavStation", "\"III\", \"IDI\", \"IRI\", I, iron_ingot, D, diamond, R, redstone_block"), new MCH_ConfigPrm("ItemRecipe_UavStation2", "\"IDI\", \"IRI\", I, iron_ingot, D, diamond, R, redstone") };
        MCH_Config.ItemRecipe_DraftingTable = new MCH_ConfigPrm("ItemRecipe_DraftingTable", "\"R  \", \"PCP\", \"F F\", R, redstone, C, crafting_table, P, planks, F, fence");
        MCH_Config.ItemRecipe_Wrench = new MCH_ConfigPrm("ItemRecipe_Wrench", "\" I \", \" II\", \"I  \", I, iron_ingot");
        MCH_Config.ItemRecipe_RangeFinder = new MCH_ConfigPrm("ItemRecipe_RangeFinder", "\"III\", \"RGR\", \"III\", I, iron_ingot, G, glass, R, redstone");
        MCH_Config.ItemRecipe_Stinger = new MCH_ConfigPrm("ItemRecipe_Stinger", "\"G  \", \"III\", \"RI \", G, glass, I, iron_ingot, R, redstone");
        MCH_Config.ItemRecipe_StingerMissile = new MCH_ConfigPrm("ItemRecipe_StingerMissile", "\"R  \", \" I \", \"  G\", G, gunpowder, I, iron_ingot, R, redstone");
        MCH_Config.ItemRecipe_Javelin = new MCH_ConfigPrm("ItemRecipe_Javelin", "\"III\", \"GR \", G, glass, I, iron_ingot, R, redstone");
        MCH_Config.ItemRecipe_JavelinMissile = new MCH_ConfigPrm("ItemRecipe_JavelinMissile", "\" R \", \" I \", \" G \", G, gunpowder, I, iron_ingot, R, redstone");
        MCH_Config.ItemID_GLTD = new MCH_ConfigPrm("ItemID_GLTD", 28799);
        MCH_Config.ItemID_Chain = new MCH_ConfigPrm("ItemID_Chain", 28798);
        MCH_Config.ItemID_Parachute = new MCH_ConfigPrm("ItemID_Parachute", 28797);
        MCH_Config.ItemID_Container = new MCH_ConfigPrm("ItemID_Container", 28796);
        MCH_Config.ItemID_UavStation = new MCH_ConfigPrm[] { new MCH_ConfigPrm("ItemID_UavStation", 28795), new MCH_ConfigPrm("ItemID_UavStation2", 28790) };
        MCH_Config.ItemID_InvisibleItem = new MCH_ConfigPrm("ItemID_Internal", 28794);
        MCH_Config.ItemID_Fuel = new MCH_ConfigPrm("ItemID_Fuel", 28793);
        MCH_Config.ItemID_DraftingTable = new MCH_ConfigPrm("ItemID_DraftingTable", 28792);
        MCH_Config.ItemID_Wrench = new MCH_ConfigPrm("ItemID_Wrench", 28791);
        MCH_Config.ItemID_RangeFinder = new MCH_ConfigPrm("ItemID_RangeFinder", 28789);
        MCH_Config.ItemID_Stinger = new MCH_ConfigPrm("ItemID_Stinger", 28900);
        MCH_Config.ItemID_StingerMissile = new MCH_ConfigPrm("ItemID_StingerMissile", 28901);
        MCH_Config.BlockID_DraftingTableOFF = new MCH_ConfigPrm("BlockID_DraftingTable", 3450);
        MCH_Config.BlockID_DraftingTableON = new MCH_ConfigPrm("BlockID_DraftingTableON", 3451);
        MCH_Config.General = new MCH_ConfigPrm[] { MCH_Config.TestMode, MCH_Config.EnableCommand, null, MCH_Config.PlaceableOnSpongeOnly, MCH_Config.ItemDamage, MCH_Config.ItemFuel, MCH_Config.AutoRepairHP, MCH_Config.Explosion_DestroyBlock, MCH_Config.Explosion_FlamingBlock, MCH_Config.BulletBreakableBlock, MCH_Config.Collision_DestroyBlock, MCH_Config.Collision_Car_BreakableBlock, MCH_Config.Collision_Car_BreakableMaterial, MCH_Config.Collision_Tank_BreakableBlock, MCH_Config.Collision_Tank_BreakableMaterial, MCH_Config.Collision_EntityDamage, MCH_Config.Collision_EntityTankDamage, MCH_Config.InfinityAmmo, MCH_Config.InfinityFuel, MCH_Config.DismountAll, MCH_Config.MountMinecartHeli, MCH_Config.MountMinecartPlane, MCH_Config.MountMinecartVehicle, MCH_Config.MountMinecartTank, MCH_Config.PreventingBroken, MCH_Config.DropItemInCreativeMode, MCH_Config.BreakableOnlyPickaxe, MCH_Config.AllHeliSpeed, MCH_Config.AllPlaneSpeed, MCH_Config.AllTankSpeed, MCH_Config.HurtResistantTime, MCH_Config.StingerLockRange, MCH_Config.RangeFinderSpotDist, MCH_Config.RangeFinderSpotTime, MCH_Config.RangeFinderConsume, MCH_Config.EnablePutRackInFlying, MCH_Config.EnableDebugBoundingBox, null, MCH_Config.InvertMouse, MCH_Config.MouseSensitivity, MCH_Config.MouseControlStickModeHeli, MCH_Config.MouseControlStickModePlane, MCH_Config.MouseControlFlightSimMode, MCH_Config.AutoThrottleDownHeli, MCH_Config.AutoThrottleDownPlane, MCH_Config.AutoThrottleDownTank, MCH_Config.SwitchWeaponWithMouseWheel, MCH_Config.LWeaponAutoFire, MCH_Config.DisableItemRender, MCH_Config.HideKeybind, MCH_Config.RenderDistanceWeight, MCH_Config.MobRenderDistanceWeight, MCH_Config.CreativeTabIcon, MCH_Config.CreativeTabIconHeli, MCH_Config.CreativeTabIconPlane, MCH_Config.CreativeTabIconTank, MCH_Config.CreativeTabIconVehicle, MCH_Config.DisableShader, MCH_Config.DefaultExplosionParticle, MCH_Config.AliveTimeOfCartridge, MCH_Config.HitMarkColor, MCH_Config.SmoothShading, MCH_Config.EnableModEntityRender, MCH_Config.DisableRenderLivingSpecials, MCH_Config.DisplayHUDThirdPerson, MCH_Config.DisableCameraDistChange, MCH_Config.EnableReplaceTextureManager, MCH_Config.DisplayEntityMarker, MCH_Config.EntityMarkerSize, MCH_Config.BlockMarkerSize, MCH_Config.ReplaceRenderViewEntity, null, MCH_Config.ItemRecipe_Fuel, MCH_Config.ItemRecipe_GLTD, MCH_Config.ItemRecipe_Chain, MCH_Config.ItemRecipe_Parachute, MCH_Config.ItemRecipe_Container, MCH_Config.ItemRecipe_UavStation[0], MCH_Config.ItemRecipe_UavStation[1], MCH_Config.ItemRecipe_DraftingTable, MCH_Config.ItemRecipe_Wrench, MCH_Config.ItemRecipe_RangeFinder, MCH_Config.ItemRecipe_Stinger, MCH_Config.ItemRecipe_StingerMissile, MCH_Config.ItemRecipe_Javelin, MCH_Config.ItemRecipe_JavelinMissile };
        MCH_Config.DamageVsEntity = new DamageFactor("DamageVsEntity");
        MCH_Config.DamageVsLiving = new DamageFactor("DamageVsLiving");
        MCH_Config.DamageVsPlayer = new DamageFactor("DamageVsPlayer");
        MCH_Config.DamageVsMCHeliAircraft = new DamageFactor("DamageVsMCHeliAircraft");
        MCH_Config.DamageVsMCHeliTank = new DamageFactor("DamageVsMCHeliTank");
        MCH_Config.DamageVsMCHeliVehicle = new DamageFactor("DamageVsMCHeliVehicle");
        MCH_Config.DamageVsMCHeliOther = new DamageFactor("DamageVsMCHeliOther");
        MCH_Config.DamageAircraftByExternal = new DamageFactor("DamageMCHeliAircraftByExternal");
        MCH_Config.DamageTankByExternal = new DamageFactor("DamageMCHeliTankByExternal");
        MCH_Config.DamageVehicleByExternal = new DamageFactor("DamageMCHeliVehicleByExternal");
        MCH_Config.DamageOtherByExternal = new DamageFactor("DamageMCHeliOtherByExternal");
        MCH_Config.DamageFactorList = new DamageFactor[] { MCH_Config.DamageVsEntity, MCH_Config.DamageVsLiving, MCH_Config.DamageVsPlayer, MCH_Config.DamageVsMCHeliAircraft, MCH_Config.DamageVsMCHeliTank, MCH_Config.DamageVsMCHeliVehicle, MCH_Config.DamageVsMCHeliOther, MCH_Config.DamageAircraftByExternal, MCH_Config.DamageTankByExternal, MCH_Config.DamageVehicleByExternal, MCH_Config.DamageOtherByExternal };
    }
    
    public void setBlockListFromString(final List<Block> list, final String str) {
        list.clear();
        final String[] arr$;
        final String[] s = arr$ = str.split("\\s*,\\s*");
        for (final String blockName : arr$) {
            final Block b = W_Block.getBlockFromName(blockName);
            if (b != null) {
                list.add(b);
            }
        }
    }
    
    public void setMaterialListFromString(final List<Material> list, final String str) {
        list.clear();
        final String[] arr$;
        final String[] s = arr$ = str.split("\\s*,\\s*");
        for (final String name : arr$) {
            final Material m = MCH_Lib.getMaterialFromName(name);
            if (m != null) {
                list.add(m);
            }
        }
    }
    
    public void correctionParameter() {
        final String[] s = MCH_Config.HitMarkColor.prmString.split("\\s*,\\s*");
        if (s.length == 4) {
            MCH_Config.hitMarkColorAlpha = this.toInt255(s[0]) / 255.0f;
            MCH_Config.hitMarkColorRGB = (this.toInt255(s[1]) << 16 | this.toInt255(s[2]) << 8 | this.toInt255(s[3]));
        }
        MCH_Config.AllHeliSpeed.prmDouble = MCH_Lib.RNG(MCH_Config.AllHeliSpeed.prmDouble, 0.0, 1000.0);
        MCH_Config.AllPlaneSpeed.prmDouble = MCH_Lib.RNG(MCH_Config.AllPlaneSpeed.prmDouble, 0.0, 1000.0);
        MCH_Config.AllTankSpeed.prmDouble = MCH_Lib.RNG(MCH_Config.AllTankSpeed.prmDouble, 0.0, 1000.0);
        this.setBlockListFromString(MCH_Config.bulletBreakableBlocks, MCH_Config.BulletBreakableBlock.prmString);
        this.setBlockListFromString(MCH_Config.carBreakableBlocks, MCH_Config.Collision_Car_BreakableBlock.prmString);
        this.setBlockListFromString(MCH_Config.carNoBreakableBlocks, MCH_Config.Collision_Car_NoBreakableBlock.prmString);
        this.setMaterialListFromString(MCH_Config.carBreakableMaterials, MCH_Config.Collision_Car_BreakableMaterial.prmString);
        this.setBlockListFromString(MCH_Config.tankBreakableBlocks, MCH_Config.Collision_Tank_BreakableBlock.prmString);
        this.setBlockListFromString(MCH_Config.tankNoBreakableBlocks, MCH_Config.Collision_Tank_NoBreakableBlock.prmString);
        this.setMaterialListFromString(MCH_Config.tankBreakableMaterials, MCH_Config.Collision_Tank_BreakableMaterial.prmString);
        if (MCH_Config.EntityMarkerSize.prmDouble < 0.0) {
            MCH_Config.EntityMarkerSize.prmDouble = 0.0;
        }
        if (MCH_Config.BlockMarkerSize.prmDouble < 0.0) {
            MCH_Config.BlockMarkerSize.prmDouble = 0.0;
        }
        if (MCH_Config.HurtResistantTime.prmDouble < 0.0) {
            MCH_Config.HurtResistantTime.prmDouble = 0.0;
        }
        if (MCH_Config.HurtResistantTime.prmDouble > 10000.0) {
            MCH_Config.HurtResistantTime.prmDouble = 10000.0;
        }
        if (MCH_Config.MobRenderDistanceWeight.prmDouble < 0.1) {
            MCH_Config.MobRenderDistanceWeight.prmDouble = 0.1;
        }
        else if (MCH_Config.MobRenderDistanceWeight.prmDouble > 10.0) {
            MCH_Config.MobRenderDistanceWeight.prmDouble = 10.0;
        }
        for (final MCH_ConfigPrm p : MCH_Config.CommandPermission) {
            final CommandPermission cpm = new CommandPermission(p.prmString);
            if (!cpm.name.isEmpty()) {
                MCH_Config.CommandPermissionList.add(cpm);
            }
        }
        if (MCH_Config.IgnoreBulletHitList.size() <= 0) {
            MCH_Config.IgnoreBulletHitList.add("flansmod.common.guns.EntityBullet");
            MCH_Config.IgnoreBulletHitList.add("flansmod.common.guns.EntityGrenade");
        }
        final boolean isNoDamageVsSetting = MCH_Config.DamageVs.size() <= 0;
        for (final MCH_ConfigPrm p2 : MCH_Config.DamageVs) {
            for (final DamageFactor df : MCH_Config.DamageFactorList) {
                if (p2.name.equals(df.itemName)) {
                    df.list.add(this.newDamageEntity(p2.prmString));
                }
            }
        }
        for (final DamageFactor df2 : MCH_Config.DamageFactorList) {
            if (df2.list.size() <= 0) {
                MCH_Config.DamageVs.add(new MCH_ConfigPrm(df2.itemName, "1.0"));
            }
            else {
                boolean foundCommon = false;
                for (final DamageEntity n : df2.list) {
                    if (n.name.isEmpty()) {
                        foundCommon = true;
                        break;
                    }
                }
                if (!foundCommon) {
                    MCH_Config.DamageVs.add(new MCH_ConfigPrm(df2.itemName, "1.0"));
                }
            }
        }
        if (isNoDamageVsSetting) {
            MCH_Config.DamageVs.add(new MCH_ConfigPrm("DamageVsEntity", "3.0, flansmod"));
            MCH_Config.DamageVs.add(new MCH_ConfigPrm("DamageMCHeliAircraftByExternal", "0.5, flansmod"));
            MCH_Config.DamageVs.add(new MCH_ConfigPrm("DamageMCHeliVehicleByExternal", "0.5, flansmod"));
        }
    }
    
    public DamageEntity newDamageEntity(final String s) {
        final String[] splt = s.split("\\s*,\\s*");
        if (splt.length == 1) {
            return new DamageEntity(Double.parseDouble(splt[0]), "");
        }
        if (splt.length == 2) {
            return new DamageEntity(Double.parseDouble(splt[0]), splt[1]);
        }
        return new DamageEntity(1.0, "");
    }
    
    public static float applyDamageByExternal(final Entity target, final DamageSource ds, float damage) {
        List<DamageEntity> list;
        if (target instanceof MCH_EntityHeli || target instanceof MCP_EntityPlane) {
            list = MCH_Config.DamageAircraftByExternal.list;
        }
        else if (target instanceof MCH_EntityTank) {
            list = MCH_Config.DamageTankByExternal.list;
        }
        else if (target instanceof MCH_EntityVehicle) {
            list = MCH_Config.DamageVehicleByExternal.list;
        }
        else {
            list = MCH_Config.DamageOtherByExternal.list;
        }
        final Entity attacker = ds.func_76346_g();
        final Entity attackerSource = ds.func_76364_f();
        for (final DamageEntity de : list) {
            if (de.name.isEmpty() || (attacker != null && attacker.getClass().toString().indexOf(de.name) > 0) || (attackerSource != null && attackerSource.getClass().toString().indexOf(de.name) > 0)) {
                damage *= (float)de.factor;
            }
        }
        return damage;
    }
    
    public static float applyDamageVsEntity(final Entity target, final DamageSource ds, float damage) {
        if (target == null) {
            return damage;
        }
        final String targetName = target.getClass().toString();
        List<DamageEntity> list;
        if (target instanceof MCH_EntityHeli || target instanceof MCP_EntityPlane) {
            list = MCH_Config.DamageVsMCHeliAircraft.list;
        }
        else if (target instanceof MCH_EntityTank) {
            list = MCH_Config.DamageVsMCHeliTank.list;
        }
        else if (target instanceof MCH_EntityVehicle) {
            list = MCH_Config.DamageVsMCHeliVehicle.list;
        }
        else if (targetName.indexOf("mcheli.") > 0) {
            list = MCH_Config.DamageVsMCHeliOther.list;
        }
        else if (target instanceof EntityPlayer) {
            list = MCH_Config.DamageVsPlayer.list;
        }
        else if (target instanceof EntityLivingBase) {
            list = MCH_Config.DamageVsLiving.list;
        }
        else {
            list = MCH_Config.DamageVsEntity.list;
        }
        for (final DamageEntity de : list) {
            if (de.name.isEmpty() || targetName.indexOf(de.name) > 0) {
                damage *= (float)de.factor;
            }
        }
        return damage;
    }
    
    public static List<Block> getBreakableBlockListFromType(final int n) {
        if (n == 2) {
            return MCH_Config.tankBreakableBlocks;
        }
        if (n == 1) {
            return MCH_Config.carBreakableBlocks;
        }
        return MCH_Config.dummyBreakableBlocks;
    }
    
    public static List<Block> getNoBreakableBlockListFromType(final int n) {
        if (n == 2) {
            return MCH_Config.tankNoBreakableBlocks;
        }
        if (n == 1) {
            return MCH_Config.carNoBreakableBlocks;
        }
        return MCH_Config.dummyBreakableBlocks;
    }
    
    public static List<Material> getBreakableMaterialListFromType(final int n) {
        if (n == 2) {
            return MCH_Config.tankBreakableMaterials;
        }
        if (n == 1) {
            return MCH_Config.carBreakableMaterials;
        }
        return MCH_Config.dummyBreakableMaterials;
    }
    
    public int toInt255(final String s) {
        final int a = Integer.valueOf(s);
        return (a < 0) ? 0 : ((a > 255) ? 255 : a);
    }
    
    public void load() {
        final MCH_InputFile file = new MCH_InputFile();
        if (file.open(MCH_Config.configFilePath)) {
            for (String str = file.readLine(); str != null; str = file.readLine()) {
                if (str.trim().equalsIgnoreCase("McHeliOutputDebugLog")) {
                    MCH_Config.DebugLog = true;
                }
                else {
                    this.readConfigData(str);
                }
            }
            file.close();
            MCH_Lib.Log("loaded " + file.file.getAbsolutePath(), new Object[0]);
        }
        else {
            MCH_Lib.Log("" + new File(MCH_Config.configFilePath).getAbsolutePath() + " not found.", new Object[0]);
        }
        this.correctionParameter();
    }
    
    private void readConfigData(final String str) {
        final String[] s = str.split("=");
        if (s.length != 2) {
            return;
        }
        s[0] = s[0].trim();
        s[1] = s[1].trim();
        if (s[0].equalsIgnoreCase("MOD_Version")) {
            MCH_Config.configVer = s[1];
            return;
        }
        if (s[0].equalsIgnoreCase("CommandPermission")) {
            MCH_Config.CommandPermission.add(new MCH_ConfigPrm("CommandPermission", s[1]));
        }
        for (final DamageFactor item : MCH_Config.DamageFactorList) {
            if (item.itemName.equalsIgnoreCase(s[0])) {
                MCH_Config.DamageVs.add(new MCH_ConfigPrm(item.itemName, s[1]));
            }
        }
        if (MCH_Config.IgnoreBulletHitItem.compare(s[0])) {
            MCH_Config.IgnoreBulletHitList.add(s[1]);
        }
        for (final MCH_ConfigPrm p : MCH_Config.KeyConfig) {
            if (p != null && p.compare(s[0]) && p.isValidVer(MCH_Config.configVer)) {
                p.setPrm(s[1]);
                return;
            }
        }
        for (final MCH_ConfigPrm p : MCH_Config.General) {
            if (p != null && p.compare(s[0]) && p.isValidVer(MCH_Config.configVer)) {
                p.setPrm(s[1]);
                return;
            }
        }
    }
    
    public void write() {
        final MCH_OutputFile file = new MCH_OutputFile();
        if (file.open(MCH_Config.configFilePath)) {
            this.writeConfigData(file.pw);
            file.close();
            MCH_Lib.Log("update " + file.file.getAbsolutePath(), new Object[0]);
        }
        else {
            MCH_Lib.Log("" + new File(MCH_Config.configFilePath).getAbsolutePath() + " cannot open.", new Object[0]);
        }
    }
    
    private void writeConfigData(final PrintWriter pw) {
        pw.println("[General]");
        pw.println("MOD_Name = mcheli");
        pw.println("MOD_Version = " + MCH_MOD.VER);
        pw.println("MOD_MC_Version = 1.7.10");
        pw.println();
        if (MCH_Config.DebugLog) {
            pw.println("McHeliOutputDebugLog");
            pw.println();
        }
        for (final MCH_ConfigPrm p : MCH_Config.General) {
            if (p != null) {
                if (!p.desc.isEmpty()) {
                    pw.println(p.desc);
                }
                pw.println(p.name + " = " + p);
            }
            else {
                pw.println("");
            }
        }
        pw.println();
        for (final MCH_ConfigPrm p2 : MCH_Config.DamageVs) {
            pw.println(p2.name + " = " + p2);
        }
        pw.println();
        for (final String s : MCH_Config.IgnoreBulletHitList) {
            pw.println(MCH_Config.IgnoreBulletHitItem.name + " = " + s);
        }
        pw.println();
        pw.println(";CommandPermission = commandName(eg, modlist, status, fill...):playerName1, playerName2, playerName3...");
        if (MCH_Config.CommandPermission.size() == 0) {
            pw.println(";CommandPermission = modlist :example1, example2");
            pw.println(";CommandPermission = status :  example2");
        }
        for (final MCH_ConfigPrm p2 : MCH_Config.CommandPermission) {
            pw.println(p2.name + " = " + p2);
        }
        pw.println();
        pw.println();
        pw.println("[Key config]");
        pw.println("http://minecraft.gamepedia.com/Key_codes");
        pw.println();
        for (final MCH_ConfigPrm p : MCH_Config.KeyConfig) {
            pw.println(p.name + " = " + p);
        }
    }
    
    static {
        dummyBreakableBlocks = new ArrayList<Block>();
        dummyBreakableMaterials = new ArrayList<Material>();
    }
    
    class DamageFactor
    {
        public final String itemName;
        public List<DamageEntity> list;
        
        public DamageFactor(final String itemName) {
            this.itemName = itemName;
            this.list = new ArrayList<DamageEntity>();
        }
    }
    
    class DamageEntity
    {
        public final double factor;
        public final String name;
        
        public DamageEntity(final double factor, final String name) {
            this.factor = factor;
            this.name = name;
        }
    }
    
    public class CommandPermission
    {
        public final String name;
        public final String[] players;
        
        public CommandPermission(final String param) {
            final String[] s = param.split(":");
            if (s.length == 2) {
                this.name = s[0].toLowerCase().trim();
                this.players = s[1].trim().split("\\s*,\\s*");
            }
            else {
                this.name = "";
                this.players = new String[0];
            }
        }
    }
}
