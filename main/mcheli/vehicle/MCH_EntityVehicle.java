package mcheli.vehicle;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import mcheli.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import cpw.mods.fml.relauncher.*;
import mcheli.weapon.*;
import mcheli.aircraft.*;
import mcheli.wrapper.*;
import net.minecraft.util.*;

public class MCH_EntityVehicle extends MCH_EntityAircraft
{
    private MCH_VehicleInfo vehicleInfo;
    public boolean isUsedPlayer;
    public float lastRiderYaw;
    public float lastRiderPitch;
    
    public MCH_EntityVehicle(final World world) {
        super(world);
        this.vehicleInfo = null;
        this.currentSpeed = 0.07;
        this.field_70156_m = true;
        this.func_70105_a(2.0f, 0.7f);
        this.field_70129_M = this.field_70131_O / 2.0f;
        this.field_70159_w = 0.0;
        this.field_70181_x = 0.0;
        this.field_70179_y = 0.0;
        this.isUsedPlayer = false;
        this.lastRiderYaw = 0.0f;
        this.lastRiderPitch = 0.0f;
        this.weapons = this.createWeapon(0);
    }
    
    @Override
    public String getKindName() {
        return "vehicles";
    }
    
    @Override
    public String getEntityType() {
        return "Vehicle";
    }
    
    public MCH_VehicleInfo getVehicleInfo() {
        return this.vehicleInfo;
    }
    
    @Override
    public void changeType(final String type) {
        if (!type.isEmpty()) {
            this.vehicleInfo = MCH_VehicleInfoManager.get(type);
        }
        if (this.vehicleInfo == null) {
            MCH_Lib.Log(this, "##### MCH_EntityVehicle changeVehicleType() Vehicle info null %d, %s, %s", W_Entity.getEntityId(this), type, this.getEntityName());
            this.func_70106_y();
        }
        else {
            this.setAcInfo(this.vehicleInfo);
            this.newSeats(this.getAcInfo().getNumSeatAndRack());
            this.weapons = this.createWeapon(1 + this.getSeatNum());
            this.initPartRotation(this.field_70177_z, this.field_70125_A);
        }
    }
    
    @Override
    public boolean canMountWithNearEmptyMinecart() {
        final MCH_Config config = MCH_MOD.config;
        return MCH_Config.MountMinecartVehicle.prmBool;
    }
    
    @Override
    protected void func_70088_a() {
        super.func_70088_a();
    }
    
    @Override
    protected void func_70014_b(final NBTTagCompound par1NBTTagCompound) {
        super.func_70014_b(par1NBTTagCompound);
    }
    
    @Override
    protected void func_70037_a(final NBTTagCompound par1NBTTagCompound) {
        super.func_70037_a(par1NBTTagCompound);
        if (this.vehicleInfo == null) {
            this.vehicleInfo = MCH_VehicleInfoManager.get(this.getTypeName());
            if (this.vehicleInfo == null) {
                MCH_Lib.Log(this, "##### MCH_EntityVehicle readEntityFromNBT() Vehicle info null %d, %s", W_Entity.getEntityId(this), this.getEntityName());
                this.func_70106_y();
            }
            else {
                this.setAcInfo(this.vehicleInfo);
            }
        }
    }
    
    @Override
    public Item getItem() {
        return (this.getVehicleInfo() != null) ? this.getVehicleInfo().item : null;
    }
    
    @Override
    public void func_70106_y() {
        super.func_70106_y();
    }
    
    @Override
    public float getSoundVolume() {
        return (float)this.getCurrentThrottle() * 2.0f;
    }
    
    @Override
    public float getSoundPitch() {
        return (float)(this.getCurrentThrottle() * 0.5);
    }
    
