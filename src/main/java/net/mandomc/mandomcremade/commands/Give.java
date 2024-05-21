package net.mandomc.mandomcremade.commands;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.tasks.KothRunnable;
import net.mandomc.mandomcremade.utility.GiveOrGet;
import net.mandomc.mandomcremade.utility.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Give implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (sender instanceof Player player) {

            if (player.hasPermission("mmc.admin.give")) {

                switch(args.length){
                    case 2:
                        GiveOrGet.give(player, args[0], args[1]);
                        break;
                    case 3:
                        GiveOrGet.give(player, args[0], args[1], args[2]);
                        break;
                    default:
                        Messages.msg(player, "&cPlease fill the command with /give <player> <item> (color)");
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
                // Provide completions for players
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    completions.add(onlinePlayer.getName());
                }
                break;
            case 2:
                // Provide completions for items
                completions.addAll(items);
                break;
            case 3:
                // Provide completions for colors
                completions.addAll(colors);
                break;
        }
        return completions;
    }
}
