package net.mandomc.mandomcremade.db;

import java.sql.*;

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
}
