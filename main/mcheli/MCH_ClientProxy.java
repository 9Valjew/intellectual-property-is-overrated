package mcheli;

import net.minecraft.client.*;
import mcheli.debug.*;
import net.minecraft.client.renderer.entity.*;
import mcheli.chain.*;
import mcheli.parachute.*;
import mcheli.container.*;
import mcheli.uav.*;
import mcheli.flare.*;
import mcheli.lweapon.*;
import net.minecraft.item.*;
import net.minecraftforge.client.*;
import mcheli.gltd.*;
import mcheli.tool.*;
import mcheli.tool.rangefinder.*;
import cpw.mods.fml.client.registry.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.block.*;
import mcheli.block.*;
import mcheli.throwable.*;
import java.util.*;
import mcheli.weapon.*;
import net.minecraftforge.client.model.*;
import mcheli.helicopter.*;
import mcheli.plane.*;
import mcheli.vehicle.*;
import mcheli.tank.*;
import mcheli.wrapper.modelloader.*;
import cpw.mods.fml.relauncher.*;
import mcheli.aircraft.*;
import net.minecraft.client.entity.*;
import mcheli.hud.*;
import net.minecraft.entity.*;
import net.minecraftforge.common.*;
import mcheli.particles.*;
import mcheli.wrapper.*;
import mcheli.multiplay.*;
import net.minecraft.util.*;
import mcheli.command.*;

public class MCH_ClientProxy extends MCH_CommonProxy
{
    public String lastLoadHUDPath;
    
    public MCH_ClientProxy() {
        this.lastLoadHUDPath = "";
    }
    
    @Override
    public String getDataDir() {
        return Minecraft.func_71410_x().field_71412_D.getPath();
    }
    
    @Override
    public void registerRenderer() {
        RenderingRegistry.registerEntityRenderingHandler((Class)MCH_EntitySeat.class, (Render)new MCH_RenderTest(0.0f, 0.0f, 0.0f, "seat"));
        RenderingRegistry.registerEntityRenderingHandler((Class)MCH_EntityHeli.class, (Render)new MCH_RenderHeli());
        RenderingRegistry.registerEntityRenderingHandler((Class)MCP_EntityPlane.class, (Render)new MCP_RenderPlane());
        RenderingRegistry.registerEntityRenderingHandler((Class)MCH_EntityTank.class, (Render)new MCH_RenderTank());
        RenderingRegistry.registerEntityRenderingHandler((Class)MCH_EntityGLTD.class, (Render)new MCH_RenderGLTD());
        RenderingRegistry.registerEntityRenderingHandler((Class)MCH_EntityChain.class, (Render)new MCH_RenderChain());
        RenderingRegistry.registerEntityRenderingHandler((Class)MCH_EntityParachute.class, (Render)new MCH_RenderParachute());
        RenderingRegistry.registerEntityRenderingHandler((Class)MCH_EntityContainer.class, (Render)new MCH_RenderContainer());
        RenderingRegistry.registerEntityRenderingHandler((Class)MCH_EntityVehicle.class, (Render)new MCH_RenderVehicle());
        RenderingRegistry.registerEntityRenderingHandler((Class)MCH_EntityUavStation.class, (Render)new MCH_RenderUavStation());
        RenderingRegistry.registerEntityRenderingHandler((Class)MCH_EntityCartridge.class, (Render)new MCH_RenderCartridge());
        RenderingRegistry.registerEntityRenderingHandler((Class)MCH_EntityHide.class, (Render)new MCH_RenderNull());
        RenderingRegistry.registerEntityRenderingHandler((Class)MCH_ViewEntityDummy.class, (Render)new MCH_RenderNull());
        RenderingRegistry.registerEntityRenderingHandler((Class)MCH_EntityRocket.class, (Render)new MCH_RenderBullet());
        RenderingRegistry.registerEntityRenderingHandler((Class)MCH_EntityTvMissile.class, (Render)new MCH_RenderTvMissile());
        RenderingRegistry.registerEntityRenderingHandler((Class)MCH_EntityBullet.class, (Render)new MCH_RenderBullet());
        RenderingRegistry.registerEntityRenderingHandler((Class)MCH_EntityA10.class, (Render)new MCH_RenderA10());
        RenderingRegistry.registerEntityRenderingHandler((Class)MCH_EntityAAMissile.class, (Render)new MCH_RenderAAMissile());
        RenderingRegistry.registerEntityRenderingHandler((Class)MCH_EntityASMissile.class, (Render)new MCH_RenderASMissile());
        RenderingRegistry.registerEntityRenderingHandler((Class)MCH_EntityATMissile.class, (Render)new MCH_RenderTvMissile());
        RenderingRegistry.registerEntityRenderingHandler((Class)MCH_EntityTorpedo.class, (Render)new MCH_RenderBullet());
        RenderingRegistry.registerEntityRenderingHandler((Class)MCH_EntityBomb.class, (Render)new MCH_RenderBomb());
        RenderingRegistry.registerEntityRenderingHandler((Class)MCH_EntityMarkerRocket.class, (Render)new MCH_RenderBullet());
        RenderingRegistry.registerEntityRenderingHandler((Class)MCH_EntityDispensedItem.class, (Render)new MCH_RenderNone());
        RenderingRegistry.registerEntityRenderingHandler((Class)MCH_EntityFlare.class, (Render)new MCH_RenderFlare());
        RenderingRegistry.registerEntityRenderingHandler((Class)MCH_EntityThrowable.class, (Render)new MCH_RenderThrowable());
        W_MinecraftForgeClient.registerItemRenderer(MCH_MOD.itemJavelin, (IItemRenderer)new MCH_ItemLightWeaponRender());
        W_MinecraftForgeClient.registerItemRenderer(MCH_MOD.itemStinger, (IItemRenderer)new MCH_ItemLightWeaponRender());
        W_MinecraftForgeClient.registerItemRenderer(MCH_MOD.invisibleItem, (IItemRenderer)new MCH_InvisibleItemRender());
        W_MinecraftForgeClient.registerItemRenderer(MCH_MOD.itemGLTD, (IItemRenderer)new MCH_ItemGLTDRender());
        W_MinecraftForgeClient.registerItemRenderer(MCH_MOD.itemWrench, (IItemRenderer)new MCH_ItemRenderWrench());
        W_MinecraftForgeClient.registerItemRenderer(MCH_MOD.itemRangeFinder, (IItemRenderer)new MCH_ItemRenderRangeFinder());
    }
    
