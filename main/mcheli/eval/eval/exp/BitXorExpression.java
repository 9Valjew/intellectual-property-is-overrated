package mcheli.eval.eval.exp;

public class BitXorExpression extends Col2Expression
{
    public BitXorExpression() {
        this.setOperator("^");
    }
    
    protected BitXorExpression(final BitXorExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    @Override
    public AbstractExpression dup(final ShareExpValue s) {
        return new BitXorExpression(this, s);
    }
    
    @Override
    protected long operateLong(final long vl, final long vr) {
        return vl ^ vr;
    }
    
    @Override
    protected double operateDouble(final double vl, final double vr) {
        return (long)vl ^ (long)vr;
    }
    
    @Override
    protected Object operateObject(final Object vl, final Object vr) {
        return this.share.oper.bitXor(vl, vr);
    }
}
