package mcheli;

import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.entity.item.*;
import mcheli.weapon.*;
import net.minecraft.enchantment.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import mcheli.wrapper.*;
import java.util.*;
import mcheli.flare.*;
import mcheli.particles.*;

public class MCH_Explosion extends Explosion
{
    public final int field_77289_h = 16;
    public World world;
    private static Random explosionRNG;
    public Map field_77288_k;
    public boolean isDestroyBlock;
    public int countSetFireEntity;
    public boolean isPlaySound;
    public boolean isInWater;
    ExplosionResult result;
    public EntityPlayer explodedPlayer;
    public float explosionSizeBlock;
    public MCH_DamageFactor damageFactor;
    
    public MCH_Explosion(final World par1World, final Entity exploder, final Entity player, final double x, final double y, final double z, final float size) {
        super(par1World, exploder, x, y, z, size);
        this.field_77288_k = new HashMap();
        this.damageFactor = null;
        this.world = par1World;
        this.isDestroyBlock = false;
        this.explosionSizeBlock = size;
        this.countSetFireEntity = 0;
        this.isPlaySound = true;
        this.isInWater = false;
        this.result = null;
        this.explodedPlayer = ((player instanceof EntityPlayer) ? player : null);
    }
    
    public boolean isRemote() {
        return this.world.field_72995_K;
    }
    
