package mcheli.aircraft;

import cpw.mods.fml.common.registry.*;
import mcheli.flare.*;
import mcheli.chain.*;
import mcheli.uav.*;
import net.minecraft.entity.player.*;
import mcheli.command.*;
import cpw.mods.fml.relauncher.*;
import io.netty.buffer.*;
import net.minecraft.nbt.*;
import mcheli.multiplay.*;
import net.minecraft.world.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.entity.item.*;
import net.minecraft.potion.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import net.minecraft.crash.*;
import net.minecraft.block.*;
import mcheli.particles.*;
import net.minecraft.init.*;
import mcheli.*;
import mcheli.parachute.*;
import net.minecraft.entity.*;
import mcheli.tool.*;
import java.util.*;
import mcheli.wrapper.*;
import mcheli.weapon.*;
import net.minecraft.item.*;

public abstract class MCH_EntityAircraft extends W_EntityContainer implements MCH_IEntityLockChecker, MCH_IEntityCanRideAircraft, IEntityAdditionalSpawnData
{
    private static final int DATAWT_ID_DAMAGE = 19;
    private static final int DATAWT_ID_TYPE = 20;
    private static final int DATAWT_ID_TEXTURE_NAME = 21;
    private static final int DATAWT_ID_UAV_STATION = 22;
    private static final int DATAWT_ID_STATUS = 23;
    private static final int CMN_ID_FLARE = 0;
    private static final int CMN_ID_FREE_LOOK = 1;
    private static final int CMN_ID_RELOADING = 2;
    private static final int CMN_ID_INGINITY_AMMO = 3;
    private static final int CMN_ID_INGINITY_FUEL = 4;
    private static final int CMN_ID_RAPELLING = 5;
    private static final int CMN_ID_SEARCHLIGHT = 6;
    private static final int CMN_ID_CNTRL_LEFT = 7;
    private static final int CMN_ID_CNTRL_RIGHT = 8;
    private static final int CMN_ID_CNTRL_UP = 9;
    private static final int CMN_ID_CNTRL_DOWN = 10;
    private static final int CMN_ID_CNTRL_BRAKE = 11;
    private static final int DATAWT_ID_USE_WEAPON = 24;
    private static final int DATAWT_ID_FUEL = 25;
    private static final int DATAWT_ID_ROT_ROLL = 26;
    private static final int DATAWT_ID_COMMAND = 27;
    private static final int DATAWT_ID_THROTTLE = 29;
    protected static final int DATAWT_ID_FOLD_STAT = 30;
    protected static final int DATAWT_ID_PART_STAT = 31;
    protected static final int PART_ID_CANOPY = 0;
    protected static final int PART_ID_NOZZLE = 1;
    protected static final int PART_ID_LANDINGGEAR = 2;
    protected static final int PART_ID_WING = 3;
    protected static final int PART_ID_HATCH = 4;
    public static final byte LIMIT_GROUND_PITCH = 40;
    public static final byte LIMIT_GROUND_ROLL = 40;
    public boolean isRequestedSyncStatus;
    private MCH_AircraftInfo acInfo;
    private int commonStatus;
    private Entity[] partEntities;
    private MCH_EntityHitBox pilotSeat;
    private MCH_EntitySeat[] seats;
    private MCH_SeatInfo[] seatsInfo;
    private String commonUniqueId;
    private int seatSearchCount;
    protected double velocityX;
    protected double velocityY;
    protected double velocityZ;
    public boolean keepOnRideRotation;
    protected int aircraftPosRotInc;
    protected double aircraftX;
    protected double aircraftY;
    protected double aircraftZ;
    protected double aircraftYaw;
    protected double aircraftPitch;
    public boolean aircraftRollRev;
    public boolean aircraftRotChanged;
    public float rotationRoll;
    public float prevRotationRoll;
    private double currentThrottle;
    private double prevCurrentThrottle;
    public double currentSpeed;
    public int currentFuel;
    public float throttleBack;
    public double beforeHoverThrottle;
    public int waitMountEntity;
    public boolean throttleUp;
    public boolean throttleDown;
    public boolean moveLeft;
    public boolean moveRight;
    public MCH_LowPassFilterFloat lowPassPartialTicks;
    private MCH_Radar entityRadar;
    private int radarRotate;
    private MCH_Flare flareDv;
    private int currentFlareIndex;
    protected MCH_WeaponSet[] weapons;
    protected int[] currentWeaponID;
    public float lastRiderYaw;
    public float prevLastRiderYaw;
    public float lastRiderPitch;
    public float prevLastRiderPitch;
    protected MCH_WeaponSet dummyWeapon;
    protected int useWeaponStat;
    protected int hitStatus;
    protected final MCH_SoundUpdater soundUpdater;
    protected Entity lastRiddenByEntity;
    protected Entity lastRidingEntity;
    public List<UnmountReserve> listUnmountReserve;
    private int countOnUpdate;
    private MCH_EntityChain towChainEntity;
    private MCH_EntityChain towedChainEntity;
    public MCH_Camera camera;
    private int cameraId;
    protected boolean isGunnerMode;
    protected boolean isGunnerModeOtherSeat;
    private boolean isHoveringMode;
    public static final int CAMERA_PITCH_MIN = -30;
    public static final int CAMERA_PITCH_MAX = 70;
    private MCH_EntityTvMissile TVmissile;
    protected boolean isGunnerFreeLookMode;
    public final MCH_MissileDetector missileDetector;
    public int serverNoMoveCount;
    public int repairCount;
    public int beforeDamageTaken;
    public int timeSinceHit;
    private int despawnCount;
    public float rotDestroyedYaw;
    public float rotDestroyedPitch;
    public float rotDestroyedRoll;
    public int damageSinceDestroyed;
    public boolean isFirstDamageSmoke;
    public Vec3[] prevDamageSmokePos;
    private MCH_EntityUavStation uavStation;
    public boolean cs_dismountAll;
    public boolean cs_heliAutoThrottleDown;
    public boolean cs_planeAutoThrottleDown;
    public boolean cs_tankAutoThrottleDown;
    public MCH_Parts partHatch;
    public MCH_Parts partCanopy;
    public MCH_Parts partLandingGear;
    public double prevRidingEntityPosX;
    public double prevRidingEntityPosY;
    public double prevRidingEntityPosZ;
    public boolean canRideRackStatus;
    private int modeSwitchCooldown;
    public MCH_BoundingBox[] extraBoundingBox;
    public float lastBBDamageFactor;
    private final MCH_AircraftInventory inventory;
    private double fuelConsumption;
    private int fuelSuppliedCount;
    private int supplyAmmoWait;
    private boolean beforeSupplyAmmo;
    public WeaponBay[] weaponBays;
    public float[] rotPartRotation;
    public float[] prevRotPartRotation;
    public float[] rotCrawlerTrack;
    public float[] prevRotCrawlerTrack;
    public float[] throttleCrawlerTrack;
    public float[] rotTrackRoller;
    public float[] prevRotTrackRoller;
    public float rotWheel;
    public float prevRotWheel;
    public float rotYawWheel;
    public float prevRotYawWheel;
    private boolean isParachuting;
    public float ropesLength;
    private MCH_Queue<Vec3> prevPosition;
    private int tickRepelling;
    private int lastUsedRopeIndex;
    private boolean dismountedUserCtrl;
    public float lastSearchLightYaw;
    public float lastSearchLightPitch;
    public float rotLightHatch;
    public float prevRotLightHatch;
    public int recoilCount;
    public float recoilYaw;
    public float recoilValue;
    public int brightnessHigh;
    public int brightnessLow;
    public final HashMap<Entity, Integer> noCollisionEntities;
    private double lastCalcLandInDistanceCount;
    private double lastLandInDistance;
    private static final MCH_EntitySeat[] seatsDummy;
    private boolean switchSeat;
    
    public MCH_EntityAircraft(final World world) {
        super(world);
        this.throttleBack = 0.0f;
        this.waitMountEntity = 0;
        this.throttleUp = false;
        this.throttleDown = false;
        this.moveLeft = false;
        this.moveRight = false;
        this.listUnmountReserve = new ArrayList<UnmountReserve>();
        this.isGunnerMode = false;
        this.isGunnerModeOtherSeat = false;
        this.isHoveringMode = false;
        this.isGunnerFreeLookMode = false;
        this.serverNoMoveCount = 0;
        this.isFirstDamageSmoke = true;
        this.prevDamageSmokePos = new Vec3[0];
        this.rotCrawlerTrack = new float[2];
        this.prevRotCrawlerTrack = new float[2];
        this.throttleCrawlerTrack = new float[2];
        this.rotTrackRoller = new float[2];
        this.prevRotTrackRoller = new float[2];
        this.rotWheel = 0.0f;
        this.prevRotWheel = 0.0f;
        this.rotYawWheel = 0.0f;
        this.prevRotYawWheel = 0.0f;
        this.ropesLength = 0.0f;
        this.rotLightHatch = 0.0f;
        this.prevRotLightHatch = 0.0f;
        this.recoilCount = 0;
        this.recoilYaw = 0.0f;
        this.recoilValue = 0.0f;
        this.brightnessHigh = 240;
        this.brightnessLow = 240;
        this.noCollisionEntities = new HashMap<Entity, Integer>();
        this.switchSeat = false;
        this.isRequestedSyncStatus = false;
        this.setAcInfo(null);
        this.commonStatus = 0;
        this.dropContentsWhenDead = false;
        this.field_70158_ak = true;
        this.flareDv = new MCH_Flare(world, this);
        this.currentFlareIndex = 0;
        this.entityRadar = new MCH_Radar(world);
        this.radarRotate = 0;
        this.currentWeaponID = new int[0];
        this.aircraftPosRotInc = 0;
        this.aircraftX = 0.0;
        this.aircraftY = 0.0;
        this.aircraftZ = 0.0;
        this.aircraftYaw = 0.0;
        this.aircraftPitch = 0.0;
        this.setCurrentThrottle(this.currentSpeed = 0.0);
        this.currentFuel = 0;
        this.cs_dismountAll = false;
        this.cs_heliAutoThrottleDown = true;
        this.cs_planeAutoThrottleDown = false;
        final MCH_Config config = MCH_MOD.config;
        this.field_70155_l = MCH_Config.RenderDistanceWeight.prmDouble;
        this.setCommonUniqueId("");
        this.seatSearchCount = 0;
        this.seatsInfo = null;
        this.seats = new MCH_EntitySeat[0];
        this.pilotSeat = new MCH_EntityHitBox(world, this, 1.0f, 1.0f);
        this.pilotSeat.parent = this;
        this.partEntities = new Entity[] { this.pilotSeat };
        this.setTextureName("");
        this.camera = new MCH_Camera(world, this, this.field_70165_t, this.field_70163_u, this.field_70161_v);
        this.setCameraId(0);
        this.lastRiddenByEntity = null;
        this.lastRidingEntity = null;
        this.soundUpdater = MCH_MOD.proxy.CreateSoundUpdater(this);
        this.countOnUpdate = 0;
        this.setTowChainEntity(null);
        this.dummyWeapon = new MCH_WeaponSet(new MCH_WeaponDummy(this.field_70170_p, Vec3.func_72443_a(0.0, 0.0, 0.0), 0.0f, 0.0f, "", null));
        this.useWeaponStat = 0;
        this.hitStatus = 0;
        this.repairCount = 0;
        this.beforeDamageTaken = 0;
        this.setDespawnCount(this.timeSinceHit = 0);
        this.missileDetector = new MCH_MissileDetector(this, world);
        this.uavStation = null;
        this.modeSwitchCooldown = 0;
        this.partHatch = null;
        this.partCanopy = null;
        this.partLandingGear = null;
        this.weaponBays = new WeaponBay[0];
        this.rotPartRotation = new float[0];
        this.prevRotPartRotation = new float[0];
        this.lastRiderYaw = 0.0f;
        this.prevLastRiderYaw = 0.0f;
        this.lastRiderPitch = 0.0f;
        this.prevLastRiderPitch = 0.0f;
        this.rotationRoll = 0.0f;
        this.prevRotationRoll = 0.0f;
        this.lowPassPartialTicks = new MCH_LowPassFilterFloat(10);
        this.extraBoundingBox = new MCH_BoundingBox[0];
        W_Reflection.setBoundingBox(this, new MCH_AircraftBoundingBox(this));
        this.lastBBDamageFactor = 1.0f;
        this.inventory = new MCH_AircraftInventory(this);
        this.fuelConsumption = 0.0;
        this.fuelSuppliedCount = 0;
        this.canRideRackStatus = false;
        this.isParachuting = false;
        this.prevPosition = new MCH_Queue<Vec3>(10, Vec3.func_72443_a(0.0, 0.0, 0.0));
        final float n = 0.0f;
        this.lastSearchLightPitch = n;
        this.lastSearchLightYaw = n;
    }
    
    @Override
    protected void func_70088_a() {
        super.func_70088_a();
        this.func_70096_w().func_75682_a(20, (Object)"");
        this.func_70096_w().func_75682_a(19, (Object)new Integer(0));
        this.func_70096_w().func_75682_a(23, (Object)new Integer(0));
        this.func_70096_w().func_75682_a(24, (Object)new Integer(0));
        this.func_70096_w().func_75682_a(25, (Object)new Integer(0));
        this.func_70096_w().func_75682_a(21, (Object)"");
        this.func_70096_w().func_75682_a(22, (Object)new Integer(0));
        this.func_70096_w().func_75682_a(26, (Object)new Short((short)0));
        this.func_70096_w().func_75682_a(27, (Object)new String(""));
        this.func_70096_w().func_75682_a(29, (Object)new Integer(0));
        this.func_70096_w().func_75682_a(31, (Object)new Integer(0));
        if (!this.field_70170_p.field_72995_K) {
            final int bit = 3;
            final MCH_Config config = MCH_MOD.config;
            this.setCommonStatus(bit, MCH_Config.InfinityAmmo.prmBool);
            final int bit2 = 4;
            final MCH_Config config2 = MCH_MOD.config;
            this.setCommonStatus(bit2, MCH_Config.InfinityFuel.prmBool);
        }
        this.getEntityData().func_74778_a("EntityType", this.getEntityType());
    }
    
    public float getServerRoll() {
        return this.func_70096_w().func_75693_b(26);
    }
    
    public float getRotYaw() {
        return this.field_70177_z;
    }
    
    public float getRotPitch() {
        return this.field_70125_A;
    }
    
    public float getRotRoll() {
        return this.rotationRoll;
    }
    
    public void setRotYaw(final float f) {
        this.field_70177_z = f;
    }
    
    public void setRotPitch(final float f) {
        this.field_70125_A = f;
    }
    
    public void setRotPitch(final float f, final String msg) {
        this.setRotPitch(f);
    }
    
    public void setRotRoll(final float f) {
        this.rotationRoll = f;
    }
    
    public void applyOnGroundPitch(final float factor) {
        if (this.getAcInfo() != null) {
            final float ogp = this.getAcInfo().onGroundPitch;
            float pitch = this.getRotPitch();
            pitch -= ogp;
            pitch *= factor;
            pitch += ogp;
            this.setRotPitch(pitch, "applyOnGroundPitch");
        }
        this.setRotRoll(this.getRotRoll() * factor);
    }
    
    public float calcRotYaw(final float partialTicks) {
        return this.field_70126_B + (this.getRotYaw() - this.field_70126_B) * partialTicks;
    }
    
    public float calcRotPitch(final float partialTicks) {
        return this.field_70127_C + (this.getRotPitch() - this.field_70127_C) * partialTicks;
    }
    
    public float calcRotRoll(final float partialTicks) {
        return this.prevRotationRoll + (this.getRotRoll() - this.prevRotationRoll) * partialTicks;
    }
    
    protected void func_70101_b(final float y, final float p) {
        this.setRotYaw(y % 360.0f);
        this.setRotPitch(p % 360.0f);
    }
    
    public boolean isInfinityAmmo(final Entity player) {
        return this.isCreative(player) || this.getCommonStatus(3);
    }
    
