package mcheli.multiplay;

import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.client.*;
import mcheli.wrapper.*;
import java.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import net.minecraft.client.network.*;
import net.minecraft.scoreboard.*;
import net.minecraft.client.renderer.*;

public abstract class MCH_GuiScoreboard_Base extends W_GuiContainer
{
    public List<Gui> listGui;
    public static final int BUTTON_ID_SHUFFLE = 256;
    public static final int BUTTON_ID_CREATE_TEAM = 512;
    public static final int BUTTON_ID_CREATE_TEAM_OK = 528;
    public static final int BUTTON_ID_CREATE_TEAM_CANCEL = 544;
    public static final int BUTTON_ID_CREATE_TEAM_FF = 560;
    public static final int BUTTON_ID_CREATE_TEAM_NEXT_C = 576;
    public static final int BUTTON_ID_CREATE_TEAM_PREV_C = 577;
    public static final int BUTTON_ID_JUMP_SPAWN_POINT = 768;
    public static final int BUTTON_ID_SWITCH_PVP = 1024;
    public static final int BUTTON_ID_DESTORY_ALL = 1280;
    private MCH_IGuiScoreboard screen_switcher;
    
    public MCH_GuiScoreboard_Base(final MCH_IGuiScoreboard switcher, final EntityPlayer player) {
        super(new MCH_ContainerScoreboard(player));
        this.screen_switcher = switcher;
        this.field_146297_k = Minecraft.func_71410_x();
    }
    
    public void func_73866_w_() {
    }
    
    public void initGui(final List buttonList, final GuiScreen parents) {
        this.listGui = new ArrayList<Gui>();
        this.field_146297_k = Minecraft.func_71410_x();
        this.field_146289_q = this.field_146297_k.field_71466_p;
        this.field_146294_l = parents.field_146294_l;
        this.field_146295_m = parents.field_146295_m;
        this.func_73866_w_();
        for (final Gui b : this.listGui) {
            if (b instanceof GuiButton) {
                buttonList.add(b);
            }
        }
        this.field_146292_n.clear();
    }
    
    public static void setVisible(final Object g, final boolean v) {
        if (g instanceof GuiButton) {
            ((GuiButton)g).field_146125_m = v;
        }
        if (g instanceof GuiTextField) {
            ((GuiTextField)g).func_146189_e(v);
        }
    }
    
    public void updateScreenButtons(final List list) {
    }
    
    protected void func_146976_a(final float p_146976_1_, final int p_146976_2_, final int p_146976_3_) {
    }
    
    public int getTeamNum() {
        return this.field_146297_k.field_71441_e.func_96441_U().func_96525_g().size();
    }
    
    protected void acviveScreen() {
    }
    
    public void onSwitchScreen() {
        for (final Object b : this.listGui) {
            setVisible(b, true);
        }
        this.acviveScreen();
    }
    
    public void leaveScreen() {
        for (final Object b : this.listGui) {
            setVisible(b, false);
        }
    }
    
    public void keyTypedScreen(final char c, final int code) {
        this.func_73869_a(c, code);
    }
    
    public void mouseClickedScreen(final int p_73864_1_, final int p_73864_2_, final int p_73864_3_) {
        try {
            this.func_73864_a(p_73864_1_, p_73864_2_, p_73864_3_);
        }
        catch (Exception e) {
            if (p_73864_3_ == 0) {
                for (int l = 0; l < this.field_146292_n.size(); ++l) {
                    final GuiButton guibutton = this.field_146292_n.get(l);
                    if (guibutton.func_146116_c(this.field_146297_k, p_73864_1_, p_73864_2_)) {
                        guibutton.func_146113_a(this.field_146297_k.func_147118_V());
                        this.func_146284_a(guibutton);
                    }
                }
            }
        }
    }
    
    public void drawGuiContainerForegroundLayerScreen(final int param1, final int param2) {
        this.func_146979_b(param1, param2);
    }
    
