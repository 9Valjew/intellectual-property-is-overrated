package mcheli.eval.eval.exp;

public class IncAfterExpression extends Col1AfterExpression
{
    public IncAfterExpression() {
        this.setOperator("++");
    }
    
    protected IncAfterExpression(final IncAfterExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    @Override
    public AbstractExpression dup(final ShareExpValue s) {
        return new IncAfterExpression(this, s);
    }
    
    @Override
    protected long operateLong(final long val) {
        this.exp.let(val + 1L, this.pos);
        return val;
    }
    
    @Override
    protected double operateDouble(final double val) {
        this.exp.let(val + 1.0, this.pos);
        return val;
    }
    
    @Override
    public Object evalObject() {
        final Object val = this.exp.evalObject();
        this.exp.let(this.share.oper.inc(val, 1), this.pos);
        return val;
    }
}
