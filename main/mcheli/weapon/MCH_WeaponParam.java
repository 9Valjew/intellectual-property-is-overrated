package mcheli.weapon;

import net.minecraft.entity.*;

public class MCH_WeaponParam
{
    public Entity entity;
    public Entity user;
    public double posX;
    public double posY;
    public double posZ;
    public float rotYaw;
    public float rotPitch;
    public float rotRoll;
    public int option1;
    public int option2;
    public boolean isInfinity;
    public boolean isTurret;
    public boolean result;
    public boolean reload;
    
    public MCH_WeaponParam() {
        this.entity = null;
        this.user = null;
        this.posX = 0.0;
        this.posY = 0.0;
        this.posZ = 0.0;
        this.rotYaw = 0.0f;
        this.rotPitch = 0.0f;
        this.rotRoll = 0.0f;
        this.option1 = 0;
        this.option2 = 0;
        this.isInfinity = false;
        this.isTurret = false;
    }
    
    public void setPosAndRot(final double x, final double y, final double z, final float yaw, final float pitch) {
        this.setPosition(x, y, z);
        this.setRotation(yaw, pitch);
    }
    
    public void setPosition(final double x, final double y, final double z) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
    }
    
    public void setRotation(final float y, final float p) {
        this.rotYaw = y;
        this.rotPitch = p;
    }
}
