package net.mandomc.mandomcremade.enchants;

import net.mandomc.mandomcremade.utility.Messages;
import org.bukkit.Bukkit;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            ItemStack item = player.getInventory().getItem(player.getInventory().getHeldItemSlot());

            if(item == null){
                return;
            }
            
            if (item.getType().toString().endsWith("_SWORD") || item.getType().toString().endsWith("_AXE")) {
                double doubleXpChance = getAdjustedDoubleXpChance(item);

                Random random = new Random();
                if (random.nextDouble() < doubleXpChance) {
                    int originalExp = event.getDroppedExp();
                    event.setDroppedExp(originalExp * 2);
                    Messages.msg(player,"&7Dropped 2x EXP!");
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
        ItemStack book = event.getCursor(); //enchantment book
        ItemStack equipment = event.getCurrentItem(); //weapon

        if ((book != null && book.getType() == Material.ENCHANTED_BOOK) &&
                (equipment != null && (equipment.getType().toString().endsWith("_SWORD") || equipment.getType().toString().endsWith("_AXE")))) {
            if(book.getItemMeta() == null || book.getItemMeta().getLore() == null){
                return;
            }
            ItemMeta bookMeta = book.getItemMeta();
            if (bookMeta.getDisplayName().contains("Jedi's Luck")) {

                if (bookMeta.hasLore()) {
                    List<String> bookLore = bookMeta.getLore();

                    // Extract the first and second lines of lore
                    String firstLine = bookLore.getFirst();

                    Player player = (Player) event.getWhoClicked();

                    if(successfulApplication(extractNumber(firstLine))){
                        String displayName = book.getItemMeta().getDisplayName();
                        // Apply the enchantment effect (glow)
                        ItemMeta equipmentMeta = equipment.getItemMeta();
                        assert equipmentMeta != null;
                        equipmentMeta.addEnchant(Enchantment.BREACH, 1, true);
                        // Add custom enchantment name to lore
                        ArrayList<String> itemLore = new ArrayList<>();
                        itemLore.add(displayName);
                        equipmentMeta.setLore(itemLore);

                        // Update item meta
                        equipment.setItemMeta(equipmentMeta);

                        Messages.msg(player, "&7The enchant was &asuccessfully &7applied!");
                    }else{
                        Messages.msg(player, "&7The enchant application &cfailed&7!");
                    }
                    // Remove the custom enchanted book from the cursor
                    event.getWhoClicked().setItemOnCursor(new ItemStack(Material.AIR, 1));

                    // Cancel the event to prevent default behavior
                    event.setCancelled(true);
                }
            }
        }
    }

    // Method to extract number from a string using regex
    private static int extractNumber(String text){
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        }
        return -1;
    }

    private static boolean successfulApplication(int successRate){

        return new Random().nextInt(1, 100) < successRate;
    }
}
