package net.mandomc.mandomcremade.handlers;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Glow {

    // Method to add a glow to an ItemStack
    public static ItemStack addGlow(ItemStack item) {
        item.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.LURE, 1); // Add an enchantment
        org.bukkit.inventory.meta.ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS); // Hide the enchantment
            item.setItemMeta(meta);
        }
        return item;
    }

    // Method to check if an ItemStack has a glow
    public static boolean isGlow(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) {
            return false;
        }

        if (item.hasItemMeta() && item.getItemMeta().hasEnchants()) {
            return item.getItemMeta().hasItemFlag(org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS);
        }
        return false;
    }

    // Method to remove the glow from an ItemStack
    public static ItemStack removeGlow(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) {
            return item;
        }

        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                // Remove the specific enchantment if it exists
                if (meta.hasEnchant(Enchantment.LURE)) {
                    meta.removeEnchant(Enchantment.LURE);
                }
                // Remove the HIDE_ENCHANTS flag
                meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
                item.setItemMeta(meta);
            }
        }
        return item;
    }
}
