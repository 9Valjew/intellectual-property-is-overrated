package mcheli.wrapper;

import net.minecraft.client.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.texture.*;

public class W_TextureUtil
{
    private static W_TextureUtil instance;
    
    private TextureParam newParam() {
        return new TextureParam();
    }
    
    public static TextureParam getTextureInfo(final String domain, final String name) {
        final TextureManager textureManager = Minecraft.func_71410_x().func_110434_K();
        final ResourceLocation r = new ResourceLocation(domain, name);
        textureManager.func_110577_a(r);
        final TextureParam info = W_TextureUtil.instance.newParam();
        info.width = GL11.glGetTexLevelParameteri(3553, 0, 4096);
        info.height = GL11.glGetTexLevelParameteri(3553, 0, 4097);
        return info;
    }
    
    static {
        W_TextureUtil.instance = new W_TextureUtil();
    }
    
    public class TextureParam
    {
        public int width;
        public int height;
    }
}
