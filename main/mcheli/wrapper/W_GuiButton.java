package mcheli.wrapper;

import net.minecraft.client.gui.*;
import java.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;

public class W_GuiButton extends GuiButton
{
    public List<String> hoverStringList;
    
    public W_GuiButton(final int par1, final int par2, final int par3, final int par4, final int par5, final String par6Str) {
        super(par1, par2, par3, par4, par5, par6Str);
        this.hoverStringList = null;
    }
    
    public void addHoverString(final String s) {
        if (this.hoverStringList == null) {
            this.hoverStringList = new ArrayList<String>();
        }
        this.hoverStringList.add(s);
    }
    
    public boolean isVisible() {
        return this.field_146125_m;
    }
    
    public void setVisible(final boolean b) {
        this.field_146125_m = b;
    }
    
    public static void setVisible(final GuiButton button, final boolean b) {
        button.field_146125_m = b;
    }
    
    public void enableBlend() {
        GL11.glEnable(3042);
        OpenGlHelper.func_148821_a(770, 771, 1, 0);
        GL11.glBlendFunc(770, 771);
    }
    
    public boolean isOnMouseOver() {
        return this.field_146123_n;
    }
    
    public void setOnMouseOver(final boolean b) {
        this.field_146123_n = b;
    }
    
    public int getWidth() {
        return this.field_146120_f;
    }
    
    public int getHeight() {
        return this.field_146121_g;
    }
}
