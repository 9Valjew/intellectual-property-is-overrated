package mcheli.aircraft;

import net.minecraft.util.*;

public class MCH_SeatInfo
{
    public final Vec3 pos;
    public final boolean gunner;
    private final MCH_AircraftInfo.CameraPosition camPos;
    public boolean invCamPos;
    public final boolean switchgunner;
    public final boolean fixRot;
    public final float fixYaw;
    public final float fixPitch;
    public final float minPitch;
    public final float maxPitch;
    public final boolean rotSeat;
    
    public MCH_SeatInfo(final Vec3 p, final boolean g, final MCH_AircraftInfo.CameraPosition cp, final boolean icp, final boolean sg, final boolean fr, final float yaw, final float pitch, final float pmin, final float pmax, final boolean rotSeat) {
        this.camPos = cp;
        this.pos = p;
        this.gunner = g;
        this.invCamPos = icp;
        this.switchgunner = sg;
        this.fixRot = fr;
        this.fixYaw = yaw;
        this.fixPitch = pitch;
        this.minPitch = pmin;
        this.maxPitch = pmax;
        this.rotSeat = rotSeat;
    }
    
    public MCH_SeatInfo(final Vec3 p, final boolean g, final MCH_AircraftInfo.CameraPosition cp, final boolean icp, final boolean sg, final boolean fr, final float yaw, final float pitch, final boolean rotSeat) {
        this(p, g, cp, icp, sg, fr, yaw, pitch, -30.0f, 70.0f, rotSeat);
    }
    
    public MCH_SeatInfo(final Vec3 p, final MCH_AircraftInfo.CameraPosition cp, final float yaw, final float pitch, final boolean rotSeat) {
        this(p, false, cp, false, false, false, yaw, pitch, -30.0f, 70.0f, rotSeat);
    }
    
    public MCH_SeatInfo(final Vec3 p, final boolean rotSeat) {
        this(p, false, null, false, false, false, 0.0f, 0.0f, -30.0f, 70.0f, rotSeat);
    }
    
    public MCH_AircraftInfo.CameraPosition getCamPos() {
        return this.camPos;
    }
}
