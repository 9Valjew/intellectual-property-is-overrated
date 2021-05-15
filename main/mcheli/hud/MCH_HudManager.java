package mcheli.hud;

import net.minecraft.client.*;
import java.io.*;
import mcheli.*;
import java.util.*;

public class MCH_HudManager
{
    private static MCH_HudManager instance;
    private static HashMap<String, MCH_Hud> map;
    
    private MCH_HudManager() {
        MCH_HudManager.map = new HashMap<String, MCH_Hud>();
    }
    
    public static boolean load(String path) {
        MCH_HudItem.mc = Minecraft.func_71410_x();
        MCH_HudManager.map.clear();
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
            Label_0368: {
                try {
                    String name = f.getName().toLowerCase();
                    name = name.substring(0, name.length() - 4);
                    if (MCH_HudManager.map.containsKey(name)) {
                        break Label_0368;
                    }
                    if (inFile.openUTF8(f)) {
                        final MCH_Hud info = new MCH_Hud(name, f.getPath());
                        String str;
                        while ((str = inFile.br.readLine()) != null) {
                            ++line;
                            str = str.trim();
                            if (str.equalsIgnoreCase("endif")) {
                                str = "endif=0";
                            }
                            if (str.equalsIgnoreCase("exit")) {
                                str = "exit=0";
                            }
                            final int eqIdx = str.indexOf(61);
                            if (eqIdx < 0) {
                                continue;
                            }
                            if (str.length() <= eqIdx + 1) {
                                continue;
                            }
                            info.loadItemData(line, str.substring(0, eqIdx).trim().toLowerCase(), str.substring(eqIdx + 1).trim());
                        }
                        info.checkData();
                        MCH_HudManager.map.put(name, info);
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                catch (Exception e2) {
                    MCH_Lib.Log("### HUD file error! %s Line=%d", f.getName(), line);
                    e2.printStackTrace();
                    throw new RuntimeException(e2);
                }
                finally {
                    inFile.close();
                }
            }
        }
        MCH_Lib.Log("Read %d HUD", MCH_HudManager.map.size());
        return MCH_HudManager.map.size() > 0;
    }
    
    public static MCH_Hud get(final String name) {
        return MCH_HudManager.map.get(name.toLowerCase());
    }
    
    public static boolean contains(final String name) {
        return MCH_HudManager.map.containsKey(name);
    }
    
    public static Set<String> getKeySet() {
        return MCH_HudManager.map.keySet();
    }
    
    public static Collection<MCH_Hud> getValues() {
        return MCH_HudManager.map.values();
    }
    
    static {
        MCH_HudManager.instance = new MCH_HudManager();
    }
}
