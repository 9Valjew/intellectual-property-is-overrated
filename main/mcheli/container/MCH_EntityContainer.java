package mcheli.container;

import cpw.mods.fml.relauncher.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import mcheli.multiplay.*;
import net.minecraft.entity.player.*;
import mcheli.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import mcheli.wrapper.*;
import java.util.*;
import net.minecraft.nbt.*;
import mcheli.aircraft.*;

public class MCH_EntityContainer extends W_EntityContainer implements MCH_IEntityCanRideAircraft
{
    private boolean field_70279_a;
    private double speedMultiplier;
    private int boatPosRotationIncrements;
    private double boatX;
    private double boatY;
    private double boatZ;
    private double boatYaw;
    private double boatPitch;
    @SideOnly(Side.CLIENT)
    private double velocityX;
    @SideOnly(Side.CLIENT)
    private double velocityY;
    @SideOnly(Side.CLIENT)
    private double velocityZ;
    
    public MCH_EntityContainer(final World par1World) {
        super(par1World);
        this.speedMultiplier = 0.07;
        this.field_70156_m = true;
        this.func_70105_a(2.0f, 1.0f);
        this.field_70129_M = this.field_70131_O / 2.0f;
        this.field_70138_W = 0.6f;
        this.field_70178_ae = true;
        this.field_70155_l = 2.0;
    }
    
