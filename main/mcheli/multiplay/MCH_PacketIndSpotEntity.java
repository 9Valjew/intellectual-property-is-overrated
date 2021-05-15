package mcheli.multiplay;

import mcheli.*;
import com.google.common.io.*;
import java.io.*;
import net.minecraft.entity.*;
import mcheli.wrapper.*;

public class MCH_PacketIndSpotEntity extends MCH_Packet
{
    public int targetFilter;
    
    public MCH_PacketIndSpotEntity() {
        this.targetFilter = -1;
    }
    
    @Override
    public int getMessageID() {
        return 536873216;
    }
    
    @Override
    public void readData(final ByteArrayDataInput data) {
        try {
            this.targetFilter = data.readInt();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void writeData(final DataOutputStream dos) {
        try {
            dos.writeInt(this.targetFilter);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void send(final EntityLivingBase spoter, final int targetFilter) {
        final MCH_PacketIndSpotEntity s = new MCH_PacketIndSpotEntity();
        s.targetFilter = targetFilter;
        W_Network.sendToServer(s);
    }
}
