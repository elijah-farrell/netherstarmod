package com.example.netherstararsenal.items;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.item.Tiers;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Nether Star Pickaxe with auto-smelting capability
 * Automatically smelts ores when mined
 * 
 * @author CS Graduate Portfolio
 */
public class NetherStarPickaxe extends PickaxeItem {
    
    /**
     * Custom tier for Nether Star tools
     * Provides high mining speed and durability
     */
    private static final Tier NETHER_STAR_TIER = new Tier() {
        @Override
        public int getUses() {
            return 2500; // High durability
        }
        
        @Override
        public float getSpeed() {
            return 12.0f; // Very fast mining speed
        }
        
        @Override
        public float getAttackDamageBonus() {
            return 6.0f; // Good damage
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
     * Constructor for Nether Star Pickaxe
     */
    public NetherStarPickaxe(Properties properties) {
        super(Tiers.NETHERITE, 5, -2.8F, properties);
    }
    
    @Override
    public void appendHoverText(@Nonnull ItemStack stack, Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.netherstararsenal.pickaxe_effect"));
        tooltip.add(Component.translatable("tooltip.netherstararsenal.piglins_neutral"));
    }
    
    /**
     * Override to add auto-smelting capability
     * Automatically smelts ores when mined
     */
    @Override
    public boolean mineBlock(@Nonnull ItemStack stack, Level level, @Nonnull BlockState state, @Nonnull BlockPos pos, @Nonnull LivingEntity miner) {
        // Check if the mined block is an ore that can be smelted
        if (isSmeltableOre(state.getBlock())) {
            // Get the smelted result
            Block smeltedBlock = getSmeltedResult(state.getBlock());
            if (smeltedBlock != null) {
                // Replace the block with smelted version
                level.setBlock(pos, smeltedBlock.defaultBlockState(), 3);
                
                // Drop the smelted item - simplified approach
                Block.dropResources(smeltedBlock.defaultBlockState(), level, pos, null, miner, stack);
                
                // Remove the original block drops
                return true;
            }
        }
        
        return super.mineBlock(stack, level, state, pos, miner);
    }
    
    /**
     * Checks if a block is an ore that can be smelted
     * 
     * @param block The block to check
     * @return true if the block can be smelted
     */
    private boolean isSmeltableOre(Block block) {
        return block == Blocks.IRON_ORE || 
               block == Blocks.GOLD_ORE || 
               block == Blocks.COPPER_ORE || 
               block == Blocks.DEEPSLATE_IRON_ORE || 
               block == Blocks.DEEPSLATE_GOLD_ORE || 
               block == Blocks.DEEPSLATE_COPPER_ORE;
    }
    
    /**
     * Gets the smelted result for a given ore block
     * 
     * @param oreBlock The ore block to smelt
     * @return The smelted block, or null if not smeltable
     */
    private Block getSmeltedResult(Block oreBlock) {
        if (oreBlock == Blocks.IRON_ORE || oreBlock == Blocks.DEEPSLATE_IRON_ORE) {
            return Blocks.IRON_BLOCK;
        } else if (oreBlock == Blocks.GOLD_ORE || oreBlock == Blocks.DEEPSLATE_GOLD_ORE) {
            return Blocks.GOLD_BLOCK;
        } else if (oreBlock == Blocks.COPPER_ORE || oreBlock == Blocks.DEEPSLATE_COPPER_ORE) {
            return Blocks.COPPER_BLOCK;
        }
        return null;
    }
    
    @Override
    public boolean isValidRepairItem(@Nonnull ItemStack toRepair, @Nonnull ItemStack repair) {
        return repair.getItem() == Items.NETHER_STAR;
    }
    
    @Override
    public boolean makesPiglinsNeutral(@Nonnull ItemStack stack, @Nonnull LivingEntity wearer) {
        return true; // Nether Star pickaxe makes piglins neutral
    }
} 