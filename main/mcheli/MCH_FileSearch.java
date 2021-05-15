package mcheli;

import java.io.*;
import java.util.*;
import java.text.*;

public class MCH_FileSearch
{
    public static final int TYPE_FILE_OR_DIR = 1;
    public static final int TYPE_FILE = 2;
    public static final int TYPE_DIR = 3;
    private TreeSet set;
    
    public MCH_FileSearch() {
        this.set = new TreeSet();
    }
    
    public File[] listFiles(final String directoryPath, String fileName) {
        if (fileName != null) {
            fileName = fileName.replace(".", "\\.");
            fileName = fileName.replace("*", ".*");
        }
        return this.listFiles(directoryPath, fileName, 2, true, 0);
    }
    
    public File[] listFiles(final String directoryPath, final String fileNamePattern, final int type, final boolean isRecursive, final int period) {
        final File dir = new File(directoryPath);
        if (!dir.isDirectory()) {
            throw new IllegalArgumentException("\u5f15\u6570\u3067\u6307\u5b9a\u3055\u308c\u305f\u30d1\u30b9[" + dir.getAbsolutePath() + "]\u306f\u30c7\u30a3\u30ec\u30af\u30c8\u30ea\u3067\u306f\u3042\u308a\u307e\u305b\u3093\u3002");
        }
        final File[] files = dir.listFiles();
        for (int i = 0; i < files.length; ++i) {
            final File file = files[i];
            this.addFile(type, fileNamePattern, this.set, file, period);
            if (isRecursive && file.isDirectory()) {
                this.listFiles(file.getAbsolutePath(), fileNamePattern, type, isRecursive, period);
            }
        }
        return (File[])this.set.toArray(new File[this.set.size()]);
    }
    
    private void addFile(final int type, final String match, final TreeSet set, final File file, final int period) {
        switch (type) {
            case 2: {
                if (!file.isFile()) {
                    return;
                }
                break;
            }
            case 3: {
                if (!file.isDirectory()) {
                    return;
                }
                break;
            }
        }
        if (match != null && !file.getName().matches(match)) {
            return;
        }
        if (period != 0) {
            final Date lastModifiedDate = new Date(file.lastModified());
            final String lastModifiedDateStr = new SimpleDateFormat("yyyyMMdd").format(lastModifiedDate);
            final long oneDayTime = 86400000L;
            final long periodTime = oneDayTime * Math.abs(period);
            final Date designatedDate = new Date(System.currentTimeMillis() - periodTime);
            final String designatedDateStr = new SimpleDateFormat("yyyyMMdd").format(designatedDate);
            if (period > 0) {
                if (lastModifiedDateStr.compareTo(designatedDateStr) < 0) {
                    return;
                }
            }
            else if (lastModifiedDateStr.compareTo(designatedDateStr) > 0) {
                return;
            }
        }
        set.add(file);
    }
    
    public void clear() {
        this.set.clear();
    }
}
