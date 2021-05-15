package mcheli.lweapon;

import net.minecraftforge.client.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import mcheli.wrapper.*;
import mcheli.*;
import cpw.mods.fml.relauncher.*;

public class MCH_ItemLightWeaponRender implements IItemRenderer
{
    public boolean handleRenderType(final ItemStack item, final IItemRenderer.ItemRenderType type) {
        return type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON;
    }
    
    public boolean shouldUseRenderHelper(final IItemRenderer.ItemRenderType type, final ItemStack item, final IItemRenderer.ItemRendererHelper helper) {
        return false;
    }
    
    public boolean useCurrentWeapon() {
        return false;
    }
    
    public void renderItem(final IItemRenderer.ItemRenderType type, final ItemStack item, final Object... data) {
        boolean isRender = false;
        if (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON || type == IItemRenderer.ItemRenderType.EQUIPPED) {
            isRender = true;
            if (data[1] instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)data[1];
                if (MCH_ItemLightWeaponBase.isHeld(player) && W_Lib.isFirstPerson() && W_Lib.isClientPlayer((Entity)player)) {
                    isRender = false;
                }
            }
        }
        if (isRender) {
            renderItem(item, (Entity)W_Lib.castEntityLivingBase(data[1]), type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public static void renderItem(final ItemStack pitem, final Entity entity, final boolean isFirstPerson) {
        if (pitem == null || pitem.func_77973_b() == null) {
            return;
        }
        final String name = MCH_ItemLightWeaponBase.getName(pitem);
        GL11.glEnable(32826);
        GL11.glEnable(2903);
        GL11.glPushMatrix();
        final MCH_Config config = MCH_MOD.config;
        if (MCH_Config.SmoothShading.prmBool) {
            GL11.glShadeModel(7425);
        }
        GL11.glEnable(2884);
        W_McClient.MOD_bindTexture("textures/lweapon/" + name + ".png");
        if (isFirstPerson) {
            GL11.glTranslatef(0.0f, 0.005f, -0.165f);
            GL11.glScalef(2.0f, 2.0f, 2.0f);
            GL11.glRotatef(-10.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-50.0f, 1.0f, 0.0f, 0.0f);
        }
        else {
            GL11.glTranslatef(0.3f, 0.3f, 0.0f);
            GL11.glScalef(2.0f, 2.0f, 2.0f);
            GL11.glRotatef(20.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(10.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(15.0f, 1.0f, 0.0f, 0.0f);
        }
        MCH_ModelManager.render("lweapons", name);
        GL11.glShadeModel(7424);
        GL11.glPopMatrix();
        GL11.glDisable(32826);
    }
}
