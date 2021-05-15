package mcheli.aircraft;

import net.minecraft.item.crafting.*;
import net.minecraftforge.client.model.*;
import net.minecraft.item.*;
import mcheli.hud.*;
import java.util.*;
import mcheli.*;
import mcheli.weapon.*;
import net.minecraft.util.*;

public abstract class MCH_AircraftInfo extends MCH_BaseInfo
{
    public final String name;
    public String displayName;
    public HashMap<String, String> displayNameLang;
    public int itemID;
    public List<String> recipeString;
    public List<IRecipe> recipe;
    public boolean isShapedRecipe;
    public String category;
    public boolean isEnableGunnerMode;
    public int cameraZoom;
    public boolean isEnableConcurrentGunnerMode;
    public boolean isEnableNightVision;
    public boolean isEnableEntityRadar;
    public boolean isEnableEjectionSeat;
    public boolean isEnableParachuting;
    public Flare flare;
    public float bodyHeight;
    public float bodyWidth;
    public boolean isFloat;
    public float floatOffset;
    public float gravity;
    public float gravityInWater;
    public int maxHp;
    public float armorMinDamage;
    public float armorMaxDamage;
    public float armorDamageFactor;
    public boolean enableBack;
    public int inventorySize;
    public boolean isUAV;
    public boolean isSmallUAV;
    public boolean isTargetDrone;
    public float autoPilotRot;
    public float onGroundPitch;
    public boolean canMoveOnGround;
    public boolean canRotOnGround;
    public List<WeaponSet> weaponSetList;
    public List<MCH_SeatInfo> seatList;
    public List<Integer[]> exclusionSeatList;
    public List<MCH_Hud> hudList;
    public MCH_Hud hudTvMissile;
    public float damageFactor;
    public float submergedDamageHeight;
    public boolean regeneration;
    public List<MCH_BoundingBox> extraBoundingBox;
    public List<Wheel> wheels;
    public int maxFuel;
    public float fuelConsumption;
    public float fuelSupplyRange;
    public float ammoSupplyRange;
    public float repairOtherVehiclesRange;
    public int repairOtherVehiclesValue;
    public float stealth;
    public boolean canRide;
    public float entityWidth;
    public float entityHeight;
    public float entityPitch;
    public float entityRoll;
    public float stepHeight;
    public List<MCH_SeatRackInfo> entityRackList;
    public int mobSeatNum;
    public int entityRackNum;
    public MCH_MobDropOption mobDropOption;
    public List<RepellingHook> repellingHooks;
    public List<RideRack> rideRacks;
    public List<ParticleSplash> particleSplashs;
    public List<SearchLight> searchLights;
    public float rotorSpeed;
    public boolean enableSeaSurfaceParticle;
    public float pivotTurnThrottle;
    public float trackRollerRot;
    public float partWheelRot;
    public float onGroundPitchFactor;
    public float onGroundRollFactor;
    public Vec3 turretPosition;
    public boolean defaultFreelook;
    public Vec3 unmountPosition;
    public float markerWidth;
    public float markerHeight;
    public float bbZmin;
    public float bbZmax;
    public float bbZ;
    public boolean alwaysCameraView;
    public List<CameraPosition> cameraPosition;
    public float cameraRotationSpeed;
    public float speed;
    public float motionFactor;
    public float mobilityYaw;
    public float mobilityPitch;
    public float mobilityRoll;
    public float mobilityYawOnGround;
    public float minRotationPitch;
    public float maxRotationPitch;
    public float minRotationRoll;
    public float maxRotationRoll;
    public boolean limitRotation;
    public float throttleUpDown;
    public float throttleUpDownOnEntity;
    private List<String> textureNameList;
    public int textureCount;
    public float particlesScale;
    public boolean hideEntity;
    public boolean smoothShading;
    public String soundMove;
    public float soundRange;
    public float soundVolume;
    public float soundPitch;
    public IModelCustom model;
    public List<Hatch> hatchList;
    public List<Camera> cameraList;
    public List<PartWeapon> partWeapon;
    public List<WeaponBay> partWeaponBay;
    public List<Canopy> canopyList;
    public List<LandingGear> landingGear;
    public List<Throttle> partThrottle;
    public List<RotPart> partRotPart;
    public List<CrawlerTrack> partCrawlerTrack;
    public List<TrackRoller> partTrackRoller;
    public List<PartWheel> partWheel;
    public List<PartWheel> partSteeringWheel;
    public List<Hatch> lightHatchList;
    private String lastWeaponType;
    private int lastWeaponIndex;
    private PartWeapon lastWeaponPart;
    
    public abstract Item getItem();
    
    public ItemStack getItemStack() {
        return new ItemStack(this.getItem());
    }
    
    public abstract String getDirectoryName();
    
    public abstract String getKindName();
    
    public MCH_AircraftInfo(final String s) {
        this.lastWeaponType = "";
        this.lastWeaponIndex = -1;
        this.name = s;
        this.displayName = this.name;
        this.displayNameLang = new HashMap<String, String>();
        this.itemID = 0;
        this.recipeString = new ArrayList<String>();
        this.recipe = new ArrayList<IRecipe>();
        this.isShapedRecipe = true;
        this.category = "zzz";
        this.isEnableGunnerMode = false;
        this.isEnableConcurrentGunnerMode = false;
        this.isEnableNightVision = false;
        this.isEnableEntityRadar = false;
        this.isEnableEjectionSeat = false;
        this.isEnableParachuting = false;
        this.flare = new Flare();
        this.weaponSetList = new ArrayList<WeaponSet>();
        this.seatList = new ArrayList<MCH_SeatInfo>();
        this.exclusionSeatList = new ArrayList<Integer[]>();
        this.hudList = new ArrayList<MCH_Hud>();
        this.hudTvMissile = null;
        this.bodyHeight = 0.7f;
        this.bodyWidth = 2.0f;
        this.isFloat = false;
        this.floatOffset = 0.0f;
        this.gravity = -0.04f;
        this.gravityInWater = -0.04f;
        this.maxHp = 50;
        this.damageFactor = 0.2f;
        this.submergedDamageHeight = 0.0f;
        this.inventorySize = 0;
        this.armorDamageFactor = 1.0f;
        this.armorMaxDamage = 100000.0f;
        this.armorMinDamage = 0.0f;
        this.enableBack = false;
        this.isUAV = false;
        this.isSmallUAV = false;
        this.isTargetDrone = false;
        this.autoPilotRot = -0.6f;
        this.regeneration = false;
        this.onGroundPitch = 0.0f;
        this.canMoveOnGround = true;
        this.canRotOnGround = true;
        this.cameraZoom = this.getDefaultMaxZoom();
        this.extraBoundingBox = new ArrayList<MCH_BoundingBox>();
        this.maxFuel = 0;
        this.fuelConsumption = 1.0f;
        this.fuelSupplyRange = 0.0f;
        this.ammoSupplyRange = 0.0f;
        this.repairOtherVehiclesRange = 0.0f;
        this.repairOtherVehiclesValue = 10;
        this.stealth = 0.0f;
        this.canRide = true;
        this.entityWidth = 1.0f;
        this.entityHeight = 1.0f;
        this.entityPitch = 0.0f;
        this.entityRoll = 0.0f;
        this.stepHeight = this.getDefaultStepHeight();
        this.entityRackList = new ArrayList<MCH_SeatRackInfo>();
        this.mobSeatNum = 0;
        this.entityRackNum = 0;
        this.mobDropOption = new MCH_MobDropOption();
        this.repellingHooks = new ArrayList<RepellingHook>();
        this.rideRacks = new ArrayList<RideRack>();
        this.particleSplashs = new ArrayList<ParticleSplash>();
        this.searchLights = new ArrayList<SearchLight>();
        this.markerHeight = 1.0f;
        this.markerWidth = 2.0f;
        this.bbZmax = 1.0f;
        this.bbZmin = -1.0f;
        this.rotorSpeed = this.getDefaultRotorSpeed();
        this.wheels = this.getDefaultWheelList();
        this.onGroundPitchFactor = 0.0f;
        this.onGroundRollFactor = 0.0f;
        this.turretPosition = Vec3.func_72443_a(0.0, 0.0, 0.0);
        this.defaultFreelook = false;
        this.unmountPosition = null;
        this.cameraPosition = new ArrayList<CameraPosition>();
        this.alwaysCameraView = false;
        this.cameraRotationSpeed = 1000.0f;
        this.speed = 0.1f;
        this.motionFactor = 0.96f;
        this.mobilityYaw = 1.0f;
        this.mobilityPitch = 1.0f;
        this.mobilityRoll = 1.0f;
        this.mobilityYawOnGround = 1.0f;
        this.minRotationPitch = this.getMinRotationPitch();
        this.maxRotationPitch = this.getMaxRotationPitch();
        this.minRotationRoll = this.getMinRotationPitch();
        this.maxRotationRoll = this.getMaxRotationPitch();
        this.limitRotation = false;
        this.throttleUpDown = 1.0f;
        this.throttleUpDownOnEntity = 2.0f;
        this.pivotTurnThrottle = 0.0f;
        this.trackRollerRot = 30.0f;
        this.partWheelRot = 30.0f;
        (this.textureNameList = new ArrayList<String>()).add(this.name);
        this.textureCount = 0;
        this.particlesScale = 1.0f;
        this.enableSeaSurfaceParticle = false;
        this.hideEntity = false;
        this.smoothShading = true;
        this.soundMove = "";
        this.soundPitch = 1.0f;
        this.soundVolume = 1.0f;
        this.soundRange = this.getDefaultSoundRange();
        this.model = null;
        this.hatchList = new ArrayList<Hatch>();
        this.cameraList = new ArrayList<Camera>();
        this.partWeapon = new ArrayList<PartWeapon>();
        this.lastWeaponPart = null;
        this.partWeaponBay = new ArrayList<WeaponBay>();
        this.canopyList = new ArrayList<Canopy>();
        this.landingGear = new ArrayList<LandingGear>();
        this.partThrottle = new ArrayList<Throttle>();
        this.partRotPart = new ArrayList<RotPart>();
        this.partCrawlerTrack = new ArrayList<CrawlerTrack>();
        this.partTrackRoller = new ArrayList<TrackRoller>();
        this.partWheel = new ArrayList<PartWheel>();
        this.partSteeringWheel = new ArrayList<PartWheel>();
        this.lightHatchList = new ArrayList<Hatch>();
    }
    
