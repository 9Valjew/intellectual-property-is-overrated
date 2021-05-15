package mcheli.plane;

import mcheli.*;
import net.minecraft.item.*;
import mcheli.aircraft.*;
import java.util.*;

public class MCP_PlaneInfoManager extends MCH_AircraftInfoManager
{
    private static MCP_PlaneInfoManager instance;
    public static HashMap<String, MCP_PlaneInfo> map;
    
    public static MCP_PlaneInfo get(final String name) {
        return MCP_PlaneInfoManager.map.get(name);
    }
    
    public static MCP_PlaneInfoManager getInstance() {
        return MCP_PlaneInfoManager.instance;
    }
    
    @Override
    public MCH_BaseInfo newInfo(final String name) {
        return new MCP_PlaneInfo(name);
    }
    
    @Override
    public Map getMap() {
        return MCP_PlaneInfoManager.map;
    }
    
    public static MCP_PlaneInfo getFromItem(final Item item) {
        return getInstance().getAcInfoFromItem(item);
    }
    
    @Override
    public MCP_PlaneInfo getAcInfoFromItem(final Item item) {
        if (item == null) {
            return null;
        }
        for (final MCP_PlaneInfo info : MCP_PlaneInfoManager.map.values()) {
            if (info.item == item) {
                return info;
            }
        }
        return null;
    }
    
    static {
        MCP_PlaneInfoManager.instance = new MCP_PlaneInfoManager();
        MCP_PlaneInfoManager.map = new LinkedHashMap<String, MCP_PlaneInfo>();
    }
}
