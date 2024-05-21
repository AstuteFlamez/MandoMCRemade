package net.mandomc.mandomcremade.utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GiveOrGet {

    public static void give(Player player, String user, String input){
        Player target = Bukkit.getServer().getPlayerExact(user);
        if(target == null){
            player.sendMessage(ChatColor.RED + "You did not provide an online player!");
        }else{
            get(target, input);
        }
    }

    public static void give(Player player, String user, String input, String color){
        Player target = Bukkit.getServer().getPlayerExact(user);
        if(target == null){
            player.sendMessage(ChatColor.RED + "You did not provide an online player!");
        }else{
            get(target, input, color);
        }
    }

    public static void get(Player player, String input) {
        input = input.toLowerCase();

        switch (input) {
            case "xwing":
                player.getInventory().addItem(CustomItems.xWing("red"));
                break;
            case "tie":
                player.getInventory().addItem(CustomItems.tieFighter());
                break;
            case "lightsabercore":
                player.getInventory().addItem(CustomItems.lightsaberCore());
                break;
            case "lukeskywalkerhilt":
                player.getInventory().addItem(CustomItems.hilt("lukeSkywalker"));
                break;
            case "anakinskywalkerhilt":
                player.getInventory().addItem(CustomItems.hilt("anakinSkywalker"));
                break;
            case "lukeskywalkersaber":
                player.getInventory().addItem(CustomItems.lightSaber("lukeSkywalker"));
                break;
            case "anakinskywalkersaber":
                player.getInventory().addItem(CustomItems.lightSaber("anakinSkywalker"));
                break;
            case "activationstud":
                player.getInventory().addItem(CustomItems.activationStud());
                break;
            default:
                Messages.msg(player, "&cPlease fill the command with /give <player> <item> (color)");
                break;
        }
    }

    public static void get(Player player, String input, String color) {
        input = input.toLowerCase();
        color = color.toLowerCase();

        switch (input) {
            case "kyber":
                switch (color) {
                    case "red":
                        player.getInventory().addItem(CustomItems.kyber("red"));
                        break;
                    case "blue":
                        player.getInventory().addItem(CustomItems.kyber("blue"));
                        break;
                    case "green":
                        player.getInventory().addItem(CustomItems.kyber("green"));
                        break;
                    case "purple":
                        player.getInventory().addItem(CustomItems.kyber("purple"));
                        break;
                    case "yellow":
                        player.getInventory().addItem(CustomItems.kyber("yellow"));
                        break;
                    case "white":
                        player.getInventory().addItem(CustomItems.kyber("white"));
                        break;
                    default:
                        Messages.msg(player, "&cPlease fill the command with /give <player> <item> (color)");
                        break;
                }
                break;
            case "xwing":
                switch (color) {
                    case "red":
                        player.getInventory().addItem(CustomItems.xWing("red"));
                        break;
                    case "blue":
                        player.getInventory().addItem(CustomItems.xWing("blue"));
                        break;
                    case "green":
                        player.getInventory().addItem(CustomItems.xWing("green"));
                        break;
                    default:
                        Messages.msg(player, "&cPlease fill the command with /give <player> <item> (color)");
                        break;
                }
                break;
            default:
                Messages.msg(player, "&cPlease fill the command with /give <player> <item> (color)");
                break;
        }
    }
}
