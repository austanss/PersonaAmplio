package me.austanss.personamplio;

import me.austanss.personamplio.common.RegistryManager;
import me.austanss.personamplio.common.container.ContainerTypeRegistryManager;
import me.austanss.personamplio.common.screen.SynthesisChamberScreen;
import net.minecraft.block.Block;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

@Mod(PersonaAmplio.MODID)
public class PersonaAmplio
{
    public static final Logger LOGGER = LogManager.getLogger();

    public static final String MODID = "personamplio";

    public static final RegistryManager registries = new RegistryManager();

    public PersonaAmplio() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        registries.registerAll();

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("[Persona Amplio] Registering common modifications...");
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        LOGGER.info("[Persona Amplio] Registering client-side modifications...");

        ScreenManager.register(ContainerTypeRegistryManager.SYNTHESIS_CHAMBER_CONTAINER.get(), SynthesisChamberScreen::new);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
    }

    private void processIMC(final InterModProcessEvent event)
    {
        LOGGER.info("[Persona Amplio] Received IMC: {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        LOGGER.info("[Persona Amplio] Registering server-side modifications...");
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            LOGGER.info("[Persona Amplio] Registering items and blocks...");
        }
    }
}
