package mcheli.aircraft;

import mcheli.*;
import com.google.common.io.*;
import java.io.*;
import net.minecraft.entity.*;
import mcheli.wrapper.*;

public class MCH_PacketSeatListRequest extends MCH_Packet
{
    public int entityID_AC;
    
    public MCH_PacketSeatListRequest() {
        this.entityID_AC = -1;
    }
    
    @Override
    public int getMessageID() {
        return 536875024;
    }
    
    @Override
    public void readData(final ByteArrayDataInput data) {
        try {
            this.entityID_AC = data.readInt();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void writeData(final DataOutputStream dos) {
        try {
            dos.writeInt(this.entityID_AC);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void requestSeatList(final MCH_EntityAircraft ac) {
        final MCH_PacketSeatListRequest s = new MCH_PacketSeatListRequest();
        s.entityID_AC = W_Entity.getEntityId(ac);
        W_Network.sendToServer(s);
    }
}
