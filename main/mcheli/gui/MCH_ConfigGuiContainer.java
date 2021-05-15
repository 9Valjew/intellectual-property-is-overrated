package mcheli.gui;

import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class MCH_ConfigGuiContainer extends Container
{
    public final EntityPlayer player;
    
    public MCH_ConfigGuiContainer(final EntityPlayer player) {
        this.player = player;
    }
    
    public void func_75142_b() {
        super.func_75142_b();
    }
    
    public boolean func_75145_c(final EntityPlayer player) {
        return true;
    }
    
    public ItemStack func_82846_b(final EntityPlayer par1EntityPlayer, final int par2) {
        return null;
    }
}
