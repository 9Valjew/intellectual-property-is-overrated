package mcheli.eval.eval.exp;

public class SignMinusExpression extends Col1Expression
{
    public SignMinusExpression() {
        this.setOperator("-");
    }
    
    protected SignMinusExpression(final SignMinusExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    @Override
    public AbstractExpression dup(final ShareExpValue s) {
        return new SignMinusExpression(this, s);
    }
    
    @Override
    protected long operateLong(final long val) {
        return -val;
    }
    
    @Override
    protected double operateDouble(final double val) {
        return -val;
    }
    
    @Override
    public Object evalObject() {
        return this.share.oper.signMinus(this.exp.evalObject());
    }
}
