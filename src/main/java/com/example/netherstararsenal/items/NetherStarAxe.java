package com.example.netherstararsenal.items;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Nether Star Axe with treecapitator functionality
 * Breaks entire trees when chopping the bottom log
 * 
 * @author Elijah Farrell
 */
public class NetherStarAxe extends AxeItem {
    
    /**
     * Constructor for Nether Star Axe
     */
    public NetherStarAxe(Properties properties) {
        super(Tiers.NETHERITE, 10, -3.0F, properties);
    }
    
    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.netherstararsenal.axe_effects"));
    }
    
    /**
     * Override to add treecapitator functionality
     * Breaks entire trees when chopping the bottom log
     */
    @Override
    public boolean mineBlock(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull BlockState state, @Nonnull BlockPos pos, @Nonnull LivingEntity miner) {
        // Check if the broken block is a log and miner is a player
        if (isLog(state.getBlock()) && miner instanceof Player player) {
            // Break the entire tree
            breakEntireTree(level, pos, player, stack);
        }
        
        return super.mineBlock(stack, level, state, pos, miner);
    }
    
    /**
     * Checks if a block is a log
     * 
     * @param block The block to check
     * @return true if the block is a log
     */
    private boolean isLog(Block block) {
        return block == Blocks.OAK_LOG || 
               block == Blocks.BIRCH_LOG || 
               block == Blocks.SPRUCE_LOG || 
               block == Blocks.JUNGLE_LOG || 
               block == Blocks.ACACIA_LOG || 
               block == Blocks.DARK_OAK_LOG ||
               block == Blocks.MANGROVE_LOG ||
               block == Blocks.CHERRY_LOG;
    }
    
    /**
     * Breaks an entire tree starting from the given position
     * 
     * @param level The world level
     * @param startPos The starting position (bottom log)
     * @param player The player chopping
     * @param tool The tool being used
     */
    private void breakEntireTree(Level level, BlockPos startPos, Player player, ItemStack tool) {
        List<BlockPos> treeBlocks = new ArrayList<>();
        findTreeBlocks(level, startPos, treeBlocks, new HashSet<>());
        
        // Break all tree blocks
        for (BlockPos pos : treeBlocks) {
            BlockState state = level.getBlockState(pos);
            if (state.getBlock() != Blocks.AIR && player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) <= 25.0) {
                // Drop the block
                Block.dropResources(state, level, pos, null, player, tool);
                // Remove the block
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            }
        }
    }
    
    /**
     * Recursively finds all blocks that are part of the tree
     * 
     * @param level The world level
     * @param pos The current position
     * @param treeBlocks List to store tree block positions
     * @param visited Set of already visited positions
     */
    private void findTreeBlocks(Level level, BlockPos pos, List<BlockPos> treeBlocks, Set<BlockPos> visited) {
        if (visited.contains(pos)) return;
        visited.add(pos);
        
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();
        
        // Check if this is a log or leaves
        if (isLog(block) || isLeaves(block)) {
            treeBlocks.add(pos);
            
            // Check adjacent blocks (including diagonals)
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        if (x == 0 && y == 0 && z == 0) continue;
                        
                        BlockPos newPos = pos.offset(x, y, z);
                        BlockState newState = level.getBlockState(newPos);
                        Block newBlock = newState.getBlock();
                        
                        if ((isLog(newBlock) || isLeaves(newBlock)) && !visited.contains(newPos)) {
                            findTreeBlocks(level, newPos, treeBlocks, visited);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Checks if a block is leaves
     * 
     * @param block The block to check
     * @return true if the block is leaves
     */
    private boolean isLeaves(Block block) {
        return block == Blocks.OAK_LEAVES || 
               block == Blocks.BIRCH_LEAVES || 
               block == Blocks.SPRUCE_LEAVES || 
               block == Blocks.JUNGLE_LEAVES || 
               block == Blocks.ACACIA_LEAVES || 
               block == Blocks.DARK_OAK_LEAVES ||
               block == Blocks.MANGROVE_LEAVES ||
               block == Blocks.CHERRY_LEAVES;
    }
    
    @Override
    public boolean isValidRepairItem(@Nonnull ItemStack toRepair, @Nonnull ItemStack repair) {
        return repair.getItem() == Items.NETHER_STAR;
    }
} 