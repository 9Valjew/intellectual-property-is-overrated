package mcheli;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import mcheli.wrapper.*;
import net.minecraft.potion.*;

public class MCH_Camera
{
    private final World worldObj;
    private float zoom;
    private int[] mode;
    private boolean[] canUseShader;
    private int[] lastMode;
    public double posX;
    public double posY;
    public double posZ;
    public float rotationYaw;
    public float rotationPitch;
    public float prevRotationYaw;
    public float prevRotationPitch;
    private int lastZoomDir;
    public float partRotationYaw;
    public float partRotationPitch;
    public float prevPartRotationYaw;
    public float prevPartRotationPitch;
    public static final int MODE_NORMAL = 0;
    public static final int MODE_NIGHTVISION = 1;
    public static final int MODE_THERMALVISION = 2;
    
    public MCH_Camera(final World w, final Entity p) {
        this.worldObj = w;
        this.mode = new int[] { 0, 0 };
        this.zoom = 1.0f;
        this.lastMode = new int[this.getUserMax()];
        this.lastZoomDir = 0;
        this.canUseShader = new boolean[this.getUserMax()];
    }
    
    public MCH_Camera(final World w, final Entity p, final double x, final double y, final double z) {
        this(w, p);
        this.setPosition(x, y, z);
        this.setCameraZoom(1.0f);
    }
    
    public int getUserMax() {
        return this.mode.length;
    }
    
    public void initCamera(final int uid, final Entity viewer) {
        this.setCameraZoom(1.0f);
        this.setMode(uid, 0);
        this.updateViewer(uid, viewer);
    }
    
    public void setMode(final int uid, final int m) {
        if (!this.isValidUid(uid)) {
            return;
        }
        switch (this.mode[uid] = ((m < 0) ? 0 : (m % this.getModeNum(uid)))) {
            case 2: {
                if (this.worldObj.field_72995_K) {
                    W_EntityRenderer.activateShader("pencil");
                    break;
                }
                break;
            }
            case 0:
            case 1: {
                if (this.worldObj.field_72995_K) {
                    W_EntityRenderer.deactivateShader();
                    break;
                }
                break;
            }
        }
    }
    
    public void setShaderSupport(final int uid, final Boolean b) {
        if (this.isValidUid(uid)) {
            this.setMode(uid, 0);
            this.canUseShader[uid] = b;
        }
    }
    
    public boolean isValidUid(final int uid) {
        return uid >= 0 && uid < this.getUserMax();
    }
    
    public int getModeNum(final int uid) {
        if (!this.isValidUid(uid)) {
            return 2;
        }
        return this.canUseShader[uid] ? 3 : 2;
    }
    
    public int getMode(final int uid) {
        return this.isValidUid(uid) ? this.mode[uid] : 0;
    }
    
    public String getModeName(final int uid) {
        if (this.getMode(uid) == 1) {
            return "NIGHT VISION";
        }
        if (this.getMode(uid) == 2) {
            return "THERMAL VISION";
        }
        return "";
    }
    
    public void updateViewer(final int uid, final Entity viewer) {
        if (!this.isValidUid(uid) || viewer == null) {
            return;
        }
        if (W_Lib.isEntityLivingBase(viewer) && !viewer.field_70128_L) {
            if (this.getMode(uid) == 0 && this.lastMode[uid] != 0) {
                final PotionEffect pe = W_Entity.getActivePotionEffect(viewer, Potion.field_76439_r);
                if (pe != null && pe.func_76459_b() > 0 && pe.func_76459_b() < 500) {
                    if (viewer.field_70170_p.field_72995_K) {
                        W_Entity.removePotionEffectClient(viewer, Potion.field_76439_r.field_76415_H);
                    }
                    else {
                        W_Entity.removePotionEffect(viewer, Potion.field_76439_r.field_76415_H);
                    }
                }
            }
            if (this.getMode(uid) == 1 || this.getMode(uid) == 2) {
                final PotionEffect pe = W_Entity.getActivePotionEffect(viewer, Potion.field_76439_r);
                if ((pe == null || (pe != null && pe.func_76459_b() < 500)) && !viewer.field_70170_p.field_72995_K) {
                    W_Entity.addPotionEffect(viewer, new PotionEffect(Potion.field_76439_r.field_76415_H, 250, 0, true));
                }
            }
        }
        this.lastMode[uid] = this.getMode(uid);
    }
    
    public void setPosition(final double x, final double y, final double z) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
    }
    
    public void setCameraZoom(final float z) {
        final float prevZoom = this.zoom;
        this.zoom = ((z < 1.0f) ? 1.0f : z);
        if (this.zoom > prevZoom) {
            this.lastZoomDir = 1;
        }
        else if (this.zoom < prevZoom) {
            this.lastZoomDir = -1;
        }
        else {
            this.lastZoomDir = 0;
        }
    }
    
    public float getCameraZoom() {
        return this.zoom;
    }
}
