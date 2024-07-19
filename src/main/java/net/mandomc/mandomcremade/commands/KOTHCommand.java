package net.mandomc.mandomcremade.commands;

import net.mandomc.mandomcremade.config.LangConfig;
import net.mandomc.mandomcremade.managers.KOTHManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

import static net.mandomc.mandomcremade.MandoMCRemade.str;

public class KOTHCommand implements CommandExecutor, TabCompleter {
    private final KOTHManager kothManager;

    public KOTHCommand(KOTHManager kothManager) {
        this.kothManager = kothManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        FileConfiguration config = LangConfig.get();
        String prefix = config.getString("EventPrefix");

        if (!(sender instanceof Player player)) {
            executeCommand(sender, args, prefix);
            return true;
        }

        if(!player.hasPermission("mmc.admin.koth")) {
            String noPermission = str(config.getString("NoPermission"));
            player.sendMessage(prefix + noPermission);
            return true;
        }

        if(kothManager.isKOTHActive()) return true;

        executeCommand(player, args, prefix);
        return true;
    }

    private void executeCommand(CommandSender sender, String[] args, String prefix) {
        String usage = str("&cUsage: /koth <start|end|status>");

        if (args.length == 0) {
            sender.sendMessage(str(prefix + usage));
            return;
        }

        switch (args[0].toLowerCase()) {
            case "start":
                if (kothManager.isKOTHActive()) {
                    String active = str("&7The KOTH event is already active.");
                    sender.sendMessage(prefix + active);
                } else {
                    kothManager.startKOTH();
                }
                break;
            case "end":
                if (kothManager.isKOTHActive()) {
                    kothManager.endKOTH();
                } else {
                    String unactive = str("&7There is no active KOTH event to end.");
                    sender.sendMessage(prefix + unactive);
                }
                break;
            case "status":
                String status = str("&7KOTH is " + (kothManager.isKOTHActive() ? "active" : "not active") + ".");
                sender.sendMessage(prefix + status);
                break;
            default:
                sender.sendMessage(prefix + usage);
                break;
        }
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return Arrays.asList("start", "end", "status");
    }
}
