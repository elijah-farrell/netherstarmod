package com.example.netherstararsenal.items;

import com.example.netherstararsenal.NetherStarArsenal;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.UUID;

/**
 * Base class for Nether Star armor items with special abilities
 * Provides creative-like flight and passive effects when full set is equipped
 * 
 * @author CS Graduate Portfolio
 */
public class NetherStarArmorItem extends ArmorItem {
    
    /**
     * Enum defining armor piece types for easier management
     */
    public enum ArmorType {
        HELMET(Type.HELMET),
        CHESTPLATE(Type.CHESTPLATE),
        LEGGINGS(Type.LEGGINGS),
        BOOTS(Type.BOOTS);
        
        private final Type type;
        
        ArmorType(Type type) {
            this.type = type;
        }
        
        public Type getType() {
            return type;
        }
    }
    
    /**
     * Custom armor material for Nether Star armor
     * Provides high durability and enchantability
     */
    private static final ArmorMaterial NETHER_STAR_MATERIAL = new ArmorMaterial() {
        @Override
        public int getDurabilityForType(Type type) {
            return switch (type) {
                case HELMET -> 407;
                case CHESTPLATE -> 592;
                case LEGGINGS -> 555;
                case BOOTS -> 481;
            };
        }
        
        @Override
        public int getDefenseForType(Type type) {
            return switch (type) {
                case HELMET -> 4;
                case CHESTPLATE -> 9;
                case LEGGINGS -> 7;
                case BOOTS -> 4;
            };
        }
        
        @Override
        public int getEnchantmentValue() {
            return 25;
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
            return 3.0F;
        }
        
        @Override
        public float getKnockbackResistance() {
            return 0.1F;
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
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        
        // Add individual armor piece effects
        switch (this.getType()) {
            case HELMET -> tooltip.add(Component.translatable("tooltip.netherstararsenal.helmet_effect"));
            case CHESTPLATE -> tooltip.add(Component.translatable("tooltip.netherstararsenal.chestplate_effect"));
            case LEGGINGS -> tooltip.add(Component.translatable("tooltip.netherstararsenal.leggings_effect"));
            case BOOTS -> tooltip.add(Component.translatable("tooltip.netherstararsenal.boots_effect"));
        }
        
        // Add full set bonus hint
        tooltip.add(Component.translatable("tooltip.netherstararsenal.armor_set_bonus"));
        tooltip.add(Component.translatable("tooltip.netherstararsenal.piglins_neutral"));
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
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == Items.NETHER_STAR;
    }
    
    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, net.minecraft.world.entity.LivingEntity wearer) {
        return true; // Nether Star armor makes piglins neutral
    }
} 