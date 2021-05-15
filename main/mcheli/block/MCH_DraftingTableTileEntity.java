package mcheli.block;

import net.minecraft.tileentity.*;
import mcheli.*;

public class MCH_DraftingTableTileEntity extends TileEntity
{
    public int func_145832_p() {
        if (this.field_145847_g == -1) {
            this.field_145847_g = this.field_145850_b.func_72805_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
            MCH_Lib.DbgLog(this.field_145850_b, "MCH_DraftingTableTileEntity.getBlockMetadata : %d(0x%08X)", this.field_145847_g, this.field_145847_g);
        }
        return this.field_145847_g;
    }
}
