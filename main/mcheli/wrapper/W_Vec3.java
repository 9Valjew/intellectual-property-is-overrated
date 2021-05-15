package mcheli.wrapper;

import net.minecraft.util.*;

public class W_Vec3
{
    public static void rotateAroundZ(final float par1, final Vec3 vOut) {
        final float f1 = MathHelper.func_76134_b(par1);
        final float f2 = MathHelper.func_76126_a(par1);
        final double d0 = vOut.field_72450_a * f1 + vOut.field_72448_b * f2;
        final double d2 = vOut.field_72448_b * f1 - vOut.field_72450_a * f2;
        final double d3 = vOut.field_72449_c;
        vOut.field_72450_a = d0;
        vOut.field_72448_b = d2;
        vOut.field_72449_c = d3;
    }
}
