package me.austanss.personamplio.common.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.austanss.personamplio.PersonaAmplio;
import me.austanss.personamplio.common.container.SynthesisChamberContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.ParametersAreNonnullByDefault;

public class SynthesisChamberScreen extends ContainerScreen<SynthesisChamberContainer> {

    private final ResourceLocation SCREEN_RESOURCE = new ResourceLocation(PersonaAmplio.MODID, "textures/screens/synthesis_chamber.png");

    public SynthesisChamberScreen(SynthesisChamberContainer container, PlayerInventory inventory, ITextComponent text) {
        super(container, inventory, text);
    }

    @Override
    public void render(MatrixStack matrix, int x, int y, float partialTicks) {
        this.renderBackground(matrix);
        super.render(matrix, x, y, partialTicks);
        this.renderTooltip(matrix, x, y);
    }

    @Override
    protected void renderBg(MatrixStack matrix, float partialTicks, int x, int y) {
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        this.minecraft.getTextureManager().bind(SCREEN_RESOURCE);
        int ix = this.getGuiLeft();
        int iy = this.getGuiTop();
        this.blit(matrix, ix, iy, 0, 0, this.imageWidth, this.imageHeight);

        if (menu.isRunning()) {
            float percentage = ((float)(int)((menu.getProgressTicks() / 195f) * 100) / 100f);
            int height = (int) Math.ceil(42 * percentage);
            this.blit(matrix, ix + 85, iy + 22, 176, 0, 5, height);
        }
    }
}
