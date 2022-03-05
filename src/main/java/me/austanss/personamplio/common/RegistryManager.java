package me.austanss.personamplio.common;

import me.austanss.personamplio.common.block.BlockRegistryManager;
import me.austanss.personamplio.common.container.ContainerTypeRegistryManager;
import me.austanss.personamplio.common.fluid.FluidRegistryManager;
import me.austanss.personamplio.common.tile.TileEntityTypeRegistryManager;
import me.austanss.personamplio.common.item.ItemRegistryManager;

public class RegistryManager {
    public RegistryManager() {
        _blockManager = new BlockRegistryManager();
        _fluidManager = new FluidRegistryManager();
        _itemManager = new ItemRegistryManager();
        _tileEntityManager = new TileEntityTypeRegistryManager();
        _containerManager = new ContainerTypeRegistryManager();
    }

    public void registerAll() {
        _blockManager.registerAll();
        _fluidManager.registerAll();
        _itemManager.registerAll();
        _tileEntityManager.registerAll();
        _containerManager.registerAll();
    }

    private final BlockRegistryManager _blockManager;
    private final FluidRegistryManager _fluidManager;
    private final ItemRegistryManager _itemManager;
    private final TileEntityTypeRegistryManager _tileEntityManager;
    private final ContainerTypeRegistryManager _containerManager;

    public ItemRegistryManager getItemManager() {
        return _itemManager;
    }

    public FluidRegistryManager getFluidManager() {
        return _fluidManager;
    }

    public BlockRegistryManager getBlockManager() {
        return _blockManager;
    }

    public TileEntityTypeRegistryManager getTileManager() {
        return _tileEntityManager;
    }

    public ContainerTypeRegistryManager getContainerManager() {
        return _containerManager;
    }

}
