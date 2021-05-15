package mcheli.block;

import net.minecraft.item.crafting.*;
import net.minecraft.util.*;
import mcheli.wrapper.modelloader.*;
import mcheli.aircraft.*;
import java.util.*;
import mcheli.plane.*;
import mcheli.weapon.*;
import mcheli.*;
import java.io.*;

public class MCH_CurrentRecipe
{
    public final IRecipe recipe;
    public final int index;
    public final String displayName;
    public final List<ResourceLocation> descTexture;
    private final MCH_AircraftInfo acInfo;
    public List<String> infoItem;
    public List<String> infoData;
    private int descMaxPage;
    private int descPage;
    private W_ModelCustom model;
    public int modelRot;
    private ResourceLocation modelTexture;
    
    public MCH_CurrentRecipe(final MCH_IRecipeList list, final int idx) {
        if (list.getRecipeListSize() > 0) {
            this.recipe = list.getRecipe(idx);
        }
        else {
            this.recipe = null;
        }
        this.index = idx;
        this.displayName = ((this.recipe != null) ? this.recipe.func_77571_b().func_82833_r() : "None");
        this.descTexture = this.getDescTexture(this.recipe);
        this.descPage = 0;
        this.descMaxPage = this.descTexture.size();
        MCH_AircraftInfo info = null;
        if (list instanceof MCH_AircraftInfoManager) {
            info = ((MCH_AircraftInfoManager)list).getAcInfoFromItem(this.recipe);
            if (info != null) {
                ++this.descMaxPage;
                final String dir = info.getDirectoryName();
                final String name = info.name;
                this.model = MCH_ModelManager.get(dir, name);
                if (this.model != null) {
                    this.modelTexture = new ResourceLocation("mcheli", "textures/" + dir + "/" + name + ".png");
                    ++this.descMaxPage;
                    if (list instanceof MCP_PlaneInfoManager) {
                        this.modelRot = 0;
                    }
                    else {
                        this.modelRot = 1;
                    }
                }
            }
        }
        this.getAcInfoText(info);
        this.acInfo = info;
    }
    
    private void getAcInfoText(final MCH_AircraftInfo info) {
        this.infoItem = new ArrayList<String>();
        this.infoData = new ArrayList<String>();
        if (info == null) {
            return;
        }
        this.getAcInfoTextSub("Name", info.getItemStack().func_82833_r());
        this.getAcInfoTextSub("HP", "" + info.maxHp);
        final int seatNum = info.isUAV ? (info.getNumSeat() - 1) : info.getNumSeat();
        this.getAcInfoTextSub("Num of Seat", "" + seatNum);
        this.getAcInfoTextSub("GunnerMode", info.isEnableGunnerMode ? "YES" : "NO");
        this.getAcInfoTextSub("NightVision", info.isEnableNightVision ? "YES" : "NO");
        this.getAcInfoTextSub("Radar", info.isEnableEntityRadar ? "YES" : "NO");
        this.getAcInfoTextSub("Inventory", "" + info.inventorySize);
        if (info instanceof MCP_PlaneInfo) {
            final MCP_PlaneInfo pinfo = (MCP_PlaneInfo)info;
            this.getAcInfoTextSub("VTOL", pinfo.isEnableVtol ? "YES" : "NO");
        }
        if (info.getWeaponNum() > 0) {
            this.getAcInfoTextSub("Armed----------------");
            for (int i = 0; i < info.getWeaponNum(); ++i) {
                final String type = info.getWeaponSetById(i).type;
                final MCH_WeaponInfo winfo = MCH_WeaponInfoManager.get(type);
                if (winfo != null) {
                    this.getAcInfoTextSub(winfo.getWeaponTypeName(), winfo.displayName);
                }
                else {
                    this.getAcInfoTextSub("ERROR", "Not found weapon " + (i + 1));
                }
            }
        }
    }
    
    private void getAcInfoTextSub(final String item, final String data) {
        this.infoItem.add(item + " :");
        this.infoData.add(data);
    }
    
    private void getAcInfoTextSub(final String item) {
        this.infoItem.add(item);
        this.infoData.add("");
    }
    
    public void switchNextPage() {
        if (this.descMaxPage >= 2) {
            this.descPage = (this.descPage + 1) % this.descMaxPage;
        }
        else {
            this.descPage = 0;
        }
    }
    
    public void switchPrevPage() {
        --this.descPage;
        if (this.descPage < 0 && this.descMaxPage >= 2) {
            this.descPage = this.descMaxPage - 1;
        }
        else {
            this.descPage = 0;
        }
    }
    
    public int getDescCurrentPage() {
        return this.descPage;
    }
    
    public void setDescCurrentPage(final int page) {
        if (this.descMaxPage > 0) {
            this.descPage = ((page < this.descMaxPage) ? page : (this.descMaxPage - 1));
        }
        else {
            this.descPage = 0;
        }
    }
    
    public int getDescMaxPage() {
        return this.descMaxPage;
    }
    
    public ResourceLocation getCurrentPageTexture() {
        if (this.descPage < this.descTexture.size()) {
            return this.descTexture.get(this.descPage);
        }
        return null;
    }
    
    public W_ModelCustom getModel() {
        return this.model;
    }
    
    public ResourceLocation getModelTexture() {
        return this.modelTexture;
    }
    
    public MCH_AircraftInfo getAcInfo() {
        return this.acInfo;
    }
    
    public boolean isCurrentPageTexture() {
        return this.descPage >= 0 && this.descPage < this.descTexture.size();
    }
    
    public boolean isCurrentPageModel() {
        return this.getAcInfo() != null && this.getModel() != null && this.descPage == this.descTexture.size();
    }
    
    public boolean isCurrentPageAcInfo() {
        return this.getAcInfo() != null && this.descPage == this.descMaxPage - 1;
    }
    
    private List<ResourceLocation> getDescTexture(final IRecipe r) {
        final List<ResourceLocation> list = new ArrayList<ResourceLocation>();
        if (r != null) {
            for (int i = 0; i < 20; ++i) {
                String itemName = r.func_77571_b().func_77977_a();
                if (itemName.startsWith("tile.")) {
                    itemName = itemName.substring(5);
                }
                if (itemName.indexOf(":") >= 0) {
                    itemName = itemName.substring(itemName.indexOf(":") + 1);
                }
                itemName = "/textures/drafting_table_desc/" + itemName + "#" + i + ".png";
                final File filePng = new File(MCH_MOD.sourcePath, "/assets/mcheli/" + itemName);
                if (filePng.exists()) {
                    list.add(new ResourceLocation("mcheli", itemName));
                }
            }
        }
        return list;
    }
}
