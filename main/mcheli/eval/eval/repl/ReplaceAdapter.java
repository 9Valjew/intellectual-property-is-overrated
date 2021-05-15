package mcheli.eval.eval.repl;

import mcheli.eval.eval.exp.*;

public class ReplaceAdapter implements Replace
{
    @Override
    public AbstractExpression replace0(final WordExpression exp) {
        return exp;
    }
    
    @Override
    public AbstractExpression replace1(final Col1Expression exp) {
        return exp;
    }
    
    @Override
    public AbstractExpression replace2(final Col2Expression exp) {
        return exp;
    }
    
    @Override
    public AbstractExpression replace2(final Col2OpeExpression exp) {
        return exp;
    }
    
    @Override
    public AbstractExpression replace3(final Col3Expression exp) {
        return exp;
    }
    
    @Override
    public AbstractExpression replaceVar0(final WordExpression exp) {
        return exp;
    }
    
    @Override
    public AbstractExpression replaceVar1(final Col1Expression exp) {
        return exp;
    }
    
    @Override
    public AbstractExpression replaceVar2(final Col2Expression exp) {
        return exp;
    }
    
    @Override
    public AbstractExpression replaceVar2(final Col2OpeExpression exp) {
        return exp;
    }
    
    @Override
    public AbstractExpression replaceVar3(final Col3Expression exp) {
        return exp;
    }
    
    @Override
    public AbstractExpression replaceFunc(final FunctionExpression exp) {
        return exp;
    }
    
    @Override
    public AbstractExpression replaceLet(final Col2Expression exp) {
        return exp;
    }
}
