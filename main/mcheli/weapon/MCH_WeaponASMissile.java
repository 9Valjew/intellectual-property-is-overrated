package mcheli.weapon;

import net.minecraft.world.*;
import mcheli.wrapper.*;
import mcheli.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class MCH_WeaponASMissile extends MCH_WeaponBase
{
    public MCH_WeaponASMissile(final World w, final Vec3 v, final float yaw, final float pitch, final String nm, final MCH_WeaponInfo wi) {
        super(w, v, yaw, pitch, nm, wi);
        this.acceleration = 3.0f;
        this.explosionPower = 9;
        this.power = 40;
        this.interval = -350;
        if (w.field_72995_K) {
            this.interval -= 10;
        }
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
        final float yaw = prm.user.field_70177_z;
        final float pitch = prm.user.field_70125_A;
        double tX = -MathHelper.func_76126_a(yaw / 180.0f * 3.1415927f) * MathHelper.func_76134_b(pitch / 180.0f * 3.1415927f);
        double tZ = MathHelper.func_76134_b(yaw / 180.0f * 3.1415927f) * MathHelper.func_76134_b(pitch / 180.0f * 3.1415927f);
        double tY = -MathHelper.func_76126_a(pitch / 180.0f * 3.1415927f);
        final double dist = MathHelper.func_76133_a(tX * tX + tY * tY + tZ * tZ);
        if (this.worldObj.field_72995_K) {
            tX = tX * 200.0 / dist;
            tY = tY * 200.0 / dist;
            tZ = tZ * 200.0 / dist;
        }
        else {
            tX = tX * 250.0 / dist;
            tY = tY * 250.0 / dist;
            tZ = tZ * 250.0 / dist;
        }
        final Vec3 src = W_WorldFunc.getWorldVec3(this.worldObj, prm.entity.field_70165_t, prm.entity.field_70163_u + 1.62, prm.entity.field_70161_v);
        final Vec3 dst = W_WorldFunc.getWorldVec3(this.worldObj, prm.entity.field_70165_t + tX, prm.entity.field_70163_u + 1.62 + tY, prm.entity.field_70161_v + tZ);
        final MovingObjectPosition m = W_WorldFunc.clip(this.worldObj, src, dst);
        if (m != null && W_MovingObjectPosition.isHitTypeTile(m) && !MCH_Lib.isBlockInWater(this.worldObj, m.field_72311_b, m.field_72312_c, m.field_72309_d)) {
            if (!this.worldObj.field_72995_K) {
                final MCH_EntityASMissile e = new MCH_EntityASMissile(this.worldObj, prm.posX, prm.posY, prm.posZ, tX, tY, tZ, yaw, pitch, this.acceleration);
                e.setName(this.name);
                e.setParameterFromWeapon(this, prm.entity, prm.user);
                e.targetPosX = m.field_72307_f.field_72450_a;
                e.targetPosY = m.field_72307_f.field_72448_b;
                e.targetPosZ = m.field_72307_f.field_72449_c;
                this.worldObj.func_72838_d((Entity)e);
                this.playSound(prm.entity);
            }
            return true;
        }
        return false;
    }
}
