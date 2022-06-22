package net.oshewo.blairesbrews.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.oshewo.blairesbrews.BlairesBrews;

public class ModScreenHandlers {
    public static ScreenHandlerType<BrewingKettleScreenHandler> BREWING_KETTLE_SCREEN_HANDLER;

    public static void registerAllScreenHandlers() {
        BREWING_KETTLE_SCREEN_HANDLER =
            ScreenHandlerRegistry.registerSimple(new Identifier(BlairesBrews.MOD_ID, "brewing_kettle"),
                    BrewingKettleScreenHandler::new);
    }
}
