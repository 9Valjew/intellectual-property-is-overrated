package mcheli.aircraft;

import mcheli.wrapper.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import cpw.mods.fml.relauncher.*;
import mcheli.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import mcheli.tool.*;
import net.minecraft.item.*;

public class MCH_EntitySeat extends W_Entity
{
    public String parentUniqueID;
    private MCH_EntityAircraft parent;
    public int seatID;
    public int parentSearchCount;
    protected Entity lastRiddenByEntity;
    public static final float BB_SIZE = 1.0f;
    
    public MCH_EntitySeat(final World world) {
        super(world);
        this.func_70105_a(1.0f, 1.0f);
        this.field_70129_M = 0.0f;
        this.field_70159_w = 0.0;
        this.field_70181_x = 0.0;
        this.field_70179_y = 0.0;
        this.seatID = -1;
        this.setParent(null);
        this.parentSearchCount = 0;
        this.lastRiddenByEntity = null;
        this.field_70158_ak = true;
        this.field_70178_ae = true;
    }
    
    public MCH_EntitySeat(final World world, final double x, final double y, final double z) {
        this(world);
        this.func_70107_b(x, y + 1.0, z);
        this.field_70169_q = x;
        this.field_70167_r = y + 1.0;
        this.field_70166_s = z;
    }
    
    protected boolean func_70041_e_() {
        return false;
    }
    
    public AxisAlignedBB func_70114_g(final Entity par1Entity) {
        return par1Entity.field_70121_D;
    }
    
    public AxisAlignedBB func_70046_E() {
        return this.field_70121_D;
    }
    
    public boolean func_70104_M() {
        return false;
    }
    
    public double func_70042_X() {
        return -0.3;
    }
    
    @Override
    public boolean func_70097_a(final DamageSource par1DamageSource, final float par2) {
        return this.getParent() != null && this.getParent().func_70097_a(par1DamageSource, par2);
    }
    
    public boolean func_70067_L() {
        return !this.field_70128_L;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_70056_a(final double par1, final double par3, final double par5, final float par7, final float par8, final int par9) {
    }
    
    public void func_70106_y() {
        super.func_70106_y();
    }
    
    public void func_70071_h_() {
        super.func_70071_h_();
        this.field_70143_R = 0.0f;
        if (this.field_70153_n != null) {
            this.field_70153_n.field_70143_R = 0.0f;
        }
        if (this.lastRiddenByEntity == null && this.field_70153_n != null) {
            if (this.getParent() != null) {
                MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntitySeat.onUpdate:SeatID=%d", this.seatID, this.field_70153_n.toString());
                this.getParent().onMountPlayerSeat(this, this.field_70153_n);
            }
        }
        else if (this.lastRiddenByEntity != null && this.field_70153_n == null && this.getParent() != null) {
            MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntitySeat.onUpdate:SeatID=%d", this.seatID, this.lastRiddenByEntity.toString());
            this.getParent().onUnmountPlayerSeat(this, this.lastRiddenByEntity);
        }
        if (this.field_70170_p.field_72995_K) {
            this.onUpdate_Client();
        }
        else {
            this.onUpdate_Server();
        }
        this.lastRiddenByEntity = this.field_70153_n;
    }
    
    private void onUpdate_Client() {
        this.checkDetachmentAndDelete();
    }
    
    private void onUpdate_Server() {
        this.checkDetachmentAndDelete();
        if (this.field_70153_n != null && this.field_70153_n.field_70128_L) {
            this.field_70153_n = null;
        }
    }
    
    public void func_70043_V() {
        this.updatePosition();
    }
    
    public void updatePosition() {
        final Entity ridEnt = this.field_70153_n;
        if (ridEnt != null) {
            ridEnt.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
            final Entity entity = ridEnt;
            final Entity entity2 = ridEnt;
            final Entity entity3 = ridEnt;
            final double field_70159_w = 0.0;
            entity3.field_70179_y = field_70159_w;
            entity2.field_70181_x = field_70159_w;
            entity.field_70159_w = field_70159_w;
        }
    }
    
    public void updateRotation(final float yaw, final float pitch) {
        final Entity ridEnt = this.field_70153_n;
        if (ridEnt != null) {
            ridEnt.field_70177_z = yaw;
            ridEnt.field_70125_A = pitch;
        }
    }
    
    protected void checkDetachmentAndDelete() {
        if (!this.field_70128_L && (this.seatID < 0 || this.getParent() == null || this.getParent().field_70128_L)) {
            if (this.getParent() != null && this.getParent().field_70128_L) {
                this.parentSearchCount = 100000000;
            }
            if (this.parentSearchCount >= 1200) {
                this.func_70106_y();
                if (!this.field_70170_p.field_72995_K && this.field_70153_n != null) {
                    this.field_70153_n.func_70078_a((Entity)null);
                }
                this.setParent(null);
                MCH_Lib.DbgLog(this.field_70170_p, "[Error]\u5ea7\u5e2d\u30a8\u30f3\u30c6\u30a3\u30c6\u30a3\u306f\u672c\u4f53\u304c\u898b\u3064\u304b\u3089\u306a\u3044\u305f\u3081\u524a\u9664 seat=%d, parentUniqueID=%s", this.seatID, this.parentUniqueID);
            }
            else {
                ++this.parentSearchCount;
            }
        }
        else {
            this.parentSearchCount = 0;
        }
    }
    
    protected void func_70014_b(final NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.func_74768_a("SeatID", this.seatID);
        par1NBTTagCompound.func_74778_a("ParentUniqueID", this.parentUniqueID);
    }
    
    protected void func_70037_a(final NBTTagCompound par1NBTTagCompound) {
        this.seatID = par1NBTTagCompound.func_74762_e("SeatID");
        this.parentUniqueID = par1NBTTagCompound.func_74779_i("ParentUniqueID");
    }
    
    @SideOnly(Side.CLIENT)
    public float func_70053_R() {
        return 0.0f;
    }
    
    public boolean canRideMob(final Entity entity) {
        return this.getParent() != null && this.seatID >= 0 && !(this.getParent().getSeatInfo(this.seatID + 1) instanceof MCH_SeatRackInfo);
    }
    
    public boolean isGunnerMode() {
        return this.field_70153_n != null && this.getParent() != null && this.getParent().getIsGunnerMode(this.field_70153_n);
    }
    
    @Override
    public boolean func_130002_c(final EntityPlayer player) {
        if (this.getParent() == null || this.getParent().isDestroyed()) {
            return false;
        }
        if (!this.getParent().checkTeam(player)) {
            return false;
        }
        final ItemStack itemStack = player.func_71045_bC();
        if (itemStack != null && itemStack.func_77973_b() instanceof MCH_ItemWrench) {
            return this.getParent().func_130002_c(player);
        }
        if (this.field_70153_n != null) {
            return false;
        }
        if (player.field_70154_o != null) {
            return false;
        }
        if (!this.canRideMob((Entity)player)) {
            return false;
        }
        player.func_70078_a((Entity)this);
        return true;
    }
    
    public MCH_EntityAircraft getParent() {
        return this.parent;
    }
    
    public void setParent(final MCH_EntityAircraft parent) {
        this.parent = parent;
    }
}
