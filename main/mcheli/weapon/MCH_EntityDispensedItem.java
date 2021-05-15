package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import mcheli.wrapper.*;
import mcheli.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;

public class MCH_EntityDispensedItem extends MCH_EntityBaseBullet
{
    public MCH_EntityDispensedItem(final World par1World) {
        super(par1World);
    }
    
    public MCH_EntityDispensedItem(final World par1World, final double posX, final double posY, final double posZ, final double targetX, final double targetY, final double targetZ, final float yaw, final float pitch, final double acceleration) {
        super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
    }
    
    @Override
    public void func_70071_h_() {
        super.func_70071_h_();
        if (this.getInfo() != null && !this.getInfo().disableSmoke) {
            this.spawnParticle(this.getInfo().trajectoryParticleName, 3, 7.0f * this.getInfo().smokeSize);
        }
        if (!this.field_70170_p.field_72995_K && this.getInfo() != null) {
            if (this.acceleration < 1.0E-4) {
                this.field_70159_w *= 0.999;
                this.field_70179_y *= 0.999;
            }
            if (this.func_70090_H()) {
                this.field_70159_w *= this.getInfo().velocityInWater;
                this.field_70181_x *= this.getInfo().velocityInWater;
                this.field_70179_y *= this.getInfo().velocityInWater;
            }
        }
        this.onUpdateBomblet();
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition m, final float damageFactor) {
        if (!this.field_70170_p.field_72995_K) {
            final AxisAlignedBB field_70121_D = this.field_70121_D;
            field_70121_D.field_72337_e += 2000.0;
            final AxisAlignedBB field_70121_D2 = this.field_70121_D;
            field_70121_D2.field_72338_b += 2000.0;
            EntityPlayer player = null;
            Item item = null;
            int itemDamage = 0;
            if (m != null && this.getInfo() != null) {
                if (this.shootingAircraft instanceof EntityPlayer) {
                    player = (EntityPlayer)this.shootingAircraft;
                }
                if (this.shootingEntity instanceof EntityPlayer) {
                    player = (EntityPlayer)this.shootingEntity;
                }
                item = this.getInfo().dispenseItem;
                itemDamage = this.getInfo().dispenseDamege;
            }
            if (player != null && !player.field_70128_L && item != null) {
                final EntityPlayer dummyPlayer = new MCH_DummyEntityPlayer(this.field_70170_p, player);
                dummyPlayer.field_70125_A = 90.0f;
                for (int RNG = this.getInfo().dispenseRange - 1, x = -RNG; x <= RNG; ++x) {
                    for (int y = -RNG; y <= RNG; ++y) {
                        if (y >= 0 && y < 256) {
                            for (int z = -RNG; z <= RNG; ++z) {
                                final int dist = x * x + y * y + z * z;
                                if (dist <= RNG * RNG) {
                                    if (dist <= 0.5 * RNG * RNG) {
                                        this.useItemToBlock(m.field_72311_b + x, m.field_72312_c + y, m.field_72309_d + z, item, itemDamage, dummyPlayer);
                                    }
                                    else if (this.field_70146_Z.nextInt(2) == 0) {
                                        this.useItemToBlock(m.field_72311_b + x, m.field_72312_c + y, m.field_72309_d + z, item, itemDamage, dummyPlayer);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            this.func_70106_y();
        }
    }
    
    private void useItemToBlock(final int x, final int y, final int z, final Item item, final int itemDamage, final EntityPlayer dummyPlayer) {
        dummyPlayer.field_70165_t = x + 0.5;
        dummyPlayer.field_70163_u = y + 2.5;
        dummyPlayer.field_70161_v = z + 0.5;
        dummyPlayer.field_70177_z = this.field_70146_Z.nextInt(360);
        final Block block = W_WorldFunc.getBlock(this.field_70170_p, x, y, z);
        final Material blockMat = W_WorldFunc.getBlockMaterial(this.field_70170_p, x, y, z);
        if (block != W_Blocks.field_150350_a && blockMat != Material.field_151579_a) {
            if (item == W_Item.getItemByName("water_bucket")) {
                final MCH_Config config = MCH_MOD.config;
                if (MCH_Config.Collision_DestroyBlock.prmBool) {
                    if (blockMat == Material.field_151581_o) {
                        this.field_70170_p.func_147468_f(x, y, z);
                    }
                    else if (blockMat == Material.field_151587_i) {
                        final int metadata = this.field_70170_p.func_72805_g(x, y, z);
                        if (metadata == 0) {
                            W_WorldFunc.setBlock(this.field_70170_p, x, y, z, W_Blocks.field_150343_Z);
                        }
                        else if (metadata <= 4) {
                            W_WorldFunc.setBlock(this.field_70170_p, x, y, z, W_Blocks.field_150347_e);
                        }
                    }
                }
            }
            else if (!item.onItemUseFirst(new ItemStack(item, 1, itemDamage), dummyPlayer, this.field_70170_p, x, y, z, 1, (float)x, (float)y, (float)z) && !item.func_77648_a(new ItemStack(item, 1, itemDamage), dummyPlayer, this.field_70170_p, x, y, z, 1, (float)x, (float)y, (float)z)) {
                item.func_77659_a(new ItemStack(item, 1, itemDamage), this.field_70170_p, dummyPlayer);
            }
        }
    }
    
    @Override
    public void sprinkleBomblet() {
        if (!this.field_70170_p.field_72995_K) {
            final MCH_EntityDispensedItem e = new MCH_EntityDispensedItem(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70159_w, this.field_70181_x, this.field_70179_y, this.field_70146_Z.nextInt(360), 0.0f, this.acceleration);
            e.setParameterFromWeapon(this, this.shootingAircraft, this.shootingEntity);
            e.setName(this.getName());
            final float MOTION = 1.0f;
            final float RANDOM = this.getInfo().bombletDiff;
            e.field_70159_w = this.field_70159_w * 1.0 + (this.field_70146_Z.nextFloat() - 0.5f) * RANDOM;
            e.field_70181_x = this.field_70181_x * 1.0 / 2.0 + (this.field_70146_Z.nextFloat() - 0.5f) * RANDOM / 2.0f;
            e.field_70179_y = this.field_70179_y * 1.0 + (this.field_70146_Z.nextFloat() - 0.5f) * RANDOM;
            e.setBomblet();
            this.field_70170_p.func_72838_d((Entity)e);
        }
    }
    
    @Override
    public MCH_BulletModel getDefaultBulletModel() {
        return MCH_DefaultBulletModels.Bomb;
    }
}
