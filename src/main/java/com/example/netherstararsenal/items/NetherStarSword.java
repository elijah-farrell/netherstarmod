package com.example.netherstararsenal.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Nether Star Sword with special abilities
 * Applies wither effect and fire steal (brief fire + fire resistance)
 * 
 * @author Elijah Farrell
 */
public class NetherStarSword extends SwordItem {
    
    /**
     * Constructor for Nether Star Sword
     */
    public NetherStarSword(Properties properties) {
        super(Tiers.NETHERITE, 8, -2.4F, properties);
    }
    
    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.netherstararsenal.sword_effects"));
    }
    
    /**
     * Override to add special effects when hitting entities
     * Applies wither effect and fire steal (brief fire + fire resistance)
     */
    @Override
    public boolean hurtEnemy(@Nonnull ItemStack stack, @Nonnull LivingEntity target, @Nonnull LivingEntity attacker) {
        // Apply wither effect to the target
        target.addEffect(new MobEffectInstance(MobEffects.WITHER, 200, 1)); // 10 seconds, level 2
        
        // Fire steal effect
        if (attacker instanceof Player player) {
            // Set target on fire briefly (1 second)
            target.setSecondsOnFire(1);
            
            // Give attacker 10 seconds of fire resistance
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 0)); // 10 seconds
        }
        
        return super.hurtEnemy(stack, target, attacker);
    }
    
    @Override
    public boolean isValidRepairItem(@Nonnull ItemStack toRepair, @Nonnull ItemStack repair) {
        return repair.getItem() == Items.NETHER_STAR;
    }
} 