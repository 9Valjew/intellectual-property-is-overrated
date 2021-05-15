package mcheli.eval.eval.exp;

public class ShiftLeftExpression extends Col2Expression
{
    public ShiftLeftExpression() {
        this.setOperator("<<");
    }
    
    protected ShiftLeftExpression(final ShiftLeftExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    @Override
    public AbstractExpression dup(final ShareExpValue s) {
        return new ShiftLeftExpression(this, s);
    }
    
    @Override
    protected long operateLong(final long vl, final long vr) {
        return vl << (int)vr;
    }
    
    @Override
    protected double operateDouble(final double vl, final double vr) {
        return vl * Math.pow(2.0, vr);
    }
    
    @Override
    protected Object operateObject(final Object vl, final Object vr) {
        return this.share.oper.shiftLeft(vl, vr);
    }
}
