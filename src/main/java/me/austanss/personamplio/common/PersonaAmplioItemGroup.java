package me.austanss.personamplio.common;

import me.austanss.personamplio.common.item.ItemRegistryManager;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class PersonaAmplioItemGroup extends ItemGroup {

    public static final String LABEL = "personamplio";

    private static final PersonaAmplioItemGroup _this = new PersonaAmplioItemGroup();

    public static ItemGroup get() {
        return _this;
    }

    private PersonaAmplioItemGroup() {
        super(LABEL);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ItemRegistryManager.DNA_SEQUENCE.get());
    }

    @Override
    public void fillItemList(NonNullList<ItemStack> list) {
        list.add(new ItemStack(ItemRegistryManager.SYNTHESIS_CHAMBER.get()));
        list.add(new ItemStack(ItemRegistryManager.ACCELERATED_DECOMPOSER.get()));

        list.add(new ItemStack(ItemRegistryManager.CYTOPLASM_BUCKET.get()));

        list.add(new ItemStack(ItemRegistryManager.DNA_SEQUENCE.get()));
        list.add(new ItemStack(ItemRegistryManager.NUCLEUS.get()));
        list.add(new ItemStack(ItemRegistryManager.NUTRIENT_POLYMER.get()));
    }
}
