package mcheli.particles;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.crash.*;
import java.util.*;
import net.minecraft.util.*;
import mcheli.wrapper.*;
import net.minecraft.block.*;

public abstract class MCH_EntityParticleBase extends W_EntityFX
{
    public boolean isEffectedWind;
    public boolean diffusible;
    public boolean toWhite;
    public float particleMaxScale;
    public float gravity;
    public float moutionYUpAge;
    
    public MCH_EntityParticleBase(final World par1World, final double x, final double y, final double z, final double mx, final double my, final double mz) {
        super(par1World, x, y, z, mx, my, mz);
        this.field_70159_w = mx;
        this.field_70181_x = my;
        this.field_70179_y = mz;
        this.isEffectedWind = false;
        this.particleMaxScale = this.field_70544_f;
    }
    
    public MCH_EntityParticleBase setParticleScale(final float scale) {
        this.field_70544_f = scale;
        return this;
    }
    
    public void setParticleMaxAge(final int age) {
        this.field_70547_e = age;
    }
    
    public void func_70536_a(final int par1) {
        this.field_94054_b = par1 % 8;
        this.field_94055_c = par1 / 8;
    }
    
    public int func_70537_b() {
        return 2;
    }
    
    public void func_70091_d(double par1, double par3, double par5) {
        if (this.field_70145_X) {
            this.field_70121_D.func_72317_d(par1, par3, par5);
            this.field_70165_t = (this.field_70121_D.field_72340_a + this.field_70121_D.field_72336_d) / 2.0;
            this.field_70163_u = this.field_70121_D.field_72338_b + this.field_70129_M - this.field_70139_V;
            this.field_70161_v = (this.field_70121_D.field_72339_c + this.field_70121_D.field_72334_f) / 2.0;
        }
        else {
            this.field_70170_p.field_72984_F.func_76320_a("move");
            this.field_70139_V *= 0.4f;
            final double d3 = this.field_70165_t;
            final double d4 = this.field_70163_u;
            final double d5 = this.field_70161_v;
            final double d6 = par1;
            final double d7 = par3;
            final double d8 = par5;
            final AxisAlignedBB axisalignedbb = this.field_70121_D.func_72329_c();
            final boolean flag = false;
            final List list = this.field_70170_p.func_72945_a((Entity)this, this.field_70121_D.func_72321_a(par1, par3, par5));
            for (int i = 0; i < list.size(); ++i) {
                par3 = list.get(i).func_72323_b(this.field_70121_D, par3);
            }
            this.field_70121_D.func_72317_d(0.0, par3, 0.0);
            if (!this.field_70135_K && d7 != par3) {
                par5 = 0.0;
                par3 = 0.0;
                par1 = 0.0;
            }
            final boolean flag2 = this.field_70122_E || (d7 != par3 && d7 < 0.0);
            for (int j = 0; j < list.size(); ++j) {
                par1 = list.get(j).func_72316_a(this.field_70121_D, par1);
            }
            this.field_70121_D.func_72317_d(par1, 0.0, 0.0);
            if (!this.field_70135_K && d6 != par1) {
                par5 = 0.0;
                par3 = 0.0;
                par1 = 0.0;
            }
            for (int j = 0; j < list.size(); ++j) {
                par5 = list.get(j).func_72322_c(this.field_70121_D, par5);
            }
            this.field_70121_D.func_72317_d(0.0, 0.0, par5);
            if (!this.field_70135_K && d8 != par5) {
                par5 = 0.0;
                par3 = 0.0;
                par1 = 0.0;
            }
            this.field_70170_p.field_72984_F.func_76319_b();
            this.field_70170_p.field_72984_F.func_76320_a("rest");
            this.field_70165_t = (this.field_70121_D.field_72340_a + this.field_70121_D.field_72336_d) / 2.0;
            this.field_70163_u = this.field_70121_D.field_72338_b + this.field_70129_M - this.field_70139_V;
            this.field_70161_v = (this.field_70121_D.field_72339_c + this.field_70121_D.field_72334_f) / 2.0;
            this.field_70123_F = (d6 != par1 || d8 != par5);
            this.field_70124_G = (d7 != par3);
            this.field_70122_E = (d7 != par3 && d7 < 0.0);
            this.field_70132_H = (this.field_70123_F || this.field_70124_G);
            this.func_70064_a(par3, this.field_70122_E);
            if (d6 != par1) {
                this.field_70159_w = 0.0;
            }
            if (d7 != par3) {
                this.field_70181_x = 0.0;
            }
            if (d8 != par5) {
                this.field_70179_y = 0.0;
            }
            final double d9 = this.field_70165_t - d3;
            final double d10 = this.field_70163_u - d4;
            final double d11 = this.field_70161_v - d5;
            try {
                this.doBlockCollisions();
            }
            catch (Throwable throwable) {
                final CrashReport crashreport = CrashReport.func_85055_a(throwable, "Checking entity block collision");
                final CrashReportCategory crashreportcategory = crashreport.func_85058_a("Entity being checked for collision");
                this.func_85029_a(crashreportcategory);
                throw new ReportedException(crashreport);
            }
            this.field_70170_p.field_72984_F.func_76319_b();
        }
    }
    
    public List getCollidingBoundingBoxes(final Entity par1Entity, final AxisAlignedBB par2AxisAlignedBB) {
        final ArrayList collidingBoundingBoxes = new ArrayList();
        final int i = MathHelper.func_76128_c(par2AxisAlignedBB.field_72340_a);
        final int j = MathHelper.func_76128_c(par2AxisAlignedBB.field_72336_d + 1.0);
        final int k = MathHelper.func_76128_c(par2AxisAlignedBB.field_72338_b);
        final int l = MathHelper.func_76128_c(par2AxisAlignedBB.field_72337_e + 1.0);
        final int i2 = MathHelper.func_76128_c(par2AxisAlignedBB.field_72339_c);
        final int j2 = MathHelper.func_76128_c(par2AxisAlignedBB.field_72334_f + 1.0);
        for (int k2 = i; k2 < j; ++k2) {
            for (int l2 = i2; l2 < j2; ++l2) {
                if (this.field_70170_p.func_72899_e(k2, 64, l2)) {
                    for (int i3 = k - 1; i3 < l; ++i3) {
                        Block block;
                        if (k2 >= -30000000 && k2 < 30000000 && l2 >= -30000000 && l2 < 30000000) {
                            block = W_WorldFunc.getBlock(this.field_70170_p, k2, i3, l2);
                        }
                        else {
                            block = W_Blocks.field_150348_b;
                        }
                        block.func_149743_a(this.field_70170_p, k2, i3, l2, par2AxisAlignedBB, (List)collidingBoundingBoxes, par1Entity);
                    }
                }
            }
        }
        return collidingBoundingBoxes;
    }
}
