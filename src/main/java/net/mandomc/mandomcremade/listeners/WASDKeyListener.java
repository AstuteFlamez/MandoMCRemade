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
import org.bukkit.util.Vector;

import java.util.UUID;

public class WASDKeyListener extends PacketAdapter {

    private static final double BASE_SPEED = 0.2;
    private static final double SPEED_CHANGE = 1.0; // Increased speed change

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
        phantom.setRotation(player.getLocation().getYaw(), player.getLocation().getPitch());

        // Calculate movement direction
        double yaw = Math.toRadians(phantom.getLocation().getYaw());

        // Calculate speed based on W (forward > 0) or S (forward < 0)
        double speed = BASE_SPEED;
        if (forward > 0) {
            speed += SPEED_CHANGE; // Significantly increase speed when W is pressed
        } else if (forward < 0) {
            speed = Math.max(0, speed - SPEED_CHANGE); // Decrease speed when S is pressed, but do not go backward
        }

        double dx = -Math.sin(yaw) * speed + Math.cos(yaw) * sideways;
        double dz = Math.cos(yaw) * speed + Math.sin(yaw) * sideways;

        // Adjust vertical velocity based on jump and unmount
        double dy = 0;
        if (jump) {
            dy = 0.5; // Adjust jump strength as needed
        } else if (unmount) {
            dy = -0.5; // Adjust downward movement as needed
        }

        // Apply velocity changes
        phantom.setVelocity(new Vector(dx, dy, dz));

        Location location = phantom.getLocation(); // Get phantom's location
        Location targetLocation = location.clone(); // Clone the location to avoid modifying phantom's location
        targetLocation.setY(targetLocation.getY() - 1); // Adjust the Y-coordinate to be one block lower

        // Schedule the teleport to run on the main server thread
        Bukkit.getScheduler().runTask(plugin, () -> model.teleport(targetLocation));
    }
}
