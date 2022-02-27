package me.austanss.personamplio.common.item;

import me.austanss.personamplio.PersonaAmplioItemGroup;
import me.austanss.personamplio.common.block.BlockRegistryManager;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;

public class SynthesisChamberBlockItem extends BlockItem {
    public static final String ITEM_ID = "synthesis_chamber";

    public SynthesisChamberBlockItem() {
        super(BlockRegistryManager.SYNTHESIS_CHAMBER.get(), new Item.Properties()
                .tab(PersonaAmplioItemGroup.get())
                .rarity(Rarity.RARE));
    }
}
