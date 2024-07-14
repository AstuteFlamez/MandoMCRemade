package net.mandomc.mandomcremade.vehicles;

import net.mandomc.mandomcremade.listeners.VehicleSafetyListener;
import net.mandomc.mandomcremade.objects.Vehicles;
import net.mandomc.mandomcremade.utility.CustomItems;
import net.mandomc.mandomcremade.utility.Messages;
import net.mandomc.mandomcremade.handlers.Handlers;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class TieFighter implements Listener {

    static ArrayList<Vehicles> allActiveTieFighters = new ArrayList<>();
    final HashMap<UUID, Long> cannonCooldown = new HashMap<>();

    public static ArrayList<Vehicles> getAllActiveTieFighters() {
        return allActiveTieFighters;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){

        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = player.getItemInUse();
        assert item != null;
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;
        String displayName = itemMeta.getDisplayName();

        if ((action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)) { //is player right-clicking?
            if (item.hasItemMeta()) { //null check
                if (item.getType() == Material.WOODEN_SWORD && itemMeta.hasCustomModelData()) { //is it an tie fighter? (customModelData shenanigans)
                    //checks if player is trying to spawn in a tie fighter

                    switch (displayName) {

                        case "Tie-Fighter":
                            createShip(player);
                            break;
                    }}}}

        if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) { //is player left-clicking?
            for (Vehicles tieFighter : getAllActiveTieFighters()) { //loops through all active x-wings
                if (tieFighter.getPilot() == player) {//checks if player is already a pilot of the x-wing
                    shootCannons(player);
                }}}
    }

    public void createShip(Player player){

        if(Handlers.isMobSpawningEnabled(player.getLocation(), player)){

            Vehicles tieFighter = new Vehicles();

            Location loc = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());

            LivingEntity seat = (Phantom) player.getWorld().spawnEntity(loc, EntityType.PHANTOM);
            ArmorStand model = (ArmorStand) player.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);

            int slot = player.getInventory().getHeldItemSlot();
            player.getInventory().setItem(slot, new ItemStack(Material.AIR));

            seat.setAI(false);
            seat.setSilent(true);
            seat.setInvisible(true);
            seat.setCollidable(true);
            seat.setRotation(player.getLocation().getYaw(), 0);

            model.setInvulnerable(true);
            model.setGravity(true);
            model.setInvisible(true);
            model.setCollidable(true);
            model.setRotation(player.getLocation().getYaw(), 0);
            model.setHelmet(CustomItems.tieFighter());

            tieFighter.setSeatOne(seat);
            tieFighter.setModel(model);

            allActiveTieFighters.add(tieFighter);

            VehicleSafetyListener.entitiesInShip.add(tieFighter.getSeatOne());
            VehicleSafetyListener.armorStandsInShip.add(tieFighter.getModel());

            rideShip(player, tieFighter);

        }else{
            Messages.msg(player, "&cYou cannot spawn in your &8Tie-Fighter &chere!");
        }
    }

    public void rideShip(Player player, Vehicles tieFighter){

        tieFighter.setPilot(player);
        VehicleSafetyListener.playersInShip.add(tieFighter.getPilot());
        LivingEntity seat1Living = (Phantom) tieFighter.getSeatOne();
        seat1Living.addPassenger(player);

        Messages.msg(player, "&7You mounted your &8Tie-Fighter&7!");
    }

    public static void removeShip(Player player, Vehicles tieFighter){

        Entity seat = tieFighter.getSeatOne();
        Entity model = tieFighter.getModel();

        VehicleSafetyListener.entitiesInShip.remove(seat);
        VehicleSafetyListener.armorStandsInShip.remove(model);

        tieFighter.setPilot(null);
        tieFighter.setModel(null);

        seat.remove();
        model.remove();

        allActiveTieFighters.remove(tieFighter);

        player.getInventory().addItem(CustomItems.tieFighter());

        Messages.msg(player, "&7You dismounted your &8Tie-Fighter&7!");
    }

    public void shootCannons(Player player) {
        if (!this.cannonCooldown.containsKey(player.getUniqueId())) {
            this.cannonCooldown.put(player.getUniqueId(), System.currentTimeMillis());
            Messages.msg(player, "&6Laser cannons go pow pow!");
            Location loc = player.getLocation();
            Vector direction = loc.getDirection();

            for (double t = 0; t < 128; t++) {
                loc.add(direction);

                player.getWorld().spawnParticle(Particle.FIREWORK, loc, 30, new Particle.DustOptions(Color.GREEN, 1F));

                if (loc.getBlock().getType().isSolid() || t == 127) {
                    player.getWorld().spawnParticle(Particle.CLOUD, loc, 30);
                    player.getWorld().spawnParticle(Particle.EXPLOSION, loc, 30);
                    if (Handlers.isMobSpawningEnabled(loc, player)) {
                        for (Entity entity : loc.getWorld().getNearbyEntities(loc, 8.0, 8.0, 8.0)) {
                            Vector vector = Handlers.genVec(player.getLocation(), entity.getLocation());
                            vector.setY(1.2);
                            entity.setVelocity(vector);
                        }
                    }
                }
            }
        } else {
            long timeElapsed = System.currentTimeMillis() - cannonCooldown.get(player.getUniqueId());
            if (timeElapsed >= 2000) {
                this.cannonCooldown.remove(player.getUniqueId());
            } else {
                Messages.msg(player, "&6You are out of laser cannon ammunition, try again in &c" + ((2000 - timeElapsed) / 1000) + " &6seconds!");
            }
        }
    }
}
