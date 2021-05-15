package mcheli.uav;

import net.minecraft.entity.*;
import cpw.mods.fml.relauncher.*;
import mcheli.aircraft.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import mcheli.multiplay.*;
import net.minecraft.entity.player.*;
import mcheli.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import mcheli.wrapper.*;
import mcheli.plane.*;
import mcheli.helicopter.*;
import mcheli.tank.*;

public class MCH_EntityUavStation extends W_EntityContainer
{
    protected static final int DATAWT_ID_KIND = 27;
    protected static final int DATAWT_ID_LAST_AC = 28;
    protected static final int DATAWT_ID_UAV_X = 29;
    protected static final int DATAWT_ID_UAV_Y = 30;
    protected static final int DATAWT_ID_UAV_Z = 31;
    protected Entity lastRiddenByEntity;
    public boolean isRequestedSyncStatus;
    @SideOnly(Side.CLIENT)
    protected double velocityX;
    @SideOnly(Side.CLIENT)
    protected double velocityY;
    @SideOnly(Side.CLIENT)
    protected double velocityZ;
    protected int aircraftPosRotInc;
    protected double aircraftX;
    protected double aircraftY;
    protected double aircraftZ;
    protected double aircraftYaw;
    protected double aircraftPitch;
    private MCH_EntityAircraft controlAircraft;
    private MCH_EntityAircraft lastControlAircraft;
    private String loadedLastControlAircraftGuid;
    public int posUavX;
    public int posUavY;
    public int posUavZ;
    public float rotCover;
    public float prevRotCover;
    
    public MCH_EntityUavStation(final World world) {
        super(world);
        this.dropContentsWhenDead = false;
        this.field_70156_m = true;
        this.func_70105_a(2.0f, 0.7f);
        this.field_70129_M = this.field_70131_O / 2.0f;
        this.field_70159_w = 0.0;
        this.field_70181_x = 0.0;
        this.field_70179_y = 0.0;
        this.field_70158_ak = true;
        this.lastRiddenByEntity = null;
        this.aircraftPosRotInc = 0;
        this.aircraftX = 0.0;
        this.aircraftY = 0.0;
        this.aircraftZ = 0.0;
        this.aircraftYaw = 0.0;
        this.aircraftPitch = 0.0;
        this.posUavX = 0;
        this.posUavY = 0;
        this.posUavZ = 0;
        this.rotCover = 0.0f;
        this.prevRotCover = 0.0f;
        this.setControlAircract(null);
        this.setLastControlAircraft(null);
        this.loadedLastControlAircraftGuid = "";
    }
    
    @Override
    protected void func_70088_a() {
        super.func_70088_a();
        this.func_70096_w().func_75682_a(27, (Object)(byte)0);
        this.func_70096_w().func_75682_a(28, (Object)0);
        this.func_70096_w().func_75682_a(29, (Object)0);
        this.func_70096_w().func_75682_a(30, (Object)0);
        this.func_70096_w().func_75682_a(31, (Object)0);
        this.setOpen(true);
    }
    
    public int getStatus() {
        return this.func_70096_w().func_75683_a(27);
    }
    
    public void setStatus(final int n) {
        if (!this.field_70170_p.field_72995_K) {
            MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityUavStation.setStatus(%d)", n);
            this.func_70096_w().func_75692_b(27, (Object)(byte)n);
        }
    }
    
    public int getKind() {
        return 0x7F & this.getStatus();
    }
    
    public void setKind(final int n) {
        this.setStatus((this.getStatus() & 0x80) | n);
    }
    
    public boolean isOpen() {
        return (this.getStatus() & 0x80) != 0x0;
    }
    
    public void setOpen(final boolean b) {
        this.setStatus((b ? 128 : 0) | (this.getStatus() & 0x7F));
    }
    
    public MCH_EntityAircraft getControlAircract() {
        return this.controlAircraft;
    }
    
    public void setControlAircract(final MCH_EntityAircraft ac) {
        this.controlAircraft = ac;
        if (ac != null && !ac.field_70128_L) {
            this.setLastControlAircraft(ac);
        }
    }
    
    public void setUavPosition(final int x, final int y, final int z) {
        if (!this.field_70170_p.field_72995_K) {
            this.posUavX = x;
            this.posUavY = y;
            this.posUavZ = z;
            this.func_70096_w().func_75692_b(29, (Object)x);
            this.func_70096_w().func_75692_b(30, (Object)y);
            this.func_70096_w().func_75692_b(31, (Object)z);
        }
    }
    
