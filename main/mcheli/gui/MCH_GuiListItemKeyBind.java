package mcheli.gui;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import mcheli.*;

public class MCH_GuiListItemKeyBind extends MCH_GuiListItem
{
    public String displayString;
    public GuiButton button;
    public GuiButton buttonReset;
    public int keycode;
    public final int defaultKeycode;
    public MCH_ConfigPrm config;
    public GuiButton lastPushButton;
    
    public MCH_GuiListItemKeyBind(final int id, final int idReset, final int posX, final String dispStr, final MCH_ConfigPrm prm) {
        this.displayString = dispStr;
        this.defaultKeycode = prm.prmIntDefault;
        this.button = new GuiButton(id, posX + 160, 0, 70, 20, "");
        this.buttonReset = new GuiButton(idReset, posX + 240, 0, 40, 20, "Reset");
        this.config = prm;
        this.lastPushButton = null;
        this.setKeycode(prm.prmInt);
    }
    
    @Override
    public void mouseReleased(final int x, final int y) {
        this.button.func_146118_a(x, y);
        this.buttonReset.func_146118_a(x, y);
    }
    
    @Override
    public boolean mousePressed(final Minecraft mc, final int x, final int y) {
        if (this.button.func_146116_c(mc, x, y)) {
            this.lastPushButton = this.button;
            return true;
        }
        if (this.buttonReset.func_146116_c(mc, x, y)) {
            this.lastPushButton = this.buttonReset;
            return true;
        }
        return false;
    }
    
    @Override
    public void draw(final Minecraft mc, final int mouseX, final int mouseY, final int posX, final int posY) {
        final int y = 6;
        this.button.func_73731_b(mc.field_71466_p, this.displayString, posX + 10, posY + y, -1);
        this.button.field_146129_i = posY;
        this.button.func_146112_a(mc, mouseX, mouseY);
        this.buttonReset.field_146124_l = (this.keycode != this.defaultKeycode);
        this.buttonReset.field_146129_i = posY;
        this.buttonReset.func_146112_a(mc, mouseX, mouseY);
    }
    
    public void applyKeycode() {
        this.config.setPrm(this.keycode);
    }
    
    public void resetKeycode() {
        this.setKeycode(this.defaultKeycode);
    }
    
    public void setKeycode(final int k) {
        if (k != 0 && !MCH_KeyName.getDescOrName(k).isEmpty()) {
            this.keycode = k;
            this.button.field_146126_j = MCH_KeyName.getDescOrName(k);
        }
    }
}
