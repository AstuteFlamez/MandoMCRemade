package net.mandomc.mandomcremade.commands;

import net.mandomc.mandomcremade.utility.Messages;
import org.bukkit.Bukkit;
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

public class MMC implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (sender instanceof Player player) {

            switch(args.length){

                case 1:

                    switch (args[0]) {

                        case "yaw":
                            Messages.msg(player, "&7Your yaw is: " + player.getLocation().getYaw() + "!");
                            break;
                        case "pitch":
                            Messages.msg(player, "&7Your pitch is: " + player.getLocation().getPitch() + "!");
                            break;
                        default:
                            Messages.msg(player, "&cPlease fill the command with /mmc <cmd>");
                            break;
                    }

                    break;
                case 2:

                    break;
                case 3:

                    break;
                default:
                    Messages.msg(player, "&cPlease fill the command with /mmc <cmd>");
                    break;

            }
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> arg0 = Arrays.asList("yaw", "pitch", "test", "recipes", "warp", "vehicle");
        List<String> colors = Arrays.asList("red", "blue", "green", "purple", "yellow", "white");

        switch(args.length){
            case 1:
                completions.addAll(arg0);

                if(sender.hasPermission("mmc.admin.maintenance")){
                    completions.add("maintenance");
                }else if(sender.hasPermission("mmc.admin.reload")){
                    completions.add("reload");
                }

                break;
            case 2:

                break;
            case 3:

                break;
        }
        return completions;
    }
}
