package net.mandomc.mandomcremade.utility;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class GetEnchant {

    public static void getEnchant(Player sender, Player player, String rarity, String enchant, int level){

        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();
        assert meta != null;
        meta.addStoredEnchant(Enchantment.LUCK, 1, true);

        String romanLevel = "";
        switch(level){
            case 1:
                romanLevel = " I";
                break;
            case 2:
                romanLevel = " II";
                break;
            case 3:
                romanLevel = " III";
                break;
            case 4:
                romanLevel = " IV";
                break;
            case 5:
                romanLevel = " V";
                break;
            default:
                Messages.msg(sender, "&cPlease fill the command with /ce <player> <enchant> <rarity> (level)");
                break;
        }

        String color = "";
        switch(rarity){
            case "common":
                color = "&e";
                break;
            case "rare":
                color = "&b";
                break;
            case "epic":
                color = "&5";
                break;
            case "legendary":
                color = "&6";
                break;
            default:
                Messages.msg(sender, "&cPlease fill the command with /ce <player> <enchant> <rarity> (level)");
                break;
        }
        color += enchant + romanLevel;

        meta.setDisplayName(color);
        book.setItemMeta(meta);

        player.getInventory().addItem(book);
    }

}
