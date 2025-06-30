package com.example.netherstararsenal.init;

import com.example.netherstararsenal.items.NetherStarArmorItem;
import com.example.netherstararsenal.items.NetherStarSword;
import com.example.netherstararsenal.items.NetherBow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEventHandler {
    
    // Sword: Cook drops if killed by Nether Star Sword
    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player)) return;
        ItemStack mainHand = player.getMainHandItem();
        if (!(mainHand.getItem() instanceof NetherStarSword)) return;
        Level level = player.level();
        RecipeManager recipeManager = level.getRecipeManager();
        for (var drop : event.getDrops()) {
            ItemStack stack = drop.getItem();
            // Try to smelt the drop
            var smelted = recipeManager.getRecipeFor(RecipeType.SMELTING, new net.minecraft.world.SimpleContainer(stack), level)
                .map(r -> r.getResultItem(level.registryAccess()))
                .orElse(ItemStack.EMPTY);
            if (!smelted.isEmpty()) {
                drop.setItem(smelted.copyWithCount(stack.getCount()));
            }
        }
    }
    
    // Sword: Fire resistance on kill
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player)) return;
        ItemStack mainHand = player.getMainHandItem();
        if (!(mainHand.getItem() instanceof NetherStarSword)) return;
        
        // Give attacker 30 seconds of fire resistance on kill
        player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 600, 0)); // 30 seconds
    }

    // Armor: Full set bonus effects and individual piece effects
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event) {
        Player player = event.player;
        
        // Individual armor piece effects
        ItemStack helmet = player.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.HEAD);
        ItemStack chestplate = player.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.CHEST);
        ItemStack leggings = player.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.LEGS);
        ItemStack boots = player.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.FEET);
        
        // Helmet: Night Vision
        if (helmet.getItem() instanceof NetherStarArmorItem) {
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 220, 0, true, false));
        }
        
        // Chestplate: Extra Health (2 hearts = 4 health points)
        if (chestplate.getItem() instanceof NetherStarArmorItem) {
            player.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 220, 1, true, false)); // Level 1 = +4 health
        }
        
        // Leggings: Speed Boost (20%)
        if (leggings.getItem() instanceof NetherStarArmorItem) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 220, 0, true, false));
        }
        
        // Boots: Fire Walking (fire resistance)
        if (boots.getItem() instanceof NetherStarArmorItem) {
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 220, 0, true, false));
        }
        
        // Full set bonus effects
        if (NetherStarArmorItem.hasFullSet(player)) {
            // Grant flight ability, but do not force flying
            player.getAbilities().mayfly = true;
            // Regeneration (infinite duration)
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 24000, 0, true, false)); // 20 minutes
            // Fire Resistance (infinite duration) - overrides boots effect
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 24000, 0, true, false)); // 20 minutes
            // Wither Immunity
            if (player.hasEffect(MobEffects.WITHER)) {
                player.removeEffect(MobEffects.WITHER);
            }
        } else {
            // Remove flight if not wearing full set
            if (player.getAbilities().mayfly && !player.isCreative()) {
                player.getAbilities().mayfly = false;
                player.getAbilities().flying = false;
            }
        }
    }

    // Bow: Explosive arrows
    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        if (!(event.getProjectile() instanceof Arrow arrow)) return;
        if (!(arrow.getOwner() instanceof Player player)) return;
        ItemStack bow = player.getMainHandItem();
        if (!(bow.getItem() instanceof NetherBow)) return;
        
        // Explode if sneaking
        if (player.isCrouching()) {
            HitResult hit = event.getRayTraceResult();
            arrow.level().explode(null, hit.getLocation().x, hit.getLocation().y, hit.getLocation().z, 2.0F, net.minecraft.world.level.Level.ExplosionInteraction.NONE);
            arrow.discard();
        }
    }
} 