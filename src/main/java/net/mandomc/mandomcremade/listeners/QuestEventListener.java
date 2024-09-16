package net.mandomc.mandomcremade.listeners;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.db.PlayerQuestsTable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class QuestEventListener implements Listener {
    private final MandoMCRemade plugin;

    public QuestEventListener(final MandoMCRemade plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Gives the player the starter quest upon first join.
        if (!player.hasPlayedBefore())
        {
            try {
                PlayerQuestsTable.playerStartQuest(player.getUniqueId().toString(), plugin.getConfig().getString("StarterQuest"));
            } catch (SQLException e) {
                Bukkit.getConsoleSender().sendMessage(e.getMessage());
            }
        }
    }
}
