package me.austanss.personamplio.common.tile;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class MachineTile extends TileEntity implements ITickableTileEntity {

    public ItemStackHandler inventory;
    private final LazyOptional<IItemHandler> lazy;

    boolean running = false;
    int ticks = 0;
    boolean finalizing = false;

    public final IIntArray data = new IIntArray() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0: return running ? 1 : 0;
                case 1: return ticks;
                default: throw new IndexOutOfBoundsException();
            }
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0: running = value > 0;
                case 1: ticks = value;
                default: throw new IndexOutOfBoundsException();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public MachineTile(TileEntityType<?> type) {
        super(type);
        lazy = LazyOptional.of(() -> inventory);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return lazy.cast();

        return super.getCapability(capability, side);
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.put("inventory", inventory.serializeNBT());
        nbt.putInt("progress", ticks);
        nbt.putBoolean("running", running);
        return super.save(nbt);
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        inventory.deserializeNBT(nbt.getCompound("inventory"));
        ticks = nbt.getInt("progress");
        running = nbt.getBoolean("running");
        super.load(state, nbt);
    }
}
