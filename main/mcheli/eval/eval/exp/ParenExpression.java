package mcheli.eval.eval.exp;

public class ParenExpression extends Col1Expression
{
    public ParenExpression() {
        this.setOperator("(");
        this.setEndOperator(")");
    }
    
    protected ParenExpression(final ParenExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    @Override
    public AbstractExpression dup(final ShareExpValue s) {
        return new ParenExpression(this, s);
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
        return this.exp.evalObject();
    }
    
    @Override
    public String toString() {
        if (this.exp == null) {
            return "";
        }
        return this.getOperator() + this.exp.toString() + this.getEndOperator();
    }
}
