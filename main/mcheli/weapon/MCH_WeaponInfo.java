package mcheli.weapon;

import mcheli.*;
import java.util.*;
import mcheli.wrapper.*;
import net.minecraft.entity.player.*;
import mcheli.helicopter.*;
import mcheli.plane.*;
import mcheli.tank.*;
import mcheli.vehicle.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;

public class MCH_WeaponInfo extends MCH_BaseInfo
{
    public final String name;
    public String displayName;
    public String type;
    public int power;
    public float acceleration;
    public float accelerationInWater;
    public int explosion;
    public int explosionBlock;
    public int explosionInWater;
    public int explosionAltitude;
    public int delayFuse;
    public float bound;
    public int timeFuse;
    public boolean flaming;
    public MCH_SightType sight;
    public float[] zoom;
    public int delay;
    public int reloadTime;
    public int round;
    public int suppliedNum;
    public int maxAmmo;
    public List<RoundItem> roundItems;
    public int soundDelay;
    public float soundVolume;
    public float soundPitch;
    public float soundPitchRandom;
    public int soundPattern;
    public int lockTime;
    public boolean ridableOnly;
    public float proximityFuseDist;
    public int rigidityTime;
    public float accuracy;
    public int bomblet;
    public int bombletSTime;
    public float bombletDiff;
    public int modeNum;
    public int fixMode;
    public int piercing;
    public int heatCount;
    public int maxHeatCount;
    public boolean isFAE;
    public boolean isGuidedTorpedo;
    public float gravity;
    public float gravityInWater;
    public float velocityInWater;
    public boolean destruct;
    public String trajectoryParticleName;
    public int trajectoryParticleStartTick;
    public boolean disableSmoke;
    public MCH_Cartridge cartridge;
    public MCH_Color color;
    public MCH_Color colorInWater;
    public String soundFileName;
    public float smokeSize;
    public int smokeNum;
    public int smokeMaxAge;
    public Item dispenseItem;
    public int dispenseDamege;
    public int dispenseRange;
    public int recoilBufCount;
    public int recoilBufCountSpeed;
    public float length;
    public float radius;
    public float angle;
    public boolean displayMortarDistance;
    public boolean fixCameraPitch;
    public float cameraRotationSpeedPitch;
    public int target;
    public int markTime;
    public float recoil;
    public String bulletModelName;
    public MCH_BulletModel bulletModel;
    public String bombletModelName;
    public MCH_BulletModel bombletModel;
    public MCH_DamageFactor damageFactor;
    public String group;
    public List<MuzzleFlash> listMuzzleFlash;
    public List<MuzzleFlash> listMuzzleFlashSmoke;
    
    public MCH_WeaponInfo(final String name) {
        this.name = name;
        this.displayName = name;
        this.type = "";
        this.power = 0;
        this.acceleration = 1.0f;
        this.accelerationInWater = 1.0f;
        this.explosion = 0;
        this.explosionBlock = -1;
        this.explosionInWater = 0;
        this.explosionAltitude = 0;
        this.delayFuse = 0;
        this.timeFuse = 0;
        this.flaming = false;
        this.sight = MCH_SightType.NONE;
        this.zoom = new float[] { 1.0f };
        this.delay = 10;
        this.reloadTime = 30;
        this.round = 0;
        this.suppliedNum = 1;
        this.roundItems = new ArrayList<RoundItem>();
        this.maxAmmo = 0;
        this.soundDelay = 0;
        this.soundPattern = 0;
        this.soundVolume = 1.0f;
        this.soundPitch = 1.0f;
        this.soundPitchRandom = 0.1f;
        this.lockTime = 30;
        this.ridableOnly = false;
        this.proximityFuseDist = 0.0f;
        this.rigidityTime = 7;
        this.accuracy = 0.0f;
        this.bomblet = 0;
        this.bombletSTime = 10;
        this.bombletDiff = 0.3f;
        this.modeNum = 0;
        this.fixMode = 0;
        this.piercing = 0;
        this.heatCount = 0;
        this.maxHeatCount = 0;
        this.bulletModelName = "";
        this.bombletModelName = "";
        this.bulletModel = null;
        this.bombletModel = null;
        this.isFAE = false;
        this.isGuidedTorpedo = false;
        this.gravity = 0.0f;
        this.gravityInWater = 0.0f;
        this.velocityInWater = 0.999f;
        this.destruct = false;
        this.trajectoryParticleName = "explode";
        this.trajectoryParticleStartTick = 0;
        this.cartridge = null;
        this.disableSmoke = false;
        this.color = new MCH_Color();
        this.colorInWater = new MCH_Color();
        this.soundFileName = name + "_snd";
        this.smokeMaxAge = 100;
        this.smokeNum = 1;
        this.smokeSize = 2.0f;
        this.dispenseItem = null;
        this.dispenseDamege = 0;
        this.dispenseRange = 1;
        this.recoilBufCount = 2;
        this.recoilBufCountSpeed = 3;
        this.length = 0.0f;
        this.radius = 0.0f;
        this.target = 1;
        this.recoil = 0.0f;
        this.damageFactor = null;
        this.group = "";
        this.listMuzzleFlash = null;
        this.listMuzzleFlashSmoke = null;
        this.displayMortarDistance = false;
        this.fixCameraPitch = false;
        this.cameraRotationSpeedPitch = 1.0f;
    }
    
