package mcheli.aircraft;

import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import mcheli.uav.*;
import net.minecraft.item.*;
import mcheli.*;
import mcheli.parachute.*;

public class MCH_AircraftGuiContainer extends Container
{
    public final EntityPlayer player;
    public final MCH_EntityAircraft aircraft;
    
    public MCH_AircraftGuiContainer(final EntityPlayer player, final MCH_EntityAircraft ac) {
        this.player = player;
        this.aircraft = ac;
        final MCH_AircraftInventory guiInventory;
        final MCH_AircraftInventory iv = guiInventory = this.aircraft.getGuiInventory();
        iv.getClass();
        this.func_75146_a(new Slot((IInventory)guiInventory, 0, 10, 30));
        final MCH_AircraftInventory mch_AircraftInventory = iv;
        iv.getClass();
        this.func_75146_a(new Slot((IInventory)mch_AircraftInventory, 1, 10, 48));
        final MCH_AircraftInventory mch_AircraftInventory2 = iv;
        iv.getClass();
        this.func_75146_a(new Slot((IInventory)mch_AircraftInventory2, 2, 10, 66));
        for (int num = this.aircraft.getNumEjectionSeat(), i = 0; i < num; ++i) {
            final MCH_AircraftInventory mch_AircraftInventory3 = iv;
            iv.getClass();
            this.func_75146_a(new Slot((IInventory)mch_AircraftInventory3, 3 + i, 10 + 18 * i, 105));
        }
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.func_75146_a(new Slot((IInventory)player.field_71071_by, 9 + x + y * 9, 25 + x * 18, 135 + y * 18));
            }
        }
        for (int x2 = 0; x2 < 9; ++x2) {
            this.func_75146_a(new Slot((IInventory)player.field_71071_by, x2, 25 + x2 * 18, 195));
        }
    }
    
    public int getInventoryStartIndex() {
        if (this.aircraft == null) {
            return 3;
        }
        return 3 + this.aircraft.getNumEjectionSeat();
    }
    
    public void func_75142_b() {
        super.func_75142_b();
    }
    
    public boolean func_75145_c(final EntityPlayer player) {
        if (this.aircraft.getGuiInventory().func_70300_a(player)) {
            return true;
        }
        if (this.aircraft.isUAV()) {
            final MCH_EntityUavStation us = this.aircraft.getUavStation();
            if (us != null) {
                final double x = us.field_70165_t + us.posUavX;
                final double z = us.field_70161_v + us.posUavZ;
                if (this.aircraft.field_70165_t < x + 10.0 && this.aircraft.field_70165_t > x - 10.0 && this.aircraft.field_70161_v < z + 10.0 && this.aircraft.field_70161_v > z - 10.0) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public ItemStack func_82846_b(final EntityPlayer player, final int slotIndex) {
        final MCH_AircraftInventory iv = this.aircraft.getGuiInventory();
        final Slot slot = this.field_75151_b.get(slotIndex);
        if (slot == null) {
            return null;
        }
        final ItemStack itemStack = slot.func_75211_c();
        MCH_Lib.DbgLog(player.field_70170_p, "transferStackInSlot : %d :" + itemStack, slotIndex);
        if (itemStack == null) {
            return null;
        }
        if (slotIndex < this.getInventoryStartIndex()) {
            for (int i = this.getInventoryStartIndex(); i < this.field_75151_b.size(); ++i) {
                final Slot playerSlot = this.field_75151_b.get(i);
                if (playerSlot.func_75211_c() == null) {
                    playerSlot.func_75215_d(itemStack);
                    slot.func_75215_d((ItemStack)null);
                    return itemStack;
                }
            }
        }
        else if (itemStack.func_77973_b() instanceof MCH_ItemFuel) {
            for (int i = 0; i < 3; ++i) {
                if (iv.getFuelSlotItemStack(i) == null) {
                    final MCH_AircraftInventory mch_AircraftInventory = iv;
                    iv.getClass();
                    mch_AircraftInventory.func_70299_a(0 + i, itemStack);
                    slot.func_75215_d((ItemStack)null);
                    return itemStack;
                }
            }
        }
        else if (itemStack.func_77973_b() instanceof MCH_ItemParachute) {
            for (int num = this.aircraft.getNumEjectionSeat(), j = 0; j < num; ++j) {
                if (iv.getParachuteSlotItemStack(j) == null) {
                    final MCH_AircraftInventory mch_AircraftInventory2 = iv;
                    iv.getClass();
                    mch_AircraftInventory2.func_70299_a(3 + j, itemStack);
                    slot.func_75215_d((ItemStack)null);
                    return itemStack;
                }
            }
        }
        return null;
    }
    
    public void func_75134_a(final EntityPlayer player) {
        super.func_75134_a(player);
        if (!player.field_70170_p.field_72995_K) {
            final MCH_AircraftInventory iv = this.aircraft.getGuiInventory();
            for (int i = 0; i < 3; ++i) {
                final ItemStack is = iv.getFuelSlotItemStack(i);
                if (is != null && !(is.func_77973_b() instanceof MCH_ItemFuel)) {
                    iv.getClass();
                    this.dropPlayerItem(player, 0 + i);
                }
            }
            for (int i = 0; i < 2; ++i) {
                final ItemStack is = iv.getParachuteSlotItemStack(i);
                if (is != null && !(is.func_77973_b() instanceof MCH_ItemParachute)) {
                    iv.getClass();
                    this.dropPlayerItem(player, 3 + i);
                }
            }
        }
    }
    
    public void dropPlayerItem(final EntityPlayer player, final int slotID) {
        if (!player.field_70170_p.field_72995_K) {
            final ItemStack itemstack = this.aircraft.getGuiInventory().func_70304_b(slotID);
            if (itemstack != null) {
                player.func_71019_a(itemstack, false);
            }
        }
    }
}
