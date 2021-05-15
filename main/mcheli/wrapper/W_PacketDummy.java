package mcheli.wrapper;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.*;

public class W_PacketDummy implements IMessage
{
    public void fromBytes(final ByteBuf buf) {
    }
    
    public void toBytes(final ByteBuf buf) {
    }
}
