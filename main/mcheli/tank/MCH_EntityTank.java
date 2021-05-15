package mcheli.tank;

import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.crash.*;
import net.minecraft.block.*;
import mcheli.particles.*;
import java.util.*;
import mcheli.wrapper.*;
import net.minecraft.command.*;
import net.minecraft.entity.item.*;
import mcheli.chain.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import mcheli.*;
import mcheli.weapon.*;
import mcheli.aircraft.*;

public class MCH_EntityTank extends MCH_EntityAircraft
{
    private MCH_TankInfo tankInfo;
    public float soundVolume;
    public float soundVolumeTarget;
    public float rotationRotor;
    public float prevRotationRotor;
    public float addkeyRotValue;
    public final MCH_WheelManager WheelMng;
    
    public MCH_EntityTank(final World world) {
        super(world);
        this.tankInfo = null;
        this.currentSpeed = 0.07;
        this.field_70156_m = true;
        this.func_70105_a(2.0f, 0.7f);
        this.field_70129_M = this.field_70131_O / 2.0f;
        this.field_70159_w = 0.0;
        this.field_70181_x = 0.0;
        this.field_70179_y = 0.0;
        this.weapons = this.createWeapon(0);
        this.soundVolume = 0.0f;
        this.field_70138_W = 0.6f;
        this.rotationRotor = 0.0f;
        this.prevRotationRotor = 0.0f;
        this.WheelMng = new MCH_WheelManager(this);
    }
    
    @Override
    public String getKindName() {
        return "tanks";
    }
    
    @Override
    public String getEntityType() {
        return "Vehicle";
    }
    
    public MCH_TankInfo getTankInfo() {
        return this.tankInfo;
    }
    
    @Override
    public void changeType(final String type) {
        if (!type.isEmpty()) {
            this.tankInfo = MCH_TankInfoManager.get(type);
        }
        if (this.tankInfo == null) {
            MCH_Lib.Log(this, "##### MCH_EntityTank changeTankType() Tank info null %d, %s, %s", W_Entity.getEntityId(this), type, this.getEntityName());
            this.func_70106_y();
        }
        else {
            this.setAcInfo(this.tankInfo);
            this.newSeats(this.getAcInfo().getNumSeatAndRack());
            this.switchFreeLookModeClient(this.getAcInfo().defaultFreelook);
            this.weapons = this.createWeapon(1 + this.getSeatNum());
            this.initPartRotation(this.getRotYaw(), this.getRotPitch());
            this.WheelMng.createWheels(this.field_70170_p, this.getAcInfo().wheels, Vec3.func_72443_a(0.0, -0.35, (double)this.getTankInfo().weightedCenterZ));
        }
    }
    
    @Override
    public Item getItem() {
        return (this.getTankInfo() != null) ? this.getTankInfo().item : null;
    }
    
    @Override
    public boolean canMountWithNearEmptyMinecart() {
        final MCH_Config config = MCH_MOD.config;
        return MCH_Config.MountMinecartTank.prmBool;
    }
    
    @Override
    protected void func_70088_a() {
        super.func_70088_a();
    }
    
    @Override
    public float getGiveDamageRot() {
        return 91.0f;
    }
    
    @Override
    protected void func_70014_b(final NBTTagCompound par1NBTTagCompound) {
        super.func_70014_b(par1NBTTagCompound);
    }
    
    @Override
    protected void func_70037_a(final NBTTagCompound par1NBTTagCompound) {
        super.func_70037_a(par1NBTTagCompound);
        if (this.tankInfo == null) {
            this.tankInfo = MCH_TankInfoManager.get(this.getTypeName());
            if (this.tankInfo == null) {
                MCH_Lib.Log(this, "##### MCH_EntityTank readEntityFromNBT() Tank info null %d, %s", W_Entity.getEntityId(this), this.getEntityName());
                this.func_70106_y();
            }
            else {
                this.setAcInfo(this.tankInfo);
            }
        }
    }
    
    @Override
    public void func_70106_y() {
        super.func_70106_y();
    }
    
    @Override
    public void onInteractFirst(final EntityPlayer player) {
        this.addkeyRotValue = 0.0f;
        final float lastRiderYaw = this.getLastRiderYaw();
        player.field_70758_at = lastRiderYaw;
        player.field_70759_as = lastRiderYaw;
        final float lastRiderYaw2 = this.getLastRiderYaw();
        player.field_70177_z = lastRiderYaw2;
        player.field_70126_B = lastRiderYaw2;
        player.field_70125_A = this.getLastRiderPitch();
    }
    
    @Override
    public boolean canSwitchGunnerMode() {
        return !super.canSwitchGunnerMode() && false;
    }
    
