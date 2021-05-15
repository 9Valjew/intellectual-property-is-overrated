package mcheli.hud;

public class MCH_HudItemLine extends MCH_HudItem
{
    private final String[] pos;
    
    public MCH_HudItemLine(final int fileLine, final String[] position) {
        super(fileLine);
        this.pos = new String[position.length];
        for (int i = 0; i < position.length; ++i) {
            this.pos[i] = position[i].toLowerCase();
        }
    }
    
    @Override
    public void execute() {
        final double[] lines = new double[this.pos.length];
        for (int i = 0; i < lines.length; i += 2) {
            lines[i + 0] = MCH_HudItemLine.centerX + MCH_HudItem.calc(this.pos[i + 0]);
            lines[i + 1] = MCH_HudItemLine.centerY + MCH_HudItem.calc(this.pos[i + 1]);
        }
        this.drawLine(lines, MCH_HudItemLine.colorSetting, 3);
    }
}
