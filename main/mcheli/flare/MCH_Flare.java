package mcheli.flare;

import net.minecraft.world.*;
import mcheli.aircraft.*;
import java.util.*;
import mcheli.particles.*;
import mcheli.*;
import mcheli.wrapper.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class MCH_Flare
{
    public final World worldObj;
    public final MCH_EntityAircraft aircraft;
    public final Random rand;
    public int numFlare;
    public int tick;
    private int flareType;
    private static FlareParam[] FLARE_DATA;
    
    public MCH_Flare(final World w, final MCH_EntityAircraft ac) {
        this.worldObj = w;
        this.aircraft = ac;
        this.rand = new Random();
        this.tick = 0;
        this.numFlare = 0;
        this.flareType = 0;
        if (MCH_Flare.FLARE_DATA == null) {
            final int delay = w.field_72995_K ? 50 : 0;
            (MCH_Flare.FLARE_DATA = new FlareParam[11])[1] = new FlareParam(1, 3, 200 + delay, 100, 16);
            MCH_Flare.FLARE_DATA[2] = new FlareParam(3, 5, 300 + delay, 200, 16);
            MCH_Flare.FLARE_DATA[3] = new FlareParam(2, 3, 200 + delay, 100, 16);
            MCH_Flare.FLARE_DATA[4] = new FlareParam(1, 3, 200 + delay, 100, 16);
            MCH_Flare.FLARE_DATA[5] = new FlareParam(2, 3, 200 + delay, 100, 16);
            MCH_Flare.FLARE_DATA[10] = new FlareParam(8, 1, 250 + delay, 60, 1);
            MCH_Flare.FLARE_DATA[0] = MCH_Flare.FLARE_DATA[1];
            MCH_Flare.FLARE_DATA[6] = MCH_Flare.FLARE_DATA[1];
            MCH_Flare.FLARE_DATA[7] = MCH_Flare.FLARE_DATA[1];
            MCH_Flare.FLARE_DATA[8] = MCH_Flare.FLARE_DATA[1];
            MCH_Flare.FLARE_DATA[9] = MCH_Flare.FLARE_DATA[1];
        }
    }
    
    public boolean isInPreparation() {
        return this.tick != 0;
    }
    
    public boolean isUsing() {
        final int type = this.getFlareType();
        return this.tick != 0 && type < MCH_Flare.FLARE_DATA.length && this.tick > MCH_Flare.FLARE_DATA[type].tickWait - MCH_Flare.FLARE_DATA[type].tickEnable;
    }
    
    public int getFlareType() {
        return this.flareType;
    }
    
    public void spawnParticle(final String name, final int num, final float size) {
        if (this.worldObj.field_72995_K) {
            if (name.isEmpty() || num < 1 || num > 50) {
                return;
            }
            final double x = (this.aircraft.field_70165_t - this.aircraft.field_70169_q) / num;
            final double y = (this.aircraft.field_70163_u - this.aircraft.field_70167_r) / num;
            final double z = (this.aircraft.field_70161_v - this.aircraft.field_70166_s) / num;
            for (int i = 0; i < num; ++i) {
                final MCH_ParticleParam prm = new MCH_ParticleParam(this.worldObj, "smoke", this.aircraft.field_70169_q + x * i, this.aircraft.field_70167_r + y * i, this.aircraft.field_70166_s + z * i);
                prm.size = size + this.rand.nextFloat();
                MCH_ParticlesUtil.spawnParticle(prm);
            }
        }
    }
    
    public boolean use(final int type) {
        boolean result = false;
        MCH_Lib.DbgLog(this.aircraft.field_70170_p, "MCH_Flare.use type = %d", type);
        this.flareType = type;
        if (type <= 0 && type >= MCH_Flare.FLARE_DATA.length) {
            return false;
        }
        if (this.worldObj.field_72995_K) {
            if (this.tick == 0) {
                this.tick = MCH_Flare.FLARE_DATA[this.getFlareType()].tickWait;
                result = true;
                this.numFlare = 0;
                W_McClient.DEF_playSoundFX("random.click", 1.0f, 1.0f);
            }
        }
        else {
            result = true;
            this.numFlare = 0;
            this.tick = MCH_Flare.FLARE_DATA[this.getFlareType()].tickWait;
            this.aircraft.getEntityData().func_74757_a("FlareUsing", true);
        }
        return result;
    }
    
    public void update() {
        final int type = this.getFlareType();
        if (this.aircraft == null || this.aircraft.field_70128_L || type <= 0 || type > MCH_Flare.FLARE_DATA.length) {
            return;
        }
        if (this.tick > 0) {
            --this.tick;
        }
        if (!this.worldObj.field_72995_K && this.tick > 0 && this.tick % MCH_Flare.FLARE_DATA[type].interval == 0 && this.numFlare < MCH_Flare.FLARE_DATA[type].numFlareMax) {
            Vec3 v = this.aircraft.getAcInfo().flare.pos;
            v = this.aircraft.getTransformedPosition(v.field_72450_a, v.field_72448_b, v.field_72449_c, this.aircraft.field_70169_q, this.aircraft.field_70167_r, this.aircraft.field_70166_s);
            this.spawnFlare(v);
        }
        if (!this.isUsing() && this.aircraft.getEntityData().func_74767_n("FlareUsing")) {
            this.aircraft.getEntityData().func_74757_a("FlareUsing", false);
        }
    }
    
    private void spawnFlare(final Vec3 v) {
        ++this.numFlare;
        final int type = this.getFlareType();
        final int num = MCH_Flare.FLARE_DATA[type].num;
        double x = v.field_72450_a - this.aircraft.field_70159_w * 2.0;
        double y = v.field_72448_b - this.aircraft.field_70181_x * 2.0 - 1.0;
        double z = v.field_72449_c - this.aircraft.field_70179_y * 2.0;
        this.worldObj.func_72889_a((EntityPlayer)null, 1004, (int)x, (int)y, (int)z, 0);
        for (int i = 0; i < num; ++i) {
            x = v.field_72450_a - this.aircraft.field_70159_w * 2.0;
            y = v.field_72448_b - this.aircraft.field_70181_x * 2.0 - 1.0;
            z = v.field_72449_c - this.aircraft.field_70179_y * 2.0;
            double tx = 0.0;
            double ty = this.aircraft.field_70181_x;
            double tz = 0.0;
            int fuseCount = 0;
            double r = this.aircraft.field_70177_z;
            if (type == 1) {
                tx = MathHelper.func_76126_a(this.rand.nextFloat() * 360.0f);
                tz = MathHelper.func_76134_b(this.rand.nextFloat() * 360.0f);
            }
            else if (type == 2 || type == 3) {
                if (i == 0) {
                    r += 90.0;
                }
                if (i == 1) {
                    r -= 90.0;
                }
                if (i == 2) {
                    r += 180.0;
                }
                r *= 0.017453292519943295;
                tx = -Math.sin(r) + (this.rand.nextFloat() - 0.5) * 0.6;
                tz = Math.cos(r) + (this.rand.nextFloat() - 0.5) * 0.6;
            }
            else if (type == 4) {
                r *= 0.017453292519943295;
                tx = -Math.sin(r) + (this.rand.nextFloat() - 0.5) * 1.3;
                tz = Math.cos(r) + (this.rand.nextFloat() - 0.5) * 1.3;
            }
            else if (type == 5) {
                r *= 0.017453292519943295;
                tx = -Math.sin(r) + (this.rand.nextFloat() - 0.5) * 0.9;
                tz = Math.cos(r) + (this.rand.nextFloat() - 0.5) * 0.9;
                tx *= 0.3;
                tz *= 0.3;
            }
            tx += this.aircraft.field_70159_w;
            ty += this.aircraft.field_70181_x / 2.0;
            tz += this.aircraft.field_70179_y;
            if (type == 10) {
                r += 360 / num / 2 + i * (360 / num);
                r *= 0.017453292519943295;
                tx = -Math.sin(r) * 2.0;
                tz = Math.cos(r) * 2.0;
                ty = 0.7;
                y += 2.0;
                fuseCount = 10;
            }
            final MCH_EntityFlare e = new MCH_EntityFlare(this.worldObj, x, y, z, tx * 0.5, ty * 0.5, tz * 0.5, 6.0f, fuseCount);
            e.field_70125_A = this.rand.nextFloat() * 360.0f;
            e.field_70177_z = this.rand.nextFloat() * 360.0f;
            e.field_70127_C = this.rand.nextFloat() * 360.0f;
            e.field_70126_B = this.rand.nextFloat() * 360.0f;
            if (type == 4) {
                final MCH_EntityFlare mch_EntityFlare = e;
                mch_EntityFlare.gravity *= 0.6;
                e.airResistance = 0.995;
            }
            this.worldObj.func_72838_d((Entity)e);
        }
    }
    
    static {
        MCH_Flare.FLARE_DATA = null;
    }
    
    private class FlareParam
    {
        public final int num;
        public final int interval;
        public final int tickWait;
        public final int tickEnable;
        public final int numFlareMax;
        
        public FlareParam(final int num, final int interval, final int tickWait, final int tickEnable, final int numFlareMax) {
            this.num = num;
            this.interval = interval;
            this.tickWait = tickWait;
            this.tickEnable = tickEnable;
            this.numFlareMax = numFlareMax;
        }
    }
}
