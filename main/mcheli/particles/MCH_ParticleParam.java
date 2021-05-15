package mcheli.particles;

import net.minecraft.world.*;

public class MCH_ParticleParam
{
    public final World world;
    public final String name;
    public double posX;
    public double posY;
    public double posZ;
    public double motionX;
    public double motionY;
    public double motionZ;
    public float size;
    public float a;
    public float r;
    public float g;
    public float b;
    public boolean isEffectWind;
    public int age;
    public boolean diffusible;
    public boolean toWhite;
    public float gravity;
    public float motionYUpAge;
    
    public MCH_ParticleParam(final World w, final String name, final double x, final double y, final double z) {
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.size = 1.0f;
        this.a = 1.0f;
        this.r = 1.0f;
        this.g = 1.0f;
        this.b = 1.0f;
        this.isEffectWind = false;
        this.age = 0;
        this.diffusible = false;
        this.toWhite = false;
        this.gravity = 0.0f;
        this.motionYUpAge = 2.0f;
        this.world = w;
        this.name = name;
        this.posX = x;
        this.posY = y;
        this.posZ = z;
    }
    
    public MCH_ParticleParam(final World w, final String name, final double x, final double y, final double z, final double mx, final double my, final double mz, final float size) {
        this(w, name, x, y, z);
        this.motionX = mx;
        this.motionY = my;
        this.motionZ = mz;
        this.size = size;
    }
    
    public void setColor(final float a, final float r, final float g, final float b) {
        this.a = a;
        this.r = r;
        this.g = g;
        this.b = b;
    }
    
    public void setMotion(final double x, final double y, final double z) {
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
    }
}
