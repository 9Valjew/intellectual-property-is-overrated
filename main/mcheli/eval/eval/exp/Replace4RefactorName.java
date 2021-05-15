package mcheli.eval.eval.exp;

import mcheli.eval.eval.repl.*;
import mcheli.eval.eval.ref.*;
import mcheli.eval.eval.*;

public class Replace4RefactorName extends ReplaceAdapter
{
    protected Refactor ref;
    
    Replace4RefactorName(final Refactor ref) {
        this.ref = ref;
    }
    
    protected void var(final VariableExpression exp) {
        final String name = this.ref.getNewName(null, exp.getWord());
        if (name != null) {
            exp.setWord(name);
        }
    }
    
    protected void field(final FieldExpression exp) {
        final AbstractExpression exp2 = exp.expl;
        final Object obj = exp2.getVariable();
        if (obj == null) {
            throw new EvalException(2104, this.toString(), exp2.string, exp2.pos, null);
        }
        final AbstractExpression exp3 = exp.expr;
        final String name = this.ref.getNewName(obj, exp3.getWord());
        if (name != null) {
            exp3.setWord(name);
        }
    }
    
    protected void func(final FunctionExpression exp) {
        Object obj = null;
        if (exp.target != null) {
            obj = exp.target.getVariable();
        }
        final String name = this.ref.getNewFuncName(obj, exp.name);
        if (name != null) {
            exp.name = name;
        }
    }
    
    @Override
    public AbstractExpression replace0(final WordExpression exp) {
        if (exp instanceof VariableExpression) {
            this.var((VariableExpression)exp);
        }
        return exp;
    }
    
    @Override
    public AbstractExpression replace2(final Col2Expression exp) {
        if (exp instanceof FieldExpression) {
            this.field((FieldExpression)exp);
        }
        return exp;
    }
    
    @Override
    public AbstractExpression replaceFunc(final FunctionExpression exp) {
        this.func(exp);
        return exp;
    }
    
    public AbstractExpression replaceVar(final AbstractExpression exp) {
        if (exp instanceof VariableExpression) {
            this.var((VariableExpression)exp);
        }
        else if (exp instanceof FieldExpression) {
            this.field((FieldExpression)exp);
        }
        else if (exp instanceof FunctionExpression) {
            this.func((FunctionExpression)exp);
        }
        return exp;
    }
}
