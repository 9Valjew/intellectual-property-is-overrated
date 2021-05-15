package mcheli.eval.eval.exp;

import mcheli.eval.eval.func.*;
import mcheli.eval.eval.var.*;
import mcheli.eval.eval.repl.*;
import mcheli.eval.eval.oper.*;
import mcheli.eval.eval.srch.*;
import mcheli.eval.eval.ref.*;
import mcheli.eval.eval.*;

public class ShareExpValue extends Expression
{
    public AbstractExpression paren;
    
    public void setAbstractExpression(final AbstractExpression ae) {
        this.ae = ae;
    }
    
    public void initVar() {
        if (this.var == null) {
            this.var = new MapVariable();
        }
    }
    
    public void initOper() {
        if (this.oper == null) {
            this.oper = new JavaExOperator();
        }
    }
    
    public void initFunc() {
        if (this.func == null) {
            this.func = new InvokeFunction();
        }
    }
    
    @Override
    public long evalLong() {
        this.initVar();
        this.initFunc();
        return this.ae.evalLong();
    }
    
    @Override
    public double evalDouble() {
        this.initVar();
        this.initFunc();
        return this.ae.evalDouble();
    }
    
    @Override
    public Object eval() {
        this.initVar();
        this.initOper();
        this.initFunc();
        return this.ae.evalObject();
    }
    
    @Override
    public void optimizeLong(final Variable var) {
        this.optimize(var, new OptimizeLong());
    }
    
    @Override
    public void optimizeDouble(final Variable var) {
        this.optimize(var, new OptimizeDouble());
    }
    
    @Override
    public void optimize(final Variable var, final Operator oper) {
        final Operator bak = this.oper;
        this.oper = oper;
        try {
            this.optimize(var, new OptimizeObject());
        }
        finally {
            this.oper = bak;
        }
    }
    
    protected void optimize(Variable var, final Replace repl) {
        final Variable bak = this.var;
        if (var == null) {
            var = new MapVariable();
        }
        this.var = var;
        this.repl = repl;
        try {
            this.ae = this.ae.replace();
        }
        finally {
            this.var = bak;
        }
    }
    
    @Override
    public void search(final Search srch) {
        if (srch == null) {
            throw new NullPointerException();
        }
        this.srch = srch;
        this.ae.search();
    }
    
    @Override
    public void refactorName(final Refactor ref) {
        if (ref == null) {
            throw new NullPointerException();
        }
        this.srch = new Search4RefactorName(ref);
        this.ae.search();
    }
    
    @Override
    public void refactorFunc(final Refactor ref, final Rule rule) {
        if (ref == null) {
            throw new NullPointerException();
        }
        this.repl = new Replace4RefactorGetter(ref, rule);
        this.ae.replace();
    }
    
    @Override
    public boolean same(final Expression obj) {
        if (obj instanceof ShareExpValue) {
            final AbstractExpression p = ((ShareExpValue)obj).paren;
            return this.paren.same(p) && super.same(obj);
        }
        return false;
    }
    
    @Override
    public Expression dup() {
        final ShareExpValue n = new ShareExpValue();
        n.ae = this.ae.dup(n);
        n.paren = this.paren.dup(n);
        return n;
    }
}
