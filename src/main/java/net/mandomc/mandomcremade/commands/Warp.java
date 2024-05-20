package net.mandomc.mandomcremade.commands;

import net.mandomc.mandomcremade.utility.MatrixHolders;
import net.mandomc.mandomcremade.utility.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Warp implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;

            if(args.length==0){
                if(player.hasPermission("mmc.warps")){
                    player.openInventory(warpCreator(player));

                }
            }else if(args.length==1){
                warp(player, args[0]);
            }
        }

        return true;
    }

    public Inventory warpCreator(Player player){

        Inventory warps = Bukkit.createInventory(player, 54, Messages.str("&2&lMandoMC Warps"));

        Object[][] object = MatrixHolders.getWarpsMatrix();

        for (Object[] objects : object) {

            ItemStack itemStack = new ItemStack((ItemStack) objects[10]);
            ItemMeta itemMeta = itemStack.getItemMeta();
            assert itemMeta != null;
            itemMeta.setDisplayName((String) objects[2]);
            ArrayList<String> itemLore = new ArrayList<>();
            itemLore.add((String) objects[3]);
            itemMeta.setLore(itemLore);
            itemStack.setItemMeta(itemMeta);

            warps.setItem((int) objects[1], itemStack);
        }

        for (int slot = 0; slot < 54; slot++) {
            ItemStack item = warps.getItem(slot);if (item == null || item.getType() == Material.AIR) {

                ItemStack itemStack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                ItemMeta itemMeta = itemStack.getItemMeta();
                assert itemMeta != null;
                itemMeta.setDisplayName(" ");
                ArrayList<String> itemLore = new ArrayList<>();
                itemLore.add("");
                itemMeta.setLore(itemLore);
                itemStack.setItemMeta(itemMeta);

                warps.setItem(slot, itemStack);
            }
        }

        return warps;
    }

    public void warp(Player player, String input){
        if (player.hasPermission("mmc.warps")) {
            Location warpLocation = null;

            Object[][] object = MatrixHolders.getWarpsMatrix();
            for (Object[] objects : object) {
                if (objects.length > 1) {
                    if (input == objects[2]) {
                        warpLocation = new Location(Bukkit.getWorld((String) objects[2]), (double) objects[4], (double) objects[5], (double) objects[6], (float) objects[7], (float) objects[8]);
                        Messages.msg(player, "&7You are traveling to " + objects[2] + "&7!");
                        player.teleport(warpLocation);
                    }
                }
            }
            if (warpLocation != null) {
                player.teleport(warpLocation);
            } else {
                player.openInventory(warpCreator(player));
            }}
    }
}
