package mcheli.aircraft;

import com.google.common.io.*;
import java.io.*;
import mcheli.*;
import mcheli.wrapper.*;

public class MCH_PacketNotifyClientSetting extends MCH_Packet
{
    public boolean dismountAll;
    public boolean heliAutoThrottleDown;
    public boolean planeAutoThrottleDown;
    public boolean tankAutoThrottleDown;
    public boolean shaderSupport;
    
    public MCH_PacketNotifyClientSetting() {
        this.dismountAll = true;
        this.shaderSupport = false;
    }
    
    @Override
    public int getMessageID() {
        return 536875072;
    }
    
    @Override
    public void readData(final ByteArrayDataInput di) {
        try {
            byte data = 0;
            data = di.readByte();
            this.dismountAll = this.getBit(data, 0);
            this.heliAutoThrottleDown = this.getBit(data, 1);
            this.planeAutoThrottleDown = this.getBit(data, 2);
            this.tankAutoThrottleDown = this.getBit(data, 3);
            this.shaderSupport = this.getBit(data, 4);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void writeData(final DataOutputStream dos) {
        try {
            byte data = 0;
            data = this.setBit(data, 0, this.dismountAll);
            data = this.setBit(data, 1, this.heliAutoThrottleDown);
            data = this.setBit(data, 2, this.planeAutoThrottleDown);
            data = this.setBit(data, 3, this.tankAutoThrottleDown);
            data = this.setBit(data, 4, this.shaderSupport);
            dos.writeByte(data);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void send() {
        final MCH_PacketNotifyClientSetting mch_PacketNotifyClientSetting;
        final MCH_PacketNotifyClientSetting s = mch_PacketNotifyClientSetting = new MCH_PacketNotifyClientSetting();
        final MCH_Config config = MCH_MOD.config;
        mch_PacketNotifyClientSetting.dismountAll = MCH_Config.DismountAll.prmBool;
        final MCH_PacketNotifyClientSetting mch_PacketNotifyClientSetting2 = s;
        final MCH_Config config2 = MCH_MOD.config;
        mch_PacketNotifyClientSetting2.heliAutoThrottleDown = MCH_Config.AutoThrottleDownHeli.prmBool;
        final MCH_PacketNotifyClientSetting mch_PacketNotifyClientSetting3 = s;
        final MCH_Config config3 = MCH_MOD.config;
        mch_PacketNotifyClientSetting3.planeAutoThrottleDown = MCH_Config.AutoThrottleDownPlane.prmBool;
        final MCH_PacketNotifyClientSetting mch_PacketNotifyClientSetting4 = s;
        final MCH_Config config4 = MCH_MOD.config;
        mch_PacketNotifyClientSetting4.tankAutoThrottleDown = MCH_Config.AutoThrottleDownTank.prmBool;
        s.shaderSupport = W_EntityRenderer.isShaderSupport();
        W_Network.sendToServer(s);
    }
}
