package mcheli.eval.eval.exp;

public class NotEqualExpression extends Col2Expression
{
    public NotEqualExpression() {
        this.setOperator("!=");
    }
    
    protected NotEqualExpression(final NotEqualExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    @Override
    public AbstractExpression dup(final ShareExpValue s) {
        return new NotEqualExpression(this, s);
    }
    
    @Override
    protected long operateLong(final long vl, final long vr) {
        return (vl != vr) ? 1 : 0;
    }
    
    @Override
    protected double operateDouble(final double vl, final double vr) {
        return (vl != vr) ? 1.0 : 0.0;
    }
    
    @Override
    protected Object operateObject(final Object vl, final Object vr) {
        return this.share.oper.notEqual(vl, vr);
    }
}
