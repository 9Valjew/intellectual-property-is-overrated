package mcheli.aircraft;

public interface MCH_IEntityCanRideAircraft
{
    boolean isSkipNormalRender();
    
    boolean canRideAircraft(final MCH_EntityAircraft p0, final int p1, final MCH_SeatRackInfo p2);
}
