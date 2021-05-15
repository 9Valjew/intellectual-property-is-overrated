package mcheli.eval.eval.srch;

import mcheli.eval.eval.exp.*;

public class SearchAdapter implements Search
{
    protected boolean end;
    
    public SearchAdapter() {
        this.end = false;
    }
    
    @Override
    public boolean end() {
        return this.end;
    }
    
    protected void setEnd() {
        this.end = true;
    }
    
    @Override
    public void search(final AbstractExpression exp) {
    }
    
    @Override
    public void search0(final WordExpression exp) {
    }
    
    @Override
    public boolean search1_begin(final Col1Expression exp) {
        return false;
    }
    
    @Override
    public void search1_end(final Col1Expression exp) {
    }
    
    @Override
    public boolean search2_begin(final Col2Expression exp) {
        return false;
    }
    
    @Override
    public boolean search2_2(final Col2Expression exp) {
        return false;
    }
    
    @Override
    public void search2_end(final Col2Expression exp) {
    }
    
    @Override
    public boolean search3_begin(final Col3Expression exp) {
        return false;
    }
    
    @Override
    public boolean search3_2(final Col3Expression exp3) {
        return false;
    }
    
    @Override
    public boolean search3_3(final Col3Expression exp) {
        return false;
    }
    
    @Override
    public void search3_end(final Col3Expression exp) {
    }
    
    @Override
    public boolean searchFunc_begin(final FunctionExpression exp) {
        return false;
    }
    
    @Override
    public boolean searchFunc_2(final FunctionExpression exp) {
        return false;
    }
    
    @Override
    public void searchFunc_end(final FunctionExpression exp) {
    }
}
