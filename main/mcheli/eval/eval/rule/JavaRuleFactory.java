package mcheli.eval.eval.rule;

import mcheli.eval.eval.*;
import mcheli.eval.eval.exp.*;

public class JavaRuleFactory extends ExpRuleFactory
{
    private static JavaRuleFactory me;
    
    public static ExpRuleFactory getInstance() {
        if (JavaRuleFactory.me == null) {
            JavaRuleFactory.me = new JavaRuleFactory();
        }
        return JavaRuleFactory.me;
    }
    
    @Override
    protected AbstractRule createCommaRule(final ShareRuleValue share) {
        return null;
    }
    
    @Override
    protected AbstractRule createPowerRule(final ShareRuleValue share) {
        return null;
    }
    
    @Override
    protected AbstractExpression createLetPowerExpression() {
        return null;
    }
}
