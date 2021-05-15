package mcheli.eval.eval.exp;

public class BitAndExpression extends Col2Expression
{
    public BitAndExpression() {
        this.setOperator("&");
    }
    
    protected BitAndExpression(final BitAndExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    @Override
    public AbstractExpression dup(final ShareExpValue s) {
        return new BitAndExpression(this, s);
    }
    
    @Override
    protected long operateLong(final long vl, final long vr) {
        return vl & vr;
    }
    
    @Override
    protected double operateDouble(final double vl, final double vr) {
        return (long)vl & (long)vr;
    }
    
    @Override
    protected Object operateObject(final Object vl, final Object vr) {
        return this.share.oper.bitAnd(vl, vr);
    }
}
