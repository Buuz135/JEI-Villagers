package com.buuz135.jeivillagers;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import java.util.Random;

@Mod(
        modid = Jeivillagers.MOD_ID,
        name = Jeivillagers.MOD_NAME,
        version = Jeivillagers.VERSION,
        dependencies = "required-after:jei@[4.7.8.91,)", clientSideOnly = true
)
public class Jeivillagers {

    public static final String MOD_ID = "jeivillagers";
    public static final String MOD_NAME = "Jeivillagers";
    public static final String VERSION = "1.0";

    public static Multimap<VillagerRegistry.VillagerCareer, VillagerTradeInfo> tradeInfoMultimap = ArrayListMultimap.create();
    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static Jeivillagers INSTANCE;

    /**
     * This is the first initialization event. Register tile entities here.
     * The registry events below will have fired prior to entry to this method.
     */
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {

    }

    /**
     * This is the second initialization event. Register custom recipes
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    public static void registerCareer(IMerchant merchant, VillagerRegistry.VillagerCareer career, Random random) {
        int level = 0;
        while (career.getTrades(level) != null && level < 50) {
            for (EntityVillager.ITradeList list : career.getTrades(level)) {
                if (list.getClass().toString().endsWith("TreasureMapForEmeralds")) continue;
                try {
                    tradeInfoMultimap.put(career, generateTradeInfo(merchant, list, random));
                } catch (Exception e) {
                    System.out.println("Error checking recipes for class " + list.getClass().toString());
                }
            }
            ++level;
        }
    }

    private static VillagerTradeInfo generateTradeInfo(IMerchant merchant, EntityVillager.ITradeList tradeList, Random random) {
        VillagerTradeInfo info = new VillagerTradeInfo();
        for (int i = 0; i < 100; ++i) {
            MerchantRecipeList list = new MerchantRecipeList();
            tradeList.addMerchantRecipe(merchant, list, random);
            if (list.size() > 0) {
                info.addMerchantRecipeInfo(list.get(0));
            }
        }
        return info;
    }

    /**
     * This is the final initialization event. Register actions from other mods here
     */
    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {

    }

    public static class VillagerTradeInfo {

        public ItemStack firstInput;
        public ItemStack secondInput;
        public ItemStack outputStack;

        public EntityVillager.PriceInfo first;
        public EntityVillager.PriceInfo second;
        public EntityVillager.PriceInfo output;

        public VillagerTradeInfo() {
            firstInput = ItemStack.EMPTY;
            secondInput = ItemStack.EMPTY;
            outputStack = ItemStack.EMPTY;
        }

        public void addMerchantRecipeInfo(MerchantRecipe recipe) {
            firstInput = cleanStack(recipe.getItemToBuy());
            secondInput = cleanStack(recipe.getSecondItemToBuy());
            outputStack = cleanStack(recipe.getItemToSell());

            first = checkPriceInfo(first, recipe.getItemToBuy());
            second = checkPriceInfo(second, recipe.getSecondItemToBuy());
            output = checkPriceInfo(output, recipe.getItemToSell());
        }

        public ItemStack cleanStack(ItemStack stack) {
            if (stack.isEmpty()) return stack;
            ItemStack cleaned = stack.copy();
            cleaned.setCount(1);
            return cleaned;
        }

        public EntityVillager.PriceInfo checkPriceInfo(EntityVillager.PriceInfo info, ItemStack stack) {
            if (stack.isEmpty()) return info;
            if (info == null) return new EntityVillager.PriceInfo(stack.getCount(), stack.getCount());
            return new EntityVillager.PriceInfo(stack.getCount() < info.getFirst() ? stack.getCount() : info.getFirst(), stack.getCount() > info.getSecond() ? stack.getCount() : info.getSecond());
        }

        public void clean() {
            first = cleanStackWithInfo(firstInput, first);
            second = cleanStackWithInfo(secondInput, second);
            output = cleanStackWithInfo(outputStack, output);
        }

        private EntityVillager.PriceInfo cleanStackWithInfo(ItemStack stack, EntityVillager.PriceInfo info) {
            if (info == null || stack.isEmpty()) return null;
            if (info.getFirst().equals(info.getSecond())) {
                stack.setCount(info.getFirst());
                return null;
            }
            return info;
        }
    }

    /**
     * Forge will automatically look up and bind blocks to the fields in this class
     * based on their registry name.
     */
    @GameRegistry.ObjectHolder(MOD_ID)
    public static class Blocks {
      /*
          public static final MySpecialBlock mySpecialBlock = null; // placeholder for special block below
      */
    }

    /**
     * Forge will automatically look up and bind items to the fields in this class
     * based on their registry name.
     */
    @GameRegistry.ObjectHolder(MOD_ID)
    public static class Items {
      /*
          public static final ItemBlock mySpecialBlock = null; // itemblock for the block above
          public static final MySpecialItem mySpecialItem = null; // placeholder for special item below
      */
    }

    /**
     * This is a special class that listens to registry events, to allow creation of mod blocks and items at the proper time.
     */
    @Mod.EventBusSubscriber
    public static class ObjectRegistryHandler {
        /**
         * Listen for the register event for creating custom items
         */
        @SubscribeEvent
        public static void addItems(RegistryEvent.Register<Item> event) {
           /*
             event.getRegistry().register(new ItemBlock(Blocks.myBlock).setRegistryName(MOD_ID, "myBlock"));
             event.getRegistry().register(new MySpecialItem().setRegistryName(MOD_ID, "mySpecialItem"));
            */
        }

        /**
         * Listen for the register event for creating custom blocks
         */
        @SubscribeEvent
        public static void addBlocks(RegistryEvent.Register<Block> event) {
           /*
             event.getRegistry().register(new MySpecialBlock().setRegistryName(MOD_ID, "mySpecialBlock"));
            */
        }
    }
    /* EXAMPLE ITEM AND BLOCK - you probably want these in separate files
    public static class MySpecialItem extends Item {

    }

    public static class MySpecialBlock extends Block {

    }
    */
}
