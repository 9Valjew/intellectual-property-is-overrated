package mcheli.uav;

import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import mcheli.wrapper.*;
import mcheli.*;
import java.util.*;
import net.minecraft.util.*;

public class MCH_ItemUavStation extends W_Item
{
    public static int UAV_STATION_KIND_NUM;
    public final int UavStationKind;
    
    public MCH_ItemUavStation(final int par1, final int kind) {
        super(par1);
        this.field_77777_bU = 1;
        this.UavStationKind = kind;
    }
    
    public MCH_EntityUavStation createUavStation(final World world, final double x, final double y, final double z, final int kind) {
        final MCH_EntityUavStation uavst = new MCH_EntityUavStation(world);
        uavst.func_70107_b(x, y + uavst.field_70129_M, z);
        uavst.field_70169_q = x;
        uavst.field_70167_r = y;
        uavst.field_70166_s = z;
        uavst.setKind(kind);
        return uavst;
    }
    
    public ItemStack func_77659_a(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        final float f = 1.0f;
        final float f2 = par3EntityPlayer.field_70127_C + (par3EntityPlayer.field_70125_A - par3EntityPlayer.field_70127_C) * f;
        final float f3 = par3EntityPlayer.field_70126_B + (par3EntityPlayer.field_70177_z - par3EntityPlayer.field_70126_B) * f;
        final double d0 = par3EntityPlayer.field_70169_q + (par3EntityPlayer.field_70165_t - par3EntityPlayer.field_70169_q) * f;
        final double d2 = par3EntityPlayer.field_70167_r + (par3EntityPlayer.field_70163_u - par3EntityPlayer.field_70167_r) * f + 1.62 - par3EntityPlayer.field_70129_M;
        final double d3 = par3EntityPlayer.field_70166_s + (par3EntityPlayer.field_70161_v - par3EntityPlayer.field_70166_s) * f;
        final Vec3 vec3 = W_WorldFunc.getWorldVec3(par2World, d0, d2, d3);
        final float f4 = MathHelper.func_76134_b(-f3 * 0.017453292f - 3.1415927f);
        final float f5 = MathHelper.func_76126_a(-f3 * 0.017453292f - 3.1415927f);
        final float f6 = -MathHelper.func_76134_b(-f2 * 0.017453292f);
        final float f7 = MathHelper.func_76126_a(-f2 * 0.017453292f);
        final float f8 = f5 * f6;
        final float f9 = f4 * f6;
        final double d4 = 5.0;
        final Vec3 vec4 = vec3.func_72441_c(f8 * d4, f7 * d4, f9 * d4);
        final MovingObjectPosition movingobjectposition = W_WorldFunc.clip(par2World, vec3, vec4, true);
        if (movingobjectposition == null) {
            return par1ItemStack;
        }
        final Vec3 vec5 = par3EntityPlayer.func_70676_i(f);
        boolean flag = false;
        final float f10 = 1.0f;
        final List list = par2World.func_72839_b((Entity)par3EntityPlayer, par3EntityPlayer.field_70121_D.func_72321_a(vec5.field_72450_a * d4, vec5.field_72448_b * d4, vec5.field_72449_c * d4).func_72314_b((double)f10, (double)f10, (double)f10));
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
        if (W_MovingObjectPosition.isHitTypeTile(movingobjectposition)) {
            final int i = movingobjectposition.field_72311_b;
            final int j = movingobjectposition.field_72312_c;
            final int k = movingobjectposition.field_72309_d;
            final MCH_EntityUavStation entityUavSt = this.createUavStation(par2World, i + 0.5f, j + 1.0f, k + 0.5f, this.UavStationKind);
            final int rot = (int)(MCH_Lib.getRotate360(par3EntityPlayer.field_70177_z) + 45.0);
            entityUavSt.field_70177_z = rot / 90 * 90 - 180;
            entityUavSt.initUavPostion();
            if (!par2World.func_72945_a((Entity)entityUavSt, entityUavSt.field_70121_D.func_72314_b(-0.1, -0.1, -0.1)).isEmpty()) {
                return par1ItemStack;
            }
            if (!par2World.field_72995_K) {
                par2World.func_72838_d((Entity)entityUavSt);
            }
            if (!par3EntityPlayer.field_71075_bZ.field_75098_d) {
                --par1ItemStack.field_77994_a;
            }
        }
        return par1ItemStack;
    }
    
    static {
        MCH_ItemUavStation.UAV_STATION_KIND_NUM = 2;
    }
}
