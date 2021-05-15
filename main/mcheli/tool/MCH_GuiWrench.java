package mcheli.tool;

import mcheli.gui.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;
import mcheli.aircraft.*;

@SideOnly(Side.CLIENT)
public class MCH_GuiWrench extends MCH_Gui
{
    public MCH_GuiWrench(final Minecraft minecraft) {
        super(minecraft);
    }
    
    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
    }
    
    @Override
    public boolean func_73868_f() {
        return false;
    }
    
    @Override
    public boolean isDrawGui(final EntityPlayer player) {
        return player != null && player.field_70170_p != null && player.func_71045_bC() != null && player.func_71045_bC().func_77973_b() instanceof MCH_ItemWrench;
    }
    
    @Override
    public void drawGui(final EntityPlayer player, final boolean isThirdPersonView) {
        if (isThirdPersonView) {
            return;
        }
        GL11.glLineWidth((float)MCH_GuiWrench.scaleFactor);
        if (!this.isDrawGui(player)) {
            return;
        }
        GL11.glDisable(3042);
        final MCH_EntityAircraft ac = ((MCH_ItemWrench)player.func_71045_bC().func_77973_b()).getMouseOverAircraft(player);
        if (ac != null && ac.getMaxHP() > 0) {
            final int color = (ac.getHP() / ac.getMaxHP() > 0.3) ? -14101432 : -2161656;
            this.drawHP(color, -15433180, ac.getHP(), ac.getMaxHP());
        }
    }
    
    void drawHP(final int color, final int colorBG, int hp, final int hpmax) {
        final int posX = this.centerX;
        final int posY = this.centerY + 20;
        final int WID = 20;
        final int INV = 10;
        func_73734_a(posX - 20, posY + 20 + 1, posX - 20 + 40, posY + 20 + 1 + 1 + 3 + 1, colorBG);
        if (hp > hpmax) {
            hp = hpmax;
        }
        final float hpp = hp / hpmax;
        func_73734_a(posX - 20 + 1, posY + 20 + 1 + 1, posX - 20 + 1 + (int)(38.0 * hpp), posY + 20 + 1 + 1 + 3, color);
        int hppn = (int)(hpp * 100.0f);
        if (hp < hpmax && hppn >= 100) {
            hppn = 99;
        }
        this.drawCenteredString(String.format("%d %%", hppn), posX, posY + 30, color);
    }
}
