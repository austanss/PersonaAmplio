package me.austanss.personamplio.common.item;

import me.austanss.personamplio.common.PersonaAmplioItemGroup;
import me.austanss.personamplio.common.block.BlockRegistryManager;
import me.austanss.personamplio.common.block.SynthesisChamberBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;

public class SynthesisChamberBlockItem extends BlockItem {
    public static final String ITEM_ID = SynthesisChamberBlock.BLOCK_ID;

    public SynthesisChamberBlockItem() {
        super(BlockRegistryManager.SYNTHESIS_CHAMBER.get(), new Item.Properties()
                .tab(PersonaAmplioItemGroup.get())
                .rarity(Rarity.RARE));
    }
}
