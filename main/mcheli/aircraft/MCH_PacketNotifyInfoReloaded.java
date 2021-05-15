package mcheli.aircraft;

import mcheli.*;
import com.google.common.io.*;
import java.io.*;
import mcheli.wrapper.*;

public class MCH_PacketNotifyInfoReloaded extends MCH_Packet
{
    public int type;
    
    public MCH_PacketNotifyInfoReloaded() {
        this.type = -1;
    }
    
    @Override
    public int getMessageID() {
        return 536875063;
    }
    
    @Override
    public void readData(final ByteArrayDataInput data) {
        try {
            this.type = data.readInt();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void writeData(final DataOutputStream dos) {
        try {
            dos.writeInt(this.type);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void sendRealodAc() {
        final MCH_PacketNotifyInfoReloaded s = new MCH_PacketNotifyInfoReloaded();
        s.type = 0;
        W_Network.sendToServer(s);
    }
    
    public static void sendRealodAllWeapon() {
        final MCH_PacketNotifyInfoReloaded s = new MCH_PacketNotifyInfoReloaded();
        s.type = 1;
        W_Network.sendToServer(s);
    }
}
