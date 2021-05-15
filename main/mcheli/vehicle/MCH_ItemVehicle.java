package mcheli.vehicle;

import net.minecraft.world.*;
import net.minecraft.item.*;
import mcheli.*;
import mcheli.aircraft.*;

public class MCH_ItemVehicle extends MCH_ItemAircraft
{
    public MCH_ItemVehicle(final int par1) {
        super(par1);
        this.field_77777_bU = 1;
    }
    
    @Override
    public MCH_AircraftInfo getAircraftInfo() {
        return MCH_VehicleInfoManager.getFromItem(this);
    }
    
    @Override
    public MCH_EntityVehicle createAircraft(final World world, final double x, final double y, final double z, final ItemStack item) {
        final MCH_VehicleInfo info = MCH_VehicleInfoManager.getFromItem(this);
        if (info == null) {
            MCH_Lib.Log(world, "##### MCH_ItemVehicle Vehicle info null %s", this.func_77658_a());
            return null;
        }
        final MCH_EntityVehicle vehicle = new MCH_EntityVehicle(world);
        vehicle.func_70107_b(x, y + vehicle.field_70129_M, z);
        vehicle.field_70169_q = x;
        vehicle.field_70167_r = y;
        vehicle.field_70166_s = z;
        vehicle.camera.setPosition(x, y, z);
        vehicle.setTypeName(info.name);
        if (!world.field_72995_K) {
            vehicle.setTextureName(info.getTextureName());
        }
        return vehicle;
    }
}
