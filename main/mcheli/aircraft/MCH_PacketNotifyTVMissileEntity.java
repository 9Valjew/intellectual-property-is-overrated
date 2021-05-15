package mcheli.aircraft;

import mcheli.*;
import com.google.common.io.*;
import java.io.*;
import mcheli.wrapper.*;

public class MCH_PacketNotifyTVMissileEntity extends MCH_Packet
{
    public int entityID_Ac;
    public int entityID_TVMissile;
    
    public MCH_PacketNotifyTVMissileEntity() {
        this.entityID_Ac = -1;
        this.entityID_TVMissile = -1;
    }
    
    @Override
    public int getMessageID() {
        return 268439600;
    }
    
    @Override
    public void readData(final ByteArrayDataInput data) {
        try {
            this.entityID_Ac = data.readInt();
            this.entityID_TVMissile = data.readInt();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void writeData(final DataOutputStream dos) {
        try {
            dos.writeInt(this.entityID_Ac);
            dos.writeInt(this.entityID_TVMissile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void send(final int heliEntityID, final int tvMissileEntityID) {
        final MCH_PacketNotifyTVMissileEntity s = new MCH_PacketNotifyTVMissileEntity();
        s.entityID_Ac = heliEntityID;
        s.entityID_TVMissile = tvMissileEntityID;
        W_Network.sendToAllPlayers(s);
    }
}
