package mcheli.weapon;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import mcheli.wrapper.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import cpw.mods.fml.relauncher.*;

public class MCH_EntityA10 extends W_Entity
{
    public static final int DATAWT_NAME = 29;
    public final int DESPAWN_COUNT = 70;
    public int despawnCount;
    public Entity shootingAircraft;
    public Entity shootingEntity;
    public int shotCount;
    public int direction;
    public int power;
    public float acceleration;
    public int explosionPower;
    public boolean isFlaming;
    public String name;
    public MCH_WeaponInfo weaponInfo;
    static int snd_num;
    
    public MCH_EntityA10(final World world) {
        super(world);
        this.despawnCount = 0;
        this.shotCount = 0;
        this.direction = 0;
        this.field_70158_ak = true;
        this.field_70156_m = false;
        this.func_70105_a(5.0f, 3.0f);
        this.field_70129_M = this.field_70131_O / 2.0f;
        this.field_70159_w = 0.0;
        this.field_70181_x = 0.0;
        this.field_70179_y = 0.0;
        this.power = 32;
        this.acceleration = 4.0f;
        this.explosionPower = 1;
        this.isFlaming = false;
        this.shootingEntity = null;
        this.shootingAircraft = null;
        this.field_70178_ae = true;
        this.field_70155_l *= 10.0;
    }
    
    public MCH_EntityA10(final World world, final double x, final double y, final double z) {
        this(world);
        this.field_70165_t = x;
        this.field_70169_q = x;
        this.field_70142_S = x;
        this.field_70163_u = y;
        this.field_70167_r = y;
        this.field_70137_T = y;
        this.field_70161_v = z;
        this.field_70166_s = z;
        this.field_70136_U = z;
    }
    
    protected boolean func_70041_e_() {
        return false;
    }
    
    @Override
    protected void func_70088_a() {
        this.func_70096_w().func_75682_a(29, (Object)String.valueOf(""));
    }
    
    public void setWeaponName(final String s) {
        if (s != null && !s.isEmpty()) {
            this.weaponInfo = MCH_WeaponInfoManager.get(s);
            if (this.weaponInfo != null && !this.field_70170_p.field_72995_K) {
                this.func_70096_w().func_75692_b(29, (Object)String.valueOf(s));
            }
        }
    }
    
    public String getWeaponName() {
        return this.func_70096_w().func_75681_e(29);
    }
    
    public MCH_WeaponInfo getInfo() {
        return this.weaponInfo;
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
    
    @Override
    public boolean func_70097_a(final DamageSource par1DamageSource, final float par2) {
        return false;
    }
    
    public boolean func_70067_L() {
        return false;
    }
    
    public void func_70106_y() {
        super.func_70106_y();
    }
    
    public void func_70071_h_() {
        super.func_70071_h_();
        if (!this.field_70128_L) {
            ++this.despawnCount;
        }
        if (this.weaponInfo == null) {
            this.setWeaponName(this.getWeaponName());
            if (this.weaponInfo == null) {
                this.func_70106_y();
                return;
            }
        }
        if (this.field_70170_p.field_72995_K) {
            this.onUpdate_Client();
        }
        else {
            this.onUpdate_Server();
        }
        if (!this.field_70128_L) {
            if (this.despawnCount <= 20) {
                this.field_70181_x = -0.3;
            }
            else {
                this.func_70107_b(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
                this.field_70181_x += 0.02;
            }
        }
    }
    
    public boolean isRender() {
        return this.despawnCount > 20;
    }
    
    private void onUpdate_Client() {
        this.shotCount += 4;
    }
    
    private void onUpdate_Server() {
        if (!this.field_70128_L) {
            if (this.despawnCount > 70) {
                this.func_70106_y();
            }
            else if (this.despawnCount > 0 && this.shotCount < 40) {
                for (int i = 0; i < 2; ++i) {
                    this.shotGAU8(true, this.shotCount);
                    ++this.shotCount;
                }
                if (this.shotCount == 38) {
                    W_WorldFunc.MOD_playSoundEffect(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, "gau-8_snd", 150.0f, 1.0f);
                }
            }
        }
    }
    
    protected void shotGAU8(final boolean playSound, final int cnt) {
        final float yaw = 90 * this.direction;
        final float pitch = 30.0f;
        double x = this.field_70165_t;
        final double y = this.field_70163_u;
        double z = this.field_70161_v;
        double tX = this.field_70146_Z.nextDouble() - 0.5;
        double tY = -2.6;
        double tZ = this.field_70146_Z.nextDouble() - 0.5;
        if (this.direction == 0) {
            tZ += 10.0;
            z += cnt * 0.6;
        }
        if (this.direction == 1) {
            tX -= 10.0;
            x -= cnt * 0.6;
        }
        if (this.direction == 2) {
            tZ -= 10.0;
            z -= cnt * 0.6;
        }
        if (this.direction == 3) {
            tX += 10.0;
            x += cnt * 0.6;
        }
        final double dist = MathHelper.func_76133_a(tX * tX + tY * tY + tZ * tZ);
        tX = tX * 4.0 / dist;
        tY = tY * 4.0 / dist;
        tZ = tZ * 4.0 / dist;
        final MCH_EntityBullet e = new MCH_EntityBullet(this.field_70170_p, x, y, z, tX, tY, tZ, yaw, pitch, this.acceleration);
        e.setName(this.getWeaponName());
        e.explosionPower = ((this.shotCount % 4 == 0) ? this.explosionPower : 0);
        e.setPower(this.power);
        e.shootingEntity = this.shootingEntity;
        e.shootingAircraft = this.shootingAircraft;
        this.field_70170_p.func_72838_d((Entity)e);
    }
    
    protected void func_70014_b(final NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.func_74778_a("WeaponName", this.getWeaponName());
    }
    
    protected void func_70037_a(final NBTTagCompound par1NBTTagCompound) {
        this.despawnCount = 200;
        if (par1NBTTagCompound.func_74764_b("WeaponName")) {
            this.setWeaponName(par1NBTTagCompound.func_74779_i("WeaponName"));
        }
    }
    
    @SideOnly(Side.CLIENT)
    public float func_70053_R() {
        return 10.0f;
    }
    
    static {
        MCH_EntityA10.snd_num = 0;
    }
}