    @Override
    public void registerBlockRenderer() {
        ClientRegistry.bindTileEntitySpecialRenderer((Class)MCH_DraftingTableTileEntity.class, (TileEntitySpecialRenderer)new MCH_DraftingTableRenderer());
        W_MinecraftForgeClient.registerItemRenderer(W_Item.getItemFromBlock((Block)MCH_MOD.blockDraftingTable), (IItemRenderer)new MCH_DraftingTableItemRender());
    }
    
    @Override
    public void registerModels() {
        MCH_ModelManager.setForceReloadMode(true);
        MCH_RenderAircraft.debugModel = MCH_ModelManager.load("box");
        MCH_ModelManager.load("a-10");
        MCH_RenderGLTD.model = MCH_ModelManager.load("gltd");
        MCH_ModelManager.load("chain");
        MCH_ModelManager.load("container");
        MCH_ModelManager.load("parachute1");
        MCH_ModelManager.load("parachute2");
        MCH_ModelManager.load("lweapons", "fim92");
        MCH_ModelManager.load("lweapons", "fgm148");
        for (final String s : MCH_RenderUavStation.MODEL_NAME) {
            MCH_ModelManager.load(s);
        }
        MCH_ModelManager.load("wrench");
        MCH_ModelManager.load("rangefinder");
        MCH_HeliInfoManager.getInstance();
        for (final String key : MCH_HeliInfoManager.map.keySet()) {
            this.registerModelsHeli(key, false);
        }
        for (final String key : MCP_PlaneInfoManager.map.keySet()) {
            this.registerModelsPlane(key, false);
        }
        MCH_TankInfoManager.getInstance();
        for (final String key : MCH_TankInfoManager.map.keySet()) {
            this.registerModelsTank(key, false);
        }
        for (final String key : MCH_VehicleInfoManager.map.keySet()) {
            this.registerModelsVehicle(key, false);
        }
        registerModels_Bullet();
        MCH_DefaultBulletModels.Bullet = this.loadBulletModel("bullet");
        MCH_DefaultBulletModels.AAMissile = this.loadBulletModel("aamissile");
        MCH_DefaultBulletModels.ATMissile = this.loadBulletModel("asmissile");
        MCH_DefaultBulletModels.ASMissile = this.loadBulletModel("asmissile");
        MCH_DefaultBulletModels.Bomb = this.loadBulletModel("bomb");
        MCH_DefaultBulletModels.Rocket = this.loadBulletModel("rocket");
        MCH_DefaultBulletModels.Torpedo = this.loadBulletModel("torpedo");
        for (final MCH_ThrowableInfo wi : MCH_ThrowableInfoManager.getValues()) {
            wi.model = MCH_ModelManager.load("throwable", wi.name);
        }
        MCH_ModelManager.load("blocks", "drafting_table");
    }
    
