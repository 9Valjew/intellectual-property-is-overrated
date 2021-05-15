package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import mcheli.wrapper.*;
import net.minecraft.util.*;

public class MCH_WeaponATMissile extends MCH_WeaponEntitySeeker
{
    public MCH_WeaponATMissile(final World w, final Vec3 v, final float yaw, final float pitch, final String nm, final MCH_WeaponInfo wi) {
        super(w, v, yaw, pitch, nm, wi);
        this.power = 32;
        this.acceleration = 2.0f;
        this.explosionPower = 4;
        this.interval = -100;
        if (w.field_72995_K) {
            this.interval -= 10;
        }
        this.numMode = 2;
        this.guidanceSystem.canLockOnGround = true;
        this.guidanceSystem.ridableOnly = wi.ridableOnly;
    }
    
    @Override
    public boolean isCooldownCountReloadTime() {
        return true;
    }
    
    @Override
    public String getName() {
        String opt = "";
        if (this.getCurrentMode() == 1) {
            opt = " [TA]";
        }
        return super.getName() + opt;
    }
    
    @Override
    public void update(final int countWait) {
        super.update(countWait);
    }
    
    @Override
    public boolean shot(final MCH_WeaponParam prm) {
        if (this.worldObj.field_72995_K) {
            return this.shotClient(prm.entity, prm.user);
        }
        return this.shotServer(prm);
    }
    
    protected boolean shotClient(final Entity entity, final Entity user) {
        boolean result = false;
        if (this.guidanceSystem.lock(user) && this.guidanceSystem.lastLockEntity != null) {
            result = true;
            this.optionParameter1 = W_Entity.getEntityId(this.guidanceSystem.lastLockEntity);
        }
        this.optionParameter2 = this.getCurrentMode();
        return result;
    }
    
    protected boolean shotServer(final MCH_WeaponParam prm) {
        Entity tgtEnt = null;
        tgtEnt = prm.user.field_70170_p.func_73045_a(prm.option1);
        if (tgtEnt == null || tgtEnt.field_70128_L) {
            return false;
        }
        final float yaw = prm.user.field_70177_z + this.fixRotationYaw;
        final float pitch = prm.entity.field_70125_A + this.fixRotationPitch;
        final double tX = -MathHelper.func_76126_a(yaw / 180.0f * 3.1415927f) * MathHelper.func_76134_b(pitch / 180.0f * 3.1415927f);
        final double tZ = MathHelper.func_76134_b(yaw / 180.0f * 3.1415927f) * MathHelper.func_76134_b(pitch / 180.0f * 3.1415927f);
        final double tY = -MathHelper.func_76126_a(pitch / 180.0f * 3.1415927f);
        final MCH_EntityATMissile e = new MCH_EntityATMissile(this.worldObj, prm.posX, prm.posY, prm.posZ, tX, tY, tZ, yaw, pitch, this.acceleration);
        e.setName(this.name);
        e.setParameterFromWeapon(this, prm.entity, prm.user);
        e.setTargetEntity(tgtEnt);
        e.guidanceType = prm.option2;
        this.worldObj.func_72838_d((Entity)e);
        this.playSound(prm.entity);
        return true;
    }
}
