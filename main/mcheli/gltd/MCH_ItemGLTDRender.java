package mcheli.gltd;

import net.minecraftforge.client.*;
import net.minecraft.item.*;
import org.lwjgl.opengl.*;
import mcheli.wrapper.*;

public class MCH_ItemGLTDRender implements IItemRenderer
{
    public boolean handleRenderType(final ItemStack item, final IItemRenderer.ItemRenderType type) {
        return type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON || type == IItemRenderer.ItemRenderType.ENTITY;
    }
    
    public boolean shouldUseRenderHelper(final IItemRenderer.ItemRenderType type, final ItemStack item, final IItemRenderer.ItemRendererHelper helper) {
        return type == IItemRenderer.ItemRenderType.ENTITY;
    }
    
    public void renderItem(final IItemRenderer.ItemRenderType type, final ItemStack item, final Object... data) {
        GL11.glPushMatrix();
        GL11.glEnable(2884);
        W_McClient.MOD_bindTexture("textures/gltd.png");
        switch (type) {
            case ENTITY: {
                GL11.glEnable(32826);
                GL11.glEnable(2903);
                GL11.glScalef(1.0f, 1.0f, 1.0f);
                MCH_RenderGLTD.model.renderAll();
                GL11.glDisable(32826);
                break;
            }
            case EQUIPPED: {
                GL11.glEnable(32826);
                GL11.glEnable(2903);
                GL11.glTranslatef(0.0f, 0.005f, -0.165f);
                GL11.glRotatef(-10.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(-10.0f, 1.0f, 0.0f, 0.0f);
                MCH_RenderGLTD.model.renderAll();
                GL11.glDisable(32826);
                break;
            }
            case EQUIPPED_FIRST_PERSON: {
                GL11.glEnable(32826);
                GL11.glEnable(2903);
                GL11.glTranslatef(0.3f, 0.5f, -0.5f);
                GL11.glScalef(0.5f, 0.5f, 0.5f);
                GL11.glRotatef(10.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(50.0f, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(-10.0f, 1.0f, 0.0f, 0.0f);
                MCH_RenderGLTD.model.renderAll();
                GL11.glDisable(32826);
                break;
            }
        }
        GL11.glPopMatrix();
    }
}
