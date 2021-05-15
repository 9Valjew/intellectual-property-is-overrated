package mcheli.eval.eval.exp;

import mcheli.eval.eval.*;

public class FieldExpression extends Col2OpeExpression
{
    public FieldExpression() {
        this.setOperator(".");
    }
    
    protected FieldExpression(final FieldExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    @Override
    public AbstractExpression dup(final ShareExpValue s) {
        return new FieldExpression(this, s);
    }
    
    @Override
    public long evalLong() {
        try {
            return this.share.var.evalLong(this.getVariable());
        }
        catch (EvalException e) {
            throw e;
        }
        catch (Exception e2) {
            throw new EvalException(2003, this.toString(), this.string, this.pos, e2);
        }
    }
    
    @Override
    public double evalDouble() {
        try {
            return this.share.var.evalDouble(this.getVariable());
        }
        catch (EvalException e) {
            throw e;
        }
        catch (Exception e2) {
            throw new EvalException(2003, this.toString(), this.string, this.pos, e2);
        }
    }
    
    @Override
    public Object evalObject() {
        return this.getVariable();
    }
    
    @Override
    protected Object getVariable() {
        final Object obj = this.expl.getVariable();
        if (obj == null) {
            throw new EvalException(2104, this.expl.toString(), this.string, this.pos, null);
        }
        final String word = this.expr.getWord();
        try {
            return this.share.var.getObject(obj, word);
        }
        catch (EvalException e) {
            throw e;
        }
        catch (Exception e2) {
            throw new EvalException(2301, this.toString(), this.string, this.pos, e2);
        }
    }
    
    @Override
    protected void let(final Object val, final int pos) {
        final Object obj = this.expl.getVariable();
        if (obj == null) {
            throw new EvalException(2104, this.expl.toString(), this.string, pos, null);
        }
        final String word = this.expr.getWord();
        try {
            this.share.var.setValue(obj, word, val);
        }
        catch (EvalException e) {
            throw e;
        }
        catch (Exception e2) {
            throw new EvalException(2302, this.toString(), this.string, pos, e2);
        }
    }
    
    @Override
    protected AbstractExpression replace() {
        this.expl = this.expl.replaceVar();
        return this.share.repl.replace2(this);
    }
    
    @Override
    protected AbstractExpression replaceVar() {
        this.expl = this.expl.replaceVar();
        return this.share.repl.replaceVar2(this);
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(this.expl.toString());
        sb.append('.');
        sb.append(this.expr.toString());
        return sb.toString();
    }
}
