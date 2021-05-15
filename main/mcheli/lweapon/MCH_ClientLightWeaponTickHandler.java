package mcheli.lweapon;

import java.nio.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.*;
import mcheli.aircraft.*;
import mcheli.gltd.*;
import mcheli.weapon.*;
import net.minecraft.item.*;
import mcheli.*;
import mcheli.wrapper.*;
import net.minecraft.potion.*;
import org.lwjgl.*;

public class MCH_ClientLightWeaponTickHandler extends MCH_ClientTickHandlerBase
{
    private static FloatBuffer screenPos;
    private static FloatBuffer screenPosBB;
    private static FloatBuffer matModel;
    private static FloatBuffer matProjection;
    private static IntBuffer matViewport;
    protected boolean isHeldItem;
    protected boolean isBeforeHeldItem;
    protected EntityPlayer prevThePlayer;
    protected ItemStack prevItemStack;
    public MCH_Key KeyAttack;
    public MCH_Key KeyUseWeapon;
    public MCH_Key KeySwWeaponMode;
    public MCH_Key KeyZoom;
    public MCH_Key KeyCameraMode;
    public MCH_Key[] Keys;
    protected static MCH_WeaponBase weapon;
    public static int reloadCount;
    public static int lockonSoundCount;
    public static int weaponMode;
    public static int selectedZoom;
    public static Entity markEntity;
    public static Vec3 markPos;
    public static MCH_WeaponGuidanceSystem gs;
    public static double lockRange;
    
    public MCH_ClientLightWeaponTickHandler(final Minecraft minecraft, final MCH_Config config) {
        super(minecraft);
        this.isHeldItem = false;
        this.isBeforeHeldItem = false;
        this.prevThePlayer = null;
        this.prevItemStack = null;
        this.updateKeybind(config);
        MCH_ClientLightWeaponTickHandler.gs.canLockInAir = false;
        MCH_ClientLightWeaponTickHandler.gs.canLockOnGround = false;
        MCH_ClientLightWeaponTickHandler.gs.canLockInWater = false;
        MCH_ClientLightWeaponTickHandler.gs.setLockCountMax(40);
        MCH_ClientLightWeaponTickHandler.gs.lockRange = 120.0;
        MCH_ClientLightWeaponTickHandler.lockonSoundCount = 0;
        this.initWeaponParam(null);
    }
    
    public static void markEntity(final Entity entity, final double x, final double y, final double z) {
        if (MCH_ClientLightWeaponTickHandler.gs.getLockingEntity() == entity) {
            GL11.glGetFloat(2982, MCH_ClientLightWeaponTickHandler.matModel);
            GL11.glGetFloat(2983, MCH_ClientLightWeaponTickHandler.matProjection);
            GL11.glGetInteger(2978, MCH_ClientLightWeaponTickHandler.matViewport);
            GLU.gluProject((float)x, (float)y, (float)z, MCH_ClientLightWeaponTickHandler.matModel, MCH_ClientLightWeaponTickHandler.matProjection, MCH_ClientLightWeaponTickHandler.matViewport, MCH_ClientLightWeaponTickHandler.screenPos);
            final MCH_AircraftInfo i = (entity instanceof MCH_EntityAircraft) ? ((MCH_EntityAircraft)entity).getAcInfo() : null;
            final float w = (i != null) ? i.markerWidth : ((entity.field_70130_N > entity.field_70131_O) ? entity.field_70130_N : entity.field_70131_O);
            final float h = (i != null) ? i.markerHeight : entity.field_70131_O;
            GLU.gluProject((float)x + w, (float)y + h, (float)z + w, MCH_ClientLightWeaponTickHandler.matModel, MCH_ClientLightWeaponTickHandler.matProjection, MCH_ClientLightWeaponTickHandler.matViewport, MCH_ClientLightWeaponTickHandler.screenPosBB);
            MCH_ClientLightWeaponTickHandler.markEntity = entity;
        }
    }
    
    public static Vec3 getMartEntityPos() {
        if (MCH_ClientLightWeaponTickHandler.gs.getLockingEntity() == MCH_ClientLightWeaponTickHandler.markEntity && MCH_ClientLightWeaponTickHandler.markEntity != null) {
            return Vec3.func_72443_a((double)MCH_ClientLightWeaponTickHandler.screenPos.get(0), (double)MCH_ClientLightWeaponTickHandler.screenPos.get(1), (double)MCH_ClientLightWeaponTickHandler.screenPos.get(2));
        }
        return null;
    }
    
