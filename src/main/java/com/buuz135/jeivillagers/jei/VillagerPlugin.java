package com.buuz135.jeivillagers.jei;

import com.buuz135.jeivillagers.FakeVillager;
import com.buuz135.jeivillagers.Jeivillagers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
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

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        category = new VillagerCategory(registry.getJeiHelpers().getGuiHelper());
        registry.addRecipeCategories(category);
    }

    @Override
    public void register(IModRegistry registry) {
        NBTTagCompound compound = new NBTTagCompound();
        NBTTagCompound idnbt = new NBTTagCompound();
        idnbt.setString("id", "minecraft:villager");
        compound.setTag("EntityTag", idnbt);
        ItemStack stack = new ItemStack(Items.SPAWN_EGG);
        stack.setTagCompound(compound);
        registry.addRecipeCatalyst(stack, category.getUid());

        long time = System.currentTimeMillis();
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
        System.out.println("Checked villager recipes in " + (System.currentTimeMillis() - time) + "ms.");

        for (VillagerRegistry.VillagerCareer career : tradeInfoMultimap.keySet()) {
            registry.addRecipes(tradeInfoMultimap.get(career).stream().filter(villagerTradeInfo -> !villagerTradeInfo.outputStack.isEmpty()).map(villagerTradeInfo -> new VillagerRecipe(career, villagerTradeInfo)).collect(Collectors.toList()), category.getUid());
        }
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {

    }
}
