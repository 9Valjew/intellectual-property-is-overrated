package mcheli.uav;

import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class MCH_ContainerUavStation extends Container
{
    protected MCH_EntityUavStation uavStation;
    
    public MCH_ContainerUavStation(final InventoryPlayer inventoryPlayer, final MCH_EntityUavStation te) {
        this.uavStation = te;
        this.func_75146_a(new Slot((IInventory)this.uavStation, 0, 20, 20));
        this.bindPlayerInventory(inventoryPlayer);
    }
    
    public boolean func_75145_c(final EntityPlayer player) {
        return this.uavStation.func_70300_a(player);
    }
    
    public void func_75130_a(final IInventory par1IInventory) {
        super.func_75130_a(par1IInventory);
    }
    
    protected void bindPlayerInventory(final InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.func_75146_a(new Slot((IInventory)inventoryPlayer, 9 + (j + i * 9), 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.func_75146_a(new Slot((IInventory)inventoryPlayer, i, 8 + i * 18, 142));
        }
    }
    
    public ItemStack func_82846_b(final EntityPlayer player, final int slot) {
        return null;
    }
}
