package mcheli.weapon;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import mcheli.wrapper.*;

public class MCH_DummyEntityPlayer extends W_EntityPlayer
{
    public MCH_DummyEntityPlayer(final World p_i45324_1_, final EntityPlayer player) {
        super(p_i45324_1_, player);
    }
    
    public void func_145747_a(final IChatComponent var1) {
    }
    
    public boolean func_70003_b(final int var1, final String var2) {
        return false;
    }
    
    public ChunkCoordinates func_82114_b() {
        return null;
    }
    
    public void sendChatToPlayer(final ChatMessageComponent chatmessagecomponent) {
    }
}
