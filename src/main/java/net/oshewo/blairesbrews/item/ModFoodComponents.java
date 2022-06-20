package net.oshewo.blairesbrews.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.FoodComponents;

public class ModFoodComponents {

    public static final FoodComponent REGAL_RED = new FoodComponent.Builder().hunger(0).alwaysEdible().statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 6000, 1), 1.0f).build();

    public static final FoodComponent TRAVEL_TINCTURE = new FoodComponent.Builder().hunger(0).alwaysEdible().statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 6000, 1), 1.0f).build();

    public static final FoodComponent TRAVEL_TINCTURE_LV2 = new FoodComponent.Builder().hunger(0).alwaysEdible().statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 24000, 4), 1.0f).statusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 24000, 1), 1.0f).build();
}
