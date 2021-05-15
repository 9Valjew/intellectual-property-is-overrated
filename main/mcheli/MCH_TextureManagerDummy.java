package mcheli;

import net.minecraft.util.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.renderer.texture.*;

public class MCH_TextureManagerDummy extends TextureManager
{
    public static ResourceLocation R;
    private TextureManager tm;
    
    public MCH_TextureManagerDummy(final TextureManager t) {
        super((IResourceManager)null);
        this.tm = t;
    }
    
    public void func_110577_a(final ResourceLocation resouce) {
        if (MCH_ClientCommonTickHandler.cameraMode == 2) {
            this.tm.func_110577_a(MCH_TextureManagerDummy.R);
        }
        else {
            this.tm.func_110577_a(resouce);
        }
    }
    
    public ResourceLocation func_130087_a(final int p_130087_1_) {
        return this.tm.func_130087_a(p_130087_1_);
    }
    
    public boolean func_130088_a(final ResourceLocation p_130088_1_, final TextureMap p_130088_2_) {
        return this.tm.func_130088_a(p_130088_1_, p_130088_2_);
    }
    
    public boolean func_110580_a(final ResourceLocation p_110580_1_, final ITickableTextureObject p_110580_2_) {
        return this.tm.func_110580_a(p_110580_1_, p_110580_2_);
    }
    
    public boolean func_110579_a(final ResourceLocation p_110579_1_, final ITextureObject p_110579_2_) {
        return this.tm.func_110579_a(p_110579_1_, p_110579_2_);
    }
    
    public ITextureObject func_110581_b(final ResourceLocation p_110581_1_) {
        return this.tm.func_110581_b(p_110581_1_);
    }
    
    public ResourceLocation func_110578_a(final String p_110578_1_, final DynamicTexture p_110578_2_) {
        return this.tm.func_110578_a(p_110578_1_, p_110578_2_);
    }
    
    public void func_110550_d() {
        this.tm.func_110550_d();
    }
    
    public void func_147645_c(final ResourceLocation p_147645_1_) {
        this.tm.func_147645_c(p_147645_1_);
    }
    
    public void func_110549_a(final IResourceManager p_110549_1_) {
        this.tm.func_110549_a(p_110549_1_);
    }
    
    static {
        MCH_TextureManagerDummy.R = new ResourceLocation("mcheli", "textures/test.png");
    }
}
