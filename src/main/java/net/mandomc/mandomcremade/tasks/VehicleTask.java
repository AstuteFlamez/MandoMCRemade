package net.mandomc.mandomcremade.tasks;

import net.mandomc.mandomcremade.managers.VehicleManager;
import net.mandomc.mandomcremade.objects.Vehicle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.UUID;

public class VehicleTask extends BukkitRunnable {

    @Override
    public void run() {

        for(Vehicle vehicle : VehicleManager.vehicles){
            if(vehicle.getPilot() == null) return;
            UUID uuid = vehicle.getPilot();
            Player player = Bukkit.getPlayer(uuid);

            if(player == null) return;

            /*Location playerLocation = player.getLocation().clone();
            float yaw = playerLocation.getYaw();
            float pitch = playerLocation.getPitch();

            LivingEntity phantom = vehicle.getVehicle();
            LivingEntity zombie = vehicle.getModelMob();
            Location phantomLocation = phantom.getLocation().clone();
            Vector direction = phantomLocation.getDirection();
            phantom.setAI(true);

            phantom.setRotation(yaw, pitch);
            phantom.setVelocity(direction.multiply(2));

            zombie.teleport(phantom.getLocation());*/


            Entity seat1 = (Entity) vehicle.getVehicle();
            Entity model = (Entity) vehicle.getModelMob();
            LivingEntity seat1Living = (Phantom) seat1;
            seat1Living.setAI(true);

            seat1.setRotation(player.getLocation().getYaw(), player.getLocation().getPitch());
            seat1.setVelocity(seat1.getLocation().getDirection().multiply(2));

            model.teleport(seat1.getLocation());
        }

    }
}
