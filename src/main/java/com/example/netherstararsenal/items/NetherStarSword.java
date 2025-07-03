package com.example.netherstararsenal.items;

import com.example.netherstararsenal.Config;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Nether Star Sword with balanced abilities
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
        
        if (Config.ENABLE_WITHER_EFFECT.get()) {
            tooltip.add(Component.translatable("tooltip.netherstararsenal.sword_effects.wither").withStyle(style -> style.withColor(0xFF5555)));
        }
        if (Config.ENABLE_FIRE_STEAL.get()) {
            tooltip.add(Component.translatable("tooltip.netherstararsenal.sword_effects.fire_steal").withStyle(style -> style.withColor(0xFF5555)));
        }
        if (Config.ENABLE_PROJECTILE_ABILITY.get()) {
            tooltip.add(Component.translatable("tooltip.netherstararsenal.sword_effects.projectile").withStyle(style -> style.withColor(0xFF5555)));
        }
    }
    
    /**
     * Override to add special effects when hitting entities
     * Applies wither effect and fire steal (brief fire + fire resistance)
     */
    @Override
    public boolean hurtEnemy(@Nonnull ItemStack stack, @Nonnull LivingEntity target, @Nonnull LivingEntity attacker) {
        // Apply wither effect to the target
        if (Config.ENABLE_WITHER_EFFECT.get()) {
            target.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 0)); // 5 seconds, level 1
        }
        
        // Fire steal effect
        if (Config.ENABLE_FIRE_STEAL.get() && attacker instanceof Player player) {
            // Set target on fire briefly (1 second)
            target.setSecondsOnFire(1);
            
            // Give attacker 5 seconds of fire resistance
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 100, 0)); // 5 seconds
        }
        
        return super.hurtEnemy(stack, target, attacker);
    }
    
    @Override
    public boolean isValidRepairItem(@Nonnull ItemStack toRepair, @Nonnull ItemStack repair) {
        return repair.getItem() == Items.NETHER_STAR;
    }
}

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
class NetherStarSwordProjectileHandler {
    
    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickItem event) {
        if (!Config.ENABLE_PROJECTILE_ABILITY.get()) return;
        
        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();
        
        if (!(stack.getItem() instanceof NetherStarSword)) return;
        
        Level level = player.level();
        
        // Create and shoot an arrow
        Arrow arrow = new Arrow(level, player);
        arrow.setBaseDamage(arrow.getBaseDamage() * 1.5); // 50% more damage
        
        Vec3 lookAngle = player.getLookAngle();
        arrow.shoot(lookAngle.x, lookAngle.y, lookAngle.z, 2.0F, 1.0F);
        
        level.addFreshEntity(arrow);
        
        // Don't consume the item, just use it
        event.setCanceled(true);
    }
} 