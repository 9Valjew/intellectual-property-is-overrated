package mcheli.tank;

import net.minecraft.world.*;
import java.util.*;
import mcheli.aircraft.*;
import mcheli.*;
import mcheli.wrapper.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import mcheli.particles.*;

public class MCH_WheelManager
{
    public final MCH_EntityAircraft parent;
    public MCH_EntityWheel[] wheels;
    private double minZ;
    private double maxZ;
    private double avgZ;
    public Vec3 weightedCenter;
    public float targetPitch;
    public float targetRoll;
    public float prevYaw;
    private static Random rand;
    
    public MCH_WheelManager(final MCH_EntityAircraft ac) {
        this.parent = ac;
        this.wheels = new MCH_EntityWheel[0];
        this.weightedCenter = Vec3.func_72443_a(0.0, 0.0, 0.0);
    }
    
    public void createWheels(final World w, final List<MCH_AircraftInfo.Wheel> list, final Vec3 weightedCenter) {
        this.wheels = new MCH_EntityWheel[list.size() * 2];
        this.minZ = 999999.0;
        this.maxZ = -999999.0;
        this.weightedCenter = weightedCenter;
        for (int i = 0; i < this.wheels.length; ++i) {
            final MCH_EntityWheel wheel = new MCH_EntityWheel(w);
            wheel.setParents(this.parent);
            final Vec3 wp = list.get(i / 2).pos;
            wheel.setWheelPos(Vec3.func_72443_a((i % 2 == 0) ? wp.field_72450_a : (-wp.field_72450_a), wp.field_72448_b, wp.field_72449_c), this.weightedCenter);
            final Vec3 v = this.parent.getTransformedPosition(wheel.pos.field_72450_a, wheel.pos.field_72448_b, wheel.pos.field_72449_c);
            wheel.func_70012_b(v.field_72450_a, v.field_72448_b + 1.0, v.field_72449_c, 0.0f, 0.0f);
            this.wheels[i] = wheel;
            if (wheel.pos.field_72449_c <= this.minZ) {
                this.minZ = wheel.pos.field_72449_c;
            }
            if (wheel.pos.field_72449_c >= this.maxZ) {
                this.maxZ = wheel.pos.field_72449_c;
            }
        }
        this.avgZ = this.maxZ - this.minZ;
    }
    
