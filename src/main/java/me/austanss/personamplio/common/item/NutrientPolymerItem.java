package me.austanss.personamplio.common.item;

import me.austanss.personamplio.common.PersonaAmplioItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;

public class NutrientPolymerItem extends Item {
    public static final String ITEM_ID = "nutrient_polymer";

    public NutrientPolymerItem() {
        super(new Item.Properties()
                .rarity(Rarity.UNCOMMON)
                .tab(PersonaAmplioItemGroup.get()));
    }
}
