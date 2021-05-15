package mcheli.aircraft;

import net.minecraft.util.*;
import mcheli.*;

public class MCH_BoundingBox
{
    public final AxisAlignedBB boundingBox;
    public final AxisAlignedBB backupBoundingBox;
    public final double offsetX;
    public final double offsetY;
    public final double offsetZ;
    public final float width;
    public final float height;
    public Vec3 rotatedOffset;
    public Vec3 nowPos;
    public Vec3 prevPos;
    public final float damegeFactor;
    
    public MCH_BoundingBox(final double x, final double y, final double z, final float w, final float h, final float df) {
        this.offsetX = x;
        this.offsetY = y;
        this.offsetZ = z;
        this.width = w;
        this.height = h;
        this.damegeFactor = df;
        this.boundingBox = AxisAlignedBB.func_72330_a(x - w / 2.0f, y - h / 2.0f, z - w / 2.0f, x + w / 2.0f, y + h / 2.0f, z + w / 2.0f);
        this.backupBoundingBox = AxisAlignedBB.func_72330_a(x - w / 2.0f, y - h / 2.0f, z - w / 2.0f, x + w / 2.0f, y + h / 2.0f, z + w / 2.0f);
        this.nowPos = Vec3.func_72443_a(x, y, z);
        this.prevPos = Vec3.func_72443_a(x, y, z);
        this.updatePosition(0.0, 0.0, 0.0, 0.0f, 0.0f, 0.0f);
    }
    
    public MCH_BoundingBox copy() {
        return new MCH_BoundingBox(this.offsetX, this.offsetY, this.offsetZ, this.width, this.height, this.damegeFactor);
    }
    
    public void updatePosition(final double posX, final double posY, final double posZ, final float yaw, final float pitch, final float roll) {
        final Vec3 v = Vec3.func_72443_a(this.offsetX, this.offsetY, this.offsetZ);
        this.rotatedOffset = MCH_Lib.RotVec3(v, -yaw, -pitch, -roll);
        final float w = this.width;
        final float h = this.height;
        final double x = posX + this.rotatedOffset.field_72450_a;
        final double y = posY + this.rotatedOffset.field_72448_b;
        final double z = posZ + this.rotatedOffset.field_72449_c;
        this.prevPos.field_72450_a = this.nowPos.field_72450_a;
        this.prevPos.field_72448_b = this.nowPos.field_72448_b;
        this.prevPos.field_72449_c = this.nowPos.field_72449_c;
        this.nowPos.field_72450_a = x;
        this.nowPos.field_72448_b = y;
        this.nowPos.field_72449_c = z;
        this.backupBoundingBox.func_72328_c(this.boundingBox);
        this.boundingBox.func_72324_b(x - w / 2.0f, y - h / 2.0f, z - w / 2.0f, x + w / 2.0f, y + h / 2.0f, z + w / 2.0f);
    }
}
