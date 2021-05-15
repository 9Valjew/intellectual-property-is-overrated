package mcheli.eval.eval.func;

public interface Function
{
    long evalLong(final Object p0, final String p1, final Long[] p2) throws Throwable;
    
    double evalDouble(final Object p0, final String p1, final Double[] p2) throws Throwable;
    
    Object evalObject(final Object p0, final String p1, final Object[] p2) throws Throwable;
}
