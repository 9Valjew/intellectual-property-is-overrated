package mcheli;

import com.google.common.io.*;
import java.io.*;
import net.minecraft.server.*;
import mcheli.wrapper.*;
import net.minecraft.entity.player.*;

public class MCH_PacketNotifyServerSettings extends MCH_Packet
{
    public boolean enableCamDistChange;
    public boolean enableEntityMarker;
    public boolean enablePVP;
    public double stingerLockRange;
    public boolean enableDebugBoundingBox;
    
    public MCH_PacketNotifyServerSettings() {
        this.enableCamDistChange = true;
        this.enableEntityMarker = true;
        this.enablePVP = true;
        this.stingerLockRange = 120.0;
        this.enableDebugBoundingBox = true;
    }
    
    @Override
    public int getMessageID() {
        return 268437568;
    }
    
    @Override
    public void readData(final ByteArrayDataInput data) {
        try {
            final byte b = data.readByte();
            this.enableCamDistChange = this.getBit(b, 0);
            this.enableEntityMarker = this.getBit(b, 1);
            this.enablePVP = this.getBit(b, 2);
            this.stingerLockRange = data.readFloat();
            this.enableDebugBoundingBox = this.getBit(b, 3);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void writeData(final DataOutputStream dos) {
        try {
            byte b = 0;
            b = this.setBit(b, 0, this.enableCamDistChange);
            b = this.setBit(b, 1, this.enableEntityMarker);
            b = this.setBit(b, 2, this.enablePVP);
            b = this.setBit(b, 3, this.enableDebugBoundingBox);
            dos.writeByte(b);
            dos.writeFloat((float)this.stingerLockRange);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void send(final EntityPlayerMP player) {
        final MCH_PacketNotifyServerSettings mch_PacketNotifyServerSettings;
        final MCH_PacketNotifyServerSettings s = mch_PacketNotifyServerSettings = new MCH_PacketNotifyServerSettings();
        final MCH_Config config = MCH_MOD.config;
        mch_PacketNotifyServerSettings.enableCamDistChange = !MCH_Config.DisableCameraDistChange.prmBool;
        final MCH_PacketNotifyServerSettings mch_PacketNotifyServerSettings2 = s;
        final MCH_Config config2 = MCH_MOD.config;
        mch_PacketNotifyServerSettings2.enableEntityMarker = MCH_Config.DisplayEntityMarker.prmBool;
        s.enablePVP = MinecraftServer.func_71276_C().func_71219_W();
        final MCH_PacketNotifyServerSettings mch_PacketNotifyServerSettings3 = s;
        final MCH_Config config3 = MCH_MOD.config;
        mch_PacketNotifyServerSettings3.stingerLockRange = MCH_Config.StingerLockRange.prmDouble;
        final MCH_PacketNotifyServerSettings mch_PacketNotifyServerSettings4 = s;
        final MCH_Config config4 = MCH_MOD.config;
        mch_PacketNotifyServerSettings4.enableDebugBoundingBox = MCH_Config.EnableDebugBoundingBox.prmBool;
        if (player != null) {
            W_Network.sendToPlayer(s, (EntityPlayer)player);
        }
        else {
            W_Network.sendToAllPlayers(s);
        }
    }
    
    public static void sendAll() {
        send(null);
    }
}
