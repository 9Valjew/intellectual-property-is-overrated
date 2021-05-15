package mcheli.wrapper.modelloader;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class W_Vertex
{
    public float x;
    public float y;
    public float z;
    
    public W_Vertex(final float x, final float y) {
        this(x, y, 0.0f);
    }
    
    public W_Vertex(final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void normalize() {
        final double d = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        this.x /= (float)d;
        this.y /= (float)d;
        this.z /= (float)d;
    }
    
    public void add(final W_Vertex v) {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
    }
    
    public boolean equal(final W_Vertex v) {
        return this.x == v.x && this.y == v.y && this.z == v.z;
    }
}
