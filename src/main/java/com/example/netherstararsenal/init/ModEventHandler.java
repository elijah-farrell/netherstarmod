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

    // Armor: Full set bonus effects
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event) {
        Player player = event.player;
        if (NetherStarArmorItem.hasFullSet(player)) {
            // Grant flight ability, but do not force flying
            player.getAbilities().mayfly = true;
            // Regeneration
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 220, 0, true, false));
            // Fire Resistance
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 220, 0, true, false));
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

    // Bow: Flaming and explosive arrows
    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        if (!(event.getProjectile() instanceof Arrow arrow)) return;
        if (!(arrow.getOwner() instanceof Player player)) return;
        ItemStack bow = player.getMainHandItem();
        if (!(bow.getItem() instanceof NetherBow)) return;
        // Always flaming
        arrow.setSecondsOnFire(100);
        // Explode if sneaking
        if (player.isCrouching()) {
            HitResult hit = event.getRayTraceResult();
            arrow.level().explode(null, hit.getLocation().x, hit.getLocation().y, hit.getLocation().z, 2.0F, net.minecraft.world.level.Level.ExplosionInteraction.NONE);
            arrow.discard();
        }
    }
} 