package mcheli.eval.eval.exp;

public class OrExpression extends Col2OpeExpression
{
    public OrExpression() {
        this.setOperator("||");
    }
    
    protected OrExpression(final OrExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    @Override
    public AbstractExpression dup(final ShareExpValue s) {
        return new OrExpression(this, s);
    }
    
    @Override
    public long evalLong() {
        final long val = this.expl.evalLong();
        if (val != 0L) {
            return val;
        }
        return this.expr.evalLong();
    }
    
    @Override
    public double evalDouble() {
        final double val = this.expl.evalDouble();
        if (val != 0.0) {
            return val;
        }
        return this.expr.evalDouble();
    }
    
    @Override
    public Object evalObject() {
        final Object val = this.expl.evalObject();
        if (this.share.oper.bool(val)) {
            return val;
        }
        return this.expr.evalObject();
    }
}
