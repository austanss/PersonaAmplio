package me.austanss.personamplio.common.block;

import me.austanss.personamplio.PersonaAmplio;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegistryManager {
    public BlockRegistryManager() {
        _register = DeferredRegister.create(ForgeRegistries.BLOCKS, PersonaAmplio.MODID);
    }

    public void registerAll() {
        SYNTHESIS_CHAMBER = _register.register(SynthesisChamberBlock.BLOCK_ID, SynthesisChamberBlock::new);

        _register.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static RegistryObject<Block> SYNTHESIS_CHAMBER;

    private final DeferredRegister<Block> _register;

    public DeferredRegister<Block> getRegister() {
        return _register;
    }
}
