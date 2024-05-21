package net.mandomc.mandomcremade.enchants;

import net.mandomc.mandomcremade.utility.Messages;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JedisLuckEnchant implements Listener {

    private final double baseDoubleXpChance;

    public JedisLuckEnchant(double baseDoubleXpChance) {
        this.baseDoubleXpChance = baseDoubleXpChance;
    }
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        // Check if the entity was killed by a player
        if (event.getEntity().getKiller() != null) {
            Player player = event.getEntity().getKiller();
            ItemStack item = player.getItemInUse();

            if(item == null){
                return;
            }

            if (item.getType().toString().endsWith("_SWORD") || item.getType().toString().endsWith("_AXE")) {
                double doubleXpChance = getAdjustedDoubleXpChance(item);

                Random random = new Random();
                if (random.nextDouble() < doubleXpChance) {
                    int originalExp = event.getDroppedExp();
                    event.setDroppedExp(originalExp * 2);
                    Messages.str("&7Dropped 2x EXP!");
                }
            }
        }
    }

    private double getAdjustedDoubleXpChance(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null && meta.hasLore()) {
            List<String> lore = meta.getLore();
            if (lore != null) {
                for (String line : lore) {
                    switch (ChatColor.stripColor(line)) {
                        case "Jedi's Luck I":
                            return baseDoubleXpChance + 0.05;
                        case "Jedi's Luck II":
                            return baseDoubleXpChance + 0.10;
                        case "Jedi's Luck III":
                            return baseDoubleXpChance + 0.15;
                        case "Jedi's Luck IV":
                            return baseDoubleXpChance + 0.20;
                        case "Jedi's Luck V":
                            return baseDoubleXpChance + 0.25;
                    }
                }
            }
        }
        return baseDoubleXpChance;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // Check if the clicked item is our custom enchanted book
        ItemStack cursorItem = event.getCursor(); //enchantment book
        ItemStack currentItem = event.getCurrentItem(); //weapon

        if ((cursorItem != null && cursorItem.getType() == Material.ENCHANTED_BOOK) &&
                (currentItem != null && (currentItem.getType().toString().endsWith("_SWORD") || currentItem.getType().toString().endsWith("_AXE")))) {

            if(cursorItem.getItemMeta() == null || cursorItem.getItemMeta().getLore() == null){
                return;
            }

            if (cursorItem.getItemMeta().hasLore()) {
                List<String> lore = cursorItem.getItemMeta().getLore();
                for (String line : lore) {
                    if (line.contains("Jedi's Luck")) {
                        // Extract the level from the line
                        String level = line.substring(line.indexOf("Jedi's Luck") + "Jedi's Luck".length()).trim();

                        // Apply the enchantment effect (glow)
                        ItemMeta itemMeta = currentItem.getItemMeta();
                        assert itemMeta != null;
                        itemMeta.addEnchant(Enchantment.LUCK, 1, true);

                        // Add custom enchantment name to lore
                        List<String> itemLore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
                        assert itemLore != null;
                        itemLore.add(ChatColor.GOLD + "Jedi's Luck " + level);
                        itemMeta.setLore(itemLore);

                        // Update item meta
                        currentItem.setItemMeta(itemMeta);

                        // Remove the custom enchanted book from the cursor
                        event.getWhoClicked().setItemOnCursor(null);

                        // Cancel the event to prevent default behavior
                        event.setCancelled(true);
                        return;  // Exit after processing
                    }
                }
            }
        }
    }
}
