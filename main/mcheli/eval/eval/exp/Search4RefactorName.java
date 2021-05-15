package mcheli.eval.eval.exp;

import mcheli.eval.eval.srch.*;
import mcheli.eval.eval.ref.*;
import mcheli.eval.eval.*;

public class Search4RefactorName extends SearchAdapter
{
    protected Refactor ref;
    
    Search4RefactorName(final Refactor ref) {
        this.ref = ref;
    }
    
    @Override
    public void search0(final WordExpression exp) {
        if (exp instanceof VariableExpression) {
            final String name = this.ref.getNewName(null, exp.getWord());
            if (name != null) {
                exp.setWord(name);
            }
        }
    }
    
    @Override
    public boolean search2_2(final Col2Expression exp) {
        if (!(exp instanceof FieldExpression)) {
            return false;
        }
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
        return true;
    }
    
    @Override
    public boolean searchFunc_2(final FunctionExpression exp) {
        Object obj = null;
        if (exp.target != null) {
            obj = exp.target.getVariable();
        }
        final String name = this.ref.getNewFuncName(obj, exp.name);
        if (name != null) {
            exp.name = name;
        }
        return false;
    }
}
