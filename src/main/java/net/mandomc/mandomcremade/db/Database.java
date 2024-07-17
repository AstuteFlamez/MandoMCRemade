package net.mandomc.mandomcremade.db;

import net.mandomc.mandomcremade.MandoMCRemade;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

public class Database {

    private Connection connection;

    public Connection getConnection() throws SQLException {

        if(connection != null){
            return connection;
        }

        String url = MandoMCRemade.getInstance().getConfig().getString("DatabaseURL");
        String user = MandoMCRemade.getInstance().getConfig().getString("DatabaseUSER");
        String password = MandoMCRemade.getInstance().getConfig().getString("DatabasePASSWORD");

        this.connection = DriverManager.getConnection(url, user, password);

        Bukkit.getConsoleSender().sendMessage("[MandoMCRemade] Database connected!");

        return this.connection;
    }

    public void initializeDatabase() throws SQLException{

        Statement statement = getConnection().createStatement();
        String perks = "CREATE TABLE IF NOT EXISTS perks(uuid varchar(36) primary key, lightsaberthrow int)";
        
        statement.execute(perks);;

        statement.close();

        Bukkit.getConsoleSender().sendMessage("[MandoMCRemade] Created tables successfully!");
    }

    public void createPerks(Perks perks) throws SQLException {

        PreparedStatement statement = getConnection()
                .prepareStatement("INSERT INTO perks(uuid, lightsaberthrow) VALUES (?, ?)");
        statement.setString(1, perks.getPlayerUUID().toString());
        statement.setInt(2, 0);

        statement.executeUpdate();

        statement.close();

    }

    public void updatePerks(Perks perks) throws SQLException {

        if(perks!=null) {

            PreparedStatement statement = getConnection().prepareStatement("UPDATE perks SET lightsaberthrow = ? WHERE uuid = ?");
            statement.setInt(1, perks.getLightsaberThrow());
            statement.setString(2, perks.getPlayerUUID().toString());

            statement.executeUpdate();

            statement.close();
        }else{
            Bukkit.broadcastMessage("null");
        }
    }

    public Perks getPerks(Player player) throws SQLException{

        PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM perks WHERE uuid = ?");
        statement.setString(1, player.getUniqueId().toString());

        ResultSet resultSet = statement.executeQuery();

        Perks perks;

        if(resultSet.next()){

            perks = new Perks(UUID.fromString(resultSet.getString("uuid")), resultSet.getInt("lightsaberthrow"));

            statement.close();

            return perks;
        }
        statement.close();

        return null;
    }

    public void deletePerks(Perks perks) throws SQLException {

        PreparedStatement statement = getConnection().prepareStatement("DELETE FROM forcepowers WHERE uuid = ?");
        statement.setString(1, perks.getPlayerUUID().toString());

        statement.executeUpdate();

        statement.close();
    }
}
