package mcheli.particles;

import net.minecraft.world.*;
import cpw.mods.fml.client.*;
import mcheli.wrapper.*;
import net.minecraft.client.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.player.*;

public class MCH_ParticlesUtil
{
    public static MCH_EntityParticleMarkPoint markPoint;
    
    public static void spawnParticleExplode(final World w, final double x, final double y, final double z, final float size, final float r, final float g, final float b, final float a, final int age) {
        final MCH_EntityParticleExplode epe = new MCH_EntityParticleExplode(w, x, y, z, size, age, 0.0);
        epe.setParticleMaxAge(age);
        epe.func_70538_b(r, g, b);
        epe.func_82338_g(a);
        FMLClientHandler.instance().getClient().field_71452_i.func_78873_a((EntityFX)epe);
    }
    
    public static void spawnParticleTileCrack(final World w, final int blockX, final int blockY, final int blockZ, final double x, final double y, final double z, final double mx, final double my, final double mz) {
        final String name = W_Particle.getParticleTileCrackName(w, blockX, blockY, blockZ);
        if (!name.isEmpty()) {
            DEF_spawnParticle(name, x, y, z, mx, my, mz, 20.0f);
        }
    }
    
    public static boolean spawnParticleTileDust(final World w, final int blockX, final int blockY, final int blockZ, final double x, final double y, final double z, final double mx, final double my, final double mz, final float scale) {
        boolean ret = false;
        final int[][] offset = { { 0, 0, 0 }, { 0, 0, -1 }, { 0, 0, 1 }, { 1, 0, 0 }, { -1, 0, 0 } };
        for (int len = offset.length, i = 0; i < len; ++i) {
            final String name = W_Particle.getParticleTileDustName(w, blockX + offset[i][0], blockY + offset[i][1], blockZ + offset[i][2]);
            if (!name.isEmpty()) {
                final EntityFX e = DEF_spawnParticle(name, x, y, z, mx, my, mz, 20.0f);
                if (e instanceof MCH_EntityBlockDustFX) {
                    ((MCH_EntityBlockDustFX)e).setScale(scale * 2.0f);
                    ret = true;
                    break;
                }
            }
        }
        return ret;
    }
    
    public static EntityFX DEF_spawnParticle(final String s, final double x, final double y, final double z, final double mx, final double my, final double mz, final float dist) {
        final EntityFX e = doSpawnParticle(s, x, y, z, mx, my, mz);
        if (e != null) {
            final EntityFX entityFX = e;
            entityFX.field_70155_l *= dist;
        }
        return e;
    }
    
