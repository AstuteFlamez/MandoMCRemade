package net.mandomc.mandomcremade.guis;

import net.mandomc.mandomcremade.config.WarpConfig;
import net.mandomc.mandomcremade.utility.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class WarpGUI {

    private static final String[] WARP_NAMES = {
            "Dathomir", "Mustafar", "Earth", "Umbara", "Alderaan", "Concordia",
            "Kashyyyk", "Naboo", "Ilum", "Geonosis", "Morak", "Tatooine",
            "Hoth", "Mandalore"
    };

    public static Inventory warpCreator(Player player) {
        Inventory warps = Bukkit.createInventory(player, 54, Messages.str("&2&lMandoMC Warps"));
        Object[][] warpData = getWarpsMatrix();

        for (Object[] warp : warpData) {
            ItemStack itemStack = new ItemStack((ItemStack) warp[10]);
            ItemMeta itemMeta = itemStack.getItemMeta();
            assert itemMeta != null;
            itemMeta.setDisplayName((String) warp[2]);
            itemMeta.setLore(List.of((String) warp[3]));
            itemStack.setItemMeta(itemMeta);
            warps.setItem((int) warp[1], itemStack);
        }

        fillEmptySlots(warps, new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1));

        return warps;
    }

    public static void warp(Player player, String input) {
        if (player.hasPermission("mmc.warps")) {
            Object[][] warpData = getWarpsMatrix();
            for (Object[] warp : warpData) {
                if (input.equalsIgnoreCase((String) warp[0])) {
                    Location warpLocation = new Location(Bukkit.getWorld((String) warp[0]),
                            (double) warp[4], (double) warp[5], (double) warp[6],
                            (float) warp[7], (float) warp[8]);
                    Messages.msg(player, "&7You are traveling to " + warp[2] + "&7!");
                    player.teleport(warpLocation);
                    return;
                }
            }
            player.openInventory(warpCreator(player));
        }
    }

    private static void fillEmptySlots(Inventory inventory, ItemStack filler) {
        ItemMeta itemMeta = filler.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(" ");
        itemMeta.setLore(List.of(""));
        filler.setItemMeta(itemMeta);

        for (int slot = 0; slot < inventory.getSize(); slot++) {
            if (inventory.getItem(slot) == null || inventory.getItem(slot).getType() == Material.AIR) {
                inventory.setItem(slot, filler);
            }
        }
    }

    private static Object[] createWarpRow(String warpName) {
        return new Object[]{
                warpName.toLowerCase(),
                WarpConfig.get().getInt(warpName + "Slot"),
                WarpConfig.get().getString(warpName + "Name"),
                WarpConfig.get().getStringList(warpName + "Desc"),
                WarpConfig.get().getDouble(warpName + "X"),
                WarpConfig.get().getDouble(warpName + "Y"),
                WarpConfig.get().getDouble(warpName + "Z"),
                (float) WarpConfig.get().getDouble(warpName + "Yaw"),
                (float) WarpConfig.get().getDouble(warpName + "Pitch"),
                WarpConfig.get().getInt(warpName + "Rank"),
                WarpConfig.get().getItemStack(warpName + "Block")
        };
    }

    public static Object[][] getWarpsMatrix() {
        Object[][] matrix = new Object[WARP_NAMES.length][11];
        for (int i = 0; i < WARP_NAMES.length; i++) {
            matrix[i] = createWarpRow(WARP_NAMES[i]);
        }
        return matrix;
    }
}
