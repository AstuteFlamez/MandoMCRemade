package net.mandomc.mandomcremade.tasks;

import net.mandomc.mandomcremade.managers.StaminaManager;
import net.mandomc.mandomcremade.objects.CustomScoreboard;
import net.mandomc.mandomcremade.objects.Stamina;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class StaminaTask extends BukkitRunnable {
    private JavaPlugin plugin;
    private StaminaManager staminaManager;
    private CustomScoreboard customScoreboard;

    public StaminaTask(JavaPlugin plugin, StaminaManager staminaManager, CustomScoreboard customScoreboard) {
        this.plugin = plugin;
        this.staminaManager = staminaManager;
        this.customScoreboard = customScoreboard;
    }

    @Override
    public void run() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            Stamina stamina = staminaManager.getStamina(player);
            if (stamina != null) {
                if (!player.isSprinting() && stamina.getStaminaAmount() < 100 && !stamina.isEffectOnCooldown()) {
                    stamina.addStamina(10);
                }
                Bukkit.broadcastMessage("" + stamina.getStaminaAmount());
                player.setExp((float) stamina.getStaminaAmount() / 2500);
                customScoreboard.updateScore(player, stamina.getStaminaAmount());
            }
        }
    }
}
