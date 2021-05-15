package mcheli.eval.eval.oper;

import java.math.*;

public class JavaExOperator implements Operator
{
    boolean inLong(final Object x) {
        return x instanceof Long || x instanceof Integer || x instanceof Short || x instanceof Byte || x instanceof BigInteger || x instanceof BigDecimal;
    }
    
    long l(final Object x) {
        return ((Number)x).longValue();
    }
    
    boolean inDouble(final Object x) {
        return x instanceof Double || x instanceof Float;
    }
    
    double d(final Object x) {
        return ((Number)x).doubleValue();
    }
    
    Object n(final long n, final Object x) {
        if (x instanceof Long) {
            return new Long(n);
        }
        if (x instanceof Double) {
            return new Double(n);
        }
        if (x instanceof Integer) {
            return new Integer((int)n);
        }
        if (x instanceof Short) {
            return new Short((short)n);
        }
        if (x instanceof Byte) {
            return new Byte((byte)n);
        }
        if (x instanceof Float) {
            return new Float(n);
        }
        if (x instanceof BigInteger) {
            return BigInteger.valueOf(n);
        }
        if (x instanceof BigDecimal) {
            return BigDecimal.valueOf(n);
        }
        if (x instanceof String) {
            return String.valueOf(n);
        }
        return new Long(n);
    }
    
    Object n(final long n, final Object x, final Object y) {
        if (x instanceof Byte || y instanceof Byte) {
            return new Byte((byte)n);
        }
        if (x instanceof Short || y instanceof Short) {
            return new Short((short)n);
        }
        if (x instanceof Integer || y instanceof Integer) {
            return new Integer((int)n);
        }
        if (x instanceof Long || y instanceof Long) {
            return new Long(n);
        }
        if (x instanceof BigInteger || y instanceof BigInteger) {
            return BigInteger.valueOf(n);
        }
        if (x instanceof BigDecimal || y instanceof BigDecimal) {
            return BigDecimal.valueOf(n);
        }
        if (x instanceof Float || y instanceof Float) {
            return new Float(n);
        }
        if (x instanceof Double || y instanceof Double) {
            return new Double(n);
        }
        if (x instanceof String || y instanceof String) {
            return String.valueOf(n);
        }
        return new Long(n);
    }
    
    Object n(final double n, final Object x) {
        if (x instanceof Float) {
            return new Float(n);
        }
        if (x instanceof String) {
            return String.valueOf(n);
        }
        return new Double(n);
    }
    
    Object n(final double n, final Object x, final Object y) {
        if (x instanceof Float || y instanceof Float) {
            return new Float(n);
        }
        if (x instanceof Number || y instanceof Number) {
            return new Double(n);
        }
        if (x instanceof String || y instanceof String) {
            return String.valueOf(n);
        }
        return new Double(n);
    }
    
    Object nn(final long n, final Object x) {
        if (x instanceof BigDecimal) {
            return BigDecimal.valueOf(n);
        }
        if (x instanceof BigInteger) {
            return BigInteger.valueOf(n);
        }
        return new Long(n);
    }
    
    Object nn(final long n, final Object x, final Object y) {
        if (x instanceof BigDecimal || y instanceof BigDecimal) {
            return BigDecimal.valueOf(n);
        }
        if (x instanceof BigInteger || y instanceof BigInteger) {
            return BigInteger.valueOf(n);
        }
        return new Long(n);
    }
    
    Object nn(final double n, final Object x) {
        if (this.inLong(x)) {
            return new Long((long)n);
        }
        return new Double(n);
    }
    
    Object nn(final double n, final Object x, final Object y) {
        if (this.inLong(x) && this.inLong(y)) {
            return new Long((long)n);
        }
        return new Double(n);
    }
    
    RuntimeException undefined(final Object x) {
        String c = null;
        if (x != null) {
            c = x.getClass().getName();
        }
        return new RuntimeException("\u672a\u5b9a\u7fa9\u5358\u9805\u6f14\u7b97\uff1a" + c);
    }
    
    RuntimeException undefined(final Object x, final Object y) {
        String c1 = null;
        String c2 = null;
        if (x != null) {
            c1 = x.getClass().getName();
        }
        if (y != null) {
            c2 = y.getClass().getName();
        }
        return new RuntimeException("\u672a\u5b9a\u7fa9\u4e8c\u9805\u6f14\u7b97\uff1a" + c1 + " , " + c2);
    }
    
    @Override
    public Object power(final Object x, final Object y) {
        if (x == null && y == null) {
            return null;
        }
        return this.nn(Math.pow(this.d(x), this.d(y)), x, y);
    }
    
    @Override
    public Object signPlus(final Object x) {
        return x;
    }
    
