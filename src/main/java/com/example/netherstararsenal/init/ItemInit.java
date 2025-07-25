package com.example.netherstararsenal.init;

import com.example.netherstararsenal.items.*;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Handles registration of all items for the Nether Star Arsenal mod
 * Registers items with the game registry and provides static references
 * 
 * @author Elijah Farrell
 */
public class ItemInit {
    
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "netherstararsenal");
    
    // Nether Star Armor
    public static final RegistryObject<Item> NETHER_STAR_HELMET = ITEMS.register("nether_star_helmet",
            () -> new NetherStarArmorItem(ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> NETHER_STAR_CHESTPLATE = ITEMS.register("nether_star_chestplate",
            () -> new NetherStarArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> NETHER_STAR_LEGGINGS = ITEMS.register("nether_star_leggings",
            () -> new NetherStarArmorItem(ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> NETHER_STAR_BOOTS = ITEMS.register("nether_star_boots",
            () -> new NetherStarArmorItem(ArmorItem.Type.BOOTS, new Item.Properties()));
    
    // Nether Star Tools
    public static final RegistryObject<Item> NETHER_STAR_SWORD = ITEMS.register("nether_star_sword",
            () -> new NetherStarSword(new Item.Properties()));
    public static final RegistryObject<Item> NETHER_STAR_PICKAXE = ITEMS.register("nether_star_pickaxe",
            () -> new NetherStarPickaxe(new Item.Properties()));
    public static final RegistryObject<Item> NETHER_STAR_AXE = ITEMS.register("nether_star_axe",
            () -> new NetherStarAxe(new Item.Properties()));
    public static final RegistryObject<Item> NETHER_STAR_SHOVEL = ITEMS.register("nether_star_shovel",
            () -> new NetherStarShovel(new Item.Properties()));
    public static final RegistryObject<Item> NETHER_STAR_HOE = ITEMS.register("nether_star_hoe",
            () -> new NetherStarHoe(new Item.Properties()));
} 