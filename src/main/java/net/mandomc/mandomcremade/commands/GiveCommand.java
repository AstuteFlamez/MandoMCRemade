package net.mandomc.mandomcremade.commands;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.db.Database;
import net.mandomc.mandomcremade.guis.GUIManager;
import net.mandomc.mandomcremade.utility.CustomItems;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GiveCommand implements CommandExecutor, TabCompleter {

    public GiveCommand() {}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        HashMap<String, ItemStack> map = CustomItems.getCustomItemMap();
        String name = args[0];
        if(args.length != 2) return false;
        String type = args[1];
        if(Bukkit.getPlayer(name) == null || map.get(type) == null) return false;

        Player player = Bukkit.getPlayer(name);
        ItemStack item = map.get(type);
        player.getInventory().addItem(item);
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1)
        {
            for(Player player : Bukkit.getOnlinePlayers()) {
                completions.add(player.getName());
            }
        } else if (args.length == 2) {
            if (sender.hasPermission("mmc.admin.give")) {
                completions.addAll(CustomItems.CUSTOM_ITEM_MAP.keySet());
            }
        }
        return completions;
    }
}
