package net.mandomc.mandomcremade.commands;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.config.LangConfig;
import net.mandomc.mandomcremade.config.SaberConfig;
import net.mandomc.mandomcremade.config.WarpConfig;
import net.mandomc.mandomcremade.db.Database;
import net.mandomc.mandomcremade.db.Perks;
import net.mandomc.mandomcremade.managers.GUIManager;
import net.mandomc.mandomcremade.guis.ItemsGUI.ItemHub;
import net.mandomc.mandomcremade.guis.RecipeGUI.RecipeHub;
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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static net.mandomc.mandomcremade.MandoMCRemade.str;

public class MMC implements CommandExecutor, TabCompleter {

    private final GUIManager guiManager;
    private final MandoMCRemade plugin;
    private final Database database;

    public MMC(GUIManager guiManager, MandoMCRemade plugin, Database database) {
        this.guiManager = guiManager;
        this.plugin = plugin;
        this.database = database;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {

            switch (args[0].toLowerCase()) {
                case "give":
                    handleGiveCommand(args);
                    break;
                case "perk":
                    try {
                        handlePerkCommand(args);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }

            return true;
        }

        if (args.length == 0) {
            player.sendMessage("&cPlease fill the command with /mmc <cmd>");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "get":
                if(player.hasPermission("mmc.admin.get")) {
                    this.guiManager.openGUI(new ItemHub(), player);
                }
                break;
            case "give":
                if(player.hasPermission("mmc.admin.give")) {
                    handleGiveCommand(args);
                }
                break;
            case "yaw":
                player.sendMessage("&7Your yaw is: " + player.getLocation().getYaw() + "!");
                break;
            case "pitch":
                player.sendMessage("&7Your pitch is: " + player.getLocation().getPitch() + "!");
                break;
            case "reload":
                if (player.hasPermission("mmc.admin.reload")) {
                    reload();
                    player.sendMessage("&7You successfully reloaded the MandoMC Plugin");
                }
                break;
            case "maintenance":
                handleMaintenanceCommand(player, args);
                break;
            case "test":
                if (player.hasPermission("mmc.admin.test")) {
                    // testing here
                    Bukkit.broadcastMessage("boom shaka laka");
                }
                break;
            case "recipes":
                this.guiManager.openGUI(new RecipeHub(guiManager), player);
                break;
            case "warp":
                handleWarpCommand(player, args);
                break;
            case "koth":
                handleKothCommand(player, args);
                break;
            default:
                player.sendMessage("&cPlease fill the command with /mmc <cmd>");
                break;
        }

        return true;
    }

    private void handleMaintenanceCommand(Player player, String[] args) {
        if (!player.hasPermission("mmc.admin.maintenance")) {
            return;
        }
        if (args.length == 1) {
            return;
        }
        switch (args[1].toLowerCase()) {
            case "on":
                setMaintenanceMode(player, true, "&4&lMandoMC has gone into Maintenance Mode! Try logging again soon.");
                break;
            case "off":
                setMaintenanceMode(player, false, "&7The server has exited maintenance mode.");
                break;
            default:
                player.sendMessage("&cPlease use the format /mmc maintenance <on/off>");
                break;
        }
    }

    private void handleGiveCommand(String[] args) {
        HashMap<String, ItemStack> map = CustomItems.getCustomItemMap();
        String name = args[1];
        String type = args[2];
        if(args.length != 3) return;
        if(Bukkit.getPlayer(name) != null && map.get(type) != null) {
            Player player = Bukkit.getPlayer(name);
            ItemStack item = map.get(type);
            player.getInventory().addItem(item);
        }
    }

    private void handlePerkCommand(String[] args) throws SQLException {

        String action = args[1];
        String type = args[2];
        String name = args[3];

        if(args.length != 4) return;
        if(Bukkit.getPlayer(name) != null) {
            Player player = Bukkit.getPlayer(name);
            Perks perks = database.getPerks(player);

            switch(type){
                case "lightsaberthrow":
                    switch(action){
                        case "lock":
                            perks.setLightsaberThrow(0);
                            database.updatePerks(perks);
                            Bukkit.getConsoleSender().sendMessage(player.getName() + " has had their lightsaber throw perk locked");
                            break;
                        case "unlock":
                            perks.setLightsaberThrow(1);
                            database.updatePerks(perks);
                            Bukkit.getConsoleSender().sendMessage(player.getName() + " has had their lightsaber throw perk unlocked");
                            break;
                        default:
                            break;
                    }
                default:
                    break;
            }
        }
    }

    private void handleWarpCommand(Player player, String[] args) {

    }

    private void handleKothCommand(Player player, String[] args) {
        if (!player.hasPermission("mmc.admin.koth")) {
            return;
        }
        if (args.length == 1) {
            return;
        }

        switch (args[1].toLowerCase()) {
            case "start":
                //new KothRunnable(MandoMCRemade.getInstance()).runTaskTimer(MandoMCRemade.getInstance(), 0L, 20L);
                break;
            case "end":
                player.sendMessage("&cThis feature has not been implemented yet.");
                break;
            default:
                player.sendMessage("&cPlease use the format /mmc koth <start/end>");
                break;
        }
    }

    private void setMaintenanceMode(Player player, boolean enable, String kickMessage) {
        plugin.getConfig().set("Maintenance", enable);
        plugin.saveConfig();
        if (enable) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!p.hasPermission("mmc.admin.maintenance")) {
                    p.kickPlayer(str(kickMessage));
                }
            }
            player.sendMessage("&7The server has gone into maintenance mode.");
        } else {
            player.sendMessage("&7The server has exited maintenance mode.");
        }
    }

    private void reload() {
        plugin.reloadConfig();
        WarpConfig.reload();
        SaberConfig.reload();
        LangConfig.reload();
        Bukkit.getConsoleSender().sendMessage("MMCCore successfully reloaded!");
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> arg0 = Arrays.asList("yaw", "pitch", "test", "recipes", "warp", "maintenance", "reload");
        //List<String> warps = Arrays.asList("alderaan", "arena", "blackmarket", "dathomir", "earth", "geonosis", "hoth", "ilum", "jabba", "kashyyyk", "mandalore", "mines", "moseisley", "morak", "mustafar", "naboo", "umbara");

        switch (args.length) {
            case 1 -> {
                completions.addAll(arg0);
                if (sender.hasPermission("mmc.admin.maintenance")) {
                    completions.add("maintenance");
                } else if (sender.hasPermission("mmc.admin.reload")) {
                    completions.add("reload");
                } else if (sender.hasPermission("mmc.admin.get")) {
                    completions.add("get");
                }
            }
            case 2 -> {
                switch (args[0].toLowerCase()) {
                    case "maintenance" -> {
                        if (sender.hasPermission("mmc.admin.maintenance")) {
                            completions.addAll(Arrays.asList("on", "off"));
                        }
                    }
                    //case "warp" -> completions.addAll(warps);
                    case "koth" -> {
                        if (sender.hasPermission("mmc.admin.koth")) {
                            completions.addAll(Arrays.asList("start", "end"));
                        }
                    }
                    case "give" -> {
                        if (sender.hasPermission("mmc.admin.give")) {
                            for(Player player : Bukkit.getOnlinePlayers()) {
                                completions.add(player.getName());
                            }
                        }
                    }
                }
            }
            case 3 -> {
                switch (args[0].toLowerCase()) {
                    case "give" -> {
                        if (sender.hasPermission("mmc.admin.give")) {
                            completions.addAll(CustomItems.CUSTOM_ITEM_MAP.keySet());
                        }
                    }
                }
            }
        }
        return completions;
    }

}
