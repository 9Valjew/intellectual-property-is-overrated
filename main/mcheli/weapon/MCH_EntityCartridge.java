package mcheli.weapon;

import net.minecraftforge.client.model.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import cpw.mods.fml.relauncher.*;
import mcheli.*;
import mcheli.wrapper.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;

public class MCH_EntityCartridge extends W_Entity
{
    public final String texture_name;
    public final IModelCustom model;
    private final float bound;
    private final float gravity;
    private final float scale;
    private int countOnUpdate;
    public float targetYaw;
    public float targetPitch;
    
    @SideOnly(Side.CLIENT)
    public static void spawnCartridge(final World world, final MCH_Cartridge cartridge, final double x, final double y, final double z, final double mx, final double my, final double mz, final float yaw, final float pitch) {
        if (cartridge != null) {
            final MCH_EntityCartridge entityFX = new MCH_EntityCartridge(world, cartridge, x, y, z, mx + (world.field_73012_v.nextFloat() - 0.5) * 0.07, my, mz + (world.field_73012_v.nextFloat() - 0.5) * 0.07);
            entityFX.field_70126_B = yaw;
            entityFX.field_70177_z = yaw;
            entityFX.targetYaw = yaw;
            entityFX.field_70127_C = pitch;
            entityFX.field_70125_A = pitch;
            entityFX.targetPitch = pitch;
            final float cy = yaw + cartridge.yaw;
            final float cp = pitch + cartridge.pitch;
            final double tX = -MathHelper.func_76126_a(cy / 180.0f * 3.1415927f) * MathHelper.func_76134_b(cp / 180.0f * 3.1415927f);
            final double tZ = MathHelper.func_76134_b(cy / 180.0f * 3.1415927f) * MathHelper.func_76134_b(cp / 180.0f * 3.1415927f);
            final double tY = -MathHelper.func_76126_a(cp / 180.0f * 3.1415927f);
            final double d = MathHelper.func_76133_a(tX * tX + tY * tY + tZ * tZ);
            if (Math.abs(d) > 0.001) {
                final MCH_EntityCartridge mch_EntityCartridge = entityFX;
                mch_EntityCartridge.field_70159_w += tX * cartridge.acceleration / d;
                final MCH_EntityCartridge mch_EntityCartridge2 = entityFX;
                mch_EntityCartridge2.field_70181_x += tY * cartridge.acceleration / d;
                final MCH_EntityCartridge mch_EntityCartridge3 = entityFX;
                mch_EntityCartridge3.field_70179_y += tZ * cartridge.acceleration / d;
            }
            world.func_72838_d((Entity)entityFX);
        }
    }
    
    public MCH_EntityCartridge(final World par1World, final MCH_Cartridge c, final double x, final double y, final double z, final double mx, final double my, final double mz) {
        super(par1World);
        this.func_70080_a(x, y, z, 0.0f, 0.0f);
        this.field_70159_w = mx;
        this.field_70181_x = my;
        this.field_70179_y = mz;
        this.texture_name = c.name;
        this.model = c.model;
        this.bound = c.bound;
        this.gravity = c.gravity;
        this.scale = c.scale;
        this.countOnUpdate = 0;
    }
    
    public float getScale() {
        return this.scale;
    }
    
    public void func_70071_h_() {
        this.field_70169_q = this.field_70165_t;
        this.field_70167_r = this.field_70163_u;
        this.field_70166_s = this.field_70161_v;
        this.field_70126_B = this.field_70177_z;
        this.field_70127_C = this.field_70125_A;
        final int countOnUpdate = this.countOnUpdate;
        final MCH_Config config = MCH_MOD.config;
        if (countOnUpdate < MCH_Config.AliveTimeOfCartridge.prmInt) {
            ++this.countOnUpdate;
        }
        else {
            this.func_70106_y();
        }
        this.field_70159_w *= 0.98;
        this.field_70179_y *= 0.98;
        this.field_70181_x += this.gravity;
        this.move();
    }
    
