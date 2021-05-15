package mcheli.wrapper;

import net.minecraft.item.*;
import net.minecraft.block.*;

public class W_Item extends Item
{
    public W_Item(final int par1) {
    }
    
    public W_Item() {
    }
    
    public static int getIdFromItem(final Item i) {
        return (i == null) ? 0 : W_Item.field_150901_e.func_148757_b((Object)i);
    }
    
    public Item setTexture(final String par1Str) {
        this.func_111206_d(W_MOD.DOMAIN + ":" + par1Str);
        return this;
    }
    
    public static Item getItemById(final int i) {
        return Item.func_150899_d(i);
    }
    
    public static Item getItemByName(String nm) {
        if (nm.indexOf(58) < 0) {
            nm = "minecraft:" + nm;
        }
        return (Item)Item.field_150901_e.func_82594_a(nm);
    }
    
    public static String getNameForItem(final Item item) {
        return Item.field_150901_e.func_148750_c((Object)item);
    }
    
    public static Item getItemFromBlock(final Block block) {
        return Item.func_150898_a(block);
    }
}
