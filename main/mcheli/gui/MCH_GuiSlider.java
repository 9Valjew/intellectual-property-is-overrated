package mcheli.gui;

import mcheli.wrapper.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import mcheli.*;
import net.minecraft.util.*;

public class MCH_GuiSlider extends W_GuiButton
{
    private float currentSlider;
    private boolean isMousePress;
    public String stringFormat;
    public float valueMin;
    public float valueMax;
    public float valueStep;
    
    public MCH_GuiSlider(final int gui_id, final int posX, final int posY, final int sliderWidth, final int sliderHeight, final String string_format, final float defaultSliderPos, final float minVal, final float maxVal, final float step) {
        super(gui_id, posX, posY, sliderWidth, sliderHeight, "");
        this.valueMin = 0.0f;
        this.valueMax = 1.0f;
        this.valueStep = 0.1f;
        this.stringFormat = string_format;
        this.valueMin = minVal;
        this.valueMax = maxVal;
        this.valueStep = step;
        this.setSliderValue(defaultSliderPos);
    }
    
    public int func_146114_a(final boolean p_146114_1_) {
        return 0;
    }
    
    protected void func_146119_b(final Minecraft mc, final int x, final int y) {
        if (this.isVisible()) {
            if (this.isMousePress) {
                this.currentSlider = (x - (this.field_146128_h + 4)) / (this.field_146120_f - 8);
                if (this.currentSlider < 0.0f) {
                    this.currentSlider = 0.0f;
                }
                if (this.currentSlider > 1.0f) {
                    this.currentSlider = 1.0f;
                }
                this.currentSlider = this.normalizeValue(this.denormalizeValue(this.currentSlider));
                this.updateDisplayString();
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.func_73729_b(this.field_146128_h + (int)(this.currentSlider * (this.field_146120_f - 8)), this.field_146129_i, 0, 66, 4, 20);
            this.func_73729_b(this.field_146128_h + (int)(this.currentSlider * (this.field_146120_f - 8)) + 4, this.field_146129_i, 196, 66, 4, 20);
            if (!MCH_Key.isKeyDown(-100)) {
                this.func_146118_a(x, y);
            }
        }
    }
    
    public void updateDisplayString() {
        this.field_146126_j = String.format(this.stringFormat, this.denormalizeValue(this.currentSlider));
    }
    
    public void setSliderValue(final float f) {
        this.currentSlider = this.normalizeValue(f);
        this.updateDisplayString();
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
            this.currentSlider = (x - (this.field_146128_h + 4)) / (this.field_146120_f - 8);
            if (this.currentSlider < 0.0f) {
                this.currentSlider = 0.0f;
            }
            if (this.currentSlider > 1.0f) {
                this.currentSlider = 1.0f;
            }
            this.updateDisplayString();
            return this.isMousePress = true;
        }
        return false;
    }
    
    public void func_146118_a(final int p_146118_1_, final int p_146118_2_) {
        this.isMousePress = false;
    }
}