    public static void registerModels_Bullet() {
        for (final MCH_WeaponInfo wi : MCH_WeaponInfoManager.getValues()) {
            IModelCustom m = null;
            if (!wi.bulletModelName.isEmpty()) {
                m = MCH_ModelManager.load("bullets", wi.bulletModelName);
                if (m != null) {
                    wi.bulletModel = new MCH_BulletModel(wi.bulletModelName, m);
                }
            }
            if (!wi.bombletModelName.isEmpty()) {
                m = MCH_ModelManager.load("bullets", wi.bombletModelName);
                if (m != null) {
                    wi.bombletModel = new MCH_BulletModel(wi.bombletModelName, m);
                }
            }
            if (wi.cartridge != null && !wi.cartridge.name.isEmpty()) {
                wi.cartridge.model = MCH_ModelManager.load("bullets", wi.cartridge.name);
                if (wi.cartridge.model != null) {
                    continue;
                }
                wi.cartridge = null;
            }
        }
    }
    
    @Override
    public void registerModelsHeli(final String name, final boolean reload) {
        MCH_ModelManager.setForceReloadMode(reload);
        final MCH_HeliInfo info = MCH_HeliInfoManager.map.get(name);
        info.model = MCH_ModelManager.load("helicopters", info.name);
        for (final MCH_HeliInfo.Rotor rotor : info.rotorList) {
            rotor.model = this.loadPartModel("helicopters", info.name, info.model, rotor.modelName);
        }
        this.registerCommonPart("helicopters", info);
        MCH_ModelManager.setForceReloadMode(false);
    }
    
    @Override
    public void registerModelsPlane(final String name, final boolean reload) {
        MCH_ModelManager.setForceReloadMode(reload);
        final MCP_PlaneInfo info = MCP_PlaneInfoManager.map.get(name);
        info.model = MCH_ModelManager.load("planes", info.name);
        for (final MCH_AircraftInfo.DrawnPart n : info.nozzles) {
            n.model = this.loadPartModel("planes", info.name, info.model, n.modelName);
        }
        for (final MCP_PlaneInfo.Rotor r : info.rotorList) {
            r.model = this.loadPartModel("planes", info.name, info.model, r.modelName);
            for (final MCP_PlaneInfo.Blade b : r.blades) {
                b.model = this.loadPartModel("planes", info.name, info.model, b.modelName);
            }
        }
        for (final MCP_PlaneInfo.Wing w : info.wingList) {
            w.model = this.loadPartModel("planes", info.name, info.model, w.modelName);
            if (w.pylonList != null) {
                for (final MCP_PlaneInfo.Pylon p : w.pylonList) {
                    p.model = this.loadPartModel("planes", info.name, info.model, p.modelName);
                }
            }
        }
        this.registerCommonPart("planes", info);
        MCH_ModelManager.setForceReloadMode(false);
    }
    
    @Override
    public void registerModelsVehicle(final String name, final boolean reload) {
        MCH_ModelManager.setForceReloadMode(reload);
        final MCH_VehicleInfo info = MCH_VehicleInfoManager.map.get(name);
        info.model = MCH_ModelManager.load("vehicles", info.name);
        for (final MCH_VehicleInfo.VPart vp : info.partList) {
            vp.model = this.loadPartModel("vehicles", info.name, info.model, vp.modelName);
            if (vp.child != null) {
                this.registerVCPModels(info, vp);
            }
        }
        this.registerCommonPart("vehicles", info);
        MCH_ModelManager.setForceReloadMode(false);
    }
    
    @Override
    public void registerModelsTank(final String name, final boolean reload) {
        MCH_ModelManager.setForceReloadMode(reload);
        final MCH_TankInfo info = MCH_TankInfoManager.map.get(name);
        info.model = MCH_ModelManager.load("tanks", info.name);
        this.registerCommonPart("tanks", info);
        MCH_ModelManager.setForceReloadMode(false);
    }
    
