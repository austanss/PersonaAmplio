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

    boolean running = false;
    int ticks = 0;
    boolean finalizing = false;

    public MachineTile(TileEntityType<?> type) {
        super(type);
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
