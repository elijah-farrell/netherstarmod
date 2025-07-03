package com.example.netherstararsenal.items;

import com.example.netherstararsenal.Config;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Nether Star Hoe with 3x3 tilling functionality
 * Tills a 3x3 area when used
 * 
 * @author Elijah Farrell
 */
public class NetherStarHoe extends HoeItem {
    
    /**
     * Constructor for Nether Star Hoe
     */
    public NetherStarHoe(Properties properties) {
        super(Tiers.NETHERITE, -3, 0.0F, properties);
    }
    
    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        
        if (Config.ENABLE_3X3_TILLING.get()) {
            tooltip.add(Component.translatable("tooltip.netherstararsenal.hoe_effects").withStyle(style -> style.withColor(0xFF5555)));
        }
    }
    
    @Override
    public boolean isValidRepairItem(@Nonnull ItemStack toRepair, @Nonnull ItemStack repair) {
        return repair.getItem() == Items.NETHER_STAR;
    }
} 