package mcheli.eval.eval.lex;

import java.util.*;
import mcheli.eval.eval.exp.*;
import mcheli.eval.util.*;

public class Lex
{
    protected List[] opeList;
    protected String string;
    protected int pos;
    protected int len;
    protected int type;
    public static final int TYPE_WORD = 2147483632;
    public static final int TYPE_NUM = 2147483633;
    public static final int TYPE_OPE = 2147483634;
    public static final int TYPE_STRING = 2147483635;
    public static final int TYPE_CHAR = 2147483636;
    public static final int TYPE_EOF = Integer.MAX_VALUE;
    public static final int TYPE_ERR = -1;
    protected String ope;
    protected ShareExpValue expShare;
    protected String SPC_CHAR;
    protected String NUMBER_CHAR;
    
    protected Lex(final String str, final List[] lists, final AbstractExpression paren, final ShareExpValue exp) {
        this.pos = 0;
        this.len = 0;
        this.type = -1;
        this.SPC_CHAR = " \t\r\n";
        this.NUMBER_CHAR = "._";
        this.string = str;
        this.opeList = lists;
        this.expShare = exp;
        if (this.expShare.paren == null) {
            this.expShare.paren = paren;
        }
    }
    
    protected boolean isSpace(final int pos) {
        if (pos >= this.string.length()) {
            return true;
        }
        final char c = this.string.charAt(pos);
        return this.SPC_CHAR.indexOf(c) >= 0;
    }
    
    protected boolean isNumberTop(final int pos) {
        if (pos >= this.string.length()) {
            return false;
        }
        final char c = this.string.charAt(pos);
        return '0' <= c && c <= '9';
    }
    
    protected boolean isSpecialNumber(final int pos) {
        if (pos >= this.string.length()) {
            return false;
        }
        final char c = this.string.charAt(pos);
        return this.NUMBER_CHAR.indexOf(c) >= 0;
    }
    
    protected String isOperator(final int pos) {
        for (int i = this.opeList.length - 1; i >= 0; --i) {
            if (pos + i < this.string.length()) {
                final List list = this.opeList[i];
                if (list != null) {
                    int j = 0;
                Label_0042:
                    while (j < list.size()) {
                        final String ope = list.get(j);
                        for (int k = 0; k <= i; ++k) {
                            final char c = this.string.charAt(pos + k);
                            final char o = ope.charAt(k);
                            if (c != o) {
                                ++j;
                                continue Label_0042;
                            }
                        }
                        return ope;
                    }
                }
            }
        }
        return null;
    }
    
    protected boolean isStringTop(final int pos) {
        if (pos >= this.string.length()) {
            return false;
        }
        final char c = this.string.charAt(pos);
        return c == '\"';
    }
    
    protected boolean isStringEnd(final int pos) {
        return this.isStringTop(pos);
    }
    
    protected boolean isCharTop(final int pos) {
        if (pos >= this.string.length()) {
            return false;
        }
        final char c = this.string.charAt(pos);
        return c == '\'';
    }
    
    protected boolean isCharEnd(final int pos) {
        return this.isCharTop(pos);
    }
    
    public void check() {
        while (this.isSpace(this.pos)) {
            if (this.pos >= this.string.length()) {
                this.type = Integer.MAX_VALUE;
                this.len = 0;
                return;
            }
            ++this.pos;
        }
        if (this.isStringTop(this.pos)) {
            this.processString();
            return;
        }
        if (this.isCharTop(this.pos)) {
            this.processChar();
            return;
        }
        final String ope = this.isOperator(this.pos);
        if (ope != null) {
            this.type = 2147483634;
            this.ope = ope;
            this.len = ope.length();
            return;
        }
        final boolean number = this.isNumberTop(this.pos);
        this.type = (number ? 2147483633 : 2147483632);
        this.len = 1;
        while (!this.isSpace(this.pos + this.len)) {
            if (!number || !this.isSpecialNumber(this.pos + this.len)) {
                if (this.isOperator(this.pos + this.len) != null) {
                    return;
                }
            }
            ++this.len;
        }
    }
    
    protected void processString() {
        final int[] ret = { 0 };
        this.type = 2147483635;
        this.len = 1;
        do {
            this.len += this.getCharLen(this.pos + this.len, ret);
            if (this.pos + this.len >= this.string.length()) {
                this.type = Integer.MAX_VALUE;
                return;
            }
        } while (!this.isStringEnd(this.pos + this.len));
        ++this.len;
    }
    
    protected void processChar() {
        final int[] ret = { 0 };
        this.type = 2147483636;
        this.len = 1;
        do {
            this.len += this.getCharLen(this.pos + this.len, ret);
            if (this.pos + this.len >= this.string.length()) {
                this.type = Integer.MAX_VALUE;
                return;
            }
        } while (!this.isCharEnd(this.pos + this.len));
        ++this.len;
    }
    
    protected int getCharLen(final int pos, final int[] ret) {
        CharUtil.escapeChar(this.string, pos, this.string.length(), ret);
        return ret[0];
    }
    
    public Lex next() {
        this.pos += this.len;
        this.check();
        return this;
    }
    
    public int getType() {
        return this.type;
    }
    
    public String getOperator() {
        return this.ope;
    }
    
    public boolean isOperator(final String ope) {
        return this.type == 2147483634 && this.ope.equals(ope);
    }
    
    public String getWord() {
        return this.string.substring(this.pos, this.pos + this.len);
    }
    
    public String getString() {
        return this.string;
    }
    
    public int getPos() {
        return this.pos;
    }
    
    public ShareExpValue getShare() {
        return this.expShare;
    }
}
