package mcheli.wrapper;

import net.minecraft.entity.*;
import net.minecraft.client.entity.*;

public class W_EntityPlayerSP
{
    public static void closeScreen(final Entity p) {
        if (p instanceof EntityPlayerSP) {
            ((EntityPlayerSP)p).func_71053_j();
        }
    }
}
