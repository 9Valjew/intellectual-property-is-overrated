package mcheli.wrapper;

import net.minecraft.client.audio.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class W_Sound extends MovingSound
{
    protected W_Sound(final ResourceLocation r, final float volume, final float pitch, final double x, final double y, final double z) {
        super(r);
        this.setVolumeAndPitch(volume, pitch);
        this.setPosition(x, y, z);
    }
    
    protected W_Sound(final ResourceLocation r, final float volume, final float pitch) {
        super(r);
        this.setVolumeAndPitch(volume, pitch);
        final Entity entity = W_McClient.getRenderEntity();
        if (entity != null) {
            this.setPosition(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v);
        }
    }
    
    public void setRepeat(final boolean b) {
        this.field_147659_g = b;
    }
    
    public void setSoundParam(final Entity e, final float v, final float p) {
        this.setPosition(e);
        this.setVolumeAndPitch(v, p);
    }
    
    public void setVolumeAndPitch(final float v, final float p) {
        this.setVolume(v);
        this.setPitch(p);
    }
    
    public void setVolume(final float v) {
        this.field_147662_b = v;
    }
    
    public void setPitch(final float p) {
        this.field_147663_c = p;
    }
    
    public void setPosition(final double x, final double y, final double z) {
        this.field_147660_d = (float)x;
        this.field_147661_e = (float)y;
        this.field_147658_f = (float)z;
    }
    
    public void setPosition(final Entity e) {
        this.setPosition(e.field_70165_t, e.field_70163_u, e.field_70161_v);
    }
    
    public void func_73660_a() {
    }
}
