package mcheli.eval.eval.var;

import java.util.*;
import java.lang.reflect.*;

public class MapVariable implements Variable
{
    protected Map map;
    
    public MapVariable() {
        this(new HashMap());
    }
    
    public MapVariable(final Map varMap) {
        this.map = varMap;
    }
    
    public void setMap(final Map varMap) {
        this.map = varMap;
    }
    
    public Map getMap() {
        return this.map;
    }
    
    @Override
    public void setValue(final Object name, final Object obj) {
        this.map.put(name, obj);
    }
    
    @Override
    public Object getObject(final Object name) {
        return this.map.get(name);
    }
    
    @Override
    public long evalLong(final Object val) {
        return ((Number)val).longValue();
    }
    
    @Override
    public double evalDouble(final Object val) {
        return ((Number)val).doubleValue();
    }
    
    @Override
    public Object getObject(final Object array, final int index) {
        return Array.get(array, index);
    }
    
    @Override
    public void setValue(final Object array, final int index, final Object val) {
        Array.set(array, index, val);
    }
    
    @Override
    public Object getObject(final Object obj, final String field) {
        try {
            final Class c = obj.getClass();
            final Field f = c.getField(field);
            return f.get(obj);
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }
    
    @Override
    public void setValue(final Object obj, final String field, final Object val) {
        try {
            final Class c = obj.getClass();
            final Field f = c.getField(field);
            f.set(obj, val);
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }
}
