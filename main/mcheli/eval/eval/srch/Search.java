package mcheli.eval.eval.srch;

import mcheli.eval.eval.exp.*;

public interface Search
{
    boolean end();
    
    void search(final AbstractExpression p0);
    
    void search0(final WordExpression p0);
    
    boolean search1_begin(final Col1Expression p0);
    
    void search1_end(final Col1Expression p0);
    
    boolean search2_begin(final Col2Expression p0);
    
    boolean search2_2(final Col2Expression p0);
    
    void search2_end(final Col2Expression p0);
    
    boolean search3_begin(final Col3Expression p0);
    
    boolean search3_2(final Col3Expression p0);
    
    boolean search3_3(final Col3Expression p0);
    
    void search3_end(final Col3Expression p0);
    
    boolean searchFunc_begin(final FunctionExpression p0);
    
    boolean searchFunc_2(final FunctionExpression p0);
    
    void searchFunc_end(final FunctionExpression p0);
}
