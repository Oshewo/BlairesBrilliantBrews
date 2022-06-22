package net.oshewo.blairesbrews.world.feature;

import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import net.oshewo.blairesbrews.BlairesBrews;
import net.oshewo.blairesbrews.block.ModBlocks;

public class ModConfiguredFeatures {
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig,?>> WITCHWOOD_TREE =
            ConfiguredFeatures.register("witchwood_tree", Feature.TREE,new TreeFeatureConfig.Builder(
                    BlockStateProvider.of(ModBlocks.WITCHWOOD_LOG),
                    new StraightTrunkPlacer(4,5,2),
                    BlockStateProvider.of(ModBlocks.WITCHWOOD_LEAVES),
                    new BlobFoliagePlacer(ConstantIntProvider.create(2),ConstantIntProvider.create(0),3),
                    new TwoLayersFeatureSize(1,0,2)).build());

    public static void registerConfiguredFeatures(){
        System.out.println("Registering ModConfiguredFeatures for "+ BlairesBrews.MOD_ID);
    }
}
