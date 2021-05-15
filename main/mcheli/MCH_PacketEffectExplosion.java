package mcheli;

import com.google.common.io.*;
import java.io.*;
import mcheli.wrapper.*;

public class MCH_PacketEffectExplosion extends MCH_Packet
{
    ExplosionParam prm;
    
    public MCH_PacketEffectExplosion() {
        this.prm = new ExplosionParam();
    }
    
    @Override
    public int getMessageID() {
        return 268437520;
    }
    
    @Override
    public void readData(final ByteArrayDataInput data) {
        try {
            this.prm.posX = data.readDouble();
            this.prm.posY = data.readDouble();
            this.prm.posZ = data.readDouble();
            this.prm.size = data.readFloat();
            this.prm.exploderID = data.readInt();
            this.prm.inWater = (data.readByte() != 0);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void writeData(final DataOutputStream dos) {
        try {
            dos.writeDouble(this.prm.posX);
            dos.writeDouble(this.prm.posY);
            dos.writeDouble(this.prm.posZ);
            dos.writeFloat(this.prm.size);
            dos.writeInt(this.prm.exploderID);
            dos.writeByte(this.prm.inWater ? 1 : 0);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static ExplosionParam create() {
        return new MCH_PacketEffectExplosion().aaa();
    }
    
    private ExplosionParam aaa() {
        return new ExplosionParam();
    }
    
    public static void send(final ExplosionParam param) {
        if (param != null) {
            final MCH_PacketEffectExplosion s = new MCH_PacketEffectExplosion();
            s.prm = param;
            W_Network.sendToAllPlayers(s);
        }
    }
    
    public class ExplosionParam
    {
        public double posX;
        public double posY;
        public double posZ;
        public float size;
        public int exploderID;
        public boolean inWater;
    }
}
