package mcheli.eval.eval.exp;

import mcheli.eval.eval.*;
import java.util.*;

public class FunctionExpression extends Col1Expression
{
    protected AbstractExpression target;
    String name;
    
    public static AbstractExpression create(AbstractExpression x, final AbstractExpression args, final int prio, final ShareExpValue share) {
        AbstractExpression obj;
        if (x instanceof VariableExpression) {
            obj = null;
        }
        else {
            if (!(x instanceof FieldExpression)) {
                throw new EvalException(1101, x.toString(), x.string, x.pos, null);
            }
            final FieldExpression f = (FieldExpression)x;
            obj = f.expl;
            x = f.expr;
        }
        final String name = x.getWord();
        final FunctionExpression f2 = new FunctionExpression(obj, name);
        f2.setExpression(args);
        f2.setPos(x.string, x.pos);
        f2.setPriority(prio);
        f2.share = share;
        return f2;
    }
    
    public FunctionExpression() {
        this.setOperator("(");
        this.setEndOperator(")");
    }
    
    public FunctionExpression(final AbstractExpression obj, final String word) {
        this();
        this.target = obj;
        this.name = word;
    }
    
    protected FunctionExpression(final FunctionExpression from, final ShareExpValue s) {
        super(from, s);
        if (from.target != null) {
            this.target = from.target.dup(s);
        }
        this.name = from.name;
    }
    
    @Override
    public AbstractExpression dup(final ShareExpValue s) {
        return new FunctionExpression(this, s);
    }
    
    @Override
    public long evalLong() {
        Object obj = null;
        if (this.target != null) {
            obj = this.target.getVariable();
        }
        final List args = this.evalArgsLong();
        try {
            final Long[] arr = new Long[args.size()];
            return this.share.func.evalLong(obj, this.name, args.toArray(arr));
        }
        catch (EvalException e) {
            throw e;
        }
        catch (Throwable e2) {
            throw new EvalException(2401, this.name, this.string, this.pos, e2);
        }
    }
    
    @Override
    public double evalDouble() {
        Object obj = null;
        if (this.target != null) {
            obj = this.target.getVariable();
        }
        final List args = this.evalArgsDouble();
        try {
            final Double[] arr = new Double[args.size()];
            return this.share.func.evalDouble(obj, this.name, args.toArray(arr));
        }
        catch (EvalException e) {
            throw e;
        }
        catch (Throwable e2) {
            throw new EvalException(2401, this.name, this.string, this.pos, e2);
        }
    }
    
    @Override
    public Object evalObject() {
        Object obj = null;
        if (this.target != null) {
            obj = this.target.getVariable();
        }
        final List args = this.evalArgsObject();
        try {
            final Object[] arr = new Object[args.size()];
            return this.share.func.evalObject(obj, this.name, args.toArray(arr));
        }
        catch (EvalException e) {
            throw e;
        }
        catch (Throwable e2) {
            throw new EvalException(2401, this.name, this.string, this.pos, e2);
        }
    }
    
    private List evalArgsLong() {
        final List args = new ArrayList();
        if (this.exp != null) {
            this.exp.evalArgsLong(args);
        }
        return args;
    }
    
    private List evalArgsDouble() {
        final List args = new ArrayList();
        if (this.exp != null) {
            this.exp.evalArgsDouble(args);
        }
        return args;
    }
    
    private List evalArgsObject() {
        final List args = new ArrayList();
        if (this.exp != null) {
            this.exp.evalArgsObject(args);
        }
        return args;
    }
    
    @Override
    protected Object getVariable() {
        return this.evalObject();
    }
    
    @Override
    protected long operateLong(final long val) {
        throw new RuntimeException("\u3053\u306e\u95a2\u6570\u304c\u547c\u3070\u308c\u3066\u306f\u3044\u3051\u306a\u3044\u3002\u30b5\u30d6\u30af\u30e9\u30b9\u3067\u5b9f\u88c5\u8981");
    }
    
    @Override
    protected double operateDouble(final double val) {
        throw new RuntimeException("\u3053\u306e\u95a2\u6570\u304c\u547c\u3070\u308c\u3066\u306f\u3044\u3051\u306a\u3044\u3002\u30b5\u30d6\u30af\u30e9\u30b9\u3067\u5b9f\u88c5\u8981");
    }
    
    @Override
    protected void search() {
        this.share.srch.search(this);
        if (this.share.srch.end()) {
            return;
        }
        if (this.share.srch.searchFunc_begin(this)) {
            return;
        }
        if (this.share.srch.end()) {
            return;
        }
        if (this.target != null) {
            this.target.search();
            if (this.share.srch.end()) {
                return;
            }
        }
        if (this.share.srch.searchFunc_2(this)) {
            return;
        }
        if (this.share.srch.end()) {
            return;
        }
        if (this.exp != null) {
            this.exp.search();
            if (this.share.srch.end()) {
                return;
            }
        }
        this.share.srch.searchFunc_end(this);
    }
    
    @Override
    protected AbstractExpression replace() {
        if (this.target != null) {
            this.target = this.target.replace();
        }
        if (this.exp != null) {
            this.exp = this.exp.replace();
        }
        return this.share.repl.replaceFunc(this);
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof FunctionExpression) {
            final FunctionExpression e = (FunctionExpression)obj;
            return this.name.equals(e.name) && equals(this.target, e.target) && equals(this.exp, e.exp);
        }
        return false;
    }
    
    private static boolean equals(final AbstractExpression e1, final AbstractExpression e2) {
        if (e1 == null) {
            return e2 == null;
        }
        return e2 != null && e1.equals(e2);
    }
    
    @Override
    public int hashCode() {
        final int t = (this.target != null) ? this.target.hashCode() : 0;
        final int a = (this.exp != null) ? this.exp.hashCode() : 0;
        return this.name.hashCode() ^ t ^ a * 2;
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        if (this.target != null) {
            sb.append(this.target.toString());
            sb.append('.');
        }
        sb.append(this.name);
        sb.append('(');
        if (this.exp != null) {
            sb.append(this.exp.toString());
        }
        sb.append(')');
        return sb.toString();
    }
}
