package mcheli.eval.eval.exp;

import mcheli.eval.eval.lex.*;
import mcheli.eval.util.*;
import mcheli.eval.eval.*;

public class CharExpression extends WordExpression
{
    public static AbstractExpression create(final Lex lex, final int prio) {
        String str = lex.getWord();
        str = CharUtil.escapeString(str, 1, str.length() - 2);
        final AbstractExpression exp = new CharExpression(str);
        exp.setPos(lex.getString(), lex.getPos());
        exp.setPriority(prio);
        exp.share = lex.getShare();
        return exp;
    }
    
    public CharExpression(final String str) {
        super(str);
        this.setOperator("'");
        this.setEndOperator("'");
    }
    
    protected CharExpression(final CharExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    @Override
    public AbstractExpression dup(final ShareExpValue s) {
        return new CharExpression(this, s);
    }
    
    public static CharExpression create(final AbstractExpression from, final String word) {
        final CharExpression n = new CharExpression(word);
        n.string = from.string;
        n.pos = from.pos;
        n.prio = from.prio;
        n.share = from.share;
        return n;
    }
    
    @Override
    public long evalLong() {
        try {
            return this.word.charAt(0);
        }
        catch (Exception e) {
            throw new EvalException(2003, this.word, this.string, this.pos, e);
        }
    }
    
    @Override
    public double evalDouble() {
        try {
            return this.word.charAt(0);
        }
        catch (Exception e) {
            throw new EvalException(2003, this.word, this.string, this.pos, e);
        }
    }
    
    @Override
    public Object evalObject() {
        return new Character(this.word.charAt(0));
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
