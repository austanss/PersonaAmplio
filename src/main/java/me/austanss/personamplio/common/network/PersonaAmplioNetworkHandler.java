package me.austanss.personamplio.common.network;

import me.austanss.personamplio.PersonaAmplio;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PersonaAmplioNetworkHandler {
    private static final String REVISION = "1";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(PersonaAmplio.MODID, "channel"),
                () -> REVISION,
                REVISION::equals,
                REVISION::equals
    );

    public static void registerPackets() {
        int id = 0;
    }
}
