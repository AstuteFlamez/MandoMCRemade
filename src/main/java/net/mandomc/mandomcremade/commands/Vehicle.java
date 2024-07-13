package net.mandomc.mandomcremade.commands;

import net.mandomc.mandomcremade.utility.CustomItems;
import net.mandomc.mandomcremade.utility.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Vehicle implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(sender instanceof Player player){

            Inventory conv = Bukkit.createInventory(player, 54, Messages.str("&4&lVehicles"));

            ItemStack item = new ItemStack(Material.DIAMOND);
            ItemMeta itemMeta = item.getItemMeta();
            assert itemMeta != null;
            itemMeta.setDisplayName(Messages.str("&3&lPick a Vehicle!"));
            item.setItemMeta(itemMeta);

            conv.setItem(13, item);
            conv.setItem(30, CustomItems.xWing("red"));
            conv.setItem(32, CustomItems.tieFighter());
            //conv.setItem(53, CustomItems.close());

            player.openInventory(conv);
        }

        return true;
    }

    @EventHandler
    public void onConvClick(InventoryClickEvent event){

        Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem() == null) {
            return;
        }

        if (event.getView().getTitle().equalsIgnoreCase(Messages.str("Vehicles"))) {
            event.setCancelled(true);
            switch(event.getSlot()){
                case 30:
                    Inventory xWing = Bukkit.createInventory(player, 9, Messages.str("&c&lX-Wing Starfighter"));

                    xWing.setItem(3, CustomItems.xWing("red"));
                    xWing.setItem(4, CustomItems.xWing("green"));
                    xWing.setItem(5, CustomItems.xWing("blue"));
                    //xWing.setItem(7, CustomItems.back());
                    //xWing.setItem(8, CustomItems.close());

                    player.openInventory(xWing);
                    break;
                case 32:
                    Inventory tie = Bukkit.createInventory(player, 9, Messages.str("&8&lTie Fighter"));

                    tie.setItem(4, CustomItems.tieFighter());
                    //tie.setItem(7, CustomItems.back());
                    //tie.setItem(8, CustomItems.close());

                    player.openInventory(tie);
                    break;
                case 53:
                    player.closeInventory();
                    break;
            }
        }

        if (event.getView().getTitle().equalsIgnoreCase(Messages.str("&c&lX-Wing Starfighter"))){
            event.setCancelled(true);
            /*if(player.getItemInUse() == null){
                return;
            }*/
            ItemStack inUse = player.getInventory().getItemInMainHand();

            player.closeInventory();

            switch(event.getSlot()){
                case 3:
                    if(inUse.isSimilar(CustomItems.xWing("green")) || inUse.isSimilar(CustomItems.xWing("blue"))){
                        player.getInventory().setItemInMainHand(CustomItems.xWing("red"));
                        player.sendMessage(Messages.str("&7Converted your X-Wing into a &cRed Squadron X-Wing&7."));
                    }else if(inUse.isSimilar(CustomItems.xWing("red"))){
                        player.sendMessage(Messages.str("&7You already have a &cRed Squadron X-Wing&7."));
                    }else{
                        player.sendMessage(Messages.str("&7You are not holding a &cRed Squadron X-Wing &7in your hand!"));
                    }
                    break;
                case 4:
                    if(inUse.isSimilar(CustomItems.xWing("red")) || inUse.isSimilar(CustomItems.xWing("blue"))){
                        player.getInventory().setItemInMainHand(CustomItems.xWing("green"));
                        player.sendMessage(Messages.str("&7Converted your X-Wing into a &2Green Squadron X-Wing&7."));
                    }else if(inUse.isSimilar(CustomItems.xWing("green"))){
                        player.sendMessage(Messages.str("&7You already have a &2Green Squadron X-Wing&7."));
                    }else{
                        player.sendMessage(Messages.str("&7You are not holding a &2Green Squadron X-Wing &7in your hand!"));
                    }
                    break;
                case 5:
                    if(inUse.isSimilar(CustomItems.xWing("red")) || inUse.isSimilar(CustomItems.xWing("green"))){
                        player.getInventory().setItemInMainHand(CustomItems.xWing("blue"));
                        player.sendMessage(Messages.str("&7Converted your X-Wing into a &3Blue Squadron X-Wing&7."));
                    }else if(inUse.isSimilar(CustomItems.xWing("blue"))){
                        player.sendMessage(Messages.str("&7You already have a &3Blue Squadron X-Wing&7."));
                    }else{
                        player.sendMessage(Messages.str("&7You are not holding a &3Blue Squadron X-Wing &7in your hand!"));
                    }
                    break;
                case 7:
                    player.performCommand("vehicle");
                    break;
                //don't need 8 bc close inventory is before switch statement
            }
        }

        if (event.getView().getTitle().equalsIgnoreCase(Messages.str("&8&lTie Fighter"))){
            event.setCancelled(true);

            player.closeInventory();

            switch(event.getSlot()){
                case 4:
                    Messages.str("&7Sorry, we only have one Tie Fighter variant...mining guild soon!");
                case 7:
                    player.performCommand("vehicle");
                    break;
                //don't need 8 bc close inventory is before switch statement
            }
        }
    }

}
