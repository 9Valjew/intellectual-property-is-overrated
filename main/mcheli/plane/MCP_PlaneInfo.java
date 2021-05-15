package mcheli.plane;

import mcheli.aircraft.*;
import net.minecraft.item.*;
import java.util.*;
import mcheli.*;

public class MCP_PlaneInfo extends MCH_AircraftInfo
{
    public MCP_ItemPlane item;
    public List<DrawnPart> nozzles;
    public List<Rotor> rotorList;
    public List<Wing> wingList;
    public boolean isEnableVtol;
    public boolean isDefaultVtol;
    public float vtolYaw;
    public float vtolPitch;
    public boolean isEnableAutoPilot;
    public boolean isVariableSweepWing;
    public float sweepWingSpeed;
    
    @Override
    public Item getItem() {
        return this.item;
    }
    
    public MCP_PlaneInfo(final String name) {
        super(name);
        this.item = null;
        this.nozzles = new ArrayList<DrawnPart>();
        this.rotorList = new ArrayList<Rotor>();
        this.wingList = new ArrayList<Wing>();
        this.isEnableVtol = false;
        this.vtolYaw = 0.3f;
        this.vtolPitch = 0.2f;
        this.isEnableAutoPilot = false;
        this.isVariableSweepWing = false;
        this.sweepWingSpeed = this.speed;
    }
    
    @Override
    public float getDefaultRotorSpeed() {
        return 47.94f;
    }
    
    private float getDefaultStepHeight() {
        return 0.6f;
    }
    
    public boolean haveNozzle() {
        return this.nozzles.size() > 0;
    }
    
    public boolean haveRotor() {
        return this.rotorList.size() > 0;
    }
    
