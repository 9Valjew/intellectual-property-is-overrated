package mcheli.eval.eval.exp;

public class LetModExpression extends ModExpression
{
    public LetModExpression() {
        this.setOperator("%=");
    }
    
    protected LetModExpression(final LetModExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    @Override
    public AbstractExpression dup(final ShareExpValue s) {
        return new LetModExpression(this, s);
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
