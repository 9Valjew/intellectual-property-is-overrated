package mcheli.multiplay;

import mcheli.plane.*;
import mcheli.helicopter.*;
import mcheli.vehicle.*;
import mcheli.tank.*;
import net.minecraft.entity.*;
import mcheli.aircraft.*;
import mcheli.*;
import net.minecraft.command.*;
import net.minecraft.server.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.command.server.*;
import net.minecraft.scoreboard.*;
import java.util.*;
import net.minecraft.server.management.*;

public class MCH_Multiplay
{
    public static final MCH_TargetType[][] ENTITY_SPOT_TABLE;
    
    public static boolean canSpotEntityWithFilter(final int filter, final Entity entity) {
        if (entity instanceof MCP_EntityPlane) {
            return (filter & 0x20) != 0x0;
        }
        if (entity instanceof MCH_EntityHeli) {
            return (filter & 0x10) != 0x0;
        }
        if (entity instanceof MCH_EntityVehicle || entity instanceof MCH_EntityTank) {
            return (filter & 0x8) != 0x0;
        }
        if (entity instanceof EntityPlayer) {
            return (filter & 0x4) != 0x0;
        }
        if (!(entity instanceof EntityLivingBase)) {
            return false;
        }
        if (isMonster(entity)) {
            return (filter & 0x2) != 0x0;
        }
        return (filter & 0x1) != 0x0;
    }
    
    public static boolean isMonster(final Entity entity) {
        return entity.getClass().toString().toLowerCase().indexOf("monster") >= 0;
    }
    
