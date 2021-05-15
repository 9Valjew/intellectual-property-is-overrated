package mcheli.wrapper;

import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.player.*;
import cpw.mods.fml.common.network.*;

public class W_NetworkRegistry
{
    public static W_PacketHandler packetHandler;
    
    public static void registerChannel(final W_PacketHandler handler, final String name) {
        W_NetworkRegistry.packetHandler = handler;
        W_Network.INSTANCE.registerMessage((Class)W_PacketHandler.class, (Class)W_PacketBase.class, 0, Side.SERVER);
        W_Network.INSTANCE.registerMessage((Class)W_PacketHandler.class, (Class)W_PacketBase.class, 0, Side.CLIENT);
    }
    
    public static void handlePacket(final EntityPlayer player, final byte[] data) {
    }
    
    public static void registerGuiHandler(final Object mod, final IGuiHandler handler) {
        NetworkRegistry.INSTANCE.registerGuiHandler(mod, handler);
    }
}