    public static Vec3 getMartEntityBBPos() {
        if (MCH_ClientLightWeaponTickHandler.gs.getLockingEntity() == MCH_ClientLightWeaponTickHandler.markEntity && MCH_ClientLightWeaponTickHandler.markEntity != null) {
            return Vec3.func_72443_a((double)MCH_ClientLightWeaponTickHandler.screenPosBB.get(0), (double)MCH_ClientLightWeaponTickHandler.screenPosBB.get(1), (double)MCH_ClientLightWeaponTickHandler.screenPosBB.get(2));
        }
        return null;
    }
    
    public void initWeaponParam(final EntityPlayer player) {
        MCH_ClientLightWeaponTickHandler.reloadCount = 0;
        MCH_ClientLightWeaponTickHandler.weaponMode = 0;
        MCH_ClientLightWeaponTickHandler.selectedZoom = 0;
    }
    
    @Override
    public void updateKeybind(final MCH_Config config) {
        this.KeyAttack = new MCH_Key(MCH_Config.KeyAttack.prmInt);
        this.KeyUseWeapon = new MCH_Key(MCH_Config.KeyUseWeapon.prmInt);
        this.KeySwWeaponMode = new MCH_Key(MCH_Config.KeySwWeaponMode.prmInt);
        this.KeyZoom = new MCH_Key(MCH_Config.KeyZoom.prmInt);
        this.KeyCameraMode = new MCH_Key(MCH_Config.KeyCameraMode.prmInt);
        this.Keys = new MCH_Key[] { this.KeyAttack, this.KeyUseWeapon, this.KeySwWeaponMode, this.KeyZoom, this.KeyCameraMode };
    }
    
