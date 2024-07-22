package net.mandomc.mandomcremade.db;

import net.mandomc.mandomcremade.db.data.Perks;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

public class PerksTable extends Database {

    public static void initializePerksTable() throws SQLException {

        Connection connection = getConnection();

        Statement statement = connection.createStatement();
        String perks = "CREATE TABLE IF NOT EXISTS perks(uuid varchar(36) primary key, lightsaberthrow tinyint(1))";

        statement.execute(perks);;

        statement.close();

        Bukkit.getConsoleSender().sendMessage("[MandoMCRemade] Created perks table successfully!");

        connection.close();
    }

    public static void addPlayer(Perks perks) throws SQLException {

        Connection connection = getConnection();

        PreparedStatement statement = connection
                .prepareStatement("INSERT INTO perks(uuid, lightsaberthrow) VALUES (?, ?)");
        statement.setString(1, perks.getPlayerUUID().toString());
        statement.setBoolean(2, false);

        statement.executeUpdate();

        statement.close();
        connection.close();
    }

    public static void updatePerks(Perks perks) throws SQLException {
        Connection connection = getConnection();
        if(perks!=null) {

            PreparedStatement statement = connection.prepareStatement("UPDATE perks SET lightsaberthrow = ? WHERE uuid = ?");
            statement.setBoolean(1, perks.getLightsaberThrow());
            statement.setString(2, perks.getPlayerUUID().toString());

            statement.executeUpdate();

            statement.close();
        }else{
            Bukkit.getConsoleSender().sendMessage("Player uuid not found when updating perks.");
        }
        connection.close();
    }

    public static Perks getPerks(Player player) throws SQLException{

        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM perks WHERE uuid = ?");
        statement.setString(1, player.getUniqueId().toString());

        ResultSet resultSet = statement.executeQuery();

        Perks perks;

        if(resultSet.next()){

            perks = new Perks(UUID.fromString(resultSet.getString("uuid")), resultSet.getBoolean("lightsaberthrow"));

            statement.close();
            connection.close();

            return perks;
        }
        statement.close();
        connection.close();

        return null;
    }

    public static void deletePerks(Perks perks) throws SQLException {

        Connection connection = getConnection();
        PreparedStatement statement = getConnection().prepareStatement("DELETE FROM forcepowers WHERE uuid = ?");
        statement.setString(1, perks.getPlayerUUID().toString());

        statement.executeUpdate();

        statement.close();
        connection.close();
    }
}
