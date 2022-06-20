package net.oshewo.blairesbrews.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.oshewo.blairesbrews.BlairesBrews;
import net.oshewo.blairesbrews.block.ModBlocks;

public class ModBlockEntities {
    public static BlockEntityType<BrewingKettleBlockEntity> BREWING_KETTLE;

    public static void registerAllBlockEntities(){
        BREWING_KETTLE = Registry.register(Registry.BLOCK_ENTITY_TYPE,new Identifier(BlairesBrews.MOD_ID,"brewing_kettle"),
                FabricBlockEntityTypeBuilder.create(BrewingKettleBlockEntity::new,
                        ModBlocks.BREWING_KETTLE).build(null));
    }
}
