package mcheli.eval.eval.rule;

import mcheli.eval.eval.lex.*;
import mcheli.eval.eval.exp.*;

public class SignRule extends AbstractRule
{
    public SignRule(final ShareRuleValue share) {
        super(share);
    }
    
    public AbstractExpression parse(final Lex lex) {
        switch (lex.getType()) {
            case 2147483634: {
                final String ope = lex.getOperator();
                if (this.isMyOperator(ope)) {
                    final int pos = lex.getPos();
                    return Col1Expression.create(this.newExpression(ope, lex.getShare()), lex.getString(), pos, this.parse(lex.next()));
                }
                return this.nextRule.parse(lex);
            }
            default: {
                return this.nextRule.parse(lex);
            }
        }
    }
}
