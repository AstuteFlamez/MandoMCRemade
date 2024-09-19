package net.mandomc.mandomcremade.db;

import net.mandomc.mandomcremade.db.data.RewardItem;
import org.bukkit.inventory.ItemStack;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemRewardsTable extends Database {

    public static void addItemReward(int poolId, ItemStack item) throws SQLException {
        int id = getItemPoolSize(poolId);
        byte[] bytes = RewardItem.convertToByteArray(item);

        Connection connection = getConnection();

        PreparedStatement statement = connection.prepareStatement("INSERT INTO questItemRewards(`PoolId`, `Id`, `ItemBlob`) VALUES (?,?,?)");
        statement.setInt(1, poolId);
        statement.setInt(2, id);
        statement.setBytes(3, bytes);

        statement.executeUpdate();
    }

    public static void removeItemReward(int poolId, int id) throws SQLException {
        Connection connection = getConnection();

        PreparedStatement statement = connection.prepareStatement("DELETE FROM questItemRewards WHERE PoolId = ? AND Id = ?");
        statement.setInt(1, poolId);
        statement.setInt(2, id);

        statement.executeUpdate();

        statement.close();
        connection.close();
    }

    public static List<RewardItem> getItemRewards(int poolId) throws SQLException {
        List<RewardItem> items = new ArrayList<RewardItem>();

        Connection connection = getConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM questItemRewards WHERE PoolId = ?");
        statement.setInt(1, poolId);

        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("Id");
            byte[] blob = resultSet.getBytes("ItemBlob");

            items.add(new RewardItem(poolId, id, blob));
        }

        resultSet.close();
        statement.close();
        connection.close();

        return items;
    }

    public static int getItemPoolSize(int poolId) throws SQLException {
        Connection connection = getConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) as size FROM questItemRewards WHERE poolId = ?");
        statement.setInt(1, poolId);

        ResultSet resultSet = statement.executeQuery();

        int size = 0;
        while (resultSet.next()) {
            size = resultSet.getInt("size");
        }

        resultSet.close();
        statement.close();
        connection.close();

        return size;
    }
}
