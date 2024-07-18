package net.mandomc.mandomcremade.utility;

import net.mandomc.mandomcremade.MandoMCRemade;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

public class Energy {
    private static final int FATIGUE_COOLDOWN_TICKS = 60;
    private static final double MAX_ENERGY = 100.0;

    private double energy;
    private Player player;
    private MandoMCRemade plugin_instance;
    private Scoreboard scoreboard;
    private Objective objective;
    private boolean isFatigued;

    public Energy(Player player, double energy, MandoMCRemade plugin_instance) {
        this.player = player;
        this.energy = Math.max(1.0, Math.min(energy, MAX_ENERGY));
        this.plugin_instance = plugin_instance;
        this.isFatigued = false;
        startEnergyTask();
        setupScoreboard();
    }

    private void startEnergyTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
                    if (onlinePlayer.isSprinting()) {
                        addEnergy(25);
                    }
                }
            }
        }.runTaskTimer(plugin_instance, 0L, 20L);
    }

    private void setupScoreboard() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        this.scoreboard = manager.getNewScoreboard();


        this.objective = scoreboard.registerNewObjective("energy", "dummy", "Energy: " + (int) energy);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);


        player.setScoreboard(scoreboard);
    }

    private void updatePlayerScoreboard() {
        if (isFatigued) {
            objective.setDisplayName("&eEnergy: &4" + (int) energy);
        }
        else{
            objective.setDisplayName("&eEnergy: " + (int) energy);
        }

        if (energy <= 0 && !isFatigued) {
            isFatigued = true;
            Bukkit.getScheduler().runTaskLater(plugin_instance, () -> {isFatigued = false;}, FATIGUE_COOLDOWN_TICKS);
        }
    }
    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = Math.max(1.0, Math.min(energy, MAX_ENERGY));
        updatePlayerExperienceBar();
    }

    private Player getPlayer() {
        return this.player;
    }

    public static Energy getPlayerEnergy(Player player) {
        for (Energy energy : MandoMCRemade.energyList) {
            if (energy.getPlayer().equals(player)) {
                return energy;
            }
        }
        return null;
    }

    private void addEnergy(double amount) {
        if (isFatigued) {
            return;
        }

        this.energy += Math.min(MAX_ENERGY, this.energy + amount);
        updatePlayerExperienceBar();
    }

    private void updatePlayerExperienceBar() {
        int energyLevel = (int) this.energy;
        float expProgress = (float) (this.energy - energyLevel);
        player.setLevel(energyLevel);
        player.setExp(expProgress);
    }
}
