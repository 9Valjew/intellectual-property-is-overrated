package mcheli.hud;

public class MCH_HudItemColor extends MCH_HudItem
{
    private final String updateColor;
    
    public MCH_HudItemColor(final int fileLine, final String newColor) {
        super(fileLine);
        this.updateColor = newColor;
    }
    
    public static MCH_HudItemColor createByParams(final int fileLine, final String[] prm) {
        if (prm.length == 1) {
            return new MCH_HudItemColor(fileLine, MCH_HudItem.toFormula(prm[0]));
        }
        if (prm.length == 4) {
            return new MCH_HudItemColor(fileLine, "((" + MCH_HudItem.toFormula(prm[0]) + ")<<24)|" + "((" + MCH_HudItem.toFormula(prm[1]) + ")<<16)|" + "((" + MCH_HudItem.toFormula(prm[2]) + ")<<8 )|" + "((" + MCH_HudItem.toFormula(prm[3]) + ")<<0 )");
        }
        return null;
    }
    
    @Override
    public void execute() {
        final double d = MCH_HudItem.calc(this.updateColor);
        final long l = (long)d;
        MCH_HudItem.colorSetting = (int)l;
        MCH_HudItem.updateVarMapItem("color", MCH_HudItem.getColor());
    }
}
