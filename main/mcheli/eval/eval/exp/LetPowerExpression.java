package mcheli.eval.eval.exp;

public class LetPowerExpression extends PowerExpression
{
    public LetPowerExpression() {
        this.setOperator("**=");
    }
    
    protected LetPowerExpression(final LetPowerExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    @Override
    public AbstractExpression dup(final ShareExpValue s) {
        return new LetPowerExpression(this, s);
    }
    
    @Override
    public long evalLong() {
        final long val = super.evalLong();
        this.expl.let(val, this.pos);
        return val;
    }
    
    @Override
    public double evalDouble() {
        final double val = super.evalDouble();
        this.expl.let(val, this.pos);
        return val;
    }
    
    @Override
    public Object evalObject() {
        final Object val = super.evalObject();
        this.expl.let(val, this.pos);
        return val;
    }
    
    @Override
    protected AbstractExpression replace() {
        this.expl = this.expl.replaceVar();
        this.expr = this.expr.replace();
        return this.share.repl.replaceLet(this);
    }
}
