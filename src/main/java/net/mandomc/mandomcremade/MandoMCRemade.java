package net.mandomc.mandomcremade;

import net.mandomc.mandomcremade.commands.*;
import net.mandomc.mandomcremade.config.SaberConfig;
import net.mandomc.mandomcremade.config.WarpConfig;
import net.mandomc.mandomcremade.enchants.JedisLuckEnchant;
import net.mandomc.mandomcremade.listeners.LightsaberThrowEvent;
import net.mandomc.mandomcremade.listeners.VehicleSafetyEvents;
import net.mandomc.mandomcremade.listeners.WarpClickEvent;
import net.mandomc.mandomcremade.tasks.KothScheduler;
import net.mandomc.mandomcremade.tasks.ShipsRunnable;
import net.mandomc.mandomcremade.utility.Messages;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public final class MandoMCRemade extends JavaPlugin {

    public static MandoMCRemade instance;
    public static String kothTime = Messages.str("The next KOTH will be in ");
    private final HashMap<UUID, Long> lightsaberCooldown;

    public MandoMCRemade() {
        lightsaberCooldown = new HashMap<>();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        new ShipsRunnable(this).runTaskTimer(this, 0L, 1L);
        KothScheduler kothScheduler = new KothScheduler();
        kothScheduler.start();
        kothTime += kothScheduler.timeUntilNextTask();

        getConfig().options().copyDefaults();
        saveDefaultConfig();
        WarpConfig.setup();
        WarpConfig.get().options().copyDefaults(true);
        WarpConfig.save();
        SaberConfig.setup();
        SaberConfig.get().options().copyDefaults(true);
        SaberConfig.save();

        getCommand("warp").setExecutor(new Warp());
        getCommand("forcestartkoth").setExecutor(new ForceStartKoth());
        getCommand("yaw").setExecutor(new Yaw());
        getCommand("pitch").setExecutor(new Pitch());
        getCommand("reload").setExecutor(new Reload());
        getCommand("recipes").setExecutor(new Recipes());
        getCommand("give").setExecutor(new Give());
        getCommand("get").setExecutor(new Get());
        getCommand("vehicle").setExecutor(new Vehicle());
        getCommand("enchant").setExecutor(new Enchant());


        getServer().getPluginManager().registerEvents(new VehicleSafetyEvents(), this);
        getServer().getPluginManager().registerEvents(new WarpClickEvent(), this);
        getServer().getPluginManager().registerEvents(new LightsaberThrowEvent(lightsaberCooldown), this);
        getServer().getPluginManager().registerEvents(new JedisLuckEnchant(0.00), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static MandoMCRemade getInstance(){
        return instance;
    }

}
