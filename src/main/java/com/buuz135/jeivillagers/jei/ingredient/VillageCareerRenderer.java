package com.buuz135.jeivillagers.jei.ingredient;

import com.buuz135.jeivillagers.jei.VillagerPlugin;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class VillageCareerRenderer implements IIngredientRenderer<VillagerRegistry.VillagerCareer> {
    @Override
    public void render(Minecraft minecraft, int xPosition, int yPosition, @Nullable VillagerRegistry.VillagerCareer ingredient) {
        if (ingredient != null) { //TODO Select a item from the trade
            GlStateManager.enableDepth();
            RenderHelper.enableGUIStandardItemLighting();
            FontRenderer font = getFontRenderer(minecraft, ingredient);
            minecraft.getRenderItem().renderItemAndEffectIntoGUI(null, VillagerPlugin.getVillagerEgg(), xPosition, yPosition);
            minecraft.getRenderItem().renderItemOverlayIntoGUI(font,  VillagerPlugin.getVillagerEgg(), xPosition, yPosition, null);
            GlStateManager.disableBlend();
            RenderHelper.disableStandardItemLighting();
        }
    }

    @Override
    public List<String> getTooltip(Minecraft minecraft, VillagerRegistry.VillagerCareer ingredient, ITooltipFlag tooltipFlag) {
        return Arrays.asList(new TextComponentTranslation("entity.Villager."+ingredient.getName()).getUnformattedComponentText());
    }

    @Override
    public FontRenderer getFontRenderer(Minecraft minecraft, VillagerRegistry.VillagerCareer ingredient) {
        return Minecraft.getMinecraft().fontRenderer;
    }

}
