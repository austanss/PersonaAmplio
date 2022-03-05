package me.austanss.personamplio.common.item;

import me.austanss.personamplio.PersonaAmplioItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;

public class NucleusItem extends Item {
    public static final String ITEM_ID = "nucleus";

    public NucleusItem() {
        super(new Item.Properties()
                .rarity(Rarity.UNCOMMON)
                .tab(PersonaAmplioItemGroup.get())
                .stacksTo(16));
    }


}
