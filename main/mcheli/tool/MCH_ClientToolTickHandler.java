package mcheli.tool;

import net.minecraft.client.*;
import mcheli.*;
import net.minecraft.entity.player.*;
import mcheli.tool.rangefinder.*;
import mcheli.wrapper.*;
import net.minecraft.item.*;

public class MCH_ClientToolTickHandler extends MCH_ClientTickHandlerBase
{
    public MCH_Key KeyUseItem;
    public MCH_Key KeyZoomIn;
    public MCH_Key KeyZoomOut;
    public MCH_Key KeySwitchMode;
    public MCH_Key[] Keys;
    
    public MCH_ClientToolTickHandler(final Minecraft minecraft, final MCH_Config config) {
        super(minecraft);
        this.updateKeybind(config);
    }
    
    @Override
    public void updateKeybind(final MCH_Config config) {
        this.KeyUseItem = new MCH_Key(MCH_Config.KeyAttack.prmInt);
        this.KeyZoomIn = new MCH_Key(MCH_Config.KeyZoom.prmInt);
        this.KeyZoomOut = new MCH_Key(MCH_Config.KeySwWeaponMode.prmInt);
        this.KeySwitchMode = new MCH_Key(MCH_Config.KeyFlare.prmInt);
        this.Keys = new MCH_Key[] { this.KeyUseItem, this.KeyZoomIn, this.KeyZoomOut, this.KeySwitchMode };
    }
    
    @Override
    protected void onTick(final boolean inGUI) {
        for (final MCH_Key k : this.Keys) {
            k.update();
        }
        this.onTick_ItemWrench(inGUI, (EntityPlayer)this.mc.field_71439_g);
        this.onTick_ItemRangeFinder(inGUI, (EntityPlayer)this.mc.field_71439_g);
    }
    
    private void onTick_ItemRangeFinder(final boolean inGUI, final EntityPlayer player) {
        if (MCH_ItemRangeFinder.rangeFinderUseCooldown > 0) {
            --MCH_ItemRangeFinder.rangeFinderUseCooldown;
        }
        ItemStack itemStack = null;
        if (player != null) {
            itemStack = this.mc.field_71439_g.func_71045_bC();
            if (itemStack != null && itemStack.func_77973_b() instanceof MCH_ItemRangeFinder) {
                final boolean usingItem = player.func_71057_bx() > 8 && MCH_ItemRangeFinder.canUse(player);
                if (!MCH_ItemRangeFinder.continueUsingItem && usingItem) {
                    MCH_ItemRangeFinder.onStartUseItem();
                }
                if (usingItem) {
                    if (this.KeyUseItem.isKeyDown()) {
                        ((MCH_ItemRangeFinder)itemStack.func_77973_b()).spotEntity(player, itemStack);
                    }
                    if (this.KeyZoomIn.isKeyPress() && MCH_ItemRangeFinder.zoom < 10.0f) {
                        MCH_ItemRangeFinder.zoom += MCH_ItemRangeFinder.zoom / 10.0f;
                        if (MCH_ItemRangeFinder.zoom > 10.0f) {
                            MCH_ItemRangeFinder.zoom = 10.0f;
                        }
                        W_McClient.MOD_playSoundFX("zoom", 0.05f, 1.0f);
                        W_Reflection.setCameraZoom(MCH_ItemRangeFinder.zoom);
                    }
                    if (this.KeyZoomOut.isKeyPress() && MCH_ItemRangeFinder.zoom > 1.2f) {
                        MCH_ItemRangeFinder.zoom -= MCH_ItemRangeFinder.zoom / 10.0f;
                        if (MCH_ItemRangeFinder.zoom < 1.2f) {
                            MCH_ItemRangeFinder.zoom = 1.2f;
                        }
                        W_McClient.MOD_playSoundFX("zoom", 0.05f, 0.9f);
                        W_Reflection.setCameraZoom(MCH_ItemRangeFinder.zoom);
                    }
                    if (this.KeySwitchMode.isKeyDown()) {
                        W_McClient.MOD_playSoundFX("lockon", 1.0f, 0.9f);
                        MCH_ItemRangeFinder.mode = (MCH_ItemRangeFinder.mode + 1) % 3;
                        if (this.mc.func_71356_B() && MCH_ItemRangeFinder.mode == 0) {
                            MCH_ItemRangeFinder.mode = 1;
                        }
                    }
                }
            }
        }
        if (MCH_ItemRangeFinder.continueUsingItem && (itemStack == null || !(itemStack.func_77973_b() instanceof MCH_ItemRangeFinder))) {
            MCH_ItemRangeFinder.onStopUseItem();
        }
    }
    
    private void onTick_ItemWrench(final boolean inGUI, final EntityPlayer player) {
        if (player == null) {
            return;
        }
        final ItemStack itemStack = player.func_71045_bC();
        if (itemStack != null && itemStack.func_77973_b() instanceof MCH_ItemWrench) {
            final int maxdm = itemStack.func_77958_k();
            final int dm = itemStack.func_77960_j();
            if (dm <= maxdm) {
                final ItemStack renderItemstack = W_Reflection.getItemRenderer_ItemToRender();
                if (renderItemstack == null || itemStack.func_77973_b() == renderItemstack.func_77973_b()) {
                    W_Reflection.setItemRenderer_ItemToRender(player.field_71071_by.func_70448_g());
                }
            }
        }
    }
}
