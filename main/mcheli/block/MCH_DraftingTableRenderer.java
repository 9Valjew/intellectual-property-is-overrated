package mcheli.block;

import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.tileentity.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import mcheli.wrapper.*;
import mcheli.*;

public class MCH_DraftingTableRenderer extends TileEntitySpecialRenderer
{
    public void func_147500_a(final TileEntity tile, final double posX, final double posY, final double posZ, final float var8) {
        GL11.glPushMatrix();
        GL11.glEnable(2884);
        GL11.glTranslated(posX + 0.5, posY, posZ + 0.5);
        final float yaw = -tile.func_145832_p() * 45 + 180;
        GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        RenderHelper.func_74519_b();
        GL11.glColor4f(0.75f, 0.75f, 0.75f, 1.0f);
        GL11.glEnable(3042);
        final int srcBlend = GL11.glGetInteger(3041);
        final int dstBlend = GL11.glGetInteger(3040);
        GL11.glBlendFunc(770, 771);
        final MCH_Config config = MCH_MOD.config;
        if (MCH_Config.SmoothShading.prmBool) {
            GL11.glShadeModel(7425);
        }
        W_McClient.MOD_bindTexture("textures/blocks/drafting_table.png");
        MCH_ModelManager.render("blocks", "drafting_table");
        GL11.glBlendFunc(srcBlend, dstBlend);
        GL11.glDisable(3042);
        GL11.glShadeModel(7424);
        GL11.glPopMatrix();
    }
}