    public float getDefaultSoundRange() {
        return 100.0f;
    }
    
    public List<Wheel> getDefaultWheelList() {
        return new ArrayList<Wheel>();
    }
    
    public float getDefaultRotorSpeed() {
        return 0.0f;
    }
    
    private float getDefaultStepHeight() {
        return 0.0f;
    }
    
    public boolean haveRepellingHook() {
        return this.repellingHooks.size() > 0;
    }
    
    public boolean haveFlare() {
        return this.flare.types.length > 0;
    }
    
    public boolean haveCanopy() {
        return this.canopyList.size() > 0;
    }
    
    public boolean haveLandingGear() {
        return this.landingGear.size() > 0;
    }
    
    public abstract String getDefaultHudName(final int p0);
    
    @Override
    public boolean isValidData() throws Exception {
        if (this.cameraPosition.size() <= 0) {
            this.cameraPosition.add(new CameraPosition());
        }
        this.bbZ = (this.bbZmax + this.bbZmin) / 2.0f;
        if (this.isTargetDrone) {
            this.isUAV = true;
        }
        if (this.isEnableParachuting && this.repellingHooks.size() > 0) {
            this.isEnableParachuting = false;
            this.repellingHooks.clear();
        }
        if (this.isUAV) {
            this.alwaysCameraView = true;
            if (this.seatList.size() == 0) {
                final MCH_SeatInfo s = new MCH_SeatInfo(Vec3.func_72443_a(0.0, 0.0, 0.0), false);
                this.seatList.add(s);
            }
        }
        this.mobSeatNum = this.seatList.size();
        this.entityRackNum = this.entityRackList.size();
        if (this.getNumSeat() < 1) {
            throw new Exception();
        }
        if (this.getNumHud() < this.getNumSeat()) {
            for (int i = this.getNumHud(); i < this.getNumSeat(); ++i) {
                this.hudList.add(MCH_HudManager.get(this.getDefaultHudName(i)));
            }
        }
        if (this.getNumSeat() == 1 && this.getNumHud() == 1) {
            this.hudList.add(MCH_HudManager.get(this.getDefaultHudName(1)));
        }
        for (final MCH_SeatRackInfo ei : this.entityRackList) {
            this.seatList.add(ei);
        }
        this.entityRackList.clear();
        if (this.hudTvMissile == null) {
            this.hudTvMissile = MCH_HudManager.get("tv_missile");
        }
        if (this.textureNameList.size() < 1) {
            throw new Exception();
        }
        if (this.itemID <= 0) {}
        for (int i = 0; i < this.partWeaponBay.size(); ++i) {
            final WeaponBay wb = this.partWeaponBay.get(i);
            final String[] weaponNames = wb.weaponName.split("\\s*/\\s*");
            if (weaponNames.length <= 0) {
                this.partWeaponBay.remove(i);
            }
            else {
                final List<Integer> list = new ArrayList<Integer>();
                for (final String s2 : weaponNames) {
                    final int id = this.getWeaponIdByName(s2);
                    if (id >= 0) {
                        list.add(id);
                    }
                }
                if (list.size() <= 0) {
                    this.partWeaponBay.remove(i);
                }
                else {
                    this.partWeaponBay.get(i).weaponIds = list.toArray(new Integer[0]);
                }
            }
        }
        return true;
    }
    
    public int getInfo_MaxSeatNum() {
        return 30;
    }
    
    public int getNumSeatAndRack() {
        return this.seatList.size();
    }
    
    public int getNumSeat() {
        return this.mobSeatNum;
    }
    
    public int getNumRack() {
        return this.entityRackNum;
    }
    
    public int getNumHud() {
        return this.hudList.size();
    }
    
    public float getMaxSpeed() {
        return 0.8f;
    }
    
    public float getMinRotationPitch() {
        return -89.9f;
    }
    
    public float getMaxRotationPitch() {
        return 80.0f;
    }
    
    public float getMinRotationRoll() {
        return -80.0f;
    }
    
    public float getMaxRotationRoll() {
        return 80.0f;
    }
    
    public int getDefaultMaxZoom() {
        return 1;
    }
    
    public boolean haveHatch() {
        return this.hatchList.size() > 0;
    }
    
    public boolean havePartCamera() {
        return this.cameraList.size() > 0;
    }
    
    public boolean havePartThrottle() {
        return this.partThrottle.size() > 0;
    }
    
    public WeaponSet getWeaponSetById(final int id) {
        return (id >= 0 && id < this.weaponSetList.size()) ? this.weaponSetList.get(id) : null;
    }
    
    public Weapon getWeaponById(final int id) {
        final WeaponSet ws = this.getWeaponSetById(id);
        return (ws != null) ? ws.weapons.get(0) : null;
    }
    
    public int getWeaponIdByName(final String s) {
        for (int i = 0; i < this.weaponSetList.size(); ++i) {
            if (this.weaponSetList.get(i).type.equalsIgnoreCase(s)) {
                return i;
            }
        }
        return -1;
    }
    
    public Weapon getWeaponByName(final String s) {
        for (int i = 0; i < this.weaponSetList.size(); ++i) {
            if (this.weaponSetList.get(i).type.equalsIgnoreCase(s)) {
                return this.getWeaponById(i);
            }
        }
        return null;
    }
    
    public int getWeaponNum() {
        return this.weaponSetList.size();
    }
    
