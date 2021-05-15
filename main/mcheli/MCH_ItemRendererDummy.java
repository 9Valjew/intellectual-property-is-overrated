package mcheli;

import net.minecraft.client.renderer.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.client.*;
import mcheli.aircraft.*;
import mcheli.uav.*;
import mcheli.gltd.*;
import mcheli.wrapper.*;

@SideOnly(Side.CLIENT)
public class MCH_ItemRendererDummy extends ItemRenderer
{
    protected static Minecraft mc;
    protected static ItemRenderer backupItemRenderer;
    protected static MCH_ItemRendererDummy instance;
    
    public MCH_ItemRendererDummy(final Minecraft par1Minecraft) {
        super(par1Minecraft);
        MCH_ItemRendererDummy.mc = par1Minecraft;
    }
    
    public void func_78440_a(final float par1) {
        if (MCH_ItemRendererDummy.mc.field_71439_g == null) {
            super.func_78440_a(par1);
        }
        else if (!(MCH_ItemRendererDummy.mc.field_71439_g.field_70154_o instanceof MCH_EntityAircraft) && !(MCH_ItemRendererDummy.mc.field_71439_g.field_70154_o instanceof MCH_EntityUavStation)) {
            if (!(MCH_ItemRendererDummy.mc.field_71439_g.field_70154_o instanceof MCH_EntityGLTD)) {
                super.func_78440_a(par1);
            }
        }
    }
    
    public static void enableDummyItemRenderer() {
        if (MCH_ItemRendererDummy.instance == null) {
            MCH_ItemRendererDummy.instance = new MCH_ItemRendererDummy(Minecraft.func_71410_x());
        }
        if (!(MCH_ItemRendererDummy.mc.field_71460_t.field_78516_c instanceof MCH_ItemRendererDummy)) {
            MCH_ItemRendererDummy.backupItemRenderer = MCH_ItemRendererDummy.mc.field_71460_t.field_78516_c;
        }
        W_EntityRenderer.setItemRenderer(MCH_ItemRendererDummy.mc, MCH_ItemRendererDummy.instance);
    }
    
    public static void disableDummyItemRenderer() {
        if (MCH_ItemRendererDummy.backupItemRenderer != null) {
            W_EntityRenderer.setItemRenderer(MCH_ItemRendererDummy.mc, MCH_ItemRendererDummy.backupItemRenderer);
        }
    }
}
