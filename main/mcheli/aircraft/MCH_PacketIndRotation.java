package mcheli.aircraft;

import mcheli.*;
import com.google.common.io.*;
import java.io.*;
import net.minecraft.entity.*;
import mcheli.wrapper.*;

public class MCH_PacketIndRotation extends MCH_Packet
{
    public int entityID_Ac;
    public float yaw;
    public float pitch;
    public float roll;
    public boolean rollRev;
    
    public MCH_PacketIndRotation() {
        this.entityID_Ac = -1;
        this.yaw = 0.0f;
        this.pitch = 0.0f;
        this.roll = 0.0f;
        this.rollRev = false;
    }
    
    @Override
    public int getMessageID() {
        return 536875062;
    }
    
    @Override
    public void readData(final ByteArrayDataInput data) {
        try {
            this.entityID_Ac = data.readInt();
            this.yaw = data.readFloat();
            this.pitch = data.readFloat();
            this.roll = data.readFloat();
            this.rollRev = (data.readByte() != 0);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void writeData(final DataOutputStream dos) {
        try {
            dos.writeInt(this.entityID_Ac);
            dos.writeFloat(this.yaw);
            dos.writeFloat(this.pitch);
            dos.writeFloat(this.roll);
            dos.writeByte(this.rollRev ? 1 : 0);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void send(final MCH_EntityAircraft ac) {
        if (ac == null) {
            return;
        }
        final MCH_PacketIndRotation s = new MCH_PacketIndRotation();
        s.entityID_Ac = W_Entity.getEntityId(ac);
        s.yaw = ac.getRotYaw();
        s.pitch = ac.getRotPitch();
        s.roll = ac.getRotRoll();
        s.rollRev = ac.aircraftRollRev;
        W_Network.sendToServer(s);
    }
}
