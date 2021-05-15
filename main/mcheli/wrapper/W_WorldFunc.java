package mcheli.wrapper;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;

public class W_WorldFunc
{
    public static void DEF_playSoundEffect(final World w, final double x, final double y, final double z, final String name, final float volume, final float pitch) {
        w.func_72908_a(x, y, z, name, volume, pitch);
    }
    
    public static void MOD_playSoundEffect(final World w, final double x, final double y, final double z, final String name, final float volume, final float pitch) {
        DEF_playSoundEffect(w, x, y, z, W_MOD.DOMAIN + ":" + name, volume, pitch);
    }
    
    private static void playSoundAtEntity(final Entity e, final String name, final float volume, final float pitch) {
        e.field_70170_p.func_72956_a(e, name, volume, pitch);
    }
    
    public static void MOD_playSoundAtEntity(final Entity e, final String name, final float volume, final float pitch) {
        playSoundAtEntity(e, W_MOD.DOMAIN + ":" + name, volume, pitch);
    }
    
    public static int getBlockId(final World w, final int x, final int y, final int z) {
        return Block.func_149682_b(w.func_147439_a(x, y, z));
    }
    
    public static Block getBlock(final World w, final int x, final int y, final int z) {
        return w.func_147439_a(x, y, z);
    }
    
    public static Material getBlockMaterial(final World w, final int x, final int y, final int z) {
        return w.func_147439_a(x, y, z).func_149688_o();
    }
    
    public static boolean isBlockWater(final World w, final int x, final int y, final int z) {
        return isEqualBlock(w, x, y, z, W_Block.getWater());
    }
    
    public static boolean isEqualBlock(final World w, final int x, final int y, final int z, final Block block) {
        return Block.func_149680_a(w.func_147439_a(x, y, z), block);
    }
    
    public static MovingObjectPosition clip(final World w, final Vec3 par1Vec3, final Vec3 par2Vec3) {
        return w.func_72933_a(par1Vec3, par2Vec3);
    }
    
    public static MovingObjectPosition clip(final World w, final Vec3 par1Vec3, final Vec3 par2Vec3, final boolean b) {
        return w.func_72901_a(par1Vec3, par2Vec3, b);
    }
    
    public static MovingObjectPosition clip(final World w, final Vec3 par1Vec3, final Vec3 par2Vec3, final boolean b1, final boolean b2, final boolean b3) {
        return w.func_147447_a(par1Vec3, par2Vec3, b1, b2, b3);
    }
    
    public static boolean setBlock(final World w, final int a, final int b, final int c, final Block d) {
        return w.func_147449_b(a, b, c, d);
    }
    
    public static void setBlock(final World w, final int x, final int y, final int z, final Block b, final int i, final int j) {
        w.func_147465_d(x, y, z, b, i, j);
    }
    
    public static boolean destroyBlock(final World w, final int x, final int y, final int z, final boolean par4) {
        return w.func_147480_a(x, y, z, par4);
    }
    
    public static Vec3 getWorldVec3(final World w, final double x, final double y, final double z) {
        return Vec3.func_72443_a(x, y, z);
    }
    
    public static Vec3 getWorldVec3EntityPos(final Entity e) {
        return getWorldVec3(e.field_70170_p, e.field_70165_t, e.field_70163_u, e.field_70161_v);
    }
}
