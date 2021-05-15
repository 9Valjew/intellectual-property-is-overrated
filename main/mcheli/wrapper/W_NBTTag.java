package mcheli.wrapper;

import net.minecraft.nbt.*;

public class W_NBTTag
{
    public static final int TAG_COMPOUND = 10;
    
    public static NBTTagCompound tagAt(final NBTTagList list, final int i) {
        return (list != null) ? list.func_150305_b(i) : null;
    }
    
    public static NBTTagList getTagList(final NBTTagCompound nbt, final String s, final int i) {
        return nbt.func_150295_c(s, i);
    }
    
    public static NBTTagIntArray newTagIntArray(final String s, final int[] n) {
        return new NBTTagIntArray(n);
    }
}