    @Override
    public void loadItemData(final String item, final String data) {
        if (item.compareTo("displayname") == 0) {
            this.displayName = data.trim();
        }
        else if (item.compareTo("adddisplayname") == 0) {
            final String[] s = data.split("\\s*,\\s*");
            if (s != null && s.length == 2) {
                this.displayNameLang.put(s[0].trim(), s[1].trim());
            }
        }
        else if (item.equalsIgnoreCase("Category")) {
            this.category = data.toUpperCase().replaceAll("[,;:]", ".").replaceAll("[ \t]", "");
        }
        else if (item.equalsIgnoreCase("CanRide")) {
            this.canRide = this.toBool(data, true);
        }
        else if (item.equalsIgnoreCase("MaxFuel")) {
            this.maxFuel = this.toInt(data, 0, 100000000);
        }
        else if (item.equalsIgnoreCase("FuelConsumption")) {
            this.fuelConsumption = this.toFloat(data, 0.0f, 10000.0f);
        }
        else if (item.equalsIgnoreCase("FuelSupplyRange")) {
            this.fuelSupplyRange = this.toFloat(data, 0.0f, 1000.0f);
        }
        else if (item.equalsIgnoreCase("AmmoSupplyRange")) {
            this.ammoSupplyRange = this.toFloat(data, 0.0f, 1000.0f);
        }
        else if (item.equalsIgnoreCase("RepairOtherVehicles")) {
            final String[] s = this.splitParam(data);
            if (s.length >= 1) {
                this.repairOtherVehiclesRange = this.toFloat(s[0], 0.0f, 1000.0f);
                if (s.length >= 2) {
                    this.repairOtherVehiclesValue = this.toInt(s[1], 0, 10000000);
                }
            }
        }
        else if (item.compareTo("itemid") == 0) {
            this.itemID = this.toInt(data, 0, 65535);
        }
        else if (item.compareTo("addtexture") == 0) {
            this.textureNameList.add(data.toLowerCase());
        }
        else if (item.compareTo("particlesscale") == 0) {
            this.particlesScale = this.toFloat(data, 0.0f, 50.0f);
        }
        else if (item.equalsIgnoreCase("EnableSeaSurfaceParticle")) {
            this.enableSeaSurfaceParticle = this.toBool(data);
        }
        else if (item.equalsIgnoreCase("AddParticleSplash")) {
            final String[] s = this.splitParam(data);
            if (s.length >= 3) {
                final Vec3 v = this.toVec3(s[0], s[1], s[2]);
                final int num = (s.length >= 4) ? this.toInt(s[3], 1, 100) : 2;
                final float size = (s.length >= 5) ? this.toFloat(s[4]) : 2.0f;
                final float acc = (s.length >= 6) ? this.toFloat(s[5]) : 1.0f;
                final int age = (s.length >= 7) ? this.toInt(s[6], 1, 100000) : 80;
                final float motionY = (s.length >= 8) ? this.toFloat(s[7]) : 0.01f;
                final float gravity = (s.length >= 9) ? this.toFloat(s[8]) : 0.0f;
                this.particleSplashs.add(new ParticleSplash(v, num, size, acc, age, motionY, gravity));
            }
        }
        else if (item.equalsIgnoreCase("AddSearchLight") || item.equalsIgnoreCase("AddFixedSearchLight") || item.equalsIgnoreCase("AddSteeringSearchLight")) {
            final String[] s = this.splitParam(data);
            if (s.length >= 7) {
                final Vec3 v = this.toVec3(s[0], s[1], s[2]);
                final int cs = this.hex2dec(s[3]);
                final int ce = this.hex2dec(s[4]);
                final float h = this.toFloat(s[5]);
                final float w = this.toFloat(s[6]);
                final float yaw = (s.length >= 8) ? this.toFloat(s[7]) : 0.0f;
                final float pitch = (s.length >= 9) ? this.toFloat(s[8]) : 0.0f;
                final float stRot = (s.length >= 10) ? this.toFloat(s[9]) : 0.0f;
                final boolean fixDir = !item.equalsIgnoreCase("AddSearchLight");
                final boolean steering = item.equalsIgnoreCase("AddSteeringSearchLight");
                this.searchLights.add(new SearchLight(v, cs, ce, h, w, fixDir, yaw, pitch, steering, stRot));
            }
        }
        else if (item.equalsIgnoreCase("AddPartLightHatch")) {
            final String[] s = this.splitParam(data);
            if (s.length >= 6) {
                final float mx = (s.length >= 7) ? this.toFloat(s[6], -1800.0f, 1800.0f) : 90.0f;
                this.lightHatchList.add(new Hatch(this.toFloat(s[0]), this.toFloat(s[1]), this.toFloat(s[2]), this.toFloat(s[3]), this.toFloat(s[4]), this.toFloat(s[5]), mx, "light_hatch" + this.lightHatchList.size(), false));
            }
        }
        else if (item.equalsIgnoreCase("AddRepellingHook")) {
            final String[] s = this.splitParam(data);
            if (s != null && s.length >= 3) {
                final int inv = (s.length >= 4) ? this.toInt(s[3], 1, 100000) : 10;
                this.repellingHooks.add(new RepellingHook(this.toVec3(s[0], s[1], s[2]), inv));
            }
        }
        else if (item.equalsIgnoreCase("AddRack")) {
            final String[] s = data.toLowerCase().split("\\s*,\\s*");
            if (s != null && s.length >= 7) {
                final String[] names = s[0].split("\\s*/\\s*");
                final float range = (s.length >= 8) ? this.toFloat(s[7]) : 6.0f;
                final float para = (s.length >= 9) ? this.toFloat(s[8], 0.0f, 1000000.0f) : 20.0f;
                final float yaw2 = (s.length >= 10) ? this.toFloat(s[9]) : 0.0f;
                final float pitch2 = (s.length >= 11) ? this.toFloat(s[10]) : 0.0f;
                final boolean rs = s.length >= 12 && this.toBool(s[11]);
                this.entityRackList.add(new MCH_SeatRackInfo(names, this.toDouble(s[1]), this.toDouble(s[2]), this.toDouble(s[3]), new CameraPosition(this.toVec3(s[4], s[5], s[6]).func_72441_c(0.0, 1.5, 0.0)), range, para, yaw2, pitch2, rs));
            }
        }
        else if (item.equalsIgnoreCase("RideRack")) {
            final String[] s = this.splitParam(data);
            if (s.length >= 2) {
                final RideRack r = new RideRack(s[0].trim().toLowerCase(), this.toInt(s[1], 1, 10000));
                this.rideRacks.add(r);
            }
        }
        else if (item.equalsIgnoreCase("AddSeat") || item.equalsIgnoreCase("AddGunnerSeat") || item.equalsIgnoreCase("AddFixRotSeat")) {
            if (this.seatList.size() >= this.getInfo_MaxSeatNum()) {
                return;
            }
            final String[] s = this.splitParam(data);
            if (s.length < 3) {
                return;
            }
            final Vec3 p = this.toVec3(s[0], s[1], s[2]);
            if (item.equalsIgnoreCase("AddSeat")) {
                final boolean rs2 = s.length >= 4 && this.toBool(s[3]);
                final MCH_SeatInfo seat = new MCH_SeatInfo(p, rs2);
                this.seatList.add(seat);
            }
            else {
                MCH_SeatInfo seat;
                if (s.length >= 6) {
                    final CameraPosition c = new CameraPosition(this.toVec3(s[3], s[4], s[5]));
                    final boolean sg = s.length >= 7 && this.toBool(s[6]);
                    if (item.equalsIgnoreCase("AddGunnerSeat")) {
                        if (s.length >= 9) {
                            float minPitch = this.toFloat(s[7], -90.0f, 90.0f);
                            float maxPitch = this.toFloat(s[8], -90.0f, 90.0f);
                            if (minPitch > maxPitch) {
                                final float t = minPitch;
                                minPitch = maxPitch;
                                maxPitch = t;
                            }
                            final boolean rs3 = s.length >= 10 && this.toBool(s[9]);
                            seat = new MCH_SeatInfo(p, true, c, true, sg, false, 0.0f, 0.0f, minPitch, maxPitch, rs3);
                        }
                        else {
                            seat = new MCH_SeatInfo(p, true, c, true, sg, false, 0.0f, 0.0f, false);
                        }
                    }
                    else {
                        final boolean fixRot = s.length >= 9;
                        final float fixYaw = fixRot ? this.toFloat(s[7]) : 0.0f;
                        final float fixPitch = fixRot ? this.toFloat(s[8]) : 0.0f;
                        final boolean rs4 = s.length >= 10 && this.toBool(s[9]);
                        seat = new MCH_SeatInfo(p, true, c, true, sg, fixRot, fixYaw, fixPitch, rs4);
                    }
                }
                else {
                    seat = new MCH_SeatInfo(p, true, new CameraPosition(), false, false, false, 0.0f, 0.0f, false);
                }
                this.seatList.add(seat);
            }
        }
        else if (item.equalsIgnoreCase("SetWheelPos")) {
            final String[] s = this.splitParam(data);
            if (s.length >= 4) {
                final float x = Math.abs(this.toFloat(s[0]));
                final float y = this.toFloat(s[1]);
                this.wheels.clear();
                for (int i = 2; i < s.length; ++i) {
                    this.wheels.add(new Wheel(Vec3.func_72443_a((double)x, (double)y, (double)this.toFloat(s[i]))));
                }
                Collections.sort(this.wheels, new Comparator<Wheel>() {
                    @Override
                    public int compare(final Wheel arg0, final Wheel arg1) {
                        return (arg0.pos.field_72449_c > arg1.pos.field_72449_c) ? -1 : 1;
                    }
                });
            }
        }
        else if (item.equalsIgnoreCase("ExclusionSeat")) {
            final String[] s = this.splitParam(data);
            if (s.length >= 2) {
                final Integer[] a = new Integer[s.length];
                for (int j = 0; j < a.length; ++j) {
                    a[j] = this.toInt(s[j], 1, 10000) - 1;
                }
                this.exclusionSeatList.add(a);
            }
        }
        else if (MCH_MOD.proxy.isRemote() && item.equalsIgnoreCase("HUD")) {
            this.hudList.clear();
            final String[] arr$;
            final String[] ss = arr$ = data.split("\\s*,\\s*");
            for (final String s2 : arr$) {
                MCH_Hud hud = MCH_HudManager.get(s2);
                if (hud == null) {
                    hud = MCH_Hud.NoDisp;
                }
                this.hudList.add(hud);
            }
        }
        else if (item.compareTo("enablenightvision") == 0) {
            this.isEnableNightVision = this.toBool(data);
        }
        else if (item.compareTo("enableentityradar") == 0) {
            this.isEnableEntityRadar = this.toBool(data);
        }
        else if (item.equalsIgnoreCase("EnableEjectionSeat")) {
            this.isEnableEjectionSeat = this.toBool(data);
        }
        else if (item.equalsIgnoreCase("EnableParachuting")) {
            this.isEnableParachuting = this.toBool(data);
        }
        else if (item.equalsIgnoreCase("MobDropOption")) {
            final String[] s = this.splitParam(data);
            if (s.length >= 3) {
                this.mobDropOption.pos = this.toVec3(s[0], s[1], s[2]);
                this.mobDropOption.interval = ((s.length >= 4) ? this.toInt(s[3]) : 12);
            }
        }
        else if (item.equalsIgnoreCase("Width")) {
            this.bodyWidth = this.toFloat(data, 0.1f, 1000.0f);
        }
        else if (item.equalsIgnoreCase("Height")) {
            this.bodyHeight = this.toFloat(data, 0.1f, 1000.0f);
        }
        else if (item.compareTo("float") == 0) {
            this.isFloat = this.toBool(data);
        }
        else if (item.compareTo("floatoffset") == 0) {
            this.floatOffset = -this.toFloat(data);
        }
        else if (item.compareTo("gravity") == 0) {
            this.gravity = this.toFloat(data, -50.0f, 50.0f);
        }
        else if (item.compareTo("gravityinwater") == 0) {
            this.gravityInWater = this.toFloat(data, -50.0f, 50.0f);
        }
        else if (item.compareTo("cameraposition") == 0) {
            final String[] s = data.split("\\s*,\\s*");
            if (s.length >= 3) {
                this.alwaysCameraView = (s.length >= 4 && this.toBool(s[3]));
                final boolean fixRot2 = s.length >= 5;
                final float yaw3 = (s.length >= 5) ? this.toFloat(s[4]) : 0.0f;
                final float pitch3 = (s.length >= 6) ? this.toFloat(s[5]) : 0.0f;
                this.cameraPosition.add(new CameraPosition(this.toVec3(s[0], s[1], s[2]), fixRot2, yaw3, pitch3));
            }
        }
        else if (item.equalsIgnoreCase("UnmountPosition")) {
            final String[] s = data.split("\\s*,\\s*");
            if (s.length >= 3) {
                this.unmountPosition = this.toVec3(s[0], s[1], s[2]);
            }
        }
        else if (item.equalsIgnoreCase("TurretPosition")) {
            final String[] s = data.split("\\s*,\\s*");
            if (s.length >= 3) {
                this.turretPosition = this.toVec3(s[0], s[1], s[2]);
            }
        }
        else if (item.equalsIgnoreCase("CameraRotationSpeed")) {
            this.cameraRotationSpeed = this.toFloat(data, 0.0f, 10000.0f);
        }
        else if (item.compareTo("regeneration") == 0) {
            this.regeneration = this.toBool(data);
        }
        else if (item.compareTo("speed") == 0) {
            this.speed = this.toFloat(data, 0.0f, this.getMaxSpeed());
        }
        else if (item.equalsIgnoreCase("EnableBack")) {
            this.enableBack = this.toBool(data);
        }
        else if (item.equalsIgnoreCase("MotionFactor")) {
            this.motionFactor = this.toFloat(data, 0.0f, 1.0f);
        }
        else if (item.equalsIgnoreCase("MobilityYawOnGround")) {
            this.mobilityYawOnGround = this.toFloat(data, 0.0f, 100.0f);
        }
        else if (item.equalsIgnoreCase("MobilityYaw")) {
            this.mobilityYaw = this.toFloat(data, 0.0f, 100.0f);
        }
        else if (item.equalsIgnoreCase("MobilityPitch")) {
            this.mobilityPitch = this.toFloat(data, 0.0f, 100.0f);
        }
        else if (item.equalsIgnoreCase("MobilityRoll")) {
            this.mobilityRoll = this.toFloat(data, 0.0f, 100.0f);
        }
        else if (item.equalsIgnoreCase("MinRotationPitch")) {
            this.limitRotation = true;
            this.minRotationPitch = this.toFloat(data, this.getMinRotationPitch(), 0.0f);
        }
        else if (item.equalsIgnoreCase("MaxRotationPitch")) {
            this.limitRotation = true;
            this.maxRotationPitch = this.toFloat(data, 0.0f, this.getMaxRotationPitch());
        }
        else if (item.equalsIgnoreCase("MinRotationRoll")) {
            this.limitRotation = true;
            this.minRotationRoll = this.toFloat(data, this.getMinRotationRoll(), 0.0f);
        }
        else if (item.equalsIgnoreCase("MaxRotationRoll")) {
            this.limitRotation = true;
            this.maxRotationRoll = this.toFloat(data, 0.0f, this.getMaxRotationRoll());
        }
        else if (item.compareTo("throttleupdown") == 0) {
            this.throttleUpDown = this.toFloat(data, 0.0f, 3.0f);
        }
        else if (item.equalsIgnoreCase("ThrottleUpDownOnEntity")) {
            this.throttleUpDownOnEntity = this.toFloat(data, 0.0f, 100000.0f);
        }
        else if (item.equalsIgnoreCase("Stealth")) {
            this.stealth = this.toFloat(data, 0.0f, 1.0f);
        }
        else if (item.equalsIgnoreCase("EntityWidth")) {
            this.entityWidth = this.toFloat(data, -100.0f, 100.0f);
        }
        else if (item.equalsIgnoreCase("EntityHeight")) {
            this.entityHeight = this.toFloat(data, -100.0f, 100.0f);
        }
        else if (item.equalsIgnoreCase("EntityPitch")) {
            this.entityPitch = this.toFloat(data, -360.0f, 360.0f);
        }
        else if (item.equalsIgnoreCase("EntityRoll")) {
            this.entityRoll = this.toFloat(data, -360.0f, 360.0f);
        }
        else if (item.equalsIgnoreCase("StepHeight")) {
            this.stepHeight = this.toFloat(data, 0.0f, 1000.0f);
        }
        else if (item.equalsIgnoreCase("CanMoveOnGround")) {
            this.canMoveOnGround = this.toBool(data);
        }
        else if (item.equalsIgnoreCase("CanRotOnGround")) {
            this.canRotOnGround = this.toBool(data);
        }
        else if (item.equalsIgnoreCase("AddWeapon") || item.equalsIgnoreCase("AddTurretWeapon")) {
            final String[] s = data.split("\\s*,\\s*");
            final String type = s[0].toLowerCase();
            if (s.length >= 4 && MCH_WeaponInfoManager.contains(type)) {
                final float y = (s.length >= 5) ? this.toFloat(s[4]) : 0.0f;
                final float p2 = (s.length >= 6) ? this.toFloat(s[5]) : 0.0f;
                boolean canUsePilot = s.length < 7 || this.toBool(s[6]);
                final int seatID = (s.length >= 8) ? (this.toInt(s[7], 1, this.getInfo_MaxSeatNum()) - 1) : 0;
                if (seatID <= 0) {
                    canUsePilot = true;
                }
                float dfy = (s.length >= 9) ? this.toFloat(s[8]) : 0.0f;
                dfy = MathHelper.func_76142_g(dfy);
                final float mny = (s.length >= 10) ? this.toFloat(s[9]) : 0.0f;
                final float mxy = (s.length >= 11) ? this.toFloat(s[10]) : 0.0f;
                final float mnp = (s.length >= 12) ? this.toFloat(s[11]) : 0.0f;
                final float mxp = (s.length >= 13) ? this.toFloat(s[12]) : 0.0f;
                final Weapon e = new Weapon(this.toFloat(s[1]), this.toFloat(s[2]), this.toFloat(s[3]), y, p2, canUsePilot, seatID, dfy, mny, mxy, mnp, mxp, item.equalsIgnoreCase("AddTurretWeapon"));
                if (type.compareTo(this.lastWeaponType) != 0) {
                    this.weaponSetList.add(new WeaponSet(type));
                    ++this.lastWeaponIndex;
                    this.lastWeaponType = type;
                }
                this.weaponSetList.get(this.lastWeaponIndex).weapons.add(e);
            }
        }
        else if (item.equalsIgnoreCase("AddPartWeapon") || item.equalsIgnoreCase("AddPartRotWeapon") || item.equalsIgnoreCase("AddPartTurretWeapon") || item.equalsIgnoreCase("AddPartTurretRotWeapon") || item.equalsIgnoreCase("AddPartWeaponMissile")) {
            final String[] s = data.split("\\s*,\\s*");
            if (s.length >= 7) {
                float rx = 0.0f;
                float ry = 0.0f;
                float rz = 0.0f;
                float rb = 0.0f;
                final boolean isRot = item.equalsIgnoreCase("AddPartRotWeapon") || item.equalsIgnoreCase("AddPartTurretRotWeapon");
                final boolean isMissile = item.equalsIgnoreCase("AddPartWeaponMissile");
                final boolean turret = item.equalsIgnoreCase("AddPartTurretWeapon") || item.equalsIgnoreCase("AddPartTurretRotWeapon");
                if (isRot) {
                    rx = ((s.length >= 10) ? this.toFloat(s[7]) : 0.0f);
                    ry = ((s.length >= 10) ? this.toFloat(s[8]) : 0.0f);
                    rz = ((s.length >= 10) ? this.toFloat(s[9]) : -1.0f);
                }
                else {
                    rb = ((s.length >= 8) ? this.toFloat(s[7]) : 0.0f);
                }
                final PartWeapon w2 = new PartWeapon(this.splitParamSlash(s[0].toLowerCase().trim()), isRot, isMissile, this.toBool(s[1]), this.toBool(s[2]), this.toBool(s[3]), this.toFloat(s[4]), this.toFloat(s[5]), this.toFloat(s[6]), "weapon" + this.partWeapon.size(), rx, ry, rz, rb, turret);
                this.lastWeaponPart = w2;
                this.partWeapon.add(w2);
            }
        }
        else if (item.equalsIgnoreCase("AddPartWeaponChild")) {
            final String[] s = data.split("\\s*,\\s*");
            if (s.length >= 5 && this.lastWeaponPart != null) {
                final float rb2 = (s.length >= 6) ? this.toFloat(s[5]) : 0.0f;
                final PartWeaponChild w3 = new PartWeaponChild(this.lastWeaponPart.name, this.toBool(s[0]), this.toBool(s[1]), this.toFloat(s[2]), this.toFloat(s[3]), this.toFloat(s[4]), this.lastWeaponPart.modelName + "_" + this.lastWeaponPart.child.size(), 0.0f, 0.0f, 0.0f, rb2);
                this.lastWeaponPart.child.add(w3);
            }
        }
        else if (item.compareTo("addrecipe") == 0 || item.compareTo("addshapelessrecipe") == 0) {
            this.isShapedRecipe = (item.compareTo("addrecipe") == 0);
            this.recipeString.add(data.toUpperCase());
        }
        else if (item.compareTo("maxhp") == 0) {
            this.maxHp = this.toInt(data, 1, 100000);
        }
        else if (item.compareTo("inventorysize") == 0) {
            this.inventorySize = this.toInt(data, 0, 54);
        }
        else if (item.compareTo("damagefactor") == 0) {
            this.damageFactor = this.toFloat(data, 0.0f, 1.0f);
        }
        else if (item.equalsIgnoreCase("SubmergedDamageHeight")) {
            this.submergedDamageHeight = this.toFloat(data, -1000.0f, 1000.0f);
        }
        else if (item.equalsIgnoreCase("ArmorDamageFactor")) {
            this.armorDamageFactor = this.toFloat(data, 0.0f, 10000.0f);
        }
        else if (item.equalsIgnoreCase("ArmorMinDamage")) {
            this.armorMinDamage = this.toFloat(data, 0.0f, 1000000.0f);
        }
        else if (item.equalsIgnoreCase("ArmorMaxDamage")) {
            this.armorMaxDamage = this.toFloat(data, 0.0f, 1000000.0f);
        }
        else if (item.equalsIgnoreCase("FlareType")) {
            final String[] s = data.split("\\s*,\\s*");
            this.flare.types = new int[s.length];
            for (int k = 0; k < s.length; ++k) {
                this.flare.types[k] = this.toInt(s[k], 1, 10);
            }
        }
        else if (item.equalsIgnoreCase("FlareOption")) {
            final String[] s = this.splitParam(data);
            if (s.length >= 3) {
                this.flare.pos = this.toVec3(s[0], s[1], s[2]);
            }
        }
        else if (item.equalsIgnoreCase("Sound")) {
            this.soundMove = data.toLowerCase();
        }
        else if (item.equalsIgnoreCase("SoundRange")) {
            this.soundRange = this.toFloat(data, 1.0f, 1000.0f);
        }
        else if (item.equalsIgnoreCase("SoundVolume")) {
            this.soundVolume = this.toFloat(data, 0.0f, 10.0f);
        }
        else if (item.equalsIgnoreCase("SoundPitch")) {
            this.soundPitch = this.toFloat(data, 0.0f, 10.0f);
        }
        else if (item.equalsIgnoreCase("UAV")) {
            this.isUAV = this.toBool(data);
            this.isSmallUAV = false;
        }
        else if (item.equalsIgnoreCase("SmallUAV")) {
            this.isUAV = this.toBool(data);
            this.isSmallUAV = true;
        }
        else if (item.equalsIgnoreCase("TargetDrone")) {
            this.isTargetDrone = this.toBool(data);
        }
        else if (item.compareTo("autopilotrot") == 0) {
            this.autoPilotRot = this.toFloat(data, -5.0f, 5.0f);
        }
        else if (item.compareTo("ongroundpitch") == 0) {
            this.onGroundPitch = -this.toFloat(data, -90.0f, 90.0f);
        }
        else if (item.compareTo("enablegunnermode") == 0) {
            this.isEnableGunnerMode = this.toBool(data);
        }
        else if (item.compareTo("hideentity") == 0) {
            this.hideEntity = this.toBool(data);
        }
        else if (item.equalsIgnoreCase("SmoothShading")) {
            this.smoothShading = this.toBool(data);
        }
        else if (item.compareTo("concurrentgunnermode") == 0) {
            this.isEnableConcurrentGunnerMode = this.toBool(data);
        }
        else if (item.equalsIgnoreCase("AddPartWeaponBay") || item.equalsIgnoreCase("AddPartSlideWeaponBay")) {
            final boolean slide = item.equalsIgnoreCase("AddPartSlideWeaponBay");
            final String[] s3 = data.split("\\s*,\\s*");
            WeaponBay n = null;
            if (slide) {
                if (s3.length >= 4) {
                    n = new WeaponBay(s3[0].trim().toLowerCase(), this.toFloat(s3[1]), this.toFloat(s3[2]), this.toFloat(s3[3]), 0.0f, 0.0f, 0.0f, 90.0f, "wb" + this.partWeaponBay.size(), slide);
                    this.partWeaponBay.add(n);
                }
            }
            else if (s3.length >= 7) {
                final float mx2 = (s3.length >= 8) ? this.toFloat(s3[7], -180.0f, 180.0f) : 90.0f;
                n = new WeaponBay(s3[0].trim().toLowerCase(), this.toFloat(s3[1]), this.toFloat(s3[2]), this.toFloat(s3[3]), this.toFloat(s3[4]), this.toFloat(s3[5]), this.toFloat(s3[6]), mx2 / 90.0f, "wb" + this.partWeaponBay.size(), slide);
                this.partWeaponBay.add(n);
            }
        }
        else if (item.compareTo("addparthatch") == 0 || item.compareTo("addpartslidehatch") == 0) {
            final boolean slide = item.compareTo("addpartslidehatch") == 0;
            final String[] s3 = data.split("\\s*,\\s*");
            Hatch n2 = null;
            if (slide) {
                if (s3.length >= 3) {
                    n2 = new Hatch(this.toFloat(s3[0]), this.toFloat(s3[1]), this.toFloat(s3[2]), 0.0f, 0.0f, 0.0f, 90.0f, "hatch" + this.hatchList.size(), slide);
                    this.hatchList.add(n2);
                }
            }
            else if (s3.length >= 6) {
                final float mx2 = (s3.length >= 7) ? this.toFloat(s3[6], -180.0f, 180.0f) : 90.0f;
                n2 = new Hatch(this.toFloat(s3[0]), this.toFloat(s3[1]), this.toFloat(s3[2]), this.toFloat(s3[3]), this.toFloat(s3[4]), this.toFloat(s3[5]), mx2, "hatch" + this.hatchList.size(), slide);
                this.hatchList.add(n2);
            }
        }
        else if (item.compareTo("addpartcanopy") == 0 || item.compareTo("addpartslidecanopy") == 0) {
            final String[] s = data.split("\\s*,\\s*");
            final boolean slide2 = item.compareTo("addpartslidecanopy") == 0;
            int canopyNum = this.canopyList.size();
            if (canopyNum > 0) {
                --canopyNum;
            }
            if (slide2) {
                if (s.length >= 3) {
                    Canopy c2 = new Canopy(this.toFloat(s[0]), this.toFloat(s[1]), this.toFloat(s[2]), 0.0f, 0.0f, 0.0f, 90.0f, "canopy" + canopyNum, slide2);
                    this.canopyList.add(c2);
                    if (canopyNum == 0) {
                        c2 = new Canopy(this.toFloat(s[0]), this.toFloat(s[1]), this.toFloat(s[2]), 0.0f, 0.0f, 0.0f, 90.0f, "canopy", slide2);
                        this.canopyList.add(c2);
                    }
                }
            }
            else if (s.length >= 6) {
                float mx3 = (s.length >= 7) ? this.toFloat(s[6], -180.0f, 180.0f) : 90.0f;
                mx3 /= 90.0f;
                Canopy c2 = new Canopy(this.toFloat(s[0]), this.toFloat(s[1]), this.toFloat(s[2]), this.toFloat(s[3]), this.toFloat(s[4]), this.toFloat(s[5]), mx3, "canopy" + canopyNum, slide2);
                this.canopyList.add(c2);
                if (canopyNum == 0) {
                    c2 = new Canopy(this.toFloat(s[0]), this.toFloat(s[1]), this.toFloat(s[2]), this.toFloat(s[3]), this.toFloat(s[4]), this.toFloat(s[5]), mx3, "canopy", slide2);
                    this.canopyList.add(c2);
                }
            }
        }
        else if (item.equalsIgnoreCase("AddPartLG") || item.equalsIgnoreCase("AddPartSlideRotLG") || item.equalsIgnoreCase("AddPartLGRev") || item.equalsIgnoreCase("AddPartLGHatch")) {
            final String[] s = data.split("\\s*,\\s*");
            if (!item.equalsIgnoreCase("AddPartSlideRotLG") && s.length >= 6) {
                float maxRot = (s.length >= 7) ? this.toFloat(s[6], -180.0f, 180.0f) : 90.0f;
                maxRot /= 90.0f;
                final LandingGear n3 = new LandingGear(this.toFloat(s[0]), this.toFloat(s[1]), this.toFloat(s[2]), this.toFloat(s[3]), this.toFloat(s[4]), this.toFloat(s[5]), "lg" + this.landingGear.size(), maxRot, item.equalsIgnoreCase("AddPartLgRev"), item.equalsIgnoreCase("AddPartLGHatch"));
                if (s.length >= 8) {
                    n3.enableRot2 = true;
                    n3.maxRotFactor2 = ((s.length >= 11) ? this.toFloat(s[10], -180.0f, 180.0f) : 90.0f);
                    final LandingGear landingGear = n3;
                    landingGear.maxRotFactor2 /= 90.0f;
                    n3.rot2 = Vec3.func_72443_a((double)this.toFloat(s[7]), (double)this.toFloat(s[8]), (double)this.toFloat(s[9]));
                }
                this.landingGear.add(n3);
            }
            if (item.equalsIgnoreCase("AddPartSlideRotLG") && s.length >= 9) {
                float maxRot = (s.length >= 10) ? this.toFloat(s[9], -180.0f, 180.0f) : 90.0f;
                maxRot /= 90.0f;
                final LandingGear n3 = new LandingGear(this.toFloat(s[3]), this.toFloat(s[4]), this.toFloat(s[5]), this.toFloat(s[6]), this.toFloat(s[7]), this.toFloat(s[8]), "lg" + this.landingGear.size(), maxRot, false, false);
                n3.slide = Vec3.func_72443_a((double)this.toFloat(s[0]), (double)this.toFloat(s[1]), (double)this.toFloat(s[2]));
                this.landingGear.add(n3);
            }
        }
        else if (item.equalsIgnoreCase("AddPartThrottle")) {
            final String[] s = data.split("\\s*,\\s*");
            if (s.length >= 7) {
                final float x = (s.length >= 8) ? this.toFloat(s[7]) : 0.0f;
                final float y = (s.length >= 9) ? this.toFloat(s[8]) : 0.0f;
                final float z = (s.length >= 10) ? this.toFloat(s[9]) : 0.0f;
                final Throttle c3 = new Throttle(this.toFloat(s[0]), this.toFloat(s[1]), this.toFloat(s[2]), this.toFloat(s[3]), this.toFloat(s[4]), this.toFloat(s[5]), this.toFloat(s[6]), "throttle" + this.partThrottle.size(), x, y, z);
                this.partThrottle.add(c3);
            }
        }
        else if (item.equalsIgnoreCase("AddPartRotation")) {
            final String[] s = data.split("\\s*,\\s*");
            if (s.length >= 7) {
                final boolean always = s.length < 8 || this.toBool(s[7]);
                final RotPart c4 = new RotPart(this.toFloat(s[0]), this.toFloat(s[1]), this.toFloat(s[2]), this.toFloat(s[3]), this.toFloat(s[4]), this.toFloat(s[5]), this.toFloat(s[6]), always, "rotpart" + this.partThrottle.size());
                this.partRotPart.add(c4);
            }
        }
        else if (item.compareTo("addpartcamera") == 0) {
            final String[] s = data.split("\\s*,\\s*");
            if (s.length >= 3) {
                final boolean ys = s.length < 4 || this.toBool(s[3]);
                final boolean ps = s.length >= 5 && this.toBool(s[4]);
                final Camera c5 = new Camera(this.toFloat(s[0]), this.toFloat(s[1]), this.toFloat(s[2]), 0.0f, -1.0f, 0.0f, "camera" + this.cameraList.size(), ys, ps);
                this.cameraList.add(c5);
            }
        }
        else if (item.equalsIgnoreCase("AddPartWheel")) {
            final String[] s = this.splitParam(data);
            if (s.length >= 3) {
                final float rd = (s.length >= 4) ? this.toFloat(s[3], -1800.0f, 1800.0f) : 0.0f;
                final float rx2 = (s.length >= 7) ? this.toFloat(s[4]) : 0.0f;
                final float ry2 = (s.length >= 7) ? this.toFloat(s[5]) : 1.0f;
                final float rz2 = (s.length >= 7) ? this.toFloat(s[6]) : 0.0f;
                final float px = (s.length >= 10) ? this.toFloat(s[7]) : this.toFloat(s[0]);
                final float py = (s.length >= 10) ? this.toFloat(s[8]) : this.toFloat(s[1]);
                final float pz = (s.length >= 10) ? this.toFloat(s[9]) : this.toFloat(s[2]);
                this.partWheel.add(new PartWheel(this.toFloat(s[0]), this.toFloat(s[1]), this.toFloat(s[2]), rx2, ry2, rz2, rd, px, py, pz, "wheel" + this.partWheel.size()));
            }
        }
        else if (item.equalsIgnoreCase("AddPartSteeringWheel")) {
            final String[] s = this.splitParam(data);
            if (s.length >= 7) {
                this.partSteeringWheel.add(new PartWheel(this.toFloat(s[0]), this.toFloat(s[1]), this.toFloat(s[2]), this.toFloat(s[3]), this.toFloat(s[4]), this.toFloat(s[5]), this.toFloat(s[6]), "steering_wheel" + this.partSteeringWheel.size()));
            }
        }
        else if (item.equalsIgnoreCase("AddTrackRoller")) {
            final String[] s = this.splitParam(data);
            if (s.length >= 3) {
                this.partTrackRoller.add(new TrackRoller(this.toFloat(s[0]), this.toFloat(s[1]), this.toFloat(s[2]), "track_roller" + this.partTrackRoller.size()));
            }
        }
        else if (item.equalsIgnoreCase("AddCrawlerTrack")) {
            this.partCrawlerTrack.add(this.createCrawlerTrack(data, "crawler_track" + this.partCrawlerTrack.size()));
        }
        else if (item.equalsIgnoreCase("PivotTurnThrottle")) {
            this.pivotTurnThrottle = this.toFloat(data, 0.0f, 1.0f);
        }
        else if (item.equalsIgnoreCase("TrackRollerRot")) {
            this.trackRollerRot = this.toFloat(data, -10000.0f, 10000.0f);
        }
        else if (item.equalsIgnoreCase("PartWheelRot")) {
            this.partWheelRot = this.toFloat(data, -10000.0f, 10000.0f);
        }
        else if (item.compareTo("camerazoom") == 0) {
            this.cameraZoom = this.toInt(data, 1, 10);
        }
        else if (item.equalsIgnoreCase("DefaultFreelook")) {
            this.defaultFreelook = this.toBool(data);
        }
        else if (item.equalsIgnoreCase("BoundingBox")) {
            final String[] s = data.split("\\s*,\\s*");
            if (s.length >= 5) {
                final float df = (s.length >= 6) ? this.toFloat(s[5]) : 1.0f;
                final MCH_BoundingBox c6 = new MCH_BoundingBox(this.toFloat(s[0]), this.toFloat(s[1]), this.toFloat(s[2]), this.toFloat(s[3]), this.toFloat(s[4]), df);
                this.extraBoundingBox.add(c6);
                if (c6.boundingBox.field_72337_e > this.markerHeight) {
                    this.markerHeight = (float)c6.boundingBox.field_72337_e;
                }
                this.markerWidth = (float)Math.max(this.markerWidth, Math.abs(c6.boundingBox.field_72336_d) / 2.0);
                this.markerWidth = (float)Math.max(this.markerWidth, Math.abs(c6.boundingBox.field_72340_a) / 2.0);
                this.markerWidth = (float)Math.max(this.markerWidth, Math.abs(c6.boundingBox.field_72334_f) / 2.0);
                this.markerWidth = (float)Math.max(this.markerWidth, Math.abs(c6.boundingBox.field_72339_c) / 2.0);
                this.bbZmin = (float)Math.min(this.bbZmin, c6.boundingBox.field_72339_c);
                this.bbZmax = (float)Math.min(this.bbZmax, c6.boundingBox.field_72334_f);
            }
        }
        else if (item.equalsIgnoreCase("RotorSpeed")) {
            this.rotorSpeed = this.toFloat(data, -10000.0f, 10000.0f);
            if (this.rotorSpeed > 0.01) {
                this.rotorSpeed -= 0.01;
            }
            if (this.rotorSpeed < -0.01) {
                this.rotorSpeed += 0.01;
            }
        }
        else if (item.equalsIgnoreCase("OnGroundPitchFactor")) {
            this.onGroundPitchFactor = this.toFloat(data, 0.0f, 180.0f);
        }
        else if (item.equalsIgnoreCase("OnGroundRollFactor")) {
            this.onGroundRollFactor = this.toFloat(data, 0.0f, 180.0f);
        }
    }
    
