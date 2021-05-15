package mcheli;

import cpw.mods.fml.relauncher.*;
import net.minecraftforge.client.model.*;
import net.minecraft.client.model.*;
import java.io.*;
import mcheli.wrapper.*;
import mcheli.wrapper.modelloader.*;
import java.util.*;

@SideOnly(Side.CLIENT)
public class MCH_ModelManager extends W_ModelBase
{
    private static MCH_ModelManager instance;
    private static HashMap<String, IModelCustom> map;
    private static ModelRenderer defaultModel;
    private static boolean forceReloadMode;
    private static Random rand;
    
    private MCH_ModelManager() {
        MCH_ModelManager.map = new HashMap<String, IModelCustom>();
        MCH_ModelManager.defaultModel = null;
        (MCH_ModelManager.defaultModel = new ModelRenderer((ModelBase)this, 0, 0)).func_78790_a(-5.0f, -5.0f, -5.0f, 10, 10, 10, 0.0f);
    }
    
    public static void setForceReloadMode(final boolean b) {
        MCH_ModelManager.forceReloadMode = b;
    }
    
    public static IModelCustom load(final String path, final String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        return load(path + "/" + name);
    }
    
    public static IModelCustom load(final String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        final IModelCustom obj = MCH_ModelManager.map.get(name);
        if (obj != null) {
            if (!MCH_ModelManager.forceReloadMode) {
                return obj;
            }
            MCH_ModelManager.map.remove(name);
        }
        IModelCustom model = null;
        try {
            String filePathMqo = "/assets/mcheli/models/" + name + ".mqo";
            String filePathObj = "/assets/mcheli/models/" + name + ".obj";
            String filePathTcn = "/assets/mcheli/models/" + name + ".tcn";
            if (new File(MCH_MOD.sourcePath + filePathMqo).exists()) {
                filePathMqo = W_ResourcePath.getModelPath() + "models/" + name + ".mqo";
                model = W_ModelBase.loadModel(filePathMqo);
            }
            else if (new File(MCH_MOD.sourcePath + filePathObj).exists()) {
                filePathObj = W_ResourcePath.getModelPath() + "models/" + name + ".obj";
                model = W_ModelBase.loadModel(filePathObj);
            }
            else if (new File(MCH_MOD.sourcePath + filePathTcn).exists()) {
                filePathTcn = W_ResourcePath.getModelPath() + "models/" + name + ".tcn";
                model = W_ModelBase.loadModel(filePathTcn);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            model = null;
        }
        if (model != null) {
            MCH_ModelManager.map.put(name, model);
            return model;
        }
        return null;
    }
    
    public static void render(final String path, final String name) {
        render(path + "/" + name);
    }
    
    public static void render(final String name) {
        final IModelCustom model = MCH_ModelManager.map.get(name);
        if (model != null) {
            model.renderAll();
        }
        else if (MCH_ModelManager.defaultModel != null) {}
    }
    
    public static void renderPart(final String name, final String partName) {
        final IModelCustom model = MCH_ModelManager.map.get(name);
        if (model != null) {
            model.renderPart(partName);
        }
    }
    
    public static void renderLine(final String path, final String name, final int startLine, final int maxLine) {
        final IModelCustom model = MCH_ModelManager.map.get(path + "/" + name);
        if (model instanceof W_ModelCustom) {
            ((W_ModelCustom)model).renderAllLine(startLine, maxLine);
        }
    }
    
    public static void render(final String path, final String name, final int startFace, final int maxFace) {
        final IModelCustom model = MCH_ModelManager.map.get(path + "/" + name);
        if (model instanceof W_ModelCustom) {
            ((W_ModelCustom)model).renderAll(startFace, maxFace);
        }
    }
    
    public static int getVertexNum(final String path, final String name) {
        final IModelCustom model = MCH_ModelManager.map.get(path + "/" + name);
        if (model instanceof W_ModelCustom) {
            return ((W_ModelCustom)model).getVertexNum();
        }
        return 0;
    }
    
    public static W_ModelCustom get(final String path, final String name) {
        final IModelCustom model = MCH_ModelManager.map.get(path + "/" + name);
        if (model instanceof W_ModelCustom) {
            return (W_ModelCustom)model;
        }
        return null;
    }
    
    public static W_ModelCustom getRandome() {
        final int size = MCH_ModelManager.map.size();
        for (int i = 0; i < 10; ++i) {
            int idx = 0;
            final int index = MCH_ModelManager.rand.nextInt(size);
            for (final IModelCustom model : MCH_ModelManager.map.values()) {
                if (idx >= index && model instanceof W_ModelCustom) {
                    return (W_ModelCustom)model;
                }
                ++idx;
            }
        }
        return null;
    }
    
    public static boolean containsModel(final String path, final String name) {
        return containsModel(path + "/" + name);
    }
    
    public static boolean containsModel(final String name) {
        return MCH_ModelManager.map.containsKey(name);
    }
    
    static {
        MCH_ModelManager.instance = new MCH_ModelManager();
        MCH_ModelManager.forceReloadMode = false;
        MCH_ModelManager.rand = new Random();
    }
}
