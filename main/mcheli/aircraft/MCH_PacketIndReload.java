package mcheli.aircraft;

import mcheli.*;
import com.google.common.io.*;
import java.io.*;
import net.minecraft.entity.*;
import mcheli.wrapper.*;

public class MCH_PacketIndReload extends MCH_Packet
{
    public int entityID_Ac;
    public int weaponID;
    
    public MCH_PacketIndReload() {
        this.entityID_Ac = -1;
        this.weaponID = -1;
    }
    
    @Override
    public int getMessageID() {
        return 536875059;
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
    
    public static void send(final MCH_EntityAircraft ac, final int weaponId) {
        if (ac == null) {
            return;
        }
        final MCH_PacketIndReload s = new MCH_PacketIndReload();
        s.entityID_Ac = W_Entity.getEntityId(ac);
        s.weaponID = weaponId;
        W_Network.sendToServer(s);
    }
}
