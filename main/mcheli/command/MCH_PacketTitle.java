package mcheli.command;

import mcheli.*;
import net.minecraft.util.*;
import com.google.common.io.*;
import java.io.*;
import mcheli.wrapper.*;

public class MCH_PacketTitle extends MCH_Packet
{
    public IChatComponent chatComponent;
    public int showTime;
    public int position;
    
    public MCH_PacketTitle() {
        this.chatComponent = null;
        this.showTime = 1;
        this.position = 0;
    }
    
    @Override
    public int getMessageID() {
        return 268438272;
    }
    
    @Override
    public void readData(final ByteArrayDataInput data) {
        try {
            this.chatComponent = IChatComponent.Serializer.func_150699_a(data.readUTF());
            this.showTime = data.readShort();
            this.position = data.readShort();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void writeData(final DataOutputStream dos) {
        try {
            dos.writeUTF(IChatComponent.Serializer.func_150696_a(this.chatComponent));
            dos.writeShort(this.showTime);
            dos.writeShort(this.position);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void send(final IChatComponent chat, final int showTime, final int pos) {
        final MCH_PacketTitle s = new MCH_PacketTitle();
        s.chatComponent = chat;
        s.showTime = showTime;
        s.position = pos;
        W_Network.sendToAllPlayers(s);
    }
}