    public void updateUavPosition() {
        this.posUavX = this.func_70096_w().func_75679_c(29);
        this.posUavY = this.func_70096_w().func_75679_c(30);
        this.posUavZ = this.func_70096_w().func_75679_c(31);
    }
    
    @Override
    protected void func_70014_b(final NBTTagCompound nbt) {
        super.func_70014_b(nbt);
        nbt.func_74768_a("UavStatus", this.getStatus());
        nbt.func_74768_a("PosUavX", this.posUavX);
        nbt.func_74768_a("PosUavY", this.posUavY);
        nbt.func_74768_a("PosUavZ", this.posUavZ);
        String s = "";
        if (this.getLastControlAircraft() != null && !this.getLastControlAircraft().field_70128_L) {
            s = this.getLastControlAircraft().getCommonUniqueId();
        }
        if (s.isEmpty()) {
            s = this.loadedLastControlAircraftGuid;
        }
        nbt.func_74778_a("LastCtrlAc", s);
    }
    
    @Override
    protected void func_70037_a(final NBTTagCompound nbt) {
        super.func_70037_a(nbt);
        this.setUavPosition(nbt.func_74762_e("PosUavX"), nbt.func_74762_e("PosUavY"), nbt.func_74762_e("PosUavZ"));
        if (nbt.func_74764_b("UavStatus")) {
            this.setStatus(nbt.func_74762_e("UavStatus"));
        }
        else {
            this.setKind(1);
        }
        this.loadedLastControlAircraftGuid = nbt.func_74779_i("LastCtrlAc");
    }
    
    public void initUavPostion() {
        final int rt = (int)(MCH_Lib.getRotate360(this.field_70177_z + 45.0f) / 90.0);
        final int D = 12;
        this.posUavX = ((rt == 0 || rt == 3) ? 12 : -12);
        this.posUavZ = ((rt == 0 || rt == 1) ? 12 : -12);
        this.posUavY = 2;
        this.setUavPosition(this.posUavX, this.posUavY, this.posUavZ);
    }
    
    @Override
    public void func_70106_y() {
        super.func_70106_y();
    }
    
    @Override
    public boolean func_70097_a(final DamageSource damageSource, float damage) {
        if (this.func_85032_ar()) {
            return false;
        }
        if (this.field_70128_L) {
            return true;
        }
        if (this.field_70170_p.field_72995_K) {
            return true;
        }
        final String dmt = damageSource.func_76355_l();
        final MCH_Config config = MCH_MOD.config;
        damage = MCH_Config.applyDamageByExternal(this, damageSource, damage);
        if (!MCH_Multiplay.canAttackEntity(damageSource, this)) {
            return false;
        }
        boolean isCreative = false;
        final Entity entity = damageSource.func_76346_g();
        boolean isDamegeSourcePlayer = false;
        if (entity instanceof EntityPlayer) {
            isCreative = ((EntityPlayer)entity).field_71075_bZ.field_75098_d;
            if (dmt.compareTo("player") == 0) {
                isDamegeSourcePlayer = true;
            }
            W_WorldFunc.MOD_playSoundAtEntity(this, "hit", 1.0f, 1.0f);
        }
        else {
            W_WorldFunc.MOD_playSoundAtEntity(this, "helidmg", 1.0f, 0.9f + this.field_70146_Z.nextFloat() * 0.1f);
        }
        this.func_70018_K();
        if (damage > 0.0f) {
            if (this.field_70153_n != null) {
                this.field_70153_n.func_70078_a((Entity)this);
            }
            this.dropContentsWhenDead = true;
            this.func_70106_y();
            if (!isDamegeSourcePlayer) {
                MCH_Explosion.newExplosion(this.field_70170_p, null, this.field_70153_n, this.field_70165_t, this.field_70163_u, this.field_70161_v, 1.0f, 0.0f, true, true, false, false, 0);
            }
            if (!isCreative) {
                final int kind = this.getKind();
                if (kind > 0) {
                    this.dropItemWithOffset(MCH_MOD.itemUavStation[kind - 1], 1, 0.0f);
                }
            }
        }
        return true;
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
        if (this.getKind() == 2 && this.field_70153_n != null) {
            final double px = -Math.sin(this.field_70177_z * 3.141592653589793 / 180.0) * 0.9;
            final double pz = Math.cos(this.field_70177_z * 3.141592653589793 / 180.0) * 0.9;
            final int x = (int)(this.field_70165_t + px);
            final int y = (int)(this.field_70163_u - 0.5);
            final int z = (int)(this.field_70161_v + pz);
            final Block block = this.field_70170_p.func_147439_a(x, y, z);
            return block.func_149662_c() ? -0.4 : -0.9;
        }
        return 0.35;
    }
    
