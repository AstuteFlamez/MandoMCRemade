package net.mandomc.mandomcremade.tasks;

import net.mandomc.mandomcremade.managers.VehicleManager;
import net.mandomc.mandomcremade.objects.Vehicle;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class VehicleTask extends BukkitRunnable {

    @Override
    public void run() {
        for (Vehicle vehicle : VehicleManager.vehicles) {
            UUID pilotUUID = vehicle.getPilot();
            if (pilotUUID == null) continue;

            Player player = Bukkit.getPlayer(pilotUUID);
            if (player == null) continue;

            Entity vehicleMob = vehicle.getVehicleMob();
            Entity modelMob = vehicle.getModelMob();
            if (vehicleMob instanceof Phantom vehicleMobLiving) {
                vehicleMobLiving.setAI(true);
            }

            vehicleMob.setRotation(player.getLocation().getYaw(), player.getLocation().getPitch());
            vehicleMob.setVelocity(vehicleMob.getLocation().getDirection().multiply(2));
            modelMob.teleport(vehicleMob.getLocation());
        }
    }
}
