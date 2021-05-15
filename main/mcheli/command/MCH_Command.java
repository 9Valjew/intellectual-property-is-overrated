package mcheli.command;

import net.minecraft.entity.*;
import net.minecraftforge.event.*;
import net.minecraft.entity.player.*;
import mcheli.multiplay.*;
import mcheli.*;
import org.apache.commons.lang3.exception.*;
import com.google.gson.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;
import net.minecraft.command.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.*;
import net.minecraft.item.*;
import net.minecraft.server.*;

public class MCH_Command extends CommandBase
{
    public static final String CMD_GET_SS = "sendss";
    public static final String CMD_MOD_LIST = "modlist";
    public static final String CMD_RECONFIG = "reconfig";
    public static final String CMD_TITLE = "title";
    public static final String CMD_FILL = "fill";
    public static final String CMD_STATUS = "status";
    public static final String CMD_KILL_ENTITY = "killentity";
    public static final String CMD_REMOVE_ENTITY = "removeentity";
    public static final String CMD_ATTACK_ENTITY = "attackentity";
    public static final String CMD_SHOW_BB = "showboundingbox";
    public static final String CMD_LIST = "list";
    public static String[] ALL_COMMAND;
    public static MCH_Command instance;
    
    public static boolean canUseCommand(final Entity player) {
        return player instanceof EntityPlayer && MCH_Command.instance.func_71519_b((ICommandSender)player);
    }
    
    public String func_71517_b() {
        return "mcheli";
    }
    
