package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.util.*;
import mcheli.*;
import net.minecraft.entity.*;

public class MCH_WeaponRocket extends MCH_WeaponBase
{
    public MCH_WeaponRocket(final World w, final Vec3 v, final float yaw, final float pitch, final String nm, final MCH_WeaponInfo wi) {
        super(w, v, yaw, pitch, nm, wi);
        this.acceleration = 4.0f;
        this.explosionPower = 3;
        this.power = 22;
        this.interval = 5;
        if (w.field_72995_K) {
            this.interval += 2;
        }
    }
    
    @Override
    public String getName() {
        return super.getName() + ((this.getCurrentMode() == 0) ? "" : " [HEIAP]");
    }
    
    @Override
    public boolean shot(final MCH_WeaponParam prm) {
        if (!this.worldObj.field_72995_K) {
            this.playSound(prm.entity);
            final Vec3 v = MCH_Lib.RotVec3(0.0, 0.0, 1.0, -prm.rotYaw, -prm.rotPitch, -prm.rotRoll);
            final MCH_EntityRocket e = new MCH_EntityRocket(this.worldObj, prm.posX, prm.posY, prm.posZ, v.field_72450_a, v.field_72448_b, v.field_72449_c, prm.rotYaw, prm.rotPitch, this.acceleration);
            e.setName(this.name);
            e.setParameterFromWeapon(this, prm.entity, prm.user);
            if (prm.option1 == 0 && this.numMode > 1) {
                e.piercing = 0;
            }
            this.worldObj.func_72838_d((Entity)e);
        }
        else {
            this.optionParameter1 = this.getCurrentMode();
        }
        return true;
    }
}
