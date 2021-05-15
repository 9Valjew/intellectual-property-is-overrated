package mcheli.aircraft;

import net.minecraft.inventory.*;
import net.minecraft.item.*;
import mcheli.parachute.*;
import java.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import mcheli.wrapper.*;

public class MCH_AircraftInventory implements IInventory
{
    public final int SLOT_FUEL0 = 0;
    public final int SLOT_FUEL1 = 1;
    public final int SLOT_FUEL2 = 2;
    public final int SLOT_PARACHUTE0 = 3;
    public final int SLOT_PARACHUTE1 = 4;
    private ItemStack[] containerItems;
    final MCH_EntityAircraft aircraft;
    
    public MCH_AircraftInventory(final MCH_EntityAircraft ac) {
        this.containerItems = new ItemStack[this.func_70302_i_()];
        this.aircraft = ac;
    }
    
    public ItemStack getFuelSlotItemStack(final int i) {
        return this.func_70301_a(0 + i);
    }
    
    public ItemStack getParachuteSlotItemStack(final int i) {
        return this.func_70301_a(3 + i);
    }
    
    public boolean haveParachute() {
        for (int i = 0; i < 2; ++i) {
            final ItemStack item = this.getParachuteSlotItemStack(i);
            if (item != null && item.func_77973_b() instanceof MCH_ItemParachute) {
                return true;
            }
        }
        return false;
    }
    
    public void consumeParachute() {
        for (int i = 0; i < 2; ++i) {
            final ItemStack item = this.getParachuteSlotItemStack(i);
            if (item != null && item.func_77973_b() instanceof MCH_ItemParachute) {
                this.func_70299_a(3 + i, null);
                break;
            }
        }
    }
    
    public int func_70302_i_() {
        return 10;
    }
    
    public ItemStack func_70301_a(final int var1) {
        return this.containerItems[var1];
    }
    
    public void setDead() {
        final Random rand = new Random();
        if (this.aircraft.dropContentsWhenDead && !this.aircraft.field_70170_p.field_72995_K) {
            for (int i = 0; i < this.func_70302_i_(); ++i) {
                final ItemStack itemstack = this.func_70301_a(i);
                if (itemstack != null) {
                    final float x = rand.nextFloat() * 0.8f + 0.1f;
                    final float y = rand.nextFloat() * 0.8f + 0.1f;
                    final float z = rand.nextFloat() * 0.8f + 0.1f;
                    while (itemstack.field_77994_a > 0) {
                        int j = rand.nextInt(21) + 10;
                        if (j > itemstack.field_77994_a) {
                            j = itemstack.field_77994_a;
                        }
                        final ItemStack itemStack = itemstack;
                        itemStack.field_77994_a -= j;
                        final EntityItem entityitem = new EntityItem(this.aircraft.field_70170_p, this.aircraft.field_70165_t + x, this.aircraft.field_70163_u + y, this.aircraft.field_70161_v + z, new ItemStack(itemstack.func_77973_b(), j, itemstack.func_77960_j()));
                        if (itemstack.func_77942_o()) {
                            entityitem.func_92059_d().func_77982_d((NBTTagCompound)itemstack.func_77978_p().func_74737_b());
                        }
                        final float f3 = 0.05f;
                        entityitem.field_70159_w = (float)rand.nextGaussian() * f3;
                        entityitem.field_70181_x = (float)rand.nextGaussian() * f3 + 0.2f;
                        entityitem.field_70179_y = (float)rand.nextGaussian() * f3;
                        this.aircraft.field_70170_p.func_72838_d((Entity)entityitem);
                    }
                }
            }
        }
    }
    
    public ItemStack func_70298_a(final int par1, final int par2) {
        if (this.containerItems[par1] == null) {
            return null;
        }
        if (this.containerItems[par1].field_77994_a <= par2) {
            final ItemStack itemstack = this.containerItems[par1];
            this.containerItems[par1] = null;
            return itemstack;
        }
        final ItemStack itemstack = this.containerItems[par1].func_77979_a(par2);
        if (this.containerItems[par1].field_77994_a == 0) {
            this.containerItems[par1] = null;
        }
        return itemstack;
    }
    
    public ItemStack func_70304_b(final int par1) {
        if (this.containerItems[par1] != null) {
            final ItemStack itemstack = this.containerItems[par1];
            this.containerItems[par1] = null;
            return itemstack;
        }
        return null;
    }
    
    public void func_70299_a(final int par1, final ItemStack par2ItemStack) {
        this.containerItems[par1] = par2ItemStack;
        if (par2ItemStack != null && par2ItemStack.field_77994_a > this.func_70297_j_()) {
            par2ItemStack.field_77994_a = this.func_70297_j_();
        }
    }
    
    public String func_145825_b() {
        return this.getInvName();
    }
    
    public String getInvName() {
        if (this.aircraft.getAcInfo() == null) {
            return "";
        }
        final String s = this.aircraft.getAcInfo().displayName;
        return (s.length() <= 32) ? s : s.substring(0, 31);
    }
    
    public boolean isInvNameLocalized() {
        return this.aircraft.getAcInfo() != null;
    }
    
    public boolean func_145818_k_() {
        return this.isInvNameLocalized();
    }
    
    public int func_70297_j_() {
        return 64;
    }
    
    public void func_70296_d() {
    }
    
    public boolean func_70300_a(final EntityPlayer player) {
        return player.func_70068_e((Entity)this.aircraft) <= 144.0;
    }
    
    public boolean func_94041_b(final int par1, final ItemStack par2ItemStack) {
        return true;
    }
    
    public boolean isStackValidForSlot(final int par1, final ItemStack par2ItemStack) {
        return true;
    }
    
    public void func_70295_k_() {
    }
    
    public void func_70305_f() {
    }
    
    protected void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        final NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.containerItems.length; ++i) {
            if (this.containerItems[i] != null) {
                final NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.func_74774_a("SlotAC", (byte)i);
                this.containerItems[i].func_77955_b(nbttagcompound1);
                nbttaglist.func_74742_a((NBTBase)nbttagcompound1);
            }
        }
        par1NBTTagCompound.func_74782_a("ItemsAC", (NBTBase)nbttaglist);
    }
    
    protected void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        final NBTTagList nbttaglist = W_NBTTag.getTagList(par1NBTTagCompound, "ItemsAC", 10);
        this.containerItems = new ItemStack[this.func_70302_i_()];
        for (int i = 0; i < nbttaglist.func_74745_c(); ++i) {
            final NBTTagCompound nbttagcompound1 = W_NBTTag.tagAt(nbttaglist, i);
            final int j = nbttagcompound1.func_74771_c("SlotAC") & 0xFF;
            if (j >= 0 && j < this.containerItems.length) {
                this.containerItems[j] = ItemStack.func_77949_a(nbttagcompound1);
            }
        }
    }
    
    public void onInventoryChanged() {
    }
    
    public void openChest() {
    }
    
    public void closeChest() {
    }
}
