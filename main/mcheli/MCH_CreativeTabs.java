package mcheli;

import net.minecraft.creativetab.*;
import mcheli.wrapper.*;
import net.minecraft.item.*;
import mcheli.aircraft.*;
import java.util.*;
import cpw.mods.fml.relauncher.*;

public class MCH_CreativeTabs extends CreativeTabs
{
    private List<Item> iconItems;
    private Item lastItem;
    private int currentIconIndex;
    private int switchItemWait;
    private Item fixedItem;
    
    public MCH_CreativeTabs(final String label) {
        super(label);
        this.fixedItem = null;
        this.iconItems = new ArrayList<Item>();
        this.currentIconIndex = 0;
        this.switchItemWait = 0;
    }
    
    public void setFixedIconItem(final String itemName) {
        if (itemName.indexOf(58) >= 0) {
            this.fixedItem = W_Item.getItemByName(itemName);
            if (this.fixedItem != null) {
                this.fixedItem.func_111206_d(itemName);
            }
        }
        else {
            this.fixedItem = W_Item.getItemByName("mcheli:" + itemName);
            if (this.fixedItem != null) {
                this.fixedItem.func_111206_d("mcheli:" + itemName);
            }
        }
    }
    
    public Item func_78016_d() {
        if (this.iconItems.size() <= 0) {
            return null;
        }
        this.currentIconIndex = (this.currentIconIndex + 1) % this.iconItems.size();
        return this.iconItems.get(this.currentIconIndex);
    }
    
    public ItemStack func_151244_d() {
        if (this.fixedItem != null) {
            return new ItemStack(this.fixedItem, 1, 0);
        }
        if (this.switchItemWait > 0) {
            --this.switchItemWait;
        }
        else {
            this.lastItem = this.func_78016_d();
            this.switchItemWait = 60;
        }
        if (this.lastItem == null) {
            this.lastItem = W_Item.getItemByName("iron_block");
        }
        return new ItemStack(this.lastItem, 1, 0);
    }
    
    @SideOnly(Side.CLIENT)
    public void func_78018_a(final List list) {
        super.func_78018_a(list);
        final Comparator cmp = new Comparator<ItemStack>() {
            @Override
            public int compare(final ItemStack i1, final ItemStack i2) {
                if (i1.func_77973_b() instanceof MCH_ItemAircraft && i2.func_77973_b() instanceof MCH_ItemAircraft) {
                    final MCH_AircraftInfo info1 = ((MCH_ItemAircraft)i1.func_77973_b()).getAircraftInfo();
                    final MCH_AircraftInfo info2 = ((MCH_ItemAircraft)i2.func_77973_b()).getAircraftInfo();
                    if (info1 != null && info2 != null) {
                        final String s1 = info1.category + "." + info1.name;
                        final String s2 = info2.category + "." + info2.name;
                        return s1.compareTo(s2);
                    }
                }
                return i1.func_77973_b().func_77658_a().compareTo(i2.func_77973_b().func_77658_a());
            }
        };
        Collections.sort((List<Object>)list, cmp);
    }
    
    public void addIconItem(final Item i) {
        if (i != null) {
            this.iconItems.add(i);
        }
    }
    
    public String func_78024_c() {
        return "MC Heli";
    }
}
