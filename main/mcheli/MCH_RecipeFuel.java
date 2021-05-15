package mcheli;

import net.minecraft.item.crafting.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;
import mcheli.aircraft.*;
import net.minecraft.item.*;

public class MCH_RecipeFuel implements IRecipe
{
    public boolean func_77569_a(final InventoryCrafting inv, final World var2) {
        int jcnt = 0;
        int ccnt = 0;
        for (int i = 0; i < inv.func_70302_i_(); ++i) {
            final ItemStack is = inv.func_70301_a(i);
            if (is != null) {
                if (is.func_77973_b() instanceof MCH_ItemFuel) {
                    if (is.func_77960_j() == 0) {
                        return false;
                    }
                    if (++jcnt > 1) {
                        return false;
                    }
                }
                else {
                    if (!(is.func_77973_b() instanceof ItemCoal) || is.field_77994_a <= 0) {
                        return false;
                    }
                    ++ccnt;
                }
            }
        }
        return jcnt == 1 && ccnt > 0;
    }
    
    public ItemStack func_77572_b(final InventoryCrafting inv) {
        final ItemStack output = new ItemStack((Item)MCH_MOD.itemFuel);
        for (int i = 0; i < inv.func_70302_i_(); ++i) {
            final ItemStack is = inv.func_70301_a(i);
            if (is != null && is.func_77973_b() instanceof MCH_ItemFuel) {
                output.func_77964_b(is.func_77960_j());
                break;
            }
        }
        for (int i = 0; i < inv.func_70302_i_(); ++i) {
            final ItemStack is = inv.func_70301_a(i);
            if (is != null && is.func_77973_b() instanceof ItemCoal) {
                int sp = 100;
                if (is.func_77960_j() == 1) {
                    sp = 75;
                }
                if (output.func_77960_j() > sp) {
                    output.func_77964_b(output.func_77960_j() - sp);
                }
                else {
                    output.func_77964_b(0);
                }
            }
        }
        return output;
    }
    
    public int func_77570_a() {
        return 9;
    }
    
    public ItemStack func_77571_b() {
        return null;
    }
}
