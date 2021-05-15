package mcheli.gui;

import cpw.mods.fml.relauncher.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import mcheli.wrapper.*;
import java.util.*;

@SideOnly(Side.CLIENT)
public abstract class MCH_Gui extends GuiScreen
{
    protected int centerX;
    protected int centerY;
    protected Random rand;
    protected float smoothCamPartialTicks;
    public static int scaleFactor;
    
    public MCH_Gui(final Minecraft minecraft) {
        this.centerX = 0;
        this.centerY = 0;
        this.rand = new Random();
        this.field_146297_k = minecraft;
        this.smoothCamPartialTicks = 0.0f;
        this.field_73735_i = -110.0f;
    }
    
    public void func_73866_w_() {
        super.func_73866_w_();
    }
    
    public boolean func_73868_f() {
        return false;
    }
    
    public void onTick() {
    }
    
    public abstract boolean isDrawGui(final EntityPlayer p0);
    
    public abstract void drawGui(final EntityPlayer p0, final boolean p1);
    
    public void func_73863_a(final int par1, final int par2, final float partialTicks) {
        this.smoothCamPartialTicks = partialTicks;
        final ScaledResolution scaledresolution = new W_ScaledResolution(this.field_146297_k, this.field_146297_k.field_71443_c, this.field_146297_k.field_71440_d);
        MCH_Gui.scaleFactor = scaledresolution.func_78325_e();
        if (!this.field_146297_k.field_71474_y.field_74319_N) {
            this.field_146294_l = this.field_146297_k.field_71443_c / MCH_Gui.scaleFactor;
            this.field_146295_m = this.field_146297_k.field_71440_d / MCH_Gui.scaleFactor;
            this.centerX = this.field_146294_l / 2;
            this.centerY = this.field_146295_m / 2;
            GL11.glPushMatrix();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            if (this.field_146297_k.field_71439_g != null) {
                this.drawGui((EntityPlayer)this.field_146297_k.field_71439_g, this.field_146297_k.field_71474_y.field_74320_O != 0);
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glPopMatrix();
        }
    }
    
    public void drawTexturedModalRectRotate(final double left, final double top, final double width, final double height, final double uLeft, final double vTop, final double uWidth, final double vHeight, final float rot) {
        GL11.glPushMatrix();
        GL11.glTranslated(left + width / 2.0, top + height / 2.0, 0.0);
        GL11.glRotatef(rot, 0.0f, 0.0f, 1.0f);
        final float f = 0.00390625f;
        final Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78382_b();
        tessellator.func_78374_a(-width / 2.0, height / 2.0, (double)this.field_73735_i, uLeft * 0.00390625, (vTop + vHeight) * 0.00390625);
        tessellator.func_78374_a(width / 2.0, height / 2.0, (double)this.field_73735_i, (uLeft + uWidth) * 0.00390625, (vTop + vHeight) * 0.00390625);
        tessellator.func_78374_a(width / 2.0, -height / 2.0, (double)this.field_73735_i, (uLeft + uWidth) * 0.00390625, vTop * 0.00390625);
        tessellator.func_78374_a(-width / 2.0, -height / 2.0, (double)this.field_73735_i, uLeft * 0.00390625, vTop * 0.00390625);
        tessellator.func_78381_a();
        GL11.glPopMatrix();
    }
    
    public void drawTexturedRect(final double left, final double top, final double width, final double height, final double uLeft, final double vTop, final double uWidth, final double vHeight, final double textureWidth, final double textureHeight) {
        final float fx = (float)(1.0 / textureWidth);
        final float fy = (float)(1.0 / textureHeight);
        final Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78382_b();
        tessellator.func_78374_a(left, top + height, (double)this.field_73735_i, uLeft * fx, (vTop + vHeight) * fy);
        tessellator.func_78374_a(left + width, top + height, (double)this.field_73735_i, (uLeft + uWidth) * fx, (vTop + vHeight) * fy);
        tessellator.func_78374_a(left + width, top, (double)this.field_73735_i, (uLeft + uWidth) * fx, vTop * fy);
        tessellator.func_78374_a(left, top, (double)this.field_73735_i, uLeft * fx, vTop * fy);
        tessellator.func_78381_a();
    }
    
    public void drawLineStipple(final double[] line, final int color, final int factor, final int pattern) {
        GL11.glEnable(2852);
        GL11.glLineStipple(factor, (short)pattern);
        this.drawLine(line, color);
        GL11.glDisable(2852);
    }
    
    public void drawLine(final double[] line, final int color) {
        this.drawLine(line, color, 1);
    }
    
    public void drawString(final String s, final int x, final int y, final int color) {
        this.func_73731_b(this.field_146297_k.field_71466_p, s, x, y, color);
    }
    
    public void drawDigit(final String s, final int x, final int y, final int interval, final int color) {
        GL11.glEnable(3042);
        GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color & 0xFF), (byte)(color >> 24 & 0xFF));
        final int srcBlend = GL11.glGetInteger(3041);
        final int dstBlend = GL11.glGetInteger(3040);
        GL11.glBlendFunc(770, 771);
        W_McClient.MOD_bindTexture("textures/gui/digit.png");
        for (int i = 0; i < s.length(); ++i) {
            final char c = s.charAt(i);
            if (c >= '0' && c <= '9') {
                this.func_73729_b(x + interval * i, y, '\u0010' * (c - '0'), 0, 16, 16);
            }
            if (c == '-') {
                this.func_73729_b(x + interval * i, y, 160, 0, 16, 16);
            }
        }
        GL11.glBlendFunc(srcBlend, dstBlend);
        GL11.glDisable(3042);
    }
    
    public void drawCenteredString(final String s, final int x, final int y, final int color) {
        this.func_73732_a(this.field_146297_k.field_71466_p, s, x, y, color);
    }
    
    public void drawLine(final double[] line, final int color, final int mode) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color >> 0 & 0xFF), (byte)(color >> 24 & 0xFF));
        final Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78371_b(mode);
        for (int i = 0; i < line.length; i += 2) {
            tessellator.func_78377_a(line[i + 0], line[i + 1], (double)this.field_73735_i);
        }
        tessellator.func_78381_a();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glColor4b((byte)(-1), (byte)(-1), (byte)(-1), (byte)(-1));
        GL11.glPopMatrix();
    }
    
    public void drawPoints(final double[] points, final int color, final int pointWidth) {
        final int prevWidth = GL11.glGetInteger(2833);
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color >> 0 & 0xFF), (byte)(color >> 24 & 0xFF));
        GL11.glPointSize((float)pointWidth);
        final Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78371_b(0);
        for (int i = 0; i < points.length; i += 2) {
            tessellator.func_78377_a(points[i], points[i + 1], 0.0);
        }
        tessellator.func_78381_a();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glColor4b((byte)(-1), (byte)(-1), (byte)(-1), (byte)(-1));
        GL11.glPointSize((float)prevWidth);
    }
    
    public void drawPoints(final ArrayList<Double> points, final int color, final int pointWidth) {
        final int prevWidth = GL11.glGetInteger(2833);
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color >> 0 & 0xFF), (byte)(color >> 24 & 0xFF));
        GL11.glPointSize((float)pointWidth);
        final Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78371_b(0);
        for (int i = 0; i < points.size(); i += 2) {
            tessellator.func_78377_a((double)points.get(i), (double)points.get(i + 1), 0.0);
        }
        tessellator.func_78381_a();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glColor4b((byte)(-1), (byte)(-1), (byte)(-1), (byte)(-1));
        GL11.glPointSize((float)prevWidth);
    }
}
