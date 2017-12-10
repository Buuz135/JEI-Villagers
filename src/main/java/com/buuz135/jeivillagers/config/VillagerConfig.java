package com.buuz135.jeivillagers.config;

import com.buuz135.jeivillagers.Jeivillagers;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Jeivillagers.MOD_ID)
public class VillagerConfig {

    @Config.Comment("If enabled will reduce the amount of text displayed in JEI recipes")
    public static boolean CompactMode = false;

    @Config.Comment("A list of blacklisted VillagerCareer classes that causes incompatibilities as they are implemented differently (not using the MC way) or just don't want to be shown in JEI. Removing the defaults can cause issues")
    public static String[] BlackListedVillagerClasses = new String[]{"XUVillagerCareer"};

    @Mod.EventBusSubscriber(modid = Jeivillagers.MOD_ID)
    private static class EventHandler{
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(Jeivillagers.MOD_ID)) {
                ConfigManager.sync(Jeivillagers.MOD_ID, Config.Type.INSTANCE);
            }
        }
    }
}
