package mcheli.tank;

import mcheli.aircraft.*;
import com.google.common.io.*;
import java.io.*;

public class MCH_TankPacketPlayerControl extends MCH_PacketPlayerControlBase
{
    public byte switchVtol;
    
    public MCH_TankPacketPlayerControl() {
        this.switchVtol = -1;
    }
    
    @Override
    public int getMessageID() {
        return 537919504;
    }
    
    @Override
    public void readData(final ByteArrayDataInput data) {
        super.readData(data);
        try {
            this.switchVtol = data.readByte();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void writeData(final DataOutputStream dos) {
        super.writeData(dos);
        try {
            dos.writeByte(this.switchVtol);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
