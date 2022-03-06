package me.austanss.personamplio.common.tile;

import me.austanss.personamplio.common.fluid.FluidRegistryManager;
import me.austanss.personamplio.common.item.ItemRegistryManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AcceleratedDecomposerTile extends MachineTile {

    private static final int DECOMPOSITION_TICK_DURATION = 100;

    private static final int PRIMARY_MATERIAL_SLOT = 0;
    private static final int PRIMARY_OUTPUT_SLOT = 1;
    private static final int SECONDARY_MATERIAL_SLOT = 2;
    private static final int SECONDARY_MATERIAL_SLOTS = 4;
    private static final int SECONDARY_OUTPUT_SLOT = 6;
    private static final int SECONDARY_OUTPUT_SLOTS = 4;

    private static final int INVENTORY_SLOTS = 10;

    private static final int TANK_BUCKET_CAPACITY = 10;

    private static final int MATERIALS_PER_OUTPUT = 20;

    public final FluidTank tank;

    public AcceleratedDecomposerTile() {
        super(TileEntityTypeRegistryManager.ACCELERATED_DECOMPOSER_TILE.get());

        inventory = new ItemStackHandler(INVENTORY_SLOTS) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if (slot == PRIMARY_MATERIAL_SLOT || (slot >= SECONDARY_MATERIAL_SLOT && slot < SECONDARY_OUTPUT_SLOT))
                    return isValidBiomaterial(stack.getItem());
                if (slot == PRIMARY_OUTPUT_SLOT && finalizing && stack.getItem() == ItemRegistryManager.NUTRIENT_POLYMER.get())
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

        tank = new FluidTank(TANK_BUCKET_CAPACITY * FluidAttributes.BUCKET_VOLUME);
        itemLazy = LazyOptional.of(() -> inventory);
        fluidLazy = LazyOptional.of(() -> tank);
    }

    private final LazyOptional<IItemHandler> itemLazy;
    private final LazyOptional<IFluidHandler> fluidLazy;

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return itemLazy.cast();

        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return fluidLazy.cast();

        return super.getCapability(capability, side);
    }

    public boolean isValidBiomaterial(Item item) {
        switch (item.getRegistryName().getPath()) {
            case "rotten_flesh":
            case "bone":
            case "beef":
            case "mutton":
            case "salmon":
                return true;
            default:
                return false;
        }
    }

    @Override
    public void tick() {
        boolean valid = isValidBiomaterial(inventory.getStackInSlot(PRIMARY_MATERIAL_SLOT).getItem());

        if (tank.getFluidAmount() % FluidAttributes.BUCKET_VOLUME == 0 && tank.getFluidAmount() > FluidAttributes.BUCKET_VOLUME) {
            finalizing = true;
            inventory.insertItem(PRIMARY_OUTPUT_SLOT, new ItemStack(ItemRegistryManager.NUTRIENT_POLYMER.get()), false);
            finalizing = false;
            tank.drain(FluidAttributes.BUCKET_VOLUME, IFluidHandler.FluidAction.EXECUTE);
        }

        if (!running && !valid)
            return;

        if (!valid) {
            running = false;
        }

        if (ticks < DECOMPOSITION_TICK_DURATION) {
            ticks++;
            return;
        }

        running = false;
        ticks = 0;

        inventory.getStackInSlot(PRIMARY_MATERIAL_SLOT).shrink(1);

        tank.fill(new FluidStack(FluidRegistryManager.CYTOPLASM_SOURCE.get(), FluidAttributes.BUCKET_VOLUME / MATERIALS_PER_OUTPUT), IFluidHandler.FluidAction.EXECUTE);
    }
}
