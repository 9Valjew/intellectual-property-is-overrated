package mcheli.eval.eval.rule;

import java.util.*;
import mcheli.eval.eval.exp.*;
import mcheli.eval.eval.lex.*;

public abstract class AbstractRule
{
    public AbstractRule nextRule;
    protected ShareRuleValue share;
    private final Map opes;
    public int prio;
    
    public AbstractRule(final ShareRuleValue share) {
        this.opes = new HashMap();
        this.share = share;
    }
    
    public final void addExpression(final AbstractExpression exp) {
        if (exp == null) {
            return;
        }
        final String ope = exp.getOperator();
        this.addOperator(ope, exp);
        this.addLexOperator(exp.getEndOperator());
        if (exp instanceof ParenExpression) {
            this.share.paren = exp;
        }
    }
    
    public final void addOperator(final String ope, final AbstractExpression exp) {
        this.opes.put(ope, exp);
        this.addLexOperator(ope);
    }
    
    public final String[] getOperators() {
        final List list = new ArrayList();
        final Iterator i = this.opes.keySet().iterator();
        while (i.hasNext()) {
            list.add(i.next());
        }
        return list.toArray(new String[list.size()]);
    }
    
    public final void addLexOperator(final String ope) {
        if (ope == null) {
            return;
        }
        final int n = ope.length() - 1;
        if (this.share.opeList[n] == null) {
            this.share.opeList[n] = new ArrayList();
        }
        this.share.opeList[n].add(ope);
    }
    
    protected final boolean isMyOperator(final String ope) {
        return this.opes.containsKey(ope);
    }
    
    protected final AbstractExpression newExpression(final String ope, final ShareExpValue share) {
        try {
            final AbstractExpression org = this.opes.get(ope);
            final AbstractExpression n = org.dup(share);
            n.setPriority(this.prio);
            n.share = share;
            return n;
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }
    
    public final void initPriority(final int prio) {
        this.prio = prio;
        if (this.nextRule != null) {
            this.nextRule.initPriority(prio + 1);
        }
    }
    
    protected abstract AbstractExpression parse(final Lex p0);
}
