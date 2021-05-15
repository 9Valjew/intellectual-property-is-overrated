package mcheli.wrapper;

import cpw.mods.fml.common.eventhandler.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.entity.*;
import net.minecraftforge.event.*;

public class W_EventHook
{
    @SubscribeEvent
    public void onEvent_entitySpawn(final EntityJoinWorldEvent event) {
        this.entitySpawn(event);
    }
    
    public void entitySpawn(final EntityJoinWorldEvent event) {
    }
    
    @SubscribeEvent
    public void onEvent_livingHurtEvent(final LivingHurtEvent event) {
        this.livingHurtEvent(event);
    }
    
    public void livingHurtEvent(final LivingHurtEvent event) {
    }
    
    @SubscribeEvent
    public void onEvent_livingAttackEvent(final LivingAttackEvent event) {
        this.livingAttackEvent(event);
    }
    
    public void livingAttackEvent(final LivingAttackEvent event) {
    }
    
    @SubscribeEvent
    public void onEvent_entityInteractEvent(final EntityInteractEvent event) {
        this.entityInteractEvent(event);
    }
    
    public void entityInteractEvent(final EntityInteractEvent event) {
    }
    
    @SubscribeEvent
    public void onEvent_entityCanUpdate(final EntityEvent.CanUpdate event) {
        this.entityCanUpdate(event);
    }
    
    public void entityCanUpdate(final EntityEvent.CanUpdate event) {
    }
    
    @SubscribeEvent
    public void onEvent_commandEvent(final CommandEvent event) {
        this.commandEvent(event);
    }
    
    public void commandEvent(final CommandEvent event) {
    }
}
