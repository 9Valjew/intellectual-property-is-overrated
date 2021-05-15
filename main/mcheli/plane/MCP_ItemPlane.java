package mcheli.plane;

import net.minecraft.world.*;
import net.minecraft.item.*;
import mcheli.*;
import mcheli.aircraft.*;

public class MCP_ItemPlane extends MCH_ItemAircraft
{
    public MCP_ItemPlane(final int par1) {
        super(par1);
        this.field_77777_bU = 1;
    }
    
    @Override
    public MCH_AircraftInfo getAircraftInfo() {
        return MCP_PlaneInfoManager.getFromItem(this);
    }
    
    @Override
    public MCP_EntityPlane createAircraft(final World world, final double x, final double y, final double z, final ItemStack itemStack) {
        final MCP_PlaneInfo info = MCP_PlaneInfoManager.getFromItem(this);
        if (info == null) {
            MCH_Lib.Log(world, "##### MCP_EntityPlane Plane info null %s", this.func_77658_a());
            return null;
        }
        final MCP_EntityPlane plane = new MCP_EntityPlane(world);
        plane.func_70107_b(x, y + plane.field_70129_M, z);
        plane.field_70169_q = x;
        plane.field_70167_r = y;
        plane.field_70166_s = z;
        plane.camera.setPosition(x, y, z);
        plane.setTypeName(info.name);
        if (!world.field_72995_K) {
            plane.setTextureName(info.getTextureName());
        }
        return plane;
    }
}
