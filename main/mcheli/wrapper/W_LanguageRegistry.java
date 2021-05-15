package mcheli.wrapper;

import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.stats.*;
import mcheli.*;
import java.util.*;

public class W_LanguageRegistry
{
    private static HashMap<String, ArrayList<String>> map;
    
    public static void addName(final Object objectToName, final String name) {
        addNameForObject(objectToName, "en_US", name);
    }
    
    public static void addNameForObject(final Object o, final String lang, final String name) {
        addNameForObject(o, lang, name, "", "");
    }
    
    public static void addNameForObject(final Object o, final String lang, final String name, final String key, final String desc) {
        if (o == null) {
            return;
        }
        if (!W_LanguageRegistry.map.containsKey(lang)) {
            W_LanguageRegistry.map.put(lang, new ArrayList<String>());
        }
        if (o instanceof Item) {
            W_LanguageRegistry.map.get(lang).add(((Item)o).func_77658_a() + ".name=" + name);
        }
        if (o instanceof Block) {
            W_LanguageRegistry.map.get(lang).add(((Block)o).func_149739_a() + ".name=" + name);
        }
        else if (o instanceof Achievement) {
            W_LanguageRegistry.map.get(lang).add("achievement." + key + "=" + name);
            W_LanguageRegistry.map.get(lang).add("achievement." + key + ".desc=" + desc);
        }
    }
    
    public static void updateLang(final String filePath) {
        for (final String key : W_LanguageRegistry.map.keySet()) {
            final ArrayList<String> list = W_LanguageRegistry.map.get(key);
            final MCH_OutputFile file = new MCH_OutputFile();
            if (file.openUTF8(filePath + key + ".lang")) {
                for (final String s : list) {
                    file.writeLine(s);
                }
                MCH_Lib.Log("[mcheli] Update lang:" + file.file.getAbsolutePath(), new Object[0]);
                file.close();
            }
        }
        W_LanguageRegistry.map = null;
    }
    
    static {
        W_LanguageRegistry.map = new HashMap<String, ArrayList<String>>();
    }
}
