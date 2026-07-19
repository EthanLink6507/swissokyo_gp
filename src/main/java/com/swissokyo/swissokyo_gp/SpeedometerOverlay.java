package com.swissokyo.swissokyo_gp;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;

public class SpeedometerOverlay implements LayeredDraw.Layer {
    public static final SpeedometerOverlay INSTANCE = new SpeedometerOverlay();

    public SpeedometerOverlay() {
    }

    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            Entity var5 = mc.player.getVehicle();
            if (var5 instanceof Boat) {
                Boat boat = (Boat)var5;
                double currentBps = boat.getDeltaMovement().horizontalDistance() * (double)20.0F;
                double multiplier = (Double)Config.INSTANCE.hudSpeedMultiplier.get();
                int displayValue = (int)(currentBps * multiplier);
                String unit = Math.abs(multiplier - 3.6) < 0.01 ? " km/h" : " pts";
                String text = "Speed: " + displayValue + unit;
                int color = 65280;
                if (displayValue > 100) {
                color = 16776960;
            }

            if (displayValue > 200) {
                color = 16711680;
            }

            Font font = mc.font;
            int screenWidth = mc.getWindow().getGuiScaledWidth();
            int screenHeight = mc.getWindow().getGuiScaledHeight();
            int x = (screenWidth - font.width(text)) / 2;
            int y = screenHeight - 60;
            guiGraphics.drawString(font, text, x, y, color, true);
            int barWidth = 100;
            double maxBpsLimit = (Double)Config.INSTANCE.boatMaxSpeedLimitBps.get();
            int fillWidth = (int)Math.min((double)barWidth, currentBps / Math.max((double)1.0F, maxBpsLimit) * (double)barWidth);
            int barX = (screenWidth - barWidth) / 2;
            int barY = y + 10;
            guiGraphics.fill(barX, barY, barX + barWidth, barY + 5, -11184811);
            guiGraphics.fill(barX, barY, barX + fillWidth, barY + 5, color | -16777216);
            }
        }
    }
}
