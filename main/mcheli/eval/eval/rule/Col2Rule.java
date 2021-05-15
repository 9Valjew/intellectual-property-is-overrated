package mcheli.eval.eval.rule;

import mcheli.eval.eval.lex.*;
import mcheli.eval.eval.exp.*;

public class Col2Rule extends AbstractRule
{
    public Col2Rule(final ShareRuleValue share) {
        super(share);
    }
    
    @Override
    protected AbstractExpression parse(final Lex lex) {
        AbstractExpression x = this.nextRule.parse(lex);
        while (true) {
            switch (lex.getType()) {
                case 2147483634: {
                    final String ope = lex.getOperator();
                    if (this.isMyOperator(ope)) {
                        final int pos = lex.getPos();
                        final AbstractExpression y = this.nextRule.parse(lex.next());
                        x = Col2Expression.create(this.newExpression(ope, lex.getShare()), lex.getString(), pos, x, y);
                        continue;
                    }
                    return x;
                }
                default: {
                    return x;
                }
            }
        }
    }
}
