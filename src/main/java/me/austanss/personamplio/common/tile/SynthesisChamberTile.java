package me.austanss.personamplio.common.tile;

import me.austanss.personamplio.common.item.ItemRegistryManager;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SynthesisChamberTile extends TileEntity implements ITickableTileEntity {

    public final ItemStackHandler inventory;
    private final LazyOptional<IItemHandler> lazyHandler;

    private static final int SYNTHESIS_TICK_DURATION = 200;

    boolean synthesizing = false;
    int ticks = 0;
    boolean insertingResult = false;

    public final IIntArray data = new IIntArray() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0: return synthesizing ? 1 : 0;
                case 1: return ticks;
                default: return 0;
            }
        }

        @Override
        public void set(int p_221477_1_, int p_221477_2_) {
            throw null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public SynthesisChamberTile() {
        super(TileEntityTypeRegistryManager.SYNTHESIS_CHAMBER_ENTITY.get());

        inventory = new ItemStackHandler(3) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if (slot == 0)
                    return stack.getItem() == ItemRegistryManager.NUTRIENT_POLYMER.get();
                if (slot == 1)
                    return true;
                if (slot == 2 && insertingResult)
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

        lazyHandler = LazyOptional.of(() -> inventory);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return lazyHandler.cast();

        return super.getCapability(capability, side);
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.put("inv", inventory.serializeNBT());
        nbt.putInt("progress", ticks);
        nbt.putBoolean("running", synthesizing);
        return super.save(nbt);
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        inventory.deserializeNBT(nbt.getCompound("inv"));
        ticks = nbt.getInt("progress");
        synthesizing = nbt.getBoolean("running");
        super.load(state, nbt);
    }

    @Override
    public void tick() {
        boolean containsCorrectIngredients =
                inventory.getStackInSlot(0).getItem() == ItemRegistryManager.NUTRIENT_POLYMER.get() &&
                        inventory.getStackInSlot(1).getItem() == ItemRegistryManager.DNA_SEQUENCE.get();

        if (!synthesizing && !containsCorrectIngredients)
            return;

        if (!synthesizing && containsCorrectIngredients) {
            synthesizing = true;
            return;
        }

        if (synthesizing && !containsCorrectIngredients) {
            synthesizing = false;
            ticks = 0;
            return;
        }

        if (synthesizing && ticks < SYNTHESIS_TICK_DURATION) {
            ticks++;
            return;
        }

        if (synthesizing && ticks >= SYNTHESIS_TICK_DURATION) {
            synthesizing = false;
            ticks = 0;

            inventory.getStackInSlot(0).shrink(1);

            insertingResult = true;
            inventory.insertItem(2, new ItemStack(ItemRegistryManager.NUCLEUS.get(), 1), false);
            insertingResult = false;
        }
    }
}
