package mcheli.eval.eval.oper;

public interface Operator
{
    Object power(final Object p0, final Object p1);
    
    Object signPlus(final Object p0);
    
    Object signMinus(final Object p0);
    
    Object plus(final Object p0, final Object p1);
    
    Object minus(final Object p0, final Object p1);
    
    Object mult(final Object p0, final Object p1);
    
    Object div(final Object p0, final Object p1);
    
    Object mod(final Object p0, final Object p1);
    
    Object bitNot(final Object p0);
    
    Object shiftLeft(final Object p0, final Object p1);
    
    Object shiftRight(final Object p0, final Object p1);
    
    Object shiftRightLogical(final Object p0, final Object p1);
    
    Object bitAnd(final Object p0, final Object p1);
    
    Object bitOr(final Object p0, final Object p1);
    
    Object bitXor(final Object p0, final Object p1);
    
    Object not(final Object p0);
    
    Object equal(final Object p0, final Object p1);
    
    Object notEqual(final Object p0, final Object p1);
    
    Object lessThan(final Object p0, final Object p1);
    
    Object lessEqual(final Object p0, final Object p1);
    
    Object greaterThan(final Object p0, final Object p1);
    
    Object greaterEqual(final Object p0, final Object p1);
    
    boolean bool(final Object p0);
    
    Object inc(final Object p0, final int p1);
}
