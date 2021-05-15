package mcheli.eval.eval.exp;

public class PowerExpression extends Col2Expression
{
    public PowerExpression() {
        this.setOperator("**");
    }
    
    protected PowerExpression(final PowerExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    @Override
    public AbstractExpression dup(final ShareExpValue s) {
        return new PowerExpression(this, s);
    }
    
    @Override
    protected long operateLong(final long vl, final long vr) {
        return (long)Math.pow(vl, vr);
    }
    
    @Override
    protected double operateDouble(final double vl, final double vr) {
        return Math.pow(vl, vr);
    }
    
    @Override
    protected Object operateObject(final Object vl, final Object vr) {
        return this.share.oper.power(vl, vr);
    }
}
