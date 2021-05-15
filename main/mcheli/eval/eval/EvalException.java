package mcheli.eval.eval;

import mcheli.eval.eval.lex.*;

public class EvalException extends RuntimeException
{
    private static final long serialVersionUID = 4174576689426433715L;
    public static final int PARSE_NOT_FOUND_END_OP = 1001;
    public static final int PARSE_INVALID_OP = 1002;
    public static final int PARSE_INVALID_CHAR = 1003;
    public static final int PARSE_END_OF_STR = 1004;
    public static final int PARSE_STILL_EXIST = 1005;
    public static final int PARSE_NOT_FUNC = 1101;
    public static final int EXP_FORBIDDEN_CALL = 2001;
    public static final int EXP_NOT_VARIABLE = 2002;
    public static final int EXP_NOT_NUMBER = 2003;
    public static final int EXP_NOT_LET = 2004;
    public static final int EXP_NOT_VAR_VALUE = 2101;
    public static final int EXP_NOT_LET_VAR = 2102;
    public static final int EXP_NOT_DEF_VAR = 2103;
    public static final int EXP_NOT_DEF_OBJ = 2104;
    public static final int EXP_NOT_ARR_VALUE = 2201;
    public static final int EXP_NOT_LET_ARR = 2202;
    public static final int EXP_NOT_FLD_VALUE = 2301;
    public static final int EXP_NOT_LET_FIELD = 2302;
    public static final int EXP_FUNC_CALL_ERROR = 2401;
    protected int msg_code;
    protected String[] msg_opt;
    protected String string;
    protected int pos;
    protected String word;
    
    public EvalException(final int msg, final Lex lex) {
        this(msg, null, lex);
    }
    
    public EvalException(final int msg, final String[] opt, final Lex lex) {
        this.pos = -1;
        this.msg_code = msg;
        this.msg_opt = opt;
        if (lex != null) {
            this.string = lex.getString();
            this.pos = lex.getPos();
            this.word = lex.getWord();
        }
    }
    
    public EvalException(final int msg, final String word, final String string, final int pos, Throwable e) {
        this.pos = -1;
        while (e != null && e.getClass() == RuntimeException.class && e.getCause() != null) {
            e = e.getCause();
        }
        if (e != null) {
            super.initCause(e);
        }
        this.msg_code = msg;
        this.string = string;
        this.pos = pos;
        this.word = word;
    }
    
    public int getErrorCode() {
        return this.msg_code;
    }
    
    public String[] getOption() {
        return this.msg_opt;
    }
    
    public String getWord() {
        return this.word;
    }
    
    public String getString() {
        return this.string;
    }
    
    public int getPos() {
        return this.pos;
    }
    
