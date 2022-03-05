package me.austanss.personamplio.common.item;

import me.austanss.personamplio.PersonaAmplioItemGroup;
import me.austanss.personamplio.common.block.AcceleratedDecomposerBlock;
import me.austanss.personamplio.common.block.BlockRegistryManager;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Rarity;

public class AcceleratedDecomposerBlockItem extends BlockItem {
    public static final String ITEM_ID = AcceleratedDecomposerBlock.BLOCK_ID;

    public AcceleratedDecomposerBlockItem() {
        super(BlockRegistryManager.ACCELERATED_DECOMPOSER.get(), new Properties()
                .tab(PersonaAmplioItemGroup.get())
                .rarity(Rarity.RARE));
    }
}
