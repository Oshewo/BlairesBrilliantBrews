package net.oshewo.blairesbrews.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.oshewo.blairesbrews.BlairesBrews;
import net.oshewo.blairesbrews.item.custom.ModDrinkItem;

public class ModItems {
    public static final Item VAGUE_GLASS = registerItem("vague_glass",
            new Item(new FabricItemSettings().group(ModItemGroup.BREWS)));
    public static final Item REGAL_RED = registerItem("regal_red",
            new ModDrinkItem(new FabricItemSettings().group(ModItemGroup.BREWS).food(ModFoodComponents.REGAL_RED)));

    public static final Item TRAVEL_TINCTURE = registerItem("travel_tincture",
        new ModDrinkItem(new FabricItemSettings().group(ModItemGroup.BREWS).food(ModFoodComponents.TRAVEL_TINCTURE)));

    public static final Item TRAVEL_TINCTURE_LV2 = registerItem("travel_tincture_lv2", new ModDrinkItem(new FabricItemSettings().group(ModItemGroup.BREWS).food(ModFoodComponents.TRAVEL_TINCTURE_LV2)));




    private static Item registerItem(String name, Item item){
        return Registry.register(Registry.ITEM,new Identifier(BlairesBrews.MOD_ID, name), item);
    }
    public static void registerModItems() {
        BlairesBrews.LOGGER.info("Registering Mod Items for "+ BlairesBrews.MOD_ID);
    }
}
