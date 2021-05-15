package mcheli.eval.eval.exp;

public class ShiftRightLogicalExpression extends Col2Expression
{
    public ShiftRightLogicalExpression() {
        this.setOperator(">>>");
    }
    
    protected ShiftRightLogicalExpression(final ShiftRightLogicalExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    @Override
    public AbstractExpression dup(final ShareExpValue s) {
        return new ShiftRightLogicalExpression(this, s);
    }
    
    @Override
    protected long operateLong(final long vl, final long vr) {
        return vl >>> (int)vr;
    }
    
    @Override
    protected double operateDouble(double vl, final double vr) {
        if (vl < 0.0) {
            vl = -vl;
        }
        return vl / Math.pow(2.0, vr);
    }
    
    @Override
    protected Object operateObject(final Object vl, final Object vr) {
        return this.share.oper.shiftRightLogical(vl, vr);
    }
}