    public void move(final double x, final double y, final double z) {
        final MCH_EntityAircraft ac = this.parent;
        if (ac.getAcInfo() == null) {
            return;
        }
        final boolean showLog = ac.field_70173_aa % 1 == 1;
        if (showLog) {
            MCH_Lib.DbgLog(ac.field_70170_p, "[" + (ac.field_70170_p.field_72995_K ? "Client" : "Server") + "] ==============================", new Object[0]);
        }
        for (final MCH_EntityWheel wheel : this.wheels) {
            wheel.field_70169_q = wheel.field_70165_t;
            wheel.field_70167_r = wheel.field_70163_u;
            wheel.field_70166_s = wheel.field_70161_v;
            final Vec3 v = ac.getTransformedPosition(wheel.pos.field_72450_a, wheel.pos.field_72448_b, wheel.pos.field_72449_c);
            wheel.field_70159_w = v.field_72450_a - wheel.field_70165_t + x;
            wheel.field_70181_x = v.field_72448_b - wheel.field_70163_u;
            wheel.field_70179_y = v.field_72449_c - wheel.field_70161_v + z;
        }
        for (final MCH_EntityWheel mch_EntityWheel : this.wheels) {
            final MCH_EntityWheel wheel = mch_EntityWheel;
            mch_EntityWheel.field_70181_x *= 0.15;
            wheel.func_70091_d(wheel.field_70159_w, wheel.field_70181_x, wheel.field_70179_y);
            final double f = 1.0;
            wheel.func_70091_d(0.0, -0.1 * f, 0.0);
        }
        int zmog = -1;
        for (int i = 0; i < this.wheels.length / 2; ++i) {
            zmog = i;
            final MCH_EntityWheel w1 = this.wheels[i * 2 + 0];
            final MCH_EntityWheel w2 = this.wheels[i * 2 + 1];
            if (!w1.isPlus && (w1.field_70122_E || w2.field_70122_E)) {
                zmog = -1;
                break;
            }
        }
        if (zmog >= 0) {
            this.wheels[zmog * 2 + 0].field_70122_E = true;
            this.wheels[zmog * 2 + 1].field_70122_E = true;
        }
        zmog = -1;
        for (int i = this.wheels.length / 2 - 1; i >= 0; --i) {
            zmog = i;
            final MCH_EntityWheel w1 = this.wheels[i * 2 + 0];
            final MCH_EntityWheel w2 = this.wheels[i * 2 + 1];
            if (w1.isPlus && (w1.field_70122_E || w2.field_70122_E)) {
                zmog = -1;
                break;
            }
        }
        if (zmog >= 0) {
            this.wheels[zmog * 2 + 0].field_70122_E = true;
            this.wheels[zmog * 2 + 1].field_70122_E = true;
        }
        Vec3 rv = Vec3.func_72443_a(0.0, 0.0, 0.0);
        final Vec3 transformedPosition;
        final Vec3 wc = transformedPosition = ac.getTransformedPosition(this.weightedCenter);
        transformedPosition.field_72450_a -= ac.field_70165_t;
        wc.field_72448_b = this.weightedCenter.field_72448_b;
        final Vec3 vec3 = wc;
        vec3.field_72449_c -= ac.field_70161_v;
        for (int j = 0; j < this.wheels.length / 2; ++j) {
            final MCH_EntityWheel w3 = this.wheels[j * 2 + 0];
            final MCH_EntityWheel w4 = this.wheels[j * 2 + 1];
            final Vec3 v2 = Vec3.func_72443_a(w3.field_70165_t - (ac.field_70165_t + wc.field_72450_a), w3.field_70163_u - (ac.field_70163_u + wc.field_72448_b), w3.field_70161_v - (ac.field_70161_v + wc.field_72449_c));
            final Vec3 v3 = Vec3.func_72443_a(w4.field_70165_t - (ac.field_70165_t + wc.field_72450_a), w4.field_70163_u - (ac.field_70163_u + wc.field_72448_b), w4.field_70161_v - (ac.field_70161_v + wc.field_72449_c));
            Vec3 v4 = (w3.pos.field_72449_c >= 0.0) ? v3.func_72431_c(v2) : v2.func_72431_c(v3);
            v4 = v4.func_72432_b();
            double f2 = Math.abs(w3.pos.field_72449_c / this.avgZ);
            if (!w3.field_70122_E && !w4.field_70122_E) {
                f2 = 0.0;
            }
            final Vec3 vec4 = rv;
            vec4.field_72450_a += v4.field_72450_a * f2;
            final Vec3 vec5 = rv;
            vec5.field_72448_b += v4.field_72448_b * f2;
            final Vec3 vec6 = rv;
            vec6.field_72449_c += v4.field_72449_c * f2;
            if (showLog) {
                v4.func_72442_b((float)(ac.getRotYaw() * 3.141592653589793 / 180.0));
                MCH_Lib.DbgLog(ac.field_70170_p, "%2d : %.2f :[%+.1f, %+.1f, %+.1f][%s %d %d][%+.2f(%+.2f), %+.2f(%+.2f)][%+.1f, %+.1f, %+.1f]", j, f2, v4.field_72450_a, v4.field_72448_b, v4.field_72449_c, w3.isPlus ? "+" : "-", (int)(w3.field_70122_E ? 1 : 0), (int)(w4.field_70122_E ? 1 : 0), w3.field_70163_u - w3.field_70167_r, w3.field_70181_x, w4.field_70163_u - w4.field_70167_r, w4.field_70181_x, v4.field_72450_a, v4.field_72448_b, v4.field_72449_c);
            }
        }
        rv = rv.func_72432_b();
        if (rv.field_72448_b > 0.01 && rv.field_72448_b < 0.7) {
            final MCH_EntityAircraft mch_EntityAircraft = ac;
            mch_EntityAircraft.field_70159_w += rv.field_72450_a / 50.0;
            final MCH_EntityAircraft mch_EntityAircraft2 = ac;
            mch_EntityAircraft2.field_70179_y += rv.field_72449_c / 50.0;
        }
        rv.func_72442_b((float)(ac.getRotYaw() * 3.141592653589793 / 180.0));
        float pitch = (float)(90.0 - Math.atan2(rv.field_72448_b, rv.field_72449_c) * 180.0 / 3.141592653589793);
        float roll = -(float)(90.0 - Math.atan2(rv.field_72448_b, rv.field_72450_a) * 180.0 / 3.141592653589793);
        final float ogpf = ac.getAcInfo().onGroundPitchFactor;
        if (pitch - ac.getRotPitch() > ogpf) {
            pitch = ac.getRotPitch() + ogpf;
        }
        if (pitch - ac.getRotPitch() < -ogpf) {
            pitch = ac.getRotPitch() - ogpf;
        }
        final float ogrf = ac.getAcInfo().onGroundRollFactor;
        if (roll - ac.getRotRoll() > ogrf) {
            roll = ac.getRotRoll() + ogrf;
        }
        if (roll - ac.getRotRoll() < -ogrf) {
            roll = ac.getRotRoll() - ogrf;
        }
        this.targetPitch = pitch;
        this.targetRoll = roll;
        if (!W_Lib.isClientPlayer(ac.getRiddenByEntity())) {
            ac.setRotPitch(pitch);
            ac.setRotRoll(roll);
        }
        if (showLog) {
            MCH_Lib.DbgLog(ac.field_70170_p, "%+03d, %+03d :[%.2f, %.2f, %.2f] yaw=%.2f, pitch=%.2f, roll=%.2f", (int)pitch, (int)roll, rv.field_72450_a, rv.field_72448_b, rv.field_72449_c, ac.getRotYaw(), this.targetPitch, this.targetRoll);
        }
        for (final MCH_EntityWheel wheel2 : this.wheels) {
            final Vec3 v5 = this.getTransformedPosition(wheel2.pos.field_72450_a, wheel2.pos.field_72448_b, wheel2.pos.field_72449_c, ac, ac.getRotYaw(), this.targetPitch, this.targetRoll);
            final double offset = wheel2.field_70122_E ? 0.01 : -0.0;
            final double rangeH = 2.0;
            final double poy = wheel2.field_70138_W / 2.0f;
            int b = 0;
            if (wheel2.field_70165_t > v5.field_72450_a + rangeH) {
                wheel2.field_70165_t = v5.field_72450_a + rangeH;
                wheel2.field_70163_u = v5.field_72448_b + poy;
                b |= 0x1;
            }
            if (wheel2.field_70165_t < v5.field_72450_a - rangeH) {
                wheel2.field_70165_t = v5.field_72450_a - rangeH;
                wheel2.field_70163_u = v5.field_72448_b + poy;
                b |= 0x2;
            }
            if (wheel2.field_70161_v > v5.field_72449_c + rangeH) {
                wheel2.field_70161_v = v5.field_72449_c + rangeH;
                wheel2.field_70163_u = v5.field_72448_b + poy;
                b |= 0x4;
            }
            if (wheel2.field_70161_v < v5.field_72449_c - rangeH) {
                wheel2.field_70161_v = v5.field_72449_c - rangeH;
                wheel2.field_70163_u = v5.field_72448_b + poy;
                b |= 0x8;
            }
            wheel2.func_70080_a(wheel2.field_70165_t, wheel2.field_70163_u, wheel2.field_70161_v, 0.0f, 0.0f);
        }
    }
    
