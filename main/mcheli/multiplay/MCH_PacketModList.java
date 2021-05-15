package mcheli.multiplay;

import mcheli.*;
import com.google.common.io.*;
import java.io.*;
import java.util.*;
import net.minecraft.entity.player.*;
import mcheli.wrapper.*;

public class MCH_PacketModList extends MCH_Packet
{
    public boolean firstData;
    public int id;
    public int num;
    public List<String> list;
    
    public MCH_PacketModList() {
        this.firstData = false;
        this.id = 0;
        this.num = 0;
        this.list = new ArrayList<String>();
    }
    
    @Override
    public int getMessageID() {
        return 536873473;
    }
    
    @Override
    public void readData(final ByteArrayDataInput data) {
        try {
            this.firstData = (data.readByte() == 1);
            this.id = data.readInt();
            this.num = data.readInt();
            for (int i = 0; i < this.num; ++i) {
                this.list.add(data.readUTF());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void writeData(final DataOutputStream dos) {
        try {
            dos.writeByte(this.firstData ? 1 : 0);
            dos.writeInt(this.id);
            dos.writeInt(this.num);
            for (final String s : this.list) {
                dos.writeUTF(s);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void send(final EntityPlayer player, final MCH_PacketModList p) {
        W_Network.sendToPlayer(p, player);
    }
    
    public static void send(final List<String> list, final int id) {
        MCH_PacketModList p = null;
        int size = 0;
        boolean isFirst = true;
        for (final String s : list) {
            if (p == null) {
                p = new MCH_PacketModList();
                p.id = id;
                p.firstData = isFirst;
                isFirst = false;
            }
            p.list.add(s);
            size += s.length() + 2;
            if (size > 1024) {
                p.num = p.list.size();
                W_Network.sendToServer(p);
                p = null;
                size = 0;
            }
        }
        if (p != null) {
            p.num = p.list.size();
            W_Network.sendToServer(p);
        }
    }
}
