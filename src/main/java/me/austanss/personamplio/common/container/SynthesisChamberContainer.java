package me.austanss.personamplio.common.container;

import me.austanss.personamplio.common.block.BlockRegistryManager;
import me.austanss.personamplio.common.tile.SynthesisChamberTile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class SynthesisChamberContainer extends Container {

    private final SynthesisChamberTile tile;
    private final PlayerEntity player;
    private final IItemHandler playerInventory;

    private IntReferenceHolder progress;
    private IntReferenceHolder running;

    public SynthesisChamberContainer(int id, World worldIn, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        super(ContainerTypeRegistryManager.SYNTHESIS_CHAMBER_CONTAINER.get(), id);

        if (!(worldIn.getBlockEntity(pos) instanceof SynthesisChamberTile))
            throw new IllegalStateException("Mismatch between container and tile entity");

        this.tile = (SynthesisChamberTile) worldIn.getBlockEntity(pos);
        this.player = player;
        this.playerInventory = new InvWrapper(playerInventory);

        if (this.tile == null)
            return;

        running = IntReferenceHolder.forContainer(tile.data, 0);
        progress = IntReferenceHolder.forContainer(tile.data, 1);

        layoutContainer(8, 86);

        tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(consumer -> {
            addSlot(new SlotItemHandler(consumer, 0, 55, 22));
            addSlot(new SlotItemHandler(consumer, 1, 55, 50));
            addSlot(new SlotItemHandler(consumer, 2, 105, 36));
        });
    }

    public boolean isRunning() {
        return running.get() == 1;
    }

    public int getProgressTicks() {
        return progress.get();
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return stillValid(IWorldPosCallable.create(tile.getLevel(), tile.getBlockPos()), player, BlockRegistryManager.SYNTHESIS_CHAMBER.get());
    }

    private int addRow(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }

        return index;
    }

    private int addSlot(IItemHandler handler, int index, int x, int y, int horizontalAmount, int dx, int verticalAmount, int dy) {
        for (int j = 0; j < verticalAmount; j++) {
            index = addRow(handler, index, x, y, horizontalAmount, dx);
            y += dy;
        }

        return index;
    }

    private void layoutContainer(int leftColumn, int topRow) {
        addSlot(playerInventory, 9, leftColumn, topRow, 9, 18, 3, 18);

        topRow += 58;
        addRow(playerInventory, 0, leftColumn, topRow, 9, 18);
    }

    private static final int HOTBAR_SLOTS = 9;
    private static final int INVENTORY_SLOTS = 27;
    private static final int PLAYER_SLOTS = HOTBAR_SLOTS + INVENTORY_SLOTS;
    private static final int FIRST_BLOCK_INVENTORY_SLOT = PLAYER_SLOTS;

    private static final int BLOCK_INVENTORY_SLOTS = 3;

    @Override
    public ItemStack quickMoveStack(PlayerEntity player, int index) {
        Slot slot = slots.get(index);
        if (slot == null || !slot.hasItem()) return ItemStack.EMPTY;
        ItemStack source = slot.getItem();
        ItemStack copy = source.copy();

        if (index < PLAYER_SLOTS) {
            if (!this.moveItemStackTo(source, FIRST_BLOCK_INVENTORY_SLOT, FIRST_BLOCK_INVENTORY_SLOT + BLOCK_INVENTORY_SLOTS, false))
                return ItemStack.EMPTY;
        }
        else if (index < FIRST_BLOCK_INVENTORY_SLOT + BLOCK_INVENTORY_SLOTS) {
            if (!this.moveItemStackTo(source, 0, PLAYER_SLOTS, false))
                return ItemStack.EMPTY;
        }
        else
            return ItemStack.EMPTY;

        if (source.getCount() == 0)
            slot.set(ItemStack.EMPTY);
        else
            slot.setChanged();

        slot.onTake(player, source);
        return copy;
    }
}
