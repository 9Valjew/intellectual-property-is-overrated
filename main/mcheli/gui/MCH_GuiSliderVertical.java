package mcheli.gui;

import mcheli.wrapper.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import mcheli.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;

public class MCH_GuiSliderVertical extends W_GuiButton
{
    private float currentSlider;
    private boolean isMousePress;
    public float valueMin;
    public float valueMax;
    public float valueStep;
    
    public MCH_GuiSliderVertical(final int gui_id, final int posX, final int posY, final int sliderWidth, final int sliderHeight, final String string, final float defaultSliderPos, final float minVal, final float maxVal, final float step) {
        super(gui_id, posX, posY, sliderWidth, sliderHeight, string);
        this.valueMin = 0.0f;
        this.valueMax = 1.0f;
        this.valueStep = 0.1f;
        this.valueMin = minVal;
        this.valueMax = maxVal;
        this.valueStep = step;
        this.setSliderValue(defaultSliderPos);
    }
    
    public int func_146114_a(final boolean p_146114_1_) {
        return 0;
    }
    
    public void scrollUp(final float a) {
        if (this.isVisible() && !this.isMousePress) {
            this.setSliderValue(this.getSliderValue() + this.valueStep * a);
        }
    }
    
    public void scrollDown(final float a) {
        if (this.isVisible() && !this.isMousePress) {
            this.setSliderValue(this.getSliderValue() - this.valueStep * a);
        }
    }
    
    protected void func_146119_b(final Minecraft mc, final int x, final int y) {
        if (this.isVisible()) {
            if (this.isMousePress) {
                this.currentSlider = (y - (this.field_146129_i + 4)) / (this.field_146121_g - 8);
                if (this.currentSlider < 0.0f) {
                    this.currentSlider = 0.0f;
                }
                if (this.currentSlider > 1.0f) {
                    this.currentSlider = 1.0f;
                }
                this.currentSlider = this.normalizeValue(this.denormalizeValue(this.currentSlider));
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.func_73729_b(this.field_146128_h, this.field_146129_i + (int)(this.currentSlider * (this.field_146121_g - 8)), 66, 0, 20, 4);
            this.func_73729_b(this.field_146128_h, this.field_146129_i + (int)(this.currentSlider * (this.field_146121_g - 8)) + 4, 66, 196, 20, 4);
            if (!MCH_Key.isKeyDown(-100)) {
                this.func_146118_a(x, y);
            }
        }
    }
    
    public void setSliderValue(final float f) {
        this.currentSlider = this.normalizeValue(f);
    }
    
    public float getSliderValue() {
        return this.denormalizeValue(this.currentSlider);
    }
    
    public float getSliderValueInt(int digit) {
        int d = 1;
        while (digit > 0) {
            d *= 10;
            --digit;
        }
        final int n = (int)(this.denormalizeValue(this.currentSlider) * d);
        return n / d;
    }
    
    public float normalizeValue(final float f) {
        return MathHelper.func_76131_a((this.snapToStepClamp(f) - this.valueMin) / (this.valueMax - this.valueMin), 0.0f, 1.0f);
    }
    
    public float denormalizeValue(final float f) {
        return this.snapToStepClamp(this.valueMin + (this.valueMax - this.valueMin) * MathHelper.func_76131_a(f, 0.0f, 1.0f));
    }
    
    public float snapToStepClamp(float f) {
        f = this.snapToStep(f);
        return MathHelper.func_76131_a(f, this.valueMin, this.valueMax);
    }
    
    protected float snapToStep(float f) {
        if (this.valueStep > 0.0f) {
            f = this.valueStep * Math.round(f / this.valueStep);
        }
        return f;
    }
    
    public boolean func_146116_c(final Minecraft mc, final int x, final int y) {
        if (super.func_146116_c(mc, x, y)) {
            this.currentSlider = (y - (this.field_146129_i + 4)) / (this.field_146121_g - 8);
            if (this.currentSlider < 0.0f) {
                this.currentSlider = 0.0f;
            }
            if (this.currentSlider > 1.0f) {
                this.currentSlider = 1.0f;
            }
            return this.isMousePress = true;
        }
        return false;
    }
    
    public void func_146118_a(final int p_146118_1_, final int p_146118_2_) {
        this.isMousePress = false;
    }
    
    public void func_146112_a(final Minecraft mc, final int x, final int y) {
        if (this.isVisible()) {
            final FontRenderer fontrenderer = mc.field_71466_p;
            mc.func_110434_K().func_110577_a(new ResourceLocation("mcheli", "textures/gui/widgets.png"));
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.setOnMouseOver(x >= this.field_146128_h && y >= this.field_146129_i && x < this.field_146128_h + this.field_146120_f && y < this.field_146129_i + this.field_146121_g);
            final int k = this.func_146114_a(this.isOnMouseOver());
            this.enableBlend();
            this.func_73729_b(this.field_146128_h, this.field_146129_i, 46 + k * 20, 0, this.field_146120_f, this.field_146121_g / 2);
            this.func_73729_b(this.field_146128_h, this.field_146129_i + this.field_146121_g / 2, 46 + k * 20, 200 - this.field_146121_g / 2, this.field_146120_f, this.field_146121_g / 2);
            this.func_146119_b(mc, x, y);
            int l = 14737632;
            if (this.packedFGColour != 0) {
                l = this.packedFGColour;
            }
            else if (!this.field_146124_l) {
                l = 10526880;
            }
            else if (this.isOnMouseOver()) {
                l = 16777120;
            }
            this.func_73732_a(fontrenderer, this.field_146126_j, this.field_146128_h + this.field_146120_f / 2, this.field_146129_i + (this.field_146121_g - 8) / 2, l);
            mc.func_110434_K().func_110577_a(MCH_GuiSliderVertical.field_146122_a);
        }
    }
}
