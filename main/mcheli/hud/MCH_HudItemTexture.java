package mcheli.hud;

import org.lwjgl.opengl.*;
import mcheli.wrapper.*;

public class MCH_HudItemTexture extends MCH_HudItem
{
    private final String name;
    private final String left;
    private final String top;
    private final String width;
    private final String height;
    private final String uLeft;
    private final String vTop;
    private final String uWidth;
    private final String vHeight;
    private final String rot;
    private int textureWidth;
    private int textureHeight;
    
    public MCH_HudItemTexture(final int fileLine, final String name, final String left, final String top, final String width, final String height, final String uLeft, final String vTop, final String uWidth, final String vHeight, final String rot) {
        super(fileLine);
        this.name = name;
        this.left = MCH_HudItem.toFormula(left);
        this.top = MCH_HudItem.toFormula(top);
        this.width = MCH_HudItem.toFormula(width);
        this.height = MCH_HudItem.toFormula(height);
        this.uLeft = MCH_HudItem.toFormula(uLeft);
        this.vTop = MCH_HudItem.toFormula(vTop);
        this.uWidth = MCH_HudItem.toFormula(uWidth);
        this.vHeight = MCH_HudItem.toFormula(vHeight);
        this.rot = MCH_HudItem.toFormula(rot);
        final boolean b = false;
        this.textureHeight = (b ? 1 : 0);
        this.textureWidth = (b ? 1 : 0);
    }
    
    @Override
    public void execute() {
        GL11.glEnable(3042);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (this.textureWidth == 0 || this.textureHeight == 0) {
            int w = 0;
            int h = 0;
            final W_TextureUtil.TextureParam prm = W_TextureUtil.getTextureInfo("mcheli", "textures/gui/" + this.name + ".png");
            if (prm != null) {
                w = prm.width;
                h = prm.height;
            }
            this.textureWidth = ((w > 0) ? w : 256);
            this.textureHeight = ((h > 0) ? h : 256);
        }
        this.drawTexture(this.name, MCH_HudItemTexture.centerX + MCH_HudItem.calc(this.left), MCH_HudItemTexture.centerY + MCH_HudItem.calc(this.top), MCH_HudItem.calc(this.width), MCH_HudItem.calc(this.height), MCH_HudItem.calc(this.uLeft), MCH_HudItem.calc(this.vTop), MCH_HudItem.calc(this.uWidth), MCH_HudItem.calc(this.vHeight), (float)MCH_HudItem.calc(this.rot), this.textureWidth, this.textureHeight);
    }
}