    public void rotation() {
        if (this.field_70177_z < this.targetYaw - 3.0f) {
            this.field_70177_z += 10.0f;
            if (this.field_70177_z > this.targetYaw) {
                this.field_70177_z = this.targetYaw;
            }
        }
        else if (this.field_70177_z > this.targetYaw + 3.0f) {
            this.field_70177_z -= 10.0f;
            if (this.field_70177_z < this.targetYaw) {
                this.field_70177_z = this.targetYaw;
            }
        }
        if (this.field_70125_A < this.targetPitch) {
            this.field_70125_A += 10.0f;
            if (this.field_70125_A > this.targetPitch) {
                this.field_70125_A = this.targetPitch;
            }
        }
        else if (this.field_70125_A > this.targetPitch) {
            this.field_70125_A -= 10.0f;
            if (this.field_70125_A < this.targetPitch) {
                this.field_70125_A = this.targetPitch;
            }
        }
    }
    
    public void move() {
        final Vec3 vec1 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
        final Vec3 vec2 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
        final MovingObjectPosition m = W_WorldFunc.clip(this.field_70170_p, vec1, vec2);
        double d = Math.max(Math.abs(this.field_70159_w), Math.abs(this.field_70181_x));
        d = Math.max(d, Math.abs(this.field_70179_y));
        if (W_MovingObjectPosition.isHitTypeTile(m)) {
            this.func_70107_b(m.field_72307_f.field_72450_a, m.field_72307_f.field_72448_b, m.field_72307_f.field_72449_c);
            this.field_70159_w += d * (this.field_70146_Z.nextFloat() - 0.5f) * 0.10000000149011612;
            this.field_70181_x += d * (this.field_70146_Z.nextFloat() - 0.5f) * 0.10000000149011612;
            this.field_70179_y += d * (this.field_70146_Z.nextFloat() - 0.5f) * 0.10000000149011612;
            if (d > 0.10000000149011612) {
                this.targetYaw += (float)(d * (this.field_70146_Z.nextFloat() - 0.5f) * 720.0);
                this.targetPitch = (float)(d * (this.field_70146_Z.nextFloat() - 0.5f) * 720.0);
            }
            else {
                this.targetPitch = 0.0f;
            }
            switch (m.field_72310_e) {
                case 0: {
                    if (this.field_70181_x > 0.0) {
                        this.field_70181_x = -this.field_70181_x * this.bound;
                        break;
                    }
                    break;
                }
                case 1: {
                    if (this.field_70181_x < 0.0) {
                        this.field_70181_x = -this.field_70181_x * this.bound;
                    }
                    this.targetPitch *= 0.3f;
                    break;
                }
                case 2: {
                    if (this.field_70179_y > 0.0) {
                        this.field_70179_y = -this.field_70179_y * this.bound;
                        break;
                    }
                    this.field_70161_v += this.field_70179_y;
                    break;
                }
                case 3: {
                    if (this.field_70179_y < 0.0) {
                        this.field_70179_y = -this.field_70179_y * this.bound;
                        break;
                    }
                    this.field_70161_v += this.field_70179_y;
                    break;
                }
                case 4: {
                    if (this.field_70159_w > 0.0) {
                        this.field_70159_w = -this.field_70159_w * this.bound;
                        break;
                    }
                    this.field_70165_t += this.field_70159_w;
                    break;
                }
                case 5: {
                    if (this.field_70159_w < 0.0) {
                        this.field_70159_w = -this.field_70159_w * this.bound;
                        break;
                    }
                    this.field_70165_t += this.field_70159_w;
                    break;
                }
            }
        }
        else {
            this.field_70165_t += this.field_70159_w;
            this.field_70163_u += this.field_70181_x;
            this.field_70161_v += this.field_70179_y;
            if (d > 0.05000000074505806) {
                this.rotation();
            }
        }
    }
    
    protected void func_70037_a(final NBTTagCompound var1) {
    }
    
    protected void func_70014_b(final NBTTagCompound var1) {
    }
}
