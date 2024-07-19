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
import org.bukkit.ChatColor;

public class EnergyManager implements Listener {
    private static final int FATIGUE_COOLDOWN_TICKS = 60;
    private final MandoMCRemade plugin;
    private static final HashMap<UUID, Energy> playerEnergyMap = new HashMap<>();

    public EnergyManager(MandoMCRemade plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        startEnergyTasks();
    }

    public void addEnergy(Player player, double initialEnergy) {
        if (playerEnergyMap.containsKey(player.getUniqueId())) {
            return;
        }
        Energy energy = new Energy(player, initialEnergy, plugin);
        playerEnergyMap.put(player.getUniqueId(), energy);
        setupScoreboard(player);
    }

    public void removeEnergy(Player player) {
        playerEnergyMap.remove(player.getUniqueId());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        addEnergy(player, 0.0);
    }

    @EventHandler
    public void onPlayerLeave(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        removeEnergy(player);
    }


    private void startEnergyTasks() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (UUID playerId : playerEnergyMap.keySet()) {
                    Player player = Bukkit.getPlayer(playerId);

                    if (player == null || !player.isOnline()) continue;

                    Energy energy = playerEnergyMap.get(playerId);
                    if (energy == null) continue;

                    if (player.isSprinting() && energy.isFatigued()) {
                        player.setSprinting(false);
                    }

                    if (!player.isSprinting() && !energy.isFatigued()) {
                        energy.setEnergy(energy.getEnergy() + 20.0);
                    }

                    applyFatigue(player);

                    updateScoreboard(player);
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    public static Energy getPlayerEnergy(Player player) {return playerEnergyMap.get(player.getUniqueId());}

    private void setupScoreboard(Player player) {
        Energy energy = playerEnergyMap.get(player.getUniqueId());
        if (energy == null) return;

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        if (manager == null) return;

        Scoreboard scoreboard = manager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("energy", "dummy", ChatColor.YELLOW + "Energy: " + ChatColor.RED + (int) energy.getEnergy());
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
                objective.setDisplayName(ChatColor.YELLOW + "Energy: " + ChatColor.RED + (int) energy.getEnergy());
            } else {
                objective.setDisplayName(ChatColor.YELLOW + "Energy: " + (int) energy.getEnergy());
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


                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    energy.setFatigued(false);
                    updateScoreboard(player);
                }, FATIGUE_COOLDOWN_TICKS);
            }
        }
    }

}