    public Vec3 getTransformedPosition(final double x, final double y, final double z, final MCH_EntityAircraft ac, final float yaw, final float pitch, final float roll) {
        final Vec3 v = MCH_Lib.RotVec3(x, y, z, -yaw, -pitch, -roll);
        return v.func_72441_c(ac.field_70165_t, ac.field_70163_u, ac.field_70161_v);
    }
    
    public void updateBlock() {
        final MCH_Config config = MCH_MOD.config;
        if (!MCH_Config.Collision_DestroyBlock.prmBool) {
            return;
        }
        final MCH_EntityAircraft ac = this.parent;
        for (final MCH_EntityWheel w : this.wheels) {
            final Vec3 v = ac.getTransformedPosition(w.pos);
            final int x = (int)(v.field_72450_a + 0.5);
            final int y = (int)(v.field_72448_b + 0.5);
            final int z = (int)(v.field_72449_c + 0.5);
            final Block block = ac.field_70170_p.func_147439_a(x, y, z);
            if (block == W_Block.getSnowLayer()) {
                ac.field_70170_p.func_147468_f(x, y, z);
            }
            if (block == W_Blocks.field_150392_bi || block == W_Blocks.field_150414_aQ) {
                W_WorldFunc.destroyBlock(ac.field_70170_p, x, y, z, false);
            }
        }
    }
    
    public void particleLandingGear() {
        if (this.wheels.length <= 0) {
            return;
        }
        final MCH_EntityAircraft ac = this.parent;
        final double d = ac.field_70159_w * ac.field_70159_w + ac.field_70179_y * ac.field_70179_y + Math.abs(this.prevYaw - ac.getRotYaw());
        this.prevYaw = ac.getRotYaw();
        if (d > 0.001) {
            for (int i = 0; i < 2; ++i) {
                final MCH_EntityWheel w = this.wheels[MCH_WheelManager.rand.nextInt(this.wheels.length)];
                final Vec3 v = ac.getTransformedPosition(w.pos);
                final int x = MathHelper.func_76128_c(v.field_72450_a + 0.5);
                int y = MathHelper.func_76128_c(v.field_72448_b - 0.5);
                final int z = MathHelper.func_76128_c(v.field_72449_c + 0.5);
                Block block = ac.field_70170_p.func_147439_a(x, y, z);
                if (Block.func_149680_a(block, Blocks.field_150350_a)) {
                    y = MathHelper.func_76128_c(v.field_72448_b + 0.5);
                    block = ac.field_70170_p.func_147439_a(x, y, z);
                }
                if (!Block.func_149680_a(block, Blocks.field_150350_a)) {
                    MCH_ParticlesUtil.spawnParticleTileCrack(ac.field_70170_p, x, y, z, v.field_72450_a + (MCH_WheelManager.rand.nextFloat() - 0.5), v.field_72448_b + 0.1, v.field_72449_c + (MCH_WheelManager.rand.nextFloat() - 0.5), -ac.field_70159_w * 4.0 + (MCH_WheelManager.rand.nextFloat() - 0.5) * 0.1, MCH_WheelManager.rand.nextFloat() * 0.5, -ac.field_70179_y * 4.0 + (MCH_WheelManager.rand.nextFloat() - 0.5) * 0.1);
                }
            }
        }
    }
    
    static {
        MCH_WheelManager.rand = new Random();
    }
}
