package mcheli.hud;

public class MCH_HudItemExit extends MCH_HudItem
{
    public MCH_HudItemExit(final int fileLine) {
        super(fileLine);
    }
    
    @Override
    public void execute() {
        this.parent.exit = true;
    }
}
