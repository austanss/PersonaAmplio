package me.austanss.personamplio.common.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.austanss.personamplio.PersonaAmplio;
import me.austanss.personamplio.common.container.AcceleratedDecomposerContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class AcceleratedDecomposerScreen extends ContainerScreen<AcceleratedDecomposerContainer> {

    private final ResourceLocation SCREEN_RESOURCE = new ResourceLocation(PersonaAmplio.MODID, "textures/screens/accelerated_decomposer.png");

    public AcceleratedDecomposerScreen(AcceleratedDecomposerContainer container, PlayerInventory inventory, ITextComponent text) {
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
        final int ix = this.getGuiLeft();
        final int iy = this.getGuiTop();
        this.blit(matrix, ix, iy, 0, 0, this.imageWidth, this.imageHeight);

        if (menu.isRunning()) {
            final float percentage = ((float)(int)((menu.getProgressTicks() / 95f) * 100) / 100f);
            final int height = (int) Math.ceil(42 * percentage);
            this.blit(matrix, ix + 12, iy + 22, 176, 0, 5, height);
        }

        float percentage = ((float)(int)((menu.getTankVolume() / 10000f) * 100) / 100f);
        final int height = (int) Math.ceil(57 * percentage);
        final int width = 20;
        final int jx = 182;
        final int jy = 57 - height;
        final int kx = ix + 142;
        final int ky = iy + 15 + jy;
        this.blit(matrix, kx, ky, jx, jy, width, height);
    }
}
