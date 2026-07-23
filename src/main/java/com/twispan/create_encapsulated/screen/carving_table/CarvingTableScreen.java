package com.twispan.create_encapsulated.screen.carving_table;

import com.mojang.blaze3d.systems.RenderSystem;
import com.twispan.create_encapsulated.CreateEncapsulated;
import com.twispan.create_encapsulated.crafting.CarvingRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CarvingTableScreen extends AbstractContainerScreen<CarvingTableMenu> {

    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(CreateEncapsulated.MODID,
                    "textures/gui/carving_table.png");

    private float scrollOffs;
    private boolean scrolling;
    private int startIndex;
    private boolean displayRecipes;

    public CarvingTableScreen(CarvingTableMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);

        this.titleLabelX = 8;
        this.titleLabelY = 4;
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.containerChanged();
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        if (this.canScroll()) {
            int scrollbarX = x + 105;
            int scrollbarY = y + 15;
            int scrollbarTexY = this.displayRecipes ? 0 : 12;
            guiGraphics.blit(GUI_TEXTURE, scrollbarX + 14, scrollbarY + (int)(41.0F * this.scrollOffs),
                    176 + scrollbarTexY, 0, 12, 15);
        } else {
            int scrollbarX = x + 105;
            int scrollbarY = y + 15;
            guiGraphics.blit(GUI_TEXTURE, scrollbarX + 14, scrollbarY,
                    176 + 12, 0, 12, 15);
        }

        int recipeAreaX = x + 52;
        int recipeAreaY = y + 14;
        int selectedRecipe = this.menu.getSelectedRecipeIndex();

        this.renderButtons(guiGraphics, mouseX, mouseY, recipeAreaX, recipeAreaY, selectedRecipe);
        this.renderRecipes(guiGraphics, recipeAreaX, recipeAreaY, selectedRecipe);
    }

    @Override
    protected void renderTooltip(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderTooltip(guiGraphics, mouseX, mouseY);

        if (this.displayRecipes) {
            int x = (this.width - this.imageWidth) / 2;
            int y = (this.height - this.imageHeight) / 2;
            int recipeAreaX = x + 52;
            int recipeAreaY = y + 14;

            List<RecipeHolder<CarvingRecipe>> recipes = this.menu.getRecipes();

            for (int i = this.startIndex; i < Math.min(this.startIndex + 12, recipes.size()); i++) {
                int col = (i - this.startIndex) % 4;
                int row = (i - this.startIndex) / 4;
                int buttonX = recipeAreaX + col * 16;
                int buttonY = recipeAreaY + row * 18 + 2;

                if (mouseX >= buttonX && mouseX < buttonX + 16 &&
                        mouseY >= buttonY && mouseY < buttonY + 16) {
                    assert this.minecraft != null;
                    assert this.minecraft.level != null;
                    guiGraphics.renderTooltip(this.font, recipes.get(i).value().getResultItem(
                            this.minecraft.level.registryAccess()), mouseX, mouseY);
                }
            }
        }
    }

    private void renderButtons(GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y, int selectedRecipe) {
        if (!this.displayRecipes) return;

        int numRecipes = this.menu.getNumRecipes();
        for (int i = this.startIndex; i < Math.min(this.startIndex + 12, numRecipes); i++) {
            int col = (i - this.startIndex) % 4;
            int row = (i - this.startIndex) / 4;
            int buttonX = x + col * 16;
            int buttonY = y + row * 18 + 2;
            int textureY = this.imageHeight;

            if (i == selectedRecipe) {
                textureY += 18;
            } else if (mouseX >= buttonX && mouseX < buttonX + 16 &&
                    mouseY >= buttonY && mouseY < buttonY + 16) {
                textureY += 36;
            }

            guiGraphics.blit(GUI_TEXTURE, buttonX, buttonY - 1, 0, textureY, 16, 18);
        }
    }

        private void renderRecipes(GuiGraphics guiGraphics, int x, int y, int selectedRecipe) {
        if (!this.displayRecipes) return;

        List<RecipeHolder<CarvingRecipe>> recipes = this.menu.getRecipes();

        if (this.startIndex < 0) this.startIndex = 0;
        if (this.startIndex >= recipes.size() && !recipes.isEmpty()) {
            this.startIndex = Math.max(0, recipes.size() - 12);
        }

        for (int i = this.startIndex; i < Math.min(this.startIndex + 12, recipes.size()); i++) {
            int col = (i - this.startIndex) % 4;
            int row = (i - this.startIndex) / 4;
            int buttonX = x + col * 16;
            int buttonY = y + row * 18 + 2;

            assert this.minecraft != null;
            assert this.minecraft.level != null;
            guiGraphics.renderItem(recipes.get(i).value().getResultItem(
                    this.minecraft.level.registryAccess()), buttonX, buttonY);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.scrolling = false;

        if (this.displayRecipes) {
            int x = (this.width - this.imageWidth) / 2;
            int y = (this.height - this.imageHeight) / 2;
            int recipeAreaX = x + 52;
            int recipeAreaY = y + 14;

            for (int i = this.startIndex; i < Math.min(this.startIndex + 12, this.menu.getNumRecipes()); i++) {
                int col = (i - this.startIndex) % 4;
                int row = (i - this.startIndex) / 4;
                int buttonX = recipeAreaX + col * 16;
                int buttonY = recipeAreaY + row * 18 + 2;

                if (mouseX >= buttonX && mouseX < buttonX + 16 &&
                        mouseY >= buttonY && mouseY < buttonY + 16) {

                    if (this.minecraft != null && this.minecraft.gameMode != null) {
                        this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, i);
                        Minecraft.getInstance().getSoundManager().play(
                                net.minecraft.client.resources.sounds.SimpleSoundInstance.forUI(
                                        SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
                    }
                    return true;
                }
            }

            // Check scrollbar click
            int scrollbarX = x + 119;
            int scrollbarY = y + 15;

            if (mouseX >= scrollbarX && mouseX < scrollbarX + 14 &&
                    mouseY >= scrollbarY && mouseY < scrollbarY + 54) {
                this.scrolling = true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.scrolling && this.displayRecipes) {
            int y = (this.height - this.imageHeight) / 2;
            int scrollbarY = y + 15;
            int scrollbarHeight = 54;

            this.scrollOffs = ((float)mouseY - scrollbarY - 7.5F) / (scrollbarHeight - 15.0F);
            this.scrollOffs = Math.clamp(this.scrollOffs, 0.0F, 1.0F);
            this.startIndex = (int)((double)(this.scrollOffs * (float)this.getOffscreenRows()) + 0.5D) * 4;
            return true;
        }

        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (this.displayRecipes && this.canScroll()) {
            int offscreenRows = this.getOffscreenRows();
            float delta = (float)scrollY / (float)offscreenRows;
            this.scrollOffs = Math.clamp(this.scrollOffs - delta, 0.0F, 1.0F);
            this.startIndex = (int)((double)(this.scrollOffs * (float)offscreenRows) + 0.5D) * 4;
        }

        return true;
    }

    private boolean canScroll() {
        return this.displayRecipes && this.menu.getNumRecipes() > 12;
    }

    protected int getOffscreenRows() {
        int numRecipes = this.menu.getNumRecipes();
        if (numRecipes <= 12) return 0;
        return (numRecipes + 3) / 4 - 3;
    }

    private void containerChanged() {
        this.displayRecipes = this.menu.hasInputItem();

        if (!this.displayRecipes) {
            this.scrollOffs = 0.0F;
            this.startIndex = 0;
        }
    }
}
