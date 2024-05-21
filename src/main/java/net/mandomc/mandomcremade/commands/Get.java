package net.mandomc.mandomcremade.commands;

import net.mandomc.mandomcremade.utility.GetEnchant;
import net.mandomc.mandomcremade.utility.GiveOrGet;
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

public class Get implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (sender instanceof Player player) {

            if (player.hasPermission("mmc.admin.get")) {

                switch(args.length){
                    case 2:
                        GiveOrGet.give(player, args[0], args[1]);
                        break;
                    default:
                        Messages.msg(player, "&cPlease fill the command with /get <item> (color)");
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
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> items = Arrays.asList("xwing", "tie", "lightsaberCore", "activationStud", "lukeSkywalkerHilt", "lukeSkywalkerSaber", "anakinSkywalkerHilt", "anakinSkywalkerSaber");
        List<String> colors = Arrays.asList("red", "blue", "green", "purple", "yellow", "white");

        switch(args.length){
            case 1:
                // Provide completions for items
                completions.addAll(items);
                break;
            case 2:
                // Provide completions for colors
                completions.addAll(colors);
                break;
        }

        return completions;
    }
}
