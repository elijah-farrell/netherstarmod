package com.example.netherstararsenal.init;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Handles adding items to vanilla creative tabs for the Nether Star Arsenal mod
 * 
 * @author Elijah Farrell
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreativeTabInit {
    
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            // Add armor pieces (they will appear after netherite armor due to alphabetical sorting)
            event.accept(ItemInit.NETHER_STAR_HELMET);
            event.accept(ItemInit.NETHER_STAR_CHESTPLATE);
            event.accept(ItemInit.NETHER_STAR_LEGGINGS);
            event.accept(ItemInit.NETHER_STAR_BOOTS);
            
            // Add weapons
            event.accept(ItemInit.NETHER_STAR_SWORD);
            event.accept(ItemInit.NETHER_STAR_AXE);
        }
        
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            // Add tools
            event.accept(ItemInit.NETHER_STAR_PICKAXE);
            event.accept(ItemInit.NETHER_STAR_SHOVEL);
            event.accept(ItemInit.NETHER_STAR_HOE);
            event.accept(ItemInit.NETHER_STAR_AXE);
        }
        
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            // (Guide book removed)
        }
    }
} 