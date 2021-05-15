package mcheli.wrapper;

import cpw.mods.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.world.*;
import net.minecraftforge.event.entity.*;

public class W_ClientEventHook
{
    @SubscribeEvent
    public void onEvent_MouseEvent(final MouseEvent event) {
        this.mouseEvent(event);
    }
    
    public void mouseEvent(final MouseEvent event) {
    }
    
    @SubscribeEvent
    public void onEvent_renderLivingEventSpecialsPre(final RenderLivingEvent.Specials.Pre event) {
        this.renderLivingEventSpecialsPre(event);
    }
    
    public void renderLivingEventSpecialsPre(final RenderLivingEvent.Specials.Pre event) {
    }
    
    @SubscribeEvent
    public void onEvent_renderLivingEventSpecialsPost(final RenderLivingEvent.Specials.Post event) {
        this.renderLivingEventSpecialsPost(event);
    }
    
    public void renderLivingEventSpecialsPost(final RenderLivingEvent.Specials.Post event) {
    }
    
    @SubscribeEvent
    public void onEvent_renderLivingEventPre(final RenderLivingEvent.Pre event) {
        this.renderLivingEventPre(event);
    }
    
    public void renderLivingEventPre(final RenderLivingEvent.Pre event) {
    }
    
    @SubscribeEvent
    public void onEvent_renderLivingEventPost(final RenderLivingEvent.Post event) {
        this.renderLivingEventPost(event);
    }
    
    public void renderLivingEventPost(final RenderLivingEvent.Post event) {
    }
    
    @SubscribeEvent
    public void onEvent_renderPlayerPre(final RenderPlayerEvent.Pre event) {
        this.renderPlayerPre(event);
    }
    
    public void renderPlayerPre(final RenderPlayerEvent.Pre event) {
    }
    
    @SubscribeEvent
    public void Event_renderPlayerPost(final RenderPlayerEvent.Post event) {
        this.renderPlayerPost(event);
    }
    
    public void renderPlayerPost(final RenderPlayerEvent.Post event) {
    }
    
    @SubscribeEvent
    public void onEvent_WorldEventUnload(final WorldEvent.Unload event) {
        this.worldEventUnload(event);
    }
    
    public void worldEventUnload(final WorldEvent.Unload event) {
    }
    
    @SubscribeEvent
    public void onEvent_EntityJoinWorldEvent(final EntityJoinWorldEvent event) {
        this.entityJoinWorldEvent(event);
    }
    
    public void entityJoinWorldEvent(final EntityJoinWorldEvent event) {
    }
}
