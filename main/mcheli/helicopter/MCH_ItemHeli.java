package mcheli.helicopter;

import net.minecraft.world.*;
import net.minecraft.item.*;
import mcheli.*;
import mcheli.aircraft.*;

public class MCH_ItemHeli extends MCH_ItemAircraft
{
    public MCH_ItemHeli(final int par1) {
        super(par1);
        this.field_77777_bU = 1;
    }
    
    @Override
    public MCH_AircraftInfo getAircraftInfo() {
        return MCH_HeliInfoManager.getFromItem(this);
    }
    
    @Override
    public MCH_EntityHeli createAircraft(final World world, final double x, final double y, final double z, final ItemStack itemStack) {
        final MCH_HeliInfo info = MCH_HeliInfoManager.getFromItem(this);
        if (info == null) {
            MCH_Lib.Log(world, "##### MCH_ItemHeli Heli info null %s", this.func_77658_a());
            return null;
        }
        final MCH_EntityHeli heli = new MCH_EntityHeli(world);
        heli.func_70107_b(x, y + heli.field_70129_M, z);
        heli.field_70169_q = x;
        heli.field_70167_r = y;
        heli.field_70166_s = z;
        heli.camera.setPosition(x, y, z);
        heli.setTypeName(info.name);
        if (!world.field_72995_K) {
            heli.setTextureName(info.getTextureName());
        }
        return heli;
    }
}
