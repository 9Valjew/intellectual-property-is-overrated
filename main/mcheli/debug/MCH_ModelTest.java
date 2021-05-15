package mcheli.debug;

import mcheli.wrapper.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.client.model.*;

@SideOnly(Side.CLIENT)
public class MCH_ModelTest extends W_ModelBase
{
    public ModelRenderer test;
    
    public MCH_ModelTest() {
        final int SIZE = 10;
        (this.test = new ModelRenderer((ModelBase)this, 0, 0)).func_78790_a(-5.0f, -5.0f, -5.0f, 10, 10, 10, 0.0f);
    }
    
    public void renderModel(final double yaw, final double pitch, final float par7) {
        this.test.func_78785_a(par7);
    }
}
