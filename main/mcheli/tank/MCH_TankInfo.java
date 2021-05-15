package mcheli.tank;

import mcheli.aircraft.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.util.*;
import mcheli.*;

public class MCH_TankInfo extends MCH_AircraftInfo
{
    public MCH_ItemTank item;
    public int weightType;
    public float weightedCenterZ;
    
    @Override
    public Item getItem() {
        return this.item;
    }
    
    public MCH_TankInfo(final String name) {
        super(name);
        this.item = null;
        this.weightType = 0;
        this.weightedCenterZ = 0.0f;
    }
    
    @Override
    public List<Wheel> getDefaultWheelList() {
        final List<Wheel> list = new ArrayList<Wheel>();
        list.add(new Wheel(Vec3.func_72443_a(1.5, -0.24, 2.0)));
        list.add(new Wheel(Vec3.func_72443_a(1.5, -0.24, -2.0)));
        return list;
    }
    
    @Override
    public float getDefaultSoundRange() {
        return 50.0f;
    }
    
    @Override
    public float getDefaultRotorSpeed() {
        return 47.94f;
    }
    
    private float getDefaultStepHeight() {
        return 0.6f;
    }
    
    @Override
    public float getMaxSpeed() {
        return 1.8f;
    }
    
    @Override
    public int getDefaultMaxZoom() {
        return 8;
    }
    
    @Override
    public String getDefaultHudName(final int seatId) {
        if (seatId <= 0) {
            return "tank";
        }
        if (seatId == 1) {
            return "tank";
        }
        return "gunner";
    }
    
    @Override
    public boolean isValidData() throws Exception {
        final double n = this.speed;
        final MCH_Config config = MCH_MOD.config;
        this.speed = (float)(n * MCH_Config.AllTankSpeed.prmDouble);
        return super.isValidData();
    }
    
    @Override
    public void loadItemData(final String item, String data) {
        super.loadItemData(item, data);
        if (item.equalsIgnoreCase("WeightType")) {
            data = data.toLowerCase();
            this.weightType = (data.equals("tank") ? 2 : (data.equals("car") ? 1 : 0));
        }
        else if (item.equalsIgnoreCase("WeightedCenterZ")) {
            this.weightedCenterZ = this.toFloat(data, -1000.0f, 1000.0f);
        }
    }
    
    @Override
    public String getDirectoryName() {
        return "tanks";
    }
    
    @Override
    public String getKindName() {
        return "tank";
    }
    
    @Override
    public void preReload() {
        super.preReload();
    }
    
    @Override
    public void postReload() {
        MCH_MOD.proxy.registerModelsTank(this.name, true);
    }
}
