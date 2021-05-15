package mcheli.weapon;

import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.*;
import net.minecraft.client.*;
import mcheli.aircraft.*;
import mcheli.uav.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderTvMissile extends MCH_RenderBulletBase
{
    public MCH_RenderTvMissile() {
        this.field_76989_e = 0.5f;
    }
    
    @Override
    public void renderBullet(final Entity entity, final double posX, final double posY, final double posZ, final float par8, final float par9) {
        MCH_EntityAircraft ac = null;
        final Entity ridingEntity = Minecraft.func_71410_x().field_71439_g.field_70154_o;
        if (ridingEntity instanceof MCH_EntityAircraft) {
            ac = (MCH_EntityAircraft)ridingEntity;
        }
        else if (ridingEntity instanceof MCH_EntitySeat) {
            ac = ((MCH_EntitySeat)ridingEntity).getParent();
        }
        else if (ridingEntity instanceof MCH_EntityUavStation) {
            ac = ((MCH_EntityUavStation)ridingEntity).getControlAircract();
        }
        if (ac != null && !ac.isRenderBullet(entity, (Entity)Minecraft.func_71410_x().field_71439_g)) {
            return;
        }
        if (entity instanceof MCH_EntityBaseBullet) {
            final MCH_EntityBaseBullet bullet = (MCH_EntityBaseBullet)entity;
            GL11.glPushMatrix();
            GL11.glTranslated(posX, posY, posZ);
            GL11.glRotatef(-entity.field_70177_z, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-entity.field_70125_A, -1.0f, 0.0f, 0.0f);
            this.renderModel(bullet);
            GL11.glPopMatrix();
        }
    }
    
    @Override
    protected ResourceLocation func_110775_a(final Entity entity) {
        return MCH_RenderTvMissile.TEX_DEFAULT;
    }
}
