package mcheli.block;

import net.minecraftforge.client.*;
import net.minecraft.item.*;
import org.lwjgl.opengl.*;
import mcheli.wrapper.*;
import mcheli.*;

public class MCH_DraftingTableItemRender implements IItemRenderer
{
    public boolean handleRenderType(final ItemStack item, final IItemRenderer.ItemRenderType type) {
        switch (type) {
            case ENTITY:
            case EQUIPPED:
            case EQUIPPED_FIRST_PERSON:
            case INVENTORY: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public boolean shouldUseRenderHelper(final IItemRenderer.ItemRenderType type, final ItemStack item, final IItemRenderer.ItemRendererHelper helper) {
        return true;
    }
    
    public void renderItem(final IItemRenderer.ItemRenderType type, final ItemStack item, final Object... data) {
        GL11.glPushMatrix();
        W_McClient.MOD_bindTexture("textures/blocks/drafting_table.png");
        GL11.glEnable(32826);
        switch (type) {
            case ENTITY: {
                GL11.glTranslatef(0.0f, 0.5f, 0.0f);
                GL11.glScalef(1.5f, 1.5f, 1.5f);
                break;
            }
            case INVENTORY: {
                final float INV_SIZE = 0.75f;
                GL11.glTranslatef(0.0f, -0.5f, 0.0f);
                GL11.glScalef(0.75f, 0.75f, 0.75f);
                break;
            }
            case EQUIPPED: {
                GL11.glTranslatef(0.0f, 0.0f, 0.5f);
                GL11.glScalef(1.0f, 1.0f, 1.0f);
                break;
            }
            case EQUIPPED_FIRST_PERSON: {
                GL11.glTranslatef(0.75f, 0.0f, 0.0f);
                GL11.glScalef(1.0f, 1.0f, 1.0f);
                GL11.glRotatef(90.0f, 0.0f, -1.0f, 0.0f);
                break;
            }
        }
        MCH_ModelManager.render("blocks", "drafting_table");
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(3042);
    }
}
