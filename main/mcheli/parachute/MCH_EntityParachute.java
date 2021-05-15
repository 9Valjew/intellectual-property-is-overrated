package mcheli.parachute;

import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import mcheli.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import mcheli.particles.*;
import java.util.*;
import mcheli.wrapper.*;
import net.minecraft.entity.item.*;
import net.minecraft.block.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;

public class MCH_EntityParachute extends W_Entity
{
    private double speedMultiplier;
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
    public Entity user;
    public int onGroundCount;
    
    public MCH_EntityParachute(final World par1World) {
        super(par1World);
        this.speedMultiplier = 0.07;
        this.field_70156_m = true;
        this.func_70105_a(1.5f, 0.6f);
        this.field_70129_M = this.field_70131_O / 2.0f;
        this.user = null;
        this.onGroundCount = 0;
    }
    
    public MCH_EntityParachute(final World par1World, final double par2, final double par4, final double par6) {
        this(par1World);
        this.func_70107_b(par2, par4 + this.field_70129_M, par6);
        this.field_70159_w = 0.0;
        this.field_70181_x = 0.0;
        this.field_70179_y = 0.0;
        this.field_70169_q = par2;
        this.field_70167_r = par4;
        this.field_70166_s = par6;
    }
    
    protected boolean func_70041_e_() {
        return false;
    }
    
    @Override
    protected void func_70088_a() {
        this.func_70096_w().func_75682_a(31, (Object)(byte)0);
    }
    
    public void setType(final int n) {
        this.func_70096_w().func_75692_b(31, (Object)(byte)n);
    }
    
    public int getType() {
        return this.func_70096_w().func_75683_a(31);
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
        return this.field_70131_O * 0.0 - 0.30000001192092896;
    }
    
    @Override
    public boolean func_70097_a(final DamageSource par1DamageSource, final float par2) {
        return false;
    }
    
