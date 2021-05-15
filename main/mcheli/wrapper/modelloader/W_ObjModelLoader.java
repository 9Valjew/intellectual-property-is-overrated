package mcheli.wrapper.modelloader;

import net.minecraft.util.*;
import net.minecraftforge.client.model.*;
import java.net.*;

public class W_ObjModelLoader implements IModelCustomLoader
{
    private static final String[] types;
    
    public String getType() {
        return "OBJ model";
    }
    
    public String[] getSuffixes() {
        return W_ObjModelLoader.types;
    }
    
    public IModelCustom loadInstance(final ResourceLocation resource) throws ModelFormatException {
        return (IModelCustom)new W_WavefrontObject(resource);
    }
    
    public IModelCustom loadInstance(final String resourceName, final URL resource) throws ModelFormatException {
        return (IModelCustom)new W_WavefrontObject(resourceName, resource);
    }
    
    static {
        types = new String[] { "obj" };
    }
}
