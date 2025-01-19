package net.mandomc.mandomcremade.listeners;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.managers.StaminaManager;
import net.mandomc.mandomcremade.db.Database;
import net.mandomc.mandomcremade.db.PerksTable;
import net.mandomc.mandomcremade.db.data.Perks;
import net.mandomc.mandomcremade.objects.Stamina;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class SaberListener implements Listener {

    private final HashMap<UUID, Long> lightsaberCooldown;
    private final MandoMCRemade plugin;
    private final HashMap<UUID, BukkitRunnable> activeSounds = new HashMap<>();
    private Set<UUID> reflectingPlayers;
    private final StaminaManager staminaManager;

    public SaberListener(HashMap<UUID, Long> lightsaberCooldown, MandoMCRemade plugin, StaminaManager staminaManager) {
        this.lightsaberCooldown = lightsaberCooldown;
        this.plugin = plugin;
        this.staminaManager = staminaManager;
        this.reflectingPlayers = new HashSet<>();
    }

    @EventHandler
    public void onPlayerBlock(Player player) {
        if (isHoldingSaber(player) && player.isBlocking()) {
            reflectingPlayers.add(player.getUniqueId());
        } else {
            reflectingPlayers.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        Entity hitEntity = event.getEntity();
        if (hitEntity instanceof Arrow arrow) {
            if (event.getHitEntity() instanceof Player player && 
                reflectingPlayers != null && // Ensure reflectingPlayers is not null
                reflectingPlayers.contains(player.getUniqueId())) {
                
                Stamina stamina = staminaManager.getStamina(player); // Assume you have a staminaManager instance
    
                // Check if the player has enough stamina to reflect
                if (stamina != null && stamina.getStaminaAmount() > 50) {
                    reflectProjectile(player, arrow);
    
                    // Reduce stamina when reflecting
                    staminaManager.handleStaminaDecrease(player, stamina, 50);
                }
            }
        }
    }
    


    private void reflectProjectile(Player player, Arrow projectile) {
        // Calculate the new direction
        Vector direction = player.getEyeLocation().getDirection().normalize();

        // Redirect the projectile
        projectile.setVelocity(direction.multiply(2)); // Adjust speed multiplier as needed

        // Play sound and particle effects
        Location playerLocation = player.getLocation();
        player.getWorld().playSound(playerLocation, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1.0f, 1.5f);
        player.getWorld().spawnParticle(Particle.CRIT, playerLocation, 10, 0.2, 0.2, 0.2);
    }

    private boolean isHoldingSaber(Player player) {
        // Replace with your own item check logic
        return player.getInventory().getItemInMainHand().getType().name().contains("Lightsaber");
    }

    @EventHandler
    public void throwLightsaber(PlayerInteractEvent event) throws SQLException {
        Player player = event.getPlayer();

        if (!isSneakingAndLeftClicking(event, player)) {
            return;
        }

        Perks perks = PerksTable.getPerks(player);

        if (perks.getLightsaberThrow()) {
            player.sendMessage(MandoMCRemade.str("You don't have this perk unlocked"));
            return;
        }

        ItemStack inHand = player.getInventory().getItemInMainHand();

        if (!isValidLightsaber(inHand)) {
            return;
        }

        if (!lightsaberCooldown.containsKey(player.getUniqueId())) {
            activateLightsaberCooldown(player);
        } else {
            handleCooldown(player);
        }
    }

    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack newItem = player.getInventory().getItem(event.getNewSlot());

        // Check if the new item is a "lightsaber"
        if (isSaber(newItem)) {
            startSaberSound(player);
        } else {
            stopSaberSound(player);
        }
    }

    public static boolean isSaber(ItemStack item) {
        return item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() &&
                item.getItemMeta().getDisplayName().toLowerCase().contains("lightsaber");
    }

    private void startSaberSound(Player player) {
        UUID playerId = player.getUniqueId();

        // If the sound task is already running, do nothing
        if (activeSounds.containsKey(playerId)) {
            return;
        }

        // Create a new repeating task to play the sound at the player's location
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                if (!isSaber(player.getInventory().getItemInMainHand())) {
                    stopSaberSound(player); // Stop if the player switches items
                    return;
                }

                // Play the sound at the player's current location
                player.playSound(player.getLocation(), "melee.lightsaber.held", 0.1f, 1.0f);
            }
        };

        task.runTaskTimer(plugin, 0L, 1L); // Recheck every tick (20 ticks = 1 second)
        activeSounds.put(playerId, task);
    }

    private void stopSaberSound(Player player) {
        UUID playerId = player.getUniqueId();

        // Cancel and remove the task for this player if it exists
        if (activeSounds.containsKey(playerId)) {
            activeSounds.get(playerId).cancel();
            activeSounds.remove(playerId);
        }
    }
    private boolean isSneakingAndLeftClicking(PlayerInteractEvent event, Player player) {
        return player.isSneaking() && (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK);
    }

    private boolean isValidLightsaber(ItemStack inHand) {
        return inHand.getType() == Material.SHIELD && Objects.requireNonNull(inHand.getItemMeta()).hasCustomModelData();
    }

    private void activateLightsaberCooldown(Player player) {
        lightsaberCooldown.put(player.getUniqueId(), System.currentTimeMillis());
        player.sendMessage(MandoMCRemade.str("&6You threw your lightsaber!"));
        //Energy.getPlayerEnergy(player.setEnergy(playerEnergy.getEnergy() - 10);
        playerThrowEvent(player, player.getLocation(), targetedEnemy(player,
                plugin.getConfig().getInt("SaberThrowRange"),
                plugin.getConfig().getDouble("SaberThrowThreshold")));
    }

    private void handleCooldown(Player player) {
        long timeElapsed = System.currentTimeMillis() - lightsaberCooldown.get(player.getUniqueId());
        int cooldown = plugin.getConfig().getInt("SaberThrowCooldown") * 1000;

        if (timeElapsed >= cooldown) {
            lightsaberCooldown.remove(player.getUniqueId());
        } else {
            long timeRemaining = (cooldown - timeElapsed) / 1000;
            player.sendMessage(MandoMCRemade.str("&6You are too tired to throw a lightsaber, try again in &c" + timeRemaining + " &6seconds!"));
        }
    }

    public static Location targetedEnemy(Player player, int range, double threshold) {
        List<Block> lineOfSight = player.getLineOfSight(null, range);

        for (Block block : lineOfSight) {
            Location location = block.getLocation();
            World world = location.getWorld();
            assert world != null;
            for (Entity entity : world.getNearbyEntities(location, 1.5, 1.5, 1.5)) {
                if (isValidTarget(player, entity, threshold)) {
                    return entity.getLocation();
                }
            }
        }

        return player.getLocation().add(player.getLocation().getDirection().multiply(10));
    }

    private static boolean isValidTarget(Player player, Entity entity, double threshold) {
        if (entity == player || !(entity instanceof LivingEntity)) {
            return false;
        }

        Vector playerEyeLocation = player.getEyeLocation().toVector();
        Vector playerDirection = player.getLocation().getDirection();
        Vector entityLocation = entity.getLocation().toVector();
        Vector toEntity = entityLocation.subtract(playerEyeLocation).normalize();

        double dotProduct = playerDirection.dot(toEntity);

        return dotProduct >= threshold;
    }

    public void playerThrowEvent(Player player, Location start, Location destination) {
        ItemStack eggItem = player.getInventory().getItemInMainHand();
        ItemStack throwStack = new ItemStack(eggItem);
        throwStack.setAmount(1);

        ArmorStand armorStand = createArmorStand(player, eggItem);

        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);

        Vector vector = destination.subtract(start).toVector();

        new BukkitRunnable() {
            final int distance = plugin.getConfig().getInt("SaberThrowRange");
            int i = 0;

            public void run() {
                rotateAndMoveArmorStand(armorStand, vector);
                if (checkCollision(player, armorStand) || i > distance) {
                    armorStand.remove();
                    if (player.getInventory().firstEmpty() != -1) {
                        player.getInventory().addItem(throwStack);
                    } else {
                        player.getWorld().dropItemNaturally(player.getLocation(), throwStack);
                    }
                    cancel();
                }
                i++;
            }
        }.runTaskTimer(MandoMCRemade.getInstance(), 0L, 1L);
    }

    private ArmorStand createArmorStand(Player player, ItemStack eggItem) {
        ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation().add(100, 0.5, 100), EntityType.ARMOR_STAND);
        armorStand.setArms(true);
        armorStand.setGravity(false);
        armorStand.setVisible(false);
        armorStand.setSmall(true);
        armorStand.setMarker(true);
        Objects.requireNonNull(armorStand.getEquipment()).setItemInMainHand(eggItem);
        armorStand.setRightArmPose(new EulerAngle(Math.toRadians(0), Math.toRadians(0), Math.toRadians(90)));
        armorStand.teleport(player.getLocation().add(0, 0.5, 0));
        return armorStand;
    }

    private void rotateAndMoveArmorStand(ArmorStand armorStand, Vector vector) {
        EulerAngle rot = armorStand.getRightArmPose();
        EulerAngle rotNew = rot.add(plugin.getConfig().getInt("SaberThrowDegrees"), 0, 0);
        armorStand.setRightArmPose(rotNew);
        armorStand.teleport(armorStand.getLocation().add(vector.normalize()));
    }

    private boolean checkCollision(Player player, ArmorStand armorStand) {
        if (armorStand.getTargetBlockExact(1) != null && !Objects.requireNonNull(armorStand.getTargetBlockExact(1)).isPassable()) {
            return true;
        }

        for (Entity entity : armorStand.getLocation().getChunk().getEntities()) {
            if (armorStand.getLocation().distanceSquared(entity.getLocation()) <= 1 && entity != player && entity != armorStand) {
                if (entity instanceof LivingEntity livingEntity) {
                    livingEntity.damage(plugin.getConfig().getInt("SaberThrowDamage"), player);
                }
            }
        }

        return false;
    }
}
