package mcheli.hud;

public class MCH_HudItemRect extends MCH_HudItem
{
    private final String left;
    private final String top;
    private final String width;
    private final String height;
    
    public MCH_HudItemRect(final int fileLine, final String left, final String top, final String width, final String height) {
        super(fileLine);
        this.left = MCH_HudItem.toFormula(left);
        this.top = MCH_HudItem.toFormula(top);
        this.width = MCH_HudItem.toFormula(width);
        this.height = MCH_HudItem.toFormula(height);
    }
    
    @Override
    public void execute() {
        final double x2 = MCH_HudItemRect.centerX + MCH_HudItem.calc(this.left);
        final double y2 = MCH_HudItemRect.centerY + MCH_HudItem.calc(this.top);
        final double x3 = x2 + (int)MCH_HudItem.calc(this.width);
        final double y3 = y2 + (int)MCH_HudItem.calc(this.height);
        MCH_HudItem.drawRect(x3, y3, x2, y2, MCH_HudItemRect.colorSetting);
    }
}
