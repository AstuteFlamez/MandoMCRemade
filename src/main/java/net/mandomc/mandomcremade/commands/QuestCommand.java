package net.mandomc.mandomcremade.commands;

import net.mandomc.mandomcremade.config.LangConfig;
import net.mandomc.mandomcremade.db.PlayerQuestsTable;
import net.mandomc.mandomcremade.db.QuestsTable;
import net.mandomc.mandomcremade.db.data.PlayerQuest;
import net.mandomc.mandomcremade.db.data.Quest;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
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

        String action = args[0].toLowerCase();
        String quest = args[1];
        try {
            switch (action) {
                case "create":
                    if (sender instanceof Player player && !player.hasPermission("mmc.quests.manage")) {

                        player.sendMessage(prefix + noPermission);
                        break;
                    }
                    String desc = args[2];
                    String parent = null;
                    if (args.length > 3) {
                        parent = args[3];
                    }
                    QuestsTable.addQuest(new Quest(quest, desc, parent));
                    break;
                case "list":
                    if (Objects.equals(args[1].toLowerCase(), "all")) {
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
                    else {
                        Player target;
                        if (args.length == 2) {
                            if (sender instanceof Player player && !player.hasPermission("mmc.quests.manage")) {
                                player.sendMessage(prefix + noPermission);
                                break;
                            }
                            target = Bukkit.getPlayer(args[1]);
                        }
                        else {
                            if (sender instanceof Player player) {
                                target = player;
                            }
                            else {
                                throw new CommandException("No Player Specified");
                            }
                        }
                        StringBuilder output = new StringBuilder();
                        ArrayList<PlayerQuest> quests = PlayerQuestsTable.getInProgressQuests(target.getUniqueId().toString());
                        for (PlayerQuest q : quests) {
                            output.append(q.getQuestName()).append(": ").append(String.format("%.0f",q.getQuestProgress() * 100)).append("%\n");

                            Quest quest1 = QuestsTable.getQuest(q.getQuestName());
                            output.append("\t").append(quest1.getQuestDesc());
                        }

                        OutputString(sender, output.toString());
                    }
                    break;
                case "delete":
                    if (sender instanceof Player player && !player.hasPermission("mmc.quests.manage")) {
                        player.sendMessage(prefix + noPermission);
                        break;
                    }
                    QuestsTable.removeQuest(quest);
                    break;
                case "give":
                    if (sender instanceof Player player && !player.hasPermission("mmc.quests.manage")) {
                        player.sendMessage(prefix + noPermission);
                        break;
                    }
                    String targetNameG = args[2];

                    PlayerQuestsTable.playerStartQuest(Bukkit.getPlayer(targetNameG).getUniqueId().toString(), quest);
                    break;
                case "take":
                    if (sender instanceof Player player && !player.hasPermission("mmc.quests.manage")) {
                        player.sendMessage(prefix + noPermission);
                        break;
                    }
                    String targetNameT = args[2];

                    PlayerQuestsTable.removePlayerQuest(Bukkit.getPlayer(targetNameT).getUniqueId().toString(), quest);
                    break;
                case "update":
                    if (sender instanceof Player player && !player.hasPermission("mmc.quests.manage")) {
                        player.sendMessage(prefix + noPermission);
                        break;
                    }
                    String targetNameU = args[2];
                    String progress = args[3];

                    PlayerQuestsTable.updateQuestProgress(Bukkit.getPlayer(targetNameU).getUniqueId().toString(), quest, Float.parseFloat(progress));
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
        switch (args.length){
            case 0:
                completions.add("list");
                if (sender instanceof Player player && !player.hasPermission("mmc.quests.manage")){
                    completions.add("create");
                    completions.add("delete");
                    completions.add("list");
                    completions.add("give");
                    completions.add("take");
                    completions.add("update");
                }
                break;
            case 1:
                if (args[0].equalsIgnoreCase("list")) {
                    if (sender instanceof Player player && !player.hasPermission("mmc.quests.manage")) break;
                    completions.add("all");
                    ArrayList<Player> players = (ArrayList<Player>) Bukkit.getOnlinePlayers();
                    for (Player p: players){
                        completions.add(p.getName());
                    }
                    break;
                }
                try {
                    ArrayList<Quest> quests = QuestsTable.getAllQuests();
                    for (Quest q: quests){
                        completions.add(q.getQuestName());
                    }
                } catch (SQLException e) {
                    throw new CommandException(e.getMessage());
                }
                break;
            case 2:
                switch (args[0].toLowerCase()){
                    case "give":
                    case "take":
                    case "update":
                        ArrayList<Player> players = (ArrayList<Player>) Bukkit.getOnlinePlayers();
                        for (Player p: players){
                            completions.add(p.getName());
                        }
                        break;
                }
        }
        return completions;
    }

    private static void OutputString(@NotNull CommandSender sender, String output) {
        if (sender instanceof Player player) {
            player.sendMessage(output);
        }
        else {
            Bukkit.getConsoleSender().sendMessage(output);
        }
    }
}
