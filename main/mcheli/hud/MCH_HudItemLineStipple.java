package mcheli.hud;

public class MCH_HudItemLineStipple extends MCH_HudItem
{
    private final String pat;
    private final String fac;
    private final String[] pos;
    
    public MCH_HudItemLineStipple(final int fileLine, final String[] position) {
        super(fileLine);
        this.pat = position[0];
        this.fac = position[1];
        this.pos = new String[position.length - 2];
        for (int i = 0; i < position.length - 2; ++i) {
            this.pos[i] = MCH_HudItem.toFormula(position[2 + i]);
        }
    }
    
    @Override
    public void execute() {
        final double[] lines = new double[this.pos.length];
        for (int i = 0; i < lines.length; i += 2) {
            lines[i + 0] = MCH_HudItemLineStipple.centerX + MCH_HudItem.calc(this.pos[i + 0]);
            lines[i + 1] = MCH_HudItemLineStipple.centerY + MCH_HudItem.calc(this.pos[i + 1]);
        }
        this.drawLineStipple(lines, MCH_HudItemLineStipple.colorSetting, (int)MCH_HudItem.calc(this.fac), (int)MCH_HudItem.calc(this.pat));
    }
}
