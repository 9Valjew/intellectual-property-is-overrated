package mcheli.eval.eval.exp;

public abstract class Col3Expression extends AbstractExpression
{
    protected AbstractExpression exp1;
    protected AbstractExpression exp2;
    protected AbstractExpression exp3;
    
    public static AbstractExpression create(final AbstractExpression exp, final String string, final int pos, final AbstractExpression x, final AbstractExpression y, final AbstractExpression z) {
        final Col3Expression n = (Col3Expression)exp;
        n.setExpression(x, y, z);
        n.setPos(string, pos);
        return n;
    }
    
    protected Col3Expression() {
    }
    
    protected Col3Expression(final Col3Expression from, final ShareExpValue s) {
        super(from, s);
        if (from.exp1 != null) {
            this.exp1 = from.exp1.dup(s);
        }
        if (from.exp2 != null) {
            this.exp2 = from.exp2.dup(s);
        }
        if (from.exp3 != null) {
            this.exp3 = from.exp3.dup(s);
        }
    }
    
    public final void setExpression(final AbstractExpression x, final AbstractExpression y, final AbstractExpression z) {
        this.exp1 = x;
        this.exp2 = y;
        this.exp3 = z;
    }
    
    @Override
    protected final int getCols() {
        return 3;
    }
    
    @Override
    protected int getFirstPos() {
        return this.exp1.getFirstPos();
    }
    
    @Override
    protected void search() {
        this.share.srch.search(this);
        if (this.share.srch.end()) {
            return;
        }
        if (this.share.srch.search3_begin(this)) {
            return;
        }
        if (this.share.srch.end()) {
            return;
        }
        this.exp1.search();
        if (this.share.srch.end()) {
            return;
        }
        if (this.share.srch.search3_2(this)) {
            return;
        }
        if (this.share.srch.end()) {
            return;
        }
        this.exp2.search();
        if (this.share.srch.end()) {
            return;
        }
        if (this.share.srch.search3_3(this)) {
            return;
        }
        if (this.share.srch.end()) {
            return;
        }
        this.exp3.search();
        if (this.share.srch.end()) {
            return;
        }
        this.share.srch.search3_end(this);
    }
    
    @Override
    protected AbstractExpression replace() {
        this.exp1 = this.exp1.replace();
        this.exp2 = this.exp2.replace();
        this.exp3 = this.exp3.replace();
        return this.share.repl.replace3(this);
    }
    
    @Override
    protected AbstractExpression replaceVar() {
        this.exp1 = this.exp1.replace();
        this.exp2 = this.exp2.replaceVar();
        this.exp3 = this.exp3.replaceVar();
        return this.share.repl.replaceVar3(this);
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Col3Expression) {
            final Col3Expression e = (Col3Expression)obj;
            if (this.getClass() == e.getClass()) {
                return this.exp1.equals(e.exp1) && this.exp2.equals(e.exp2) && this.exp3.equals(e.exp3);
            }
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.getClass().hashCode() ^ this.exp1.hashCode() ^ this.exp2.hashCode() * 2 ^ this.exp3.hashCode() * 3;
    }
    
    @Override
    public void dump(final int n) {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; ++i) {
            sb.append(' ');
        }
        sb.append(this.getOperator());
        System.out.println(sb.toString());
        this.exp1.dump(n + 1);
        this.exp2.dump(n + 1);
        this.exp3.dump(n + 1);
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        if (this.exp1.getPriority() <= this.prio || this.exp1.getCols() >= 2) {
            sb.append(this.share.paren.getOperator());
            sb.append(this.exp1.toString());
            sb.append(this.share.paren.getEndOperator());
        }
        else {
            sb.append(this.exp1.toString());
        }
        sb.append(' ');
        sb.append(this.getOperator());
        sb.append(' ');
        if (this.exp2.getPriority() <= this.prio || this.exp2.getCols() >= 2) {
            sb.append(this.share.paren.getOperator());
            sb.append(this.exp2.toString());
            sb.append(this.share.paren.getEndOperator());
        }
        else {
            sb.append(this.exp2.toString());
        }
        sb.append(' ');
        sb.append(this.getEndOperator());
        sb.append(' ');
        if (this.exp3.getPriority() <= this.prio || this.exp3.getCols() >= 2) {
            sb.append(this.share.paren.getOperator());
            sb.append(this.exp3.toString());
            sb.append(this.share.paren.getEndOperator());
        }
        else {
            sb.append(this.exp3.toString());
        }
        return sb.toString();
    }
}
