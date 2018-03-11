package com.buuz135.jeivillagers.jei;

import com.buuz135.jeivillagers.FakeVillager;
import com.buuz135.jeivillagers.Jeivillagers;
import com.buuz135.jeivillagers.jei.ingredient.VillageCareerRenderer;
import com.buuz135.jeivillagers.jei.ingredient.VillagerCareerFactory;
import com.buuz135.jeivillagers.jei.ingredient.VillagerCareerHelper;
import com.google.common.base.Preconditions;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import java.util.Random;
import java.util.stream.Collectors;

import static com.buuz135.jeivillagers.Jeivillagers.tradeInfoMultimap;

@JEIPlugin
public class VillagerPlugin implements IModPlugin {


    private VillagerCategory category;
    private ISubtypeRegistry subtypes;
    private static IRecipesGui recipesGui;
    private static IRecipeRegistry recipeRegistry;

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
        this.subtypes = subtypeRegistry;
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registry) {
        Preconditions.checkState(this.subtypes != null);
        registry.register(VillagerRegistry.VillagerCareer.class, VillagerCareerFactory.create(), new VillagerCareerHelper(), new VillageCareerRenderer());
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        category = new VillagerCategory(registry.getJeiHelpers().getGuiHelper());
        registry.addRecipeCategories(category);
    }

    @Override
    public void register(IModRegistry registry) {
        registry.addRecipeCatalyst(getVillagerEgg(), category.getUid());

        tradeInfoMultimap.clear();
        FakeVillager merchant = new FakeVillager();
        Random random = new Random();
        for (VillagerRegistry.VillagerProfession profession : ForgeRegistries.VILLAGER_PROFESSIONS) {
            int id = 1;
            VillagerRegistry.VillagerCareer original = profession.getCareer(0);
            Jeivillagers.registerCareer(merchant, original, random);
            while (!original.equals(profession.getCareer(id))) {
                Jeivillagers.registerCareer(merchant, profession.getCareer(id), random);
                ++id;
            }
        }
        for (Jeivillagers.VillagerTradeInfo tradeInfo : tradeInfoMultimap.values()) {
            tradeInfo.clean();
        }
        for (VillagerRegistry.VillagerCareer career : tradeInfoMultimap.keySet()) {
            registry.addRecipes(tradeInfoMultimap.get(career).stream().filter(villagerTradeInfo -> !villagerTradeInfo.outputStack.isEmpty()).map(villagerTradeInfo -> new VillagerRecipe(career, villagerTradeInfo)).collect(Collectors.toList()), category.getUid());
        }
        registry.addRecipeClickArea(GuiMerchant.class, 82, 52, 26, 15, category.getUid());
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        recipesGui = jeiRuntime.getRecipesGui();
        recipeRegistry = jeiRuntime.getRecipeRegistry();
    }

    public static ItemStack getVillagerEgg(){
        NBTTagCompound compound = new NBTTagCompound();
        NBTTagCompound idnbt = new NBTTagCompound();
        idnbt.setString("id", "minecraft:villager");
        compound.setTag("EntityTag", idnbt);
        ItemStack stack = new ItemStack(Items.SPAWN_EGG);
        stack.setTagCompound(compound);
        return stack;
    }

    public static void showUses(VillagerRegistry.VillagerCareer career) {
        if (recipesGui != null && recipeRegistry != null)
            recipesGui.show(recipeRegistry.createFocus(IFocus.Mode.OUTPUT, career));
    }
}
