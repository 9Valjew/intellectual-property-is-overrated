package mcheli.aircraft;

import mcheli.*;
import com.google.common.io.*;
import java.io.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import mcheli.wrapper.*;

public class MCH_PacketNotifyAmmoNum extends MCH_Packet
{
    public int entityID_Ac;
    public boolean all;
    public byte weaponID;
    public byte num;
    public short[] ammo;
    public short[] restAmmo;
    
    public MCH_PacketNotifyAmmoNum() {
        this.entityID_Ac = -1;
        this.all = false;
        this.weaponID = -1;
        this.num = 0;
        this.ammo = new short[0];
        this.restAmmo = new short[0];
    }
    
    @Override
    public int getMessageID() {
        return 268439604;
    }
    
    @Override
    public void readData(final ByteArrayDataInput data) {
        try {
            this.entityID_Ac = data.readInt();
            this.all = (data.readByte() != 0);
            if (this.all) {
                this.num = data.readByte();
                this.ammo = new short[this.num];
                this.restAmmo = new short[this.num];
                for (int i = 0; i < this.num; ++i) {
                    this.ammo[i] = data.readShort();
                    this.restAmmo[i] = data.readShort();
                }
            }
            else {
                this.weaponID = data.readByte();
                this.ammo = new short[] { data.readShort() };
                this.restAmmo = new short[] { data.readShort() };
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void writeData(final DataOutputStream dos) {
        try {
            dos.writeInt(this.entityID_Ac);
            dos.writeByte(this.all ? 1 : 0);
            if (this.all) {
                dos.writeByte(this.num);
                for (int i = 0; i < this.num; ++i) {
                    dos.writeShort(this.ammo[i]);
                    dos.writeShort(this.restAmmo[i]);
                }
            }
            else {
                dos.writeByte(this.weaponID);
                dos.writeShort(this.ammo[0]);
                dos.writeShort(this.restAmmo[0]);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void sendAllAmmoNum(final MCH_EntityAircraft ac, final EntityPlayer target) {
        final MCH_PacketNotifyAmmoNum s = new MCH_PacketNotifyAmmoNum();
        s.entityID_Ac = W_Entity.getEntityId(ac);
        s.all = true;
        s.num = (byte)ac.getWeaponNum();
        s.ammo = new short[s.num];
        s.restAmmo = new short[s.num];
        for (int i = 0; i < s.num; ++i) {
            s.ammo[i] = (short)ac.getWeapon(i).getAmmoNum();
            s.restAmmo[i] = (short)ac.getWeapon(i).getRestAllAmmoNum();
        }
        send(s, ac, target);
    }
    
    public static void sendAmmoNum(final MCH_EntityAircraft ac, final EntityPlayer target, final int wid) {
        sendAmmoNum(ac, target, wid, ac.getWeapon(wid).getAmmoNum(), ac.getWeapon(wid).getRestAllAmmoNum());
    }
    
    public static void sendAmmoNum(final MCH_EntityAircraft ac, final EntityPlayer target, final int wid, final int ammo, final int rest_ammo) {
        final MCH_PacketNotifyAmmoNum s = new MCH_PacketNotifyAmmoNum();
        s.entityID_Ac = W_Entity.getEntityId(ac);
        s.all = false;
        s.weaponID = (byte)wid;
        s.ammo = new short[] { (short)ammo };
        s.restAmmo = new short[] { (short)rest_ammo };
        send(s, ac, target);
    }
    
    public static void send(final MCH_PacketNotifyAmmoNum s, final MCH_EntityAircraft ac, final EntityPlayer target) {
        if (target == null) {
            for (int i = 0; i < ac.getSeatNum() + 1; ++i) {
                final Entity e = ac.getEntityBySeatId(i);
                if (e instanceof EntityPlayer) {
                    W_Network.sendToPlayer(s, (EntityPlayer)e);
                }
            }
        }
        else {
            W_Network.sendToPlayer(s, target);
        }
    }
}
