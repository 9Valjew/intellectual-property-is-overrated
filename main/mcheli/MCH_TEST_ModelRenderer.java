package mcheli;

import net.minecraft.client.model.*;
import org.lwjgl.opengl.*;

public class MCH_TEST_ModelRenderer extends ModelRenderer
{
    public MCH_TEST_ModelRenderer(final ModelBase par1ModelBase) {
        super(par1ModelBase);
    }
    
    public void func_78785_a(final float par1) {
        GL11.glPushMatrix();
        GL11.glScaled(0.2, -0.2, 0.2);
        MCH_ModelManager.render("helicopters", "ah-64");
        GL11.glPopMatrix();
    }
}
