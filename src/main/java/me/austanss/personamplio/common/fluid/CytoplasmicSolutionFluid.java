package me.austanss.personamplio.common.fluid;

import me.austanss.personamplio.common.block.BlockRegistryManager;
import me.austanss.personamplio.common.item.ItemRegistryManager;
import net.minecraft.fluid.*;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public abstract class CytoplasmicSolutionFluid extends ForgeFlowingFluid {
    public static String BLOCK_ID = "cytoplasm";

    public static final ResourceLocation STILL_RESOURCE = new ResourceLocation("block/water_still");
    public static final ResourceLocation FLOWING_RESOURCE = new ResourceLocation("block/water_flow");
    public static final ResourceLocation OVERLAY_RESOURCE = new ResourceLocation("block/water_overlay");

    public static final ForgeFlowingFluid.Properties FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            () -> FluidRegistryManager.CYTOPLASM_SOURCE.get(), () -> FluidRegistryManager.CYTOPLASM_FLOWING.get(),
            FluidAttributes.builder(STILL_RESOURCE, FLOWING_RESOURCE)
                    .overlay(OVERLAY_RESOURCE)
                    .viscosity(10)
                    .luminosity(3)
                    .temperature(30)
                    .sound(SoundEvents.WATER_AMBIENT)
                    .color(0xBB59CA59))
                    .slopeFindDistance(4)
                    .block(() -> BlockRegistryManager.CYTOPlASM.get())
                    .bucket(() -> ItemRegistryManager.CYTOPLASM_BUCKET.get());

    protected CytoplasmicSolutionFluid(Properties properties) {
        super(properties);
    }

    public static class Flowing extends CytoplasmicSolutionFluid {
        public static String FLUID_ID = "cytoplasm_flowing";

        public Flowing() {
            super(FLUID_PROPERTIES);
        }

        protected void createFluidStateDefinition(StateContainer.Builder<Fluid, FluidState> pBuilder) {
            super.createFluidStateDefinition(pBuilder);
            pBuilder.add(LEVEL);
        }

        @Override
        public int getAmount(FluidState pState) {
            return pState.getValue(LEVEL);
        }

        @Override
        public boolean isSource(FluidState pState) {
            return false;
        }
    }

    public static class Source extends CytoplasmicSolutionFluid {
        public static String FLUID_ID = "cytoplasm_source";

        public Source() {
            super(FLUID_PROPERTIES);
        }

        @Override
        public int getAmount(FluidState pState) {
            return 6;
        }

        @Override
        public boolean isSource(FluidState pState) {
            return true;
        }
    }
}
