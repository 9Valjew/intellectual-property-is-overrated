package mcheli.flare;

import mcheli.wrapper.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.client.model.*;

@SideOnly(Side.CLIENT)
public class MCH_ModelFlare extends W_ModelBase
{
    public ModelRenderer model;
    
    public MCH_ModelFlare() {
        final int SIZE = 4;
        (this.model = new ModelRenderer((ModelBase)this, 0, 0).func_78787_b(4, 4)).func_78790_a(-2.0f, -2.0f, -2.0f, 4, 4, 4, 0.0f);
    }
    
    public void renderModel(final double yaw, final double pitch, final float par7) {
        this.model.func_78785_a(par7);
    }
}
