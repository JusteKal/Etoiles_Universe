package be.justekal.etoiles_universe.screen;

import be.justekal.etoiles_universe.EtoilesUniverseMod;
import be.justekal.etoiles_universe.inventory.EtoilesInventoryMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class EtoilesInventoryScreen extends AbstractContainerScreen<EtoilesInventoryMenu> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(EtoilesUniverseMod.MODID, "textures/gui/etoiles_inventory.png");
    
    private float xMouse;
    private float yMouse;

    public EtoilesInventoryScreen(EtoilesInventoryMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.imageHeight = 256; // Hauteur totale de l'interface (inventaire entité + joueur)
        this.inventoryLabelY = this.imageHeight - 94; // Position du label "Inventory"
        this.titleLabelX = 82; // Décale le titre "Etoiles Inventory" vers la droite
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        this.xMouse = (float)mouseX;
        this.yMouse = (float)mouseY;
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        
        // Rendu de l'entité Etoiles PAR-DESSUS le background mais AVANT les tooltips
        if (this.menu.getEntity() != null) {
            int x = (width - imageWidth) / 2;
            int y = (height - imageHeight) / 2;
            int entityX = x + 50;
            int entityY = y + 76;
            InventoryScreen.renderEntityInInventoryFollowsMouse(
                guiGraphics, 
                entityX, 
                entityY, 
                30, 
                (float)(entityX) - this.xMouse, 
                (float)(entityY - 50) - this.yMouse, 
                this.menu.getEntity()
            );
        }
        
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
