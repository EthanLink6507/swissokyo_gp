package com.swissokyo.swissokyo_gp;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;

public class EngineSoundInstance extends AbstractTickableSoundInstance {
    private final Boat boat;

    public EngineSoundInstance(Boat boat) {
        super(SoundEvents.ELYTRA_FLYING, SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());
        this.boat = boat;
        this.looping = true;
        this.delay = 0;
        this.volume = 0.5F;
        this.pitch = 0.7F;
    }

    public void tick() {
        if (this.boat.isAlive() && !this.boat.getPassengers().isEmpty()) {
            this.x = (double)((float)this.boat.getX());
            this.y = (double)((float)this.boat.getY());
            this.z = (double)((float)this.boat.getZ());
        double speedBps = this.boat.getDeltaMovement().horizontalDistance() * (double)20.0F;
        double targetBps = Math.max((double)1.0F, (Double)Config.INSTANCE.boatTargetSpeedBps.get());
        float speedFactor = (float)Mth.clamp(speedBps / targetBps, (double)0.0F, (double)1.0F);
            this.pitch = 0.7F + speedFactor * 1.5F;
            this.volume = 0.5F + speedFactor * 0.5F;
        if (this.boat.tickCount % 20 == 0) {
            System.out.println("[SwissokyoGP] Engine Tick - BPS: " + speedBps + ", Pitch: " + this.pitch);
        }
        } else {
            this.stop();
        }

    }

    public void stopSound() {
        this.stop();
    }
}
