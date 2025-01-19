package net.mandomc.mandomcremade.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Location;
import net.mandomc.mandomcremade.managers.StaminaManager;
import net.mandomc.mandomcremade.objects.Stamina;

import java.util.HashMap;
import java.util.UUID;

public class StaminaTask extends BukkitRunnable {
    private JavaPlugin plugin;
    private StaminaManager staminaManager;

    // Track players' recharge sound states
    private final HashMap<UUID, BukkitRunnable> soundTasks = new HashMap<>();

    public StaminaTask(JavaPlugin plugin, StaminaManager staminaManager) {
        this.plugin = plugin;
        this.staminaManager = staminaManager;
    }

    @Override
    public void run() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            Stamina stamina = staminaManager.getStamina(player);
            if (stamina != null) {
                // Check if the player is recharging naturally
                boolean isRecharging = !player.isSprinting() &&
                                       stamina.getStaminaAmount() < 2100 &&
                                       !stamina.isEffectOnCooldown() &&
                                       !stamina.isRegenerationOnCooldown(); // Check cooldown after energy spent
    
                if (isRecharging) {
                    if (player.getAttribute(Attribute.GENERIC_ATTACK_SPEED) != null) {
                        double staminaVal = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).getValue();
                        int addingAmt = (staminaVal > 8) 
                                      ? (int) (75 - (5 * (staminaVal - 8)))
                                      : (int) (75 + (5 * staminaVal));
                        stamina.addStamina(addingAmt);
                    } else {
                        stamina.addStamina(75);
                    }
                    startRechargeSound(player, stamina); // Start the recharge sound
                } else {
                    stopRechargeSound(player); // Stop the recharge sound if interrupted
                }
    
                // Update player's stamina display
                player.setLevel(0);
                player.setExp(((float) stamina.getStaminaAmount()) / ((float) 2100.0));
            }
        }
    }
    
    

    private void startRechargeSound(Player player, Stamina stamina) {
        UUID playerId = player.getUniqueId();
    
        // Avoid starting a new task if one is already running
        if (soundTasks.containsKey(playerId)) return;
    
        // Define the playback interval in ticks (12 ticks = 0.6 seconds)
        int playbackInterval = 1;
    
        // Create a new sound-playing task
        BukkitRunnable soundTask = new BukkitRunnable() {
            @Override
            public void run() {
                float staminaPercent = ((float) stamina.getStaminaAmount()) / 1800.0f;
                float pitch = 1.0f + (staminaPercent * 0.25f);
    
                // Play the sound with the calculated pitch
                player.playSound(player.getLocation(), "misc.staminarecharging", 0.1f, pitch);
            }
        };
    
        // Schedule the task to run at the playback interval
        soundTask.runTaskTimer(plugin, 0, playbackInterval);
        soundTasks.put(playerId, soundTask);
    }
    

    private void stopRechargeSound(Player player) {
        UUID playerId = player.getUniqueId();

        // Stop and remove the sound task if it exists
        if (soundTasks.containsKey(playerId)) {
            soundTasks.get(playerId).cancel();
            soundTasks.remove(playerId);
        }
    }
}
