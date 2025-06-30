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
            // Add combat items to Combat tab
            event.accept(ItemInit.NETHER_STAR_SWORD);
            event.accept(ItemInit.NETHER_BOW);
            // Add armor to Combat tab (armor is in combat tab in vanilla)
            event.accept(ItemInit.NETHER_STAR_HELMET);
            event.accept(ItemInit.NETHER_STAR_CHESTPLATE);
            event.accept(ItemInit.NETHER_STAR_LEGGINGS);
            event.accept(ItemInit.NETHER_STAR_BOOTS);
        }
        
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            // Add tools to Tools & Utilities tab
            event.accept(ItemInit.NETHER_STAR_PICKAXE);
            event.accept(ItemInit.NETHER_STAR_AXE);
            event.accept(ItemInit.NETHER_STAR_SHOVEL);
        }
    }
} 