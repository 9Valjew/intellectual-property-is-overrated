package mcheli.eval.eval.exp;

import mcheli.eval.eval.repl.*;
import mcheli.eval.eval.ref.*;
import mcheli.eval.eval.rule.*;
import mcheli.eval.eval.*;

public class Replace4RefactorGetter extends ReplaceAdapter
{
    protected Refactor ref;
    protected ShareRuleValue rule;
    
    Replace4RefactorGetter(final Refactor ref, final Rule rule) {
        this.ref = ref;
        this.rule = (ShareRuleValue)rule;
    }
    
    protected AbstractExpression var(final VariableExpression exp) {
        final String name = this.ref.getNewName(null, exp.getWord());
        if (name == null) {
            return exp;
        }
        return this.rule.parse(name, exp.share);
    }
    
    protected AbstractExpression field(final FieldExpression exp) {
        final AbstractExpression exp2 = exp.expl;
        final Object obj = exp2.getVariable();
        if (obj == null) {
            return exp;
        }
        final AbstractExpression exp3 = exp.expr;
        final String name = this.ref.getNewName(obj, exp3.getWord());
        if (name == null) {
            return exp;
        }
        exp.expr = this.rule.parse(name, exp3.share);
        return exp;
    }
    
    @Override
    public AbstractExpression replace0(final WordExpression exp) {
        if (exp instanceof VariableExpression) {
            return this.var((VariableExpression)exp);
        }
        return exp;
    }
    
    @Override
    public AbstractExpression replace2(final Col2OpeExpression exp) {
        if (exp instanceof FieldExpression) {
            return this.field((FieldExpression)exp);
        }
        return exp;
    }
}
