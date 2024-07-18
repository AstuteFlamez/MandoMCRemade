package net.mandomc.mandomcremade.commands;

import net.mandomc.mandomcremade.config.LangConfig;
import net.mandomc.mandomcremade.utility.CustomItems;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static net.mandomc.mandomcremade.MandoMCRemade.str;


public class GiveCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        // Check if the correct number of arguments are provided
        FileConfiguration config = LangConfig.get();
        String prefix = str(config.getString("Prefix"));

        if(sender instanceof Player player){
            if(!player.hasPermission("mmc.admin.give")){
                String noPermission = str(config.getString("NoPermission"));
                player.sendMessage(prefix + noPermission);
            }
        }

        if (args.length != 2) {
            String usage = str("&cUsage: /give <player> <item>");
            sender.sendMessage(prefix + usage);
            return true;
        }

        String name = args[0];
        String type = args[1];

        // Ensure the player and item type are valid
        Player receiver = Bukkit.getPlayer(name);
        HashMap<String, ItemStack> map = CustomItems.getCustomItemMap();
        ItemStack item = map.get(type);

        if (receiver == null) {
            String playerNotFound = str("&cPlayer not found.");
            sender.sendMessage(prefix + playerNotFound);
            return true;
        }

        if (item == null) {
            String itemNotFound = str("&cItem not found.");
            sender.sendMessage(prefix + itemNotFound);
            return true;
        }

        // Give the item to the player
        receiver.getInventory().addItem(item);

        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        type = meta.getDisplayName();

        String giveMSG = str("&7You gave " + type + " &7to " + name + ".");
        sender.sendMessage(prefix + giveMSG);
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
