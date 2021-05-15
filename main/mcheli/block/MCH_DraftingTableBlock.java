package mcheli.block;

import net.minecraft.block.material.*;
import net.minecraft.entity.player.*;
import mcheli.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.texture.*;
import cpw.mods.fml.relauncher.*;
import java.util.*;
import net.minecraft.item.*;
import mcheli.wrapper.*;

public class MCH_DraftingTableBlock extends W_BlockContainer implements ITileEntityProvider
{
    private final boolean isLighting;
    
    public MCH_DraftingTableBlock(final int blockId, final boolean p_i45421_1_) {
        super(blockId, Material.field_151573_f);
        this.func_149672_a(W_Block.field_149777_j);
        this.func_149711_c(0.2f);
        this.isLighting = p_i45421_1_;
        if (p_i45421_1_) {
            this.func_149715_a(1.0f);
        }
    }
    
    public boolean func_149727_a(final World world, final int x, final int y, final int z, final EntityPlayer player, final int par6, final float par7, final float par8, final float par9) {
        if (!world.field_72995_K) {
            if (!player.func_70093_af()) {
                MCH_Lib.DbgLog(player.field_70170_p, "MCH_DraftingTableGui.MCH_DraftingTableGui OPEN GUI (%d, %d, %d)", x, y, z);
                player.openGui((Object)MCH_MOD.instance, 4, world, x, y, z);
            }
            else {
                final int yaw = world.func_72805_g(x, y, z);
                MCH_Lib.DbgLog(world, "MCH_DraftingTableBlock.onBlockActivated:yaw=%d Light %s", yaw, this.isLighting ? "OFF->ON" : "ON->OFF");
                if (this.isLighting) {
                    W_WorldFunc.setBlock(world, x, y, z, (Block)MCH_MOD.blockDraftingTable, yaw + 180, 2);
                }
                else {
                    W_WorldFunc.setBlock(world, x, y, z, (Block)MCH_MOD.blockDraftingTableLit, yaw + 180, 2);
                }
                world.func_72921_c(x, y, z, yaw, 2);
                world.func_72908_a(x + 0.5, y + 0.5, z + 0.5, "random.click", 0.3f, 0.5f);
            }
        }
        return true;
    }
    
    public TileEntity func_149915_a(final World world, final int a) {
        return new MCH_DraftingTableTileEntity();
    }
    
    public TileEntity createNewTileEntity(final World world) {
        return new MCH_DraftingTableTileEntity();
    }
    
    public boolean func_149646_a(final IBlockAccess p_149646_1_, final int p_149646_2_, final int p_149646_3_, final int p_149646_4_, final int p_149646_5_) {
        return true;
    }
    
    public boolean func_149686_d() {
        return false;
    }
    
    public boolean func_149662_c() {
        return false;
    }
    
    public boolean canHarvestBlock(final EntityPlayer player, final int meta) {
        return true;
    }
    
    public boolean canRenderInPass(final int pass) {
        return false;
    }
    
    public int func_149656_h() {
        return 1;
    }
    
    public void func_149689_a(final World world, final int par2, final int par3, final int par4, final EntityLivingBase entity, final ItemStack itemStack) {
        float pyaw = (float)MCH_Lib.getRotate360(entity.field_70177_z);
        pyaw += 22.5f;
        int yaw = (int)(pyaw / 45.0f);
        if (yaw < 0) {
            yaw = yaw % 8 + 8;
        }
        world.func_72921_c(par2, par3, par4, yaw, 2);
        MCH_Lib.DbgLog(world, "MCH_DraftingTableBlock.onBlockPlacedBy:yaw=%d", yaw);
    }
    
    public boolean func_149710_n() {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_149651_a(final IIconRegister par1IconRegister) {
        this.field_149761_L = par1IconRegister.func_94245_a("mcheli:drafting_table");
    }
    
    public void registerIcons(final IconRegister par1IconRegister) {
        this.field_149761_L = par1IconRegister.registerIcon("mcheli:drafting_table");
    }
    
    public Item func_149650_a(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return W_Item.getItemFromBlock((Block)MCH_MOD.blockDraftingTable);
    }
    
    @SideOnly(Side.CLIENT)
    public Item func_149694_d(final World world, final int p_149694_2_, final int p_149694_3_, final int p_149694_4_) {
        return W_Item.getItemFromBlock((Block)MCH_MOD.blockDraftingTable);
    }
    
    protected ItemStack func_149644_j(final int p_149644_1_) {
        return new ItemStack((Block)MCH_MOD.blockDraftingTable);
    }
}
