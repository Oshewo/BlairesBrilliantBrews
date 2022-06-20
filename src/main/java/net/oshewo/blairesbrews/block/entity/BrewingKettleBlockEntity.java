package net.oshewo.blairesbrews.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.Text;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.oshewo.blairesbrews.item.ModItems;
import net.oshewo.blairesbrews.item.inventory.ImplementedInventory;
import net.oshewo.blairesbrews.screen.BrewingKettleScreenHandler;
import org.jetbrains.annotations.Nullable;

public class BrewingKettleBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(7,ItemStack.EMPTY);

    public BrewingKettleBlockEntity( BlockPos pos, BlockState state) {
        super(ModBlockEntities.BREWING_KETTLE, pos, state);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Brewing Kettle");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new BrewingKettleScreenHandler(syncId,inv,this);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
    }

    public static void tick(World world, BlockPos pos, BlockState state, BrewingKettleBlockEntity entity){
        if(hasRecipe(entity) && hasNotReachedStackLimit(entity)){
            craftItem(entity);
        }
    }

    private static void craftItem(BrewingKettleBlockEntity entity){
        entity.removeStack(3,1);
        entity.removeStack(1,1);
        entity.removeStack(2,1);

        entity.setStack(6,new ItemStack(ModItems.TRAVEL_TINCTURE_LV2,
                entity.getStack(6).getCount()+1));

    }

    private static boolean hasRecipe(BrewingKettleBlockEntity entity){
        boolean hasItemInFirstSlot = entity.getStack(1).getItem() == Items.FEATHER;
        boolean hasItemInSecondSlot = entity.getStack(2).getItem() == Items.REDSTONE;
        boolean hasItemInThirdSlot = entity.getStack(3).getItem() == ModItems.TRAVEL_TINCTURE;

        return hasItemInFirstSlot && hasItemInSecondSlot && hasItemInThirdSlot;
    }

    private static boolean hasNotReachedStackLimit(BrewingKettleBlockEntity entity){
        return entity.getStack(3).getCount() <entity.getStack(3).getMaxCount();
    }
}
