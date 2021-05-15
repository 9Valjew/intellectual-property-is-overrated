package mcheli;

import java.io.*;

public class MCH_OutputFile
{
    public File file;
    public PrintWriter pw;
    
    public MCH_OutputFile() {
        this.file = null;
        this.pw = null;
    }
    
    public boolean open(final String path) {
        this.close();
        this.file = new File(path);
        try {
            this.pw = new PrintWriter(this.file);
        }
        catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }
    
    public boolean openUTF8(final String path) {
        this.close();
        this.file = new File(path);
        try {
            this.pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.file), "UTF-8"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public void writeLine(final String s) {
        if (this.pw != null && s != null) {
            this.pw.println(s);
        }
    }
    
    public void close() {
        if (this.pw != null) {
            this.pw.close();
        }
        this.pw = null;
    }
}
