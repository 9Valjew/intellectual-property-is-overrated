package mcheli.aircraft;

import mcheli.*;
import com.google.common.io.*;
import java.io.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import mcheli.wrapper.*;

public class MCH_PacketNotifyOnMountEntity extends MCH_Packet
{
    public int entityID_Ac;
    public int entityID_rider;
    public int seatID;
    
    public MCH_PacketNotifyOnMountEntity() {
        this.entityID_Ac = -1;
        this.entityID_rider = -1;
        this.seatID = -1;
    }
    
    @Override
    public int getMessageID() {
        return 268439632;
    }
    
    @Override
    public void readData(final ByteArrayDataInput data) {
        try {
            this.entityID_Ac = data.readInt();
            this.entityID_rider = data.readInt();
            this.seatID = data.readByte();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void writeData(final DataOutputStream dos) {
        try {
            dos.writeInt(this.entityID_Ac);
            dos.writeInt(this.entityID_rider);
            dos.writeByte(this.seatID);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void send(final MCH_EntityAircraft ac, final Entity rider, final int seatId) {
        if (ac == null || rider == null) {
            return;
        }
        final Entity pilot = ac.getRiddenByEntity();
        if (!(pilot instanceof EntityPlayer) || pilot.field_70128_L) {
            return;
        }
        final MCH_PacketNotifyOnMountEntity s = new MCH_PacketNotifyOnMountEntity();
        s.entityID_Ac = W_Entity.getEntityId(ac);
        s.entityID_rider = W_Entity.getEntityId(rider);
        s.seatID = seatId;
        W_Network.sendToPlayer(s, (EntityPlayer)pilot);
    }
}
