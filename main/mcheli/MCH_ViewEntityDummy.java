package mcheli;

import net.minecraft.client.entity.*;
import net.minecraft.world.*;
import net.minecraft.client.*;
import mcheli.wrapper.*;
import net.minecraft.entity.*;

public class MCH_ViewEntityDummy extends EntityPlayerSP
{
    private static MCH_ViewEntityDummy instance;
    private float zoom;
    
    private MCH_ViewEntityDummy(final World world) {
        super(Minecraft.func_71410_x(), world, W_Session.newSession(), 0);
        this.field_70737_aN = 0;
        this.field_70738_aO = 1;
        this.func_70105_a(1.0f, 1.0f);
    }
    
    public static MCH_ViewEntityDummy getInstance(final World w) {
        if ((MCH_ViewEntityDummy.instance == null || MCH_ViewEntityDummy.instance.field_70128_L) && w.field_72995_K) {
            MCH_ViewEntityDummy.instance = new MCH_ViewEntityDummy(w);
            if (Minecraft.func_71410_x().field_71439_g != null) {
                MCH_ViewEntityDummy.instance.field_71158_b = Minecraft.func_71410_x().field_71439_g.field_71158_b;
            }
            MCH_ViewEntityDummy.instance.func_70107_b(0.0, -4.0, 0.0);
            w.func_72838_d((Entity)MCH_ViewEntityDummy.instance);
        }
        return MCH_ViewEntityDummy.instance;
    }
    
    public static void onUnloadWorld() {
        if (MCH_ViewEntityDummy.instance != null) {
            MCH_ViewEntityDummy.instance.func_70106_y();
            MCH_ViewEntityDummy.instance = null;
        }
    }
    
    public void func_70071_h_() {
    }
    
    public void update(final MCH_Camera camera) {
        if (camera == null) {
            return;
        }
        this.zoom = camera.getCameraZoom();
        this.field_70126_B = this.field_70177_z;
        this.field_70127_C = this.field_70125_A;
        this.field_70177_z = camera.rotationYaw;
        this.field_70125_A = camera.rotationPitch;
        this.field_70169_q = camera.posX;
        this.field_70167_r = camera.posY;
        this.field_70166_s = camera.posZ;
        this.field_70165_t = camera.posX;
        this.field_70163_u = camera.posY;
        this.field_70161_v = camera.posZ;
    }
    
    public static void setCameraPosition(final double x, final double y, final double z) {
        if (MCH_ViewEntityDummy.instance == null) {
            return;
        }
        MCH_ViewEntityDummy.instance.field_70169_q = x;
        MCH_ViewEntityDummy.instance.field_70167_r = y;
        MCH_ViewEntityDummy.instance.field_70166_s = z;
        MCH_ViewEntityDummy.instance.field_70142_S = x;
        MCH_ViewEntityDummy.instance.field_70137_T = y;
        MCH_ViewEntityDummy.instance.field_70136_U = z;
        MCH_ViewEntityDummy.instance.field_70165_t = x;
        MCH_ViewEntityDummy.instance.field_70163_u = y;
        MCH_ViewEntityDummy.instance.field_70161_v = z;
    }
    
    public float func_71151_f() {
        return super.func_71151_f() * (1.0f / this.zoom);
    }
    
    static {
        MCH_ViewEntityDummy.instance = null;
    }
}
