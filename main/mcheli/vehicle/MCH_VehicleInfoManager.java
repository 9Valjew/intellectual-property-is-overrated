package mcheli.vehicle;

import mcheli.*;
import net.minecraft.item.*;
import mcheli.aircraft.*;
import java.util.*;

public class MCH_VehicleInfoManager extends MCH_AircraftInfoManager
{
    private static MCH_VehicleInfoManager instance;
    public static HashMap<String, MCH_VehicleInfo> map;
    
    public static MCH_VehicleInfo get(final String name) {
        return MCH_VehicleInfoManager.map.get(name);
    }
    
    public static MCH_VehicleInfoManager getInstance() {
        return MCH_VehicleInfoManager.instance;
    }
    
    @Override
    public MCH_BaseInfo newInfo(final String name) {
        return new MCH_VehicleInfo(name);
    }
    
    @Override
    public Map getMap() {
        return MCH_VehicleInfoManager.map;
    }
    
    public static MCH_VehicleInfo getFromItem(final Item item) {
        return getInstance().getAcInfoFromItem(item);
    }
    
    @Override
    public MCH_VehicleInfo getAcInfoFromItem(final Item item) {
        if (item == null) {
            return null;
        }
        for (final MCH_VehicleInfo info : MCH_VehicleInfoManager.map.values()) {
            if (info.item == item) {
                return info;
            }
        }
        return null;
    }
    
    static {
        MCH_VehicleInfoManager.instance = new MCH_VehicleInfoManager();
        MCH_VehicleInfoManager.map = new LinkedHashMap<String, MCH_VehicleInfo>();
    }
}
