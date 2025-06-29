package com.example.netherstararsenal.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.Tiers;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Nether Star Axe with treecapitator functionality
 * Breaks entire trees when chopping the bottom log
 * 
 * @author CS Graduate Portfolio
 */
public class NetherStarAxe extends AxeItem {
    
    /**
     * Custom tier for Nether Star tools
     * Provides high chopping speed and durability
     */
    private static final Tier NETHER_STAR_TIER = new Tier() {
        @Override
        public int getUses() {
            return 2500; // High durability
        }
        
        @Override
        public float getSpeed() {
            return 12.0f; // Very fast chopping speed
        }
        
        @Override
        public float getAttackDamageBonus() {
            return 10.0f; // High damage
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
     * Constructor for Nether Star Axe
     */
    public NetherStarAxe(Properties properties) {
        super(Tiers.NETHERITE, 10, -3.0F, properties);
    }
    
    @Override
    public void appendHoverText(@Nonnull ItemStack stack, Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.netherstararsenal.axe_effect"));
        tooltip.add(Component.translatable("tooltip.netherstararsenal.piglins_neutral"));
    }
    
    /**
     * Override to add treecapitator functionality
     * Breaks entire trees when chopping the bottom log
     */
    @Override
    public boolean mineBlock(@Nonnull ItemStack stack, Level level, @Nonnull BlockState state, @Nonnull BlockPos pos, @Nonnull LivingEntity miner) {
        // Check if the broken block is a log
        if (isLog(state.getBlock())) {
            // Find and break the entire tree
            breakEntireTree(level, pos, miner, stack);
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
     * @param miner The entity mining the tree
     * @param tool The tool being used
     */
    private void breakEntireTree(Level level, BlockPos startPos, LivingEntity miner, ItemStack tool) {
        List<BlockPos> treeBlocks = new ArrayList<>();
        findTreeBlocks(level, startPos, treeBlocks, new ArrayList<>());
        
        // Break all tree blocks
        for (BlockPos pos : treeBlocks) {
            BlockState state = level.getBlockState(pos);
            if (state.getBlock() != Blocks.AIR) {
                // Drop the block
                Block.dropResources(state, level, pos, null, miner, tool);
                // Remove the block
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            }
        }
    }
    
    /**
     * Recursively finds all blocks that are part of the tree
     * 
     * @param level The world level
     * @param pos The current position to check
     * @param treeBlocks List to store found tree blocks
     * @param checkedPositions List of already checked positions to prevent infinite loops
     */
    private void findTreeBlocks(Level level, BlockPos pos, List<BlockPos> treeBlocks, List<BlockPos> checkedPositions) {
        if (checkedPositions.contains(pos)) {
            return; // Already checked this position
        }
        
        checkedPositions.add(pos);
        BlockState state = level.getBlockState(pos);
        
        // Check if this block is part of the tree
        if (isLog(state.getBlock()) || isLeaves(state.getBlock())) {
            treeBlocks.add(pos);
            
            // Check adjacent blocks (including diagonals for leaves)
            for (Direction direction : Direction.values()) {
                BlockPos adjacentPos = pos.relative(direction);
                findTreeBlocks(level, adjacentPos, treeBlocks, checkedPositions);
            }
            
            // For logs, also check blocks above
            BlockPos abovePos = pos.above();
            findTreeBlocks(level, abovePos, treeBlocks, checkedPositions);
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
    
    @Override
    public boolean makesPiglinsNeutral(@Nonnull ItemStack stack, @Nonnull LivingEntity wearer) {
        return true; // Nether Star axe makes piglins neutral
    }
} 