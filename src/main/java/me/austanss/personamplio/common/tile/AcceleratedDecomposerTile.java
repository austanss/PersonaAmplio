package me.austanss.personamplio.common.tile;

import me.austanss.personamplio.common.item.ItemRegistryManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class AcceleratedDecomposerTile extends MachineTile {

    private static final int DECOMPOSITION_TICK_DURATION = 100;

    private static final int PRIMARY_MATERIAL_SLOT = 0;
    private static final int PRIMARY_OUTPUT_SLOT = 1;
    private static final int SECONDARY_MATERIAL_SLOT = 2;
    private static final int SECONDARY_MATERIAL_SLOTS = 6;
    private static final int SECONDARY_OUTPUT_SLOT = 8;
    private static final int SECONDARY_OUTPUT_SLOTS = 6;

    private static final int INVENTORY_SLOTS = 14;

    public AcceleratedDecomposerTile() {
        super(TileEntityTypeRegistryManager.ACCELERATED_DECOMPOSER_TILE.get());

        inventory = new ItemStackHandler(INVENTORY_SLOTS) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if (slot == PRIMARY_MATERIAL_SLOT)
                    return true;
                if (slot == PRIMARY_OUTPUT_SLOT && finalizing && stack.getItem() == ItemRegistryManager.NUCLEUS.get())
                    return true;
                if (slot >= SECONDARY_MATERIAL_SLOT && slot < SECONDARY_OUTPUT_SLOT)
                    return true;
                if (slot >= SECONDARY_OUTPUT_SLOT && slot < INVENTORY_SLOTS)
                    return stack.getItem() == ItemRegistryManager.NUTRIENT_POLYMER.get();

                return false;
            }

            @Override
            public int getSlotLimit(int slot) {
                return 64;
            }
        };
    }

    @Override
    public void tick() {

    }
}
