package net.oshewo.blairesbrews.block.entity;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.text.Text;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.oshewo.blairesbrews.item.inventory.ImplementedInventory;
import net.oshewo.blairesbrews.recipe.BrewingKettleRecipe;
import net.oshewo.blairesbrews.screen.BrewingKettleScreenHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BrewingKettleBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(7,ItemStack.EMPTY);

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;
    private int fuelTime = 0;
    private int maxFuelTime = 0;



    public BrewingKettleBlockEntity( BlockPos pos, BlockState state) {
        super(ModBlockEntities.BREWING_KETTLE, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                switch (index){
                    case 0: return BrewingKettleBlockEntity.this.progress;
                    case 1: return BrewingKettleBlockEntity.this.maxProgress;
                    case 2: return BrewingKettleBlockEntity.this.fuelTime;
                    case 3: return BrewingKettleBlockEntity.this.maxFuelTime;
                    default: return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0: BrewingKettleBlockEntity.this.progress = value; break;
                    case 1: BrewingKettleBlockEntity.this.maxProgress = value; break;
                    case 2: BrewingKettleBlockEntity.this.fuelTime = value; break;
                    case 3: BrewingKettleBlockEntity.this.maxFuelTime = value; break;
                }
            }

            @Override
            public int size() {
                return 4;
            }
        };
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Brewing Kettle");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new BrewingKettleScreenHandler(syncId,inv,this, this.propertyDelegate);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("kettle.progress", progress);
        nbt.putInt("kettle.fuelTime", fuelTime);
        nbt.putInt("kettle.maxFuelTime", maxFuelTime);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
        progress = nbt.getInt("kettle.progress");
        fuelTime = nbt.getInt("kettle.fuelTime");
        maxFuelTime = nbt.getInt("kettle.maxFuelTime");
    }

    private void consumeFuel() {
        if(!getStack(0).isEmpty()) {
            this.fuelTime = FuelRegistry.INSTANCE.get(this.removeStack(0, 1).getItem());
            this.maxFuelTime = this.fuelTime;
        }
    }

    public static void tick(World world, BlockPos pos, BlockState state, BrewingKettleBlockEntity entity) {
        if(isConsumingFuel(entity)) {
            entity.fuelTime--;
        }

        if(hasRecipe(entity)) {
            if(!isConsumingFuel(entity)) {
                if(hasFuelInFuelSlot(entity)){
                    entity.consumeFuel();
                }
                else{
                    entity.resetProgress();
                }
            }
            if(isConsumingFuel(entity)) {
                entity.progress++;
                if(entity.progress > entity.maxProgress) {
                    craftItem(entity);
                }
            }
        }
        else {
            entity.resetProgress();
        }
    }

    private static boolean hasFuelInFuelSlot(BrewingKettleBlockEntity entity) {
        return !entity.getStack(0).isEmpty();
    }

    private static boolean isConsumingFuel(BrewingKettleBlockEntity entity) {
        return entity.fuelTime > 0;
    }

    private static boolean hasRecipe(BrewingKettleBlockEntity entity) {
        World world = entity.world;
        SimpleInventory inventory = new SimpleInventory(entity.inventory.size());
        for (int i = 0; i < entity.inventory.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<BrewingKettleRecipe> match = world.getRecipeManager()
                .getFirstMatch(BrewingKettleRecipe.Type.INSTANCE, inventory, world);

        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory)
                && canInsertItemIntoOutputSlot(inventory, match.get().getOutput());
    }

    private static void craftItem(BrewingKettleBlockEntity entity) {
        World world = entity.world;
        SimpleInventory inventory = new SimpleInventory(entity.inventory.size());
        for (int i = 0; i < entity.inventory.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<BrewingKettleRecipe> match = world.getRecipeManager()
                .getFirstMatch(BrewingKettleRecipe.Type.INSTANCE, inventory, world);

        if(match.isPresent()) {
            /*
            for(int i = 1; i < entity.inventory.size(); i++){
                ItemStack itemStack = inventory.getStack(i);
                if (itemStack.isEmpty()) continue
                entity.removeStack(i,1);
            }
            */
            entity.removeStack(1,1);
            entity.removeStack(2,1);
            entity.removeStack(3,1);
            entity.removeStack(4,1);
            entity.removeStack(5,1);



            entity.setStack(6, new ItemStack(match.get().getOutput().getItem(),
                    entity.getStack(6).getCount() + 1));

            entity.resetProgress();
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleInventory inventory, ItemStack output) {
        return inventory.getStack(6).getItem() == output.getItem() || inventory.getStack(6).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleInventory inventory) {
        return inventory.getStack(6).getMaxCount() > inventory.getStack(6).getCount();
    }
}

