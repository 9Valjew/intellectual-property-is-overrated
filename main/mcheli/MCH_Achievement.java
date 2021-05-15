package mcheli;

import net.minecraft.item.*;
import net.minecraftforge.common.*;
import mcheli.wrapper.*;
import mcheli.plane.*;
import mcheli.vehicle.*;
import mcheli.aircraft.*;
import mcheli.helicopter.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;

public class MCH_Achievement
{
    public static Achievement welcome;
    public static Achievement supplyFuel;
    public static Achievement supplyAmmo;
    public static Achievement aintWarHell;
    public static Achievement reliefSupplies;
    public static Achievement rideValkyries;
    
    public static void PreInit() {
        Item item = getAnyAircraftIcon("ah-64");
        final int BC = 1;
        final int BR = 1;
        String name = "McHeliWelcome";
        W_LanguageRegistry.addNameForObject(MCH_Achievement.welcome = W_Achievement.registerAchievement("mcheli" + name, name, 1, 1, item, null), "en_US", "Welcome to MC Helicopter MOD", name, "Put the helicopter");
        W_LanguageRegistry.addNameForObject(MCH_Achievement.welcome, "ja_JP", "MC Helicopter MOD \u3078\u3088\u3046\u3053\u305d", name, "\u30d8\u30ea\u30b3\u30d7\u30bf\u30fc\u3092\u8a2d\u7f6e");
        name = "McHeliSupplyFuel";
        W_LanguageRegistry.addNameForObject(MCH_Achievement.supplyFuel = W_Achievement.registerAchievement("mcheli" + name, name, -1, 1, MCH_MOD.itemFuel, null), "en_US", "Refueling", name, "Refuel aircraft");
        W_LanguageRegistry.addNameForObject(MCH_Achievement.supplyFuel, "ja_JP", "\u71c3\u6599\u88dc\u7d66", name, "\u71c3\u6599\u3092\u88dc\u7d66");
        item = getAircraftIcon("ammo_box");
        name = "McHeliSupplyAmmo";
        W_LanguageRegistry.addNameForObject(MCH_Achievement.supplyAmmo = W_Achievement.registerAchievement("mcheli" + name, name, 3, 1, item, null), "en_US", "Supply ammo", name, "Supply ammo to the aircraft");
        W_LanguageRegistry.addNameForObject(MCH_Achievement.supplyAmmo, "ja_JP", "\u5f3e\u85ac\u88dc\u7d66", name, "\u5f3e\u85ac\u3092\u88dc\u7d66");
        item = getAircraftIcon("uh-1c");
        name = "McHeliRideValkyries";
        W_LanguageRegistry.addNameForObject(MCH_Achievement.rideValkyries = W_Achievement.registerAchievement("mcheli" + name, name, -1, 3, item, null), "en_US", "Ride Of The Valkyries", name, "?");
        W_LanguageRegistry.addNameForObject(MCH_Achievement.rideValkyries, "ja_JP", "\u30ef\u30eb\u30ad\u30e5\u30fc\u30ec\u306e\u9a0e\u884c", name, "?");
        item = getAircraftIcon("mh-60l_dap");
        name = "McHeliAintWarHell";
        W_LanguageRegistry.addNameForObject(MCH_Achievement.aintWarHell = W_Achievement.registerAchievement("mcheli" + name, name, 3, 3, item, null), "en_US", "Ain't war hell?", name, "?");
        W_LanguageRegistry.addNameForObject(MCH_Achievement.aintWarHell, "ja_JP", "\u30db\u30f3\u30c8\u6226\u4e89\u306f\u5730\u7344\u3060\u305c", name, "?");
        item = MCH_MOD.itemContainer;
        name = "McHeliReliefSupplies";
        W_LanguageRegistry.addNameForObject(MCH_Achievement.reliefSupplies = W_Achievement.registerAchievement("mcheli" + name, name, -1, -1, item, null), "en_US", "Relief supplies", name, "Drop a container");
        W_LanguageRegistry.addNameForObject(MCH_Achievement.reliefSupplies, "ja_JP", "\u652f\u63f4\u7269\u8cc7", name, "\u30b3\u30f3\u30c6\u30ca\u3092\u6295\u4e0b");
        final Achievement[] achievements = { MCH_Achievement.welcome, MCH_Achievement.supplyFuel, MCH_Achievement.supplyAmmo, MCH_Achievement.aintWarHell, MCH_Achievement.rideValkyries, MCH_Achievement.reliefSupplies };
        AchievementPage.registerAchievementPage(new AchievementPage("MC Helicopter", achievements));
    }
    
    public static Item getAircraftIcon(final String defaultIconAircraft) {
        final Item item = W_Item.getItemByName("stone");
        MCH_AircraftInfo info = MCH_HeliInfoManager.get(defaultIconAircraft);
        if (info != null && info.getItem() != null) {
            return info.getItem();
        }
        info = MCP_PlaneInfoManager.get(defaultIconAircraft);
        if (info != null && info.getItem() != null) {
            return info.getItem();
        }
        info = MCH_VehicleInfoManager.get(defaultIconAircraft);
        if (info != null && info.getItem() != null) {
            return info.getItem();
        }
        return item;
    }
    
    public static Item getAnyAircraftIcon(final String defaultIconAircraft) {
        Item item = W_Item.getItemByName("stone");
        if (MCH_HeliInfoManager.map.size() > 0) {
            final MCH_HeliInfo info = MCH_HeliInfoManager.get(defaultIconAircraft);
            if (info != null && info.item != null) {
                item = info.item;
            }
            else {
                for (final MCH_HeliInfo i : MCH_HeliInfoManager.map.values()) {
                    if (i.item != null) {
                        item = i.item;
                        break;
                    }
                }
            }
        }
        return item;
    }
    
    public static void addStat(final Entity player, final Achievement a, final int i) {
        if (a != null && player instanceof EntityPlayer && !player.field_70170_p.field_72995_K) {
            ((EntityPlayer)player).func_71064_a((StatBase)a, i);
        }
    }
    
    static {
        MCH_Achievement.welcome = null;
        MCH_Achievement.supplyFuel = null;
        MCH_Achievement.supplyAmmo = null;
        MCH_Achievement.aintWarHell = null;
        MCH_Achievement.reliefSupplies = null;
        MCH_Achievement.rideValkyries = null;
    }
}
