package mcheli;

import mcheli.wrapper.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;
import cpw.mods.fml.relauncher.*;

public class MCH_ItemArmor extends W_ItemArmor
{
    public static final String HELMET_TEXTURE = "mcheli:textures/helicopters/ah-64.png";
    public static final String CHESTPLATE_TEXTURE = "mcheli:textures/armor/plate.png";
    public static final String LEGGINGS_TEXTURE = "mcheli:textures/armor/leg.png";
    public static final String BOOTS_TEXTURE = "mcheli:textures/armor/boots.png";
    public static MCH_TEST_ModelBiped model;
    
    public MCH_ItemArmor(final int par1, final int par3, final int par4) {
        super(par1, par3, par4);
    }
    
    public String getArmorTexture(final ItemStack stack, final Entity entity, final int slot, final String layer) {
        if (slot == 0) {
            return "mcheli:textures/helicopters/ah-64.png";
        }
        if (slot == 1) {
            return "mcheli:textures/armor/plate.png";
        }
        if (slot == 2) {
            return "mcheli:textures/armor/leg.png";
        }
        if (slot == 3) {
            return "mcheli:textures/armor/boots.png";
        }
        return "none";
    }
    
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(final EntityLivingBase entityLiving, final ItemStack itemStack, final int armorSlot) {
        if (MCH_ItemArmor.model == null) {
            MCH_ItemArmor.model = new MCH_TEST_ModelBiped();
        }
        if (armorSlot == 0) {
            return MCH_ItemArmor.model;
        }
        return null;
    }
    
    static {
        MCH_ItemArmor.model = null;
    }
}
