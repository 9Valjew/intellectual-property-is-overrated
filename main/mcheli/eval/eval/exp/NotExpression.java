package mcheli.eval.eval.exp;

public class NotExpression extends Col1Expression
{
    public NotExpression() {
        this.setOperator("!");
    }
    
    protected NotExpression(final NotExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    @Override
    public AbstractExpression dup(final ShareExpValue s) {
        return new NotExpression(this, s);
    }
    
    @Override
    protected long operateLong(final long val) {
        return (val == 0L) ? 1 : 0;
    }
    
    @Override
    protected double operateDouble(final double val) {
        return (val == 0.0) ? 1.0 : 0.0;
    }
    
    @Override
    public Object evalObject() {
        return this.share.oper.not(this.exp.evalObject());
    }
}