    @Override
    public Object signMinus(final Object x) {
        if (x == null) {
            return null;
        }
        if (this.inLong(x)) {
            return this.n(-this.l(x), x);
        }
        if (this.inDouble(x)) {
            return this.n(-this.d(x), x);
        }
        if (x instanceof Boolean) {
            return x;
        }
        throw this.undefined(x);
    }
    
    @Override
    public Object plus(final Object x, final Object y) {
        if (x == null && y == null) {
            return null;
        }
        if (this.inLong(x) && this.inLong(y)) {
            return this.nn(this.l(x) + this.l(y), x, y);
        }
        if (this.inDouble(x) && this.inDouble(y)) {
            return this.nn(this.d(x) + this.d(y), x, y);
        }
        if (x instanceof String || y instanceof String) {
            return String.valueOf(x) + String.valueOf(y);
        }
        if (x instanceof Character || y instanceof Character) {
            return String.valueOf(x) + String.valueOf(y);
        }
        throw this.undefined(x, y);
    }
    
    @Override
    public Object minus(final Object x, final Object y) {
        if (x == null && y == null) {
            return null;
        }
        if (this.inLong(x) && this.inLong(y)) {
            return this.nn(this.l(x) - this.l(y), x, y);
        }
        if (this.inDouble(x) && this.inDouble(y)) {
            return this.nn(this.d(x) - this.d(y), x, y);
        }
        throw this.undefined(x, y);
    }
    
    @Override
    public Object mult(final Object x, final Object y) {
        if (x == null && y == null) {
            return null;
        }
        if (this.inLong(x) && this.inLong(y)) {
            return this.nn(this.l(x) * this.l(y), x, y);
        }
        if (this.inDouble(x) && this.inDouble(y)) {
            return this.nn(this.d(x) * this.d(y), x, y);
        }
        String s = null;
        int ct = 0;
        boolean str = false;
        if (x instanceof String && y instanceof Number) {
            s = (String)x;
            ct = (int)this.l(y);
            str = true;
        }
        else if (y instanceof String && x instanceof Number) {
            s = (String)y;
            ct = (int)this.l(x);
            str = true;
        }
        if (str) {
            final StringBuffer sb = new StringBuffer(s.length() * ct);
            for (int i = 0; i < ct; ++i) {
                sb.append(s);
            }
            return sb.toString();
        }
        throw this.undefined(x, y);
    }
    
    @Override
    public Object div(final Object x, final Object y) {
        if (x == null && y == null) {
            return null;
        }
        if (this.inLong(x) && this.inLong(y)) {
            return this.nn(this.l(x) / this.l(y), x);
        }
        if (this.inDouble(x) && this.inDouble(y)) {
            return this.nn(this.d(x) / this.d(y), x);
        }
        if (x instanceof String && y instanceof String) {
            final String s = (String)x;
            final String r = (String)y;
            return s.split(r);
        }
        throw this.undefined(x, y);
    }
    
    @Override
    public Object mod(final Object x, final Object y) {
        if (x == null && y == null) {
            return null;
        }
        if (this.inLong(x) && this.inLong(y)) {
            return this.nn(this.l(x) % this.l(y), x);
        }
        if (this.inDouble(x) && this.inDouble(y)) {
            return this.nn(this.d(x) % this.d(y), x);
        }
        throw this.undefined(x, y);
    }
    
    @Override
    public Object bitNot(final Object x) {
        if (x == null) {
            return null;
        }
        if (x instanceof Number) {
            return this.n(~this.l(x), x);
        }
        throw this.undefined(x);
    }
    
    @Override
    public Object shiftLeft(final Object x, final Object y) {
        if (x == null && y == null) {
            return null;
        }
        if (this.inLong(x) && this.inLong(y)) {
            return this.n(this.l(x) << (int)this.l(y), x);
        }
        if (this.inDouble(x) && this.inDouble(y)) {
            return this.n(this.d(x) * Math.pow(2.0, this.d(y)), x);
        }
        throw this.undefined(x, y);
    }
    
    @Override
    public Object shiftRight(final Object x, final Object y) {
        if (x == null && y == null) {
            return null;
        }
        if (this.inLong(x) && this.inLong(y)) {
            return this.n(this.l(x) >> (int)this.l(y), x);
        }
        if (this.inDouble(x) && this.inDouble(y)) {
            return this.n(this.d(x) / Math.pow(2.0, this.d(y)), x);
        }
        throw this.undefined(x, y);
    }
    
