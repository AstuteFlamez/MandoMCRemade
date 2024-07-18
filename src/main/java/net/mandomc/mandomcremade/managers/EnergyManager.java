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
import org.bukkit.Sound;

public class EnergyManager implements Listener {
    private static final int FATIGUE_COOLDOWN_TICKS = 60;
    private MandoMCRemade plugin_instance;
    private static final HashMap<UUID, Energy> playerEnergyMap = new HashMap<>();

    public EnergyManager(MandoMCRemade plugin_instance) {
        this.plugin_instance = plugin_instance;
        Bukkit.getPluginManager().registerEvents(this, plugin_instance);
        startEnergyTasks();
    }


    public void addEnergy(Player player, double initialEnergy) {
        if (playerEnergyMap.containsKey(player)) {
            return;
        }
        Energy energy = new Energy(player, initialEnergy, plugin_instance);
        playerEnergyMap.put(player.getUniqueId(), energy);
        setupScoreboard(player);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        addEnergy(player, 100.0);
    }

    private void startEnergyTasks() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (UUID playerId : playerEnergyMap.keySet()) {
                    Player player = Bukkit.getPlayer(playerId);
                    Energy playerEnergy = Energy.getPlayerEnergy(player);
                    if (player == null || !player.isOnline()) continue;

                    Energy energy = playerEnergyMap.get(playerId);
                    if (energy == null) continue;

                    if (player.isSprinting() && energy.isFatigued()) {player.setSprinting(false);}

                    if (!player.isSprinting() && !energy.isFatigued()){
                        playerEnergy.setEnergy(playerEnergy.getEnergy() + 20.0);
                    }

                    applyFatigue(player);

                    updateScoreboard(player);
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

    private void updateScoreboard(Player player) {
        Energy energy = playerEnergyMap.get(player.getUniqueId());
        if (energy == null) return;

        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective("energy");
        if (objective != null) {
            if (energy.getEnergy() <= 0) {
                objective.setDisplayName("&eEnergy: &4" + (int) energy.getEnergy());
            }
            else{
                objective.setDisplayName("&eEnergy: " + (int) energy.getEnergy());
            }
        } else {

            setupScoreboard(player);
        }
    }

    private void applyFatigue(Player player) {
        Energy energy = playerEnergyMap.get(player.getUniqueId());
        if (energy != null && energy.getEnergy() <= 0) {
            if (!energy.isFatigued()) {
                energy.setFatigued(true);
                player.setSprinting(false);
                player.playSound(player.getLocation(), Sound.ENTITY_WOLF_PANT, 1.0f, 0.5f);

                updateScoreboard(player);


                Bukkit.getScheduler().runTaskLater(plugin_instance, () -> {
                    energy.setFatigued(false);
                    updateScoreboard(player);
                }, FATIGUE_COOLDOWN_TICKS);
            }
        }
    }

}
