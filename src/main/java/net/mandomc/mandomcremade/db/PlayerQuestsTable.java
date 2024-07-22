package net.mandomc.mandomcremade.db;

import net.mandomc.mandomcremade.db.data.PlayerQuest;
import net.mandomc.mandomcremade.db.data.Quest;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class PlayerQuestsTable extends Database {

    public static void initializePlayerQuestsTable() throws SQLException {
        Connection connection = getConnection();

        Statement statement = connection.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS PlayerQuests(uuid varchar(36) primary key, QuestName varchar(64), Progress float)";

        statement.executeUpdate(sql);

        statement.close();

        Bukkit.getConsoleSender().sendMessage("[MandoMCRemade] Created player quests table successfully!");

        connection.close();
    }

    public static void playerStartQuest(String uuid, String questName) throws SQLException {
        Connection connection = getConnection();

        PreparedStatement statement = connection.prepareStatement("INSERT INTO PlayerQuests(uuid, QuestName, Progress) VALUES(?,?,?)");
        statement.setString(1, uuid);
        statement.setString(2, questName);
        statement.setFloat(3, 0.0f);

        statement.executeUpdate();

        statement.close();
        connection.close();
    }

    private static void playerStartQuests(String uuid, ArrayList<String> questNames) throws SQLException {
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO PlayerQuests(uuid, QuestName, Progress) VALUES(?,?,?)");
        ) {
            for (String questName : questNames) {
                statement.setString(1, uuid);
                statement.setString(2, questName);
                statement.setFloat(3, 0.0f);

                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    public static void removePlayerQuest(String uuid, String questName) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM PlayerQuests WHERE uuid = ? AND QuestName = ?");
        statement.setString(1, uuid);
        statement.setString(2, questName);

        statement.executeUpdate();

        statement.close();
        connection.close();
    }

    private static void playerFinishQuest(String uuid, String questName) throws SQLException {
        Connection connection = getConnection();

        PreparedStatement statement = connection.prepareStatement("UPDATE PlayerQuests SET Progress = ? WHERE uuid = ? AND QuestName = ?");

        statement.setFloat(1, 1.0f);
        statement.setString(2, uuid);
        statement.setString(3, questName);

        statement.executeUpdate();
        statement.close();
        connection.close();

        ArrayList<Quest> quests = QuestsTable.getChildren(questName);

        if (quests.isEmpty()) {
            return;
        }

        ArrayList<String> questNames = new ArrayList<>();
        for (Quest quest : quests) {
            questNames.add(quest.getQuestName());
        }

        playerStartQuests(uuid, questNames);
    }

    public static void updateQuestProgress(String uuid, String questName, float progress) throws SQLException {
        if (progress <= 0.0f) {
            progress = 0.0f;
        }
        if (progress > 1.0f) {
            progress = 1.0f;
        }
        Connection connection = getConnection();

        PreparedStatement statement = connection.prepareStatement("UPDATE PlayerQuests SET Progress = ? WHERE uuid = ? AND QuestName = ?");

        statement.setFloat(1, progress);
        statement.setString(2, uuid);
        statement.setString(3, questName);

        statement.executeUpdate();

        statement.close();
        connection.close();

        if (progress == 1.0f) {
            playerFinishQuest(uuid, questName);
        }
    }

    public static ArrayList<PlayerQuest> getInProgressQuests(String uuid) throws SQLException {
        Connection connection = getConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM PlayerQuests WHERE uuid = ? AND Progress < 1.0 ORDER BY Progress DESC");
        statement.setString(1, uuid);

        ResultSet resultSet = statement.executeQuery();

        ArrayList<PlayerQuest> inProgressQuests = new ArrayList<>();
        if (resultSet.next()) {
            PlayerQuest quest = new PlayerQuest(UUID.fromString(uuid), resultSet.getString("QuestName"), resultSet.getFloat("Progress"));
            inProgressQuests.add(quest);
        }

        resultSet.close();
        statement.close();
        connection.close();

        return inProgressQuests;
    }
}
