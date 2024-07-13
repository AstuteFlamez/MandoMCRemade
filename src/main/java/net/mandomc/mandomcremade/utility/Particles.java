package net.mandomc.mandomcremade.utility;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.objects.LivingEntityWrapper;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.checkerframework.checker.units.qual.Force;

import java.util.concurrent.CompletableFuture;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static net.mandomc.mandomcremade.utility.Utilities.genVec;

public class Particles {

    static MandoMCRemade plugin = MandoMCRemade.getInstance();

    public static void circleOutwards(Location loc, int radius, Color c1, Color c2) {
        new BukkitRunnable(){
            double startRadius = 0;
            final double radiusIncrease = 0.5;
            final double endRadius = radius;
            @Override
            public void run() {
                for (double angle = 0; angle < 2 * Math.PI; angle += Math.PI / 36) {
                    final double x = startRadius * cos(angle);
                    final double z = startRadius * Math.sin(angle);

                    loc.add(x, 0, z);
                    Particle.DustTransition dustOptions = new Particle.DustTransition(c1, c2, 1);
                    loc.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, loc, 1, dustOptions);
                    loc.subtract(x, 0, z);
                }
                startRadius += radiusIncrease;
                if (startRadius >= endRadius) {
                    this.cancel();
                }
            }
        }.runTaskTimer(MandoMCRemade.getInstance(), 0L, 1L);
    }

    public static void circleInwards(Location loc, int radius, Color c1, Color c2) {
        new BukkitRunnable(){
            int startRadius = radius;
            final double radiusDecrease = 0.5;
            final double endRadius = 0;
            @Override
            public void run() {
                for (double angle = 0; angle < 2 * Math.PI; angle += Math.PI / 36) {
                    final double x = startRadius * cos(angle);
                    final double z = startRadius * Math.sin(angle);

                    loc.add(x, 0, z);
                    Particle.DustTransition dustOptions = new Particle.DustTransition(c1, c2, 1);
                    loc.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, loc, 1, dustOptions);
                    loc.subtract(x, 0, z);
                }
                startRadius -= radiusDecrease;
                if (startRadius <= endRadius) {
                    this.cancel();
                }
            }
        }.runTaskTimer(MandoMCRemade.getInstance(), 0L, 1L);
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

    public static void helix(Location loc) {
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

    public static void radialWaves(Location loc, Color c1, Color c2, Color c3, Color c4){
        new BukkitRunnable(){
            double t = Math.PI/4;
            public void run(){
                t = t + 0.1*Math.PI;
                for (double theta = 0; theta <= 2*Math.PI; theta = theta + Math.PI/32){
                    double x = t*cos(theta);
                    double y = 2*Math.exp(-0.1*t) * sin(t) + 1.5;
                    double z = t*sin(theta);
                    loc.add(x,y,z);
                    Particle.DustTransition dustOptions = new Particle.DustTransition(c1, c2, 1);
                    loc.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, loc, 1, dustOptions);
                    loc.subtract(x,y,z);

                    theta = theta + Math.PI/64;

                    x = t*cos(theta);
                    y = 2*Math.exp(-0.1*t) * sin(t) + 1.5;
                    z = t*sin(theta);
                    loc.add(x,y,z);
                    dustOptions = new Particle.DustTransition(c3, c4, 1);
                    loc.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, loc, 1, dustOptions);
                    loc.subtract(x,y,z);
                }
                if (t > 7){
                    this.cancel();
                }
            }

        }.runTaskTimer(MandoMCRemade.getInstance(), 0, 1);
    }

    public static void sphere(Location loc, Color c1, Color c2, Color c3, Color c4){
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
                    Particle.DustTransition dustOptions = new Particle.DustTransition(c1, c2, 1);
                    loc.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, loc, 1, dustOptions);
                    loc.subtract(x,y,z);

                    double r2 = 1;
                    double x2 = r2*cos(theta)*sin(t);
                    double y2 = r2*cos(t) + 1.5;
                    double z2 = r2*sin(theta)*sin(t);
                    loc.add(x2,y2,z2);
                    dustOptions = new Particle.DustTransition(c3, c4, 1);
                    loc.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, loc, 1, dustOptions);
                    loc.subtract(x2,y2,z2);
                }
                if (t > Math.PI){
                    this.cancel();

                }
            }

        }.runTaskTimer(MandoMCRemade.getInstance(), 0, 1);
    }

    public static void sphereLoop(Location loc, Color one, Color two){
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
                    Particle.DustTransition dustOptions = new Particle.DustTransition(one, two, 1);
                    loc.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, loc, 1, dustOptions);
                    loc.subtract(x,y,z);
                }
                if (t > 2*Math.PI){
                    this.cancel();
                }
            }

        }.runTaskTimer(MandoMCRemade.getInstance(), 0, 1);
    }

    public static void spiral(Location loc){
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

    public static void spiralGround(Location loc, Color c1, Color c2){
        new BukkitRunnable(){
            double t = 0;
            public void run(){

                t += Math.PI / 4;
                double x = 0.1 * t * Math.cos(t);
                double y = 0.1 * t * 0;
                double z = 0.1 * t * Math.sin(t);
                loc.add(x, y, z);
                Particle.DustTransition dustOptions = new Particle.DustTransition(c1, c2, 1);
                loc.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, loc, 1, dustOptions);
                loc.subtract(x, y, z);

                if (t > Math.PI * 10) {
                    cancel();
                }

            }

        }.runTaskTimer(MandoMCRemade.getInstance(), 0, 1);
    }

    public static void spiralHelix(Location loc){
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

    public static void spiralHelixBoom(Location loc, Color c1, Color c2){
        new BukkitRunnable(){
            double t,x,y,z = 0;
            public void run(){
                t+=Math.PI/2;
                for(double i = 0; i <= 2*Math.PI; i+=Math.PI/2){
                    x = 0.3*(4*Math.PI-t)*cos(t+i);
                    y = 0.2*t;
                    z = 0.3*(4*Math.PI-t)*sin(t+i);
                    loc.add(x, y, z);
                    Particle.DustTransition dustOptions = new Particle.DustTransition(c1, c2, 1);
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

    public static void spinningBeam(Player player, String ability, int range, Color c1, Color c2){
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
                    Particle.DustTransition dustOptions = new Particle.DustTransition(c1, c2, 1);
                    player.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, playerLoc, 1, dustOptions);
                    LivingEntity livingEntity = entityHit(player, playerLoc);
                    if (livingEntity != null) {
                        switch(ability){
                            case "blindI":
                                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, plugin.getConfig().getInt("BlindIDuration")*20, 1));
                                break;
                            case "chokeI":
                                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, plugin.getConfig().getInt("ChokeIDuration")*20, 0));
                                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, plugin.getConfig().getInt("ChokeIDuration")*20, 0));
                                break;
                        }
                    }
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

    public static void orbitingBeam(Player player, String ability, int range, Color c1, Color c2, Color c3, Color c4){
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
            int beamLength = range;
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
                    Particle.DustTransition dustOptions = new Particle.DustTransition(c1, c2, 1);
                    player.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, startLoc, 1, dustOptions);
                    startLoc.subtract(vec);
                }
                // Always spawn a center particle in the same direction the player was facing.
                startLoc.add(dir);
                Particle.DustTransition dustOptions = new Particle.DustTransition(c3, c4, 1);
                player.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, startLoc, 1, dustOptions);
                LivingEntity livingEntity = entityHit(player, startLoc);
                if (livingEntity != null) {
                    switch(ability){
                        case "pushI":
                            Vector vector = genVec(player.getLocation(), livingEntity.getLocation());
                            vector.setY(1);
                            livingEntity.setVelocity(vector);
                            break;
                        case "crushI":
                            livingEntity.damage(plugin.getConfig().getInt("CrushIDamage"), player);
                            break;
                    }
                }
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
        }.runTaskTimer(MandoMCRemade.getInstance(), 0, 1);
    }

    public static void oscillatingBeam(Player player, String ability, int range, Color c1, Color c2) {
        new BukkitRunnable() {
            int circlePoints = 12;
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
                    Particle.DustTransition dustOptions = new Particle.DustTransition(c1, c2, 1);
                    world.spawnParticle(Particle.DUST_COLOR_TRANSITION, playerLoc, 1, dustOptions);
                    LivingEntity livingEntity = entityHit(player, playerLoc);
                    if (livingEntity != null) {
                        switch(ability){
                            case "pullI":
                                Vector vector = genVec(livingEntity.getLocation(), player.getLocation());
                                livingEntity.setVelocity(vector);
                                break;
                            case "drainI":
                                double damage = plugin.getConfig().getDouble("DrainIDamage");
                                livingEntity.damage(damage, player);
                                double health = player.getHealth();
                                if (player.getHealth() < (20.0-damage)) {
                                    player.setHealth(health + damage);
                                } else if (player.getHealth() >= (20-damage) && player.getHealth() <= 20.00) {
                                    player.setHealth(20.00);
                                }
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

    public static void controlledBeam(Player player) {
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

    public static void roof(Player player) {
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

    public static void circularBeam(Player player) {
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

    public static void beam(Player player, int range, Location loc1, Location loc2, Color c1, Color c2){
        // Player's eye location is the starting location for the particle
        Location startLoc = player.getEyeLocation();

        // We need to clone() this location, because we will add() to it later.
        Location particleLoc = startLoc.clone();

        // dir is the Vector direction (offset from 0,0,0) the player is facing in 3D space
        Vector dir = loc1.toVector().subtract(loc2.toVector()).normalize();
        //Vector dir = startLoc.getDirection();

        Vector vecOffset = dir.clone().multiply(0.5);

        new BukkitRunnable(){
            int maxBeamLength = range; // Max beam length
            int beamLength = 0; // Current beam length

            // The run() function runs every X number of ticks - see below
            public void run(){
                beamLength ++; // This is the distance between each particle
                // Kill this task if the beam length is max
                if(beamLength >= maxBeamLength){
                    this.cancel();
                    return;
                }

                // Now we add the direction vector offset to the particle's current location
                particleLoc.add(vecOffset);

                Particle.DustTransition dustOptions = new Particle.DustTransition(c1, c2, 1);
                player.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, particleLoc, 1, dustOptions);
            }
        }.runTaskTimer(MandoMCRemade.getInstance(), 0, 1);
        // 0 is the delay in ticks before starting this task
        // 1 is the how often to repeat the run() function, in ticks (20 ticks are in one second)
    }

    public static void beam(Player player, int length, Color c1, Color c2){
        // Player's eye location is the starting location for the particle
        Location startLoc = player.getEyeLocation();

        // We need to clone() this location, because we will add() to it later.
        Location particleLoc = startLoc.clone();

        World world = startLoc.getWorld(); // We need this later to show the particle

        // dir is the Vector direction (offset from 0,0,0) the player is facing in 3D space
        Vector dir = startLoc.getDirection().normalize();
        //Vector dir = startLoc.getDirection();

        /* vecOffset is used to determine where the next particle should appear
        We are taking the direction and multiplying it by 0.5 to make it appear 1/2 block
          in its continuing Vector direction.
        NOTE: We have to clone() because multiply() modifies the original variable!
        For a straight beam, we only need to calculate this once, as the direction does not change.
        */
        Vector vecOffset = dir.clone().multiply(0.5);

        new BukkitRunnable(){
            int maxBeamLength = length; // Max beam length
            int beamLength = 0; // Current beam length

            // The run() function runs every X number of ticks - see below
            public void run(){
                beamLength ++; // This is the distance between each particle
                // Kill this task if the beam length is max
                if(beamLength >= maxBeamLength){
                    LivingEntity livingEntity = entityHit(player, particleLoc);
                    if (livingEntity != null) {
                        particleLoc.getWorld().strikeLightning(livingEntity.getLocation());
                    }
                    Particle.DustTransition dustOptions = new Particle.DustTransition(c1, c2, 1);
                    player.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, particleLoc, 1, dustOptions);
                    this.cancel();
                    return;
                }

                // Now we add the direction vector offset to the particle's current location
                particleLoc.add(vecOffset);
            }
        }.runTaskTimer(MandoMCRemade.getInstance(), 0, 1);
        // 0 is the delay in ticks before starting this task
        // 1 is the how often to repeat the run() function, in ticks (20 ticks are in one second)
    }
}
