package me.austanss.personamplio.common.item;

import me.austanss.personamplio.PersonaAmplioItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;

public class InsulatedPlateItem extends Item {
    public static final String ITEM_ID = "insulated_plate";

    public InsulatedPlateItem() {
        super(new Item.Properties()
                .tab(PersonaAmplioItemGroup.get())
                .rarity(Rarity.COMMON));
    }
}
