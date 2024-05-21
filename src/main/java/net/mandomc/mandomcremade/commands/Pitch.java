package net.mandomc.mandomcremade.commands;

import net.mandomc.mandomcremade.utility.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Pitch implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(sender instanceof Player player){
            Messages.msg(player, "&7Your pitch is: " + player.getLocation().getPitch() + "!");
        }

        return true;
    }
}
