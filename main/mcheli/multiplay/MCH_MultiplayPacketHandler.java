package mcheli.multiplay;

import com.google.common.io.*;
import net.minecraft.server.*;
import mcheli.*;
import net.minecraft.entity.player.*;
import net.minecraft.command.*;
import net.minecraft.command.server.*;
import mcheli.aircraft.*;
import java.util.*;
import java.io.*;
import net.minecraft.util.*;
import org.apache.logging.log4j.*;
import java.text.*;

public class MCH_MultiplayPacketHandler
{
    private static final Logger logger;
    private static DateFormat dateFormat;
    private static byte[] imageData;
    private static String lastPlayerName;
    private static double lastDataPercent;
    public static EntityPlayer modListRequestPlayer;
    private static int playerInfoId;
    
    public static void onPacket_Command(final EntityPlayer player, final ByteArrayDataInput data) {
        if (player.field_70170_p.field_72995_K) {
            return;
        }
        final MinecraftServer minecraftServer = MinecraftServer.func_71276_C();
        if (minecraftServer == null) {
            return;
        }
        final MCH_PacketIndMultiplayCommand pc = new MCH_PacketIndMultiplayCommand();
        pc.readData(data);
        MCH_Lib.DbgLog(false, "MCH_MultiplayPacketHandler.onPacket_Command cmd:%d:%s", pc.CmdID, pc.CmdStr);
        switch (pc.CmdID) {
            case 256: {
                MCH_Multiplay.shuffleTeam(player);
                break;
            }
            case 512: {
                MCH_Multiplay.jumpSpawnPoint(player);
                break;
            }
            case 768: {
                final ICommandManager icommandmanager = minecraftServer.func_71187_D();
                icommandmanager.func_71556_a((ICommandSender)player, pc.CmdStr);
                break;
            }
            case 1024: {
                if (new CommandScoreboard().func_71519_b((ICommandSender)player)) {
                    minecraftServer.func_71188_g(!minecraftServer.func_71219_W());
                    MCH_PacketNotifyServerSettings.send(null);
                    break;
                }
                break;
            }
            case 1280: {
                destoryAllAircraft(player);
                break;
            }
            default: {
                MCH_Lib.DbgLog(false, "MCH_MultiplayPacketHandler.onPacket_Command unknown cmd:%d:%s", pc.CmdID, pc.CmdStr);
                break;
            }
        }
    }
    
    private static void destoryAllAircraft(final EntityPlayer player) {
        final CommandSummon cmd = new CommandSummon();
        if (cmd.func_71519_b((ICommandSender)player)) {
            for (final Object e : player.field_70170_p.field_72996_f) {
                if (e instanceof MCH_EntityAircraft) {
                    ((MCH_EntityAircraft)e).func_70106_y();
                }
            }
        }
    }
    
    public static void onPacket_NotifySpotedEntity(final EntityPlayer player, final ByteArrayDataInput data) {
        if (!player.field_70170_p.field_72995_K) {
            return;
        }
        final MCH_PacketNotifySpotedEntity pc = new MCH_PacketNotifySpotedEntity();
        pc.readData(data);
        if (pc.count > 0) {
            for (int i = 0; i < pc.num; ++i) {
                MCH_GuiTargetMarker.addSpotedEntity(pc.entityId[i], pc.count);
            }
        }
    }
    
    public static void onPacket_NotifyMarkPoint(final EntityPlayer player, final ByteArrayDataInput data) {
        if (!player.field_70170_p.field_72995_K) {
            return;
        }
        final MCH_PacketNotifyMarkPoint pc = new MCH_PacketNotifyMarkPoint();
        pc.readData(data);
        MCH_GuiTargetMarker.markPoint(pc.px, pc.py, pc.pz);
    }
    
