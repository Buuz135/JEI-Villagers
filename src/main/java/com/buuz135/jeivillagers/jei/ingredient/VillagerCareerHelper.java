package com.buuz135.jeivillagers.jei.ingredient;

import mezz.jei.api.ingredients.IIngredientHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class VillagerCareerHelper implements IIngredientHelper<VillagerRegistry.VillagerCareer>{
    @Override
    public List<VillagerRegistry.VillagerCareer> expandSubtypes(List<VillagerRegistry.VillagerCareer> ingredients) {
        return ingredients;
    }

    @Nullable
    @Override
    public VillagerRegistry.VillagerCareer getMatch(Iterable<VillagerRegistry.VillagerCareer> ingredients, VillagerRegistry.VillagerCareer ingredientToMatch) {
        for (VillagerRegistry.VillagerCareer career : ingredients){
            if (career.equals(ingredientToMatch)) return career;
        }
        return null;
    }

    @Override
    public String getDisplayName(VillagerRegistry.VillagerCareer ingredient) {
        return new TextComponentTranslation("entity.Villager."+ingredient.getName()).getUnformattedComponentText();
    }

    @Override
    public String getUniqueId(VillagerRegistry.VillagerCareer ingredient) {
        return "villager_career:"+ingredient.getName();
    }

    @Override
    public String getWildcardId(VillagerRegistry.VillagerCareer ingredient) {
        return getUniqueId(ingredient);
    }

    @Override
    public String getModId(VillagerRegistry.VillagerCareer ingredient) {
        return "JEI Villagers";
    }

    @Override
    public Iterable<Color> getColors(VillagerRegistry.VillagerCareer ingredient) {
        return Arrays.asList();
    }

    @Override
    public String getResourceId(VillagerRegistry.VillagerCareer ingredient) {
        return ingredient.getName();
    }

    @Override
    public VillagerRegistry.VillagerCareer copyIngredient(VillagerRegistry.VillagerCareer ingredient) {
        return ingredient;
    }

    @Override
    public String getErrorInfo(VillagerRegistry.VillagerCareer ingredient) {
        return "";
    }
}
