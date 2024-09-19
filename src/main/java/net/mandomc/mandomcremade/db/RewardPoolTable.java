package net.mandomc.mandomcremade.db;

import net.mandomc.mandomcremade.db.data.QuestRewards;
import net.mandomc.mandomcremade.db.data.RewardEvent;
import net.mandomc.mandomcremade.db.data.RewardItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RewardPoolTable extends Database {

    public static void createNewPool() throws SQLException {
        Connection connection = getConnection();

        Statement statement = connection.createStatement();
        String sql = "INSERT INTO questRewardPool(`PoolId`) VALUES (0)";

        statement.executeUpdate(sql);

        statement.close();
        connection.close();
    }

    public static boolean poolExists(int poolId) throws SQLException {
        Connection connection = getConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM questRewardPool WHERE `PoolId` = ?");
        statement.setInt(1, poolId);

        ResultSet resultSet = statement.executeQuery();

        boolean exists = resultSet.next();

        statement.close();
        connection.close();

        return exists;
    }

    public static int getPoolCount() throws SQLException {
        Connection connection = getConnection();

        Statement statement = connection.createStatement();
        String sql = "SELECT COUNT(*) as size FROM questRewardPool";

        ResultSet resultSet = statement.executeQuery(sql);

        int size = 0;
        while (resultSet.next()) {
            size = resultSet.getInt("size");
        }

        resultSet.close();
        statement.close();
        connection.close();

        return size;
    }

    public static QuestRewards getRewardPool(int poolId) throws SQLException {
        Connection connection = getConnection();

        PreparedStatement statement1 = connection.prepareStatement("SELECT * FROM questItemRewards WHERE PoolId = ?");
        statement1.setInt(1, poolId);

        ResultSet resultSet = statement1.executeQuery();
        List<RewardItem> items = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("Id");
            byte[] bytes = resultSet.getBytes("ItemBlob");

            items.add(new RewardItem(poolId, id, bytes));
        }
        resultSet.close();
        statement1.close();

        PreparedStatement statement2 = connection.prepareStatement("SELECT * FROM questEventRewards WHERE PoolId = ?");
        statement2.setInt(1, poolId);

        ResultSet resultSet2 = statement2.executeQuery();
        List<RewardEvent> events = new ArrayList<>();
        while (resultSet2.next()) {
            int id = resultSet2.getInt("Id");
            String eventName = resultSet2.getString("eventName");
            String metaData = resultSet2.getString("metaData");

            events.add(new RewardEvent(id, eventName, metaData, poolId));
        }

        resultSet2.close();
        statement2.close();
        connection.close();

        return new QuestRewards(items, events);
    }
}
