package net.mandomc.mandomcremade.listeners;

import me.deecaad.weaponmechanics.weapon.weaponevents.WeaponReloadCompleteEvent;
import me.deecaad.weaponmechanics.weapon.weaponevents.WeaponScopeEvent;
import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.db.Database;
import net.mandomc.mandomcremade.db.PerksTable;
import net.mandomc.mandomcremade.db.data.Perks;
import net.mandomc.mandomcremade.managers.StaminaManager;
import net.mandomc.mandomcremade.objects.Stamina;
import net.mandomc.mandomcremade.utility.CustomItems;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import me.deecaad.weaponmechanics.weapon.weaponevents.WeaponPreReloadEvent;
import org.bukkit.event.Event;


import java.sql.SQLException;

public class PlayerJoinListener implements Listener {

    private final MandoMCRemade plugin;
    private final StaminaManager staminaManager;

    public PlayerJoinListener(MandoMCRemade plugin, StaminaManager staminaManager) {
        this.plugin = plugin;
        this.staminaManager = staminaManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        staminaManager.addPlayer(player);

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
}
