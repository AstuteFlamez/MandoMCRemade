package net.mandomc.mandomcremade.commands;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.config.SaberConfig;
import net.mandomc.mandomcremade.config.WarpConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand implements CommandExecutor {

    private final MandoMCRemade plugin;
    public ReloadCommand(final MandoMCRemade plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        plugin.reloadConfig();
        WarpConfig.reload();
        SaberConfig.reload();
        Bukkit.getConsoleSender().sendMessage("MMCCore successfully reloaded!");
        if (sender instanceof Player player) player.sendMessage("&7You successfully reloaded the MandoMC Plugin");
        return true;
    }
}
