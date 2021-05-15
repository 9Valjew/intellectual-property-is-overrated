package mcheli.eval.eval.exp;

import mcheli.eval.eval.lex.*;
import mcheli.eval.util.*;
import mcheli.eval.eval.*;

public class NumberExpression extends WordExpression
{
    public static AbstractExpression create(final Lex lex, final int prio) {
        final AbstractExpression exp = new NumberExpression(lex.getWord());
        exp.setPos(lex.getString(), lex.getPos());
        exp.setPriority(prio);
        exp.share = lex.getShare();
        return exp;
    }
    
    public NumberExpression(final String str) {
        super(str);
    }
    
    protected NumberExpression(final NumberExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    @Override
    public AbstractExpression dup(final ShareExpValue s) {
        return new NumberExpression(this, s);
    }
    
    public static NumberExpression create(final AbstractExpression from, final String word) {
        final NumberExpression n = new NumberExpression(word);
        n.string = from.string;
        n.pos = from.pos;
        n.prio = from.prio;
        n.share = from.share;
        return n;
    }
    
    @Override
    public long evalLong() {
        try {
            return NumberUtil.parseLong(this.word);
        }
        catch (Exception e) {
            try {
                return Long.parseLong(this.word);
            }
            catch (Exception e) {
                try {
                    return (long)Double.parseDouble(this.word);
                }
                catch (Exception e) {
                    throw new EvalException(2003, this.word, this.string, this.pos, e);
                }
            }
        }
    }
    
    @Override
    public double evalDouble() {
        try {
            return Double.parseDouble(this.word);
        }
        catch (Exception e) {
            try {
                return NumberUtil.parseLong(this.word);
            }
            catch (Exception e2) {
                throw new EvalException(2003, this.word, this.string, this.pos, e);
            }
        }
    }
    
    @Override
    public Object evalObject() {
        try {
            return new Long(NumberUtil.parseLong(this.word));
        }
        catch (Exception e) {
            try {
                return Long.valueOf(this.word);
            }
            catch (Exception e) {
                try {
                    return Double.valueOf(this.word);
                }
                catch (Exception e) {
                    throw new EvalException(2003, this.word, this.string, this.pos, e);
                }
            }
        }
    }
}
