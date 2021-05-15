package mcheli.aircraft;

import mcheli.*;
import com.google.common.io.*;
import java.io.*;

public class MCH_PacketSeatPlayerControl extends MCH_Packet
{
    public boolean isUnmount;
    public byte switchSeat;
    public boolean parachuting;
    
    public MCH_PacketSeatPlayerControl() {
        this.isUnmount = false;
        this.switchSeat = 0;
    }
    
    @Override
    public int getMessageID() {
        return 536875040;
    }
    
    @Override
    public void readData(final ByteArrayDataInput data) {
        try {
            final byte bf = data.readByte();
            this.isUnmount = ((bf >> 3 & 0x1) != 0x0);
            this.switchSeat = (byte)(bf >> 1 & 0x3);
            this.parachuting = ((bf >> 0 & 0x1) != 0x0);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void writeData(final DataOutputStream dos) {
        try {
            final byte bf = (byte)((this.isUnmount ? 8 : 0) | this.switchSeat << 1 | (this.parachuting ? 1 : 0));
            dos.writeByte(bf);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
