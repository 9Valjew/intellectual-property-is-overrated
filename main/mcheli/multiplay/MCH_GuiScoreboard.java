package mcheli.multiplay;

import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;
import mcheli.wrapper.*;
import java.util.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;

public class MCH_GuiScoreboard extends W_GuiContainer implements MCH_IGuiScoreboard
{
    public final EntityPlayer thePlayer;
    private MCH_GuiScoreboard_Base.SCREEN_ID screenID;
    private Map<MCH_GuiScoreboard_Base.SCREEN_ID, MCH_GuiScoreboard_Base> listScreen;
    private int lastTeamNum;
    
    public MCH_GuiScoreboard(final EntityPlayer player) {
        super(new MCH_ContainerScoreboard(player));
        this.lastTeamNum = 0;
        this.thePlayer = player;
    }
    
    public void func_73866_w_() {
        Keyboard.enableRepeatEvents(true);
        super.func_73866_w_();
        this.field_146292_n.clear();
        this.field_146293_o.clear();
        this.field_147003_i = 0;
        this.field_147009_r = 0;
        (this.listScreen = new HashMap<MCH_GuiScoreboard_Base.SCREEN_ID, MCH_GuiScoreboard_Base>()).put(MCH_GuiScoreboard_Base.SCREEN_ID.MAIN, new MCH_GuiScoreboard_Main(this, this.thePlayer));
        this.listScreen.put(MCH_GuiScoreboard_Base.SCREEN_ID.CREATE_TEAM, new MCH_GuiScoreboard_CreateTeam(this, this.thePlayer));
        for (final MCH_GuiScoreboard_Base s : this.listScreen.values()) {
            s.initGui(this.field_146292_n, (GuiScreen)this);
        }
        this.lastTeamNum = this.field_146297_k.field_71441_e.func_96441_U().func_96525_g().size();
        this.switchScreen(MCH_GuiScoreboard_Base.SCREEN_ID.MAIN);
    }
    
    public void func_73876_c() {
        super.func_73876_c();
        final int nowTeamNum = this.field_146297_k.field_71441_e.func_96441_U().func_96525_g().size();
        if (this.lastTeamNum != nowTeamNum) {
            this.lastTeamNum = nowTeamNum;
            this.func_73866_w_();
        }
        for (final MCH_GuiScoreboard_Base s : this.listScreen.values()) {
            try {
                s.updateScreenButtons(this.field_146292_n);
                s.func_73876_c();
            }
            catch (Exception ex) {}
        }
    }
    
    @Override
    public void switchScreen(final MCH_GuiScoreboard_Base.SCREEN_ID id) {
        for (final MCH_GuiScoreboard_Base b : this.listScreen.values()) {
            b.leaveScreen();
        }
        this.screenID = id;
        this.getCurrentScreen().onSwitchScreen();
    }
    
    private MCH_GuiScoreboard_Base getCurrentScreen() {
        return this.listScreen.get(this.screenID);
    }
    
    public static void setVisible(final Object g, final boolean v) {
        if (g instanceof GuiButton) {
            ((GuiButton)g).field_146125_m = v;
        }
        if (g instanceof GuiTextField) {
            ((GuiTextField)g).func_146189_e(v);
        }
    }
    
    protected void func_73869_a(final char c, final int code) {
        this.getCurrentScreen().keyTypedScreen(c, code);
    }
    
    protected void func_73864_a(final int p_73864_1_, final int p_73864_2_, final int p_73864_3_) {
        try {
            for (final MCH_GuiScoreboard_Base s : this.listScreen.values()) {
                s.mouseClickedScreen(p_73864_1_, p_73864_2_, p_73864_3_);
            }
            super.func_73864_a(p_73864_1_, p_73864_2_, p_73864_3_);
        }
        catch (Exception ex) {}
    }
    
    protected void func_146284_a(final GuiButton btn) {
        if (btn != null && btn.field_146124_l) {
            this.getCurrentScreen().actionPerformedScreen(btn);
        }
    }
    
    public void func_146276_q_() {
    }
    
    public void func_146278_c(final int p_146278_1_) {
        GL11.glDisable(2896);
        GL11.glDisable(2912);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    protected void func_146979_b(final int x, final int y) {
        this.getCurrentScreen().drawGuiContainerForegroundLayerScreen(x, y);
        for (final Object o : this.field_146292_n) {
            if (o instanceof W_GuiButton) {
                final W_GuiButton btn = (W_GuiButton)o;
                if (btn.isOnMouseOver() && btn.hoverStringList != null) {
                    this.drawHoveringText((List)btn.hoverStringList, x, y, this.field_146289_q);
                    break;
                }
                continue;
            }
        }
    }
    
    public static void drawList(final Minecraft mc, final FontRenderer fontRendererObj, final boolean mng) {
        MCH_GuiScoreboard_Base.drawList(mc, fontRendererObj, mng);
    }
    
    protected void func_146976_a(final float par1, final int par2, final int par3) {
        this.getCurrentScreen().func_146976_a(par1, par2, par3);
    }
    
    public void func_146280_a(final Minecraft p_146280_1_, final int p_146280_2_, final int p_146280_3_) {
        super.func_146280_a(p_146280_1_, p_146280_2_, p_146280_3_);
        for (final MCH_GuiScoreboard_Base s : this.listScreen.values()) {
            s.func_146280_a(p_146280_1_, p_146280_2_, p_146280_3_);
        }
    }
}
