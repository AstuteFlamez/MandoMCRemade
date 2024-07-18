package net.mandomc.mandomcremade.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class KothCommand implements CommandExecutor, TabCompleter {

    public KothCommand() {

    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        switch (args[1].toLowerCase()) {
            case "start":
                //new KothRunnable(MandoMCRemade.getInstance()).runTaskTimer(MandoMCRemade.getInstance(), 0L, 20L);
                break;
            case "end":
                if (sender instanceof Player player) player.sendMessage("&cThis feature has not been implemented yet.");
                else Bukkit.getConsoleSender().sendMessage("&cThis feature has not been implemented yet.");
                break;
            default:
                if (sender instanceof Player player) player.sendMessage("&cPlease use the format /mmc koth <start/end>");
                else Bukkit.getConsoleSender().sendMessage("&cPlease use the format /mmc koth <start/end>");
                return false;
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        return Arrays.asList("start", "end");
    }
}
