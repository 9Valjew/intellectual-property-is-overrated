package mcheli.multiplay;

import mcheli.*;
import com.google.common.io.*;
import java.io.*;
import mcheli.wrapper.*;

public class MCH_PacketIndMultiplayCommand extends MCH_Packet
{
    public int CmdID;
    public String CmdStr;
    
    public MCH_PacketIndMultiplayCommand() {
        this.CmdID = -1;
    }
    
    @Override
    public int getMessageID() {
        return 536873088;
    }
    
    @Override
    public void readData(final ByteArrayDataInput data) {
        try {
            this.CmdID = data.readInt();
            this.CmdStr = data.readUTF();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void writeData(final DataOutputStream dos) {
        try {
            dos.writeInt(this.CmdID);
            dos.writeUTF(this.CmdStr);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void send(final int cmd_id, final String str) {
        if (cmd_id <= 0) {
            return;
        }
        final MCH_PacketIndMultiplayCommand s = new MCH_PacketIndMultiplayCommand();
        s.CmdID = cmd_id;
        s.CmdStr = str;
        W_Network.sendToServer(s);
    }
}
