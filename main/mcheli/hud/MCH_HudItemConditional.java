package mcheli.hud;

public class MCH_HudItemConditional extends MCH_HudItem
{
    private final boolean isEndif;
    private final String conditional;
    
    public MCH_HudItemConditional(final int fileLine, final boolean isEndif, final String conditional) {
        super(fileLine);
        this.isEndif = isEndif;
        this.conditional = conditional;
    }
    
    @Override
    public boolean canExecute() {
        return true;
    }
    
    @Override
    public void execute() {
        if (!this.isEndif) {
            this.parent.isIfFalse = (MCH_HudItem.calc(this.conditional) == 0.0);
        }
        else {
            this.parent.isIfFalse = false;
        }
    }
}
