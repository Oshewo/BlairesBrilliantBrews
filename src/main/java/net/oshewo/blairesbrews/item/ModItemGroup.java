package net.oshewo.blairesbrews.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.oshewo.blairesbrews.BlairesBrews;

public class ModItemGroup {
    public static final ItemGroup BREWS = FabricItemGroupBuilder.build(new Identifier(BlairesBrews.MOD_ID, "brews"),
            () -> new ItemStack(ModItems.REGAL_RED));
}
