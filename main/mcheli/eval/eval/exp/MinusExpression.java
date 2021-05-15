package mcheli.eval.eval.exp;

public class MinusExpression extends Col2Expression
{
    public MinusExpression() {
        this.setOperator("-");
    }
    
    protected MinusExpression(final MinusExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    @Override
    public AbstractExpression dup(final ShareExpValue s) {
        return new MinusExpression(this, s);
    }
    
    @Override
    protected long operateLong(final long vl, final long vr) {
        return vl - vr;
    }
    
    @Override
    protected double operateDouble(final double vl, final double vr) {
        return vl - vr;
    }
    
    @Override
    protected Object operateObject(final Object vl, final Object vr) {
        return this.share.oper.minus(vl, vr);
    }
}