    @SideOnly(Side.CLIENT)
    public float func_70053_R() {
        return 2.0f;
    }
    
    public boolean func_70067_L() {
        return !this.field_70128_L;
    }
    
    public void func_70108_f(final Entity par1Entity) {
    }
    
    public void func_70024_g(final double par1, final double par3, final double par5) {
    }
    
    @SideOnly(Side.CLIENT)
    public void func_70016_h(final double par1, final double par3, final double par5) {
        this.field_70159_w = par1;
        this.velocityX = par1;
        this.field_70181_x = par3;
        this.velocityY = par3;
        this.field_70179_y = par5;
        this.velocityZ = par5;
    }
    
    public void func_70071_h_() {
        super.func_70071_h_();
        this.prevRotCover = this.rotCover;
        if (this.isOpen()) {
            if (this.rotCover < 1.0f) {
                this.rotCover += 0.1f;
            }
            else {
                this.rotCover = 1.0f;
            }
        }
        else if (this.rotCover > 0.0f) {
            this.rotCover -= 0.1f;
        }
        else {
            this.rotCover = 0.0f;
        }
        if (this.field_70153_n == null) {
            if (this.lastRiddenByEntity != null) {
                this.unmountEntity(true);
            }
            this.setControlAircract(null);
        }
        final int uavStationKind = this.getKind();
        if (this.field_70173_aa < 30 && uavStationKind > 0) {
            if (uavStationKind != 1) {
                if (uavStationKind == 2) {}
            }
        }
        if (this.field_70170_p.field_72995_K && !this.isRequestedSyncStatus) {
            this.isRequestedSyncStatus = true;
        }
        this.field_70169_q = this.field_70165_t;
        this.field_70167_r = this.field_70163_u;
        this.field_70166_s = this.field_70161_v;
        if (this.getControlAircract() != null && this.getControlAircract().field_70128_L) {
            this.setControlAircract(null);
        }
        if (this.getLastControlAircraft() != null && this.getLastControlAircraft().field_70128_L) {
            this.setLastControlAircraft(null);
        }
        if (this.field_70170_p.field_72995_K) {
            this.onUpdate_Client();
        }
        else {
            this.onUpdate_Server();
        }
        this.lastRiddenByEntity = this.field_70153_n;
    }
    
    public MCH_EntityAircraft getLastControlAircraft() {
        return this.lastControlAircraft;
    }
    
    public MCH_EntityAircraft getAndSearchLastControlAircraft() {
        if (this.getLastControlAircraft() == null) {
            final int id = this.getLastControlAircraftEntityId();
            if (id > 0) {
                final Entity entity = this.field_70170_p.func_73045_a(id);
                if (entity instanceof MCH_EntityAircraft) {
                    final MCH_EntityAircraft ac = (MCH_EntityAircraft)entity;
                    if (ac.isUAV()) {
                        this.setLastControlAircraft(ac);
                    }
                }
            }
        }
        return this.getLastControlAircraft();
    }
    
    public void setLastControlAircraft(final MCH_EntityAircraft ac) {
        MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityUavStation.setLastControlAircraft:" + ac, new Object[0]);
        this.lastControlAircraft = ac;
    }
    
    public Integer getLastControlAircraftEntityId() {
        return this.func_70096_w().func_75679_c(28);
    }
    
    public void setLastControlAircraftEntityId(final int s) {
        if (!this.field_70170_p.field_72995_K) {
            this.func_70096_w().func_75692_b(28, (Object)s);
        }
    }
    
