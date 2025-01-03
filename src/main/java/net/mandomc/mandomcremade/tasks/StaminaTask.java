package net.mandomc.mandomcremade.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.mandomc.mandomcremade.managers.StaminaManager;
import net.mandomc.mandomcremade.objects.Stamina;

public class StaminaTask extends BukkitRunnable {
    private JavaPlugin plugin;
    private StaminaManager staminaManager;

    public StaminaTask(JavaPlugin plugin, StaminaManager staminaManager) {
        this.plugin = plugin;
        this.staminaManager = staminaManager;
    }

    @Override
    public void run() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            Stamina stamina = staminaManager.getStamina(player);
            if (stamina != null) {
                if (!player.isSprinting() && stamina.getStaminaAmount() < 2500 && !stamina.isEffectOnCooldown()) {
                    stamina.addStamina(50);
                }
                player.setExp(((float)stamina.getStaminaAmount()) / ((float)1800.0));
            }
        }
    }
    
}
