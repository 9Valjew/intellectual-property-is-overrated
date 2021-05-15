package mcheli.eval.eval.exp;

import mcheli.eval.eval.repl.*;

public class OptimizeObject extends ReplaceAdapter
{
    protected boolean isConst(final AbstractExpression x) {
        return x instanceof NumberExpression || x instanceof StringExpression || x instanceof CharExpression;
    }
    
    protected boolean isTrue(final AbstractExpression x) {
        return x.evalLong() != 0L;
    }
    
    protected AbstractExpression toConst(final AbstractExpression exp) {
        try {
            final Object val = exp.evalObject();
            if (val instanceof String) {
                return StringExpression.create(exp, (String)val);
            }
            if (val instanceof Character) {
                return CharExpression.create(exp, val.toString());
            }
            return NumberExpression.create(exp, val.toString());
        }
        catch (Exception e) {
            return exp;
        }
    }
    
    @Override
    public AbstractExpression replace0(final WordExpression exp) {
        if (exp instanceof VariableExpression) {
            return this.toConst(exp);
        }
        return exp;
    }
    
    @Override
    public AbstractExpression replace1(final Col1Expression exp) {
        if (exp instanceof ParenExpression) {
            return exp.exp;
        }
        if (exp instanceof SignPlusExpression) {
            return exp.exp;
        }
        if (this.isConst(exp.exp)) {
            return this.toConst(exp);
        }
        return exp;
    }
    
    @Override
    public AbstractExpression replace2(final Col2Expression exp) {
        final boolean const_l = this.isConst(exp.expl);
        final boolean const_r = this.isConst(exp.expr);
        if (const_l && const_r) {
            return this.toConst(exp);
        }
        return exp;
    }
    
    @Override
    public AbstractExpression replace2(final Col2OpeExpression exp) {
        if (exp instanceof ArrayExpression) {
            if (this.isConst(exp.expr)) {
                return this.toConst(exp);
            }
            return exp;
        }
        else {
            if (exp instanceof FieldExpression) {
                return this.toConst(exp);
            }
            final boolean const_l = this.isConst(exp.expl);
            if (exp instanceof AndExpression) {
                if (!const_l) {
                    return exp;
                }
                if (this.isTrue(exp.expl)) {
                    return exp.expr;
                }
                return exp.expl;
            }
            else if (exp instanceof OrExpression) {
                if (!const_l) {
                    return exp;
                }
                if (this.isTrue(exp.expl)) {
                    return exp.expl;
                }
                return exp.expr;
            }
            else {
                if (!(exp instanceof CommaExpression)) {
                    return exp;
                }
                if (const_l) {
                    return exp.expr;
                }
                return exp;
            }
        }
    }
    
    @Override
    public AbstractExpression replace3(final Col3Expression exp) {
        if (!this.isConst(exp.exp1)) {
            return exp;
        }
        if (this.isTrue(exp.exp1)) {
            return exp.exp2;
        }
        return exp.exp3;
    }
}