    protected void actionPerformedScreen(final GuiButton btn) {
        this.func_146284_a(btn);
    }
    
    public void switchScreen(final SCREEN_ID id) {
        this.screen_switcher.switchScreen(id);
    }
    
    public static int getScoreboradWidth(final Minecraft mc) {
        final ScaledResolution scaledresolution = new W_ScaledResolution(mc, mc.field_71443_c, mc.field_71440_d);
        final int ScaledWidth = scaledresolution.func_78326_a() - 40;
        int width = ScaledWidth * 3 / 4 / (mc.field_71441_e.func_96441_U().func_96525_g().size() + 1);
        if (width > 150) {
            width = 150;
        }
        return width;
    }
    
    public static int getScoreBoardLeft(final Minecraft mc, final int teamNum, final int teamIndex) {
        final ScaledResolution scaledresolution = new W_ScaledResolution(mc, mc.field_71443_c, mc.field_71440_d);
        final int ScaledWidth = scaledresolution.func_78326_a();
        return (int)(ScaledWidth / 2 + (getScoreboradWidth(mc) + 10) * (-teamNum / 2.0 + teamIndex));
    }
    
    public static void drawList(final Minecraft mc, final FontRenderer fontRendererObj, final boolean mng) {
        final ArrayList<ScorePlayerTeam> teamList = new ArrayList<ScorePlayerTeam>();
        teamList.add(null);
        for (final Object team : mc.field_71441_e.func_96441_U().func_96525_g()) {
            teamList.add((ScorePlayerTeam)team);
        }
        Collections.sort(teamList, new Comparator<ScorePlayerTeam>() {
            @Override
            public int compare(final ScorePlayerTeam o1, final ScorePlayerTeam o2) {
                if (o1 == null && o2 == null) {
                    return 0;
                }
                if (o1 == null) {
                    return -1;
                }
                if (o2 == null) {
                    return 1;
                }
                return o1.func_96661_b().compareTo(o2.func_96661_b());
            }
        });
        for (int i = 0; i < teamList.size(); ++i) {
            if (mng) {
                drawPlayersList(mc, fontRendererObj, teamList.get(i), 1 + i, 1 + teamList.size());
            }
            else {
                drawPlayersList(mc, fontRendererObj, teamList.get(i), i, teamList.size());
            }
        }
    }
    
