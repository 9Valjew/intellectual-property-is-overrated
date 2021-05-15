package mcheli;

public class MCH_Color
{
    public float a;
    public float r;
    public float g;
    public float b;
    
    public MCH_Color(final float aa, final float rr, final float gg, final float bb) {
        this.a = this.round(aa);
        this.r = this.round(rr);
        this.g = this.round(gg);
        this.b = this.round(bb);
    }
    
    public MCH_Color(final float rr, final float gg, final float bb) {
        this(1.0f, rr, gg, bb);
    }
    
    public MCH_Color() {
        this(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public float round(final float f) {
        return (f < 0.0f) ? 0.0f : ((f > 1.0f) ? 1.0f : f);
    }
}
