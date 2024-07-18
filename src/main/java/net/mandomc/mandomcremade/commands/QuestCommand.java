package net.mandomc.mandomcremade.commands;

import net.mandomc.mandomcremade.config.LangConfig;
import net.mandomc.mandomcremade.db.QuestsTable;
import net.mandomc.mandomcremade.db.data.Quest;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static net.mandomc.mandomcremade.MandoMCRemade.str;

public class QuestCommand implements CommandExecutor, TabCompleter {

    public QuestCommand() {}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        FileConfiguration config = LangConfig.get();
        String prefix = str(config.getString("Prefix"));
        String noPermission = str(config.getString("NoPermission"));

        String action = args[0];
        String quest = args[1];
        try {
            switch (action) {
                case "create":
                    if (sender instanceof Player player && !player.hasPermission("mmc.quests.manage")) {

                        player.sendMessage(prefix + noPermission);
                        break;
                    }
                    String desc = args[2];
                    QuestsTable.addQuest(new Quest(quest, desc));
                    break;
                case "list":
                    if (Objects.equals(args[3].toLowerCase(), "all")) {
                        if (sender instanceof Player player && !player.hasPermission("mmc.quests.manage")) {
                            player.sendMessage(prefix + noPermission);
                            break;
                        }
                        StringBuilder output = new StringBuilder();
                        ArrayList<Quest> quests = QuestsTable.getAllQuests();
                        for (Quest q : quests) {
                            output.append(q.getQuestName()).append("\n");
                        }
                        OutputString(sender, output.toString());
                    }
                    // TODO: Add player quest listing
                    break;
                case "delete":
                    if (sender instanceof Player player && !player.hasPermission("mmc.quests.manage")) {
                        player.sendMessage(prefix + noPermission);
                        break;
                    }
                    QuestsTable.removeQuest(quest);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        ArrayList<String> completions = new ArrayList<>();
        if (args.length == 1){
            completions.add("list");
            if (sender instanceof Player player && !player.hasPermission("mmc.quests.manage")){
                completions.add("create");
                completions.add("delete");
            }
            return completions;
        }
        return List.of();
    }

    private void OutputString(@NotNull CommandSender sender, String output) {
        if (sender instanceof Player player) {
            player.sendMessage(output);
        }
        else {
            Bukkit.getConsoleSender().sendMessage(output);
        }
    }
}
