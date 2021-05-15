package mcheli.tank;

import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.crash.*;
import java.util.*;
import net.minecraft.util.*;
import mcheli.wrapper.*;
import mcheli.aircraft.*;
import net.minecraft.block.*;

public class MCH_EntityWheel extends W_Entity
{
    private MCH_EntityAircraft parents;
    public Vec3 pos;
    boolean isPlus;
    
    public MCH_EntityWheel(final World w) {
        super(w);
        this.func_70105_a(1.0f, 1.0f);
        this.field_70138_W = 1.5f;
        this.field_70178_ae = true;
        this.isPlus = false;
    }
    
    public void setWheelPos(final Vec3 pos, final Vec3 weightedCenter) {
        this.pos = pos;
        this.isPlus = (pos.field_72449_c >= weightedCenter.field_72449_c);
    }
    
    public void func_71027_c(final int p_71027_1_) {
    }
    
    public MCH_EntityAircraft getParents() {
        return this.parents;
    }
    
    public void setParents(final MCH_EntityAircraft parents) {
        this.parents = parents;
    }
    
    protected void func_70037_a(final NBTTagCompound p_70037_1_) {
        this.func_70106_y();
    }
    
    protected void func_70014_b(final NBTTagCompound p_70014_1_) {
    }
    
    public void func_70091_d(double parX, double parY, double parZ) {
        this.field_70170_p.field_72984_F.func_76320_a("move");
        this.field_70139_V *= 0.4f;
        final double nowPosX = this.field_70165_t;
        final double nowPosY = this.field_70163_u;
        final double nowPosZ = this.field_70161_v;
        final double mx = parX;
        final double my = parY;
        final double mz = parZ;
        final AxisAlignedBB axisalignedbb = this.field_70121_D.func_72329_c();
        List list = this.getCollidingBoundingBoxes(this, this.field_70121_D.func_72321_a(parX, parY, parZ));
        for (int i = 0; i < list.size(); ++i) {
            parY = list.get(i).func_72323_b(this.field_70121_D, parY);
        }
        this.field_70121_D.func_72317_d(0.0, parY, 0.0);
        final boolean flag1 = this.field_70122_E || (my != parY && my < 0.0);
        for (int j = 0; j < list.size(); ++j) {
            parX = list.get(j).func_72316_a(this.field_70121_D, parX);
        }
        this.field_70121_D.func_72317_d(parX, 0.0, 0.0);
        for (int k = 0; k < list.size(); ++k) {
            parZ = list.get(k).func_72322_c(this.field_70121_D, parZ);
        }
        this.field_70121_D.func_72317_d(0.0, 0.0, parZ);
        if (this.field_70138_W > 0.0f && flag1 && this.field_70139_V < 0.05f && (mx != parX || mz != parZ)) {
            final double bkParX = parX;
            final double bkParY = parY;
            final double bkParZ = parZ;
            parX = mx;
            parY = this.field_70138_W;
            parZ = mz;
            final AxisAlignedBB axisalignedbb2 = this.field_70121_D.func_72329_c();
            this.field_70121_D.func_72328_c(axisalignedbb);
            list = this.getCollidingBoundingBoxes(this, this.field_70121_D.func_72321_a(mx, parY, mz));
            for (int l = 0; l < list.size(); ++l) {
                parY = list.get(l).func_72323_b(this.field_70121_D, parY);
            }
            this.field_70121_D.func_72317_d(0.0, parY, 0.0);
            for (int l = 0; l < list.size(); ++l) {
                parX = list.get(l).func_72316_a(this.field_70121_D, parX);
            }
            this.field_70121_D.func_72317_d(parX, 0.0, 0.0);
            for (int l = 0; l < list.size(); ++l) {
                parZ = list.get(l).func_72322_c(this.field_70121_D, parZ);
            }
            this.field_70121_D.func_72317_d(0.0, 0.0, parZ);
            parY = -this.field_70138_W;
            for (int l = 0; l < list.size(); ++l) {
                parY = list.get(l).func_72323_b(this.field_70121_D, parY);
            }
            this.field_70121_D.func_72317_d(0.0, parY, 0.0);
            if (bkParX * bkParX + bkParZ * bkParZ >= parX * parX + parZ * parZ) {
                parX = bkParX;
                parY = bkParY;
                parZ = bkParZ;
                this.field_70121_D.func_72328_c(axisalignedbb2);
            }
        }
        this.field_70170_p.field_72984_F.func_76319_b();
        this.field_70170_p.field_72984_F.func_76320_a("rest");
        this.field_70165_t = (this.field_70121_D.field_72340_a + this.field_70121_D.field_72336_d) / 2.0;
        this.field_70163_u = this.field_70121_D.field_72338_b + this.field_70129_M - this.field_70139_V;
        this.field_70161_v = (this.field_70121_D.field_72339_c + this.field_70121_D.field_72334_f) / 2.0;
        this.field_70123_F = (mx != parX || mz != parZ);
        this.field_70124_G = (my != parY);
        this.field_70122_E = (my != parY && my < 0.0);
        this.field_70132_H = (this.field_70123_F || this.field_70124_G);
        this.func_70064_a(parY, this.field_70122_E);
        if (mx != parX) {
            this.field_70159_w = 0.0;
        }
        if (my != parY) {
            this.field_70181_x = 0.0;
        }
        if (mz != parZ) {
            this.field_70179_y = 0.0;
        }
        try {
            this.doBlockCollisions();
        }
        catch (Throwable throwable) {
            final CrashReport crashreport = CrashReport.func_85055_a(throwable, "Checking entity tile collision");
            final CrashReportCategory crashreportcategory = crashreport.func_85058_a("Entity being checked for collision");
            this.func_85029_a(crashreportcategory);
        }
        this.field_70170_p.field_72984_F.func_76319_b();
    }
    
