package mcheli.parachute;

import mcheli.wrapper.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;

public class MCH_ItemParachute extends W_Item
{
    public MCH_ItemParachute(final int par1) {
        super(par1);
        this.field_77777_bU = 1;
    }
    
    public ItemStack func_77659_a(final ItemStack item, final World world, final EntityPlayer player) {
        if (!world.field_72995_K && player.field_70154_o == null && !player.field_70122_E) {
            final double x = player.field_70165_t + 0.5;
            final double y = player.field_70163_u + 3.5;
            final double z = player.field_70161_v + 0.5;
            final MCH_EntityParachute entity = new MCH_EntityParachute(world, x, y, z);
            entity.field_70177_z = player.field_70177_z;
            entity.field_70159_w = player.field_70159_w;
            entity.field_70181_x = player.field_70181_x;
            entity.field_70179_y = player.field_70179_y;
            entity.field_70143_R = player.field_70143_R;
            player.field_70143_R = 0.0f;
            entity.user = (Entity)player;
            entity.setType(1);
            world.func_72838_d((Entity)entity);
        }
        if (!player.field_71075_bZ.field_75098_d) {
            --item.field_77994_a;
        }
        return item;
    }
}
