package mcheli.aircraft;

import net.minecraft.entity.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import java.util.*;
import mcheli.wrapper.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.crash.*;

public class MCH_EntityHide extends W_Entity
{
    private MCH_EntityAircraft ac;
    private Entity user;
    private int paraPosRotInc;
    private double paraX;
    private double paraY;
    private double paraZ;
    private double paraYaw;
    private double paraPitch;
    @SideOnly(Side.CLIENT)
    private double velocityX;
    @SideOnly(Side.CLIENT)
    private double velocityY;
    @SideOnly(Side.CLIENT)
    private double velocityZ;
    
    public MCH_EntityHide(final World par1World) {
        super(par1World);
        this.func_70105_a(1.0f, 1.0f);
        this.field_70156_m = true;
        this.field_70129_M = this.field_70131_O / 2.0f;
        this.user = null;
        final double field_70159_w = 0.0;
        this.field_70179_y = field_70159_w;
        this.field_70181_x = field_70159_w;
        this.field_70159_w = field_70159_w;
    }
    
    public MCH_EntityHide(final World par1World, final double x, final double y, final double z) {
        this(par1World);
        this.field_70165_t = x;
        this.field_70163_u = y;
        this.field_70161_v = z;
    }
    
    @Override
    protected void func_70088_a() {
        super.func_70088_a();
        this.createRopeIndex(-1);
        this.func_70096_w().func_75682_a(31, (Object)new Integer(0));
    }
    
    public void setParent(final MCH_EntityAircraft ac, final Entity user, final int ropeIdx) {
        this.ac = ac;
        this.setRopeIndex(ropeIdx);
        this.user = user;
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
        return true;
    }
    
    public double func_70042_X() {
        return this.field_70131_O * 0.0 - 0.3;
    }
    
    @Override
    public boolean func_70097_a(final DamageSource par1DamageSource, final float par2) {
        return false;
    }
    
    public boolean func_70067_L() {
        return !this.field_70128_L;
    }
    
    protected void func_70014_b(final NBTTagCompound nbt) {
    }
    
    protected void func_70037_a(final NBTTagCompound nbt) {
    }
    
    @SideOnly(Side.CLIENT)
    public float func_70053_R() {
        return 0.0f;
    }
    
    @Override
    public boolean func_130002_c(final EntityPlayer par1EntityPlayer) {
        return false;
    }
    
    public void createRopeIndex(final int defaultValue) {
        this.func_70096_w().func_75682_a(30, (Object)new Integer(defaultValue));
    }
    
    public int getRopeIndex() {
        return this.func_70096_w().func_75679_c(30);
    }
    
    public void setRopeIndex(final int value) {
        this.func_70096_w().func_75692_b(30, (Object)new Integer(value));
    }
    
