package mcheli.uav;

import net.minecraft.client.gui.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import mcheli.plane.*;
import mcheli.helicopter.*;
import mcheli.tank.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import mcheli.aircraft.*;
import org.lwjgl.opengl.*;
import mcheli.wrapper.*;

public class MCH_GuiUavStation extends W_GuiContainer
{
    final MCH_EntityUavStation uavStation;
    static final int BX = 20;
    static final int BY = 22;
    private final int BUTTON_ID_CONTINUE = 256;
    private GuiButton buttonContinue;
    
    public MCH_GuiUavStation(final InventoryPlayer inventoryPlayer, final MCH_EntityUavStation uavStation) {
        super(new MCH_ContainerUavStation(inventoryPlayer, uavStation));
        this.uavStation = uavStation;
    }
    
    protected void func_146979_b(final int param1, final int param2) {
        if (this.uavStation == null) {
            return;
        }
        final ItemStack item = this.uavStation.func_70301_a(0);
        MCH_AircraftInfo info = null;
        if (item != null && item.func_77973_b() instanceof MCP_ItemPlane) {
            info = MCP_PlaneInfoManager.getFromItem(item.func_77973_b());
        }
        if (item != null && item.func_77973_b() instanceof MCH_ItemHeli) {
            info = MCH_HeliInfoManager.getFromItem(item.func_77973_b());
        }
        if (item != null && item.func_77973_b() instanceof MCH_ItemTank) {
            info = MCH_TankInfoManager.getFromItem(item.func_77973_b());
        }
        if (item == null || (item != null && info != null && info.isUAV)) {
            if (this.uavStation.getKind() <= 1) {
                this.drawString("UAV Station", 8, 6, 16777215);
            }
            else if (item == null || info.isSmallUAV) {
                this.drawString("UAV Controller", 8, 6, 16777215);
            }
            else {
                this.drawString("Small UAV only", 8, 6, 16711680);
            }
        }
        else if (item != null) {
            this.drawString("Not UAV", 8, 6, 16711680);
        }
        this.drawString(StatCollector.func_74838_a("container.inventory"), 8, this.field_147000_g - 96 + 2, 16777215);
        this.drawString(String.format("X.%+2d", this.uavStation.posUavX), 58, 15, 16777215);
        this.drawString(String.format("Y.%+2d", this.uavStation.posUavY), 58, 37, 16777215);
        this.drawString(String.format("Z.%+2d", this.uavStation.posUavZ), 58, 59, 16777215);
    }
    
    protected void func_146976_a(final float par1, final int par2, final int par3) {
        W_McClient.MOD_bindTexture("textures/gui/uav_station.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final int x = (this.field_146294_l - this.field_146999_f) / 2;
        final int y = (this.field_146295_m - this.field_147000_g) / 2;
        this.func_73729_b(x, y, 0, 0, this.field_146999_f, this.field_147000_g);
    }
    
    protected void func_146284_a(final GuiButton btn) {
        if (btn != null && btn.field_146124_l) {
            if (btn.field_146127_k == 256) {
                if (this.uavStation != null && !this.uavStation.field_70128_L && this.uavStation.getLastControlAircraft() != null && !this.uavStation.getLastControlAircraft().field_70128_L) {
                    final MCH_UavPacketStatus data = new MCH_UavPacketStatus();
                    data.posUavX = (byte)this.uavStation.posUavX;
                    data.posUavY = (byte)this.uavStation.posUavY;
                    data.posUavZ = (byte)this.uavStation.posUavZ;
                    data.continueControl = true;
                    W_Network.sendToServer(data);
                }
                this.buttonContinue.field_146124_l = false;
            }
            else {
                final int[] pos = { this.uavStation.posUavX, this.uavStation.posUavY, this.uavStation.posUavZ };
                final int i = btn.field_146127_k >> 4 & 0xF;
                final int j = (btn.field_146127_k & 0xF) - 1;
                final int[] BTN = { -10, -1, 1, 10 };
                final int[] array = pos;
                final int n = i;
                array[n] += BTN[j];
                if (pos[i] < -50) {
                    pos[i] = -50;
                }
                if (pos[i] > 50) {
                    pos[i] = 50;
                }
                if (this.uavStation.posUavX != pos[0] || this.uavStation.posUavY != pos[1] || this.uavStation.posUavZ != pos[2]) {
                    final MCH_UavPacketStatus data2 = new MCH_UavPacketStatus();
                    data2.posUavX = (byte)pos[0];
                    data2.posUavY = (byte)pos[1];
                    data2.posUavZ = (byte)pos[2];
                    W_Network.sendToServer(data2);
                }
            }
        }
    }
    
    public void func_73866_w_() {
        super.func_73866_w_();
        this.field_146292_n.clear();
        final int x = this.field_146294_l / 2 - 5;
        final int y = this.field_146295_m / 2 - 76;
        final String[] BTN = { "-10", "-1", "+1", "+10" };
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 4; ++col) {
                final int id = row << 4 | col + 1;
                this.field_146292_n.add(new GuiButton(id, x + col * 20, y + row * 22, 20, 20, BTN[col]));
            }
        }
        this.buttonContinue = new GuiButton(256, x - 80 + 3, y + 44, 50, 20, "Continue");
        this.buttonContinue.field_146124_l = false;
        if (this.uavStation != null && !this.uavStation.field_70128_L && this.uavStation.getAndSearchLastControlAircraft() != null) {
            this.buttonContinue.field_146124_l = true;
        }
        this.field_146292_n.add(this.buttonContinue);
    }
}
