package net.mandomc.mandomcremade.koth;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.config.LangConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class KOTHCommand implements CommandExecutor {
    private final KOTHManager kothManager;

    public KOTHCommand(KOTHManager kothManager) {
        this.kothManager = kothManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        FileConfiguration config = LangConfig.get();
        String prefix = config.getString("Prefix");

        if (!(sender instanceof Player player)) {
            executeCommand(sender, args, prefix);
            return true;
        }

        executeCommand(player, args, prefix);
        return true;
    }

    private void executeCommand(CommandSender sender, String[] args, String prefix) {
        if (args.length == 0) {
            sender.sendMessage(MandoMCRemade.str(prefix + "Usage: /koth <start|end|status>"));
            return;
        }

        switch (args[0].toLowerCase()) {
            case "start":
                if (kothManager.isKOTHActive()) {
                    sender.sendMessage(prefix + "KOTH event is already active.");
                } else {
                    kothManager.startKOTH();
                }
                break;
            case "end":
                if (kothManager.isKOTHActive()) {
                    kothManager.endKOTH();
                } else {
                    sender.sendMessage(prefix + "No active KOTH event to end.");
                }
                break;
            case "status":
                sender.sendMessage(prefix + "KOTH is " + (kothManager.isKOTHActive() ? "active" : "not active") + ".");
                break;
            default:
                sender.sendMessage(prefix + "Usage: /koth <start|end|status>");
                break;
        }
    }
}
