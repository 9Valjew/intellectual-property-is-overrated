package mcheli.throwable;

import net.minecraft.dispenser.*;
import net.minecraft.item.*;
import mcheli.wrapper.*;
import mcheli.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class MCH_ItemThrowableDispenseBehavior extends BehaviorDefaultDispenseItem
{
    public ItemStack func_82487_b(final IBlockSource bs, final ItemStack itemStack) {
        final EnumFacing enumfacing = W_BlockDispenser.getFacing(bs.func_82620_h());
        final double x = bs.func_82615_a() + enumfacing.func_82601_c() * 2.0;
        final double y = bs.func_82617_b() + enumfacing.func_96559_d() * 2.0;
        final double z = bs.func_82616_c() + enumfacing.func_82599_e() * 2.0;
        if (itemStack.func_77973_b() instanceof MCH_ItemThrowable) {
            final MCH_ThrowableInfo info = MCH_ThrowableInfoManager.get(itemStack.func_77973_b());
            if (info != null) {
                bs.func_82618_k().func_72980_b(x, y, z, "random.bow", 0.5f, 0.4f / (bs.func_82618_k().field_73012_v.nextFloat() * 0.4f + 0.8f), false);
                if (!bs.func_82618_k().field_72995_K) {
                    MCH_Lib.DbgLog(bs.func_82618_k(), "MCH_ItemThrowableDispenseBehavior.dispenseStack(%s)", info.name);
                    final MCH_EntityThrowable entity = new MCH_EntityThrowable(bs.func_82618_k(), x, y, z);
                    entity.field_70159_w = enumfacing.func_82601_c() * info.dispenseAcceleration;
                    entity.field_70181_x = enumfacing.func_96559_d() * info.dispenseAcceleration;
                    entity.field_70179_y = enumfacing.func_82599_e() * info.dispenseAcceleration;
                    entity.setInfo(info);
                    bs.func_82618_k().func_72838_d((Entity)entity);
                    itemStack.func_77979_a(1);
                }
            }
        }
        return itemStack;
    }
}
