package mcheli.wrapper;

import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import cpw.mods.fml.common.gameevent.*;
import cpw.mods.fml.common.eventhandler.*;

public abstract class W_TickHandler implements ITickHandler
{
    protected Minecraft mc;
    
    public W_TickHandler(final Minecraft m) {
        this.mc = m;
    }
    
    public void onPlayerTickPre(final EntityPlayer player) {
    }
    
    public void onPlayerTickPost(final EntityPlayer player) {
    }
    
    public void onRenderTickPre(final float partialTicks) {
    }
    
    public void onRenderTickPost(final float partialTicks) {
    }
    
    public void onTickPre() {
    }
    
    public void onTickPost() {
    }
    
    @SubscribeEvent
    public void onPlayerTickEvent(final TickEvent.PlayerTickEvent event) {
        final TickEvent.Phase phase = event.phase;
        final TickEvent.Phase phase2 = event.phase;
        if (phase == TickEvent.Phase.START) {
            this.onPlayerTickPre(event.player);
        }
        final TickEvent.Phase phase3 = event.phase;
        final TickEvent.Phase phase4 = event.phase;
        if (phase3 == TickEvent.Phase.END) {
            this.onPlayerTickPost(event.player);
        }
    }
    
    @SubscribeEvent
    public void onClientTickEvent(final TickEvent.ClientTickEvent event) {
        final TickEvent.Phase phase = event.phase;
        final TickEvent.Phase phase2 = event.phase;
        if (phase == TickEvent.Phase.START) {
            this.onTickPre();
        }
        final TickEvent.Phase phase3 = event.phase;
        final TickEvent.Phase phase4 = event.phase;
        if (phase3 == TickEvent.Phase.END) {
            this.onTickPost();
        }
    }
    
    @SubscribeEvent
    public void onRenderTickEvent(final TickEvent.RenderTickEvent event) {
        final TickEvent.Phase phase = event.phase;
        final TickEvent.Phase phase2 = event.phase;
        if (phase == TickEvent.Phase.START) {
            this.onRenderTickPre(event.renderTickTime);
        }
        final TickEvent.Phase phase3 = event.phase;
        final TickEvent.Phase phase4 = event.phase;
        if (phase3 == TickEvent.Phase.END) {
            this.onRenderTickPost(event.renderTickTime);
        }
    }
    
    enum TickType
    {
        RENDER, 
        CLIENT;
    }
}
