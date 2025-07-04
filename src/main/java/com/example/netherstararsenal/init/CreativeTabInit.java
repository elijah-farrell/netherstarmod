package com.example.netherstararsenal.init;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;

/**
 * Handles adding items to vanilla creative tabs for the Nether Star Arsenal mod
 * 
 * @author Elijah Farrell
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreativeTabInit {
    
    // Custom creative tab registration
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(ResourceKey.createRegistryKey(Registries.CREATIVE_MODE_TAB.location()), "netherstararsenal");

    public static final RegistryObject<CreativeModeTab> NETHERSTAR_TAB = TABS.register("netherstar_tab",
        () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ItemInit.NETHER_STAR_SWORD.get()))
            .title(Component.translatable("itemGroup.netherstararsenal"))
            .build()
    );

    static {
        TABS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == NETHERSTAR_TAB.getKey()) {
            event.accept(ItemInit.NETHER_STAR_HELMET);
            event.accept(ItemInit.NETHER_STAR_CHESTPLATE);
            event.accept(ItemInit.NETHER_STAR_LEGGINGS);
            event.accept(ItemInit.NETHER_STAR_BOOTS);
            event.accept(ItemInit.NETHER_STAR_SWORD);
            event.accept(ItemInit.NETHER_STAR_PICKAXE);
            event.accept(ItemInit.NETHER_STAR_AXE);
            event.accept(ItemInit.NETHER_STAR_SHOVEL);
            event.accept(ItemInit.NETHER_STAR_HOE);
        }
    }

} 