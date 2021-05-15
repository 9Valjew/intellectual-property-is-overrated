package mcheli.multiplay;

import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;

public class MCH_ContainerScoreboard extends Container
{
    public final EntityPlayer thePlayer;
    
    public MCH_ContainerScoreboard(final EntityPlayer player) {
        this.thePlayer = player;
    }
    
    public boolean func_75145_c(final EntityPlayer player) {
        return true;
    }
}