    public static void drawPlayersList(final Minecraft mc, final FontRenderer fontRendererObj, final ScorePlayerTeam team, final int teamIndex, final int teamNum) {
        final ScaledResolution scaledresolution = new W_ScaledResolution(mc, mc.field_71443_c, mc.field_71440_d);
        final int ScaledWidth = scaledresolution.func_78326_a();
        final int ScaledHeight = scaledresolution.func_78328_b();
        final ScoreObjective scoreobjective = mc.field_71441_e.func_96441_U().func_96539_a(0);
        final NetHandlerPlayClient nethandlerplayclient = mc.field_71439_g.field_71174_a;
        final List list = nethandlerplayclient.field_147303_b;
        int MaxPlayers = (list.size() / 5 + 1) * 5;
        MaxPlayers = ((MaxPlayers < 10) ? 10 : MaxPlayers);
        if (MaxPlayers > nethandlerplayclient.field_147304_c) {
            MaxPlayers = nethandlerplayclient.field_147304_c;
        }
        final int width = getScoreboradWidth(mc);
        final int listLeft = getScoreBoardLeft(mc, teamNum, teamIndex);
        final int listTop = ScaledHeight / 2 - (MaxPlayers * 9 + 10) / 2;
        func_73734_a(listLeft - 1, listTop - 1 - 18, listLeft + width, listTop + 9 * MaxPlayers, Integer.MIN_VALUE);
        final String teamName = ScorePlayerTeam.func_96667_a((Team)team, (team == null) ? "No team" : team.func_96661_b());
        final int teamNameX = listLeft + width / 2 - fontRendererObj.func_78256_a(teamName) / 2;
        fontRendererObj.func_78261_a(teamName, teamNameX, listTop - 18, -1);
        final String ff_onoff = "FriendlyFire : " + ((team == null) ? "ON" : (team.func_96665_g() ? "ON" : "OFF"));
        final int ff_onoffX = listLeft + width / 2 - fontRendererObj.func_78256_a(ff_onoff) / 2;
        fontRendererObj.func_78261_a(ff_onoff, ff_onoffX, listTop - 9, -1);
        int drawY = 0;
        for (int i = 0; i < MaxPlayers; ++i) {
            final int x = listLeft;
            final int y = listTop + drawY * 9;
            final int rectY = listTop + i * 9;
            func_73734_a(x, rectY, x + width - 1, rectY + 8, 553648127);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glEnable(3008);
            if (i < list.size()) {
                final GuiPlayerInfo guiplayerinfo = list.get(i);
                final String playerName = guiplayerinfo.field_78831_a;
                final ScorePlayerTeam steam = mc.field_71441_e.func_96441_U().func_96509_i(playerName);
                if (steam != null || team != null) {
                    if (steam == null || team == null) {
                        continue;
                    }
                    if (!steam.func_142054_a((Team)team)) {
                        continue;
                    }
                }
                ++drawY;
                fontRendererObj.func_78261_a(playerName, x, y, -1);
                if (scoreobjective != null) {
                    final int j4 = x + fontRendererObj.func_78256_a(playerName) + 5;
                    final int k4 = x + width - 12 - 5;
                    if (k4 - j4 > 5) {
                        final Score score = scoreobjective.func_96682_a().func_96529_a(guiplayerinfo.field_78831_a, scoreobjective);
                        final String s1 = EnumChatFormatting.YELLOW + "" + score.func_96652_c();
                        fontRendererObj.func_78261_a(s1, k4 - fontRendererObj.func_78256_a(s1), y, 16777215);
                    }
                }
                drawResponseTime(x + width - 12, y, guiplayerinfo.field_78829_b);
            }
        }
    }
    
    public static void drawResponseTime(final int x, final int y, final int responseTime) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Minecraft.func_71410_x().func_110434_K().func_110577_a(MCH_GuiScoreboard_Base.field_110324_m);
        byte b2;
        if (responseTime < 0) {
            b2 = 5;
        }
        else if (responseTime < 150) {
            b2 = 0;
        }
        else if (responseTime < 300) {
            b2 = 1;
        }
        else if (responseTime < 600) {
            b2 = 2;
        }
        else if (responseTime < 1000) {
            b2 = 3;
        }
        else {
            b2 = 4;
        }
        static_drawTexturedModalRect(x, y, 0, 176 + b2 * 8, 10, 8, 0.0);
    }
    
    public static void static_drawTexturedModalRect(final int x, final int y, final int x2, final int y2, final int x3, final int y3, final double zLevel) {
        final float f = 0.00390625f;
        final Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78382_b();
        tessellator.func_78374_a((double)(x + 0), (double)(y + y3), zLevel, (double)((x2 + 0) * 0.00390625f), (double)((y2 + y3) * 0.00390625f));
        tessellator.func_78374_a((double)(x + x3), (double)(y + y3), zLevel, (double)((x2 + x3) * 0.00390625f), (double)((y2 + y3) * 0.00390625f));
        tessellator.func_78374_a((double)(x + x3), (double)(y + 0), zLevel, (double)((x2 + x3) * 0.00390625f), (double)((y2 + 0) * 0.00390625f));
        tessellator.func_78374_a((double)(x + 0), (double)(y + 0), zLevel, (double)((x2 + 0) * 0.00390625f), (double)((y2 + 0) * 0.00390625f));
        tessellator.func_78381_a();
    }
    
    protected enum SCREEN_ID
    {
        MAIN, 
        CREATE_TEAM;
    }
}
