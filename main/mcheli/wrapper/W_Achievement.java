package mcheli.wrapper;

import net.minecraft.item.*;
import net.minecraft.stats.*;

public class W_Achievement
{
    public static Achievement registerAchievement(final String par1, final String par2Str, final int par3, final int par4, final Item par5Item, final Achievement par6Achievement) {
        return new Achievement(par1, par2Str, par3, par4, par5Item, par6Achievement).func_75966_h().func_75971_g();
    }
}
