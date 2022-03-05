package me.austanss.personamplio.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class AcceleratedDecomposerBlock extends Block {
    public static final String BLOCK_ID = "accelerated_decomposer";

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
        return super.createTileEntity(state, world);
    }
}
