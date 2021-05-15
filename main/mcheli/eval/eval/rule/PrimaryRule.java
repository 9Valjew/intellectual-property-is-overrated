package mcheli.eval.eval.rule;

import mcheli.eval.eval.lex.*;
import mcheli.eval.eval.*;
import mcheli.eval.eval.exp.*;

public class PrimaryRule extends AbstractRule
{
    public PrimaryRule(final ShareRuleValue share) {
        super(share);
    }
    
    public final AbstractExpression parse(final Lex lex) {
        switch (lex.getType()) {
            case 2147483633: {
                final AbstractExpression n = NumberExpression.create(lex, this.prio);
                lex.next();
                return n;
            }
            case 2147483632: {
                final AbstractExpression w = VariableExpression.create(lex, this.prio);
                lex.next();
                return w;
            }
            case 2147483635: {
                final AbstractExpression s = StringExpression.create(lex, this.prio);
                lex.next();
                return s;
            }
            case 2147483636: {
                final AbstractExpression c = CharExpression.create(lex, this.prio);
                lex.next();
                return c;
            }
            case 2147483634: {
                final String ope = lex.getOperator();
                final int pos = lex.getPos();
                if (!this.isMyOperator(ope)) {
                    throw new EvalException(1002, lex);
                }
                if (ope.equals(this.share.paren.getOperator())) {
                    return this.parseParen(lex, ope, pos);
                }
                return Col1Expression.create(this.newExpression(ope, lex.getShare()), lex.getString(), pos, this.parse(lex.next()));
            }
            case Integer.MAX_VALUE: {
                throw new EvalException(1004, lex);
            }
            default: {
                throw new EvalException(1003, lex);
            }
        }
    }
    
    protected AbstractExpression parseParen(final Lex lex, final String ope, final int pos) {
        final AbstractExpression s = this.share.topRule.parse(lex.next());
        if (!lex.isOperator(this.share.paren.getEndOperator())) {
            throw new EvalException(1001, new String[] { this.share.paren.getEndOperator() }, lex);
        }
        lex.next();
        return Col1Expression.create(this.newExpression(ope, lex.getShare()), lex.getString(), pos, s);
    }
}
