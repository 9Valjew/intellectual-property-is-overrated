package mcheli;

import java.util.*;
import net.minecraft.entity.*;

public class MCH_DamageFactor
{
    private HashMap<Class, Float> map;
    
    public MCH_DamageFactor() {
        this.map = new HashMap<Class, Float>();
    }
    
    public void clear() {
        this.map.clear();
    }
    
    public void add(final Class c, final float value) {
        this.map.put(c, value);
    }
    
    public float getDamageFactor(final Class c) {
        if (this.map.containsKey(c)) {
            return this.map.get(c);
        }
        return 1.0f;
    }
    
    public float getDamageFactor(final Entity e) {
        return (e != null) ? this.getDamageFactor(e.getClass()) : 1.0f;
    }
}
