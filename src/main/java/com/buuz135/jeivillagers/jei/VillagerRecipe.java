package com.buuz135.jeivillagers.jei;

import com.buuz135.jeivillagers.Jeivillagers;
import com.buuz135.jeivillagers.config.VillagerConfig;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.List;

public class VillagerRecipe implements IRecipeWrapper {

    private final VillagerRegistry.VillagerCareer career;
    private final Jeivillagers.VillagerTradeInfo tradeInfo;

    public VillagerRecipe(VillagerRegistry.VillagerCareer career, Jeivillagers.VillagerTradeInfo tradeInfo) {
        this.career = career;
        this.tradeInfo = tradeInfo;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(ItemStack.class, Arrays.asList(tradeInfo.firstInput, tradeInfo.secondInput));
        ingredients.setOutput(ItemStack.class, tradeInfo.outputStack);
        ingredients.setOutput(VillagerRegistry.VillagerCareer.class, career);
        ingredients.setInput(VillagerRegistry.VillagerCareer.class, career);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        int y = 38;
        drawStringCentered(minecraft.fontRenderer, TextFormatting.DARK_GRAY + (VillagerConfig.CompactMode ? "" : I18n.format(Jeivillagers.MOD_ID + ".career")) + new TextComponentTranslation("entity.Villager." + career.getName()).getUnformattedComponentText(), 53, -1);
        if (tradeInfo.first != null) {
            if (VillagerConfig.CompactMode){
                drawStringCentered(minecraft.fontRenderer, TextFormatting.DARK_GRAY +""+ tradeInfo.first.getFirst() + "-" + tradeInfo.first.getSecond(), -18, 18);
            }else{
                drawStringCentered(minecraft.fontRenderer, TextFormatting.DARK_GRAY + I18n.format(Jeivillagers.MOD_ID + ".first.input") + tradeInfo.first.getFirst() + "-" + tradeInfo.first.getSecond(), 53, y);
                y += minecraft.fontRenderer.FONT_HEIGHT + 2;
            }
        }
        if (tradeInfo.second != null) {
            if (VillagerConfig.CompactMode){
                drawStringCentered(minecraft.fontRenderer, TextFormatting.DARK_GRAY +""+ tradeInfo.second.getFirst() + "-" + tradeInfo.second.getSecond(), 36, 34);
            }else {
                drawStringCentered(minecraft.fontRenderer, TextFormatting.DARK_GRAY + I18n.format(Jeivillagers.MOD_ID + ".second.input") + tradeInfo.second.getFirst() + "-" + tradeInfo.second.getSecond(), 53, y);
                y += minecraft.fontRenderer.FONT_HEIGHT + 2;
            }
        }
        if (tradeInfo.output != null) {
            if (VillagerConfig.CompactMode){
                drawStringCentered(minecraft.fontRenderer, TextFormatting.DARK_GRAY +""+ tradeInfo.output.getFirst() + "-" + tradeInfo.output.getSecond(), 126, 18);
            }else {
                drawStringCentered(minecraft.fontRenderer, TextFormatting.DARK_GRAY + I18n.format(Jeivillagers.MOD_ID + ".output") + tradeInfo.output.getFirst() + "-" + tradeInfo.output.getSecond(), 53, y);
            }
        }
        if (tradeInfo.outputStack.isItemEnchanted() || tradeInfo.outputStack.getItem().equals(Items.ENCHANTED_BOOK)) {
            if (VillagerConfig.CompactMode) drawStringCentered(minecraft.fontRenderer, TextFormatting.DARK_GRAY + "random", 95, y);
            else drawStringCentered(minecraft.fontRenderer, TextFormatting.DARK_GRAY + I18n.format(Jeivillagers.MOD_ID + ".random.enchant.output"), 53, y);
        }
    }

    @SideOnly(Side.CLIENT)
    private void drawStringCentered(FontRenderer fontRenderer, String text, int x, int y) {
        fontRenderer.drawString(text, (x - fontRenderer.getStringWidth(text) / 2), y, 0);
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        int x = 53;
        int y = 0;
        int size = Minecraft.getMinecraft().fontRenderer.getStringWidth(TextFormatting.DARK_GRAY + I18n.format(Jeivillagers.MOD_ID + ".career") + new TextComponentTranslation("entity.Villager." + career.getName()).getUnformattedComponentText());
        if (mouseX > x-size/2 -4 && mouseX < x+size/2 +4 && mouseY > y -4 && mouseY < y+Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT) return Arrays.asList("Show recipes for "+new TextComponentTranslation("entity.Villager." + career.getName()).getUnformattedComponentText()+ " career");
        return null;
    }

    @Override
    public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        int x = 53;
        int y = 0;
        int size = Minecraft.getMinecraft().fontRenderer.getStringWidth(TextFormatting.DARK_GRAY + I18n.format(Jeivillagers.MOD_ID + ".career") + new TextComponentTranslation("entity.Villager." + career.getName()).getUnformattedComponentText());
        if (mouseX > x-size/2 -4 && mouseX < x+size/2 +4 && mouseY > y -4 && mouseY < y+Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT){
            VillagerPlugin.showUses(career);
            return true;
        }
        return false;
    }
}