    @Override
    public void onUpdateAircraft() {
        if (this.tankInfo == null) {
            this.changeType(this.getTypeName());
            this.field_70169_q = this.field_70165_t;
            this.field_70167_r = this.field_70163_u;
            this.field_70166_s = this.field_70161_v;
            return;
        }
        if (!this.isRequestedSyncStatus) {
            this.isRequestedSyncStatus = true;
            if (this.field_70170_p.field_72995_K) {
                MCH_PacketStatusRequest.requestStatus(this);
            }
        }
        if (this.lastRiddenByEntity == null && this.getRiddenByEntity() != null) {
            this.initCurrentWeapon(this.getRiddenByEntity());
        }
        this.updateWeapons();
        this.onUpdate_Seats();
        this.onUpdate_Control();
        this.prevRotationRotor = this.rotationRotor;
        this.rotationRotor += (float)(this.getCurrentThrottle() * this.getAcInfo().rotorSpeed);
        if (this.rotationRotor > 360.0f) {
            this.rotationRotor -= 360.0f;
            this.prevRotationRotor -= 360.0f;
        }
        if (this.rotationRotor < 0.0f) {
            this.rotationRotor += 360.0f;
            this.prevRotationRotor += 360.0f;
        }
        this.field_70169_q = this.field_70165_t;
        this.field_70167_r = this.field_70163_u;
        this.field_70166_s = this.field_70161_v;
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
    
    @SideOnly(Side.CLIENT)
    public boolean func_90999_ad() {
        return this.isDestroyed() || super.func_90999_ad();
    }
    
    @Override
    public void updateExtraBoundingBox() {
        if (this.field_70170_p.field_72995_K) {
            super.updateExtraBoundingBox();
        }
        else if (this.getCountOnUpdate() <= 1) {
            super.updateExtraBoundingBox();
            super.updateExtraBoundingBox();
        }
    }
    
    public double calculateXOffset(final List list, final AxisAlignedBB bb, double parX) {
        for (int i = 0; i < list.size(); ++i) {
            parX = list.get(i).func_72316_a(bb, parX);
        }
        bb.func_72317_d(parX, 0.0, 0.0);
        return parX;
    }
    
    public double calculateYOffset(final List list, final AxisAlignedBB bb, double parY) {
        for (int i = 0; i < list.size(); ++i) {
            parY = list.get(i).func_72323_b(bb, parY);
        }
        bb.func_72317_d(0.0, parY, 0.0);
        return parY;
    }
    
    public double calculateZOffset(final List list, final AxisAlignedBB bb, double parZ) {
        for (int i = 0; i < list.size(); ++i) {
            parZ = list.get(i).func_72322_c(bb, parZ);
        }
        bb.func_72317_d(0.0, 0.0, parZ);
        return parZ;
    }
    
    @Override
    public void func_70091_d(double parX, double parY, double parZ) {
        this.field_70170_p.field_72984_F.func_76320_a("move");
        this.field_70139_V *= 0.4f;
        final double nowPosX = this.field_70165_t;
        final double nowPosY = this.field_70163_u;
        final double nowPosZ = this.field_70161_v;
        final double mx = parX;
        final double my = parY;
        final double mz = parZ;
        final AxisAlignedBB backUpAxisalignedBB = this.field_70121_D.func_72329_c();
        List list = MCH_EntityAircraft.getCollidingBoundingBoxes(this, this.field_70121_D.func_72321_a(parX, parY, parZ));
        parY = this.calculateYOffset(list, this.field_70121_D, parY);
        final boolean flag1 = this.field_70122_E || (my != parY && my < 0.0);
        for (final MCH_BoundingBox ebb : this.extraBoundingBox) {
            ebb.updatePosition(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.getRotYaw(), this.getRotPitch(), this.getRotRoll());
        }
        parX = this.calculateXOffset(list, this.field_70121_D, parX);
        parZ = this.calculateZOffset(list, this.field_70121_D, parZ);
        if (this.field_70138_W > 0.0f && flag1 && this.field_70139_V < 0.05f && (mx != parX || mz != parZ)) {
            final double bkParX = parX;
            final double bkParY = parY;
            final double bkParZ = parZ;
            parX = mx;
            parY = this.field_70138_W;
            parZ = mz;
            final AxisAlignedBB axisalignedbb1 = this.field_70121_D.func_72329_c();
            this.field_70121_D.func_72328_c(backUpAxisalignedBB);
            list = MCH_EntityAircraft.getCollidingBoundingBoxes(this, this.field_70121_D.func_72321_a(mx, parY, mz));
            parY = this.calculateYOffset(list, this.field_70121_D, parY);
            parX = this.calculateXOffset(list, this.field_70121_D, parX);
            parZ = this.calculateZOffset(list, this.field_70121_D, parZ);
            parY = this.calculateYOffset(list, this.field_70121_D, -this.field_70138_W);
            if (bkParX * bkParX + bkParZ * bkParZ >= parX * parX + parZ * parZ) {
                parX = bkParX;
                parY = bkParY;
                parZ = bkParZ;
                this.field_70121_D.func_72328_c(axisalignedbb1);
            }
        }
        final double prevPX = this.field_70165_t;
        final double prevPZ = this.field_70161_v;
        this.field_70170_p.field_72984_F.func_76319_b();
        this.field_70170_p.field_72984_F.func_76320_a("rest");
        final double minX = this.field_70121_D.field_72340_a;
        final double minZ = this.field_70121_D.field_72339_c;
        final double maxX = this.field_70121_D.field_72336_d;
        final double maxZ = this.field_70121_D.field_72334_f;
        this.field_70165_t = (minX + maxX) / 2.0;
        this.field_70163_u = this.field_70121_D.field_72338_b + this.field_70129_M - this.field_70139_V;
        this.field_70161_v = (minZ + maxZ) / 2.0;
        this.field_70123_F = (mx != parX || mz != parZ);
        this.field_70124_G = (my != parY);
        this.field_70122_E = (my != parY && my < 0.0);
        this.field_70132_H = (this.field_70123_F || this.field_70124_G);
        this.func_70064_a(parY, this.field_70122_E);
        if (mx != parX) {
            this.field_70159_w = 0.0;
        }
        if (my != parY) {
            this.field_70181_x = 0.0;
        }
        if (mz != parZ) {
            this.field_70179_y = 0.0;
        }
        try {
            this.doBlockCollisions();
        }
        catch (Throwable throwable) {
            final CrashReport crashreport = CrashReport.func_85055_a(throwable, "Checking entity tile collision");
            final CrashReportCategory crashreportcategory = crashreport.func_85058_a("Entity being checked for collision");
            this.func_85029_a(crashreportcategory);
        }
        this.field_70170_p.field_72984_F.func_76319_b();
    }
    
    private void rotationByKey(final float partialTicks) {
        final float rot = 0.2f;
        if (this.moveLeft && !this.moveRight) {
            this.addkeyRotValue -= rot * partialTicks;
        }
        if (this.moveRight && !this.moveLeft) {
            this.addkeyRotValue += rot * partialTicks;
        }
    }
    
    @Override
    public void onUpdateAngles(final float partialTicks) {
        if (this.isDestroyed()) {
            return;
        }
        if (this.isGunnerMode) {
            this.setRotPitch(this.getRotPitch() * 0.95f);
            this.setRotYaw(this.getRotYaw() + this.getAcInfo().autoPilotRot * 0.2f);
            if (MathHelper.func_76135_e(this.getRotRoll()) > 20.0f) {
                this.setRotRoll(this.getRotRoll() * 0.95f);
            }
        }
        this.updateRecoil(partialTicks);
        this.setRotPitch(this.getRotPitch() + (this.WheelMng.targetPitch - this.getRotPitch()) * partialTicks);
        this.setRotRoll(this.getRotRoll() + (this.WheelMng.targetRoll - this.getRotRoll()) * partialTicks);
        final boolean isFly = MCH_Lib.getBlockIdY(this, 3, -3) == 0;
        if (!isFly || (this.getAcInfo().isFloat && this.getWaterDepth() > 0.0)) {
            float gmy = 1.0f;
            if (!isFly) {
                gmy = this.getAcInfo().mobilityYawOnGround;
                if (!this.getAcInfo().canRotOnGround) {
                    final Block block = MCH_Lib.getBlockY(this, 3, -2, false);
                    if (!W_Block.isEqual(block, W_Block.getWater()) && !W_Block.isEqual(block, W_Blocks.field_150350_a)) {
                        gmy = 0.0f;
                    }
                }
            }
            final float pivotTurnThrottle = this.getAcInfo().pivotTurnThrottle;
            final double dx = this.field_70165_t - this.field_70169_q;
            final double dz = this.field_70161_v - this.field_70166_s;
            final double dist = dx * dx + dz * dz;
            if (pivotTurnThrottle <= 0.0f || this.getCurrentThrottle() >= pivotTurnThrottle || this.throttleBack >= pivotTurnThrottle / 10.0f || dist > this.throttleBack * 0.01) {
                float sf = (float)Math.sqrt((dist <= 1.0) ? dist : 1.0);
                if (pivotTurnThrottle <= 0.0f) {
                    sf = 1.0f;
                }
                final float flag = (!this.throttleUp && this.throttleDown && this.getCurrentThrottle() < pivotTurnThrottle + 0.05) ? -1.0f : 1.0f;
                if (this.moveLeft && !this.moveRight) {
                    this.setRotYaw(this.getRotYaw() - 0.6f * gmy * partialTicks * flag * sf);
                }
                if (this.moveRight && !this.moveLeft) {
                    this.setRotYaw(this.getRotYaw() + 0.6f * gmy * partialTicks * flag * sf);
                }
            }
        }
        this.addkeyRotValue *= (float)(1.0 - 0.1f * partialTicks);
    }
    
    protected void onUpdate_Control() {
        if (this.isGunnerMode && !this.canUseFuel()) {
            this.switchGunnerMode(false);
        }
        this.throttleBack *= 0.8;
        if (this.getBrake()) {
            this.throttleBack *= 0.5;
            if (this.getCurrentThrottle() > 0.0) {
                this.addCurrentThrottle(-0.02 * this.getAcInfo().throttleUpDown);
            }
            else {
                this.setCurrentThrottle(0.0);
            }
        }
        if (this.getRiddenByEntity() != null && !this.getRiddenByEntity().field_70128_L && this.isCanopyClose() && this.canUseFuel() && !this.isDestroyed()) {
            this.onUpdate_ControlSub();
        }
        else if (this.isTargetDrone() && this.canUseFuel() && !this.isDestroyed()) {
            this.throttleUp = true;
            this.onUpdate_ControlSub();
        }
        else if (this.getCurrentThrottle() > 0.0) {
            this.addCurrentThrottle(-0.0025 * this.getAcInfo().throttleUpDown);
        }
        else {
            this.setCurrentThrottle(0.0);
        }
        if (this.getCurrentThrottle() < 0.0) {
            this.setCurrentThrottle(0.0);
        }
        if (this.field_70170_p.field_72995_K) {
            if (!W_Lib.isClientPlayer(this.getRiddenByEntity()) || this.getCountOnUpdate() % 200 == 0) {
                final double ct = this.getThrottle();
                if (this.getCurrentThrottle() > ct) {
                    this.addCurrentThrottle(-0.005);
                }
                if (this.getCurrentThrottle() < ct) {
                    this.addCurrentThrottle(0.005);
                }
            }
        }
        else {
            this.setThrottle(this.getCurrentThrottle());
        }
    }
    
    protected void onUpdate_ControlSub() {
        if (!this.isGunnerMode) {
            final float throttleUpDown = this.getAcInfo().throttleUpDown;
            if (this.throttleUp) {
                float f = throttleUpDown;
                if (this.getRidingEntity() != null) {
                    final double mx = this.getRidingEntity().field_70159_w;
                    final double mz = this.getRidingEntity().field_70179_y;
                    f *= MathHelper.func_76133_a(mx * mx + mz * mz) * this.getAcInfo().throttleUpDownOnEntity;
                }
                if (this.getAcInfo().enableBack && this.throttleBack > 0.0f) {
                    this.throttleBack -= (float)(0.01 * f);
                }
                else {
                    this.throttleBack = 0.0f;
                    if (this.getCurrentThrottle() < 1.0) {
                        this.addCurrentThrottle(0.01 * f);
                    }
                    else {
                        this.setCurrentThrottle(1.0);
                    }
                }
            }
            else if (this.throttleDown) {
                if (this.getCurrentThrottle() > 0.0) {
                    this.addCurrentThrottle(-0.01 * throttleUpDown);
                }
                else {
                    this.setCurrentThrottle(0.0);
                    if (this.getAcInfo().enableBack) {
                        this.throttleBack += (float)(0.0025 * throttleUpDown);
                        if (this.throttleBack > 0.6f) {
                            this.throttleBack = 0.6f;
                        }
                    }
                }
            }
            else if (this.cs_tankAutoThrottleDown && this.getCurrentThrottle() > 0.0) {
                this.addCurrentThrottle(-0.005 * throttleUpDown);
                if (this.getCurrentThrottle() <= 0.0) {
                    this.setCurrentThrottle(0.0);
                }
            }
        }
    }
    
    protected void onUpdate_Particle2() {
        if (!this.field_70170_p.field_72995_K) {
            return;
        }
        if (this.getHP() >= this.getMaxHP() * 0.5) {
            return;
        }
        if (this.getTankInfo() == null) {
            return;
        }
        int bbNum = this.getTankInfo().extraBoundingBox.size();
        if (bbNum < 0) {
            bbNum = 0;
        }
        if (this.isFirstDamageSmoke || this.prevDamageSmokePos.length != bbNum + 1) {
            this.prevDamageSmokePos = new Vec3[bbNum + 1];
        }
        final float yaw = this.getRotYaw();
        final float pitch = this.getRotPitch();
        final float roll = this.getRotRoll();
        for (int ri = 0; ri < bbNum; ++ri) {
            if (this.getHP() >= this.getMaxHP() * 0.2 && this.getMaxHP() > 0) {
                final int d = (int)((this.getHP() / this.getMaxHP() - 0.2) / 0.3 * 15.0);
                if (d > 0 && this.field_70146_Z.nextInt(d) > 0) {
                    continue;
                }
            }
            final MCH_BoundingBox bb = this.getTankInfo().extraBoundingBox.get(ri);
            final Vec3 pos = this.getTransformedPosition(bb.offsetX, bb.offsetY, bb.offsetZ);
            final double x = pos.field_72450_a;
            final double y = pos.field_72448_b;
            final double z = pos.field_72449_c;
            this.onUpdate_Particle2SpawnSmoke(ri, x, y, z, 1.0f);
        }
        boolean b = true;
        if (this.getHP() >= this.getMaxHP() * 0.2 && this.getMaxHP() > 0) {
            final int d = (int)((this.getHP() / this.getMaxHP() - 0.2) / 0.3 * 15.0);
            if (d > 0 && this.field_70146_Z.nextInt(d) > 0) {
                b = false;
            }
        }
        if (b) {
            double px = this.field_70165_t;
            double py = this.field_70163_u;
            double pz = this.field_70161_v;
            if (this.getSeatInfo(0) != null && this.getSeatInfo(0).pos != null) {
                final Vec3 pos2 = MCH_Lib.RotVec3(0.0, this.getSeatInfo(0).pos.field_72448_b, -2.0, -yaw, -pitch, -roll);
                px += pos2.field_72450_a;
                py += pos2.field_72448_b;
                pz += pos2.field_72449_c;
            }
            this.onUpdate_Particle2SpawnSmoke(bbNum, px, py, pz, (bbNum == 0) ? 2.0f : 1.0f);
        }
        this.isFirstDamageSmoke = false;
    }
    
    public void onUpdate_Particle2SpawnSmoke(final int ri, final double x, final double y, final double z, final float size) {
        if (this.isFirstDamageSmoke || this.prevDamageSmokePos[ri] == null) {
            this.prevDamageSmokePos[ri] = Vec3.func_72443_a(x, y, z);
        }
        final Vec3 prev = this.prevDamageSmokePos[ri];
        final double dx = x - prev.field_72450_a;
        final double dy = y - prev.field_72448_b;
        final double dz = z - prev.field_72449_c;
        for (int num = 1, i = 0; i < num; ++i) {
            final float c = 0.2f + this.field_70146_Z.nextFloat() * 0.3f;
            final MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", x, y, z);
            prm.motionX = size * (this.field_70146_Z.nextDouble() - 0.5) * 0.3;
            prm.motionY = size * this.field_70146_Z.nextDouble() * 0.1;
            prm.motionZ = size * (this.field_70146_Z.nextDouble() - 0.5) * 0.3;
            prm.size = size * (this.field_70146_Z.nextInt(5) + 5.0f) * 1.0f;
            prm.setColor(0.7f + this.field_70146_Z.nextFloat() * 0.1f, c, c, c);
            MCH_ParticlesUtil.spawnParticle(prm);
        }
        this.prevDamageSmokePos[ri].field_72450_a = x;
        this.prevDamageSmokePos[ri].field_72448_b = y;
        this.prevDamageSmokePos[ri].field_72449_c = z;
    }
    
    public void onUpdate_Particle2SpawnSmode(final int ri, final double x, final double y, final double z, final float size) {
        if (this.isFirstDamageSmoke) {
            this.prevDamageSmokePos[ri] = Vec3.func_72443_a(x, y, z);
        }
        final Vec3 prev = this.prevDamageSmokePos[ri];
        final double dx = x - prev.field_72450_a;
        final double dy = y - prev.field_72448_b;
        final double dz = z - prev.field_72449_c;
        for (int num = (int)(MathHelper.func_76133_a(dx * dx + dy * dy + dz * dz) / 0.3) + 1, i = 0; i < num; ++i) {
            final float c = 0.2f + this.field_70146_Z.nextFloat() * 0.3f;
            final MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", x, y, z);
            prm.motionX = size * (this.field_70146_Z.nextDouble() - 0.5) * 0.3;
            prm.motionY = size * this.field_70146_Z.nextDouble() * 0.1;
            prm.motionZ = size * (this.field_70146_Z.nextDouble() - 0.5) * 0.3;
            prm.size = size * (this.field_70146_Z.nextInt(5) + 5.0f) * 1.0f;
            prm.setColor(0.7f + this.field_70146_Z.nextFloat() * 0.1f, c, c, c);
            MCH_ParticlesUtil.spawnParticle(prm);
        }
        this.prevDamageSmokePos[ri].field_72450_a = x;
        this.prevDamageSmokePos[ri].field_72448_b = y;
        this.prevDamageSmokePos[ri].field_72449_c = z;
    }
    
    public void onUpdate_ParticleLandingGear() {
        this.WheelMng.particleLandingGear();
    }
    
    private void onUpdate_ParticleSplash() {
        if (this.getAcInfo() == null) {
            return;
        }
        if (!this.field_70170_p.field_72995_K) {
            return;
        }
        final double mx = this.field_70165_t - this.field_70169_q;
        final double mz = this.field_70161_v - this.field_70166_s;
        double dist = mx * mx + mz * mz;
        if (dist > 1.0) {
            dist = 1.0;
        }
        for (final MCH_AircraftInfo.ParticleSplash p : this.getAcInfo().particleSplashs) {
            for (int i = 0; i < p.num; ++i) {
                if (dist > 0.03 + this.field_70146_Z.nextFloat() * 0.1) {
                    this.setParticleSplash(p.pos, -mx * p.acceleration, p.motionY, -mz * p.acceleration, p.gravity, p.size * (0.5 + dist * 0.5), p.age);
                }
            }
        }
    }
    
    private void setParticleSplash(final Vec3 pos, final double mx, final double my, final double mz, final float gravity, final double size, final int age) {
        Vec3 v = this.getTransformedPosition(pos);
        v = v.func_72441_c(this.field_70146_Z.nextDouble() - 0.5, (this.field_70146_Z.nextDouble() - 0.5) * 0.5, this.field_70146_Z.nextDouble() - 0.5);
        final int x = (int)(v.field_72450_a + 0.5);
        final int y = (int)(v.field_72448_b + 0.0);
        final int z = (int)(v.field_72449_c + 0.5);
        if (W_WorldFunc.isBlockWater(this.field_70170_p, x, y, z)) {
            final float c = this.field_70146_Z.nextFloat() * 0.3f + 0.7f;
            final MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", v.field_72450_a, v.field_72448_b, v.field_72449_c);
            prm.motionX = mx + (this.field_70146_Z.nextFloat() - 0.5) * 0.7;
            prm.motionY = my;
            prm.motionZ = mz + (this.field_70146_Z.nextFloat() - 0.5) * 0.7;
            prm.size = (float)size * (this.field_70146_Z.nextFloat() * 0.2f + 0.8f);
            prm.setColor(0.9f, c, c, c);
            prm.age = age + (int)(this.field_70146_Z.nextFloat() * 0.5 * age);
            prm.gravity = gravity;
            MCH_ParticlesUtil.spawnParticle(prm);
        }
    }
    
    @Override
    public void destroyAircraft() {
        super.destroyAircraft();
        this.rotDestroyedPitch = 0.0f;
        this.rotDestroyedRoll = 0.0f;
        this.rotDestroyedYaw = 0.0f;
    }
    
    @Override
    public int getClientPositionDelayCorrection() {
        return (this.getTankInfo() == null) ? 7 : ((this.getTankInfo().weightType == 1) ? 2 : 7);
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
        this.updateWheels();
        this.onUpdate_Particle2();
        this.updateSound();
        if (this.field_70170_p.field_72995_K) {
            this.onUpdate_ParticleLandingGear();
            this.onUpdate_ParticleSplash();
            this.onUpdate_ParticleSandCloud(true);
        }
        this.updateCamera(this.field_70165_t, this.field_70163_u, this.field_70161_v);
    }
    
    @Override
    public void applyOnGroundPitch(final float factor) {
    }
    
    private void onUpdate_Server() {
        final Entity rdnEnt = this.getRiddenByEntity();
        final double prevMotion = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
        double dp = 0.0;
        if (this.canFloatWater()) {
            dp = this.getWaterDepth();
        }
        final boolean levelOff = this.isGunnerMode;
        if (dp == 0.0) {
            if (!levelOff) {
                this.field_70181_x += 0.04 + (this.func_70090_H() ? this.getAcInfo().gravityInWater : this.getAcInfo().gravity);
                this.field_70181_x += -0.047 * (1.0 - this.getCurrentThrottle());
            }
            else {
                this.field_70181_x *= 0.8;
            }
        }
        else {
            if (MathHelper.func_76135_e(this.getRotRoll()) < 40.0f) {}
            if (dp < 1.0) {
                this.field_70181_x -= 1.0E-4;
                this.field_70181_x += 0.007 * this.getCurrentThrottle();
            }
            else {
                if (this.field_70181_x < 0.0) {
                    this.field_70181_x /= 2.0;
                }
                this.field_70181_x += 0.007;
            }
        }
        final float throttle = (float)(this.getCurrentThrottle() / 10.0);
        final Vec3 v = MCH_Lib.Rot2Vec3(this.getRotYaw(), this.getRotPitch() - 10.0f);
        if (!levelOff) {
            this.field_70181_x += v.field_72448_b * throttle / 8.0;
        }
        boolean canMove = true;
        if (!this.getAcInfo().canMoveOnGround) {
            final Block block = MCH_Lib.getBlockY(this, 3, -2, false);
            if (!W_Block.isEqual(block, W_Block.getWater()) && !W_Block.isEqual(block, W_Blocks.field_150350_a)) {
                canMove = false;
            }
        }
        if (canMove) {
            if (this.getAcInfo().enableBack && this.throttleBack > 0.0f) {
                this.field_70159_w -= v.field_72450_a * this.throttleBack;
                this.field_70179_y -= v.field_72449_c * this.throttleBack;
            }
            else {
                this.field_70159_w += v.field_72450_a * throttle;
                this.field_70179_y += v.field_72449_c * throttle;
            }
        }
        double motion = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
        final float speedLimit = this.getMaxSpeed();
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
        if (this.field_70122_E || MCH_Lib.getBlockIdY(this, 1, -2) > 0) {
            this.field_70159_w *= this.getAcInfo().motionFactor;
            this.field_70179_y *= this.getAcInfo().motionFactor;
            if (MathHelper.func_76135_e(this.getRotPitch()) < 40.0f) {
                this.applyOnGroundPitch(0.8f);
            }
        }
        this.updateWheels();
        this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
        this.field_70181_x *= 0.95;
        this.field_70159_w *= this.getAcInfo().motionFactor;
        this.field_70179_y *= this.getAcInfo().motionFactor;
        this.func_70101_b(this.getRotYaw(), this.getRotPitch());
        this.onUpdate_updateBlock();
        this.updateCollisionBox();
        if (this.getRiddenByEntity() != null && this.getRiddenByEntity().field_70128_L) {
            this.unmountEntity();
            this.field_70153_n = null;
        }
    }
    
    private void collisionEntity(final AxisAlignedBB bb) {
        if (bb == null) {
            return;
        }
        final double speed = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y);
        if (speed <= 0.05) {
            return;
        }
        final Entity rider = this.getRiddenByEntity();
        float damage = (float)(speed * 15.0);
        final MCH_EntityAircraft rideAc = (MCH_EntityAircraft)((this.field_70154_o instanceof MCH_EntityAircraft) ? this.field_70154_o : ((this.field_70154_o instanceof MCH_EntitySeat) ? ((MCH_EntitySeat)this.field_70154_o).getParent() : null));
        final List list = this.field_70170_p.func_94576_a((Entity)this, bb.func_72314_b(0.3, 0.3, 0.3), (IEntitySelector)new IEntitySelector() {
            public boolean func_82704_a(final Entity e) {
                if (e == rideAc || e instanceof EntityItem || e instanceof EntityXPOrb || e instanceof MCH_EntityBaseBullet || e instanceof MCH_EntityChain || e instanceof MCH_EntitySeat) {
                    return false;
                }
                if (e instanceof MCH_EntityTank) {
                    final MCH_EntityTank tank = (MCH_EntityTank)e;
                    if (tank.getTankInfo() != null && tank.getTankInfo().weightType == 2) {
                        final MCH_Config config = MCH_MOD.config;
                        return MCH_Config.Collision_EntityTankDamage.prmBool;
                    }
                }
                final MCH_Config config2 = MCH_MOD.config;
                return MCH_Config.Collision_EntityDamage.prmBool;
            }
        });
        for (int i = 0; i < list.size(); ++i) {
            final Entity e = list.get(i);
            if (this.shouldCollisionDamage(e)) {
                final double dx = e.field_70165_t - this.field_70165_t;
                final double dz = e.field_70161_v - this.field_70161_v;
                double dist = Math.sqrt(dx * dx + dz * dz);
                if (dist > 5.0) {
                    dist = 5.0;
                }
                damage += (float)(5.0 - dist);
                DamageSource ds;
                if (rider instanceof EntityLivingBase) {
                    ds = DamageSource.func_76358_a((EntityLivingBase)rider);
                }
                else {
                    ds = DamageSource.field_76377_j;
                }
                MCH_Lib.applyEntityHurtResistantTimeConfig(e);
                e.func_70097_a(ds, damage);
                if (e instanceof MCH_EntityAircraft) {
                    final Entity entity = e;
                    entity.field_70159_w += this.field_70159_w * 0.05;
                    final Entity entity2 = e;
                    entity2.field_70179_y += this.field_70179_y * 0.05;
                }
                else if (e instanceof EntityArrow) {
                    e.func_70106_y();
                }
                else {
                    final Entity entity3 = e;
                    entity3.field_70159_w += this.field_70159_w * 1.5;
                    final Entity entity4 = e;
                    entity4.field_70179_y += this.field_70179_y * 1.5;
                }
                if (this.getTankInfo().weightType != 2 && (e.field_70130_N >= 1.0f || e.field_70131_O >= 1.5)) {
                    if (e instanceof EntityLivingBase) {
                        ds = DamageSource.func_76358_a((EntityLivingBase)e);
                    }
                    else {
                        ds = DamageSource.field_76377_j;
                    }
                    this.func_70097_a(ds, damage / 3.0f);
                }
                MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityTank.collisionEntity damage=%.1f %s", damage, e.toString());
            }
        }
    }
    
