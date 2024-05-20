package net.mandomc.mandomcremade.vehicles;

import net.mandomc.mandomcremade.listeners.VehicleSafety;
import net.mandomc.mandomcremade.objects.Vehicles;
import net.mandomc.mandomcremade.utility.CustomItems;
import net.mandomc.mandomcremade.utility.Messages;
import net.mandomc.mandomcremade.utility.Utilities;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static net.mandomc.mandomcremade.utility.Utilities.isMobSpawningEnabled;

public class XWing implements Listener {

    static ArrayList<Vehicles> allActiveXWings = new ArrayList<>();
    final HashMap<UUID, Long> torpedoCooldown = new HashMap<>();

    public static ArrayList<Vehicles> getAllActiveXWings() {
        return allActiveXWings;
    }

    @EventHandler
    public void onPlayerInteractXWing(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = player.getItemInUse();
        assert item != null;
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;
        String displayName = itemMeta.getDisplayName();

        if ((action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)) { //is player right-clicking?
            if (item.hasItemMeta()) { //null check
                if (item.getType() == Material.WOODEN_SWORD && itemMeta.hasCustomModelData()) { //is it an xwing (customModelData shenanigans)
                    //checks if player is trying to spawn in an x-wing

                    switch (displayName) {

                        case "Red Squadron X-Wing":
                            createShip(player, "c");
                            break;
                        case "Green Squadron X-Wing":
                            if (player.hasPermission("mmc.greenxwing")) {
                                createShip(player, "2");
                            } else {
                                Messages.noPermission(player);
                            }
                            break;
                        case "Blue Squadron X-Wing":
                            if (player.hasPermission("mmc.bluexwing")) {
                                createShip(player, "3");
                            } else {
                                Messages.noPermission(player);
                            }
                            break;
                    }}}}

        if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) { //is player left-clicking?
            for (Vehicles xWing : getAllActiveXWings()) { //loops through all active x-wings
                if (xWing.getPilot() == player) {//checks if player is already a pilot of the x-wing
                    shootTorpedos(player);
                }}}
    }
    public void createShip(Player player, String color) {

        if (isMobSpawningEnabled(player.getLocation(), player)) {

            Vehicles xWing = new Vehicles();

            Location loc = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());

            LivingEntity seat = (Phantom) player.getWorld().spawnEntity(loc, EntityType.PHANTOM);
            LivingEntity model = (Zombie) player.getPlayer().getWorld().spawnEntity(loc, EntityType.ZOMBIE);

            Utilities.takeOneItemAway(player);

            seat.setAI(false);
            seat.setSilent(true);
            seat.setInvisible(true);
            seat.setCollidable(true);
            seat.setPersistent(true); // could be a problem!
            seat.setRemoveWhenFarAway(false);
            seat.setRotation(player.getLocation().getYaw(), 0);

            model.setAI(false);
            model.setSilent(true);
            model.setInvisible(true);
            model.setCollidable(true);
            model.setPersistent(true); // could be a problem!
            model.setRemoveWhenFarAway(false);
            model.setRotation(player.getLocation().getYaw(), 0);
            model.getEquipment().setHelmet(CustomItems.xWing(color));

            xWing.setSeatOne(seat);
            xWing.setModel(model);

            getAllActiveXWings().add(xWing);

            VehicleSafety.entitiesInShip.add(xWing.getSeatOne());
            VehicleSafety.entitiesInShip.add(xWing.getModel());

            rideShip(player, xWing);
        } else {
            Messages.msg(player, "&7You cannot spawn in your " + color + "X-Wing Starfighter &7here!");
        }
    }

    public void rideShip(Player player, Vehicles xWing){

        LivingEntity model = (Zombie) xWing.getModel();
        if(model.getEquipment() == null) return;
        if(model.getEquipment().getHelmet() == null) return;
        if(model.getEquipment().getHelmet().getItemMeta() == null) return;
        int customModelData = model.getEquipment().getHelmet().getItemMeta().getCustomModelData();

        LivingEntity seat = (Phantom) xWing.getSeatOne();
        seat.addPassenger(player);
        xWing.setPilot(player);
        VehicleSafety.playersInShip.add(xWing.getPilot());

        switch(customModelData){
            case 6:
                Messages.msg(player, "&7You mounted your &cX-Wing Starfighter&7!");
                break;
            case 7:
                Messages.msg(player, "&7You mounted your &2X-Wing Starfighter&7!");
                break;
            case 9:
                Messages.msg(player, "&7You mounted your &3X-Wing Starfighter&7!");
                break;

        }
    }

    public static void removeShip(Player player, Vehicles xWing){

        LivingEntity model = (Zombie) xWing.getModel();
        LivingEntity seat = (Phantom) xWing.getSeatOne();
        if(model.getEquipment() == null) return;
        if(model.getEquipment().getHelmet() == null) return;
        if(model.getEquipment().getHelmet().getItemMeta() == null) return;
        int customModelData = model.getEquipment().getHelmet().getItemMeta().getCustomModelData();

        VehicleSafety.entitiesInShip.remove(seat);
        VehicleSafety.entitiesInShip.remove(model);

        seat.remove();
        model.remove();

        xWing.setPilot(null);
        xWing.setSeatOne(null);
        xWing.setModel(null);

        allActiveXWings.remove(xWing);

        switch(customModelData){
            case 6:
                player.getInventory().addItem(CustomItems.xWing("c"));
                Messages.msg(player, "&7You dismounted your &cX-Wing Starfighter&7!");
                break;
            case 7:
                player.getInventory().addItem(CustomItems.xWing("2"));
                Messages.msg(player, "&7You dismounted your &2X-Wing Starfighter&7!");
                break;
            case 9:
                player.getInventory().addItem(CustomItems.xWing("3"));
                Messages.msg(player, "&7You dismounted your &3X-Wing Starfighter&7!");
                break;
        }
    }

    public void shootTorpedos(Player player) {

        if (!this.torpedoCooldown.containsKey(player.getUniqueId())) {
            this.torpedoCooldown.put(player.getUniqueId(), System.currentTimeMillis());
            Messages.msg(player, "&6Proton torpedos go boom boom!");
            Location loc = player.getLocation();
            Vector direction = loc.getDirection();

            for (double t = 0; t < 128; t++) {
                loc.add(direction);

                player.getWorld().spawnParticle(Particle.REDSTONE, loc, 30, new Particle.DustOptions(Color.RED, 1F));

                if (loc.getBlock().getType().isSolid() || t == 127) {
                    player.getWorld().spawnParticle(Particle.CLOUD, loc, 30);
                    player.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, loc, 30);
                    if (isMobSpawningEnabled(loc, player)) {
                        if(loc.getWorld() == null) return;
                        loc.getWorld().createExplosion(loc, 1);
                    }
                }
            }
        } else {
            long timeElapsed = System.currentTimeMillis() - torpedoCooldown.get(player.getUniqueId());
            if (timeElapsed >= 60000) {
                this.torpedoCooldown.remove(player.getUniqueId());
            } else {
                Messages.msg(player, "&6You are out of proton torpedos, try again in &c" + ((60000 - timeElapsed) / 1000) + " &6seconds!");
            }}
    }

    @EventHandler
    public void dismountXWing(EntityDismountEvent event){

        Entity entity = event.getDismounted();

        List<Vehicles> allActiveXWingsCopy = new ArrayList<>(getAllActiveXWings());
        for(Vehicles xWing: allActiveXWingsCopy){
            if(xWing.getSeatOne() == entity && event.getEntity() instanceof Player){
                Player player = (Player) event.getEntity();

                removeShip(player, xWing);
            }
        }
    }
}

