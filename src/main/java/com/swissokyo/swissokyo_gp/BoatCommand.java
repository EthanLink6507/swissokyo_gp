package com.swissokyo.swissokyo_gp;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class BoatCommand {
   public BoatCommand() {
   }

   public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("boat").requires((source) -> source.hasPermission(2))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("set").then(Commands.literal("speed").then(Commands.argument("value", DoubleArgumentType.doubleArg((double)0.0F, (double)2000.0F)).executes((ctx) -> {
         double val = DoubleArgumentType.getDouble(ctx, "value");
         Config.INSTANCE.boatTargetSpeedBps.set(val);
         Config.INSTANCE.boatTargetSpeedBps.save();
         ((CommandSourceStack)ctx.getSource()).sendSuccess(() -> Component.literal("Cruise Speed (BPS) set to: " + val), true);
         return 1;
      })))).then(Commands.literal("max_speed").then(Commands.argument("value", DoubleArgumentType.doubleArg((double)0.0F, (double)2000.0F)).executes((ctx) -> {
         double val = DoubleArgumentType.getDouble(ctx, "value");
         Config.INSTANCE.boatMaxSpeedLimitBps.set(val);
         Config.INSTANCE.boatMaxSpeedLimitBps.save();
         ((CommandSourceStack)ctx.getSource()).sendSuccess(() -> Component.literal("Max Speed HUD Limit (BPS) set to: " + val), true);
         return 1;
      })))).then(Commands.literal("jump_power").then(Commands.argument("value", DoubleArgumentType.doubleArg((double)1.0F, (double)100.0F)).executes((ctx) -> {
         double val = DoubleArgumentType.getDouble(ctx, "value");
         Config.INSTANCE.boatJumpPower.set(val);
         Config.INSTANCE.boatJumpPower.save();
         ((CommandSourceStack)ctx.getSource()).sendSuccess(() -> Component.literal("Jump Height (Blocks) set to: " + val), true);
         return 1;
      })))).then(Commands.literal("acceleration").then(Commands.argument("factor", DoubleArgumentType.doubleArg(0.001, (double)1.0F)).executes((ctx) -> {
         double val = DoubleArgumentType.getDouble(ctx, "factor");
         Config.INSTANCE.boatAccelerationScale.set(val);
         Config.INSTANCE.boatAccelerationScale.save();
         ((CommandSourceStack)ctx.getSource()).sendSuccess(() -> Component.literal("Acceleration Smoothing Factor set to: " + val), true);
         return 1;
      })))).then(Commands.literal("inertia").then(Commands.argument("value", DoubleArgumentType.doubleArg((double)0.0F, (double)1.0F)).executes((ctx) -> {
         double val = DoubleArgumentType.getDouble(ctx, "value");
         Config.INSTANCE.boatInertia.set(val);
         Config.INSTANCE.boatInertia.save();
         ((CommandSourceStack)ctx.getSource()).sendSuccess(() -> Component.literal("Boat Inertia (Friction) set to: " + val), true);
         return 1;
      })))));
   }
}
