package mcheli.command;

import mcheli.*;
import com.google.common.io.*;
import java.io.*;
import mcheli.wrapper.*;

public class MCH_PacketCommandSave extends MCH_Packet
{
    public String str;
    
    public MCH_PacketCommandSave() {
        this.str = "";
    }
    
    @Override
    public int getMessageID() {
        return 536873729;
    }
    
    @Override
    public void readData(final ByteArrayDataInput data) {
        try {
            this.str = data.readUTF();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void writeData(final DataOutputStream dos) {
        try {
            dos.writeUTF(this.str);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void send(final String cmd) {
        final MCH_PacketCommandSave s = new MCH_PacketCommandSave();
        s.str = cmd;
        W_Network.sendToServer(s);
    }
}
