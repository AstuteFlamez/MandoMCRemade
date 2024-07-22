package net.mandomc.mandomcremade.commands;

import net.mandomc.mandomcremade.config.LangConfig;
import net.mandomc.mandomcremade.guis.RecipeGUI.RecipeHub;
import net.mandomc.mandomcremade.managers.GUIManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static net.mandomc.mandomcremade.MandoMCRemade.str;

public class RecipesCommand implements CommandExecutor {

    private final GUIManager guiManager;

    public RecipesCommand(GUIManager guiManager) {
        this.guiManager = guiManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        FileConfiguration config = LangConfig.get();
        String prefix = config.getString("Prefix");

        if (!(sender instanceof Player player)){
            String consoleOnly = str("&7This command must be executed by a player.");
            sender.sendMessage(prefix + consoleOnly);
            return true;
        }

        this.guiManager.openGUI(new RecipeHub(guiManager), player);

        return true;
    }
}
