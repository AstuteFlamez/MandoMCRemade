package net.mandomc.mandomcremade.commands;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.config.SaberConfig;
import net.mandomc.mandomcremade.config.WarpConfig;
import net.mandomc.mandomcremade.guis.CustomItemsGUI;
import net.mandomc.mandomcremade.guis.RecipeGUI;
import net.mandomc.mandomcremade.guis.WarpGUI;
import net.mandomc.mandomcremade.tasks.KothRunnable;
import net.mandomc.mandomcremade.utility.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MMC implements CommandExecutor, TabCompleter {

    private final MandoMCRemade plugin;

    public MMC(MandoMCRemade plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {

            switch (args[0].toLowerCase()) {
                case "give":
                    handleGiveCommand(args);
                    break;
            }

            return true;
        }

        if (args.length == 0) {
            Messages.msg(player, "&cPlease fill the command with /mmc <cmd>");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "get":
                if(player.hasPermission("mmc.admin.get")) {
                    player.openInventory(CustomItemsGUI.customItems(player));
                }
                break;
            case "give":
                if(player.hasPermission("mmc.admin.give")) {
                    handleGiveCommand(args);
                }
                break;
            case "yaw":
                Messages.msg(player, "&7Your yaw is: " + player.getLocation().getYaw() + "!");
                break;
            case "pitch":
                Messages.msg(player, "&7Your pitch is: " + player.getLocation().getPitch() + "!");
                break;
            case "reload":
                if (player.hasPermission("mmc.admin.reload")) {
                    reload();
                    Messages.msg(player, "&7You successfully reloaded the MandoMC Plugin");
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
                player.openInventory(RecipeGUI.recipes(player));
                break;
            case "warp":
                handleWarpCommand(player, args);
                break;
            case "koth":
                handleKothCommand(player, args);
                break;
            default:
                Messages.msg(player, "&cPlease fill the command with /mmc <cmd>");
                break;
        }

        return true;
    }

    private void handleMaintenanceCommand(Player player, String[] args) {
        if (!player.hasPermission("mmc.admin.maintenance")) {
            Messages.syntaxError(player);
            return;
        }
        if (args.length == 1) {
            Messages.syntaxError(player);
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
                Messages.msg(player, "&cPlease use the format /mmc maintenance <on/off>");
                break;
        }
    }

    private void handleWarpCommand(Player player, String[] args) {
        if (args.length == 1) {
            player.openInventory(WarpGUI.warpCreator(player));
        } else {
            WarpGUI.warp(player, args[1]);
        }
    }

    private void handleGiveCommand(String[] args) {
        if (args.length <=1 || Bukkit.getPlayer(args[0]) == null) return;
        Bukkit.getPlayer(args[0]).getInventory().addItem(CustomItemsGUI.getItem(args[1]));
    }

    private void handleKothCommand(Player player, String[] args) {
        if (!player.hasPermission("mmc.admin.koth")) {
            Messages.syntaxError(player);
            return;
        }
        if (args.length == 1) {
            Messages.syntaxError(player);
            return;
        }

        switch (args[1].toLowerCase()) {
            case "start":
                new KothRunnable(MandoMCRemade.getInstance()).runTaskTimer(MandoMCRemade.getInstance(), 0L, 20L);
                break;
            case "end":
                Messages.msg(player, "&cThis feature has not been implemented yet.");
                break;
            default:
                Messages.msg(player, "&cPlease use the format /mmc koth <start/end>");
                break;
        }
    }

    private void setMaintenanceMode(Player player, boolean enable, String kickMessage) {
        plugin.getConfig().set("Maintenance", enable);
        plugin.saveConfig();
        if (enable) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!p.hasPermission("mmc.admin.maintenance")) {
                    p.kickPlayer(Messages.str(kickMessage));
                }
            }
            Messages.msg(player, "&7The server has gone into maintenance mode.");
        } else {
            Messages.msg(player, "&7The server has exited maintenance mode.");
        }
    }

    private void reload() {
        plugin.reloadConfig();
        WarpConfig.reload();
        SaberConfig.reload();
        Bukkit.getConsoleSender().sendMessage("MMCCore successfully reloaded!");
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> arg0 = Arrays.asList("yaw", "pitch", "test", "recipes", "warp", "maintenance", "reload");
        List<String> warps = Arrays.asList("alderaan", "arena", "blackmarket", "dathomir", "earth", "geonosis", "hoth", "ilum", "jabba", "kashyyyk", "mandalore", "mines", "moseisley", "morak", "mustafar", "naboo", "umbara");

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
                    case "warp" -> completions.addAll(warps);
                    case "koth" -> {
                        if (sender.hasPermission("mmc.admin.koth")) {
                            completions.addAll(Arrays.asList("start", "end"));
                        }
                    }
                }
            }
        }
        return completions;
    }


}
