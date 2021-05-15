package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import mcheli.multiplay.*;
import mcheli.aircraft.*;

public class MCH_WeaponTargetingPod extends MCH_WeaponBase
{
    public MCH_WeaponTargetingPod(final World w, final Vec3 v, final float yaw, final float pitch, final String nm, final MCH_WeaponInfo wi) {
        super(w, v, yaw, pitch, nm, wi);
        this.interval = -90;
        if (w.field_72995_K) {
            this.interval -= 10;
        }
    }
    
    @Override
    public boolean shot(final MCH_WeaponParam prm) {
        if (!this.worldObj.field_72995_K) {
            final MCH_WeaponInfo info = this.getInfo();
            if ((info.target & 0x40) != 0x0) {
                if (MCH_Multiplay.markPoint((EntityPlayer)prm.user, prm.posX, prm.posY, prm.posZ)) {
                    this.playSound(prm.user);
                }
                else {
                    this.playSound(prm.user, "ng");
                }
            }
            else if (MCH_Multiplay.spotEntity((EntityPlayer)prm.user, (MCH_EntityAircraft)prm.entity, prm.posX, prm.posY, prm.posZ, info.target, info.length, info.markTime, info.angle)) {
                this.playSound(prm.entity);
            }
            else {
                this.playSound(prm.entity, "ng");
            }
        }
        return true;
    }
}
