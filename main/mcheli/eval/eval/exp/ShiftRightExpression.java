package mcheli.eval.eval.exp;

public class ShiftRightExpression extends Col2Expression
{
    public ShiftRightExpression() {
        this.setOperator(">>");
    }
    
    protected ShiftRightExpression(final ShiftRightExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    @Override
    public AbstractExpression dup(final ShareExpValue s) {
        return new ShiftRightExpression(this, s);
    }
    
    @Override
    protected long operateLong(final long vl, final long vr) {
        return vl >> (int)vr;
    }
    
    @Override
    protected double operateDouble(final double vl, final double vr) {
        return vl / Math.pow(2.0, vr);
    }
    
    @Override
    protected Object operateObject(final Object vl, final Object vr) {
        return this.share.oper.shiftRight(vl, vr);
    }
}
