package net.mandomc.mandomcremade.utility;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.managers.EnergyManager;
import org.bukkit.entity.Player;

public class Energy {
    private static final int FATIGUE_COOLDOWN_TICKS = 60;
    public static final double MAX_ENERGY = 100.0;

    private double energy;
    private Player player;
    private MandoMCRemade plugin_instance;
    private boolean isFatigued;

    public Energy(Player player, double energy, MandoMCRemade plugin_instance) {
        this.player = player;
        this.energy = Math.max(1.0, Math.min(energy, MAX_ENERGY));
        this.plugin_instance = plugin_instance;
        this.isFatigued = false;
    }


    public double getEnergy(){return energy;}

    public void setEnergy(double amt){this.energy = Math.max(1.0, Math.min(amt, MAX_ENERGY));}

    public Player getPlayer(){return player;}

    public static Energy getPlayerEnergy(Player player){return EnergyManager.getPlayerEnergy(player);}

    public boolean isFatigued(){return isFatigued;}

    public void setFatigued(boolean fatigued) {isFatigued = fatigued;}


}