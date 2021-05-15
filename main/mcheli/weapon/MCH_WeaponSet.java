package mcheli.weapon;

import java.util.*;
import mcheli.*;
import mcheli.wrapper.*;
import net.minecraft.entity.*;
import mcheli.vehicle.*;
import net.minecraft.util.*;

public class MCH_WeaponSet
{
    private static Random rand;
    private final String name;
    protected MCH_WeaponBase[] weapons;
    private int currentWeaponIndex;
    public float rotationYaw;
    public float rotationPitch;
    public float prevRotationYaw;
    public float prevRotationPitch;
    public float defaultRotationYaw;
    public float rotationTurretYaw;
    public float rotBay;
    public float prevRotBay;
    public Recoil[] recoilBuf;
    protected int numAmmo;
    protected int numRestAllAmmo;
    public int currentHeat;
    public int cooldownSpeed;
    public int countWait;
    public int countReloadWait;
    protected int[] lastUsedCount;
    private static final int WAIT_CLEAR_USED_COUNT = 4;
    public int soundWait;
    private int lastUsedOptionParameter1;
    private int lastUsedOptionParameter2;
    public float rotBarrelSpd;
    public float rotBarrel;
    public float prevRotBarrel;
    
    public MCH_WeaponSet(final MCH_WeaponBase[] weapon) {
        this.lastUsedOptionParameter1 = 0;
        this.lastUsedOptionParameter2 = 0;
        this.name = weapon[0].name;
        this.weapons = weapon;
        this.currentWeaponIndex = 0;
        this.countWait = 0;
        this.countReloadWait = 0;
        this.lastUsedCount = new int[weapon.length];
        this.rotationYaw = 0.0f;
        this.prevRotationYaw = 0.0f;
        this.rotationPitch = 0.0f;
        this.prevRotationPitch = 0.0f;
        this.setAmmoNum(0);
        this.setRestAllAmmoNum(0);
        this.currentHeat = 0;
        this.soundWait = 0;
        this.cooldownSpeed = 1;
        this.rotBarrelSpd = 0.0f;
        this.rotBarrel = 0.0f;
        this.prevRotBarrel = 0.0f;
        this.recoilBuf = new Recoil[weapon.length];
        for (int i = 0; i < this.recoilBuf.length; ++i) {
            this.recoilBuf[i] = new Recoil(weapon[i].getInfo().recoilBufCount, weapon[i].getInfo().recoilBufCountSpeed);
        }
        this.defaultRotationYaw = 0.0f;
    }
    
    public MCH_WeaponSet(final MCH_WeaponBase weapon) {
        this(new MCH_WeaponBase[] { weapon });
    }
    
    public boolean isEqual(final String s) {
        return this.name.equalsIgnoreCase(s);
    }
    
    public int getAmmoNum() {
        return this.numAmmo;
    }
    
    public int getAmmoNumMax() {
        return this.getFirstWeapon().getNumAmmoMax();
    }
    
    public int getRestAllAmmoNum() {
        return this.numRestAllAmmo;
    }
    
    public int getAllAmmoNum() {
        return this.getFirstWeapon().getAllAmmoNum();
    }
    
    public void setAmmoNum(final int n) {
        this.numAmmo = n;
    }
    
    public void setRestAllAmmoNum(final int n) {
        final int debugBefore = this.numRestAllAmmo;
        final int m = this.getInfo().maxAmmo - this.getAmmoNum();
        this.numRestAllAmmo = ((n <= m) ? n : m);
        MCH_Lib.DbgLog(this.getFirstWeapon().worldObj, "MCH_WeaponSet.setRestAllAmmoNum:%s %d->%d (%d)", this.getName(), debugBefore, this.numRestAllAmmo, n);
    }
    
    public void supplyRestAllAmmo() {
        final int m = this.getInfo().maxAmmo;
        if (this.getRestAllAmmoNum() + this.getAmmoNum() < m) {
            this.setRestAllAmmoNum(this.getRestAllAmmoNum() + this.getAmmoNum() + this.getInfo().suppliedNum);
        }
    }
    
    public boolean isInPreparation() {
        return this.countWait < 0 || this.countReloadWait > 0;
    }
    
    public String getName() {
        final MCH_WeaponBase w = this.getCurrentWeapon();
        return (w != null) ? w.getName() : "";
    }
    
    public boolean canUse() {
        return this.countWait == 0;
    }
    
    public boolean isLongDelayWeapon() {
        return this.getInfo().delay > 4;
    }
    
