package com.swissokyo.swissokyo_gp;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    public static final ModConfigSpec SPEC;
    public static final Config INSTANCE;
    public final ModConfigSpec.DoubleValue boatTargetSpeedBps;
    public final ModConfigSpec.DoubleValue boatMaxSpeedLimitBps;
    public final ModConfigSpec.DoubleValue boatAccelerationScale;
    public final ModConfigSpec.DoubleValue boatInertia;
    public final ModConfigSpec.BooleanValue boatJumpEnabled;
    public final ModConfigSpec.DoubleValue boatJumpPower;
    public final ModConfigSpec.IntValue boatJumpCooldown;
    public final ModConfigSpec.DoubleValue hudSpeedMultiplier;
    public final ModConfigSpec.BooleanValue engineSoundEnabled;

    Config(ModConfigSpec.Builder builder) {
        builder.push("Physics");
        this.boatTargetSpeedBps = builder.comment("Target speed in Blocks Per Second (BPS) when moving forward").defineInRange("targetSpeedBps", (double)20.0F, (double)0.0F, (double)2000.0F);
        this.boatMaxSpeedLimitBps = builder.comment("Maximum absolute speed limit (BPS) and HUD bar scale").defineInRange("maxSpeedLimitBps", (double)40.0F, (double)0.0F, (double)2000.0F);
        this.boatAccelerationScale = builder.comment("Physic smoothing factor (0.01 - 1.0). Lower = slower acceleration.").defineInRange("accelerationScale", 0.05, 0.001, (double)1.0F);
        this.boatInertia = builder.comment("Inertia (Friction) when not accelerating.").defineInRange("inertia", 0.98, (double)0.0F, (double)1.0F);
        this.boatJumpEnabled = builder.comment("Enable or disable boat jumping").define("jumpEnabled", true);
        this.boatJumpPower = builder.comment("Jump power in blocks (1.0 - 100.0)").defineInRange("jumpPower", (double)1.0F, (double)1.0F, (double)100.0F);
        this.boatJumpCooldown = builder.comment("Cooldown between jumps in ticks (20 ticks = 1 second)").defineInRange("jumpCooldown", 40, 0, 1000);
        builder.pop();
        builder.push("HUD");
        this.hudSpeedMultiplier = builder.comment("Multiplier for the speedometer display value").defineInRange("speedMultiplier", 3.6, (double)0.0F, (double)1000.0F);
        builder.pop();
        builder.push("Sound");
        this.engineSoundEnabled = builder.comment("Enable or disable boat engine sound effects").define("engineSoundEnabled", true);
        builder.pop();
    }

    static {
        Pair<Config, ModConfigSpec> pair = (new ModConfigSpec.Builder()).configure(Config::new);
        SPEC = (ModConfigSpec)pair.getRight();
        INSTANCE = (Config)pair.getLeft();
    }
}