    public void func_77278_a() {
        final HashSet hashset = new HashSet();
        int i = 0;
        while (true) {
            final int n = i;
            this.getClass();
            if (n >= 16) {
                break;
            }
            int j = 0;
            while (true) {
                final int n2 = j;
                this.getClass();
                if (n2 >= 16) {
                    break;
                }
                int k = 0;
                while (true) {
                    final int n3 = k;
                    this.getClass();
                    if (n3 >= 16) {
                        break;
                    }
                    Label_0517: {
                        if (i != 0) {
                            final int n4 = i;
                            this.getClass();
                            if (n4 != 16 - 1 && j != 0) {
                                final int n5 = j;
                                this.getClass();
                                if (n5 != 16 - 1 && k != 0) {
                                    final int n6 = k;
                                    this.getClass();
                                    if (n6 != 16 - 1) {
                                        break Label_0517;
                                    }
                                }
                            }
                        }
                        final float n7 = i;
                        this.getClass();
                        double d3 = n7 / (16.0f - 1.0f) * 2.0f - 1.0f;
                        final float n8 = j;
                        this.getClass();
                        double d4 = n8 / (16.0f - 1.0f) * 2.0f - 1.0f;
                        final float n9 = k;
                        this.getClass();
                        double d5 = n9 / (16.0f - 1.0f) * 2.0f - 1.0f;
                        final double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
                        d3 /= d6;
                        d4 /= d6;
                        d5 /= d6;
                        float f1 = this.explosionSizeBlock * (0.7f + this.world.field_73012_v.nextFloat() * 0.6f);
                        double d7 = this.field_77284_b;
                        double d8 = this.field_77285_c;
                        double d9 = this.field_77282_d;
                        final float f2 = 0.3f;
                        while (f1 > 0.0f) {
                            final int l = MathHelper.func_76128_c(d7);
                            final int i2 = MathHelper.func_76128_c(d8);
                            final int j2 = MathHelper.func_76128_c(d9);
                            final int k2 = W_WorldFunc.getBlockId(this.world, l, i2, j2);
                            if (k2 > 0) {
                                final Block block = W_WorldFunc.getBlock(this.world, l, i2, j2);
                                float f3;
                                if (this.field_77283_e != null) {
                                    f3 = W_Entity.getBlockExplosionResistance(this.field_77283_e, this, this.world, l, i2, j2, block);
                                }
                                else {
                                    f3 = block.getExplosionResistance(this.field_77283_e, this.world, l, i2, j2, this.field_77284_b, this.field_77285_c, this.field_77282_d);
                                }
                                if (this.isInWater) {
                                    f3 *= this.world.field_73012_v.nextFloat() * 0.2f + 0.2f;
                                }
                                f1 -= (f3 + 0.3f) * 0.3f;
                            }
                            if (f1 > 0.0f && (this.field_77283_e == null || W_Entity.shouldExplodeBlock(this.field_77283_e, this, this.world, l, i2, j2, k2, f1))) {
                                hashset.add(new ChunkPosition(l, i2, j2));
                            }
                            d7 += d3 * 0.30000001192092896;
                            d8 += d4 * 0.30000001192092896;
                            d9 += d5 * 0.30000001192092896;
                            f1 -= 0.22500001f;
                        }
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
        final float f4 = this.field_77280_f;
        this.field_77281_g.addAll(hashset);
        this.field_77280_f *= 2.0f;
        i = MathHelper.func_76128_c(this.field_77284_b - this.field_77280_f - 1.0);
        int j = MathHelper.func_76128_c(this.field_77284_b + this.field_77280_f + 1.0);
        int k = MathHelper.func_76128_c(this.field_77285_c - this.field_77280_f - 1.0);
        final int l2 = MathHelper.func_76128_c(this.field_77285_c + this.field_77280_f + 1.0);
        final int i3 = MathHelper.func_76128_c(this.field_77282_d - this.field_77280_f - 1.0);
        final int j3 = MathHelper.func_76128_c(this.field_77282_d + this.field_77280_f + 1.0);
        final List list = this.world.func_72839_b(this.field_77283_e, W_AxisAlignedBB.getAABB(i, k, i3, j, l2, j3));
        final Vec3 vec3 = W_WorldFunc.getWorldVec3(this.world, this.field_77284_b, this.field_77285_c, this.field_77282_d);
        this.field_77283_e = (Entity)this.explodedPlayer;
        for (int k3 = 0; k3 < list.size(); ++k3) {
            final Entity entity = list.get(k3);
            final double d10 = entity.func_70011_f(this.field_77284_b, this.field_77285_c, this.field_77282_d) / this.field_77280_f;
            if (d10 <= 1.0) {
                double d7 = entity.field_70165_t - this.field_77284_b;
                double d8 = entity.field_70163_u + entity.func_70047_e() - this.field_77285_c;
                double d9 = entity.field_70161_v - this.field_77282_d;
                final double d11 = MathHelper.func_76133_a(d7 * d7 + d8 * d8 + d9 * d9);
                if (d11 != 0.0) {
                    d7 /= d11;
                    d8 /= d11;
                    d9 /= d11;
                    final double d12 = this.getBlockDensity(vec3, entity.field_70121_D);
                    final double d13 = (1.0 - d10) * d12;
                    float damage = (int)((d13 * d13 + d13) / 2.0 * 8.0 * this.field_77280_f + 1.0);
                    if (damage > 0.0f && this.result != null && !(entity instanceof EntityItem) && !(entity instanceof EntityExpBottle) && !(entity instanceof EntityXPOrb)) {
                        if (!W_Entity.isEntityFallingBlock(entity)) {
                            if (entity instanceof MCH_EntityBaseBullet && this.field_77283_e instanceof EntityPlayer) {
                                if (!W_Entity.isEqual(((MCH_EntityBaseBullet)entity).shootingEntity, this.field_77283_e)) {
                                    this.result.hitEntity = true;
                                    MCH_Lib.DbgLog(this.world, "MCH_Explosion.doExplosionA:Damage=%.1f:HitEntityBullet=" + entity.getClass(), damage);
                                }
                            }
                            else {
                                MCH_Lib.DbgLog(this.world, "MCH_Explosion.doExplosionA:Damage=%.1f:HitEntity=" + entity.getClass(), damage);
                                this.result.hitEntity = true;
                            }
                        }
                    }
                    MCH_Lib.applyEntityHurtResistantTimeConfig(entity);
                    final DamageSource ds = DamageSource.func_94539_a((Explosion)this);
                    final MCH_Config config = MCH_MOD.config;
                    damage = MCH_Config.applyDamageVsEntity(entity, ds, damage);
                    damage *= ((this.damageFactor != null) ? this.damageFactor.getDamageFactor(entity) : 1.0f);
                    W_Entity.attackEntityFrom(entity, ds, damage);
                    final double d14 = EnchantmentProtection.func_92092_a(entity, d13);
                    if (!(entity instanceof MCH_EntityBaseBullet)) {
                        final Entity entity2 = entity;
                        entity2.field_70159_w += d7 * d14 * 0.4;
                        final Entity entity3 = entity;
                        entity3.field_70181_x += d8 * d14 * 0.1;
                        final Entity entity4 = entity;
                        entity4.field_70179_y += d9 * d14 * 0.4;
                    }
                    if (entity instanceof EntityPlayer) {
                        this.field_77288_k.put(entity, W_WorldFunc.getWorldVec3(this.world, d7 * d13, d8 * d13, d9 * d13));
                    }
                    if (damage > 0.0f && this.countSetFireEntity > 0) {
                        final double fireFactor = 1.0 - d11 / this.field_77280_f;
                        if (fireFactor > 0.0) {
                            entity.func_70015_d((int)(fireFactor * this.countSetFireEntity));
                        }
                    }
                }
            }
        }
        this.field_77280_f = f4;
    }
    
    private double getBlockDensity(final Vec3 vec3, final AxisAlignedBB p_72842_2_) {
        final double d0 = 1.0 / ((p_72842_2_.field_72336_d - p_72842_2_.field_72340_a) * 2.0 + 1.0);
        final double d2 = 1.0 / ((p_72842_2_.field_72337_e - p_72842_2_.field_72338_b) * 2.0 + 1.0);
        final double d3 = 1.0 / ((p_72842_2_.field_72334_f - p_72842_2_.field_72339_c) * 2.0 + 1.0);
        if (d0 >= 0.0 && d2 >= 0.0 && d3 >= 0.0) {
            int i = 0;
            int j = 0;
            for (float f = 0.0f; f <= 1.0f; f += (float)d0) {
                for (float f2 = 0.0f; f2 <= 1.0f; f2 += (float)d2) {
                    for (float f3 = 0.0f; f3 <= 1.0f; f3 += (float)d3) {
                        final double d4 = p_72842_2_.field_72340_a + (p_72842_2_.field_72336_d - p_72842_2_.field_72340_a) * f;
                        final double d5 = p_72842_2_.field_72338_b + (p_72842_2_.field_72337_e - p_72842_2_.field_72338_b) * f2;
                        final double d6 = p_72842_2_.field_72339_c + (p_72842_2_.field_72334_f - p_72842_2_.field_72339_c) * f3;
                        if (this.world.func_147447_a(Vec3.func_72443_a(d4, d5, d6), vec3, false, true, false) == null) {
                            ++i;
                        }
                        ++j;
                    }
                }
            }
            return i / j;
        }
        return 0.0;
    }
    
    public void func_77279_a(final boolean par1) {
        if (this.isPlaySound) {
            W_WorldFunc.DEF_playSoundEffect(this.world, this.field_77284_b, this.field_77285_c, this.field_77282_d, "random.explode", 4.0f, (1.0f + (this.world.field_73012_v.nextFloat() - this.world.field_73012_v.nextFloat()) * 0.2f) * 0.7f);
        }
        if (this.field_82755_b) {
            for (final ChunkPosition chunkposition : this.field_77281_g) {
                final int i = W_ChunkPosition.getChunkPosX(chunkposition);
                final int j = W_ChunkPosition.getChunkPosY(chunkposition);
                final int k = W_ChunkPosition.getChunkPosZ(chunkposition);
                final int l = W_WorldFunc.getBlockId(this.world, i, j, k);
                if (l > 0 && this.isDestroyBlock && this.explosionSizeBlock > 0.0f) {
                    final MCH_Config config = MCH_MOD.config;
                    if (!MCH_Config.Explosion_DestroyBlock.prmBool) {
                        continue;
                    }
                    final Block block = W_Block.getBlockById(l);
                    if (block.func_149659_a((Explosion)this)) {
                        block.func_149690_a(this.world, i, j, k, this.world.func_72805_g(i, j, k), 1.0f / this.explosionSizeBlock, 0);
                    }
                    block.onBlockExploded(this.world, i, j, k, (Explosion)this);
                }
            }
        }
        if (this.field_77286_a) {
            final MCH_Config config2 = MCH_MOD.config;
            if (MCH_Config.Explosion_FlamingBlock.prmBool) {
                for (final ChunkPosition chunkposition : this.field_77281_g) {
                    final int i = W_ChunkPosition.getChunkPosX(chunkposition);
                    final int j = W_ChunkPosition.getChunkPosY(chunkposition);
                    final int k = W_ChunkPosition.getChunkPosZ(chunkposition);
                    final int l = W_WorldFunc.getBlockId(this.world, i, j, k);
                    final Block b = W_WorldFunc.getBlock(this.world, i, j - 1, k);
                    if (l == 0 && b != null && b.func_149662_c() && MCH_Explosion.explosionRNG.nextInt(3) == 0) {
                        W_WorldFunc.setBlock(this.world, i, j, k, (Block)W_Blocks.field_150480_ab);
                    }
                }
            }
        }
    }
    
    public ExplosionResult newExplosionResult() {
        return new ExplosionResult();
    }
    
    public static ExplosionResult newExplosion(final World w, final Entity entityExploded, final Entity player, final double x, final double y, final double z, final float size, final float sizeBlock, final boolean playSound, final boolean isSmoking, final boolean isFlaming, final boolean isDestroyBlock, final int countSetFireEntity) {
        return newExplosion(w, entityExploded, player, x, y, z, size, sizeBlock, playSound, isSmoking, isFlaming, isDestroyBlock, countSetFireEntity, null);
    }
    
    public static ExplosionResult newExplosion(final World w, final Entity entityExploded, final Entity player, final double x, final double y, final double z, final float size, final float sizeBlock, final boolean playSound, final boolean isSmoking, final boolean isFlaming, final boolean isDestroyBlock, final int countSetFireEntity, final MCH_DamageFactor df) {
        if (w.field_72995_K) {
            return null;
        }
        final MCH_Explosion exp = new MCH_Explosion(w, entityExploded, player, x, y, z, size);
        exp.field_82755_b = w.func_82736_K().func_82766_b("mobGriefing");
        exp.field_77286_a = isFlaming;
        exp.isDestroyBlock = isDestroyBlock;
        exp.explosionSizeBlock = sizeBlock;
        exp.countSetFireEntity = countSetFireEntity;
        exp.isPlaySound = playSound;
        exp.isInWater = false;
        exp.result = exp.newExplosionResult();
        exp.damageFactor = df;
        exp.func_77278_a();
        exp.func_77279_a(true);
        final MCH_PacketEffectExplosion.ExplosionParam param = MCH_PacketEffectExplosion.create();
        param.exploderID = W_Entity.getEntityId(entityExploded);
        param.posX = x;
        param.posY = y;
        param.posZ = z;
        param.size = size;
        param.inWater = false;
        MCH_PacketEffectExplosion.send(param);
        return exp.result;
    }
    
    public static ExplosionResult newExplosionInWater(final World w, final Entity entityExploded, final Entity player, final double x, final double y, final double z, final float size, final float sizeBlock, final boolean playSound, final boolean isSmoking, final boolean isFlaming, final boolean isDestroyBlock, final int countSetFireEntity, final MCH_DamageFactor df) {
        if (w.field_72995_K) {
            return null;
        }
        final MCH_Explosion exp = new MCH_Explosion(w, entityExploded, player, x, y, z, size);
        exp.field_82755_b = w.func_82736_K().func_82766_b("mobGriefing");
        exp.field_77286_a = isFlaming;
        exp.isDestroyBlock = isDestroyBlock;
        exp.explosionSizeBlock = sizeBlock;
        exp.countSetFireEntity = countSetFireEntity;
        exp.isPlaySound = playSound;
        exp.isInWater = true;
        exp.result = exp.newExplosionResult();
        exp.damageFactor = df;
        exp.func_77278_a();
        exp.func_77279_a(true);
        final MCH_PacketEffectExplosion.ExplosionParam param = MCH_PacketEffectExplosion.create();
        param.exploderID = W_Entity.getEntityId(entityExploded);
        param.posX = x;
        param.posY = y;
        param.posZ = z;
        param.size = size;
        param.inWater = true;
        MCH_PacketEffectExplosion.send(param);
        return exp.result;
    }
    
    public static void playExplosionSound(final World w, final double x, final double y, final double z) {
        final Random rand = new Random();
        W_WorldFunc.DEF_playSoundEffect(w, x, y, z, "random.explode", 4.0f, (1.0f + (rand.nextFloat() - rand.nextFloat()) * 0.2f) * 0.7f);
    }
    
    public static void effectExplosion(final World world, final Entity exploder, final double explosionX, final double explosionY, final double explosionZ, final float explosionSize, final boolean isSmoking) {
        final List affectedBlockPositions = new ArrayList();
        final int field_77289_h = 16;
        final HashSet hashset = new HashSet();
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                for (int k = 0; k < 16; ++k) {
                    if (i == 0 || i == 15 || j == 0 || j == 15 || k == 0 || k == 15) {
                        double d3 = i / 15.0f * 2.0f - 1.0f;
                        double d4 = j / 15.0f * 2.0f - 1.0f;
                        double d5 = k / 15.0f * 2.0f - 1.0f;
                        final double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
                        d3 /= d6;
                        d4 /= d6;
                        d5 /= d6;
                        float f1 = explosionSize * (0.7f + world.field_73012_v.nextFloat() * 0.6f);
                        double d7 = explosionX;
                        double d8 = explosionY;
                        double d9 = explosionZ;
                        for (float f2 = 0.3f; f1 > 0.0f; f1 -= f2 * 0.75f) {
                            final int l = MathHelper.func_76128_c(d7);
                            final int i2 = MathHelper.func_76128_c(d8);
                            final int j2 = MathHelper.func_76128_c(d9);
                            final int k2 = W_WorldFunc.getBlockId(world, l, i2, j2);
                            if (k2 > 0) {
                                final Block block = W_Block.getBlockById(k2);
                                final float f3 = block.getExplosionResistance(exploder, world, l, i2, j2, explosionX, explosionY, explosionZ);
                                f1 -= (f3 + 0.3f) * f2;
                            }
                            if (f1 > 0.0f) {
                                hashset.add(new ChunkPosition(l, i2, j2));
                            }
                            d7 += d3 * f2;
                            d8 += d4 * f2;
                            d9 += d5 * f2;
                        }
                    }
                }
            }
        }
        affectedBlockPositions.addAll(hashset);
        if (explosionSize >= 2.0f && isSmoking) {
            MCH_ParticlesUtil.DEF_spawnParticle("hugeexplosion", explosionX, explosionY, explosionZ, 1.0, 0.0, 0.0, 10.0f);
        }
        else {
            MCH_ParticlesUtil.DEF_spawnParticle("largeexplode", explosionX, explosionY, explosionZ, 1.0, 0.0, 0.0, 10.0f);
        }
        if (isSmoking) {
            final Iterator iterator = affectedBlockPositions.iterator();
            int cnt = 0;
            int flareCnt = (int)explosionSize;
            while (iterator.hasNext()) {
                final ChunkPosition chunkposition = iterator.next();
                final int i = W_ChunkPosition.getChunkPosX(chunkposition);
                final int j = W_ChunkPosition.getChunkPosY(chunkposition);
                final int k = W_ChunkPosition.getChunkPosZ(chunkposition);
                final int m = W_WorldFunc.getBlockId(world, i, j, k);
                ++cnt;
                final double d7 = i + world.field_73012_v.nextFloat();
                final double d8 = j + world.field_73012_v.nextFloat();
                final double d9 = k + world.field_73012_v.nextFloat();
                double mx = d7 - explosionX;
                double my = d8 - explosionY;
                double mz = d9 - explosionZ;
                final double d10 = MathHelper.func_76133_a(mx * mx + my * my + mz * mz);
                mx /= d10;
                my /= d10;
                mz /= d10;
                double d11 = 0.5 / (d10 / explosionSize + 0.1);
                d11 *= world.field_73012_v.nextFloat() * world.field_73012_v.nextFloat() + 0.3f;
                mx *= d11 * 0.5;
                my *= d11 * 0.5;
                mz *= d11 * 0.5;
                final double px = (d7 + explosionX * 1.0) / 2.0;
                final double py = (d8 + explosionY * 1.0) / 2.0;
                final double pz = (d9 + explosionZ * 1.0) / 2.0;
                final double r = 3.141592653589793 * world.field_73012_v.nextInt(360) / 180.0;
                if (explosionSize >= 4.0f && flareCnt > 0) {
                    final double a = Math.min(explosionSize / 12.0f, 0.6) * (0.5f + world.field_73012_v.nextFloat() * 0.5f);
                    world.func_72838_d((Entity)new MCH_EntityFlare(world, px, py + 2.0, pz, Math.sin(r) * a, (1.0 + my / 5.0) * a, Math.cos(r) * a, 2.0f, 0));
                    --flareCnt;
                }
                if (cnt % 4 == 0) {
                    final float bdf = Math.min(explosionSize / 3.0f, 2.0f) * (0.5f + world.field_73012_v.nextFloat() * 0.5f);
                    final boolean ret = MCH_ParticlesUtil.spawnParticleTileDust(world, (int)(px + 0.5), (int)(py - 0.5), (int)(pz + 0.5), px, py + 1.0, pz, Math.sin(r) * bdf, 0.5 + my / 5.0 * bdf, Math.cos(r) * bdf, Math.min(explosionSize / 2.0f, 3.0f) * (0.5f + world.field_73012_v.nextFloat() * 0.5f));
                }
                final int es = (int)((explosionSize >= 4.0f) ? explosionSize : 4.0f);
                if (explosionSize <= 1.0f || cnt % es == 0) {
                    if (world.field_73012_v.nextBoolean()) {
                        my *= 3.0;
                        mx *= 0.1;
                        mz *= 0.1;
                    }
                    else {
                        my *= 0.2;
                        mx *= 3.0;
                        mz *= 3.0;
                    }
                    final MCH_ParticleParam mch_ParticleParam3;
                    final MCH_ParticleParam mch_ParticleParam2;
                    final MCH_ParticleParam mch_ParticleParam;
                    final MCH_ParticleParam prm = mch_ParticleParam = (mch_ParticleParam2 = (mch_ParticleParam3 = new MCH_ParticleParam(world, "explode", px, py, pz, mx, my, mz, (explosionSize < 8.0f) ? ((explosionSize < 2.0f) ? 2.0f : (explosionSize * 2.0f)) : 16.0f)));
                    final float r2 = 0.3f + world.field_73012_v.nextFloat() * 0.4f;
                    mch_ParticleParam.b = r2;
                    mch_ParticleParam2.g = r2;
                    mch_ParticleParam3.r = r2;
                    final MCH_ParticleParam mch_ParticleParam4 = prm;
                    mch_ParticleParam4.r += 0.1f;
                    final MCH_ParticleParam mch_ParticleParam5 = prm;
                    mch_ParticleParam5.g += 0.05f;
                    final MCH_ParticleParam mch_ParticleParam6 = prm;
                    mch_ParticleParam6.b += 0.0f;
                    prm.age = 10 + world.field_73012_v.nextInt(30);
                    final MCH_ParticleParam mch_ParticleParam7 = prm;
                    mch_ParticleParam7.age *= (int)((explosionSize < 6.0f) ? explosionSize : 6.0f);
                    prm.age = prm.age * 2 / 3;
                    prm.diffusible = true;
                    MCH_ParticlesUtil.spawnParticle(prm);
                }
            }
        }
    }
    
    public static void DEF_effectExplosion(final World world, final Entity exploder, final double explosionX, final double explosionY, final double explosionZ, final float explosionSize, final boolean isSmoking) {
        final List affectedBlockPositions = new ArrayList();
        final int field_77289_h = 16;
        final HashSet hashset = new HashSet();
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                for (int k = 0; k < 16; ++k) {
                    if (i == 0 || i == 15 || j == 0 || j == 15 || k == 0 || k == 15) {
                        double d3 = i / 15.0f * 2.0f - 1.0f;
                        double d4 = j / 15.0f * 2.0f - 1.0f;
                        double d5 = k / 15.0f * 2.0f - 1.0f;
                        final double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
                        d3 /= d6;
                        d4 /= d6;
                        d5 /= d6;
                        float f1 = explosionSize * (0.7f + world.field_73012_v.nextFloat() * 0.6f);
                        double d7 = explosionX;
                        double d8 = explosionY;
                        double d9 = explosionZ;
                        for (float f2 = 0.3f; f1 > 0.0f; f1 -= f2 * 0.75f) {
                            final int l = MathHelper.func_76128_c(d7);
                            final int i2 = MathHelper.func_76128_c(d8);
                            final int j2 = MathHelper.func_76128_c(d9);
                            final int k2 = W_WorldFunc.getBlockId(world, l, i2, j2);
                            if (k2 > 0) {
                                final Block block = W_Block.getBlockById(k2);
                                final float f3 = block.getExplosionResistance(exploder, world, l, i2, j2, explosionX, explosionY, explosionZ);
                                f1 -= (f3 + 0.3f) * f2;
                            }
                            if (f1 > 0.0f) {
                                hashset.add(new ChunkPosition(l, i2, j2));
                            }
                            d7 += d3 * f2;
                            d8 += d4 * f2;
                            d9 += d5 * f2;
                        }
                    }
                }
            }
        }
        affectedBlockPositions.addAll(hashset);
        if (explosionSize >= 2.0f && isSmoking) {
            MCH_ParticlesUtil.DEF_spawnParticle("hugeexplosion", explosionX, explosionY, explosionZ, 1.0, 0.0, 0.0, 10.0f);
        }
        else {
            MCH_ParticlesUtil.DEF_spawnParticle("largeexplode", explosionX, explosionY, explosionZ, 1.0, 0.0, 0.0, 10.0f);
        }
        if (isSmoking) {
            for (final ChunkPosition chunkposition : affectedBlockPositions) {
                final int i = W_ChunkPosition.getChunkPosX(chunkposition);
                final int j = W_ChunkPosition.getChunkPosY(chunkposition);
                final int k = W_ChunkPosition.getChunkPosZ(chunkposition);
                final int m = W_WorldFunc.getBlockId(world, i, j, k);
                final double d7 = i + world.field_73012_v.nextFloat();
                final double d8 = j + world.field_73012_v.nextFloat();
                final double d9 = k + world.field_73012_v.nextFloat();
                double d10 = d7 - explosionX;
                double d11 = d8 - explosionY;
                double d12 = d9 - explosionZ;
                final double d13 = MathHelper.func_76133_a(d10 * d10 + d11 * d11 + d12 * d12);
                d10 /= d13;
                d11 /= d13;
                d12 /= d13;
                double d14 = 0.5 / (d13 / explosionSize + 0.1);
                d14 *= world.field_73012_v.nextFloat() * world.field_73012_v.nextFloat() + 0.3f;
                d10 *= d14;
                d11 *= d14;
                d12 *= d14;
                MCH_ParticlesUtil.DEF_spawnParticle("explode", (d7 + explosionX * 1.0) / 2.0, (d8 + explosionY * 1.0) / 2.0, (d9 + explosionZ * 1.0) / 2.0, d10, d11, d12, 10.0f);
                MCH_ParticlesUtil.DEF_spawnParticle("smoke", d7, d8, d9, d10, d11, d12, 10.0f);
            }
        }
    }
    
