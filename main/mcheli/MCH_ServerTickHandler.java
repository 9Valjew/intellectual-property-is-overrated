package mcheli;

import cpw.mods.fml.common.gameevent.*;
import cpw.mods.fml.common.eventhandler.*;
import mcheli.wrapper.*;
import net.minecraft.network.*;
import java.util.*;
import cpw.mods.fml.common.network.internal.*;

public class MCH_ServerTickHandler
{
    HashMap<String, Integer> rcvMap;
    HashMap<String, Integer> sndMap;
    int sndPacketNum;
    int rcvPacketNum;
    int tick;
    
    public MCH_ServerTickHandler() {
        this.rcvMap = new HashMap<String, Integer>();
        this.sndMap = new HashMap<String, Integer>();
        this.sndPacketNum = 0;
        this.rcvPacketNum = 0;
    }
    
    @SubscribeEvent
    public void onServerTickEvent(final TickEvent.ServerTickEvent event) {
        final TickEvent.Phase phase = event.phase;
        final TickEvent.Phase phase2 = event.phase;
        if (phase == TickEvent.Phase.START) {}
        final TickEvent.Phase phase3 = event.phase;
        final TickEvent.Phase phase4 = event.phase;
        if (phase3 == TickEvent.Phase.END) {}
    }
    
    private void onServerTickPre() {
        ++this.tick;
        final List list = W_Reflection.getNetworkManagers();
        if (list != null) {
            for (int i = 0; i < list.size(); ++i) {
                Queue queue = W_Reflection.getReceivedPacketsQueue(list.get(i));
                if (queue != null) {
                    this.putMap(this.rcvMap, queue.iterator());
                    this.rcvPacketNum += queue.size();
                }
                queue = W_Reflection.getSendPacketsQueue(list.get(i));
                if (queue != null) {
                    this.putMap(this.sndMap, queue.iterator());
                    this.sndPacketNum += queue.size();
                }
            }
        }
        if (this.tick >= 20) {
            this.tick = 0;
            final boolean b = false;
            this.sndPacketNum = (b ? 1 : 0);
            this.rcvPacketNum = (b ? 1 : 0);
            this.rcvMap.clear();
            this.sndMap.clear();
        }
    }
    
    public void putMap(final HashMap<String, Integer> map, final Iterator iterator) {
        while (iterator.hasNext()) {
            final Object o = iterator.next();
            String key = o.getClass().getName().toString();
            if (key.startsWith("net.minecraft.")) {
                key = "Minecraft";
            }
            else if (o instanceof FMLProxyPacket) {
                final FMLProxyPacket p = (FMLProxyPacket)o;
                key = p.channel();
            }
            else {
                key = "Unknown!";
            }
            if (map.containsKey(key)) {
                map.put(key, 1 + map.get(key));
            }
            else {
                map.put(key, 1);
            }
        }
    }
    
    private void onServerTickPost() {
    }
}
