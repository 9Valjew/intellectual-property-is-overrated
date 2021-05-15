package mcheli.particles;

import net.minecraft.scoreboard.*;
import net.minecraft.world.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import mcheli.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import mcheli.wrapper.*;
import net.minecraft.util.*;
import mcheli.multiplay.*;
import net.minecraft.entity.*;

public class MCH_EntityParticleMarkPoint extends MCH_EntityParticleBase
{
    final Team taem;
    
    public MCH_EntityParticleMarkPoint(final World par1World, final double x, final double y, final double z, final Team team) {
        super(par1World, x, y, z, 0.0, 0.0, 0.0);
        this.setParticleMaxAge(30);
        this.taem = team;
    }
    
    public void func_70071_h_() {
        this.field_70169_q = this.field_70165_t;
        this.field_70167_r = this.field_70163_u;
        this.field_70166_s = this.field_70161_v;
        final EntityPlayer player = (EntityPlayer)Minecraft.func_71410_x().field_71439_g;
        if (player == null) {
            this.func_70106_y();
        }
        else if (player.func_96124_cp() == null && this.taem != null) {
            this.func_70106_y();
        }
        else if (player.func_96124_cp() != null && !player.func_142012_a(this.taem)) {
            this.func_70106_y();
        }
    }
    
    public void func_70106_y() {
        super.func_70106_y();
        MCH_Lib.DbgLog(true, "MCH_EntityParticleMarkPoint.setDead : " + this, new Object[0]);
    }
    
    @Override
    public int func_70537_b() {
        return 3;
    }
    
    public void func_70539_a(final Tessellator par1Tessellator, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        GL11.glPushMatrix();
        final Minecraft mc = Minecraft.func_71410_x();
        final EntityPlayer player = (EntityPlayer)mc.field_71439_g;
        if (player == null) {
            return;
        }
        double ix = MCH_EntityParticleMarkPoint.field_70556_an;
        double iy = MCH_EntityParticleMarkPoint.field_70554_ao;
        double iz = MCH_EntityParticleMarkPoint.field_70555_ap;
        if (mc.field_71474_y.field_74320_O > 0 && mc.field_71451_h != null) {
            final Entity viewer = (Entity)mc.field_71451_h;
            final double dist = W_Reflection.getThirdPersonDistance();
            final float yaw = (mc.field_71474_y.field_74320_O != 2) ? (-viewer.field_70177_z) : (-viewer.field_70177_z);
            final float pitch = (mc.field_71474_y.field_74320_O != 2) ? (-viewer.field_70125_A) : (-viewer.field_70125_A);
            final Vec3 v = MCH_Lib.RotVec3(0.0, 0.0, -dist, yaw, pitch);
            if (mc.field_71474_y.field_74320_O == 2) {
                v.field_72450_a = -v.field_72450_a;
                v.field_72448_b = -v.field_72448_b;
                v.field_72449_c = -v.field_72449_c;
            }
            final Vec3 vs = Vec3.func_72443_a(viewer.field_70165_t, viewer.field_70163_u + viewer.func_70047_e(), viewer.field_70161_v);
            final MovingObjectPosition mop = mc.field_71451_h.field_70170_p.func_72933_a(vs.func_72441_c(0.0, 0.0, 0.0), vs.func_72441_c(v.field_72450_a, v.field_72448_b, v.field_72449_c));
            double block_dist = dist;
            if (mop != null && mop.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK) {
                block_dist = vs.func_72438_d(mop.field_72307_f) - 0.4;
                if (block_dist < 0.0) {
                    block_dist = 0.0;
                }
            }
            GL11.glTranslated(v.field_72450_a * (block_dist / dist), v.field_72448_b * (block_dist / dist), v.field_72449_c * (block_dist / dist));
            ix += v.field_72450_a * (block_dist / dist);
            iy += v.field_72448_b * (block_dist / dist);
            iz += v.field_72449_c * (block_dist / dist);
        }
        final double px = (float)(this.field_70169_q + (this.field_70165_t - this.field_70169_q) * par2 - ix);
        final double py = (float)(this.field_70167_r + (this.field_70163_u - this.field_70167_r) * par2 - iy);
        final double pz = (float)(this.field_70166_s + (this.field_70161_v - this.field_70166_s) * par2 - iz);
        double scale = Math.sqrt(px * px + py * py + pz * pz) / 10.0;
        if (scale < 1.0) {
            scale = 1.0;
        }
        MCH_GuiTargetMarker.addMarkEntityPos(100, (Entity)this, px / scale, py / scale, pz / scale, false);
        GL11.glPopMatrix();
    }
}