    public static String getErrCodeMessage(final int code) {
        switch (code) {
            case 1001: {
                return "\u6f14\u7b97\u5b50\u300c%0\u300d\u304c\u5728\u308a\u307e\u305b\u3093\u3002";
            }
            case 1002: {
                return "\u6f14\u7b97\u5b50\u306e\u6587\u6cd5\u30a8\u30e9\u30fc\u3067\u3059\u3002";
            }
            case 1003: {
                return "\u672a\u5bfe\u5fdc\u306e\u8b58\u5225\u5b50\u3067\u3059\u3002";
            }
            case 1004: {
                return "\u5f0f\u306e\u89e3\u91c8\u306e\u9014\u4e2d\u3067\u6587\u5b57\u5217\u304c\u7d42\u4e86\u3057\u3066\u3044\u307e\u3059\u3002";
            }
            case 1005: {
                return "\u5f0f\u306e\u89e3\u91c8\u304c\u7d42\u308f\u308a\u307e\u3057\u305f\u304c\u6587\u5b57\u5217\u304c\u6b8b\u3063\u3066\u3044\u307e\u3059\u3002";
            }
            case 1101: {
                return "\u95a2\u6570\u3068\u3057\u3066\u4f7f\u7528\u3067\u304d\u307e\u305b\u3093\u3002";
            }
            case 2001: {
                return "\u7981\u6b62\u3055\u308c\u3066\u3044\u308b\u30e1\u30bd\u30c3\u30c9\u3092\u547c\u3073\u51fa\u3057\u307e\u3057\u305f\u3002";
            }
            case 2002: {
                return "\u5909\u6570\u3068\u3057\u3066\u4f7f\u7528\u3067\u304d\u307e\u305b\u3093\u3002";
            }
            case 2003: {
                return "\u6570\u5024\u3068\u3057\u3066\u4f7f\u7528\u3067\u304d\u307e\u305b\u3093\u3002";
            }
            case 2004: {
                return "\u4ee3\u5165\u3067\u304d\u307e\u305b\u3093\u3002";
            }
            case 2101: {
                return "\u5909\u6570\u306e\u5024\u304c\u53d6\u5f97\u3067\u304d\u307e\u305b\u3093\u3002";
            }
            case 2102: {
                return "\u5909\u6570\u306b\u4ee3\u5165\u3067\u304d\u307e\u305b\u3093\u3002";
            }
            case 2103: {
                return "\u5909\u6570\u304c\u672a\u5b9a\u7fa9\u3067\u3059\u3002";
            }
            case 2104: {
                return "\u30aa\u30d6\u30b8\u30a7\u30af\u30c8\u304c\u672a\u5b9a\u7fa9\u3067\u3059\u3002";
            }
            case 2201: {
                return "\u914d\u5217\u306e\u5024\u304c\u53d6\u5f97\u3067\u304d\u307e\u305b\u3093\u3002";
            }
            case 2202: {
                return "\u914d\u5217\u306b\u4ee3\u5165\u3067\u304d\u307e\u305b\u3093\u3002";
            }
            case 2301: {
                return "\u30d5\u30a3\u30fc\u30eb\u30c9\u306e\u5024\u304c\u53d6\u5f97\u3067\u304d\u307e\u305b\u3093\u3002";
            }
            case 2302: {
                return "\u30d5\u30a3\u30fc\u30eb\u30c9\u306b\u4ee3\u5165\u3067\u304d\u307e\u305b\u3093\u3002";
            }
            case 2401: {
                return "\u95a2\u6570\u306e\u547c\u3073\u51fa\u3057\u306b\u5931\u6557\u3057\u307e\u3057\u305f\u3002";
            }
            default: {
                return "\u30a8\u30e9\u30fc\u304c\u767a\u751f\u3057\u307e\u3057\u305f\u3002";
            }
        }
    }
    
    public String getDefaultFormat(final String msgFmt) {
        final StringBuffer fmt = new StringBuffer(128);
        fmt.append(msgFmt);
        boolean bWord = false;
        if (this.word != null && this.word.length() > 0) {
            bWord = true;
            if (this.word.equals(this.string)) {
                bWord = false;
            }
        }
        if (bWord) {
            fmt.append(" word=\u300c%w\u300d");
        }
        if (this.pos >= 0) {
            fmt.append(" pos=%p");
        }
        if (this.string != null) {
            fmt.append(" string=\u300c%s\u300d");
        }
        if (this.getCause() != null) {
            fmt.append(" cause by %e");
        }
        return fmt.toString();
    }
    
    @Override
    public String toString() {
        final String msg = getErrCodeMessage(this.msg_code);
        final String fmt = this.getDefaultFormat(msg);
        return this.toString(fmt);
    }
    
    public String toString(final String fmt) {
        final StringBuffer sb = new StringBuffer(256);
        for (int len = fmt.length(), i = 0; i < len; ++i) {
            char c = fmt.charAt(i);
            if (c != '%') {
                sb.append(c);
            }
            else {
                if (i + 1 >= len) {
                    sb.append(c);
                    break;
                }
                c = fmt.charAt(++i);
                switch (c) {
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9': {
                        final int n = c - '0';
                        if (this.msg_opt != null && n < this.msg_opt.length) {
                            sb.append(this.msg_opt[n]);
                            break;
                        }
                        break;
                    }
                    case 'c': {
                        sb.append(this.msg_code);
                        break;
                    }
                    case 'w': {
                        sb.append(this.word);
                        break;
                    }
                    case 'p': {
                        sb.append(this.pos);
                        break;
                    }
                    case 's': {
                        sb.append(this.string);
                        break;
                    }
                    case 'e': {
                        sb.append(this.getCause());
                        break;
                    }
                    case '%': {
                        sb.append('%');
                        break;
                    }
                    default: {
                        sb.append('%');
                        sb.append(c);
                        break;
                    }
                }
            }
        }
        return sb.toString();
    }
}