    public void checkData() {
        if (this.explosionBlock < 0) {
            this.explosionBlock = this.explosion;
        }
        if (this.fixMode >= this.modeNum) {
            this.fixMode = 0;
        }
        if (this.round <= 0) {
            this.round = this.maxAmmo;
        }
        if (this.round > this.maxAmmo) {
            this.round = this.maxAmmo;
        }
        if (this.explosion <= 0) {
            this.isFAE = false;
        }
        if (this.delayFuse <= 0) {
            this.bound = 0.0f;
        }
        if (this.isFAE) {
            this.explosionInWater = 0;
        }
        if (this.bomblet > 0 && this.bombletSTime < 1) {
            this.bombletSTime = 1;
        }
        if (this.destruct) {
            this.delay = 1000000;
        }
        this.angle = (float)(Math.atan2(this.radius, this.length) * 180.0 / 3.141592653589793);
    }
    
    @Override
    public void loadItemData(final String item, String data) {
        if (item.compareTo("displayname") == 0) {
            this.displayName = data;
        }
        else if (item.compareTo("type") == 0) {
            this.type = data.toLowerCase();
            if (this.type.equalsIgnoreCase("bomb") || this.type.equalsIgnoreCase("dispenser")) {
                this.gravity = -0.03f;
                this.gravityInWater = -0.03f;
            }
        }
        else if (item.compareTo("group") == 0) {
            this.group = data.toLowerCase().trim();
        }
        else if (item.compareTo("power") == 0) {
            this.power = this.toInt(data);
        }
        else if (item.equalsIgnoreCase("sound")) {
            this.soundFileName = data.toLowerCase().trim();
        }
        else if (item.compareTo("acceleration") == 0) {
            this.acceleration = this.toFloat(data, 0.0f, 100.0f);
        }
        else if (item.compareTo("accelerationinwater") == 0) {
            this.accelerationInWater = this.toFloat(data, 0.0f, 100.0f);
        }
        else if (item.compareTo("gravity") == 0) {
            this.gravity = this.toFloat(data, -50.0f, 50.0f);
        }
        else if (item.compareTo("gravityinwater") == 0) {
            this.gravityInWater = this.toFloat(data, -50.0f, 50.0f);
        }
        else if (item.equalsIgnoreCase("VelocityInWater")) {
            this.velocityInWater = this.toFloat(data);
        }
        else if (item.compareTo("explosion") == 0) {
            this.explosion = this.toInt(data, 0, 50);
        }
        else if (item.equalsIgnoreCase("explosionBlock")) {
            this.explosionBlock = this.toInt(data, 0, 50);
        }
        else if (item.compareTo("explosioninwater") == 0) {
            this.explosionInWater = this.toInt(data, 0, 50);
        }
        else if (item.equalsIgnoreCase("ExplosionAltitude")) {
            this.explosionAltitude = this.toInt(data, 0, 100);
        }
        else if (item.equalsIgnoreCase("TimeFuse")) {
            this.timeFuse = this.toInt(data, 0, 100000);
        }
        else if (item.equalsIgnoreCase("DelayFuse")) {
            this.delayFuse = this.toInt(data, 0, 100000);
        }
        else if (item.equalsIgnoreCase("Bound")) {
            this.bound = this.toFloat(data, 0.0f, 100000.0f);
        }
        else if (item.compareTo("flaming") == 0) {
            this.flaming = this.toBool(data);
        }
        else if (item.equalsIgnoreCase("DisplayMortarDistance")) {
            this.displayMortarDistance = this.toBool(data);
        }
        else if (item.equalsIgnoreCase("FixCameraPitch")) {
            this.fixCameraPitch = this.toBool(data);
        }
        else if (item.equalsIgnoreCase("CameraRotationSpeedPitch")) {
            this.cameraRotationSpeedPitch = this.toFloat(data, 0.0f, 100.0f);
        }
        else if (item.compareTo("sight") == 0) {
            data = data.toLowerCase();
            if (data.compareTo("movesight") == 0) {
                this.sight = MCH_SightType.ROCKET;
            }
            if (data.compareTo("missilesight") == 0) {
                this.sight = MCH_SightType.LOCK;
            }
        }
        else if (item.equalsIgnoreCase("Zoom")) {
            final String[] s = this.splitParam(data);
            if (s.length > 0) {
                this.zoom = new float[s.length];
                for (int i = 0; i < s.length; ++i) {
                    this.zoom[i] = this.toFloat(s[i], 0.1f, 10.0f);
                }
            }
        }
        else if (item.compareTo("delay") == 0) {
            this.delay = this.toInt(data, 0, 100000);
        }
        else if (item.compareTo("reloadtime") == 0) {
            this.reloadTime = this.toInt(data, 3, 1000);
        }
        else if (item.compareTo("round") == 0) {
            this.round = this.toInt(data, 1, 30000);
        }
        else if (item.equalsIgnoreCase("MaxAmmo")) {
            this.maxAmmo = this.toInt(data, 0, 30000);
        }
        else if (item.equalsIgnoreCase("SuppliedNum")) {
            this.suppliedNum = this.toInt(data, 1, 30000);
        }
        else if (item.equalsIgnoreCase("Item")) {
            final String[] s = data.split("\\s*,\\s*");
            if (s.length >= 2 && s[1].length() > 0 && this.roundItems.size() < 3) {
                final int n = this.toInt(s[0], 1, 64);
                if (n > 0) {
                    final int damage = (s.length >= 3) ? this.toInt(s[2], 0, 100000000) : 0;
                    this.roundItems.add(new RoundItem(n, s[1].toLowerCase().trim(), damage));
                }
            }
        }
        else if (item.compareTo("sounddelay") == 0) {
            this.soundDelay = this.toInt(data, 0, 1000);
        }
        else if (item.compareTo("soundpattern") != 0) {
            if (item.compareTo("soundvolume") == 0) {
                this.soundVolume = this.toFloat(data, 0.0f, 1000.0f);
            }
            else if (item.compareTo("soundpitch") == 0) {
                this.soundPitch = this.toFloat(data, 0.0f, 1.0f);
            }
            else if (item.equalsIgnoreCase("SoundPitchRandom")) {
                this.soundPitchRandom = this.toFloat(data, 0.0f, 1.0f);
            }
            else if (item.compareTo("locktime") == 0) {
                this.lockTime = this.toInt(data, 2, 1000);
            }
            else if (item.equalsIgnoreCase("RidableOnly")) {
                this.ridableOnly = this.toBool(data);
            }
            else if (item.compareTo("proximityfusedist") == 0) {
                this.proximityFuseDist = this.toFloat(data, 0.0f, 2000.0f);
            }
            else if (item.equalsIgnoreCase("RigidityTime")) {
                this.rigidityTime = this.toInt(data, 0, 1000000);
            }
            else if (item.compareTo("accuracy") == 0) {
                this.accuracy = this.toFloat(data, 0.0f, 1000.0f);
            }
            else if (item.compareTo("bomblet") == 0) {
                this.bomblet = this.toInt(data, 0, 1000);
            }
            else if (item.compareTo("bombletstime") == 0) {
                this.bombletSTime = this.toInt(data, 0, 1000);
            }
            else if (item.equalsIgnoreCase("BombletDiff")) {
                this.bombletDiff = this.toFloat(data, 0.0f, 1000.0f);
            }
            else if (item.equalsIgnoreCase("RecoilBufCount")) {
                final String[] s = this.splitParam(data);
                if (s.length >= 1) {
                    this.recoilBufCount = this.toInt(s[0], 1, 10000);
                }
                if (s.length >= 2 && this.recoilBufCount > 2) {
                    this.recoilBufCountSpeed = this.toInt(s[1], 1, 10000) - 1;
                    if (this.recoilBufCountSpeed > this.recoilBufCount / 2) {
                        this.recoilBufCountSpeed = this.recoilBufCount / 2;
                    }
                }
            }
            else if (item.compareTo("modenum") == 0) {
                this.modeNum = this.toInt(data, 0, 1000);
            }
            else if (item.equalsIgnoreCase("FixMode")) {
                this.fixMode = this.toInt(data, 0, 10);
            }
            else if (item.compareTo("piercing") == 0) {
                this.piercing = this.toInt(data, 0, 100000);
            }
            else if (item.compareTo("heatcount") == 0) {
                this.heatCount = this.toInt(data, 0, 100000);
            }
            else if (item.compareTo("maxheatcount") == 0) {
                this.maxHeatCount = this.toInt(data, 0, 100000);
            }
            else if (item.compareTo("modelbullet") == 0) {
                this.bulletModelName = data.toLowerCase().trim();
            }
            else if (item.equalsIgnoreCase("ModelBomblet")) {
                this.bombletModelName = data.toLowerCase().trim();
            }
            else if (item.compareTo("fae") == 0) {
                this.isFAE = this.toBool(data);
            }
            else if (item.compareTo("guidedtorpedo") == 0) {
                this.isGuidedTorpedo = this.toBool(data);
            }
            else if (item.compareTo("destruct") == 0) {
                this.destruct = this.toBool(data);
            }
            else if (item.equalsIgnoreCase("AddMuzzleFlash")) {
                final String[] s = this.splitParam(data);
                if (s.length >= 7) {
                    if (this.listMuzzleFlash == null) {
                        this.listMuzzleFlash = new ArrayList<MuzzleFlash>();
                    }
                    this.listMuzzleFlash.add(new MuzzleFlash(this.toFloat(s[0]), this.toFloat(s[1]), 0.0f, this.toInt(s[2]), this.toFloat(s[3]) / 255.0f, this.toFloat(s[4]) / 255.0f, this.toFloat(s[5]) / 255.0f, this.toFloat(s[6]) / 255.0f, 1));
                }
            }
            else if (item.equalsIgnoreCase("AddMuzzleFlashSmoke")) {
                final String[] s = this.splitParam(data);
                if (s.length >= 9) {
                    if (this.listMuzzleFlashSmoke == null) {
                        this.listMuzzleFlashSmoke = new ArrayList<MuzzleFlash>();
                    }
                    this.listMuzzleFlashSmoke.add(new MuzzleFlash(this.toFloat(s[0]), this.toFloat(s[2]), this.toFloat(s[3]), this.toInt(s[4]), this.toFloat(s[5]) / 255.0f, this.toFloat(s[6]) / 255.0f, this.toFloat(s[7]) / 255.0f, this.toFloat(s[8]) / 255.0f, this.toInt(s[1], 1, 1000)));
                }
            }
            else if (item.equalsIgnoreCase("TrajectoryParticle")) {
                this.trajectoryParticleName = data.toLowerCase().trim();
                if (this.trajectoryParticleName.equalsIgnoreCase("none")) {
                    this.trajectoryParticleName = "";
                }
            }
            else if (item.equalsIgnoreCase("TrajectoryParticleStartTick")) {
                this.trajectoryParticleStartTick = this.toInt(data, 0, 10000);
            }
            else if (item.equalsIgnoreCase("DisableSmoke")) {
                this.disableSmoke = this.toBool(data);
            }
            else if (item.equalsIgnoreCase("SetCartridge")) {
                final String[] s = data.split("\\s*,\\s*");
                if (s.length > 0 && s[0].length() > 0) {
                    final float ac = (s.length >= 2) ? this.toFloat(s[1]) : 0.0f;
                    final float yw = (s.length >= 3) ? this.toFloat(s[2]) : 0.0f;
                    final float pt = (s.length >= 4) ? this.toFloat(s[3]) : 0.0f;
                    final float sc = (s.length >= 5) ? this.toFloat(s[4]) : 1.0f;
                    final float gr = (s.length >= 6) ? this.toFloat(s[5]) : -0.04f;
                    final float bo = (s.length >= 7) ? this.toFloat(s[6]) : 0.5f;
                    this.cartridge = new MCH_Cartridge(s[0].toLowerCase(), ac, yw, pt, bo, gr, sc);
                }
            }
            else if (item.equalsIgnoreCase("BulletColorInWater") || item.equalsIgnoreCase("BulletColor") || item.equalsIgnoreCase("SmokeColor")) {
                final String[] s = data.split("\\s*,\\s*");
                if (s.length >= 4) {
                    final float f = 0.003921569f;
                    final MCH_Color c = new MCH_Color(0.003921569f * this.toInt(s[0], 0, 255), 0.003921569f * this.toInt(s[1], 0, 255), 0.003921569f * this.toInt(s[2], 0, 255), 0.003921569f * this.toInt(s[3], 0, 255));
                    if (item.equalsIgnoreCase("BulletColorInWater")) {
                        this.colorInWater = c;
                    }
                    else {
                        this.color = c;
                    }
                }
            }
            else if (item.equalsIgnoreCase("SmokeSize")) {
                this.smokeSize = this.toFloat(data, 0.0f, 100.0f);
            }
            else if (item.equalsIgnoreCase("SmokeNum")) {
                this.smokeNum = this.toInt(data, 1, 100);
            }
            else if (item.equalsIgnoreCase("SmokeMaxAge")) {
                this.smokeMaxAge = this.toInt(data, 2, 1000);
            }
            else if (item.equalsIgnoreCase("DispenseItem")) {
                final String[] s = data.split("\\s*,\\s*");
                if (s.length >= 2) {
                    this.dispenseDamege = this.toInt(s[1], 0, 100000000);
                }
                this.dispenseItem = W_Item.getItemByName(s[0]);
            }
            else if (item.equalsIgnoreCase("DispenseRange")) {
                this.dispenseRange = this.toInt(data, 1, 100);
            }
            else if (item.equalsIgnoreCase("Length")) {
                this.length = this.toInt(data, 1, 300);
            }
            else if (item.equalsIgnoreCase("Radius")) {
                this.radius = this.toInt(data, 1, 1000);
            }
            else if (item.equalsIgnoreCase("Target")) {
                if (data.indexOf("block") >= 0) {
                    this.target = 64;
                }
                else {
                    this.target = 0;
                    this.target |= ((data.indexOf("planes") >= 0) ? 32 : 0);
                    this.target |= ((data.indexOf("helicopters") >= 0) ? 16 : 0);
                    this.target |= ((data.indexOf("vehicles") >= 0) ? 8 : 0);
                    this.target |= ((data.indexOf("tanks") >= 0) ? 8 : 0);
                    this.target |= ((data.indexOf("players") >= 0) ? 4 : 0);
                    this.target |= ((data.indexOf("monsters") >= 0) ? 2 : 0);
                    this.target |= ((data.indexOf("others") >= 0) ? 1 : 0);
                }
            }
            else if (item.equalsIgnoreCase("MarkTime")) {
                this.markTime = this.toInt(data, 1, 30000) + 1;
            }
            else if (item.equalsIgnoreCase("Recoil")) {
                this.recoil = this.toFloat(data, 0.0f, 100.0f);
            }
            else if (item.equalsIgnoreCase("DamageFactor")) {
                final String[] s = this.splitParam(data);
                if (s.length >= 2) {
                    Class c2 = null;
                    final String className = s[0].toLowerCase();
                    if (className.equals("player")) {
                        c2 = EntityPlayer.class;
                    }
                    else if (className.equals("heli") || className.equals("helicopter")) {
                        c2 = MCH_EntityHeli.class;
                    }
                    else if (className.equals("plane")) {
                        c2 = MCP_EntityPlane.class;
                    }
                    else if (className.equals("tank")) {
                        c2 = MCH_EntityTank.class;
                    }
                    else if (className.equals("vehicle")) {
                        c2 = MCH_EntityVehicle.class;
                    }
                    if (c2 != null) {
                        if (this.damageFactor == null) {
                            this.damageFactor = new MCH_DamageFactor();
                        }
                        this.damageFactor.add(c2, this.toFloat(s[1], 0.0f, 1000000.0f));
                    }
                }
            }
        }
    }
    
