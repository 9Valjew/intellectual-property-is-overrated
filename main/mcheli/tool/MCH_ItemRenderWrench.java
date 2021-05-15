package mcheli.tool;

import net.minecraftforge.client.*;
import net.minecraft.item.*;
import org.lwjgl.opengl.*;
import mcheli.wrapper.*;
import net.minecraft.entity.player.*;
import mcheli.*;

public class MCH_ItemRenderWrench implements IItemRenderer
{
    public boolean handleRenderType(final ItemStack item, final IItemRenderer.ItemRenderType type) {
        return type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON;
    }
    
    public boolean shouldUseRenderHelper(final IItemRenderer.ItemRenderType type, final ItemStack item, final IItemRenderer.ItemRendererHelper helper) {
        return type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON;
    }
    
    public void renderItem(final IItemRenderer.ItemRenderType type, final ItemStack item, final Object... data) {
        GL11.glPushMatrix();
        W_McClient.MOD_bindTexture("textures/wrench.png");
        float size = 1.0f;
        switch (type) {
            case EQUIPPED: {
                size = 2.2f;
                GL11.glScalef(size, size, size);
                GL11.glRotatef(-130.0f, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(-40.0f, 1.0f, 0.0f, 0.0f);
                GL11.glTranslatef(0.1f, 0.5f, -0.1f);
                break;
            }
            case EQUIPPED_FIRST_PERSON: {
                int useFrame = MCH_ItemWrench.getUseAnimCount(item) - 8;
                if (useFrame < 0) {
                    useFrame = -useFrame;
                }
                size = 2.2f;
                if (data.length >= 2 && data[1] instanceof EntityPlayer) {
                    final EntityPlayer player = (EntityPlayer)data[1];
                    if (player.func_71052_bv() > 0) {
                        final float x = 0.8567f;
                        final float y = -0.0298f;
                        final float z = 0.0f;
                        GL11.glTranslatef(-x, -y, -z);
                        GL11.glRotatef((float)(useFrame + 20), 1.0f, 0.0f, 0.0f);
                        GL11.glTranslatef(x, y, z);
                    }
                }
                GL11.glScalef(size, size, size);
                GL11.glRotatef(-200.0f, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(-60.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(0.0f, 0.0f, 0.0f, 1.0f);
                GL11.glTranslatef(-0.2f, 0.5f, -0.1f);
                break;
            }
        }
        MCH_ModelManager.render("wrench");
        GL11.glPopMatrix();
    }
}
