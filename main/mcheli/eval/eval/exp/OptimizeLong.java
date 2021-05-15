package mcheli.eval.eval.exp;

public class OptimizeLong extends OptimizeObject
{
    @Override
    protected boolean isTrue(final AbstractExpression x) {
        return x.evalLong() != 0L;
    }
    
    @Override
    protected AbstractExpression toConst(final AbstractExpression exp) {
        try {
            final long val = exp.evalLong();
            return NumberExpression.create(exp, Long.toString(val));
        }
        catch (Exception e) {
            return exp;
        }
    }
}