    private MCH_BulletModel loadBulletModel(final String name) {
        final IModelCustom m = MCH_ModelManager.load("bullets", name);
        return (m != null) ? new MCH_BulletModel(name, m) : null;
    }
    
    private IModelCustom loadPartModel(final String path, final String name, final IModelCustom body, final String part) {
        if (body instanceof W_ModelCustom && ((W_ModelCustom)body).containsPart("$" + part)) {
            return null;
        }
        return MCH_ModelManager.load(path, name + "_" + part);
    }
    
    private void registerCommonPart(final String path, final MCH_AircraftInfo info) {
        for (final MCH_AircraftInfo.Hatch h : info.hatchList) {
            h.model = this.loadPartModel(path, info.name, info.model, h.modelName);
        }
        for (final MCH_AircraftInfo.Camera c : info.cameraList) {
            c.model = this.loadPartModel(path, info.name, info.model, c.modelName);
        }
        for (final MCH_AircraftInfo.Throttle c2 : info.partThrottle) {
            c2.model = this.loadPartModel(path, info.name, info.model, c2.modelName);
        }
        for (final MCH_AircraftInfo.RotPart c3 : info.partRotPart) {
            c3.model = this.loadPartModel(path, info.name, info.model, c3.modelName);
        }
        for (final MCH_AircraftInfo.PartWeapon p : info.partWeapon) {
            p.model = this.loadPartModel(path, info.name, info.model, p.modelName);
            for (final MCH_AircraftInfo.PartWeaponChild wc : p.child) {
                wc.model = this.loadPartModel(path, info.name, info.model, wc.modelName);
            }
        }
        for (final MCH_AircraftInfo.Canopy c4 : info.canopyList) {
            c4.model = this.loadPartModel(path, info.name, info.model, c4.modelName);
        }
        for (final MCH_AircraftInfo.DrawnPart n : info.landingGear) {
            n.model = this.loadPartModel(path, info.name, info.model, n.modelName);
        }
        for (final MCH_AircraftInfo.WeaponBay w : info.partWeaponBay) {
            w.model = this.loadPartModel(path, info.name, info.model, w.modelName);
        }
        for (final MCH_AircraftInfo.CrawlerTrack c5 : info.partCrawlerTrack) {
            c5.model = this.loadPartModel(path, info.name, info.model, c5.modelName);
        }
        for (final MCH_AircraftInfo.TrackRoller c6 : info.partTrackRoller) {
            c6.model = this.loadPartModel(path, info.name, info.model, c6.modelName);
        }
        for (final MCH_AircraftInfo.PartWheel c7 : info.partWheel) {
            c7.model = this.loadPartModel(path, info.name, info.model, c7.modelName);
        }
        for (final MCH_AircraftInfo.PartWheel c7 : info.partSteeringWheel) {
            c7.model = this.loadPartModel(path, info.name, info.model, c7.modelName);
        }
    }
    
    private void registerVCPModels(final MCH_VehicleInfo info, final MCH_VehicleInfo.VPart vp) {
        for (final MCH_VehicleInfo.VPart vcp : vp.child) {
            vcp.model = this.loadPartModel("vehicles", info.name, info.model, vcp.modelName);
            if (vcp.child != null) {
                this.registerVCPModels(info, vcp);
            }
        }
    }
    
    @Override
    public void registerClientTick() {
        final Minecraft mc = Minecraft.func_71410_x();
        W_TickRegistry.registerTickHandler(MCH_ClientCommonTickHandler.instance = new MCH_ClientCommonTickHandler(mc, MCH_MOD.config), Side.CLIENT);
    }
    
    @Override
    public boolean isRemote() {
        return true;
    }
    
    @Override
    public String side() {
        return "Client";
    }
    
    @Override
    public MCH_SoundUpdater CreateSoundUpdater(final MCH_EntityAircraft aircraft) {
        if (aircraft == null || !aircraft.field_70170_p.field_72995_K) {
            return null;
        }
        return new MCH_SoundUpdater(Minecraft.func_71410_x(), aircraft, (EntityPlayerSP)Minecraft.func_71410_x().field_71439_g);
    }
    
