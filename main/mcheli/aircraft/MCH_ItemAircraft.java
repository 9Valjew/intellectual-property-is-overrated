package mcheli.aircraft;

import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import mcheli.wrapper.*;
import mcheli.*;
import net.minecraft.entity.item.*;

public abstract class MCH_ItemAircraft extends W_Item
{
    private static boolean isRegistedDispenseBehavior;
    
    public MCH_ItemAircraft(final int i) {
        super(i);
    }
    
    public static void registerDispenseBehavior(final Item item) {
        if (MCH_ItemAircraft.isRegistedDispenseBehavior) {
            return;
        }
        BlockDispenser.field_149943_a.func_82595_a((Object)item, (Object)new MCH_ItemAircraftDispenseBehavior());
    }
    
    public abstract MCH_AircraftInfo getAircraftInfo();
    
    public abstract MCH_EntityAircraft createAircraft(final World p0, final double p1, final double p2, final double p3, final ItemStack p4);
    
    public MCH_EntityAircraft onTileClick(final ItemStack itemStack, final World world, final float rotationYaw, final int x, final int y, final int z) {
        final MCH_EntityAircraft ac = this.createAircraft(world, x + 0.5f, y + 1.0f, z + 0.5f, itemStack);
        if (ac == null) {
            return null;
        }
        ac.initRotationYaw(((MathHelper.func_76128_c(rotationYaw * 4.0f / 360.0f + 0.5) & 0x3) - 1) * 90);
        if (!world.func_72945_a((Entity)ac, ac.field_70121_D.func_72314_b(-0.1, -0.1, -0.1)).isEmpty()) {
            return null;
        }
        return ac;
    }
    
    public String toString() {
        final MCH_AircraftInfo info = this.getAircraftInfo();
        if (info != null) {
            return super.toString() + "(" + info.getDirectoryName() + ":" + info.name + ")";
        }
        return super.toString() + "(null)";
    }
    
    public ItemStack func_77659_a(final ItemStack par1ItemStack, final World world, final EntityPlayer player) {
        final float f = 1.0f;
        final float f2 = player.field_70127_C + (player.field_70125_A - player.field_70127_C) * f;
        final float f3 = player.field_70126_B + (player.field_70177_z - player.field_70126_B) * f;
        final double d0 = player.field_70169_q + (player.field_70165_t - player.field_70169_q) * f;
        final double d2 = player.field_70167_r + (player.field_70163_u - player.field_70167_r) * f + 1.62 - player.field_70129_M;
        final double d3 = player.field_70166_s + (player.field_70161_v - player.field_70166_s) * f;
        final Vec3 vec3 = W_WorldFunc.getWorldVec3(world, d0, d2, d3);
        final float f4 = MathHelper.func_76134_b(-f3 * 0.017453292f - 3.1415927f);
        final float f5 = MathHelper.func_76126_a(-f3 * 0.017453292f - 3.1415927f);
        final float f6 = -MathHelper.func_76134_b(-f2 * 0.017453292f);
        final float f7 = MathHelper.func_76126_a(-f2 * 0.017453292f);
        final float f8 = f5 * f6;
        final float f9 = f4 * f6;
        final double d4 = 5.0;
        final Vec3 vec4 = vec3.func_72441_c(f8 * d4, f7 * d4, f9 * d4);
        final MovingObjectPosition mop = W_WorldFunc.clip(world, vec3, vec4, true);
        if (mop == null) {
            return par1ItemStack;
        }
        final Vec3 vec5 = player.func_70676_i(f);
        boolean flag = false;
        final float f10 = 1.0f;
        final List list = world.func_72839_b((Entity)player, player.field_70121_D.func_72321_a(vec5.field_72450_a * d4, vec5.field_72448_b * d4, vec5.field_72449_c * d4).func_72314_b((double)f10, (double)f10, (double)f10));
        for (int i = 0; i < list.size(); ++i) {
            final Entity entity = list.get(i);
            if (entity.func_70067_L()) {
                final float f11 = entity.func_70111_Y();
                final AxisAlignedBB axisalignedbb = entity.field_70121_D.func_72314_b((double)f11, (double)f11, (double)f11);
                if (axisalignedbb.func_72318_a(vec3)) {
                    flag = true;
                }
            }
        }
        if (flag) {
            return par1ItemStack;
        }
        if (W_MovingObjectPosition.isHitTypeTile(mop)) {
            final MCH_Config config = MCH_MOD.config;
            if (MCH_Config.PlaceableOnSpongeOnly.prmBool) {
                final Block block = world.func_147439_a(mop.field_72311_b, mop.field_72312_c, mop.field_72309_d);
                if (!(block instanceof BlockSponge)) {
                    return par1ItemStack;
                }
            }
            this.spawnAircraft(par1ItemStack, world, player, mop.field_72311_b, mop.field_72312_c, mop.field_72309_d);
        }
        return par1ItemStack;
    }
    
    public MCH_EntityAircraft spawnAircraft(final ItemStack itemStack, final World world, final EntityPlayer player, final int x, final int y, final int z) {
        MCH_EntityAircraft ac = this.onTileClick(itemStack, world, player.field_70177_z, x, y, z);
        if (ac != null) {
            if (ac.isUAV()) {
                if (world.field_72995_K) {
                    if (ac.isSmallUAV()) {
                        W_EntityPlayer.addChatMessage(player, "Please use the UAV station OR Portable Controller");
                    }
                    else {
                        W_EntityPlayer.addChatMessage(player, "Please use the UAV station");
                    }
                }
                ac = null;
            }
            else {
                if (!world.field_72995_K) {
                    ac.getAcDataFromItem(itemStack);
                    world.func_72838_d((Entity)ac);
                    MCH_Achievement.addStat((Entity)player, MCH_Achievement.welcome, 1);
                }
                if (!player.field_71075_bZ.field_75098_d) {
                    --itemStack.field_77994_a;
                }
            }
        }
        return ac;
    }
    
    public void rideEntity(final ItemStack item, final Entity target, final EntityPlayer player) {
        final MCH_Config config = MCH_MOD.config;
        if (!MCH_Config.PlaceableOnSpongeOnly.prmBool && target instanceof EntityMinecartEmpty && target.field_70153_n == null) {
            final MCH_EntityAircraft ac = this.spawnAircraft(item, player.field_70170_p, player, (int)target.field_70165_t, (int)target.field_70163_u + 2, (int)target.field_70161_v);
            if (!player.field_70170_p.field_72995_K && ac != null) {
                ac.func_70078_a(target);
            }
        }
    }
    
    static {
        MCH_ItemAircraft.isRegistedDispenseBehavior = false;
    }
}
