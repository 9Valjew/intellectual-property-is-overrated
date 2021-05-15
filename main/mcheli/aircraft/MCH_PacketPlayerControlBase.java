package mcheli.aircraft;

import mcheli.*;
import com.google.common.io.*;
import java.io.*;

public abstract class MCH_PacketPlayerControlBase extends MCH_Packet
{
    public byte isUnmount;
    public byte switchMode;
    public byte switchCameraMode;
    public byte switchWeapon;
    public byte useFlareType;
    public boolean useWeapon;
    public int useWeaponOption1;
    public int useWeaponOption2;
    public double useWeaponPosX;
    public double useWeaponPosY;
    public double useWeaponPosZ;
    public boolean throttleUp;
    public boolean throttleDown;
    public boolean moveLeft;
    public boolean moveRight;
    public boolean openGui;
    public byte switchHatch;
    public byte switchFreeLook;
    public byte switchGear;
    public boolean ejectSeat;
    public byte putDownRack;
    public boolean switchSearchLight;
    public boolean useBrake;
    
    public MCH_PacketPlayerControlBase() {
        this.isUnmount = 0;
        this.switchMode = -1;
        this.switchCameraMode = 0;
        this.switchWeapon = -1;
        this.useFlareType = 0;
        this.useWeapon = false;
        this.useWeaponOption1 = 0;
        this.useWeaponOption2 = 0;
        this.useWeaponPosX = 0.0;
        this.useWeaponPosY = 0.0;
        this.useWeaponPosZ = 0.0;
        this.throttleUp = false;
        this.throttleDown = false;
        this.moveLeft = false;
        this.moveRight = false;
        this.switchHatch = 0;
        this.switchFreeLook = 0;
        this.switchGear = 0;
        this.ejectSeat = false;
        this.putDownRack = 0;
        this.switchSearchLight = false;
        this.useBrake = false;
    }
    
    @Override
    public void readData(final ByteArrayDataInput data) {
        try {
            short bf = data.readShort();
            this.useWeapon = this.getBit(bf, 0);
            this.throttleUp = this.getBit(bf, 1);
            this.throttleDown = this.getBit(bf, 2);
            this.moveLeft = this.getBit(bf, 3);
            this.moveRight = this.getBit(bf, 4);
            this.switchSearchLight = this.getBit(bf, 5);
            this.ejectSeat = this.getBit(bf, 6);
            this.openGui = this.getBit(bf, 7);
            this.useBrake = this.getBit(bf, 8);
            bf = data.readByte();
            this.putDownRack = (byte)(bf >> 6 & 0x3);
            this.isUnmount = (byte)(bf >> 4 & 0x3);
            this.useFlareType = (byte)(bf >> 0 & 0xF);
            this.switchMode = data.readByte();
            this.switchWeapon = data.readByte();
            if (this.useWeapon) {
                this.useWeaponOption1 = data.readInt();
                this.useWeaponOption2 = data.readInt();
                this.useWeaponPosX = data.readDouble();
                this.useWeaponPosY = data.readDouble();
                this.useWeaponPosZ = data.readDouble();
            }
            bf = data.readByte();
            this.switchCameraMode = (byte)(bf >> 6 & 0x3);
            this.switchHatch = (byte)(bf >> 4 & 0x3);
            this.switchFreeLook = (byte)(bf >> 2 & 0x3);
            this.switchGear = (byte)(bf >> 0 & 0x3);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void writeData(final DataOutputStream dos) {
        try {
            short bf = 0;
            bf = this.setBit(bf, 0, this.useWeapon);
            bf = this.setBit(bf, 1, this.throttleUp);
            bf = this.setBit(bf, 2, this.throttleDown);
            bf = this.setBit(bf, 3, this.moveLeft);
            bf = this.setBit(bf, 4, this.moveRight);
            bf = this.setBit(bf, 5, this.switchSearchLight);
            bf = this.setBit(bf, 6, this.ejectSeat);
            bf = this.setBit(bf, 7, this.openGui);
            bf = this.setBit(bf, 8, this.useBrake);
            dos.writeShort(bf);
            bf = (byte)((this.putDownRack & 0x3) << 6 | (this.isUnmount & 0x3) << 4 | (this.useFlareType & 0xF));
            dos.writeByte(bf);
            dos.writeByte(this.switchMode);
            dos.writeByte(this.switchWeapon);
            if (this.useWeapon) {
                dos.writeInt(this.useWeaponOption1);
                dos.writeInt(this.useWeaponOption2);
                dos.writeDouble(this.useWeaponPosX);
                dos.writeDouble(this.useWeaponPosY);
                dos.writeDouble(this.useWeaponPosZ);
            }
            bf = (byte)((this.switchCameraMode & 0x3) << 6 | (this.switchHatch & 0x3) << 4 | (this.switchFreeLook & 0x3) << 2 | (this.switchGear & 0x3) << 0);
            dos.writeByte(bf);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
