package mcheli.eval.eval.exp;

public abstract class WordExpression extends AbstractExpression
{
    protected String word;
    
    protected WordExpression(final String str) {
        this.word = str;
    }
    
    protected WordExpression(final WordExpression from, final ShareExpValue s) {
        super(from, s);
        this.word = from.word;
    }
    
    @Override
    protected String getWord() {
        return this.word;
    }
    
    @Override
    protected void setWord(final String word) {
        this.word = word;
    }
    
    @Override
    protected int getCols() {
        return 0;
    }
    
    @Override
    protected int getFirstPos() {
        return this.pos;
    }
    
    @Override
    protected void search() {
        this.share.srch.search(this);
        if (this.share.srch.end()) {
            return;
        }
        this.share.srch.search0(this);
    }
    
    @Override
    protected AbstractExpression replace() {
        return this.share.repl.replace0(this);
    }
    
    @Override
    protected AbstractExpression replaceVar() {
        return this.share.repl.replaceVar0(this);
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof WordExpression) {
            final WordExpression e = (WordExpression)obj;
            if (this.getClass() == e.getClass()) {
                return this.word.equals(e.word);
            }
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.word.hashCode();
    }
    
    @Override
    public void dump(final int n) {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; ++i) {
            sb.append(' ');
        }
        sb.append(this.word);
        System.out.println(sb.toString());
    }
    
    @Override
    public String toString() {
        return this.word;
    }
}
