package mcheli.helicopter;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import mcheli.*;
import net.minecraft.nbt.*;
import java.util.*;
import mcheli.aircraft.*;
import mcheli.wrapper.*;
import mcheli.particles.*;
import net.minecraft.util.*;

public class MCH_EntityHeli extends MCH_EntityAircraft
{
    public static final byte FOLD_STAT_FOLDED = 0;
    public static final byte FOLD_STAT_FOLDING = 1;
    public static final byte FOLD_STAT_UNFOLDED = 2;
    public static final byte FOLD_STAT_UNFOLDING = 3;
    private MCH_HeliInfo heliInfo;
    public double prevRotationRotor;
    public double rotationRotor;
    public MCH_Rotor[] rotors;
    public byte lastFoldBladeStat;
    public int foldBladesCooldown;
    public float prevRollFactor;
    
    public MCH_EntityHeli(final World world) {
        super(world);
        this.prevRotationRotor = 0.0;
        this.rotationRotor = 0.0;
        this.prevRollFactor = 0.0f;
        this.heliInfo = null;
        this.currentSpeed = 0.07;
        this.field_70156_m = true;
        this.func_70105_a(2.0f, 0.7f);
        this.field_70129_M = this.field_70131_O / 2.0f;
        this.field_70159_w = 0.0;
        this.field_70181_x = 0.0;
        this.field_70179_y = 0.0;
        this.weapons = this.createWeapon(0);
        this.rotors = new MCH_Rotor[0];
        this.lastFoldBladeStat = -1;
        if (this.field_70170_p.field_72995_K) {
            this.foldBladesCooldown = 40;
        }
    }
    
    @Override
    public String getKindName() {
        return "helicopters";
    }
    
    @Override
    public String getEntityType() {
        return "Plane";
    }
    
    public MCH_HeliInfo getHeliInfo() {
        return this.heliInfo;
    }
    
    @Override
    public void changeType(final String type) {
        if (!type.isEmpty()) {
            this.heliInfo = MCH_HeliInfoManager.get(type);
        }
        if (this.heliInfo == null) {
            MCH_Lib.Log(this, "##### MCH_EntityHeli changeHeliType() Heli info null %d, %s, %s", W_Entity.getEntityId(this), type, this.getEntityName());
            this.setDead(true);
        }
        else {
            this.setAcInfo(this.heliInfo);
            this.newSeats(this.getAcInfo().getNumSeatAndRack());
            this.createRotors();
            this.weapons = this.createWeapon(1 + this.getSeatNum());
            this.initPartRotation(this.getRotYaw(), this.getRotPitch());
        }
    }
    
    @Override
    public Item getItem() {
        return (this.getHeliInfo() != null) ? this.getHeliInfo().item : null;
    }
    
    @Override
    public boolean canMountWithNearEmptyMinecart() {
        final MCH_Config config = MCH_MOD.config;
        return MCH_Config.MountMinecartHeli.prmBool;
    }
    
    @Override
    protected void func_70088_a() {
        super.func_70088_a();
        this.field_70180_af.func_75682_a(30, (Object)(byte)2);
    }
    
    @Override
    protected void func_70014_b(final NBTTagCompound par1NBTTagCompound) {
        super.func_70014_b(par1NBTTagCompound);
        par1NBTTagCompound.func_74780_a("RotorSpeed", this.getCurrentThrottle());
        par1NBTTagCompound.func_74780_a("rotetionRotor", this.rotationRotor);
        par1NBTTagCompound.func_74757_a("FoldBlade", this.getFoldBladeStat() == 0);
    }
    
