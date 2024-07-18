package net.mandomc.mandomcremade.listeners;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.db.Database;
import net.mandomc.mandomcremade.db.Perks;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
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

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class SaberThrowListener implements Listener {

    private final HashMap<UUID, Long> lightsaberCooldown;
    private final MandoMCRemade plugin;
    private final Database database;

    public SaberThrowListener(HashMap<UUID, Long> lightsaberCooldown, MandoMCRemade plugin, Database database) {
        this.lightsaberCooldown = lightsaberCooldown;
        this.plugin = plugin;
        this.database = database;
    }

    @EventHandler
    public void throwLightsaber(PlayerInteractEvent event) throws SQLException {
        Player player = event.getPlayer();

        if (!isSneakingAndLeftClicking(event, player)) {
            return;
        }

        Perks perks = database.getPerks(player);

        if (perks.getLightsaberThrow() != 1) {
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

    private boolean isSneakingAndLeftClicking(PlayerInteractEvent event, Player player) {
        return player.isSneaking() && (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK);
    }

    private boolean isValidLightsaber(ItemStack inHand) {
        return inHand.getType() == Material.SHIELD && Objects.requireNonNull(inHand.getItemMeta()).hasCustomModelData();
    }

    private void activateLightsaberCooldown(Player player) {
        lightsaberCooldown.put(player.getUniqueId(), System.currentTimeMillis());
        player.sendMessage(MandoMCRemade.str("&6You threw your lightsaber!"));
        Energy.getPlayerEnergy(player.setEnergy(playerEnergy.getEnergy() - 10);
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
