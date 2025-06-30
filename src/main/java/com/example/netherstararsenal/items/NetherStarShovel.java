package com.example.netherstararsenal.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Nether Star Shovel with 3x3 digging capability
 * Breaks a 3x3 area when digging
 * 
 * @author Elijah Farrell
 */
public class NetherStarShovel extends ShovelItem {
    
    /**
     * Constructor for Nether Star Shovel
     */
    public NetherStarShovel(Properties properties) {
        super(Tiers.NETHERITE, 6.5F, -3.0F, properties);
    }
    
    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.netherstararsenal.shovel_effects"));
    }
    
    /**
     * Override to add 3x3 digging capability
     * Breaks a 3x3 area when digging and shift is held
     */
    @Override
    public boolean mineBlock(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull BlockState state, @Nonnull BlockPos pos, @Nonnull LivingEntity miner) {
        // Check if the broken block is diggable and shift is held
        if (isDiggable(state.getBlock()) && miner instanceof Player player && player.isShiftKeyDown()) {
            // Get the direction the player is facing
            Direction facing = player.getDirection();
            
            // Break a 3x3 area in the direction the player is facing
            break3x3Area(level, pos, facing, player, stack);
        }
        
        return super.mineBlock(stack, level, state, pos, miner);
    }
    
    /**
     * Checks if a block is diggable with a shovel
     * 
     * @param block The block to check
     * @return true if the block is diggable
     */
    private boolean isDiggable(Block block) {
        return block == Blocks.DIRT || 
               block == Blocks.GRASS_BLOCK || 
               block == Blocks.COARSE_DIRT || 
               block == Blocks.ROOTED_DIRT || 
               block == Blocks.MUD || 
               block == Blocks.SAND || 
               block == Blocks.RED_SAND || 
               block == Blocks.GRAVEL || 
               block == Blocks.SOUL_SAND || 
               block == Blocks.SOUL_SOIL || 
               block == Blocks.CLAY || 
               block == Blocks.SNOW || 
               block == Blocks.SNOW_BLOCK;
    }
    
    /**
     * Breaks a 3x3 area in the specified direction
     * 
     * @param level The world level
     * @param centerPos The center position
     * @param facing The direction the player is facing
     * @param player The player digging
     * @param tool The tool being used
     */
    private void break3x3Area(Level level, BlockPos centerPos, Direction facing, Player player, ItemStack tool) {
        // Calculate the 3x3 area based on the facing direction
        BlockPos[] positions = get3x3Positions(centerPos, facing);
        
        for (BlockPos pos : positions) {
            BlockState state = level.getBlockState(pos);
            
            // Check if the block is diggable and within reach
            if (isDiggable(state.getBlock()) && player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) <= 25.0) {
                // Drop the block
                Block.dropResources(state, level, pos, null, player, tool);
                // Remove the block
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            }
        }
    }
    
    /**
     * Gets the 3x3 positions around the center position
     * 
     * @param centerPos The center position
     * @param facing The direction the player is facing
     * @return Array of 9 positions forming a 3x3 area
     */
    private BlockPos[] get3x3Positions(BlockPos centerPos, Direction facing) {
        BlockPos[] positions = new BlockPos[9];
        
        // Calculate the relative positions based on facing direction
        Direction left = facing.getClockWise();
        
        int index = 0;
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                BlockPos offset;
                if (facing == Direction.NORTH || facing == Direction.SOUTH) {
                    offset = centerPos.offset(left.getStepX() * x, 0, facing.getStepZ() * z);
                } else {
                    offset = centerPos.offset(facing.getStepX() * x, 0, left.getStepZ() * z);
                }
                positions[index++] = offset;
            }
        }
        
        return positions;
    }
    
    @Override
    public boolean isValidRepairItem(@Nonnull ItemStack toRepair, @Nonnull ItemStack repair) {
        return repair.getItem() == Items.NETHER_STAR;
    }
} 