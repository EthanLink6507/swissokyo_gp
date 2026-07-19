package com.swissokyo.swissokyo_gp;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

@EventBusSubscriber(
    modid = "swissokyo_gp",
    value = {Dist.CLIENT},
    bus = Bus.MOD
)
public class ClientEvents {
    public ClientEvents() {
    }

    @SubscribeEvent
    public static void registerOverlay(RegisterGuiLayersEvent event) {
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath("swissokyo_gp", "speedometer"), SpeedometerOverlay.INSTANCE);
    }

    @EventBusSubscriber(
        modid = "swissokyo_gp",
        value = {Dist.CLIENT},
        bus = Bus.GAME
    )
    public static class SoundTickHandler {
        private static EngineSoundInstance engineSound = null;

        public SoundTickHandler() {
    }

    @SubscribeEvent
        public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
            if ((Boolean)Config.INSTANCE.engineSoundEnabled.get()) {
                Entity var3 = mc.player.getVehicle();
                if (var3 instanceof Boat) {
                    Boat boat = (Boat)var3;
                    if (engineSound == null || !mc.getSoundManager().isActive(engineSound)) {
                        System.out.println("[SwissokyoGP] Starting engine sound for boat: " + boat.getId());
                        engineSound = new EngineSoundInstance(boat);
                        mc.getSoundManager().play(engineSound);
                    }

                    return;
                }
            }

            if (engineSound != null) {
                System.out.println("[SwissokyoGP] Stopping engine sound.");
                engineSound.stopSound();
                engineSound = null;
            }

            }
        }
    }
}