    public static boolean checkCommandPermission(final ICommandSender sender, final String cmd) {
        if (new CommandGameMode().func_71519_b(sender)) {
            return true;
        }
        if (sender instanceof EntityPlayer && cmd.length() > 0) {
            final String playerName = ((EntityPlayer)sender).func_146103_bH().getName();
            final MCH_Config config = MCH_MOD.config;
            for (final MCH_Config.CommandPermission c : MCH_Config.CommandPermissionList) {
                if (c.name.equals(cmd)) {
                    for (final String s : c.players) {
                        if (s.equalsIgnoreCase(playerName)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public static void onCommandEvent(final CommandEvent event) {
        if (!(event.command instanceof MCH_Command)) {
            return;
        }
        if (event.parameters.length <= 0 || event.parameters[0].length() <= 0) {
            event.setCanceled(true);
            return;
        }
        if (!checkCommandPermission(event.sender, event.parameters[0])) {
            event.setCanceled(true);
            final ChatComponentTranslation c = new ChatComponentTranslation("commands.generic.permission", new Object[0]);
            c.func_150256_b().func_150238_a(EnumChatFormatting.RED);
            event.sender.func_145747_a((IChatComponent)c);
        }
    }
    
    public boolean func_71519_b(final ICommandSender player) {
        return true;
    }
    
    public String func_71518_a(final ICommandSender p_71518_1_) {
        return "commands.mcheli.usage";
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] prm) {
        final MCH_Config config = MCH_MOD.config;
        if (!MCH_Config.EnableCommand.prmBool) {
            return;
        }
        if (!checkCommandPermission(sender, prm[0])) {
            final ChatComponentTranslation c = new ChatComponentTranslation("commands.generic.permission", new Object[0]);
            c.func_150256_b().func_150238_a(EnumChatFormatting.RED);
            sender.func_145747_a((IChatComponent)c);
            return;
        }
        if (prm[0].equalsIgnoreCase("sendss")) {
            if (prm.length != 2) {
                throw new CommandException("Parameter error! : /mcheli sendss playerName", new Object[0]);
            }
            final EntityPlayerMP player = func_82359_c(sender, prm[1]);
            if (player != null) {
                MCH_PacketIndClient.send((EntityPlayer)player, 1, prm[1]);
            }
        }
        else if (prm[0].equalsIgnoreCase("modlist")) {
            if (prm.length != 2) {
                throw new CommandException("Parameter error! : /mcheli modlist playerName", new Object[0]);
            }
            final EntityPlayerMP reqPlayer = (sender instanceof EntityPlayerMP) ? sender : null;
            final EntityPlayerMP player2 = func_82359_c(sender, prm[1]);
            if (player2 != null) {
                MCH_PacketIndClient.send((EntityPlayer)player2, 2, "" + MCH_MultiplayPacketHandler.getPlayerInfoId((EntityPlayer)reqPlayer));
            }
        }
        else if (prm[0].equalsIgnoreCase("reconfig")) {
            if (prm.length != 1) {
                throw new CommandException("Parameter error! : /mcheli reconfig", new Object[0]);
            }
            MCH_MOD.proxy.reconfig();
            if (sender.func_130014_f_() != null && !sender.func_130014_f_().field_72995_K) {
                MCH_PacketNotifyServerSettings.sendAll();
            }
            if (MCH_MOD.proxy.isSinglePlayer()) {
                sender.func_145747_a((IChatComponent)new ChatComponentText("Reload mcheli.cfg"));
            }
            else {
                sender.func_145747_a((IChatComponent)new ChatComponentText("Reload server side mcheli.cfg"));
            }
        }
        else if (prm[0].equalsIgnoreCase("title")) {
            if (prm.length < 4) {
                throw new WrongUsageException("Parameter error! : /mcheli title time[1~180] position[0~4] messege[JSON format]", new Object[0]);
            }
            final String s = func_82360_a(sender, prm, 3);
            int showTime = Integer.valueOf(prm[1]);
            if (showTime < 1) {
                showTime = 1;
            }
            if (showTime > 180) {
                showTime = 180;
            }
            int pos = Integer.valueOf(prm[2]);
            if (pos < 0) {
                pos = 0;
            }
            if (pos > 5) {
                pos = 5;
            }
            try {
                final IChatComponent ichatcomponent = IChatComponent.Serializer.func_150699_a(s);
                MCH_PacketTitle.send(ichatcomponent, 20 * showTime, pos);
            }
            catch (JsonParseException jsonparseexception) {
                final Throwable throwable = ExceptionUtils.getRootCause((Throwable)jsonparseexception);
                throw new SyntaxErrorException("mcheli.title.jsonException", new Object[] { (throwable == null) ? "" : throwable.getMessage() });
            }
        }
        else if (prm[0].equalsIgnoreCase("fill")) {
            this.executeFill(sender, prm);
        }
        else if (prm[0].equalsIgnoreCase("status")) {
            this.executeStatus(sender, prm);
        }
        else if (prm[0].equalsIgnoreCase("killentity")) {
            this.executeKillEntity(sender, prm);
        }
        else if (prm[0].equalsIgnoreCase("removeentity")) {
            this.executeRemoveEntity(sender, prm);
        }
        else if (prm[0].equalsIgnoreCase("attackentity")) {
            this.executeAttackEntity(sender, prm);
        }
        else if (prm[0].equalsIgnoreCase("showboundingbox")) {
            if (prm.length != 2) {
                throw new CommandException("Parameter error! : /mcheli showboundingbox true or false", new Object[0]);
            }
            if (!func_110662_c(sender, prm[1])) {
                final MCH_Config config2 = MCH_MOD.config;
                MCH_Config.EnableDebugBoundingBox.prmBool = false;
                MCH_PacketNotifyServerSettings.sendAll();
                sender.func_145747_a((IChatComponent)new ChatComponentText("Disabled bounding box"));
            }
            else {
                final MCH_Config config3 = MCH_MOD.config;
                MCH_Config.EnableDebugBoundingBox.prmBool = true;
                MCH_PacketNotifyServerSettings.sendAll();
                sender.func_145747_a((IChatComponent)new ChatComponentText("Enabled bounding box [F3 + b]"));
            }
        }
        else {
            if (!prm[0].equalsIgnoreCase("list")) {
                throw new CommandException("Unknown mcheli command. please type /mcheli list", new Object[0]);
            }
            String msg = "";
            for (final String s2 : MCH_Command.ALL_COMMAND) {
                msg = msg + s2 + ", ";
            }
            sender.func_145747_a((IChatComponent)new ChatComponentText("/mcheli command list : " + msg));
        }
    }
    
    private void executeAttackEntity(final ICommandSender sender, final String[] args) {
        if (args.length < 3) {
            throw new WrongUsageException("/mcheli attackentity <entity class name : example1 EntityBat , example2 minecraft.entity.passive> <damage> [damage source]", new Object[0]);
        }
        final String className = args[1].toLowerCase();
        final float damage = Float.valueOf(args[2]);
        final String damageName = (args.length >= 4) ? args[3].toLowerCase() : "";
        DamageSource ds = DamageSource.field_76377_j;
        if (!damageName.isEmpty()) {
            if (damageName.equals("player")) {
                if (sender instanceof EntityPlayer) {
                    ds = DamageSource.func_76365_a((EntityPlayer)sender);
                }
            }
            else if (damageName.equals("anvil")) {
                ds = DamageSource.field_82728_o;
            }
            else if (damageName.equals("cactus")) {
                ds = DamageSource.field_76367_g;
            }
            else if (damageName.equals("drown")) {
                ds = DamageSource.field_76369_e;
            }
            else if (damageName.equals("fall")) {
                ds = DamageSource.field_76379_h;
            }
            else if (damageName.equals("fallingblock")) {
                ds = DamageSource.field_82729_p;
            }
            else if (damageName.equals("generic")) {
                ds = DamageSource.field_76377_j;
            }
            else if (damageName.equals("infire")) {
                ds = DamageSource.field_76372_a;
            }
            else if (damageName.equals("inwall")) {
                ds = DamageSource.field_76368_d;
            }
            else if (damageName.equals("lava")) {
                ds = DamageSource.field_76371_c;
            }
            else if (damageName.equals("magic")) {
                ds = DamageSource.field_76376_m;
            }
            else if (damageName.equals("onfire")) {
                ds = DamageSource.field_76370_b;
            }
            else if (damageName.equals("starve")) {
                ds = DamageSource.field_76366_f;
            }
            else if (damageName.equals("wither")) {
                ds = DamageSource.field_82727_n;
            }
        }
        int attacked = 0;
        final List list = sender.func_130014_f_().field_72996_f;
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) != null && !(list.get(i) instanceof EntityPlayer) && list.get(i).getClass().getName().toLowerCase().indexOf(className) >= 0) {
                list.get(i).func_70097_a(ds, damage);
                ++attacked;
            }
        }
        sender.func_145747_a((IChatComponent)new ChatComponentText(attacked + " entity attacked(" + args[1] + ", damage=" + damage + ")."));
    }
    
    private void executeKillEntity(final ICommandSender sender, final String[] args) {
        if (args.length < 2) {
            throw new WrongUsageException("/mcheli killentity <entity class name : example1 EntityBat , example2 minecraft.entity.passive>", new Object[0]);
        }
        final String className = args[1].toLowerCase();
        int killed = 0;
        final List list = sender.func_130014_f_().field_72996_f;
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) != null && !(list.get(i) instanceof EntityPlayer) && list.get(i).getClass().getName().toLowerCase().indexOf(className) >= 0) {
                list.get(i).func_70106_y();
                ++killed;
            }
        }
        sender.func_145747_a((IChatComponent)new ChatComponentText(killed + " entity killed(" + args[1] + ")."));
    }
    
    private void executeRemoveEntity(final ICommandSender sender, final String[] args) {
        if (args.length < 2) {
            throw new WrongUsageException("/mcheli removeentity <entity class name : example1 EntityBat , example2 minecraft.entity.passive>", new Object[0]);
        }
        final String className = args[1].toLowerCase();
        final List list = sender.func_130014_f_().field_72996_f;
        int removed = 0;
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) != null && !(list.get(i) instanceof EntityPlayer) && list.get(i).getClass().getName().toLowerCase().indexOf(className) >= 0) {
                list.get(i).field_70128_L = true;
                ++removed;
            }
        }
        sender.func_145747_a((IChatComponent)new ChatComponentText(removed + " entity removed(" + args[1] + ")."));
    }
    
    private void executeStatus(final ICommandSender sender, final String[] args) {
        if (args.length < 2) {
            throw new WrongUsageException("/mcheli status <entity or tile> [min num]", new Object[0]);
        }
        if (args[1].equalsIgnoreCase("entity")) {
            this.executeStatusSub(sender, args, "Server loaded Entity List", sender.func_130014_f_().field_72996_f);
        }
        else if (args[1].equalsIgnoreCase("tile")) {
            this.executeStatusSub(sender, args, "Server loaded Tile Entity List", sender.func_130014_f_().field_147482_g);
        }
    }
    
    private void executeStatusSub(final ICommandSender sender, final String[] args, final String title, final List list) {
        final int minNum = (args.length >= 3) ? Integer.valueOf(args[2]) : 0;
        final HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 0; i < list.size(); ++i) {
            final String key = list.get(i).getClass().getName();
            if (map.containsKey(key)) {
                map.put(key, map.get(key) + 1);
            }
            else {
                map.put(key, 1);
            }
        }
        final List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(final Map.Entry<String, Integer> entry1, final Map.Entry<String, Integer> entry2) {
                return entry1.getKey().compareTo((String)entry2.getKey());
            }
        });
        boolean send = false;
        sender.func_145747_a((IChatComponent)new ChatComponentText("--- " + title + " ---"));
        for (final Map.Entry<String, Integer> s : entries) {
            if (s.getValue() >= minNum) {
                final String msg = " " + s.getKey() + " : " + s.getValue();
                System.out.println(msg);
                sender.func_145747_a((IChatComponent)new ChatComponentText(msg));
                send = true;
            }
        }
        if (!send) {
            System.out.println("none");
            sender.func_145747_a((IChatComponent)new ChatComponentText("none"));
        }
    }
    
    public void executeFill(final ICommandSender sender, final String[] args) {
        if (args.length < 8) {
            throw new WrongUsageException("/mcheli fill <x1> <y1> <z1> <x2> <y2> <z2> <block name> [meta data] [oldBlockHandling] [data tag]", new Object[0]);
        }
        int x1 = sender.func_82114_b().field_71574_a;
        int y1 = sender.func_82114_b().field_71572_b;
        int z1 = sender.func_82114_b().field_71573_c;
        int x2 = sender.func_82114_b().field_71574_a;
        int y2 = sender.func_82114_b().field_71572_b;
        int z2 = sender.func_82114_b().field_71573_c;
        x1 = MathHelper.func_76128_c(func_110666_a(sender, (double)x1, args[1]));
        y1 = MathHelper.func_76128_c(func_110666_a(sender, (double)y1, args[2]));
        z1 = MathHelper.func_76128_c(func_110666_a(sender, (double)z1, args[3]));
        x2 = MathHelper.func_76128_c(func_110666_a(sender, (double)x2, args[4]));
        y2 = MathHelper.func_76128_c(func_110666_a(sender, (double)y2, args[5]));
        z2 = MathHelper.func_76128_c(func_110666_a(sender, (double)z2, args[6]));
        final Block block = CommandBase.func_147180_g(sender, args[7]);
        int metadata = 0;
        if (args.length >= 9) {
            metadata = func_71532_a(sender, args[8], 0, 15);
        }
        final World world = sender.func_130014_f_();
        if (x1 > x2) {
            final int t = x1;
            x1 = x2;
            x2 = t;
        }
        if (y1 > y2) {
            final int t = y1;
            y1 = y2;
            y2 = t;
        }
        if (z1 > z2) {
            final int t = z1;
            z1 = z2;
            z2 = t;
        }
        if (y1 < 0 || y2 >= 256) {
            throw new CommandException("commands.setblock.outOfWorld", new Object[0]);
        }
        final int blockNum = (x2 - x1 + 1) * (y2 - y1 + 1) * (z2 - z1 + 1);
        if (blockNum > 3000000) {
            throw new CommandException("commands.setblock.tooManyBlocks " + blockNum + " limit=327680", new Object[] { blockNum, 3276800 });
        }
        boolean result = false;
        final boolean keep = args.length >= 10 && args[9].equals("keep");
        final boolean destroy = args.length >= 10 && args[9].equals("destroy");
        final boolean override = args.length >= 10 && args[9].equals("override");
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        boolean flag = false;
        if (args.length >= 11 && block.func_149716_u()) {
            final String s = func_147178_a(sender, args, 10).func_150260_c();
            try {
                final NBTBase nbtbase = JsonToNBT.func_150315_a(s);
                if (!(nbtbase instanceof NBTTagCompound)) {
                    throw new CommandException("commands.setblock.tagError", new Object[] { "Not a valid tag" });
                }
                nbttagcompound = (NBTTagCompound)nbtbase;
                flag = true;
            }
            catch (NBTException nbtexception) {
                throw new CommandException("commands.setblock.tagError", new Object[] { nbtexception.getMessage() });
            }
        }
        for (int x3 = x1; x3 <= x2; ++x3) {
            for (int y3 = y1; y3 <= y2; ++y3) {
                for (int z3 = z1; z3 <= z2; ++z3) {
                    if (world.func_72899_e(x3, y3, z3)) {
                        if (world.func_147437_c(x3, y3, z3)) {
                            if (override) {
                                continue;
                            }
                        }
                        else if (keep) {
                            continue;
                        }
                        if (destroy) {
                            world.func_147480_a(x3, y3, z3, false);
                        }
                        final TileEntity block2 = world.func_147438_o(x3, y3, z3);
                        if (block2 instanceof IInventory) {
                            final IInventory ii = (IInventory)block2;
                            for (int i = 0; i < ii.func_70302_i_(); ++i) {
                                final ItemStack is = ii.func_70304_b(i);
                                if (is != null) {
                                    is.field_77994_a = 0;
                                }
                            }
                        }
                        if (world.func_147465_d(x3, y3, z3, block, metadata, 3)) {
                            if (flag) {
                                final TileEntity tileentity = world.func_147438_o(x3, y3, z3);
                                if (tileentity != null) {
                                    nbttagcompound.func_74768_a("x", x3);
                                    nbttagcompound.func_74768_a("y", y3);
                                    nbttagcompound.func_74768_a("z", z3);
                                    tileentity.func_145839_a(nbttagcompound);
                                }
                            }
                            result = true;
                        }
                    }
                }
            }
        }
        if (result) {
            func_152373_a(sender, (ICommand)this, "commands.setblock.success", new Object[0]);
            return;
        }
        throw new CommandException("commands.setblock.noChange", new Object[0]);
    }
    
    public List func_71516_a(final ICommandSender sender, final String[] prm) {
        final MCH_Config config = MCH_MOD.config;
        if (!MCH_Config.EnableCommand.prmBool) {
            return null;
        }
        if (prm.length <= 1) {
            return func_71530_a(prm, MCH_Command.ALL_COMMAND);
        }
        if (prm[0].equalsIgnoreCase("sendss")) {
            if (prm.length == 2) {
                return func_71530_a(prm, MinecraftServer.func_71276_C().func_71213_z());
            }
        }
        else if (prm[0].equalsIgnoreCase("modlist")) {
            if (prm.length == 3) {
                return func_71530_a(prm, MinecraftServer.func_71276_C().func_71213_z());
            }
        }
        else if (prm[0].equalsIgnoreCase("fill")) {
            if ((prm.length == 2 || prm.length == 5) && sender instanceof Entity) {
                final Entity entity = (Entity)sender;
                final List a = new ArrayList();
                final int x = (entity.field_70165_t < 0.0) ? ((int)(entity.field_70165_t - 1.0)) : ((int)entity.field_70165_t);
                final int z = (entity.field_70161_v < 0.0) ? ((int)(entity.field_70161_v - 1.0)) : ((int)entity.field_70161_v);
                a.add("" + x + " " + (int)(entity.field_70163_u + 0.5) + " " + z);
                return a;
            }
            return (prm.length == 8) ? func_71531_a(prm, (Iterable)Block.field_149771_c.func_148742_b()) : ((prm.length == 10) ? func_71530_a(prm, new String[] { "replace", "destroy", "keep", "override" }) : null);
        }
        else if (prm[0].equalsIgnoreCase("status")) {
            if (prm.length == 2) {
                return func_71530_a(prm, new String[] { "entity", "tile" });
            }
        }
        else if (prm[0].equalsIgnoreCase("attackentity")) {
            if (prm.length == 4) {
                return func_71530_a(prm, new String[] { "player", "inFire", "onFire", "lava", "inWall", "drown", "starve", "cactus", "fall", "outOfWorld", "generic", "magic", "wither", "anvil", "fallingBlock" });
            }
        }
        else if (prm[0].equalsIgnoreCase("showboundingbox") && prm.length == 2) {
            return func_71530_a(prm, new String[] { "true", "false" });
        }
        return null;
    }
    
    static {
        MCH_Command.ALL_COMMAND = new String[] { "sendss", "modlist", "reconfig", "title", "fill", "status", "killentity", "removeentity", "attackentity", "showboundingbox", "list" };
        MCH_Command.instance = new MCH_Command();
    }
}
