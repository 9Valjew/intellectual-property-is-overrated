package mcheli.multiplay;

import java.nio.*;
import net.minecraft.client.*;
import net.minecraft.client.shader.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.texture.*;
import javax.imageio.*;
import java.awt.image.*;
import net.minecraft.util.*;
import cpw.mods.fml.common.*;
import cpw.mods.fml.relauncher.*;
import java.util.jar.*;
import java.util.zip.*;
import java.util.*;
import java.io.*;
import mcheli.*;

public class MCH_MultiplayClient
{
    private static IntBuffer pixelBuffer;
    private static int[] pixelValues;
    private static MCH_OStream dataOutputStream;
    private static List<String> modList;
    
    public static void startSendImageData() {
        final Minecraft mc = Minecraft.func_71410_x();
        sendScreenShot(mc.field_71443_c, mc.field_71440_d, mc.func_147110_a());
    }
    
    public static void sendScreenShot(int displayWidth, int displayHeight, final Framebuffer framebufferMc) {
        try {
            if (OpenGlHelper.func_148822_b()) {
                displayWidth = framebufferMc.field_147622_a;
                displayHeight = framebufferMc.field_147620_b;
            }
            final int k = displayWidth * displayHeight;
            if (MCH_MultiplayClient.pixelBuffer == null || MCH_MultiplayClient.pixelBuffer.capacity() < k) {
                MCH_MultiplayClient.pixelBuffer = BufferUtils.createIntBuffer(k);
                MCH_MultiplayClient.pixelValues = new int[k];
            }
            GL11.glPixelStorei(3333, 1);
            GL11.glPixelStorei(3317, 1);
            MCH_MultiplayClient.pixelBuffer.clear();
            if (OpenGlHelper.func_148822_b()) {
                GL11.glBindTexture(3553, framebufferMc.field_147617_g);
                GL11.glGetTexImage(3553, 0, 32993, 33639, MCH_MultiplayClient.pixelBuffer);
            }
            else {
                GL11.glReadPixels(0, 0, displayWidth, displayHeight, 32993, 33639, MCH_MultiplayClient.pixelBuffer);
            }
            MCH_MultiplayClient.pixelBuffer.get(MCH_MultiplayClient.pixelValues);
            TextureUtil.func_147953_a(MCH_MultiplayClient.pixelValues, displayWidth, displayHeight);
            BufferedImage bufferedimage = null;
            if (OpenGlHelper.func_148822_b()) {
                bufferedimage = new BufferedImage(framebufferMc.field_147621_c, framebufferMc.field_147618_d, 1);
                int i1;
                for (int l = i1 = framebufferMc.field_147620_b - framebufferMc.field_147618_d; i1 < framebufferMc.field_147620_b; ++i1) {
                    for (int j1 = 0; j1 < framebufferMc.field_147621_c; ++j1) {
                        bufferedimage.setRGB(j1, i1 - l, MCH_MultiplayClient.pixelValues[i1 * framebufferMc.field_147622_a + j1]);
                    }
                }
            }
            else {
                bufferedimage = new BufferedImage(displayWidth, displayHeight, 1);
                bufferedimage.setRGB(0, 0, displayWidth, displayHeight, MCH_MultiplayClient.pixelValues, 0, displayWidth);
            }
            ImageIO.write(bufferedimage, "png", MCH_MultiplayClient.dataOutputStream = new MCH_OStream());
        }
        catch (Exception ex) {}
    }
    
    public static void readImageData(final DataOutputStream dos) throws IOException {
        MCH_MultiplayClient.dataOutputStream.write(dos);
    }
    
    public static void sendImageData() {
        if (MCH_MultiplayClient.dataOutputStream != null) {
            MCH_PacketLargeData.send();
            if (MCH_MultiplayClient.dataOutputStream.isDataEnd()) {
                MCH_MultiplayClient.dataOutputStream = null;
            }
        }
    }
    
    public static double getPerData() {
        return (MCH_MultiplayClient.dataOutputStream == null) ? -1.0 : (MCH_MultiplayClient.dataOutputStream.index / MCH_MultiplayClient.dataOutputStream.size());
    }
    
