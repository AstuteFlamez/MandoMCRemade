package net.mandomc.mandomcremade.listeners;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.utility.Messages;
import org.bukkit.Location;
import org.bukkit.Material;
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
import java.util.Objects;
import java.util.UUID;

public class LightsaberThrowEvent implements Listener {

    private final HashMap<UUID, Long> lightsaberCooldown;

    public LightsaberThrowEvent(HashMap<UUID, Long> lightsaberCooldown) {
        this.lightsaberCooldown = lightsaberCooldown;
    }

    public void playerThrowEvent(Player player) {

        ItemStack eggItem = player.getItemInUse();
        assert eggItem != null;
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

        Location destination = player.getLocation().add(player.getLocation().getDirection().multiply(10));
        Vector vector = destination.subtract(player.getLocation()).toVector();

        new BukkitRunnable() {
            final int distance = 30;
            int i = 0;

            public void run() {

                EulerAngle rot = armorStand.getRightArmPose();
                EulerAngle rotNew = rot.add(20, 0, 0);
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
                                if (entity instanceof LivingEntity) {
                                    LivingEntity livingEntity = (LivingEntity) entity;
                                    livingEntity.damage(24.0, player);
                                    armorStand.remove();
                                    if (player.getInventory().firstEmpty() != -1) {
                                        player.getInventory().addItem(throwStack);
                                    } else {
                                        player.getWorld().dropItemNaturally(player.getLocation(), throwStack);
                                    }
                                    cancel();
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

    @EventHandler
    public void throwLightsaber(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if ((player.isSneaking() && event.getAction() == Action.LEFT_CLICK_AIR) || (player.isSneaking() && event.getAction() == Action.LEFT_CLICK_BLOCK)) {
            ItemStack inHand = player.getItemInUse();
            assert inHand != null;
            if (inHand.getType() == Material.SHIELD && Objects.requireNonNull(inHand.getItemMeta()).hasCustomModelData()) {
                if (!this.lightsaberCooldown.containsKey(player.getUniqueId())) {
                    this.lightsaberCooldown.put(player.getUniqueId(), System.currentTimeMillis());
                    Messages.msg(player, "&6You threw your lightsaber!");
                    playerThrowEvent(player);
                } else {
                    long timeElapsed = System.currentTimeMillis() - lightsaberCooldown.get(player.getUniqueId());
                    if (timeElapsed >= 10000) {
                        this.lightsaberCooldown.remove(player.getUniqueId());
                    } else {
                        Messages.msg(player, "&6You are too tired to throw a lightsaber, try again in &c" + ((10000 - timeElapsed) / 1000) + " &6seconds!");
                    }
                }
            }
        }
    }

}

