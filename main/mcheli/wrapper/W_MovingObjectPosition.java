package mcheli.wrapper;

import net.minecraft.util.*;

public class W_MovingObjectPosition
{
    public static boolean isHitTypeEntity(final MovingObjectPosition m) {
        return m != null && m.field_72313_a == MovingObjectPosition.MovingObjectType.ENTITY;
    }
    
    public static boolean isHitTypeTile(final MovingObjectPosition m) {
        return m != null && m.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK;
    }
    
    public static MovingObjectPosition newMOP(final int p1, final int p2, final int p3, final int p4, final Vec3 p5, final boolean p6) {
        return new MovingObjectPosition(p1, p2, p3, p4, p5, p6);
    }
}
