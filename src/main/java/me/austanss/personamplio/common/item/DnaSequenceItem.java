package me.austanss.personamplio.common.item;

import me.austanss.personamplio.PersonaAmplioItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;

public class DnaSequenceItem extends Item {
    public static final String ITEM_ID = "dna_sequence";

    public DnaSequenceItem() {
        super(new Item.Properties()
                .rarity(Rarity.UNCOMMON)
                .stacksTo(1)
                .tab(PersonaAmplioItemGroup.get()));
    }
}
