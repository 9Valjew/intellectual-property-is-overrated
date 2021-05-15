package mcheli.eval.eval.func;

import java.lang.reflect.*;

public class InvokeFunction implements Function
{
    @Override
    public long evalLong(final Object object, final String name, final Long[] args) throws Throwable {
        if (object == null) {
            return 0L;
        }
        final Object r = callMethod(object, name, args);
        return ((Number)r).longValue();
    }
    
    @Override
    public double evalDouble(final Object object, final String name, final Double[] args) throws Throwable {
        if (object == null) {
            return 0.0;
        }
        final Object r = callMethod(object, name, args);
        return ((Number)r).doubleValue();
    }
    
    @Override
    public Object evalObject(final Object object, final String name, final Object[] args) throws Throwable {
        if (object == null) {
            return null;
        }
        return callMethod(object, name, args);
    }
    
    public static Object callMethod(final Object obj, final String name, final Object[] args) throws Exception {
        final Class c = obj.getClass();
        final Class[] types = new Class[args.length];
        for (int i = 0; i < types.length; ++i) {
            types[i] = args[i].getClass();
        }
        final Method m = c.getMethod(name, (Class[])types);
        return m.invoke(obj, args);
    }
}
