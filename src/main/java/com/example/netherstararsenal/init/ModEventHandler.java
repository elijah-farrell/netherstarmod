package com.example.netherstararsenal.init;

import com.example.netherstararsenal.Config;
import com.example.netherstararsenal.items.NetherStarSword;
import com.example.netherstararsenal.items.NetherStarHoe;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraft.core.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEventHandler {
    
    // Sword: Cook drops if killed by Nether Star Sword
    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (!Config.ENABLE_AUTO_SMELTING.get()) return;
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
        if (!Config.ENABLE_FIRE_STEAL.get()) return;
        if (!(event.getSource().getEntity() instanceof Player player)) return;
        ItemStack mainHand = player.getMainHandItem();
        if (!(mainHand.getItem() instanceof NetherStarSword)) return;
        
        // Give attacker 15 seconds of fire resistance on kill
        player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 300, 0)); // 15 seconds
    }

    /*// Armor: Full set bonus effects and individual piece effects
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event) {
        Player player = event.player;
        
        // Individual armor piece effects
        ItemStack helmet = player.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.HEAD);
        ItemStack chestplate = player.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.CHEST);
        ItemStack leggings = player.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.LEGS);
        ItemStack boots = player.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.FEET);
        
        
    }*/
    
    // Hoe: 3x3 tilling (only when sneaking)
    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
        if (!Config.ENABLE_3X3_TILLING.get()) return;
        
        Player player = event.getEntity();
        ItemStack mainHand = player.getMainHandItem();
        
        if (!(mainHand.getItem() instanceof NetherStarHoe)) return;
        
        Level level = player.level();
        BlockPos clickedPos = event.getPos();
        
        // Check if the clicked block can be tilled
        if (level.getBlockState(clickedPos).is(net.minecraft.world.level.block.Blocks.DIRT) || 
            level.getBlockState(clickedPos).is(net.minecraft.world.level.block.Blocks.GRASS_BLOCK)) {
            
            if (player.isCrouching()) {
                // Till a 3x3 area around the clicked position when sneaking
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        BlockPos pos = clickedPos.offset(x, 0, z);
                        if (level.getBlockState(pos).is(net.minecraft.world.level.block.Blocks.DIRT) || 
                            level.getBlockState(pos).is(net.minecraft.world.level.block.Blocks.GRASS_BLOCK)) {
                            // Use the hoe to till the block
                            mainHand.getItem().useOn(new net.minecraft.world.item.context.UseOnContext(player, player.getUsedItemHand(), 
                                new net.minecraft.world.phys.BlockHitResult(net.minecraft.world.phys.Vec3.ZERO, net.minecraft.core.Direction.UP, pos, false)));
                        }
                    }
                }
            }
        }
    }
} 