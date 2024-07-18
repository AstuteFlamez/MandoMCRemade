package net.mandomc.mandomcremade.commands;

import net.mandomc.mandomcremade.db.Database;
import net.mandomc.mandomcremade.db.PerksTable;
import net.mandomc.mandomcremade.db.data.Perks;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class PerkCommand implements CommandExecutor {

    public PerkCommand() {

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if ((sender instanceof Player)) return false;
        if (args.length != 3) return false;
        String action = args[0];
        String type = args[1];
        String name = args[2];

        Player player = Bukkit.getPlayer(name);
        if (player == null) return false;
        Perks perks;
        try{
            perks = PerksTable.getPerks(player);
            switch(type){
                case "lightsaberthrow":
                    switch(action){
                        case "lock":
                            perks.setLightsaberThrow(false);
                            PerksTable.updatePerks(perks);
                            Bukkit.getConsoleSender().sendMessage(player.getName() + " has had their lightsaber throw perk locked");
                            break;
                        case "unlock":
                            perks.setLightsaberThrow(true);
                            PerksTable.updatePerks(perks);
                            Bukkit.getConsoleSender().sendMessage(player.getName() + " has had their lightsaber throw perk unlocked");
                            break;
                        default:
                            break;
                    }
                default:
                    break;
            }
        }
        catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(e.getLocalizedMessage());
            return false;
        }

        return true;
    }
}
