package net.mandomc.mandomcremade.db;

import net.mandomc.mandomcremade.db.data.Quest;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestsTable extends Database {

    public static void initializeQuestsTable() throws SQLException {
        Connection connection = getConnection();

        Statement statement = connection.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS quests(QuestName varchar(64) primary key, Description text, QuestTrigger varchar(64), RewardPool int, Parent varchar(64))";

        statement.executeUpdate(sql);

        statement.close();

        Bukkit.getConsoleSender().sendMessage("[MandoMCRemade] Created quests table successfully!");

        connection.close();
    }

    public static void addQuest(Quest quest) throws SQLException {
        Connection connection = getConnection();

        PreparedStatement statement = connection.prepareStatement("INSERT INTO quests (QuestName, Description, QuestTrigger, RewardPool, Parent) VALUES (?, ?, ?, ?, ?)");
        statement.setString(1, quest.getQuestName());
        statement.setString(2, quest.getQuestDesc());
        statement.setString(3, quest.getQuestTrigger());
        statement.setInt(4, quest.getRewardsPool());
        statement.setString(5, quest.getParent());

        statement.executeUpdate();

        statement.close();
        connection.close();
    }

    public static void removeQuest(String questName) throws SQLException {
        Connection connection = getConnection();

        PreparedStatement statement = connection.prepareStatement("DELETE FROM quests WHERE QuestName = ?");
        statement.setString(1, questName);

        statement.executeUpdate();

        statement.close();
        connection.close();
    }

    public static Quest getQuest(String questName) throws SQLException {
        Connection connection = getConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM quests WHERE QuestName = ?");
        statement.setString(1, questName);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            Quest quest = new Quest(resultSet.getString("QuestName"), resultSet.getString("Description"), resultSet.getString("QuestTrigger"), resultSet.getInt("RewardPool"), resultSet.getString("Parent"));

            statement.close();
            connection.close();
            return quest;
        }

        statement.close();
        connection.close();
        return null;
    }

    public static List<Quest> getAllQuests() throws SQLException {
        Connection connection = getConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM quests");

        ResultSet resultSet = statement.executeQuery();
        ArrayList<Quest> quests = new ArrayList<>();
        while (resultSet.next()) {
            Quest quest = new Quest(resultSet.getString("QuestName"), resultSet.getString("Description"), resultSet.getString("QuestTrigger"), resultSet.getInt("RewardPool"), resultSet.getString("Parent"));
            quests.add(quest);
        }

        resultSet.close();
        statement.close();
        connection.close();
        return quests;
    }

    public static List<Quest> getChildren(String parent) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM quests WHERE Parent = ?");
        statement.setString(1, parent);

        ResultSet resultSet = statement.executeQuery();
        ArrayList<Quest> quests = new ArrayList<>();
        while (resultSet.next()) {
            Quest child = new Quest(resultSet.getString("QuestName"), resultSet.getString("Description"), resultSet.getString("QuestTrigger"), resultSet.getInt("RewardPool"), resultSet.getString("Parent"));
            quests.add(child);
        }

        resultSet.close();
        statement.close();
        connection.close();
        return quests;
    }
}