    public CrawlerTrack createCrawlerTrack(final String data, final String name) {
        final String[] s = this.splitParam(data);
        final int PC = s.length - 3;
        final boolean REV = this.toBool(s[0]);
        final float LEN = this.toFloat(s[1], 0.001f, 1000.0f) * 0.9f;
        final float Z = this.toFloat(s[2]);
        if (PC < 4) {
            return null;
        }
        final double[] cx = new double[PC];
        final double[] cy = new double[PC];
        for (int i = 0; i < PC; ++i) {
            final int idx = REV ? (PC - i - 1) : i;
            final String[] xy = this.splitParamSlash(s[3 + idx]);
            cx[i] = this.toFloat(xy[0]);
            cy[i] = this.toFloat(xy[1]);
        }
        final List<CrawlerTrackPrm> lp = new ArrayList<CrawlerTrackPrm>();
        lp.add(new CrawlerTrackPrm((float)cx[0], (float)cy[0]));
        double dist = 0.0;
        for (int j = 0; j < PC; ++j) {
            final double x = cx[(j + 1) % PC] - cx[j];
            final double y = cy[(j + 1) % PC] - cy[j];
            final double dist2;
            dist = (dist2 = dist + Math.sqrt(x * x + y * y));
            for (int k = 1; dist >= LEN; dist -= LEN, ++k) {
                lp.add(new CrawlerTrackPrm((float)(cx[j] + x * (LEN * k / dist2)), (float)(cy[j] + y * (LEN * k / dist2))));
            }
        }
        for (int j = 0; j < lp.size(); ++j) {
            final CrawlerTrackPrm pp = lp.get((j + lp.size() - 1) % lp.size());
            final CrawlerTrackPrm cp = lp.get(j);
            final CrawlerTrackPrm np = lp.get((j + 1) % lp.size());
            final float pr = (float)(Math.atan2(pp.x - cp.x, pp.y - cp.y) * 180.0 / 3.141592653589793);
            final float nr = (float)(Math.atan2(np.x - cp.x, np.y - cp.y) * 180.0 / 3.141592653589793);
            final float ppr = (pr + 360.0f) % 360.0f;
            float nnr = nr + 180.0f;
            if ((nnr < ppr - 0.3 || nnr > ppr + 0.3) && nnr - ppr < 100.0f && nnr - ppr > -100.0f) {
                nnr = (nnr + ppr) / 2.0f;
            }
            cp.r = nnr;
        }
        final CrawlerTrack c = new CrawlerTrack(name);
        c.len = LEN;
        c.cx = cx;
        c.cy = cy;
        c.lp = lp;
        c.z = Z;
        c.side = ((Z >= 0.0f) ? 1 : 0);
        return c;
    }
    
