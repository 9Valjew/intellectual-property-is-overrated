package mcheli.tool.rangefinder;

import net.minecraft.entity.player.*;
import mcheli.aircraft.*;
import mcheli.multiplay.*;
import net.minecraft.entity.*;
import mcheli.wrapper.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.world.*;
import net.minecraft.item.*;

public class MCH_ItemRangeFinder extends W_Item
{
    public static int rangeFinderUseCooldown;
    public static boolean continueUsingItem;
    public static float zoom;
    public static int mode;
    
    public MCH_ItemRangeFinder(final int itemId) {
        super(itemId);
        this.field_77777_bU = 1;
        this.func_77656_e(10);
    }
    
    public static boolean canUse(final EntityPlayer player) {
        if (player == null) {
            return false;
        }
        if (player.field_70170_p == null) {
            return false;
        }
        if (player.func_71045_bC() == null) {
            return false;
        }
        if (!(player.func_71045_bC().func_77973_b() instanceof MCH_ItemRangeFinder)) {
            return false;
        }
        if (player.field_70154_o instanceof MCH_EntityAircraft) {
            return false;
        }
        if (player.field_70154_o instanceof MCH_EntitySeat) {
            final MCH_EntityAircraft ac = ((MCH_EntitySeat)player.field_70154_o).getParent();
            if (ac != null && (ac.getIsGunnerMode((Entity)player) || ac.getWeaponIDBySeatID(ac.getSeatIdByEntity((Entity)player)) >= 0)) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isUsingScope(final EntityPlayer player) {
        return player.func_71057_bx() > 8 || MCH_ItemRangeFinder.continueUsingItem;
    }
    
    public static void onStartUseItem() {
        W_Reflection.setCameraZoom(MCH_ItemRangeFinder.zoom = 2.0f);
        MCH_ItemRangeFinder.continueUsingItem = true;
    }
    
    public static void onStopUseItem() {
        W_Reflection.restoreCameraZoom();
        MCH_ItemRangeFinder.continueUsingItem = false;
    }
    
    @SideOnly(Side.CLIENT)
    public void spotEntity(final EntityPlayer player, final ItemStack itemStack) {
        if (player != null && player.field_70170_p.field_72995_K && MCH_ItemRangeFinder.rangeFinderUseCooldown == 0 && player.func_71057_bx() > 8) {
            if (MCH_ItemRangeFinder.mode == 2) {
                MCH_ItemRangeFinder.rangeFinderUseCooldown = 60;
                MCH_PacketIndSpotEntity.send((EntityLivingBase)player, 0);
            }
            else if (itemStack.func_77960_j() < itemStack.func_77958_k()) {
                MCH_ItemRangeFinder.rangeFinderUseCooldown = 60;
                MCH_PacketIndSpotEntity.send((EntityLivingBase)player, (MCH_ItemRangeFinder.mode == 0) ? 60 : 3);
            }
            else {
                W_McClient.MOD_playSoundFX("ng", 1.0f, 1.0f);
            }
        }
    }
    
    public void func_77615_a(final ItemStack p_77615_1_, final World p_77615_2_, final EntityPlayer p_77615_3_, final int p_77615_4_) {
        if (p_77615_2_.field_72995_K) {
            onStopUseItem();
        }
    }
    
    public ItemStack func_77654_b(final ItemStack p_77654_1_, final World p_77654_2_, final EntityPlayer p_77654_3_) {
        return p_77654_1_;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean func_77662_d() {
        return true;
    }
    
    public EnumAction func_77661_b(final ItemStack itemStack) {
        return EnumAction.bow;
    }
    
    public int func_77626_a(final ItemStack itemStack) {
        return 72000;
    }
    
    public ItemStack func_77659_a(final ItemStack itemStack, final World world, final EntityPlayer player) {
        if (canUse(player)) {
            player.func_71008_a(itemStack, this.func_77626_a(itemStack));
        }
        return itemStack;
    }
    
    static {
        MCH_ItemRangeFinder.rangeFinderUseCooldown = 0;
        MCH_ItemRangeFinder.continueUsingItem = false;
        MCH_ItemRangeFinder.zoom = 2.0f;
        MCH_ItemRangeFinder.mode = 0;
    }
}
