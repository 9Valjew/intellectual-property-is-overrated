package mcheli.chain;

import mcheli.wrapper.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.entity.*;
import mcheli.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderChain extends W_Render
{
    public void func_76986_a(final Entity e, final double posX, final double posY, final double posZ, final float par8, final float par9) {
        if (!(e instanceof MCH_EntityChain)) {
            return;
        }
        final MCH_EntityChain chain = (MCH_EntityChain)e;
        if (chain.towedEntity == null || chain.towEntity == null) {
            return;
        }
        GL11.glPushMatrix();
        GL11.glEnable(2884);
        GL11.glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
        final double field_70142_S = chain.towedEntity.field_70142_S;
        final RenderManager field_78727_a = RenderManager.field_78727_a;
        final double n = field_70142_S - RenderManager.field_78725_b;
        final double field_70137_T = chain.towedEntity.field_70137_T;
        final RenderManager field_78727_a2 = RenderManager.field_78727_a;
        final double n2 = field_70137_T - RenderManager.field_78726_c;
        final double field_70136_U = chain.towedEntity.field_70136_U;
        final RenderManager field_78727_a3 = RenderManager.field_78727_a;
        GL11.glTranslated(n, n2, field_70136_U - RenderManager.field_78723_d);
        this.bindTexture("textures/chain.png");
        final double dx = chain.towEntity.field_70142_S - chain.towedEntity.field_70142_S;
        final double dy = chain.towEntity.field_70137_T - chain.towedEntity.field_70137_T;
        final double dz = chain.towEntity.field_70136_U - chain.towedEntity.field_70136_U;
        double diff = Math.sqrt(dx * dx + dy * dy + dz * dz);
        final float CHAIN_LEN = 0.95f;
        final double x = dx * 0.949999988079071 / diff;
        final double y = dy * 0.949999988079071 / diff;
        final double z = dz * 0.949999988079071 / diff;
        while (diff > 0.949999988079071) {
            GL11.glTranslated(x, y, z);
            GL11.glPushMatrix();
            final Vec3 v = MCH_Lib.getYawPitchFromVec(x, y, z);
            GL11.glRotatef((float)v.field_72448_b, 0.0f, -1.0f, 0.0f);
            GL11.glRotatef((float)v.field_72449_c, 0.0f, 0.0f, 1.0f);
            MCH_ModelManager.render("chain");
            GL11.glPopMatrix();
            diff -= 0.949999988079071;
        }
        GL11.glPopMatrix();
    }
    
    @Override
    protected ResourceLocation func_110775_a(final Entity entity) {
        return MCH_RenderChain.TEX_DEFAULT;
    }
}