    @Override
    protected void func_70037_a(final NBTTagCompound par1NBTTagCompound) {
        super.func_70037_a(par1NBTTagCompound);
        final boolean beforeFoldBlade = this.getFoldBladeStat() == 0;
        if (this.getCommonUniqueId().isEmpty()) {
            this.setCommonUniqueId(par1NBTTagCompound.func_74779_i("HeliUniqueId"));
            MCH_Lib.Log(this, "# MCH_EntityHeli readEntityFromNBT() " + W_Entity.getEntityId(this) + ", " + this.getEntityName() + ", AircraftUniqueId=null, HeliUniqueId=" + this.getCommonUniqueId(), new Object[0]);
        }
        if (this.getTypeName().isEmpty()) {
            this.setTypeName(par1NBTTagCompound.func_74779_i("HeliType"));
            MCH_Lib.Log(this, "# MCH_EntityHeli readEntityFromNBT() " + W_Entity.getEntityId(this) + ", " + this.getEntityName() + ", TypeName=null, HeliType=" + this.getTypeName(), new Object[0]);
        }
        this.setCurrentThrottle(par1NBTTagCompound.func_74769_h("RotorSpeed"));
        this.rotationRotor = par1NBTTagCompound.func_74769_h("rotetionRotor");
        this.setFoldBladeStat((byte)(par1NBTTagCompound.func_74767_n("FoldBlade") ? 0 : 2));
        if (this.heliInfo == null) {
            this.heliInfo = MCH_HeliInfoManager.get(this.getTypeName());
            if (this.heliInfo == null) {
                MCH_Lib.Log(this, "##### MCH_EntityHeli readEntityFromNBT() Heli info null %d, %s", W_Entity.getEntityId(this), this.getEntityName());
                this.setDead(true);
            }
            else {
                this.setAcInfo(this.heliInfo);
            }
        }
        if (!beforeFoldBlade && this.getFoldBladeStat() == 0) {
            this.forceFoldBlade();
        }
        this.prevRotationRotor = this.rotationRotor;
    }
    
    @Override
    public float getSoundVolume() {
        if (this.getAcInfo() != null && this.getAcInfo().throttleUpDown <= 0.0f) {
            return 0.0f;
        }
        return (float)this.getCurrentThrottle() * 2.0f;
    }
    
    @Override
    public float getSoundPitch() {
        return (float)(0.2 + this.getCurrentThrottle() * 0.2);
    }
    
    @Override
    public String getDefaultSoundName() {
        return "heli";
    }
    
    @Override
    public float getUnfoldLandingGearThrottle() {
        final double x = this.field_70165_t - this.field_70169_q;
        final double y = this.field_70163_u - this.field_70167_r;
        final double z = this.field_70161_v - this.field_70166_s;
        final float s = this.getAcInfo().speed / 3.5f;
        return (x * x + y * y + z * z <= s) ? 0.8f : 0.3f;
    }
    
    protected void createRotors() {
        if (this.heliInfo == null) {
            return;
        }
        this.rotors = new MCH_Rotor[this.heliInfo.rotorList.size()];
        int i = 0;
        for (final MCH_HeliInfo.Rotor r : this.heliInfo.rotorList) {
            this.rotors[i] = new MCH_Rotor(r.bladeNum, r.bladeRot, this.field_70170_p.field_72995_K ? 2 : 2, (float)r.pos.field_72450_a, (float)r.pos.field_72448_b, (float)r.pos.field_72449_c, (float)r.rot.field_72450_a, (float)r.rot.field_72448_b, (float)r.rot.field_72449_c, r.haveFoldFunc);
            ++i;
        }
    }
    
    protected void forceFoldBlade() {
        if (this.heliInfo != null && this.rotors.length > 0 && this.heliInfo.isEnableFoldBlade) {
            for (final MCH_Rotor r : this.rotors) {
                r.update((float)this.rotationRotor);
                this.foldBlades();
                r.forceFold();
            }
        }
    }
    
    public boolean isFoldBlades() {
        return this.heliInfo != null && this.rotors.length > 0 && this.getFoldBladeStat() == 0;
    }
    
    protected boolean canSwitchFoldBlades() {
        return this.heliInfo != null && this.rotors.length > 0 && this.heliInfo.isEnableFoldBlade && this.getCurrentThrottle() <= 0.01 && this.foldBladesCooldown == 0 && (this.getFoldBladeStat() == 2 || this.getFoldBladeStat() == 0);
    }
    
