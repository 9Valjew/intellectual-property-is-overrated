package mcheli.tank;

import mcheli.*;
import net.minecraft.item.*;
import mcheli.aircraft.*;
import java.util.*;

public class MCH_TankInfoManager extends MCH_AircraftInfoManager
{
    private static MCH_TankInfoManager instance;
    public static HashMap<String, MCH_TankInfo> map;
    
    public static MCH_TankInfo get(final String name) {
        return MCH_TankInfoManager.map.get(name);
    }
    
    public static MCH_TankInfoManager getInstance() {
        return MCH_TankInfoManager.instance;
    }
    
    @Override
    public MCH_BaseInfo newInfo(final String name) {
        return new MCH_TankInfo(name);
    }
    
    @Override
    public Map getMap() {
        return MCH_TankInfoManager.map;
    }
    
    public static MCH_TankInfo getFromItem(final Item item) {
        return getInstance().getAcInfoFromItem(item);
    }
    
    @Override
    public MCH_TankInfo getAcInfoFromItem(final Item item) {
        if (item == null) {
            return null;
        }
        for (final MCH_TankInfo info : MCH_TankInfoManager.map.values()) {
            if (info.item == item) {
                return info;
            }
        }
        return null;
    }
    
    static {
        MCH_TankInfoManager.instance = new MCH_TankInfoManager();
        MCH_TankInfoManager.map = new LinkedHashMap<String, MCH_TankInfo>();
    }
}
