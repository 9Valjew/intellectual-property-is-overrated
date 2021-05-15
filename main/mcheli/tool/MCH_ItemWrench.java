package mcheli.tool;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import mcheli.wrapper.*;
import mcheli.*;
import mcheli.aircraft.*;
import java.util.*;
import net.minecraft.util.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.item.*;
import com.google.common.collect.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;

public class MCH_ItemWrench extends W_Item
{
    private float damageVsEntity;
    private final Item.ToolMaterial toolMaterial;
    private static Random rand;
    
    public MCH_ItemWrench(final int itemId, final Item.ToolMaterial material) {
        super(itemId);
        this.toolMaterial = material;
        this.field_77777_bU = 1;
        this.func_77656_e(material.func_77997_a());
        this.damageVsEntity = 4.0f + material.func_78000_c();
    }
    
    public boolean func_150897_b(final Block b) {
        final Material material = b.func_149688_o();
        return material == Material.field_151573_f || material instanceof MaterialLogic;
    }
    
    public float func_150893_a(final ItemStack itemStack, final Block block) {
        final Material material = block.func_149688_o();
        if (material == Material.field_151573_f) {
            return 20.5f;
        }
        if (material instanceof MaterialLogic) {
            return 5.5f;
        }
        return 2.0f;
    }
    
    public static int getUseAnimCount(final ItemStack stack) {
        return getAnimCount(stack, "MCH_WrenchAnim");
    }
    
    public static void setUseAnimCount(final ItemStack stack, final int n) {
        setAnimCount(stack, "MCH_WrenchAnim", n);
    }
    
    public static int getAnimCount(final ItemStack stack, final String name) {
        if (!stack.func_77942_o()) {
            stack.field_77990_d = new NBTTagCompound();
        }
        if (stack.field_77990_d.func_74764_b(name)) {
            return stack.field_77990_d.func_74762_e(name);
        }
        stack.field_77990_d.func_74768_a(name, 0);
        return 0;
    }
    
    public static void setAnimCount(final ItemStack stack, final String name, final int n) {
        if (!stack.func_77942_o()) {
            stack.field_77990_d = new NBTTagCompound();
        }
        stack.field_77990_d.func_74768_a(name, n);
    }
    
    public boolean func_77644_a(final ItemStack itemStack, final EntityLivingBase entity, final EntityLivingBase player) {
        if (!player.field_70170_p.field_72995_K) {
            if (MCH_ItemWrench.rand.nextInt(40) == 0) {
                entity.func_70099_a(new ItemStack(W_Item.getItemByName("iron_ingot"), 1, 0), 0.0f);
            }
            else if (MCH_ItemWrench.rand.nextInt(20) == 0) {
                entity.func_70099_a(new ItemStack(W_Item.getItemByName("gunpowder"), 1, 0), 0.0f);
            }
        }
        itemStack.func_77972_a(2, player);
        return true;
    }
    
    public void func_77615_a(final ItemStack stack, final World world, final EntityPlayer player, final int count) {
        setUseAnimCount(stack, 0);
    }
    
    public void onUsingTick(final ItemStack stack, final EntityPlayer player, final int count) {
        if (player.field_70170_p.field_72995_K) {
            final MCH_EntityAircraft ac = this.getMouseOverAircraft(player);
            if (ac != null) {
                int cnt = getUseAnimCount(stack);
                if (cnt <= 0) {
                    cnt = 16;
                }
                else {
                    --cnt;
                }
                setUseAnimCount(stack, cnt);
            }
        }
        if (!player.field_70170_p.field_72995_K && count < this.func_77626_a(stack) && count % 20 == 0) {
            final MCH_EntityAircraft ac = this.getMouseOverAircraft(player);
            if (ac != null && ac.getHP() > 0 && ac.repair(10)) {
                stack.func_77972_a(1, (EntityLivingBase)player);
                W_WorldFunc.MOD_playSoundEffect(player.field_70170_p, (int)ac.field_70165_t, (int)ac.field_70163_u, (int)ac.field_70161_v, "wrench", 1.0f, 0.9f + MCH_ItemWrench.rand.nextFloat() * 0.2f);
            }
        }
    }
    
