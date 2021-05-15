package mcheli.wrapper;

import cpw.mods.fml.relauncher.*;
import cpw.mods.fml.common.*;

public class W_TickRegistry
{
    public static void registerTickHandler(final W_TickHandler handler, final Side side) {
        FMLCommonHandler.instance().bus().register((Object)handler);
    }
}
