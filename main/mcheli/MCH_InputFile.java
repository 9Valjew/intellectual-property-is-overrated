package mcheli;

import java.io.*;

public class MCH_InputFile
{
    public File file;
    public BufferedReader br;
    
    public MCH_InputFile() {
        this.file = null;
        this.br = null;
    }
    
    public boolean open(final String path) {
        this.close();
        this.file = new File(path);
        final String filePath = this.file.getAbsolutePath();
        try {
            this.br = new BufferedReader(new FileReader(this.file));
        }
        catch (FileNotFoundException e) {
            MCH_Lib.DbgLog(true, "FILE open failed MCH_InputFile.open:" + filePath, new Object[0]);
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean openUTF8(final File file) {
        return this.openUTF8(file.getPath());
    }
    
    public boolean openUTF8(final String path) {
        this.close();
        this.file = new File(path);
        try {
            this.br = new BufferedReader(new InputStreamReader(new FileInputStream(this.file), "UTF-8"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public String readLine() {
        try {
            return (this.br != null) ? this.br.readLine() : null;
        }
        catch (IOException e) {
            return null;
        }
    }
    
    public void close() {
        try {
            if (this.br != null) {
                this.br.close();
            }
        }
        catch (IOException ex) {}
        this.br = null;
    }
}
