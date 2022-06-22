package net.oshewo.blairesbrews.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.oshewo.blairesbrews.BlairesBrews;
import net.oshewo.blairesbrews.block.custom.BrewingKettleBlock;
import net.oshewo.blairesbrews.item.ModItemGroup;

public class ModBlocks {

    public static final Block WITCHWOOD_LOG = registerBlock("witchwood_log",
            new Block(FabricBlockSettings.of(Material.WOOD)), ModItemGroup.BREWS);
    public static final Block WITCHWOOD_PLANKS = registerBlock("witchwood_planks",
            new Block(FabricBlockSettings.of(Material.WOOD)), ModItemGroup.BREWS);
    public static final Block WITCHWOOD_LEAVES = registerBlock("witchwood_leaves",
            new LeavesBlock(FabricBlockSettings.copy(Blocks.OAK_LEAVES).nonOpaque()),ModItemGroup.BREWS);
    public static final Block WITCHWOOD_SAPLING = registerBlock("witchwood_leaves",
            new SaplingBlock(,
                    FabricBlockSettings.copy(Blocks.OAK_SAPLING)),ModItemGroup.BREWS);


    public static final Block BREWING_KETTLE = registerBlock("brewing_kettle",
            new BrewingKettleBlock(FabricBlockSettings.of(Material.METAL).strength(2.0f).requiresTool().nonOpaque()), ModItemGroup.BREWS);





    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(BlairesBrews.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, new Identifier(BlairesBrews.MOD_ID, name),
            new BlockItem(block, new FabricItemSettings().group(group)));
    }
    public static void registerModBlocks() {
        BlairesBrews.LOGGER.info("Registering Blocks for " + BlairesBrews.MOD_ID);
    }
}