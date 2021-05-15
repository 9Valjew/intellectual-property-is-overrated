package mcheli.wrapper;

import net.minecraft.client.model.*;
import net.minecraft.util.*;
import cpw.mods.fml.common.*;
import net.minecraftforge.client.model.*;
import mcheli.wrapper.modelloader.*;

public abstract class W_ModelBase extends ModelBase
{
    private static IModelCustomLoader objLoader;
    private static IModelCustomLoader mqoLoader;
    
    public static IModelCustom loadModel(final String name) throws IllegalArgumentException, ModelFormatException {
        final ResourceLocation resource = new ResourceLocation("mcheli", name);
        final String path = resource.func_110623_a();
        final int i = path.lastIndexOf(46);
        if (i == -1) {
            FMLLog.severe("The resource name %s is not valid", new Object[] { resource });
            throw new IllegalArgumentException("The resource name is not valid");
        }
        final String test = path.substring(i);
        if (path.substring(i).equalsIgnoreCase(".mqo")) {
            return W_ModelBase.mqoLoader.loadInstance(resource);
        }
        if (path.substring(i).equalsIgnoreCase(".obj")) {
            return W_ModelBase.objLoader.loadInstance(resource);
        }
        return AdvancedModelLoader.loadModel(resource);
    }
    
    static {
        W_ModelBase.objLoader = (IModelCustomLoader)new W_ObjModelLoader();
        W_ModelBase.mqoLoader = (IModelCustomLoader)new W_MqoModelLoader();
    }
}
