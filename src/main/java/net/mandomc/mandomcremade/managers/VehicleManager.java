package net.mandomc.mandomcremade.managers;

import net.mandomc.mandomcremade.config.LangConfig;
import net.mandomc.mandomcremade.objects.Vehicle;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import static net.mandomc.mandomcremade.MandoMCRemade.str;

public class VehicleManager {

    Vehicle vehicle;
    private final UUID owner;
    public static ArrayList<Vehicle> vehicles = new ArrayList<>();
    public static final HashMap<UUID, Long> torpedosCooldown = new HashMap<>();
    public static FileConfiguration config = LangConfig.get();
    public static String prefix = config.getString("Prefix");

    public VehicleManager(UUID owner) {
        this.owner = owner;
        createVehicle();
    }

    private void createVehicle() {
        Player player = Bukkit.getPlayer(owner);

        if(player == null) return;
        World world = player.getWorld();
        Location location = player.getLocation();

        LivingEntity phantom = (LivingEntity) world.spawnEntity(location, EntityType.PHANTOM);
        LivingEntity zombie = (LivingEntity) world.spawnEntity(location, EntityType.ZOMBIE);
        vehicle = new Vehicle(owner, phantom, zombie);

        PlayerInventory inventory = player.getInventory();
        ItemStack modelItem = inventory.getItemInMainHand();
        ItemMeta meta = modelItem.getItemMeta();
        assert meta != null;
        String displayName = meta.getDisplayName();
        inventory.removeItem(modelItem);
        vehicle.setModelItem(modelItem);

        phantom.setAI(false);
        phantom.setSilent(true);
        phantom.setInvisible(true);
        phantom.setCollidable(false);
        phantom.setVisualFire(false);
        phantom.setPersistent(true); // could be a problem!
        phantom.setRemoveWhenFarAway(false);
        phantom.setInvulnerable(true);
        phantom.setRotation(player.getLocation().getYaw(), 0);
        vehicle.setVehicle(phantom);

        zombie.setAI(false);
        zombie.setSilent(true);
        zombie.setInvisible(true);
        zombie.setCollidable(false);
        zombie.setVisualFire(false);
        zombie.setPersistent(true); // could be a problem!
        zombie.setInvulnerable(true);
        zombie.setRemoveWhenFarAway(false);
        zombie.setRotation(player.getLocation().getYaw(), 0);
        Objects.requireNonNull(zombie.getEquipment()).setHelmet(vehicle.getModelItem());
        vehicle.setModelMob(zombie);

        vehicles.add(vehicle);

        String msg = str("&7You spawned in your " + displayName + "&7!");
        player.sendMessage(prefix + msg);
    }

    public static void shootTorpedos(Player player) {
        UUID uuid = player.getUniqueId();

        if (!torpedosCooldown.containsKey(uuid)) {
            torpedosCooldown.put(uuid, System.currentTimeMillis());

            String msg = str("&7You shot your proton torpedos!");
            player.sendMessage(prefix + msg);
            Location loc = player.getLocation();
            Vector direction = loc.getDirection();
            World world = loc.getWorld();

            for (double t = 0; t < 128; t++) {
                loc.add(direction);

                Particle.DustTransition dustOptions = new Particle.DustTransition(Color.RED, Color.MAROON, 1);
                world.spawnParticle(Particle.DUST_COLOR_TRANSITION, loc, 1, dustOptions);

                if (loc.getBlock().getType().isSolid() || t == 127) {
                    world.spawnParticle(Particle.CLOUD, loc, 30);
                    world.spawnParticle(Particle.EXPLOSION, loc, 30);
                }
            }
        } else {
            long timeElapsed = System.currentTimeMillis() - torpedosCooldown.get(uuid);
            if (timeElapsed >= 60000) {
                torpedosCooldown.remove(uuid);
            } else {
                String msg = str("&7You are out of proton torpedos, try again in &c"  + ((60000 - timeElapsed) / 1000) + " &7seconds!");
                player.sendMessage(prefix + msg);

            }
        }
    }

    public static void removeVehicle(Vehicle vehicle){
        LivingEntity phantom = vehicle.getVehicle();
        LivingEntity zombie = vehicle.getModelMob();
        UUID uuid = vehicle.getOwner();
        ItemStack modelItem = vehicle.getModelItem();

        Player player = Bukkit.getPlayer(uuid);
        if(player == null) return;
        PlayerInventory inventory = player.getInventory();
        inventory.addItem(modelItem);

        phantom.remove();
        zombie.remove();

        vehicles.remove(vehicle);
    }
}
