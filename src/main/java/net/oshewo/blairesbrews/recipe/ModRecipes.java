package net.oshewo.blairesbrews.recipe;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.oshewo.blairesbrews.BlairesBrews;

public class ModRecipes  {
    public static void registerRecipes() {
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(BlairesBrews.MOD_ID, BrewingKettleRecipe.Serializer.ID),
                BrewingKettleRecipe.Serializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(BlairesBrews.MOD_ID, BrewingKettleRecipe.Type.ID),
                BrewingKettleRecipe.Type.INSTANCE);
    }
}
