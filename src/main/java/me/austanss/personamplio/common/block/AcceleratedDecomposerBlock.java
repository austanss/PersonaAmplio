package me.austanss.personamplio.common.block;

import me.austanss.personamplio.common.container.AcceleratedDecomposerContainer;
import me.austanss.personamplio.common.container.SynthesisChamberContainer;
import me.austanss.personamplio.common.item.ItemRegistryManager;
import me.austanss.personamplio.common.tile.AcceleratedDecomposerTile;
import me.austanss.personamplio.common.tile.TileEntityTypeRegistryManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.INBTType;
import net.minecraft.nbt.IntNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nullable;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

public class AcceleratedDecomposerBlock extends Block {
    public static final String BLOCK_ID = "accelerated_decomposer";

    private AcceleratedDecomposerTile tile;

    public AcceleratedDecomposerBlock() {
        super(BlockCommons.MACHINE_BLOCK_PROPERTIES);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        tile = TileEntityTypeRegistryManager.ACCELERATED_DECOMPOSER_TILE.get().create();
        return tile;
    }

    @Override
    public List<ItemStack> getDrops(BlockState p_220076_1_, LootContext.Builder p_220076_2_) {
        return super.getDrops(p_220076_1_, p_220076_2_);
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isClientSide)
            return ActionResultType.SUCCESS;

        if (player.isCrouching())
            return ActionResultType.PASS;

        TileEntity tile = worldIn.getBlockEntity(pos);

        if (!(tile instanceof AcceleratedDecomposerTile))
            throw new IllegalStateException("Mismatch between block and tile entity");

        INamedContainerProvider provider = new INamedContainerProvider() {
            @Override
            public ITextComponent getDisplayName() {
                return new TranslationTextComponent("screen.personamplio.accelerated_decomposer");
            }

            @Nullable
            @Override
            public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
                return new AcceleratedDecomposerContainer(id, worldIn, pos, inventory, player);
            }
        };

        NetworkHooks.openGui((ServerPlayerEntity) player, provider, pos);

        return ActionResultType.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        InventoryHelper.dropContents(worldIn, pos, new RecipeWrapper(tile.inventory));

        CompoundNBT fluidTag = new CompoundNBT();
        fluidTag.putInt("fluid_volume", tile.tank.getFluidAmount());

        PlayerEntity playerLikelyDestroying = worldIn.getNearestPlayer(EntityPredicate.DEFAULT, pos.getX(), pos.getY(), pos.getZ());

        if (!playerLikelyDestroying.isCreative())
        InventoryHelper.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(),
                new ItemStack(ItemRegistryManager.ACCELERATED_DECOMPOSER.get(), 1, fluidTag));

        super.onRemove(state, worldIn, pos, newState, isMoving);
    }
}
