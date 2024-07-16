package net.mandomc.mandomcremade.listeners;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.utility.Messages;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class SaberThrowListener implements Listener {

    private final HashMap<UUID, Long> lightsaberCooldown;
    MandoMCRemade plugin;

    public SaberThrowListener(HashMap<UUID, Long> lightsaberCooldown, MandoMCRemade plugin) {
        this.lightsaberCooldown = lightsaberCooldown;
        this.plugin = plugin;
    }

    @EventHandler
    public void throwLightsaber(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if ((player.isSneaking() && event.getAction() == Action.LEFT_CLICK_AIR) || (player.isSneaking() && event.getAction() == Action.LEFT_CLICK_BLOCK)) {
            ItemStack inHand = player.getInventory().getItemInMainHand();
            if (inHand.getType() == Material.SHIELD && Objects.requireNonNull(inHand.getItemMeta()).hasCustomModelData()) {
                if (!this.lightsaberCooldown.containsKey(player.getUniqueId())) {
                    this.lightsaberCooldown.put(player.getUniqueId(), System.currentTimeMillis());
                    Messages.msg(player, "&6You threw your lightsaber!");
                    playerThrowEvent(player, player.getLocation(), targetedEnemy(player,
                            plugin.getConfig().getInt("SaberThrowRange"),
                            plugin.getConfig().getInt("SaberThrowThreshold")));
                } else {
                    long timeElapsed = System.currentTimeMillis() - lightsaberCooldown.get(player.getUniqueId());
                    if (timeElapsed >= plugin.getConfig().getInt("SaberThrowCooldown")* 1000L) {
                        this.lightsaberCooldown.remove(player.getUniqueId());
                    } else {
                        Messages.msg(player, "&6You are too tired to throw a lightsaber, try again in &c" + ((plugin.getConfig().getInt("SaberThrowCooldown")*1000L - timeElapsed) / 1000) + " &6seconds!");
                    }
                }
            }
        }
    }

    public static Location targetedEnemy(Player player, int range, double threshold) {
        // Get the player's line of sight
        List<Block> lineOfSight = player.getLineOfSight(null, range);

        for (Block block : lineOfSight) {
            Location location = block.getLocation();
            // Get nearby entities within a small radius (e.g., 1.5 blocks)
            for (Entity entity : location.getWorld().getNearbyEntities(location, 1.5, 1.5, 1.5)) {
                // Ensure it's not the player itself
                if (entity != player && entity instanceof LivingEntity) {
                    // Get player's eye location and the direction they're looking
                    Vector playerEyeLocation = player.getEyeLocation().toVector();
                    Vector playerDirection = player.getLocation().getDirection();

                    // Get entity's location
                    Vector entityLocation = entity.getLocation().toVector();

                    // Calculate the direction vector from player to entity
                    Vector toEntity = entityLocation.subtract(playerEyeLocation).normalize();

                    // Calculate the dot product of the two direction vectors
                    double dotProduct = playerDirection.dot(toEntity);

                    // Check if the dot product is greater than the threshold (cosine of angle difference)
                    if (dotProduct >= threshold) {
                        return entity.getLocation();
                    }
                }
            }
        }
        return player.getLocation().add(player.getLocation().getDirection().multiply(10));
    }

    public void playerThrowEvent(Player player, Location start, Location destination) {

        ItemStack eggItem = player.getInventory().getItemInMainHand();
        ItemStack throwStack = new ItemStack(eggItem);
        throwStack.setAmount(1);

        ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation().add(0, 0.5, 0), EntityType.ARMOR_STAND);

        armorStand.setArms(true);
        armorStand.setGravity(false);
        armorStand.setVisible(false);
        armorStand.setSmall(true);
        armorStand.setMarker(true);
        Objects.requireNonNull(armorStand.getEquipment()).setItemInMainHand(eggItem);
        armorStand.setRightArmPose(new EulerAngle(Math.toRadians(0), Math.toRadians(0), Math.toRadians(90)));

        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);

        Vector vector = destination.subtract(start).toVector();

        new BukkitRunnable() {
            final int distance = plugin.getConfig().getInt("SaberThrowRange");
            int i = 0;

            public void run() {

                EulerAngle rot = armorStand.getRightArmPose();
                EulerAngle rotNew = rot.add(plugin.getConfig().getInt("SaberThrowDegrees"), 0, 0);
                armorStand.setRightArmPose(rotNew);
                armorStand.teleport(armorStand.getLocation().add(vector.normalize()));

                if (armorStand.getTargetBlockExact(1) != null && !Objects.requireNonNull(armorStand.getTargetBlockExact(1)).isPassable()) {
                    if (!armorStand.isDead()) {
                        armorStand.remove();
                        if (player.getInventory().firstEmpty() != -1) {
                            player.getInventory().addItem(throwStack);
                        } else {
                            player.getWorld().dropItemNaturally(player.getLocation(), throwStack);
                        }
                        cancel();
                    }
                }

                for (Entity entity : armorStand.getLocation().getChunk().getEntities()) {
                    if (!armorStand.isDead()) {
                        if (armorStand.getLocation().distanceSquared(entity.getLocation()) <= 1) {
                            if (entity != player && entity != armorStand) {
                                if (entity instanceof LivingEntity livingEntity) {
                                    livingEntity.damage(plugin.getConfig().getInt("SaberThrowDamage"), player);
                                }
                            }
                        }
                    }
                }

                if (i > distance) {
                    if (!armorStand.isDead()) {
                        armorStand.remove();
                        if (player.getInventory().firstEmpty() != -1) {
                            player.getInventory().addItem(throwStack);
                        } else {
                            player.getWorld().dropItemNaturally(player.getLocation(), throwStack);
                        }
                        cancel();
                    }
                }

                i++;
            }
        }.runTaskTimer(MandoMCRemade.getInstance(), 0L, 1L);

    }

}