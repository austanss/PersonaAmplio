package me.austanss.personamplio.common.container;

import me.austanss.personamplio.PersonaAmplio;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerTypeRegistryManager {

    public ContainerTypeRegistryManager() {
        _register = DeferredRegister.create(ForgeRegistries.CONTAINERS, PersonaAmplio.MODID);
    }

    public void registerAll() {
        SYNTHESIS_CHAMBER_CONTAINER = _register.register("synthesis_chamber_container",
                () -> IForgeContainerType.create((((id, inventory, data) -> {
                    BlockPos pos = data.readBlockPos();
                    World world = inventory.player.getCommandSenderWorld();
                    return new SynthesisChamberContainer(id, world, pos, inventory, inventory.player);
                }))));

        ACCELERATED_DECOMPOSER_CONTAINER = _register.register("accelerated_decomposer_container",
                () -> IForgeContainerType.create((((id, inventory, data) -> {
                    BlockPos pos = data.readBlockPos();
                    World world = inventory.player.getCommandSenderWorld();
                    return new AcceleratedDecomposerContainer(id, world, pos, inventory, inventory.player);
                }))));

        _register.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static RegistryObject<ContainerType<SynthesisChamberContainer>> SYNTHESIS_CHAMBER_CONTAINER;
    public static RegistryObject<ContainerType<AcceleratedDecomposerContainer>> ACCELERATED_DECOMPOSER_CONTAINER;

    private final DeferredRegister<ContainerType<?>> _register;

    public DeferredRegister<ContainerType<?>> getRegister() {
        return _register;
    }
}
