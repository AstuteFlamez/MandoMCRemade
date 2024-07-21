package net.mandomc.mandomcremade.utility;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class ProtonTorpedoes {

    public ProtonTorpedoes(LivingEntity vehicle, double distance, double yOffset, double sidewaysOffset) {
        Location location = vehicle.getEyeLocation();
        Vector direction = location.getDirection().normalize();
        double lineLength = distance; // Length of the line to draw

        // Draw the particle line along the direction vector
        Location endLocation = location.clone(); // Track the end location of the direction line
        /*for (double t = 0; t < lineLength; t++) {
            Location tempLocation = endLocation.clone().add(direction.clone().multiply(t));

            Particle.DustTransition dustOptions = new Particle.DustTransition(Color.RED, Color.GREEN, 1);
            tempLocation.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, tempLocation, 1, dustOptions);
        }*/

        // Set the start location for the dupe line to the end of the direction line
        Location dupeStartLocation = endLocation.clone().add(direction.clone().multiply(lineLength));
        dupeStartLocation = dupeStartLocation.add(0, yOffset, 0);

        // Calculate a perpendicular vector in the same plane as `direction`
        Vector dupe = getPerpendicularVector(direction).normalize().multiply(2); // Adjust magnitude as needed

        // Draw the particle line along the perpendicular vector
        for (double t = 0; t < Math.abs(sidewaysOffset); t++) {
            Location tempLocation = dupeStartLocation.clone().add(dupe.clone().multiply(t));

            Particle.DustTransition dustOptions = new Particle.DustTransition(Color.RED, Color.GREEN, 1);
            tempLocation.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, tempLocation, 1, dustOptions);
        }
    }

    public static Vector getPerpendicularVector(Vector vector) {
        // Get the x and z components of the vector
        double x = vector.getX();
        double z = vector.getZ();

        // Calculate a perpendicular vector on the same plane
        return new Vector(-z, 0, x);
    }
}