    private boolean shouldCollisionDamage(final Entity e) {
        if (this.getSeatIdByEntity(e) >= 0) {
            return false;
        }
        if (this.noCollisionEntities.containsKey(e)) {
            return false;
        }
        if (e instanceof MCH_EntityHitBox && ((MCH_EntityHitBox)e).parent != null) {
            final MCH_EntityAircraft ac = ((MCH_EntityHitBox)e).parent;
            if (this.noCollisionEntities.containsKey(ac)) {
                return false;
            }
        }
        return (!(e.field_70154_o instanceof MCH_EntityAircraft) || !this.noCollisionEntities.containsKey(e.field_70154_o)) && (!(e.field_70154_o instanceof MCH_EntitySeat) || ((MCH_EntitySeat)e.field_70154_o).getParent() == null || !this.noCollisionEntities.containsKey(((MCH_EntitySeat)e.field_70154_o).getParent()));
    }
    
    public void updateCollisionBox() {
        if (this.getAcInfo() == null) {
            return;
        }
        this.WheelMng.updateBlock();
        for (final MCH_BoundingBox bb : this.extraBoundingBox) {
            if (this.field_70146_Z.nextInt(3) == 0) {
                final MCH_Config config = MCH_MOD.config;
                if (MCH_Config.Collision_DestroyBlock.prmBool) {
                    final Vec3 v = this.getTransformedPosition(bb.offsetX, bb.offsetY, bb.offsetZ);
                    this.destoryBlockRange(v, bb.width, bb.height);
                }
                this.collisionEntity(bb.boundingBox);
            }
        }
        final MCH_Config config2 = MCH_MOD.config;
        if (MCH_Config.Collision_DestroyBlock.prmBool) {
            this.destoryBlockRange(this.getTransformedPosition(0.0, 0.0, 0.0), this.field_70130_N * 1.5, this.field_70131_O * 2.0f);
        }
        this.collisionEntity(this.func_70046_E());
    }
    
