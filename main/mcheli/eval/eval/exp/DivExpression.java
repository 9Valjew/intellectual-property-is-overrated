package mcheli.eval.eval.exp;

public class DivExpression extends Col2Expression
{
    public DivExpression() {
        this.setOperator("/");
    }
    
    protected DivExpression(final DivExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    @Override
    public AbstractExpression dup(final ShareExpValue s) {
        return new DivExpression(this, s);
    }
    
    @Override
    protected long operateLong(final long vl, final long vr) {
        return vl / vr;
    }
    
    @Override
    protected double operateDouble(final double vl, final double vr) {
        return vl / vr;
    }
    
    @Override
    protected Object operateObject(final Object vl, final Object vr) {
        return this.share.oper.div(vl, vr);
    }
}