    public String getTextureName() {
        final String s = this.textureNameList.get(this.textureCount);
        this.textureCount = (this.textureCount + 1) % this.textureNameList.size();
        return s;
    }
    
    public String getNextTextureName(final String base) {
        if (this.textureNameList.size() >= 2) {
            for (int i = 0; i < this.textureNameList.size(); ++i) {
                final String s = this.textureNameList.get(i);
                if (s.equalsIgnoreCase(base)) {
                    i = (i + 1) % this.textureNameList.size();
                    return this.textureNameList.get(i);
                }
            }
        }
        return base;
    }
    
    @Override
    public void preReload() {
        this.textureNameList.clear();
        this.textureNameList.add(this.name);
        this.cameraList.clear();
        this.cameraPosition.clear();
        this.canopyList.clear();
        this.flare = new Flare();
        this.hatchList.clear();
        this.hudList.clear();
        this.landingGear.clear();
        this.particleSplashs.clear();
        this.searchLights.clear();
        this.partThrottle.clear();
        this.partRotPart.clear();
        this.partCrawlerTrack.clear();
        this.partTrackRoller.clear();
        this.partWheel.clear();
        this.partSteeringWheel.clear();
        this.lightHatchList.clear();
        this.partWeapon.clear();
        this.partWeaponBay.clear();
        this.repellingHooks.clear();
        this.rideRacks.clear();
        this.seatList.clear();
        this.exclusionSeatList.clear();
        this.entityRackList.clear();
        this.extraBoundingBox.clear();
        this.weaponSetList.clear();
        this.lastWeaponIndex = -1;
        this.lastWeaponType = "";
        this.lastWeaponPart = null;
        this.wheels.clear();
        this.unmountPosition = null;
    }
    
