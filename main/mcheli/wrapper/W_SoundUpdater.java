package mcheli.wrapper;

import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;

public class W_SoundUpdater
{
    protected final SoundHandler theSoundHnadler;
    protected W_Sound es;
    
    public W_SoundUpdater(final Minecraft minecraft, final Entity entity) {
        this.theSoundHnadler = minecraft.func_147118_V();
    }
    
    public void initEntitySound(final String name) {
        (this.es = new W_Sound(new ResourceLocation("mcheli", name), 1.0f, 1.0f)).setRepeat(true);
    }
    
    public boolean isValidSound() {
        return this.es != null;
    }
    
    public void playEntitySound(final String name, final Entity entity, final float volume, final float pitch, final boolean par5) {
        if (this.isValidSound()) {
            this.es.setSoundParam(entity, volume, pitch);
            this.theSoundHnadler.func_147682_a((ISound)this.es);
        }
    }
    
    public void stopEntitySound(final Entity entity) {
        if (this.isValidSound()) {
            this.theSoundHnadler.func_147683_b((ISound)this.es);
        }
    }
    
    public boolean isEntitySoundPlaying(final Entity entity) {
        return this.isValidSound() && this.theSoundHnadler.func_147692_c((ISound)this.es);
    }
    
    public void setEntitySoundPitch(final Entity entity, final float pitch) {
        if (this.isValidSound()) {
            this.es.setPitch(pitch);
        }
    }
    
    public void setEntitySoundVolume(final Entity entity, final float volume) {
        if (this.isValidSound()) {
            this.es.setVolume(volume);
        }
    }
    
    public void updateSoundLocation(final Entity entity) {
        if (this.isValidSound()) {
            this.es.setPosition(entity);
        }
    }
    
    public void updateSoundLocation(final double x, final double y, final double z) {
        if (this.isValidSound()) {
            this.es.setPosition(x, y, z);
        }
    }
    
    public void _updateSoundLocation(final Entity entityListener, final Entity entity) {
        if (this.isValidSound()) {
            this.es.setPosition(entity);
        }
    }
}
