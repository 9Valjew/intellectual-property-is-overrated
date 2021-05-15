package mcheli;

import cpw.mods.fml.common.registry.*;
import net.minecraft.block.*;
import mcheli.gltd.*;
import mcheli.chain.*;
import mcheli.parachute.*;
import mcheli.container.*;
import mcheli.uav.*;
import mcheli.tool.*;
import mcheli.tool.rangefinder.*;
import mcheli.lweapon.*;
import mcheli.helicopter.*;
import mcheli.aircraft.*;
import mcheli.plane.*;
import mcheli.tank.*;
import mcheli.vehicle.*;
import mcheli.throwable.*;
import java.util.*;
import net.minecraft.item.*;
import mcheli.wrapper.*;
import net.minecraft.item.crafting.*;

public class MCH_ItemRecipe implements MCH_IRecipeList
{
    private static final MCH_ItemRecipe instance;
    private static List<IRecipe> commonItemRecipe;
    
    public static MCH_ItemRecipe getInstance() {
        return MCH_ItemRecipe.instance;
    }
    
    @Override
    public int getRecipeListSize() {
        return MCH_ItemRecipe.commonItemRecipe.size();
    }
    
    @Override
    public IRecipe getRecipe(final int index) {
        return MCH_ItemRecipe.commonItemRecipe.get(index);
    }
    
    private static void addRecipeList(final IRecipe recipe) {
        if (recipe != null) {
            MCH_ItemRecipe.commonItemRecipe.add(recipe);
        }
    }
    
    private static void registerCommonItemRecipe() {
        MCH_ItemRecipe.commonItemRecipe.clear();
        GameRegistry.addRecipe((IRecipe)new MCH_RecipeFuel());
        final MCH_ItemFuel itemFuel = MCH_MOD.itemFuel;
        final MCH_Config config = MCH_MOD.config;
        addRecipeList(addRecipe(itemFuel, MCH_Config.ItemRecipe_Fuel.prmString));
        final MCH_ItemGLTD itemGLTD = MCH_MOD.itemGLTD;
        final MCH_Config config2 = MCH_MOD.config;
        addRecipeList(addRecipe(itemGLTD, MCH_Config.ItemRecipe_GLTD.prmString));
        final MCH_ItemChain itemChain = MCH_MOD.itemChain;
        final MCH_Config config3 = MCH_MOD.config;
        addRecipeList(addRecipe(itemChain, MCH_Config.ItemRecipe_Chain.prmString));
        final MCH_ItemParachute itemParachute = MCH_MOD.itemParachute;
        final MCH_Config config4 = MCH_MOD.config;
        addRecipeList(addRecipe(itemParachute, MCH_Config.ItemRecipe_Parachute.prmString));
        final MCH_ItemContainer itemContainer = MCH_MOD.itemContainer;
        final MCH_Config config5 = MCH_MOD.config;
        addRecipeList(addRecipe(itemContainer, MCH_Config.ItemRecipe_Container.prmString));
        for (int i = 0; i < MCH_MOD.itemUavStation.length; ++i) {
            final MCH_ItemUavStation item = MCH_MOD.itemUavStation[i];
            final MCH_Config config6 = MCH_MOD.config;
            addRecipeList(addRecipe(item, MCH_Config.ItemRecipe_UavStation[i].prmString));
        }
        final MCH_ItemWrench itemWrench = MCH_MOD.itemWrench;
        final MCH_Config config7 = MCH_MOD.config;
        addRecipeList(addRecipe(itemWrench, MCH_Config.ItemRecipe_Wrench.prmString));
        final MCH_ItemRangeFinder itemRangeFinder = MCH_MOD.itemRangeFinder;
        final MCH_Config config8 = MCH_MOD.config;
        addRecipeList(addRecipe(itemRangeFinder, MCH_Config.ItemRecipe_RangeFinder.prmString));
        GameRegistry.addRecipe((IRecipe)new MCH_RecipeReloadRangeFinder());
        final MCH_ItemLightWeaponBase itemStinger = MCH_MOD.itemStinger;
        final MCH_Config config9 = MCH_MOD.config;
        addRecipeList(addRecipe(itemStinger, MCH_Config.ItemRecipe_Stinger.prmString));
        final MCH_ItemLightWeaponBullet itemStingerBullet = MCH_MOD.itemStingerBullet;
        final StringBuilder append = new StringBuilder().append("2,");
        final MCH_Config config10 = MCH_MOD.config;
        addRecipeList(addRecipe(itemStingerBullet, append.append(MCH_Config.ItemRecipe_StingerMissile.prmString).toString()));
        final MCH_ItemLightWeaponBase itemJavelin = MCH_MOD.itemJavelin;
        final MCH_Config config11 = MCH_MOD.config;
        addRecipeList(addRecipe(itemJavelin, MCH_Config.ItemRecipe_Javelin.prmString));
        final MCH_ItemLightWeaponBullet itemJavelinBullet = MCH_MOD.itemJavelinBullet;
        final StringBuilder append2 = new StringBuilder().append("2,");
        final MCH_Config config12 = MCH_MOD.config;
        addRecipeList(addRecipe(itemJavelinBullet, append2.append(MCH_Config.ItemRecipe_JavelinMissile.prmString).toString()));
        final Item itemFromBlock = W_Item.getItemFromBlock((Block)MCH_MOD.blockDraftingTable);
        final MCH_Config config13 = MCH_MOD.config;
        addRecipeList(addRecipe(itemFromBlock, MCH_Config.ItemRecipe_DraftingTable.prmString));
    }
    