    public static EntityFX doSpawnParticle(final String p_72726_1_, final double p_72726_2_, final double p_72726_4_, final double p_72726_6_, final double p_72726_8_, final double p_72726_10_, final double p_72726_12_) {
        final Minecraft mc = Minecraft.func_71410_x();
        final RenderGlobal renderGlobal = mc.field_71438_f;
        if (mc == null || mc.field_71451_h == null || mc.field_71452_i == null) {
            return null;
        }
        int i = mc.field_71474_y.field_74362_aa;
        if (i == 1 && mc.field_71441_e.field_73012_v.nextInt(3) == 0) {
            i = 2;
        }
        final double d6 = mc.field_71451_h.field_70165_t - p_72726_2_;
        final double d7 = mc.field_71451_h.field_70163_u - p_72726_4_;
        final double d8 = mc.field_71451_h.field_70161_v - p_72726_6_;
        EntityFX entityfx = null;
        if (p_72726_1_.equalsIgnoreCase("hugeexplosion")) {
            mc.field_71452_i.func_78873_a(entityfx = (EntityFX)new EntityHugeExplodeFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_));
        }
        else if (p_72726_1_.equalsIgnoreCase("largeexplode")) {
            mc.field_71452_i.func_78873_a(entityfx = (EntityFX)new EntityLargeExplodeFX(mc.field_71446_o, (World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_));
        }
        else if (p_72726_1_.equalsIgnoreCase("fireworksSpark")) {
            mc.field_71452_i.func_78873_a(entityfx = (EntityFX)new EntityFireworkSparkFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_, mc.field_71452_i));
        }
        if (entityfx != null) {
            return entityfx;
        }
        final double d9 = 300.0;
        if (d6 * d6 + d7 * d7 + d8 * d8 > d9 * d9) {
            return null;
        }
        if (i > 1) {
            return null;
        }
        if (p_72726_1_.equalsIgnoreCase("bubble")) {
            entityfx = (EntityFX)new EntityBubbleFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("suspended")) {
            entityfx = (EntityFX)new EntitySuspendFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("depthsuspend")) {
            entityfx = (EntityFX)new EntityAuraFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("townaura")) {
            entityfx = (EntityFX)new EntityAuraFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("crit")) {
            entityfx = (EntityFX)new EntityCritFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("magicCrit")) {
            entityfx = (EntityFX)new EntityCritFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
            entityfx.func_70538_b(entityfx.func_70534_d() * 0.3f, entityfx.func_70542_f() * 0.8f, entityfx.func_70535_g());
            entityfx.func_94053_h();
        }
        else if (p_72726_1_.equalsIgnoreCase("smoke")) {
            entityfx = (EntityFX)new EntitySmokeFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("mobSpell")) {
            entityfx = (EntityFX)new EntitySpellParticleFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, 0.0, 0.0, 0.0);
            entityfx.func_70538_b((float)p_72726_8_, (float)p_72726_10_, (float)p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("mobSpellAmbient")) {
            entityfx = (EntityFX)new EntitySpellParticleFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, 0.0, 0.0, 0.0);
            entityfx.func_82338_g(0.15f);
            entityfx.func_70538_b((float)p_72726_8_, (float)p_72726_10_, (float)p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("spell")) {
            entityfx = (EntityFX)new EntitySpellParticleFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("instantSpell")) {
            entityfx = (EntityFX)new EntitySpellParticleFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
            ((EntitySpellParticleFX)entityfx).func_70589_b(144);
        }
        else if (p_72726_1_.equalsIgnoreCase("witchMagic")) {
            entityfx = (EntityFX)new EntitySpellParticleFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
            ((EntitySpellParticleFX)entityfx).func_70589_b(144);
            final float f = mc.field_71441_e.field_73012_v.nextFloat() * 0.5f + 0.35f;
            entityfx.func_70538_b(1.0f * f, 0.0f * f, 1.0f * f);
        }
        else if (p_72726_1_.equalsIgnoreCase("note")) {
            entityfx = (EntityFX)new EntityNoteFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("portal")) {
            entityfx = (EntityFX)new EntityPortalFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("enchantmenttable")) {
            entityfx = (EntityFX)new EntityEnchantmentTableParticleFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("explode")) {
            entityfx = (EntityFX)new EntityExplodeFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("flame")) {
            entityfx = (EntityFX)new EntityFlameFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("lava")) {
            entityfx = (EntityFX)new EntityLavaFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_);
        }
        else if (p_72726_1_.equalsIgnoreCase("footstep")) {
            entityfx = (EntityFX)new EntityFootStepFX(mc.field_71446_o, (World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_);
        }
        else if (p_72726_1_.equalsIgnoreCase("splash")) {
            entityfx = (EntityFX)new EntitySplashFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("wake")) {
            entityfx = (EntityFX)new EntityFishWakeFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("largesmoke")) {
            entityfx = (EntityFX)new EntitySmokeFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_, 2.5f);
        }
        else if (p_72726_1_.equalsIgnoreCase("cloud")) {
            entityfx = (EntityFX)new EntityCloudFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("reddust")) {
            entityfx = (EntityFX)new EntityReddustFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, (float)p_72726_8_, (float)p_72726_10_, (float)p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("snowballpoof")) {
            entityfx = (EntityFX)new EntityBreakingFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, Items.field_151126_ay);
        }
        else if (p_72726_1_.equalsIgnoreCase("dripWater")) {
            entityfx = (EntityFX)new EntityDropParticleFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, Material.field_151586_h);
        }
        else if (p_72726_1_.equalsIgnoreCase("dripLava")) {
            entityfx = (EntityFX)new EntityDropParticleFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, Material.field_151587_i);
        }
        else if (p_72726_1_.equalsIgnoreCase("snowshovel")) {
            entityfx = (EntityFX)new EntitySnowShovelFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("slime")) {
            entityfx = (EntityFX)new EntityBreakingFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, Items.field_151123_aH);
        }
        else if (p_72726_1_.equalsIgnoreCase("heart")) {
            entityfx = (EntityFX)new EntityHeartFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
        }
        else if (p_72726_1_.equalsIgnoreCase("angryVillager")) {
            entityfx = (EntityFX)new EntityHeartFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_ + 0.5, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
            entityfx.func_70536_a(81);
            entityfx.func_70538_b(1.0f, 1.0f, 1.0f);
        }
        else if (p_72726_1_.equalsIgnoreCase("happyVillager")) {
            entityfx = (EntityFX)new EntityAuraFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
            entityfx.func_70536_a(82);
            entityfx.func_70538_b(1.0f, 1.0f, 1.0f);
        }
        else if (p_72726_1_.startsWith("iconcrack_")) {
            final String[] astring = p_72726_1_.split("_", 3);
            final int j = Integer.parseInt(astring[1]);
            if (astring.length > 2) {
                final int k = Integer.parseInt(astring[2]);
                entityfx = (EntityFX)new EntityBreakingFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_, Item.func_150899_d(j), k);
            }
            else {
                entityfx = (EntityFX)new EntityBreakingFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_, Item.func_150899_d(j), 0);
            }
        }
        else if (p_72726_1_.startsWith("blockcrack_")) {
            final String[] astring = p_72726_1_.split("_", 3);
            final Block block = Block.func_149729_e(Integer.parseInt(astring[1]));
            final int k = Integer.parseInt(astring[2]);
            entityfx = (EntityFX)new EntityDiggingFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_, block, k).func_90019_g(k);
        }
        else if (p_72726_1_.startsWith("blockdust_")) {
            final String[] astring = p_72726_1_.split("_", 3);
            final Block block = Block.func_149729_e(Integer.parseInt(astring[1]));
            final int k = Integer.parseInt(astring[2]);
            entityfx = (EntityFX)new MCH_EntityBlockDustFX((World)mc.field_71441_e, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_, block, k).func_90019_g(k);
        }
        if (entityfx != null) {
            mc.field_71452_i.func_78873_a(entityfx);
        }
        return entityfx;
    }
    
    public static void spawnParticle(final MCH_ParticleParam p) {
        if (p.world.field_72995_K) {
            MCH_EntityParticleBase entityFX = null;
            if (p.name.equalsIgnoreCase("Splash")) {
                entityFX = new MCH_EntityParticleSplash(p.world, p.posX, p.posY, p.posZ, p.motionX, p.motionY, p.motionZ);
            }
            else {
                entityFX = new MCH_EntityParticleSmoke(p.world, p.posX, p.posY, p.posZ, p.motionX, p.motionY, p.motionZ);
            }
            entityFX.func_70538_b(p.r, p.g, p.b);
            entityFX.func_82338_g(p.a);
            if (p.age > 0) {
                entityFX.setParticleMaxAge(p.age);
            }
            entityFX.moutionYUpAge = p.motionYUpAge;
            entityFX.gravity = p.gravity;
            entityFX.isEffectedWind = p.isEffectWind;
            entityFX.diffusible = p.diffusible;
            entityFX.toWhite = p.toWhite;
            if (p.diffusible) {
                entityFX.setParticleScale(p.size * 0.2f);
                entityFX.particleMaxScale = p.size * 2.0f;
            }
            else {
                entityFX.setParticleScale(p.size);
            }
            FMLClientHandler.instance().getClient().field_71452_i.func_78873_a((EntityFX)entityFX);
        }
    }
    
    public static void spawnMarkPoint(final EntityPlayer player, final double x, final double y, final double z) {
        clearMarkPoint();
        MCH_ParticlesUtil.markPoint = new MCH_EntityParticleMarkPoint(player.field_70170_p, x, y, z, player.func_96124_cp());
        FMLClientHandler.instance().getClient().field_71452_i.func_78873_a((EntityFX)MCH_ParticlesUtil.markPoint);
    }
    
    public static void clearMarkPoint() {
        if (MCH_ParticlesUtil.markPoint != null) {
            MCH_ParticlesUtil.markPoint.func_70106_y();
            MCH_ParticlesUtil.markPoint = null;
        }
    }
    
    static {
        MCH_ParticlesUtil.markPoint = null;
    }
}
