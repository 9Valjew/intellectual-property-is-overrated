package mcheli.wrapper;

import cpw.mods.fml.common.network.simpleimpl.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import cpw.mods.fml.common.network.*;

public class W_Network
{
    public static final SimpleNetworkWrapper INSTANCE;
    
    public static void sendToServer(final W_PacketBase pkt) {
        W_Network.INSTANCE.sendToServer((IMessage)pkt);
    }
    
    public static void sendToPlayer(final W_PacketBase pkt, final EntityPlayer player) {
        if (player instanceof EntityPlayerMP) {
            W_Network.INSTANCE.sendTo((IMessage)pkt, (EntityPlayerMP)player);
        }
    }
    
    public static void sendToAllAround(final W_PacketBase pkt, final Entity sender, final double renge) {
        final NetworkRegistry.TargetPoint t = new NetworkRegistry.TargetPoint(sender.field_71093_bK, sender.field_70165_t, sender.field_70163_u, sender.field_70161_v, renge);
        W_Network.INSTANCE.sendToAllAround((IMessage)pkt, t);
    }
    
    public static void sendToAllPlayers(final W_PacketBase pkt) {
        W_Network.INSTANCE.sendToAll((IMessage)pkt);
    }
    
    static {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("MCHeli_CH");
    }
}
