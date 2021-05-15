package mcheli;

import java.io.*;

public class MCH_OStream extends ByteArrayOutputStream
{
    public int index;
    public static final int SIZE = 30720;
    
    public MCH_OStream() {
        this.index = 0;
    }
    
    public void write(final DataOutputStream dos) {
        try {
            int datasize;
            if (this.index + 30720 <= this.size()) {
                datasize = 30720;
            }
            else {
                datasize = this.size() - this.index;
            }
            dos.writeInt(this.index);
            dos.writeInt(datasize);
            dos.writeInt(this.size());
            dos.write(this.buf, this.index, datasize);
            this.index += datasize;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean isDataEnd() {
        return this.index >= this.size();
    }
}
