package net.mandomc.mandomcremade.commands;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.tasks.KothRunnable;
import net.mandomc.mandomcremade.utility.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ForceStartKoth implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (sender instanceof Player player) {

            if (player.hasPermission("mmc.admin.koth")) {
                new KothRunnable(MandoMCRemade.getInstance()).runTaskTimer(MandoMCRemade.getInstance(), 0L, 20L);
            } else {
                Messages.noPermission(player);
            }
        }
        return true;
    }
}