    @Override
    protected void onTick(final boolean inGUI) {
        for (final MCH_Key k : this.Keys) {
            k.update();
        }
        this.isBeforeHeldItem = this.isHeldItem;
        final EntityPlayer player = (EntityPlayer)this.mc.field_71439_g;
        if (this.prevThePlayer == null || this.prevThePlayer != player) {
            this.initWeaponParam(player);
            this.prevThePlayer = player;
        }
        ItemStack is = (player != null) ? player.func_70694_bm() : null;
        if (player == null || player.field_70154_o instanceof MCH_EntityGLTD || player.field_70154_o instanceof MCH_EntityAircraft) {
            is = null;
        }
        if (MCH_ClientLightWeaponTickHandler.gs.getLockingEntity() == null) {
            MCH_ClientLightWeaponTickHandler.markEntity = null;
        }
        if (is != null && is.func_77973_b() instanceof MCH_ItemLightWeaponBase) {
            final MCH_ItemLightWeaponBase lweapon = (MCH_ItemLightWeaponBase)is.func_77973_b();
            if (this.prevItemStack == null || (!this.prevItemStack.func_77969_a(is) && !this.prevItemStack.func_77977_a().equals(is.func_77977_a()))) {
                this.initWeaponParam(player);
                MCH_ClientLightWeaponTickHandler.weapon = MCH_WeaponCreator.createWeapon(player.field_70170_p, MCH_ItemLightWeaponBase.getName(is), Vec3.func_72443_a(0.0, 0.0, 0.0), 0.0f, 0.0f, null, false);
                if (MCH_ClientLightWeaponTickHandler.weapon != null && MCH_ClientLightWeaponTickHandler.weapon.getInfo() != null && MCH_ClientLightWeaponTickHandler.weapon.getGuidanceSystem() != null) {
                    MCH_ClientLightWeaponTickHandler.gs = MCH_ClientLightWeaponTickHandler.weapon.getGuidanceSystem();
                }
            }
            if (MCH_ClientLightWeaponTickHandler.weapon == null || MCH_ClientLightWeaponTickHandler.gs == null) {
                return;
            }
            MCH_ClientLightWeaponTickHandler.gs.setWorld(player.field_70170_p);
            MCH_ClientLightWeaponTickHandler.gs.lockRange = MCH_ClientLightWeaponTickHandler.lockRange;
            if (player.func_71057_bx() > 10) {
                MCH_ClientLightWeaponTickHandler.selectedZoom %= MCH_ClientLightWeaponTickHandler.weapon.getInfo().zoom.length;
                W_Reflection.setCameraZoom(MCH_ClientLightWeaponTickHandler.weapon.getInfo().zoom[MCH_ClientLightWeaponTickHandler.selectedZoom]);
            }
            else {
                W_Reflection.restoreCameraZoom();
            }
            if (is.func_77960_j() < is.func_77958_k()) {
                if (player.func_71057_bx() > 10) {
                    MCH_ClientLightWeaponTickHandler.gs.lock((Entity)player);
                    if (MCH_ClientLightWeaponTickHandler.gs.getLockCount() > 0) {
                        if (MCH_ClientLightWeaponTickHandler.lockonSoundCount > 0) {
                            --MCH_ClientLightWeaponTickHandler.lockonSoundCount;
                        }
                        else {
                            MCH_ClientLightWeaponTickHandler.lockonSoundCount = 7;
                            MCH_ClientLightWeaponTickHandler.lockonSoundCount *= (int)(1.0 - MCH_ClientLightWeaponTickHandler.gs.getLockCount() / MCH_ClientLightWeaponTickHandler.gs.getLockCountMax());
                            if (MCH_ClientLightWeaponTickHandler.lockonSoundCount < 3) {
                                MCH_ClientLightWeaponTickHandler.lockonSoundCount = 2;
                            }
                            W_McClient.MOD_playSoundFX("lockon", 1.0f, 1.0f);
                        }
                    }
                }
                else {
                    W_Reflection.restoreCameraZoom();
                    MCH_ClientLightWeaponTickHandler.gs.clearLock();
                }
                MCH_ClientLightWeaponTickHandler.reloadCount = 0;
            }
            else {
                MCH_ClientLightWeaponTickHandler.lockonSoundCount = 0;
                if (W_EntityPlayer.hasItem(player, lweapon.bullet) && player.func_71052_bv() <= 0) {
                    if (MCH_ClientLightWeaponTickHandler.reloadCount == 10) {
                        W_McClient.MOD_playSoundFX("fim92_reload", 1.0f, 1.0f);
                    }
                    final int RELOAD_CNT = 40;
                    if (MCH_ClientLightWeaponTickHandler.reloadCount < 40) {
                        ++MCH_ClientLightWeaponTickHandler.reloadCount;
                        if (MCH_ClientLightWeaponTickHandler.reloadCount == 40) {
                            this.onCompleteReload();
                        }
                    }
                }
                else {
                    MCH_ClientLightWeaponTickHandler.reloadCount = 0;
                }
                MCH_ClientLightWeaponTickHandler.gs.clearLock();
            }
            if (!inGUI) {
                this.playerControl(player, is, (MCH_ItemLightWeaponBase)is.func_77973_b());
            }
            this.isHeldItem = MCH_ItemLightWeaponBase.isHeld(player);
        }
        else {
            MCH_ClientLightWeaponTickHandler.lockonSoundCount = 0;
            MCH_ClientLightWeaponTickHandler.reloadCount = 0;
            this.isHeldItem = false;
        }
        if (this.isBeforeHeldItem != this.isHeldItem) {
            MCH_Lib.DbgLog(true, "LWeapon cancel", new Object[0]);
            if (!this.isHeldItem) {
                if (getPotionNightVisionDuration(player) < 250) {
                    final MCH_PacketLightWeaponPlayerControl pc = new MCH_PacketLightWeaponPlayerControl();
                    pc.camMode = 1;
                    W_Network.sendToServer(pc);
                    player.func_70618_n(Potion.field_76439_r.func_76396_c());
                }
                W_Reflection.restoreCameraZoom();
            }
        }
        this.prevItemStack = is;
        MCH_ClientLightWeaponTickHandler.gs.update();
    }
    
    protected void onCompleteReload() {
        final MCH_PacketLightWeaponPlayerControl pc = new MCH_PacketLightWeaponPlayerControl();
        pc.cmpReload = 1;
        W_Network.sendToServer(pc);
    }
    
