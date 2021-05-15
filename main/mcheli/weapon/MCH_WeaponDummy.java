package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.util.*;

public class MCH_WeaponDummy extends MCH_WeaponBase
{
    static final MCH_WeaponInfo dummy;
    
    public int getUseInterval() {
        return 0;
    }
    
    public MCH_WeaponDummy(final World w, final Vec3 v, final float yaw, final float pitch, final String nm, final MCH_WeaponInfo wi) {
        super(w, v, yaw, pitch, nm.isEmpty() ? "none" : nm, (wi != null) ? wi : MCH_WeaponDummy.dummy);
    }
    
    @Override
    public boolean shot(final MCH_WeaponParam prm) {
        return false;
    }
    
    static {
        dummy = new MCH_WeaponInfo("none");
    }
}
