package mcheli.gltd;

import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import mcheli.weapon.*;
import mcheli.*;
import mcheli.multiplay.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import mcheli.wrapper.*;
import net.minecraft.nbt.*;

public class MCH_EntityGLTD extends W_Entity
{
    private boolean field_70279_a;
    private double speedMultiplier;
    private int gltdPosRotInc;
    private double gltdX;
    private double gltdY;
    private double gltdZ;
    private double gltdYaw;
    private double gltdPitch;
    @SideOnly(Side.CLIENT)
    private double velocityX;
    @SideOnly(Side.CLIENT)
    private double velocityY;
    @SideOnly(Side.CLIENT)
    private double velocityZ;
    public final MCH_Camera camera;
    public boolean zoomDir;
    public final MCH_WeaponCAS weaponCAS;
    public int countWait;
    public boolean isUsedPlayer;
    public float renderRotaionYaw;
    public float renderRotaionPitch;
    public int retryRiddenByEntityCheck;
    public Entity lastRiddenByEntity;
    
    public MCH_EntityGLTD(final World world) {
        super(world);
        this.field_70279_a = true;
        this.speedMultiplier = 0.07;
        this.field_70156_m = true;
        this.func_70105_a(0.5f, 0.5f);
        this.field_70129_M = this.field_70131_O / 2.0f;
        this.camera = new MCH_Camera(world, this);
        final MCH_WeaponInfo wi = MCH_WeaponInfoManager.get("a10gau8");
        this.weaponCAS = new MCH_WeaponCAS(world, Vec3.func_72443_a(0.0, 0.0, 0.0), 0.0f, 0.0f, "a10gau8", wi);
        final MCH_WeaponCAS weaponCAS = this.weaponCAS;
        weaponCAS.interval += ((this.weaponCAS.interval > 0) ? 150 : -150);
        this.weaponCAS.displayName = "A-10 GAU-8 Avenger";
        this.field_70158_ak = true;
        this.countWait = 0;
        this.retryRiddenByEntityCheck = 0;
        this.lastRiddenByEntity = null;
        this.isUsedPlayer = false;
        this.renderRotaionYaw = 0.0f;
        this.renderRotaionYaw = 0.0f;
        this.renderRotaionPitch = 0.0f;
        this.zoomDir = true;
        this.field_70155_l = 2.0;
    }
    
    public MCH_EntityGLTD(final World par1World, final double x, final double y, final double z) {
        this(par1World);
        this.func_70107_b(x, y + this.field_70129_M, z);
        this.field_70159_w = 0.0;
        this.field_70181_x = 0.0;
        this.field_70179_y = 0.0;
        this.field_70169_q = x;
        this.field_70167_r = y;
        this.field_70166_s = z;
        this.camera.setPosition(x, y, z);
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
        return false;
    }
    
    public double func_70042_X() {
        return this.field_70131_O * 0.0 - 0.3;
    }
    
