package mcheli.eval.eval.lex;

import java.util.*;
import mcheli.eval.eval.rule.*;
import mcheli.eval.eval.exp.*;

public class LexFactory
{
    public Lex create(final String str, final List[] opeList, final ShareRuleValue share, final ShareExpValue exp) {
        return new Lex(str, opeList, share.paren, exp);
    }
}
