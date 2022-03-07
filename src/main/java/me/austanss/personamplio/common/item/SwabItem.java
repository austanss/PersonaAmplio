package me.austanss.personamplio.common.item;

import me.austanss.personamplio.common.PersonaAmplioItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;

import java.util.List;

public class SwabItem extends Item {
    public static final String ITEM_ID = "swab";

    public SwabItem() {
        super(new Item.Properties()
                .tab(PersonaAmplioItemGroup.get())
                .stacksTo(16));
    }
}
