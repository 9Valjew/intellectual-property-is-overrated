package mcheli.wrapper;

import net.minecraft.item.*;
import net.minecraftforge.client.*;

public class W_MinecraftForgeClient
{
    public static void registerItemRenderer(final Item item, final IItemRenderer renderer) {
        if (item != null) {
            MinecraftForgeClient.registerItemRenderer(item, renderer);
        }
    }
}
