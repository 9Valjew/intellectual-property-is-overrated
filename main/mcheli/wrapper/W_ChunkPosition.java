package mcheli.wrapper;

import net.minecraft.world.*;

public class W_ChunkPosition
{
    public static int getChunkPosX(final ChunkPosition c) {
        return c.field_151329_a;
    }
    
    public static int getChunkPosY(final ChunkPosition c) {
        return c.field_151327_b;
    }
    
    public static int getChunkPosZ(final ChunkPosition c) {
        return c.field_151328_c;
    }
}
