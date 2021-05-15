package mcheli.eval.eval.rule;

import java.util.*;
import mcheli.eval.eval.exp.*;
import mcheli.eval.eval.*;
import mcheli.eval.eval.lex.*;
import mcheli.eval.eval.var.*;
import mcheli.eval.eval.oper.*;
import mcheli.eval.eval.srch.*;
import mcheli.eval.eval.ref.*;

public class ShareRuleValue extends Rule
{
    public AbstractRule topRule;
    public AbstractRule funcArgRule;
    public LexFactory lexFactory;
    protected List[] opeList;
    public AbstractExpression paren;
    
    public ShareRuleValue() {
        this.opeList = new List[4];
    }
    
    @Override
    public Expression parse(final String str) {
        if (str == null) {
            return null;
        }
        if (str.trim().length() <= 0) {
            return new EmptyExpression();
        }
        final ShareExpValue exp = new ShareExpValue();
        final AbstractExpression x = this.parse(str, exp);
        exp.setAbstractExpression(x);
        return exp;
    }
    
    public AbstractExpression parse(final String str, final ShareExpValue exp) {
        if (str == null) {
            return null;
        }
        final Lex lex = this.lexFactory.create(str, this.opeList, this, exp);
        lex.check();
        final AbstractExpression x = this.topRule.parse(lex);
        if (lex.getType() != Integer.MAX_VALUE) {
            throw new EvalException(1005, lex);
        }
        return x;
    }
    
    class EmptyExpression extends Expression
    {
        @Override
        public long evalLong() {
            return 0L;
        }
        
        @Override
        public double evalDouble() {
            return 0.0;
        }
        
        @Override
        public Object eval() {
            return null;
        }
        
        @Override
        public void optimizeLong(final Variable var) {
        }
        
        @Override
        public void optimizeDouble(final Variable var) {
        }
        
        @Override
        public void optimize(final Variable var, final Operator oper) {
        }
        
        @Override
        public void search(final Search srch) {
        }
        
        @Override
        public void refactorName(final Refactor ref) {
        }
        
        @Override
        public void refactorFunc(final Refactor ref, final Rule rule) {
        }
        
        @Override
        public Expression dup() {
            return new EmptyExpression();
        }
        
        @Override
        public boolean same(final Expression obj) {
            return obj instanceof EmptyExpression;
        }
        
        @Override
        public String toString() {
            return "";
        }
    }
}
