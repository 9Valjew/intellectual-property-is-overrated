package mcheli.wrapper;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;

public abstract class W_GuiContainer extends GuiContainer
{
    public W_GuiContainer(final Container par1Container) {
        super(par1Container);
    }
    
    public void drawItemStack(final ItemStack item, final int x, final int y) {
        if (item == null) {
            return;
        }
        if (item.func_77973_b() == null) {
            return;
        }
        FontRenderer font = item.func_77973_b().getFontRenderer(item);
        if (font == null) {
            font = this.field_146289_q;
        }
        GL11.glEnable(2929);
        GL11.glEnable(2896);
        W_GuiContainer.field_146296_j.func_82406_b(font, this.field_146297_k.func_110434_K(), item, x, y);
        W_GuiContainer.field_146296_j.func_94148_a(font, this.field_146297_k.func_110434_K(), item, x, y, (String)null);
        this.field_73735_i = 0.0f;
        W_GuiContainer.field_146296_j.field_77023_b = 0.0f;
    }
    
    public void drawString(final String s, final int x, final int y, final int color) {
        this.func_73731_b(this.field_146289_q, s, x, y, color);
    }
    
    public void drawCenteredString(final String s, final int x, final int y, final int color) {
        this.func_73732_a(this.field_146289_q, s, x, y, color);
    }
    
    public int getStringWidth(final String s) {
        return this.field_146289_q.func_78256_a(s);
    }
}
