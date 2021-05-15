package mcheli.tool.rangefinder;

import mcheli.gui.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;
import mcheli.wrapper.*;
import net.minecraft.util.*;
import mcheli.*;
import net.minecraft.item.*;

@SideOnly(Side.CLIENT)
public class MCH_GuiRangeFinder extends MCH_Gui
{
    public MCH_GuiRangeFinder(final Minecraft minecraft) {
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
        return MCH_ItemRangeFinder.canUse(player);
    }
    
    @Override
    public void drawGui(final EntityPlayer player, final boolean isThirdPersonView) {
        if (isThirdPersonView) {
            return;
        }
        GL11.glLineWidth((float)MCH_GuiRangeFinder.scaleFactor);
        if (!this.isDrawGui(player)) {
            return;
        }
        GL11.glDisable(3042);
        if (MCH_ItemRangeFinder.isUsingScope(player)) {
            this.drawRF(player);
        }
    }
    
    void drawRF(final EntityPlayer player) {
        GL11.glEnable(3042);
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
        final int srcBlend = GL11.glGetInteger(3041);
        final int dstBlend = GL11.glGetInteger(3040);
        GL11.glBlendFunc(770, 771);
        W_McClient.MOD_bindTexture("textures/gui/rangefinder.png");
        double size;
        for (size = 512.0; size < this.field_146294_l || size < this.field_146295_m; size *= 2.0) {}
        this.drawTexturedModalRectRotate(-(size - this.field_146294_l) / 2.0, -(size - this.field_146295_m) / 2.0, size, size, 0.0, 0.0, 256.0, 256.0, 0.0f);
        GL11.glBlendFunc(srcBlend, dstBlend);
        GL11.glDisable(3042);
        final double factor = size / 512.0;
        final double SCALE_FACTOR = MCH_GuiRangeFinder.scaleFactor * factor;
        final double CX = this.field_146297_k.field_71443_c / 2;
        final double CY = this.field_146297_k.field_71440_d / 2;
        double px = (CX - 80.0 * SCALE_FACTOR) / SCALE_FACTOR;
        double py = (CY + 55.0 * SCALE_FACTOR) / SCALE_FACTOR;
        GL11.glPushMatrix();
        GL11.glScaled(factor, factor, factor);
        final ItemStack item = player.func_71045_bC();
        final int damage = (int)((item.func_77958_k() - item.func_77960_j()) / item.func_77958_k() * 100.0);
        this.drawDigit(String.format("%3d", damage), (int)px, (int)py, 13, (damage > 0) ? -15663328 : -61424);
        if (damage <= 0) {
            this.drawString("Please craft", (int)px + 40, (int)py + 0, -65536);
            this.drawString("redstone", (int)px + 40, (int)py + 10, -65536);
        }
        px = (CX - 20.0 * SCALE_FACTOR) / SCALE_FACTOR;
        if (damage > 0) {
            final Vec3 vs = Vec3.func_72443_a(player.field_70165_t, player.field_70163_u + player.func_70047_e(), player.field_70161_v);
            Vec3 ve = MCH_Lib.Rot2Vec3(player.field_70177_z, player.field_70125_A);
            ve = vs.func_72441_c(ve.field_72450_a * 300.0, ve.field_72448_b * 300.0, ve.field_72449_c * 300.0);
            final MovingObjectPosition mop = player.field_70170_p.func_72901_a(vs, ve, true);
            if (mop != null && mop.field_72313_a != MovingObjectPosition.MovingObjectType.MISS) {
                final int range = (int)player.func_70011_f(mop.field_72307_f.field_72450_a, mop.field_72307_f.field_72448_b, mop.field_72307_f.field_72449_c);
                this.drawDigit(String.format("%4d", range), (int)px, (int)py, 13, -15663328);
            }
            else {
                this.drawDigit(String.format("----", new Object[0]), (int)px, (int)py, 13, -61424);
            }
        }
        py -= 4.0;
        px -= 80.0;
        func_73734_a((int)px, (int)py, (int)px + 30, (int)py + 2, -15663328);
        func_73734_a((int)px, (int)py, (int)px + MCH_ItemRangeFinder.rangeFinderUseCooldown / 2, (int)py + 2, -61424);
        this.drawString(String.format("x%.1f", MCH_ItemRangeFinder.zoom), (int)px, (int)py - 20, -1);
        px += 130.0;
        final int mode = MCH_ItemRangeFinder.mode;
        this.drawString(">", (int)px, (int)py - 30 + mode * 10, -1);
        px += 10.0;
        this.drawString("Players/Vehicles", (int)px, (int)py - 30, (mode == 0) ? -1 : -12566464);
        this.drawString("Monsters/Mobs", (int)px, (int)py - 20, (mode == 1) ? -1 : -12566464);
        this.drawString("Mark Point", (int)px, (int)py - 10, (mode == 2) ? -1 : -12566464);
        GL11.glPopMatrix();
        px = (CX - 160.0 * SCALE_FACTOR) / MCH_GuiRangeFinder.scaleFactor;
        py = (CY - 100.0 * SCALE_FACTOR) / MCH_GuiRangeFinder.scaleFactor;
        if (px < 10.0) {
            px = 10.0;
        }
        if (py < 10.0) {
            py = 10.0;
        }
        final StringBuilder append = new StringBuilder().append("Spot      : ");
        final MCH_Config config = MCH_MOD.config;
        String s = append.append(MCH_KeyName.getDescOrName(MCH_Config.KeyAttack.prmInt)).toString();
        this.drawString(s, (int)px, (int)py + 0, -1);
        final StringBuilder append2 = new StringBuilder().append("Zoom in   : ");
        final MCH_Config config2 = MCH_MOD.config;
        s = append2.append(MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt)).toString();
        this.drawString(s, (int)px, (int)py + 10, (MCH_ItemRangeFinder.zoom < 10.0f) ? -1 : -12566464);
        final StringBuilder append3 = new StringBuilder().append("Zoom out : ");
        final MCH_Config config3 = MCH_MOD.config;
        s = append3.append(MCH_KeyName.getDescOrName(MCH_Config.KeySwWeaponMode.prmInt)).toString();
        this.drawString(s, (int)px, (int)py + 20, (MCH_ItemRangeFinder.zoom > 1.2f) ? -1 : -12566464);
        final StringBuilder append4 = new StringBuilder().append("Mode      : ");
        final MCH_Config config4 = MCH_MOD.config;
        s = append4.append(MCH_KeyName.getDescOrName(MCH_Config.KeyFlare.prmInt)).toString();
        this.drawString(s, (int)px, (int)py + 30, -1);
    }
}
