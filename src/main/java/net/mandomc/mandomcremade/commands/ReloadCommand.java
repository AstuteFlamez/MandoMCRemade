package net.mandomc.mandomcremade.commands;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.config.LangConfig;
import net.mandomc.mandomcremade.config.SaberConfig;
import net.mandomc.mandomcremade.config.WarpConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static net.mandomc.mandomcremade.MandoMCRemade.str;

public class ReloadCommand implements CommandExecutor {

    private final MandoMCRemade plugin;
    public ReloadCommand(final MandoMCRemade plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        FileConfiguration config = LangConfig.get();
        String prefix = config.getString("Prefix");
        String reload = str("&7MandoMCRemade successfully reloaded!");

        if (!(sender instanceof Player player)){
            handleReload(prefix, reload);
            return true;
        }

        if(!player.hasPermission("mmc.admin.reload")) {
            String noPermission = str(config.getString("NoPermission"));
            player.sendMessage(prefix + noPermission);
            return true;
        }

        handleReload(prefix, reload);
        player.sendMessage(prefix + reload);
        return true;
    }

    public void handleReload(String prefix, String reload){
        ConsoleCommandSender console = Bukkit.getConsoleSender();
        plugin.reloadConfig();
        WarpConfig.reload();
        SaberConfig.reload();
        LangConfig.reload();

        console.sendMessage(prefix + reload);
    }
}
