package net.mandomc.mandomcremade.managers;

import java.util.HashMap;
import java.util.UUID;

import net.mandomc.mandomcremade.objects.Energy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import net.mandomc.mandomcremade.MandoMCRemade;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class EnergyManager implements Listener {
    private MandoMCRemade plugin_instance;
    private static final HashMap<UUID, Energy> playerEnergyMap = new HashMap<>();

    public EnergyManager(MandoMCRemade plugin_instance) {
        this.plugin_instance = plugin_instance;
        Bukkit.getPluginManager().registerEvents(this, plugin_instance);
        startEnergyTasks();
    }


    public void addEnergy(Player player, double initialEnergy) {
        if (playerEnergyMap.containsKey(player)) {
            return; // Player already has an Energy object
        }
        Energy energy = new Energy(player, initialEnergy, plugin_instance);
        playerEnergyMap.put(player.getUniqueId(), energy);
        setupScoreboard(player);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        // Add energy to the player with an initial value
        addEnergy(player, 100.0); // Adjust the initial energy value as needed
    }

    private void startEnergyTasks() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Energy energy : playerEnergyMap.values()) {
                    if (energy != null && energy.getPlayer().isSprinting() && energy.isFatigued()) {
                        energy.getPlayer().setSprinting(false);
                    }
                }
            }
        }.runTaskTimer(plugin_instance, 0L, 20L);
    }

    public static Energy getPlayerEnergy(Player player) {return playerEnergyMap.get(player.getUniqueId());}

    private void setupScoreboard(Player player) {
        Energy energy = playerEnergyMap.get(player);
        if (energy == null) return;

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("energy", "dummy", "Energy: " + (int) energy.getEnergy());
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(scoreboard);
    }

}
