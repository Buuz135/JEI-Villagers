package com.buuz135.jeivillagers.jei;

import com.buuz135.jeivillagers.Jeivillagers;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class VillagerCategory implements IRecipeCategory<VillagerRecipe> {

    private final IGuiHelper helper;

    public VillagerCategory(IGuiHelper helper) {
        this.helper = helper;
    }

    @Override
    public String getUid() {
        return "VILLAGER_TRADE_CATEGORY";
    }

    @Override
    public String getTitle() {
        return "Villager Trades";
    }

    @Override
    public String getModName() {
        return Jeivillagers.MOD_NAME;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IDrawable getBackground() {
        return helper.createDrawable(new ResourceLocation(Jeivillagers.MOD_ID, "textures/gui/villager.png"), 0, 0, 106, 26, 9, Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT * 3 + 6, 0, 0);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, VillagerRecipe recipeWrapper, IIngredients ingredients) {
        int y = 12;
        recipeLayout.getItemStacks().init(0, true, 0, y);
        recipeLayout.getItemStacks().init(1, true, 26, y);
        recipeLayout.getItemStacks().init(2, false, 84, y + 1);

        recipeLayout.getItemStacks().set(0, ingredients.getInputs(ItemStack.class).get(0));
        recipeLayout.getItemStacks().set(1, ingredients.getInputs(ItemStack.class).get(1));
        recipeLayout.getItemStacks().set(2, ingredients.getOutputs(ItemStack.class).get(0));
        ingredients.setOutput(VillagerRegistry.VillagerCareer.class, ingredients.getOutputs(VillagerRegistry.VillagerCareer.class).get(0));
        ingredients.setInput(VillagerRegistry.VillagerCareer.class, ingredients.getInputs(VillagerRegistry.VillagerCareer.class).get(0));
    }
}
