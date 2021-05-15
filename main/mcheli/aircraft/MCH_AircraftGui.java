package mcheli.aircraft;

import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import mcheli.command.*;
import mcheli.*;
import mcheli.weapon.*;
import java.util.*;
import mcheli.multiplay.*;
import mcheli.wrapper.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;

public class MCH_AircraftGui extends W_GuiContainer
{
    private final EntityPlayer thePlayer;
    private final MCH_EntityAircraft aircraft;
    private int scaleFactor;
    private GuiButton buttonReload;
    private GuiButton buttonNext;
    private GuiButton buttonPrev;
    private GuiButton buttonInventory;
    private int currentWeaponId;
    private int reloadWait;
    private GuiTextField editCommand;
    public static final int BUTTON_RELOAD = 1;
    public static final int BUTTON_NEXT = 2;
    public static final int BUTTON_PREV = 3;
    public static final int BUTTON_CLOSE = 4;
    public static final int BUTTON_CONFIG = 5;
    public static final int BUTTON_INVENTORY = 6;
    
    public MCH_AircraftGui(final EntityPlayer player, final MCH_EntityAircraft ac) {
        super(new MCH_AircraftGuiContainer(player, ac));
        this.aircraft = ac;
        this.thePlayer = player;
        this.field_146999_f = 210;
        this.field_147000_g = 236;
        this.buttonReload = null;
        this.currentWeaponId = 0;
    }
    
    public void func_73866_w_() {
        super.func_73866_w_();
        this.field_146292_n.clear();
        this.buttonReload = new GuiButton(1, this.field_147003_i + 85, this.field_147009_r + 40, 50, 20, "Reload");
        this.buttonNext = new GuiButton(3, this.field_147003_i + 140, this.field_147009_r + 40, 20, 20, "<<");
        this.buttonPrev = new GuiButton(2, this.field_147003_i + 160, this.field_147009_r + 40, 20, 20, ">>");
        this.buttonReload.field_146124_l = this.canReload(this.thePlayer);
        this.buttonNext.field_146124_l = (this.aircraft.getWeaponNum() >= 2);
        this.buttonPrev.field_146124_l = (this.aircraft.getWeaponNum() >= 2);
        this.buttonInventory = new GuiButton(6, this.field_147003_i + 210 - 30 - 60, this.field_147009_r + 90, 80, 20, "Inventory");
        this.field_146292_n.add(new GuiButton(5, this.field_147003_i + 210 - 30 - 60, this.field_147009_r + 110, 80, 20, "MOD Options"));
        this.field_146292_n.add(new GuiButton(4, this.field_147003_i + 210 - 30 - 20, this.field_147009_r + 10, 40, 20, "Close"));
        this.field_146292_n.add(this.buttonReload);
        this.field_146292_n.add(this.buttonNext);
        this.field_146292_n.add(this.buttonPrev);
        if (this.aircraft != null && this.aircraft.func_70302_i_() > 0) {
            this.field_146292_n.add(this.buttonInventory);
        }
        (this.editCommand = new GuiTextField(this.field_146289_q, this.field_147003_i + 25, this.field_147009_r + 215, 160, 15)).func_146180_a(this.aircraft.getCommand());
        this.editCommand.func_146203_f(512);
        this.currentWeaponId = 0;
        this.reloadWait = 10;
    }
    
    public void closeScreen() {
        MCH_PacketCommandSave.send(this.editCommand.func_146179_b());
        this.field_146297_k.field_71439_g.func_71053_j();
    }
    
    public boolean canReload(final EntityPlayer player) {
        return this.aircraft.canPlayerSupplyAmmo(player, this.currentWeaponId);
    }
    
    public void func_73876_c() {
        super.func_73876_c();
        if (this.reloadWait > 0) {
            --this.reloadWait;
            if (this.reloadWait == 0) {
                this.buttonReload.field_146124_l = this.canReload(this.thePlayer);
                this.reloadWait = 20;
            }
        }
        this.editCommand.func_146178_a();
    }
    
    protected void func_73864_a(final int p_73864_1_, final int p_73864_2_, final int p_73864_3_) {
        this.editCommand.func_146192_a(p_73864_1_, p_73864_2_, p_73864_3_);
        super.func_73864_a(p_73864_1_, p_73864_2_, p_73864_3_);
    }
    
    public void func_146281_b() {
        super.func_146281_b();
    }
    
