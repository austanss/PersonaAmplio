package me.austanss.personamplio.common.item;

import me.austanss.personamplio.PersonaAmplio;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistryManager {
    public ItemRegistryManager() {
        _register = DeferredRegister.create(ForgeRegistries.ITEMS, PersonaAmplio.MODID);
    }

    public void registerAll() {
        NUTRIENT_POLYMER = _register.register(NutrientPolymerItem.ITEM_ID, NutrientPolymerItem::new);
        NUCLEUS = _register.register(NucleusItem.ITEM_ID, NucleusItem::new);
        DNA_SEQUENCE = _register.register(DnaSequenceItem.ITEM_ID, DnaSequenceItem::new);

        SYNTHESIS_CHAMBER = _register.register(SynthesisChamberBlockItem.ITEM_ID, SynthesisChamberBlockItem::new);
        ACCELERATED_DECOMPOSER = _register.register(AcceleratedDecomposerBlockItem.ITEM_ID, AcceleratedDecomposerBlockItem::new);

        CYTOPLASM_BUCKET = _register.register(CytoplasmicSolutionBucketItem.ITEM_ID, CytoplasmicSolutionBucketItem::new);

        _register.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private final DeferredRegister<Item> _register;

    public static RegistryObject<Item> NUTRIENT_POLYMER;
    public static RegistryObject<Item> NUCLEUS;
    public static RegistryObject<Item> DNA_SEQUENCE;

    public static RegistryObject<Item> SYNTHESIS_CHAMBER;
    public static RegistryObject<Item> ACCELERATED_DECOMPOSER;

    public static RegistryObject<Item> CYTOPLASM_BUCKET;

    public DeferredRegister<Item> getRegister() {
        return _register;
    }
}
