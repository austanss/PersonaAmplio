package me.austanss.personamplio.common.tile;

import me.austanss.personamplio.common.item.ItemRegistryManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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

        lazy = LazyOptional.of(() -> inventory);
    }

    private final LazyOptional<IItemHandler> lazy;

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return lazy.cast();

        return super.getCapability(capability, side);
    }

    @Override
    public void tick() {
        boolean valid =
                inventory.getStackInSlot(0).getItem() == ItemRegistryManager.NUTRIENT_POLYMER.get() &&
                        inventory.getStackInSlot(1).getItem() == ItemRegistryManager.DNA_SEQUENCE.get();

        if (!running && !valid)
            return;

        if (!running) {
            running = true;
            return;
        }

        if (!valid) {
            running = false;
            ticks = 0;
            return;
        }

        if (ticks < SYNTHESIS_TICK_DURATION) {
            ticks++;
            return;
        }

        running = false;
        ticks = 0;

        inventory.getStackInSlot(MATERIAL_SLOT).shrink(1);

        finalizing = true;
        inventory.insertItem(RESULT_SLOT, ItemRegistryManager.NUCLEUS.get().getDefaultInstance(), false);
        finalizing = false;
    }
}