    public static MCH_TargetType canSpotEntity(final Entity user, final double posX, final double posY, final double posZ, final Entity target, final boolean checkSee) {
        if (!(user instanceof EntityLivingBase)) {
            return MCH_TargetType.NONE;
        }
        final EntityLivingBase spotter = (EntityLivingBase)user;
        final int col = (spotter.func_96124_cp() != null) ? 1 : 0;
        int row = 0;
        if (target instanceof EntityLivingBase) {
            if (!isMonster(target)) {
                row = 1;
            }
            else {
                row = 2;
            }
        }
        if (spotter.func_96124_cp() != null) {
            if (target instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)target;
                if (player.func_96124_cp() == null) {
                    row = 3;
                }
                else if (spotter.func_142014_c((EntityLivingBase)player)) {
                    row = 4;
                }
                else {
                    row = 5;
                }
            }
            else if (target instanceof MCH_EntityAircraft) {
                final MCH_EntityAircraft ac = (MCH_EntityAircraft)target;
                final EntityPlayer rideEntity = ac.getFirstMountPlayer();
                if (rideEntity == null) {
                    row = 6;
                }
                else if (rideEntity.func_96124_cp() == null) {
                    row = 7;
                }
                else if (spotter.func_142014_c((EntityLivingBase)rideEntity)) {
                    row = 8;
                }
                else {
                    row = 9;
                }
            }
        }
        else if (target instanceof EntityPlayer || target instanceof MCH_EntityAircraft) {
            row = 0;
        }
        MCH_TargetType ret = MCH_Multiplay.ENTITY_SPOT_TABLE[row][col];
        if (checkSee && ret != MCH_TargetType.NONE) {
            final Vec3 vs = Vec3.func_72443_a(posX, posY, posZ);
            final Vec3 ve = Vec3.func_72443_a(target.field_70165_t, target.field_70163_u + target.func_70047_e(), target.field_70161_v);
            final MovingObjectPosition mop = target.field_70170_p.func_72933_a(vs, ve);
            if (mop != null && mop.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK) {
                ret = MCH_TargetType.NONE;
            }
        }
        return ret;
    }
    
    public static boolean canAttackEntity(final DamageSource ds, final Entity target) {
        return canAttackEntity(ds.func_76346_g(), target);
    }
    
    public static boolean canAttackEntity(final Entity attacker, final Entity target) {
        if (attacker != null && target != null) {
            EntityPlayer attackPlayer = null;
            EntityPlayer targetPlayer = null;
            if (attacker instanceof EntityPlayer) {
                attackPlayer = (EntityPlayer)attacker;
            }
            if (target instanceof EntityPlayer) {
                targetPlayer = (EntityPlayer)target;
            }
            else if (target.field_70153_n instanceof EntityPlayer) {
                targetPlayer = (EntityPlayer)target.field_70153_n;
            }
            if (target instanceof MCH_EntityAircraft) {
                final MCH_EntityAircraft ac = (MCH_EntityAircraft)target;
                if (ac.getRiddenByEntity() instanceof EntityPlayer) {
                    targetPlayer = (EntityPlayer)ac.getRiddenByEntity();
                }
            }
            if (attackPlayer != null && targetPlayer != null && !attackPlayer.func_96122_a(targetPlayer)) {
                return false;
            }
        }
        return true;
    }
    
    public static void jumpSpawnPoint(final EntityPlayer player) {
        MCH_Lib.DbgLog(false, "JumpSpawnPoint", new Object[0]);
        final CommandTeleport cmd = new CommandTeleport();
        if (cmd.func_71519_b((ICommandSender)player)) {
            final MinecraftServer minecraftServer = MinecraftServer.func_71276_C();
            for (final String playerName : minecraftServer.func_71203_ab().func_72369_d()) {
                final EntityPlayerMP jumpPlayer = CommandTeleport.func_82359_c((ICommandSender)player, playerName);
                ChunkCoordinates cc = null;
                if (jumpPlayer != null && jumpPlayer.field_71093_bK == player.field_71093_bK) {
                    cc = jumpPlayer.getBedLocation(jumpPlayer.field_71093_bK);
                    if (cc != null) {
                        cc = EntityPlayer.func_71056_a((World)minecraftServer.func_71218_a(jumpPlayer.field_71093_bK), cc, true);
                    }
                    if (cc == null) {
                        cc = jumpPlayer.field_70170_p.field_73011_w.getRandomizedSpawnPoint();
                    }
                }
                if (cc != null) {
                    final String[] cmdStr = { playerName, String.format("%.1f", cc.field_71574_a + 0.5), String.format("%.1f", cc.field_71572_b + 0.1), String.format("%.1f", cc.field_71573_c + 0.5) };
                    cmd.func_71515_b((ICommandSender)player, cmdStr);
                }
            }
        }
    }
    
    public static void shuffleTeam(final EntityPlayer player) {
        final Collection teams = player.field_70170_p.func_96441_U().func_96525_g();
        final int teamNum = teams.size();
        MCH_Lib.DbgLog(false, "ShuffleTeam:%d teams ----------", teamNum);
        if (teamNum > 0) {
            final CommandScoreboard cmd = new CommandScoreboard();
            if (cmd.func_71519_b((ICommandSender)player)) {
                final List<String> list = Arrays.asList(MinecraftServer.func_71276_C().func_71203_ab().func_72369_d());
                Collections.shuffle(list);
                final ArrayList<String> listTeam = new ArrayList<String>();
                for (final Object o : teams) {
                    final ScorePlayerTeam team = (ScorePlayerTeam)o;
                    listTeam.add(team.func_96661_b());
                }
                Collections.shuffle(listTeam);
                int i = 0;
                int j = 0;
                while (i < list.size()) {
                    listTeam.set(j, listTeam.get(j) + " " + list.get(i));
                    if (++j >= teamNum) {
                        j = 0;
                    }
                    ++i;
                }
                for (int k = 0; k < listTeam.size(); ++k) {
                    final String exe_cmd = "teams join " + listTeam.get(k);
                    final String[] process_cmd = exe_cmd.split(" ");
                    if (process_cmd.length > 3) {
                        MCH_Lib.DbgLog(false, "ShuffleTeam:" + exe_cmd, new Object[0]);
                        cmd.func_71515_b((ICommandSender)player, process_cmd);
                    }
                }
            }
        }
    }
    
    public static boolean spotEntity(final EntityPlayer player, final MCH_EntityAircraft ac, final double posX, final double posY, final double posZ, final int targetFilter, final float spotLength, final int markTime, final float angle) {
        boolean ret = false;
        if (!player.field_70170_p.field_72995_K) {
            float acYaw = 0.0f;
            float acPitch = 0.0f;
            float acRoll = 0.0f;
            if (ac != null) {
                acYaw = ac.getRotYaw();
                acPitch = ac.getRotPitch();
                acRoll = ac.getRotRoll();
            }
            final Vec3 vv = MCH_Lib.RotVec3(0.0, 0.0, 1.0, -player.field_70177_z, -player.field_70125_A, -acRoll);
            final double tx = vv.field_72450_a;
            final double tz = vv.field_72449_c;
            final List list = player.field_70170_p.func_72839_b((Entity)player, player.field_70121_D.func_72314_b((double)spotLength, (double)spotLength, (double)spotLength));
            final List<Integer> entityList = new ArrayList<Integer>();
            final Vec3 pos = Vec3.func_72443_a(posX, posY, posZ);
            for (int i = 0; i < list.size(); ++i) {
                final Entity entity = list.get(i);
                if (canSpotEntityWithFilter(targetFilter, entity)) {
                    final MCH_TargetType stopType = canSpotEntity((Entity)player, posX, posY, posZ, entity, true);
                    if (stopType != MCH_TargetType.NONE && stopType != MCH_TargetType.SAME_TEAM_PLAYER) {
                        final double dist = entity.func_70092_e(pos.field_72450_a, pos.field_72448_b, pos.field_72449_c);
                        if (dist > 1.0 && dist < spotLength * spotLength) {
                            final double cx = entity.field_70165_t - pos.field_72450_a;
                            final double cy = entity.field_70163_u - pos.field_72448_b;
                            final double cz = entity.field_70161_v - pos.field_72449_c;
                            final double h = MCH_Lib.getPosAngle(tx, tz, cx, cz);
                            double v = Math.atan2(cy, Math.sqrt(cx * cx + cz * cz)) * 180.0 / 3.141592653589793;
                            v = Math.abs(v + player.field_70125_A);
                            if (h < angle * 2.0f && v < angle * 2.0f) {
                                entityList.add(entity.func_145782_y());
                            }
                        }
                    }
                }
            }
            if (entityList.size() > 0) {
                final int[] entityId = new int[entityList.size()];
                for (int j = 0; j < entityId.length; ++j) {
                    entityId[j] = entityList.get(j);
                }
                sendSpotedEntityListToSameTeam(player, markTime, entityId);
                ret = true;
            }
            else {
                ret = false;
            }
        }
        return ret;
    }
    
    public static void sendSpotedEntityListToSameTeam(final EntityPlayer player, final int count, final int[] entityId) {
        final ServerConfigurationManager svCnf = MinecraftServer.func_71276_C().func_71203_ab();
        for (final EntityPlayer notifyPlayer : svCnf.field_72404_b) {
            if (player == notifyPlayer || player.func_142014_c((EntityLivingBase)notifyPlayer)) {
                MCH_PacketNotifySpotedEntity.send(notifyPlayer, count, entityId);
            }
        }
    }
    
    public static boolean markPoint(final EntityPlayer player, final double posX, final double posY, final double posZ) {
        final Vec3 vs = Vec3.func_72443_a(posX, posY, posZ);
        Vec3 ve = MCH_Lib.Rot2Vec3(player.field_70177_z, player.field_70125_A);
        ve = vs.func_72441_c(ve.field_72450_a * 300.0, ve.field_72448_b * 300.0, ve.field_72449_c * 300.0);
        final MovingObjectPosition mop = player.field_70170_p.func_72901_a(vs, ve, true);
        if (mop != null && mop.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK) {
            sendMarkPointToSameTeam(player, mop.field_72311_b, mop.field_72312_c + 2, mop.field_72309_d);
            return true;
        }
        sendMarkPointToSameTeam(player, 0, 1000, 0);
        return false;
    }
    
    public static void sendMarkPointToSameTeam(final EntityPlayer player, final int x, final int y, final int z) {
        final ServerConfigurationManager svCnf = MinecraftServer.func_71276_C().func_71203_ab();
        for (final EntityPlayer notifyPlayer : svCnf.field_72404_b) {
            if (player == notifyPlayer || player.func_142014_c((EntityLivingBase)notifyPlayer)) {
                MCH_PacketNotifyMarkPoint.send(notifyPlayer, x, y, z);
            }
        }
    }
    
    static {
        ENTITY_SPOT_TABLE = new MCH_TargetType[][] { { MCH_TargetType.NONE, MCH_TargetType.NONE }, { MCH_TargetType.OTHER_MOB, MCH_TargetType.OTHER_MOB }, { MCH_TargetType.MONSTER, MCH_TargetType.MONSTER }, { MCH_TargetType.NONE, MCH_TargetType.NO_TEAM_PLAYER }, { MCH_TargetType.NONE, MCH_TargetType.SAME_TEAM_PLAYER }, { MCH_TargetType.NONE, MCH_TargetType.OTHER_TEAM_PLAYER }, { MCH_TargetType.NONE, MCH_TargetType.NONE }, { MCH_TargetType.NONE, MCH_TargetType.NO_TEAM_PLAYER }, { MCH_TargetType.NONE, MCH_TargetType.SAME_TEAM_PLAYER }, { MCH_TargetType.NONE, MCH_TargetType.OTHER_TEAM_PLAYER } };
    }
}
