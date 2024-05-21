package net.mandomc.mandomcremade.commands;

import net.mandomc.mandomcremade.guis.RecipeGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Recipes implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(sender instanceof Player player){
            player.openInventory(RecipeGUI.recipes(player));
        }

        return true;
    }
}
