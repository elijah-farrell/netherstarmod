package com.example.netherstararsenal.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Nether Bow with special arrow effects
 * Fires flaming arrows or explosive arrows based on arrow type
 * 
 * @author CS Graduate Portfolio
 */
public class NetherBow extends BowItem {
    
    /**
     * Constructor for Nether Bow
     */
    public NetherBow(Properties properties) {
        super(properties);
    }
    
    @Override
    public void appendHoverText(@Nonnull ItemStack stack, Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.netherstararsenal.bow_effect"));
        tooltip.add(Component.translatable("tooltip.netherstararsenal.piglins_neutral"));
    }
    
    /**
     * Override to add special effects when shooting
     */
    @Override
    public void releaseUsing(@Nonnull ItemStack stack, Level level, @Nonnull LivingEntity shooter, int timeLeft) {
        if (shooter instanceof Player player) {
            // Add fire resistance effect to shooter
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 60, 0));
            // Play special sound
            level.playSound(null, player.getX(), player.getY(), player.getZ(), 
                          SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 1.0f, 1.0f);
        }
        super.releaseUsing(stack, level, shooter, timeLeft);
    }
    
    @Override
    public boolean isValidRepairItem(@Nonnull ItemStack toRepair, @Nonnull ItemStack repair) {
        return repair.getItem() == Items.NETHER_STAR;
    }
    
    @Override
    public boolean makesPiglinsNeutral(@Nonnull ItemStack stack, @Nonnull LivingEntity wearer) {
        return true; // Nether Bow makes piglins neutral
    }
} 