    public static void effectExplosionInWater(final World world, final Entity exploder, final double explosionX, final double explosionY, final double explosionZ, final float explosionSize, final boolean isSmoking) {
        if (explosionSize <= 0.0f) {
            return;
        }
        final int range = (int)(explosionSize + 0.5) / 1;
        final int ex = (int)(explosionX + 0.5);
        final int ey = (int)(explosionY + 0.5);
        final int ez = (int)(explosionZ + 0.5);
        for (int y = -range; y <= range; ++y) {
            if (ey + y >= 1) {
                for (int x = -range; x <= range; ++x) {
                    for (int z = -range; z <= range; ++z) {
                        final int d = x * x + y * y + z * z;
                        if (d < range * range && W_Block.func_149680_a(W_WorldFunc.getBlock(world, ex + x, ey + y, ez + z), W_Block.getWater())) {
                            for (int n = MCH_Explosion.explosionRNG.nextInt(2), i = 0; i < n; ++i) {
                                final MCH_ParticleParam prm = new MCH_ParticleParam(world, "splash", ex + x, ey + y, ez + z, x / range * (MCH_Explosion.explosionRNG.nextFloat() - 0.2), 1.0 - Math.sqrt(x * x + z * z) / range + MCH_Explosion.explosionRNG.nextFloat() * 0.4 * range * 0.4, z / range * (MCH_Explosion.explosionRNG.nextFloat() - 0.2), MCH_Explosion.explosionRNG.nextInt(range) * 3 + range);
                                MCH_ParticlesUtil.spawnParticle(prm);
                            }
                        }
                    }
                }
            }
        }
    }
    
    static {
        MCH_Explosion.explosionRNG = new Random();
    }
    
    public class ExplosionResult
    {
        public boolean hitEntity;
        
        public ExplosionResult() {
            this.hitEntity = false;
        }
    }
}
