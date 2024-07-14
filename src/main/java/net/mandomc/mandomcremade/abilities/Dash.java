package net.mandomc.mandomcremade.abilities;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.utility.Particles;
import net.mandomc.mandomcremade.handlers.Handlers;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Dash {

    public static void dash(Player player, MandoMCRemade plugin) {
        FileConfiguration config = plugin.getConfig();
        Location start = player.getLocation().clone();
        Vector direction = start.getDirection();
        int distance = plugin.getConfig().getInt("DashIRange");

        // Calculate the target location 'distance' blocks ahead
        Location target = start.clone().add(direction.multiply(distance));

        // Get the highest block at the target location
        Location highestBlockLocation = player.getWorld().getHighestBlockAt(target).getLocation();

        // Adjust the target location to be one block above the highest block and retain the player's yaw and pitch
        Location adjustedLocation = new Location(player.getWorld(), highestBlockLocation.getX(), highestBlockLocation.getY() + 1, highestBlockLocation.getZ(), start.getYaw(), start.getPitch());

        // Teleport the player to the adjusted location
        player.teleport(adjustedLocation);
        Particles.beam(player,
                distance,
                start,
                player.getLocation(),
                Handlers.hexToColor(config.getString("DashHexCode1")),
                Handlers.hexToColor(config.getString("DashHexCode2")));
    }

}