    public void reload() {
        final MCH_WeaponBase crtWpn = this.getCurrentWeapon();
        if (this.getAmmoNumMax() > 0 && this.getAmmoNum() < this.getAmmoNumMax() && crtWpn.getReloadCount() > 0) {
            this.countReloadWait = crtWpn.getReloadCount();
            if (crtWpn.worldObj.field_72995_K) {
                this.setAmmoNum(0);
            }
            if (!crtWpn.worldObj.field_72995_K) {
                this.countReloadWait -= 20;
                if (this.countReloadWait <= 0) {
                    this.countReloadWait = 1;
                }
            }
        }
    }
    
    public void reloadMag() {
        final int restAmmo = this.getRestAllAmmoNum();
        int nAmmo = this.getAmmoNumMax() - this.getAmmoNum();
        if (nAmmo > 0) {
            if (nAmmo > restAmmo) {
                nAmmo = restAmmo;
            }
            this.setAmmoNum(this.getAmmoNum() + nAmmo);
            this.setRestAllAmmoNum(this.getRestAllAmmoNum() - nAmmo);
        }
    }
    
    public void switchMode() {
        boolean isChanged = false;
        for (final MCH_WeaponBase w : this.weapons) {
            if (w != null) {
                isChanged = (w.switchMode() || isChanged);
            }
        }
        if (isChanged) {
            final int cntSwitch = 15;
            if (this.countWait >= -cntSwitch) {
                if (this.countWait > cntSwitch) {
                    this.countWait = -this.countWait;
                }
                else {
                    this.countWait = -cntSwitch;
                }
            }
            if (this.getCurrentWeapon().worldObj.field_72995_K) {
                W_McClient.DEF_playSoundFX("random.click", 1.0f, 1.0f);
            }
        }
    }
    
    public void onSwitchWeapon(final boolean isRemote, final boolean isCreative) {
        int cntSwitch = 15;
        if (isRemote) {
            cntSwitch += 10;
        }
        if (this.countWait >= -cntSwitch) {
            if (this.countWait > cntSwitch) {
                this.countWait = -this.countWait;
            }
            else {
                this.countWait = -cntSwitch;
            }
        }
        this.currentWeaponIndex = 0;
        if (isCreative) {
            this.setAmmoNum(this.getAmmoNumMax());
        }
    }
    
    public boolean isUsed(final int index) {
        final MCH_WeaponBase w = this.getFirstWeapon();
        if (w != null && index < this.lastUsedCount.length) {
            final int cnt = this.lastUsedCount[index];
            return (w.interval >= 4 && cnt > w.interval / 2) || cnt >= 4;
        }
        return false;
    }
    
    public void update(final Entity shooter, final boolean isSelected, final boolean isUsed) {
        if (this.getCurrentWeapon().getInfo() == null) {
            return;
        }
        if (this.countReloadWait > 0) {
            --this.countReloadWait;
            if (this.countReloadWait == 0) {
                this.reloadMag();
            }
        }
        for (int i = 0; i < this.lastUsedCount.length; ++i) {
            if (this.lastUsedCount[i] > 0) {
                if (this.lastUsedCount[i] == 4) {
                    if (0 == this.getCurrentWeaponIndex() && this.canUse() && (this.getAmmoNum() > 0 || this.getAllAmmoNum() <= 0)) {
                        final int[] lastUsedCount = this.lastUsedCount;
                        final int n = i;
                        --lastUsedCount[n];
                    }
                }
                else {
                    final int[] lastUsedCount2 = this.lastUsedCount;
                    final int n2 = i;
                    --lastUsedCount2[n2];
                }
            }
        }
        if (this.currentHeat > 0) {
            if (this.currentHeat < this.getCurrentWeapon().getInfo().maxHeatCount) {
                ++this.cooldownSpeed;
            }
            this.currentHeat -= this.cooldownSpeed / 20 + 1;
            if (this.currentHeat < 0) {
                this.currentHeat = 0;
            }
        }
        if (this.countWait > 0) {
            --this.countWait;
        }
        if (this.countWait < 0) {
            ++this.countWait;
        }
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
        if (this.weapons != null && this.weapons.length > 0) {
            for (final MCH_WeaponBase w : this.weapons) {
                if (w != null) {
                    w.update(this.countWait);
                }
            }
        }
        if (this.soundWait > 0) {
            --this.soundWait;
        }
        if (isUsed && this.rotBarrelSpd < 75.0f) {
            this.rotBarrelSpd += 25 + MCH_WeaponSet.rand.nextInt(3);
            if (this.rotBarrelSpd > 74.0f) {
                this.rotBarrelSpd = 74.0f;
            }
        }
        this.prevRotBarrel = this.rotBarrel;
        this.rotBarrel += this.rotBarrelSpd;
        if (this.rotBarrel >= 360.0f) {
            this.rotBarrel -= 360.0f;
            this.prevRotBarrel -= 360.0f;
        }
        if (this.rotBarrelSpd > 0.0f) {
            this.rotBarrelSpd -= 1.48f;
            if (this.rotBarrelSpd < 0.0f) {
                this.rotBarrelSpd = 0.0f;
            }
        }
    }
    
