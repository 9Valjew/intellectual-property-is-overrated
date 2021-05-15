package mcheli.vehicle;

import mcheli.aircraft.*;
import com.google.common.io.*;
import java.io.*;

public class MCH_PacketVehiclePlayerControl extends MCH_PacketPlayerControlBase
{
    public byte switchFold;
    public int unhitchChainId;
    
    public MCH_PacketVehiclePlayerControl() {
        this.switchFold = -1;
        this.unhitchChainId = -1;
    }
    
    @Override
    public int getMessageID() {
        return 537002000;
    }
    
    @Override
    public void readData(final ByteArrayDataInput data) {
        super.readData(data);
        try {
            this.switchFold = data.readByte();
            this.unhitchChainId = data.readInt();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void writeData(final DataOutputStream dos) {
        super.writeData(dos);
        try {
            dos.writeByte(this.switchFold);
            dos.writeInt(this.unhitchChainId);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
