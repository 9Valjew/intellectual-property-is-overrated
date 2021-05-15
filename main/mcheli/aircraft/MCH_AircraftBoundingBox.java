package mcheli.aircraft;

import net.minecraft.util.*;

public class MCH_AircraftBoundingBox extends AxisAlignedBB
{
    private final MCH_EntityAircraft ac;
    
    protected MCH_AircraftBoundingBox(final MCH_EntityAircraft ac) {
        super(ac.field_70121_D.field_72340_a, ac.field_70121_D.field_72338_b, ac.field_70121_D.field_72339_c, ac.field_70121_D.field_72336_d, ac.field_70121_D.field_72337_e, ac.field_70121_D.field_72334_f);
        this.ac = ac;
    }
    
    public AxisAlignedBB NewAABB(final double p_72324_1_, final double p_72324_3_, final double p_72324_5_, final double p_72324_7_, final double p_72324_9_, final double p_72324_11_) {
        return new MCH_AircraftBoundingBox(this.ac).func_72324_b(p_72324_1_, p_72324_3_, p_72324_5_, p_72324_7_, p_72324_9_, p_72324_11_);
    }
    
    public double getDistSq(final AxisAlignedBB a1, final AxisAlignedBB a2) {
        final double x1 = (a1.field_72336_d + a1.field_72340_a) / 2.0;
        final double y1 = (a1.field_72337_e + a1.field_72338_b) / 2.0;
        final double z1 = (a1.field_72334_f + a1.field_72339_c) / 2.0;
        final double x2 = (a2.field_72336_d + a2.field_72340_a) / 2.0;
        final double y2 = (a2.field_72337_e + a2.field_72338_b) / 2.0;
        final double z2 = (a2.field_72334_f + a2.field_72339_c) / 2.0;
        final double dx = x1 - x2;
        final double dy = y1 - y2;
        final double dz = z1 - z2;
        return dx * dx + dy * dy + dz * dz;
    }
    
    public boolean func_72326_a(final AxisAlignedBB aabb) {
        boolean ret = false;
        double dist = 1.0E7;
        this.ac.lastBBDamageFactor = 1.0f;
        if (super.func_72326_a(aabb)) {
            dist = this.getDistSq(aabb, this);
            ret = true;
        }
        for (final MCH_BoundingBox bb : this.ac.extraBoundingBox) {
            if (bb.boundingBox.func_72326_a(aabb)) {
                final double dist2 = this.getDistSq(aabb, this);
                if (dist2 < dist) {
                    dist = dist2;
                    this.ac.lastBBDamageFactor = bb.damegeFactor;
                }
                ret = true;
            }
        }
        return ret;
    }
    
    public AxisAlignedBB func_72314_b(final double p_72314_1_, final double p_72314_3_, final double p_72314_5_) {
        final double d3 = this.field_72340_a - p_72314_1_;
        final double d4 = this.field_72338_b - p_72314_3_;
        final double d5 = this.field_72339_c - p_72314_5_;
        final double d6 = this.field_72336_d + p_72314_1_;
        final double d7 = this.field_72337_e + p_72314_3_;
        final double d8 = this.field_72334_f + p_72314_5_;
        return this.NewAABB(d3, d4, d5, d6, d7, d8);
    }
    
    public AxisAlignedBB func_111270_a(final AxisAlignedBB p_111270_1_) {
        final double d0 = Math.min(this.field_72340_a, p_111270_1_.field_72340_a);
        final double d2 = Math.min(this.field_72338_b, p_111270_1_.field_72338_b);
        final double d3 = Math.min(this.field_72339_c, p_111270_1_.field_72339_c);
        final double d4 = Math.max(this.field_72336_d, p_111270_1_.field_72336_d);
        final double d5 = Math.max(this.field_72337_e, p_111270_1_.field_72337_e);
        final double d6 = Math.max(this.field_72334_f, p_111270_1_.field_72334_f);
        return this.NewAABB(d0, d2, d3, d4, d5, d6);
    }
    
    public AxisAlignedBB func_72321_a(final double p_72321_1_, final double p_72321_3_, final double p_72321_5_) {
        double d3 = this.field_72340_a;
        double d4 = this.field_72338_b;
        double d5 = this.field_72339_c;
        double d6 = this.field_72336_d;
        double d7 = this.field_72337_e;
        double d8 = this.field_72334_f;
        if (p_72321_1_ < 0.0) {
            d3 += p_72321_1_;
        }
        if (p_72321_1_ > 0.0) {
            d6 += p_72321_1_;
        }
        if (p_72321_3_ < 0.0) {
            d4 += p_72321_3_;
        }
        if (p_72321_3_ > 0.0) {
            d7 += p_72321_3_;
        }
        if (p_72321_5_ < 0.0) {
            d5 += p_72321_5_;
        }
        if (p_72321_5_ > 0.0) {
            d8 += p_72321_5_;
        }
        return this.NewAABB(d3, d4, d5, d6, d7, d8);
    }
    
    public AxisAlignedBB func_72331_e(final double p_72331_1_, final double p_72331_3_, final double p_72331_5_) {
        final double d3 = this.field_72340_a + p_72331_1_;
        final double d4 = this.field_72338_b + p_72331_3_;
        final double d5 = this.field_72339_c + p_72331_5_;
        final double d6 = this.field_72336_d - p_72331_1_;
        final double d7 = this.field_72337_e - p_72331_3_;
        final double d8 = this.field_72334_f - p_72331_5_;
        return this.NewAABB(d3, d4, d5, d6, d7, d8);
    }
    
    public AxisAlignedBB func_72329_c() {
        return this.NewAABB(this.field_72340_a, this.field_72338_b, this.field_72339_c, this.field_72336_d, this.field_72337_e, this.field_72334_f);
    }
    
    public AxisAlignedBB func_72325_c(final double x, final double y, final double z) {
        return this.NewAABB(this.field_72340_a + x, this.field_72338_b + y, this.field_72339_c + z, this.field_72336_d + x, this.field_72337_e + y, this.field_72334_f + z);
    }
    
    public MovingObjectPosition func_72327_a(final Vec3 v1, final Vec3 v2) {
        this.ac.lastBBDamageFactor = 1.0f;
        MovingObjectPosition mop = super.func_72327_a(v1, v2);
        double dist = 1.0E7;
        if (mop != null) {
            dist = v1.func_72438_d(mop.field_72307_f);
        }
        for (final MCH_BoundingBox bb : this.ac.extraBoundingBox) {
            final MovingObjectPosition mop2 = bb.boundingBox.func_72327_a(v1, v2);
            if (mop2 != null) {
                final double dist2 = v1.func_72438_d(mop2.field_72307_f);
                if (dist2 < dist) {
                    mop = mop2;
                    dist = dist2;
                    this.ac.lastBBDamageFactor = bb.damegeFactor;
                }
            }
        }
        return mop;
    }
}
