package net.mandomc.mandomcremade.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import net.mandomc.mandomcremade.managers.VehicleManager;
import net.mandomc.mandomcremade.objects.Vehicle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.UUID;

public class WASDKeyListener extends PacketAdapter {

    public WASDKeyListener(Plugin plugin) {
        super(plugin, PacketType.Play.Client.STEER_VEHICLE);
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        // Get the player
        Player player = event.getPlayer();

        for (Vehicle vehicle : VehicleManager.vehicles) {
            UUID pilotUUID = vehicle.getPilot();
            if (pilotUUID == null) continue;

            Player pilot = Bukkit.getPlayer(pilotUUID);
            if (pilot == null) continue;

            if (player != pilot) continue;

            // Get the packet
            float sideways = event.getPacket().getFloat().read(0);  // A and D keys
            float forward = event.getPacket().getFloat().read(1);   // W and S keys
            boolean jump = event.getPacket().getBooleans().read(0); // Space key
            boolean unmount = event.getPacket().getBooleans().read(1); // Shift key

            // Process movement
            handleMovement(pilot, vehicle, forward, sideways, jump, unmount);
        }
    }

    private void handleMovement(Player player, Vehicle vehicle, float forward, float sideways, boolean jump, boolean unmount) {
        LivingEntity phantom = vehicle.getVehicleMob();
        LivingEntity model = vehicle.getModelMob();

        phantom.setAI(true);
        phantom.setRotation(player.getLocation().getYaw(), (player.getLocation().getPitch()*-1));

        // Calculate movement direction
        double yaw = Math.toRadians(phantom.getLocation().getYaw());
        double dx = -Math.sin(yaw) * forward + Math.cos(yaw) * sideways;
        double dz = Math.cos(yaw) * forward + Math.sin(yaw) * sideways;

        // Adjust vertical velocity based on jump and unmount
        double dy = 0;
        if (jump) {
            dy = 0.5; // Adjust jump strength as needed
        } else if (unmount) {
            dy = -0.5; // Adjust downward movement as needed
        }

        // Apply velocity changes
        phantom.setVelocity(new Vector(dx, dy, dz));

        // Schedule model teleport to sync with phantom
        new BukkitRunnable() {
            @Override
            public void run() {
                Location location = player.getLocation(); // Get phantom's location
                Location targetLocation = location.clone(); // Clone the location to avoid modifying phantom's location
                targetLocation.setY(targetLocation.getY() - 0.15); // Adjust the Y-coordinate to be one block lower
                //targetLocation.setPitch(targetLocation.getPitch() * -1);
                model.teleport(targetLocation); // Teleport the model to the adjusted location
            }
        }.runTask(plugin);
    }
}