    public List getCollidingBoundingBoxes(final Entity par1Entity, final AxisAlignedBB par2AxisAlignedBB) {
        final ArrayList collidingBoundingBoxes = new ArrayList();
        collidingBoundingBoxes.clear();
        final int i = MathHelper.func_76128_c(par2AxisAlignedBB.field_72340_a);
        final int j = MathHelper.func_76128_c(par2AxisAlignedBB.field_72336_d + 1.0);
        final int k = MathHelper.func_76128_c(par2AxisAlignedBB.field_72338_b);
        final int l = MathHelper.func_76128_c(par2AxisAlignedBB.field_72337_e + 1.0);
        final int i2 = MathHelper.func_76128_c(par2AxisAlignedBB.field_72339_c);
        final int j2 = MathHelper.func_76128_c(par2AxisAlignedBB.field_72334_f + 1.0);
        for (int k2 = i; k2 < j; ++k2) {
            for (int l2 = i2; l2 < j2; ++l2) {
                if (par1Entity.field_70170_p.func_72899_e(k2, 64, l2)) {
                    for (int i3 = k - 1; i3 < l; ++i3) {
                        final Block block = W_WorldFunc.getBlock(par1Entity.field_70170_p, k2, i3, l2);
                        if (block != null) {
                            block.func_149743_a(par1Entity.field_70170_p, k2, i3, l2, par2AxisAlignedBB, (List)collidingBoundingBoxes, par1Entity);
                        }
                    }
                }
            }
        }
        final double d0 = 0.25;
        final List list = par1Entity.field_70170_p.func_72839_b(par1Entity, par2AxisAlignedBB.func_72314_b(d0, d0, d0));
        for (int j3 = 0; j3 < list.size(); ++j3) {
            final Entity entity = list.get(j3);
            if (!W_Lib.isEntityLivingBase(entity)) {
                if (!(entity instanceof MCH_EntitySeat)) {
                    if (!(entity instanceof MCH_EntityHitBox)) {
                        if (entity != this.parents) {
                            AxisAlignedBB axisalignedbb1 = entity.func_70046_E();
                            if (axisalignedbb1 != null && axisalignedbb1.func_72326_a(par2AxisAlignedBB)) {
                                collidingBoundingBoxes.add(axisalignedbb1);
                            }
                            axisalignedbb1 = par1Entity.func_70114_g(entity);
                            if (axisalignedbb1 != null && axisalignedbb1.func_72326_a(par2AxisAlignedBB)) {
                                collidingBoundingBoxes.add(axisalignedbb1);
                            }
                        }
                    }
                }
            }
        }
        return collidingBoundingBoxes;
    }
}
