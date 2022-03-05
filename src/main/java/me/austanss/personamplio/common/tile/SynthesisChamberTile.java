package me.austanss.personamplio.common.tile;

import me.austanss.personamplio.common.item.ItemRegistryManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class SynthesisChamberTile extends MachineTile {

    private static final int SYNTHESIS_TICK_DURATION = 200;

    private static final int MATERIAL_SLOT = 0;
    private static final int SEQUENCE_SLOT = 1;
    private static final int RESULT_SLOT = 2;

    private static final int INVENTORY_SLOTS = 3;

    public SynthesisChamberTile() {
        super(TileEntityTypeRegistryManager.SYNTHESIS_CHAMBER_TILE.get());

        inventory = new ItemStackHandler(INVENTORY_SLOTS) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if (slot == MATERIAL_SLOT)
                    return stack.getItem() == ItemRegistryManager.NUTRIENT_POLYMER.get();
                if (slot == SEQUENCE_SLOT)
                    return stack.getItem() == ItemRegistryManager.DNA_SEQUENCE.get();
                if (slot == RESULT_SLOT && finalizing && stack.getItem() == ItemRegistryManager.NUCLEUS.get())
                    return true;

                return false;
            }

            @Override
            public int getSlotLimit(int slot) {
                if (slot == 1)
                    return 1;

                return 64;
            }
        };
    }

    @Override
    public void tick() {
        boolean containsCorrectIngredients =
                inventory.getStackInSlot(0).getItem() == ItemRegistryManager.NUTRIENT_POLYMER.get() &&
                        inventory.getStackInSlot(1).getItem() == ItemRegistryManager.DNA_SEQUENCE.get();

        if (!running && !containsCorrectIngredients)
            return;

        if (!running && containsCorrectIngredients) {
            running = true;
            return;
        }

        if (running && !containsCorrectIngredients) {
            running = false;
            ticks = 0;
            return;
        }

        if (running && ticks < SYNTHESIS_TICK_DURATION) {
            ticks++;
            return;
        }

        if (running && ticks >= SYNTHESIS_TICK_DURATION) {
            running = false;
            ticks = 0;

            inventory.getStackInSlot(0).shrink(1);

            finalizing = true;
            inventory.insertItem(2, new ItemStack(ItemRegistryManager.NUCLEUS.get(), 1), false);
            finalizing = false;
        }
    }
}
