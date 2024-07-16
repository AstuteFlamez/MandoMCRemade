package net.mandomc.mandomcremade;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import net.mandomc.mandomcremade.commands.*;
import net.mandomc.mandomcremade.config.SaberConfig;
import net.mandomc.mandomcremade.config.WarpConfig;
import net.mandomc.mandomcremade.koth.KOTHCommand;
import net.mandomc.mandomcremade.koth.KOTHManager;
import net.mandomc.mandomcremade.listeners.*;
import net.mandomc.mandomcremade.guis.GUIListener;
import net.mandomc.mandomcremade.guis.GUIManager;
import net.mandomc.mandomcremade.utility.CustomItems;
import net.mandomc.mandomcremade.utility.Messages;
import net.mandomc.mandomcremade.utility.Recipes;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.TimeUnit;

public final class MandoMCRemade extends JavaPlugin implements Listener {

    public static MandoMCRemade instance;
    private KOTHManager kothManager;
    private final HashMap<UUID, Long> lightsaberCooldown;

    public MandoMCRemade() {
        lightsaberCooldown = new HashMap<>();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        getServer().getConsoleSender().sendMessage("[MandoMC]: Plugin is enabled");

        instance = this;

        getConfig().options().copyDefaults();
        saveDefaultConfig();
        WarpConfig.setup();
        WarpConfig.get().options().copyDefaults(true);
        WarpConfig.save();
        SaberConfig.setup();
        SaberConfig.get().options().copyDefaults(true);
        SaberConfig.save();

        GUIManager guiManager = new GUIManager();
        GUIListener guiListener = new GUIListener(guiManager);
        Bukkit.getPluginManager().registerEvents(guiListener, this);

        Recipes.createRecipe(CustomItems.lightsaberCore(), "core", Recipes.core);
        Recipes.createRecipe(CustomItems.hilt("LukeSkywalker"), "lukeskywalkerhilt", Recipes.lukeSkywalkerHilt);
        Recipes.createRecipe(CustomItems.lightSaber("LukeSkywalker"), "lukeskywalkersaber", Recipes.lukeSkywalkerSaber);
        Recipes.createRecipe(CustomItems.hilt("AnakinSkywalker"), "anakinskywalkerhilt", Recipes.anakinSkywalkerHilt);
        Recipes.createRecipe(CustomItems.lightSaber("AnakinSkywalker"), "anakinskywalkersaber", Recipes.anakinSkywalkerSaber);

        getCommand("mmc").setExecutor(new MMC(guiManager, this));

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new SaberThrowListener(lightsaberCooldown, this), this);
        getServer().getPluginManager().registerEvents(this, this);

        //Define the KOTH location and capture radius
        Location kothLocation = new Location(Bukkit.getWorld("world"), 0, 64, 0);
        double captureRadius = 10.0;

        kothManager = new KOTHManager(this, kothLocation, captureRadius);
        this.getCommand("koth").setExecutor(new KOTHCommand(this, kothManager));

        // Schedule KOTH event every 4 hours
        new BukkitRunnable() {
            @Override
            public void run() {
                kothManager.startKOTH();
            }
        }.runTaskTimer(this, TimeUnit.MINUTES.toSeconds(5) * 20, TimeUnit.HOURS.toSeconds(4) * 20);

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Status.Server.SERVER_INFO) {
            @Override
            public void onPacketSending(PacketEvent event) {
                WrappedServerPing packet = event.getPacket().getServerPings().read(0);

                packet.setPlayersMaximum(66);

                if (getConfig().getBoolean("Maintenance")) {
                    packet.setVersionProtocol(1);
                    packet.setVersionName("Maintenance");;
                    packet.setMotD(Messages.str(getConfig().getString("MaintenanceMOTD")));
                } else {
                    packet.setVersionName(getConfig().getString("VersionName"));;
                    packet.setMotD(Messages.str(getConfig().getString("MOTD")));
                }

                // Create WrappedGameProfile objects for the custom player list
                List<WrappedGameProfile> profiles = new ArrayList<>();
                for (int i = 0; i < getConfig().getStringList("ServerListHoverText").size(); i++) {
                    String hoverText = getConfig().getStringList("ServerListHoverText").get(i);
                    UUID uuid = UUID.randomUUID(); // Generate a random UUID
                    profiles.add(new WrappedGameProfile(uuid.toString(), hoverText));
                }

                packet.setPlayers(profiles);

                event.getPacket().getServerPings().write(0, packet);
            }
        });

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        ProtocolLibrary.getProtocolManager().removePacketListeners(this);
        getServer().getConsoleSender().sendMessage("[MandoMC]: Plugin is disabled");
        kothManager.endKOTH();

    }

    public static MandoMCRemade getInstance(){
        return instance;
    }

    public KOTHManager getKOTHManager() {
        return kothManager;
    }
}
