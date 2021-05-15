package mcheli.wrapper;

import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.nbt.*;
import mcheli.*;

public abstract class W_EntityContainer extends W_Entity implements IInventory
{
    public static final int MAX_INVENTORY_SIZE = 54;
    private ItemStack[] containerItems;
    public boolean dropContentsWhenDead;
    
    public W_EntityContainer(final World par1World) {
        super(par1World);
        this.dropContentsWhenDead = true;
        this.containerItems = new ItemStack[54];
    }
    
    @Override
    protected void func_70088_a() {
    }
    
    public ItemStack func_70301_a(final int par1) {
        return this.containerItems[par1];
    }
    
    public int getUsingSlotNum() {
        int numUsingSlot = 0;
        if (this.containerItems == null) {
            numUsingSlot = 0;
        }
        else {
            final int n = this.func_70302_i_();
            numUsingSlot = 0;
            for (int i = 0; i < n && i < this.containerItems.length; ++i) {
                if (this.func_70301_a(i) != null) {
                    ++numUsingSlot;
                }
            }
        }
        return numUsingSlot;
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
        this.func_70296_d();
    }
    
    public void onInventoryChanged() {
    }
    
    public boolean func_70300_a(final EntityPlayer par1EntityPlayer) {
        return !this.field_70128_L && par1EntityPlayer.func_70068_e((Entity)this) <= 64.0;
    }
    
    public void openChest() {
    }
    
    public void closeChest() {
    }
    
    public boolean func_94041_b(final int par1, final ItemStack par2ItemStack) {
        return true;
    }
    
    public boolean isStackValidForSlot(final int par1, final ItemStack par2ItemStack) {
        return true;
    }
    
    public String getInvName() {
        return "Inventory";
    }
    
    public String func_145825_b() {
        return this.getInvName();
    }
    
    public boolean isInvNameLocalized() {
        return false;
    }
    
    public boolean func_145818_k_() {
        return this.isInvNameLocalized();
    }
    
    public int func_70297_j_() {
        return 64;
    }
    
    public void func_70106_y() {
        if (this.dropContentsWhenDead && !this.field_70170_p.field_72995_K) {
            for (int i = 0; i < this.func_70302_i_(); ++i) {
                final ItemStack itemstack = this.func_70301_a(i);
                if (itemstack != null) {
                    final float x = this.field_70146_Z.nextFloat() * 0.8f + 0.1f;
                    final float y = this.field_70146_Z.nextFloat() * 0.8f + 0.1f;
                    final float z = this.field_70146_Z.nextFloat() * 0.8f + 0.1f;
                    while (itemstack.field_77994_a > 0) {
                        int j = this.field_70146_Z.nextInt(21) + 10;
                        if (j > itemstack.field_77994_a) {
                            j = itemstack.field_77994_a;
                        }
                        final ItemStack itemStack = itemstack;
                        itemStack.field_77994_a -= j;
                        final EntityItem entityitem = new EntityItem(this.field_70170_p, this.field_70165_t + x, this.field_70163_u + y, this.field_70161_v + z, new ItemStack(itemstack.func_77973_b(), j, itemstack.func_77960_j()));
                        if (itemstack.func_77942_o()) {
                            entityitem.func_92059_d().func_77982_d((NBTTagCompound)itemstack.func_77978_p().func_74737_b());
                        }
                        final float f3 = 0.05f;
                        entityitem.field_70159_w = (float)this.field_70146_Z.nextGaussian() * f3;
                        entityitem.field_70181_x = (float)this.field_70146_Z.nextGaussian() * f3 + 0.2f;
                        entityitem.field_70179_y = (float)this.field_70146_Z.nextGaussian() * f3;
                        this.field_70170_p.func_72838_d((Entity)entityitem);
                    }
                }
            }
        }
        super.func_70106_y();
    }
    
    protected void func_70014_b(final NBTTagCompound par1NBTTagCompound) {
        final NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.containerItems.length; ++i) {
            if (this.containerItems[i] != null) {
                final NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.func_74774_a("Slot", (byte)i);
                this.containerItems[i].func_77955_b(nbttagcompound1);
                nbttaglist.func_74742_a((NBTBase)nbttagcompound1);
            }
        }
        par1NBTTagCompound.func_74782_a("Items", (NBTBase)nbttaglist);
    }
    
    protected void func_70037_a(final NBTTagCompound par1NBTTagCompound) {
        final NBTTagList nbttaglist = W_NBTTag.getTagList(par1NBTTagCompound, "Items", 10);
        this.containerItems = new ItemStack[this.func_70302_i_()];
        MCH_Lib.DbgLog(this.field_70170_p, "W_EntityContainer.readEntityFromNBT.InventorySize = %d", this.func_70302_i_());
        for (int i = 0; i < nbttaglist.func_74745_c(); ++i) {
            final NBTTagCompound nbttagcompound1 = W_NBTTag.tagAt(nbttaglist, i);
            final int j = nbttagcompound1.func_74771_c("Slot") & 0xFF;
            if (j >= 0 && j < this.containerItems.length) {
                this.containerItems[j] = ItemStack.func_77949_a(nbttagcompound1);
            }
        }
    }
    
    public void func_71027_c(final int par1) {
        this.dropContentsWhenDead = false;
        super.func_71027_c(par1);
    }
    
    public boolean openInventory(final EntityPlayer player) {
        if (!this.field_70170_p.field_72995_K && this.func_70302_i_() > 0) {
            player.func_71007_a((IInventory)this);
            return true;
        }
        return false;
    }
    
    public void func_70295_k_() {
    }
    
    public void func_70305_f() {
    }
    
    public void func_70296_d() {
    }
    
    public int func_70302_i_() {
        return 0;
    }
}
