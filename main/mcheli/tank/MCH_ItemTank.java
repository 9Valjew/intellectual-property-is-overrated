package mcheli.tank;

import net.minecraft.world.*;
import net.minecraft.item.*;
import mcheli.*;
import mcheli.aircraft.*;

public class MCH_ItemTank extends MCH_ItemAircraft
{
    public MCH_ItemTank(final int par1) {
        super(par1);
        this.field_77777_bU = 1;
    }
    
    @Override
    public MCH_AircraftInfo getAircraftInfo() {
        return MCH_TankInfoManager.getFromItem(this);
    }
    
    @Override
    public MCH_EntityTank createAircraft(final World world, final double x, final double y, final double z, final ItemStack itemStack) {
        final MCH_TankInfo info = MCH_TankInfoManager.getFromItem(this);
        if (info == null) {
            MCH_Lib.Log(world, "##### MCH_EntityTank Tank info null %s", this.func_77658_a());
            return null;
        }
        final MCH_EntityTank tank = new MCH_EntityTank(world);
        tank.func_70107_b(x, y + tank.field_70129_M, z);
        tank.field_70169_q = x;
        tank.field_70167_r = y;
        tank.field_70166_s = z;
        tank.camera.setPosition(x, y, z);
        tank.setTypeName(info.name);
        if (!world.field_72995_K) {
            tank.setTextureName(info.getTextureName());
        }
        return tank;
    }
}
