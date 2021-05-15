package mcheli.multiplay;

import net.minecraft.entity.player.*;
import net.minecraft.client.gui.*;
import mcheli.wrapper.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;

public class MCH_GuiScoreboard_CreateTeam extends MCH_GuiScoreboard_Base
{
    private GuiButton buttonCreateTeamOK;
    private GuiButton buttonCreateTeamFF;
    private GuiTextField editCreateTeamName;
    private static boolean friendlyFire;
    private int lastTeamColor;
    private static final String[] colorNames;
    
    public MCH_GuiScoreboard_CreateTeam(final MCH_IGuiScoreboard switcher, final EntityPlayer player) {
        super(switcher, player);
        this.lastTeamColor = 0;
    }
    
    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        final ScaledResolution sr = new W_ScaledResolution(this.field_146297_k, this.field_146297_k.field_71443_c, this.field_146297_k.field_71440_d);
        final int factor = (sr.func_78325_e() > 0) ? sr.func_78325_e() : 1;
        this.field_147003_i = 0;
        this.field_147009_r = 0;
        final int x = this.field_146297_k.field_71443_c / 2 / factor;
        final int y = this.field_146297_k.field_71440_d / 2 / factor;
        final GuiButton buttonCTNextC = new GuiButton(576, x + 40, y - 20, 40, 20, ">");
        final GuiButton buttonCTPrevC = new GuiButton(577, x - 80, y - 20, 40, 20, "<");
        this.buttonCreateTeamFF = new GuiButton(560, x - 80, y + 20, 160, 20, "");
        this.buttonCreateTeamOK = new GuiButton(528, x - 80, y + 60, 80, 20, "OK");
        final GuiButton buttonCTCancel = new GuiButton(544, x + 0, y + 60, 80, 20, "Cancel");
        (this.editCreateTeamName = new GuiTextField(this.field_146289_q, x - 80, y - 55, 160, 20)).func_146180_a("");
        this.editCreateTeamName.func_146193_g(-1);
        this.editCreateTeamName.func_146203_f(16);
        this.editCreateTeamName.func_146195_b(true);
        this.listGui.add((Gui)buttonCTNextC);
        this.listGui.add((Gui)buttonCTPrevC);
        this.listGui.add((Gui)this.buttonCreateTeamFF);
        this.listGui.add((Gui)this.buttonCreateTeamOK);
        this.listGui.add((Gui)buttonCTCancel);
        this.listGui.add((Gui)this.editCreateTeamName);
    }
    
    public void func_73876_c() {
        final String teamName = this.editCreateTeamName.func_146179_b();
        this.buttonCreateTeamOK.field_146124_l = (teamName.length() > 0 && teamName.length() <= 16);
        this.editCreateTeamName.func_146178_a();
        this.buttonCreateTeamFF.field_146126_j = "Friendly Fire : " + (MCH_GuiScoreboard_CreateTeam.friendlyFire ? "ON" : "OFF");
    }
    
    public void acviveScreen() {
        this.editCreateTeamName.func_146180_a("");
        this.editCreateTeamName.func_146195_b(true);
    }
    
    protected void func_73869_a(final char c, final int code) {
        if (code == 1) {
            this.switchScreen(SCREEN_ID.MAIN);
        }
        else {
            this.editCreateTeamName.func_146201_a(c, code);
        }
    }
    
    protected void func_73864_a(final int p_73864_1_, final int p_73864_2_, final int p_73864_3_) {
        this.editCreateTeamName.func_146192_a(p_73864_1_, p_73864_2_, p_73864_3_);
        super.func_73864_a(p_73864_1_, p_73864_2_, p_73864_3_);
    }
    
    protected void func_146284_a(final GuiButton btn) {
        if (btn != null && btn.field_146124_l) {
            switch (btn.field_146127_k) {
                case 528: {
                    final String teamName = this.editCreateTeamName.func_146179_b();
                    if (teamName.length() > 0 && teamName.length() <= 16) {
                        MCH_PacketIndMultiplayCommand.send(768, "scoreboard teams add " + teamName);
                        MCH_PacketIndMultiplayCommand.send(768, "scoreboard teams option " + teamName + " color " + MCH_GuiScoreboard_CreateTeam.colorNames[this.lastTeamColor]);
                        MCH_PacketIndMultiplayCommand.send(768, "scoreboard teams option " + teamName + " friendlyfire " + MCH_GuiScoreboard_CreateTeam.friendlyFire);
                    }
                    this.switchScreen(SCREEN_ID.MAIN);
                    break;
                }
                case 544: {
                    this.switchScreen(SCREEN_ID.MAIN);
                    break;
                }
                case 560: {
                    MCH_GuiScoreboard_CreateTeam.friendlyFire = !MCH_GuiScoreboard_CreateTeam.friendlyFire;
                    break;
                }
                case 576: {
                    ++this.lastTeamColor;
                    if (this.lastTeamColor >= MCH_GuiScoreboard_CreateTeam.colorNames.length) {
                        this.lastTeamColor = 0;
                        break;
                    }
                    break;
                }
                case 577: {
                    --this.lastTeamColor;
                    if (this.lastTeamColor < 0) {
                        this.lastTeamColor = MCH_GuiScoreboard_CreateTeam.colorNames.length - 1;
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    @Override
    protected void func_146976_a(final float par1, final int par2, final int par3) {
        MCH_GuiScoreboard_Base.drawList(this.field_146297_k, this.field_146289_q, true);
        final ScaledResolution sr = new W_ScaledResolution(this.field_146297_k, this.field_146297_k.field_71443_c, this.field_146297_k.field_71440_d);
        final int factor = (sr.func_78325_e() > 0) ? sr.func_78325_e() : 1;
        W_McClient.MOD_bindTexture("textures/gui/mp_new_team.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        int x = (this.field_146297_k.field_71443_c / factor - 222) / 2;
        int y = (this.field_146297_k.field_71440_d / factor - 200) / 2;
        this.func_73729_b(x, y, 0, 0, 222, 200);
        x = this.field_146297_k.field_71443_c / 2 / factor;
        y = this.field_146297_k.field_71440_d / 2 / factor;
        this.drawCenteredString("Create team", x, y - 85, -1);
        this.drawCenteredString("Team name", x, y - 70, -1);
        final EnumChatFormatting ecf = EnumChatFormatting.func_96300_b(MCH_GuiScoreboard_CreateTeam.colorNames[this.lastTeamColor]);
        this.drawCenteredString(ecf + "Team Color" + ecf, x, y - 13, -1);
        this.editCreateTeamName.func_146194_f();
    }
    
    static {
        MCH_GuiScoreboard_CreateTeam.friendlyFire = true;
        colorNames = new String[] { "RESET", "BLACK", "DARK_BLUE", "DARK_GREEN", "DARK_AQUA", "DARK_RED", "DARK_PURPLE", "GOLD", "GRAY", "DARK_GRAY", "BLUE", "GREEN", "AQUA", "RED", "LIGHT_PURPLE", "YELLOW" };
    }
}
