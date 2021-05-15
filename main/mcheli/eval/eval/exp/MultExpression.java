package mcheli.eval.eval.exp;

public class MultExpression extends Col2Expression
{
    public MultExpression() {
        this.setOperator("*");
    }
    
    protected MultExpression(final MultExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    @Override
    public AbstractExpression dup(final ShareExpValue s) {
        return new MultExpression(this, s);
    }
    
    @Override
    protected long operateLong(final long vl, final long vr) {
        return vl * vr;
    }
    
    @Override
    protected double operateDouble(final double vl, final double vr) {
        return vl * vr;
    }
    
    @Override
    protected Object operateObject(final Object vl, final Object vr) {
        return this.share.oper.mult(vl, vr);
    }
}
