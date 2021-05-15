package mcheli.eval.eval.exp;

public class CommaExpression extends Col2OpeExpression
{
    public CommaExpression() {
        this.setOperator(",");
    }
    
    protected CommaExpression(final CommaExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    @Override
    public AbstractExpression dup(final ShareExpValue s) {
        return new CommaExpression(this, s);
    }
    
    @Override
    public long evalLong() {
        this.expl.evalLong();
        return this.expr.evalLong();
    }
    
    @Override
    public double evalDouble() {
        this.expl.evalDouble();
        return this.expr.evalDouble();
    }
    
    @Override
    public Object evalObject() {
        this.expl.evalObject();
        return this.expr.evalObject();
    }
    
    @Override
    protected String toStringLeftSpace() {
        return "";
    }
}