    @Override
    public String getDefaultSoundName() {
        return "";
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void zoomCamera() {
        if (this.canZoom()) {
            float z = this.camera.getCameraZoom();
            ++z;
            this.camera.setCameraZoom((z <= this.getZoomMax() + 0.01) ? z : 1.0f);
        }
    }
    
    public void _updateCameraRotate(final float yaw, float pitch) {
        this.camera.prevRotationYaw = this.camera.rotationYaw;
        this.camera.prevRotationPitch = this.camera.rotationPitch;
        if (pitch > 89.0f) {
            pitch = 89.0f;
        }
        if (pitch < -89.0f) {
            pitch = -89.0f;
        }
        this.camera.rotationYaw = yaw;
        this.camera.rotationPitch = pitch;
    }
    
    @Override
    public boolean isCameraView(final Entity entity) {
        return true;
    }
    
    @Override
    public boolean useCurrentWeapon(final MCH_WeaponParam prm) {
        if (prm.user != null) {
            final MCH_WeaponSet currentWs = this.getCurrentWeapon(prm.user);
            if (currentWs != null) {
                final MCH_AircraftInfo.Weapon w = this.getAcInfo().getWeaponByName(currentWs.getInfo().name);
                if (w != null && w.maxYaw != 0.0f && w.minYaw != 0.0f) {
                    return super.useCurrentWeapon(prm);
                }
            }
        }
        final float breforeUseWeaponPitch = this.field_70125_A;
        final float breforeUseWeaponYaw = this.field_70177_z;
        this.field_70125_A = prm.user.field_70125_A;
        this.field_70177_z = prm.user.field_70177_z;
        final boolean result = super.useCurrentWeapon(prm);
        this.field_70125_A = breforeUseWeaponPitch;
        this.field_70177_z = breforeUseWeaponYaw;
        return result;
    }
    
    @Override
    public void onUpdateAircraft() {
        if (this.vehicleInfo == null) {
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
            this.getRiddenByEntity().field_70125_A = 0.0f;
            this.getRiddenByEntity().field_70127_C = 0.0f;
            this.initCurrentWeapon(this.getRiddenByEntity());
        }
        this.updateWeapons();
        this.onUpdate_Seats();
        this.onUpdate_Control();
        this.field_70169_q = this.field_70165_t;
        this.field_70167_r = this.field_70163_u;
        this.field_70166_s = this.field_70161_v;
        if (this.func_70090_H()) {
            this.field_70125_A *= 0.9f;
        }
        if (this.field_70170_p.field_72995_K) {
            this.onUpdate_Client();
        }
        else {
            this.onUpdate_Server();
        }
    }
    
    protected void onUpdate_Control() {
        final double max_y = 1.0;
        if (this.field_70153_n != null && !this.field_70153_n.field_70128_L) {
            if (this.getVehicleInfo().isEnableMove || this.getVehicleInfo().isEnableRot) {
                this.onUpdate_ControlOnGround();
            }
        }
        else if (this.getCurrentThrottle() > 0.0) {
            this.addCurrentThrottle(-0.00125);
        }
        else {
            this.setCurrentThrottle(0.0);
        }
        if (this.getCurrentThrottle() < 0.0) {
            this.setCurrentThrottle(0.0);
        }
        if (this.field_70170_p.field_72995_K) {
            if (!W_Lib.isClientPlayer(this.getRiddenByEntity())) {
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
    
    protected void onUpdate_ControlOnGround() {
        if (!this.field_70170_p.field_72995_K) {
            boolean move = false;
            float yaw = this.field_70177_z;
            double x = 0.0;
            double z = 0.0;
            if (this.getVehicleInfo().isEnableMove) {
                if (this.throttleUp) {
                    yaw = this.field_70177_z;
                    x += Math.sin(yaw * 3.141592653589793 / 180.0);
                    z += Math.cos(yaw * 3.141592653589793 / 180.0);
                    move = true;
                }
                if (this.throttleDown) {
                    yaw = this.field_70177_z - 180.0f;
                    x += Math.sin(yaw * 3.141592653589793 / 180.0);
                    z += Math.cos(yaw * 3.141592653589793 / 180.0);
                    move = true;
                }
            }
            if (this.getVehicleInfo().isEnableMove) {
                if (this.moveLeft && !this.moveRight) {
                    this.field_70177_z -= 0.5;
                }
                if (this.moveRight && !this.moveLeft) {
                    this.field_70177_z += 0.5;
                }
            }
            if (move) {
                final double d = Math.sqrt(x * x + z * z);
                this.field_70159_w -= x / d * 0.029999999329447746;
                this.field_70179_y += z / d * 0.029999999329447746;
            }
        }
    }
    
    protected void onUpdate_Particle() {
        double particlePosY = this.field_70163_u;
        boolean b;
        int y;
        int x;
        int z;
        int block;
        int i;
        for (b = false, y = 0; y < 5 && !b; ++y) {
            for (x = -1; x <= 1; ++x) {
                for (z = -1; z <= 1; ++z) {
                    block = W_WorldFunc.getBlockId(this.field_70170_p, (int)(this.field_70165_t + 0.5) + x, (int)(this.field_70163_u + 0.5) - y, (int)(this.field_70161_v + 0.5) + z);
                    if (block != 0 && !b) {
                        particlePosY = (int)(this.field_70163_u + 1.0) - y;
                        b = true;
                    }
                }
            }
            for (x = -3; b && x <= 3; ++x) {
                for (z = -3; z <= 3; ++z) {
                    if (W_WorldFunc.isBlockWater(this.field_70170_p, (int)(this.field_70165_t + 0.5) + x, (int)(this.field_70163_u + 0.5) - y, (int)(this.field_70161_v + 0.5) + z)) {
                        for (i = 0; i < 7.0 * this.getCurrentThrottle(); ++i) {
                            this.field_70170_p.func_72869_a("splash", this.field_70165_t + 0.5 + x + (this.field_70146_Z.nextDouble() - 0.5) * 2.0, particlePosY + this.field_70146_Z.nextDouble(), this.field_70161_v + 0.5 + z + (this.field_70146_Z.nextDouble() - 0.5) * 2.0, x + (this.field_70146_Z.nextDouble() - 0.5) * 2.0, -0.3, z + (this.field_70146_Z.nextDouble() - 0.5) * 2.0);
                        }
                    }
                }
            }
        }
        final double pn = (5 - y + 1) / 5.0;
        if (b) {
            for (int k = 0; k < (int)(this.getCurrentThrottle() * 6.0 * pn); ++k) {
                final float f3 = 0.25f;
                this.field_70170_p.func_72869_a("explode", this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5), particlePosY + (this.field_70146_Z.nextDouble() - 0.5), this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5), (this.field_70146_Z.nextDouble() - 0.5) * 2.0, -0.4, (this.field_70146_Z.nextDouble() - 0.5) * 2.0);
            }
        }
    }
    
    protected void onUpdate_Client() {
        this.updateCameraViewers();
        if (this.field_70153_n != null && W_Lib.isClientPlayer(this.getRiddenByEntity())) {
            this.getRiddenByEntity().field_70125_A = this.getRiddenByEntity().field_70127_C;
        }
        if (this.aircraftPosRotInc > 0) {
            final double rpinc = this.aircraftPosRotInc;
            final double yaw = MathHelper.func_76138_g(this.aircraftYaw - this.field_70177_z);
            this.field_70177_z += (float)(yaw / rpinc);
            this.field_70125_A += (float)((this.aircraftPitch - this.field_70125_A) / rpinc);
            this.func_70107_b(this.field_70165_t + (this.aircraftX - this.field_70165_t) / rpinc, this.field_70163_u + (this.aircraftY - this.field_70163_u) / rpinc, this.field_70161_v + (this.aircraftZ - this.field_70161_v) / rpinc);
            this.func_70101_b(this.field_70177_z, this.field_70125_A);
            --this.aircraftPosRotInc;
        }
        else {
            this.func_70107_b(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
            if (this.field_70122_E) {
                this.field_70159_w *= 0.95;
                this.field_70179_y *= 0.95;
            }
            if (this.func_70090_H()) {
                this.field_70159_w *= 0.99;
                this.field_70179_y *= 0.99;
            }
        }
        if (this.field_70153_n != null) {}
        this.updateCamera(this.field_70165_t, this.field_70163_u, this.field_70161_v);
    }
    
    private void onUpdate_Server() {
        final double prevMotion = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
        this.updateCameraViewers();
        double dp = 0.0;
        if (this.canFloatWater()) {
            dp = this.getWaterDepth();
        }
        if (dp == 0.0) {
            this.field_70181_x += (this.func_70090_H() ? this.getAcInfo().gravityInWater : this.getAcInfo().gravity);
        }
        else if (dp < 1.0) {
            this.field_70181_x -= 1.0E-4;
            this.field_70181_x += 0.007 * this.getCurrentThrottle();
        }
        else {
            if (this.field_70181_x < 0.0) {
                this.field_70181_x /= 2.0;
            }
            this.field_70181_x += 0.007;
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
        }
        this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
        this.field_70181_x *= 0.95;
        this.field_70159_w *= 0.99;
        this.field_70179_y *= 0.99;
        this.onUpdate_updateBlock();
        if (this.field_70153_n != null && this.field_70153_n.field_70128_L) {
            this.unmountEntity();
            this.field_70153_n = null;
        }
    }
    
    @Override
    public void onUpdateAngles(final float partialTicks) {
    }
    
    public void _updateRiderPosition() {
        final float yaw = this.field_70177_z;
        if (this.field_70153_n != null) {
            this.field_70177_z = this.field_70153_n.field_70177_z;
        }
        super.func_70043_V();
        this.field_70177_z = yaw;
    }
    
    @Override
    public boolean canSwitchFreeLook() {
        return false;
    }
}
