package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.util.*;

public abstract class MCH_WeaponEntitySeeker extends MCH_WeaponBase
{
    public MCH_IEntityLockChecker entityLockChecker;
    public MCH_WeaponGuidanceSystem guidanceSystem;
    
    public MCH_WeaponEntitySeeker(final World w, final Vec3 v, final float yaw, final float pitch, final String nm, final MCH_WeaponInfo wi) {
        super(w, v, yaw, pitch, nm, wi);
        this.guidanceSystem = new MCH_WeaponGuidanceSystem(w);
        this.guidanceSystem.lockRange = 200.0;
        this.guidanceSystem.lockAngle = 5;
        this.guidanceSystem.setLockCountMax(25);
    }
    
    @Override
    public MCH_WeaponGuidanceSystem getGuidanceSystem() {
        return this.guidanceSystem;
    }
    
    @Override
    public int getLockCount() {
        return this.guidanceSystem.getLockCount();
    }
    
    @Override
    public void setLockCountMax(final int n) {
        this.guidanceSystem.setLockCountMax(n);
    }
    
    @Override
    public int getLockCountMax() {
        return this.guidanceSystem.getLockCountMax();
    }
    
    @Override
    public void setLockChecker(final MCH_IEntityLockChecker checker) {
        this.guidanceSystem.checker = checker;
    }
    
    @Override
    public void update(final int countWait) {
        super.update(countWait);
        this.guidanceSystem.update();
    }
}
