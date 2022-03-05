package me.austanss.personamplio.common.item;

import me.austanss.personamplio.PersonaAmplioItemGroup;
import me.austanss.personamplio.common.fluid.FluidRegistryManager;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;

public class CytoplasmicSolutionBucketItem extends BucketItem {
    public static final String ITEM_ID = "cytoplasm_bucket";

    public CytoplasmicSolutionBucketItem() {
        super(() -> FluidRegistryManager.CYTOPLASM_SOURCE.get(),
                new Item.Properties()
                        .stacksTo(1)
                        .tab(PersonaAmplioItemGroup.get()));
    }
}
