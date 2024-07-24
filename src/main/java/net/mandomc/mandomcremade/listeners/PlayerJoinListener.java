package net.mandomc.mandomcremade.listeners;

import me.deecaad.weaponmechanics.weapon.weaponevents.WeaponReloadCompleteEvent;
import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.db.Database;
import net.mandomc.mandomcremade.db.PerksTable;
import net.mandomc.mandomcremade.db.data.Perks;
import net.mandomc.mandomcremade.managers.StaminaManager;
import net.mandomc.mandomcremade.objects.CustomScoreboard;
import net.mandomc.mandomcremade.objects.Stamina;
import net.mandomc.mandomcremade.utility.CustomItems;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import me.deecaad.weaponmechanics.weapon.weaponevents.WeaponPreReloadEvent;

import java.sql.SQLException;

public class PlayerJoinListener implements Listener {

    private final MandoMCRemade plugin;
    private final StaminaManager staminaManager;
    private final CustomScoreboard customScoreboard;

    public PlayerJoinListener(MandoMCRemade plugin, StaminaManager staminaManager, CustomScoreboard customScoreboard) {
        this.plugin = plugin;
        this.staminaManager = staminaManager;
        this.customScoreboard = customScoreboard;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        staminaManager.addPlayer(player);
        customScoreboard.updateScore(player, staminaManager.getStamina(player).getStaminaAmount());

        if (plugin.getConfig().getBoolean("Maintenance") && !player.hasPermission("mmc.staff.maintenancebypass")) {
            player.kickPlayer("§4Server is currently under maintenance. Please try again later.");
        }

        if (!player.hasPlayedBefore()) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                try {
                    PerksTable.addPlayer(new Perks(player.getUniqueId()));
                } catch (SQLException e) {
                    Bukkit.getScheduler().runTask(plugin, () -> {
                        ConsoleCommandSender console = Bukkit.getConsoleSender();
                        console.sendMessage("[MMC] There was an issue creating the perks table for " + player.getName() + ".");
                    });
                }
            });
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        staminaManager.removePlayer(player);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Stamina stamina = staminaManager.getStamina(player);

        if (event.getAction().toString().contains("LEFT_CLICK")) {
            handleLeftClick(player, stamina, event);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player player) {
            Stamina stamina = staminaManager.getStamina(player);
            handlePlayerDamage(player, stamina, event);
        }

        if (event.getDamager() instanceof Player player) {
            Stamina stamina = staminaManager.getStamina(player);
            handleEntityDamage(player, stamina, event);
        }
    }

    @EventHandler
    public void onWeaponReloadComplete(WeaponReloadCompleteEvent event) {
        LivingEntity shooter = event.getShooter();
        if (shooter instanceof Player player) {
            Stamina stamina = staminaManager.getStamina(player);
            staminaManager.handleStaminaDecrease(player, stamina, 15);
        }
    }

    private void handleLeftClick(Player player, Stamina stamina, PlayerInteractEvent event) {
        if (CustomItems.isHoldingLightsaber(player)) {
            if (stamina != null && stamina.isEffectOnCooldown()) {
                event.setCancelled(true); // Cancel the left-click action for lightsaber
                return;
            }
            staminaManager.handleStaminaDecrease(player, stamina, 10);
        }
    }

    private void handlePlayerDamage(Player player, Stamina stamina, EntityDamageByEntityEvent event) {
        if (player.isBlocking() && CustomItems.isHoldingLightsaber(player)) {
            Entity damager = event.getDamager();
            if (damager instanceof LivingEntity && isFromFront(player, (LivingEntity) damager)) {
                event.setCancelled(true); // Cancel the damage
                staminaManager.handleStaminaDecrease(player, stamina, 10); // Reduce stamina when blocking
            }
        }
    }

    private void handleEntityDamage(Player player, Stamina stamina, EntityDamageByEntityEvent event) {
        if (CustomItems.isHoldingLightsaber(player)) {
            if (stamina != null && stamina.isEffectOnCooldown()) {
                event.setCancelled(true);
                return;
            }
            staminaManager.handleStaminaDecrease(player, stamina, 5);
        }
    }

    private boolean isFromFront(Player player, LivingEntity damager) {
        Vector playerToDamager = damager.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
        Vector playerDirection = player.getLocation().getDirection();

        double angle = playerToDamager.angle(playerDirection);

        // Check if the angle is within 60 degrees (30 degrees on either side)
        return angle < Math.toRadians(30);
    }
}
