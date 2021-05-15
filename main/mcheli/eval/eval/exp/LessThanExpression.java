package mcheli.eval.eval.exp;

public class LessThanExpression extends Col2Expression
{
    public LessThanExpression() {
        this.setOperator("<");
    }
    
    protected LessThanExpression(final LessThanExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    @Override
    public AbstractExpression dup(final ShareExpValue s) {
        return new LessThanExpression(this, s);
    }
    
    @Override
    protected long operateLong(final long vl, final long vr) {
        return (vl < vr) ? 1 : 0;
    }
    
    @Override
    protected double operateDouble(final double vl, final double vr) {
        return (vl < vr) ? 1.0 : 0.0;
    }
    
    @Override
    protected Object operateObject(final Object vl, final Object vr) {
        return this.share.oper.lessThan(vl, vr);
    }
}
