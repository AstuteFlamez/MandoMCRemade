package net.mandomc.mandomcremade.utility;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.objects.LivingEntityWrapper;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.concurrent.CompletableFuture;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static net.mandomc.mandomcremade.utility.Utilities.genVec;

public class Particles {

    static MandoMCRemade plugin = MandoMCRemade.getInstance();

    public static void playCircleOutwards(Player player, Location loc) {
        new BukkitRunnable(){
            double startRadius = 0;
            final double radiusIncrease = 0.5;
            final double endRadius = 10;
            @Override
            public void run() {
                circle(player, loc, startRadius);
                startRadius += radiusIncrease;
                if (startRadius >= endRadius) {
                    this.cancel();
                }
            }
        }.runTaskTimer(MandoMCRemade.getInstance(), 0L, 1L);
    }

    public static void playCircleInwards(Player player, Location loc, int radius) {
        new BukkitRunnable(){
            int startRadius = radius;
            final double radiusDecrease = 0.5;
            final double endRadius = 0;
            @Override
            public void run() {
                circle(player, loc, startRadius);
                startRadius -= radiusDecrease;
                if (startRadius <= endRadius) {
                    this.cancel();
                }
            }
        }.runTaskTimer(MandoMCRemade.getInstance(), 0L, 1L);
    }

