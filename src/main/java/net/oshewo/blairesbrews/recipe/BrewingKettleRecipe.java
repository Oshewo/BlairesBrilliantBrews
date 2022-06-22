package net.oshewo.blairesbrews.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class BrewingKettleRecipe implements Recipe<SimpleInventory> {
    private final Identifier id;
    private final ItemStack output;
    private final DefaultedList<Ingredient> recipeItems;


    public BrewingKettleRecipe(Identifier id, ItemStack output, DefaultedList<Ingredient> recipeItems) {
        this.id = id;
        this.recipeItems = recipeItems;
        this.output = output;
    }


    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        if(world.isClient()){return false;}
        RecipeMatcher recipeMatcher = new RecipeMatcher();
        int i = 0;
        for (int j = 1; j <= 5; j++){
            ItemStack itemStack = inventory.getStack(j);
            if (itemStack.isEmpty()) continue;
            i++;
            recipeMatcher.addInput(itemStack, 1);

        }
        return i == recipeItems.size() && recipeMatcher.match(this,null);

    }

    @Override
    public ItemStack craft(SimpleInventory inventory) {
        return output;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return recipeItems;
    }

    @Override
    public ItemStack getOutput() {
        return output.copy();
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<BrewingKettleRecipe>{
        private Type(){}
        public static final Type INSTANCE = new Type();
        public static final String ID = "brewing_kettle";
    }

    public static class Serializer implements RecipeSerializer<BrewingKettleRecipe>{
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "brewing_kettle";
        @Override
        public BrewingKettleRecipe read(Identifier id, JsonObject json) {
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "output"));

            JsonArray ingredients = JsonHelper.getArray(json, "ingredients");
            DefaultedList<Ingredient> inputs = DefaultedList.of();

            /*
            for (int i = 0; i < ingredients.size() && i < inputs.size(); i++){
                inputs.set(i,Ingredient.fromJson(ingredients.get(i)));
            }
             */
            for (int i = 0; i<ingredients.size(); i++){
                Ingredient ingredient = Ingredient.fromJson(ingredients.get(i));
                if (!ingredient.isEmpty()){
                    inputs.add(ingredient);
                }
            }

            return new BrewingKettleRecipe(id,output,inputs);
        }

        @Override
        public BrewingKettleRecipe read(Identifier id, PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(),Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++){
                inputs.set(i,Ingredient.fromPacket(buf));
            }

            ItemStack output = buf.readItemStack();
            return new BrewingKettleRecipe(id,output,inputs);
        }

        @Override
        public void write(PacketByteBuf buf, BrewingKettleRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()){
                ing.write(buf);
            }
            buf.writeItemStack(recipe.getOutput());

        }
    }
}