    public MCH_EntityContainer(final World par1World, final double par2, final double par4, final double par6) {
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
        this.field_70180_af.func_75682_a(17, (Object)new Integer(0));
        this.field_70180_af.func_75682_a(18, (Object)new Integer(1));
        this.field_70180_af.func_75682_a(19, (Object)new Integer(0));
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
    
    @Override
    public int func_70302_i_() {
        return 54;
    }
    
    @Override
    public String getInvName() {
        return "Container " + super.getInvName();
    }
    
    public double func_70042_X() {
        return -0.3;
    }
    
    @Override
    public boolean func_70097_a(final DamageSource ds, float damage) {
        if (this.func_85032_ar()) {
            return false;
        }
        if (this.field_70170_p.field_72995_K || this.field_70128_L) {
            return false;
        }
        final MCH_Config config = MCH_MOD.config;
        damage = MCH_Config.applyDamageByExternal(this, ds, damage);
        if (!MCH_Multiplay.canAttackEntity(ds, this)) {
            return false;
        }
        if (ds.func_76346_g() instanceof EntityPlayer && ds.func_76355_l().equalsIgnoreCase("player")) {
            MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityContainer.attackEntityFrom:damage=%.1f:%s", damage, ds.func_76355_l());
            W_WorldFunc.MOD_playSoundAtEntity(this, "hit", 1.0f, 1.3f);
            this.setDamageTaken(this.getDamageTaken() + (int)(damage * 20.0f));
            this.setForwardDirection(-this.getForwardDirection());
            this.setTimeSinceHit(10);
            this.func_70018_K();
            final boolean flag = ds.func_76346_g() instanceof EntityPlayer && ((EntityPlayer)ds.func_76346_g()).field_71075_bZ.field_75098_d;
            if (flag || this.getDamageTaken() > 40.0f) {
                if (!flag) {
                    this.dropItemWithOffset(MCH_MOD.itemContainer, 1, 0.0f);
                }
                this.func_70106_y();
            }
            return true;
        }
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_70057_ab() {
        this.setForwardDirection(-this.getForwardDirection());
        this.setTimeSinceHit(10);
        this.setDamageTaken(this.getDamageTaken() * 11);
    }
    
    public boolean func_70067_L() {
        return !this.field_70128_L;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_70056_a(final double par1, final double par3, final double par5, final float par7, final float par8, final int par9) {
        this.boatPosRotationIncrements = par9 + 10;
        this.boatX = par1;
        this.boatY = par3;
        this.boatZ = par5;
        this.boatYaw = par7;
        this.boatPitch = par8;
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
    
    public void func_70071_h_() {
        super.func_70071_h_();
        if (this.getTimeSinceHit() > 0) {
            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
        }
        if (this.getDamageTaken() > 0.0f) {
            this.setDamageTaken(this.getDamageTaken() - 1);
        }
        this.field_70169_q = this.field_70165_t;
        this.field_70167_r = this.field_70163_u;
        this.field_70166_s = this.field_70161_v;
        final byte b0 = 5;
        double d0 = 0.0;
        for (int i = 0; i < b0; ++i) {
            final double d2 = this.field_70121_D.field_72338_b + (this.field_70121_D.field_72337_e - this.field_70121_D.field_72338_b) * (i + 0) / b0 - 0.125;
            final double d3 = this.field_70121_D.field_72338_b + (this.field_70121_D.field_72337_e - this.field_70121_D.field_72338_b) * (i + 1) / b0 - 0.125;
            final AxisAlignedBB axisalignedbb = W_AxisAlignedBB.getAABB(this.field_70121_D.field_72340_a, d2, this.field_70121_D.field_72339_c, this.field_70121_D.field_72336_d, d3, this.field_70121_D.field_72334_f);
            if (this.field_70170_p.func_72830_b(axisalignedbb, Material.field_151586_h)) {
                d0 += 1.0 / b0;
            }
            else if (this.field_70170_p.func_72830_b(axisalignedbb, Material.field_151587_i)) {
                d0 += 1.0 / b0;
            }
        }
        final double d4 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
        if (d4 > 0.2625) {
            final double d5 = Math.cos(this.field_70177_z * 3.141592653589793 / 180.0);
            Math.sin(this.field_70177_z * 3.141592653589793 / 180.0);
        }
        if (this.field_70170_p.field_72995_K) {
            if (this.boatPosRotationIncrements > 0) {
                final double d5 = this.field_70165_t + (this.boatX - this.field_70165_t) / this.boatPosRotationIncrements;
                final double d6 = this.field_70163_u + (this.boatY - this.field_70163_u) / this.boatPosRotationIncrements;
                final double d7 = this.field_70161_v + (this.boatZ - this.field_70161_v) / this.boatPosRotationIncrements;
                final double d8 = MathHelper.func_76138_g(this.boatYaw - this.field_70177_z);
                this.field_70177_z += (float)(d8 / this.boatPosRotationIncrements);
                this.field_70125_A += (float)((this.boatPitch - this.field_70125_A) / this.boatPosRotationIncrements);
                --this.boatPosRotationIncrements;
                this.func_70107_b(d5, d6, d7);
                this.func_70101_b(this.field_70177_z, this.field_70125_A);
            }
            else {
                final double d5 = this.field_70165_t + this.field_70159_w;
                final double d6 = this.field_70163_u + this.field_70181_x;
                final double d7 = this.field_70161_v + this.field_70179_y;
                this.func_70107_b(d5, d6, d7);
                if (this.field_70122_E) {
                    final float groundSpeed = 0.9f;
                    this.field_70159_w *= 0.8999999761581421;
                    this.field_70179_y *= 0.8999999761581421;
                }
                this.field_70159_w *= 0.99;
                this.field_70181_x *= 0.95;
                this.field_70179_y *= 0.99;
            }
        }
        else {
            if (d0 < 1.0) {
                final double d5 = d0 * 2.0 - 1.0;
                this.field_70181_x += 0.04 * d5;
            }
            else {
                if (this.field_70181_x < 0.0) {
                    this.field_70181_x /= 2.0;
                }
                this.field_70181_x += 0.007;
            }
            double d5 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
            if (d5 > 0.35) {
                final double d6 = 0.35 / d5;
                this.field_70159_w *= d6;
                this.field_70179_y *= d6;
                d5 = 0.35;
            }
            if (d5 > d4 && this.speedMultiplier < 0.35) {
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
            if (this.field_70122_E) {
                final float groundSpeed = 0.9f;
                this.field_70159_w *= 0.8999999761581421;
                this.field_70179_y *= 0.8999999761581421;
            }
            this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
            this.field_70159_w *= 0.99;
            this.field_70181_x *= 0.95;
            this.field_70179_y *= 0.99;
            this.field_70125_A = 0.0f;
            double d6 = this.field_70177_z;
            final double d7 = this.field_70169_q - this.field_70165_t;
            final double d8 = this.field_70166_s - this.field_70161_v;
            if (d7 * d7 + d8 * d8 > 0.001) {
                d6 = (float)(Math.atan2(d8, d7) * 180.0 / 3.141592653589793);
            }
            double d9 = MathHelper.func_76138_g(d6 - this.field_70177_z);
            if (d9 > 5.0) {
                d9 = 5.0;
            }
            if (d9 < -5.0) {
                d9 = -5.0;
            }
            this.func_70101_b(this.field_70177_z += (float)d9, this.field_70125_A);
            if (!this.field_70170_p.field_72995_K) {
                final List list = this.field_70170_p.func_72839_b((Entity)this, this.field_70121_D.func_72314_b(0.2, 0.0, 0.2));
                if (list != null && !list.isEmpty()) {
                    for (int l = 0; l < list.size(); ++l) {
                        final Entity entity = list.get(l);
                        if (entity.func_70104_M() && entity instanceof MCH_EntityContainer) {
                            entity.func_70108_f((Entity)this);
                        }
                    }
                }
                final MCH_Config config = MCH_MOD.config;
                if (MCH_Config.Collision_DestroyBlock.prmBool) {
                    for (int l = 0; l < 4; ++l) {
                        final int i2 = MathHelper.func_76128_c(this.field_70165_t + (l % 2 - 0.5) * 0.8);
                        final int j1 = MathHelper.func_76128_c(this.field_70161_v + (l / 2 - 0.5) * 0.8);
                        for (int k1 = 0; k1 < 2; ++k1) {
                            final int l2 = MathHelper.func_76128_c(this.field_70163_u) + k1;
                            if (W_WorldFunc.isEqualBlock(this.field_70170_p, i2, l2, j1, W_Block.getSnowLayer())) {
                                this.field_70170_p.func_147468_f(i2, l2, j1);
                            }
                            else if (W_WorldFunc.isEqualBlock(this.field_70170_p, i2, l2, j1, W_Blocks.field_150392_bi)) {
                                W_WorldFunc.destroyBlock(this.field_70170_p, i2, l2, j1, true);
                            }
                        }
                    }
                }
            }
        }
    }
    
    @Override
    protected void func_70014_b(final NBTTagCompound par1NBTTagCompound) {
        super.func_70014_b(par1NBTTagCompound);
    }
    
    @Override
    protected void func_70037_a(final NBTTagCompound par1NBTTagCompound) {
        super.func_70037_a(par1NBTTagCompound);
    }
    
    @SideOnly(Side.CLIENT)
    public float func_70053_R() {
        return 2.0f;
    }
    
    @Override
    public boolean func_130002_c(final EntityPlayer player) {
        if (player != null) {
            this.openInventory(player);
        }
        return true;
    }
    
    public void setDamageTaken(final int par1) {
        this.field_70180_af.func_75692_b(19, (Object)par1);
    }
    
    public int getDamageTaken() {
        return this.field_70180_af.func_75679_c(19);
    }
    
    public void setTimeSinceHit(final int par1) {
        this.field_70180_af.func_75692_b(17, (Object)par1);
    }
    
    public int getTimeSinceHit() {
        return this.field_70180_af.func_75679_c(17);
    }
    
    public void setForwardDirection(final int par1) {
        this.field_70180_af.func_75692_b(18, (Object)par1);
    }
    
    public int getForwardDirection() {
        return this.field_70180_af.func_75679_c(18);
    }
    
    @Override
    public boolean canRideAircraft(final MCH_EntityAircraft ac, final int seatID, final MCH_SeatRackInfo info) {
        for (final String s : info.names) {
            if (s.equalsIgnoreCase("container")) {
                return ac.field_70154_o == null && this.field_70154_o == null;
            }
        }
        return false;
    }
    
    @Override
    public boolean isSkipNormalRender() {
        return this.field_70154_o instanceof MCH_EntitySeat;
    }
}