    protected boolean canUseBlades() {
        if (this.heliInfo == null) {
            return false;
        }
        if (this.rotors.length <= 0) {
            return true;
        }
        if (this.getFoldBladeStat() == 2) {
            for (final MCH_Rotor r : this.rotors) {
                if (r.isFoldingOrUnfolding()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    protected void foldBlades() {
        if (this.heliInfo == null || this.rotors.length <= 0) {
            return;
        }
        this.setCurrentThrottle(0.0);
        for (final MCH_Rotor r : this.rotors) {
            r.startFold();
        }
    }
    
    public void unfoldBlades() {
        if (this.heliInfo == null || this.rotors.length <= 0) {
            return;
        }
        for (final MCH_Rotor r : this.rotors) {
            r.startUnfold();
        }
    }
    
    @Override
    public void onRideEntity(final Entity ridingEntity) {
        if (ridingEntity instanceof MCH_EntitySeat) {
            if (this.heliInfo == null || this.rotors.length <= 0) {
                return;
            }
            if (this.heliInfo.isEnableFoldBlade) {
                this.forceFoldBlade();
                this.setFoldBladeStat((byte)0);
            }
        }
    }
    
    protected byte getFoldBladeStat() {
        return this.field_70180_af.func_75683_a(30);
    }
    
    public void setFoldBladeStat(final byte b) {
        if (!this.field_70170_p.field_72995_K && b >= 0 && b <= 3) {
            this.field_70180_af.func_75692_b(30, (Object)b);
        }
    }
    
    @Override
    public boolean canSwitchGunnerMode() {
        if (super.canSwitchGunnerMode() && this.canUseBlades()) {
            final float roll = MathHelper.func_76135_e(MathHelper.func_76142_g(this.getRotRoll()));
            final float pitch = MathHelper.func_76135_e(MathHelper.func_76142_g(this.getRotPitch()));
            if (roll < 40.0f && pitch < 40.0f) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean canSwitchHoveringMode() {
        if (super.canSwitchHoveringMode() && this.canUseBlades()) {
            final float roll = MathHelper.func_76135_e(MathHelper.func_76142_g(this.getRotRoll()));
            final float pitch = MathHelper.func_76135_e(MathHelper.func_76142_g(this.getRotPitch()));
            if (roll < 40.0f && pitch < 40.0f) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void onUpdateAircraft() {
        if (this.heliInfo == null) {
            this.changeType(this.getTypeName());
            this.field_70169_q = this.field_70165_t;
            this.field_70167_r = this.field_70163_u;
            this.field_70166_s = this.field_70161_v;
            return;
        }
        if (!this.isRequestedSyncStatus) {
            this.isRequestedSyncStatus = true;
            if (this.field_70170_p.field_72995_K) {
                final int stat = this.getFoldBladeStat();
                if (stat == 1 || stat == 0) {
                    this.forceFoldBlade();
                }
                MCH_PacketStatusRequest.requestStatus(this);
            }
        }
        if (this.lastRiddenByEntity == null && this.getRiddenByEntity() != null) {
            this.initCurrentWeapon(this.getRiddenByEntity());
        }
        this.updateWeapons();
        this.onUpdate_Seats();
        this.onUpdate_Control();
        this.onUpdate_Rotor();
        this.field_70169_q = this.field_70165_t;
        this.field_70167_r = this.field_70163_u;
        this.field_70166_s = this.field_70161_v;
        if (!this.isDestroyed() && this.isHovering() && MathHelper.func_76135_e(this.getRotPitch()) < 70.0f) {
            this.setRotPitch(this.getRotPitch() * 0.95f);
        }
        if (this.isDestroyed() && this.getCurrentThrottle() > 0.0) {
            if (MCH_Lib.getBlockIdY(this, 3, -2) > 0) {
                this.setCurrentThrottle(this.getCurrentThrottle() * 0.8);
            }
            if (this.isExploded()) {
                this.setCurrentThrottle(this.getCurrentThrottle() * 0.98);
            }
        }
        this.updateCameraViewers();
        if (this.field_70170_p.field_72995_K) {
            this.onUpdate_Client();
        }
        else {
            this.onUpdate_Server();
        }
    }
    
    @Override
    public boolean canMouseRot() {
        return super.canMouseRot();
    }
    
    @Override
    public boolean canUpdatePitch(final Entity player) {
        return super.canUpdatePitch(player) && !this.isHovering();
    }
    
    @Override
    public boolean canUpdateRoll(final Entity player) {
        return super.canUpdateRoll(player) && !this.isHovering();
    }
    
    @Override
    public boolean isOverridePlayerPitch() {
        return super.isOverridePlayerPitch() && !this.isHovering();
    }
    
    @Override
    public float getRollFactor() {
        final float roll = super.getRollFactor();
        double d = this.func_70092_e(this.field_70169_q, this.field_70163_u, this.field_70166_s);
        final double s = this.getAcInfo().speed;
        d = ((s > 0.1) ? (d / s) : 0.0);
        final float f = this.prevRollFactor;
        return ((this.prevRollFactor = roll) + f) / 2.0f;
    }
    
    @Override
    public float getControlRotYaw(final float mouseX, final float mouseY, final float tick) {
        return mouseX;
    }
    
    @Override
    public float getControlRotPitch(final float mouseX, final float mouseY, final float tick) {
        return mouseY;
    }
    
    @Override
    public float getControlRotRoll(final float mouseX, final float mouseY, final float tick) {
        return mouseX;
    }
    
    @Override
    public void onUpdateAngles(final float partialTicks) {
        if (this.isDestroyed()) {
            return;
        }
        float rotRoll = this.isHovering() ? 0.07f : 0.04f;
        rotRoll = 1.0f - rotRoll * partialTicks;
        if (this.getRotRoll() > 0.1 && this.getRotRoll() < 65.0f) {
            this.setRotRoll(this.getRotRoll() * rotRoll);
        }
        if (this.getRotRoll() < -0.1 && this.getRotRoll() > -65.0f) {
            this.setRotRoll(this.getRotRoll() * rotRoll);
        }
        if (MCH_Lib.getBlockIdY(this, 3, -3) == 0) {
            if (this.moveLeft && !this.moveRight) {
                this.setRotRoll(this.getRotRoll() - 1.2f * partialTicks);
            }
            if (this.moveRight && !this.moveLeft) {
                this.setRotRoll(this.getRotRoll() + 1.2f * partialTicks);
            }
        }
        else {
            if (MathHelper.func_76135_e(this.getRotPitch()) < 40.0f) {
                this.applyOnGroundPitch(0.97f);
            }
            if (this.heliInfo.isEnableFoldBlade && this.rotors.length > 0 && this.getFoldBladeStat() == 0 && !this.isDestroyed()) {
                if (this.moveLeft && !this.moveRight) {
                    this.setRotYaw(this.getRotYaw() - 0.5f * partialTicks);
                }
                if (this.moveRight && !this.moveLeft) {
                    this.setRotYaw(this.getRotYaw() + 0.5f * partialTicks);
                }
            }
        }
    }
    
    protected void onUpdate_Rotor() {
        final byte stat = this.getFoldBladeStat();
        boolean isEndSwitch = true;
        if (stat != this.lastFoldBladeStat) {
            if (stat == 1) {
                this.foldBlades();
            }
            else if (stat == 3) {
                this.unfoldBlades();
            }
            if (this.field_70170_p.field_72995_K) {
                this.foldBladesCooldown = 40;
            }
            this.lastFoldBladeStat = stat;
        }
        else if (this.foldBladesCooldown > 0) {
            --this.foldBladesCooldown;
        }
        for (final MCH_Rotor r : this.rotors) {
            r.update((float)this.rotationRotor);
            if (r.isFoldingOrUnfolding()) {
                isEndSwitch = false;
            }
        }
        if (isEndSwitch) {
            if (stat == 1) {
                this.setFoldBladeStat((byte)0);
            }
            else if (stat == 3) {
                this.setFoldBladeStat((byte)2);
            }
        }
    }
    
    protected void onUpdate_Control() {
        if (this.isHoveringMode() && !this.canUseFuel(true)) {
            this.switchHoveringMode(false);
        }
        if (this.isGunnerMode && !this.canUseFuel()) {
            this.switchGunnerMode(false);
        }
        if (!this.isDestroyed() && (this.getRiddenByEntity() != null || this.isHoveringMode()) && this.canUseBlades() && this.isCanopyClose() && this.canUseFuel(true)) {
            if (!this.isHovering()) {
                this.onUpdate_ControlNotHovering();
            }
            else {
                this.onUpdate_ControlHovering();
            }
        }
        else {
            if (this.getCurrentThrottle() > 0.0) {
                this.addCurrentThrottle(-0.00125);
            }
            else {
                this.setCurrentThrottle(0.0);
            }
            if (this.heliInfo.isEnableFoldBlade && this.rotors.length > 0 && this.getFoldBladeStat() == 0 && this.field_70122_E && !this.isDestroyed()) {
                this.onUpdate_ControlFoldBladeAndOnGround();
            }
        }
        if (this.field_70170_p.field_72995_K) {
            if (!W_Lib.isClientPlayer(this.getRiddenByEntity())) {
                final double ct = this.getThrottle();
                if (this.getCurrentThrottle() >= ct - 0.02) {
                    this.addCurrentThrottle(-0.01);
                }
                else if (this.getCurrentThrottle() < ct) {
                    this.addCurrentThrottle(0.01);
                }
            }
        }
        else {
            this.setThrottle(this.getCurrentThrottle());
        }
        if (this.getCurrentThrottle() < 0.0) {
            this.setCurrentThrottle(0.0);
        }
        this.prevRotationRotor = this.rotationRotor;
        final float rp = (float)(1.0 - this.getCurrentThrottle());
        this.rotationRotor += (1.0f - rp * rp * rp) * this.getAcInfo().rotorSpeed;
        this.rotationRotor %= 360.0;
    }
    
    protected void onUpdate_ControlNotHovering() {
        final float throttleUpDown = this.getAcInfo().throttleUpDown;
        if (this.throttleUp) {
            if (this.getCurrentThrottle() < 1.0) {
                this.addCurrentThrottle(0.02 * throttleUpDown);
            }
            else {
                this.setCurrentThrottle(1.0);
            }
        }
        else if (this.throttleDown) {
            if (this.getCurrentThrottle() > 0.0) {
                this.addCurrentThrottle(-0.014285714285714285 * throttleUpDown);
            }
            else {
                this.setCurrentThrottle(0.0);
            }
        }
        else if ((!this.field_70170_p.field_72995_K || W_Lib.isClientPlayer(this.getRiddenByEntity())) && this.cs_heliAutoThrottleDown) {
            if (this.getCurrentThrottle() > 0.52) {
                this.addCurrentThrottle(-0.01 * throttleUpDown);
            }
            else if (this.getCurrentThrottle() < 0.48) {
                this.addCurrentThrottle(0.01 * throttleUpDown);
            }
        }
        if (!this.field_70170_p.field_72995_K) {
            boolean move = false;
            float yaw = this.getRotYaw();
            double x = 0.0;
            double z = 0.0;
            if (this.moveLeft && !this.moveRight) {
                yaw = this.getRotYaw() - 90.0f;
                x += Math.sin(yaw * 3.141592653589793 / 180.0);
                z += Math.cos(yaw * 3.141592653589793 / 180.0);
                move = true;
            }
            if (this.moveRight && !this.moveLeft) {
                yaw = this.getRotYaw() + 90.0f;
                x += Math.sin(yaw * 3.141592653589793 / 180.0);
                z += Math.cos(yaw * 3.141592653589793 / 180.0);
                move = true;
            }
            if (move) {
                final double f = 1.0;
                final double d = Math.sqrt(x * x + z * z);
                this.field_70159_w -= x / d * 0.019999999552965164 * f * this.getAcInfo().speed;
                this.field_70179_y += z / d * 0.019999999552965164 * f * this.getAcInfo().speed;
            }
        }
    }
    
    protected void onUpdate_ControlHovering() {
        if (this.getCurrentThrottle() < 1.0) {
            this.addCurrentThrottle(0.03333333333333333);
        }
        else {
            this.setCurrentThrottle(1.0);
        }
        if (!this.field_70170_p.field_72995_K) {
            boolean move = false;
            float yaw = this.getRotYaw();
            double x = 0.0;
            double z = 0.0;
            if (this.throttleUp) {
                yaw = this.getRotYaw();
                x += Math.sin(yaw * 3.141592653589793 / 180.0);
                z += Math.cos(yaw * 3.141592653589793 / 180.0);
                move = true;
            }
            if (this.throttleDown) {
                yaw = this.getRotYaw() - 180.0f;
                x += Math.sin(yaw * 3.141592653589793 / 180.0);
                z += Math.cos(yaw * 3.141592653589793 / 180.0);
                move = true;
            }
            if (this.moveLeft && !this.moveRight) {
                yaw = this.getRotYaw() - 90.0f;
                x += Math.sin(yaw * 3.141592653589793 / 180.0);
                z += Math.cos(yaw * 3.141592653589793 / 180.0);
                move = true;
            }
            if (this.moveRight && !this.moveLeft) {
                yaw = this.getRotYaw() + 90.0f;
                x += Math.sin(yaw * 3.141592653589793 / 180.0);
                z += Math.cos(yaw * 3.141592653589793 / 180.0);
                move = true;
            }
            if (move) {
                final double d = Math.sqrt(x * x + z * z);
                this.field_70159_w -= x / d * 0.009999999776482582 * this.getAcInfo().speed;
                this.field_70179_y += z / d * 0.009999999776482582 * this.getAcInfo().speed;
            }
        }
    }
    
    protected void onUpdate_ControlFoldBladeAndOnGround() {
        if (!this.field_70170_p.field_72995_K) {
            boolean move = false;
            float yaw = this.getRotYaw();
            double x = 0.0;
            double z = 0.0;
            if (this.throttleUp) {
                yaw = this.getRotYaw();
                x += Math.sin(yaw * 3.141592653589793 / 180.0);
                z += Math.cos(yaw * 3.141592653589793 / 180.0);
                move = true;
            }
            if (this.throttleDown) {
                yaw = this.getRotYaw() - 180.0f;
                x += Math.sin(yaw * 3.141592653589793 / 180.0);
                z += Math.cos(yaw * 3.141592653589793 / 180.0);
                move = true;
            }
            if (move) {
                final double d = Math.sqrt(x * x + z * z);
                this.field_70159_w -= x / d * 0.029999999329447746;
                this.field_70179_y += z / d * 0.029999999329447746;
            }
        }
    }
    
    protected void onUpdate_Particle2() {
        if (!this.field_70170_p.field_72995_K) {
            return;
        }
        if (this.getHP() > this.getMaxHP() * 0.5) {
            return;
        }
        if (this.getHeliInfo() == null) {
            return;
        }
        final int rotorNum = this.getHeliInfo().rotorList.size();
        if (rotorNum <= 0) {
            return;
        }
        if (this.isFirstDamageSmoke) {
            this.prevDamageSmokePos = new Vec3[rotorNum];
        }
        for (int ri = 0; ri < rotorNum; ++ri) {
            final Vec3 rotor_pos = this.getHeliInfo().rotorList.get(ri).pos;
            final float yaw = this.getRotYaw();
            final float pitch = this.getRotPitch();
            final Vec3 pos = MCH_Lib.RotVec3(rotor_pos, -yaw, -pitch, -this.getRotRoll());
            final double x = this.field_70165_t + pos.field_72450_a;
            final double y = this.field_70163_u + pos.field_72448_b;
            final double z = this.field_70161_v + pos.field_72449_c;
            if (this.isFirstDamageSmoke) {
                this.prevDamageSmokePos[ri] = Vec3.func_72443_a(x, y, z);
            }
            final Vec3 prev = this.prevDamageSmokePos[ri];
            final double dx = x - prev.field_72450_a;
            final double dy = y - prev.field_72448_b;
            final double dz = z - prev.field_72449_c;
            final int num = (int)(MathHelper.func_76133_a(dx * dx + dy * dy + dz * dz) * 2.0f) + 1;
            for (double i = 0.0; i < num; ++i) {
                final double p = this.getHP() / this.getMaxHP();
                if (p < this.field_70146_Z.nextFloat() / 2.0f) {
                    final float c = 0.2f + this.field_70146_Z.nextFloat() * 0.3f;
                    final MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", prev.field_72450_a + (x - prev.field_72450_a) * (i / num), prev.field_72448_b + (y - prev.field_72448_b) * (i / num), prev.field_72449_c + (z - prev.field_72449_c) * (i / num));
                    prm.motionX = (this.field_70146_Z.nextDouble() - 0.5) * 0.3;
                    prm.motionY = this.field_70146_Z.nextDouble() * 0.1;
                    prm.motionZ = (this.field_70146_Z.nextDouble() - 0.5) * 0.3;
                    prm.size = (this.field_70146_Z.nextInt(5) + 5.0f) * 1.0f;
                    prm.setColor(0.7f + this.field_70146_Z.nextFloat() * 0.1f, c, c, c);
                    MCH_ParticlesUtil.spawnParticle(prm);
                    final int ebi = this.field_70146_Z.nextInt(1 + this.extraBoundingBox.length);
                    if (p < 0.3 && ebi > 0) {
                        final AxisAlignedBB bb = this.extraBoundingBox[ebi - 1].boundingBox;
                        final double bx = (bb.field_72336_d + bb.field_72340_a) / 2.0;
                        final double by = (bb.field_72337_e + bb.field_72338_b) / 2.0;
                        final double bz = (bb.field_72334_f + bb.field_72339_c) / 2.0;
                        prm.posX = bx;
                        prm.posY = by;
                        prm.posZ = bz;
                        MCH_ParticlesUtil.spawnParticle(prm);
                    }
                }
            }
            this.prevDamageSmokePos[ri].field_72450_a = x;
            this.prevDamageSmokePos[ri].field_72448_b = y;
            this.prevDamageSmokePos[ri].field_72449_c = z;
        }
        this.isFirstDamageSmoke = false;
    }
    
    protected void onUpdate_Client() {
        if (this.getRiddenByEntity() != null && W_Lib.isClientPlayer(this.getRiddenByEntity())) {
            this.getRiddenByEntity().field_70125_A = this.getRiddenByEntity().field_70127_C;
        }
        if (this.aircraftPosRotInc > 0) {
            this.applyServerPositionAndRotation();
        }
        else {
            this.func_70107_b(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
            if (!this.isDestroyed() && (this.field_70122_E || MCH_Lib.getBlockIdY(this, 1, -2) > 0)) {
                this.field_70159_w *= 0.95;
                this.field_70179_y *= 0.95;
                this.applyOnGroundPitch(0.95f);
            }
            if (this.func_70090_H()) {
                this.field_70159_w *= 0.99;
                this.field_70179_y *= 0.99;
            }
        }
        if (this.isDestroyed()) {
            if (this.rotDestroyedYaw < 15.0f) {
                this.rotDestroyedYaw += 0.3f;
            }
            this.setRotYaw(this.getRotYaw() + this.rotDestroyedYaw * (float)this.getCurrentThrottle());
            if (MCH_Lib.getBlockIdY(this, 3, -3) == 0) {
                if (MathHelper.func_76135_e(this.getRotPitch()) < 10.0f) {
                    this.setRotPitch(this.getRotPitch() + this.rotDestroyedPitch);
                }
                this.setRotRoll(this.getRotRoll() + this.rotDestroyedRoll);
            }
        }
        if (this.getRiddenByEntity() != null) {}
        this.onUpdate_ParticleSandCloud(false);
        this.onUpdate_Particle2();
        this.updateCamera(this.field_70165_t, this.field_70163_u, this.field_70161_v);
    }
    
    private void onUpdate_Server() {
        final double prevMotion = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
        final float ogp = this.getAcInfo().onGroundPitch;
        if (!this.isHovering()) {
            double dp = 0.0;
            if (this.canFloatWater()) {
                dp = this.getWaterDepth();
            }
            if (dp == 0.0) {
                this.field_70181_x += (this.func_70090_H() ? this.getAcInfo().gravityInWater : ((double)this.getAcInfo().gravity));
                final float yaw = this.getRotYaw() / 180.0f * 3.1415927f;
                float pitch = this.getRotPitch();
                if (MCH_Lib.getBlockIdY(this, 3, -3) > 0) {
                    pitch -= ogp;
                }
                this.field_70159_w += 0.1 * MathHelper.func_76126_a(yaw) * this.currentSpeed * -(pitch * pitch * pitch / 30000.0f) * this.getCurrentThrottle();
                this.field_70179_y += 0.1 * MathHelper.func_76134_b(yaw) * this.currentSpeed * (pitch * pitch * pitch / 30000.0f) * this.getCurrentThrottle();
                double y = MathHelper.func_76135_e(this.getRotPitch()) + MathHelper.func_76135_e(this.getRotRoll());
                y *= 0.6000000238418579;
                if (y <= 50.0) {
                    y = 1.0 - y / 50.0;
                }
                else {
                    y = 0.0;
                }
                double throttle = this.getCurrentThrottle();
                if (this.isDestroyed()) {
                    throttle *= 0.65;
                }
                this.field_70181_x += (y * 0.025 + 0.03) * throttle;
            }
            else {
                if (MathHelper.func_76135_e(this.getRotPitch()) < 40.0f) {
                    float pitch2 = this.getRotPitch();
                    pitch2 -= ogp;
                    pitch2 *= 0.9f;
                    pitch2 += ogp;
                    this.setRotPitch(pitch2);
                }
                if (MathHelper.func_76135_e(this.getRotRoll()) < 40.0f) {
                    this.setRotRoll(this.getRotRoll() * 0.9f);
                }
                if (dp < 1.0) {
                    this.field_70181_x -= 1.0E-4;
                    this.field_70181_x += 0.007 * this.getCurrentThrottle();
                }
                else {
                    if (this.field_70181_x < 0.0) {
                        this.field_70181_x *= 0.7;
                    }
                    this.field_70181_x += 0.007;
                }
            }
        }
        else {
            if (this.field_70146_Z.nextInt(50) == 0) {
                this.field_70159_w += (this.field_70146_Z.nextDouble() - 0.5) / 30.0;
            }
            if (this.field_70146_Z.nextInt(50) == 0) {
                this.field_70181_x += (this.field_70146_Z.nextDouble() - 0.5) / 50.0;
            }
            if (this.field_70146_Z.nextInt(50) == 0) {
                this.field_70179_y += (this.field_70146_Z.nextDouble() - 0.5) / 30.0;
            }
        }
        double motion = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
        final float speedLimit = this.getAcInfo().speed;
        if (motion > speedLimit) {
            this.field_70159_w *= speedLimit / motion;
            this.field_70179_y *= speedLimit / motion;
            motion = speedLimit;
        }
        if (motion > prevMotion && this.currentSpeed < speedLimit) {
            this.currentSpeed += (speedLimit - this.currentSpeed) / 35.0;
            if (this.currentSpeed > speedLimit) {
                this.currentSpeed = speedLimit;
            }
        }
        else {
            this.currentSpeed -= (this.currentSpeed - 0.07) / 35.0;
            if (this.currentSpeed < 0.07) {
                this.currentSpeed = 0.07;
            }
        }
        if (this.field_70122_E) {
            this.field_70159_w *= 0.5;
            this.field_70179_y *= 0.5;
            if (MathHelper.func_76135_e(this.getRotPitch()) < 40.0f) {
                float pitch = this.getRotPitch();
                pitch -= ogp;
                pitch *= 0.9f;
                pitch += ogp;
                this.setRotPitch(pitch);
            }
            if (MathHelper.func_76135_e(this.getRotRoll()) < 40.0f) {
                this.setRotRoll(this.getRotRoll() * 0.9f);
            }
        }
        this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
        this.field_70181_x *= 0.95;
        this.field_70159_w *= 0.99;
        this.field_70179_y *= 0.99;
        this.func_70101_b(this.getRotYaw(), this.getRotPitch());
        this.onUpdate_updateBlock();
        if (this.getRiddenByEntity() != null && this.getRiddenByEntity().field_70128_L) {
            this.unmountEntity();
            this.field_70153_n = null;
        }
    }
}
