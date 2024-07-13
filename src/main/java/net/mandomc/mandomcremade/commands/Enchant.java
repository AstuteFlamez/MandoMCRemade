package net.mandomc.mandomcremade.commands;

import net.mandomc.mandomcremade.utility.GetEnchant;
import net.mandomc.mandomcremade.utility.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Enchant implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (commandSender instanceof Player player) {

            Player sender = (Player) commandSender;

            if (player.hasPermission("mmc.admin.enchants")) {

                switch(args.length){
                    case 3:
                        GetEnchant.getEnchant(sender, player, args[1], args[2], "I", -1);
                        break;
                    case 4:
                        GetEnchant.getEnchant(sender, player, args[1], args[2], args[3], -1);
                        break;
                    case 5:
                        GetEnchant.getEnchant(sender, player, args[1], args[2], args[3], Integer.parseInt(args[4]));
                        break;
                    default:
                        Messages.msg(player, "&cPlease fill the command with /ce <player> <enchant> <rarity> (level)");
                        break;
                }
            } else {
                Messages.noPermission(player);
            }
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> enchants = Arrays.asList("jedisluck");
        List<String> rarities = Arrays.asList("common", "rare", "epic", "legendary");
        List<String> levels = Arrays.asList("I", "II", "III", "IV", "V");
        switch(args.length){
            case 1:
                // Provide completions for players
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    completions.add(onlinePlayer.getName());
                }
                break;
            case 2:
                // Provide completions for enchants
                completions.addAll(enchants);
                break;
            case 3:
                // Provide completions for rarities
                completions.addAll(rarities);
                break;
            case 4:
                // Provide completions for levels
                completions.addAll(levels);
                break;
            case 5:
                // Provide completions for numbers
                for (int i = 1; i < 101; i++) {
                    completions.add("" + i);
                }
        }
        return completions;
    }
}
