package net.oshewo.blairesbrews.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.oshewo.blairesbrews.BlairesBrews;

public class BrewingKettleScreen extends HandledScreen<BrewingKettleScreenHandler> {
    private static final Identifier TEXTURE =
            new Identifier(BlairesBrews.MOD_ID, "textures/gui/brewing_kettle_gui.png");

    public BrewingKettleScreen(BrewingKettleScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleX =(backgroundWidth- textRenderer.getWidth(title)-5);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);
        RenderSystem.setShaderTexture(0,TEXTURE);
        int x = (width-backgroundWidth)/2;
        int y = (height-backgroundHeight)/2;
        drawTexture(matrices,x,y,0,0,backgroundWidth,backgroundHeight);

        if(handler.isCrafting()){
            drawTexture(matrices, x+112, y+67,176,27, handler.getScaledProgress(),10);
        }

        if(handler.hasFuel()){
            drawTexture(matrices,x+8,y+38 + 14 -handler.getScaledFuelProgress(), 176,14- handler.getScaledFuelProgress(),14, handler.getScaledFuelProgress());
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices,mouseX,mouseY);
    }
}
