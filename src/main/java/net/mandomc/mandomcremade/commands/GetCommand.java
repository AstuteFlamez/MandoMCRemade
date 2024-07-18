package net.mandomc.mandomcremade.commands;

import net.mandomc.mandomcremade.guis.ItemsGUI.ItemHub;
import net.mandomc.mandomcremade.managers.GUIManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GetCommand implements CommandExecutor {

    private final GUIManager guiManager;
    public GetCommand(GUIManager guiManager) {
        this.guiManager = guiManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;
        this.guiManager.openGUI(new ItemHub(guiManager), player);
        return true;
    }
}
