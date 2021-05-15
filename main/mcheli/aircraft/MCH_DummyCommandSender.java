package mcheli.aircraft;

import net.minecraft.server.*;
import net.minecraft.command.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class MCH_DummyCommandSender implements ICommandSender
{
    public static MCH_DummyCommandSender instance;
    
    public static void execCommand(final String s) {
        final ICommandManager icommandmanager = MinecraftServer.func_71276_C().func_71187_D();
        icommandmanager.func_71556_a((ICommandSender)MCH_DummyCommandSender.instance, s);
    }
    
    public String func_70005_c_() {
        return "";
    }
    
    public IChatComponent func_145748_c_() {
        return null;
    }
    
    public void func_145747_a(final IChatComponent p_145747_1_) {
    }
    
    public boolean func_70003_b(final int p_70003_1_, final String p_70003_2_) {
        return true;
    }
    
    public ChunkCoordinates func_82114_b() {
        return null;
    }
    
    public World func_130014_f_() {
        return null;
    }
    
    static {
        MCH_DummyCommandSender.instance = new MCH_DummyCommandSender();
    }
}
