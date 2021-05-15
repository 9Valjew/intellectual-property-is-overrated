package mcheli.aircraft;

import mcheli.wrapper.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;

@SideOnly(Side.CLIENT)
public class MCH_SoundUpdater extends W_SoundUpdater
{
    private final MCH_EntityAircraft theAircraft;
    private final EntityPlayerSP thePlayer;
    private boolean isMoving;
    private boolean silent;
    private float aircraftPitch;
    private float aircraftVolume;
    private float addPitch;
    private boolean isFirstUpdate;
    private double prevDist;
    private int soundDelay;
    
    public MCH_SoundUpdater(final Minecraft mc, final MCH_EntityAircraft aircraft, final EntityPlayerSP entityPlayerSP) {
        super(mc, aircraft);
        this.soundDelay = 0;
        this.theAircraft = aircraft;
        this.thePlayer = entityPlayerSP;
        this.isFirstUpdate = true;
    }
    
    public void update() {
        if (this.theAircraft.getSoundName().isEmpty() || this.theAircraft.getAcInfo() == null) {
            return;
        }
        if (this.isFirstUpdate) {
            this.isFirstUpdate = false;
            this.initEntitySound(this.theAircraft.getSoundName());
        }
        final MCH_AircraftInfo info = this.theAircraft.getAcInfo();
        final boolean isBeforeMoving = this.isMoving;
        final boolean isDead = this.theAircraft.field_70128_L;
        if (isDead || (!this.silent && this.aircraftVolume == 0.0f)) {
            if (isDead) {
                this.stopEntitySound(this.theAircraft);
            }
            this.silent = true;
            if (isDead) {
                return;
            }
        }
        final boolean isRide = this.theAircraft.getSeatIdByEntity((Entity)this.thePlayer) >= 0;
        final boolean isPlaying = this.isEntitySoundPlaying(this.theAircraft);
        boolean onPlaySound = false;
        if (!isPlaying && this.aircraftVolume > 0.0f) {
            if (this.soundDelay > 0) {
                --this.soundDelay;
            }
            else {
                this.soundDelay = 20;
                this.playEntitySound(this.theAircraft.getSoundName(), this.theAircraft, this.aircraftVolume, this.aircraftPitch, true);
            }
            this.silent = false;
            onPlaySound = true;
        }
        final float prevVolume = this.aircraftVolume;
        final float prevPitch = this.aircraftPitch;
        this.isMoving = (info.soundVolume * this.theAircraft.getSoundVolume() >= 0.01);
        if (this.isMoving) {
            this.aircraftVolume = info.soundVolume * this.theAircraft.getSoundVolume();
            this.aircraftPitch = info.soundPitch * this.theAircraft.getSoundPitch();
            if (!isRide) {
                final double dist = this.thePlayer.func_70011_f(this.theAircraft.field_70165_t, this.thePlayer.field_70163_u, this.theAircraft.field_70161_v);
                final double pitch = this.prevDist - dist;
                if (Math.abs(pitch) > 0.3) {
                    this.addPitch += (float)(pitch / 40.0);
                    final float maxAddPitch = 0.2f;
                    if (this.addPitch < -maxAddPitch) {
                        this.addPitch = -maxAddPitch;
                    }
                    if (this.addPitch > maxAddPitch) {
                        this.addPitch = maxAddPitch;
                    }
                }
                this.addPitch *= 0.9;
                this.aircraftPitch += this.addPitch;
                this.prevDist = dist;
            }
            if (this.aircraftPitch < 0.0f) {
                this.aircraftPitch = 0.0f;
            }
        }
        else if (isBeforeMoving) {
            this.aircraftVolume = 0.0f;
            this.aircraftPitch = 0.0f;
        }
        if (!this.silent) {
            if (this.aircraftPitch != prevPitch) {
                this.setEntitySoundPitch(this.theAircraft, this.aircraftPitch);
            }
            if (this.aircraftVolume != prevVolume) {
                this.setEntitySoundVolume(this.theAircraft, this.aircraftVolume);
            }
        }
        boolean updateLocation = false;
        updateLocation = true;
        if (updateLocation && this.aircraftVolume > 0.0f) {
            if (isRide) {
                this.updateSoundLocation(this.theAircraft);
            }
            else {
                final double px = this.thePlayer.field_70165_t;
                final double py = this.thePlayer.field_70163_u;
                final double pz = this.thePlayer.field_70161_v;
                double dx = this.theAircraft.field_70165_t - px;
                double dy = this.theAircraft.field_70163_u - py;
                double dz = this.theAircraft.field_70161_v - pz;
                final double dist2 = info.soundRange / 16.0;
                dx /= dist2;
                dy /= dist2;
                dz /= dist2;
                this.updateSoundLocation(px + dx, py + dy, pz + dz);
            }
        }
        else if (this.isEntitySoundPlaying(this.theAircraft)) {
            this.stopEntitySound(this.theAircraft);
        }
    }
}
