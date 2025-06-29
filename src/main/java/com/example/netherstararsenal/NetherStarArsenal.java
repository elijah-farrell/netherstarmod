package com.example.netherstararsenal;

import com.example.netherstararsenal.init.CreativeTabInit;
import com.example.netherstararsenal.init.ItemInit;
import com.example.netherstararsenal.init.ModEventHandler;
import com.example.netherstararsenal.init.RecipeInit;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("netherstararsenal")
public class NetherStarArsenal {
    
    public static final String MOD_ID = "netherstararsenal";
    
    public NetherStarArsenal() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        // Register items
        ItemInit.ITEMS.register(modEventBus);
        
        // Register recipe serializers
        RecipeInit.RECIPE_SERIALIZERS.register(modEventBus);
        
        // Register creative tab event listener
        modEventBus.addListener(CreativeTabInit::addCreative);
        
        // Register mod event handler
        MinecraftForge.EVENT_BUS.register(new ModEventHandler());
        
        // Register this mod instance
        MinecraftForge.EVENT_BUS.register(this);
    }
} 