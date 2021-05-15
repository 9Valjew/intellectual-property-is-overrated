package mcheli.aircraft;

import net.minecraftforge.client.*;
import net.minecraft.item.*;
import org.lwjgl.opengl.*;
import mcheli.wrapper.*;
import mcheli.*;

public class MCH_ItemAircraftRender implements IItemRenderer
{
    float size;
    float x;
    float y;
    float z;
    
    public MCH_ItemAircraftRender() {
        this.size = 0.1f;
        this.x = 0.1f;
        this.y = 0.1f;
        this.z = 0.1f;
    }
    
    public boolean handleRenderType(final ItemStack item, final IItemRenderer.ItemRenderType type) {
        if (item != null && item.func_77973_b() instanceof MCH_ItemAircraft) {
            final MCH_AircraftInfo info = ((MCH_ItemAircraft)item.func_77973_b()).getAircraftInfo();
            if (info == null) {
                return false;
            }
            if (info != null && info.name.equalsIgnoreCase("mh-60l_dap")) {
                return type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON || type == IItemRenderer.ItemRenderType.ENTITY || type == IItemRenderer.ItemRenderType.INVENTORY;
            }
        }
        return false;
    }
    
    public boolean shouldUseRenderHelper(final IItemRenderer.ItemRenderType type, final ItemStack item, final IItemRenderer.ItemRendererHelper helper) {
        return type == IItemRenderer.ItemRenderType.ENTITY || type == IItemRenderer.ItemRenderType.INVENTORY;
    }
    
    public void renderItem(final IItemRenderer.ItemRenderType type, final ItemStack item, final Object... data) {
        boolean isRender = true;
        GL11.glPushMatrix();
        GL11.glEnable(2884);
        W_McClient.MOD_bindTexture("textures/helicopters/mh-60l_dap.png");
        switch (type) {
            case ENTITY: {
                GL11.glEnable(32826);
                GL11.glEnable(2903);
                GL11.glScalef(0.1f, 0.1f, 0.1f);
                MCH_ModelManager.render("helicopters", "mh-60l_dap");
                GL11.glDisable(32826);
                break;
            }
            case EQUIPPED: {
                GL11.glEnable(32826);
                GL11.glEnable(2903);
                GL11.glTranslatef(0.0f, 0.005f, -0.165f);
                GL11.glScalef(0.1f, 0.1f, 0.1f);
                GL11.glRotatef(-10.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(90.0f, 0.0f, -1.0f, 0.0f);
                GL11.glRotatef(-50.0f, 1.0f, 0.0f, 0.0f);
                MCH_ModelManager.render("helicopters", "mh-60l_dap");
                GL11.glDisable(32826);
                break;
            }
            case EQUIPPED_FIRST_PERSON: {
                GL11.glEnable(32826);
                GL11.glEnable(2903);
                GL11.glTranslatef(0.3f, 0.5f, -0.5f);
                GL11.glScalef(0.1f, 0.1f, 0.1f);
                GL11.glRotatef(10.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(140.0f, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(-10.0f, 1.0f, 0.0f, 0.0f);
                MCH_ModelManager.render("helicopters", "mh-60l_dap");
                GL11.glDisable(32826);
                break;
            }
            case INVENTORY: {
                GL11.glTranslatef(this.x, this.y, this.z);
                GL11.glScalef(this.size, this.size, this.size);
                MCH_ModelManager.render("helicopters", "mh-60l_dap");
                break;
            }
            default: {
                isRender = false;
                break;
            }
        }
        GL11.glPopMatrix();
    }
}
