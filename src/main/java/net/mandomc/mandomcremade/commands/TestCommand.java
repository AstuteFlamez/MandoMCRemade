package net.mandomc.mandomcremade.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (sender instanceof Player player)
        {
            Map<String, Object> data = player.getInventory().getItemInMainHand().serialize();

            Bukkit.getConsoleSender().sendMessage(data.toString());
            player.sendMessage(data.toString());

            return true;
        }


        return false;
    }
}
