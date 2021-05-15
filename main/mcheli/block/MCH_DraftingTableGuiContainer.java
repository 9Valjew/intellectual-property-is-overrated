package mcheli.block;

import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.block.*;
import mcheli.wrapper.*;
import net.minecraft.item.*;
import mcheli.*;
import mcheli.helicopter.*;
import mcheli.plane.*;
import mcheli.vehicle.*;
import mcheli.tank.*;
import net.minecraft.item.crafting.*;
import java.util.*;

public class MCH_DraftingTableGuiContainer extends Container
{
    public final EntityPlayer player;
    public final int posX;
    public final int posY;
    public final int posZ;
    public final int outputSlotIndex;
    private IInventory outputSlot;
    
    public MCH_DraftingTableGuiContainer(final EntityPlayer player, final int posX, final int posY, final int posZ) {
        this.outputSlot = (IInventory)new InventoryCraftResult();
        this.player = player;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.func_75146_a(new Slot((IInventory)player.field_71071_by, 9 + x + y * 9, 30 + x * 18, 140 + y * 18));
            }
        }
        for (int x2 = 0; x2 < 9; ++x2) {
            this.func_75146_a(new Slot((IInventory)player.field_71071_by, x2, 30 + x2 * 18, 198));
        }
        this.outputSlotIndex = this.field_75153_a.size();
        final Slot a = new Slot(this.outputSlot, this.outputSlotIndex, 178, 90) {
            public boolean func_75214_a(final ItemStack par1ItemStack) {
                return false;
            }
        };
        this.func_75146_a(a);
        MCH_Lib.DbgLog(player.field_70170_p, "MCH_DraftingTableGuiContainer.MCH_DraftingTableGuiContainer", new Object[0]);
    }
    
    public void func_75142_b() {
        super.func_75142_b();
    }
    
    public boolean func_75145_c(final EntityPlayer player) {
        final Block block = W_WorldFunc.getBlock(player.field_70170_p, this.posX, this.posY, this.posZ);
        return (W_Block.isEqual(block, (Block)MCH_MOD.blockDraftingTable) || W_Block.isEqual(block, (Block)MCH_MOD.blockDraftingTableLit)) && player.func_70092_e((double)this.posX, (double)this.posY, (double)this.posZ) <= 144.0;
    }
    
    public ItemStack func_82846_b(final EntityPlayer player, final int slotIndex) {
        ItemStack itemstack = null;
        final Slot slot = this.field_75151_b.get(slotIndex);
        if (slot != null && slot.func_75216_d()) {
            final ItemStack itemstack2 = slot.func_75211_c();
            itemstack = itemstack2.func_77946_l();
            if (slotIndex != this.outputSlotIndex) {
                return null;
            }
            if (!this.func_75135_a(itemstack2, 0, 36, true)) {
                return null;
            }
            slot.func_75220_a(itemstack2, itemstack);
            if (itemstack2.field_77994_a == 0) {
                slot.func_75215_d((ItemStack)null);
            }
            else {
                slot.func_75218_e();
            }
            if (itemstack2.field_77994_a == itemstack.field_77994_a) {
                return null;
            }
            slot.func_82870_a(player, itemstack2);
        }
        return itemstack;
    }
    
    public void func_75134_a(final EntityPlayer player) {
        super.func_75134_a(player);
        if (!player.field_70170_p.field_72995_K) {
            final ItemStack itemstack = this.func_75139_a(this.outputSlotIndex).func_75211_c();
            if (itemstack != null) {
                W_EntityPlayer.dropPlayerItemWithRandomChoice(player, itemstack, false, false);
            }
        }
        MCH_Lib.DbgLog(player.field_70170_p, "MCH_DraftingTableGuiContainer.onContainerClosed", new Object[0]);
    }
    
    public void createRecipeItem(final Item outputItem, final Map<Item, Integer> map) {
        final boolean isCreativeMode = this.player.field_71075_bZ.field_75098_d;
        if (this.func_75139_a(this.outputSlotIndex).func_75216_d() && !isCreativeMode) {
            MCH_Lib.DbgLog(this.player.field_70170_p, "MCH_DraftingTableGuiContainer.createRecipeItem:OutputSlot is not empty", new Object[0]);
            return;
        }
        if (outputItem == null) {
            MCH_Lib.DbgLog(this.player.field_70170_p, "Error:MCH_DraftingTableGuiContainer.createRecipeItem:outputItem = null", new Object[0]);
            return;
        }
        if (map == null || map.size() <= 0) {
            MCH_Lib.DbgLog(this.player.field_70170_p, "Error:MCH_DraftingTableGuiContainer.createRecipeItem:map is null : " + map, new Object[0]);
            return;
        }
        final ItemStack itemStack = new ItemStack(outputItem);
        boolean result = false;
        IRecipe recipe = null;
        final MCH_IRecipeList[] arr$;
        final MCH_IRecipeList[] recipeLists = arr$ = new MCH_IRecipeList[] { MCH_ItemRecipe.getInstance(), MCH_HeliInfoManager.getInstance(), MCP_PlaneInfoManager.getInstance(), MCH_VehicleInfoManager.getInstance(), MCH_TankInfoManager.getInstance() };
        for (final MCH_IRecipeList rl : arr$) {
            final int index = this.searchRecipeFromList(rl, itemStack);
            if (index >= 0) {
                recipe = this.isValidRecipe(rl, itemStack, index, map);
                break;
            }
        }
        if (recipe != null && (isCreativeMode || MCH_Lib.canPlayerCreateItem(recipe, this.player.field_71071_by))) {
            for (final Item key : map.keySet()) {
                for (int i = 0; i < map.get(key); ++i) {
                    if (!isCreativeMode) {
                        W_EntityPlayer.consumeInventoryItem(this.player, key);
                    }
                    this.func_75139_a(this.outputSlotIndex).func_75215_d(recipe.func_77571_b().func_77946_l());
                    result = true;
                }
            }
        }
        MCH_Lib.DbgLog(this.player.field_70170_p, "MCH_DraftingTableGuiContainer:Result=" + result + ":Recipe=" + recipe + " :" + outputItem.func_77658_a() + ": map=" + map, new Object[0]);
    }
    
    public IRecipe isValidRecipe(final MCH_IRecipeList list, final ItemStack itemStack, final int startIndex, final Map<Item, Integer> map) {
        for (int index = startIndex; index >= 0 && index < list.getRecipeListSize(); ++index) {
            final IRecipe recipe = list.getRecipe(index);
            if (!itemStack.func_77969_a(recipe.func_77571_b())) {
                return null;
            }
            final Map<Item, Integer> mapRecipe = MCH_Lib.getItemMapFromRecipe(recipe);
            boolean isEqual = true;
            for (final Item key : map.keySet()) {
                if (!mapRecipe.containsKey(key) || mapRecipe.get(key) != map.get(key)) {
                    isEqual = false;
                    break;
                }
            }
            if (isEqual) {
                return recipe;
            }
        }
        return null;
    }
    
    public int searchRecipeFromList(final MCH_IRecipeList list, final ItemStack item) {
        for (int i = 0; i < list.getRecipeListSize(); ++i) {
            if (list.getRecipe(i).func_77571_b().func_77969_a(item)) {
                return i;
            }
        }
        return -1;
    }
}
