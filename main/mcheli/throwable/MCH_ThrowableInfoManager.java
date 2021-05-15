package mcheli.throwable;

import mcheli.*;
import java.io.*;
import net.minecraft.item.*;
import java.util.*;

public class MCH_ThrowableInfoManager
{
    private static MCH_ThrowableInfoManager instance;
    private static HashMap<String, MCH_ThrowableInfo> map;
    
    public static boolean load(String path) {
        path = path.replace('\\', '/');
        final File dir = new File(path);
        final File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(final File pathname) {
                final String s = pathname.getName().toLowerCase();
                return pathname.isFile() && s.length() >= 5 && s.substring(s.length() - 4).compareTo(".txt") == 0;
            }
        });
        if (files == null || files.length <= 0) {
            return false;
        }
        for (final File f : files) {
            final MCH_InputFile inFile = new MCH_InputFile();
            int line = 0;
            try {
                String name = f.getName().toLowerCase();
                name = name.substring(0, name.length() - 4);
                if (!MCH_ThrowableInfoManager.map.containsKey(name)) {
                    if (inFile.openUTF8(f)) {
                        final MCH_ThrowableInfo info = new MCH_ThrowableInfo(name);
                        String str;
                        while ((str = inFile.br.readLine()) != null) {
                            ++line;
                            str = str.trim();
                            final int eqIdx = str.indexOf(61);
                            if (eqIdx < 0) {
                                continue;
                            }
                            if (str.length() <= eqIdx + 1) {
                                continue;
                            }
                            info.loadItemData(str.substring(0, eqIdx).trim().toLowerCase(), str.substring(eqIdx + 1).trim());
                        }
                        info.checkData();
                        MCH_ThrowableInfoManager.map.put(name, info);
                    }
                }
            }
            catch (IOException e) {
                if (line > 0) {
                    MCH_Lib.Log("### Load failed %s : line=%d", f.getName(), line);
                }
                else {
                    MCH_Lib.Log("### Load failed %s", f.getName());
                }
                e.printStackTrace();
            }
            finally {
                inFile.close();
            }
        }
        MCH_Lib.Log("Read %d throwable", MCH_ThrowableInfoManager.map.size());
        return MCH_ThrowableInfoManager.map.size() > 0;
    }
    
    public static MCH_ThrowableInfo get(final String name) {
        return MCH_ThrowableInfoManager.map.get(name);
    }
    
    public static MCH_ThrowableInfo get(final Item item) {
        for (final MCH_ThrowableInfo info : MCH_ThrowableInfoManager.map.values()) {
            if (info.item == item) {
                return info;
            }
        }
        return null;
    }
    
    public static boolean contains(final String name) {
        return MCH_ThrowableInfoManager.map.containsKey(name);
    }
    
    public static Set<String> getKeySet() {
        return MCH_ThrowableInfoManager.map.keySet();
    }
    
    public static Collection<MCH_ThrowableInfo> getValues() {
        return MCH_ThrowableInfoManager.map.values();
    }
    
    static {
        MCH_ThrowableInfoManager.instance = new MCH_ThrowableInfoManager();
        MCH_ThrowableInfoManager.map = new LinkedHashMap<String, MCH_ThrowableInfo>();
    }
}
