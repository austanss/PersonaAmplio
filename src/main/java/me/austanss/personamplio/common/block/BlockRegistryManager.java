package me.austanss.personamplio.common.block;

import me.austanss.personamplio.PersonaAmplio;
import me.austanss.personamplio.common.fluid.CytoplasmicSolutionFluid;
import me.austanss.personamplio.common.fluid.FluidRegistryManager;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
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
        ACCELERATED_DECOMPOSER = _register.register(AcceleratedDecomposerBlock.BLOCK_ID, AcceleratedDecomposerBlock::new);

        CYTOPlASM = _register.register(CytoplasmicSolutionFluid.BLOCK_ID, () -> new FlowingFluidBlock(() -> FluidRegistryManager.CYTOPLASM_SOURCE.get(), AbstractBlock.Properties.of(Material.WATER)));

        _register.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static RegistryObject<Block> SYNTHESIS_CHAMBER;
    public static RegistryObject<Block> ACCELERATED_DECOMPOSER;

    public static RegistryObject<FlowingFluidBlock> CYTOPlASM;

    private final DeferredRegister<Block> _register;

    public DeferredRegister<Block> getRegister() {
        return _register;
    }
}
