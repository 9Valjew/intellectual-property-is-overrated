package mcheli.aircraft;

import net.minecraft.dispenser.*;
import net.minecraft.item.*;
import mcheli.wrapper.*;
import net.minecraft.entity.*;
import mcheli.*;
import net.minecraft.util.*;

public class MCH_ItemAircraftDispenseBehavior extends BehaviorDefaultDispenseItem
{
    public ItemStack func_82487_b(final IBlockSource bs, final ItemStack itemStack) {
        final EnumFacing enumfacing = W_BlockDispenser.getFacing(bs.func_82620_h());
        final double x = bs.func_82615_a() + enumfacing.func_82601_c() * 2.0;
        final double y = bs.func_82617_b() + enumfacing.func_96559_d() * 2.0;
        final double z = bs.func_82616_c() + enumfacing.func_82599_e() * 2.0;
        if (itemStack.func_77973_b() instanceof MCH_ItemAircraft) {
            final MCH_EntityAircraft ac = ((MCH_ItemAircraft)itemStack.func_77973_b()).onTileClick(itemStack, bs.func_82618_k(), 0.0f, (int)x, (int)y, (int)z);
            if (ac != null) {
                if (!ac.isUAV()) {
                    if (!bs.func_82618_k().field_72995_K) {
                        ac.getAcDataFromItem(itemStack);
                        bs.func_82618_k().func_72838_d((Entity)ac);
                    }
                    itemStack.func_77979_a(1);
                    MCH_Lib.DbgLog(bs.func_82618_k(), "dispenseStack:x=%.1f,y=%.1f,z=%.1f;dir=%s:item=" + itemStack.func_82833_r(), x, y, z, enumfacing.toString());
                }
            }
        }
        return itemStack;
    }
}
