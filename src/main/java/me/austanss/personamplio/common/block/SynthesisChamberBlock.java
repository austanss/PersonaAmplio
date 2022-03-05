package me.austanss.personamplio.common.block;

import me.austanss.personamplio.common.container.SynthesisChamberContainer;
import me.austanss.personamplio.common.tile.SynthesisChamberTile;
import me.austanss.personamplio.common.tile.TileEntityTypeRegistryManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
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

public class SynthesisChamberBlock extends Block {
    public static final String BLOCK_ID = "synthesis_chamber";

    private SynthesisChamberTile tile;

    public SynthesisChamberBlock() {
        super(BlockCommons.MACHINE_BLOCK_PROPERTIES);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        tile = TileEntityTypeRegistryManager.SYNTHESIS_CHAMBER_TILE.get().create();
        return tile;
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isClientSide)
            return ActionResultType.SUCCESS;

        if (player.isCrouching())
            return ActionResultType.PASS;

        TileEntity tile = worldIn.getBlockEntity(pos);

        if (!(tile instanceof SynthesisChamberTile))
            throw new IllegalStateException("Mismatch between block and tile entity");

        INamedContainerProvider provider = new INamedContainerProvider() {
            @Override
            public ITextComponent getDisplayName() {
                return new TranslationTextComponent("screen.personamplio.synthesis_chamber");
            }

            @Nullable
            @Override
            public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
                return new SynthesisChamberContainer(id, worldIn, pos, inventory, player);
            }
        };

        NetworkHooks.openGui((ServerPlayerEntity) player, provider, pos);

        return ActionResultType.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        InventoryHelper.dropContents(worldIn, pos, new RecipeWrapper(tile.inventory));
        super.onRemove(state, worldIn, pos, newState, isMoving);
    }
}
