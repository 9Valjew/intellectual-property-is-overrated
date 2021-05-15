package mcheli.eval.eval.exp;

public class GreaterThanExpression extends Col2Expression
{
    public GreaterThanExpression() {
        this.setOperator(">");
    }
    
    protected GreaterThanExpression(final GreaterThanExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    @Override
    public AbstractExpression dup(final ShareExpValue s) {
        return new GreaterThanExpression(this, s);
    }
    
    @Override
    protected long operateLong(final long vl, final long vr) {
        return (vl > vr) ? 1 : 0;
    }
    
    @Override
    protected double operateDouble(final double vl, final double vr) {
        return (vl > vr) ? 1.0 : 0.0;
    }
    
    @Override
    protected Object operateObject(final Object vl, final Object vr) {
        return this.share.oper.greaterThan(vl, vr);
    }
}
