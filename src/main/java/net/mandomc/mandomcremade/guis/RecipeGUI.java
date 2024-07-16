package net.mandomc.mandomcremade.guis;

import net.mandomc.mandomcremade.utility.CustomItems;
import net.mandomc.mandomcremade.utility.Messages;
import net.mandomc.mandomcremade.utility.Recipes;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class RecipeGUI {

    /* 0  1  2  3  4  5  6  7  8
       9  10 11 12 13 14 15 16 17
       18 19 20 21 22 23 24 25 26
       27 28 29 30 31 32 33 34 35
       36 37 38 39 40 41 42 43 44
       45 46 47 48 49 50 51 51 53
     */

    private static final ItemStack close = CustomItems.item(Material.BARRIER, "&c&lCLOSE", 0);
    private static final ItemStack back = CustomItems.item(Material.ARROW, "&c&lBACK", 0);

    public static Inventory recipes(Player player){
        Inventory inv = Bukkit.createInventory(player, 27, Messages.str("&2&lMandoMC Recipes"));

        inv.setItem(12, CustomItems.item(Material.SHIELD, "&4&lWeapon Recipes", 1));
        inv.setItem(14, CustomItems.item(Material.BEACON, "&6&lPart Recipes", 0));
        inv.setItem(26, close);

        return inv;
    }

    public static Inventory weapons(Player player){
        Inventory inv = Bukkit.createInventory(player, 27, Messages.str("&2&lWeapons"));

        inv.setItem(13, CustomItems.item(Material.SHIELD, "&a&lLightsaber Recipes", 1));
        inv.setItem(25, back);
        inv.setItem(26, close);
        return inv;
    }

    public static Inventory parts(Player player){
        Inventory inv = Bukkit.createInventory(player, 27, Messages.str("&2&lParts"));

        inv.setItem(12, CustomItems.hilt("lukeskywalker"));
        inv.setItem(13, CustomItems.hilt("anakinskywalker"));
        inv.setItem(14, CustomItems.lightsaberCore());
        inv.setItem(25, back);
        inv.setItem(26, close);
        return inv;
    }

    public static Inventory lightsabers(Player player){
        Inventory inv = Bukkit.createInventory(player, 27, Messages.str("&2&lLightsabers"));

        inv.setItem(12, CustomItems.lightSaber("lukeskywalker"));
        inv.setItem(14, CustomItems.lightSaber("anakinskywalker"));
        inv.setItem(25, back);
        inv.setItem(26, close);
        return inv;
    }

    public static void createRecipeInv(Player player, String displayName, ItemStack[] itemList) {
        Inventory inv = Bukkit.createInventory(player, 54, Messages.str(displayName));
        player.openInventory(Recipes.addRecipeToInv(inv, itemList));
    }

}
