package net.mandomc.mandomcremade.utility;

import net.mandomc.mandomcremade.MandoMCRemade;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import static net.mandomc.mandomcremade.utility.Utilities.genVec;

public class ForcePowers {

    public static void leap(Player player, int level, MandoMCRemade plugin){
        FileConfiguration config = plugin.getConfig();
        switch(level){
            case 1:
                player.setVelocity(player.getLocation().getDirection().multiply(plugin.getConfig().getInt("LeapIVelocity")).setY(1));
                break;
            case 2:
                player.setVelocity(player.getLocation().getDirection().multiply(plugin.getConfig().getInt("LeapIIVelocity")).setY(1));
        }
        new BukkitRunnable(){

            @Override
            public void run() {
                if(player.getLocation().subtract(0,1,0).getBlock().getType() == Material.AIR){
                    Particle.DustTransition dustOptions = new Particle.DustTransition(Utilities.hexToColor(config.getString("LeapHexCode1")), Utilities.hexToColor(config.getString("LeapHexCode1")), 1);
                    player.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, player.getLocation(), 1, dustOptions);
                }else{
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 5, 1);
    }

    public static void pull(Player player, int level, MandoMCRemade plugin){
        FileConfiguration config = plugin.getConfig();
        switch(level){
            case 1:
                Particles.oscillatingBeam(player,
                        "pullI",
                        plugin.getConfig().getInt("PullIRange"),
                        Utilities.hexToColor(config.getString("PullHexCode1")),
                        Utilities.hexToColor(config.getString("PullHexCode2")));
                break;
            case 2:
                int radius = MandoMCRemade.getInstance().getConfig().getInt("PullIIRadius");
                for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
                    if (entity instanceof LivingEntity livingEntity) {
                        Vector vector2 = genVec(livingEntity.getLocation(), player.getLocation());
                        livingEntity.setVelocity(vector2);
                    }
                }
                Particles.circleInwards(player.getLocation(),
                        radius,
                        Utilities.hexToColor(config.getString("PullHexCode1")),
                        Utilities.hexToColor(config.getString("PullHexCode2")));
        }
    }

