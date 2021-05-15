package mcheli.eval.eval.exp;

public abstract class Col1AfterExpression extends Col1Expression
{
    protected Col1AfterExpression() {
    }
    
    protected Col1AfterExpression(final Col1Expression from, final ShareExpValue s) {
        super(from, s);
    }
    
    @Override
    protected AbstractExpression replace() {
        this.exp = this.exp.replaceVar();
        return this.share.repl.replaceVar1(this);
    }
    
    @Override
    protected AbstractExpression replaceVar() {
        return this.replace();
    }
    
    @Override
    public String toString() {
        if (this.exp == null) {
            return this.getOperator();
        }
        final StringBuffer sb = new StringBuffer();
        if (this.exp.getPriority() > this.prio) {
            sb.append(this.exp.toString());
            sb.append(this.getOperator());
        }
        else if (this.exp.getPriority() == this.prio) {
            sb.append(this.exp.toString());
            sb.append(' ');
            sb.append(this.getOperator());
        }
        else {
            sb.append(this.share.paren.getOperator());
            sb.append(this.exp.toString());
            sb.append(this.share.paren.getEndOperator());
            sb.append(this.getOperator());
        }
        return sb.toString();
    }
}
