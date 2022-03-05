package me.austanss.personamplio.common.fluid;

import me.austanss.personamplio.PersonaAmplio;
import me.austanss.personamplio.common.block.SynthesisChamberBlock;
import net.minecraft.block.Block;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidRegistryManager {

    public FluidRegistryManager() {
        _register = DeferredRegister.create(ForgeRegistries.FLUIDS, PersonaAmplio.MODID);
    }

    public void registerAll() {
        CYTOPLASM_SOURCE = _register.register(CytoplasmicSolutionFluid.Source.FLUID_ID, CytoplasmicSolutionFluid.Source::new);
        CYTOPLASM_FLOWING = _register.register(CytoplasmicSolutionFluid.Flowing.FLUID_ID, CytoplasmicSolutionFluid.Flowing::new);

        _register.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static RegistryObject<FlowingFluid> CYTOPLASM_SOURCE;

    public static RegistryObject<FlowingFluid> CYTOPLASM_FLOWING;

    private final DeferredRegister<Fluid> _register;
}