    public boolean haveWing() {
        return this.wingList.size() > 0;
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
            return "plane";
        }
        if (seatId == 1) {
            return "plane";
        }
        return "gunner";
    }
    
    @Override
    public boolean isValidData() throws Exception {
        if (this.haveHatch() && this.haveWing()) {
            this.wingList.clear();
            this.hatchList.clear();
        }
        final double n = this.speed;
        final MCH_Config config = MCH_MOD.config;
        this.speed = (float)(n * MCH_Config.AllPlaneSpeed.prmDouble);
        final double n2 = this.sweepWingSpeed;
        final MCH_Config config2 = MCH_MOD.config;
        this.sweepWingSpeed = (float)(n2 * MCH_Config.AllPlaneSpeed.prmDouble);
        return super.isValidData();
    }
    
    @Override
    public void loadItemData(final String item, final String data) {
        super.loadItemData(item, data);
        if (item.compareTo("addpartrotor") == 0) {
            final String[] s = data.split("\\s*,\\s*");
            if (s.length >= 6) {
                final float m = (s.length >= 7) ? (this.toFloat(s[6], -180.0f, 180.0f) / 90.0f) : 1.0f;
                final Rotor e = new Rotor(this.toFloat(s[0]), this.toFloat(s[1]), this.toFloat(s[2]), this.toFloat(s[3]), this.toFloat(s[4]), this.toFloat(s[5]), m, "rotor" + this.rotorList.size());
                this.rotorList.add(e);
            }
        }
        else if (item.compareTo("addblade") == 0) {
            final int idx = this.rotorList.size() - 1;
            final Rotor r = (this.rotorList.size() > 0) ? this.rotorList.get(idx) : null;
            if (r != null) {
                final String[] s2 = data.split("\\s*,\\s*");
                if (s2.length == 8) {
                    final Blade b = new Blade(this.toInt(s2[0]), this.toInt(s2[1]), this.toFloat(s2[2]), this.toFloat(s2[3]), this.toFloat(s2[4]), this.toFloat(s2[5]), this.toFloat(s2[6]), this.toFloat(s2[7]), "blade" + idx);
                    r.blades.add(b);
                }
            }
        }
        else if (item.compareTo("addpartwing") == 0) {
            final String[] s = data.split("\\s*,\\s*");
            if (s.length == 7) {
                final Wing n = new Wing(this.toFloat(s[0]), this.toFloat(s[1]), this.toFloat(s[2]), this.toFloat(s[3]), this.toFloat(s[4]), this.toFloat(s[5]), this.toFloat(s[6]), "wing" + this.wingList.size());
                this.wingList.add(n);
            }
        }
        else if (item.equalsIgnoreCase("AddPartPylon")) {
            final String[] s = data.split("\\s*,\\s*");
            if (s.length >= 7 && this.wingList.size() > 0) {
                final Wing w = this.wingList.get(this.wingList.size() - 1);
                if (w.pylonList == null) {
                    w.pylonList = new ArrayList<Pylon>();
                }
                final Pylon n2 = new Pylon(this.toFloat(s[0]), this.toFloat(s[1]), this.toFloat(s[2]), this.toFloat(s[3]), this.toFloat(s[4]), this.toFloat(s[5]), this.toFloat(s[6]), w.modelName + "_pylon" + w.pylonList.size());
                w.pylonList.add(n2);
            }
        }
        else if (item.compareTo("addpartnozzle") == 0) {
            final String[] s = data.split("\\s*,\\s*");
            if (s.length == 6) {
                final DrawnPart n3 = new DrawnPart(this.toFloat(s[0]), this.toFloat(s[1]), this.toFloat(s[2]), this.toFloat(s[3]), this.toFloat(s[4]), this.toFloat(s[5]), "nozzle" + this.nozzles.size());
                this.nozzles.add(n3);
            }
        }
        else if (item.compareTo("variablesweepwing") == 0) {
            this.isVariableSweepWing = this.toBool(data);
        }
        else if (item.compareTo("sweepwingspeed") == 0) {
            this.sweepWingSpeed = this.toFloat(data, 0.0f, 5.0f);
        }
        else if (item.compareTo("enablevtol") == 0) {
            this.isEnableVtol = this.toBool(data);
        }
        else if (item.compareTo("defaultvtol") == 0) {
            this.isDefaultVtol = this.toBool(data);
        }
        else if (item.compareTo("vtolyaw") == 0) {
            this.vtolYaw = this.toFloat(data, 0.0f, 1.0f);
        }
        else if (item.compareTo("vtolpitch") == 0) {
            this.vtolPitch = this.toFloat(data, 0.01f, 1.0f);
        }
        else if (item.compareTo("enableautopilot") == 0) {
            this.isEnableAutoPilot = this.toBool(data);
        }
    }
    
    @Override
    public String getDirectoryName() {
        return "planes";
    }
    
    @Override
    public String getKindName() {
        return "plane";
    }
    
    @Override
    public void preReload() {
        super.preReload();
        this.nozzles.clear();
        this.rotorList.clear();
        this.wingList.clear();
    }
    
    @Override
    public void postReload() {
        MCH_MOD.proxy.registerModelsPlane(this.name, true);
    }
    
    public class Rotor extends DrawnPart
    {
        public List<Blade> blades;
        public final float maxRotFactor;
        
        public Rotor(final float x, final float y, final float z, final float rx, final float ry, final float rz, final float mrf, final String model) {
            super(x, y, z, rx, ry, rz, model);
            this.blades = new ArrayList<Blade>();
            this.maxRotFactor = mrf;
        }
    }
    
    public class Blade extends DrawnPart
    {
        public final int numBlade;
        public final int rotBlade;
        
        public Blade(final int num, final int r, final float px, final float py, final float pz, final float rx, final float ry, final float rz, final String name) {
            super(px, py, pz, rx, ry, rz, name);
            this.numBlade = num;
            this.rotBlade = r;
        }
    }
    
    public class Wing extends DrawnPart
    {
        public final float maxRotFactor;
        public final float maxRot;
        public List<Pylon> pylonList;
        
        public Wing(final float px, final float py, final float pz, final float rx, final float ry, final float rz, final float mr, final String name) {
            super(px, py, pz, rx, ry, rz, name);
            this.maxRot = mr;
            this.maxRotFactor = this.maxRot / 90.0f;
            this.pylonList = null;
        }
    }
    
    public class Pylon extends DrawnPart
    {
        public final float maxRotFactor;
        public final float maxRot;
        
        public Pylon(final float px, final float py, final float pz, final float rx, final float ry, final float rz, final float mr, final String name) {
            super(px, py, pz, rx, ry, rz, name);
            this.maxRot = mr;
            this.maxRotFactor = this.maxRot / 90.0f;
        }
    }
}
