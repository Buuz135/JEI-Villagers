package com.buuz135.jeivillagers.jei.ingredient;

import com.buuz135.jeivillagers.Jeivillagers;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import java.util.ArrayList;
import java.util.List;

public class VillagerCareerFactory {

    public static List<VillagerRegistry.VillagerCareer> create(){
        List<VillagerRegistry.VillagerCareer> careers = new ArrayList<>();
        for (VillagerRegistry.VillagerProfession profession : ForgeRegistries.VILLAGER_PROFESSIONS) {
            int id = 1;
            VillagerRegistry.VillagerCareer original = profession.getCareer(0);
            if (!Jeivillagers.isClassBlackListed(profession.getCareer(0).getClass())) careers.add(profession.getCareer(0));
            while (!original.equals(profession.getCareer(id))) {
                if (!Jeivillagers.isClassBlackListed(profession.getCareer(0).getClass())) careers.add(profession.getCareer(id));
                ++id;
            }
        }
        return careers;
    }
}
