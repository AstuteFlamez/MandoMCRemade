package net.mandomc.mandomcremade.koth;

import net.mandomc.mandomcremade.MandoMCRemade;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KOTHCommand implements CommandExecutor {
    private final KOTHManager kothManager;

    public KOTHCommand(KOTHManager kothManager) {
        this.kothManager = kothManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can execute this command.");
            return true;
        }

        Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage("Usage: /koth <start|end|status>");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "start":
                if (kothManager.isKOTHActive()) {
                    player.sendMessage("KOTH event is already active.");
                } else {
                    kothManager.startKOTH();
                }
                break;
            case "end":
                if (kothManager.isKOTHActive()) {
                    kothManager.endKOTH();
                } else {
                    player.sendMessage("No active KOTH event to end.");
                }
                break;
            case "status":
                player.sendMessage("KOTH is " + (kothManager.isKOTHActive() ? "active" : "not active") + ".");
                break;
            default:
                player.sendMessage("Usage: /koth <start|end|status>");
                break;
        }

        return true;
    }
}
