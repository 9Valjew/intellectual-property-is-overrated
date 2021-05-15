package mcheli.eval.eval.exp;

public class LessEqualExpression extends Col2Expression
{
    public LessEqualExpression() {
        this.setOperator("<=");
    }
    
    protected LessEqualExpression(final LessEqualExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    @Override
    public AbstractExpression dup(final ShareExpValue s) {
        return new LessEqualExpression(this, s);
    }
    
    @Override
    protected long operateLong(final long vl, final long vr) {
        return (vl <= vr) ? 1 : 0;
    }
    
    @Override
    protected double operateDouble(final double vl, final double vr) {
        return (vl <= vr) ? 1.0 : 0.0;
    }
    
    @Override
    protected Object operateObject(final Object vl, final Object vr) {
        return this.share.oper.lessEqual(vl, vr);
    }
}
