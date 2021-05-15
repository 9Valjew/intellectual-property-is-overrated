package mcheli.weapon;

import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class MCH_RenderNone extends MCH_RenderBulletBase
{
    @Override
    public void renderBullet(final Entity entity, final double posX, final double posY, final double posZ, final float yaw, final float partialTickTime) {
    }
    
    @Override
    protected ResourceLocation func_110775_a(final Entity entity) {
        return MCH_RenderNone.TEX_DEFAULT;
    }
}
