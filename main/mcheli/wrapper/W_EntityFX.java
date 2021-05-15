package mcheli.wrapper;

import net.minecraft.client.particle.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public abstract class W_EntityFX extends EntityFX
{
    public W_EntityFX(final World par1World, final double par2, final double par4, final double par6) {
        super(par1World, par2, par4, par6);
    }
    
    public W_EntityFX(final World par1World, final double par2, final double par4, final double par6, final double par8, final double par10, final double par12) {
        super(par1World, par2, par4, par6, par8, par10, par12);
    }
    
    public AxisAlignedBB func_70046_E() {
        return this.field_70121_D;
    }
    
    public void setIcon(final IIcon icon) {
        this.func_110125_a(icon);
    }
    
    protected void doBlockCollisions() {
        super.func_145775_I();
    }
}
