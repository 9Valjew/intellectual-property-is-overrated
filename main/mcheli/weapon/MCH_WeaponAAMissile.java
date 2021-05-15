package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import mcheli.wrapper.*;

public class MCH_WeaponAAMissile extends MCH_WeaponEntitySeeker
{
    public MCH_WeaponAAMissile(final World w, final Vec3 v, final float yaw, final float pitch, final String nm, final MCH_WeaponInfo wi) {
        super(w, v, yaw, pitch, nm, wi);
        this.power = 12;
        this.acceleration = 2.5f;
        this.explosionPower = 4;
        this.interval = 5;
        if (w.field_72995_K) {
            this.interval += 5;
        }
        this.guidanceSystem.canLockInAir = true;
        this.guidanceSystem.ridableOnly = wi.ridableOnly;
    }
    
    @Override
    public boolean isCooldownCountReloadTime() {
        return true;
    }
    
    @Override
    public void update(final int countWait) {
        super.update(countWait);
    }
    
    @Override
    public boolean shot(final MCH_WeaponParam prm) {
        boolean result = false;
        if (!this.worldObj.field_72995_K) {
            final Entity tgtEnt = prm.user.field_70170_p.func_73045_a(prm.option1);
            if (tgtEnt != null && !tgtEnt.field_70128_L) {
                this.playSound(prm.entity);
                final float yaw = prm.entity.field_70177_z + this.fixRotationYaw;
                final float pitch = prm.entity.field_70125_A + this.fixRotationPitch;
                final double tX = -MathHelper.func_76126_a(yaw / 180.0f * 3.1415927f) * MathHelper.func_76134_b(pitch / 180.0f * 3.1415927f);
                final double tZ = MathHelper.func_76134_b(yaw / 180.0f * 3.1415927f) * MathHelper.func_76134_b(pitch / 180.0f * 3.1415927f);
                final double tY = -MathHelper.func_76126_a(pitch / 180.0f * 3.1415927f);
                final MCH_EntityAAMissile e = new MCH_EntityAAMissile(this.worldObj, prm.posX, prm.posY, prm.posZ, tX, tY, tZ, yaw, pitch, this.acceleration);
                e.setName(this.name);
                e.setParameterFromWeapon(this, prm.entity, prm.user);
                e.setTargetEntity(tgtEnt);
                this.worldObj.func_72838_d((Entity)e);
                result = true;
            }
        }
        else if (this.guidanceSystem.lock(prm.user) && this.guidanceSystem.lastLockEntity != null) {
            result = true;
            this.optionParameter1 = W_Entity.getEntityId(this.guidanceSystem.lastLockEntity);
        }
        return result;
    }
}
