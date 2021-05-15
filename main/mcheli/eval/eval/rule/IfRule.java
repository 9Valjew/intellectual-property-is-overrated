package mcheli.eval.eval.rule;

import mcheli.eval.eval.lex.*;
import mcheli.eval.eval.*;
import mcheli.eval.eval.exp.*;

public class IfRule extends AbstractRule
{
    public AbstractExpression cond;
    
    public IfRule(final ShareRuleValue share) {
        super(share);
    }
    
    @Override
    protected AbstractExpression parse(final Lex lex) {
        AbstractExpression x = this.nextRule.parse(lex);
        switch (lex.getType()) {
            case 2147483634: {
                final String ope = lex.getOperator();
                final int pos = lex.getPos();
                if (this.isMyOperator(ope) && lex.isOperator(this.cond.getOperator())) {
                    x = this.parseCond(lex, x, ope, pos);
                }
                return x;
            }
            default: {
                return x;
            }
        }
    }
    
    protected AbstractExpression parseCond(final Lex lex, AbstractExpression x, final String ope, final int pos) {
        final AbstractExpression y = this.parse(lex.next());
        if (!lex.isOperator(this.cond.getEndOperator())) {
            throw new EvalException(1001, new String[] { this.cond.getEndOperator() }, lex);
        }
        final AbstractExpression z = this.parse(lex.next());
        x = Col3Expression.create(this.newExpression(ope, lex.getShare()), lex.getString(), pos, x, y, z);
        return x;
    }
}
