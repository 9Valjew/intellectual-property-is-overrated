package mcheli.wrapper;

import org.lwjgl.opengl.*;

public class W_OpenGlHelper
{
    public static void glBlendFunc(final int i, final int j, final int k, final int l) {
        GL11.glBlendFunc(i, j);
    }
}
