package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.util.*;

public class MCH_WeaponSmoke extends MCH_WeaponBase
{
    public MCH_WeaponSmoke(final World w, final Vec3 v, final float yaw, final float pitch, final String nm, final MCH_WeaponInfo wi) {
        super(w, v, yaw, pitch, nm, wi);
        this.power = 0;
    }
    
    @Override
    public boolean shot(final MCH_WeaponParam prm) {
        return false;
    }
}
