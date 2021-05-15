package mcheli.gui;

import cpw.mods.fml.common.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.server.*;
import mcheli.*;
import mcheli.uav.*;
import mcheli.aircraft.*;
import mcheli.block.*;
import mcheli.multiplay.*;

public class MCH_GuiCommonHandler implements IGuiHandler
{
    public static final int GUIID_UAV_STATION = 0;
    public static final int GUIID_AIRCRAFT = 1;
    public static final int GUIID_CONFG = 2;
    public static final int GUIID_INVENTORY = 3;
    public static final int GUIID_DRAFTING = 4;
    public static final int GUIID_MULTI_MNG = 5;
    
    public Object getServerGuiElement(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z) {
        MCH_Lib.DbgLog(world, "MCH_GuiCommonHandler.getServerGuiElement ID=%d (%d, %d, %d)", id, x, y, z);
        switch (id) {
            case 0: {
                if (player.field_70154_o instanceof MCH_EntityUavStation) {
                    return new MCH_ContainerUavStation(player.field_71071_by, (MCH_EntityUavStation)player.field_70154_o);
                }
                break;
            }
            case 1: {
                MCH_EntityAircraft ac = null;
                if (player.field_70154_o instanceof MCH_EntityAircraft) {
                    ac = (MCH_EntityAircraft)player.field_70154_o;
                }
                else if (player.field_70154_o instanceof MCH_EntityUavStation) {
                    ac = ((MCH_EntityUavStation)player.field_70154_o).getControlAircract();
                }
                if (ac != null) {
                    return new MCH_AircraftGuiContainer(player, ac);
                }
                break;
            }
            case 2: {
                return new MCH_ConfigGuiContainer(player);
            }
            case 4: {
                return new MCH_DraftingTableGuiContainer(player, x, y, z);
            }
            case 5: {
                if (MinecraftServer.func_71276_C().func_71264_H()) {
                    final MCH_Config config = MCH_MOD.config;
                    if (!MCH_Config.DebugLog) {
                        break;
                    }
                }
                return new MCH_ContainerScoreboard(player);
            }
        }
        return null;
    }
    
    public Object getClientGuiElement(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z) {
        MCH_Lib.DbgLog(world, "MCH_GuiCommonHandler.getClientGuiElement ID=%d (%d, %d, %d)", id, x, y, z);
        switch (id) {
            case 0: {
                if (player.field_70154_o instanceof MCH_EntityUavStation) {
                    return new MCH_GuiUavStation(player.field_71071_by, (MCH_EntityUavStation)player.field_70154_o);
                }
                break;
            }
            case 1: {
                MCH_EntityAircraft ac = null;
                if (player.field_70154_o instanceof MCH_EntityAircraft) {
                    ac = (MCH_EntityAircraft)player.field_70154_o;
                }
                else if (player.field_70154_o instanceof MCH_EntityUavStation) {
                    ac = ((MCH_EntityUavStation)player.field_70154_o).getControlAircract();
                }
                if (ac != null) {
                    return new MCH_AircraftGui(player, ac);
                }
                break;
            }
            case 2: {
                return new MCH_ConfigGui(player);
            }
            case 4: {
                return new MCH_DraftingTableGui(player, x, y, z);
            }
            case 5: {
                return new MCH_GuiScoreboard(player);
            }
        }
        return null;
    }
}
