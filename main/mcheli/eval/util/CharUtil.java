package mcheli.eval.util;

public class CharUtil
{
    public static String escapeString(final String str) {
        return escapeString(str, 0, str.length());
    }
    
    public static String escapeString(final String str, int pos, final int len) {
        final StringBuffer sb = new StringBuffer(len);
        final int end_pos = pos + len;
        for (int[] ret = { 0 }; pos < end_pos; pos += ret[0]) {
            final char c = escapeChar(str, pos, end_pos, ret);
            if (ret[0] <= 0) {
                break;
            }
            sb.append(c);
        }
        return sb.toString();
    }
    
    public static char escapeChar(final String str, int pos, final int end_pos, final int[] ret) {
        if (pos >= end_pos) {
            ret[0] = 0;
            return '\0';
        }
        char c = str.charAt(pos);
        if (c != '\\') {
            ret[0] = 1;
            return c;
        }
        if (++pos >= end_pos) {
            ret[0] = 1;
            return c;
        }
        ret[0] = 2;
        c = str.charAt(pos);
        switch (c) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7': {
                long code = c - '0';
                for (int i = 1; i < 3 && ++pos < end_pos; ++i) {
                    c = str.charAt(pos);
                    if (c < '0') {
                        break;
                    }
                    if (c > '7') {
                        break;
                    }
                    final int n = 0;
                    ++ret[n];
                    code *= 8L;
                    code += c - '0';
                }
                return (char)code;
            }
            case 'b': {
                return '\b';
            }
            case 'f': {
                return '\f';
            }
            case 'n': {
                return '\n';
            }
            case 'r': {
                return '\r';
            }
            case 't': {
                return '\t';
            }
            case 'u': {
                long code = 0L;
                for (int i = 0; i < 4 && ++pos < end_pos; ++i) {
                    c = str.charAt(pos);
                    if ('0' <= c && c <= '9') {
                        final int n2 = 0;
                        ++ret[n2];
                        code *= 16L;
                        code += c - '0';
                    }
                    else if ('a' <= c && c <= 'f') {
                        final int n3 = 0;
                        ++ret[n3];
                        code *= 16L;
                        code += c - 'a' + '\n';
                    }
                    else {
                        if ('A' > c || c > 'F') {
                            break;
                        }
                        final int n4 = 0;
                        ++ret[n4];
                        code *= 16L;
                        code += c - 'A' + '\n';
                    }
                }
                return (char)code;
            }
            default: {
                return c;
            }
        }
    }
}
