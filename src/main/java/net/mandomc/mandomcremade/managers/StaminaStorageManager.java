package net.mandomc.mandomcremade.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StaminaStorageManager {
    private static final String FILE_PATH = "plugins/StaminaPlugin/stamina_data.dat";
    private Map<UUID, Integer> staminaData;

    public StaminaStorageManager() {
        this.staminaData = new HashMap<>();
        loadStaminaData();
    }

    public void saveStamina(Player player, int stamina) {
        staminaData.put(player.getUniqueId(), stamina);
        saveStaminaData();
    }

    public int loadStamina(Player player) {
        int stamina = staminaData.getOrDefault(player.getUniqueId(), 2500);
        return stamina;
    }

    public void removeStamina(Player player) {
        staminaData.remove(player.getUniqueId());
        saveStaminaData();
    }

    private void saveStaminaData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(staminaData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStaminaData() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
                staminaData = (HashMap<UUID, Integer>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
