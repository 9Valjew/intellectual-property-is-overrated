package mcheli.eval.eval;

import mcheli.eval.eval.var.*;
import mcheli.eval.eval.func.*;
import mcheli.eval.eval.oper.*;
import mcheli.eval.eval.srch.*;
import mcheli.eval.eval.repl.*;
import mcheli.eval.eval.exp.*;
import mcheli.eval.eval.ref.*;

public abstract class Expression
{
    public Variable var;
    public Function func;
    public Operator oper;
    public Search srch;
    public Replace repl;
    protected AbstractExpression ae;
    
    public static Expression parse(final String str) {
        return ExpRuleFactory.getDefaultRule().parse(str);
    }
    
    public void setVariable(final Variable var) {
        this.var = var;
    }
    
    public void setFunction(final Function func) {
        this.func = func;
    }
    
    public void setOperator(final Operator oper) {
        this.oper = oper;
    }
    
    public abstract long evalLong();
    
    public abstract double evalDouble();
    
    public abstract Object eval();
    
    public abstract void optimizeLong(final Variable p0);
    
    public abstract void optimizeDouble(final Variable p0);
    
    public abstract void optimize(final Variable p0, final Operator p1);
    
    public abstract void search(final Search p0);
    
    public abstract void refactorName(final Refactor p0);
    
    public abstract void refactorFunc(final Refactor p0, final Rule p1);
    
    public abstract Expression dup();
    
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Expression) {
            final AbstractExpression e = ((Expression)obj).ae;
            return (this.ae == null && e == null) || (this.ae != null && e != null && this.ae.equals(e));
        }
        return super.equals(obj);
    }
    
    @Override
    public int hashCode() {
        if (this.ae == null) {
            return 0;
        }
        return this.ae.hashCode();
    }
    
    public boolean same(final Expression obj) {
        final AbstractExpression e = obj.ae;
        if (this.ae == null) {
            return e == null;
        }
        return this.ae.same(e);
    }
    
    public boolean isEmpty() {
        return this.ae == null;
    }
    
    @Override
    public String toString() {
        if (this.ae == null) {
            return "";
        }
        return this.ae.toString();
    }
}
