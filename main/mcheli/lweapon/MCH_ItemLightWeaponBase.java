package mcheli.lweapon;

import mcheli.wrapper.*;
import net.minecraft.entity.player.*;
import net.minecraft.potion.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.world.*;

public class MCH_ItemLightWeaponBase extends W_Item
{
    public final MCH_ItemLightWeaponBullet bullet;
    
    public MCH_ItemLightWeaponBase(final int par1, final MCH_ItemLightWeaponBullet bullet) {
        super(par1);
        this.func_77656_e(10);
        this.func_77625_d(1);
        this.bullet = bullet;
    }
    
    public static String getName(final ItemStack itemStack) {
        if (itemStack != null && itemStack.func_77973_b() instanceof MCH_ItemLightWeaponBase) {
            String name = itemStack.func_77977_a();
            final int li = name.lastIndexOf(":");
            if (li >= 0) {
                name = name.substring(li + 1);
            }
            return name;
        }
        return "";
    }
    
    public static boolean isHeld(final EntityPlayer player) {
        final ItemStack is = (player != null) ? player.func_70694_bm() : null;
        return is != null && is.func_77973_b() instanceof MCH_ItemLightWeaponBase && player.func_71057_bx() > 10;
    }
    
    public void onUsingTick(final ItemStack stack, final EntityPlayer player, final int count) {
        final PotionEffect pe = player.func_70660_b(Potion.field_76439_r);
        if (pe != null && pe.func_76459_b() < 220) {
            player.func_70690_d(new PotionEffect(Potion.field_76439_r.func_76396_c(), 250, 0, false));
        }
    }
    
    public boolean onEntitySwing(final EntityLivingBase entityLiving, final ItemStack stack) {
        return true;
    }
    
    public EnumAction func_77661_b(final ItemStack par1ItemStack) {
        return EnumAction.bow;
    }
    
    public int func_77626_a(final ItemStack par1ItemStack) {
        return 72000;
    }
    
    public ItemStack func_77659_a(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        if (par1ItemStack != null) {
            par3EntityPlayer.func_71008_a(par1ItemStack, this.func_77626_a(par1ItemStack));
        }
        return par1ItemStack;
    }
}
