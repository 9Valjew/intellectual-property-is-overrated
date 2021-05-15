package mcheli.eval.eval.exp;

public abstract class Col2OpeExpression extends Col2Expression
{
    protected Col2OpeExpression() {
    }
    
    protected Col2OpeExpression(final Col2Expression from, final ShareExpValue s) {
        super(from, s);
    }
    
    @Override
    protected final long operateLong(final long vl, final long vr) {
        throw new RuntimeException("\u3053\u306e\u95a2\u6570\u304c\u547c\u3070\u308c\u3066\u306f\u3044\u3051\u306a\u3044");
    }
    
    @Override
    protected final double operateDouble(final double vl, final double vr) {
        throw new RuntimeException("\u3053\u306e\u95a2\u6570\u304c\u547c\u3070\u308c\u3066\u306f\u3044\u3051\u306a\u3044");
    }
    
    @Override
    protected final Object operateObject(final Object vl, final Object vr) {
        throw new RuntimeException("\u3053\u306e\u95a2\u6570\u304c\u547c\u3070\u308c\u3066\u306f\u3044\u3051\u306a\u3044");
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
}
