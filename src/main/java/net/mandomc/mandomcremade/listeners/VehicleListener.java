package net.mandomc.mandomcremade.listeners;

import net.mandomc.mandomcremade.managers.VehicleManager;
import net.mandomc.mandomcremade.objects.Vehicle;
import net.mandomc.mandomcremade.utility.CustomItems;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Iterator;
import java.util.UUID;

public class VehicleListener implements Listener {

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        PlayerInventory inventory = player.getInventory();
        ItemStack item = inventory.getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return;
        String name = meta.getDisplayName();

        String tieFighter = CustomItems.tieFighter().getItemMeta().getDisplayName();
        String redXWing = CustomItems.xWing("red").getItemMeta().getDisplayName();
        String blueXWing = CustomItems.xWing("blue").getItemMeta().getDisplayName();
        String greenXWing = CustomItems.xWing("green").getItemMeta().getDisplayName();

        switch (event.getAction()) {
            case RIGHT_CLICK_AIR:
            case RIGHT_CLICK_BLOCK:
                if(name.equals(tieFighter) || name.equals(redXWing) || name.equals(blueXWing) || name.equals(greenXWing)) {
                    new VehicleManager(player.getUniqueId());
                }
                break;
            case LEFT_CLICK_AIR:
                for(Vehicle vehicle : VehicleManager.vehicles){
                    if(vehicle.getPilot() == null) return;
                    if(vehicle.getPilot() == uuid) VehicleManager.shootTorpedos(player);
                }
        }

    }

    @EventHandler
    public void onPlayerInteractWithEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Entity clickedEntity = event.getRightClicked();

        if(!(clickedEntity instanceof LivingEntity)) return;

        for(Vehicle vehicle : VehicleManager.vehicles){
            LivingEntity phantom = vehicle.getVehicle();
            LivingEntity zombie = vehicle.getModelMob();

            if(clickedEntity == phantom || clickedEntity == zombie) {

                if(vehicle.getPilot() != null) return;

                vehicle.setPilot(uuid);
                phantom.addPassenger(player);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        for(Vehicle vehicle : VehicleManager.vehicles){
            if(vehicle.getOwner() == uuid) VehicleManager.removeVehicle(vehicle);
        }
    }

    @EventHandler
    public void onPlayerDismount(EntityDismountEvent event) {
        Entity entity = event.getDismounted();
        if (!(entity instanceof LivingEntity livingEntity)) return;

        // Use an iterator to safely remove elements from the list during iteration
        Iterator<Vehicle> iterator = VehicleManager.vehicles.iterator();
        while (iterator.hasNext()) {
            Vehicle vehicle = iterator.next();
            if (vehicle.getVehicle() == livingEntity) {
                iterator.remove();  // Remove the vehicle from the list
                VehicleManager.removeVehicle(vehicle);  // Perform additional removal logic
            }
        }
    }

    @EventHandler
    public void onCombustion(EntityCombustEvent event){
        Entity entity = event.getEntity();
        for(Vehicle vehicle : VehicleManager.vehicles){
            Entity phantom = vehicle.getVehicle();
            Entity zombie = vehicle.getModelMob();
            if(entity == phantom || entity == zombie) {
                event.setCancelled(true);
            }
        }
    }

}