    public float getDamageFactor(final Entity e) {
        return (this.damageFactor != null) ? this.damageFactor.getDamageFactor(e) : 1.0f;
    }
    
    public String getWeaponTypeName() {
        if (this.type.equalsIgnoreCase("MachineGun1")) {
            return "MachineGun";
        }
        if (this.type.equalsIgnoreCase("MachineGun2")) {
            return "MachineGun";
        }
        if (this.type.equalsIgnoreCase("Torpedo")) {
            return "Torpedo";
        }
        if (this.type.equalsIgnoreCase("CAS")) {
            return "CAS";
        }
        if (this.type.equalsIgnoreCase("Rocket")) {
            return "Rocket";
        }
        if (this.type.equalsIgnoreCase("ASMissile")) {
            return "AS Missile";
        }
        if (this.type.equalsIgnoreCase("AAMissile")) {
            return "AA Missile";
        }
        if (this.type.equalsIgnoreCase("TVMissile")) {
            return "TV Missile";
        }
        if (this.type.equalsIgnoreCase("ATMissile")) {
            return "AT Missile";
        }
        if (this.type.equalsIgnoreCase("Bomb")) {
            return "Bomb";
        }
        if (this.type.equalsIgnoreCase("MkRocket")) {
            return "Mk Rocket";
        }
        if (this.type.equalsIgnoreCase("Dummy")) {
            return "Dummy";
        }
        if (this.type.equalsIgnoreCase("Smoke")) {
            return "Smoke";
        }
        if (this.type.equalsIgnoreCase("Smoke")) {
            return "Smoke";
        }
        if (this.type.equalsIgnoreCase("Dispenser")) {
            return "Dispenser";
        }
        if (this.type.equalsIgnoreCase("TargetingPod")) {
            return "Targeting Pod";
        }
        return "";
    }
    
    public class MuzzleFlash
    {
        public final float dist;
        public final float size;
        public final float range;
        public final int age;
        public final float a;
        public final float r;
        public final float g;
        public final float b;
        public final int num;
        
        public MuzzleFlash(final float dist, final float size, final float range, final int age, final float a, final float r, final float g, final float b, final int num) {
            this.dist = dist;
            this.size = size;
            this.range = range;
            this.age = age;
            this.a = a;
            this.r = r;
            this.g = g;
            this.b = b;
            this.num = num;
        }
    }
    
    public class RoundItem
    {
        public final int num;
        public final String itemName;
        public final int damage;
        public ItemStack itemStack;
        
        public RoundItem(final int n, final String name, final int damage) {
            this.num = n;
            this.itemName = name;
            this.damage = damage;
        }
    }
}
