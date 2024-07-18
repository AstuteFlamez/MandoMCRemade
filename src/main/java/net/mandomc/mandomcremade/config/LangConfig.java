package net.mandomc.mandomcremade.config;

import net.mandomc.mandomcremade.MandoMCRemade;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.io.*;

public class LangConfig {

    private static File file;
    private static FileConfiguration customFile;

    //Finds or generates the custom config file
    public static void setup() {
        Server server = Bukkit.getServer();
        PluginManager pluginManager = server.getPluginManager();
        Plugin plugin = pluginManager.getPlugin("MandoMCRemade");
        assert plugin != null;
        File file = new File(plugin.getDataFolder(), "lang.yml");

        if (!file.exists()) {
            try (InputStream inputStream = MandoMCRemade.class.getResourceAsStream("/lang.yml");
                 OutputStream outputStream = new FileOutputStream(file)) {

                if (inputStream != null) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }
                }
            } catch (IOException e) {
                ConsoleCommandSender console = Bukkit.getConsoleSender();
                console.sendMessage("[MMC] The lang.yml file could not be found.");
            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get(){
        return customFile;
    }

    public static void save(){
        try{
            customFile.save(file);
        }catch (IOException e){
            ConsoleCommandSender console = Bukkit.getConsoleSender();
            console.sendMessage("[MMC] The lang.yml file could not be saved.");
        }
    }

    public static void reload(){
        customFile = YamlConfiguration.loadConfiguration(file);
    }

}