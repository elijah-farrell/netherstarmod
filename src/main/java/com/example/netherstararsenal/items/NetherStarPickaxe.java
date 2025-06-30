package com.example.netherstararsenal.items;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Nether Star Pickaxe with auto-smelting capability
 * Automatically smelts blocks when mined
 * 
 * @author Elijah Farrell
 */
public class NetherStarPickaxe extends PickaxeItem {
    
    /**
     * Constructor for Nether Star Pickaxe
     */
    public NetherStarPickaxe(Properties properties) {
        super(Tiers.NETHERITE, 6, -2.8F, properties);
    }
    
    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.netherstararsenal.pickaxe_effects"));
    }
    
    /**
     * Override to add auto-smelting capability
     * Automatically smelts blocks when mined
     */
    @Override
    public boolean mineBlock(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull BlockState state, @Nonnull BlockPos pos, @Nonnull LivingEntity miner) {
        // Check if the broken block can be smelted
        if (canSmelt(state.getBlock()) && miner instanceof Player) {
            // Get the smelted result
            ItemStack smeltedResult = getSmeltedResult(state.getBlock());
            if (!smeltedResult.isEmpty()) {
                // Drop the smelted item instead of the original block
                Block.popResource(level, pos, smeltedResult);
                // Don't drop the original block
                return true;
            }
        }
        
        return super.mineBlock(stack, level, state, pos, miner);
    }
    
    /**
     * Checks if a block can be smelted
     * 
     * @param block The block to check
     * @return true if the block can be smelted
     */
    private boolean canSmelt(Block block) {
        return block == Blocks.IRON_ORE || 
               block == Blocks.GOLD_ORE || 
               block == Blocks.COPPER_ORE || 
               block == Blocks.SAND || 
               block == Blocks.SANDSTONE ||
               block == Blocks.CLAY ||
               block == Blocks.CACTUS ||
               block == Blocks.KELP ||
               block == Blocks.WET_SPONGE ||
               block == Blocks.SEA_PICKLE ||
               block == Blocks.BROWN_MUSHROOM_BLOCK ||
               block == Blocks.RED_MUSHROOM_BLOCK ||
               block == Blocks.MUSHROOM_STEM;
    }
    
    /**
     * Gets the smelted result for a block
     * 
     * @param block The block to smelt
     * @return The smelted item stack
     */
    private ItemStack getSmeltedResult(Block block) {
        if (block == Blocks.IRON_ORE) {
            return new ItemStack(Items.IRON_INGOT);
        } else if (block == Blocks.GOLD_ORE) {
            return new ItemStack(Items.GOLD_INGOT);
        } else if (block == Blocks.COPPER_ORE) {
            return new ItemStack(Items.COPPER_INGOT);
        } else if (block == Blocks.SAND) {
            return new ItemStack(Items.GLASS);
        } else if (block == Blocks.SANDSTONE) {
            return new ItemStack(Items.SMOOTH_SANDSTONE);
        } else if (block == Blocks.CLAY) {
            return new ItemStack(Items.TERRACOTTA);
        } else if (block == Blocks.CACTUS) {
            return new ItemStack(Items.GREEN_DYE);
        } else if (block == Blocks.KELP) {
            return new ItemStack(Items.DRIED_KELP);
        } else if (block == Blocks.WET_SPONGE) {
            return new ItemStack(Items.SPONGE);
        } else if (block == Blocks.SEA_PICKLE) {
            return new ItemStack(Items.LIME_DYE);
        } else if (block == Blocks.BROWN_MUSHROOM_BLOCK || 
                   block == Blocks.RED_MUSHROOM_BLOCK || 
                   block == Blocks.MUSHROOM_STEM) {
            return new ItemStack(Items.BROWN_DYE);
        }
        
        return ItemStack.EMPTY;
    }
    
    @Override
    public boolean isValidRepairItem(@Nonnull ItemStack toRepair, @Nonnull ItemStack repair) {
        return repair.getItem() == Items.NETHER_STAR;
    }
} 