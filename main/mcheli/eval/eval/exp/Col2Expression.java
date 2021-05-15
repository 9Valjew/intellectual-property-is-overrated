package mcheli.eval.eval.exp;

public abstract class Col2Expression extends AbstractExpression
{
    public AbstractExpression expl;
    public AbstractExpression expr;
    
    public static AbstractExpression create(final AbstractExpression exp, final String string, final int pos, final AbstractExpression x, final AbstractExpression y) {
        final Col2Expression n = (Col2Expression)exp;
        n.setExpression(x, y);
        n.setPos(string, pos);
        return n;
    }
    
    protected Col2Expression() {
    }
    
    protected Col2Expression(final Col2Expression from, final ShareExpValue s) {
        super(from, s);
        if (from.expl != null) {
            this.expl = from.expl.dup(s);
        }
        if (from.expr != null) {
            this.expr = from.expr.dup(s);
        }
    }
    
    public final void setExpression(final AbstractExpression x, final AbstractExpression y) {
        this.expl = x;
        this.expr = y;
    }
    
    @Override
    protected final int getCols() {
        return 2;
    }
    
    @Override
    protected final int getFirstPos() {
        return this.expl.getFirstPos();
    }
    
    @Override
    public long evalLong() {
        return this.operateLong(this.expl.evalLong(), this.expr.evalLong());
    }
    
    @Override
    public double evalDouble() {
        return this.operateDouble(this.expl.evalDouble(), this.expr.evalDouble());
    }
    
    @Override
    public Object evalObject() {
        return this.operateObject(this.expl.evalObject(), this.expr.evalObject());
    }
    
    protected abstract long operateLong(final long p0, final long p1);
    
    protected abstract double operateDouble(final double p0, final double p1);
    
    protected abstract Object operateObject(final Object p0, final Object p1);
    
    @Override
    protected void search() {
        this.share.srch.search(this);
        if (this.share.srch.end()) {
            return;
        }
        if (this.share.srch.search2_begin(this)) {
            return;
        }
        if (this.share.srch.end()) {
            return;
        }
        this.expl.search();
        if (this.share.srch.end()) {
            return;
        }
        if (this.share.srch.search2_2(this)) {
            return;
        }
        if (this.share.srch.end()) {
            return;
        }
        this.expr.search();
        if (this.share.srch.end()) {
            return;
        }
        this.share.srch.search2_end(this);
    }
    
    @Override
    protected AbstractExpression replace() {
        this.expl = this.expl.replace();
        this.expr = this.expr.replace();
        return this.share.repl.replace2(this);
    }
    
    @Override
    protected AbstractExpression replaceVar() {
        this.expl = this.expl.replaceVar();
        this.expr = this.expr.replaceVar();
        return this.share.repl.replaceVar2(this);
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Col2Expression) {
            final Col2Expression e = (Col2Expression)obj;
            if (this.getClass() == e.getClass()) {
                return this.expl.equals(e.expl) && this.expr.equals(e.expr);
            }
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.getClass().hashCode() ^ this.expl.hashCode() ^ this.expr.hashCode() * 2;
    }
    
    @Override
    public void dump(final int n) {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; ++i) {
            sb.append(' ');
        }
        sb.append(this.getOperator());
        System.out.println(sb.toString());
        this.expl.dump(n + 1);
        this.expr.dump(n + 1);
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        if (this.expl.getPriority() < this.prio) {
            sb.append(this.share.paren.getOperator());
            sb.append(this.expl.toString());
            sb.append(this.share.paren.getEndOperator());
        }
        else {
            sb.append(this.expl.toString());
        }
        sb.append(this.toStringLeftSpace());
        sb.append(this.getOperator());
        sb.append(' ');
        if (this.expr.getPriority() < this.prio) {
            sb.append(this.share.paren.getOperator());
            sb.append(this.expr.toString());
            sb.append(this.share.paren.getEndOperator());
        }
        else {
            sb.append(this.expr.toString());
        }
        return sb.toString();
    }
    
    protected String toStringLeftSpace() {
        return " ";
    }
}
