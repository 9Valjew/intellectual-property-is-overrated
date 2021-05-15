package mcheli;

import mcheli.aircraft.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;

@SideOnly(Side.CLIENT)
public class MCH_GuiCommon extends MCH_AircraftCommonGui
{
    public int hitCount;
    
    public MCH_GuiCommon(final Minecraft minecraft) {
        super(minecraft);
        this.hitCount = 0;
    }
    
    @Override
    public boolean isDrawGui(final EntityPlayer player) {
        return true;
    }
    
    @Override
    public void drawGui(final EntityPlayer player, final boolean isThirdPersonView) {
        GL11.glLineWidth((float)MCH_GuiCommon.scaleFactor);
        this.drawHitBullet(this.hitCount, 15, -805306369);
    }
    
    @Override
    public void onTick() {
        super.onTick();
        if (this.hitCount > 0) {
            --this.hitCount;
        }
    }
    
    public void hitBullet() {
        this.hitCount = 15;
    }
}
