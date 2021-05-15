package mcheli.eval.eval.exp;

public class PlusExpression extends Col2Expression
{
    public PlusExpression() {
        this.setOperator("+");
    }
    
    protected PlusExpression(final PlusExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    @Override
    public AbstractExpression dup(final ShareExpValue s) {
        return new PlusExpression(this, s);
    }
    
    @Override
    protected long operateLong(final long vl, final long vr) {
        return vl + vr;
    }
    
    @Override
    protected double operateDouble(final double vl, final double vr) {
        return vl + vr;
    }
    
    @Override
    protected Object operateObject(final Object vl, final Object vr) {
        return this.share.oper.plus(vl, vr);
    }
}
