package net.mandomc.mandomcremade.commands;

import net.mandomc.mandomcremade.config.LangConfig;
import net.mandomc.mandomcremade.guis.ItemsGUI.ItemHub;
import net.mandomc.mandomcremade.managers.GUIManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static net.mandomc.mandomcremade.MandoMCRemade.str;

public class GetCommand implements CommandExecutor {

    private final GUIManager guiManager;

    public GetCommand(GUIManager guiManager) {
        this.guiManager = guiManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("You must be a player to use this command.");
            return true;
        }

        FileConfiguration config = LangConfig.get();
        String prefix = str(config.getString("Prefix"));

        if (!player.hasPermission("mmc.admin.get")) {
            String noPermission = str(config.getString("NoPermission"));
            player.sendMessage(prefix + noPermission);
            return true;
        }

        guiManager.openGUI(new ItemHub(guiManager), player);
        return true;
    }
}
