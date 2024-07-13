package net.mandomc.mandomcremade.listeners;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.utility.Utilities;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    }
}
