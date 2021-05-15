package mcheli.wrapper;

import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;

public class W_Particle
{
    public static String getParticleTileCrackName(final World w, final int blockX, final int blockY, final int blockZ) {
        final Block block = w.func_147439_a(blockX, blockY, blockZ);
        if (block.func_149688_o() != Material.field_151579_a) {
            return "blockcrack_" + Block.func_149682_b(block) + "_" + w.func_72805_g(blockX, blockY, blockZ);
        }
        return "";
    }
    
    public static String getParticleTileDustName(final World w, final int blockX, final int blockY, final int blockZ) {
        final Block block = w.func_147439_a(blockX, blockY, blockZ);
        if (block.func_149688_o() != Material.field_151579_a) {
            return "blockdust_" + Block.func_149682_b(block) + "_" + w.func_72805_g(blockX, blockY, blockZ);
        }
        return "";
    }
}