    public static void registerItemRecipe() {
        registerCommonItemRecipe();
        for (final String name : MCH_HeliInfoManager.map.keySet()) {
            final MCH_HeliInfo info = MCH_HeliInfoManager.map.get(name);
            addRecipeAndRegisterList(info, info.item, MCH_HeliInfoManager.getInstance());
        }
        for (final String name : MCP_PlaneInfoManager.map.keySet()) {
            final MCP_PlaneInfo info2 = MCP_PlaneInfoManager.map.get(name);
            addRecipeAndRegisterList(info2, info2.item, MCP_PlaneInfoManager.getInstance());
        }
        for (final String name : MCH_TankInfoManager.map.keySet()) {
            final MCH_TankInfo info3 = MCH_TankInfoManager.map.get(name);
            addRecipeAndRegisterList(info3, info3.item, MCH_TankInfoManager.getInstance());
        }
        for (final String name : MCH_VehicleInfoManager.map.keySet()) {
            final MCH_VehicleInfo info4 = MCH_VehicleInfoManager.map.get(name);
            addRecipeAndRegisterList(info4, info4.item, MCH_VehicleInfoManager.getInstance());
        }
        for (final String name : MCH_ThrowableInfoManager.getKeySet()) {
            final MCH_ThrowableInfo info5 = MCH_ThrowableInfoManager.get(name);
            for (final String s : info5.recipeString) {
                if (s.length() >= 3) {
                    final IRecipe recipe = addRecipe(info5.item, s, info5.isShapedRecipe);
                    info5.recipe.add(recipe);
                    addRecipeList(recipe);
                }
            }
            info5.recipeString = null;
        }
    }
    
    private static void addRecipeAndRegisterList(final MCH_AircraftInfo info, final Item item, final MCH_AircraftInfoManager im) {
        int count = 0;
        for (final String s : info.recipeString) {
            ++count;
            if (s.length() >= 3) {
                final IRecipe recipe = addRecipe(item, s, info.isShapedRecipe);
                info.recipe.add(recipe);
                im.addRecipe(recipe, count, info.name, s);
            }
        }
        info.recipeString = null;
    }
    
    public static IRecipe addRecipe(final Item item, final String data) {
        return addShapedRecipe(item, data);
    }
    
    public static IRecipe addRecipe(final Item item, final String data, final boolean isShaped) {
        if (isShaped) {
            return addShapedRecipe(item, data);
        }
        return addShapelessRecipe(item, data);
    }
    
    public static IRecipe addShapedRecipe(final Item item, final String data) {
        final ArrayList<Object> rcp = new ArrayList<Object>();
        final String[] s = data.split("\\s*,\\s*");
        if (s.length < 3) {
            return null;
        }
        int start = 0;
        int createNum = 1;
        if (isNumber(s[0])) {
            start = 1;
            createNum = Integer.valueOf(s[0]);
            if (createNum <= 0) {
                createNum = 1;
            }
        }
        int idx = start;
        for (int i = start; i < 3 + start; ++i) {
            if (s[idx].length() > 0 && s[idx].charAt(0) == '\"' && s[idx].charAt(s[idx].length() - 1) == '\"') {
                rcp.add(s[idx].subSequence(1, s[idx].length() - 1));
                ++idx;
            }
        }
        if (idx == 0) {
            return null;
        }
        boolean isChar = true;
        while (idx < s.length) {
            if (s[idx].length() <= 0) {
                return null;
            }
            if (isChar) {
                if (s[idx].length() != 1) {
                    return null;
                }
                final char c = s[idx].toUpperCase().charAt(0);
                if (c < 'A' || c > 'Z') {
                    return null;
                }
                rcp.add(c);
            }
            else {
                final String nm = s[idx].trim().toLowerCase();
                int dmg = 0;
                if (idx + 1 < s.length && isNumber(s[idx + 1])) {
                    ++idx;
                    dmg = Integer.parseInt(s[idx]);
                }
                if (isNumber(nm)) {
                    return null;
                }
                rcp.add(new ItemStack(W_Item.getItemByName(nm), 1, dmg));
            }
            isChar = !isChar;
            ++idx;
        }
        final Object[] recipe = new Object[rcp.size()];
        for (int j = 0; j < recipe.length; ++j) {
            recipe[j] = rcp.get(j);
        }
        final ShapedRecipes r = (ShapedRecipes)GameRegistry.addShapedRecipe(new ItemStack(item, createNum), recipe);
        for (int k = 0; k < r.field_77574_d.length; ++k) {
            if (r.field_77574_d[k] != null && r.field_77574_d[k].func_77973_b() == null) {
                throw new RuntimeException("Error: Invalid ShapedRecipes! " + item + " : " + data);
            }
        }
        return (IRecipe)r;
    }
    