    @Override
    public boolean func_70097_a(final DamageSource ds, float damage) {
        if (this.func_85032_ar()) {
            return false;
        }
        if (this.field_70170_p.field_72995_K || this.field_70128_L) {
            return true;
        }
        final MCH_Config config = MCH_MOD.config;
        damage = MCH_Config.applyDamageByExternal(this, ds, damage);
        if (!MCH_Multiplay.canAttackEntity(ds, this)) {
            return false;
        }
        this.setForwardDirection(-this.getForwardDirection());
        this.setTimeSinceHit(10);
        this.setDamageTaken((int)(this.getDamageTaken() + damage * 100.0f));
        this.func_70018_K();
        final boolean flag = ds.func_76346_g() instanceof EntityPlayer && ((EntityPlayer)ds.func_76346_g()).field_71075_bZ.field_75098_d;
        if (flag || this.getDamageTaken() > 40.0f) {
            this.camera.initCamera(0, this.field_70153_n);
            if (this.field_70153_n != null) {
                this.field_70153_n.func_70078_a((Entity)this);
            }
            if (!flag) {
                this.dropItemWithOffset(MCH_MOD.itemGLTD, 1, 0.0f);
            }
            W_WorldFunc.MOD_playSoundEffect(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, "hit", 1.0f, 1.0f);
            this.func_70106_y();
        }
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_70057_ab() {
    }
    
    public boolean func_70067_L() {
        return !this.field_70128_L;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_70056_a(final double par1, final double par3, final double par5, final float par7, final float par8, final int par9) {
        if (this.field_70279_a) {
            this.gltdPosRotInc = par9 + 5;
        }
        else {
            final double x = par1 - this.field_70165_t;
            final double y = par3 - this.field_70163_u;
            final double z = par5 - this.field_70161_v;
            if (x * x + y * y + z * z <= 1.0) {
                return;
            }
            this.gltdPosRotInc = 3;
        }
        this.gltdX = par1;
        this.gltdY = par3;
        this.gltdZ = par5;
        this.gltdYaw = par7;
        this.gltdPitch = par8;
        this.field_70159_w = this.velocityX;
        this.field_70181_x = this.velocityY;
        this.field_70179_y = this.velocityZ;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_70016_h(final double x, final double y, final double z) {
        this.field_70159_w = x;
        this.velocityX = x;
        this.field_70181_x = y;
        this.velocityY = y;
        this.field_70179_y = z;
        this.velocityZ = z;
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
        final double d3 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
        if (this.field_70153_n != null) {
            this.camera.updateViewer(0, this.field_70153_n);
        }
        if (this.field_70170_p.field_72995_K && this.field_70279_a) {
            if (this.gltdPosRotInc > 0) {
                final double d4 = this.field_70165_t + (this.gltdX - this.field_70165_t) / this.gltdPosRotInc;
                final double d5 = this.field_70163_u + (this.gltdY - this.field_70163_u) / this.gltdPosRotInc;
                final double d6 = this.field_70161_v + (this.gltdZ - this.field_70161_v) / this.gltdPosRotInc;
                final double d7 = MathHelper.func_76138_g(this.gltdYaw - this.field_70177_z);
                this.field_70177_z += (float)(d7 / this.gltdPosRotInc);
                this.field_70125_A += (float)((this.gltdPitch - this.field_70125_A) / this.gltdPosRotInc);
                --this.gltdPosRotInc;
                this.func_70107_b(d4, d5, d6);
                this.func_70101_b(this.field_70177_z, this.field_70125_A);
            }
            else {
                final double d4 = this.field_70165_t + this.field_70159_w;
                final double d5 = this.field_70163_u + this.field_70181_x;
                final double d6 = this.field_70161_v + this.field_70179_y;
                this.func_70107_b(d4, d5, d6);
                if (this.field_70122_E) {
                    this.field_70159_w *= 0.5;
                    this.field_70181_x *= 0.5;
                    this.field_70179_y *= 0.5;
                }
                this.field_70159_w *= 0.99;
                this.field_70181_x *= 0.95;
                this.field_70179_y *= 0.99;
            }
        }
        else {
            this.field_70181_x -= 0.04;
            double d4 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
            if (d4 > 0.35) {
                final double d5 = 0.35 / d4;
                this.field_70159_w *= d5;
                this.field_70179_y *= d5;
                d4 = 0.35;
            }
            if (d4 > d3 && this.speedMultiplier < 0.35) {
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
                this.field_70159_w *= 0.5;
                this.field_70181_x *= 0.5;
                this.field_70179_y *= 0.5;
            }
            this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
            this.field_70159_w *= 0.99;
            this.field_70181_x *= 0.95;
            this.field_70179_y *= 0.99;
            this.field_70125_A = 0.0f;
            double d5 = this.field_70177_z;
            final double d6 = this.field_70169_q - this.field_70165_t;
            final double d7 = this.field_70166_s - this.field_70161_v;
            if (d6 * d6 + d7 * d7 > 0.001) {
                d5 = (float)(Math.atan2(d7, d6) * 180.0 / 3.141592653589793);
            }
            double d8 = MathHelper.func_76138_g(d5 - this.field_70177_z);
            if (d8 > 20.0) {
                d8 = 20.0;
            }
            if (d8 < -20.0) {
                d8 = -20.0;
            }
            this.func_70101_b(this.field_70177_z += (float)d8, this.field_70125_A);
            if (!this.field_70170_p.field_72995_K) {
                final MCH_Config config = MCH_MOD.config;
                if (MCH_Config.Collision_DestroyBlock.prmBool) {
                    for (int l = 0; l < 4; ++l) {
                        final int i1 = MathHelper.func_76128_c(this.field_70165_t + (l % 2 - 0.5) * 0.8);
                        final int j1 = MathHelper.func_76128_c(this.field_70161_v + (l / 2 - 0.5) * 0.8);
                        for (int k1 = 0; k1 < 2; ++k1) {
                            final int l2 = MathHelper.func_76128_c(this.field_70163_u) + k1;
                            if (W_WorldFunc.isEqualBlock(this.field_70170_p, i1, l2, j1, W_Block.getSnowLayer())) {
                                this.field_70170_p.func_147468_f(i1, l2, j1);
                            }
                        }
                    }
                }
                if (this.field_70153_n != null && this.field_70153_n.field_70128_L) {
                    this.field_70153_n = null;
                }
            }
        }
        this.updateCameraPosition(false);
        if (this.countWait > 0) {
            --this.countWait;
        }
        if (this.countWait < 0) {
            ++this.countWait;
        }
        this.weaponCAS.update(this.countWait);
        if (this.lastRiddenByEntity != null && this.field_70153_n == null) {
            if (this.retryRiddenByEntityCheck < 3) {
                ++this.retryRiddenByEntityCheck;
                this.setUnmoundPosition(this.lastRiddenByEntity);
            }
            else {
                this.unmountEntity();
            }
        }
        else {
            this.retryRiddenByEntityCheck = 0;
        }
        if (this.field_70153_n != null) {
            this.lastRiddenByEntity = this.field_70153_n;
        }
    }
    
    public void setUnmoundPosition(final Entity e) {
        if (e == null) {
            return;
        }
        final float yaw = this.field_70177_z;
        final double d0 = Math.sin(yaw * 3.141592653589793 / 180.0) * 1.2;
        final double d2 = -Math.cos(yaw * 3.141592653589793 / 180.0) * 1.2;
        e.func_70107_b(this.field_70165_t + d0, this.field_70163_u + this.func_70042_X() + e.func_70033_W() + 1.0, this.field_70161_v + d2);
        final double field_70165_t = e.field_70165_t;
        e.field_70169_q = field_70165_t;
        e.field_70142_S = field_70165_t;
        final double field_70163_u = e.field_70163_u;
        e.field_70167_r = field_70163_u;
        e.field_70137_T = field_70163_u;
        final double field_70161_v = e.field_70161_v;
        e.field_70166_s = field_70161_v;
        e.field_70136_U = field_70161_v;
    }
    
    public void unmountEntity() {
        this.camera.setMode(0, 0);
        this.camera.setCameraZoom(1.0f);
        if (!this.field_70170_p.field_72995_K) {
            if (this.field_70153_n != null) {
                if (!this.field_70153_n.field_70128_L) {
                    this.field_70153_n.func_70078_a((Entity)null);
                }
            }
            else if (this.lastRiddenByEntity != null && !this.lastRiddenByEntity.field_70128_L) {
                this.camera.updateViewer(0, this.lastRiddenByEntity);
                this.setUnmoundPosition(this.lastRiddenByEntity);
            }
        }
        this.field_70153_n = null;
        this.lastRiddenByEntity = null;
    }
    
    public void updateCameraPosition(final boolean foreceUpdate) {
        if (foreceUpdate || (this.field_70153_n != null && this.camera != null)) {
            final double x = -Math.sin(this.field_70177_z * 3.141592653589793 / 180.0) * 0.6;
            final double z = Math.cos(this.field_70177_z * 3.141592653589793 / 180.0) * 0.6;
            this.camera.setPosition(this.field_70165_t + x, this.field_70163_u + 0.7, this.field_70161_v + z);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void zoomCamera(final float f) {
        float z = this.camera.getCameraZoom();
        z += f;
        if (z < 1.0f) {
            z = 1.0f;
        }
        if (z > 10.0f) {
            z = 10.0f;
        }
        this.camera.setCameraZoom(z);
    }
    
    public void func_70043_V() {
        if (this.field_70153_n != null) {
            final double x = Math.sin(this.field_70177_z * 3.141592653589793 / 180.0) * 0.5;
            final double z = -Math.cos(this.field_70177_z * 3.141592653589793 / 180.0) * 0.5;
            this.field_70153_n.func_70107_b(this.field_70165_t + x, this.field_70163_u + this.func_70042_X() + this.field_70153_n.func_70033_W(), this.field_70161_v + z);
        }
    }
    
    public void switchWeapon(final int id) {
    }
    
    public boolean useCurrentWeapon(final int option1, final int option2) {
        if (this.countWait == 0 && this.field_70153_n != null && this.weaponCAS.shot(this.field_70153_n, this.camera.posX, this.camera.posY, this.camera.posZ, option1, option2)) {
            this.countWait = this.weaponCAS.interval;
            if (this.field_70170_p.field_72995_K) {
                this.countWait += ((this.countWait > 0) ? 10 : -10);
            }
            else {
                W_WorldFunc.MOD_playSoundEffect(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, "gltd", 0.5f, 1.0f);
            }
            return true;
        }
        return false;
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
        if (this.field_70153_n != null && this.field_70153_n instanceof EntityPlayer && this.field_70153_n != player) {
            return true;
        }
        player.field_70177_z = MathHelper.func_76142_g(this.field_70177_z);
        player.field_70125_A = MathHelper.func_76142_g(this.field_70125_A);
        if (!this.field_70170_p.field_72995_K) {
            player.func_70078_a((Entity)this);
        }
        else {
            this.zoomDir = true;
            this.camera.setCameraZoom(1.0f);
            if (this.countWait > 0) {
                this.countWait = -this.countWait;
            }
            if (this.countWait > -60) {
                this.countWait = -60;
            }
        }
        this.updateCameraPosition(true);
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
        return 0;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_70270_d(final boolean par1) {
        this.field_70279_a = par1;
    }
}
