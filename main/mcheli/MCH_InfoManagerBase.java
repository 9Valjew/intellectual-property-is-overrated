package mcheli;

import java.util.*;
import java.io.*;

public abstract class MCH_InfoManagerBase
{
    public abstract MCH_BaseInfo newInfo(final String p0);
    
    public abstract Map getMap();
    
    public boolean load(String path, final String type) {
        path = path.replace('\\', '/');
        final File dir = new File(path + type);
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
            int line = 0;
            final MCH_InputFile inFile = new MCH_InputFile();
            final BufferedReader br = null;
            try {
                String name = f.getName().toLowerCase();
                name = name.substring(0, name.length() - 4);
                if (!this.getMap().containsKey(name)) {
                    if (inFile.openUTF8(f)) {
                        final MCH_BaseInfo info = this.newInfo(name);
                        info.filePath = f.getCanonicalPath();
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
                        line = 0;
                        if (info.isValidData()) {
                            this.getMap().put(name, info);
                        }
                    }
                }
            }
            catch (Exception e) {
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
        MCH_Lib.Log("Read %d %s", this.getMap().size(), type);
        return this.getMap().size() > 0;
    }
}
