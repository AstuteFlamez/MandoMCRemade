package net.mandomc.mandomcremade.commands;

import net.mandomc.mandomcremade.utility.ForceMethods;
import net.mandomc.mandomcremade.utility.Messages;
import net.mandomc.mandomcremade.utility.Particles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutionException;

public class Test implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(sender instanceof Player player){
            if(player.hasPermission("mmc.admin.test")){
                ForceMethods.pull(player, 1);
            }
        }

        return true;
    }
}