    public static String[] getCannotReloadItem() {
        return new String[] { "DisplayName", "AddDisplayName", "ItemID", "AddRecipe", "AddShapelessRecipe", "InventorySize", "Sound", "UAV", "SmallUAV", "TargetDrone", "Category" };
    }
    
    @Override
    public boolean canReloadItem(final String item) {
        final String[] arr$;
        final String[] ignoreItems = arr$ = getCannotReloadItem();
        for (final String s : arr$) {
            if (s.equalsIgnoreCase(item)) {
                return false;
            }
        }
        return true;
    }
    
    public class Wheel
    {
        public final float size;
        public final Vec3 pos;
        
        public Wheel(final Vec3 v, final float sz) {
            this.pos = v;
            this.size = sz;
        }
        
        public Wheel(final MCH_AircraftInfo mch_AircraftInfo, final Vec3 v) {
            this(mch_AircraftInfo, v, 1.0f);
        }
    }
    
    public class Flare
    {
        public int[] types;
        public Vec3 pos;
        
        public Flare() {
            this.types = new int[0];
            this.pos = Vec3.func_72443_a(0.0, 0.0, 0.0);
        }
    }
    
    public class SearchLight
    {
        public final int colorStart;
        public final int colorEnd;
        public final Vec3 pos;
        public final float height;
        public final float width;
        public final float angle;
        public final boolean fixDir;
        public final float yaw;
        public final float pitch;
        public final boolean steering;
        public final float stRot;
        
