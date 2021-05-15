package mcheli.eval.eval.exp;

import mcheli.eval.eval.*;
import java.util.*;

public abstract class AbstractExpression
{
    public static final int TRUE = 1;
    public static final int FALSE = 0;
    protected String string;
    protected int pos;
    private String ope1;
    private String ope2;
    public ShareExpValue share;
    protected int prio;
    
    protected final boolean isTrue(final boolean lng) {
        if (lng) {
            return this.evalLong() != 0L;
        }
        return this.evalDouble() != 0.0;
    }
    
    protected AbstractExpression() {
        this.string = null;
        this.pos = -1;
    }
    
    protected AbstractExpression(final AbstractExpression from, final ShareExpValue s) {
        this.string = null;
        this.pos = -1;
        this.string = from.string;
        this.pos = from.pos;
        this.prio = from.prio;
        if (s != null) {
            this.share = s;
        }
        else {
            this.share = from.share;
        }
        this.ope1 = from.ope1;
        this.ope2 = from.ope2;
    }
    
    public abstract AbstractExpression dup(final ShareExpValue p0);
    
    public final String getOperator() {
        return this.ope1;
    }
    
    public final String getEndOperator() {
        return this.ope2;
    }
    
    public final void setOperator(final String ope) {
        this.ope1 = ope;
    }
    
    public final void setEndOperator(final String ope) {
        this.ope2 = ope;
    }
    
    protected String getWord() {
        return this.getOperator();
    }
    
    protected void setWord(final String word) {
        throw new EvalException(2001, word, this.string, this.pos, null);
    }
    
    protected abstract int getCols();
    
    protected final void setPos(final String string, final int pos) {
        this.string = string;
        this.pos = pos;
    }
    
    protected abstract int getFirstPos();
    
    public final void setPriority(final int prio) {
        this.prio = prio;
    }
    
    protected final int getPriority() {
        return this.prio;
    }
    
    protected void let(final Object val, final int pos) {
        throw new EvalException(2004, this.toString(), this.string, pos, null);
    }
    
    protected void let(final long val, final int pos) {
        this.let(new Long(val), pos);
    }
    
    protected void let(final double val, final int pos) {
        this.let(new Double(val), pos);
    }
    
    protected Object getVariable() {
        final String word = this.toString();
        throw new EvalException(2002, word, this.string, this.pos, null);
    }
    
    protected void evalArgsLong(final List args) {
        args.add(new Long(this.evalLong()));
    }
    
    protected void evalArgsDouble(final List args) {
        args.add(new Double(this.evalDouble()));
    }
    
    protected void evalArgsObject(final List args) {
        args.add(this.evalObject());
    }
    
    public abstract long evalLong();
    
    public abstract double evalDouble();
    
    public abstract Object evalObject();
    
    protected abstract void search();
    
    protected abstract AbstractExpression replace();
    
    protected abstract AbstractExpression replaceVar();
    
    @Override
    public abstract boolean equals(final Object p0);
    
    @Override
    public abstract int hashCode();
    
    public boolean same(final AbstractExpression exp) {
        return same(this.getOperator(), exp.getOperator()) && same(this.getEndOperator(), exp.getEndOperator()) && this.equals(exp);
    }
    
    private static boolean same(final String str1, final String str2) {
        if (str1 == null) {
            return str2 == null;
        }
        return str1.equals(str2);
    }
    
    public abstract void dump(final int p0);
    
    @Override
    public abstract String toString();
}
