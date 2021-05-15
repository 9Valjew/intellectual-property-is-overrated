package mcheli.aircraft;

import mcheli.*;
import com.google.common.io.*;
import java.io.*;
import net.minecraft.entity.*;
import mcheli.wrapper.*;

public class MCH_PacketIndNotifyAmmoNum extends MCH_Packet
{
    public int entityID_Ac;
    public byte weaponID;
    
    public MCH_PacketIndNotifyAmmoNum() {
        this.entityID_Ac = -1;
        this.weaponID = -1;
    }
    
    @Override
    public int getMessageID() {
        return 536875061;
    }
    
    @Override
    public void readData(final ByteArrayDataInput data) {
        try {
            this.entityID_Ac = data.readInt();
            this.weaponID = data.readByte();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void writeData(final DataOutputStream dos) {
        try {
            dos.writeInt(this.entityID_Ac);
            dos.writeByte(this.weaponID);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void send(final MCH_EntityAircraft ac, final int wid) {
        final MCH_PacketIndNotifyAmmoNum s = new MCH_PacketIndNotifyAmmoNum();
        s.entityID_Ac = W_Entity.getEntityId(ac);
        s.weaponID = (byte)wid;
        W_Network.sendToServer(s);
    }
}
