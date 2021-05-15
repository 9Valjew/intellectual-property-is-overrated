package mcheli.multiplay;

import mcheli.wrapper.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.gui.*;
import mcheli.*;
import java.util.*;

public class MCH_GuiScoreboard_Main extends MCH_GuiScoreboard_Base
{
    private W_GuiButton buttonSwitchPVP;
    
    public MCH_GuiScoreboard_Main(final MCH_IGuiScoreboard switcher, final EntityPlayer player) {
        super(switcher, player);
    }
    
    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        if (this.buttonSwitchPVP != null) {
            return;
        }
        this.field_147003_i = 0;
        this.field_147009_r = 0;
        int WIDTH = MCH_GuiScoreboard_Base.getScoreboradWidth(this.field_146297_k) * 3 / 4;
        if (WIDTH < 80) {
            WIDTH = 80;
        }
        final int LEFT = MCH_GuiScoreboard_Base.getScoreBoardLeft(this.field_146297_k, this.getTeamNum() + 1, 0) / 4;
        this.buttonSwitchPVP = new W_GuiButton(1024, LEFT, 80, WIDTH, 20, "");
        this.listGui.add((Gui)this.buttonSwitchPVP);
        W_GuiButton btn = new W_GuiButton(256, LEFT, 100, WIDTH, 20, "Team shuffle");
        btn.addHoverString("Shuffle all players.");
        this.listGui.add((Gui)btn);
        this.listGui.add((Gui)new W_GuiButton(512, LEFT, 120, WIDTH, 20, "New team"));
        btn = new W_GuiButton(768, LEFT, 140, WIDTH, 20, "Jump spawn pos");
        btn.addHoverString("Teleport all players -> spawn point.");
        this.listGui.add((Gui)btn);
        btn = new W_GuiButton(1280, LEFT, 160, WIDTH, 20, "Destroy All");
        btn.addHoverString("Destroy all aircraft and vehicle.");
        this.listGui.add((Gui)btn);
    }
    
    protected void func_73869_a(final char c, final int code) {
        if (code == 1) {
            this.field_146297_k.field_71439_g.func_71053_j();
        }
    }
    
    @Override
    public void updateScreenButtons(final List list) {
        for (final Object o : list) {
            final GuiButton button = (GuiButton)o;
            if (button.field_146127_k == 1024) {
                button.field_146126_j = "PVP : " + (MCH_ServerSettings.enablePVP ? "ON" : "OFF");
            }
        }
    }
    
    protected void func_146284_a(final GuiButton btn) {
        if (btn != null && btn.field_146124_l) {
            switch (btn.field_146127_k) {
                case 256: {
                    MCH_PacketIndMultiplayCommand.send(256, "");
                    break;
                }
                case 768: {
                    MCH_PacketIndMultiplayCommand.send(512, "");
                    break;
                }
                case 512: {
                    this.switchScreen(SCREEN_ID.CREATE_TEAM);
                    break;
                }
                case 1024: {
                    MCH_PacketIndMultiplayCommand.send(1024, "");
                    break;
                }
                case 1280: {
                    MCH_PacketIndMultiplayCommand.send(1280, "");
                    break;
                }
            }
        }
    }
    
    @Override
    public void drawGuiContainerForegroundLayerScreen(final int x, final int y) {
        super.drawGuiContainerForegroundLayerScreen(x, y);
    }
    
    @Override
    protected void func_146976_a(final float par1, final int par2, final int par3) {
        MCH_GuiScoreboard_Base.drawList(this.field_146297_k, this.field_146289_q, true);
    }
}
