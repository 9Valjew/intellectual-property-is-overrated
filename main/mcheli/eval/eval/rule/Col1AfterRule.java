package mcheli.eval.eval.rule;

import mcheli.eval.eval.lex.*;
import mcheli.eval.eval.*;
import mcheli.eval.eval.exp.*;

public class Col1AfterRule extends AbstractRule
{
    public AbstractExpression func;
    public AbstractExpression array;
    public AbstractExpression field;
    
    public Col1AfterRule(final ShareRuleValue share) {
        super(share);
    }
    
    public AbstractExpression parse(final Lex lex) {
        AbstractExpression x = this.nextRule.parse(lex);
        while (true) {
            switch (lex.getType()) {
                case 2147483634: {
                    final String ope = lex.getOperator();
                    final int pos = lex.getPos();
                    if (!this.isMyOperator(ope)) {
                        return x;
                    }
                    if (lex.isOperator(this.func.getOperator())) {
                        x = this.parseFunc(lex, x);
                        continue;
                    }
                    if (lex.isOperator(this.array.getOperator())) {
                        x = this.parseArray(lex, x, ope, pos);
                        continue;
                    }
                    if (lex.isOperator(this.field.getOperator())) {
                        x = this.parseField(lex, x, ope, pos);
                        continue;
                    }
                    x = Col1Expression.create(this.newExpression(ope, lex.getShare()), lex.getString(), pos, x);
                    lex.next();
                    continue;
                }
                default: {
                    return x;
                }
            }
        }
    }
    
    protected AbstractExpression parseFunc(final Lex lex, AbstractExpression x) {
        AbstractExpression a = null;
        lex.next();
        if (!lex.isOperator(this.func.getEndOperator())) {
            a = this.share.funcArgRule.parse(lex);
            if (!lex.isOperator(this.func.getEndOperator())) {
                throw new EvalException(1001, new String[] { this.func.getEndOperator() }, lex);
            }
        }
        lex.next();
        x = FunctionExpression.create(x, a, this.prio, lex.getShare());
        return x;
    }
    
    protected AbstractExpression parseArray(final Lex lex, AbstractExpression x, final String ope, final int pos) {
        final AbstractExpression y = this.share.topRule.parse(lex.next());
        if (!lex.isOperator(this.array.getEndOperator())) {
            throw new EvalException(1001, new String[] { this.array.getEndOperator() }, lex);
        }
        lex.next();
        x = Col2Expression.create(this.newExpression(ope, lex.getShare()), lex.getString(), pos, x, y);
        return x;
    }
    
    protected AbstractExpression parseField(final Lex lex, AbstractExpression x, final String ope, final int pos) {
        final AbstractExpression y = this.nextRule.parse(lex.next());
        x = Col2Expression.create(this.newExpression(ope, lex.getShare()), lex.getString(), pos, x, y);
        return x;
    }
}
