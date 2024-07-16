package net.mandomc.mandomcremade.guis;

import net.mandomc.mandomcremade.utility.CustomItems;
import net.mandomc.mandomcremade.utility.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CustomItemsGUI implements Listener {

    // Method to create and return a custom inventory for the player
    public static Inventory customItems(Player player) {
        // Create a new inventory with 27 slots and a custom title
        Inventory inv = Bukkit.createInventory(player, 27, Messages.str("&c&lCustom Items"));

        // Add custom items to the inventory
        inv.setItem(0, CustomItems.tieFighter());
        inv.setItem(1, CustomItems.xWing("red"));
        inv.setItem(2, CustomItems.xWing("green"));
        inv.setItem(3, CustomItems.xWing("blue"));
        inv.setItem(4, CustomItems.kyber("RedKyber"));
        inv.setItem(5, CustomItems.kyber("GreenKyber"));
        inv.setItem(6, CustomItems.kyber("BlueKyber"));
        inv.setItem(7, CustomItems.kyber("PurpleKyber"));
        inv.setItem(8, CustomItems.kyber("YellowKyber"));
        inv.setItem(9, CustomItems.kyber("WhiteKyber"));
        inv.setItem(10, CustomItems.lightsaberCore());
        inv.setItem(11, CustomItems.hilt("LukeSkywalker"));
        inv.setItem(12, CustomItems.hilt("AnakinSkywalker"));
        inv.setItem(13, CustomItems.lightSaber("LukeSkywalker"));
        inv.setItem(14, CustomItems.lightSaber("AnakinSkywalker"));

        return inv;
    }

    // Method to get a specific custom item based on the provided name
    public static ItemStack getItem(String name) {
        // Use a switch statement to determine which item to return
        switch(name) {
            case "tieFighter":
                return CustomItems.tieFighter();
            case "redXWing":
                return CustomItems.xWing("red");
            case "greenXWing":
                return CustomItems.xWing("green");
            case "blueXWing":
                return CustomItems.xWing("blue");
            case "redKyber":
                return CustomItems.kyber("RedKyber");
            case "greenKyber":
                return CustomItems.kyber("GreenKyber");
            case "blueKyber":
                return CustomItems.kyber("BlueKyber");
            case "purpleKyber":
                return CustomItems.kyber("PurpleKyber");
            case "yellowKyber":
                return CustomItems.kyber("YellowKyber");
            case "whiteKyber":
                return CustomItems.kyber("WhiteKyber");
            case "lightsaberCore":
                return CustomItems.lightsaberCore();
            case "lukeHilt":
                return CustomItems.hilt("LukeSkywalker");
            case "anakinHilt":
                return CustomItems.hilt("AnakinSkywalker");
            case "lukeLightsaber":
                return CustomItems.lightSaber("LukeSkywalker");
            case "anakinLightsaber":
                return CustomItems.lightSaber("AnakinSkywalker");
            default:
                // Return an empty item stack if the name does not match any case
                return new ItemStack(Material.AIR);
        }
    }
}
