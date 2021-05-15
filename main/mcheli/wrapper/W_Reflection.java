package mcheli.wrapper;

import net.minecraft.client.renderer.entity.*;
import cpw.mods.fml.common.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.server.*;
import net.minecraft.network.*;
import java.util.*;

public class W_Reflection
{
    public static RenderManager getRenderManager(final Render render) {
        try {
            return (RenderManager)ObfuscationReflectionHelper.getPrivateValue((Class)Render.class, (Object)render, new String[] { "field_76990_c", "renderManager" });
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void restoreDefaultThirdPersonDistance() {
        setThirdPersonDistance(4.0f);
    }
    
    public static void setThirdPersonDistance(final float dist) {
        if (dist < 0.1) {
            return;
        }
        try {
            final Minecraft mc = Minecraft.func_71410_x();
            ObfuscationReflectionHelper.setPrivateValue((Class)EntityRenderer.class, (Object)mc.field_71460_t, (Object)dist, new String[] { "field_78490_B", "thirdPersonDistance" });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static float getThirdPersonDistance() {
        try {
            final Minecraft mc = Minecraft.func_71410_x();
            return (float)ObfuscationReflectionHelper.getPrivateValue((Class)EntityRenderer.class, (Object)mc.field_71460_t, new String[] { "field_78490_B", "thirdPersonDistance" });
        }
        catch (Exception e) {
            e.printStackTrace();
            return 4.0f;
        }
    }
    
    public static void setCameraRoll(float roll) {
        try {
            roll = MathHelper.func_76142_g(roll);
            final Minecraft mc = Minecraft.func_71410_x();
            ObfuscationReflectionHelper.setPrivateValue((Class)EntityRenderer.class, (Object)Minecraft.func_71410_x().field_71460_t, (Object)roll, new String[] { "field_78495_O", "camRoll" });
            ObfuscationReflectionHelper.setPrivateValue((Class)EntityRenderer.class, (Object)Minecraft.func_71410_x().field_71460_t, (Object)roll, new String[] { "field_78505_P", "prevCamRoll" });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static float getPrevCameraRoll() {
        try {
            final Minecraft mc = Minecraft.func_71410_x();
            return (float)ObfuscationReflectionHelper.getPrivateValue((Class)EntityRenderer.class, (Object)Minecraft.func_71410_x().field_71460_t, new String[] { "field_78505_P", "prevCamRoll" });
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0.0f;
        }
    }
    
    public static void restoreCameraZoom() {
        setCameraZoom(1.0f);
    }
    
    public static void setCameraZoom(final float zoom) {
        try {
            final Minecraft mc = Minecraft.func_71410_x();
            ObfuscationReflectionHelper.setPrivateValue((Class)EntityRenderer.class, (Object)mc.field_71460_t, (Object)zoom, new String[] { "field_78503_V", "cameraZoom" });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void setItemRenderer(final ItemRenderer r) {
        try {
            final Minecraft mc = Minecraft.func_71410_x();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void setCreativeDigSpeed(final int n) {
        try {
            final Minecraft mc = Minecraft.func_71410_x();
            ObfuscationReflectionHelper.setPrivateValue((Class)PlayerControllerMP.class, (Object)mc.field_71442_b, (Object)n, new String[] { "field_78781_i", "blockHitDelay" });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static ItemRenderer getItemRenderer() {
        return Minecraft.func_71410_x().field_71460_t.field_78516_c;
    }
    
    public static void setItemRenderer_ItemToRender(final ItemStack itemToRender) {
        try {
            ObfuscationReflectionHelper.setPrivateValue((Class)ItemRenderer.class, (Object)getItemRenderer(), (Object)itemToRender, new String[] { "field_78453_b", "itemToRender" });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static ItemStack getItemRenderer_ItemToRender() {
        try {
            final ItemStack itemstack = (ItemStack)ObfuscationReflectionHelper.getPrivateValue((Class)ItemRenderer.class, (Object)getItemRenderer(), new String[] { "field_78453_b", "itemToRender" });
            return itemstack;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void setItemRendererProgress(final float equippedProgress) {
        try {
            ObfuscationReflectionHelper.setPrivateValue((Class)ItemRenderer.class, (Object)getItemRenderer(), (Object)equippedProgress, new String[] { "field_78454_c", "equippedProgress" });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void setBoundingBox(final Entity entity, final AxisAlignedBB bb) {
        try {
            ObfuscationReflectionHelper.setPrivateValue((Class)Entity.class, (Object)entity, (Object)bb, new String[] { "field_70121_D", "boundingBox" });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static List getNetworkManagers() {
        try {
            final List list = (List)ObfuscationReflectionHelper.getPrivateValue((Class)NetworkSystem.class, (Object)MinecraftServer.func_71276_C().func_147137_ag(), new String[] { "field_151272_f", "networkManagers" });
            return list;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Queue getReceivedPacketsQueue(final NetworkManager nm) {
        try {
            final Queue queue = (Queue)ObfuscationReflectionHelper.getPrivateValue((Class)NetworkManager.class, (Object)nm, new String[] { "field_150748_i", "receivedPacketsQueue" });
            return queue;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Queue getSendPacketsQueue(final NetworkManager nm) {
        try {
            final Queue queue = (Queue)ObfuscationReflectionHelper.getPrivateValue((Class)NetworkManager.class, (Object)nm, new String[] { "field_150745_j", "outboundPacketsQueue" });
            return queue;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
