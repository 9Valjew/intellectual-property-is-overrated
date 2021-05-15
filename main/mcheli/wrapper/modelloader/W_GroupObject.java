package mcheli.wrapper.modelloader;

import cpw.mods.fml.relauncher.*;
import net.minecraft.client.renderer.*;
import java.util.*;

@SideOnly(Side.CLIENT)
public class W_GroupObject
{
    public String name;
    public ArrayList<W_Face> faces;
    public int glDrawingMode;
    
    public W_GroupObject() {
        this("");
    }
    
    public W_GroupObject(final String name) {
        this(name, -1);
    }
    
    public W_GroupObject(final String name, final int glDrawingMode) {
        this.faces = new ArrayList<W_Face>();
        this.name = name;
        this.glDrawingMode = glDrawingMode;
    }
    
    public void render() {
        if (this.faces.size() > 0) {
            final Tessellator tessellator = Tessellator.field_78398_a;
            tessellator.func_78371_b(this.glDrawingMode);
            this.render(tessellator);
            tessellator.func_78381_a();
        }
    }
    
    public void render(final Tessellator tessellator) {
        if (this.faces.size() > 0) {
            for (final W_Face face : this.faces) {
                face.addFaceForRender(tessellator);
            }
        }
    }
}
