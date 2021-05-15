package mcheli.eval.eval.exp;

public class BitOrExpression extends Col2Expression
{
    public BitOrExpression() {
        this.setOperator("|");
    }
    
    protected BitOrExpression(final BitOrExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    @Override
    public AbstractExpression dup(final ShareExpValue s) {
        return new BitOrExpression(this, s);
    }
    
    @Override
    protected long operateLong(final long vl, final long vr) {
        return vl | vr;
    }
    
    @Override
    protected double operateDouble(final double vl, final double vr) {
        return (long)vl | (long)vr;
    }
    
    @Override
    protected Object operateObject(final Object vl, final Object vr) {
        return this.share.oper.bitOr(vl, vr);
    }
}
