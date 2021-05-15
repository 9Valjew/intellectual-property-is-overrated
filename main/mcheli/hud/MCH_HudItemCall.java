package mcheli.hud;

public class MCH_HudItemCall extends MCH_HudItem
{
    private final String hudName;
    
    public MCH_HudItemCall(final int fileLine, final String name) {
        super(fileLine);
        this.hudName = name;
    }
    
    @Override
    public void execute() {
        final MCH_Hud hud = MCH_HudManager.get(this.hudName);
        if (hud != null) {
            hud.drawItems();
        }
    }
}
