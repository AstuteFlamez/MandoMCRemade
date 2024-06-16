package net.mandomc.mandomcremade.commands;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.config.PunishmentConfig;
import net.mandomc.mandomcremade.config.SaberConfig;
import net.mandomc.mandomcremade.config.WarpConfig;
import net.mandomc.mandomcremade.tasks.KothRunnable;
import net.mandomc.mandomcremade.utility.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Reload implements CommandExecutor {

    MandoMCRemade plugin;

    public Reload(MandoMCRemade plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (sender instanceof Player player) {

            if (player.hasPermission("mmc.admin.reload")) {
                reload();
                Messages.msg(player, "&7You successfully reloaded the MandoMC Plugin");
            } else {
                Messages.noPermission(player);
            }
        }else{
            reload();
        }
        return true;
    }

    public void reload(){
        plugin.reloadConfig();
        PunishmentConfig.reload();
        WarpConfig.reload();
        SaberConfig.reload();
        Bukkit.getConsoleSender().sendMessage("MMCCore successfully reloaded!");
    }
}
