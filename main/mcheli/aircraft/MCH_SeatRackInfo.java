package mcheli.aircraft;

import net.minecraft.util.*;

public class MCH_SeatRackInfo extends MCH_SeatInfo
{
    public final float range;
    public final float openParaAlt;
    public final String[] names;
    
    public MCH_SeatRackInfo(final String[] entityNames, final double x, final double y, final double z, final MCH_AircraftInfo.CameraPosition ep, final float rng, final float paraAlt, final float yaw, final float pitch, final boolean rotSeat) {
        super(Vec3.func_72443_a(x, y, z), ep, yaw, pitch, rotSeat);
        this.range = rng;
        this.openParaAlt = paraAlt;
        this.names = entityNames;
    }
    
    public Vec3 getEntryPos() {
        return this.getCamPos().pos;
    }
}
