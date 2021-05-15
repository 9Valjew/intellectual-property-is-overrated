package mcheli.wrapper;

import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;
import net.minecraft.entity.*;

public class W_McClient
{
    public static void DEF_playSoundFX(final String name, final float volume, final float pitch) {
        Minecraft.func_71410_x().func_147118_V().func_147682_a((ISound)new W_Sound(new ResourceLocation(name), volume, pitch));
    }
    
    public static void MOD_playSoundFX(final String name, final float volume, final float pitch) {
        DEF_playSoundFX(W_MOD.DOMAIN + ":" + name, volume, pitch);
    }
    
    public static void addSound(final String name) {
        final Minecraft mc = Minecraft.func_71410_x();
    }
    
    public static void DEF_bindTexture(final String tex) {
        Minecraft.func_71410_x().field_71446_o.func_110577_a(new ResourceLocation(tex));
    }
    
    public static void MOD_bindTexture(final String tex) {
        Minecraft.func_71410_x().field_71446_o.func_110577_a(new ResourceLocation(W_MOD.DOMAIN, tex));
    }
    
    public static boolean isGamePaused() {
        final Minecraft mc = Minecraft.func_71410_x();
        return mc.func_147113_T();
    }
    
    public static Entity getRenderEntity() {
        return (Entity)Minecraft.func_71410_x().field_71451_h;
    }
    
    public static void setRenderEntity(final EntityLivingBase entity) {
        Minecraft.func_71410_x().field_71451_h = entity;
    }
}
