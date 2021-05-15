package mcheli.weapon;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import mcheli.wrapper.*;
import mcheli.aircraft.*;
import net.minecraft.util.*;

public class MCH_WeaponTvMissile extends MCH_WeaponBase
{
    protected MCH_EntityTvMissile lastShotTvMissile;
    protected Entity lastShotEntity;
    protected boolean isTVGuided;
    
    public MCH_WeaponTvMissile(final World w, final Vec3 v, final float yaw, final float pitch, final String nm, final MCH_WeaponInfo wi) {
        super(w, v, yaw, pitch, nm, wi);
        this.lastShotTvMissile = null;
        this.lastShotEntity = null;
        this.isTVGuided = false;
        this.power = 32;
        this.acceleration = 2.0f;
        this.explosionPower = 4;
        this.interval = -100;
        if (w.field_72995_K) {
            this.interval -= 10;
        }
        this.numMode = 2;
        this.lastShotEntity = null;
        this.lastShotTvMissile = null;
        this.isTVGuided = false;
    }
    
    @Override
    public String getName() {
        String opt = "";
        if (this.getCurrentMode() == 0) {
            opt = " [TV]";
        }
        if (this.getCurrentMode() == 2) {
            opt = " [TA]";
        }
        return super.getName() + opt;
    }
    
    @Override
    public void update(final int countWait) {
        super.update(countWait);
        if (!this.worldObj.field_72995_K) {
            if (this.isTVGuided && this.tick <= 9) {
                if (this.tick % 3 == 0 && this.lastShotTvMissile != null && !this.lastShotTvMissile.field_70128_L && this.lastShotEntity != null && !this.lastShotEntity.field_70128_L) {
                    MCH_PacketNotifyTVMissileEntity.send(W_Entity.getEntityId(this.lastShotEntity), W_Entity.getEntityId(this.lastShotTvMissile));
                }
                if (this.tick == 9) {
                    this.lastShotEntity = null;
                    this.lastShotTvMissile = null;
                }
            }
            if (this.tick <= 2 && this.lastShotEntity instanceof MCH_EntityAircraft) {
                ((MCH_EntityAircraft)this.lastShotEntity).setTVMissile(this.lastShotTvMissile);
            }
        }
    }
    
    @Override
    public boolean shot(final MCH_WeaponParam prm) {
        if (this.worldObj.field_72995_K) {
            return this.shotClient(prm.entity, prm.user);
        }
        return this.shotServer(prm);
    }
    
    protected boolean shotClient(final Entity entity, final Entity user) {
        this.optionParameter2 = 0;
        this.optionParameter1 = this.getCurrentMode();
        return true;
    }
    
    protected boolean shotServer(final MCH_WeaponParam prm) {
        final float yaw = prm.user.field_70177_z + this.fixRotationYaw;
        final float pitch = prm.user.field_70125_A + this.fixRotationPitch;
        final double tX = -MathHelper.func_76126_a(yaw / 180.0f * 3.1415927f) * MathHelper.func_76134_b(pitch / 180.0f * 3.1415927f);
        final double tZ = MathHelper.func_76134_b(yaw / 180.0f * 3.1415927f) * MathHelper.func_76134_b(pitch / 180.0f * 3.1415927f);
        final double tY = -MathHelper.func_76126_a(pitch / 180.0f * 3.1415927f);
        this.isTVGuided = (prm.option1 == 0);
        float acr = this.acceleration;
        if (!this.isTVGuided) {
            acr *= 1.5;
        }
        final MCH_EntityTvMissile e = new MCH_EntityTvMissile(this.worldObj, prm.posX, prm.posY, prm.posZ, tX, tY, tZ, yaw, pitch, acr);
        e.setName(this.name);
        e.setParameterFromWeapon(this, prm.entity, prm.user);
        this.lastShotEntity = prm.entity;
        this.lastShotTvMissile = e;
        this.worldObj.func_72838_d((Entity)e);
        this.playSound(prm.entity);
        return true;
    }
}
