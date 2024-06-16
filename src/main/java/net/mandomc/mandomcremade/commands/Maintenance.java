package net.mandomc.mandomcremade.commands;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.utility.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Maintenance implements CommandExecutor {

    MandoMCRemade plugin;

    public Maintenance(MandoMCRemade plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (commandSender instanceof Player player) {

            if (player.hasPermission("mmc.admin.maintenance")) {

                if(args.length == 1){
                    switch (args[0]){
                        case "on":
                            plugin.getConfig().set("Maintenance", true);
                            plugin.saveConfig();
                            for(Player p : Bukkit.getOnlinePlayers()){
                                if(!p.hasPermission("mmc.admin.maintenance")){
                                    p.kickPlayer(Messages.str("&4&lMandoMC has gone into Maintenance Mode! Try logging again soon."));
                                }
                            }
                            Messages.msg(player, "&7The server has gone into maintenance mode.");
                            break;
                        case "off":
                            plugin.getConfig().set("Maintenance", false);
                            plugin.saveConfig();
                            Messages.msg(player, "&7The server has exited maintenance mode.");
                            break;
                        default:
                            Messages.msg(player, "&7Please use the format /maintenance <on/off>");
                            break;
                    }
                }else{
                    Messages.msg(player, "&7Please use the format /maintenance <on/off>");
                }
            } else {
                Messages.noPermission(player);
            }
        }
        return true;
    }
}
