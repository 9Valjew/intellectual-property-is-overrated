package mcheli;

import net.minecraft.item.crafting.*;

public interface MCH_IRecipeList
{
    int getRecipeListSize();
    
    IRecipe getRecipe(final int p0);
}
