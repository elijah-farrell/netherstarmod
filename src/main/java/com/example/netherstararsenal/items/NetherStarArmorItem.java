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
 * Nether Star armor items for progression mod
 * Provides balanced protection and moderate bonuses
 * 
 * @author Elijah Farrell
 */
public class NetherStarArmorItem extends ArmorItem {
    
    /**
     * Custom armor material for Nether Star armor
     * Provides good protection for mid-game progression
     */
    private static final ArmorMaterial NETHER_STAR_MATERIAL = new ArmorMaterial() {
        @Override
        public int getDurabilityForType(@Nonnull Type type) {
            return switch (type) {
                case HELMET -> 611; // 1.5x Netherite helmet (407 * 1.5)
                case CHESTPLATE -> 888; // 1.5x Netherite chestplate (592 * 1.5)
                case LEGGINGS -> 833; // 1.5x Netherite leggings (555 * 1.5)
                case BOOTS -> 488; // 1.5x Netherite boots (325 * 1.5)
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
            return 22; 
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
            return 3.5f; // Moderate toughness
        }
        
        @Override
        public float getKnockbackResistance() {
            return 0.15f; // Slight knockback resistance
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