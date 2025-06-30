package com.example.netherstararsenal.init;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Handles registration of custom recipes for the Nether Star Arsenal mod
 * 
 * @author Elijah Farrell
 */
public class RecipeInit {
    
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "netherstararsenal");
    
    // Custom recipe serializers can be added here if needed
    // For now, we'll use JSON recipes in the resources folder
} 