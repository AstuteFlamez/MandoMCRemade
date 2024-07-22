package net.mandomc.mandomcremade.commands;

import net.mandomc.mandomcremade.config.LangConfig;
import net.mandomc.mandomcremade.db.Database;
import net.mandomc.mandomcremade.db.PerksTable;
import net.mandomc.mandomcremade.db.data.Perks;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

import static net.mandomc.mandomcremade.MandoMCRemade.str;
public class PerkCommand implements CommandExecutor {

    public PerkCommand() {
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        FileConfiguration config = LangConfig.get();
        String prefix = config.getString("Prefix");
        String usage = str("&cUsage: /perk <unlock/lock> <type>");

        if ((sender instanceof Player)){
            String consoleOnly = str("&7This command must be executed by the console.");
            sender.sendMessage(prefix + consoleOnly);
            return true;
        }

        if (args.length != 3){
            sender.sendMessage(prefix + usage);
            return true;
        }

        String action = args[0];
        String type = args[1];
        String name = args[2];

        Player player = Bukkit.getPlayer(name);
        if (player == null) {
            String playerNotFound = str("&cPlayer not found.");
            sender.sendMessage(prefix + playerNotFound);
            return true;
        }

        String msg = str(player.getName() + " &7has had their lightsaber throw perk " + action + "ed.");

        Perks perks;
        try{
            perks = PerksTable.getPerks(player);
            switch(type){
                case "lightsaberthrow":
                    switch(action){
                        case "lock":
                            perks.setLightsaberThrow(false);
                            PerksTable.updatePerks(perks);
                            sender.sendMessage(prefix + msg);
                            break;
                        case "unlock":
                            perks.setLightsaberThrow(true);
                            PerksTable.updatePerks(perks);
                            sender.sendMessage(prefix + msg);
                            break;
                        default:
                            sender.sendMessage(prefix + usage);
                            break;
                    }
                default:
                    sender.sendMessage(prefix + usage);
                    break;
            }
        }
        catch (SQLException exception) {
            ConsoleCommandSender console = Bukkit.getConsoleSender();
            console.sendMessage(exception.getLocalizedMessage());
            return false;
        }

        return true;
    }
}