    public void updateWeapon(final Entity shooter, final boolean isUsed, final int index) {
        final MCH_WeaponBase crtWpn = this.getWeapon(index);
        if (isUsed && shooter.field_70170_p.field_72995_K && crtWpn != null && crtWpn.cartridge != null) {
            final Vec3 v = crtWpn.getShotPos(shooter);
            final float yaw = shooter.field_70177_z;
            final float pitch = shooter.field_70125_A;
            if (!(shooter instanceof MCH_EntityVehicle) || shooter.field_70153_n != null) {}
            MCH_EntityCartridge.spawnCartridge(shooter.field_70170_p, crtWpn.cartridge, shooter.field_70165_t + v.field_72450_a, shooter.field_70163_u + v.field_72448_b, shooter.field_70161_v + v.field_72449_c, shooter.field_70159_w, shooter.field_70181_x, shooter.field_70179_y, yaw + this.rotationYaw, pitch + this.rotationPitch);
        }
        if (index < this.recoilBuf.length) {
            final Recoil r = this.recoilBuf[index];
            r.prevRecoilBuf = r.recoilBuf;
            if (isUsed && r.recoilBufCount <= 0) {
                r.recoilBufCount = r.recoilBufCountMax;
            }
            if (r.recoilBufCount > 0) {
                if (r.recoilBufCountMax <= 1) {
                    r.recoilBuf = 1.0f;
                }
                else if (r.recoilBufCountMax == 2) {
                    r.recoilBuf = ((r.recoilBufCount == 2) ? 1.0f : 0.6f);
                }
                else {
                    if (r.recoilBufCount > r.recoilBufCountMax / 2) {
                        final Recoil recoil = r;
                        recoil.recoilBufCount -= r.recoilBufCountSpeed;
                    }
                    final float rb = r.recoilBufCount / r.recoilBufCountMax;
                    r.recoilBuf = MathHelper.func_76126_a(rb * 3.1415927f);
                }
                final Recoil recoil2 = r;
                --recoil2.recoilBufCount;
            }
            else {
                r.recoilBuf = 0.0f;
            }
        }
    }
    
    public boolean use(final MCH_WeaponParam prm) {
        final MCH_WeaponBase crtWpn = this.getCurrentWeapon();
        if (crtWpn != null && crtWpn.getInfo() != null) {
            final MCH_WeaponInfo info = crtWpn.getInfo();
            if ((this.getAmmoNumMax() <= 0 || this.getAmmoNum() > 0) && (info.maxHeatCount <= 0 || this.currentHeat < info.maxHeatCount)) {
                crtWpn.canPlaySound = (this.soundWait == 0);
                prm.rotYaw = ((prm.entity != null) ? prm.entity.field_70177_z : 0.0f);
                prm.rotPitch = ((prm.entity != null) ? prm.entity.field_70125_A : 0.0f);
                prm.rotYaw += this.rotationYaw + crtWpn.fixRotationYaw;
                prm.rotPitch += this.rotationPitch + crtWpn.fixRotationPitch;
                if (info.accuracy > 0.0f) {
                    prm.rotYaw += (MCH_WeaponSet.rand.nextFloat() - 0.5f) * info.accuracy;
                    prm.rotPitch += (MCH_WeaponSet.rand.nextFloat() - 0.5f) * info.accuracy;
                }
                prm.rotYaw = MathHelper.func_76142_g(prm.rotYaw);
                prm.rotPitch = MathHelper.func_76142_g(prm.rotPitch);
                if (crtWpn.use(prm)) {
                    if (info.maxHeatCount > 0) {
                        this.cooldownSpeed = 1;
                        this.currentHeat += crtWpn.heatCount;
                        if (this.currentHeat >= info.maxHeatCount) {
                            this.currentHeat += 30;
                        }
                    }
                    if (info.soundDelay > 0 && this.soundWait == 0) {
                        this.soundWait = info.soundDelay;
                    }
                    this.lastUsedOptionParameter1 = crtWpn.optionParameter1;
                    this.lastUsedOptionParameter2 = crtWpn.optionParameter2;
                    this.lastUsedCount[this.currentWeaponIndex] = ((crtWpn.interval > 0) ? crtWpn.interval : (-crtWpn.interval));
                    if (crtWpn.isCooldownCountReloadTime() && crtWpn.getReloadCount() - 10 > this.lastUsedCount[this.currentWeaponIndex]) {
                        this.lastUsedCount[this.currentWeaponIndex] = crtWpn.getReloadCount() - 10;
                    }
                    this.currentWeaponIndex = (this.currentWeaponIndex + 1) % this.weapons.length;
                    this.countWait = crtWpn.interval;
                    this.countReloadWait = 0;
                    if (this.getAmmoNum() > 0) {
                        this.setAmmoNum(this.getAmmoNum() - 1);
                    }
                    if (this.getAmmoNum() <= 0) {
                        if (prm.isInfinity && this.getRestAllAmmoNum() < this.getAmmoNumMax()) {
                            this.setRestAllAmmoNum(this.getAmmoNumMax());
                        }
                        this.reload();
                        prm.reload = true;
                    }
                    prm.result = true;
                }
            }
        }
        return prm.result;
    }
    
