package net.mandomc.mandomcremade.commands;

import net.mandomc.mandomcremade.config.LangConfig;
import net.mandomc.mandomcremade.db.ItemRewardsTable;
import net.mandomc.mandomcremade.db.PlayerQuestsTable;
import net.mandomc.mandomcremade.db.QuestsTable;
import net.mandomc.mandomcremade.db.RewardPoolTable;
import net.mandomc.mandomcremade.db.data.PlayerQuest;
import net.mandomc.mandomcremade.db.data.Quest;
import net.mandomc.mandomcremade.db.data.QuestRewards;
import net.mandomc.mandomcremade.db.data.RewardItem;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static net.mandomc.mandomcremade.MandoMCRemade.str;

public class QuestCommand implements CommandExecutor, TabCompleter {

    public QuestCommand() {}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        FileConfiguration config = LangConfig.get();
        String prefix = str(config.getString("Prefix"));
        String noPermission = str(config.getString("NoPermission"));

        String action = args.length == 0 ? "list" : args[0].toLowerCase();
        String quest = args.length < 2 ? "" : args[1];
        try {
            switch (action) {
                case "create":
                    if (sender instanceof Player player && !player.hasPermission("mmc.quests.manage")) {

                        player.sendMessage(prefix + noPermission);
                        return true;
                    }
                    String desc = args.length >= 3 ? args[2] : "";
                    String trigger = args.length >= 4 ? args[3] : null;
                    Integer rewardPool = args.length >= 5 ? Integer.parseInt(args[4]) : null;
                    String parent = args.length >= 6 ? args[5] : null;
                    QuestsTable.addQuest(new Quest(quest, desc, trigger, rewardPool, parent));
                    return true;
                case "list":
                    if (Objects.equals(args.length >= 2 ? args[1].toLowerCase() : "", "all")) {
                        if (sender instanceof Player player && !player.hasPermission("mmc.quests.manage")) {
                            player.sendMessage(prefix + noPermission);
                            return true;
                        }
                        StringBuilder output = new StringBuilder();
                        List<Quest> quests = QuestsTable.getAllQuests();
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
                                return true;
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
                        List<PlayerQuest> quests = PlayerQuestsTable.getInProgressQuests(target.getUniqueId().toString());
                        for (PlayerQuest q : quests) {
                            output.append(q.getQuestName()).append(": ").append(String.format("%.0f",q.getQuestProgress() * 100)).append("%\n");

                            Quest quest1 = QuestsTable.getQuest(q.getQuestName());
                            output.append("\t").append(quest1.getQuestDesc());
                        }

                        OutputString(sender, output.toString());
                    }
                    return true;
                case "delete":
                    if (sender instanceof Player player && !player.hasPermission("mmc.quests.manage")) {
                        player.sendMessage(prefix + noPermission);
                        return true;
                    }
                    QuestsTable.removeQuest(quest);
                    return true;
                case "give":
                    if (sender instanceof Player player && !player.hasPermission("mmc.quests.manage")) {
                        player.sendMessage(prefix + noPermission);
                        return true;
                    }
                    String targetNameG = args.length >= 3 ? args[2] : "";

                    PlayerQuestsTable.playerStartQuest(Bukkit.getPlayer(targetNameG).getUniqueId().toString(), quest);
                    return true;
                case "take":
                    if (sender instanceof Player player && !player.hasPermission("mmc.quests.manage")) {
                        player.sendMessage(prefix + noPermission);
                        return true;
                    }
                    String targetNameT = args[2];

                    PlayerQuestsTable.removePlayerQuest(Bukkit.getPlayer(targetNameT).getUniqueId().toString(), quest);
                    return true;
                case "update":
                    if (sender instanceof Player player && !player.hasPermission("mmc.quests.manage")) {
                        player.sendMessage(prefix + noPermission);
                        return true;
                    }
                    String targetNameU = args.length >= 3 ? args[2] : "";
                    String progress = args.length >= 4 ? args[3] : "";

                    PlayerQuestsTable.updateQuestProgress(Bukkit.getPlayer(targetNameU).getUniqueId().toString(), quest, Float.parseFloat(progress));
                    return true;
                case "trigger":
                    if (sender instanceof Player player && !player.hasPermission("mmc.quests.manage")) {
                        player.sendMessage(prefix + noPermission);
                        return true;
                    }
                    String targetNameTr = args.length >= 3 ? args[2] : "";
                    String toTrigger = args.length >= 4 ? args[3] : "";
                    float progressAmount = args.length >= 5 ? Float.parseFloat(args[4]) : 1.0f;

                    List<PlayerQuest> quests = PlayerQuestsTable.getTriggeredQuests(Bukkit.getPlayer(targetNameTr).getUniqueId().toString(), toTrigger);

                    for (PlayerQuest q : quests) {
                        PlayerQuestsTable.updateQuestProgress(Bukkit.getPlayer(targetNameTr).getUniqueId().toString(), q.getQuestName(), progressAmount);
                    }

                    String output = quests.size() + " quests triggered for " + targetNameTr;

                    OutputString(sender, output);
                    return true;
                case "rewards":
                    if (sender instanceof Player player && !player.hasPermission("mmc.quests.manage")) {
                        player.sendMessage(prefix + noPermission);
                        return true;
                    }
                    String poolId = args.length >= 2 ? args[1] : "-1";

                    int poolCount = RewardPoolTable.getPoolCount();

                    if (poolId.equals("pools") || poolId.equals("-1")) {
                        OutputString(sender, "Pool Count: " + poolCount);
                        return true;
                    }

                    int pool = Integer.parseInt(poolId);

                    if (!RewardPoolTable.poolExists(pool))
                    {
                        if (poolCount + 1 == pool) {
                            RewardPoolTable.createNewPool();
                        }
                        else
                        {
                            OutputString(sender, "Pool " + pool + " does not exist and is not next to be added.");
                            return true;
                        }
                    }

                    String rewardAction = args.length >= 3 ? args[2] : "";
                    String rewardType = args.length >= 4 ? args[3] : "";

                    switch (rewardAction) {
                        case "add":

                            switch (rewardType) {
                                case "item":
                                    if (sender instanceof Player player) {
                                        ItemStack item = player.getInventory().getItemInMainHand();

                                        ItemRewardsTable.addItemReward(pool, item);

                                        OutputString(sender, "Added item " + item + " to pool " + pool);
                                        return true;
                                    }
                                    else {
                                        OutputString(sender, "Adding quest reward items from console is currently not supported.");
                                        return false;
                                    }

                                case "event":
                                    String eventType = args.length >= 5 ? args[4] : "";

                                    return true;

                            }

                        case "remove":
                            switch (rewardType) {
                                case "item":
                                    String itemId = args.length >= 5 ? args[4] : "";
                                    int intId = Integer.parseInt(itemId);

                                    ItemRewardsTable.removeItemReward(pool, intId);

                                    OutputString(sender, "Item " + intId + " removed from pool" + poolId);
                                    return true;

                                case "event":

                                    return true;
                            }

                        case "list":
                            StringBuilder sb = new StringBuilder();

                            sb.append("Rewards in Pool #").append(pool).append(":\n");
                            sb.append("--------------------").append("\n");

                            List<RewardItem> items = ItemRewardsTable.getItemRewards(pool);

                            if (!items.isEmpty()) {
                                sb.append("Items:\n");

                                for (RewardItem item : items) {
                                    sb.append(item.toString()).append("\n");
                                }

                                sb.append("--------------------").append("\n");
                            }

                            // TODO: add rewardEvent output

                            OutputString(sender, sb.toString());

                            return true;

                        case "give":
                            String targetNameRw = args.length >= 4 ? args[3] : "";
                            UUID targetUuid = Bukkit.getPlayer(targetNameRw).getUniqueId();

                            QuestRewards rewards = RewardPoolTable.getRewardPool(pool);

                            rewards.givePlayer(targetUuid);

                            OutputString(sender, "Rewards given to " + targetNameRw);
                            return true;

                        default:
                            OutputString(sender, "No reward type specified.");
                            OutputString(sender, "/quests rewards <pool> <add|remove|give|list>");
                            return true;
                    }
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
            case 1:
                completions.add("list");
                if (sender instanceof Player player && player.hasPermission("mmc.quests.manage")){
                    completions.add("create");
                    completions.add("delete");
                    completions.add("give");
                    completions.add("take");
                    completions.add("update");
                    completions.add("trigger");
                    completions.add("rewards");
                }
                break;
            case 2:
                if (args[0].equalsIgnoreCase("list")) {
                    if (sender instanceof Player player && !player.hasPermission("mmc.quests.manage")) break;
                    completions.add("all");
                    for (Player p: Bukkit.getOnlinePlayers()){
                        completions.add(p.getName());
                    }
                    break;
                }
                else if (args[0].equalsIgnoreCase("trigger")) {
                    if (sender instanceof Player player && !player.hasPermission("mmc.quests.manage")) break;
                    for (Player p: Bukkit.getOnlinePlayers()){
                        completions.add(p.getName());
                    }
                    break;
                }
                else if (args[0].equalsIgnoreCase("rewards")) {
                    if (sender instanceof Player player && !player.hasPermission("mmc.quests.manage")) break;
                    completions.add("pools");
                    break;
                }
                try {
                    List<Quest> quests = QuestsTable.getAllQuests();
                    for (Quest q: quests){
                        completions.add(q.getQuestName());
                    }
                } catch (SQLException e) {
                    throw new CommandException(e.getMessage());
                }
                break;
            case 3:
                switch (args[0].toLowerCase()){
                    case "give":
                    case "take":
                    case "update":
                        for (Player p: Bukkit.getOnlinePlayers()){
                            completions.add(p.getName());
                        }
                        break;
                    case "rewards":
                        if (sender instanceof Player player && !player.hasPermission("mmc.quests.manage")) break;
                        if (args[1].equalsIgnoreCase("pools")) break;
                        completions.add("add");
                        completions.add("remove");
                        completions.add("give");
                        completions.add("list");
                }
            case 4:
                if (args[0].equalsIgnoreCase("rewards")) {
                    if (sender instanceof Player player && !player.hasPermission("mmc.quests.manage")) break;
                    if (args[2].equalsIgnoreCase("give")) {
                        for (Player p: Bukkit.getOnlinePlayers()){
                            completions.add(p.getName());
                        }
                        break;
                    }
                    completions.add("item");
                    completions.add("event");
                    break;
                }
        }
//        sender.sendMessage(completions.toString());
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
