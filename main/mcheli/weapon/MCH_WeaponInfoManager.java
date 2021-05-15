package mcheli.weapon;

import mcheli.*;
import java.io.*;
import mcheli.wrapper.*;
import net.minecraft.item.*;
import java.util.*;

public class MCH_WeaponInfoManager
{
    private static MCH_WeaponInfoManager instance;
    private static HashMap<String, MCH_WeaponInfo> map;
    private static String lastPath;
    
    private MCH_WeaponInfoManager() {
        MCH_WeaponInfoManager.map = new HashMap<String, MCH_WeaponInfo>();
    }
    
    public static boolean reload() {
        boolean ret = false;
        try {
            MCH_WeaponInfoManager.map.clear();
            ret = load(MCH_WeaponInfoManager.lastPath);
            setRoundItems();
            MCH_MOD.proxy.registerModels();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
    
    public static boolean load(String path) {
        MCH_WeaponInfoManager.lastPath = path;
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
            BufferedReader br = null;
            int line = 0;
            try {
                String name = f.getName().toLowerCase();
                name = name.substring(0, name.length() - 4);
                if (MCH_WeaponInfoManager.map.containsKey(name)) {}
                br = new BufferedReader(new FileReader(f));
                final MCH_WeaponInfo info = new MCH_WeaponInfo(name);
                String str;
                while ((str = br.readLine()) != null) {
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
                MCH_WeaponInfoManager.map.put(name, info);
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
                try {
                    if (br != null) {
                        br.close();
                    }
                }
                catch (Exception ex) {}
            }
            Label_0372:;
        }
        MCH_Lib.Log("[mcheli] Read %d weapons", MCH_WeaponInfoManager.map.size());
        return MCH_WeaponInfoManager.map.size() > 0;
    }
    
    public static void setRoundItems() {
        for (final MCH_WeaponInfo w : MCH_WeaponInfoManager.map.values()) {
            for (final MCH_WeaponInfo.RoundItem r : w.roundItems) {
                final Item item = W_Item.getItemByName(r.itemName);
                r.itemStack = new ItemStack(item, 1, r.damage);
            }
        }
    }
    
    public static MCH_WeaponInfo get(final String name) {
        return MCH_WeaponInfoManager.map.get(name);
    }
    
    public static boolean contains(final String name) {
        return MCH_WeaponInfoManager.map.containsKey(name);
    }
    
    public static Set<String> getKeySet() {
        return MCH_WeaponInfoManager.map.keySet();
    }
    
    public static Collection<MCH_WeaponInfo> getValues() {
        return MCH_WeaponInfoManager.map.values();
    }
    
    static {
        MCH_WeaponInfoManager.instance = new MCH_WeaponInfoManager();
    }
}
