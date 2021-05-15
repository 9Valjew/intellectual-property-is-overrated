package mcheli.eval.eval.exp;

public class BitNotExpression extends Col1Expression
{
    public BitNotExpression() {
        this.setOperator("~");
    }
    
    protected BitNotExpression(final BitNotExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    @Override
    public AbstractExpression dup(final ShareExpValue s) {
        return new BitNotExpression(this, s);
    }
    
    @Override
    protected long operateLong(final long val) {
        return ~val;
    }
    
    @Override
    protected double operateDouble(final double val) {
        return ~(long)val;
    }
    
    @Override
    public Object evalObject() {
        return this.share.oper.bitNot(this.exp.evalObject());
    }
}
