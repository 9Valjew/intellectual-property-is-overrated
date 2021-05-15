package mcheli;

import net.minecraft.item.crafting.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;
import mcheli.tool.rangefinder.*;
import net.minecraft.item.*;

public class MCH_RecipeReloadRangeFinder implements IRecipe
{
    public boolean func_77569_a(final InventoryCrafting inv, final World var2) {
        int jcnt = 0;
        int ccnt = 0;
        for (int i = 0; i < inv.func_70302_i_(); ++i) {
            final ItemStack is = inv.func_70301_a(i);
            if (is != null) {
                if (is.func_77973_b() instanceof MCH_ItemRangeFinder) {
                    if (is.func_77960_j() == 0) {
                        return false;
                    }
                    if (++jcnt > 1) {
                        return false;
                    }
                }
                else {
                    if (!(is.func_77973_b() instanceof ItemRedstone) || is.field_77994_a <= 0) {
                        return false;
                    }
                    if (++ccnt > 1) {
                        return false;
                    }
                }
            }
        }
        return jcnt == 1 && ccnt > 0;
    }
    
    public ItemStack func_77572_b(final InventoryCrafting inv) {
        final ItemStack output = new ItemStack((Item)MCH_MOD.itemRangeFinder);
        return output;
    }
    
    public int func_77570_a() {
        return 9;
    }
    
    public ItemStack func_77571_b() {
        return null;
    }
}