    public static IRecipe addShapelessRecipe(final Item item, final String data) {
        final ArrayList<Object> rcp = new ArrayList<Object>();
        final String[] s = data.split("\\s*,\\s*");
        if (s.length < 1) {
            return null;
        }
        final int start = 0;
        int createNum = 1;
        if (isNumber(s[0]) && createNum <= 0) {
            createNum = 1;
        }
        for (int idx = start; idx < s.length; ++idx) {
            if (s[idx].length() <= 0) {
                return null;
            }
            final String nm = s[idx].trim().toLowerCase();
            int dmg = 0;
            if (idx + 1 < s.length && isNumber(s[idx + 1])) {
                ++idx;
                dmg = Integer.parseInt(s[idx]);
            }
            if (isNumber(nm)) {
                final int n = Integer.parseInt(nm);
                if (n <= 255) {
                    rcp.add(new ItemStack(W_Block.getBlockById(n), 1, dmg));
                }
                else if (n <= 511) {
                    rcp.add(new ItemStack(W_Item.getItemById(n), 1, dmg));
                }
                else if (n <= 2255) {
                    rcp.add(new ItemStack(W_Block.getBlockById(n), 1, dmg));
                }
                else if (n <= 2267) {
                    rcp.add(new ItemStack(W_Item.getItemById(n), 1, dmg));
                }
                else if (n <= 4095) {
                    rcp.add(new ItemStack(W_Block.getBlockById(n), 1, dmg));
                }
                else if (n <= 31999) {
                    rcp.add(new ItemStack(W_Item.getItemById(n + 256), 1, dmg));
                }
            }
            else {
                rcp.add(new ItemStack(W_Item.getItemByName(nm), 1, dmg));
            }
        }
        final Object[] recipe = new Object[rcp.size()];
        for (int i = 0; i < recipe.length; ++i) {
            recipe[i] = rcp.get(i);
        }
        final ShapelessRecipes r = getShapelessRecipe(new ItemStack(item, createNum), recipe);
        for (int j = 0; j < r.field_77579_b.size(); ++j) {
            final ItemStack is = r.field_77579_b.get(j);
            if (is.func_77973_b() == null) {
                throw new RuntimeException("Error: Invalid ShapelessRecipes! " + item + " : " + data);
            }
        }
        GameRegistry.addRecipe((IRecipe)r);
        return (IRecipe)r;
    }
    
    public static ShapelessRecipes getShapelessRecipe(final ItemStack par1ItemStack, final Object... par2ArrayOfObj) {
        final ArrayList arraylist = new ArrayList();
        for (final Object object1 : par2ArrayOfObj) {
            if (object1 instanceof ItemStack) {
                arraylist.add(((ItemStack)object1).func_77946_l());
            }
            else if (object1 instanceof Item) {
                arraylist.add(new ItemStack((Item)object1));
            }
            else {
                if (!(object1 instanceof Block)) {
                    throw new RuntimeException("Invalid shapeless recipy!");
                }
                arraylist.add(new ItemStack((Block)object1));
            }
        }
        return new ShapelessRecipes(par1ItemStack, (List)arraylist);
    }
    
    public static boolean isNumber(final String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        final byte[] arr$;
        final byte[] buf = arr$ = s.getBytes();
        for (final byte b : arr$) {
            if (b < 48 || b > 57) {
                return false;
            }
        }
        return true;
    }
    
    static {
        instance = new MCH_ItemRecipe();
        MCH_ItemRecipe.commonItemRecipe = new ArrayList<IRecipe>();
    }
}
