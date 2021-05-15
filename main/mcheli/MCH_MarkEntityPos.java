package mcheli;

import java.nio.*;
import net.minecraft.entity.*;
import org.lwjgl.*;

public class MCH_MarkEntityPos
{
    public FloatBuffer pos;
    public int type;
    public Entity entity;
    
    public MCH_MarkEntityPos(final int type, final Entity entity) {
        this.type = type;
        this.pos = BufferUtils.createFloatBuffer(3);
        this.entity = entity;
    }
    
    public MCH_MarkEntityPos(final int type) {
        this(type, null);
    }
}
