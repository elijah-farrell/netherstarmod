package com.example.netherstararsenal;

import com.example.netherstararsenal.init.CreativeTabInit;
import com.example.netherstararsenal.init.ItemInit;
import com.example.netherstararsenal.init.RecipeInit;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("netherstararsenal")
public class NetherStarArsenal {
    public NetherStarArsenal() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemInit.ITEMS.register(modEventBus);
        RecipeInit.RECIPE_SERIALIZERS.register(modEventBus);
        
        // Register creative tab event listener
        modEventBus.addListener(CreativeTabInit::addCreative);
        
        MinecraftForge.EVENT_BUS.register(this);
    }
} 