    @Override
    public Object shiftRightLogical(final Object x, final Object y) {
        if (x == null && y == null) {
            return null;
        }
        if (x instanceof Byte && y instanceof Number) {
            return this.n((this.l(x) & 0xFFL) >>> (int)this.l(y), x);
        }
        if (x instanceof Short && y instanceof Number) {
            return this.n((this.l(x) & 0xFFFFL) >>> (int)this.l(y), x);
        }
        if (x instanceof Integer && y instanceof Number) {
            return this.n((this.l(x) & 0xFFFFFFFFL) >>> (int)this.l(y), x);
        }
        if (this.inLong(x) && y instanceof Number) {
            return this.n(this.l(x) >>> (int)this.l(y), x);
        }
        if (this.inDouble(x) && y instanceof Number) {
            double t = this.d(x);
            if (t < 0.0) {
                t = -t;
            }
            return this.n(t / Math.pow(2.0, this.d(y)), x);
        }
        throw this.undefined(x, y);
    }
    
    @Override
    public Object bitAnd(final Object x, final Object y) {
        if (x == null && y == null) {
            return null;
        }
        if (x instanceof Number && y instanceof Number) {
            return this.n(this.l(x) & this.l(y), x);
        }
        throw this.undefined(x, y);
    }
    
    @Override
    public Object bitOr(final Object x, final Object y) {
        if (x == null && y == null) {
            return null;
        }
        if (x instanceof Number && y instanceof Number) {
            return this.n(this.l(x) | this.l(y), x);
        }
        throw this.undefined(x, y);
    }
    
    @Override
    public Object bitXor(final Object x, final Object y) {
        if (x == null && y == null) {
            return null;
        }
        if (x instanceof Number && y instanceof Number) {
            return this.n(this.l(x) ^ this.l(y), x);
        }
        throw this.undefined(x, y);
    }
    
    @Override
    public Object not(final Object x) {
        if (x == null) {
            return null;
        }
        if (x instanceof Boolean) {
            return x ? Boolean.FALSE : Boolean.TRUE;
        }
        if (!(x instanceof Number)) {
            throw this.undefined(x);
        }
        if (this.l(x) == 0L) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
    
    int compare(final Object x, final Object y) {
        if (x == null && y == null) {
            return 0;
        }
        if (x == null && y != null) {
            return -1;
        }
        if (x != null && y == null) {
            return 1;
        }
        if (this.inLong(x) && this.inLong(y)) {
            final long c = this.l(x) - this.l(y);
            if (c == 0L) {
                return 0;
            }
            if (c < 0L) {
                return -1;
            }
            return 1;
        }
        else if (x instanceof Number && y instanceof Number) {
            final double n = this.d(x) - this.d(y);
            if (n == 0.0) {
                return 0;
            }
            return (n < 0.0) ? -1 : 1;
        }
        else {
            final Class xc = x.getClass();
            final Class yc = y.getClass();
            if (xc.isAssignableFrom(yc) && x instanceof Comparable) {
                return ((Comparable)x).compareTo(y);
            }
            if (yc.isAssignableFrom(xc) && y instanceof Comparable) {
                return -((Comparable)y).compareTo(x);
            }
            if (x.equals(y)) {
                return 0;
            }
            throw this.undefined(x, y);
        }
    }
    
    @Override
    public Object equal(final Object x, final Object y) {
        return (this.compare(x, y) == 0) ? Boolean.TRUE : Boolean.FALSE;
    }
    
    @Override
    public Object notEqual(final Object x, final Object y) {
        return (this.compare(x, y) != 0) ? Boolean.TRUE : Boolean.FALSE;
    }
    
    @Override
    public Object lessThan(final Object x, final Object y) {
        return (this.compare(x, y) < 0) ? Boolean.TRUE : Boolean.FALSE;
    }
    
    @Override
    public Object lessEqual(final Object x, final Object y) {
        return (this.compare(x, y) <= 0) ? Boolean.TRUE : Boolean.FALSE;
    }
    
    @Override
    public Object greaterThan(final Object x, final Object y) {
        return (this.compare(x, y) > 0) ? Boolean.TRUE : Boolean.FALSE;
    }
    
    @Override
    public Object greaterEqual(final Object x, final Object y) {
        return (this.compare(x, y) >= 0) ? Boolean.TRUE : Boolean.FALSE;
    }
    
    @Override
    public boolean bool(final Object x) {
        if (x == null) {
            return false;
        }
        if (x instanceof Boolean) {
            return (boolean)x;
        }
        if (x instanceof Number) {
            return ((Number)x).longValue() != 0L;
        }
        return Boolean.valueOf(x.toString());
    }
    
    @Override
    public Object inc(final Object x, final int inc) {
        if (x == null) {
            return null;
        }
        if (this.inLong(x)) {
            return this.n(this.l(x) + inc, x);
        }
        if (this.inDouble(x)) {
            return this.n(this.d(x) + inc, x);
        }
        throw this.undefined(x);
    }
}
