package net.mandomc.mandomcremade.commands;

import net.mandomc.mandomcremade.MandoMCRemade;
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

import static net.mandomc.mandomcremade.MandoMCRemade.str;

public class MaintenanceCommand implements CommandExecutor, TabCompleter {

    private final MandoMCRemade plugin;
    public MaintenanceCommand(MandoMCRemade plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        switch (args[1].toLowerCase()) {
            case "on":
                setMaintenanceMode(sender, true, "&4&lMandoMC has gone into Maintenance Mode! Try logging again soon.");
                break;
            case "off":
                setMaintenanceMode(sender, false, "&7The server has exited maintenance mode.");
                break;
            default:
                if (sender instanceof Player player) player.sendMessage("&cPlease use the format /mmc maintenance <on/off>");
                else Bukkit.getConsoleSender().sendMessage("&cPlease use the format /mmc maintenance <on/off>");
                break;
        }
        return true;
    }

    private void setMaintenanceMode(@NotNull CommandSender sender, boolean enable, String kickMessage) {
        plugin.getConfig().set("Maintenance", enable);
        plugin.saveConfig();
        if (enable) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!p.hasPermission("mmc.admin.maintenance")) {
                    p.kickPlayer(str(kickMessage));
                }
            }
            if (sender instanceof Player player) player.sendMessage("&7The server has gone into maintenance mode.");
            else Bukkit.getConsoleSender().sendMessage("&7The server has gone into maintenance mode.");
        } else {
            if (sender instanceof Player player) player.sendMessage("&7The server has exited maintenance mode.");
            else Bukkit.getConsoleSender().sendMessage("&7The server has exited maintenance mode.");
        }
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        return Arrays.asList("on", "off");
    }
}