    public void waitAndReloadByOther(final boolean reload) {
        final MCH_WeaponBase crtWpn = this.getCurrentWeapon();
        if (crtWpn != null && crtWpn.getInfo() != null) {
            this.countWait = crtWpn.interval;
            this.countReloadWait = 0;
            if (reload && this.getAmmoNumMax() > 0 && crtWpn.getReloadCount() > 0) {
                this.countReloadWait = crtWpn.getReloadCount();
                if (!crtWpn.worldObj.field_72995_K) {
                    this.countReloadWait -= 20;
                    if (this.countReloadWait <= 0) {
                        this.countReloadWait = 1;
                    }
                }
            }
        }
    }
    
    public int getLastUsedOptionParameter1() {
        return this.lastUsedOptionParameter1;
    }
    
    public int getLastUsedOptionParameter2() {
        return this.lastUsedOptionParameter2;
    }
    
    public MCH_WeaponBase getFirstWeapon() {
        return this.getWeapon(0);
    }
    
    public int getCurrentWeaponIndex() {
        return this.currentWeaponIndex;
    }
    
    public MCH_WeaponBase getCurrentWeapon() {
        return this.getWeapon(this.currentWeaponIndex);
    }
    
    public MCH_WeaponBase getWeapon(final int idx) {
        if (this.weapons != null && this.weapons.length > 0 && idx < this.weapons.length) {
            return this.weapons[idx];
        }
        return null;
    }
    
    public int getWeaponNum() {
        return (this.weapons != null) ? this.weapons.length : 0;
    }
    
    public MCH_WeaponInfo getInfo() {
        return this.getFirstWeapon().getInfo();
    }
    
    public double getLandInDistance(final MCH_WeaponParam prm) {
        final double ret = -1.0;
        final MCH_WeaponBase crtWpn = this.getCurrentWeapon();
        if (crtWpn != null && crtWpn.getInfo() != null) {
            final MCH_WeaponInfo info = crtWpn.getInfo();
            prm.rotYaw = ((prm.entity != null) ? prm.entity.field_70177_z : 0.0f);
            prm.rotPitch = ((prm.entity != null) ? prm.entity.field_70125_A : 0.0f);
            prm.rotYaw += this.rotationYaw + crtWpn.fixRotationYaw;
            prm.rotPitch += this.rotationPitch + crtWpn.fixRotationPitch;
            prm.rotYaw = MathHelper.func_76142_g(prm.rotYaw);
            prm.rotPitch = MathHelper.func_76142_g(prm.rotPitch);
            return crtWpn.getLandInDistance(prm);
        }
        return ret;
    }
    
    static {
        MCH_WeaponSet.rand = new Random();
    }
    
    public class Recoil
    {
        public int recoilBufCount;
        public final int recoilBufCountMax;
        public final int recoilBufCountSpeed;
        public float recoilBuf;
        public float prevRecoilBuf;
        
        public Recoil(final int max, final int spd) {
            this.recoilBufCountMax = max;
            this.recoilBufCountSpeed = spd;
        }
    }
}
