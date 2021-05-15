package mcheli.gui;

import mcheli.wrapper.*;
import net.minecraft.client.*;

public class MCH_GuiOnOffButton extends W_GuiButton
{
    private boolean statOnOff;
    private final String dispOnOffString;
    
    public MCH_GuiOnOffButton(final int par1, final int par2, final int par3, final int par4, final int par5, final String par6Str) {
        super(par1, par2, par3, par4, par5, "");
        this.dispOnOffString = par6Str;
        this.setOnOff(false);
    }
    
    public void setOnOff(final boolean b) {
        this.statOnOff = b;
        this.field_146126_j = this.dispOnOffString + (this.getOnOff() ? "ON" : "OFF");
    }
    
    public boolean getOnOff() {
        return this.statOnOff;
    }
    
    public void switchOnOff() {
        this.setOnOff(!this.getOnOff());
    }
    
    public boolean func_146116_c(final Minecraft mc, final int x, final int y) {
        if (super.func_146116_c(mc, x, y)) {
            this.switchOnOff();
            return true;
        }
        return false;
    }
}
