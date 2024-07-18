package net.mandomc.mandomcremade.commands;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.config.LangConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Maintenance implements CommandExecutor, TabCompleter {

    private final MandoMCRemade plugin;

    public Maintenance(MandoMCRemade plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        /* 
        Get the configuration for language settings 
        */
        FileConfiguration config = LangConfig.get();
        ConsoleCommandSender console = Bukkit.getConsoleSender();
        String incorrectSyntaxMessage = config.getString("Prefix") + config.getString("IncorrectSyntax");
        String incorrectPermissions = config.getString("Prefix") + config.getString("NoPermission");

        if (!(sender instanceof Player player)) {
            handleMaintenanceCommand(args, console, incorrectSyntaxMessage);
            return true;
        }

        /* 
        Handle player commands 
        */
        if(player.hasPermission("mmc.admin.maintenance")) {
            if (args.length == 1) {
                handleMaintenanceCommand(args, player, incorrectSyntaxMessage);
            } else {
                player.sendMessage(incorrectSyntaxMessage);
            }
        }else{
            player.sendMessage(incorrectPermissions);
        }

        return true;
    }

    private void handleMaintenanceCommand(String[] args, CommandSender sender, String incorrectSyntaxMessage) {
        /* Process the maintenance command arguments */
        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "on":
                    setMaintenanceMode(true);
                    sender.sendMessage(sender instanceof Player ?
                            MandoMCRemade.str("&7The server has gone into maintenance mode.") :
                            incorrectSyntaxMessage);
                    break;
                case "off":
                    setMaintenanceMode(false);
                    sender.sendMessage(sender instanceof Player ?
                            MandoMCRemade.str("&7The server has exited maintenance mode.") :
                            incorrectSyntaxMessage);
                    break;
                default:
                    sender.sendMessage(incorrectSyntaxMessage);
            }
        } else {
            sender.sendMessage(incorrectSyntaxMessage);
        }
    }

    private void setMaintenanceMode(boolean enable) {
        /* Update the configuration and kick players if maintenance mode is enabled */
        plugin.getConfig().set("Maintenance", enable);
        plugin.saveConfig();
        if (enable) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!player.hasPermission("mmc.admin.maintenance")) {
                    player.kickPlayer(LangConfig.get().getString("MaintenanceKick"));
                }
            }
        }
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        /* Provide tab completions for the command */
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.addAll(Arrays.asList("on", "off"));
        }
        return completions;
    }
}
