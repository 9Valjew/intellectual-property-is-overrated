package mcheli.aircraft;

import net.minecraft.entity.*;
import mcheli.*;
import mcheli.wrapper.*;

public class MCH_Parts
{
    public final Entity parent;
    public final DataWatcher dataWatcher;
    public final int shift;
    public final int dataIndex;
    public final String partName;
    public float prevRotation;
    public float rotation;
    public float rotationMax;
    public float rotationInv;
    public Sound soundStartSwichOn;
    public Sound soundEndSwichOn;
    public Sound soundSwitching;
    public Sound soundStartSwichOff;
    public Sound soundEndSwichOff;
    private boolean status;
    
    public MCH_Parts(final Entity parent, final int shiftBit, final int dataWatcherIndex, final String name) {
        this.prevRotation = 0.0f;
        this.rotation = 0.0f;
        this.rotationMax = 90.0f;
        this.rotationInv = 1.0f;
        this.soundStartSwichOn = new Sound();
        this.soundEndSwichOn = new Sound();
        this.soundSwitching = new Sound();
        this.soundStartSwichOff = new Sound();
        this.soundEndSwichOff = new Sound();
        this.status = false;
        this.parent = parent;
        this.dataWatcher = parent.func_70096_w();
        this.shift = shiftBit;
        this.dataIndex = dataWatcherIndex;
        this.status = ((this.getDataWatcherValue() & 1 << this.shift) != 0x0);
        this.partName = name;
    }
    
    public int getDataWatcherValue() {
        return this.dataWatcher.func_75679_c(this.dataIndex);
    }
    
    public void setStatusServer(final boolean stat) {
        this.setStatusServer(stat, true);
    }
    
    public void setStatusServer(final boolean stat, final boolean playSound) {
        if (!this.parent.field_70170_p.field_72995_K && this.getStatus() != stat) {
            MCH_Lib.DbgLog(false, "setStatusServer(ID=%d %s :%s -> %s)", this.shift, this.partName, this.getStatus() ? "ON" : "OFF", stat ? "ON" : "OFF");
            this.updateDataWatcher(stat);
            this.playSound(this.soundSwitching);
            if (!stat) {
                this.playSound(this.soundStartSwichOff);
            }
            else {
                this.playSound(this.soundStartSwichOn);
            }
            this.update();
        }
    }
    
    protected void updateDataWatcher(final boolean stat) {
        final int currentStatus = this.dataWatcher.func_75679_c(this.dataIndex);
        final int mask = 1 << this.shift;
        if (!stat) {
            this.dataWatcher.func_75692_b(this.dataIndex, (Object)(currentStatus & ~mask));
        }
        else {
            this.dataWatcher.func_75692_b(this.dataIndex, (Object)(currentStatus | mask));
        }
        this.status = stat;
    }
    
    public boolean getStatus() {
        return this.status;
    }
    
    public boolean isOFF() {
        return !this.status && this.rotation <= 0.02f;
    }
    
    public boolean isON() {
        return this.status && this.rotation >= this.rotationMax - 0.02f;
    }
    
    public void updateStatusClient(final int statFromDataWatcher) {
        if (this.parent.field_70170_p.field_72995_K) {
            this.status = ((statFromDataWatcher & 1 << this.shift) != 0x0);
        }
    }
    
    public void update() {
        this.prevRotation = this.rotation;
        if (this.getStatus()) {
            if (this.rotation < this.rotationMax) {
                this.rotation += this.rotationInv;
                if (this.rotation >= this.rotationMax) {
                    this.playSound(this.soundEndSwichOn);
                }
            }
        }
        else if (this.rotation > 0.0f) {
            this.rotation -= this.rotationInv;
            if (this.rotation <= 0.0f) {
                this.playSound(this.soundEndSwichOff);
            }
        }
    }
    
    public void forceSwitch(final boolean onoff) {
        this.updateDataWatcher(onoff);
        final float rotationMax = this.rotationMax;
        this.prevRotation = rotationMax;
        this.rotation = rotationMax;
    }
    
    public float getFactor() {
        if (this.rotationMax > 0.0f) {
            return this.rotation / this.rotationMax;
        }
        return 0.0f;
    }
    
    public void playSound(final Sound snd) {
        if (!snd.name.isEmpty() && !this.parent.field_70170_p.field_72995_K) {
            W_WorldFunc.MOD_playSoundAtEntity(this.parent, snd.name, snd.volume, snd.pitch);
        }
    }
    
    public class Sound
    {
        public String name;
        public float volume;
        public float pitch;
        
        public Sound() {
            this.name = "";
            this.volume = 1.0f;
            this.pitch = 1.0f;
        }
        
        public void setPrm(final String n, final float v, final float p) {
            this.name = n;
            this.volume = v;
            this.pitch = p;
        }
    }
}
