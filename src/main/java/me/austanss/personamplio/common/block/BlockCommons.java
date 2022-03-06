package me.austanss.personamplio.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class BlockCommons {
    protected BlockCommons() {}

    public static final AbstractBlock.Properties MACHINE_BLOCK_PROPERTIES = Block.Properties.of(Material.HEAVY_METAL)
            .harvestTool(ToolType.PICKAXE)
            .harvestLevel(2)
            .friction(0.9f)
            .strength(20f)
            .noDrops();
}
