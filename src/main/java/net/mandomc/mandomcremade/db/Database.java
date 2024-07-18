package net.mandomc.mandomcremade.db;

import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.db.data.Perks;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

public class Database {

    public static Connection getConnection() throws SQLException {

        String url = MandoMCRemade.getInstance().getConfig().getString("DatabaseURL");
        String user = MandoMCRemade.getInstance().getConfig().getString("DatabaseUSER");
        String password = MandoMCRemade.getInstance().getConfig().getString("DatabasePASSWORD");

        Connection connection = DriverManager.getConnection(url, user, password);

        Bukkit.getConsoleSender().sendMessage("[MandoMCRemade] Database connected!");

        return connection;
    }
}