    public void destoryBlockRange(final Vec3 v, final double w, final double h) {
        if (this.getAcInfo() == null) {
            return;
        }
        final MCH_Config config = MCH_MOD.config;
        final List<Block> destroyBlocks = MCH_Config.getBreakableBlockListFromType(this.getTankInfo().weightType);
        final MCH_Config config2 = MCH_MOD.config;
        final List<Block> noDestroyBlocks = MCH_Config.getNoBreakableBlockListFromType(this.getTankInfo().weightType);
        final MCH_Config config3 = MCH_MOD.config;
        final List<Material> destroyMaterials = MCH_Config.getBreakableMaterialListFromType(this.getTankInfo().weightType);
        final int ws = (int)(w + 2.0) / 2;
        final int hs = (int)(h + 2.0) / 2;
        for (int x = -ws; x <= ws; ++x) {
            for (int z = -ws; z <= ws; ++z) {
                for (int y = -hs; y <= hs + 1; ++y) {
                    final int bx = (int)(v.field_72450_a + x - 0.5);
                    final int by = (int)(v.field_72448_b + y - 1.0);
                    final int bz = (int)(v.field_72449_c + z - 0.5);
                    Block block = (by >= 0 && by < 256) ? this.field_70170_p.func_147439_a(bx, by, bz) : Blocks.field_150350_a;
                    Material mat = block.func_149688_o();
                    if (!Block.func_149680_a(block, Blocks.field_150350_a)) {
                        for (final Block c : noDestroyBlocks) {
                            if (Block.func_149680_a(block, c)) {
                                block = null;
                                break;
                            }
                        }
                        if (block == null) {
                            break;
                        }
                        for (final Block c : destroyBlocks) {
                            if (Block.func_149680_a(block, c)) {
                                this.destroyBlock(bx, by, bz);
                                mat = null;
                                break;
                            }
                        }
                        if (mat == null) {
                            break;
                        }
                        for (final Material m : destroyMaterials) {
                            if (block.func_149688_o() == m) {
                                this.destroyBlock(bx, by, bz);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void destroyBlock(final int bx, final int by, final int bz) {
        if (this.field_70146_Z.nextInt(8) == 0) {
            W_WorldFunc.destroyBlock(this.field_70170_p, bx, by, bz, true);
        }
        else {
            this.field_70170_p.func_147468_f(bx, by, bz);
        }
    }
    
    private void updateWheels() {
        this.WheelMng.move(this.field_70159_w, this.field_70181_x, this.field_70179_y);
    }
    
    public float getMaxSpeed() {
        return this.getTankInfo().speed + 0.0f;
    }
    
    @Override
    public void setAngles(final Entity player, final boolean fixRot, final float fixYaw, final float fixPitch, float deltaX, float deltaY, float x, float y, float partialTicks) {
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
        final float yaw = 0.0f;
        final float pitch = 0.0f;
        final float roll = 0.0f;
        final MCH_Math.FMatrix m_add = MCH_Math.newMatrix();
        MCH_Math.MatTurnZ(m_add, roll / 180.0f * 3.1415927f);
        MCH_Math.MatTurnX(m_add, pitch / 180.0f * 3.1415927f);
        MCH_Math.MatTurnY(m_add, yaw / 180.0f * 3.1415927f);
        MCH_Math.MatTurnZ(m_add, (float)(this.getRotRoll() / 180.0f * 3.141592653589793));
        MCH_Math.MatTurnX(m_add, (float)(this.getRotPitch() / 180.0f * 3.141592653589793));
        MCH_Math.MatTurnY(m_add, (float)(this.getRotYaw() / 180.0f * 3.141592653589793));
        final MCH_Math.FVector3D v = MCH_Math.MatrixToEuler(m_add);
        v.x = MCH_Lib.RNG(v.x, -90.0f, 90.0f);
        v.z = MCH_Lib.RNG(v.z, -90.0f, 90.0f);
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
            v.x = MCH_Lib.RNG(this.getRotPitch(), -90.0f, 90.0f);
            v.z = MCH_Lib.RNG(this.getRotRoll(), -90.0f, 90.0f);
            this.setRotPitch(v.x);
            this.setRotRoll(v.z);
        }
        final float RV = 180.0f;
        if (MathHelper.func_76135_e(this.getRotPitch()) > 90.0f) {
            MCH_Lib.DbgLog(true, "MCH_EntityAircraft.setAngles Error:Pitch=%.1f", this.getRotPitch());
            this.setRotPitch(0.0f);
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
        float deltaLimit = this.getAcInfo().cameraRotationSpeed * partialTicks;
        final MCH_WeaponSet ws = this.getCurrentWeapon(player);
        deltaLimit *= ((ws != null && ws.getInfo() != null) ? ws.getInfo().cameraRotationSpeedPitch : 1.0f);
        if (deltaX > deltaLimit) {
            deltaX = deltaLimit;
        }
        if (deltaX < -deltaLimit) {
            deltaX = -deltaLimit;
        }
        if (deltaY > deltaLimit) {
            deltaY = deltaLimit;
        }
        if (deltaY < -deltaLimit) {
            deltaY = -deltaLimit;
        }
        if (this.isOverridePlayerYaw() || fixRot) {
            if (this.getRidingEntity() == null) {
                player.field_70126_B = this.getRotYaw() + fixYaw;
            }
            else {
                if (this.getRotYaw() - player.field_70177_z > 180.0f) {
                    player.field_70126_B += 360.0f;
                }
                if (this.getRotYaw() - player.field_70177_z < -180.0f) {
                    player.field_70126_B -= 360.0f;
                }
            }
            player.field_70177_z = this.getRotYaw() + fixYaw;
        }
        else {
            player.func_70082_c(deltaX, 0.0f);
        }
        if (this.isOverridePlayerPitch() || fixRot) {
            player.field_70127_C = this.getRotPitch() + fixPitch;
            player.field_70125_A = this.getRotPitch() + fixPitch;
        }
        else {
            player.func_70082_c(0.0f, deltaY);
        }
        final float playerYaw = MathHelper.func_76142_g(this.getRotYaw() - player.field_70177_z);
        final float playerPitch = this.getRotPitch() * MathHelper.func_76134_b((float)(playerYaw * 3.141592653589793 / 180.0)) + -this.getRotRoll() * MathHelper.func_76126_a((float)(playerYaw * 3.141592653589793 / 180.0));
        if (MCH_MOD.proxy.isFirstPerson()) {
            player.field_70125_A = MCH_Lib.RNG(player.field_70125_A, playerPitch + this.getAcInfo().minRotationPitch, playerPitch + this.getAcInfo().maxRotationPitch);
            player.field_70125_A = MCH_Lib.RNG(player.field_70125_A, -90.0f, 90.0f);
        }
        player.field_70127_C = player.field_70125_A;
        if ((this.getRidingEntity() == null && ac_yaw != this.getRotYaw()) || ac_pitch != this.getRotPitch() || ac_roll != this.getRotRoll()) {
            this.aircraftRotChanged = true;
        }
    }
    
    @Override
    public float getSoundVolume() {
        if (this.getAcInfo() != null && this.getAcInfo().throttleUpDown <= 0.0f) {
            return 0.0f;
        }
        return this.soundVolume * 0.7f;
    }
    
    public void updateSound() {
        float target = (float)this.getCurrentThrottle();
        if (this.getRiddenByEntity() != null && (this.partCanopy == null || this.getCanopyRotation() < 1.0f)) {
            target += 0.1f;
        }
        if (this.moveLeft || this.moveRight || this.throttleDown) {
            this.soundVolumeTarget += 0.1f;
            if (this.soundVolumeTarget > 0.75f) {
                this.soundVolumeTarget = 0.75f;
            }
        }
        else {
            this.soundVolumeTarget *= 0.8f;
        }
        if (target < this.soundVolumeTarget) {
            target = this.soundVolumeTarget;
        }
        if (this.soundVolume < target) {
            this.soundVolume += 0.02f;
            if (this.soundVolume >= target) {
                this.soundVolume = target;
            }
        }
        else if (this.soundVolume > target) {
            this.soundVolume -= 0.02f;
            if (this.soundVolume <= target) {
                this.soundVolume = target;
            }
        }
    }
    
    @Override
    public float getSoundPitch() {
        final float target1 = (float)(0.5 + this.getCurrentThrottle() * 0.5);
        final float target2 = (float)(0.5 + this.soundVolumeTarget * 0.5);
        return (target1 > target2) ? target1 : target2;
    }
    
    @Override
    public String getDefaultSoundName() {
        return "prop";
    }
    
    @Override
    public boolean hasBrake() {
        return true;
    }
    
    @Override
    public void updateParts(final int stat) {
        super.updateParts(stat);
        if (this.isDestroyed()) {
            return;
        }
        final MCH_Parts[] arr$;
        final MCH_Parts[] parts = arr$ = new MCH_Parts[0];
        for (final MCH_Parts p : arr$) {
            if (p != null) {
                p.updateStatusClient(stat);
                p.update();
            }
        }
    }
    
    @Override
    public float getUnfoldLandingGearThrottle() {
        return 0.7f;
    }
}
