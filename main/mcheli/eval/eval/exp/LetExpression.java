package mcheli.eval.eval.exp;

public class LetExpression extends Col2OpeExpression
{
    public LetExpression() {
        this.setOperator("=");
    }
    
    protected LetExpression(final LetExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    @Override
    public AbstractExpression dup(final ShareExpValue s) {
        return new LetExpression(this, s);
    }
    
    @Override
    public long evalLong() {
        final long val = this.expr.evalLong();
        this.expl.let(val, this.pos);
        return val;
    }
    
    @Override
    public double evalDouble() {
        final double val = this.expr.evalDouble();
        this.expl.let(val, this.pos);
        return val;
    }
    
    @Override
    public Object evalObject() {
        final Object val = this.expr.evalObject();
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
