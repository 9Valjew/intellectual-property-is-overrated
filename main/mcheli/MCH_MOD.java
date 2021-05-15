package mcheli;

import mcheli.lweapon.*;
import mcheli.tool.*;
import mcheli.tool.rangefinder.*;
import net.minecraft.item.*;
import java.io.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.*;
import net.minecraftforge.common.*;
import mcheli.gui.*;
import cpw.mods.fml.common.network.*;
import mcheli.block.*;
import cpw.mods.fml.common.registry.*;
import mcheli.gltd.*;
import mcheli.chain.*;
import mcheli.parachute.*;
import mcheli.container.*;
import mcheli.uav.*;
import mcheli.weapon.*;
import mcheli.flare.*;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.*;
import mcheli.command.*;
import net.minecraft.command.*;
import mcheli.wrapper.*;
import java.util.*;
import mcheli.throwable.*;
import mcheli.helicopter.*;
import mcheli.aircraft.*;
import mcheli.plane.*;
import mcheli.tank.*;
import mcheli.vehicle.*;

@Mod(modid = "mcheli", name = "mcheli", dependencies = "required-after:Forge@[10.13.2.1230,)")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class MCH_MOD
{
    public static final String MOD_ID = "mcheli";
    public static final String DOMAIN = "mcheli";
    public static final String MCVER = "1.7.10";
    public static String VER;
    public static final String MOD_CH = "MCHeli_CH";
    @Mod.Instance("mcheli")
    public static MCH_MOD instance;
    @SidedProxy(clientSide = "mcheli.MCH_ClientProxy", serverSide = "mcheli.MCH_CommonProxy")
    public static MCH_CommonProxy proxy;
    public static MCH_PacketHandler packetHandler;
    public static MCH_Config config;
    public static String sourcePath;
    public static MCH_InvisibleItem invisibleItem;
    public static MCH_ItemGLTD itemGLTD;
    public static MCH_ItemLightWeaponBullet itemStingerBullet;
    public static MCH_ItemLightWeaponBase itemStinger;
    public static MCH_ItemLightWeaponBullet itemJavelinBullet;
    public static MCH_ItemLightWeaponBase itemJavelin;
    public static MCH_ItemUavStation[] itemUavStation;
    public static MCH_ItemParachute itemParachute;
    public static MCH_ItemContainer itemContainer;
    public static MCH_ItemChain itemChain;
    public static MCH_ItemFuel itemFuel;
    public static MCH_ItemWrench itemWrench;
    public static MCH_ItemRangeFinder itemRangeFinder;
    public static MCH_CreativeTabs creativeTabs;
    public static MCH_CreativeTabs creativeTabsHeli;
    public static MCH_CreativeTabs creativeTabsPlane;
    public static MCH_CreativeTabs creativeTabsTank;
    public static MCH_CreativeTabs creativeTabsVehicle;
    public static MCH_DraftingTableBlock blockDraftingTable;
    public static MCH_DraftingTableBlock blockDraftingTableLit;
    public static Item sampleHelmet;
    
    @Mod.EventHandler
    public void PreInit(final FMLPreInitializationEvent evt) {
        MCH_MOD.VER = Loader.instance().activeModContainer().getVersion();
        MCH_Lib.init();
        MCH_Lib.Log("MC Ver:1.7.10 MOD Ver:" + MCH_MOD.VER + "", new Object[0]);
        MCH_Lib.Log("Start load...", new Object[0]);
        MCH_MOD.sourcePath = Loader.instance().activeModContainer().getSource().getPath();
        MCH_Lib.Log("SourcePath: " + MCH_MOD.sourcePath, new Object[0]);
        MCH_Lib.Log("CurrentDirectory:" + new File(".").getAbsolutePath(), new Object[0]);
        MCH_MOD.proxy.init();
        MCH_MOD.creativeTabs = new MCH_CreativeTabs("MC Heli Item");
        MCH_MOD.creativeTabsHeli = new MCH_CreativeTabs("MC Heli Helicopters");
        MCH_MOD.creativeTabsPlane = new MCH_CreativeTabs("MC Heli Planes");
        MCH_MOD.creativeTabsTank = new MCH_CreativeTabs("MC Heli Tanks");
        MCH_MOD.creativeTabsVehicle = new MCH_CreativeTabs("MC Heli Vehicles");
        W_ItemList.init();
        MCH_MOD.config = MCH_MOD.proxy.loadConfig("config/mcheli.cfg");
        MCH_MOD.proxy.loadHUD(MCH_MOD.sourcePath + "/assets/" + "mcheli" + "/hud");
        MCH_WeaponInfoManager.load(MCH_MOD.sourcePath + "/assets/" + "mcheli" + "/weapons");
        MCH_HeliInfoManager.getInstance().load(MCH_MOD.sourcePath + "/assets/" + "mcheli" + "/", "helicopters");
        MCP_PlaneInfoManager.getInstance().load(MCH_MOD.sourcePath + "/assets/" + "mcheli" + "/", "planes");
        MCH_TankInfoManager.getInstance().load(MCH_MOD.sourcePath + "/assets/" + "mcheli" + "/", "tanks");
        MCH_VehicleInfoManager.getInstance().load(MCH_MOD.sourcePath + "/assets/" + "mcheli" + "/", "vehicles");
        MCH_ThrowableInfoManager.load(MCH_MOD.sourcePath + "/assets/" + "mcheli" + "/throwable");
        MCH_SoundsJson.update(MCH_MOD.sourcePath + "/assets/" + "mcheli" + "/");
        MCH_Lib.Log("Register item", new Object[0]);
        this.registerItemRangeFinder();
        this.registerItemWrench();
        this.registerItemFuel();
        this.registerItemGLTD();
        this.registerItemChain();
        this.registerItemParachute();
        this.registerItemContainer();
        this.registerItemUavStation();
        this.registerItemInvisible();
        registerItemThrowable();
        this.registerItemLightWeaponBullet();
        this.registerItemLightWeapon();
        registerItemAircraft();
        final MCH_Config config = MCH_MOD.config;
        (MCH_MOD.blockDraftingTable = new MCH_DraftingTableBlock(MCH_Config.BlockID_DraftingTableOFF.prmInt, false)).func_149663_c("drafting_table");
        MCH_MOD.blockDraftingTable.func_149647_a((CreativeTabs)MCH_MOD.creativeTabs);
        final MCH_Config config2 = MCH_MOD.config;
        (MCH_MOD.blockDraftingTableLit = new MCH_DraftingTableBlock(MCH_Config.BlockID_DraftingTableON.prmInt, true)).func_149663_c("lit_drafting_table");
        GameRegistry.registerBlock((Block)MCH_MOD.blockDraftingTable, "drafting_table");
        GameRegistry.registerBlock((Block)MCH_MOD.blockDraftingTableLit, "lit_drafting_table");
        W_LanguageRegistry.addName(MCH_MOD.blockDraftingTable, "Drafting Table");
        W_LanguageRegistry.addNameForObject(MCH_MOD.blockDraftingTable, "ja_JP", "\u88fd\u56f3\u53f0");
        MCH_Achievement.PreInit();
        MCH_Lib.Log("Register system", new Object[0]);
        W_NetworkRegistry.registerChannel(MCH_MOD.packetHandler, "MCHeli_CH");
        MinecraftForge.EVENT_BUS.register((Object)new MCH_EventHook());
        MCH_MOD.proxy.registerClientTick();
        W_NetworkRegistry.registerGuiHandler(this, (IGuiHandler)new MCH_GuiCommonHandler());
        MCH_Lib.Log("Register entity", new Object[0]);
        this.registerEntity();
        MCH_Lib.Log("Register renderer", new Object[0]);
        MCH_MOD.proxy.registerRenderer();
        MCH_Lib.Log("Register models", new Object[0]);
        MCH_MOD.proxy.registerModels();
        MCH_Lib.Log("Register Sounds", new Object[0]);
        MCH_MOD.proxy.registerSounds();
        W_LanguageRegistry.updateLang(MCH_MOD.sourcePath + "/assets/" + "mcheli" + "/lang/");
        MCH_Lib.Log("End load", new Object[0]);
    }
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent evt) {
        GameRegistry.registerTileEntity((Class)MCH_DraftingTableTileEntity.class, "drafting_table");
        MCH_MOD.proxy.registerBlockRenderer();
    }
    
    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent evt) {
        final MCH_CreativeTabs creativeTabs = MCH_MOD.creativeTabs;
        final MCH_Config config = MCH_MOD.config;
        creativeTabs.setFixedIconItem(MCH_Config.CreativeTabIcon.prmString);
        final MCH_CreativeTabs creativeTabsHeli = MCH_MOD.creativeTabsHeli;
        final MCH_Config config2 = MCH_MOD.config;
        creativeTabsHeli.setFixedIconItem(MCH_Config.CreativeTabIconHeli.prmString);
        final MCH_CreativeTabs creativeTabsPlane = MCH_MOD.creativeTabsPlane;
        final MCH_Config config3 = MCH_MOD.config;
        creativeTabsPlane.setFixedIconItem(MCH_Config.CreativeTabIconPlane.prmString);
        final MCH_CreativeTabs creativeTabsTank = MCH_MOD.creativeTabsTank;
        final MCH_Config config4 = MCH_MOD.config;
        creativeTabsTank.setFixedIconItem(MCH_Config.CreativeTabIconTank.prmString);
        final MCH_CreativeTabs creativeTabsVehicle = MCH_MOD.creativeTabsVehicle;
        final MCH_Config config5 = MCH_MOD.config;
        creativeTabsVehicle.setFixedIconItem(MCH_Config.CreativeTabIconVehicle.prmString);
        MCH_ItemRecipe.registerItemRecipe();
        MCH_WeaponInfoManager.setRoundItems();
        MCH_MOD.proxy.readClientModList();
    }
    
    @Mod.EventHandler
    public void onStartServer(final FMLServerStartingEvent event) {
        MCH_MOD.proxy.registerServerTick();
    }
    
    public void registerEntity() {
        EntityRegistry.registerModEntity((Class)MCH_EntitySeat.class, "MCH.E.Seat", 100, (Object)this, 200, 10, true);
        EntityRegistry.registerModEntity((Class)MCH_EntityHeli.class, "MCH.E.Heli", 101, (Object)this, 200, 10, true);
        EntityRegistry.registerModEntity((Class)MCH_EntityGLTD.class, "MCH.E.GLTD", 102, (Object)this, 200, 10, true);
        EntityRegistry.registerModEntity((Class)MCP_EntityPlane.class, "MCH.E.Plane", 103, (Object)this, 200, 10, true);
        EntityRegistry.registerModEntity((Class)MCH_EntityChain.class, "MCH.E.Chain", 104, (Object)this, 200, 10, true);
        EntityRegistry.registerModEntity((Class)MCH_EntityHitBox.class, "MCH.E.PSeat", 105, (Object)this, 200, 10, true);
        EntityRegistry.registerModEntity((Class)MCH_EntityParachute.class, "MCH.E.Parachute", 106, (Object)this, 200, 10, true);
        EntityRegistry.registerModEntity((Class)MCH_EntityContainer.class, "MCH.E.Container", 107, (Object)this, 200, 10, true);
        EntityRegistry.registerModEntity((Class)MCH_EntityVehicle.class, "MCH.E.Vehicle", 108, (Object)this, 200, 10, true);
        EntityRegistry.registerModEntity((Class)MCH_EntityUavStation.class, "MCH.E.UavStation", 109, (Object)this, 200, 10, true);
        EntityRegistry.registerModEntity((Class)MCH_EntityHitBox.class, "MCH.E.HitBox", 110, (Object)this, 200, 10, true);
        EntityRegistry.registerModEntity((Class)MCH_EntityHide.class, "MCH.E.Hide", 111, (Object)this, 200, 10, true);
        EntityRegistry.registerModEntity((Class)MCH_EntityTank.class, "MCH.E.Tank", 112, (Object)this, 200, 10, true);
        EntityRegistry.registerModEntity((Class)MCH_EntityRocket.class, "MCH.E.Rocket", 200, (Object)this, 530, 5, true);
        EntityRegistry.registerModEntity((Class)MCH_EntityTvMissile.class, "MCH.E.TvMissle", 201, (Object)this, 530, 5, true);
        EntityRegistry.registerModEntity((Class)MCH_EntityBullet.class, "MCH.E.Bullet", 202, (Object)this, 530, 5, true);
        EntityRegistry.registerModEntity((Class)MCH_EntityA10.class, "MCH.E.A10", 203, (Object)this, 530, 5, true);
        EntityRegistry.registerModEntity((Class)MCH_EntityAAMissile.class, "MCH.E.AAM", 204, (Object)this, 530, 5, true);
        EntityRegistry.registerModEntity((Class)MCH_EntityASMissile.class, "MCH.E.ASM", 205, (Object)this, 530, 5, true);
        EntityRegistry.registerModEntity((Class)MCH_EntityTorpedo.class, "MCH.E.Torpedo", 206, (Object)this, 530, 5, true);
        EntityRegistry.registerModEntity((Class)MCH_EntityATMissile.class, "MCH.E.ATMissle", 207, (Object)this, 530, 5, true);
        EntityRegistry.registerModEntity((Class)MCH_EntityBomb.class, "MCH.E.Bomb", 208, (Object)this, 530, 5, true);
        EntityRegistry.registerModEntity((Class)MCH_EntityMarkerRocket.class, "MCH.E.MkRocket", 209, (Object)this, 530, 5, true);
        EntityRegistry.registerModEntity((Class)MCH_EntityDispensedItem.class, "MCH.E.DispItem", 210, (Object)this, 530, 5, true);
        EntityRegistry.registerModEntity((Class)MCH_EntityFlare.class, "MCH.E.Flare", 300, (Object)this, 330, 10, true);
        EntityRegistry.registerModEntity((Class)MCH_EntityThrowable.class, "MCH.E.Throwable", 400, (Object)this, 330, 10, true);
    }
    
    @Mod.EventHandler
    public void registerCommand(final FMLServerStartedEvent e) {
        final CommandHandler handler = (CommandHandler)FMLCommonHandler.instance().getSidedDelegate().getServer().func_71187_D();
        handler.func_71560_a((ICommand)new MCH_Command());
    }
    
    private void registerItemRangeFinder() {
        final String name = "rangefinder";
        final MCH_Config config = MCH_MOD.config;
        final MCH_ItemRangeFinder item = new MCH_ItemRangeFinder(MCH_Config.ItemID_RangeFinder.prmInt);
        registerItem(MCH_MOD.itemRangeFinder = item, "rangefinder", MCH_MOD.creativeTabs);
        W_LanguageRegistry.addName(item, "Laser Rangefinder");
        W_LanguageRegistry.addNameForObject(item, "ja_JP", "\u30ec\u30fc\u30b6\u30fc \u30ec\u30f3\u30b8 \u30d5\u30a1\u30a4\u30f3\u30c0\u30fc");
    }
    
    private void registerItemWrench() {
        final String name = "wrench";
        final MCH_Config config = MCH_MOD.config;
        final MCH_ItemWrench item = new MCH_ItemWrench(MCH_Config.ItemID_Wrench.prmInt, Item.ToolMaterial.IRON);
        registerItem(MCH_MOD.itemWrench = item, "wrench", MCH_MOD.creativeTabs);
        W_LanguageRegistry.addName(item, "Wrench");
        W_LanguageRegistry.addNameForObject(item, "ja_JP", "\u30ec\u30f3\u30c1");
    }
    
    public void registerItemInvisible() {
        final String name = "internal";
        final MCH_Config config = MCH_MOD.config;
        final MCH_InvisibleItem item = new MCH_InvisibleItem(MCH_Config.ItemID_InvisibleItem.prmInt);
        registerItem(MCH_MOD.invisibleItem = item, "internal", null);
    }
    
    public void registerItemUavStation() {
        final String[] dispName = { "UAV Station", "Portable UAV Controller" };
        final String[] localName = { "UAV\u30b9\u30c6\u30fc\u30b7\u30e7\u30f3", "\u643a\u5e2fUAV\u5236\u5fa1\u7aef\u672b" };
        MCH_MOD.itemUavStation = new MCH_ItemUavStation[MCH_ItemUavStation.UAV_STATION_KIND_NUM];
        final String name = "uav_station";
        for (int i = 0; i < MCH_MOD.itemUavStation.length; ++i) {
            final String nn = (i > 0) ? ("" + (i + 1)) : "";
            final MCH_Config config = MCH_MOD.config;
            final MCH_ItemUavStation item = new MCH_ItemUavStation(MCH_Config.ItemID_UavStation[i].prmInt, 1 + i);
            registerItem(MCH_MOD.itemUavStation[i] = item, "uav_station" + nn, MCH_MOD.creativeTabs);
            W_LanguageRegistry.addName(item, dispName[i]);
            W_LanguageRegistry.addNameForObject(item, "ja_JP", localName[i]);
        }
    }
    
    public void registerItemParachute() {
        final String name = "parachute";
        final MCH_Config config = MCH_MOD.config;
        final MCH_ItemParachute item = new MCH_ItemParachute(MCH_Config.ItemID_Parachute.prmInt);
        registerItem(MCH_MOD.itemParachute = item, "parachute", MCH_MOD.creativeTabs);
        W_LanguageRegistry.addName(item, "Parachute");
        W_LanguageRegistry.addNameForObject(item, "ja_JP", "\u30d1\u30e9\u30b7\u30e5\u30fc\u30c8");
    }
    
    public void registerItemContainer() {
        final String name = "container";
        final MCH_Config config = MCH_MOD.config;
        final MCH_ItemContainer item = new MCH_ItemContainer(MCH_Config.ItemID_Container.prmInt);
        registerItem(MCH_MOD.itemContainer = item, "container", MCH_MOD.creativeTabs);
        W_LanguageRegistry.addName(item, "Container");
        W_LanguageRegistry.addNameForObject(item, "ja_JP", "\u30b3\u30f3\u30c6\u30ca");
    }
    
    public void registerItemLightWeapon() {
        String name = "fim92";
        final MCH_Config config = MCH_MOD.config;
        MCH_ItemLightWeaponBase item = new MCH_ItemLightWeaponBase(MCH_Config.ItemID_Stinger.prmInt, MCH_MOD.itemStingerBullet);
        registerItem(MCH_MOD.itemStinger = item, name, MCH_MOD.creativeTabs);
        W_LanguageRegistry.addName(item, "FIM-92 Stinger");
        name = "fgm148";
        final MCH_Config config2 = MCH_MOD.config;
        item = new MCH_ItemLightWeaponBase(MCH_Config.ItemID_Stinger.prmInt, MCH_MOD.itemJavelinBullet);
        registerItem(MCH_MOD.itemJavelin = item, name, MCH_MOD.creativeTabs);
        W_LanguageRegistry.addName(item, "FGM-148 Javelin");
    }
    
    public void registerItemLightWeaponBullet() {
        String name = "fim92_bullet";
        final MCH_Config config = MCH_MOD.config;
        MCH_ItemLightWeaponBullet item = new MCH_ItemLightWeaponBullet(MCH_Config.ItemID_StingerMissile.prmInt);
        registerItem(MCH_MOD.itemStingerBullet = item, name, MCH_MOD.creativeTabs);
        W_LanguageRegistry.addName(item, "FIM-92 Stinger missile");
        name = "fgm148_bullet";
        final MCH_Config config2 = MCH_MOD.config;
        item = new MCH_ItemLightWeaponBullet(MCH_Config.ItemID_StingerMissile.prmInt);
        registerItem(MCH_MOD.itemJavelinBullet = item, name, MCH_MOD.creativeTabs);
        W_LanguageRegistry.addName(item, "FGM-148 Javelin missile");
    }
    
    public void registerItemChain() {
        final String name = "chain";
        final MCH_Config config = MCH_MOD.config;
        final MCH_ItemChain item = new MCH_ItemChain(MCH_Config.ItemID_Chain.prmInt);
        registerItem(MCH_MOD.itemChain = item, "chain", MCH_MOD.creativeTabs);
        W_LanguageRegistry.addName(item, "Chain");
        W_LanguageRegistry.addNameForObject(item, "ja_JP", "\u9396");
    }
    
    public void registerItemFuel() {
        final String name = "fuel";
        final MCH_Config config = MCH_MOD.config;
        final MCH_ItemFuel item = new MCH_ItemFuel(MCH_Config.ItemID_Fuel.prmInt);
        registerItem(MCH_MOD.itemFuel = item, "fuel", MCH_MOD.creativeTabs);
        W_LanguageRegistry.addName(item, "Fuel");
        W_LanguageRegistry.addNameForObject(item, "ja_JP", "\u71c3\u6599");
    }
    
    public void registerItemGLTD() {
        final String name = "gltd";
        final MCH_Config config = MCH_MOD.config;
        final MCH_ItemGLTD item = new MCH_ItemGLTD(MCH_Config.ItemID_GLTD.prmInt);
        registerItem(MCH_MOD.itemGLTD = item, "gltd", MCH_MOD.creativeTabs);
        W_LanguageRegistry.addName(item, "GLTD:Target Designator");
        W_LanguageRegistry.addNameForObject(item, "ja_JP", "GLTD:\u30ec\u30fc\u30b6\u30fc\u76ee\u6a19\u6307\u793a\u88c5\u7f6e");
    }
    
    public static void registerItem(final W_Item item, final String name, final MCH_CreativeTabs ct) {
        item.func_77655_b("mcheli:" + name);
        item.setTexture(name);
        if (ct != null) {
            item.func_77637_a((CreativeTabs)ct);
            ct.addIconItem(item);
        }
        GameRegistry.registerItem((Item)item, name);
    }
    
    public static void registerItemThrowable() {
        for (final String name : MCH_ThrowableInfoManager.getKeySet()) {
            final MCH_ThrowableInfo info = MCH_ThrowableInfoManager.get(name);
            (info.item = new MCH_ItemThrowable(info.itemID)).func_77625_d(info.stackSize);
            registerItem(info.item, name, MCH_MOD.creativeTabs);
            MCH_ItemThrowable.registerDispenseBehavior(info.item);
            info.itemID = W_Item.getIdFromItem(info.item) - 256;
            W_LanguageRegistry.addName(info.item, info.displayName);
            for (final String lang : info.displayNameLang.keySet()) {
                W_LanguageRegistry.addNameForObject(info.item, lang, info.displayNameLang.get(lang));
            }
        }
    }
    
    public static void registerItemAircraft() {
        for (final String name : MCH_HeliInfoManager.map.keySet()) {
            final MCH_HeliInfo info = MCH_HeliInfoManager.map.get(name);
            (info.item = new MCH_ItemHeli(info.itemID)).func_77656_e(info.maxHp);
            if (!info.canRide && (info.ammoSupplyRange > 0.0f || info.fuelSupplyRange > 0.0f)) {
                registerItem(info.item, name, MCH_MOD.creativeTabs);
            }
            else {
                registerItem(info.item, name, MCH_MOD.creativeTabsHeli);
            }
            MCH_ItemAircraft.registerDispenseBehavior(info.item);
            info.itemID = W_Item.getIdFromItem(info.item) - 256;
            W_LanguageRegistry.addName(info.item, info.displayName);
            for (final String lang : info.displayNameLang.keySet()) {
                W_LanguageRegistry.addNameForObject(info.item, lang, info.displayNameLang.get(lang));
            }
        }
        for (final String name : MCP_PlaneInfoManager.map.keySet()) {
            final MCP_PlaneInfo info2 = MCP_PlaneInfoManager.map.get(name);
            (info2.item = new MCP_ItemPlane(info2.itemID)).func_77656_e(info2.maxHp);
            if (!info2.canRide && (info2.ammoSupplyRange > 0.0f || info2.fuelSupplyRange > 0.0f)) {
                registerItem(info2.item, name, MCH_MOD.creativeTabs);
            }
            else {
                registerItem(info2.item, name, MCH_MOD.creativeTabsPlane);
            }
            MCH_ItemAircraft.registerDispenseBehavior(info2.item);
            info2.itemID = W_Item.getIdFromItem(info2.item) - 256;
            W_LanguageRegistry.addName(info2.item, info2.displayName);
            for (final String lang : info2.displayNameLang.keySet()) {
                W_LanguageRegistry.addNameForObject(info2.item, lang, info2.displayNameLang.get(lang));
            }
        }
        for (final String name : MCH_TankInfoManager.map.keySet()) {
            final MCH_TankInfo info3 = MCH_TankInfoManager.map.get(name);
            (info3.item = new MCH_ItemTank(info3.itemID)).func_77656_e(info3.maxHp);
            if (!info3.canRide && (info3.ammoSupplyRange > 0.0f || info3.fuelSupplyRange > 0.0f)) {
                registerItem(info3.item, name, MCH_MOD.creativeTabs);
            }
            else {
                registerItem(info3.item, name, MCH_MOD.creativeTabsTank);
            }
            MCH_ItemAircraft.registerDispenseBehavior(info3.item);
            info3.itemID = W_Item.getIdFromItem(info3.item) - 256;
            W_LanguageRegistry.addName(info3.item, info3.displayName);
            for (final String lang : info3.displayNameLang.keySet()) {
                W_LanguageRegistry.addNameForObject(info3.item, lang, info3.displayNameLang.get(lang));
            }
        }
        for (final String name : MCH_VehicleInfoManager.map.keySet()) {
            final MCH_VehicleInfo info4 = MCH_VehicleInfoManager.map.get(name);
            (info4.item = new MCH_ItemVehicle(info4.itemID)).func_77656_e(info4.maxHp);
            if (!info4.canRide && (info4.ammoSupplyRange > 0.0f || info4.fuelSupplyRange > 0.0f)) {
                registerItem(info4.item, name, MCH_MOD.creativeTabs);
            }
            else {
                registerItem(info4.item, name, MCH_MOD.creativeTabsVehicle);
            }
            MCH_ItemAircraft.registerDispenseBehavior(info4.item);
            info4.itemID = W_Item.getIdFromItem(info4.item) - 256;
            W_LanguageRegistry.addName(info4.item, info4.displayName);
            for (final String lang : info4.displayNameLang.keySet()) {
                W_LanguageRegistry.addNameForObject(info4.item, lang, info4.displayNameLang.get(lang));
            }
        }
    }
    
    static {
        MCH_MOD.VER = "";
        MCH_MOD.packetHandler = new MCH_PacketHandler();
    }
}
