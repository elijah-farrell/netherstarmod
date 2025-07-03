package com.example.netherstararsenal;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec COMMON_SPEC;
    
    // Tool ability configurations
    public static final ForgeConfigSpec.BooleanValue ENABLE_WITHER_EFFECT;
    public static final ForgeConfigSpec.BooleanValue ENABLE_FIRE_STEAL;
    public static final ForgeConfigSpec.BooleanValue ENABLE_AUTO_SMELTING;
    public static final ForgeConfigSpec.BooleanValue ENABLE_TREECAPITATOR;
    public static final ForgeConfigSpec.BooleanValue ENABLE_3X3_DIGGING;
    public static final ForgeConfigSpec.BooleanValue ENABLE_3X3_TILLING;
    public static final ForgeConfigSpec.BooleanValue ENABLE_PROJECTILE_ABILITY;
    
    static {
        COMMON_BUILDER.comment("Nether Star Arsenal Configuration");
        COMMON_BUILDER.push("Tool Abilities");
        
        ENABLE_WITHER_EFFECT = COMMON_BUILDER
            .comment("Enable wither effect on Nether Star Sword hits")
            .define("enableWitherEffect", false);
            
        ENABLE_FIRE_STEAL = COMMON_BUILDER
            .comment("Enable fire steal ability on Nether Star Sword hits")
            .define("enableFireSteal", false);
            
        ENABLE_AUTO_SMELTING = COMMON_BUILDER
            .comment("Enable auto-smelting on Nether Star Pickaxe")
            .define("enableAutoSmelting", false);
            
        ENABLE_TREECAPITATOR = COMMON_BUILDER
            .comment("Enable treecapitator on Nether Star Axe")
            .define("enableTreecapitator", false);
            
        ENABLE_3X3_DIGGING = COMMON_BUILDER
            .comment("Enable 3x3 digging on Nether Star Shovel (when sneaking)")
            .define("enable3x3Digging", false);
            
        ENABLE_3X3_TILLING = COMMON_BUILDER
            .comment("Enable 3x3 tilling on Nether Star Hoe (when sneaking)")
            .define("enable3x3Tilling", false);
            
        ENABLE_PROJECTILE_ABILITY = COMMON_BUILDER
            .comment("Enable projectile ability on tools")
            .define("enableProjectileAbility", false);
        
        COMMON_BUILDER.pop();
        
        COMMON_SPEC = COMMON_BUILDER.build();
    }
} 