    public void func_77663_a(final ItemStack item, final World world, final Entity entity, final int n, final boolean b) {
        if (entity instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)entity;
            final ItemStack itemStack = player.func_71045_bC();
            if (itemStack == item) {
                MCH_MOD.proxy.setCreativeDigDelay(0);
            }
        }
    }
    
    public MCH_EntityAircraft getMouseOverAircraft(final EntityPlayer player) {
        final MovingObjectPosition m = this.getMouseOver((EntityLivingBase)player, 1.0f);
        MCH_EntityAircraft ac = null;
        if (m != null) {
            if (m.field_72308_g instanceof MCH_EntityAircraft) {
                ac = (MCH_EntityAircraft)m.field_72308_g;
            }
            else if (m.field_72308_g instanceof MCH_EntitySeat) {
                final MCH_EntitySeat seat = (MCH_EntitySeat)m.field_72308_g;
                if (seat.getParent() != null) {
                    ac = seat.getParent();
                }
            }
        }
        return ac;
    }
    
    private static MovingObjectPosition rayTrace(final EntityLivingBase entity, final double dist, final float tick) {
        final Vec3 vec3 = Vec3.func_72443_a(entity.field_70165_t, entity.field_70163_u + entity.func_70047_e(), entity.field_70161_v);
        final Vec3 vec4 = entity.func_70676_i(tick);
        final Vec3 vec5 = vec3.func_72441_c(vec4.field_72450_a * dist, vec4.field_72448_b * dist, vec4.field_72449_c * dist);
        return entity.field_70170_p.func_147447_a(vec3, vec5, false, false, true);
    }
    
    private MovingObjectPosition getMouseOver(final EntityLivingBase user, final float tick) {
        Entity pointedEntity = null;
        final double d0 = 4.0;
        MovingObjectPosition objectMouseOver = rayTrace(user, d0, tick);
        double d2 = d0;
        final Vec3 vec3 = Vec3.func_72443_a(user.field_70165_t, user.field_70163_u + user.func_70047_e(), user.field_70161_v);
        if (objectMouseOver != null) {
            d2 = objectMouseOver.field_72307_f.func_72438_d(vec3);
        }
        final Vec3 vec4 = user.func_70676_i(tick);
        final Vec3 vec5 = vec3.func_72441_c(vec4.field_72450_a * d0, vec4.field_72448_b * d0, vec4.field_72449_c * d0);
        pointedEntity = null;
        Vec3 vec6 = null;
        final float f1 = 1.0f;
        final List list = user.field_70170_p.func_72839_b((Entity)user, user.field_70121_D.func_72321_a(vec4.field_72450_a * d0, vec4.field_72448_b * d0, vec4.field_72449_c * d0).func_72314_b((double)f1, (double)f1, (double)f1));
        double d3 = d2;
        for (int i = 0; i < list.size(); ++i) {
            final Entity entity = list.get(i);
            if (entity.func_70067_L()) {
                final float f2 = entity.func_70111_Y();
                final AxisAlignedBB axisalignedbb = entity.field_70121_D.func_72314_b((double)f2, (double)f2, (double)f2);
                final MovingObjectPosition movingobjectposition = axisalignedbb.func_72327_a(vec3, vec5);
                if (axisalignedbb.func_72318_a(vec3)) {
                    if (0.0 < d3 || d3 == 0.0) {
                        pointedEntity = entity;
                        vec6 = ((movingobjectposition == null) ? vec3 : movingobjectposition.field_72307_f);
                        d3 = 0.0;
                    }
                }
                else if (movingobjectposition != null) {
                    final double d4 = vec3.func_72438_d(movingobjectposition.field_72307_f);
                    if (d4 < d3 || d3 == 0.0) {
                        if (entity == user.field_70154_o && !entity.canRiderInteract()) {
                            if (d3 == 0.0) {
                                pointedEntity = entity;
                                vec6 = movingobjectposition.field_72307_f;
                            }
                        }
                        else {
                            pointedEntity = entity;
                            vec6 = movingobjectposition.field_72307_f;
                            d3 = d4;
                        }
                    }
                }
            }
        }
        if (pointedEntity != null && (d3 < d2 || objectMouseOver == null)) {
            objectMouseOver = new MovingObjectPosition(pointedEntity, vec6);
        }
        return objectMouseOver;
    }
    
    public boolean func_150894_a(final ItemStack itemStack, final World world, final Block block, final int x, final int y, final int z, final EntityLivingBase entity) {
        if (block.func_149712_f(world, x, y, z) != 0.0) {
            itemStack.func_77972_a(2, entity);
        }
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean func_77662_d() {
        return true;
    }
    
    public EnumAction func_77661_b(final ItemStack itemStack) {
        return EnumAction.block;
    }
    
    public int func_77626_a(final ItemStack itemStack) {
        return 72000;
    }
    
    public ItemStack func_77659_a(final ItemStack itemStack, final World world, final EntityPlayer player) {
        player.func_71008_a(itemStack, this.func_77626_a(itemStack));
        return itemStack;
    }
    
    public int func_77619_b() {
        return this.toolMaterial.func_77995_e();
    }
    
    public String getToolMaterialName() {
        return this.toolMaterial.toString();
    }
    
    public boolean func_82789_a(final ItemStack item1, final ItemStack item2) {
        return this.toolMaterial.func_150995_f() == item2.func_77973_b() || super.func_82789_a(item1, item2);
    }
    
    public Multimap func_111205_h() {
        final Multimap multimap = super.func_111205_h();
        multimap.put((Object)SharedMonsterAttributes.field_111264_e.func_111108_a(), (Object)new AttributeModifier(MCH_ItemWrench.field_111210_e, "Weapon modifier", (double)this.damageVsEntity, 0));
        return multimap;
    }
    
    static {
        MCH_ItemWrench.rand = new Random();
    }
}
