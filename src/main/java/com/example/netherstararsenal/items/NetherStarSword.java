package com.example.netherstararsenal.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Nether Star Sword with special abilities
 * Applies wither and short fire effect, cooks anything it kills
 * 
 * @author CS Graduate Portfolio
 */
public class NetherStarSword extends SwordItem {
    
    /**
     * Custom tier for Nether Star tools
     * Provides high damage and durability
     */
    private static final Tier NETHER_STAR_TIER = new Tier() {
        @Override
        public int getUses() {
            return 2500; // High durability
        }
        
        @Override
        public float getSpeed() {
            return 12.0f; // Very fast attack speed
        }
        
        @Override
        public float getAttackDamageBonus() {
            return 8.0f; // High damage
        }
        
        @Override
        public int getLevel() {
            return 5; // Higher than netherite
        }
        
        @Override
        public int getEnchantmentValue() {
            return 25; // High enchantability
        }
        
        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(Items.NETHER_STAR);
        }
    };
    
    /**
     * Constructor for Nether Star Sword
     */
    public NetherStarSword(Properties properties) {
        super(Tiers.NETHERITE, 8, -2.4F, properties);
    }
    
    @Override
    public void appendHoverText(@Nonnull ItemStack stack, Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.netherstararsenal.sword_effect"));
        tooltip.add(Component.translatable("tooltip.netherstararsenal.piglins_neutral"));
    }
    
    /**
     * Override to add special effects when hitting entities
     * Applies wither and short fire effect
     */
    @Override
    public boolean hurtEnemy(@Nonnull ItemStack stack, @Nonnull LivingEntity target, @Nonnull LivingEntity attacker) {
        // Apply wither effect for 3 seconds (60 ticks)
        target.addEffect(new MobEffectInstance(MobEffects.WITHER, 60, 1));
        // Set target on fire for 1 second
        target.setSecondsOnFire(1);
        return super.hurtEnemy(stack, target, attacker);
    }
    
    @Override
    public boolean isValidRepairItem(@Nonnull ItemStack toRepair, @Nonnull ItemStack repair) {
        return repair.getItem() == Items.NETHER_STAR;
    }
    
    @Override
    public boolean makesPiglinsNeutral(@Nonnull ItemStack stack, @Nonnull LivingEntity wearer) {
        return true; // Nether Star sword makes piglins neutral
    }
} 