    private static void circle(Player player, Location loc, double startRadius) {
        for (double angle = 0; angle < 2 * Math.PI; angle += Math.PI / 36) {
            final double x = startRadius * cos(angle);
            final double z = startRadius * Math.sin(angle);

            loc.add(x, 0, z);
            Particle.DustTransition dustOptions = new Particle.DustTransition(Color.SILVER, Color.SILVER, 1);
            player.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, loc, 1, dustOptions);
            loc.subtract(x, 0, z);
        }
    }

    public static final Vector rotateAroundAxisX(Vector v, double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double y = v.getY() * cos - v.getZ() * sin;
        double z = v.getY() * sin + v.getZ() * cos;
        return v.setY(y).setZ(z);
    }

    public static final Vector rotateAroundAxisY(Vector v, double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double x = v.getX() * cos + v.getZ() * sin;
        double z = v.getX() * -sin + v.getZ() * cos;
        return v.setX(x).setZ(z);
    }

    public static void playHelix(Location loc) {
        new BukkitRunnable() {
            double t = 0;
            final double r = 1;
            final int particlesPerTick = 5; // Number of particles to spawn per tick

            @Override
            public void run() {
                for (int i = 0; i < particlesPerTick; i++) {
                    t = t + Math.PI / (2 * particlesPerTick);
                    double x = r * Math.cos(t);
                    double y = 0.2 * t;
                    double z = r * Math.sin(t);
                    loc.add(x, y, z);
                    loc.getWorld().spawnParticle(Particle.DRIPPING_WATER, loc, 1);
                    loc.subtract(x, y, z);
                    if (t > Math.PI * 3.75) {
                        cancel();
                    }
                }
            }
        }.runTaskTimer(MandoMCRemade.getInstance(), 0, 1);
    }

    public static void playRadialWaves(Location loc){
        new BukkitRunnable(){
            double t = Math.PI/4;
            public void run(){
                t = t + 0.1*Math.PI;
                for (double theta = 0; theta <= 2*Math.PI; theta = theta + Math.PI/32){
                    double x = t*cos(theta);
                    double y = 2*Math.exp(-0.1*t) * sin(t) + 1.5;
                    double z = t*sin(theta);
                    loc.add(x,y,z);
                    Particle.DustTransition dustOptions = new Particle.DustTransition(Color.MAROON, Color.MAROON, 1);
                    loc.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, loc, 1, dustOptions);
                    loc.subtract(x,y,z);

                    theta = theta + Math.PI/64;

                    x = t*cos(theta);
                    y = 2*Math.exp(-0.1*t) * sin(t) + 1.5;
                    z = t*sin(theta);
                    loc.add(x,y,z);
                    dustOptions = new Particle.DustTransition(Color.RED, Color.RED, 1);
                    loc.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, loc, 1, dustOptions);
                    loc.subtract(x,y,z);
                }
                if (t > 7){
                    this.cancel();
                }
            }

        }.runTaskTimer(MandoMCRemade.getInstance(), 0, 1);
    }

    public static void playSphere(Location loc){
        new BukkitRunnable(){
            double t = 0;
            public void run(){
                t += Math.PI/10;
                for (double theta = 0; theta <= 2*Math.PI; theta+= Math.PI/40){
                    double r = 1.5;
                    double x = r*cos(theta)*sin(t);
                    double y = r*cos(t) + 1.5;
                    double z = r*sin(theta)*sin(t);
                    loc.add(x,y,z);
                    Particle.DustTransition dustOptions = new Particle.DustTransition(Color.TEAL, Color.TEAL, 1);
                    loc.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, loc, 1, dustOptions);
                    loc.subtract(x,y,z);

                    double r2 = 1;
                    double x2 = r2*cos(theta)*sin(t);
                    double y2 = r2*cos(t) + 1.5;
                    double z2 = r2*sin(theta)*sin(t);
                    loc.add(x2,y2,z2);
                    dustOptions = new Particle.DustTransition(Color.AQUA, Color.AQUA, 1);
                    loc.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, loc, 1, dustOptions);
                    loc.subtract(x2,y2,z2);
                }
                if (t > Math.PI){
                    this.cancel();
                }
            }

        }.runTaskTimer(MandoMCRemade.getInstance(), 0, 1);
    }

    public static void playSphereLoop(Location loc){
        new BukkitRunnable(){
            double t = 0;
            public void run(){
                t += Math.PI/10;
                for (double theta = 0; theta <= 2*Math.PI; theta+= Math.PI/4){
                    double r = 1.5;
                    double x = r*cos(theta)*sin(t);
                    double y = r*cos(t) + 1.5;
                    double z = r*sin(theta)*sin(t);
                    loc.add(x,y,z);
                    Particle.DustTransition dustOptions = new Particle.DustTransition(Color.TEAL, Color.TEAL, 1);
                    loc.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, loc, 1, dustOptions);
                    loc.subtract(x,y,z);
                }
                if (t > 2*Math.PI){
                    this.cancel();
                }
            }

        }.runTaskTimer(MandoMCRemade.getInstance(), 0, 1);
    }

    public static void playSpiral(Location loc){
        new BukkitRunnable(){
            double t = 0;
            public void run(){
                t += Math.PI/16;
                double x; double y; double z;
                for (double theta = 0; theta <= 2*Math.PI; theta = theta + Math.PI/16){
                    for (double i = 0; i <= 1; i = i + 1){
                        x = 0.4*(2*Math.PI-theta)*0.5*cos(theta + t + i*Math.PI);
                        y = 0.5*t;
                        z = 0.4*(2*Math.PI-theta)*0.5*sin(theta + t + i*Math.PI);
                        loc.add(x, y, z);
                        Particle.DustTransition dustOptions = new Particle.DustTransition(Color.MAROON, Color.MAROON, 1);
                        loc.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, loc, 1, dustOptions);
                        loc.subtract(x,y,z);
                    }

                }

                if(t > 2*Math.PI){
                    this.cancel();
                }
            }

        }.runTaskTimer(MandoMCRemade.getInstance(), 0, 1);
    }

    public static void playSpiralHelix(Location loc){
        new BukkitRunnable(){
            double phi = 0;
            public void run(){
                phi = phi + Math.PI/8;
                double x, y, z;

                for (double t = 0; t <= 2*Math.PI; t = t + Math.PI/16){
                    for (double i = 0; i <= 1; i = i + 1){
                        x = 0.4*(2*Math.PI-t)*0.5*cos(t + phi + i*Math.PI);
                        y = 0.5*t;
                        z = 0.4*(2*Math.PI-t)*0.5*sin(t + phi + i*Math.PI);
                        loc.add(x, y, z);
                        Particle.DustTransition dustOptions = new Particle.DustTransition(Color.MAROON, Color.MAROON, 1);
                        loc.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, loc, 1, dustOptions);
                        loc.subtract(x,y,z);
                    }

                }

                if(phi > Math.PI){
                    this.cancel();
                }
            }
        }.runTaskTimer(MandoMCRemade.getInstance(), 0, 3);
    }

    public static void playSpiralHelixBoom(Location loc){
        new BukkitRunnable(){
            double t,x,y,z = 0;
            public void run(){
                t+=Math.PI/2;
                for(double i = 0; i <= 2*Math.PI; i+=Math.PI/2){
                    x = 0.3*(4*Math.PI-t)*cos(t+i);
                    y = 0.2*t;
                    z = 0.3*(4*Math.PI-t)*sin(t+i);
                    loc.add(x, y, z);
                    Particle.DustTransition dustOptions = new Particle.DustTransition(Color.MAROON, Color.MAROON, 1);
                    loc.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, loc, 1, dustOptions);
                    loc.subtract(x,y,z);
                }

                if(t >= 4*Math.PI){
                    loc.add(x, y, z);
                    loc.getWorld().spawnParticle(Particle.LAVA, loc, 1);
                    this.cancel();
                }
            }
        }.runTaskTimer(MandoMCRemade.getInstance(), 0, 3);
    }

    public static void playSpinningBeam(Player player){
        new BukkitRunnable() {
            // Number of points in each circle
            int circlePoints = 10;
            // radius of the circle
            double radius = 2;
            // Starting location for the first circle will be the player's eye location
            Location playerLoc = player.getEyeLocation();
            // We need world for the spawnParticle function
            World world = playerLoc.getWorld();
            // This is the direction the player is looking, normalized to a length (speed) of 1.
            final Vector dir = player.getLocation().getDirection().normalize();
            // We need the pitch in radians for the rotate axis function
            // We also add 90 degrees to compensate for the non-standard use of pitch degrees in Minecraft.
            final double pitch = (playerLoc.getPitch() + 90.0F) * 0.017453292F;
            // The yaw is also converted to radians here, but we need to negate it for the function to work properly
            final double yaw = -playerLoc.getYaw() * 0.017453292F;
            // This is the distance between each point around the circumference of the circle.
            double increment = (2 * Math.PI) / circlePoints;
            // This is used to rotate the circle as the beam progresses
            double circlePointOffset = 0;
            // Max length of the beam..for now
            int beamLength = 30;
            // This is the amount we will shrink the circle radius with each loop
            double radiusShrinkage = radius / (double) ((beamLength + 2) / 2);
            @Override
            public void run() {
                beamLength--;
                if(beamLength < 1){
                    this.cancel();
                }
                // We need to loop to get all of the points on the circle every loop
                for (int i = 0; i < circlePoints; i++) {
                    // Angle on the circle + the offset for rotating each loop
                    double angle = i * increment + circlePointOffset;
                    double x = radius * Math.cos(angle);
                    double z = radius * Math.sin(angle);
                    // Convert that to a 3D Vector where the height is always 0
                    Vector vec = new Vector(x, 0, z);
                    // Now rotate the circle point so it's properly aligned no matter where the player is looking:
                    rotateAroundAxisX(vec, pitch);
                    rotateAroundAxisY(vec, yaw);
                    // Add that vector to the player's current location
                    playerLoc.add(vec);
                    // Display the particle
                    Particle.DustTransition dustOptions = new Particle.DustTransition(Color.MAROON, Color.MAROON, 1);
                    player.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, playerLoc, 1, dustOptions);
                    // Reminder to self - the "data" option for a (particle, location, data) is speed, not count!!
                    // Since add() modifies the original variable, we have to subtract() it so the next calculation starts from the same location as this one.
                    playerLoc.subtract(vec);
                }
            /* Rotate the circle points each iteration, like rifling in a gun barrel
               Since the particles in the circle are separated by "increment" distance, we rotate
                 the circle points 1/3 of increment with each loop. That means on the 3rd rotation,
                 the offset calculation matches the full increment, so we reset the offset to 0
                 instead of continuing to increase the offset.

                 For fun, comment out the if condition here and see what happens to the particles
                   without it. Since circlePointOffset is added to the angle in the calculation
                   above, it has an interesting side effect. Try it!
             */
                circlePointOffset += increment / 3;
                // If the rotation matches the increment, reset to 0 to ensure a smooth rotation.
                if (circlePointOffset >= increment) {
                    circlePointOffset = 0;
                }
                // Shrink each circle radius until it's just a point at the end of a long swirling cone
                radius -= radiusShrinkage;
                if (radius < 0) {
                    this.cancel();
                }

            /* We multiplied this by 1 already (using normalize()), ensuring the beam will
               travel one block away from the player each loop.
             */
                playerLoc.add(dir);
            }
        }.runTaskTimer(MandoMCRemade.getInstance(), 0, 1);
    }

    public static void playOrbitingBeam(Player player){
        new BukkitRunnable() {
            // Number of points to display, evenly spaced around the circle's radius
            int circlePoints = 3;
            // How fast should the particles rotate around the center beam
            int rotationSpeed = 20;
            double radius = 2.5;
            Location startLoc = player.getEyeLocation();
            World world = startLoc.getWorld();
            final Vector dir = player.getLocation().getDirection().normalize().multiply(1);
            final double pitch = (startLoc.getPitch() +90.0F) * 0.017453292F;
            final double yaw = -startLoc.getYaw() * 0.017453292F;
            // Particle offset increment for each loop
            double increment = (2 * Math.PI) / rotationSpeed;
            double circlePointOffset = 0; // This is used to rotate the circle as the beam progresses
            int beamLength = 60;
            double radiusShrinkage = radius / (double) ((beamLength + 2) / 2);
            @Override
            public void run() {
                beamLength--;
                if(beamLength < 1){
                    this.cancel();
                }
                for (int i = 0; i < circlePoints; i++) {
                    double x =  radius * Math.cos(2 * Math.PI * i / circlePoints + circlePointOffset);
                    double z =  radius * Math.sin(2 * Math.PI * i / circlePoints + circlePointOffset);

                    Vector vec = new Vector(x, 0, z);
                    rotateAroundAxisX(vec, pitch);
                    rotateAroundAxisY(vec, yaw);

                    startLoc.add(vec);
                    world.spawnParticle(Particle.FLAME, startLoc, 0);
                    startLoc.subtract(vec);
                }
                // Always spawn a center particle in the same direction the player was facing.
                startLoc.add(dir);
                Particle.DustTransition dustOptions = new Particle.DustTransition(Color.OLIVE, Color.OLIVE, 1);
                player.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, startLoc, 1, dustOptions);
                startLoc.subtract(dir);

                // Shrink each circle radius until it's just a point at the end of a long swirling cone
                radius -= radiusShrinkage;
                if (radius < 0) {
                    this.cancel();
                }

                // Rotate the circle points each iteration, like rifling in a barrel
                circlePointOffset += increment;
                if (circlePointOffset >= (2 * Math.PI)) {
                    circlePointOffset = 0;
                }
                startLoc.add(dir);
            }
        }.runTaskTimer(MandoMCRemade.getInstance(), 0, 3);
    }

    public static void playOscillatingBeam(Player player, String ability, int range) {
        new BukkitRunnable() {
            int circlePoints = 6;
            double maxRadius = 1.5;
            Location playerLoc = player.getEyeLocation();
            World world = playerLoc.getWorld();
            final Vector dir = player.getLocation().getDirection().normalize().multiply(0.5);
            final double pitch = (playerLoc.getPitch() + 90.0F) * 0.017453292F;
            final double yaw = -playerLoc.getYaw() * 0.017453292F;
            double increment = (2 * Math.PI) / circlePoints;
            int maxCircles = 12;
            double t = 0;
            double circlePointOffset = 0;
            int beamLength = range;

            @Override
            public void run() {
                beamLength--;
                if (beamLength < 1) {
                    this.cancel();
                    return;
                }
                double radius = Math.sin(t) * maxRadius;
                for (int i = 0; i < circlePoints; i++) {
                    double angle = i * increment + circlePointOffset;
                    double x = radius * Math.cos(angle);
                    double z = radius * Math.sin(angle);
                    Vector vec = new Vector(x, 0, z);
                    rotateAroundAxisX(vec, pitch);
                    rotateAroundAxisY(vec, yaw);
                    playerLoc.add(vec);
                    Particle.DustTransition dustOptions = new Particle.DustTransition(Color.SILVER, Color.SILVER, 1);
                    world.spawnParticle(Particle.DUST_COLOR_TRANSITION, playerLoc, 1, dustOptions);
                    LivingEntity hitEntity = entityHit(player, playerLoc);
                    if (hitEntity != null) {
                        switch(ability){
                            case "pullI":
                                Vector vector = genVec(entityHit(player, playerLoc).getLocation(), player.getLocation());
                                entityHit(player, playerLoc).setVelocity(vector);
                                break;
                        }
                    }
                    playerLoc.subtract(vec);
                }
                circlePointOffset += increment / 3;
                if (circlePointOffset >= increment) {
                    circlePointOffset = 0;
                }
                t += Math.PI / maxCircles;
                if (t > Math.PI * 2) {
                    t = 0;
                }
                playerLoc.add(dir);
            }

        }.runTaskTimer(MandoMCRemade.getInstance(), 0, 1);
    }

    public static void playControlledBeam(Player player) {
        new BukkitRunnable() {
            World world = player.getLocation().getWorld();
            int beamLength = 30;
            double t = 0;

            @Override
            public void run() {
                Location startLoc = player.getEyeLocation();
                final Vector dir = player.getLocation().getDirection().normalize();

                t += 0.5;
                if (t > beamLength) {
                    this.cancel();
                }
                // Extend the length of the vector by 0.5 each loop to ensure
                // the particle trail is always moving away
                Vector vecOffset = dir.clone().multiply(t);

                Particle.DustTransition dustOptions = new Particle.DustTransition(Color.LIME, Color.LIME, 1);
                player.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, startLoc.add(vecOffset), 1, dustOptions);
            }
        }.runTaskTimer(MandoMCRemade.getInstance(), 0, 1);
    }

    public static void playRoof(Player player) {
        new BukkitRunnable() {
            double radius = 0.5;
            final Location location = player.getLocation();

            @Override
            public void run() {
                if (radius > 5) {
                    this.cancel();
                }

                // Calculate the direction perpendicular to the player's direction
                Vector direction = player.getLocation().getDirection();
                Vector perpendicular = new Vector(-direction.getZ(), 0, direction.getX()).normalize();

                // Generate points around a vertical circle
                for (int i = 0; i < 360; i += 10) {
                    double radians = Math.toRadians(i);
                    double x = radius * Math.cos(radians);
                    double y = radius * Math.sin(radians);

                    // Calculate the particle position
                    Vector offset = perpendicular.clone().multiply(x).add(new Vector(0, y, 0));
                    Location particleLocation = location.clone().add(offset);

                    // Spawn particles
                    Particle.DustTransition dustOptions = new Particle.DustTransition(Color.ORANGE, Color.ORANGE, 1);
                    player.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, particleLocation, 1, dustOptions);
                }

                // Increment the radius for the next run
                radius += 0.1;
            }
        }.runTaskTimer(MandoMCRemade.getInstance(), 0, 2);
    }

    public static void playCircularBeam(Player player) {
        new BukkitRunnable() {
            double radius = 0.1;
            double beamLength = 10; // Length of the beam
            int steps = 100; // Number of steps for the beam
            int currentStep = 0;
            final Location startLocation = player.getEyeLocation();
            final Vector direction = player.getLocation().getDirection().normalize();

            @Override
            public void run() {
                if (currentStep > steps) {
                    this.cancel();
                }

                // Calculate the current position on the beam
                double progress = (double) currentStep / steps;
                Location beamLocation = startLocation.clone().add(direction.clone().multiply(progress * beamLength));

                // Spawn the beam particle
                player.getWorld().spawnParticle(Particle.FLAME, beamLocation, 0);

                // Generate points around a vertical circle at the beam location
                for (int i = 0; i < 360; i += 10) {
                    double radians = Math.toRadians(i);
                    double x = radius * Math.cos(radians);
                    double y = radius * Math.sin(radians);

                    // Calculate perpendicular vectors
                    Vector perpendicular = new Vector(-direction.getZ(), 0, direction.getX()).normalize();
                    Vector circleOffset = perpendicular.clone().multiply(x).add(new Vector(0, y, 0));

                    // Calculate the particle position for the circle
                    Location circleLocation = beamLocation.clone().add(circleOffset);

                    // Spawn circle particles
                    player.getWorld().spawnParticle(Particle.FLAME, circleLocation, 0, 0, 0, 0, 0);
                }

                // Increment the radius for the circles and the current step
                radius += 0.05;
                currentStep++;
            }
        }.runTaskTimer(MandoMCRemade.getInstance(), 0, 2);
    }

    private static LivingEntity entityHit(Player player, Location particleLoc){
        LivingEntity livingEntity = null;
        // Search for any entities near the particle's current location
        for (Entity entity : player.getWorld().getNearbyEntities(particleLoc, 5, 5, 5)) {
            // We only care about living entities. Any others will be ignored.
            if (entity instanceof LivingEntity) {
                // Ignore player that initiated the shot
                if (entity == player) {
                    continue;
                }

                        /* Define the bounding box of the particle.
                        We will use 0.25 here, since the particle is moving 0.5 blocks each time.
                        That means the particle won't miss very small entities like chickens or bats,
                          as the particle bounding box covers 1/2 of the movement distance.
                         */
                Vector particleMinVector = new Vector(
                        particleLoc.getX() - 0.25,
                        particleLoc.getY() - 0.25,
                        particleLoc.getZ() - 0.25);
                Vector particleMaxVector = new Vector(
                        particleLoc.getX() + 0.25,
                        particleLoc.getY() + 0.25,
                        particleLoc.getZ() + 0.25);

                // Now use a spigot API call to determine if the particle is inside the entity's hitbox
                if(entity.getBoundingBox().overlaps(particleMinVector,particleMaxVector)){
                    // We must return here, otherwise the code below will display one more particle.
                    return (LivingEntity) entity;
                }
            }
        }
        return livingEntity;
    }
}
