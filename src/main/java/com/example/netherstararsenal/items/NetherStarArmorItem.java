package com.example.netherstararsenal.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Base class for Nether Star armor items with special abilities
 * Provides creative-like flight and passive effects when full set is equipped
 * 
 * @author Elijah Farrell
 */
public class NetherStarArmorItem extends ArmorItem {
    
    /**
     * Custom armor material for Nether Star armor
     * Provides high protection and special abilities
     */
    private static final ArmorMaterial NETHER_STAR_MATERIAL = new ArmorMaterial() {
        @Override
        public int getDurabilityForType(@Nonnull Type type) {
            return switch (type) {
                case HELMET -> 500;
                case CHESTPLATE -> 800;
                case LEGGINGS -> 600;
                case BOOTS -> 400;
            };
        }
        
        @Override
        public int getDefenseForType(@Nonnull Type type) {
            return switch (type) {
                case HELMET -> 4;
                case CHESTPLATE -> 9;
                case LEGGINGS -> 7;
                case BOOTS -> 4;
            };
        }
        
        @Override
        public int getEnchantmentValue() {
            return 25; // High enchantability
        }
        
        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ARMOR_EQUIP_NETHERITE;
        }
        
        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(Items.NETHER_STAR);
        }
        
        @Override
        public String getName() {
            return "netherstararsenal:nether_star";
        }
        
        @Override
        public float getToughness() {
            return 3.0f; // High toughness
        }
        
        @Override
        public float getKnockbackResistance() {
            return 0.1f; // Some knockback resistance
        }
    };
    
    /**
     * Constructor for Nether Star armor items
     * 
     * @param type The type of armor piece to create
     * @param properties The properties of the armor item
     */
    public NetherStarArmorItem(Type type, Item.Properties properties) {
        super(NETHER_STAR_MATERIAL, type, properties);
    }
    
    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        
        // Show individual armor piece effects
        switch (this.getType()) {
            case HELMET -> tooltip.add(Component.translatable("tooltip.netherstararsenal.helmet_effects"));
            case CHESTPLATE -> tooltip.add(Component.translatable("tooltip.netherstararsenal.chestplate_effects"));
            case LEGGINGS -> tooltip.add(Component.translatable("tooltip.netherstararsenal.leggings_effects"));
            case BOOTS -> tooltip.add(Component.translatable("tooltip.netherstararsenal.boots_effects"));
        }
        
        // Add full set bonus
        tooltip.add(Component.translatable("tooltip.netherstararsenal.armor_set_bonus"));
    }
    
    /**
     * Checks if the player has the full Nether Star armor set equipped
     * This is used to activate special abilities
     * 
     * @param player The player to check
     * @return true if full set is equipped, false otherwise
     */
    public static boolean hasFullSet(net.minecraft.world.entity.player.Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof NetherStarArmorItem &&
               player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof NetherStarArmorItem &&
               player.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof NetherStarArmorItem &&
               player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof NetherStarArmorItem;
    }
    
    @Override
    public boolean isValidRepairItem(@Nonnull ItemStack stack, @Nonnull ItemStack repair) {
        return repair.getItem() == Items.NETHER_STAR;
    }
    
    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, net.minecraft.world.entity.LivingEntity wearer) {
        return true; // Nether Star armor makes piglins neutral
    }
} 