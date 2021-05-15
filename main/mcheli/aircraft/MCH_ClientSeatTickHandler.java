package mcheli.aircraft;

import net.minecraft.client.*;
import mcheli.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import mcheli.wrapper.*;

public class MCH_ClientSeatTickHandler extends MCH_ClientTickHandlerBase
{
    protected boolean isRiding;
    protected boolean isBeforeRiding;
    public MCH_Key KeySwitchNextSeat;
    public MCH_Key KeySwitchPrevSeat;
    public MCH_Key KeyParachuting;
    public MCH_Key KeyFreeLook;
    public MCH_Key KeyUnmountForce;
    public MCH_Key[] Keys;
    
    public MCH_ClientSeatTickHandler(final Minecraft minecraft, final MCH_Config config) {
        super(minecraft);
        this.isRiding = false;
        this.isBeforeRiding = false;
        this.updateKeybind(config);
    }
    
    @Override
    public void updateKeybind(final MCH_Config config) {
        this.KeySwitchNextSeat = new MCH_Key(MCH_Config.KeyExtra.prmInt);
        this.KeySwitchPrevSeat = new MCH_Key(MCH_Config.KeyGUI.prmInt);
        this.KeyParachuting = new MCH_Key(MCH_Config.KeySwitchHovering.prmInt);
        this.KeyUnmountForce = new MCH_Key(42);
        this.KeyFreeLook = new MCH_Key(MCH_Config.KeyFreeLook.prmInt);
        this.Keys = new MCH_Key[] { this.KeySwitchNextSeat, this.KeySwitchPrevSeat, this.KeyParachuting, this.KeyUnmountForce, this.KeyFreeLook };
    }
    
    @Override
    protected void onTick(final boolean inGUI) {
        for (final MCH_Key k : this.Keys) {
            k.update();
        }
        this.isBeforeRiding = this.isRiding;
        final EntityPlayer player = (EntityPlayer)this.mc.field_71439_g;
        if (player != null && player.field_70154_o instanceof MCH_EntitySeat) {
            final MCH_EntitySeat seat = (MCH_EntitySeat)player.field_70154_o;
            if (seat.getParent() == null || seat.getParent().getAcInfo() == null) {
                return;
            }
            final MCH_EntityAircraft ac = seat.getParent();
            if (!inGUI && !ac.isDestroyed()) {
                this.playerControl(player, seat, ac);
            }
            this.isRiding = true;
        }
        else {
            this.isRiding = false;
        }
        if (this.isBeforeRiding != this.isRiding) {
            if (!this.isRiding) {
                MCH_Lib.setRenderViewEntity((EntityLivingBase)player);
            }
        }
    }
    
    private void playerControlInGUI(final EntityPlayer player, final MCH_EntitySeat seat, final MCH_EntityAircraft ac) {
    }
    
    private void playerControl(final EntityPlayer player, final MCH_EntitySeat seat, final MCH_EntityAircraft ac) {
        final MCH_PacketSeatPlayerControl pc = new MCH_PacketSeatPlayerControl();
        boolean send = false;
        if (this.KeyFreeLook.isKeyDown() && ac.canSwitchGunnerFreeLook(player)) {
            ac.switchGunnerFreeLookMode();
        }
        if (this.KeyParachuting.isKeyDown()) {
            if (ac.canParachuting((Entity)player)) {
                pc.parachuting = true;
                send = true;
            }
            else if (ac.canRepelling((Entity)player)) {
                pc.parachuting = true;
                send = true;
            }
            else {
                playSoundNG();
            }
        }
        if (send) {
            W_Network.sendToServer(pc);
        }
    }
}