    @Override
    public void registerSounds() {
        W_McClient.addSound("alert.ogg");
        W_McClient.addSound("locked.ogg");
        W_McClient.addSound("gltd.ogg");
        W_McClient.addSound("zoom.ogg");
        W_McClient.addSound("ng.ogg");
        W_McClient.addSound("a-10_snd.ogg");
        W_McClient.addSound("gau-8_snd.ogg");
        W_McClient.addSound("hit.ogg");
        W_McClient.addSound("helidmg.ogg");
        W_McClient.addSound("heli.ogg");
        W_McClient.addSound("plane.ogg");
        W_McClient.addSound("plane_cc.ogg");
        W_McClient.addSound("plane_cv.ogg");
        W_McClient.addSound("chain.ogg");
        W_McClient.addSound("chain_ct.ogg");
        W_McClient.addSound("eject_seat.ogg");
        W_McClient.addSound("fim92_snd.ogg");
        W_McClient.addSound("fim92_reload.ogg");
        W_McClient.addSound("lockon.ogg");
        for (final MCH_WeaponInfo info : MCH_WeaponInfoManager.getValues()) {
            W_McClient.addSound(info.soundFileName + ".ogg");
        }
        for (final MCH_AircraftInfo info2 : MCP_PlaneInfoManager.map.values()) {
            if (!info2.soundMove.isEmpty()) {
                W_McClient.addSound(info2.soundMove + ".ogg");
            }
        }
        for (final MCH_AircraftInfo info2 : MCH_HeliInfoManager.map.values()) {
            if (!info2.soundMove.isEmpty()) {
                W_McClient.addSound(info2.soundMove + ".ogg");
            }
        }
        for (final MCH_AircraftInfo info2 : MCH_TankInfoManager.map.values()) {
            if (!info2.soundMove.isEmpty()) {
                W_McClient.addSound(info2.soundMove + ".ogg");
            }
        }
        for (final MCH_AircraftInfo info2 : MCH_VehicleInfoManager.map.values()) {
            if (!info2.soundMove.isEmpty()) {
                W_McClient.addSound(info2.soundMove + ".ogg");
            }
        }
    }
    
    @Override
    public MCH_Config loadConfig(final String fileName) {
        this.lastConfigFileName = fileName;
        final MCH_Config config = new MCH_Config(Minecraft.func_71410_x().field_71412_D.getPath(), "/" + fileName);
        config.load();
        config.write();
        return config;
    }
    
    @Override
    public MCH_Config reconfig() {
        MCH_Lib.DbgLog(false, "MCH_ClientProxy.reconfig()", new Object[0]);
        final MCH_Config config = this.loadConfig(this.lastConfigFileName);
        MCH_ClientCommonTickHandler.instance.updatekeybind(config);
        return config;
    }
    
    @Override
    public void loadHUD(final String path) {
        MCH_HudManager.load(this.lastLoadHUDPath = path);
    }
    
    @Override
    public void reloadHUD() {
        this.loadHUD(this.lastLoadHUDPath);
    }
    
    @Override
    public Entity getClientPlayer() {
        return (Entity)Minecraft.func_71410_x().field_71439_g;
    }
    
    @Override
    public void init() {
        MinecraftForge.EVENT_BUS.register((Object)new MCH_ParticlesUtil());
        MinecraftForge.EVENT_BUS.register((Object)new MCH_ClientEventHook());
    }
    
    @Override
    public void setCreativeDigDelay(final int n) {
        W_Reflection.setCreativeDigSpeed(n);
    }
    
    @Override
    public boolean isFirstPerson() {
        return Minecraft.func_71410_x().field_71474_y.field_74320_O == 0;
    }
    
    @Override
    public int getNewRenderType() {
        return RenderingRegistry.getNextAvailableRenderId();
    }
    
    @Override
    public boolean isSinglePlayer() {
        return Minecraft.func_71410_x().func_71356_B();
    }
    
    @Override
    public void readClientModList() {
        try {
            final Minecraft mc = Minecraft.func_71410_x();
            MCH_MultiplayClient.readModList(mc.func_110432_I().func_148255_b());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void printChatMessage(final IChatComponent chat, final int showTime, final int pos) {
        ((MCH_GuiTitle)MCH_ClientCommonTickHandler.instance.gui_Title).setupTitle(chat, showTime, pos);
    }
    
    @Override
    public void hitBullet() {
        MCH_ClientCommonTickHandler.instance.gui_Common.hitBullet();
    }
    
    @Override
    public void clientLocked() {
        MCH_ClientCommonTickHandler.isLocked = true;
    }
}