    public static void onPacket_LargeData(final EntityPlayer player, final ByteArrayDataInput data) {
        if (player.field_70170_p.field_72995_K) {
            return;
        }
        try {
            final MinecraftServer minecraftServer = MinecraftServer.func_71276_C();
            if (minecraftServer == null) {
                return;
            }
            final MCH_PacketLargeData pc = new MCH_PacketLargeData();
            pc.readData(data);
            if (pc.imageDataIndex < 0 || pc.imageDataTotalSize <= 0) {
                return;
            }
            if (pc.imageDataIndex == 0) {
                if (MCH_MultiplayPacketHandler.imageData != null && !MCH_MultiplayPacketHandler.lastPlayerName.isEmpty()) {
                    LogError("[mcheli]Err1:Saving the %s screen shot to server FAILED!!!", MCH_MultiplayPacketHandler.lastPlayerName);
                }
                MCH_MultiplayPacketHandler.imageData = new byte[pc.imageDataTotalSize];
                MCH_MultiplayPacketHandler.lastPlayerName = player.getDisplayName();
                MCH_MultiplayPacketHandler.lastDataPercent = 0.0;
            }
            final double dataPercent = (pc.imageDataIndex + pc.imageDataSize) / pc.imageDataTotalSize * 100.0;
            if (dataPercent - MCH_MultiplayPacketHandler.lastDataPercent >= 10.0 || MCH_MultiplayPacketHandler.lastDataPercent == 0.0) {
                LogInfo("[mcheli]Saving the %s screen shot to server. %.0f%% : %dbyte / %dbyte", player.getDisplayName(), dataPercent, pc.imageDataIndex, pc.imageDataTotalSize);
                MCH_MultiplayPacketHandler.lastDataPercent = dataPercent;
            }
            if (MCH_MultiplayPacketHandler.imageData == null) {
                if (MCH_MultiplayPacketHandler.imageData != null && !MCH_MultiplayPacketHandler.lastPlayerName.isEmpty()) {
                    LogError("[mcheli]Err2:Saving the %s screen shot to server FAILED!!!", player.getDisplayName());
                }
                MCH_MultiplayPacketHandler.imageData = null;
                MCH_MultiplayPacketHandler.lastPlayerName = "";
                MCH_MultiplayPacketHandler.lastDataPercent = 0.0;
                return;
            }
            for (int i = 0; i < pc.imageDataSize; ++i) {
                MCH_MultiplayPacketHandler.imageData[pc.imageDataIndex + i] = pc.buf[i];
            }
            if (pc.imageDataIndex + pc.imageDataSize >= pc.imageDataTotalSize) {
                DataOutputStream dos = null;
                final String dt = MCH_MultiplayPacketHandler.dateFormat.format(new Date()).toString();
                File file = new File("screenshots_op");
                file.mkdir();
                file = new File(file, player.getDisplayName() + "_" + dt + ".png");
                final String s = file.getAbsolutePath();
                LogInfo("[mcheli]Save Screenshot has been completed: %s", s);
                final FileOutputStream fos = new FileOutputStream(s);
                dos = new DataOutputStream(fos);
                dos.write(MCH_MultiplayPacketHandler.imageData);
                dos.flush();
                dos.close();
                MCH_MultiplayPacketHandler.imageData = null;
                MCH_MultiplayPacketHandler.lastPlayerName = "";
                MCH_MultiplayPacketHandler.lastDataPercent = 0.0;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void LogInfo(final String format, final Object... args) {
        MCH_MultiplayPacketHandler.logger.info(String.format(format, args));
    }
    
    public static void LogError(final String format, final Object... args) {
        MCH_MultiplayPacketHandler.logger.error(String.format(format, args));
    }
    
    public static void onPacket_IndClient(final EntityPlayer player, final ByteArrayDataInput data) {
        if (!player.field_70170_p.field_72995_K) {
            return;
        }
        final MCH_PacketIndClient pc = new MCH_PacketIndClient();
        pc.readData(data);
        if (pc.CmdID == 1) {
            MCH_MultiplayClient.startSendImageData();
        }
        else if (pc.CmdID == 2) {
            MCH_MultiplayClient.sendModsInfo(player.getDisplayName(), Integer.parseInt(pc.CmdStr));
        }
    }
    
    public static int getPlayerInfoId(final EntityPlayer player) {
        MCH_MultiplayPacketHandler.modListRequestPlayer = player;
        ++MCH_MultiplayPacketHandler.playerInfoId;
        if (MCH_MultiplayPacketHandler.playerInfoId > 1000000) {
            MCH_MultiplayPacketHandler.playerInfoId = 1;
        }
        return MCH_MultiplayPacketHandler.playerInfoId;
    }
    
    public static void onPacket_ModList(final EntityPlayer player, final ByteArrayDataInput data) {
        final MCH_PacketModList pc = new MCH_PacketModList();
        pc.readData(data);
        MCH_Lib.DbgLog(player.field_70170_p, "MCH_MultiplayPacketHandler.onPacket_ModList : ID=%d, Num=%d", pc.id, pc.num);
        if (player.field_70170_p.field_72995_K) {
            if (pc.firstData) {
                MCH_Lib.Log(EnumChatFormatting.RED + "###### " + player.getDisplayName() + " ######", new Object[0]);
                player.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.RED + "###### " + player.getDisplayName() + " ######"));
            }
            for (final String s : pc.list) {
                MCH_Lib.Log(s, new Object[0]);
                player.func_145747_a((IChatComponent)new ChatComponentText(s));
            }
        }
        else if (pc.id == MCH_MultiplayPacketHandler.playerInfoId) {
            if (MCH_MultiplayPacketHandler.modListRequestPlayer != null) {
                MCH_PacketModList.send(MCH_MultiplayPacketHandler.modListRequestPlayer, pc);
            }
            else {
                if (pc.firstData) {
                    LogInfo("###### " + player.getDisplayName() + " ######", new Object[0]);
                }
                for (final String s : pc.list) {
                    LogInfo(s, new Object[0]);
                }
            }
        }
    }
    
    static {
        logger = LogManager.getLogger();
        MCH_MultiplayPacketHandler.dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
        MCH_MultiplayPacketHandler.imageData = null;
        MCH_MultiplayPacketHandler.lastPlayerName = "";
        MCH_MultiplayPacketHandler.lastDataPercent = 0.0;
        MCH_MultiplayPacketHandler.modListRequestPlayer = null;
        MCH_MultiplayPacketHandler.playerInfoId = 0;
    }
}
