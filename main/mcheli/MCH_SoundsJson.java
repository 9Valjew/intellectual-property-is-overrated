package mcheli;

import java.io.*;
import java.util.*;

public class MCH_SoundsJson
{
    public static boolean update(String path) {
        boolean result = true;
        path = path.replace('\\', '/');
        final File dir = new File(path + "sounds");
        final File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(final File pathname) {
                final String s = pathname.getName().toLowerCase();
                return pathname.isFile() && s.length() >= 5 && s.substring(s.length() - 4).compareTo(".ogg") == 0;
            }
        });
        int cnt = 0;
        PrintWriter pw = null;
        try {
            final File file = new File(path + "sounds.json");
            pw = new PrintWriter(file);
            pw.println("{");
            if (files != null) {
                final LinkedHashMap<String, ArrayList<String>> map = new LinkedHashMap<String, ArrayList<String>>();
                for (final File f : files) {
                    String name = f.getName().toLowerCase();
                    final int ei = name.lastIndexOf(".");
                    String key;
                    name = (key = name.substring(0, ei));
                    final char c = key.charAt(key.length() - 1);
                    if (c >= '0' && c <= '9') {
                        key = key.substring(0, key.length() - 1);
                    }
                    if (!map.containsKey(key)) {
                        map.put(key, new ArrayList<String>());
                    }
                    map.get(key).add(name);
                }
                for (final String key2 : map.keySet()) {
                    ++cnt;
                    final ArrayList<String> list = map.get(key2);
                    String line = "";
                    line = "\"" + key2 + "\": {\"category\": \"master\",\"sounds\": [";
                    for (int fi = 0; fi < list.size(); ++fi) {
                        line = line + ((fi > 0) ? "," : "") + "\"" + list.get(fi) + "\"";
                    }
                    line += "]}";
                    if (cnt < map.size()) {
                        line += ",";
                    }
                    pw.println(line);
                }
            }
            pw.println("}");
            pw.println("");
            result = true;
        }
        catch (IOException e) {
            result = false;
            e.printStackTrace();
        }
        finally {
            if (pw != null) {
                pw.close();
            }
        }
        if (result) {
            MCH_Lib.Log("Update sounds.json. %d sounds", cnt);
        }
        else {
            MCH_Lib.Log("Failed sounds.json update! %d sounds", cnt);
        }
        return result;
    }
}
