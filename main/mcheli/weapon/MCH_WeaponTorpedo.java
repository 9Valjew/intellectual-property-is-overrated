package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import mcheli.*;
import mcheli.wrapper.*;
import net.minecraft.util.*;

public class MCH_WeaponTorpedo extends MCH_WeaponBase
{
    public MCH_WeaponTorpedo(final World w, final Vec3 v, final float yaw, final float pitch, final String nm, final MCH_WeaponInfo wi) {
        super(w, v, yaw, pitch, nm, wi);
        this.acceleration = 0.5f;
        this.explosionPower = 8;
        this.power = 35;
        this.interval = -100;
        if (w.field_72995_K) {
            this.interval -= 10;
        }
    }
    
    @Override
    public boolean shot(final MCH_WeaponParam prm) {
        if (this.getInfo() == null) {
            return false;
        }
        if (this.getInfo().isGuidedTorpedo) {
            return this.shotGuided(prm);
        }
        return this.shotNoGuided(prm);
    }
    
    protected boolean shotNoGuided(final MCH_WeaponParam prm) {
        if (this.worldObj.field_72995_K) {
            return true;
        }
        final float yaw = prm.rotYaw;
        final float pitch = prm.rotPitch;
        double mx = -MathHelper.func_76126_a(yaw / 180.0f * 3.1415927f) * MathHelper.func_76134_b(pitch / 180.0f * 3.1415927f);
        double mz = MathHelper.func_76134_b(yaw / 180.0f * 3.1415927f) * MathHelper.func_76134_b(pitch / 180.0f * 3.1415927f);
        double my = -MathHelper.func_76126_a(pitch / 180.0f * 3.1415927f);
        mx = mx * this.getInfo().acceleration + prm.entity.field_70159_w;
        my = my * this.getInfo().acceleration + prm.entity.field_70181_x;
        mz = mz * this.getInfo().acceleration + prm.entity.field_70179_y;
        this.acceleration = MathHelper.func_76133_a(mx * mx + my * my + mz * mz);
        final MCH_EntityTorpedo e = new MCH_EntityTorpedo(this.worldObj, prm.posX, prm.posY, prm.posZ, mx, my, mz, yaw, 0.0f, this.acceleration);
        e.setName(this.name);
        e.setParameterFromWeapon(this, prm.entity, prm.user);
        e.field_70159_w = mx;
        e.field_70181_x = my;
        e.field_70179_y = mz;
        e.accelerationInWater = ((this.getInfo() != null) ? this.getInfo().accelerationInWater : 1.0);
        this.worldObj.func_72838_d((Entity)e);
        this.playSound(prm.entity);
        return true;
    }
    
    protected boolean shotGuided(final MCH_WeaponParam prm) {
        final float yaw = prm.user.field_70177_z;
        final float pitch = prm.user.field_70125_A;
        final Vec3 v = MCH_Lib.RotVec3(0.0, 0.0, 1.0, -yaw, -pitch, -prm.rotRoll);
        double tX = v.field_72450_a;
        double tZ = v.field_72449_c;
        double tY = v.field_72448_b;
        final double dist = MathHelper.func_76133_a(tX * tX + tY * tY + tZ * tZ);
        if (this.worldObj.field_72995_K) {
            tX = tX * 100.0 / dist;
            tY = tY * 100.0 / dist;
            tZ = tZ * 100.0 / dist;
        }
        else {
            tX = tX * 150.0 / dist;
            tY = tY * 150.0 / dist;
            tZ = tZ * 150.0 / dist;
        }
        final Vec3 src = W_WorldFunc.getWorldVec3(this.worldObj, prm.user.field_70165_t, prm.user.field_70163_u, prm.user.field_70161_v);
        final Vec3 dst = W_WorldFunc.getWorldVec3(this.worldObj, prm.user.field_70165_t + tX, prm.user.field_70163_u + tY, prm.user.field_70161_v + tZ);
        final MovingObjectPosition m = W_WorldFunc.clip(this.worldObj, src, dst);
        if (m != null && W_MovingObjectPosition.isHitTypeTile(m) && MCH_Lib.isBlockInWater(this.worldObj, m.field_72311_b, m.field_72312_c, m.field_72309_d)) {
            if (!this.worldObj.field_72995_K) {
                double mx = -MathHelper.func_76126_a(yaw / 180.0f * 3.1415927f) * MathHelper.func_76134_b(pitch / 180.0f * 3.1415927f);
                double mz = MathHelper.func_76134_b(yaw / 180.0f * 3.1415927f) * MathHelper.func_76134_b(pitch / 180.0f * 3.1415927f);
                double my = -MathHelper.func_76126_a(pitch / 180.0f * 3.1415927f);
                mx = mx * this.getInfo().acceleration + prm.entity.field_70159_w;
                my = my * this.getInfo().acceleration + prm.entity.field_70181_x;
                mz = mz * this.getInfo().acceleration + prm.entity.field_70179_y;
                this.acceleration = MathHelper.func_76133_a(mx * mx + my * my + mz * mz);
                final MCH_EntityTorpedo e = new MCH_EntityTorpedo(this.worldObj, prm.posX, prm.posY, prm.posZ, prm.entity.field_70159_w, prm.entity.field_70181_x, prm.entity.field_70179_y, yaw, 0.0f, this.acceleration);
                e.setName(this.name);
                e.setParameterFromWeapon(this, prm.entity, prm.user);
                e.targetPosX = m.field_72307_f.field_72450_a;
                e.targetPosY = m.field_72307_f.field_72448_b;
                e.targetPosZ = m.field_72307_f.field_72449_c;
                e.field_70159_w = mx;
                e.field_70181_x = my;
                e.field_70179_y = mz;
                e.accelerationInWater = ((this.getInfo() != null) ? this.getInfo().accelerationInWater : 1.0);
                this.worldObj.func_72838_d((Entity)e);
                this.playSound(prm.entity);
            }
            return true;
        }
        return false;
    }
}
