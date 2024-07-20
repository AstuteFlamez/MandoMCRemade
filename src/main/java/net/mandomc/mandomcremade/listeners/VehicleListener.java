package net.mandomc.mandomcremade.listeners;

import net.mandomc.mandomcremade.managers.VehicleManager;
import net.mandomc.mandomcremade.objects.Vehicle;
import net.mandomc.mandomcremade.utility.CustomItems;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.Iterator;
import java.util.UUID;

public class VehicleListener implements Listener {

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        PlayerInventory inventory = player.getInventory();

        switch (event.getAction()) {
            case RIGHT_CLICK_AIR:
            case RIGHT_CLICK_BLOCK:

                ItemStack item = inventory.getItemInMainHand();
                ItemMeta meta = item.getItemMeta();
                if (meta == null) return;
                String name = meta.getDisplayName();

                String tieFighter = CustomItems.tieFighter().getItemMeta().getDisplayName();
                String redXWing = CustomItems.xWing("red").getItemMeta().getDisplayName();
                String blueXWing = CustomItems.xWing("blue").getItemMeta().getDisplayName();
                String greenXWing = CustomItems.xWing("green").getItemMeta().getDisplayName();

                if (name.equals(tieFighter) || name.equals(redXWing) || name.equals(blueXWing) || name.equals(greenXWing)) {
                    new VehicleManager(player.getUniqueId());
                }
                break;
            case LEFT_CLICK_AIR:
                /*testTorpedo(getBlockLocation(player, 40));
                testTorpedo(getBlockLocation(player, -40));*/
                //player.getWorld().spawnParticle(Particle.ENCHANTED_HIT, getBlockLocation(player, -40), 0);
                //player.getWorld().spawnParticle(Particle.ENCHANTED_HIT, getBlockLocation(player, 40), 0);
                for (Vehicle vehicle : VehicleManager.vehicles) {
                    if (vehicle.getPilot() == null) return;
                    if (vehicle.getPilot().equals(uuid)) {
                        LivingEntity entity = vehicle.getModelMob();

                        testTorpedo(getBlockLocation(entity, 4, 2));
                        testTorpedo(getBlockLocation(entity, 4, -2));
                        //VehicleManager.shootTorpedoes(player);
                    }
                }
                break;

        }
    }

    public Location getBlockLocation(LivingEntity entity, int yawRotation, int pitchRotation) {
        // Get the plane's current location and direction it is looking
        Location entityLocation = entity.getEyeLocation();

        // Get the yaw and pitch from the entity's eye location
        float yaw = entityLocation.getYaw() + yawRotation;
        float pitch = entityLocation.getPitch() + pitchRotation;

        // Convert yaw and pitch to radians
        double yawRad = Math.toRadians(yaw);
        double pitchRad = Math.toRadians(pitch);

        // Calculate the direction vector based on yaw and pitch
        double x = -Math.cos(pitchRad) * Math.sin(yawRad);
        double y = -Math.sin(pitchRad);
        double z = Math.cos(pitchRad) * Math.cos(yawRad);

        Vector direction = new Vector(x, y, z).normalize(); // Normalize the vector

        // Get the target location 4 blocks away in the direction the plane is moving
        Location targetLocation = entityLocation.add(direction.multiply(4));

        return targetLocation;
    }

    public void testTorpedo(Location location) {
        Vector direction = location.getDirection().normalize().multiply(2); // Adjust the speed if necessary
        ItemStack redstoneDust = new ItemStack(Material.REDSTONE);
        Item redstoneDustItem = location.getWorld().dropItem(location, redstoneDust);
        redstoneDustItem.setVelocity(direction);
    }

    @EventHandler
    public void onPlayerInteractWithEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Entity clickedEntity = event.getRightClicked();

        if (!(clickedEntity instanceof LivingEntity)) return;

        for (Vehicle vehicle : VehicleManager.vehicles) {
            LivingEntity phantom = vehicle.getVehicleMob();
            LivingEntity zombie = vehicle.getModelMob();

            if (clickedEntity == phantom || clickedEntity == zombie) {
                if (vehicle.getPilot() != null) return;

                vehicle.setPilot(uuid);
                phantom.addPassenger(player);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        Iterator<Vehicle> iterator = VehicleManager.vehicles.iterator();
        while (iterator.hasNext()) {
            Vehicle vehicle = iterator.next();
            if (vehicle.getOwner().equals(uuid)) {
                iterator.remove();
                VehicleManager.removeVehicle(vehicle);
            }
        }
    }

    @EventHandler
    public void onPlayerDismount(EntityDismountEvent event) {
        Entity entity = event.getDismounted();
        if (!(entity instanceof LivingEntity livingEntity)) return;

        for (Vehicle vehicle : VehicleManager.vehicles) {
            if(vehicle.getVehicleMob() == livingEntity) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onCombustion(EntityCombustEvent event) {
        Entity entity = event.getEntity();
        for (Vehicle vehicle : VehicleManager.vehicles) {
            Entity phantom = vehicle.getVehicleMob();
            Entity zombie = vehicle.getModelMob();
            if (entity == phantom || entity == zombie) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityAttack(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();

        if (!(entity instanceof LivingEntity)) return;

        for (Vehicle vehicle : VehicleManager.vehicles) {
            Entity phantom = vehicle.getVehicleMob();
            Entity zombie = vehicle.getModelMob();
            if (damager == phantom || damager == zombie) {
                event.setCancelled(true);
            }
        }
    }
}
