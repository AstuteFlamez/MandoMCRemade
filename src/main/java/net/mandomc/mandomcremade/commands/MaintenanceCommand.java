package net.mandomc.mandomcremade.commands;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.config.LangConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

import static net.mandomc.mandomcremade.MandoMCRemade.str;

public class MaintenanceCommand implements CommandExecutor, TabCompleter {

    private final MandoMCRemade plugin;
    public MaintenanceCommand(MandoMCRemade plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        FileConfiguration config = LangConfig.get();
        String prefix = config.getString("Prefix");

        String usage = str("&cUsage: /maintenance <on/off>");
        String enterMaintenance = str("&7The server has gone into maintenance mode.");
        String exitMaintenance = str("&7The server has exited of maintenance mode.");

        if (sender instanceof Player player) {
            if(!player.hasPermission("mmc.admin.maintenance")) {
                String noPermission = str(config.getString("NoPermission"));
                player.sendMessage(prefix + noPermission);
                return true;
            }
        }

        switch (args[0].toLowerCase()) {
            case "on":
                setMaintenanceMode(sender, true);
                sender.sendMessage(prefix + enterMaintenance);
                break;
            case "off":
                setMaintenanceMode(sender, false);
                sender.sendMessage(prefix + exitMaintenance);
                break;
            default:
                if (sender instanceof Player player){
                    player.sendMessage(prefix + usage);
                }
                else sender.sendMessage(prefix + usage);
                break;
        }
        return true;
    }

    private void setMaintenanceMode(@NotNull CommandSender sender, boolean enable) {
        plugin.getConfig().set("Maintenance", enable);
        plugin.saveConfig();
        String kickMessage = str("&4&lMandoMC has gone into Maintenance Mode! Try logging again soon.");
        if (enable) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!p.hasPermission("mmc.admin.maintenance")) {
                    p.kickPlayer(str(kickMessage));
                }
            }
        }
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        return Arrays.asList("on", "off");
    }
}
