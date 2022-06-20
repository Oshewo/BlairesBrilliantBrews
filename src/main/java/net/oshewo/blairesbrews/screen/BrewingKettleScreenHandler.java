package net.oshewo.blairesbrews.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.oshewo.blairesbrews.screen.slot.ModFuelSlot;
import net.oshewo.blairesbrews.screen.slot.ModResultSlot;

public class BrewingKettleScreenHandler extends ScreenHandler {
    private final Inventory inventory;

    public BrewingKettleScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(7));
    }
    public BrewingKettleScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ModScreenHandlers.BREWING_KETTLE_SCREEN_HANDLER, syncId);
        checkSize(inventory,7);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);

        this.addSlot(new ModFuelSlot(inventory, 0,8,55));
        this.addSlot(new Slot(inventory, 1,52,65));
        this.addSlot(new Slot(inventory, 2,92,65));
        this.addSlot(new Slot(inventory, 3,106,30));
        this.addSlot(new Slot(inventory, 4,74,6));
        this.addSlot(new Slot(inventory, 5,40,30));
        this.addSlot(new ModResultSlot(inventory, 6,152,65));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }



    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse((player));
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }
    }
}
