package net.mandomc.mandomcremade;

import net.mandomc.mandomcremade.commands.Warp;
import net.mandomc.mandomcremade.config.SaberConfig;
import net.mandomc.mandomcremade.config.WarpConfig;
import net.mandomc.mandomcremade.listeners.LightsaberThrow;
import net.mandomc.mandomcremade.listeners.VehicleSafety;
import net.mandomc.mandomcremade.listeners.WarpTeleport;
import net.mandomc.mandomcremade.tasks.KothRunnable;
import net.mandomc.mandomcremade.tasks.ShipsRunnable;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public final class MandoMCRemade extends JavaPlugin {

    public static MandoMCRemade instance;
    private final HashMap<UUID, Long> lightsaberCooldown;

    public MandoMCRemade() {
        lightsaberCooldown = new HashMap<>();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        BukkitTask vehicleTask = new ShipsRunnable(this).runTaskTimer(this, 0L, 1L);
        BukkitTask kothTask = new KothRunnable(this).runTaskTimer(this, 0L, 20L);

        getConfig().options().copyDefaults();
        saveDefaultConfig();
        WarpConfig.setup();
        WarpConfig.get().options().copyDefaults(true);
        WarpConfig.save();
        SaberConfig.setup();
        SaberConfig.get().options().copyDefaults(true);
        SaberConfig.save();

        getCommand("warp").setExecutor(new Warp());

        getServer().getPluginManager().registerEvents(new VehicleSafety(), this);
        getServer().getPluginManager().registerEvents(new WarpTeleport(), this);
        getServer().getPluginManager().registerEvents(new LightsaberThrow(lightsaberCooldown), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static MandoMCRemade getInstance(){
        return instance;
    }
}
