package net.mandomc.mandomcremade.utility;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Random;

public class GetEnchant {

    public static void getEnchant(Player sender, Player player, String enchant, String rarity, String level, int successChance){

        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();
        assert meta != null;
        meta.addStoredEnchant(Enchantment.BREACH, 1, true);
        int[] chance = new int[2];
        chance[0] = successChance;
        chance[1] = 100-successChance;

        String color = "";
        switch(rarity){
            case "common":
                color = "&r&a&l&n";
                break;
            case "rare":
                color = "&r&b&l&n";
                break;
            case "epic":
                color = "&r&5&l&n";
                break;
            case "legendary":
                color = "&r&6&l&n";
                break;
            default:
                Messages.msg(sender, "&cPlease fill the command with /ce <player> <enchant> <rarity> (level)");
                break;
        }

        ArrayList<String> itemLore = new ArrayList<>();

        if(successChance > 100 || successChance < 0) {
            chance = generateChance(rarity);
        }

        itemLore.add(Messages.str("&a" + chance[0]) + "% Success Rate");
        itemLore.add(Messages.str("&c" + chance[1]) + "% Destroy Rate");

        switch(enchant){
            case "jedisluck":
                enchant = "Jedi's Luck";
                itemLore.add(Messages.str("&eChance to get extra experience from killing mobs"));
                itemLore.add(Messages.str("&7Sword & Axe Enchantment"));
                break;
            default:
                Messages.msg(sender, "&cPlease fill the command with /ce <player> <enchant> <rarity> (level)");
                break;
        }
        itemLore.add(Messages.str("&7Drag n' drop to onto item to enchant"));
        meta.setLore(itemLore);
        color += enchant + " " + level;
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        meta.setDisplayName(Messages.str(color));

        book.setItemMeta(meta);

        player.getInventory().addItem(book);
    }

    public static int[] generateChance(String rarity) {
        Random random = new Random();
        int successChance;

        switch (rarity.toLowerCase()) {
            case "common":
                // Average success chance around 75
                successChance = random.nextInt(11) + 90; // 90 to 100
                break;
            case "rare":
                // Average success chance around 50
                successChance = random.nextInt(16) + 85; // 85 to 100
                break;
            case "epic":
                // Average success chance around 25
                successChance = random.nextInt(21) + 80; // 80 to 100
                break;
            case "legendary":
                // Average success chance around 10
                successChance = random.nextInt(31) + 70; // 70 to 100
                break;
            default:
                // Default to a balanced chance if rarity is unknown
                successChance = random.nextInt(101); // 0 to 100
                break;
        }

        int failureChance = 100 - successChance;
        return new int[]{successChance, failureChance};
    }
}
