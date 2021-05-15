package mcheli.eval.eval.exp;

import java.util.*;

public class FuncArgExpression extends Col2OpeExpression
{
    public FuncArgExpression() {
        this.setOperator(",");
    }
    
    protected FuncArgExpression(final FuncArgExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    @Override
    public AbstractExpression dup(final ShareExpValue s) {
        return new FuncArgExpression(this, s);
    }
    
    @Override
    protected void evalArgsLong(final List args) {
        this.expl.evalArgsLong(args);
        this.expr.evalArgsLong(args);
    }
    
    @Override
    protected void evalArgsDouble(final List args) {
        this.expl.evalArgsDouble(args);
        this.expr.evalArgsDouble(args);
    }
    
    @Override
    protected void evalArgsObject(final List args) {
        this.expl.evalArgsObject(args);
        this.expr.evalArgsObject(args);
    }
    
    @Override
    protected String toStringLeftSpace() {
        return "";
    }
}
