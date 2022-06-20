package net.oshewo.blairesbrews;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.RenderLayer;
import net.oshewo.blairesbrews.block.ModBlocks;
import net.oshewo.blairesbrews.screen.BrewingKettleScreen;
import net.oshewo.blairesbrews.screen.ModScreenHandlers;

public class BlairesClientMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BREWING_KETTLE, RenderLayer.getCutout());


        ScreenRegistry.register(ModScreenHandlers.BREWING_KETTLE_SCREEN_HANDLER, BrewingKettleScreen::new);
    }
}