    public boolean func_70067_L() {
        return !this.field_70128_L;
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
        if (!this.field_70170_p.field_72995_K && this.field_70173_aa % 10 == 0) {
            MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityParachute.onUpdate %d, %.3f", this.field_70173_aa, this.field_70181_x);
        }
        if (this.isOpenParachute() && this.field_70181_x > -0.3 && this.field_70173_aa > 20) {
            this.field_70143_R *= 0.85;
        }
        if (!this.field_70170_p.field_72995_K && this.user != null && this.user.field_70154_o == null) {
            this.user.func_70078_a((Entity)this);
            final float field_70177_z = this.user.field_70177_z;
            this.field_70126_B = field_70177_z;
            this.field_70177_z = field_70177_z;
            this.user = null;
        }
        this.field_70169_q = this.field_70165_t;
        this.field_70167_r = this.field_70163_u;
        this.field_70166_s = this.field_70161_v;
        final double d1 = this.field_70121_D.field_72338_b + (this.field_70121_D.field_72337_e - this.field_70121_D.field_72338_b) * 0.0 / 5.0 - 0.125;
        final double d2 = this.field_70121_D.field_72338_b + (this.field_70121_D.field_72337_e - this.field_70121_D.field_72338_b) * 1.0 / 5.0 - 0.125;
        final AxisAlignedBB axisalignedbb = W_AxisAlignedBB.getAABB(this.field_70121_D.field_72340_a, d1, this.field_70121_D.field_72339_c, this.field_70121_D.field_72336_d, d2, this.field_70121_D.field_72334_f);
        if (this.field_70170_p.func_72830_b(axisalignedbb, Material.field_151586_h)) {
            this.onWaterSetBoat();
            this.func_70106_y();
        }
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
            if (this.field_70122_E) {}
            this.field_70159_w *= 0.99;
            this.field_70181_x *= 0.95;
            this.field_70179_y *= 0.99;
        }
        if (!this.isOpenParachute() && this.field_70181_x > 0.01) {
            final float color = 0.6f + this.field_70146_Z.nextFloat() * 0.2f;
            final double dx = this.field_70169_q - this.field_70165_t;
            final double dy = this.field_70167_r - this.field_70163_u;
            final double dz = this.field_70166_s - this.field_70161_v;
            final int num = 1 + (int)(MathHelper.func_76133_a(dx * dx + dy * dy + dz * dz) * 2.0);
            for (double i = 0.0; i < num; ++i) {
                final MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", this.field_70169_q + (this.field_70165_t - this.field_70169_q) * (i / num) * 0.8, this.field_70167_r + (this.field_70163_u - this.field_70167_r) * (i / num) * 0.8, this.field_70166_s + (this.field_70161_v - this.field_70166_s) * (i / num) * 0.8);
                prm.motionX = this.field_70159_w * 0.5 + (this.field_70146_Z.nextDouble() - 0.5) * 0.5;
                prm.motionX = this.field_70181_x * -0.5 + (this.field_70146_Z.nextDouble() - 0.5) * 0.5;
                prm.motionX = this.field_70179_y * 0.5 + (this.field_70146_Z.nextDouble() - 0.5) * 0.5;
                prm.size = 5.0f;
                prm.setColor(0.8f + this.field_70146_Z.nextFloat(), color, color, color);
                MCH_ParticlesUtil.spawnParticle(prm);
            }
        }
    }
    
    public void onUpdateServer() {
        final double prevSpeed = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
        double gravity = this.field_70122_E ? 0.01 : 0.03;
        if (this.getType() == 2 && this.field_70173_aa < 20) {
            gravity = 0.01;
        }
        this.field_70181_x -= gravity;
        if (this.isOpenParachute()) {
            if (W_Lib.isEntityLivingBase(this.field_70153_n)) {
                double mv = W_Lib.getEntityMoveDist(this.field_70153_n);
                if (!this.isOpenParachute()) {
                    mv = 0.0;
                }
                if (mv > 0.0) {
                    final double mx = -Math.sin(this.field_70153_n.field_70177_z * 3.1415927f / 180.0f);
                    final double mz = Math.cos(this.field_70153_n.field_70177_z * 3.1415927f / 180.0f);
                    this.field_70159_w += mx * this.speedMultiplier * 0.05;
                    this.field_70179_y += mz * this.speedMultiplier * 0.05;
                }
            }
            double speed = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
            if (speed > 0.35) {
                this.field_70159_w *= 0.35 / speed;
                this.field_70179_y *= 0.35 / speed;
                speed = 0.35;
            }
            if (speed > prevSpeed && this.speedMultiplier < 0.35) {
                this.speedMultiplier += (0.35 - this.speedMultiplier) / 35.0;
                if (this.speedMultiplier > 0.35) {
                    this.speedMultiplier = 0.35;
                }
            }
            else {
                this.speedMultiplier -= (this.speedMultiplier - 0.07) / 35.0;
                if (this.speedMultiplier < 0.07) {
                    this.speedMultiplier = 0.07;
                }
            }
        }
        if (this.field_70122_E) {
            ++this.onGroundCount;
            if (this.onGroundCount > 5) {
                this.onGroundAndDead();
                return;
            }
        }
        this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
        if (this.getType() == 2 && this.field_70173_aa < 20) {
            this.field_70181_x *= 0.95;
        }
        else {
            this.field_70181_x *= 0.9;
        }
        if (this.isOpenParachute()) {
            this.field_70159_w *= 0.95;
            this.field_70179_y *= 0.95;
        }
        else {
            this.field_70159_w *= 0.99;
            this.field_70179_y *= 0.99;
        }
        this.field_70125_A = 0.0f;
        double yaw = this.field_70177_z;
        final double dx = this.field_70169_q - this.field_70165_t;
        final double dz = this.field_70166_s - this.field_70161_v;
        if (dx * dx + dz * dz > 0.001) {
            yaw = (float)(Math.atan2(dx, dz) * 180.0 / 3.141592653589793);
        }
        double yawDiff = MathHelper.func_76138_g(yaw - this.field_70177_z);
        if (yawDiff > 20.0) {
            yawDiff = 20.0;
        }
        if (yawDiff < -20.0) {
            yawDiff = -20.0;
        }
        if (this.field_70153_n != null) {
            this.func_70101_b(this.field_70153_n.field_70177_z, this.field_70125_A);
        }
        else {
            this.func_70101_b(this.field_70177_z += (float)yawDiff, this.field_70125_A);
        }
        final List list = this.field_70170_p.func_72839_b((Entity)this, this.field_70121_D.func_72314_b(0.2, 0.0, 0.2));
        if (list != null && !list.isEmpty()) {
            for (int l = 0; l < list.size(); ++l) {
                final Entity entity = list.get(l);
                if (entity != this.field_70153_n && entity.func_70104_M() && entity instanceof MCH_EntityParachute) {
                    entity.func_70108_f((Entity)this);
                }
            }
        }
        if (this.field_70153_n != null && this.field_70153_n.field_70128_L) {
            this.field_70153_n = null;
            this.func_70106_y();
        }
    }
    
    public void onGroundAndDead() {
        this.field_70163_u += 1.2;
        this.func_70043_V();
        this.func_70106_y();
    }
    
    public void onWaterSetBoat() {
        if (this.field_70170_p.field_72995_K) {
            return;
        }
        if (this.getType() != 2) {
            return;
        }
        if (this.field_70153_n == null) {
            return;
        }
        final int px = (int)(this.field_70165_t + 0.5);
        int py = (int)(this.field_70163_u + 0.5);
        final int pz = (int)(this.field_70161_v + 0.5);
        boolean foundBlock = false;
        for (int y = 0; y < 5 && py + y >= 0; ++y) {
            if (py + y > 255) {
                break;
            }
            final Block block = W_WorldFunc.getBlock(this.field_70170_p, px, py - y, pz);
            if (block == W_Block.getWater()) {
                py -= y;
                foundBlock = true;
                break;
            }
        }
        if (!foundBlock) {
            return;
        }
        int countWater = 0;
        final int size = 5;
        for (int y2 = 0; y2 < 3 && py + y2 >= 0 && py + y2 <= 255; ++y2) {
            for (int x = -2; x <= 2; ++x) {
                for (int z = -2; z <= 2; ++z) {
                    final Block block2 = W_WorldFunc.getBlock(this.field_70170_p, px + x, py - y2, pz + z);
                    if (block2 == W_Block.getWater() && ++countWater > 37) {
                        break;
                    }
                }
            }
        }
        if (countWater > 37) {
            final EntityBoat entityboat = new EntityBoat(this.field_70170_p, (double)px, (double)(py + 1.0f), (double)pz);
            entityboat.field_70177_z = this.field_70177_z - 90.0f;
            this.field_70170_p.func_72838_d((Entity)entityboat);
            this.field_70153_n.func_70078_a((Entity)entityboat);
        }
    }
    
    public boolean isOpenParachute() {
        return this.getType() != 2 || this.field_70181_x < -0.1;
    }
    
    public void func_70043_V() {
        if (this.field_70153_n != null) {
            final double x = -Math.sin(this.field_70177_z * 3.141592653589793 / 180.0) * 0.1;
            final double z = Math.cos(this.field_70177_z * 3.141592653589793 / 180.0) * 0.1;
            this.field_70153_n.func_70107_b(this.field_70165_t + x, this.field_70163_u + this.func_70042_X() + this.field_70153_n.func_70033_W(), this.field_70161_v + z);
        }
    }
    
    protected void func_70014_b(final NBTTagCompound nbt) {
        nbt.func_74774_a("ParachuteModelType", (byte)this.getType());
    }
    
    protected void func_70037_a(final NBTTagCompound nbt) {
        this.setType(nbt.func_74771_c("ParachuteModelType"));
    }
    
    @SideOnly(Side.CLIENT)
    public float func_70053_R() {
        return 4.0f;
    }
    
    @Override
    public boolean func_130002_c(final EntityPlayer par1EntityPlayer) {
        return false;
    }
}
