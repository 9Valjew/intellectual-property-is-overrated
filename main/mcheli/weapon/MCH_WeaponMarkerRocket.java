package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.util.*;
import mcheli.*;
import net.minecraft.entity.*;

public class MCH_WeaponMarkerRocket extends MCH_WeaponBase
{
    public MCH_WeaponMarkerRocket(final World w, final Vec3 v, final float yaw, final float pitch, final String nm, final MCH_WeaponInfo wi) {
        super(w, v, yaw, pitch, nm, wi);
        this.acceleration = 3.0f;
        this.explosionPower = 0;
        this.power = 0;
        this.interval = 60;
        if (w.field_72995_K) {
            this.interval += 10;
        }
    }
    
    @Override
    public boolean shot(final MCH_WeaponParam prm) {
        if (!this.worldObj.field_72995_K) {
            this.playSound(prm.entity);
            final Vec3 v = MCH_Lib.RotVec3(0.0, 0.0, 1.0, -prm.rotYaw, -prm.rotPitch, -prm.rotRoll);
            final MCH_EntityMarkerRocket e = new MCH_EntityMarkerRocket(this.worldObj, prm.posX, prm.posY, prm.posZ, v.field_72450_a, v.field_72448_b, v.field_72449_c, prm.rotYaw, prm.rotPitch, this.acceleration);
            e.setName(this.name);
            e.setParameterFromWeapon(this, prm.entity, prm.user);
            e.setMarkerStatus(1);
            this.worldObj.func_72838_d((Entity)e);
        }
        else {
            this.optionParameter1 = this.getCurrentMode();
        }
        return true;
    }
}