    public static void readModList(final String playerName) {
        (MCH_MultiplayClient.modList = new ArrayList<String>()).add(EnumChatFormatting.RED + "###### " + playerName + " ######");
        final String[] arr$;
        final String[] classFileNameList = arr$ = System.getProperty("java.class.path").split(File.pathSeparator);
        for (final String classFileName : arr$) {
            MCH_Lib.DbgLog(true, "java.class.path=" + classFileName, new Object[0]);
            if (classFileName.length() > 1) {
                final File javaClassFile = new File(classFileName);
                if (javaClassFile.getAbsolutePath().toLowerCase().indexOf("versions") >= 0) {
                    MCH_MultiplayClient.modList.add(EnumChatFormatting.AQUA + "# Client class=" + javaClassFile.getName() + " : file size= " + javaClassFile.length());
                }
            }
        }
        MCH_MultiplayClient.modList.add(EnumChatFormatting.YELLOW + "=== ActiveModList ===");
        for (final ModContainer mod : Loader.instance().getActiveModList()) {
            MCH_MultiplayClient.modList.add("" + mod + "  [" + mod.getModId() + "]  " + mod.getName() + "[" + mod.getDisplayVersion() + "]  " + mod.getSource().getName());
        }
        if (CoreModManager.getAccessTransformers().size() > 0) {
            MCH_MultiplayClient.modList.add(EnumChatFormatting.YELLOW + "=== AccessTransformers ===");
            for (final String s : CoreModManager.getAccessTransformers()) {
                MCH_MultiplayClient.modList.add(s);
            }
        }
        if (CoreModManager.getLoadedCoremods().size() > 0) {
            MCH_MultiplayClient.modList.add(EnumChatFormatting.YELLOW + "=== LoadedCoremods ===");
            for (final String s : CoreModManager.getLoadedCoremods()) {
                MCH_MultiplayClient.modList.add(s);
            }
        }
        if (CoreModManager.getReparseableCoremods().size() > 0) {
            MCH_MultiplayClient.modList.add(EnumChatFormatting.YELLOW + "=== ReparseableCoremods ===");
            for (final String s : CoreModManager.getReparseableCoremods()) {
                MCH_MultiplayClient.modList.add(s);
            }
        }
        final Minecraft mc = Minecraft.func_71410_x();
        MCH_FileSearch search = new MCH_FileSearch();
        File[] files = search.listFiles(new File(mc.field_71412_D, "mods").getAbsolutePath(), "*.jar");
        MCH_MultiplayClient.modList.add(EnumChatFormatting.YELLOW + "=== Manifest ===");
        for (final File file : files) {
            try {
                final String jarPath = file.getCanonicalPath();
                final JarFile jarFile = new JarFile(jarPath);
                final Enumeration jarEntries = jarFile.entries();
                String manifest = "";
                while (jarEntries.hasMoreElements()) {
                    final ZipEntry zipEntry = jarEntries.nextElement();
                    if (zipEntry.getName().equalsIgnoreCase("META-INF/MANIFEST.MF") && !zipEntry.isDirectory()) {
                        final InputStream is = jarFile.getInputStream(zipEntry);
                        final BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        for (String line = br.readLine(); line != null; line = br.readLine()) {
                            line = line.replace(" ", "").trim();
                            if (!line.isEmpty()) {
                                manifest = manifest + " [" + line + "]";
                            }
                        }
                        is.close();
                        break;
                    }
                }
                jarFile.close();
                if (!manifest.isEmpty()) {
                    MCH_MultiplayClient.modList.add(file.getName() + manifest);
                }
            }
            catch (Exception e) {
                MCH_MultiplayClient.modList.add(file.getName() + " : Read Manifest failed.");
            }
        }
        search = new MCH_FileSearch();
        files = search.listFiles(new File(mc.field_71412_D, "mods").getAbsolutePath(), "*.litemod");
        MCH_MultiplayClient.modList.add(EnumChatFormatting.LIGHT_PURPLE + "=== LiteLoader ===");
        for (final File file : files) {
            try {
                final String jarPath = file.getCanonicalPath();
                final JarFile jarFile = new JarFile(jarPath);
                final Enumeration jarEntries = jarFile.entries();
                String litemod_json = "";
                while (jarEntries.hasMoreElements()) {
                    final ZipEntry zipEntry = jarEntries.nextElement();
                    String fname = zipEntry.getName().toLowerCase();
                    if (zipEntry.isDirectory()) {
                        continue;
                    }
                    if (fname.equals("litemod.json")) {
                        final InputStream is2 = jarFile.getInputStream(zipEntry);
                        final BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
                        for (String line2 = br2.readLine(); line2 != null; line2 = br2.readLine()) {
                            line2 = line2.replace(" ", "").trim();
                            if (line2.toLowerCase().indexOf("name") >= 0) {
                                litemod_json = litemod_json + " [" + line2 + "]";
                                break;
                            }
                        }
                        is2.close();
                    }
                    else {
                        final int index = fname.lastIndexOf("/");
                        if (index >= 0) {
                            fname = fname.substring(index + 1);
                        }
                        if (fname.indexOf("litemod") < 0 || !fname.endsWith("class")) {
                            continue;
                        }
                        fname = zipEntry.getName();
                        if (index >= 0) {
                            fname = fname.substring(index + 1);
                        }
                        litemod_json = litemod_json + " [" + fname + "]";
                    }
                }
                jarFile.close();
                if (!litemod_json.isEmpty()) {
                    MCH_MultiplayClient.modList.add(file.getName() + litemod_json);
                }
            }
            catch (Exception e) {
                MCH_MultiplayClient.modList.add(file.getName() + " : Read LiteLoader failed.");
            }
        }
    }
    
    public static void sendModsInfo(final String playerName, final int id) {
        final MCH_Config config = MCH_MOD.config;
        if (MCH_Config.DebugLog) {
            MCH_MultiplayClient.modList.clear();
            readModList(playerName);
        }
        MCH_PacketModList.send(MCH_MultiplayClient.modList, id);
    }
    
    static {
        MCH_MultiplayClient.modList = new ArrayList<String>();
    }
}
