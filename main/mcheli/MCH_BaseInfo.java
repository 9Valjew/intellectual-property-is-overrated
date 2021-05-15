package mcheli;

import net.minecraft.util.*;
import java.io.*;

public class MCH_BaseInfo
{
    public String filePath;
    
    public boolean toBool(final String s) {
        return s.equalsIgnoreCase("true");
    }
    
    public boolean toBool(final String s, final boolean defaultValue) {
        return s.equalsIgnoreCase("true") || (!s.equalsIgnoreCase("false") && defaultValue);
    }
    
    public float toFloat(final String s) {
        return Float.parseFloat(s);
    }
    
    public float toFloat(final String s, final float min, final float max) {
        final float f = Float.parseFloat(s);
        return (f < min) ? min : ((f > max) ? max : f);
    }
    
    public double toDouble(final String s) {
        return Double.parseDouble(s);
    }
    
    public Vec3 toVec3(final String x, final String y, final String z) {
        return Vec3.func_72443_a(this.toDouble(x), this.toDouble(y), this.toDouble(z));
    }
    
    public int toInt(final String s) {
        return Integer.parseInt(s);
    }
    
    public int toInt(final String s, final int min, final int max) {
        final int f = Integer.parseInt(s);
        return (f < min) ? min : ((f > max) ? max : f);
    }
    
    public int hex2dec(final String s) {
        if (!s.startsWith("0x") && !s.startsWith("0X") && s.indexOf(0) != 35) {
            return (int)(Long.decode("0x" + s) & -1L);
        }
        return (int)(Long.decode(s) & -1L);
    }
    
    public String[] splitParam(final String data) {
        return data.split("\\s*,\\s*");
    }
    
    public String[] splitParamSlash(final String data) {
        return data.split("\\s*/\\s*");
    }
    
    public boolean isValidData() throws Exception {
        return true;
    }
    
    public void loadItemData(final String item, final String data) {
    }
    
    public void loadItemData(final int fileLine, final String item, final String data) {
    }
    
    public void preReload() {
    }
    
    public void postReload() {
    }
    
    public boolean canReloadItem(final String item) {
        return false;
    }
    
    public boolean reload() {
        return this.reload(this);
    }
    
    private boolean reload(final MCH_BaseInfo info) {
        int line = 0;
        final MCH_InputFile inFile = new MCH_InputFile();
        final BufferedReader br = null;
        final File f = new File(info.filePath);
        try {
            if (inFile.openUTF8(f)) {
                info.preReload();
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
                    final String item = str.substring(0, eqIdx).trim().toLowerCase();
                    if (!info.canReloadItem(item)) {
                        continue;
                    }
                    info.loadItemData(item, str.substring(eqIdx + 1).trim());
                }
                line = 0;
                info.isValidData();
                info.postReload();
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
        return true;
    }
}
