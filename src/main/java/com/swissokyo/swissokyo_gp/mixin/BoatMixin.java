package com.swissokyo.swissokyo_gp.mixin;

import com.swissokyo.swissokyo_gp.Config;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Boat.class})
public class BoatMixin {
   @Shadow
   private boolean inputLeft;
   @Shadow
   private boolean inputRight;
   @Shadow
   private boolean inputUp;
   @Shadow
   private boolean inputDown;
   @Unique
   private int swissokyogp$jumpCooldownTimer = 0;

   public BoatMixin() {
   }

   @Inject(
      method = {"controlBoat"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void swissokyogp$controlBoat(CallbackInfo ci) {
      Boat boat = (Boat)this;
      LivingEntity var4 = boat.getControllingPassenger();
      if (var4 instanceof Player player) {
         if (this.swissokyogp$jumpCooldownTimer > 0) {
            --this.swissokyogp$jumpCooldownTimer;
         }

         float turning = 0.0F;
         if (this.inputLeft) {
            --turning;
         }

         if (this.inputRight) {
            ++turning;
         }

         double currentHorizontalSpeed = boat.getDeltaMovement().horizontalDistance();
         float turningSpeed = 3.0F + (float)currentHorizontalSpeed * 2.5F;
         boat.setYRot(boat.getYRot() + turning * turningSpeed);
         double targetBps = (Double)Config.INSTANCE.boatTargetSpeedBps.get();
         double targetSpeedInternal = targetBps / (double)20.0F;
         double accelFactor = (Double)Config.INSTANCE.boatAccelerationScale.get();
         double inertia = (Double)Config.INSTANCE.boatInertia.get();
         boolean jumpEnabled = (Boolean)Config.INSTANCE.boatJumpEnabled.get();
         double jumpPowerBlocks = (Double)Config.INSTANCE.boatJumpPower.get();
         int jumpCooldown = (Integer)Config.INSTANCE.boatJumpCooldown.get();
         Vec3 currentMotion = boat.getDeltaMovement();
         double nextX = currentMotion.x;
         double nextZ = currentMotion.z;
         double nextY = currentMotion.y;
         if (this.inputUp) {
            double goalX = -Math.sin((double)(boat.getYRot() * ((float)Math.PI / 180F))) * targetSpeedInternal;
            double goalZ = Math.cos((double)(boat.getYRot() * ((float)Math.PI / 180F))) * targetSpeedInternal;
            nextX += (goalX - nextX) * accelFactor;
            nextZ += (goalZ - nextZ) * accelFactor;
         } else if (this.inputDown) {
            nextX *= 0.8;
            nextZ *= 0.8;
         } else {
            nextX *= inertia;
            nextZ *= inertia;
         }

         if (!boat.onGround() && !boat.isInWater()) {
            nextY -= 0.045;
            nextY *= 0.98;
         } else {
            nextY *= 0.1;
         }

         if (jumpEnabled && player instanceof LivingEntityAccessor accessor) {
            if (accessor.isJumping() && this.swissokyogp$jumpCooldownTimer == 0 && (boat.onGround() || boat.isInWater())) {
               double velocity = 0.44 * Math.sqrt(jumpPowerBlocks);
               nextY = velocity;
               this.swissokyogp$jumpCooldownTimer = jumpCooldown;
            }
         }

         boat.setDeltaMovement(nextX, nextY, nextZ);
         ci.cancel();
      }

   }
}
