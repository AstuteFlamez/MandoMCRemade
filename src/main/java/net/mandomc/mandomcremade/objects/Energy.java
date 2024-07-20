package net.mandomc.mandomcremade.objects;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.managers.EnergyManager;
import org.bukkit.entity.Player;

public class Energy {
    public static final double MAX_ENERGY = 100.0;
    public static final double MIN_ENERGY = 0.0;
    private static final int FATIGUE_COOLDOWN_TICKS = 200;

    private double energy;
    private Player player;
    private boolean isFatigued;
    private long lastEnergyChangeTime;

    public Energy(Player player, double energy, MandoMCRemade plugin_instance) {
        this.player = player;
        this.energy = Math.max(MIN_ENERGY, Math.min(energy, MAX_ENERGY));
        this.isFatigued = false;
        this.lastEnergyChangeTime = System.currentTimeMillis();
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double amt) {
        this.energy = Math.max(MIN_ENERGY, Math.min(amt, MAX_ENERGY));
        long currentTime = System.currentTimeMillis();

        if (this.energy <= 0) {
            if (!isFatigued) {
                // Only apply fatigue if enough time has passed
                if (currentTime - lastEnergyChangeTime >= FATIGUE_COOLDOWN_TICKS * 50) {
                    applyFatigue();
                }
            }
        } else {
            this.isFatigued = false; // Ensure fatigue is removed if energy is restored
            lastEnergyChangeTime = currentTime; // Reset the cooldown timer
        }
    }

    private void applyFatigue() {
        if (!isFatigued) {
            isFatigued = true;
            EnergyManager manager = EnergyManager.getInstance();
            if (manager != null) {
                manager.applyFatigue(player); // Call manager method to apply fatigue
            } else {
                player.sendMessage("EnergyManager instance is not available.");
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public static Energy getPlayerEnergy(Player player) {
        return EnergyManager.getPlayerEnergy(player);
    }

    public boolean isFatigued() {
        return isFatigued;
    }

    public void setFatigued(boolean fatigued) {
        isFatigued = fatigued;
    }
}
