package mcheli.eval.eval.exp;

import mcheli.eval.eval.lex.*;
import mcheli.eval.util.*;
import mcheli.eval.eval.*;

public class StringExpression extends WordExpression
{
    public static AbstractExpression create(final Lex lex, final int prio) {
        String str = lex.getWord();
        str = CharUtil.escapeString(str, 1, str.length() - 2);
        final AbstractExpression exp = new StringExpression(str);
        exp.setPos(lex.getString(), lex.getPos());
        exp.setPriority(prio);
        exp.share = lex.getShare();
        return exp;
    }
    
    public StringExpression(final String str) {
        super(str);
        this.setOperator("\"");
        this.setEndOperator("\"");
    }
    
    protected StringExpression(final StringExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    @Override
    public AbstractExpression dup(final ShareExpValue s) {
        return new StringExpression(this, s);
    }
    
    public static StringExpression create(final AbstractExpression from, final String word) {
        final StringExpression n = new StringExpression(word);
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
        return this.word;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof StringExpression) {
            final StringExpression e = (StringExpression)obj;
            return this.word.equals(e.word);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.word.hashCode();
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(this.getOperator());
        sb.append(this.word);
        sb.append(this.getEndOperator());
        return sb.toString();
    }
}
