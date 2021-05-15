package mcheli.aircraft;

import mcheli.wrapper.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.player.*;

public class MCH_EntityHitBox extends W_Entity
{
    public MCH_EntityAircraft parent;
    public int debugId;
    
    public MCH_EntityHitBox(final World world) {
        super(world);
        this.func_70105_a(1.0f, 1.0f);
        this.field_70129_M = 0.0f;
        this.field_70159_w = 0.0;
        this.field_70181_x = 0.0;
        this.field_70179_y = 0.0;
        this.parent = null;
        this.field_70158_ak = true;
        this.field_70178_ae = true;
    }
    
    public MCH_EntityHitBox(final World world, final MCH_EntityAircraft ac, final float w, final float h) {
        this(world);
        this.func_70107_b(ac.field_70165_t, ac.field_70163_u + 1.0, ac.field_70161_v);
        this.field_70169_q = ac.field_70165_t;
        this.field_70167_r = ac.field_70163_u + 1.0;
        this.field_70166_s = ac.field_70161_v;
        this.parent = ac;
        this.func_70105_a(w, h);
    }
    
    protected boolean func_70041_e_() {
        return false;
    }
    
    public AxisAlignedBB func_70114_g(final Entity par1Entity) {
        return par1Entity.field_70121_D;
    }
    
    public AxisAlignedBB func_70046_E() {
        return this.field_70121_D;
    }
    
    public boolean func_70104_M() {
        return false;
    }
    
    public double func_70042_X() {
        return -0.3;
    }
    
    @Override
    public boolean func_70097_a(final DamageSource par1DamageSource, final float par2) {
        return this.parent != null && this.parent.func_70097_a(par1DamageSource, par2);
    }
    
    public boolean func_70067_L() {
        return !this.field_70128_L;
    }
    
    public void func_70106_y() {
        super.func_70106_y();
    }
    
    public void func_70071_h_() {
        super.func_70071_h_();
    }
    
    protected void func_70014_b(final NBTTagCompound par1NBTTagCompound) {
    }
    
    protected void func_70037_a(final NBTTagCompound par1NBTTagCompound) {
    }
    
    @SideOnly(Side.CLIENT)
    public float func_70053_R() {
        return 0.0f;
    }
    
    @Override
    public boolean func_130002_c(final EntityPlayer player) {
        return this.parent != null && this.parent.func_130002_c(player);
    }
}
