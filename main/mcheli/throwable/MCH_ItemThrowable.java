package mcheli.throwable;

import mcheli.wrapper.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import mcheli.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;

public class MCH_ItemThrowable extends W_Item
{
    public MCH_ItemThrowable(final int par1) {
        super(par1);
        this.func_77625_d(1);
    }
    
    public static void registerDispenseBehavior(final Item item) {
        BlockDispenser.field_149943_a.func_82595_a((Object)item, (Object)new MCH_ItemThrowableDispenseBehavior());
    }
    
    public ItemStack func_77659_a(final ItemStack itemStack, final World world, final EntityPlayer player) {
        player.func_71008_a(itemStack, this.func_77626_a(itemStack));
        return itemStack;
    }
    
    public void func_77615_a(final ItemStack itemStack, final World world, final EntityPlayer player, int par4) {
        if (itemStack != null && itemStack.field_77994_a > 0) {
            final MCH_ThrowableInfo info = MCH_ThrowableInfoManager.get(itemStack.func_77973_b());
            if (info != null) {
                if (!player.field_71075_bZ.field_75098_d) {
                    --itemStack.field_77994_a;
                    if (itemStack.field_77994_a <= 0) {
                        player.field_71071_by.field_70462_a[player.field_71071_by.field_70461_c] = null;
                    }
                }
                world.func_72956_a((Entity)player, "random.bow", 0.5f, 0.4f / (MCH_ItemThrowable.field_77697_d.nextFloat() * 0.4f + 0.8f));
                if (!world.field_72995_K) {
                    float acceleration = 1.0f;
                    par4 = itemStack.func_77988_m() - par4;
                    if (par4 <= 35) {
                        if (par4 < 5) {
                            par4 = 5;
                        }
                        acceleration = par4 / 25.0f;
                    }
                    MCH_Lib.DbgLog(world, "MCH_ItemThrowable.onPlayerStoppedUsing(%d)", par4);
                    final MCH_EntityThrowable entity = new MCH_EntityThrowable(world, (EntityLivingBase)player, acceleration);
                    entity.setInfo(info);
                    world.func_72838_d((Entity)entity);
                }
            }
        }
    }
    
    public int func_77626_a(final ItemStack par1ItemStack) {
        return 72000;
    }
    
    public EnumAction func_77661_b(final ItemStack par1ItemStack) {
        return EnumAction.bow;
    }
}