    protected void playerControl(final EntityPlayer player, final ItemStack is, final MCH_ItemLightWeaponBase item) {
        final MCH_PacketLightWeaponPlayerControl pc = new MCH_PacketLightWeaponPlayerControl();
        boolean send = false;
        boolean autoShot = false;
        final MCH_Config config = MCH_MOD.config;
        if (MCH_Config.LWeaponAutoFire.prmBool && is.func_77960_j() < is.func_77958_k() && MCH_ClientLightWeaponTickHandler.gs.isLockComplete()) {
            autoShot = true;
        }
        if (this.KeySwWeaponMode.isKeyDown() && MCH_ClientLightWeaponTickHandler.weapon.numMode > 1) {
            MCH_ClientLightWeaponTickHandler.weaponMode = (MCH_ClientLightWeaponTickHandler.weaponMode + 1) % MCH_ClientLightWeaponTickHandler.weapon.numMode;
            W_McClient.MOD_playSoundFX("pi", 0.5f, 0.9f);
        }
        if (this.KeyAttack.isKeyPress() || autoShot) {
            boolean result = false;
            if (is.func_77960_j() < is.func_77958_k() && MCH_ClientLightWeaponTickHandler.gs.isLockComplete()) {
                boolean canFire = true;
                if (MCH_ClientLightWeaponTickHandler.weaponMode > 0 && MCH_ClientLightWeaponTickHandler.gs.getTargetEntity() != null) {
                    final double dx = MCH_ClientLightWeaponTickHandler.gs.getTargetEntity().field_70165_t - player.field_70165_t;
                    final double dz = MCH_ClientLightWeaponTickHandler.gs.getTargetEntity().field_70161_v - player.field_70161_v;
                    canFire = (Math.sqrt(dx * dx + dz * dz) >= 40.0);
                }
                if (canFire) {
                    pc.useWeapon = true;
                    pc.useWeaponOption1 = W_Entity.getEntityId(MCH_ClientLightWeaponTickHandler.gs.lastLockEntity);
                    pc.useWeaponOption2 = MCH_ClientLightWeaponTickHandler.weaponMode;
                    pc.useWeaponPosX = player.field_70165_t;
                    pc.useWeaponPosY = player.field_70163_u;
                    pc.useWeaponPosZ = player.field_70161_v;
                    MCH_ClientLightWeaponTickHandler.gs.clearLock();
                    send = true;
                    result = true;
                }
            }
            if (this.KeyAttack.isKeyDown() && !result && player.func_71057_bx() > 5) {
                playSoundNG();
            }
        }
        if (this.KeyZoom.isKeyDown()) {
            final int prevZoom = MCH_ClientLightWeaponTickHandler.selectedZoom;
            MCH_ClientLightWeaponTickHandler.selectedZoom = (MCH_ClientLightWeaponTickHandler.selectedZoom + 1) % MCH_ClientLightWeaponTickHandler.weapon.getInfo().zoom.length;
            if (prevZoom != MCH_ClientLightWeaponTickHandler.selectedZoom) {
                MCH_ClientTickHandlerBase.playSound("zoom", 0.5f, 1.0f);
            }
        }
        if (this.KeyCameraMode.isKeyDown()) {
            final PotionEffect pe = player.func_70660_b(Potion.field_76439_r);
            MCH_Lib.DbgLog(true, "LWeapon NV %s", (pe != null) ? "ON->OFF" : "OFF->ON");
            if (pe != null) {
                player.func_70618_n(Potion.field_76439_r.func_76396_c());
                pc.camMode = 1;
                send = true;
                W_McClient.MOD_playSoundFX("pi", 0.5f, 0.9f);
            }
            else if (player.func_71057_bx() > 60) {
                pc.camMode = 2;
                send = true;
                W_McClient.MOD_playSoundFX("pi", 0.5f, 0.9f);
            }
            else {
                playSoundNG();
            }
        }
        if (send) {
            W_Network.sendToServer(pc);
        }
    }
    
    public static int getPotionNightVisionDuration(final EntityPlayer player) {
        final PotionEffect cpe = player.func_70660_b(Potion.field_76439_r);
        return (player == null || cpe == null) ? 0 : cpe.func_76459_b();
    }
    
    static {
        MCH_ClientLightWeaponTickHandler.screenPos = BufferUtils.createFloatBuffer(3);
        MCH_ClientLightWeaponTickHandler.screenPosBB = BufferUtils.createFloatBuffer(3);
        MCH_ClientLightWeaponTickHandler.matModel = BufferUtils.createFloatBuffer(16);
        MCH_ClientLightWeaponTickHandler.matProjection = BufferUtils.createFloatBuffer(16);
        MCH_ClientLightWeaponTickHandler.matViewport = BufferUtils.createIntBuffer(16);
        MCH_ClientLightWeaponTickHandler.markEntity = null;
        MCH_ClientLightWeaponTickHandler.markPos = Vec3.func_72443_a(0.0, 0.0, 0.0);
        MCH_ClientLightWeaponTickHandler.gs = new MCH_WeaponGuidanceSystem();
        MCH_ClientLightWeaponTickHandler.lockRange = 120.0;
    }
}
