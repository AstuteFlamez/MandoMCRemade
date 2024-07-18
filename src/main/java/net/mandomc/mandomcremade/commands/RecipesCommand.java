package net.mandomc.mandomcremade.commands;

import net.mandomc.mandomcremade.guis.GUIManager;
import net.mandomc.mandomcremade.guis.RecipeGUI.RecipeHub;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RecipesCommand implements CommandExecutor {
    private final GUIManager guiManager;
    public RecipesCommand(GUIManager guiManager) {
        this.guiManager = guiManager;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;
        this.guiManager.openGUI(new RecipeHub(guiManager), player);
        return true;
    }
}