        public SearchLight(final Vec3 pos, final int cs, final int ce, final float h, final float w, final boolean fix, final float y, final float p, final boolean st, final float stRot) {
            this.colorStart = cs;
            this.colorEnd = ce;
            this.pos = pos;
            this.height = h;
            this.width = w;
            this.angle = (float)(Math.atan2(w / 2.0f, h) * 180.0 / 3.141592653589793);
            this.fixDir = fix;
            this.steering = st;
            this.yaw = y;
            this.pitch = p;
            this.stRot = stRot;
        }
    }
    
    public class RideRack
    {
        public final String name;
        public final int rackID;
        
        public RideRack(final String n, final int id) {
            this.name = n;
            this.rackID = id;
        }
    }
    
    public class ParticleSplash
    {
        public final int num;
        public final float acceleration;
        public final float size;
        public final Vec3 pos;
        public final int age;
        public final float motionY;
        public final float gravity;
        
        public ParticleSplash(final Vec3 v, final int nm, final float siz, final float acc, final int ag, final float my, final float gr) {
            this.num = nm;
            this.pos = v;
            this.size = siz;
            this.acceleration = acc;
            this.age = ag;
            this.motionY = my;
            this.gravity = gr;
        }
    }
    
    public class RepellingHook
    {
        final Vec3 pos;
        final int interval;
        
        public RepellingHook(final Vec3 pos, final int inv) {
            this.pos = pos;
            this.interval = inv;
        }
    }
    
    public class WeaponSet
    {
        public final String type;
        public ArrayList<Weapon> weapons;
        
