package net.mandomc.mandomcremade.managers;

import net.mandomc.mandomcremade.config.LangConfig;
import net.mandomc.mandomcremade.objects.Vehicle;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

import java.util.*;

import static net.mandomc.mandomcremade.MandoMCRemade.str;

public class VehicleManager {

    private Vehicle vehicle;
    private final UUID owner;

    public static final List<Vehicle> vehicles = new ArrayList<>();
    public static final Map<UUID, Long> torpedosCooldown = new HashMap<>();
    public static final FileConfiguration config = LangConfig.get();
    public static final String prefix = Objects.requireNonNull(config.getString("Prefix"));

    public VehicleManager(UUID owner) {
        this.owner = owner;
        createVehicle();
    }

    private void createVehicle() {
        Player player = Bukkit.getPlayer(owner);
        if (player == null) return;

        vehicle = new Vehicle(owner, spawnAndConfigureEntities(player, EntityType.SLIME), spawnAndConfigureEntities(player, EntityType.ZOMBIE));

        LivingEntity phantom = vehicle.getVehicleMob();
        LivingEntity zombie = vehicle.getModelMob();
        phantom.addPassenger(zombie);

        ItemStack modelItem = removeModelItemFromPlayer(player);
        vehicle.setModelItem(modelItem);

        equipModelItemToZombie(vehicle.getModelMob(), modelItem);

        vehicles.add(vehicle);
        
        String msg = str("&7You spawned in your " + Objects.requireNonNull(modelItem.getItemMeta()).getDisplayName() + "&7!");
        player.sendMessage(prefix + msg);
    }

    private LivingEntity spawnAndConfigureEntities(Player player, EntityType type) {
        World world = player.getWorld();
        Location location = player.getLocation();

        return configureEntity((LivingEntity) world.spawnEntity(location, type), player);
    }

    private LivingEntity configureEntity(LivingEntity entity, Player player) {
        entity.setAI(false);
        entity.setSilent(true);
        entity.setInvisible(false);
        entity.setCollidable(false);
        entity.setVisualFire(false);
        entity.setPersistent(true);
        entity.setRemoveWhenFarAway(false);
        entity.setInvulnerable(true);
        entity.setRotation(player.getLocation().getYaw(), 0);
        return entity;
    }

    private ItemStack removeModelItemFromPlayer(Player player) {
        PlayerInventory inventory = player.getInventory();
        ItemStack modelItem = inventory.getItemInMainHand();
        inventory.removeItem(modelItem);
        return modelItem;
    }

    private void equipModelItemToZombie(LivingEntity zombie, ItemStack modelItem) {
        Objects.requireNonNull(zombie.getEquipment()).setItemInMainHand(new ItemStack(Material.AIR));
        Objects.requireNonNull(zombie.getEquipment()).setHelmet(modelItem);
    }

    public static void shootTorpedoes(Player player) {
        UUID uuid = player.getUniqueId();
        long currentTime = System.currentTimeMillis();
        String msg = str("&7You shot your proton torpedos!");

        if (canShootTorpedos(uuid, currentTime)) {
            updateTorpedosCooldown(uuid, currentTime);
            player.sendMessage(prefix + msg);
            createTorpedoEffect(player);
        } else {
            notifyCooldownTime(player, uuid, currentTime);
        }
    }

    private static boolean canShootTorpedos(UUID uuid, long currentTime) {
        return !torpedosCooldown.containsKey(uuid) || (currentTime - torpedosCooldown.get(uuid) >= 60000);
    }

    private static void updateTorpedosCooldown(UUID uuid, long currentTime) {
        torpedosCooldown.put(uuid, currentTime);
    }

    private static void createTorpedoEffect(Player player) {
        Location loc = player.getLocation();
        Vector direction = loc.getDirection();
        World world = loc.getWorld();

        if (world == null) return;
        for (double t = 0; t < 128; t++) {
            loc.add(direction);
            Particle.DustTransition dustOptions = new Particle.DustTransition(Color.RED, Color.MAROON, 1);
            world.spawnParticle(Particle.DUST_COLOR_TRANSITION, loc, 1, dustOptions);

            if (loc.getBlock().getType().isSolid() || t == 127) {
                world.spawnParticle(Particle.CLOUD, loc, 30);
                world.spawnParticle(Particle.EXPLOSION, loc, 30);
            }
        }
    }

    private static void notifyCooldownTime(Player player, UUID uuid, long currentTime) {
        long timeRemaining = 60000 - (currentTime - torpedosCooldown.get(uuid));
        String msg = str("&7You are out of proton torpedos, try again in &c" + (timeRemaining / 1000) + " &7seconds!");
        player.sendMessage(prefix + msg);
    }

    public static void removeVehicle(Vehicle vehicle) {
        Player player = Bukkit.getPlayer(vehicle.getOwner());
        if (player != null) {
            player.getInventory().addItem(vehicle.getModelItem());
        }

        vehicle.getVehicleMob().remove();
        vehicle.getModelMob().remove();
        vehicles.remove(vehicle);
    }
}
