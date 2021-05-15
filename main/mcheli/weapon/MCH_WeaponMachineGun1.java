package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.util.*;
import mcheli.*;
import net.minecraft.entity.*;

public class MCH_WeaponMachineGun1 extends MCH_WeaponBase
{
    public MCH_WeaponMachineGun1(final World w, final Vec3 v, final float yaw, final float pitch, final String nm, final MCH_WeaponInfo wi) {
        super(w, v, yaw, pitch, nm, wi);
        this.power = 8;
        this.acceleration = 4.0f;
        this.explosionPower = 0;
        this.interval = 0;
    }
    
    @Override
    public boolean shot(final MCH_WeaponParam prm) {
        if (!this.worldObj.field_72995_K) {
            final Vec3 v = MCH_Lib.RotVec3(0.0, 0.0, 1.0, -prm.rotYaw, -prm.rotPitch, -prm.rotRoll);
            final MCH_EntityBullet e = new MCH_EntityBullet(this.worldObj, prm.posX, prm.posY, prm.posZ, v.field_72450_a, v.field_72448_b, v.field_72449_c, prm.rotYaw, prm.rotPitch, this.acceleration);
            e.setName(this.name);
            e.setParameterFromWeapon(this, prm.entity, prm.user);
            final MCH_EntityBullet mch_EntityBullet = e;
            mch_EntityBullet.field_70165_t += e.field_70159_w * 0.5;
            final MCH_EntityBullet mch_EntityBullet2 = e;
            mch_EntityBullet2.field_70163_u += e.field_70181_x * 0.5;
            final MCH_EntityBullet mch_EntityBullet3 = e;
            mch_EntityBullet3.field_70161_v += e.field_70179_y * 0.5;
            this.worldObj.func_72838_d((Entity)e);
            this.playSound(prm.entity);
        }
        return true;
    }
}