        public WeaponSet(final String t) {
            this.type = t;
            this.weapons = new ArrayList<Weapon>();
        }
    }
    
    public class Weapon
    {
        public final Vec3 pos;
        public final float yaw;
        public final float pitch;
        public final boolean canUsePilot;
        public final int seatID;
        public final float defaultYaw;
        public final float minYaw;
        public final float maxYaw;
        public final float minPitch;
        public final float maxPitch;
        public final boolean turret;
        
        public Weapon(final float x, final float y, final float z, final float yaw, final float pitch, final boolean canPirot, final int seatId, final float defy, final float mny, final float mxy, final float mnp, final float mxp, final boolean turret) {
            this.pos = Vec3.func_72443_a((double)x, (double)y, (double)z);
            this.yaw = yaw;
            this.pitch = pitch;
            this.canUsePilot = canPirot;
            this.seatID = seatId;
            this.defaultYaw = defy;
            this.minYaw = mny;
            this.maxYaw = mxy;
            this.minPitch = mnp;
            this.maxPitch = mxp;
            this.turret = turret;
        }
    }
    
    public class DrawnPart
    {
        public final Vec3 pos;
        public final Vec3 rot;
        public final String modelName;
        public IModelCustom model;
        
        public DrawnPart(final float px, final float py, final float pz, final float rx, final float ry, final float rz, final String name) {
            this.pos = Vec3.func_72443_a((double)px, (double)py, (double)pz);
            this.rot = Vec3.func_72443_a((double)rx, (double)ry, (double)rz);
            this.modelName = name;
            this.model = null;
        }
    }
    
    public class Hatch extends DrawnPart
    {
        public final float maxRotFactor;
        public final float maxRot;
        public final boolean isSlide;
        
        public Hatch(final float px, final float py, final float pz, final float rx, final float ry, final float rz, final float mr, final String name, final boolean slide) {
            super(px, py, pz, rx, ry, rz, name);
            this.maxRot = mr;
            this.maxRotFactor = this.maxRot / 90.0f;
            this.isSlide = slide;
        }
    }
    
    public class PartWheel extends DrawnPart
    {
        final float rotDir;
        final Vec3 pos2;
        
        public PartWheel(final float px, final float py, final float pz, final float rx, final float ry, final float rz, final float rd, final float px2, final float py2, final float pz2, final String name) {
            super(px, py, pz, rx, ry, rz, name);
            this.rotDir = rd;
            this.pos2 = Vec3.func_72443_a((double)px2, (double)py2, (double)pz2);
        }
        
        public PartWheel(final MCH_AircraftInfo mch_AircraftInfo, final float px, final float py, final float pz, final float rx, final float ry, final float rz, final float rd, final String name) {
            this(mch_AircraftInfo, px, py, pz, rx, ry, rz, rd, px, py, pz, name);
        }
    }
    
    public class TrackRoller extends DrawnPart
    {
        final int side;
        
        public TrackRoller(final float px, final float py, final float pz, final String name) {
            super(px, py, pz, 0.0f, 0.0f, 0.0f, name);
            this.side = ((px >= 0.0f) ? 1 : 0);
        }
    }
    
    public class CrawlerTrackPrm
    {
        float x;
        float y;
        float nx;
        float ny;
        float r;
        
        public CrawlerTrackPrm(final float x, final float y) {
            this.x = x;
            this.y = y;
        }
    }
    
    public class CrawlerTrack extends DrawnPart
    {
        public float len;
        public double[] cx;
        public double[] cy;
        public List<CrawlerTrackPrm> lp;
        public float z;
        public int side;
        
        public CrawlerTrack(final String name) {
            super(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, name);
            this.len = 0.35f;
        }
    }
    
    public class Camera extends DrawnPart
    {
        public final boolean yawSync;
        public final boolean pitchSync;
        
        public Camera(final float px, final float py, final float pz, final float rx, final float ry, final float rz, final String name, final boolean ys, final boolean ps) {
            super(px, py, pz, rx, ry, rz, name);
            this.yawSync = ys;
            this.pitchSync = ps;
        }
    }
    
    public class Throttle extends DrawnPart
    {
        public final Vec3 slide;
        public final float rot2;
        
        public Throttle(final float px, final float py, final float pz, final float rx, final float ry, final float rz, final float rot, final String name, final float px2, final float py2, final float pz2) {
            super(px, py, pz, rx, ry, rz, name);
            this.rot2 = rot;
            this.slide = Vec3.func_72443_a((double)px2, (double)py2, (double)pz2);
        }
    }
    
    public class LandingGear extends DrawnPart
    {
        public Vec3 slide;
        public final float maxRotFactor;
        public boolean enableRot2;
        public Vec3 rot2;
        public float maxRotFactor2;
        public final boolean reverse;
        public final boolean hatch;
        
        public LandingGear(final float x, final float y, final float z, final float rx, final float ry, final float rz, final String model, final float maxRotF, final boolean rev, final boolean isHatch) {
            super(x, y, z, rx, ry, rz, model);
            this.slide = null;
            this.maxRotFactor = maxRotF;
            this.enableRot2 = false;
            this.rot2 = Vec3.func_72443_a(0.0, 0.0, 0.0);
            this.maxRotFactor2 = 0.0f;
            this.reverse = rev;
            this.hatch = isHatch;
        }
    }
    
    public class Canopy extends DrawnPart
    {
        public final float maxRotFactor;
        public final boolean isSlide;
        
        public Canopy(final float px, final float py, final float pz, final float rx, final float ry, final float rz, final float mr, final String name, final boolean slide) {
            super(px, py, pz, rx, ry, rz, name);
            this.maxRotFactor = mr;
            this.isSlide = slide;
        }
    }
    
    public class PartWeapon extends DrawnPart
    {
        public final String[] name;
        public final boolean rotBarrel;
        public final boolean isMissile;
        public final boolean hideGM;
        public final boolean yaw;
        public final boolean pitch;
        public final float recoilBuf;
        public List<PartWeaponChild> child;
        public final boolean turret;
        
        public PartWeapon(final String[] name, final boolean rotBrl, final boolean missile, final boolean hgm, final boolean y, final boolean p, final float px, final float py, final float pz, final String modelName, final float rx, final float ry, final float rz, final float rb, final boolean turret) {
            super(px, py, pz, rx, ry, rz, modelName);
            this.name = name;
            this.rotBarrel = rotBrl;
            this.isMissile = missile;
            this.hideGM = hgm;
            this.yaw = y;
            this.pitch = p;
            this.recoilBuf = rb;
            this.child = new ArrayList<PartWeaponChild>();
            this.turret = turret;
        }
    }
    
    public class PartWeaponChild extends DrawnPart
    {
        public final String[] name;
        public final boolean yaw;
        public final boolean pitch;
        public final float recoilBuf;
        
        public PartWeaponChild(final String[] name, final boolean y, final boolean p, final float px, final float py, final float pz, final String modelName, final float rx, final float ry, final float rz, final float rb) {
            super(px, py, pz, rx, ry, rz, modelName);
            this.name = name;
            this.yaw = y;
            this.pitch = p;
            this.recoilBuf = rb;
        }
    }
    
    public class WeaponBay extends DrawnPart
    {
        public final float maxRotFactor;
        public final boolean isSlide;
        private final String weaponName;
        public Integer[] weaponIds;
        
        public WeaponBay(final String wn, final float px, final float py, final float pz, final float rx, final float ry, final float rz, final float mr, final String name, final boolean slide) {
            super(px, py, pz, rx, ry, rz, name);
            this.maxRotFactor = mr;
            this.isSlide = slide;
            this.weaponName = wn;
            this.weaponIds = new Integer[0];
        }
    }
    
    public class RotPart extends DrawnPart
    {
        public final float rotSpeed;
        public final boolean rotAlways;
        
        public RotPart(final float px, final float py, final float pz, final float rx, final float ry, final float rz, final float mr, final boolean a, final String name) {
            super(px, py, pz, rx, ry, rz, name);
            this.rotSpeed = mr;
            this.rotAlways = a;
        }
    }
    
    public class CameraPosition
    {
        public final Vec3 pos;
        public final boolean fixRot;
        public final float yaw;
        public final float pitch;
        
        public CameraPosition(final Vec3 vec3, final boolean fixRot, final float yaw, final float pitch) {
            this.pos = vec3;
            this.fixRot = fixRot;
            this.yaw = yaw;
            this.pitch = pitch;
        }
        
        public CameraPosition(final MCH_AircraftInfo mch_AircraftInfo, final Vec3 vec3) {
            this(mch_AircraftInfo, vec3, false, 0.0f, 0.0f);
        }
        
        public CameraPosition(final MCH_AircraftInfo mch_AircraftInfo) {
            this(mch_AircraftInfo, Vec3.func_72443_a(0.0, 0.0, 0.0));
        }
    }
}