    @SideOnly(Side.CLIENT)
    public void func_70056_a(final double par1, final double par3, final double par5, final float par7, final float par8, final int par9) {
        this.paraPosRotInc = par9 + 10;
        this.paraX = par1;
        this.paraY = par3;
        this.paraZ = par5;
        this.paraYaw = par7;
        this.paraPitch = par8;
        this.field_70159_w = this.velocityX;
        this.field_70181_x = this.velocityY;
        this.field_70179_y = this.velocityZ;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_70016_h(final double par1, final double par3, final double par5) {
        this.field_70159_w = par1;
        this.velocityX = par1;
        this.field_70181_x = par3;
        this.velocityY = par3;
        this.field_70179_y = par5;
        this.velocityZ = par5;
    }
    
    public void func_70106_y() {
        super.func_70106_y();
    }
    
    public void func_70071_h_() {
        super.func_70071_h_();
        if (this.user != null && !this.field_70170_p.field_72995_K) {
            if (this.ac != null) {
                this.func_70096_w().func_75692_b(31, (Object)new Integer(this.ac.func_145782_y()));
            }
            this.user.func_70078_a((Entity)this);
            this.user = null;
        }
        if (this.ac == null && this.field_70170_p.field_72995_K) {
            final int id = this.func_70096_w().func_75679_c(31);
            if (id > 0) {
                final Entity entity = this.field_70170_p.func_73045_a(id);
                if (entity instanceof MCH_EntityAircraft) {
                    this.ac = (MCH_EntityAircraft)entity;
                }
            }
        }
        this.field_70169_q = this.field_70165_t;
        this.field_70167_r = this.field_70163_u;
        this.field_70166_s = this.field_70161_v;
        this.field_70143_R = 0.0f;
        if (this.field_70153_n != null) {
            this.field_70153_n.field_70143_R = 0.0f;
        }
        if (this.ac != null) {
            if (!this.ac.isRepelling()) {
                this.func_70106_y();
            }
            final int id = this.getRopeIndex();
            if (id >= 0) {
                final Vec3 v = this.ac.getRopePos(id);
                this.field_70165_t = v.field_72450_a;
                this.field_70161_v = v.field_72449_c;
            }
        }
        this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
        if (this.field_70170_p.field_72995_K) {
            this.onUpdateClient();
        }
        else {
            this.onUpdateServer();
        }
    }
    
    public void onUpdateClient() {
        if (this.paraPosRotInc > 0) {
            final double x = this.field_70165_t + (this.paraX - this.field_70165_t) / this.paraPosRotInc;
            final double y = this.field_70163_u + (this.paraY - this.field_70163_u) / this.paraPosRotInc;
            final double z = this.field_70161_v + (this.paraZ - this.field_70161_v) / this.paraPosRotInc;
            final double yaw = MathHelper.func_76138_g(this.paraYaw - this.field_70177_z);
            this.field_70177_z += (float)(yaw / this.paraPosRotInc);
            this.field_70125_A += (float)((this.paraPitch - this.field_70125_A) / this.paraPosRotInc);
            --this.paraPosRotInc;
            this.func_70107_b(x, y, z);
            this.func_70101_b(this.field_70177_z, this.field_70125_A);
            if (this.field_70153_n != null) {
                this.func_70101_b(this.field_70153_n.field_70126_B, this.field_70125_A);
            }
        }
        else {
            this.func_70107_b(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
            this.field_70159_w *= 0.99;
            this.field_70181_x *= 0.95;
            this.field_70179_y *= 0.99;
        }
    }
    
    public void onUpdateServer() {
        this.field_70181_x -= (this.field_70122_E ? 0.01 : 0.03);
        if (this.field_70122_E) {
            this.onGroundAndDead();
            return;
        }
        this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
        this.field_70181_x *= 0.9;
        this.field_70159_w *= 0.95;
        this.field_70179_y *= 0.95;
        final int id = this.getRopeIndex();
        if (this.ac != null && id >= 0) {
            final Vec3 v = this.ac.getRopePos(id);
            if (Math.abs(this.field_70163_u - v.field_72448_b) > Math.abs(this.ac.ropesLength) + 5.0f) {
                this.onGroundAndDead();
            }
        }
        if (this.field_70153_n != null && this.field_70153_n.field_70128_L) {
            this.field_70153_n = null;
            this.func_70106_y();
        }
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
                if (this.field_70170_p.func_72899_e(k2, 64, l2)) {
                    for (int i3 = k - 1; i3 < l; ++i3) {
                        final Block block = W_WorldFunc.getBlock(this.field_70170_p, k2, i3, l2);
                        if (block != null) {
                            block.func_149743_a(this.field_70170_p, k2, i3, l2, par2AxisAlignedBB, (List)collidingBoundingBoxes, par1Entity);
                        }
                    }
                }
            }
        }
        final double d0 = 0.25;
        final List list = this.field_70170_p.func_72839_b(par1Entity, par2AxisAlignedBB.func_72314_b(d0, d0, d0));
        for (int j3 = 0; j3 < list.size(); ++j3) {
            final Entity entity = list.get(j3);
            if (!W_Lib.isEntityLivingBase(entity)) {
                if (!(entity instanceof MCH_EntitySeat)) {
                    if (!(entity instanceof MCH_EntityHitBox)) {
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
        return collidingBoundingBoxes;
    }
    
    public void func_70091_d(double par1, double par3, double par5) {
        this.field_70170_p.field_72984_F.func_76320_a("move");
        this.field_70139_V *= 0.4f;
        final double d3 = this.field_70165_t;
        final double d4 = this.field_70163_u;
        final double d5 = this.field_70161_v;
        final double d6 = par1;
        final double d7 = par3;
        final double d8 = par5;
        final AxisAlignedBB axisalignedbb = this.field_70121_D.func_72329_c();
        List list = this.getCollidingBoundingBoxes(this, this.field_70121_D.func_72321_a(par1, par3, par5));
        for (int i = 0; i < list.size(); ++i) {
            par3 = list.get(i).func_72323_b(this.field_70121_D, par3);
        }
        this.field_70121_D.func_72317_d(0.0, par3, 0.0);
        if (!this.field_70135_K && d7 != par3) {
            par5 = 0.0;
            par3 = 0.0;
            par1 = 0.0;
        }
        final boolean flag1 = this.field_70122_E || (d7 != par3 && d7 < 0.0);
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
        if (this.field_70138_W > 0.0f && flag1 && this.field_70139_V < 0.05f && (d6 != par1 || d8 != par5)) {
            final double d9 = par1;
            final double d10 = par3;
            final double d11 = par5;
            par1 = d6;
            par3 = this.field_70138_W;
            par5 = d8;
            final AxisAlignedBB axisalignedbb2 = this.field_70121_D.func_72329_c();
            this.field_70121_D.func_72328_c(axisalignedbb);
            list = this.getCollidingBoundingBoxes(this, this.field_70121_D.func_72321_a(d6, par3, d8));
            for (int k = 0; k < list.size(); ++k) {
                par3 = list.get(k).func_72323_b(this.field_70121_D, par3);
            }
            this.field_70121_D.func_72317_d(0.0, par3, 0.0);
            if (!this.field_70135_K && d7 != par3) {
                par5 = 0.0;
                par3 = 0.0;
                par1 = 0.0;
            }
            for (int k = 0; k < list.size(); ++k) {
                par1 = list.get(k).func_72316_a(this.field_70121_D, par1);
            }
            this.field_70121_D.func_72317_d(par1, 0.0, 0.0);
            if (!this.field_70135_K && d6 != par1) {
                par5 = 0.0;
                par3 = 0.0;
                par1 = 0.0;
            }
            for (int k = 0; k < list.size(); ++k) {
                par5 = list.get(k).func_72322_c(this.field_70121_D, par5);
            }
            this.field_70121_D.func_72317_d(0.0, 0.0, par5);
            if (!this.field_70135_K && d8 != par5) {
                par5 = 0.0;
                par3 = 0.0;
                par1 = 0.0;
            }
            if (!this.field_70135_K && d7 != par3) {
                par5 = 0.0;
                par3 = 0.0;
                par1 = 0.0;
            }
            else {
                par3 = -this.field_70138_W;
                for (int k = 0; k < list.size(); ++k) {
                    par3 = list.get(k).func_72323_b(this.field_70121_D, par3);
                }
                this.field_70121_D.func_72317_d(0.0, par3, 0.0);
            }
            if (d9 * d9 + d11 * d11 >= par1 * par1 + par5 * par5) {
                par1 = d9;
                par3 = d10;
                par5 = d11;
                this.field_70121_D.func_72328_c(axisalignedbb2);
            }
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
            final CrashReport crashreport = CrashReport.func_85055_a(throwable, "Checking entity tile collision");
            final CrashReportCategory crashreportcategory = crashreport.func_85058_a("Entity being checked for collision");
            this.func_85029_a(crashreportcategory);
            throw new ReportedException(crashreport);
        }
        this.field_70170_p.field_72984_F.func_76319_b();
    }
    
    public void onGroundAndDead() {
        this.field_70163_u += 0.5;
        this.func_70043_V();
        this.func_70106_y();
    }
    
    public void _updateRiderPosition() {
        if (this.field_70153_n != null) {
            final double x = -Math.sin(this.field_70177_z * 3.141592653589793 / 180.0) * 0.1;
            final double z = Math.cos(this.field_70177_z * 3.141592653589793 / 180.0) * 0.1;
            this.field_70153_n.func_70107_b(this.field_70165_t + x, this.field_70163_u + this.func_70042_X() + this.field_70153_n.func_70033_W(), this.field_70161_v + z);
        }
    }
}