    public boolean isInfinityFuel(final Entity player, final boolean checkOtherSeet) {
        if (this.isCreative(player) || this.getCommonStatus(4)) {
            return true;
        }
        if (checkOtherSeet) {
            for (final MCH_EntitySeat seat : this.getSeats()) {
                if (seat != null && this.isCreative(seat.field_70153_n)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void setCommand(final String s, final EntityPlayer player) {
        if (!this.field_70170_p.field_72995_K && MCH_Command.canUseCommand((Entity)player)) {
            this.setCommandForce(s);
        }
    }
    
    public void setCommandForce(final String s) {
        if (!this.field_70170_p.field_72995_K) {
            this.func_70096_w().func_75692_b(27, (Object)s);
        }
    }
    
    public String getCommand() {
        return this.func_70096_w().func_75681_e(27);
    }
    
    public String getKindName() {
        return "";
    }
    
    public String getEntityType() {
        return "";
    }
    
    public void setTypeName(final String s) {
        final String beforeType = this.getTypeName();
        if (s != null && !s.isEmpty() && s.compareTo(beforeType) != 0) {
            this.func_70096_w().func_75692_b(20, (Object)String.valueOf(s));
            this.changeType(s);
            this.initRotationYaw(this.getRotYaw());
        }
    }
    
    public String getTypeName() {
        return this.func_70096_w().func_75681_e(20);
    }
    
    public abstract void changeType(final String p0);
    
    public boolean isTargetDrone() {
        return this.getAcInfo() != null && this.getAcInfo().isTargetDrone;
    }
    
    public boolean isUAV() {
        return this.getAcInfo() != null && this.getAcInfo().isUAV;
    }
    
    public boolean isSmallUAV() {
        return this.getAcInfo() != null && this.getAcInfo().isSmallUAV;
    }
    
    public boolean isAlwaysCameraView() {
        return this.getAcInfo() != null && this.getAcInfo().alwaysCameraView;
    }
    
    public void setUavStation(final MCH_EntityUavStation uavSt) {
        this.uavStation = uavSt;
        if (!this.field_70170_p.field_72995_K) {
            if (uavSt != null) {
                this.func_70096_w().func_75692_b(22, (Object)W_Entity.getEntityId(uavSt));
            }
            else {
                this.func_70096_w().func_75692_b(22, (Object)0);
            }
        }
    }
    
    public float getStealth() {
        return (this.getAcInfo() != null) ? this.getAcInfo().stealth : 0.0f;
    }
    
    public MCH_AircraftInventory getGuiInventory() {
        return this.inventory;
    }
    
    public void openGui(final EntityPlayer player) {
        if (!this.field_70170_p.field_72995_K) {
            player.openGui((Object)MCH_MOD.instance, 1, this.field_70170_p, (int)this.field_70165_t, (int)this.field_70163_u, (int)this.field_70161_v);
        }
    }
    
    public MCH_EntityUavStation getUavStation() {
        return this.isUAV() ? this.uavStation : null;
    }
    
    public static MCH_EntityAircraft getAircraft_RiddenOrControl(final Entity rider) {
        if (rider != null) {
            if (rider.field_70154_o instanceof MCH_EntityAircraft) {
                return (MCH_EntityAircraft)rider.field_70154_o;
            }
            if (rider.field_70154_o instanceof MCH_EntitySeat) {
                return ((MCH_EntitySeat)rider.field_70154_o).getParent();
            }
            if (rider.field_70154_o instanceof MCH_EntityUavStation) {
                final MCH_EntityUavStation uavStation = (MCH_EntityUavStation)rider.field_70154_o;
                return uavStation.getControlAircract();
            }
        }
        return null;
    }
    
    public boolean isCreative(final Entity entity) {
        return entity instanceof EntityPlayer && ((EntityPlayer)entity).field_71075_bZ.field_75098_d;
    }
    
    public Entity getRiddenByEntity() {
        if (this.isUAV() && this.uavStation != null) {
            return this.uavStation.field_70153_n;
        }
        return this.field_70153_n;
    }
    
    public boolean getCommonStatus(final int bit) {
        return (this.commonStatus >> bit & 0x1) != 0x0;
    }
    
    public void setCommonStatus(final int bit, final boolean b) {
        this.setCommonStatus(bit, b, false);
    }
    
    public void setCommonStatus(final int bit, final boolean b, final boolean writeClient) {
        if (!this.field_70170_p.field_72995_K || writeClient) {
            final int bofore = this.commonStatus;
            final int mask = 1 << bit;
            if (b) {
                this.commonStatus |= mask;
            }
            else {
                this.commonStatus &= ~mask;
            }
            if (bofore != this.commonStatus) {
                this.func_70096_w().func_75692_b(23, (Object)this.commonStatus);
            }
        }
    }
    
    public double getThrottle() {
        return 0.05 * this.func_70096_w().func_75679_c(29);
    }
    
    public void setThrottle(final double t) {
        int n = (int)(t * 20.0);
        if (n == 0 && t > 0.0) {
            n = 1;
        }
        this.func_70096_w().func_75692_b(29, (Object)n);
    }
    
    public int getMaxHP() {
        return (this.getAcInfo() != null) ? this.getAcInfo().maxHp : 100;
    }
    
    public int getHP() {
        return (this.getMaxHP() - this.getDamageTaken() >= 0) ? (this.getMaxHP() - this.getDamageTaken()) : 0;
    }
    
    public void setDamageTaken(int par1) {
        if (par1 < 0) {
            par1 = 0;
        }
        if (par1 > this.getMaxHP()) {
            par1 = this.getMaxHP();
        }
        this.func_70096_w().func_75692_b(19, (Object)par1);
    }
    
    public int getDamageTaken() {
        return this.func_70096_w().func_75679_c(19);
    }
    
    public void destroyAircraft() {
        this.setSearchLight(false);
        this.switchHoveringMode(false);
        this.switchGunnerMode(false);
        for (int i = 0; i < this.getSeatNum() + 1; ++i) {
            final Entity e = this.getEntityBySeatId(i);
            if (e instanceof EntityPlayer) {
                this.switchCameraMode((EntityPlayer)e, 0);
            }
        }
        if (this.isTargetDrone()) {
            this.setDespawnCount(50);
        }
        else {
            this.setDespawnCount(500);
        }
        this.rotDestroyedPitch = this.field_70146_Z.nextFloat() - 0.5f;
        this.rotDestroyedRoll = (this.field_70146_Z.nextFloat() - 0.5f) * 0.5f;
        this.rotDestroyedYaw = 0.0f;
        if (this.isUAV() && this.getRiddenByEntity() != null) {
            this.getRiddenByEntity().func_70078_a((Entity)null);
        }
        if (!this.field_70170_p.field_72995_K) {
            this.ejectSeat(this.getRiddenByEntity());
            final Entity entity = this.getEntityBySeatId(1);
            if (entity != null) {
                this.ejectSeat(entity);
            }
        }
    }
    
    public boolean isDestroyed() {
        return this.getDespawnCount() > 0;
    }
    
    public int getDespawnCount() {
        return this.despawnCount;
    }
    
    public void setDespawnCount(final int despawnCount) {
        this.despawnCount = despawnCount;
    }
    
    public boolean isEntityRadarMounted() {
        return this.getAcInfo() != null && this.getAcInfo().isEnableEntityRadar;
    }
    
    public boolean canFloatWater() {
        return this.getAcInfo() != null && this.getAcInfo().isFloat && !this.isDestroyed();
    }
    
    @SideOnly(Side.CLIENT)
    public int func_70070_b(final float par1) {
        if (this.haveSearchLight() && this.isSearchLightON()) {
            return 15728880;
        }
        final int i = MathHelper.func_76128_c(this.field_70165_t);
        final int j = MathHelper.func_76128_c(this.field_70161_v);
        if (this.field_70170_p.func_72899_e(i, 0, j)) {
            final double d0 = (this.field_70121_D.field_72337_e - this.field_70121_D.field_72338_b) * 0.66;
            float fo = (this.getAcInfo() != null) ? this.getAcInfo().submergedDamageHeight : 0.0f;
            if (this.canFloatWater()) {
                fo = this.getAcInfo().floatOffset;
                if (fo < 0.0f) {
                    fo = -fo;
                }
                ++fo;
            }
            final int k = MathHelper.func_76128_c(this.field_70163_u + fo - this.field_70129_M + d0);
            final int val = this.field_70170_p.func_72802_i(i, k, j, 0);
            final int low = val & 0xFFFF;
            final int high = val >> 16 & 0xFFFF;
            if (high < this.brightnessHigh) {
                if (this.brightnessHigh > 0 && this.getCountOnUpdate() % 2 == 0) {
                    --this.brightnessHigh;
                }
            }
            else if (high > this.brightnessHigh) {
                this.brightnessHigh += 4;
                if (this.brightnessHigh > 240) {
                    this.brightnessHigh = 240;
                }
            }
            return this.brightnessHigh << 16 | low;
        }
        return 0;
    }
    
    public MCH_AircraftInfo.CameraPosition getCameraPosInfo() {
        if (this.getAcInfo() == null) {
            return null;
        }
        final Entity player = MCH_Lib.getClientPlayer();
        final int sid = this.getSeatIdByEntity(player);
        if (sid == 0 && this.canSwitchCameraPos() && this.getCameraId() > 0 && this.getCameraId() < this.getAcInfo().cameraPosition.size()) {
            return this.getAcInfo().cameraPosition.get(this.getCameraId());
        }
        if (sid > 0 && sid < this.getSeatsInfo().length && this.getSeatsInfo()[sid].invCamPos) {
            return this.getSeatsInfo()[sid].getCamPos();
        }
        return this.getAcInfo().cameraPosition.get(0);
    }
    
    public int getCameraId() {
        return this.cameraId;
    }
    
    public void setCameraId(final int cameraId) {
        MCH_Lib.DbgLog(true, "MCH_EntityAircraft.setCameraId %d -> %d", this.cameraId, cameraId);
        this.cameraId = cameraId;
    }
    
    public boolean canSwitchCameraPos() {
        return this.getCameraPosNum() >= 2;
    }
    
    public int getCameraPosNum() {
        if (this.getAcInfo() != null) {
            return this.getAcInfo().cameraPosition.size();
        }
        return 1;
    }
    
    public void onAcInfoReloaded() {
        if (this.getAcInfo() == null) {
            return;
        }
        this.func_70105_a(this.getAcInfo().bodyWidth, this.getAcInfo().bodyHeight);
    }
    
    public void writeSpawnData(final ByteBuf buffer) {
        if (this.getAcInfo() != null) {
            buffer.writeFloat(this.getAcInfo().bodyHeight);
            buffer.writeFloat(2.0f);
        }
        else {
            buffer.writeFloat(this.field_70131_O);
            buffer.writeFloat(this.field_70130_N);
        }
    }
    
    public void readSpawnData(final ByteBuf additionalData) {
        try {
            final float height = additionalData.readFloat();
            final float width = additionalData.readFloat();
            this.func_70105_a(width, height);
        }
        catch (Exception e) {
            MCH_Lib.Log(this, "readSpawnData error!", new Object[0]);
            e.printStackTrace();
        }
    }
    
    @Override
    protected void func_70037_a(final NBTTagCompound nbt) {
        this.setDespawnCount(nbt.func_74762_e("AcDespawnCount"));
        this.setTextureName(nbt.func_74779_i("TextureName"));
        this.setCommonUniqueId(nbt.func_74779_i("AircraftUniqueId"));
        this.setRotRoll(nbt.func_74760_g("AcRoll"));
        this.prevRotationRoll = this.getRotRoll();
        final float func_74760_g = nbt.func_74760_g("AcLastRYaw");
        this.lastRiderYaw = func_74760_g;
        this.prevLastRiderYaw = func_74760_g;
        final float func_74760_g2 = nbt.func_74760_g("AcLastRPitch");
        this.lastRiderPitch = func_74760_g2;
        this.prevLastRiderPitch = func_74760_g2;
        this.setPartStatus(nbt.func_74762_e("PartStatus"));
        this.setTypeName(nbt.func_74779_i("TypeName"));
        super.func_70037_a(nbt);
        this.getGuiInventory().readEntityFromNBT(nbt);
        this.setCommandForce(nbt.func_74779_i("AcCommand"));
        this.setFuel(nbt.func_74762_e("AcFuel"));
        final int[] wa_list = nbt.func_74759_k("AcWeaponsAmmo");
        for (int i = 0; i < wa_list.length; ++i) {
            this.getWeapon(i).setRestAllAmmoNum(wa_list[i]);
            this.getWeapon(i).reloadMag();
        }
        if (this.getDespawnCount() > 0) {
            this.setDamageTaken(this.getMaxHP());
        }
        else if (nbt.func_74764_b("AcDamage")) {
            this.setDamageTaken(nbt.func_74762_e("AcDamage"));
        }
        if (this.haveSearchLight() && nbt.func_74764_b("SearchLight")) {
            this.setSearchLight(nbt.func_74767_n("SearchLight"));
        }
        this.dismountedUserCtrl = nbt.func_74767_n("AcDismounted");
    }
    
    @Override
    protected void func_70014_b(final NBTTagCompound nbt) {
        nbt.func_74778_a("TextureName", this.getTextureName());
        nbt.func_74778_a("AircraftUniqueId", this.getCommonUniqueId());
        nbt.func_74778_a("TypeName", this.getTypeName());
        nbt.func_74768_a("PartStatus", this.getPartStatus() & this.getLastPartStatusMask());
        nbt.func_74768_a("AcFuel", this.getFuel());
        nbt.func_74768_a("AcDespawnCount", this.getDespawnCount());
        nbt.func_74776_a("AcRoll", this.getRotRoll());
        nbt.func_74757_a("SearchLight", this.isSearchLightON());
        nbt.func_74776_a("AcLastRYaw", this.getLastRiderYaw());
        nbt.func_74776_a("AcLastRPitch", this.getLastRiderPitch());
        nbt.func_74778_a("AcCommand", this.getCommand());
        super.func_70014_b(nbt);
        this.getGuiInventory().writeEntityToNBT(nbt);
        final int[] wa_list = new int[this.getWeaponNum()];
        for (int i = 0; i < wa_list.length; ++i) {
            wa_list[i] = this.getWeapon(i).getRestAllAmmoNum() + this.getWeapon(i).getAmmoNum();
        }
        nbt.func_74782_a("AcWeaponsAmmo", (NBTBase)W_NBTTag.newTagIntArray("AcWeaponsAmmo", wa_list));
        nbt.func_74768_a("AcDamage", this.getDamageTaken());
        nbt.func_74757_a("AcDismounted", this.dismountedUserCtrl);
    }
    
    public boolean func_70097_a(final DamageSource damageSource, final float org_damage) {
        final float damageFactor = this.lastBBDamageFactor;
        this.lastBBDamageFactor = 1.0f;
        if (this.func_85032_ar()) {
            return false;
        }
        if (this.field_70128_L) {
            return false;
        }
        if (this.timeSinceHit > 0) {
            return false;
        }
        final String dmt = damageSource.func_76355_l();
        if (dmt.equalsIgnoreCase("inFire")) {
            return false;
        }
        if (dmt.equalsIgnoreCase("cactus")) {
            return false;
        }
        if (this.field_70170_p.field_72995_K) {
            return true;
        }
        final MCH_Config config = MCH_MOD.config;
        float damage = MCH_Config.applyDamageByExternal(this, damageSource, org_damage);
        if (!MCH_Multiplay.canAttackEntity(damageSource, this)) {
            return false;
        }
        if (dmt.equalsIgnoreCase("lava")) {
            damage *= this.field_70146_Z.nextInt(8) + 2;
            this.timeSinceHit = 2;
        }
        if (dmt.startsWith("explosion")) {
            this.timeSinceHit = 1;
        }
        else if (this.isMountedEntity(damageSource.func_76346_g())) {
            return false;
        }
        if (dmt.equalsIgnoreCase("onFire")) {
            this.timeSinceHit = 10;
        }
        boolean isCreative = false;
        boolean isSneaking = false;
        final Entity entity = damageSource.func_76346_g();
        boolean isDamegeSourcePlayer = false;
        boolean playDamageSound = false;
        if (entity instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)entity;
            isCreative = player.field_71075_bZ.field_75098_d;
            isSneaking = player.func_70093_af();
            if (dmt.equalsIgnoreCase("player")) {
                if (isCreative) {
                    isDamegeSourcePlayer = true;
                }
                else {
                    final MCH_Config config2 = MCH_MOD.config;
                    if (!MCH_Config.PreventingBroken.prmBool) {
                        final MCH_Config config3 = MCH_MOD.config;
                        if (MCH_Config.BreakableOnlyPickaxe.prmBool) {
                            if (player.func_71045_bC() != null && player.func_71045_bC().func_77973_b() instanceof ItemPickaxe) {
                                isDamegeSourcePlayer = true;
                            }
                        }
                        else {
                            isDamegeSourcePlayer = !this.isRidePlayer();
                        }
                    }
                }
            }
            W_WorldFunc.MOD_playSoundAtEntity(this, "hit", 1.0f, 1.0f);
        }
        else {
            playDamageSound = true;
        }
        if (!this.isDestroyed()) {
            if (!isDamegeSourcePlayer) {
                final MCH_AircraftInfo acInfo = this.getAcInfo();
                if (acInfo != null && !dmt.equalsIgnoreCase("lava") && !dmt.equalsIgnoreCase("onFire")) {
                    if (damage > acInfo.armorMaxDamage) {
                        damage = acInfo.armorMaxDamage;
                    }
                    if (damageFactor <= 1.0f) {
                        damage *= damageFactor;
                    }
                    damage *= acInfo.armorDamageFactor;
                    damage -= acInfo.armorMinDamage;
                    if (damage <= 0.0f) {
                        MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityAircraft.attackEntityFrom:no damage=%.1f -> %.1f(factor=%.2f):%s", org_damage, damage, damageFactor, dmt);
                        return false;
                    }
                    if (damageFactor > 1.0f) {
                        damage *= damageFactor;
                    }
                }
                MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityAircraft.attackEntityFrom:damage=%.1f(factor=%.2f):%s", damage, damageFactor, dmt);
                this.setDamageTaken(this.getDamageTaken() + (int)damage);
            }
            this.func_70018_K();
            if (this.getDamageTaken() >= this.getMaxHP() || isDamegeSourcePlayer) {
                if (!isDamegeSourcePlayer) {
                    this.setDamageTaken(this.getMaxHP());
                    this.destroyAircraft();
                    this.timeSinceHit = 20;
                    String cmd = this.getCommand().trim();
                    if (cmd.startsWith("/")) {
                        cmd = cmd.substring(1);
                    }
                    if (!cmd.isEmpty()) {
                        MCH_DummyCommandSender.execCommand(cmd);
                    }
                    if (dmt.equalsIgnoreCase("inWall")) {
                        this.explosionByCrash(0.0);
                        this.damageSinceDestroyed = this.getMaxHP();
                    }
                    else {
                        MCH_Explosion.newExplosion(this.field_70170_p, null, entity, this.field_70165_t, this.field_70163_u, this.field_70161_v, 2.0f, 2.0f, true, true, true, true, 5);
                    }
                }
                else {
                    if (this.getAcInfo() != null && this.getAcInfo().getItem() != null) {
                        if (isCreative) {
                            final MCH_Config config4 = MCH_MOD.config;
                            if (MCH_Config.DropItemInCreativeMode.prmBool && !isSneaking) {
                                this.dropItemWithOffset(this.getAcInfo().getItem(), 1, 0.0f);
                            }
                            final MCH_Config config5 = MCH_MOD.config;
                            if (!MCH_Config.DropItemInCreativeMode.prmBool && isSneaking) {
                                this.dropItemWithOffset(this.getAcInfo().getItem(), 1, 0.0f);
                            }
                        }
                        else {
                            this.dropItemWithOffset(this.getAcInfo().getItem(), 1, 0.0f);
                        }
                    }
                    this.setDead(true);
                }
            }
        }
        else if (isDamegeSourcePlayer && isCreative) {
            this.setDead(true);
        }
        if (playDamageSound) {
            W_WorldFunc.MOD_playSoundAtEntity(this, "helidmg", 1.0f, 0.9f + this.field_70146_Z.nextFloat() * 0.1f);
        }
        return true;
    }
    
    public boolean isExploded() {
        return this.isDestroyed() && this.damageSinceDestroyed > this.getMaxHP() / 10 + 1;
    }
    
    public void destruct() {
        if (this.getRiddenByEntity() != null) {
            this.getRiddenByEntity().func_70078_a((Entity)null);
        }
        this.setDead(true);
    }
    
    public EntityItem func_70099_a(final ItemStack is, final float par2) {
        if (is.field_77994_a == 0) {
            return null;
        }
        this.setAcDataToItem(is);
        return super.func_70099_a(is, par2);
    }
    
    public void setAcDataToItem(final ItemStack is) {
        if (!is.func_77942_o()) {
            is.func_77982_d(new NBTTagCompound());
        }
        final NBTTagCompound nbt = is.func_77978_p();
        nbt.func_74778_a("MCH_Command", this.getCommand());
        final MCH_Config config = MCH_MOD.config;
        if (MCH_Config.ItemFuel.prmBool) {
            nbt.func_74768_a("MCH_Fuel", this.getFuel());
        }
        final MCH_Config config2 = MCH_MOD.config;
        if (MCH_Config.ItemDamage.prmBool) {
            is.func_77964_b(this.getDamageTaken());
        }
    }
    
    public void getAcDataFromItem(final ItemStack is) {
        if (!is.func_77942_o()) {
            return;
        }
        final NBTTagCompound nbt = is.func_77978_p();
        this.setCommandForce(nbt.func_74779_i("MCH_Command"));
        final MCH_Config config = MCH_MOD.config;
        if (MCH_Config.ItemFuel.prmBool) {
            this.setFuel(nbt.func_74762_e("MCH_Fuel"));
        }
        final MCH_Config config2 = MCH_MOD.config;
        if (MCH_Config.ItemDamage.prmBool) {
            this.setDamageTaken(is.func_77960_j());
        }
    }
    
    @Override
    public boolean func_70300_a(final EntityPlayer player) {
        if (this.isUAV()) {
            return super.func_70300_a(player);
        }
        if (this.field_70128_L) {
            return false;
        }
        if (this.getSeatIdByEntity((Entity)player) >= 0) {
            return player.func_70068_e((Entity)this) <= 4096.0;
        }
        return player.func_70068_e((Entity)this) <= 64.0;
    }
    
    public void func_70108_f(final Entity par1Entity) {
    }
    
    public void func_70024_g(final double par1, final double par3, final double par5) {
    }
    
    public void func_70016_h(final double par1, final double par3, final double par5) {
        this.field_70159_w = par1;
        this.velocityX = par1;
        this.field_70181_x = par3;
        this.velocityY = par3;
        this.field_70179_y = par5;
        this.velocityZ = par5;
    }
    
    public void onFirstUpdate() {
        if (!this.field_70170_p.field_72995_K) {
            final int bit = 3;
            final MCH_Config config = MCH_MOD.config;
            this.setCommonStatus(bit, MCH_Config.InfinityAmmo.prmBool);
            final int bit2 = 4;
            final MCH_Config config2 = MCH_MOD.config;
            this.setCommonStatus(bit2, MCH_Config.InfinityFuel.prmBool);
        }
    }
    
    public void onRidePilotFirstUpdate() {
        if (this.field_70170_p.field_72995_K && W_Lib.isClientPlayer(this.getRiddenByEntity())) {
            this.updateClientSettings(0);
        }
        final Entity pilot = this.getRiddenByEntity();
        if (pilot != null) {
            pilot.field_70177_z = this.getLastRiderYaw();
            pilot.field_70125_A = this.getLastRiderPitch();
        }
        this.keepOnRideRotation = false;
        if (this.getAcInfo() != null) {
            this.switchFreeLookModeClient(this.getAcInfo().defaultFreelook);
        }
    }
    
    public double getCurrentThrottle() {
        return this.currentThrottle;
    }
    
    public void setCurrentThrottle(final double throttle) {
        this.currentThrottle = throttle;
    }
    
    public void addCurrentThrottle(final double throttle) {
        this.setCurrentThrottle(this.getCurrentThrottle() + throttle);
    }
    
    public double getPrevCurrentThrottle() {
        return this.prevCurrentThrottle;
    }
    
    public boolean canMouseRot() {
        return !this.field_70128_L && this.getRiddenByEntity() != null && !this.isDestroyed();
    }
    
    public boolean canUpdateYaw(final Entity player) {
        return this.getRidingEntity() == null && this.getCountOnUpdate() >= 30 && MCH_Lib.getBlockIdY(this, 3, -2) == 0;
    }
    
    public boolean canUpdatePitch(final Entity player) {
        return this.getCountOnUpdate() >= 30 && MCH_Lib.getBlockIdY(this, 3, -2) == 0;
    }
    
    public boolean canUpdateRoll(final Entity player) {
        return this.getRidingEntity() == null && this.getCountOnUpdate() >= 30 && MCH_Lib.getBlockIdY(this, 3, -2) == 0;
    }
    
    public boolean isOverridePlayerYaw() {
        return !this.isFreeLookMode();
    }
    
    public boolean isOverridePlayerPitch() {
        return !this.isFreeLookMode();
    }
    
    public double getAddRotationYawLimit() {
        return (this.getAcInfo() != null) ? (40.0 * this.getAcInfo().mobilityYaw) : 40.0;
    }
    
    public double getAddRotationPitchLimit() {
        return (this.getAcInfo() != null) ? (40.0 * this.getAcInfo().mobilityPitch) : 40.0;
    }
    
    public double getAddRotationRollLimit() {
        return (this.getAcInfo() != null) ? (40.0 * this.getAcInfo().mobilityRoll) : 40.0;
    }
    
    public float getYawFactor() {
        return 1.0f;
    }
    
    public float getPitchFactor() {
        return 1.0f;
    }
    
    public float getRollFactor() {
        return 1.0f;
    }
    
    public abstract void onUpdateAngles(final float p0);
    
    public float getControlRotYaw(final float mouseX, final float mouseY, final float tick) {
        return 0.0f;
    }
    
    public float getControlRotPitch(final float mouseX, final float mouseY, final float tick) {
        return 0.0f;
    }
    
    public float getControlRotRoll(final float mouseX, final float mouseY, final float tick) {
        return 0.0f;
    }
    
    public void setAngles(final Entity player, final boolean fixRot, final float fixYaw, final float fixPitch, final float deltaX, final float deltaY, float x, float y, float partialTicks) {
        if (partialTicks < 0.03f) {
            partialTicks = 0.4f;
        }
        if (partialTicks > 0.9f) {
            partialTicks = 0.6f;
        }
        this.lowPassPartialTicks.put(partialTicks);
        partialTicks = this.lowPassPartialTicks.getAvg();
        final float ac_pitch = this.getRotPitch();
        final float ac_yaw = this.getRotYaw();
        final float ac_roll = this.getRotRoll();
        if (this.isFreeLookMode()) {
            y = (x = 0.0f);
        }
        float yaw = 0.0f;
        float pitch = 0.0f;
        float roll = 0.0f;
        if (this.canUpdateYaw(player)) {
            final double limit = this.getAddRotationYawLimit();
            yaw = this.getControlRotYaw(x, y, partialTicks);
            if (yaw < -limit) {
                yaw = (float)(-limit);
            }
            if (yaw > limit) {
                yaw = (float)limit;
            }
            yaw = (float)(yaw * this.getYawFactor() * 0.06 * partialTicks);
        }
        if (this.canUpdatePitch(player)) {
            final double limit = this.getAddRotationPitchLimit();
            pitch = this.getControlRotPitch(x, y, partialTicks);
            if (pitch < -limit) {
                pitch = (float)(-limit);
            }
            if (pitch > limit) {
                pitch = (float)limit;
            }
            pitch = (float)(-pitch * this.getPitchFactor() * 0.06 * partialTicks);
        }
        if (this.canUpdateRoll(player)) {
            final double limit = this.getAddRotationRollLimit();
            roll = this.getControlRotRoll(x, y, partialTicks);
            if (roll < -limit) {
                roll = (float)(-limit);
            }
            if (roll > limit) {
                roll = (float)limit;
            }
            roll = roll * this.getRollFactor() * 0.06f * partialTicks;
        }
        final MCH_Math.FMatrix m_add = MCH_Math.newMatrix();
        MCH_Math.MatTurnZ(m_add, roll / 180.0f * 3.1415927f);
        MCH_Math.MatTurnX(m_add, pitch / 180.0f * 3.1415927f);
        MCH_Math.MatTurnY(m_add, yaw / 180.0f * 3.1415927f);
        MCH_Math.MatTurnZ(m_add, (float)(this.getRotRoll() / 180.0f * 3.141592653589793));
        MCH_Math.MatTurnX(m_add, (float)(this.getRotPitch() / 180.0f * 3.141592653589793));
        MCH_Math.MatTurnY(m_add, (float)(this.getRotYaw() / 180.0f * 3.141592653589793));
        final MCH_Math.FVector3D v = MCH_Math.MatrixToEuler(m_add);
        if (this.getAcInfo().limitRotation) {
            v.x = MCH_Lib.RNG(v.x, this.getAcInfo().minRotationPitch, this.getAcInfo().maxRotationPitch);
            v.z = MCH_Lib.RNG(v.z, this.getAcInfo().minRotationRoll, this.getAcInfo().maxRotationRoll);
        }
        if (v.z > 180.0f) {
            final MCH_Math.FVector3D fVector3D = v;
            fVector3D.z -= 360.0f;
        }
        if (v.z < -180.0f) {
            final MCH_Math.FVector3D fVector3D2 = v;
            fVector3D2.z += 360.0f;
        }
        this.setRotYaw(v.y);
        this.setRotPitch(v.x);
        this.setRotRoll(v.z);
        this.onUpdateAngles(partialTicks);
        if (this.getAcInfo().limitRotation) {
            v.x = MCH_Lib.RNG(this.getRotPitch(), this.getAcInfo().minRotationPitch, this.getAcInfo().maxRotationPitch);
            v.z = MCH_Lib.RNG(this.getRotRoll(), this.getAcInfo().minRotationRoll, this.getAcInfo().maxRotationRoll);
            this.setRotPitch(v.x);
            this.setRotRoll(v.z);
        }
        final float RV = 180.0f;
        if (MathHelper.func_76135_e(this.getRotPitch()) > 90.0f) {
            MCH_Lib.DbgLog(true, "MCH_EntityAircraft.setAngles Error:Pitch=%.1f", this.getRotPitch());
        }
        if (this.getRotRoll() > 180.0f) {
            this.setRotRoll(this.getRotRoll() - 360.0f);
        }
        if (this.getRotRoll() < -180.0f) {
            this.setRotRoll(this.getRotRoll() + 360.0f);
        }
        this.prevRotationRoll = this.getRotRoll();
        this.field_70127_C = this.getRotPitch();
        if (this.getRidingEntity() == null) {
            this.field_70126_B = this.getRotYaw();
        }
        if (this.isOverridePlayerYaw() || fixRot) {
            if (this.getRidingEntity() == null) {
                player.field_70126_B = this.getRotYaw() + (fixRot ? fixYaw : 0.0f);
            }
            else {
                if (this.getRotYaw() - player.field_70177_z > 180.0f) {
                    player.field_70126_B += 360.0f;
                }
                if (this.getRotYaw() - player.field_70177_z < -180.0f) {
                    player.field_70126_B -= 360.0f;
                }
            }
            player.field_70177_z = this.getRotYaw() + (fixRot ? fixYaw : 0.0f);
        }
        else {
            player.func_70082_c(deltaX, 0.0f);
        }
        if (this.isOverridePlayerPitch() || fixRot) {
            player.field_70127_C = this.getRotPitch() + (fixRot ? fixPitch : 0.0f);
            player.field_70125_A = this.getRotPitch() + (fixRot ? fixPitch : 0.0f);
        }
        else {
            player.func_70082_c(0.0f, deltaY);
        }
        if ((this.getRidingEntity() == null && ac_yaw != this.getRotYaw()) || ac_pitch != this.getRotPitch() || ac_roll != this.getRotRoll()) {
            this.aircraftRotChanged = true;
        }
    }
    
    public boolean canSwitchSearchLight(final Entity entity) {
        return this.haveSearchLight() && this.getSeatIdByEntity(entity) <= 1;
    }
    
    public boolean isSearchLightON() {
        return this.getCommonStatus(6);
    }
    
    public void setSearchLight(final boolean onoff) {
        this.setCommonStatus(6, onoff);
    }
    
    public boolean haveSearchLight() {
        return this.getAcInfo() != null && this.getAcInfo().searchLights.size() > 0;
    }
    
    public float getSearchLightValue(final Entity entity) {
        if (this.haveSearchLight() && this.isSearchLightON()) {
            for (final MCH_AircraftInfo.SearchLight sl : this.getAcInfo().searchLights) {
                final Vec3 pos = this.getTransformedPosition(sl.pos);
                final double dist = entity.func_70092_e(pos.field_72450_a, pos.field_72448_b, pos.field_72449_c);
                if (dist > 2.0 && dist < sl.height * sl.height + 20.0f) {
                    final double cx = entity.field_70165_t - pos.field_72450_a;
                    final double cy = entity.field_70163_u - pos.field_72448_b;
                    final double cz = entity.field_70161_v - pos.field_72449_c;
                    double h = 0.0;
                    double v = 0.0;
                    if (!sl.fixDir) {
                        final Vec3 vv = MCH_Lib.RotVec3(0.0, 0.0, 1.0, -this.lastSearchLightYaw + sl.yaw, -this.lastSearchLightPitch + sl.pitch, -this.getRotRoll());
                        h = MCH_Lib.getPosAngle(vv.field_72450_a, vv.field_72449_c, cx, cz);
                        v = Math.atan2(cy, Math.sqrt(cx * cx + cz * cz)) * 180.0 / 3.141592653589793;
                        v = Math.abs(v + this.lastSearchLightPitch + sl.pitch);
                    }
                    else {
                        float stRot = 0.0f;
                        if (sl.steering) {
                            stRot = this.rotYawWheel * sl.stRot;
                        }
                        final Vec3 vv2 = MCH_Lib.RotVec3(0.0, 0.0, 1.0, -this.getRotYaw() + sl.yaw + stRot, -this.getRotPitch() + sl.pitch, -this.getRotRoll());
                        h = MCH_Lib.getPosAngle(vv2.field_72450_a, vv2.field_72449_c, cx, cz);
                        v = Math.atan2(cy, Math.sqrt(cx * cx + cz * cz)) * 180.0 / 3.141592653589793;
                        v = Math.abs(v + this.getRotPitch() + sl.pitch);
                    }
                    final float angle = sl.angle * 3.0f;
                    if (h < angle && v < angle) {
                        float value = 0.0f;
                        if (h + v < angle) {
                            value = (float)(1440.0 * (1.0 - (h + v) / angle));
                        }
                        return (value <= 240.0f) ? value : 240.0f;
                    }
                    continue;
                }
            }
        }
        return 0.0f;
    }
    
    public abstract void onUpdateAircraft();
    
    public void func_70071_h_() {
        if (this.getCountOnUpdate() < 2) {
            this.prevPosition.clear(Vec3.func_72443_a(this.field_70165_t, this.field_70163_u, this.field_70161_v));
        }
        this.prevCurrentThrottle = this.getCurrentThrottle();
        this.lastBBDamageFactor = 1.0f;
        this.updateControl();
        this.checkServerNoMove();
        this.onUpdate_RidingEntity();
        final Iterator<UnmountReserve> itr = this.listUnmountReserve.iterator();
        while (itr.hasNext()) {
            final UnmountReserve ur = itr.next();
            if (ur.entity != null && !ur.entity.field_70128_L) {
                ur.entity.func_70107_b(ur.posX, ur.posY, ur.posZ);
                ur.entity.field_70143_R = this.field_70143_R;
            }
            if (ur.cnt > 0) {
                final UnmountReserve unmountReserve = ur;
                --unmountReserve.cnt;
            }
            if (ur.cnt == 0) {
                itr.remove();
            }
        }
        if (this.isDestroyed() && this.getCountOnUpdate() % 20 == 0) {
            for (int sid = 0; sid < this.getSeatNum() + 1; ++sid) {
                final Entity entity = this.getEntityBySeatId(sid);
                if (entity != null) {
                    if (sid != 0 || !this.isUAV()) {
                        final MCH_Config config = MCH_MOD.config;
                        if (MCH_Config.applyDamageVsEntity(entity, DamageSource.field_76372_a, 1.0f) > 0.0f) {
                            entity.func_70015_d(5);
                        }
                    }
                }
            }
        }
        if ((this.aircraftRotChanged || this.aircraftRollRev) && this.field_70170_p.field_72995_K && this.getRiddenByEntity() != null) {
            MCH_PacketIndRotation.send(this);
            this.aircraftRotChanged = false;
            this.aircraftRollRev = false;
        }
        if (!this.field_70170_p.field_72995_K && (int)this.prevRotationRoll != (int)this.getRotRoll()) {
            final float roll = MathHelper.func_76142_g(this.getRotRoll());
            this.func_70096_w().func_75692_b(26, (Object)new Short((short)roll));
        }
        this.prevRotationRoll = this.getRotRoll();
        if (!this.field_70170_p.field_72995_K && this.isTargetDrone() && !this.isDestroyed() && this.getCountOnUpdate() > 20 && !this.canUseFuel()) {
            this.setDamageTaken(this.getMaxHP());
            this.destroyAircraft();
            MCH_Explosion.newExplosion(this.field_70170_p, null, null, this.field_70165_t, this.field_70163_u, this.field_70161_v, 2.0f, 2.0f, true, true, true, true, 5);
        }
        if (this.field_70170_p.field_72995_K && this.getAcInfo() != null && this.getHP() <= 0 && this.getDespawnCount() <= 0) {
            this.destroyAircraft();
        }
        if (!this.field_70170_p.field_72995_K && this.getDespawnCount() > 0) {
            this.setDespawnCount(this.getDespawnCount() - 1);
            if (this.getDespawnCount() <= 1) {
                this.setDead(true);
            }
        }
        super.func_70071_h_();
        if (this.func_70021_al() != null) {
            for (final Entity e : this.func_70021_al()) {
                if (e != null) {
                    e.func_70071_h_();
                }
            }
        }
        this.updateNoCollisionEntities();
        this.updateUAV();
        this.supplyFuel();
        this.supplyAmmoToOtherAircraft();
        this.updateFuel();
        this.repairOtherAircraft();
        if (this.modeSwitchCooldown > 0) {
            --this.modeSwitchCooldown;
        }
        if (this.lastRiddenByEntity == null && this.getRiddenByEntity() != null) {
            this.onRidePilotFirstUpdate();
        }
        if (this.countOnUpdate == 0) {
            this.onFirstUpdate();
        }
        ++this.countOnUpdate;
        if (this.countOnUpdate >= 1000000) {
            this.countOnUpdate = 1;
        }
        if (this.field_70170_p.field_72995_K) {
            this.commonStatus = this.func_70096_w().func_75679_c(23);
        }
        this.field_70143_R = 0.0f;
        if (this.field_70153_n != null) {
            this.field_70153_n.field_70143_R = 0.0f;
        }
        if (this.missileDetector != null) {
            this.missileDetector.update();
        }
        if (this.soundUpdater != null) {
            this.soundUpdater.update();
        }
        if (this.getTowChainEntity() != null && this.getTowChainEntity().field_70128_L) {
            this.setTowChainEntity(null);
        }
        this.updateSupplyAmmo();
        this.autoRepair();
        final int ft = this.getFlareTick();
        this.flareDv.update();
        if (!this.field_70170_p.field_72995_K && this.getFlareTick() == 0 && ft != 0) {
            this.setCommonStatus(0, false);
        }
        final Entity e2 = this.getRiddenByEntity();
        if (e2 != null && !e2.field_70128_L && !this.isDestroyed()) {
            this.lastRiderYaw = e2.field_70177_z;
            this.prevLastRiderYaw = e2.field_70126_B;
            this.lastRiderPitch = e2.field_70125_A;
            this.prevLastRiderPitch = e2.field_70127_C;
        }
        else if (this.getTowedChainEntity() != null || this.field_70154_o != null) {
            this.lastRiderYaw = this.field_70177_z;
            this.prevLastRiderYaw = this.field_70126_B;
            this.lastRiderPitch = this.field_70125_A;
            this.prevLastRiderPitch = this.field_70127_C;
        }
        this.updatePartCameraRotate();
        this.updatePartWheel();
        this.updatePartCrawlerTrack();
        this.updatePartLightHatch();
        this.regenerationMob();
        if (this.getRiddenByEntity() == null && this.lastRiddenByEntity != null) {
            this.unmountEntity();
        }
        this.updateExtraBoundingBox();
        final boolean prevOnGround = this.field_70122_E;
        final double prevMotionY = this.field_70181_x;
        this.onUpdateAircraft();
        if (this.getAcInfo() != null) {
            this.updateParts(this.getPartStatus());
        }
        if (this.recoilCount > 0) {
            --this.recoilCount;
        }
        if (!W_Entity.isEqual(MCH_MOD.proxy.getClientPlayer(), this.getRiddenByEntity())) {
            this.updateRecoil(1.0f);
        }
        if (!this.field_70170_p.field_72995_K && this.isDestroyed() && !this.isExploded() && !prevOnGround && this.field_70122_E && prevMotionY < -0.2) {
            this.explosionByCrash(prevMotionY);
            this.damageSinceDestroyed = this.getMaxHP();
        }
        this.onUpdate_PartRotation();
        this.onUpdate_ParticleSmoke();
        this.updateSeatsPosition(this.field_70165_t, this.field_70163_u, this.field_70161_v, false);
        this.updateHitBoxPosition();
        this.onUpdate_CollisionGroundDamage();
        this.onUpdate_UnmountCrew();
        this.onUpdate_Repelling();
        this.checkRideRack();
        if (this.lastRidingEntity == null && this.getRidingEntity() != null) {
            this.onRideEntity(this.getRidingEntity());
        }
        this.lastRiddenByEntity = this.getRiddenByEntity();
        this.lastRidingEntity = this.getRidingEntity();
        this.prevPosition.put(Vec3.func_72443_a(this.field_70165_t, this.field_70163_u, this.field_70161_v));
    }
    
    private void updateNoCollisionEntities() {
        if (this.field_70170_p.field_72995_K) {
            return;
        }
        if (this.getCountOnUpdate() % 10 != 0) {
            return;
        }
        for (int i = 0; i < 1 + this.getSeatNum(); ++i) {
            final Entity e = this.getEntityBySeatId(i);
            if (e != null) {
                this.noCollisionEntities.put(e, 8);
            }
        }
        if (this.getTowChainEntity() != null && this.getTowChainEntity().towedEntity != null) {
            this.noCollisionEntities.put(this.getTowChainEntity().towedEntity, 60);
        }
        if (this.getTowedChainEntity() != null && this.getTowedChainEntity().towEntity != null) {
            this.noCollisionEntities.put(this.getTowedChainEntity().towEntity, 60);
        }
        if (this.field_70154_o instanceof MCH_EntitySeat) {
            final MCH_EntityAircraft ac = ((MCH_EntitySeat)this.field_70154_o).getParent();
            if (ac != null) {
                this.noCollisionEntities.put(ac, 60);
            }
        }
        else if (this.field_70154_o != null) {
            this.noCollisionEntities.put(this.field_70154_o, 60);
        }
        for (final Entity key : this.noCollisionEntities.keySet()) {
            this.noCollisionEntities.put(key, this.noCollisionEntities.get(key) - 1);
        }
        final Iterator<Integer> key2 = this.noCollisionEntities.values().iterator();
        while (key2.hasNext()) {
            if (key2.next() <= 0) {
                key2.remove();
            }
        }
    }
    
    public void updateControl() {
        if (!this.field_70170_p.field_72995_K) {
            this.setCommonStatus(7, this.moveLeft);
            this.setCommonStatus(8, this.moveRight);
            this.setCommonStatus(9, this.throttleUp);
            this.setCommonStatus(10, this.throttleDown);
        }
        else if (MCH_MOD.proxy.getClientPlayer() != this.getRiddenByEntity()) {
            this.moveLeft = this.getCommonStatus(7);
            this.moveRight = this.getCommonStatus(8);
            this.throttleUp = this.getCommonStatus(9);
            this.throttleDown = this.getCommonStatus(10);
        }
    }
    
    public void updateRecoil(final float partialTicks) {
        if (this.recoilCount > 0 && this.recoilCount >= 12) {
            final float pitch = MathHelper.func_76134_b((float)((this.recoilYaw - this.getRotRoll()) * 3.141592653589793 / 180.0));
            final float roll = MathHelper.func_76126_a((float)((this.recoilYaw - this.getRotRoll()) * 3.141592653589793 / 180.0));
            final float recoil = MathHelper.func_76134_b((float)(this.recoilCount * 6 * 3.141592653589793 / 180.0)) * this.recoilValue;
            this.setRotPitch(this.getRotPitch() + recoil * pitch * partialTicks);
            this.setRotRoll(this.getRotRoll() + recoil * roll * partialTicks);
        }
    }
    
    private void updatePartLightHatch() {
        this.prevRotLightHatch = this.rotLightHatch;
        if (this.isSearchLightON()) {
            this.rotLightHatch += 0.5;
        }
        else {
            this.rotLightHatch -= 0.5;
        }
        if (this.rotLightHatch > 1.0f) {
            this.rotLightHatch = 1.0f;
        }
        if (this.rotLightHatch < 0.0f) {
            this.rotLightHatch = 0.0f;
        }
    }
    
    public void updateExtraBoundingBox() {
        for (final MCH_BoundingBox bb : this.extraBoundingBox) {
            bb.updatePosition(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.getRotYaw(), this.getRotPitch(), this.getRotRoll());
        }
    }
    
    public void updatePartWheel() {
        if (!this.field_70170_p.field_72995_K) {
            return;
        }
        if (this.getAcInfo() == null) {
            return;
        }
        this.prevRotWheel = this.rotWheel;
        this.prevRotYawWheel = this.rotYawWheel;
        final float LEN = 1.0f;
        final float MIN = 0.0f;
        double throttle = this.getCurrentThrottle();
        double pivotTurnThrottle = this.getAcInfo().pivotTurnThrottle;
        if (pivotTurnThrottle <= 0.0) {
            pivotTurnThrottle = 1.0;
        }
        else {
            pivotTurnThrottle *= 0.10000000149011612;
        }
        final boolean localMoveLeft = this.moveLeft;
        final boolean localMoveRight = this.moveRight;
        if (this.getAcInfo().enableBack && this.throttleBack > 0.01 && throttle <= 0.0) {
            throttle = -this.throttleBack * 15.0f;
        }
        if (localMoveLeft && !localMoveRight) {
            this.rotYawWheel += 0.1f;
            if (this.rotYawWheel > 1.0f) {
                this.rotYawWheel = 1.0f;
            }
        }
        else if (!localMoveLeft && localMoveRight) {
            this.rotYawWheel -= 0.1f;
            if (this.rotYawWheel < -1.0f) {
                this.rotYawWheel = -1.0f;
            }
        }
        else {
            this.rotYawWheel *= 0.9f;
        }
        this.rotWheel += (float)(throttle * this.getAcInfo().partWheelRot);
        if (this.rotWheel >= 360.0f) {
            this.rotWheel -= 360.0f;
            this.prevRotWheel -= 360.0f;
        }
        else if (this.rotWheel < 0.0f) {
            this.rotWheel += 360.0f;
            this.prevRotWheel += 360.0f;
        }
    }
    
    public void updatePartCrawlerTrack() {
        if (!this.field_70170_p.field_72995_K) {
            return;
        }
        if (this.getAcInfo() == null) {
            return;
        }
        this.prevRotTrackRoller[0] = this.rotTrackRoller[0];
        this.prevRotTrackRoller[1] = this.rotTrackRoller[1];
        this.prevRotCrawlerTrack[0] = this.rotCrawlerTrack[0];
        this.prevRotCrawlerTrack[1] = this.rotCrawlerTrack[1];
        final float LEN = 1.0f;
        final float MIN = 0.0f;
        double throttle = this.getCurrentThrottle();
        double pivotTurnThrottle = this.getAcInfo().pivotTurnThrottle;
        if (pivotTurnThrottle <= 0.0) {
            pivotTurnThrottle = 1.0;
        }
        else {
            pivotTurnThrottle *= 0.10000000149011612;
        }
        boolean localMoveLeft = this.moveLeft;
        boolean localMoveRight = this.moveRight;
        int dir = 1;
        if (this.getAcInfo().enableBack && this.throttleBack > 0.0f && throttle <= 0.0) {
            throttle = -this.throttleBack * 5.0f;
            if (localMoveLeft != localMoveRight) {
                final boolean tmp = localMoveLeft;
                localMoveLeft = localMoveRight;
                localMoveRight = tmp;
                dir = -1;
            }
        }
        if (localMoveLeft && !localMoveRight) {
            throttle = 0.2 * dir;
            final float[] throttleCrawlerTrack = this.throttleCrawlerTrack;
            final int n = 0;
            throttleCrawlerTrack[n] += (float)throttle;
            final float[] throttleCrawlerTrack2 = this.throttleCrawlerTrack;
            final int n2 = 1;
            throttleCrawlerTrack2[n2] -= (float)(pivotTurnThrottle * throttle);
        }
        else if (!localMoveLeft && localMoveRight) {
            throttle = 0.2 * dir;
            final float[] throttleCrawlerTrack3 = this.throttleCrawlerTrack;
            final int n3 = 0;
            throttleCrawlerTrack3[n3] -= (float)(pivotTurnThrottle * throttle);
            final float[] throttleCrawlerTrack4 = this.throttleCrawlerTrack;
            final int n4 = 1;
            throttleCrawlerTrack4[n4] += (float)throttle;
        }
        else {
            if (throttle > 0.2) {
                throttle = 0.2;
            }
            if (throttle < -0.2) {
                throttle = -0.2;
            }
            final float[] throttleCrawlerTrack5 = this.throttleCrawlerTrack;
            final int n5 = 0;
            throttleCrawlerTrack5[n5] += (float)throttle;
            final float[] throttleCrawlerTrack6 = this.throttleCrawlerTrack;
            final int n6 = 1;
            throttleCrawlerTrack6[n6] += (float)throttle;
        }
        for (int i = 0; i < 2; ++i) {
            if (this.throttleCrawlerTrack[i] < -0.72f) {
                this.throttleCrawlerTrack[i] = -0.72f;
            }
            else if (this.throttleCrawlerTrack[i] > 0.72f) {
                this.throttleCrawlerTrack[i] = 0.72f;
            }
            final float[] rotTrackRoller = this.rotTrackRoller;
            final int n7 = i;
            rotTrackRoller[n7] += this.throttleCrawlerTrack[i] * this.getAcInfo().trackRollerRot;
            if (this.rotTrackRoller[i] >= 360.0f) {
                final float[] rotTrackRoller2 = this.rotTrackRoller;
                final int n8 = i;
                rotTrackRoller2[n8] -= 360.0f;
                final float[] prevRotTrackRoller = this.prevRotTrackRoller;
                final int n9 = i;
                prevRotTrackRoller[n9] -= 360.0f;
            }
            else if (this.rotTrackRoller[i] < 0.0f) {
                final float[] rotTrackRoller3 = this.rotTrackRoller;
                final int n10 = i;
                rotTrackRoller3[n10] += 360.0f;
                final float[] prevRotTrackRoller2 = this.prevRotTrackRoller;
                final int n11 = i;
                prevRotTrackRoller2[n11] += 360.0f;
            }
            final float[] rotCrawlerTrack = this.rotCrawlerTrack;
            final int n12 = i;
            rotCrawlerTrack[n12] -= this.throttleCrawlerTrack[i];
            while (this.rotCrawlerTrack[i] >= 1.0f) {
                final float[] rotCrawlerTrack2 = this.rotCrawlerTrack;
                final int n13 = i;
                --rotCrawlerTrack2[n13];
                final float[] prevRotCrawlerTrack = this.prevRotCrawlerTrack;
                final int n14 = i;
                --prevRotCrawlerTrack[n14];
            }
            while (this.rotCrawlerTrack[i] < 0.0f) {
                final float[] rotCrawlerTrack3 = this.rotCrawlerTrack;
                final int n15 = i;
                ++rotCrawlerTrack3[n15];
            }
            while (this.prevRotCrawlerTrack[i] < 0.0f) {
                final float[] prevRotCrawlerTrack2 = this.prevRotCrawlerTrack;
                final int n16 = i;
                ++prevRotCrawlerTrack2[n16];
            }
            final float[] throttleCrawlerTrack7 = this.throttleCrawlerTrack;
            final int n17 = i;
            throttleCrawlerTrack7[n17] *= 0.75;
        }
    }
    
    public void checkServerNoMove() {
        if (!this.field_70170_p.field_72995_K) {
            final double moti = this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y;
            if (moti < 1.0E-4) {
                if (this.serverNoMoveCount < 20) {
                    ++this.serverNoMoveCount;
                    if (this.serverNoMoveCount >= 20) {
                        this.serverNoMoveCount = 0;
                        if (this.field_70170_p instanceof WorldServer) {
                            ((WorldServer)this.field_70170_p).func_73039_n().func_151247_a((Entity)this, (Packet)new S12PacketEntityVelocity(this.func_145782_y(), 0.0, 0.0, 0.0));
                        }
                    }
                }
            }
            else {
                this.serverNoMoveCount = 0;
            }
        }
    }
    
    public boolean haveRotPart() {
        return this.field_70170_p.field_72995_K && this.getAcInfo() != null && this.rotPartRotation.length > 0 && this.rotPartRotation.length == this.getAcInfo().partRotPart.size();
    }
    
    public void onUpdate_PartRotation() {
        if (this.haveRotPart()) {
            for (int i = 0; i < this.rotPartRotation.length; ++i) {
                this.prevRotPartRotation[i] = this.rotPartRotation[i];
                if ((!this.isDestroyed() && this.getAcInfo().partRotPart.get(i).rotAlways) || this.getRiddenByEntity() != null) {
                    final float[] rotPartRotation = this.rotPartRotation;
                    final int n = i;
                    rotPartRotation[n] += this.getAcInfo().partRotPart.get(i).rotSpeed;
                    if (this.rotPartRotation[i] < 0.0f) {
                        final float[] rotPartRotation2 = this.rotPartRotation;
                        final int n2 = i;
                        rotPartRotation2[n2] += 360.0f;
                    }
                    if (this.rotPartRotation[i] >= 360.0f) {
                        final float[] rotPartRotation3 = this.rotPartRotation;
                        final int n3 = i;
                        rotPartRotation3[n3] -= 360.0f;
                    }
                }
            }
        }
    }
    
    public void onRideEntity(final Entity ridingEntity) {
    }
    
    public int getAlt(final double px, final double py, final double pz) {
        int i;
        for (i = 0; i < 256; ++i) {
            if (py - i <= 0.0) {
                break;
            }
            if (py - i < 256.0) {
                if (0 != W_WorldFunc.getBlockId(this.field_70170_p, (int)px, (int)py - i, (int)pz)) {
                    break;
                }
            }
        }
        return i;
    }
    
    public boolean canRepelling(final Entity entity) {
        return this.isRepelling() && this.tickRepelling > 50;
    }
    
    private void onUpdate_Repelling() {
        if (this.getAcInfo() != null && this.getAcInfo().haveRepellingHook()) {
            if (this.isRepelling()) {
                final int alt = this.getAlt(this.field_70165_t, this.field_70163_u, this.field_70161_v);
                if (this.ropesLength > -50.0f && this.ropesLength > -alt) {
                    this.ropesLength -= (float)(this.field_70170_p.field_72995_K ? 0.30000001192092896 : 0.25);
                }
            }
            else {
                this.ropesLength = 0.0f;
            }
        }
        this.onUpdate_UnmountCrewRepelling();
    }
    
    private void onUpdate_UnmountCrewRepelling() {
        if (this.getAcInfo() == null) {
            return;
        }
        if (!this.isRepelling()) {
            this.tickRepelling = 0;
            return;
        }
        if (this.tickRepelling < 60) {
            ++this.tickRepelling;
            return;
        }
        if (this.field_70170_p.field_72995_K) {
            return;
        }
        for (int ropeIdx = 0; ropeIdx < this.getAcInfo().repellingHooks.size(); ++ropeIdx) {
            final MCH_AircraftInfo.RepellingHook hook = this.getAcInfo().repellingHooks.get(ropeIdx);
            if (this.getCountOnUpdate() % hook.interval == 0) {
                for (int i = 1; i < this.getSeatNum(); ++i) {
                    final MCH_EntitySeat seat = this.getSeat(i);
                    if (seat != null && seat.field_70153_n != null && !W_EntityPlayer.isPlayer(seat.field_70153_n) && !(this.getSeatInfo(i + 1) instanceof MCH_SeatRackInfo)) {
                        final Entity entity = seat.field_70153_n;
                        final Vec3 dropPos = this.getTransformedPosition(hook.pos, this.prevPosition.oldest());
                        seat.field_70165_t = dropPos.field_72450_a;
                        seat.field_70163_u = dropPos.field_72448_b - 2.0;
                        seat.field_70161_v = dropPos.field_72449_c;
                        entity.func_70078_a((Entity)null);
                        this.unmountEntityRepelling(entity, dropPos, ropeIdx);
                        this.lastUsedRopeIndex = ropeIdx;
                        break;
                    }
                }
            }
        }
    }
    
    public void unmountEntityRepelling(final Entity entity, final Vec3 dropPos, final int ropeIdx) {
        entity.field_70165_t = dropPos.field_72450_a;
        entity.field_70163_u = dropPos.field_72448_b - 2.0;
        entity.field_70161_v = dropPos.field_72449_c;
        final MCH_EntityHide hideEntity = new MCH_EntityHide(this.field_70170_p, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v);
        hideEntity.setParent(this, entity, ropeIdx);
        final MCH_EntityHide mch_EntityHide = hideEntity;
        final double n = 0.0;
        entity.field_70159_w = n;
        mch_EntityHide.field_70159_w = n;
        final MCH_EntityHide mch_EntityHide2 = hideEntity;
        final double n2 = 0.0;
        entity.field_70181_x = n2;
        mch_EntityHide2.field_70181_x = n2;
        final MCH_EntityHide mch_EntityHide3 = hideEntity;
        final double n3 = 0.0;
        entity.field_70179_y = n3;
        mch_EntityHide3.field_70179_y = n3;
        final MCH_EntityHide mch_EntityHide4 = hideEntity;
        final float n4 = 0.0f;
        entity.field_70143_R = n4;
        mch_EntityHide4.field_70143_R = n4;
        this.field_70170_p.func_72838_d((Entity)hideEntity);
    }
    
    private void onUpdate_UnmountCrew() {
        if (this.getAcInfo() == null) {
            return;
        }
        if (this.isParachuting) {
            if (MCH_Lib.getBlockIdY(this, 3, -10) != 0) {
                this.stopUnmountCrew();
            }
            else if ((!this.haveHatch() || this.getHatchRotation() > 89.0f) && this.getCountOnUpdate() % this.getAcInfo().mobDropOption.interval == 0 && !this.unmountCrew(true)) {
                this.stopUnmountCrew();
            }
        }
    }
    
    public void unmountAircraft() {
        Vec3 v = Vec3.func_72443_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
        if (this.field_70154_o instanceof MCH_EntitySeat) {
            final MCH_EntityAircraft ac = ((MCH_EntitySeat)this.field_70154_o).getParent();
            final MCH_SeatInfo seatInfo = ac.getSeatInfo(this);
            if (seatInfo instanceof MCH_SeatRackInfo) {
                v = ((MCH_SeatRackInfo)seatInfo).getEntryPos();
                v = ac.getTransformedPosition(v);
            }
        }
        else if (this.field_70154_o instanceof EntityMinecartEmpty) {
            this.dismountedUserCtrl = true;
        }
        this.func_70012_b(v.field_72450_a, v.field_72448_b, v.field_72449_c, this.getRotYaw(), this.getRotPitch());
        this.func_70078_a((Entity)null);
        this.func_70012_b(v.field_72450_a, v.field_72448_b, v.field_72449_c, this.getRotYaw(), this.getRotPitch());
    }
    
    public boolean canUnmount(final Entity entity) {
        return this.getAcInfo() != null && this.getAcInfo().isEnableParachuting && this.getSeatIdByEntity(entity) > 1 && (!this.haveHatch() || this.getHatchRotation() >= 89.0f);
    }
    
    public void unmount(final Entity entity) {
        if (this.getAcInfo() == null) {
            return;
        }
        if (this.canRepelling(entity) && this.getAcInfo().haveRepellingHook()) {
            final MCH_EntitySeat seat = this.getSeatByEntity(entity);
            if (seat != null) {
                this.lastUsedRopeIndex = (this.lastUsedRopeIndex + 1) % this.getAcInfo().repellingHooks.size();
                Vec3 dropPos = this.getTransformedPosition(this.getAcInfo().repellingHooks.get(this.lastUsedRopeIndex).pos, this.prevPosition.oldest());
                dropPos = dropPos.func_72441_c(0.0, -2.0, 0.0);
                seat.field_70165_t = dropPos.field_72450_a;
                seat.field_70163_u = dropPos.field_72448_b;
                seat.field_70161_v = dropPos.field_72449_c;
                entity.func_70078_a((Entity)null);
                entity.field_70165_t = dropPos.field_72450_a;
                entity.field_70163_u = dropPos.field_72448_b;
                entity.field_70161_v = dropPos.field_72449_c;
                this.unmountEntityRepelling(entity, dropPos, this.lastUsedRopeIndex);
            }
            else {
                MCH_Lib.Log(this, "Error:MCH_EntityAircraft.unmount seat=null : " + entity, new Object[0]);
            }
        }
        else if (this.canUnmount(entity)) {
            final MCH_EntitySeat seat = this.getSeatByEntity(entity);
            if (seat != null) {
                final Vec3 dropPos = this.getTransformedPosition(this.getAcInfo().mobDropOption.pos, this.prevPosition.oldest());
                seat.field_70165_t = dropPos.field_72450_a;
                seat.field_70163_u = dropPos.field_72448_b;
                seat.field_70161_v = dropPos.field_72449_c;
                entity.func_70078_a((Entity)null);
                entity.field_70165_t = dropPos.field_72450_a;
                entity.field_70163_u = dropPos.field_72448_b;
                entity.field_70161_v = dropPos.field_72449_c;
                this.dropEntityParachute(entity);
            }
            else {
                MCH_Lib.Log(this, "Error:MCH_EntityAircraft.unmount seat=null : " + entity, new Object[0]);
            }
        }
    }
    
    public boolean canParachuting(final Entity entity) {
        if (this.getAcInfo() == null || !this.getAcInfo().isEnableParachuting || this.getSeatIdByEntity(entity) <= 1 || MCH_Lib.getBlockIdY(this, 3, -13) != 0) {
            return false;
        }
        if (this.haveHatch() && this.getHatchRotation() > 89.0f) {
            return this.getSeatIdByEntity(entity) > 1;
        }
        return this.getSeatIdByEntity(entity) > 1;
    }
    
    public void onUpdate_RidingEntity() {
        if (!this.field_70170_p.field_72995_K && this.waitMountEntity == 0 && this.getCountOnUpdate() > 20 && this.canMountWithNearEmptyMinecart()) {
            this.mountWithNearEmptyMinecart();
        }
        if (this.waitMountEntity > 0) {
            --this.waitMountEntity;
        }
        if (!this.field_70170_p.field_72995_K && this.getRidingEntity() != null) {
            this.setRotRoll(this.getRotRoll() * 0.9f);
            this.setRotPitch(this.getRotPitch() * 0.95f);
            final Entity re = this.getRidingEntity();
            float target = MathHelper.func_76142_g(re.field_70177_z + 90.0f);
            if (target - this.field_70177_z > 180.0f) {
                target -= 360.0f;
            }
            if (target - this.field_70177_z < -180.0f) {
                target += 360.0f;
            }
            if (this.field_70173_aa % 2 == 0) {}
            float dist = 50.0f * (float)re.func_70092_e(re.field_70169_q, re.field_70167_r, re.field_70166_s);
            if (dist > 0.001) {
                dist = MathHelper.func_76133_a((double)dist);
                final float distYaw = MCH_Lib.RNG(target - this.field_70177_z, -dist, dist);
                this.field_70177_z += distYaw;
            }
            final double bkPosX = this.field_70165_t;
            final double bkPosY = this.field_70163_u;
            final double bkPosZ = this.field_70161_v;
            if (this.getRidingEntity().field_70128_L) {
                this.func_70078_a((Entity)null);
                this.waitMountEntity = 20;
            }
            else if (this.getCurrentThrottle() > 0.8) {
                this.field_70159_w = this.getRidingEntity().field_70159_w;
                this.field_70181_x = this.getRidingEntity().field_70181_x;
                this.field_70179_y = this.getRidingEntity().field_70179_y;
                this.func_70078_a((Entity)null);
                this.waitMountEntity = 20;
            }
            this.field_70165_t = bkPosX;
            this.field_70163_u = bkPosY;
            this.field_70161_v = bkPosZ;
        }
    }
    
    public void explosionByCrash(final double prevMotionY) {
        float exp = (this.getAcInfo() != null) ? (this.getAcInfo().maxFuel / 400.0f) : 2.0f;
        if (exp < 1.0f) {
            exp = 1.0f;
        }
        if (exp > 15.0f) {
            exp = 15.0f;
        }
        MCH_Lib.DbgLog(this.field_70170_p, "OnGroundAfterDestroyed:motionY=%.3f", (float)prevMotionY);
        MCH_Explosion.newExplosion(this.field_70170_p, null, null, this.field_70165_t, this.field_70163_u, this.field_70161_v, exp, (exp >= 2.0f) ? (exp * 0.5f) : 1.0f, true, true, true, true, 5);
    }
    
    public void onUpdate_CollisionGroundDamage() {
        if (this.isDestroyed()) {
            return;
        }
        if (MCH_Lib.getBlockIdY(this, 3, -3) > 0 && !this.field_70170_p.field_72995_K) {
            final float roll = MathHelper.func_76135_e(MathHelper.func_76142_g(this.getRotRoll()));
            final float pitch = MathHelper.func_76135_e(MathHelper.func_76142_g(this.getRotPitch()));
            if (roll > this.getGiveDamageRot() || pitch > this.getGiveDamageRot()) {
                float dmg = MathHelper.func_76135_e(roll) + MathHelper.func_76135_e(pitch);
                if (dmg < 90.0f) {
                    dmg *= 0.4f * (float)this.func_70011_f(this.field_70169_q, this.field_70167_r, this.field_70166_s);
                }
                else {
                    dmg *= 0.4f;
                }
                if (dmg > 1.0f && this.field_70146_Z.nextInt(4) == 0) {
                    this.func_70097_a(DamageSource.field_76368_d, dmg);
                }
            }
        }
        if (this.getCountOnUpdate() % 30 == 0 && (this.getAcInfo() == null || !this.getAcInfo().isFloat) && MCH_Lib.isBlockInWater(this.field_70170_p, (int)(this.field_70165_t + 0.5), (int)(this.field_70163_u + 1.5 + this.getAcInfo().submergedDamageHeight), (int)(this.field_70161_v + 0.5))) {
            int hp = this.getMaxHP() / 10;
            if (hp <= 0) {
                hp = 1;
            }
            this.attackEntityFrom(DamageSource.field_76368_d, hp);
        }
    }
    
    public float getGiveDamageRot() {
        return 40.0f;
    }
    
    public void applyServerPositionAndRotation() {
        final double rpinc = this.aircraftPosRotInc;
        final double yaw = MathHelper.func_76138_g(this.aircraftYaw - this.getRotYaw());
        final double roll = MathHelper.func_76138_g(this.getServerRoll() - this.getRotRoll());
        if (!this.isDestroyed() && (!W_Lib.isClientPlayer(this.getRiddenByEntity()) || this.getRidingEntity() != null)) {
            this.setRotYaw((float)(this.getRotYaw() + yaw / rpinc));
            this.setRotPitch((float)(this.getRotPitch() + (this.aircraftPitch - this.getRotPitch()) / rpinc));
            this.setRotRoll((float)(this.getRotRoll() + roll / rpinc));
        }
        this.func_70107_b(this.field_70165_t + (this.aircraftX - this.field_70165_t) / rpinc, this.field_70163_u + (this.aircraftY - this.field_70163_u) / rpinc, this.field_70161_v + (this.aircraftZ - this.field_70161_v) / rpinc);
        this.func_70101_b(this.getRotYaw(), this.getRotPitch());
        --this.aircraftPosRotInc;
    }
    
    protected void autoRepair() {
        if (this.timeSinceHit > 0) {
            --this.timeSinceHit;
        }
        if (this.getMaxHP() <= 0) {
            return;
        }
        if (!this.isDestroyed()) {
            if (this.getDamageTaken() > this.beforeDamageTaken) {
                this.repairCount = 600;
            }
            else if (this.repairCount > 0) {
                --this.repairCount;
            }
            else {
                this.repairCount = 40;
                final double n;
                final double hpp = n = this.getHP() / this.getMaxHP();
                final MCH_Config config = MCH_MOD.config;
                if (n >= MCH_Config.AutoRepairHP.prmDouble) {
                    this.repair(this.getMaxHP() / 100);
                }
            }
        }
        this.beforeDamageTaken = this.getDamageTaken();
    }
    
    public boolean repair(int tpd) {
        if (tpd < 1) {
            tpd = 1;
        }
        final int damage = this.getDamageTaken();
        if (damage > 0) {
            if (!this.field_70170_p.field_72995_K) {
                this.setDamageTaken(damage - tpd);
            }
            return true;
        }
        return false;
    }
    
    public void repairOtherAircraft() {
        final float range = (this.getAcInfo() != null) ? this.getAcInfo().repairOtherVehiclesRange : 0.0f;
        if (range <= 0.0f) {
            return;
        }
        if (!this.field_70170_p.field_72995_K && this.getCountOnUpdate() % 20 == 0) {
            final List list = this.field_70170_p.func_72872_a((Class)MCH_EntityAircraft.class, this.func_70046_E().func_72314_b((double)range, (double)range, (double)range));
            for (int i = 0; i < list.size(); ++i) {
                final MCH_EntityAircraft ac = list.get(i);
                if (!W_Entity.isEqual(this, ac)) {
                    if (ac.getHP() < ac.getMaxHP()) {
                        ac.setDamageTaken(ac.getDamageTaken() - this.getAcInfo().repairOtherVehiclesValue);
                    }
                }
            }
        }
    }
    
    protected void regenerationMob() {
        if (this.isDestroyed()) {
            return;
        }
        if (this.field_70170_p.field_72995_K) {
            return;
        }
        if (this.getAcInfo() != null && this.getAcInfo().regeneration && this.getRiddenByEntity() != null) {
            final MCH_EntitySeat[] arr$;
            final MCH_EntitySeat[] st = arr$ = this.getSeats();
            for (final MCH_EntitySeat s : arr$) {
                if (s != null && !s.field_70128_L) {
                    final Entity e = s.field_70153_n;
                    if (W_Lib.isEntityLivingBase(e) && !e.field_70128_L) {
                        final PotionEffect pe = W_Entity.getActivePotionEffect(e, Potion.field_76428_l);
                        if (pe == null || (pe != null && pe.func_76459_b() < 500)) {
                            W_Entity.addPotionEffect(e, new PotionEffect(Potion.field_76428_l.field_76415_H, 250, 0, true));
                        }
                    }
                }
            }
        }
    }
    
    public double getWaterDepth() {
        final byte b0 = 5;
        double d0 = 0.0;
        for (int i = 0; i < b0; ++i) {
            double d2 = this.field_70121_D.field_72338_b + (this.field_70121_D.field_72337_e - this.field_70121_D.field_72338_b) * (i + 0) / b0 - 0.125;
            double d3 = this.field_70121_D.field_72338_b + (this.field_70121_D.field_72337_e - this.field_70121_D.field_72338_b) * (i + 1) / b0 - 0.125;
            d2 += this.getAcInfo().floatOffset;
            d3 += this.getAcInfo().floatOffset;
            final AxisAlignedBB axisalignedbb = W_AxisAlignedBB.getAABB(this.field_70121_D.field_72340_a, d2, this.field_70121_D.field_72339_c, this.field_70121_D.field_72336_d, d3, this.field_70121_D.field_72334_f);
            if (this.field_70170_p.func_72830_b(axisalignedbb, Material.field_151586_h)) {
                d0 += 1.0 / b0;
            }
        }
        return d0;
    }
    
    public int getCountOnUpdate() {
        return this.countOnUpdate;
    }
    
    public boolean canSupply() {
        if (!this.canFloatWater()) {
            return MCH_Lib.getBlockIdY(this, 1, -3) != 0 && !this.func_70090_H();
        }
        return MCH_Lib.getBlockIdY(this, 1, -3) != 0;
    }
    
    public void setFuel(int fuel) {
        if (!this.field_70170_p.field_72995_K) {
            if (fuel < 0) {
                fuel = 0;
            }
            if (fuel > this.getMaxFuel()) {
                fuel = this.getMaxFuel();
            }
            if (fuel != this.getFuel()) {
                this.func_70096_w().func_75692_b(25, (Object)fuel);
            }
        }
    }
    
    public int getFuel() {
        return this.func_70096_w().func_75679_c(25);
    }
    
    public float getFuelP() {
        final int m = this.getMaxFuel();
        if (m == 0) {
            return 0.0f;
        }
        return this.getFuel() / m;
    }
    
    public boolean canUseFuel(final boolean checkOtherSeet) {
        return this.getMaxFuel() <= 0 || this.getFuel() > 1 || this.isInfinityFuel(this.getRiddenByEntity(), checkOtherSeet);
    }
    
    public boolean canUseFuel() {
        return this.canUseFuel(false);
    }
    
    public int getMaxFuel() {
        return (this.getAcInfo() != null) ? this.getAcInfo().maxFuel : 0;
    }
    
    public void supplyFuel() {
        final float range = (this.getAcInfo() != null) ? this.getAcInfo().fuelSupplyRange : 0.0f;
        if (range <= 0.0f) {
            return;
        }
        if (!this.field_70170_p.field_72995_K && this.getCountOnUpdate() % 10 == 0) {
            final List list = this.field_70170_p.func_72872_a((Class)MCH_EntityAircraft.class, this.func_70046_E().func_72314_b((double)range, (double)range, (double)range));
            for (int i = 0; i < list.size(); ++i) {
                final MCH_EntityAircraft ac = list.get(i);
                if (!W_Entity.isEqual(this, ac)) {
                    if ((!this.field_70122_E || ac.canSupply()) && ac.getFuel() < ac.getMaxFuel()) {
                        int fc = ac.getMaxFuel() - ac.getFuel();
                        if (fc > 30) {
                            fc = 30;
                        }
                        ac.setFuel(ac.getFuel() + fc);
                    }
                    ac.fuelSuppliedCount = 40;
                }
            }
        }
    }
    
    public void updateFuel() {
        if (this.getMaxFuel() == 0) {
            return;
        }
        if (this.fuelSuppliedCount > 0) {
            --this.fuelSuppliedCount;
        }
        if (!this.isDestroyed() && !this.field_70170_p.field_72995_K) {
            if (this.getCountOnUpdate() % 20 == 0 && this.getFuel() > 1 && this.getThrottle() > 0.0 && this.fuelSuppliedCount <= 0) {
                double t = this.getThrottle() * 1.4;
                if (t > 1.0) {
                    t = 1.0;
                }
                this.fuelConsumption += t * this.getAcInfo().fuelConsumption * this.getFuelConsumptionFactor();
                if (this.fuelConsumption > 1.0) {
                    final int f = (int)this.fuelConsumption;
                    this.fuelConsumption -= f;
                    this.setFuel(this.getFuel() - f);
                }
            }
            int curFuel = this.getFuel();
            if (this.canSupply() && this.getCountOnUpdate() % 10 == 0 && curFuel < this.getMaxFuel()) {
                for (int i = 0; i < 3; ++i) {
                    if (curFuel < this.getMaxFuel()) {
                        final ItemStack fuel = this.getGuiInventory().getFuelSlotItemStack(i);
                        if (fuel != null && fuel.func_77973_b() instanceof MCH_ItemFuel && fuel.func_77960_j() < fuel.func_77958_k()) {
                            int fc = this.getMaxFuel() - curFuel;
                            if (fc > 100) {
                                fc = 100;
                            }
                            if (fuel.func_77960_j() > fuel.func_77958_k() - fc) {
                                fc = fuel.func_77958_k() - fuel.func_77960_j();
                            }
                            fuel.func_77964_b(fuel.func_77960_j() + fc);
                            curFuel += fc;
                        }
                    }
                }
                if (this.getFuel() != curFuel) {
                    MCH_Achievement.addStat(this.field_70153_n, MCH_Achievement.supplyFuel, 1);
                }
                this.setFuel(curFuel);
            }
        }
    }
    
    public float getFuelConsumptionFactor() {
        return 1.0f;
    }
    
    public void updateSupplyAmmo() {
        if (!this.field_70170_p.field_72995_K) {
            boolean isReloading = false;
            if (this.getRiddenByEntity() instanceof EntityPlayer && !this.getRiddenByEntity().field_70128_L && ((EntityPlayer)this.getRiddenByEntity()).field_71070_bA instanceof MCH_AircraftGuiContainer) {
                isReloading = true;
            }
            this.setCommonStatus(2, isReloading);
            if (!this.isDestroyed() && this.beforeSupplyAmmo && !isReloading) {
                this.reloadAllWeapon();
                MCH_PacketNotifyAmmoNum.sendAllAmmoNum(this, null);
            }
            this.beforeSupplyAmmo = isReloading;
        }
        if (this.getCommonStatus(2)) {
            this.supplyAmmoWait = 20;
        }
        if (this.supplyAmmoWait > 0) {
            --this.supplyAmmoWait;
        }
    }
    
    public void supplyAmmo(final int weaponID) {
        if (this.field_70170_p.field_72995_K) {
            final MCH_WeaponSet ws = this.getWeapon(weaponID);
            ws.supplyRestAllAmmo();
        }
        else {
            MCH_Achievement.addStat(this.field_70153_n, MCH_Achievement.supplyAmmo, 1);
            if (this.getRiddenByEntity() instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)this.getRiddenByEntity();
                if (this.canPlayerSupplyAmmo(player, weaponID)) {
                    final MCH_WeaponSet ws2 = this.getWeapon(weaponID);
                    for (final MCH_WeaponInfo.RoundItem ri : ws2.getInfo().roundItems) {
                        int num = ri.num;
                        for (int i = 0; i < player.field_71071_by.field_70462_a.length; ++i) {
                            final ItemStack itemStack = player.field_71071_by.field_70462_a[i];
                            if (itemStack != null && itemStack.func_77969_a(ri.itemStack)) {
                                if (itemStack.func_77973_b() == W_Item.getItemByName("water_bucket") || itemStack.func_77973_b() == W_Item.getItemByName("lava_bucket")) {
                                    if (itemStack.field_77994_a == 1) {
                                        player.field_71071_by.func_70299_a(i, new ItemStack(W_Item.getItemByName("bucket"), 1));
                                        --num;
                                    }
                                }
                                else if (itemStack.field_77994_a > num) {
                                    final ItemStack itemStack2 = itemStack;
                                    itemStack2.field_77994_a -= num;
                                    num = 0;
                                }
                                else {
                                    num -= itemStack.field_77994_a;
                                    itemStack.field_77994_a = 0;
                                    player.field_71071_by.field_70462_a[i] = null;
                                }
                            }
                            if (num <= 0) {
                                break;
                            }
                        }
                    }
                    ws2.supplyRestAllAmmo();
                }
            }
        }
    }
    
    public void supplyAmmoToOtherAircraft() {
        final float range = (this.getAcInfo() != null) ? this.getAcInfo().ammoSupplyRange : 0.0f;
        if (range <= 0.0f) {
            return;
        }
        if (!this.field_70170_p.field_72995_K && this.getCountOnUpdate() % 40 == 0) {
            final List list = this.field_70170_p.func_72872_a((Class)MCH_EntityAircraft.class, this.func_70046_E().func_72314_b((double)range, (double)range, (double)range));
            for (int i = 0; i < list.size(); ++i) {
                final MCH_EntityAircraft ac = list.get(i);
                if (!W_Entity.isEqual(this, ac)) {
                    if (ac.canSupply()) {
                        for (int wid = 0; wid < ac.getWeaponNum(); ++wid) {
                            final MCH_WeaponSet ws = ac.getWeapon(wid);
                            final int num = ws.getRestAllAmmoNum() + ws.getAmmoNum();
                            if (num < ws.getAllAmmoNum()) {
                                int ammo = ws.getAllAmmoNum() / 10;
                                if (ammo < 1) {
                                    ammo = 1;
                                }
                                ws.setRestAllAmmoNum(num + ammo);
                                final EntityPlayer player = ac.getEntityByWeaponId(wid);
                                if (num != ws.getRestAllAmmoNum() + ws.getAmmoNum()) {
                                    if (ws.getAmmoNum() <= 0) {
                                        ws.reloadMag();
                                    }
                                    MCH_PacketNotifyAmmoNum.sendAmmoNum(ac, player, wid);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public boolean canPlayerSupplyAmmo(final EntityPlayer player, final int weaponId) {
        if (MCH_Lib.getBlockIdY(this, 1, -3) == 0) {
            return false;
        }
        if (!this.canSupply()) {
            return false;
        }
        final MCH_WeaponSet ws = this.getWeapon(weaponId);
        if (ws.getRestAllAmmoNum() + ws.getAmmoNum() >= ws.getAllAmmoNum()) {
            return false;
        }
        for (final MCH_WeaponInfo.RoundItem ri : ws.getInfo().roundItems) {
            int num = ri.num;
            for (final ItemStack itemStack : player.field_71071_by.field_70462_a) {
                if (itemStack != null && itemStack.func_77969_a(ri.itemStack)) {
                    num -= itemStack.field_77994_a;
                }
                if (num <= 0) {
                    break;
                }
            }
            if (num > 0) {
                return false;
            }
        }
        return true;
    }
    
    public MCH_EntityAircraft setTextureName(final String name) {
        if (name != null) {
            if (!name.isEmpty()) {
                this.func_70096_w().func_75692_b(21, (Object)String.valueOf(name));
            }
        }
        return this;
    }
    
    public String getTextureName() {
        return this.func_70096_w().func_75681_e(21);
    }
    
    public void switchNextTextureName() {
        if (this.getAcInfo() != null) {
            this.setTextureName(this.getAcInfo().getNextTextureName(this.getTextureName()));
        }
    }
    
    public void zoomCamera() {
        if (this.canZoom()) {
            float z = this.camera.getCameraZoom();
            if (z >= this.getZoomMax() - 0.01) {
                z = 1.0f;
            }
            else {
                z *= 2.0f;
                if (z >= this.getZoomMax()) {
                    z = this.getZoomMax();
                }
            }
            this.camera.setCameraZoom((z <= this.getZoomMax() + 0.01) ? z : 1.0f);
        }
    }
    
    public int getZoomMax() {
        return (this.getAcInfo() != null) ? this.getAcInfo().cameraZoom : 1;
    }
    
    public boolean canZoom() {
        return this.getZoomMax() > 1;
    }
    
    public boolean canSwitchCameraMode() {
        return !this.isDestroyed() && this.getAcInfo() != null && this.getAcInfo().isEnableNightVision;
    }
    
    public boolean canSwitchCameraMode(final int seatID) {
        return !this.isDestroyed() && this.canSwitchCameraMode() && this.camera.isValidUid(seatID);
    }
    
    public int getCameraMode(final EntityPlayer player) {
        return this.camera.getMode(this.getSeatIdByEntity((Entity)player));
    }
    
    public String getCameraModeName(final EntityPlayer player) {
        return this.camera.getModeName(this.getSeatIdByEntity((Entity)player));
    }
    
    public void switchCameraMode(final EntityPlayer player) {
        this.switchCameraMode(player, this.camera.getMode(this.getSeatIdByEntity((Entity)player)) + 1);
    }
    
    public void switchCameraMode(final EntityPlayer player, final int mode) {
        this.camera.setMode(this.getSeatIdByEntity((Entity)player), mode);
    }
    
    public void updateCameraViewers() {
        for (int i = 0; i < this.getSeatNum() + 1; ++i) {
            this.camera.updateViewer(i, this.getEntityBySeatId(i));
        }
    }
    
    public void updateRadar(final int radarSpeed) {
        if (this.isEntityRadarMounted()) {
            this.radarRotate += radarSpeed;
            if (this.radarRotate >= 360) {
                this.radarRotate = 0;
            }
            if (this.radarRotate == 0) {
                this.entityRadar.updateXZ(this, 64);
            }
        }
    }
    
    public int getRadarRotate() {
        return this.radarRotate;
    }
    
    public void initRadar() {
        this.entityRadar.clear();
        this.radarRotate = 0;
    }
    
    public ArrayList<MCH_Vector2> getRadarEntityList() {
        return this.entityRadar.getEntityList();
    }
    
    public ArrayList<MCH_Vector2> getRadarEnemyList() {
        return this.entityRadar.getEnemyList();
    }
    
    public void func_70091_d(double par1, double par3, double par5) {
        if (this.getAcInfo() == null) {
            return;
        }
        this.field_70170_p.field_72984_F.func_76320_a("move");
        this.field_70139_V *= 0.4f;
        final double d3 = this.field_70165_t;
        final double d4 = this.field_70163_u;
        final double d5 = this.field_70161_v;
        final double d6 = par1;
        final double d7 = par3;
        final double d8 = par5;
        final AxisAlignedBB axisalignedbb = this.field_70121_D.func_72329_c();
        List list = getCollidingBoundingBoxes(this, this.field_70121_D.func_72321_a(par1, par3, par5));
        for (int i = 0; i < list.size(); ++i) {
            par3 = list.get(i).func_72323_b(this.field_70121_D, par3);
        }
        this.field_70121_D.func_72317_d(0.0, par3, 0.0);
        if (!this.field_70135_K && d7 != par3) {
            par5 = 0.0;
            par3 = 0.0;
            par1 = 0.0;
        }
        final boolean flag1 = this.field_70122_E || (d7 != par3 && d7 < 0.0);
        for (int j = 0; j < list.size(); ++j) {
            par1 = list.get(j).func_72316_a(this.field_70121_D, par1);
        }
        this.field_70121_D.func_72317_d(par1, 0.0, 0.0);
        if (!this.field_70135_K && d6 != par1) {
            par5 = 0.0;
            par3 = 0.0;
            par1 = 0.0;
        }
        for (int j = 0; j < list.size(); ++j) {
            par5 = list.get(j).func_72322_c(this.field_70121_D, par5);
        }
        this.field_70121_D.func_72317_d(0.0, 0.0, par5);
        if (!this.field_70135_K && d8 != par5) {
            par5 = 0.0;
            par3 = 0.0;
            par1 = 0.0;
        }
        if (this.field_70138_W > 0.0f && flag1 && this.field_70139_V < 0.05f && (d6 != par1 || d8 != par5)) {
            final double d9 = par1;
            final double d10 = par3;
            final double d11 = par5;
            par1 = d6;
            par3 = this.field_70138_W;
            par5 = d8;
            final AxisAlignedBB axisalignedbb2 = this.field_70121_D.func_72329_c();
            this.field_70121_D.func_72328_c(axisalignedbb);
            list = getCollidingBoundingBoxes(this, this.field_70121_D.func_72321_a(d6, par3, d8));
            for (int k = 0; k < list.size(); ++k) {
                par3 = list.get(k).func_72323_b(this.field_70121_D, par3);
            }
            this.field_70121_D.func_72317_d(0.0, par3, 0.0);
            if (!this.field_70135_K && d7 != par3) {
                par5 = 0.0;
                par3 = 0.0;
                par1 = 0.0;
            }
            for (int k = 0; k < list.size(); ++k) {
                par1 = list.get(k).func_72316_a(this.field_70121_D, par1);
            }
            this.field_70121_D.func_72317_d(par1, 0.0, 0.0);
            if (!this.field_70135_K && d6 != par1) {
                par5 = 0.0;
                par3 = 0.0;
                par1 = 0.0;
            }
            for (int k = 0; k < list.size(); ++k) {
                par5 = list.get(k).func_72322_c(this.field_70121_D, par5);
            }
            this.field_70121_D.func_72317_d(0.0, 0.0, par5);
            if (!this.field_70135_K && d8 != par5) {
                par5 = 0.0;
                par3 = 0.0;
                par1 = 0.0;
            }
            if (!this.field_70135_K && d7 != par3) {
                par5 = 0.0;
                par3 = 0.0;
                par1 = 0.0;
            }
            else {
                par3 = -this.field_70138_W;
                for (int k = 0; k < list.size(); ++k) {
                    par3 = list.get(k).func_72323_b(this.field_70121_D, par3);
                }
                this.field_70121_D.func_72317_d(0.0, par3, 0.0);
            }
            if (d9 * d9 + d11 * d11 >= par1 * par1 + par5 * par5) {
                par1 = d9;
                par3 = d10;
                par5 = d11;
                this.field_70121_D.func_72328_c(axisalignedbb2);
            }
        }
        this.field_70170_p.field_72984_F.func_76319_b();
        this.field_70170_p.field_72984_F.func_76320_a("rest");
        this.field_70165_t = (this.field_70121_D.field_72340_a + this.field_70121_D.field_72336_d) / 2.0;
        this.field_70163_u = this.field_70121_D.field_72338_b + this.field_70129_M - this.field_70139_V;
        this.field_70161_v = (this.field_70121_D.field_72339_c + this.field_70121_D.field_72334_f) / 2.0;
        this.field_70123_F = (d6 != par1 || d8 != par5);
        this.field_70124_G = (d7 != par3);
        this.field_70122_E = (d7 != par3 && d7 < 0.0);
        this.field_70132_H = (this.field_70123_F || this.field_70124_G);
        this.func_70064_a(par3, this.field_70122_E);
        if (d6 != par1) {
            this.field_70159_w = 0.0;
        }
        if (d7 != par3) {
            this.field_70181_x = 0.0;
        }
        if (d8 != par5) {
            this.field_70179_y = 0.0;
        }
        final double d9 = this.field_70165_t - d3;
        final double d10 = this.field_70163_u - d4;
        final double d11 = this.field_70161_v - d5;
        try {
            this.doBlockCollisions();
        }
        catch (Throwable throwable) {
            final CrashReport crashreport = CrashReport.func_85055_a(throwable, "Checking entity tile collision");
            final CrashReportCategory crashreportcategory = crashreport.func_85058_a("Entity being checked for collision");
            this.func_85029_a(crashreportcategory);
            throw new ReportedException(crashreport);
        }
        this.field_70170_p.field_72984_F.func_76319_b();
    }
    
    public static List getCollidingBoundingBoxes(final Entity par1Entity, final AxisAlignedBB par2AxisAlignedBB) {
        final ArrayList collidingBoundingBoxes = new ArrayList();
        collidingBoundingBoxes.clear();
        final int i = MathHelper.func_76128_c(par2AxisAlignedBB.field_72340_a);
        final int j = MathHelper.func_76128_c(par2AxisAlignedBB.field_72336_d + 1.0);
        final int k = MathHelper.func_76128_c(par2AxisAlignedBB.field_72338_b);
        final int l = MathHelper.func_76128_c(par2AxisAlignedBB.field_72337_e + 1.0);
        final int i2 = MathHelper.func_76128_c(par2AxisAlignedBB.field_72339_c);
        final int j2 = MathHelper.func_76128_c(par2AxisAlignedBB.field_72334_f + 1.0);
        for (int k2 = i; k2 < j; ++k2) {
            for (int l2 = i2; l2 < j2; ++l2) {
                if (par1Entity.field_70170_p.func_72899_e(k2, 64, l2)) {
                    for (int i3 = k - 1; i3 < l; ++i3) {
                        final Block block = W_WorldFunc.getBlock(par1Entity.field_70170_p, k2, i3, l2);
                        if (block != null) {
                            block.func_149743_a(par1Entity.field_70170_p, k2, i3, l2, par2AxisAlignedBB, (List)collidingBoundingBoxes, par1Entity);
                        }
                    }
                }
            }
        }
        final double d0 = 0.25;
        final List list = par1Entity.field_70170_p.func_72839_b(par1Entity, par2AxisAlignedBB.func_72314_b(d0, d0, d0));
        for (int j3 = 0; j3 < list.size(); ++j3) {
            final Entity entity = list.get(j3);
            if (!W_Lib.isEntityLivingBase(entity)) {
                if (!(entity instanceof MCH_EntitySeat)) {
                    if (!(entity instanceof MCH_EntityHitBox)) {
                        AxisAlignedBB axisalignedbb1 = entity.func_70046_E();
                        if (axisalignedbb1 != null && axisalignedbb1.func_72326_a(par2AxisAlignedBB)) {
                            collidingBoundingBoxes.add(axisalignedbb1);
                        }
                        axisalignedbb1 = par1Entity.func_70114_g(entity);
                        if (axisalignedbb1 != null && axisalignedbb1.func_72326_a(par2AxisAlignedBB)) {
                            collidingBoundingBoxes.add(axisalignedbb1);
                        }
                    }
                }
            }
        }
        return collidingBoundingBoxes;
    }
    
    protected void onUpdate_updateBlock() {
        final MCH_Config config = MCH_MOD.config;
        if (!MCH_Config.Collision_DestroyBlock.prmBool) {
            return;
        }
        for (int l = 0; l < 4; ++l) {
            final int i1 = MathHelper.func_76128_c(this.field_70165_t + (l % 2 - 0.5) * 0.8);
            final int j1 = MathHelper.func_76128_c(this.field_70161_v + (l / 2 - 0.5) * 0.8);
            for (int k1 = 0; k1 < 2; ++k1) {
                final int l2 = MathHelper.func_76128_c(this.field_70163_u) + k1;
                final Block block = W_WorldFunc.getBlock(this.field_70170_p, i1, l2, j1);
                if (!W_Block.isNull(block)) {
                    if (block == W_Block.getSnowLayer()) {
                        this.field_70170_p.func_147468_f(i1, l2, j1);
                    }
                    if (block == W_Blocks.field_150392_bi || block == W_Blocks.field_150414_aQ) {
                        W_WorldFunc.destroyBlock(this.field_70170_p, i1, l2, j1, false);
                    }
                }
            }
        }
    }
    
    public void onUpdate_ParticleSmoke() {
        if (!this.field_70170_p.field_72995_K) {
            return;
        }
        if (this.getCurrentThrottle() <= 0.10000000149011612) {
            return;
        }
        final float yaw = this.getRotYaw();
        final float pitch = this.getRotPitch();
        final float roll = this.getRotRoll();
        final MCH_WeaponSet ws = this.getCurrentWeapon(this.getRiddenByEntity());
        if (!(ws.getFirstWeapon() instanceof MCH_WeaponSmoke)) {
            return;
        }
        for (int i = 0; i < ws.getWeaponNum(); ++i) {
            final MCH_WeaponBase wb = ws.getWeapon(i);
            if (wb != null) {
                final MCH_WeaponInfo wi = wb.getInfo();
                if (wi != null) {
                    final Vec3 rot = MCH_Lib.RotVec3(0.0, 0.0, 1.0, -yaw - 180.0f + wb.fixRotationYaw, pitch - wb.fixRotationPitch, roll);
                    if (this.field_70146_Z.nextFloat() <= this.getCurrentThrottle() * 1.5) {
                        final Vec3 pos = MCH_Lib.RotVec3(wb.position, -yaw, -pitch, -roll);
                        final double x = this.field_70165_t + pos.field_72450_a + rot.field_72450_a;
                        final double y = this.field_70163_u + pos.field_72448_b + rot.field_72448_b;
                        final double z = this.field_70161_v + pos.field_72449_c + rot.field_72449_c;
                        for (int smk = 0; smk < wi.smokeNum; ++smk) {
                            final float c = this.field_70146_Z.nextFloat() * 0.05f;
                            final int maxAge = (int)(this.field_70146_Z.nextDouble() * wi.smokeMaxAge);
                            final MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", x, y, z);
                            prm.setMotion(rot.field_72450_a * wi.acceleration + (this.field_70146_Z.nextDouble() - 0.5) * 0.2, rot.field_72448_b * wi.acceleration + (this.field_70146_Z.nextDouble() - 0.5) * 0.2, rot.field_72449_c * wi.acceleration + (this.field_70146_Z.nextDouble() - 0.5) * 0.2);
                            prm.size = (this.field_70146_Z.nextInt(5) + 5.0f) * wi.smokeSize;
                            prm.setColor(wi.color.a + this.field_70146_Z.nextFloat() * 0.05f, wi.color.r + c, wi.color.g + c, wi.color.b + c);
                            prm.age = maxAge;
                            prm.toWhite = true;
                            prm.diffusible = true;
                            MCH_ParticlesUtil.spawnParticle(prm);
                        }
                    }
                }
            }
        }
    }
    
    protected void onUpdate_ParticleSandCloud(final boolean seaOnly) {
        if (seaOnly && !this.getAcInfo().enableSeaSurfaceParticle) {
            return;
        }
        double particlePosY = (int)this.field_70163_u;
        boolean b = false;
        float scale = this.getAcInfo().particlesScale * 3.0f;
        if (seaOnly) {
            scale *= 2.0f;
        }
        double throttle = this.getCurrentThrottle();
        throttle *= 2.0;
        if (throttle > 1.0) {
            throttle = 1.0;
        }
        int count = seaOnly ? ((int)(scale * 7.0f)) : 0;
        int rangeY;
        int y;
        for (rangeY = (int)(scale * 10.0f) + 1, y = 0; y < rangeY && !b; ++y) {
            for (int x = -1; x <= 1; ++x) {
                for (int z = -1; z <= 1; ++z) {
                    final Block block = W_WorldFunc.getBlock(this.field_70170_p, (int)(this.field_70165_t + 0.5) + x, (int)(this.field_70163_u + 0.5) - y, (int)(this.field_70161_v + 0.5) + z);
                    if (!b && block != null && !Block.func_149680_a(block, Blocks.field_150350_a)) {
                        if (seaOnly && W_Block.isEqual(block, W_Block.getWater())) {
                            --count;
                        }
                        if (count <= 0) {
                            particlePosY = this.field_70163_u + 1.0 + scale / 5.0f - y;
                            b = true;
                            x += 100;
                            break;
                        }
                    }
                }
            }
        }
        final double pn = (rangeY - y + 1) / (5.0 * scale) / 2.0;
        if (b && this.getAcInfo().particlesScale > 0.01f) {
            for (int k = 0; k < (int)(throttle * 6.0 * pn); ++k) {
                final float r = (float)(this.field_70146_Z.nextDouble() * 3.141592653589793 * 2.0);
                final double dx = MathHelper.func_76134_b(r);
                final double dz = MathHelper.func_76126_a(r);
                final MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", this.field_70165_t + dx * scale * 3.0, particlePosY + (this.field_70146_Z.nextDouble() - 0.5) * scale, this.field_70161_v + dz * scale * 3.0, scale * (dx * 0.3), scale * -0.4 * 0.05, scale * (dz * 0.3), scale * 5.0f);
                prm.setColor(prm.a * 0.6f, prm.r, prm.g, prm.b);
                prm.age = (int)(10.0f * scale);
                prm.motionYUpAge = (seaOnly ? 0.2f : 0.1f);
                MCH_ParticlesUtil.spawnParticle(prm);
            }
        }
    }
    
    protected boolean func_70041_e_() {
        return false;
    }
    
    public AxisAlignedBB func_70114_g(final Entity par1Entity) {
        return par1Entity.field_70121_D;
    }
    
    public AxisAlignedBB func_70046_E() {
        return this.field_70121_D;
    }
    
    public boolean func_70104_M() {
        return false;
    }
    
    public double func_70042_X() {
        return 0.0;
    }
    
    public float func_70053_R() {
        return 2.0f;
    }
    
    public boolean func_70067_L() {
        return !this.field_70128_L;
    }
    
    public boolean useFlare(final int type) {
        if (this.getAcInfo() == null || !this.getAcInfo().haveFlare()) {
            return false;
        }
        for (final int i : this.getAcInfo().flare.types) {
            if (i == type) {
                this.setCommonStatus(0, true);
                if (this.flareDv.use(type)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public int getCurrentFlareType() {
        if (!this.haveFlare()) {
            return 0;
        }
        return this.getAcInfo().flare.types[this.currentFlareIndex];
    }
    
    public void nextFlareType() {
        if (this.haveFlare()) {
            this.currentFlareIndex = (this.currentFlareIndex + 1) % this.getAcInfo().flare.types.length;
        }
    }
    
    public boolean canUseFlare() {
        return this.getAcInfo() != null && this.getAcInfo().haveFlare() && !this.getCommonStatus(0) && this.flareDv.tick == 0;
    }
    
    public boolean isFlarePreparation() {
        return this.flareDv.isInPreparation();
    }
    
    public boolean isFlareUsing() {
        return this.flareDv.isUsing();
    }
    
    public int getFlareTick() {
        return this.flareDv.tick;
    }
    
    public boolean haveFlare() {
        return this.getAcInfo() != null && this.getAcInfo().haveFlare();
    }
    
    public boolean haveFlare(final int seatID) {
        return this.haveFlare() && seatID >= 0 && seatID <= 1;
    }
    
    public MCH_EntitySeat[] getSeats() {
        return (this.seats != null) ? this.seats : MCH_EntityAircraft.seatsDummy;
    }
    
    public int getSeatIdByEntity(final Entity entity) {
        if (entity == null) {
            return -1;
        }
        if (W_Entity.isEqual(this.getRiddenByEntity(), entity)) {
            return 0;
        }
        for (int i = 0; i < this.getSeats().length; ++i) {
            final MCH_EntitySeat seat = this.getSeats()[i];
            if (seat != null && W_Entity.isEqual(seat.field_70153_n, entity)) {
                return i + 1;
            }
        }
        return -1;
    }
    
    public MCH_EntitySeat getSeatByEntity(final Entity entity) {
        final int idx = this.getSeatIdByEntity(entity);
        if (idx > 0) {
            return this.getSeat(idx - 1);
        }
        return null;
    }
    
    public Entity getEntityBySeatId(int id) {
        if (id == 0) {
            return this.getRiddenByEntity();
        }
        if (--id < 0 || id >= this.getSeats().length) {
            return null;
        }
        return (this.seats[id] != null) ? this.seats[id].field_70153_n : null;
    }
    
    public EntityPlayer getEntityByWeaponId(final int id) {
        if (id >= 0 && id < this.getWeaponNum()) {
            for (int i = 0; i < this.currentWeaponID.length; ++i) {
                if (this.currentWeaponID[i] == id) {
                    final Entity e = this.getEntityBySeatId(i);
                    if (e instanceof EntityPlayer) {
                        return (EntityPlayer)e;
                    }
                }
            }
        }
        return null;
    }
    
    public Entity getWeaponUserByWeaponName(final String name) {
        if (this.getAcInfo() == null) {
            return null;
        }
        final MCH_AircraftInfo.Weapon weapon = this.getAcInfo().getWeaponByName(name);
        Entity entity = null;
        if (weapon != null) {
            entity = this.getEntityBySeatId(this.getWeaponSeatID(null, weapon));
            if (entity == null && weapon.canUsePilot) {
                entity = this.getRiddenByEntity();
            }
        }
        return entity;
    }
    
    protected void newSeats(final int seatsNum) {
        if (seatsNum >= 2) {
            if (this.seats != null) {
                for (int i = 0; i < this.seats.length; ++i) {
                    if (this.seats[i] != null) {
                        this.seats[i].func_70106_y();
                        this.seats[i] = null;
                    }
                }
            }
            this.seats = new MCH_EntitySeat[seatsNum - 1];
        }
    }
    
    public MCH_EntitySeat getSeat(final int idx) {
        return (idx < this.seats.length) ? this.seats[idx] : null;
    }
    
    public void setSeat(final int idx, final MCH_EntitySeat seat) {
        if (idx < this.seats.length) {
            MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityAircraft.setSeat SeatID=" + idx + " / seat[]" + (this.seats[idx] != null) + " / " + (seat.field_70153_n != null), new Object[0]);
            if (this.seats[idx] == null || this.seats[idx].field_70153_n != null) {}
            this.seats[idx] = seat;
        }
    }
    
    public boolean isValidSeatID(final int seatID) {
        return seatID >= 0 && seatID < this.getSeatNum() + 1;
    }
    
    public void updateHitBoxPosition() {
    }
    
    public void updateSeatsPosition(final double px, final double py, final double pz, final boolean setPrevPos) {
        final MCH_SeatInfo[] info = this.getSeatsInfo();
        if (this.pilotSeat != null && !this.pilotSeat.field_70128_L) {
            this.pilotSeat.field_70169_q = this.pilotSeat.field_70165_t;
            this.pilotSeat.field_70167_r = this.pilotSeat.field_70163_u;
            this.pilotSeat.field_70166_s = this.pilotSeat.field_70161_v;
            this.pilotSeat.func_70107_b(px, py, pz);
            if (info != null && info.length > 0 && info[0] != null) {
                final Vec3 v = this.getTransformedPosition(info[0].pos.field_72450_a, info[0].pos.field_72448_b, info[0].pos.field_72449_c, px, py, pz, info[0].rotSeat);
                this.pilotSeat.func_70107_b(v.field_72450_a, v.field_72448_b, v.field_72449_c);
            }
            this.pilotSeat.field_70125_A = this.getRotPitch();
            this.pilotSeat.field_70177_z = this.getRotYaw();
            if (setPrevPos) {
                this.pilotSeat.field_70169_q = this.pilotSeat.field_70165_t;
                this.pilotSeat.field_70167_r = this.pilotSeat.field_70163_u;
                this.pilotSeat.field_70166_s = this.pilotSeat.field_70161_v;
            }
        }
        int i = 0;
        for (final MCH_EntitySeat seat : this.seats) {
            ++i;
            if (seat != null && !seat.field_70128_L) {
                float offsetY = 0.0f;
                if (seat.field_70153_n != null) {
                    if (W_Lib.isClientPlayer(seat.field_70153_n)) {
                        offsetY = 1.0f;
                    }
                    else if (seat.field_70153_n.field_70131_O >= 1.0f) {
                        offsetY = -seat.field_70153_n.field_70131_O + 1.0f;
                    }
                }
                seat.field_70169_q = seat.field_70165_t;
                seat.field_70167_r = seat.field_70163_u;
                seat.field_70166_s = seat.field_70161_v;
                final MCH_SeatInfo si = (i < info.length) ? info[i] : info[0];
                final Vec3 v2 = this.getTransformedPosition(si.pos.field_72450_a, si.pos.field_72448_b + offsetY, si.pos.field_72449_c, px, py, pz, si.rotSeat);
                seat.func_70107_b(v2.field_72450_a, v2.field_72448_b, v2.field_72449_c);
                seat.field_70125_A = this.getRotPitch();
                seat.field_70177_z = this.getRotYaw();
                if (setPrevPos) {
                    seat.field_70169_q = seat.field_70165_t;
                    seat.field_70167_r = seat.field_70163_u;
                    seat.field_70166_s = seat.field_70161_v;
                }
                if (si instanceof MCH_SeatRackInfo) {
                    seat.updateRotation(((MCH_SeatRackInfo)si).fixYaw + this.getRotYaw(), ((MCH_SeatRackInfo)si).fixPitch);
                }
                seat.updatePosition();
            }
        }
    }
    
    public int getClientPositionDelayCorrection() {
        return 7;
    }
    
    public void func_70056_a(final double par1, final double par3, final double par5, final float par7, final float par8, final int par9) {
        this.aircraftPosRotInc = par9 + this.getClientPositionDelayCorrection();
        this.aircraftX = par1;
        this.aircraftY = par3;
        this.aircraftZ = par5;
        this.aircraftYaw = par7;
        this.aircraftPitch = par8;
        this.field_70159_w = this.velocityX;
        this.field_70181_x = this.velocityY;
        this.field_70179_y = this.velocityZ;
    }
    
    public void updateRiderPosition(final double px, final double py, final double pz) {
        final MCH_SeatInfo[] info = this.getSeatsInfo();
        if (this.field_70153_n != null && !this.field_70153_n.field_70128_L) {
            final float riddenEntityYOffset = this.field_70153_n.field_70129_M;
            float offset = 0.0f;
            if (this.field_70153_n instanceof EntityPlayer && !W_Lib.isClientPlayer(this.field_70153_n)) {
                offset -= 1.62f;
            }
            Vec3 v;
            if (info != null && info.length > 0) {
                v = this.getTransformedPosition(info[0].pos.field_72450_a, info[0].pos.field_72448_b + riddenEntityYOffset - 0.5, info[0].pos.field_72449_c, px, py, pz, info[0].rotSeat);
            }
            else {
                v = this.getTransformedPosition(0.0, riddenEntityYOffset - 1.0f, 0.0);
            }
            this.field_70153_n.field_70129_M = 0.0f;
            this.field_70153_n.func_70107_b(v.field_72450_a, v.field_72448_b, v.field_72449_c);
            this.field_70153_n.field_70129_M = riddenEntityYOffset;
        }
    }
    
    public void func_70043_V() {
        this.updateRiderPosition(this.field_70165_t, this.field_70163_u, this.field_70161_v);
    }
    
    public Vec3 calcOnTurretPos(final Vec3 pos) {
        float ry = this.getLastRiderYaw();
        if (this.getRiddenByEntity() != null) {
            ry = this.getRiddenByEntity().field_70177_z;
        }
        final Vec3 tpos = this.getAcInfo().turretPosition.func_72441_c(0.0, pos.field_72448_b, 0.0);
        Vec3 v = pos.func_72441_c(-tpos.field_72450_a, -tpos.field_72448_b, -tpos.field_72449_c);
        v = MCH_Lib.RotVec3(v, -ry, 0.0f, 0.0f);
        final Vec3 vv = MCH_Lib.RotVec3(tpos, -this.getRotYaw(), -this.getRotPitch(), -this.getRotRoll());
        final Vec3 vec3 = v;
        vec3.field_72450_a += vv.field_72450_a;
        final Vec3 vec4 = v;
        vec4.field_72448_b += vv.field_72448_b;
        final Vec3 vec5 = v;
        vec5.field_72449_c += vv.field_72449_c;
        return v;
    }
    
    public float getLastRiderYaw() {
        return this.lastRiderYaw;
    }
    
    public float getLastRiderPitch() {
        return this.lastRiderPitch;
    }
    
    @SideOnly(Side.CLIENT)
    public void setupAllRiderRenderPosition(final float tick, final EntityPlayer player) {
        double x = this.field_70142_S + (this.field_70165_t - this.field_70142_S) * tick;
        double y = this.field_70137_T + (this.field_70163_u - this.field_70137_T) * tick;
        double z = this.field_70136_U + (this.field_70161_v - this.field_70136_U) * tick;
        this.updateRiderPosition(x, y, z);
        this.updateSeatsPosition(x, y, z, true);
        for (int i = 0; i < this.getSeatNum() + 1; ++i) {
            final Entity e = this.getEntityBySeatId(i);
            if (e != null) {
                e.field_70142_S = e.field_70165_t;
                e.field_70137_T = e.field_70163_u;
                e.field_70136_U = e.field_70161_v;
            }
        }
        if (this.getTVMissile() != null && W_Lib.isClientPlayer(this.getTVMissile().shootingEntity)) {
            final Entity tv = this.getTVMissile();
            x = tv.field_70169_q + (tv.field_70165_t - tv.field_70169_q) * tick;
            y = tv.field_70167_r + (tv.field_70163_u - tv.field_70167_r) * tick;
            z = tv.field_70166_s + (tv.field_70161_v - tv.field_70166_s) * tick;
            MCH_ViewEntityDummy.setCameraPosition(x, y, z);
        }
        else {
            final MCH_AircraftInfo.CameraPosition cpi = this.getCameraPosInfo();
            if (cpi != null && cpi.pos != null) {
                final MCH_SeatInfo seatInfo = this.getSeatInfo((Entity)player);
                Vec3 v;
                if (seatInfo != null && seatInfo.rotSeat) {
                    v = this.calcOnTurretPos(cpi.pos);
                }
                else {
                    v = MCH_Lib.RotVec3(cpi.pos, -this.getRotYaw(), -this.getRotPitch(), -this.getRotRoll());
                }
                MCH_ViewEntityDummy.setCameraPosition(x + v.field_72450_a, y + v.field_72448_b, z + v.field_72449_c);
                if (cpi.fixRot) {}
            }
        }
    }
    
    public Vec3 getTurretPos(final Vec3 pos, final boolean turret) {
        if (turret) {
            float ry = this.getLastRiderYaw();
            if (this.getRiddenByEntity() != null) {
                ry = this.getRiddenByEntity().field_70177_z;
            }
            final Vec3 tpos = this.getAcInfo().turretPosition.func_72441_c(0.0, pos.field_72448_b, 0.0);
            Vec3 v = pos.func_72441_c(-tpos.field_72450_a, -tpos.field_72448_b, -tpos.field_72449_c);
            v = MCH_Lib.RotVec3(v, -ry, 0.0f, 0.0f);
            final Vec3 vv = MCH_Lib.RotVec3(tpos, -this.getRotYaw(), -this.getRotPitch(), -this.getRotRoll());
            final Vec3 vec3 = v;
            vec3.field_72450_a += vv.field_72450_a;
            final Vec3 vec4 = v;
            vec4.field_72448_b += vv.field_72448_b;
            final Vec3 vec5 = v;
            vec5.field_72449_c += vv.field_72449_c;
            return v;
        }
        return Vec3.func_72443_a(0.0, 0.0, 0.0);
    }
    
    public Vec3 getTransformedPosition(final Vec3 v) {
        return this.getTransformedPosition(v.field_72450_a, v.field_72448_b, v.field_72449_c);
    }
    
    public Vec3 getTransformedPosition(final double x, final double y, final double z) {
        return this.getTransformedPosition(x, y, z, this.field_70165_t, this.field_70163_u, this.field_70161_v);
    }
    
    public Vec3 getTransformedPosition(final Vec3 v, final Vec3 pos) {
        return this.getTransformedPosition(v.field_72450_a, v.field_72448_b, v.field_72449_c, pos.field_72450_a, pos.field_72448_b, pos.field_72449_c);
    }
    
    public Vec3 getTransformedPosition(final Vec3 v, final double px, final double py, final double pz) {
        return this.getTransformedPosition(v.field_72450_a, v.field_72448_b, v.field_72449_c, this.field_70165_t, this.field_70163_u, this.field_70161_v);
    }
    
    public Vec3 getTransformedPosition(final double x, final double y, final double z, final double px, final double py, final double pz) {
        final Vec3 v = MCH_Lib.RotVec3(x, y, z, -this.getRotYaw(), -this.getRotPitch(), -this.getRotRoll());
        return v.func_72441_c(px, py, pz);
    }
    
    public Vec3 getTransformedPosition(double x, double y, double z, final double px, final double py, final double pz, final boolean rotSeat) {
        if (rotSeat && this.getAcInfo() != null) {
            final MCH_AircraftInfo info = this.getAcInfo();
            final Vec3 tv = MCH_Lib.RotVec3(x - info.turretPosition.field_72450_a, y - info.turretPosition.field_72448_b, z - info.turretPosition.field_72449_c, -this.getLastRiderYaw() + this.getRotYaw(), 0.0f, 0.0f);
            x = tv.field_72450_a + info.turretPosition.field_72450_a;
            y = tv.field_72448_b + info.turretPosition.field_72450_a;
            z = tv.field_72449_c + info.turretPosition.field_72450_a;
        }
        final Vec3 v = MCH_Lib.RotVec3(x, y, z, -this.getRotYaw(), -this.getRotPitch(), -this.getRotRoll());
        return v.func_72441_c(px, py, pz);
    }
    
    protected MCH_SeatInfo[] getSeatsInfo() {
        if (this.seatsInfo != null) {
            return this.seatsInfo;
        }
        this.newSeatsPos();
        return this.seatsInfo;
    }
    
    public MCH_SeatInfo getSeatInfo(final int index) {
        final MCH_SeatInfo[] seats = this.getSeatsInfo();
        if (index >= 0 && seats != null && index < seats.length) {
            return seats[index];
        }
        return null;
    }
    
    public MCH_SeatInfo getSeatInfo(final Entity entity) {
        return this.getSeatInfo(this.getSeatIdByEntity(entity));
    }
    
    protected void setSeatsInfo(final MCH_SeatInfo[] v) {
        this.seatsInfo = v;
    }
    
    public int getSeatNum() {
        if (this.getAcInfo() == null) {
            return 0;
        }
        final int s = this.getAcInfo().getNumSeatAndRack();
        return (s >= 1) ? (s - 1) : 1;
    }
    
    protected void newSeatsPos() {
        if (this.getAcInfo() != null) {
            final MCH_SeatInfo[] v = new MCH_SeatInfo[this.getAcInfo().getNumSeatAndRack()];
            for (int i = 0; i < v.length; ++i) {
                v[i] = this.getAcInfo().seatList.get(i);
            }
            this.setSeatsInfo(v);
        }
    }
    
    public void createSeats(final String uuid) {
        if (this.field_70170_p.field_72995_K) {
            return;
        }
        if (uuid.isEmpty()) {
            return;
        }
        this.setCommonUniqueId(uuid);
        this.seats = new MCH_EntitySeat[this.getSeatNum()];
        for (int i = 0; i < this.seats.length; ++i) {
            this.seats[i] = new MCH_EntitySeat(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
            this.seats[i].parentUniqueID = this.getCommonUniqueId();
            this.seats[i].seatID = i;
            this.seats[i].setParent(this);
            this.field_70170_p.func_72838_d((Entity)this.seats[i]);
        }
    }
    
    public boolean interactFirstSeat(final EntityPlayer player) {
        if (this.getSeats() == null) {
            return false;
        }
        int seatId = 1;
        final MCH_EntitySeat[] arr$ = this.getSeats();
        final int len$ = arr$.length;
        int i$ = 0;
        while (i$ < len$) {
            final MCH_EntitySeat seat = arr$[i$];
            if (seat != null && seat.field_70153_n == null && !this.isMountedEntity((Entity)player) && this.canRideSeatOrRack(seatId, (Entity)player)) {
                if (!this.field_70170_p.field_72995_K) {
                    player.func_70078_a((Entity)seat);
                    break;
                }
                break;
            }
            else {
                ++seatId;
                ++i$;
            }
        }
        return true;
    }
    
    public void onMountPlayerSeat(final MCH_EntitySeat seat, final Entity entity) {
        if (seat == null || !(entity instanceof EntityPlayer)) {
            return;
        }
        if (this.field_70170_p.field_72995_K && MCH_Lib.getClientPlayer() == entity) {
            this.switchGunnerFreeLookMode(false);
        }
        this.initCurrentWeapon(entity);
        MCH_Lib.DbgLog(this.field_70170_p, "onMountEntitySeat:%d", W_Entity.getEntityId(entity));
        final Entity pilot = this.getRiddenByEntity();
        final int sid = this.getSeatIdByEntity(entity);
        if (sid == 1 && (this.getAcInfo() == null || !this.getAcInfo().isEnableConcurrentGunnerMode)) {
            this.switchGunnerMode(false);
        }
        if (sid > 0) {
            this.isGunnerModeOtherSeat = true;
        }
        if (pilot != null && this.getAcInfo() != null) {
            final int cwid = this.getCurrentWeaponID(pilot);
            final MCH_AircraftInfo.Weapon w = this.getAcInfo().getWeaponById(cwid);
            if (w != null && this.getWeaponSeatID(this.getWeaponInfoById(cwid), w) == sid) {
                final int next = this.getNextWeaponID(pilot, 1);
                MCH_Lib.DbgLog(this.field_70170_p, "onMountEntitySeat:%d:->%d", W_Entity.getEntityId(pilot), next);
                if (next >= 0) {
                    this.switchWeapon(pilot, next);
                }
            }
        }
        if (this.field_70170_p.field_72995_K) {
            this.updateClientSettings(sid);
        }
    }
    
    public MCH_WeaponInfo getWeaponInfoById(final int id) {
        if (id >= 0) {
            final MCH_WeaponSet ws = this.getWeapon(id);
            if (ws != null) {
                return ws.getInfo();
            }
        }
        return null;
    }
    
    public abstract boolean canMountWithNearEmptyMinecart();
    
    protected void mountWithNearEmptyMinecart() {
        if (this.getRidingEntity() != null) {
            return;
        }
        int d = 2;
        if (this.dismountedUserCtrl) {
            d = 6;
        }
        final List list = this.field_70170_p.func_72839_b((Entity)this, this.field_70121_D.func_72314_b((double)d, (double)d, (double)d));
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); ++i) {
                final Entity entity = list.get(i);
                if (entity instanceof EntityMinecartEmpty) {
                    if (this.dismountedUserCtrl) {
                        return;
                    }
                    if (entity.field_70153_n == null && entity.func_70104_M()) {
                        this.waitMountEntity = 20;
                        MCH_Lib.DbgLog(this.field_70170_p.field_72995_K, "MCH_EntityAircraft.mountWithNearEmptyMinecart:" + entity, new Object[0]);
                        this.func_70078_a(entity);
                        return;
                    }
                }
            }
        }
        this.dismountedUserCtrl = false;
    }
    
    public boolean isRidePlayer() {
        if (this.getRiddenByEntity() instanceof EntityPlayer) {
            return true;
        }
        for (final MCH_EntitySeat seat : this.getSeats()) {
            if (seat != null && seat.field_70153_n instanceof EntityPlayer) {
                return true;
            }
        }
        return false;
    }
    
    public void onUnmountPlayerSeat(final MCH_EntitySeat seat, final Entity entity) {
        MCH_Lib.DbgLog(this.field_70170_p, "onUnmountPlayerSeat:%d", W_Entity.getEntityId(entity));
        final int sid = this.getSeatIdByEntity(entity);
        this.camera.initCamera(sid, entity);
        final MCH_SeatInfo seatInfo = this.getSeatInfo(seat.seatID + 1);
        if (seatInfo != null) {
            this.setUnmountPosition(entity, Vec3.func_72443_a(seatInfo.pos.field_72450_a, 0.0, seatInfo.pos.field_72449_c));
        }
        if (!this.isRidePlayer()) {
            this.switchGunnerMode(false);
            this.switchHoveringMode(false);
        }
    }
    
    public boolean isCreatedSeats() {
        return !this.getCommonUniqueId().isEmpty();
    }
    
    public void onUpdate_Seats() {
        boolean b = false;
        for (int i = 0; i < this.seats.length; ++i) {
            if (this.seats[i] != null) {
                if (!this.seats[i].field_70128_L) {
                    this.seats[i].field_70143_R = 0.0f;
                }
            }
            else {
                b = true;
            }
        }
        if (b) {
            if (this.seatSearchCount > 40) {
                if (this.field_70170_p.field_72995_K) {
                    MCH_PacketSeatListRequest.requestSeatList(this);
                }
                else {
                    this.searchSeat();
                }
                this.seatSearchCount = 0;
            }
            ++this.seatSearchCount;
        }
    }
    
    public void searchSeat() {
        final List list = this.field_70170_p.func_72872_a((Class)MCH_EntitySeat.class, this.field_70121_D.func_72314_b(60.0, 60.0, 60.0));
        for (int i = 0; i < list.size(); ++i) {
            final MCH_EntitySeat seat = list.get(i);
            if (!seat.field_70128_L && seat.parentUniqueID.equals(this.getCommonUniqueId()) && seat.seatID >= 0 && seat.seatID < this.getSeatNum() && this.seats[seat.seatID] == null) {
                (this.seats[seat.seatID] = seat).setParent(this);
            }
        }
    }
    
    public String getCommonUniqueId() {
        return this.commonUniqueId;
    }
    
    public void setCommonUniqueId(final String uniqId) {
        this.commonUniqueId = uniqId;
    }
    
    @Override
    public void func_70106_y() {
        this.setDead(false);
    }
    
    public void setDead(final boolean dropItems) {
        this.dropContentsWhenDead = dropItems;
        super.func_70106_y();
        if (this.getRiddenByEntity() != null) {
            this.getRiddenByEntity().func_70078_a((Entity)null);
        }
        this.getGuiInventory().setDead();
        for (final MCH_EntitySeat s : this.seats) {
            if (s != null) {
                s.func_70106_y();
            }
        }
        if (this.soundUpdater != null) {
            this.soundUpdater.update();
        }
        if (this.getTowChainEntity() != null) {
            this.getTowChainEntity().func_70106_y();
            this.setTowChainEntity(null);
        }
        for (final Entity e : this.func_70021_al()) {
            if (e != null) {
                e.func_70106_y();
            }
        }
        MCH_Lib.DbgLog(this.field_70170_p, "setDead:" + ((this.getAcInfo() != null) ? this.getAcInfo().name : "null"), new Object[0]);
    }
    
    public void unmountEntity() {
        if (!this.isRidePlayer()) {
            this.switchHoveringMode(false);
        }
        final boolean b = false;
        this.throttleUp = b;
        this.throttleDown = b;
        this.moveRight = b;
        this.moveLeft = b;
        Entity rByEntity = null;
        if (this.field_70153_n != null) {
            rByEntity = this.field_70153_n;
            this.camera.initCamera(0, rByEntity);
            if (!this.field_70170_p.field_72995_K) {
                this.field_70153_n.func_70078_a((Entity)null);
            }
        }
        else if (this.lastRiddenByEntity != null) {
            rByEntity = this.lastRiddenByEntity;
            if (rByEntity instanceof EntityPlayer) {
                this.camera.initCamera(0, rByEntity);
            }
        }
        MCH_Lib.DbgLog(this.field_70170_p, "unmountEntity:" + rByEntity, new Object[0]);
        if (!this.isRidePlayer()) {
            this.switchGunnerMode(false);
        }
        this.setCommonStatus(1, false);
        if (!this.isUAV()) {
            this.setUnmountPosition(rByEntity, this.getSeatsInfo()[0].pos);
        }
        else if (rByEntity != null && rByEntity.field_70154_o instanceof MCH_EntityUavStation) {
            rByEntity.func_70078_a((Entity)null);
        }
        this.field_70153_n = null;
        this.lastRiddenByEntity = null;
        if (this.cs_dismountAll) {
            this.unmountCrew(false);
        }
    }
    
    public Entity getRidingEntity() {
        return this.field_70154_o;
    }
    
    public void startUnmountCrew() {
        this.isParachuting = true;
        if (this.haveHatch()) {
            this.foldHatch(true, true);
        }
    }
    
    public void stopUnmountCrew() {
        this.isParachuting = false;
    }
    
    public void unmountCrew() {
        if (this.getAcInfo() == null) {
            return;
        }
        if (this.getAcInfo().haveRepellingHook()) {
            if (!this.isRepelling()) {
                if (MCH_Lib.getBlockIdY(this, 3, -4) > 0) {
                    this.unmountCrew(false);
                }
                else if (this.canStartRepelling()) {
                    this.startRepelling();
                }
            }
            else {
                this.stopRepelling();
            }
        }
        else if (this.isParachuting) {
            this.stopUnmountCrew();
        }
        else if (this.getAcInfo().isEnableParachuting && MCH_Lib.getBlockIdY(this, 3, -10) == 0) {
            this.startUnmountCrew();
        }
        else {
            this.unmountCrew(false);
        }
    }
    
    public boolean isRepelling() {
        return this.getCommonStatus(5);
    }
    
    public void setRepellingStat(final boolean b) {
        this.setCommonStatus(5, b);
    }
    
    public Vec3 getRopePos(final int ropeIndex) {
        if (this.getAcInfo() != null && this.getAcInfo().haveRepellingHook() && ropeIndex < this.getAcInfo().repellingHooks.size()) {
            return this.getTransformedPosition(this.getAcInfo().repellingHooks.get(ropeIndex).pos);
        }
        return Vec3.func_72443_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
    }
    
    private void startRepelling() {
        MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityAircraft.startRepelling()", new Object[0]);
        this.setRepellingStat(true);
        this.throttleUp = false;
        this.throttleDown = false;
        this.moveLeft = false;
        this.moveRight = false;
        this.tickRepelling = 0;
    }
    
    private void stopRepelling() {
        MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityAircraft.stopRepelling()", new Object[0]);
        this.setRepellingStat(false);
    }
    
    public static float abs(final float p_76135_0_) {
        return (p_76135_0_ >= 0.0f) ? p_76135_0_ : (-p_76135_0_);
    }
    
    public static double abs(final double p_76135_0_) {
        return (p_76135_0_ >= 0.0) ? p_76135_0_ : (-p_76135_0_);
    }
    
    public boolean canStartRepelling() {
        if (this.getAcInfo().haveRepellingHook() && this.isHovering() && abs(this.getRotPitch()) < 3.0f && abs(this.getRotRoll()) < 3.0f) {
            final Vec3 v = this.prevPosition.oldest().func_72441_c(-this.field_70165_t, -this.field_70163_u, -this.field_70161_v);
            if (v.func_72433_c() < 0.3) {
                return true;
            }
        }
        return false;
    }
    
    public boolean unmountCrew(final boolean unmountParachute) {
        boolean ret = false;
        final MCH_SeatInfo[] pos = this.getSeatsInfo();
        for (int i = 0; i < this.seats.length; ++i) {
            if (this.seats[i] != null && this.seats[i].field_70153_n != null) {
                final Entity entity = this.seats[i].field_70153_n;
                if (!(entity instanceof EntityPlayer) && !(pos[i + 1] instanceof MCH_SeatRackInfo)) {
                    if (unmountParachute) {
                        if (this.getSeatIdByEntity(entity) > 1) {
                            ret = true;
                            final Vec3 dropPos = this.getTransformedPosition(this.getAcInfo().mobDropOption.pos, this.prevPosition.oldest());
                            this.seats[i].field_70165_t = dropPos.field_72450_a;
                            this.seats[i].field_70163_u = dropPos.field_72448_b;
                            this.seats[i].field_70161_v = dropPos.field_72449_c;
                            entity.func_70078_a((Entity)null);
                            entity.field_70165_t = dropPos.field_72450_a;
                            entity.field_70163_u = dropPos.field_72448_b;
                            entity.field_70161_v = dropPos.field_72449_c;
                            this.dropEntityParachute(entity);
                            break;
                        }
                    }
                    else {
                        ret = true;
                        final Vec3 dropPos = pos[i + 1].pos;
                        this.setUnmountPosition(this.seats[i], pos[i + 1].pos);
                        entity.func_70078_a((Entity)null);
                        this.setUnmountPosition(entity, pos[i + 1].pos);
                    }
                }
            }
        }
        return ret;
    }
    
    public void setUnmountPosition(final Entity rByEntity, final Vec3 pos) {
        if (rByEntity != null) {
            final MCH_AircraftInfo info = this.getAcInfo();
            Vec3 v;
            if (info != null && info.unmountPosition != null) {
                v = this.getTransformedPosition(info.unmountPosition);
            }
            else {
                double x = pos.field_72450_a;
                x = ((x >= 0.0) ? (x + 3.0) : (x - 3.0));
                v = this.getTransformedPosition(x, 2.0, pos.field_72449_c);
            }
            rByEntity.func_70107_b(v.field_72450_a, v.field_72448_b, v.field_72449_c);
            this.listUnmountReserve.add(new UnmountReserve(rByEntity, v.field_72450_a, v.field_72448_b, v.field_72449_c));
        }
    }
    
    public boolean unmountEntityFromSeat(final Entity entity) {
        if (entity == null || this.seats == null || this.seats.length == 0) {
            return false;
        }
        for (final MCH_EntitySeat seat : this.seats) {
            if (seat != null && seat.field_70153_n != null && W_Entity.isEqual(seat.field_70153_n, entity)) {
                entity.func_70078_a((Entity)null);
            }
        }
        return false;
    }
    
    public void ejectSeat(Entity entity) {
        final int sid = this.getSeatIdByEntity(entity);
        if (sid < 0 || sid > 1) {
            return;
        }
        if (this.getGuiInventory().haveParachute()) {
            if (sid == 0) {
                this.getGuiInventory().consumeParachute();
                this.unmountEntity();
                this.ejectSeatSub(entity, 0);
                entity = this.getEntityBySeatId(1);
                if (entity instanceof EntityPlayer) {
                    entity = null;
                }
            }
            if (this.getGuiInventory().haveParachute() && entity != null) {
                this.getGuiInventory().consumeParachute();
                this.unmountEntityFromSeat(entity);
                this.ejectSeatSub(entity, 1);
            }
        }
    }
    
    public void ejectSeatSub(final Entity entity, final int sid) {
        final Vec3 pos = (this.getSeatInfo(sid) != null) ? this.getSeatInfo(sid).pos : null;
        if (pos != null) {
            final Vec3 v = this.getTransformedPosition(pos.field_72450_a, pos.field_72448_b + 2.0, pos.field_72449_c);
            entity.func_70107_b(v.field_72450_a, v.field_72448_b, v.field_72449_c);
        }
        final Vec3 v = MCH_Lib.RotVec3(0.0, 2.0, 0.0, -this.getRotYaw(), -this.getRotPitch(), -this.getRotRoll());
        entity.field_70159_w = this.field_70159_w + v.field_72450_a + (this.field_70146_Z.nextFloat() - 0.5) * 0.1;
        entity.field_70181_x = this.field_70181_x + v.field_72448_b;
        entity.field_70179_y = this.field_70179_y + v.field_72449_c + (this.field_70146_Z.nextFloat() - 0.5) * 0.1;
        final MCH_EntityParachute parachute = new MCH_EntityParachute(this.field_70170_p, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v);
        parachute.field_70177_z = entity.field_70177_z;
        parachute.field_70159_w = entity.field_70159_w;
        parachute.field_70181_x = entity.field_70181_x;
        parachute.field_70179_y = entity.field_70179_y;
        parachute.field_70143_R = entity.field_70143_R;
        parachute.user = entity;
        parachute.setType(2);
        this.field_70170_p.func_72838_d((Entity)parachute);
        if (this.getAcInfo().haveCanopy() && this.isCanopyClose()) {
            this.openCanopy_EjectSeat();
        }
        W_WorldFunc.MOD_playSoundAtEntity(entity, "eject_seat", 5.0f, 1.0f);
    }
    
    public boolean canEjectSeat(final Entity entity) {
        final int sid = this.getSeatIdByEntity(entity);
        return (sid != 0 || !this.isUAV()) && sid >= 0 && sid < 2 && this.getAcInfo() != null && this.getAcInfo().isEnableEjectionSeat;
    }
    
    public int getNumEjectionSeat() {
        return 0;
    }
    
    public int getMountedEntityNum() {
        int num = 0;
        if (this.field_70153_n != null && !this.field_70153_n.field_70128_L) {
            ++num;
        }
        if (this.seats != null && this.seats.length > 0) {
            for (final MCH_EntitySeat seat : this.seats) {
                if (seat != null && seat.field_70153_n != null && !seat.field_70153_n.field_70128_L) {
                    ++num;
                }
            }
        }
        return num;
    }
    
    public void mountMobToSeats() {
        final List list = this.field_70170_p.func_72872_a(W_Lib.getEntityLivingBaseClass(), this.field_70121_D.func_72314_b(3.0, 2.0, 3.0));
        for (int i = 0; i < list.size(); ++i) {
            final Entity entity = list.get(i);
            if (!(entity instanceof EntityPlayer)) {
                if (entity.field_70154_o == null) {
                    int sid = 1;
                    for (final MCH_EntitySeat seat : this.getSeats()) {
                        if (seat != null && seat.field_70153_n == null && !this.isMountedEntity(entity) && this.canRideSeatOrRack(sid, entity)) {
                            if (this.getSeatInfo(sid) instanceof MCH_SeatRackInfo) {
                                break;
                            }
                            entity.func_70078_a((Entity)seat);
                        }
                        ++sid;
                    }
                }
            }
        }
    }
    
    public void mountEntityToRack() {
        final MCH_Config config = MCH_MOD.config;
        if (!MCH_Config.EnablePutRackInFlying.prmBool) {
            if (this.getCurrentThrottle() > 0.3) {
                return;
            }
            final Block block = MCH_Lib.getBlockY(this, 1, -3, true);
            if (block == null || W_Block.isEqual(block, Blocks.field_150350_a)) {
                return;
            }
        }
        int countRideEntity = 0;
        for (int sid = 0; sid < this.getSeatNum(); ++sid) {
            final MCH_EntitySeat seat = this.getSeat(sid);
            if (this.getSeatInfo(1 + sid) instanceof MCH_SeatRackInfo && seat != null && seat.field_70153_n == null) {
                final MCH_SeatRackInfo info = (MCH_SeatRackInfo)this.getSeatInfo(1 + sid);
                final Vec3 rotVec3;
                final Vec3 v = rotVec3 = MCH_Lib.RotVec3(info.getEntryPos().field_72450_a, info.getEntryPos().field_72448_b, info.getEntryPos().field_72449_c, -this.getRotYaw(), -this.getRotPitch(), -this.getRotRoll());
                rotVec3.field_72450_a += this.field_70165_t;
                final Vec3 vec3 = v;
                vec3.field_72448_b += this.field_70163_u;
                final Vec3 vec4 = v;
                vec4.field_72449_c += this.field_70161_v;
                final AxisAlignedBB bb = AxisAlignedBB.func_72330_a(v.field_72450_a, v.field_72448_b, v.field_72449_c, v.field_72450_a, v.field_72448_b, v.field_72449_c);
                final float range = info.range;
                final List list = this.field_70170_p.func_72839_b((Entity)this, bb.func_72314_b((double)range, (double)range, (double)range));
                for (int i = 0; i < list.size(); ++i) {
                    final Entity entity = list.get(i);
                    if (this.canRideSeatOrRack(1 + sid, entity)) {
                        if (entity instanceof MCH_IEntityCanRideAircraft) {
                            if (((MCH_IEntityCanRideAircraft)entity).canRideAircraft(this, sid, info)) {
                                MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityAircraft.mountEntityToRack:%d:%s", sid, entity);
                                entity.func_70078_a((Entity)seat);
                                ++countRideEntity;
                                break;
                            }
                        }
                        else if (entity.field_70154_o == null) {
                            final NBTTagCompound nbt = entity.getEntityData();
                            if (nbt.func_74764_b("CanMountEntity") && nbt.func_74767_n("CanMountEntity")) {
                                MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityAircraft.mountEntityToRack:%d:%s:%s", sid, entity, entity.getClass());
                                entity.func_70078_a((Entity)seat);
                                ++countRideEntity;
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (countRideEntity > 0) {
            W_WorldFunc.DEF_playSoundEffect(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, "random.click", 1.0f, 1.0f);
        }
    }
    
    public void unmountEntityFromRack() {
        int sid = this.getSeatNum() - 1;
        while (sid >= 0) {
            final MCH_EntitySeat seat = this.getSeat(sid);
            if (this.getSeatInfo(sid + 1) instanceof MCH_SeatRackInfo && seat != null && seat.field_70153_n != null) {
                final MCH_SeatRackInfo info = (MCH_SeatRackInfo)this.getSeatInfo(sid + 1);
                final Entity entity = seat.field_70153_n;
                Vec3 pos = info.getEntryPos();
                if (entity instanceof MCH_EntityAircraft) {
                    if (pos.field_72449_c >= this.getAcInfo().bbZ) {
                        pos = pos.func_72441_c(0.0, 0.0, 12.0);
                    }
                    else {
                        pos = pos.func_72441_c(0.0, 0.0, -12.0);
                    }
                }
                final Vec3 v = MCH_Lib.RotVec3(pos.field_72450_a, pos.field_72448_b, pos.field_72449_c, -this.getRotYaw(), -this.getRotPitch(), -this.getRotRoll());
                final MCH_EntitySeat mch_EntitySeat = seat;
                final Entity entity2 = entity;
                final double n = this.field_70165_t + v.field_72450_a;
                entity2.field_70165_t = n;
                mch_EntitySeat.field_70165_t = n;
                final MCH_EntitySeat mch_EntitySeat2 = seat;
                final Entity entity3 = entity;
                final double n2 = this.field_70163_u + v.field_72448_b;
                entity3.field_70163_u = n2;
                mch_EntitySeat2.field_70163_u = n2;
                final MCH_EntitySeat mch_EntitySeat3 = seat;
                final Entity entity4 = entity;
                final double n3 = this.field_70161_v + v.field_72449_c;
                entity4.field_70161_v = n3;
                mch_EntitySeat3.field_70161_v = n3;
                final UnmountReserve ur = new UnmountReserve(entity, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v);
                ur.cnt = 8;
                this.listUnmountReserve.add(ur);
                entity.func_70078_a((Entity)null);
                if (MCH_Lib.getBlockIdY(this, 3, -20) > 0) {
                    MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityAircraft.unmountEntityFromRack:%d:%s", sid, entity);
                    break;
                }
                MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityAircraft.unmountEntityFromRack:%d Parachute:%s", sid, entity);
                this.dropEntityParachute(entity);
                break;
            }
            else {
                --sid;
            }
        }
    }
    
    public void dropEntityParachute(final Entity entity) {
        entity.field_70159_w = this.field_70159_w;
        entity.field_70181_x = this.field_70181_x;
        entity.field_70179_y = this.field_70179_y;
        final MCH_EntityParachute parachute = new MCH_EntityParachute(this.field_70170_p, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v);
        parachute.field_70177_z = entity.field_70177_z;
        parachute.field_70159_w = entity.field_70159_w;
        parachute.field_70181_x = entity.field_70181_x;
        parachute.field_70179_y = entity.field_70179_y;
        parachute.field_70143_R = entity.field_70143_R;
        parachute.user = entity;
        parachute.setType(3);
        this.field_70170_p.func_72838_d((Entity)parachute);
    }
    
    public void rideRack() {
        if (this.field_70154_o != null) {
            return;
        }
        final AxisAlignedBB bb = this.func_70046_E();
        final List list = this.field_70170_p.func_72839_b((Entity)this, bb.func_72314_b(60.0, 60.0, 60.0));
        for (int i = 0; i < list.size(); ++i) {
            final Entity entity = list.get(i);
            if (entity instanceof MCH_EntityAircraft) {
                final MCH_EntityAircraft ac = (MCH_EntityAircraft)entity;
                if (ac.getAcInfo() != null) {
                    for (int sid = 0; sid < ac.getSeatNum(); ++sid) {
                        final MCH_SeatInfo seatInfo = ac.getSeatInfo(1 + sid);
                        if (seatInfo instanceof MCH_SeatRackInfo) {
                            if (ac.canRideSeatOrRack(1 + sid, entity)) {
                                final MCH_SeatRackInfo info = (MCH_SeatRackInfo)seatInfo;
                                final MCH_EntitySeat seat = ac.getSeat(sid);
                                if (seat != null && seat.field_70153_n == null) {
                                    final Vec3 v = ac.getTransformedPosition(info.getEntryPos());
                                    final float r = info.range;
                                    if (this.field_70165_t >= v.field_72450_a - r && this.field_70165_t <= v.field_72450_a + r && this.field_70163_u >= v.field_72448_b - r && this.field_70163_u <= v.field_72448_b + r && this.field_70161_v >= v.field_72449_c - r && this.field_70161_v <= v.field_72449_c + r && this.canRideAircraft(ac, sid, info)) {
                                        W_WorldFunc.DEF_playSoundEffect(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, "random.click", 1.0f, 1.0f);
                                        this.func_70078_a((Entity)seat);
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public boolean canPutToRack() {
        for (int i = 0; i < this.getSeatNum(); ++i) {
            final MCH_EntitySeat seat = this.getSeat(i);
            final MCH_SeatInfo seatInfo = this.getSeatInfo(i + 1);
            if (seat != null && seat.field_70153_n == null && seatInfo instanceof MCH_SeatRackInfo) {
                return true;
            }
        }
        return false;
    }
    
    public boolean canDownFromRack() {
        for (int i = 0; i < this.getSeatNum(); ++i) {
            final MCH_EntitySeat seat = this.getSeat(i);
            final MCH_SeatInfo seatInfo = this.getSeatInfo(i + 1);
            if (seat != null && seat.field_70153_n != null && seatInfo instanceof MCH_SeatRackInfo) {
                return true;
            }
        }
        return false;
    }
    
    public void checkRideRack() {
        if (this.getCountOnUpdate() % 10 != 0) {
            return;
        }
        this.canRideRackStatus = false;
        if (this.field_70154_o != null) {
            return;
        }
        final AxisAlignedBB bb = this.func_70046_E();
        final List list = this.field_70170_p.func_72839_b((Entity)this, bb.func_72314_b(60.0, 60.0, 60.0));
        for (int i = 0; i < list.size(); ++i) {
            final Entity entity = list.get(i);
            if (entity instanceof MCH_EntityAircraft) {
                final MCH_EntityAircraft ac = (MCH_EntityAircraft)entity;
                if (ac.getAcInfo() != null) {
                    for (int sid = 0; sid < ac.getSeatNum(); ++sid) {
                        final MCH_SeatInfo seatInfo = ac.getSeatInfo(1 + sid);
                        if (seatInfo instanceof MCH_SeatRackInfo) {
                            final MCH_SeatRackInfo info = (MCH_SeatRackInfo)seatInfo;
                            final MCH_EntitySeat seat = ac.getSeat(sid);
                            if (seat != null && seat.field_70153_n == null) {
                                final Vec3 v = ac.getTransformedPosition(info.getEntryPos());
                                final float r = info.range;
                                final boolean rx = this.field_70165_t >= v.field_72450_a - r && this.field_70165_t <= v.field_72450_a + r;
                                final boolean ry = this.field_70163_u >= v.field_72448_b - r && this.field_70163_u <= v.field_72448_b + r;
                                final boolean rz = this.field_70161_v >= v.field_72449_c - r && this.field_70161_v <= v.field_72449_c + r;
                                if (this.field_70165_t >= v.field_72450_a - r && this.field_70165_t <= v.field_72450_a + r && this.field_70163_u >= v.field_72448_b - r && this.field_70163_u <= v.field_72448_b + r && this.field_70161_v >= v.field_72449_c - r && this.field_70161_v <= v.field_72449_c + r && this.canRideAircraft(ac, sid, info)) {
                                    this.canRideRackStatus = true;
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public boolean canRideRack() {
        return this.field_70154_o == null && this.canRideRackStatus;
    }
    
    @Override
    public boolean canRideAircraft(final MCH_EntityAircraft ac, final int seatID, final MCH_SeatRackInfo info) {
        if (this.getAcInfo() == null) {
            return false;
        }
        if (ac.field_70154_o != null) {
            return false;
        }
        if (this.field_70154_o != null) {
            return false;
        }
        boolean canRide = false;
        for (final String s : info.names) {
            if (s.equalsIgnoreCase(this.getAcInfo().name) || s.equalsIgnoreCase(this.getAcInfo().getKindName())) {
                canRide = true;
                break;
            }
        }
        if (!canRide) {
            for (final MCH_AircraftInfo.RideRack rr : this.getAcInfo().rideRacks) {
                final int id = ac.getAcInfo().getNumSeat() - 1 + (rr.rackID - 1);
                if (id == seatID && rr.name.equalsIgnoreCase(ac.getAcInfo().name)) {
                    final MCH_EntitySeat seat = ac.getSeat(ac.getAcInfo().getNumSeat() - 1 + rr.rackID - 1);
                    if (seat != null && seat.field_70153_n == null) {
                        canRide = true;
                        break;
                    }
                    continue;
                }
            }
            if (!canRide) {
                return false;
            }
        }
        final MCH_EntitySeat[] arr$2 = this.getSeats();
        for (int len$ = arr$2.length, i$ = 0; i$ < len$; ++i$) {
            final MCH_EntitySeat seat = arr$2[i$];
            if (seat != null && seat.field_70153_n instanceof MCH_IEntityCanRideAircraft) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isMountedEntity(final Entity entity) {
        return entity != null && this.isMountedEntity(W_Entity.getEntityId(entity));
    }
    
    public EntityPlayer getFirstMountPlayer() {
        if (this.getRiddenByEntity() instanceof EntityPlayer) {
            return (EntityPlayer)this.getRiddenByEntity();
        }
        for (final MCH_EntitySeat seat : this.getSeats()) {
            if (seat != null && seat.field_70153_n instanceof EntityPlayer) {
                return (EntityPlayer)seat.field_70153_n;
            }
        }
        return null;
    }
    
    public boolean isMountedSameTeamEntity(final EntityLivingBase player) {
        if (player == null || player.func_96124_cp() == null) {
            return false;
        }
        if (this.field_70153_n instanceof EntityLivingBase && player.func_142014_c((EntityLivingBase)this.field_70153_n)) {
            return true;
        }
        for (final MCH_EntitySeat seat : this.getSeats()) {
            if (seat != null && seat.field_70153_n instanceof EntityLivingBase && player.func_142014_c((EntityLivingBase)seat.field_70153_n)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isMountedOtherTeamEntity(final EntityLivingBase player) {
        if (player == null) {
            return false;
        }
        EntityLivingBase target = null;
        if (this.field_70153_n instanceof EntityLivingBase) {
            target = (EntityLivingBase)this.field_70153_n;
            if (player.func_96124_cp() != null && target.func_96124_cp() != null && !player.func_142014_c(target)) {
                return true;
            }
        }
        for (final MCH_EntitySeat seat : this.getSeats()) {
            if (seat != null && seat.field_70153_n instanceof EntityLivingBase) {
                target = (EntityLivingBase)seat.field_70153_n;
                if (player.func_96124_cp() != null && target.func_96124_cp() != null && !player.func_142014_c(target)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean isMountedEntity(final int entityId) {
        if (W_Entity.getEntityId(this.field_70153_n) == entityId) {
            return true;
        }
        for (final MCH_EntitySeat seat : this.getSeats()) {
            if (seat != null && seat.field_70153_n != null && W_Entity.getEntityId(seat.field_70153_n) == entityId) {
                return true;
            }
        }
        return false;
    }
    
    public void onInteractFirst(final EntityPlayer player) {
    }
    
    public boolean checkTeam(final EntityPlayer player) {
        for (int i = 0; i < 1 + this.getSeatNum(); ++i) {
            final Entity entity = this.getEntityBySeatId(i);
            if (entity instanceof EntityPlayer) {
                final EntityPlayer riddenPlayer = (EntityPlayer)entity;
                if (riddenPlayer.func_96124_cp() != null && !riddenPlayer.func_142014_c((EntityLivingBase)player)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean interactFirst(final EntityPlayer player, final boolean ss) {
        this.switchSeat = ss;
        final boolean ret = this.func_130002_c(player);
        this.switchSeat = false;
        return ret;
    }
    
    public boolean func_130002_c(final EntityPlayer player) {
        if (this.isDestroyed()) {
            return false;
        }
        if (this.getAcInfo() == null) {
            return false;
        }
        if (!this.checkTeam(player)) {
            return false;
        }
        final ItemStack itemStack = player.func_71045_bC();
        if (itemStack != null && itemStack.func_77973_b() instanceof MCH_ItemWrench) {
            if (!this.field_70170_p.field_72995_K && player.func_70093_af()) {
                this.switchNextTextureName();
            }
            return false;
        }
        if (player.func_70093_af()) {
            super.openInventory(player);
            return false;
        }
        if (!this.getAcInfo().canRide) {
            return false;
        }
        if (this.field_70153_n != null || this.isUAV()) {
            return this.interactFirstSeat(player);
        }
        if (player.field_70154_o instanceof MCH_EntitySeat) {
            return false;
        }
        if (!this.canRideSeatOrRack(0, (Entity)player)) {
            return false;
        }
        if (!this.switchSeat) {
            if (this.getAcInfo().haveCanopy() && this.isCanopyClose()) {
                this.openCanopy();
                return false;
            }
            if (this.getModeSwitchCooldown() > 0) {
                return false;
            }
        }
        this.closeCanopy();
        this.field_70153_n = null;
        this.lastRiddenByEntity = null;
        this.initRadar();
        if (!this.field_70170_p.field_72995_K) {
            player.func_70078_a((Entity)this);
            if (!this.keepOnRideRotation) {
                this.mountMobToSeats();
            }
        }
        else {
            this.updateClientSettings(0);
        }
        this.setCameraId(0);
        this.initPilotWeapon();
        this.lowPassPartialTicks.clear();
        if (this.getAcInfo().name.equalsIgnoreCase("uh-1c")) {
            MCH_Achievement.addStat(this.field_70153_n, MCH_Achievement.rideValkyries, 1);
        }
        this.onInteractFirst(player);
        return true;
    }
    
    public boolean canRideSeatOrRack(final int seatId, final Entity entity) {
        if (this.getAcInfo() == null) {
            return false;
        }
        for (final Integer[] a : this.getAcInfo().exclusionSeatList) {
            if (Arrays.asList(a).contains(seatId)) {
                for (final int id : a) {
                    if (this.getEntityBySeatId(id) != null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    public void updateClientSettings(final int seatId) {
        final MCH_Config config = MCH_MOD.config;
        this.cs_dismountAll = MCH_Config.DismountAll.prmBool;
        final MCH_Config config2 = MCH_MOD.config;
        this.cs_heliAutoThrottleDown = MCH_Config.AutoThrottleDownHeli.prmBool;
        final MCH_Config config3 = MCH_MOD.config;
        this.cs_planeAutoThrottleDown = MCH_Config.AutoThrottleDownPlane.prmBool;
        final MCH_Config config4 = MCH_MOD.config;
        this.cs_tankAutoThrottleDown = MCH_Config.AutoThrottleDownTank.prmBool;
        this.camera.setShaderSupport(seatId, W_EntityRenderer.isShaderSupport());
        MCH_PacketNotifyClientSetting.send();
    }
    
    @Override
    public boolean canLockEntity(final Entity entity) {
        return !this.isMountedEntity(entity);
    }
    
    public void switchNextSeat(final Entity entity) {
        if (entity == null) {
            return;
        }
        if (this.seats == null || this.seats.length <= 0) {
            return;
        }
        if (!this.isMountedEntity(entity)) {
            return;
        }
        boolean isFound = false;
        int sid = 1;
        for (final MCH_EntitySeat seat : this.seats) {
            if (seat != null) {
                if (this.getSeatInfo(sid) instanceof MCH_SeatRackInfo) {
                    break;
                }
                if (W_Entity.isEqual(seat.field_70153_n, entity)) {
                    isFound = true;
                }
                else if (isFound && seat.field_70153_n == null) {
                    entity.func_70078_a((Entity)seat);
                    return;
                }
                ++sid;
            }
        }
        sid = 1;
        final MCH_EntitySeat[] arr$ = this.seats;
        final int len$ = arr$.length;
        int i$ = 0;
        while (i$ < len$) {
            final MCH_EntitySeat seat = arr$[i$];
            if (seat != null && seat.field_70153_n == null) {
                if (this.getSeatInfo(sid) instanceof MCH_SeatRackInfo) {
                    break;
                }
                entity.func_70078_a((Entity)seat);
                this.onMountPlayerSeat(seat, entity);
            }
            else {
                ++sid;
                ++i$;
            }
        }
    }
    
    public void switchPrevSeat(final Entity entity) {
        if (entity == null) {
            return;
        }
        if (this.seats == null || this.seats.length <= 0) {
            return;
        }
        if (!this.isMountedEntity(entity)) {
            return;
        }
        boolean isFound = false;
        for (int i = this.seats.length - 1; i >= 0; --i) {
            final MCH_EntitySeat seat = this.seats[i];
            if (seat != null) {
                if (W_Entity.isEqual(seat.field_70153_n, entity)) {
                    isFound = true;
                }
                else if (isFound && seat.field_70153_n == null) {
                    entity.func_70078_a((Entity)seat);
                    return;
                }
            }
        }
        for (int i = this.seats.length - 1; i >= 0; --i) {
            final MCH_EntitySeat seat = this.seats[i];
            if (!(this.getSeatInfo(i + 1) instanceof MCH_SeatRackInfo)) {
                if (seat != null && seat.field_70153_n == null) {
                    entity.func_70078_a((Entity)seat);
                    return;
                }
            }
        }
    }
    
    public Entity[] func_70021_al() {
        return this.partEntities;
    }
    
    public float getSoundVolume() {
        return 1.0f;
    }
    
    public float getSoundPitch() {
        return 1.0f;
    }
    
    public abstract String getDefaultSoundName();
    
    public String getSoundName() {
        if (this.getAcInfo() == null) {
            return "";
        }
        return this.getAcInfo().soundMove.isEmpty() ? this.getDefaultSoundName() : this.getAcInfo().soundMove;
    }
    
    @Override
    public boolean isSkipNormalRender() {
        return this.field_70154_o instanceof MCH_EntitySeat;
    }
    
    public boolean isRenderBullet(final Entity entity, final Entity rider) {
        return !this.isCameraView(rider) || !W_Entity.isEqual(this.getTVMissile(), entity) || !W_Entity.isEqual(this.getTVMissile().shootingEntity, rider);
    }
    
    public boolean isCameraView(final Entity entity) {
        return this.getIsGunnerMode(entity) || this.isUAV();
    }
    
    public void updateCamera(final double x, final double y, final double z) {
        if (!this.field_70170_p.field_72995_K) {
            return;
        }
        if (this.getTVMissile() != null) {
            this.camera.setPosition(this.TVmissile.field_70165_t, this.TVmissile.field_70163_u, this.TVmissile.field_70161_v);
            this.camera.setCameraZoom(1.0f);
            this.TVmissile.isSpawnParticle = !this.isMissileCameraMode(this.TVmissile.shootingEntity);
        }
        else {
            this.setTVMissile(null);
            final MCH_AircraftInfo.CameraPosition cpi = this.getCameraPosInfo();
            final Vec3 cp = (cpi != null) ? cpi.pos : Vec3.func_72443_a(0.0, 0.0, 0.0);
            final Vec3 v = MCH_Lib.RotVec3(cp, -this.getRotYaw(), -this.getRotPitch(), -this.getRotRoll());
            this.camera.setPosition(x + v.field_72450_a, y + v.field_72448_b, z + v.field_72449_c);
        }
    }
    
    public void updateCameraRotate(final float yaw, final float pitch) {
        this.camera.prevRotationYaw = this.camera.rotationYaw;
        this.camera.prevRotationPitch = this.camera.rotationPitch;
        this.camera.rotationYaw = yaw;
        this.camera.rotationPitch = pitch;
    }
    
    public void updatePartCameraRotate() {
        if (this.field_70170_p.field_72995_K) {
            Entity e = this.getEntityBySeatId(1);
            if (e == null) {
                e = this.getRiddenByEntity();
            }
            if (e != null) {
                this.camera.partRotationYaw = e.field_70177_z;
                final float pitch = e.field_70125_A;
                this.camera.prevPartRotationYaw = this.camera.partRotationYaw;
                this.camera.prevPartRotationPitch = this.camera.partRotationPitch;
                this.camera.partRotationPitch = pitch;
            }
        }
    }
    
    public void setTVMissile(final MCH_EntityTvMissile entity) {
        this.TVmissile = entity;
    }
    
    public MCH_EntityTvMissile getTVMissile() {
        return (this.TVmissile != null && !this.TVmissile.field_70128_L) ? this.TVmissile : null;
    }
    
    public MCH_WeaponSet[] createWeapon(final int seat_num) {
        this.currentWeaponID = new int[seat_num];
        for (int i = 0; i < this.currentWeaponID.length; ++i) {
            this.currentWeaponID[i] = -1;
        }
        if (this.getAcInfo() == null || this.getAcInfo().weaponSetList.size() <= 0 || seat_num <= 0) {
            return new MCH_WeaponSet[] { this.dummyWeapon };
        }
        final MCH_WeaponSet[] weaponSetArray = new MCH_WeaponSet[this.getAcInfo().weaponSetList.size()];
        for (int j = 0; j < this.getAcInfo().weaponSetList.size(); ++j) {
            final MCH_AircraftInfo.WeaponSet ws = this.getAcInfo().weaponSetList.get(j);
            final MCH_WeaponBase[] wb = new MCH_WeaponBase[ws.weapons.size()];
            for (int k = 0; k < ws.weapons.size(); ++k) {
                wb[k] = MCH_WeaponCreator.createWeapon(this.field_70170_p, ws.type, ws.weapons.get(k).pos, ws.weapons.get(k).yaw, ws.weapons.get(k).pitch, this, ws.weapons.get(k).turret);
                wb[k].aircraft = this;
            }
            if (wb.length > 0 && wb[0] != null) {
                final float defYaw = ws.weapons.get(0).defaultYaw;
                weaponSetArray[j] = new MCH_WeaponSet(wb);
                weaponSetArray[j].prevRotationYaw = defYaw;
                weaponSetArray[j].rotationYaw = defYaw;
                weaponSetArray[j].defaultRotationYaw = defYaw;
            }
        }
        return weaponSetArray;
    }
    
    public void switchWeapon(final Entity entity, int id) {
        final int sid = this.getSeatIdByEntity(entity);
        if (!this.isValidSeatID(sid)) {
            return;
        }
        final int beforeWeaponID = this.currentWeaponID[sid];
        if (this.getWeaponNum() <= 0 || this.currentWeaponID.length <= 0) {
            return;
        }
        if (id < 0) {
            this.currentWeaponID[sid] = -1;
        }
        if (id >= this.getWeaponNum()) {
            id = this.getWeaponNum() - 1;
        }
        MCH_Lib.DbgLog(this.field_70170_p, "switchWeapon:" + W_Entity.getEntityId(entity) + " -> " + id, new Object[0]);
        this.getCurrentWeapon(entity).reload();
        this.currentWeaponID[sid] = id;
        final MCH_WeaponSet ws = this.getCurrentWeapon(entity);
        ws.onSwitchWeapon(this.field_70170_p.field_72995_K, this.isInfinityAmmo(entity));
        if (!this.field_70170_p.field_72995_K) {
            MCH_PacketNotifyWeaponID.send(this, sid, id, ws.getAmmoNum(), ws.getRestAllAmmoNum());
        }
    }
    
    public void updateWeaponID(final int sid, int id) {
        if (sid < 0 || sid >= this.currentWeaponID.length) {
            return;
        }
        if (this.getWeaponNum() <= 0 || this.currentWeaponID.length <= 0) {
            return;
        }
        if (id < 0) {
            this.currentWeaponID[sid] = -1;
        }
        if (id >= this.getWeaponNum()) {
            id = this.getWeaponNum() - 1;
        }
        MCH_Lib.DbgLog(this.field_70170_p, "switchWeapon:seatID=" + sid + ", WeaponID=" + id, new Object[0]);
        this.currentWeaponID[sid] = id;
    }
    
    public void updateWeaponRestAmmo(final int id, final int num) {
        if (id < this.getWeaponNum()) {
            this.getWeapon(id).setRestAllAmmoNum(num);
        }
    }
    
    public MCH_WeaponSet getWeaponByName(final String name) {
        for (final MCH_WeaponSet ws : this.weapons) {
            if (ws.isEqual(name)) {
                return ws;
            }
        }
        return null;
    }
    
    public int getWeaponIdByName(final String name) {
        int id = 0;
        for (final MCH_WeaponSet ws : this.weapons) {
            if (ws.isEqual(name)) {
                return id;
            }
            ++id;
        }
        return -1;
    }
    
    public void reloadAllWeapon() {
        for (int i = 0; i < this.getWeaponNum(); ++i) {
            this.getWeapon(i).reloadMag();
        }
    }
    
    public MCH_WeaponSet getFirstSeatWeapon() {
        if (this.currentWeaponID != null && this.currentWeaponID.length > 0 && this.currentWeaponID[0] >= 0) {
            return this.getWeapon(this.currentWeaponID[0]);
        }
        return this.getWeapon(0);
    }
    
    public void initCurrentWeapon(final Entity entity) {
        final int sid = this.getSeatIdByEntity(entity);
        MCH_Lib.DbgLog(this.field_70170_p, "initCurrentWeapon:" + W_Entity.getEntityId(entity) + ":%d", sid);
        if (sid < 0 || sid >= this.currentWeaponID.length) {
            return;
        }
        this.currentWeaponID[sid] = -1;
        if (entity instanceof EntityPlayer) {
            this.currentWeaponID[sid] = this.getNextWeaponID(entity, 1);
            this.switchWeapon(entity, this.getCurrentWeaponID(entity));
            if (this.field_70170_p.field_72995_K) {
                MCH_PacketIndNotifyAmmoNum.send(this, -1);
            }
        }
    }
    
    public void initPilotWeapon() {
        this.currentWeaponID[0] = -1;
    }
    
    public MCH_WeaponSet getCurrentWeapon(final Entity entity) {
        return this.getWeapon(this.getCurrentWeaponID(entity));
    }
    
    protected MCH_WeaponSet getWeapon(final int id) {
        if (id < 0 || this.weapons.length <= 0 || id >= this.weapons.length) {
            return this.dummyWeapon;
        }
        return this.weapons[id];
    }
    
    public int getWeaponIDBySeatID(final int sid) {
        if (sid < 0 || sid >= this.currentWeaponID.length) {
            return -1;
        }
        return this.currentWeaponID[sid];
    }
    
    public double getLandInDistance(final Entity user) {
        if (this.lastCalcLandInDistanceCount != this.getCountOnUpdate() && this.getCountOnUpdate() % 5 == 0) {
            this.lastCalcLandInDistanceCount = this.getCountOnUpdate();
            final MCH_WeaponParam prm = new MCH_WeaponParam();
            prm.setPosition(this.field_70165_t, this.field_70163_u, this.field_70161_v);
            prm.entity = this;
            prm.user = user;
            prm.isInfinity = this.isInfinityAmmo(prm.user);
            if (prm.user != null) {
                final MCH_WeaponSet currentWs = this.getCurrentWeapon(prm.user);
                if (currentWs != null) {
                    final int sid = this.getSeatIdByEntity(prm.user);
                    if (this.getAcInfo().getWeaponSetById(sid) != null) {
                        prm.isTurret = this.getAcInfo().getWeaponSetById(sid).weapons.get(0).turret;
                    }
                    this.lastLandInDistance = currentWs.getLandInDistance(prm);
                }
            }
        }
        return this.lastLandInDistance;
    }
    
    public boolean useCurrentWeapon(final Entity user) {
        final MCH_WeaponParam prm = new MCH_WeaponParam();
        prm.setPosition(this.field_70165_t, this.field_70163_u, this.field_70161_v);
        prm.entity = this;
        prm.user = user;
        return this.useCurrentWeapon(prm);
    }
    
    public boolean useCurrentWeapon(final MCH_WeaponParam prm) {
        prm.isInfinity = this.isInfinityAmmo(prm.user);
        if (prm.user != null) {
            final MCH_WeaponSet currentWs = this.getCurrentWeapon(prm.user);
            if (currentWs != null && currentWs.canUse()) {
                final int sid = this.getSeatIdByEntity(prm.user);
                if (this.getAcInfo().getWeaponSetById(sid) != null) {
                    prm.isTurret = this.getAcInfo().getWeaponSetById(sid).weapons.get(0).turret;
                }
                final int lastUsedIndex = currentWs.getCurrentWeaponIndex();
                if (currentWs.use(prm)) {
                    for (final MCH_WeaponSet ws : this.weapons) {
                        if (ws != currentWs && !ws.getInfo().group.isEmpty() && ws.getInfo().group.equals(currentWs.getInfo().group)) {
                            ws.waitAndReloadByOther(prm.reload);
                        }
                    }
                    if (!this.field_70170_p.field_72995_K) {
                        int shift = 0;
                        for (final MCH_WeaponSet ws2 : this.weapons) {
                            if (ws2 == currentWs) {
                                break;
                            }
                            shift += ws2.getWeaponNum();
                        }
                        shift += lastUsedIndex;
                        this.useWeaponStat |= ((shift < 32) ? (1 << shift) : 0);
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    public void switchCurrentWeaponMode(final Entity entity) {
        this.getCurrentWeapon(entity).switchMode();
    }
    
    public int getWeaponNum() {
        return this.weapons.length;
    }
    
    public int getCurrentWeaponID(final Entity entity) {
        if (!(entity instanceof EntityPlayer)) {
            return -1;
        }
        final int id = this.getSeatIdByEntity(entity);
        return (id >= 0 && id < this.currentWeaponID.length) ? this.currentWeaponID[id] : -1;
    }
    
    public int getNextWeaponID(final Entity entity, final int step) {
        if (this.getAcInfo() == null) {
            return -1;
        }
        final int sid = this.getSeatIdByEntity(entity);
        if (sid < 0) {
            return -1;
        }
        int id = this.getCurrentWeaponID(entity);
        int i;
        for (i = 0; i < this.getWeaponNum(); ++i) {
            if (step >= 0) {
                id = (id + 1) % this.getWeaponNum();
            }
            else {
                id = ((id > 0) ? (id - 1) : (this.getWeaponNum() - 1));
            }
            final MCH_AircraftInfo.Weapon w = this.getAcInfo().getWeaponById(id);
            if (w != null) {
                final MCH_WeaponInfo wi = this.getWeaponInfoById(id);
                final int wpsid = this.getWeaponSeatID(wi, w);
                if (wpsid < this.getSeatNum() + 1 + 1) {
                    if (wpsid == sid) {
                        break;
                    }
                    if (sid == 0 && w.canUsePilot && !(this.getEntityBySeatId(wpsid) instanceof EntityPlayer)) {
                        break;
                    }
                }
            }
        }
        if (i >= this.getWeaponNum()) {
            return -1;
        }
        MCH_Lib.DbgLog(this.field_70170_p, "getNextWeaponID:%d:->%d", W_Entity.getEntityId(entity), id);
        return id;
    }
    
    public int getWeaponSeatID(final MCH_WeaponInfo wi, final MCH_AircraftInfo.Weapon w) {
        if (wi != null && (wi.target & 0xC3) == 0x0 && wi.type.isEmpty() && (MCH_MOD.proxy.isSinglePlayer() || MCH_Config.TestMode.prmBool)) {
            return 1000;
        }
        return w.seatID;
    }
    
    public boolean isMissileCameraMode(final Entity entity) {
        return this.getTVMissile() != null && this.isCameraView(entity);
    }
    
    public boolean isPilotReloading() {
        return this.getCommonStatus(2) || this.supplyAmmoWait > 0;
    }
    
    public int getUsedWeaponStat() {
        if (this.getAcInfo() == null) {
            return 0;
        }
        if (this.getAcInfo().getWeaponNum() <= 0) {
            return 0;
        }
        int stat = 0;
        int i = 0;
        for (final MCH_WeaponSet w : this.weapons) {
            if (i >= 32) {
                break;
            }
            for (int wi = 0; wi < w.getWeaponNum() && i < 32; ++i, ++wi) {
                stat |= (w.isUsed(wi) ? (1 << i) : 0);
            }
        }
        return stat;
    }
    
    public boolean isWeaponNotCooldown(final MCH_WeaponSet checkWs, final int index) {
        if (this.getAcInfo() == null) {
            return false;
        }
        if (this.getAcInfo().getWeaponNum() <= 0) {
            return false;
        }
        int shift = 0;
        for (final MCH_WeaponSet ws : this.weapons) {
            if (ws == checkWs) {
                break;
            }
            shift += ws.getWeaponNum();
        }
        shift += index;
        return shift < 32 && (this.useWeaponStat & 1 << shift) != 0x0;
    }
    
    public void updateWeapons() {
        if (this.getAcInfo() == null) {
            return;
        }
        if (this.getAcInfo().getWeaponNum() <= 0) {
            return;
        }
        final int prevUseWeaponStat = this.useWeaponStat;
        if (!this.field_70170_p.field_72995_K) {
            this.useWeaponStat |= this.getUsedWeaponStat();
            this.func_70096_w().func_75692_b(24, (Object)new Integer(this.useWeaponStat));
            this.useWeaponStat = 0;
        }
        else {
            this.useWeaponStat = this.func_70096_w().func_75679_c(24);
        }
        final float yaw = MathHelper.func_76142_g(this.getRotYaw());
        final float pitch = MathHelper.func_76142_g(this.getRotPitch());
        int id = 0;
        for (int wid = 0; wid < this.weapons.length; ++wid) {
            final MCH_WeaponSet w = this.weapons[wid];
            boolean isLongDelay = false;
            if (w.getFirstWeapon() != null) {
                isLongDelay = w.isLongDelayWeapon();
            }
            boolean isSelected = false;
            for (final int swid : this.currentWeaponID) {
                if (swid == wid) {
                    isSelected = true;
                    break;
                }
            }
            boolean isWpnUsed = false;
            for (int index = 0; index < w.getWeaponNum(); ++index) {
                final boolean isPrevUsed = id < 32 && (prevUseWeaponStat & 1 << id) != 0x0;
                boolean isUsed = id < 32 && (this.useWeaponStat & 1 << id) != 0x0;
                if (isLongDelay && isPrevUsed && isUsed) {
                    isUsed = false;
                }
                isWpnUsed |= isUsed;
                if (!isPrevUsed && isUsed) {
                    final float recoil = w.getInfo().recoil;
                    if (recoil > 0.0f) {
                        this.recoilCount = 30;
                        this.recoilValue = recoil;
                        this.recoilYaw = w.rotationYaw;
                    }
                }
                if (this.field_70170_p.field_72995_K && isUsed) {
                    final Vec3 wrv = MCH_Lib.RotVec3(0.0, 0.0, -1.0, -w.rotationYaw - yaw, -w.rotationPitch);
                    final Vec3 spv = w.getCurrentWeapon().getShotPos(this);
                    this.spawnParticleMuzzleFlash(this.field_70170_p, w.getInfo(), this.field_70165_t + spv.field_72450_a, this.field_70163_u + spv.field_72448_b, this.field_70161_v + spv.field_72449_c, wrv);
                }
                w.updateWeapon(this, isUsed, index);
                ++id;
            }
            w.update(this, isSelected, isWpnUsed);
            final MCH_AircraftInfo.Weapon wi = this.getAcInfo().getWeaponById(wid);
            if (wi != null && !this.isDestroyed()) {
                Entity entity = this.getEntityBySeatId(this.getWeaponSeatID(this.getWeaponInfoById(wid), wi));
                if (wi.canUsePilot && !(entity instanceof EntityPlayer)) {
                    entity = this.getEntityBySeatId(0);
                }
                if (entity instanceof EntityPlayer) {
                    if ((int)wi.minYaw != 0 || (int)wi.maxYaw != 0) {
                        final float ty = wi.turret ? (MathHelper.func_76142_g(this.getLastRiderYaw()) - yaw) : 0.0f;
                        final float ey = MathHelper.func_76142_g(entity.field_70177_z - yaw - wi.defaultYaw - ty);
                        if (Math.abs((int)wi.minYaw) < 360 && Math.abs((int)wi.maxYaw) < 360) {
                            final float targetYaw = MCH_Lib.RNG(ey, wi.minYaw, wi.maxYaw);
                            float wy = w.rotationYaw - wi.defaultYaw - ty;
                            if (targetYaw < wy) {
                                if (wy - targetYaw > 15.0f) {
                                    wy -= 15.0f;
                                }
                                else {
                                    wy = targetYaw;
                                }
                            }
                            else if (targetYaw > wy) {
                                if (targetYaw - wy > 15.0f) {
                                    wy += 15.0f;
                                }
                                else {
                                    wy = targetYaw;
                                }
                            }
                            w.rotationYaw = wy + wi.defaultYaw + ty;
                        }
                        else {
                            w.rotationYaw = ey + ty;
                        }
                    }
                    final float ep = MathHelper.func_76142_g(entity.field_70125_A - pitch);
                    w.rotationPitch = MCH_Lib.RNG(ep, wi.minPitch, wi.maxPitch);
                    w.rotationTurretYaw = 0.0f;
                }
                else {
                    w.rotationTurretYaw = this.getLastRiderYaw() - this.getRotYaw();
                    if (this.getTowedChainEntity() != null || this.field_70154_o != null) {
                        w.rotationYaw = 0.0f;
                    }
                }
            }
        }
        this.updateWeaponBay();
        if (this.hitStatus > 0) {
            --this.hitStatus;
        }
    }
    
    public void updateWeaponsRotation() {
        if (this.getAcInfo() == null) {
            return;
        }
        if (this.getAcInfo().getWeaponNum() <= 0) {
            return;
        }
        if (this.isDestroyed()) {
            return;
        }
        final float yaw = MathHelper.func_76142_g(this.getRotYaw());
        final float pitch = MathHelper.func_76142_g(this.getRotPitch());
        for (int wid = 0; wid < this.weapons.length; ++wid) {
            final MCH_WeaponSet w = this.weapons[wid];
            final MCH_AircraftInfo.Weapon wi = this.getAcInfo().getWeaponById(wid);
            if (wi != null) {
                Entity entity = this.getEntityBySeatId(this.getWeaponSeatID(this.getWeaponInfoById(wid), wi));
                if (wi.canUsePilot && !(entity instanceof EntityPlayer)) {
                    entity = this.getEntityBySeatId(0);
                }
                if (entity instanceof EntityPlayer) {
                    if ((int)wi.minYaw != 0 || (int)wi.maxYaw != 0) {
                        final float ty = wi.turret ? (MathHelper.func_76142_g(this.getLastRiderYaw()) - yaw) : 0.0f;
                        final float ey = MathHelper.func_76142_g(entity.field_70177_z - yaw - wi.defaultYaw - ty);
                        if (Math.abs((int)wi.minYaw) < 360 && Math.abs((int)wi.maxYaw) < 360) {
                            final float targetYaw = MCH_Lib.RNG(ey, wi.minYaw, wi.maxYaw);
                            float wy = w.rotationYaw - wi.defaultYaw - ty;
                            if (targetYaw < wy) {
                                if (wy - targetYaw > 15.0f) {
                                    wy -= 15.0f;
                                }
                                else {
                                    wy = targetYaw;
                                }
                            }
                            else if (targetYaw > wy) {
                                if (targetYaw - wy > 15.0f) {
                                    wy += 15.0f;
                                }
                                else {
                                    wy = targetYaw;
                                }
                            }
                            w.rotationYaw = wy + wi.defaultYaw + ty;
                        }
                        else {
                            w.rotationYaw = ey + ty;
                        }
                    }
                    final float ep = MathHelper.func_76142_g(entity.field_70125_A - pitch);
                    w.rotationPitch = MCH_Lib.RNG(ep, wi.minPitch, wi.maxPitch);
                    w.rotationTurretYaw = 0.0f;
                }
                else {
                    w.rotationTurretYaw = this.getLastRiderYaw() - this.getRotYaw();
                }
            }
            w.prevRotationYaw = w.rotationYaw;
        }
    }
    
    private void spawnParticleMuzzleFlash(final World w, final MCH_WeaponInfo wi, final double px, final double py, final double pz, final Vec3 wrv) {
        if (wi.listMuzzleFlashSmoke != null) {
            for (final MCH_WeaponInfo.MuzzleFlash mf : wi.listMuzzleFlashSmoke) {
                final double x = px + -wrv.field_72450_a * mf.dist;
                final double y = py + -wrv.field_72448_b * mf.dist;
                final double z = pz + -wrv.field_72449_c * mf.dist;
                final MCH_ParticleParam p = new MCH_ParticleParam(w, "smoke", px, py, pz);
                p.size = mf.size;
                for (int i = 0; i < mf.num; ++i) {
                    p.a = mf.a * 0.9f + w.field_73012_v.nextFloat() * 0.1f;
                    final float color = w.field_73012_v.nextFloat() * 0.1f;
                    p.r = color + mf.r * 0.9f;
                    p.g = color + mf.g * 0.9f;
                    p.b = color + mf.b * 0.9f;
                    p.age = (int)(mf.age + 0.1 * mf.age * w.field_73012_v.nextFloat());
                    p.posX = x + (w.field_73012_v.nextDouble() - 0.5) * mf.range;
                    p.posY = y + (w.field_73012_v.nextDouble() - 0.5) * mf.range;
                    p.posZ = z + (w.field_73012_v.nextDouble() - 0.5) * mf.range;
                    p.motionX = w.field_73012_v.nextDouble() * ((p.posX < x) ? -0.2 : 0.2);
                    p.motionY = w.field_73012_v.nextDouble() * ((p.posY < y) ? -0.03 : 0.03);
                    p.motionZ = w.field_73012_v.nextDouble() * ((p.posZ < z) ? -0.2 : 0.2);
                    MCH_ParticlesUtil.spawnParticle(p);
                }
            }
        }
        if (wi.listMuzzleFlash != null) {
            for (final MCH_WeaponInfo.MuzzleFlash mf : wi.listMuzzleFlash) {
                final float color2 = this.field_70146_Z.nextFloat() * 0.1f + 0.9f;
                MCH_ParticlesUtil.spawnParticleExplode(this.field_70170_p, px + -wrv.field_72450_a * mf.dist, py + -wrv.field_72448_b * mf.dist, pz + -wrv.field_72449_c * mf.dist, mf.size, color2 * mf.r, color2 * mf.g, color2 * mf.b, mf.a, mf.age + w.field_73012_v.nextInt(3));
            }
        }
    }
    
    private void updateWeaponBay() {
        for (int i = 0; i < this.weaponBays.length; ++i) {
            final WeaponBay wb = this.weaponBays[i];
            final MCH_AircraftInfo.WeaponBay info = this.getAcInfo().partWeaponBay.get(i);
            boolean isSelected = false;
            for (final int wid : info.weaponIds) {
                for (int sid = 0; sid < this.currentWeaponID.length; ++sid) {
                    if (wid == this.currentWeaponID[sid] && this.getEntityBySeatId(sid) != null) {
                        isSelected = true;
                    }
                }
            }
            wb.prevRot = wb.rot;
            if (isSelected) {
                if (wb.rot < 90.0f) {
                    final WeaponBay weaponBay = wb;
                    weaponBay.rot += 3.0f;
                }
                if (wb.rot >= 90.0f) {
                    wb.rot = 90.0f;
                }
            }
            else {
                if (wb.rot > 0.0f) {
                    final WeaponBay weaponBay2 = wb;
                    weaponBay2.rot -= 3.0f;
                }
                if (wb.rot <= 0.0f) {
                    wb.rot = 0.0f;
                }
            }
        }
    }
    
    public int getHitStatus() {
        return this.hitStatus;
    }
    
    public int getMaxHitStatus() {
        return 15;
    }
    
    public void hitBullet() {
        this.hitStatus = this.getMaxHitStatus();
    }
    
    public void initRotationYaw(final float yaw) {
        this.field_70177_z = yaw;
        this.field_70126_B = yaw;
        this.lastRiderYaw = yaw;
        this.lastSearchLightYaw = yaw;
        for (final MCH_WeaponSet w : this.weapons) {
            w.rotationYaw = w.defaultRotationYaw;
            w.rotationPitch = 0.0f;
        }
    }
    
    public MCH_AircraftInfo getAcInfo() {
        return this.acInfo;
    }
    
    public abstract Item getItem();
    
    public void setAcInfo(final MCH_AircraftInfo info) {
        this.acInfo = info;
        if (info != null) {
            this.partHatch = this.createHatch();
            this.partCanopy = this.createCanopy();
            this.partLandingGear = this.createLandingGear();
            this.weaponBays = this.createWeaponBays();
            this.rotPartRotation = new float[info.partRotPart.size()];
            this.prevRotPartRotation = new float[info.partRotPart.size()];
            this.extraBoundingBox = this.createExtraBoundingBox();
            this.partEntities = this.createParts();
            this.field_70138_W = info.stepHeight;
        }
    }
    
    public MCH_BoundingBox[] createExtraBoundingBox() {
        final MCH_BoundingBox[] ar = new MCH_BoundingBox[this.getAcInfo().extraBoundingBox.size()];
        int i = 0;
        for (final MCH_BoundingBox bb : this.getAcInfo().extraBoundingBox) {
            ar[i] = bb.copy();
            ++i;
        }
        return ar;
    }
    
    public Entity[] createParts() {
        final Entity[] list = { this.partEntities[0] };
        return list;
    }
    
    public void updateUAV() {
        if (!this.isUAV()) {
            return;
        }
        if (this.field_70170_p.field_72995_K) {
            final int eid = this.func_70096_w().func_75679_c(22);
            if (eid > 0) {
                if (this.uavStation == null) {
                    final Entity uavEntity = this.field_70170_p.func_73045_a(eid);
                    if (uavEntity instanceof MCH_EntityUavStation) {
                        (this.uavStation = (MCH_EntityUavStation)uavEntity).setControlAircract(this);
                    }
                }
            }
            else if (this.uavStation != null) {
                this.uavStation.setControlAircract(null);
                this.uavStation = null;
            }
        }
        else if (this.uavStation != null) {
            final double udx = this.field_70165_t - this.uavStation.field_70165_t;
            final double udz = this.field_70161_v - this.uavStation.field_70161_v;
            if (udx * udx + udz * udz > 15129.0) {
                this.uavStation.setControlAircract(null);
                this.setUavStation(null);
                this.attackEntityFrom(DamageSource.field_76380_i, this.getMaxHP() + 10);
            }
        }
        if (this.uavStation != null && this.uavStation.field_70128_L) {
            this.uavStation = null;
        }
    }
    
    public void switchGunnerMode(final boolean mode) {
        final boolean debug_bk_mode = this.isGunnerMode;
        final Entity pilot = this.getEntityBySeatId(0);
        if (!mode || this.canSwitchGunnerMode()) {
            if (this.isGunnerMode && !mode) {
                this.setCurrentThrottle(this.beforeHoverThrottle);
                this.isGunnerMode = false;
                this.camera.setCameraZoom(1.0f);
                this.getCurrentWeapon(pilot).onSwitchWeapon(this.field_70170_p.field_72995_K, this.isInfinityAmmo(pilot));
            }
            else if (!this.isGunnerMode && mode) {
                this.beforeHoverThrottle = this.getCurrentThrottle();
                this.isGunnerMode = true;
                this.camera.setCameraZoom(1.0f);
                this.getCurrentWeapon(pilot).onSwitchWeapon(this.field_70170_p.field_72995_K, this.isInfinityAmmo(pilot));
            }
        }
        MCH_Lib.DbgLog(this.field_70170_p, "switchGunnerMode %s->%s", debug_bk_mode ? "ON" : "OFF", mode ? "ON" : "OFF");
    }
    
    public boolean canSwitchGunnerMode() {
        return this.getAcInfo() != null && this.getAcInfo().isEnableGunnerMode && this.isCanopyClose() && (this.getAcInfo().isEnableConcurrentGunnerMode || !(this.getEntityBySeatId(1) instanceof EntityPlayer)) && !this.isHoveringMode();
    }
    
    public boolean canSwitchGunnerModeOtherSeat(final EntityPlayer player) {
        final int sid = this.getSeatIdByEntity((Entity)player);
        if (sid > 0) {
            final MCH_SeatInfo info = this.getSeatInfo(sid);
            if (info != null) {
                return info.gunner && info.switchgunner;
            }
        }
        return false;
    }
    
    public void switchGunnerModeOtherSeat(final EntityPlayer player) {
        this.isGunnerModeOtherSeat = !this.isGunnerModeOtherSeat;
    }
    
    public boolean isHoveringMode() {
        return this.isHoveringMode;
    }
    
    public void switchHoveringMode(final boolean mode) {
        this.stopRepelling();
        if (this.canSwitchHoveringMode() && this.isHoveringMode() != mode) {
            if (mode) {
                this.beforeHoverThrottle = this.getCurrentThrottle();
            }
            else {
                this.setCurrentThrottle(this.beforeHoverThrottle);
            }
            this.isHoveringMode = mode;
            if (this.field_70153_n != null) {
                this.field_70153_n.field_70125_A = 0.0f;
                this.field_70153_n.field_70127_C = 0.0f;
            }
        }
    }
    
    public boolean canSwitchHoveringMode() {
        return this.getAcInfo() != null && !this.isGunnerMode;
    }
    
    public boolean isHovering() {
        return this.isGunnerMode || this.isHoveringMode();
    }
    
    public boolean getIsGunnerMode(final Entity entity) {
        if (this.getAcInfo() == null) {
            return false;
        }
        final int id = this.getSeatIdByEntity(entity);
        if (id < 0) {
            return false;
        }
        if (id == 0 && this.getAcInfo().isEnableGunnerMode) {
            return this.isGunnerMode;
        }
        final MCH_SeatInfo[] st = this.getSeatsInfo();
        return id < st.length && st[id].gunner && (!this.field_70170_p.field_72995_K || !st[id].switchgunner || this.isGunnerModeOtherSeat);
    }
    
    public boolean isPilot(final Entity player) {
        return W_Entity.isEqual(this.getRiddenByEntity(), player);
    }
    
    public boolean canSwitchFreeLook() {
        return true;
    }
    
    public boolean isFreeLookMode() {
        return this.getCommonStatus(1) || this.isRepelling();
    }
    
    public void switchFreeLookMode(final boolean b) {
        this.setCommonStatus(1, b);
    }
    
    public void switchFreeLookModeClient(final boolean b) {
        this.setCommonStatus(1, b, true);
    }
    
    public boolean canSwitchGunnerFreeLook(final EntityPlayer player) {
        final MCH_SeatInfo seatInfo = this.getSeatInfo((Entity)player);
        return seatInfo != null && seatInfo.fixRot && this.getIsGunnerMode((Entity)player);
    }
    
    public boolean isGunnerLookMode(final EntityPlayer player) {
        return !this.isPilot((Entity)player) && this.isGunnerFreeLookMode;
    }
    
    public void switchGunnerFreeLookMode(final boolean b) {
        this.isGunnerFreeLookMode = b;
    }
    
    public void switchGunnerFreeLookMode() {
        this.switchGunnerFreeLookMode(!this.isGunnerFreeLookMode);
    }
    
    public void updateParts(final int stat) {
        if (this.isDestroyed()) {
            return;
        }
        final MCH_Parts[] arr$;
        final MCH_Parts[] parts = arr$ = new MCH_Parts[] { this.partHatch, this.partCanopy, this.partLandingGear };
        for (final MCH_Parts p : arr$) {
            if (p != null) {
                p.updateStatusClient(stat);
                p.update();
            }
        }
        if (!this.isDestroyed() && !this.field_70170_p.field_72995_K && this.partLandingGear != null) {
            int blockId = 0;
            if (!this.isLandingGearFolded() && this.partLandingGear.getFactor() <= 0.1f) {
                blockId = MCH_Lib.getBlockIdY(this, 3, -20);
                if (this.getCurrentThrottle() <= 0.800000011920929 || this.field_70122_E || blockId != 0) {
                    if (this.getAcInfo().isFloat && (this.func_70090_H() || MCH_Lib.getBlockY(this, 3, -20, true) == W_Block.getWater())) {
                        this.partLandingGear.setStatusServer(true);
                    }
                }
            }
            else if (this.isLandingGearFolded() && this.partLandingGear.getFactor() >= 0.9f) {
                blockId = MCH_Lib.getBlockIdY(this, 3, -10);
                if (this.getCurrentThrottle() < this.getUnfoldLandingGearThrottle() && blockId != 0) {
                    boolean unfold = true;
                    if (this.getAcInfo().isFloat) {
                        blockId = MCH_Lib.getBlockIdY(this.field_70170_p, this.field_70165_t, this.field_70163_u + 1.0 + this.getAcInfo().floatOffset, this.field_70161_v, 1, -150, true);
                        if (W_Block.isEqual(blockId, W_Block.getWater())) {
                            unfold = false;
                        }
                    }
                    if (unfold) {
                        this.partLandingGear.setStatusServer(false);
                    }
                }
                else if (this.getVtolMode() == 2 && blockId != 0) {
                    this.partLandingGear.setStatusServer(false);
                }
            }
        }
    }
    
    public float getUnfoldLandingGearThrottle() {
        return 0.8f;
    }
    
    private int getPartStatus() {
        return this.func_70096_w().func_75679_c(31);
    }
    
    private void setPartStatus(final int n) {
        this.func_70096_w().func_75692_b(31, (Object)n);
    }
    
    protected void initPartRotation(final float yaw, final float pitch) {
        this.lastRiderYaw = yaw;
        this.prevLastRiderYaw = yaw;
        this.camera.partRotationYaw = yaw;
        this.camera.prevPartRotationYaw = yaw;
        this.lastSearchLightYaw = yaw;
    }
    
    public int getLastPartStatusMask() {
        return 24;
    }
    
    public int getModeSwitchCooldown() {
        return this.modeSwitchCooldown;
    }
    
    public void setModeSwitchCooldown(final int n) {
        this.modeSwitchCooldown = n;
    }
    
    protected WeaponBay[] createWeaponBays() {
        final WeaponBay[] wbs = new WeaponBay[this.getAcInfo().partWeaponBay.size()];
        for (int i = 0; i < wbs.length; ++i) {
            wbs[i] = new WeaponBay();
        }
        return wbs;
    }
    
    protected MCH_Parts createHatch() {
        MCH_Parts hatch = null;
        if (this.getAcInfo().haveHatch()) {
            hatch = new MCH_Parts(this, 4, 31, "Hatch");
            hatch.rotationMax = 90.0f;
            hatch.rotationInv = 1.5f;
            hatch.soundEndSwichOn.setPrm("plane_cc", 1.0f, 1.0f);
            hatch.soundEndSwichOff.setPrm("plane_cc", 1.0f, 1.0f);
            hatch.soundSwitching.setPrm("plane_cv", 1.0f, 0.5f);
        }
        return hatch;
    }
    
    public boolean haveHatch() {
        return this.partHatch != null;
    }
    
    public boolean canFoldHatch() {
        return this.partHatch != null && this.modeSwitchCooldown <= 0 && this.partHatch.isOFF();
    }
    
    public boolean canUnfoldHatch() {
        return this.partHatch != null && this.modeSwitchCooldown <= 0 && this.partHatch.isON();
    }
    
    public void foldHatch(final boolean fold) {
        this.foldHatch(fold, false);
    }
    
    public void foldHatch(final boolean fold, final boolean force) {
        if (this.partHatch == null) {
            return;
        }
        if (!force && this.modeSwitchCooldown > 0) {
            return;
        }
        this.partHatch.setStatusServer(fold);
        this.modeSwitchCooldown = 20;
        if (!fold) {
            this.stopUnmountCrew();
        }
    }
    
    public float getHatchRotation() {
        return (this.partHatch != null) ? this.partHatch.rotation : 0.0f;
    }
    
    public float getPrevHatchRotation() {
        return (this.partHatch != null) ? this.partHatch.prevRotation : 0.0f;
    }
    
    public void foldLandingGear() {
        if (this.partLandingGear == null || this.getModeSwitchCooldown() > 0) {
            return;
        }
        this.partLandingGear.setStatusServer(true);
        this.setModeSwitchCooldown(20);
    }
    
    public void unfoldLandingGear() {
        if (this.partLandingGear == null || this.getModeSwitchCooldown() > 0) {
            return;
        }
        if (this.isLandingGearFolded()) {
            this.partLandingGear.setStatusServer(false);
            this.setModeSwitchCooldown(20);
        }
    }
    
    public boolean canFoldLandingGear() {
        if (this.getLandingGearRotation() >= 1.0f) {
            return false;
        }
        final Block block = MCH_Lib.getBlockY(this, 3, -10, true);
        return !this.isLandingGearFolded() && block == W_Blocks.field_150350_a;
    }
    
    public boolean canUnfoldLandingGear() {
        return this.getLandingGearRotation() >= 89.0f && this.isLandingGearFolded();
    }
    
    public boolean isLandingGearFolded() {
        return this.partLandingGear != null && this.partLandingGear.getStatus();
    }
    
    protected MCH_Parts createLandingGear() {
        MCH_Parts lg = null;
        if (this.getAcInfo().haveLandingGear()) {
            lg = new MCH_Parts(this, 2, 31, "LandingGear");
            lg.rotationMax = 90.0f;
            lg.rotationInv = 2.5f;
            lg.soundStartSwichOn.setPrm("plane_cc", 1.0f, 0.5f);
            lg.soundEndSwichOn.setPrm("plane_cc", 1.0f, 0.5f);
            lg.soundStartSwichOff.setPrm("plane_cc", 1.0f, 0.5f);
            lg.soundEndSwichOff.setPrm("plane_cc", 1.0f, 0.5f);
            lg.soundSwitching.setPrm("plane_cv", 1.0f, 0.75f);
        }
        return lg;
    }
    
    public float getLandingGearRotation() {
        return (this.partLandingGear != null) ? this.partLandingGear.rotation : 0.0f;
    }
    
    public float getPrevLandingGearRotation() {
        return (this.partLandingGear != null) ? this.partLandingGear.prevRotation : 0.0f;
    }
    
    public int getVtolMode() {
        return 0;
    }
    
    public void openCanopy() {
        if (this.partCanopy == null || this.getModeSwitchCooldown() > 0) {
            return;
        }
        this.partCanopy.setStatusServer(true);
        this.setModeSwitchCooldown(20);
    }
    
    public void openCanopy_EjectSeat() {
        if (this.partCanopy == null) {
            return;
        }
        this.partCanopy.setStatusServer(true, false);
        this.setModeSwitchCooldown(40);
    }
    
    public void closeCanopy() {
        if (this.partCanopy == null || this.getModeSwitchCooldown() > 0) {
            return;
        }
        if (this.getCanopyStat()) {
            this.partCanopy.setStatusServer(false);
            this.setModeSwitchCooldown(20);
        }
    }
    
    public boolean getCanopyStat() {
        return this.partCanopy != null && this.partCanopy.getStatus();
    }
    
    public boolean isCanopyClose() {
        return this.partCanopy == null || (!this.getCanopyStat() && this.getCanopyRotation() <= 0.01f);
    }
    
    public float getCanopyRotation() {
        return (this.partCanopy != null) ? this.partCanopy.rotation : 0.0f;
    }
    
    public float getPrevCanopyRotation() {
        return (this.partCanopy != null) ? this.partCanopy.prevRotation : 0.0f;
    }
    
    protected MCH_Parts createCanopy() {
        MCH_Parts canopy = null;
        if (this.getAcInfo().haveCanopy()) {
            canopy = new MCH_Parts(this, 0, 31, "Canopy");
            canopy.rotationMax = 90.0f;
            canopy.rotationInv = 3.5f;
            canopy.soundEndSwichOn.setPrm("plane_cc", 1.0f, 1.0f);
            canopy.soundEndSwichOff.setPrm("plane_cc", 1.0f, 1.0f);
        }
        return canopy;
    }
    
    public boolean hasBrake() {
        return false;
    }
    
    public void setBrake(final boolean b) {
        if (!this.field_70170_p.field_72995_K) {
            this.setCommonStatus(11, b);
        }
    }
    
    public boolean getBrake() {
        return this.getCommonStatus(11);
    }
    
    @Override
    public int func_70302_i_() {
        return (this.getAcInfo() != null) ? this.getAcInfo().inventorySize : 0;
    }
    
    @Override
    public String getInvName() {
        if (this.getAcInfo() == null) {
            return super.getInvName();
        }
        final String s = this.getAcInfo().displayName;
        return (s.length() <= 32) ? s : s.substring(0, 31);
    }
    
    @Override
    public boolean isInvNameLocalized() {
        return this.getAcInfo() != null;
    }
    
    public MCH_EntityChain getTowChainEntity() {
        return this.towChainEntity;
    }
    
    public void setTowChainEntity(final MCH_EntityChain chainEntity) {
        this.towChainEntity = chainEntity;
    }
    
    public MCH_EntityChain getTowedChainEntity() {
        return this.towedChainEntity;
    }
    
    public void setTowedChainEntity(final MCH_EntityChain towedChainEntity) {
        this.towedChainEntity = towedChainEntity;
    }
    
    static {
        seatsDummy = new MCH_EntitySeat[0];
    }
    
    protected class UnmountReserve
    {
        final Entity entity;
        final double posX;
        final double posY;
        final double posZ;
        int cnt;
        
        public UnmountReserve(final Entity e, final double x, final double y, final double z) {
            this.cnt = 5;
            this.entity = e;
            this.posX = x;
            this.posY = y;
            this.posZ = z;
        }
    }
    
    public class WeaponBay
    {
        public float rot;
        public float prevRot;
        
        public WeaponBay() {
            this.rot = 0.0f;
            this.prevRot = 0.0f;
        }
    }
}
