package net.mandomc.mandomcremade.managers;

import net.mandomc.mandomcremade.objects.Stamina;
import net.mandomc.mandomcremade.objects.CustomScoreboard;
import net.mandomc.mandomcremade.managers.StaminaStorageManager;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StaminaManager {
    private final StaminaStorageManager storageManager;
    private final Map<UUID, Stamina> playerStaminaMap;
    private final CustomScoreboard customScoreboard;

    public StaminaManager(StaminaStorageManager storageManager, CustomScoreboard customScoreboard) {
        this.customScoreboard = customScoreboard;
        this.storageManager = storageManager;
        this.playerStaminaMap = new HashMap<>();
    }

    public void addPlayer(Player player) {
        int initialStamina = storageManager.loadStamina(player);
        playerStaminaMap.put(player.getUniqueId(), new Stamina(player, initialStamina));
    }

    public Stamina getStamina(Player player) {
        return playerStaminaMap.get(player.getUniqueId());
    }

    public void removePlayer(Player player) {
        Stamina stamina = playerStaminaMap.remove(player.getUniqueId());
        if (stamina != null) {
            storageManager.saveStamina(player, stamina.getStaminaAmount());
        }
    }

    public void setStamina(Player player, int amount) {
        Stamina stamina = getStamina(player);
        if (stamina != null) {
            stamina.setStaminaAmount(amount);
            storageManager.saveStamina(player, stamina.getStaminaAmount());
        }
    }


    public void addStamina(Player player, int amount) {
        Stamina stamina = getStamina(player);
        if (stamina != null) {
            stamina.addStamina(amount);
            storageManager.saveStamina(player, stamina.getStaminaAmount());
        }
    }

    public void removeStamina(Player player, int amount) {
        Stamina stamina = getStamina(player);
        if (stamina != null) {
            stamina.removeStamina(amount);
            storageManager.saveStamina(player, stamina.getStaminaAmount());
        }
    }

    public void handleStaminaDecrease(Player player, Stamina stamina, int amount) {
        if (stamina != null) {
            stamina.removeStamina(amount);
            if (stamina.getStaminaAmount() <= 0) {
                stamina.setStaminaAmount(0);
                stamina.startEffectCooldown(5000);
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 100, 2));
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 2));
            }
            this.setStamina(player, stamina.getStaminaAmount());
            customScoreboard.updateScore(player, stamina.getStaminaAmount());
        }
    }

}
