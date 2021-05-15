package mcheli.aircraft;

import net.minecraft.world.*;
import mcheli.*;
import net.minecraft.entity.*;
import mcheli.wrapper.*;
import net.minecraft.entity.monster.*;
import java.util.*;

public class MCH_Radar
{
    private World worldObj;
    private ArrayList<MCH_Vector2> entityList;
    private ArrayList<MCH_Vector2> enemyList;
    
    public ArrayList<MCH_Vector2> getEntityList() {
        return this.entityList;
    }
    
    public ArrayList<MCH_Vector2> getEnemyList() {
        return this.enemyList;
    }
    
    public MCH_Radar(final World world) {
        this.entityList = new ArrayList<MCH_Vector2>();
        this.enemyList = new ArrayList<MCH_Vector2>();
        this.worldObj = world;
    }
    
    public void clear() {
        this.entityList.clear();
        this.enemyList.clear();
    }
    
    public void updateXZ(final Entity centerEntity, final int range) {
        if (!this.worldObj.field_72995_K) {
            return;
        }
        this.clear();
        final List list = centerEntity.field_70170_p.func_72839_b(centerEntity, centerEntity.field_70121_D.func_72314_b((double)range, (double)range, (double)range));
        for (int i = 0; i < list.size(); ++i) {
            final Entity entity = list.get(i);
            if (entity instanceof EntityLiving) {
                final double x = entity.field_70165_t - centerEntity.field_70165_t;
                final double z = entity.field_70161_v - centerEntity.field_70161_v;
                if (x * x + z * z < range * range) {
                    int y = 1 + (int)entity.field_70163_u;
                    if (y < 0) {
                        y = 1;
                    }
                    int blockCnt;
                    for (blockCnt = 0; y < 200 && (W_WorldFunc.getBlockId(this.worldObj, (int)entity.field_70165_t, y, (int)entity.field_70161_v) == 0 || ++blockCnt < 5); ++y) {}
                    if (blockCnt < 5) {
                        if (entity instanceof EntityMob) {
                            this.enemyList.add(new MCH_Vector2(x, z));
                        }
                        else {
                            this.entityList.add(new MCH_Vector2(x, z));
                        }
                    }
                }
            }
        }
    }
}
