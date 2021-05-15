package mcheli.weapon;

import net.minecraftforge.client.model.*;

public class MCH_Cartridge
{
    public IModelCustom model;
    public final String name;
    public final float acceleration;
    public final float yaw;
    public final float pitch;
    public final float bound;
    public final float gravity;
    public final float scale;
    
    public MCH_Cartridge(final String nm, final float a, final float y, final float p, final float b, final float g, final float s) {
        this.name = nm;
        this.acceleration = a;
        this.yaw = y;
        this.pitch = p;
        this.bound = b;
        this.gravity = g;
        this.scale = s;
    }
}
