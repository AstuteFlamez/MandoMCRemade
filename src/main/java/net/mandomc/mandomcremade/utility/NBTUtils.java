package net.mandomc.mandomcremade.utility;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class NBTUtils {

    // Your plugin's namespace
    private static final String PLUGIN_NAMESPACE = "mandomc";

    // Set an NBT tag with an integer value
    public static void setIntegerTag(ItemStack item, String key, int value) {
        if (item == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        NamespacedKey nbtKey = new NamespacedKey(PLUGIN_NAMESPACE, key);
        data.set(nbtKey, PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
    }

    // Get an integer NBT tag value
    public static Integer getIntegerTag(ItemStack item, String key) {
        if (item == null || !item.hasItemMeta()) return null;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        NamespacedKey nbtKey = new NamespacedKey(PLUGIN_NAMESPACE, key);
        return data.has(nbtKey, PersistentDataType.INTEGER) ? data.get(nbtKey, PersistentDataType.INTEGER) : null;
    }

    // Set an NBT tag with a string value
    public static void setStringTag(ItemStack item, String key, String value) {
        if (item == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        NamespacedKey nbtKey = new NamespacedKey(PLUGIN_NAMESPACE, key);
        data.set(nbtKey, PersistentDataType.STRING, value);
        item.setItemMeta(meta);
    }

    // Get a string NBT tag value
    public static String getStringTag(ItemStack item, String key) {
        if (item == null || !item.hasItemMeta()) return null;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        NamespacedKey nbtKey = new NamespacedKey(PLUGIN_NAMESPACE, key);
        return data.has(nbtKey, PersistentDataType.STRING) ? data.get(nbtKey, PersistentDataType.STRING) : null;
    }

    // Set an NBT tag with a double value
    public static void setDoubleTag(ItemStack item, String key, double value) {
        if (item == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        NamespacedKey nbtKey = new NamespacedKey(PLUGIN_NAMESPACE, key);
        data.set(nbtKey, PersistentDataType.DOUBLE, value);
        item.setItemMeta(meta);
    }

    // Get a double NBT tag value
    public static Double getDoubleTag(ItemStack item, String key) {
        if (item == null || !item.hasItemMeta()) return null;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        NamespacedKey nbtKey = new NamespacedKey(PLUGIN_NAMESPACE, key);
        return data.has(nbtKey, PersistentDataType.DOUBLE) ? data.get(nbtKey, PersistentDataType.DOUBLE) : null;
    }

    // Remove an NBT tag
    public static void removeTag(ItemStack item, String key) {
        if (item == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        NamespacedKey nbtKey = new NamespacedKey(PLUGIN_NAMESPACE, key);
        data.remove(nbtKey);
        item.setItemMeta(meta);
    }

    // Check if an NBT tag exists
    public static boolean hasTag(ItemStack item, String key) {
        if (item == null || !item.hasItemMeta()) return false;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        NamespacedKey nbtKey = new NamespacedKey(PLUGIN_NAMESPACE, key);
        return data.has(nbtKey, PersistentDataType.STRING) || data.has(nbtKey, PersistentDataType.INTEGER)
                || data.has(nbtKey, PersistentDataType.DOUBLE);
    }
}
