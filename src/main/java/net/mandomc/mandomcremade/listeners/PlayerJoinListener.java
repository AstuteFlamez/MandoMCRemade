package net.mandomc.mandomcremade.listeners;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.db.Database;
import net.mandomc.mandomcremade.db.PerksTable;
import net.mandomc.mandomcremade.db.data.Perks;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class PlayerJoinListener implements Listener {

    private final MandoMCRemade plugin;

    public PlayerJoinListener(MandoMCRemade plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Check if server is in maintenance mode
        if (plugin.getConfig().getBoolean("Maintenance")) {
            if (!player.hasPermission("mmc.staff.maintenancebypass")) {
                player.kickPlayer("§4Server is currently under maintenance. Please try again later.");
            }
        }

        if(!player.hasPlayedBefore()){

            try {
                PerksTable.addPlayer(new Perks(player.getUniqueId()));
            } catch(SQLException e){
                ConsoleCommandSender console = Bukkit.getConsoleSender();
                console.sendMessage("[MMC] There was an issue creating the perks table for " + player.getName() + ".");
            }
        }
    }
}