    public static void push(Player player, int level, MandoMCRemade plugin){
        FileConfiguration config = plugin.getConfig();
        switch(level){
            case 1:
                Particles.orbitingBeam(player,
                        "pushI",
                        plugin.getConfig().getInt("PushIRange"),
                        Utilities.hexToColor(config.getString("PushHexCode1")),
                        Utilities.hexToColor(config.getString("PushHexCode2")),
                        Utilities.hexToColor(config.getString("PushHexCode3")),
                        Utilities.hexToColor(config.getString("PushHexCode4")));
                break;
            case 2:
                int radius = MandoMCRemade.getInstance().getConfig().getInt("PushIIRadius");
                for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
                    if (entity instanceof LivingEntity livingEntity) {
                        Vector vector = genVec(player.getLocation(), livingEntity.getLocation());
                        vector.setY(1);
                        livingEntity.setVelocity(vector);
                    }
                }
                Particles.circleOutwards(player.getLocation(),
                        radius,
                        Utilities.hexToColor(config.getString("PushHexCode1")),
                        Utilities.hexToColor(config.getString("PushHexCode2")));
        }
    }

    public static void dash(Player player, MandoMCRemade plugin) {
        FileConfiguration config = plugin.getConfig();
        Location start = player.getLocation();
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
                start,
                player.getLocation(),
                Utilities.hexToColor(config.getString("DashHexCode1")),
                Utilities.hexToColor(config.getString("DashHexCode2")));
    }

    public static void scream(Player player, int level, MandoMCRemade plugin) {
        FileConfiguration config = plugin.getConfig();
        switch(level){
            case 1:
                player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, plugin.getConfig().getInt("ScreamIDuration")*20, 0));
                Particles.sphere(player.getLocation(),
                        Utilities.hexToColor(config.getString("ScreamHexCode1")),
                        Utilities.hexToColor(config.getString("ScreamHexCode2")),
                        Utilities.hexToColor(config.getString("ScreamHexCode3")),
                        Utilities.hexToColor(config.getString("ScreamHexCode4")));
                break;
            case 2:
                player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, plugin.getConfig().getInt("ScreamIIDuration")*20, 1));
                Particles.sphereLoop(player.getLocation(), Utilities.hexToColor(config.getString("ScreamHexCode1")),
                        Utilities.hexToColor(config.getString("ScreamHexCode2")));
                break;
        }
    }

    public static void confusion(Player player, int level, MandoMCRemade plugin){
        FileConfiguration config = plugin.getConfig();
        int radius;
        switch(level){
            case 1:
                radius = MandoMCRemade.getInstance().getConfig().getInt("ConfusionIRadius");
                for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
                    if (entity instanceof LivingEntity livingEntity) {
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, plugin.getConfig().getInt("ConfusionIDuration")*20, 1));
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, plugin.getConfig().getInt("ConfusionIDuration")*20, 1));
                    }
                }
                break;
            case 2:
                radius = MandoMCRemade.getInstance().getConfig().getInt("ConfusionIIRadius");
                for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
                    if (entity instanceof LivingEntity livingEntity) {
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, plugin.getConfig().getInt("ConfusionIIDuration")*20, 1));
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, plugin.getConfig().getInt("ConfusionIIDuration")*20, 1));
                    }
                }
                break;
        }
        Particles.spiralGround(player.getLocation(),
                Utilities.hexToColor(config.getString("ConfusionHexCode1")),
                Utilities.hexToColor(config.getString("ConfusionHexCode2")));
    }

    public static void stasis(Player player, int level, MandoMCRemade plugin){
        FileConfiguration config = plugin.getConfig();
        int radius;
        switch(level){
            case 1:
                radius = MandoMCRemade.getInstance().getConfig().getInt("StasisIRadius");
                for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
                    if (entity instanceof LivingEntity livingEntity) {
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, plugin.getConfig().getInt("StasisIDuration")*20, 5));
                    }
                }
                break;
            case 2:
                radius = MandoMCRemade.getInstance().getConfig().getInt("StasisIIRadius");
                for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
                    if (entity instanceof LivingEntity livingEntity) {
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, plugin.getConfig().getInt("StasisIIDuration")*20, 5));
                    }
                }
                break;
        }
        Particles.radialWaves(player.getLocation(), Utilities.hexToColor(config.getString("StasisHexCode1")),
                Utilities.hexToColor(config.getString("StasisHexCode2")),
                Utilities.hexToColor(config.getString("StasisHexCode3")),
                Utilities.hexToColor(config.getString("StasisHexCode4")));
    }

    public static void blind(Player player, int level, MandoMCRemade plugin){
        FileConfiguration config = plugin.getConfig();
        int radius;
        switch(level){
            case 1:
                Particles.spinningBeam(player, "blindI", plugin.getConfig().getInt("BlindIRange"),
                        Utilities.hexToColor(config.getString("BlindHexCode1")),
                        Utilities.hexToColor(config.getString("BlindHexCode2")));
                break;
            case 2:
                radius = MandoMCRemade.getInstance().getConfig().getInt("BlindIIRadius");
                for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
                    if (entity instanceof LivingEntity livingEntity) {
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, plugin.getConfig().getInt("BlindIIDuration")*20, 1));
                    }
                }
                Particles.spiralGround(player.getLocation(),
                        Utilities.hexToColor(config.getString("BlindHexCode1")),
                        Utilities.hexToColor(config.getString("BlindHexCode2")));
                break;
        }
    }

    public static void stealth(Player player, int level, MandoMCRemade plugin){
        FileConfiguration config = plugin.getConfig();
        switch(level){
            case 1:
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, plugin.getConfig().getInt("StealthIDuration")*20, 1));
                Particles.spiralHelixBoom(player.getLocation(),
                        Utilities.hexToColor(config.getString("StealthHexCode1")),
                        Utilities.hexToColor(config.getString("StealthHexCode2")));
                break;
            case 2:
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, plugin.getConfig().getInt("StealthIIDuration")*20, 1));
                Particles.spiralHelixBoom(player.getLocation(),
                        Utilities.hexToColor(config.getString("StealthHexCode1")),
                        Utilities.hexToColor(config.getString("StealthHexCode2")));
                break;
        }
    }

    public static void light(Player player, int level, MandoMCRemade plugin){
        FileConfiguration config = plugin.getConfig();
        switch(level){
            case 1:
                player.setAbsorptionAmount(plugin.getConfig().getInt("LightIAmount"));
                Particles.sphere(player.getLocation(),
                        Utilities.hexToColor(config.getString("LightHexCode1")),
                        Utilities.hexToColor(config.getString("LightHexCode2")),
                        Utilities.hexToColor(config.getString("LightHexCode3")),
                        Utilities.hexToColor(config.getString("LightHexCode4")));
                break;
        }
    }

    public static void heal(Player player, int level, MandoMCRemade plugin){
        FileConfiguration config = plugin.getConfig();
        double heal, health;
        switch(level){
            case 1:
                heal = config.getDouble("HealIAmount");
                health = player.getHealth();
                if (player.getHealth() < (20-heal)) {
                    player.setHealth(health + heal);
                } else if (player.getHealth() >= (20-heal) && player.getHealth() <= 20.00) {
                    player.setHealth(20.00);
                }
                break;
            case 2:
                heal = config.getDouble("HealIIAmount");
                health = player.getHealth();
                if (player.getHealth() < (20-heal)) {
                    player.setHealth(health + heal);
                } else if (player.getHealth() >= (20-heal) && player.getHealth() <= 20.00) {
                    player.setHealth(20.00);
                }
                break;
        }
        Particles.sphere(player.getLocation(),
                Utilities.hexToColor(config.getString("HealHexCode1")),
                Utilities.hexToColor(config.getString("HealHexCode2")),
                Utilities.hexToColor(config.getString("HealHexCode3")),
                Utilities.hexToColor(config.getString("HealHexCode4")));
    }

    public static void choke(Player player, int level, MandoMCRemade plugin){
        FileConfiguration config = plugin.getConfig();
        switch(level){
            case 1:
                Particles.spinningBeam(player, "choke",
                        config.getInt("ChokeIRange"),
                        Utilities.hexToColor(config.getString("ChokeHexCode1")),
                        Utilities.hexToColor(config.getString("ChokeHexCode2")));
                break;
            case 2:
                int radius = config.getInt("ChokeIIRadius");
                for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
                    if (entity instanceof LivingEntity livingEntity) {
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, plugin.getConfig().getInt("ChokeIIDuration")*20, 0));
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, plugin.getConfig().getInt("ChokeIIDuration")*20, 0));
                    }
                }
                Particles.circleOutwards(player.getLocation(),
                        radius,
                        Utilities.hexToColor(config.getString("ChokeHexCode1")),
                        Utilities.hexToColor(config.getString("ChokeHexCode2")));
                break;
        }
    }

    public static void crush(Player player, int level, MandoMCRemade plugin){
        FileConfiguration config = plugin.getConfig();
        switch(level){
            case 1:
                Particles.orbitingBeam(player, "crushI",
                        config.getInt("CrushIRange"),
                        Utilities.hexToColor(config.getString("CrushHexCode1")),
                        Utilities.hexToColor(config.getString("CrushHexCode2")),
                        Utilities.hexToColor(config.getString("CrushHexCode3")),
                        Utilities.hexToColor(config.getString("CrushHexCode4")));
                break;
            case 2:
                int radius = config.getInt("CrushIIRadius");
                int damage = config.getInt("CrushIIDamage");
                for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        livingEntity.damage(damage, player);
                    }
                }
                Particles.circleInwards(player.getLocation(),
                        radius,
                        Utilities.hexToColor(config.getString("CrushHexCode1")),
                        Utilities.hexToColor(config.getString("CrushHexCode2")));
                break;
        }
    }

    public static void drain(Player player, int level, MandoMCRemade plugin){
        FileConfiguration config = plugin.getConfig();
        switch(level){
            case 1:
                Particles.oscillatingBeam(player, "drainI",
                        config.getInt("DrainIRange"),
                        Utilities.hexToColor(config.getString("DrainHexCode1")),
                        Utilities.hexToColor(config.getString("DrainHexCode2")));
                break;
            case 2:
                int radius = config.getInt("DrainIIRadius");
                int damage = config.getInt("DrainIIDamage");
                for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
                    if (entity instanceof LivingEntity livingEntity) {
                        livingEntity.damage(damage, player);
                        double health = player.getHealth();
                        if (player.getHealth() < (20.0-damage)) {
                            player.setHealth(health + damage);
                        } else if (player.getHealth() >= (20-damage) && player.getHealth() <= 20.00) {
                            player.setHealth(20.00);
                        }
                    }
                }
                Particles.spiralGround(player.getLocation(),
                        Utilities.hexToColor(config.getString("DrainHexCode1")),
                        Utilities.hexToColor(config.getString("DrainHexCode2")));
                break;
        }
    }

    public static void lightning(Player player, int level, MandoMCRemade plugin){
        FileConfiguration config = plugin.getConfig();
        switch(level){
            case 1:
                Particles.beam(player,
                        config.getInt("LightningIRange"),
                        Utilities.hexToColor(config.getString("LightningHexCode1")),
                        Utilities.hexToColor(config.getString("LightningHexCode2")));
                break;
            case 2:
                int radius = config.getInt("LightningIIRadius");
                for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
                    if (entity instanceof LivingEntity livingEntity) {
                        Particles.beam(player,
                                player.getEyeLocation(),
                                livingEntity.getEyeLocation(),
                                Utilities.hexToColor(config.getString("LightningHexCode1")),
                                Utilities.hexToColor(config.getString("LightningHexCode2")));
                        player.getWorld().strikeLightning(livingEntity.getLocation());
                    }
                }
                break;
        }
    }
}