    public void searchLastControlAircraft() {
        if (this.loadedLastControlAircraftGuid.isEmpty()) {
            return;
        }
        final List list = this.field_70170_p.func_72872_a((Class)MCH_EntityAircraft.class, this.func_70046_E().func_72314_b(120.0, 120.0, 120.0));
        if (list == null) {
            return;
        }
        for (int i = 0; i < list.size(); ++i) {
            final MCH_EntityAircraft ac = list.get(i);
            if (ac.getCommonUniqueId().equals(this.loadedLastControlAircraftGuid)) {
                final String n = (ac.getAcInfo() != null) ? ac.getAcInfo().displayName : ("no info : " + ac);
                MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityUavStation.searchLastControlAircraft:found" + n, new Object[0]);
                this.setLastControlAircraft(ac);
                this.setLastControlAircraftEntityId(W_Entity.getEntityId(ac));
                this.loadedLastControlAircraftGuid = "";
                return;
            }
        }
    }
    
    protected void onUpdate_Client() {
        if (this.aircraftPosRotInc > 0) {
            final double rpinc = this.aircraftPosRotInc;
            final double yaw = MathHelper.func_76138_g(this.aircraftYaw - this.field_70177_z);
            this.field_70177_z += (float)(yaw / rpinc);
            this.field_70125_A += (float)((this.aircraftPitch - this.field_70125_A) / rpinc);
            this.func_70107_b(this.field_70165_t + (this.aircraftX - this.field_70165_t) / rpinc, this.field_70163_u + (this.aircraftY - this.field_70163_u) / rpinc, this.field_70161_v + (this.aircraftZ - this.field_70161_v) / rpinc);
            this.func_70101_b(this.field_70177_z, this.field_70125_A);
            --this.aircraftPosRotInc;
        }
        else {
            this.func_70107_b(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
            this.field_70181_x *= 0.96;
            this.field_70159_w = 0.0;
            this.field_70179_y = 0.0;
        }
        this.updateUavPosition();
    }
    
    private void onUpdate_Server() {
        this.func_70091_d(0.0, this.field_70181_x -= 0.03, 0.0);
        this.field_70181_x *= 0.96;
        this.field_70159_w = 0.0;
        this.field_70179_y = 0.0;
        this.func_70101_b(this.field_70177_z, this.field_70125_A);
        if (this.field_70153_n != null) {
            if (this.field_70153_n.field_70128_L) {
                this.unmountEntity(true);
                this.field_70153_n = null;
            }
            else {
                final ItemStack item = this.func_70301_a(0);
                if (item != null && item.field_77994_a > 0) {
                    this.handleItem(this.field_70153_n, item);
                    if (item.field_77994_a == 0) {
                        this.func_70299_a(0, null);
                    }
                }
            }
        }
        if (this.getLastControlAircraft() == null && this.field_70173_aa % 40 == 0) {
            this.searchLastControlAircraft();
        }
    }
    
    public void func_70056_a(final double par1, final double par3, final double par5, final float par7, final float par8, final int par9) {
        this.aircraftPosRotInc = par9 + 8;
        this.aircraftX = par1;
        this.aircraftY = par3;
        this.aircraftZ = par5;
        this.aircraftYaw = par7;
        this.aircraftPitch = par8;
        this.field_70159_w = this.velocityX;
        this.field_70181_x = this.velocityY;
        this.field_70179_y = this.velocityZ;
    }
    
    public void func_70043_V() {
        if (this.field_70153_n != null) {
            final double x = -Math.sin(this.field_70177_z * 3.141592653589793 / 180.0) * 0.9;
            final double z = Math.cos(this.field_70177_z * 3.141592653589793 / 180.0) * 0.9;
            this.field_70153_n.func_70107_b(this.field_70165_t + x, this.field_70163_u + this.func_70042_X() + this.field_70153_n.func_70033_W(), this.field_70161_v + z);
        }
    }
    
    public void controlLastAircraft(final Entity user) {
        if (this.getLastControlAircraft() != null && !this.getLastControlAircraft().field_70128_L) {
            this.getLastControlAircraft().setUavStation(this);
            this.setControlAircract(this.getLastControlAircraft());
            W_EntityPlayer.closeScreen(user);
        }
    }
    
    public void handleItem(final Entity user, final ItemStack itemStack) {
        if (user == null || user.field_70128_L || itemStack == null || itemStack.field_77994_a != 1) {
            return;
        }
        if (this.field_70170_p.field_72995_K) {
            return;
        }
        MCH_EntityAircraft ac = null;
        final double x = this.field_70165_t + this.posUavX;
        double y = this.field_70163_u + this.posUavY;
        final double z = this.field_70161_v + this.posUavZ;
        if (y <= 1.0) {
            y = 2.0;
        }
        final Item item = itemStack.func_77973_b();
        if (item instanceof MCP_ItemPlane) {
            final MCP_PlaneInfo pi = MCP_PlaneInfoManager.getFromItem(item);
            if (pi != null && pi.isUAV) {
                if (!pi.isSmallUAV && this.getKind() == 2) {
                    ac = null;
                }
                else {
                    ac = ((MCP_ItemPlane)item).createAircraft(this.field_70170_p, x, y, z, itemStack);
                }
            }
        }
        if (item instanceof MCH_ItemHeli) {
            final MCH_HeliInfo hi = MCH_HeliInfoManager.getFromItem(item);
            if (hi != null && hi.isUAV) {
                if (!hi.isSmallUAV && this.getKind() == 2) {
                    ac = null;
                }
                else {
                    ac = ((MCH_ItemHeli)item).createAircraft(this.field_70170_p, x, y, z, itemStack);
                }
            }
        }
        if (item instanceof MCH_ItemTank) {
            final MCH_TankInfo hi2 = MCH_TankInfoManager.getFromItem(item);
            if (hi2 != null && hi2.isUAV) {
                if (!hi2.isSmallUAV && this.getKind() == 2) {
                    ac = null;
                }
                else {
                    ac = ((MCH_ItemTank)item).createAircraft(this.field_70170_p, x, y, z, itemStack);
                }
            }
        }
        if (ac == null) {
            return;
        }
        ac.field_70177_z = this.field_70177_z - 180.0f;
        ac.field_70126_B = ac.field_70177_z;
        user.field_70177_z = this.field_70177_z - 180.0f;
        if (this.field_70170_p.func_72945_a((Entity)ac, ac.field_70121_D.func_72314_b(-0.1, -0.1, -0.1)).isEmpty()) {
            --itemStack.field_77994_a;
            MCH_Lib.DbgLog(this.field_70170_p, "Create UAV: %s : %s", item.func_77658_a(), item);
            user.field_70177_z = this.field_70177_z - 180.0f;
            if (!ac.isTargetDrone()) {
                ac.setUavStation(this);
                this.setControlAircract(ac);
            }
            this.field_70170_p.func_72838_d((Entity)ac);
            if (!ac.isTargetDrone()) {
                ac.setFuel((int)(ac.getMaxFuel() * 0.05f));
                W_EntityPlayer.closeScreen(user);
            }
            else {
                ac.setFuel(ac.getMaxFuel());
            }
        }
        else {
            ac.func_70106_y();
        }
    }
    
    public void _setInventorySlotContents(final int par1, final ItemStack itemStack) {
        super.func_70299_a(par1, itemStack);
    }
    
    @Override
    public boolean func_130002_c(final EntityPlayer player) {
        final int kind = this.getKind();
        if (kind <= 0) {
            return false;
        }
        if (this.field_70153_n != null) {
            return false;
        }
        if (kind == 2) {
            if (player.func_70093_af()) {
                this.setOpen(!this.isOpen());
                return false;
            }
            if (!this.isOpen()) {
                return false;
            }
        }
        this.field_70153_n = null;
        this.lastRiddenByEntity = null;
        if (!this.field_70170_p.field_72995_K) {
            player.func_70078_a((Entity)this);
            player.openGui((Object)MCH_MOD.instance, 0, player.field_70170_p, (int)this.field_70165_t, (int)this.field_70163_u, (int)this.field_70161_v);
        }
        return true;
    }
    
    @Override
    public int func_70302_i_() {
        return 1;
    }
    
    @Override
    public int func_70297_j_() {
        return 1;
    }
    
    public void unmountEntity(final boolean unmountAllEntity) {
        Entity rByEntity = null;
        if (this.field_70153_n != null) {
            if (!this.field_70170_p.field_72995_K) {
                rByEntity = this.field_70153_n;
                this.field_70153_n.func_70078_a((Entity)null);
            }
        }
        else if (this.lastRiddenByEntity != null) {
            rByEntity = this.lastRiddenByEntity;
        }
        if (this.getControlAircract() != null) {
            this.getControlAircract().setUavStation(null);
        }
        this.setControlAircract(null);
        if (this.field_70170_p.field_72995_K) {
            W_EntityPlayer.closeScreen(rByEntity);
        }
        this.field_70153_n = null;
        this.lastRiddenByEntity = null;
    }
}
