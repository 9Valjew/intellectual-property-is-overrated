package mcheli.eval.eval.exp;

public class SignPlusExpression extends Col1Expression
{
    public SignPlusExpression() {
        this.setOperator("+");
    }
    
    protected SignPlusExpression(final SignPlusExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    @Override
    public AbstractExpression dup(final ShareExpValue s) {
        return new SignPlusExpression(this, s);
    }
    
    @Override
    protected long operateLong(final long val) {
        return val;
    }
    
    @Override
    protected double operateDouble(final double val) {
        return val;
    }
    
    @Override
    public Object evalObject() {
        return this.share.oper.signPlus(this.exp.evalObject());
    }
}
