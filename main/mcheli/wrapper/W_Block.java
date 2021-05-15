package mcheli.wrapper;

import net.minecraft.block.*;
import net.minecraft.block.material.*;

public abstract class W_Block extends Block
{
    protected W_Block(final Material p_i45394_1_) {
        super(p_i45394_1_);
    }
    
    public static Block getBlockFromName(final String name) {
        return Block.func_149684_b(name);
    }
    
    public static Block getSnowLayer() {
        return W_Blocks.field_150431_aC;
    }
    
    public static boolean isNull(final Block block) {
        return block == null || block == W_Blocks.field_150350_a;
    }
    
    public static boolean isEqual(final int blockId, final Block block) {
        return Block.func_149680_a(Block.func_149729_e(blockId), block);
    }
    
    public static boolean isEqual(final Block block1, final Block block2) {
        return Block.func_149680_a(block1, block2);
    }
    
    public static Block getWater() {
        return W_Blocks.field_150355_j;
    }
    
    public static Block getBlockById(final int i) {
        return Block.func_149729_e(i);
    }
}
