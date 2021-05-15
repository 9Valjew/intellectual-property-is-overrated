package mcheli.wrapper;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;

public class W_SoundManagerFunc
{
    public static void DEF_playEntitySound(final SoundManager sm, final String name, final Entity entity, final float volume, final float pitch, final boolean par5) {
        sm.func_148611_c((ISound)new W_Sound(new ResourceLocation(name), volume, pitch, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v));
    }
    
    public static void MOD_playEntitySound(final SoundManager sm, final String name, final Entity entity, final float volume, final float pitch, final boolean par5) {
        DEF_playEntitySound(sm, W_MOD.DOMAIN + ":" + name, entity, volume, pitch, par5);
    }
}
