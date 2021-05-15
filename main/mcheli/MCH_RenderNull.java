package mcheli;

import mcheli.wrapper.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderNull extends W_Render
{
    public MCH_RenderNull() {
        this.field_76989_e = 0.0f;
    }
    
    public void func_76986_a(final Entity entity, final double posX, final double posY, final double posZ, final float par8, final float tickTime) {
    }
    
    @Override
    protected ResourceLocation func_110775_a(final Entity entity) {
        return MCH_RenderNull.TEX_DEFAULT;
    }
}