    protected void func_146284_a(final GuiButton button) {
        super.func_146284_a(button);
        if (!button.field_146124_l) {
            return;
        }
        switch (button.field_146127_k) {
            case 4: {
                this.closeScreen();
                break;
            }
            case 1: {
                this.buttonReload.field_146124_l = this.canReload(this.thePlayer);
                if (this.buttonReload.field_146124_l) {
                    MCH_PacketIndReload.send(this.aircraft, this.currentWeaponId);
                    this.aircraft.supplyAmmo(this.currentWeaponId);
                    this.reloadWait = 3;
                    this.buttonReload.field_146124_l = false;
                    break;
                }
                break;
            }
            case 2: {
                ++this.currentWeaponId;
                if (this.currentWeaponId >= this.aircraft.getWeaponNum()) {
                    this.currentWeaponId = 0;
                }
                this.buttonReload.field_146124_l = this.canReload(this.thePlayer);
                break;
            }
            case 3: {
                --this.currentWeaponId;
                if (this.currentWeaponId < 0) {
                    this.currentWeaponId = this.aircraft.getWeaponNum() - 1;
                }
                this.buttonReload.field_146124_l = this.canReload(this.thePlayer);
                break;
            }
            case 5: {
                MCH_PacketIndOpenScreen.send(2);
                break;
            }
            case 6: {
                MCH_PacketIndOpenScreen.send(3);
                break;
            }
        }
    }
    
    protected void func_146979_b(final int par1, final int par2) {
        super.func_146979_b(par1, par2);
        final MCH_EntityAircraft ac = this.aircraft;
        this.drawString(ac.getGuiInventory().func_145825_b(), 10, 10, 16777215);
        if (this.aircraft.getNumEjectionSeat() > 0) {
            this.drawString("Parachute", 9, 95, 16777215);
        }
        if (this.aircraft.getWeaponNum() > 0) {
            final MCH_WeaponSet ws = this.aircraft.getWeapon(this.currentWeaponId);
            if (ws != null && !(ws.getFirstWeapon() instanceof MCH_WeaponDummy)) {
                this.drawString(ws.getName(), 79, 30, 16777215);
                final int rest = ws.getRestAllAmmoNum() + ws.getAmmoNum();
                final int color = (rest == 0) ? 16711680 : ((rest == ws.getAllAmmoNum()) ? 2675784 : 16777215);
                final String s = String.format("%4d/%4d", rest, ws.getAllAmmoNum());
                this.drawString(s, 145, 70, color);
                int itemPosX = 90;
                for (final MCH_WeaponInfo.RoundItem r : ws.getInfo().roundItems) {
                    this.drawString("" + r.num, itemPosX, 80, 16777215);
                    itemPosX += 20;
                }
                itemPosX = 85;
                for (final MCH_WeaponInfo.RoundItem r : ws.getInfo().roundItems) {
                    this.drawItemStack(r.itemStack, itemPosX, 62);
                    itemPosX += 20;
                }
            }
        }
        else {
            this.drawString("None", 79, 45, 16777215);
        }
    }
    
    protected void func_73869_a(final char c, final int code) {
        if (code == 1) {
            this.closeScreen();
        }
        else if (code == 28) {
            String s = this.editCommand.func_146179_b().trim();
            if (s.startsWith("/")) {
                s = s.substring(1);
            }
            if (!s.isEmpty()) {
                MCH_PacketIndMultiplayCommand.send(768, s);
            }
        }
        else {
            this.editCommand.func_146201_a(c, code);
        }
    }
    
    protected void func_146976_a(final float var1, final int var2, final int var3) {
        final ScaledResolution scaledresolution = new W_ScaledResolution(this.field_146297_k, this.field_146297_k.field_71443_c, this.field_146297_k.field_71440_d);
        this.scaleFactor = scaledresolution.func_78325_e();
        W_McClient.MOD_bindTexture("textures/gui/gui.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final int x = (this.field_146294_l - this.field_146999_f) / 2;
        final int y = (this.field_146295_m - this.field_147000_g) / 2;
        this.func_73729_b(x, y, 0, 0, this.field_146999_f, this.field_147000_g);
        for (int i = 0; i < this.aircraft.getNumEjectionSeat(); ++i) {
            this.func_73729_b(x + 10 + 18 * i - 1, y + 105 - 1, 215, 55, 18, 18);
        }
        int ff = (int)(this.aircraft.getFuelP() * 50.0f);
        if (ff >= 99) {
            ff = 100;
        }
        this.func_73729_b(x + 57, y + 30 + 50 - ff, 215, 0, 12, ff);
        ff = (int)(this.aircraft.getFuelP() * 100.0f + 0.5);
        final int color = (ff > 20) ? -14101432 : 16711680;
        this.drawString(String.format("%3d", ff) + "%", x + 30, y + 65, color);
        this.editCommand.func_146194_f();
    }
}
