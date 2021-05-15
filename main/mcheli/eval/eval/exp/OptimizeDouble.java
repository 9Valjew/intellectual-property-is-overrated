package mcheli.eval.eval.exp;

public class OptimizeDouble extends OptimizeObject
{
    @Override
    protected boolean isTrue(final AbstractExpression x) {
        return x.evalDouble() != 0.0;
    }
    
    @Override
    protected AbstractExpression toConst(final AbstractExpression exp) {
        try {
            final double val = exp.evalDouble();
            return NumberExpression.create(exp, Double.toString(val));
        }
        catch (Exception e) {
            return exp;
        }
    }
}
