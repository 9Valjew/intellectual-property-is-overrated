package mcheli.wrapper;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import mcheli.*;

public class W_Lib
{
    public static boolean isEntityLivingBase(final Entity entity) {
        return entity instanceof EntityLivingBase;
    }
    
    public static EntityLivingBase castEntityLivingBase(final Object entity) {
        return (EntityLivingBase)entity;
    }
    
    public static Class getEntityLivingBaseClass() {
        return EntityLivingBase.class;
    }
    
    public static double getEntityMoveDist(final Entity entity) {
        if (entity == null) {
            return 0.0;
        }
        return (entity instanceof EntityLivingBase) ? ((EntityLivingBase)entity).field_70701_bs : 0.0;
    }
    
    public static boolean isClientPlayer(final Entity entity) {
        return entity instanceof EntityPlayer && entity.field_70170_p.field_72995_K && W_Entity.isEqual(MCH_MOD.proxy.getClientPlayer(), entity);
    }
    
    public static boolean isFirstPerson() {
        return MCH_MOD.proxy.isFirstPerson();
    }
}
