package net.mandomc.mandomcremade.listeners;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.db.Database;
import net.mandomc.mandomcremade.db.Perks;
import net.mandomc.mandomcremade.managers.StaminaManager;
import net.mandomc.mandomcremade.objects.CustomScoreboard;
import net.mandomc.mandomcremade.objects.Stamina;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.sql.SQLException;

public class PlayerJoinListener implements Listener {

    private final MandoMCRemade plugin;
    private final Database database;
    private final CustomScoreboard customScoreboard;
    private final StaminaManager staminaManager;


    public PlayerJoinListener(MandoMCRemade plugin, Database database, StaminaManager staminaManager, CustomScoreboard customScoreboard) {
        this.plugin = plugin;
        this.database = database;
        this.staminaManager = staminaManager;
        this.customScoreboard = customScoreboard;

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        staminaManager.addPlayer(player);
        customScoreboard.updateScore(player, staminaManager.getStamina(player).getStaminaAmount());

        // Check if server is in maintenance mode
        if (plugin.getConfig().getBoolean("Maintenance")) {
            if (!player.hasPermission("mmc.staff.maintenancebypass")) {
                player.kickPlayer("§4Server is currently under maintenance. Please try again later.");
            }
        }

        if (!player.hasPlayedBefore()) {

            try {
                database.createPerks(new Perks(player.getUniqueId()));
            } catch (SQLException e) {
                ConsoleCommandSender console = Bukkit.getConsoleSender();
                console.sendMessage("[MMC] There was an issue creating the perks table for " + player.getName() + ".");
            }
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
            ItemStack item = player.getInventory().getItemInMainHand();

            if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                String itemName = item.getItemMeta().getDisplayName().toLowerCase();
                if (itemName.contains("lightsaber")) {
                    if (stamina != null && stamina.isEffectOnCooldown()) {
                        event.setCancelled(true); // Cancel the left-click action for lightsaber
                        return;
                    }
                    if (stamina != null) {
                        stamina.removeStamina(10); // Decrease stamina by 10
                        if (stamina.getStaminaAmount() <= 0) {
                            stamina.setStaminaAmount(0);
                            stamina.startEffectCooldown(5000);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 100, 3));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 3));
                        }
                        staminaManager.setStamina(player, stamina.getStaminaAmount());
                        customScoreboard.updateScore(player, stamina.getStaminaAmount());
                        player.sendMessage("Stamina decreased by 10. Current stamina: " + stamina.getStaminaAmount());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            Stamina stamina = staminaManager.getStamina(player);
            ItemStack item = player.getInventory().getItemInMainHand();

            if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                String itemName = item.getItemMeta().getDisplayName().toLowerCase();
                if (itemName.contains("lightsaber")) {
                    if (stamina != null && stamina.isEffectOnCooldown()) {
                        event.setCancelled(true);
                        return;
                    }
                    if (stamina != null) {
                        stamina.removeStamina(10); // Decrease stamina by 10
                        if (stamina.getStaminaAmount() <= 0) {
                            stamina.setStaminaAmount(0);
                            stamina.startEffectCooldown(5000);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 100, 3));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 100, 3));
                        }
                        staminaManager.setStamina(player, stamina.getStaminaAmount());
                        customScoreboard.updateScore(player, stamina.getStaminaAmount());
                        player.sendMessage("Stamina decreased by 10. Current stamina: " + stamina.getStaminaAmount());
                    }
                }
            }

        }
    }


}