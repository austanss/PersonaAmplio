package me.austanss.personamplio.common.tile;

import me.austanss.personamplio.PersonaAmplio;
import me.austanss.personamplio.common.block.BlockRegistryManager;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypeRegistryManager {
    public TileEntityTypeRegistryManager() {
        _register = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, PersonaAmplio.MODID);
    }

    public void registerAll() {
        SYNTHESIS_CHAMBER_TILE = _register.register("synthesis_chamber_tile",
                () -> TileEntityType.Builder.of(SynthesisChamberTile::new, BlockRegistryManager.SYNTHESIS_CHAMBER.get()).build(null));

        ACCELERATED_DECOMPOSER_TILE = _register.register("accelerated_decomposer_tile",
                () -> TileEntityType.Builder.of(AcceleratedDecomposerTile::new, BlockRegistryManager.ACCELERATED_DECOMPOSER.get()).build(null));

        _register.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static RegistryObject<TileEntityType<SynthesisChamberTile>> SYNTHESIS_CHAMBER_TILE;
    public static RegistryObject<TileEntityType<AcceleratedDecomposerTile>> ACCELERATED_DECOMPOSER_TILE;

    private final DeferredRegister<TileEntityType<?>> _register;

    public DeferredRegister<TileEntityType<?>> getRegister() {
        return _register;
    }
}
