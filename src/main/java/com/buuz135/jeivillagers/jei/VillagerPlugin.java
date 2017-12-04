package com.buuz135.jeivillagers.jei;

import com.buuz135.jeivillagers.Jeivillagers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.entity.NpcMerchant;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import java.util.stream.Collectors;

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
        NBTTagCompound id = new NBTTagCompound();
        id.setString("id", "minecraft:villager");
        compound.setTag("EntityTag",id );
        ItemStack stack = new ItemStack(Items.SPAWN_EGG);
        stack.setTagCompound(compound);
        registry.addRecipeCatalyst(stack, category.getUid());
        for (VillagerRegistry.VillagerCareer career : Jeivillagers.tradeInfoMultimap.keySet()){
            registry.addRecipes(Jeivillagers.tradeInfoMultimap.get(career).stream().filter(villagerTradeInfo -> !villagerTradeInfo.outputStack.isEmpty()).map(villagerTradeInfo -> new VillagerRecipe(career, villagerTradeInfo)).collect(Collectors.toList()), category.getUid());
        }
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {

    }
}
