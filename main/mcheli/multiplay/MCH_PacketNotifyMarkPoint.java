package mcheli.multiplay;

import mcheli.*;
import com.google.common.io.*;
import java.io.*;
import net.minecraft.entity.player.*;
import mcheli.wrapper.*;

public class MCH_PacketNotifyMarkPoint extends MCH_Packet
{
    public int px;
    public int py;
    public int pz;
    
    public MCH_PacketNotifyMarkPoint() {
        final boolean b = false;
        this.pz = (b ? 1 : 0);
        this.px = (b ? 1 : 0);
        this.py = 0;
    }
    
    @Override
    public int getMessageID() {
        return 268437762;
    }
    
    @Override
    public void readData(final ByteArrayDataInput data) {
        try {
            this.px = data.readInt();
            this.py = data.readInt();
            this.pz = data.readInt();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void writeData(final DataOutputStream dos) {
        try {
            dos.writeInt(this.px);
            dos.writeInt(this.py);
            dos.writeInt(this.pz);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void send(final EntityPlayer player, final int x, final int y, final int z) {
        final MCH_PacketNotifyMarkPoint pkt = new MCH_PacketNotifyMarkPoint();
        pkt.px = x;
        pkt.py = y;
        pkt.pz = z;
        W_Network.sendToPlayer(pkt, player);
    }
}
